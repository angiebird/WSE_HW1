package edu.nyu.cs.cs2580;

import java.util.Scanner;
import java.util.Vector;

public class RankerSimple extends Ranker{

	protected RankerSimple(Index indexer) {
		super(indexer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector<ScoredDocument> runQuery(String query) {
		Vector < ScoredDocument > retrieval_results = new Vector < ScoredDocument > ();
		for (int i = 0; i < _index.numDocs(); ++i){
			retrieval_results.add(runquery(query, i));
		}
		return retrieval_results;
	}

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
		Vector < String > dv = d.get_title_vector();
		// Score the document. Here we have provided a very simple ranking model,
		// where a document is scored 1.0 if it gets hit by at least one query term.
		double score = 0.0;
		for (int i = 0; i < dv.size(); ++i){
			for (int j = 0; j < qv.size(); ++j){
				if (dv.get(i).equals(qv.get(j))){
					score = 1.0;
					break;
				}
			}
		}

		return new ScoredDocument(did, d.get_title_string(), score);
	}

}
