package edu.nyu.cs.cs2580;

import java.util.Scanner;
import java.util.Vector;

public class RankerLinear extends Ranker {

	final double betaCosine = 0.33;
	final double betaPhrase = 0.33;
	final double betaQL = 0.33;
	final double betaNumViews = 0.01;
	protected RankerLinear(Index index) {
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
		for (int i = 0; i < _index.numDocs(); ++i) {
			double score = 0.0;
			ScoredDocument sd = RankerCosine(query, i);
			score += (betaCosine * sd._score);
			System.out.print("\nCosine score" + score);
			sd = RankerPhrase(query, i);
			score += (betaPhrase * sd._score);
			System.out.print(" Phrase score" + (betaPhrase * sd._score ));
			sd = RankerQL(query, i);
			score += (betaQL * sd._score);
			System.out.print(" QL score" + (betaQL * sd._score));
			sd = RankerNumviews(query, i);
			score += (betaNumViews * sd._score);
			System.out.print(" Numviews score" + (betaNumViews * sd._score));
			return new ScoredDocument(i, sd._title, score);
		}

		
	}

}
