package edu.nyu.cs.cs2580;



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.print.attribute.standard.NumberOfDocuments;

public class RankerCosine extends Ranker {

	protected RankerCosine(Index index) {
		super(index);
		// TODO Auto-generated constructor stub
	}


	public ScoredDocument runquery(String query, int did){
		double score = 0.0;
		int dotProduct = 0;
	    int normA = 0;
	    int normB = 0;
		int noOfDocuments = _index.numDocs();
		Scanner s = new Scanner(query);
		Vector < String > qv = new Vector < String > ();
		Map<String,Integer> queryMap = new HashMap<String,Integer>();
		Map<String,Double> documentMap = new HashMap<String,Double>();
		while (s.hasNext()){
			String term = s.next();
			qv.add(term);
			//System.out.println("term is " + term);
		}
		//System.out.println("the length of query is" + qv.size());
		// Get the document vector. For hw1, you don't have to worry about the
		// details of how index works.
		Document d = _index.getDoc(did);
		//Vector < String > dv = d.get_title_vector();
		Vector < String > dv = d.get_body_vector();
		// Score the document. Here we have provided a very simple ranking model,
		// where a document is scored 1.0 if it gets hit by at least one query term
		//System.out.println("the length of query is" + dv.size());
		// This will create the integer query vector
		for(String queryWord : qv){
			if(dv.contains(queryWord))
			{
				queryMap.put(queryWord,1);
			}
			
		}

		// count how many times a word occurs in a document ie tf that will be used to calculate tf.idf
		for(String word : dv){
		   if (documentMap.containsKey(word)){
			   documentMap.put(word, (documentMap.get(word))+1);
		   }
		   else{
			   documentMap.put(word, 1.0);
		   }
		}
		
		//to calculate the double document vector
		for (String key : documentMap.keySet()){
			// This will calulate idf with the formula given on page 11
			double idf = 1 + Math.log((noOfDocuments/Document.documentFrequency(key)));
			double tfidf = documentMap.get(key) * idf;
			documentMap.put(key, tfidf);
		}
		// to calculate score by cosine similiarity
			for (String key: documentMap.keySet()){
			    normA += Math.pow(documentMap.get(key), 2);
				if(queryMap.containsKey(key)){
					dotProduct += documentMap.get(key) * queryMap.get(key);
					normB += Math.pow(queryMap.get(key), 2);
				}
			}
			if((Math.sqrt(normA) * Math.sqrt(normB)) > 1)
				score = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
		//System.out.println("score is" + score + " title is " + d.get_title_string());
		
		return new ScoredDocument(did, d.get_title_string(), score);
	}

}

