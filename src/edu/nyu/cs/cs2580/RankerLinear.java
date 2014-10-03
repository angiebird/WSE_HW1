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

		Document d = _index.getDoc(did);
		double score = 0.0;
		ScoredDocument sd = new RankerCosine(_index).runquery(query, did);
		score += (betaCosine * sd._score);
		System.out.print("\nCosine score" + score);
		sd = new RankerPhrase(_index).runquery(query, did);
		score += (betaPhrase * sd._score);
		System.out.print(" Phrase score" + (betaPhrase * sd._score ));
		sd = new RankerQL(_index).runquery(query, did);
		score += (betaQL * sd._score);
		System.out.print(" QL score" + (betaQL * sd._score));
		sd = new RankerNumviews(_index).runquery(query, did);
		score += (betaNumViews * sd._score);
		System.out.print(" Numviews score" + (betaNumViews * sd._score));
		return new ScoredDocument(did, d.get_title_string(), score);


	}
}


