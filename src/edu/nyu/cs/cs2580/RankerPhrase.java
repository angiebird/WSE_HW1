package edu.nyu.cs.cs2580;

import java.util.Scanner;
import java.util.Vector;

public class RankerPhrase extends Ranker {

	protected RankerPhrase(Index index) {
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
		double score = 0.0;
	    for (int j = 0; j+1 < qv.size(); ++j){
		    for (int i = 0; i+1 < dv.size(); ++i){
				if (dv.get(i).equals(qv.get(j)) && dv.get(i+1).equals(qv.get(j+1))){
                    score += 1;
				}
			}
		}

		return new ScoredDocument(did, d.get_title_string(), score);
	}

}
