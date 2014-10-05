package edu.nyu.cs.cs2580;

import java.util.Scanner;
import java.util.Vector;

public class RankerQL extends Ranker {

	protected RankerQL(Index index) {
		super(index);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ScoredDocument runquery(String query, int did){
		// you will be implementing score fuctions here - Q1 
		// Build query vector
		Scanner s = new Scanner(query);
		Vector < String > qv = new Vector < String > ();
		while (s.hasNext()){
			String term = s.next();
			qv.add(term);
			//System.out.println("term is " + term);
		}

		// Get the document vector. For hw1, you don't have to worry about the
		// details of how index works.
		Document d = _index.getDoc(did);
		Vector < String > dv = d.get_body_vector();
		// Score the document. 
		double score = 0.0;
        double lambda = 0.5;
	    for (int j = 0; j < qv.size(); ++j){
            // pqd: probability of jth term in qv occurs in document did
            // pqc: probability of jth term in qv occurs in collection of all document
            double pqd =  0.0;
            double pqc = Document.termFrequency(qv.get(j))/Document.termFrequency();
		    for (int i = 0; i < dv.size(); ++i){
				if (dv.get(i).equals(qv.get(j))){
                    pqd += 1;
				}
			}
            pqd /= dv.size();
            score += Math.log((1-lambda)*pqd + lambda*pqc);
		}

        if(qv.size() > 0){
            score = Math.pow(Math.E, score);
        }

		return new ScoredDocument(did, d.get_title_string(), score);
	}

}
