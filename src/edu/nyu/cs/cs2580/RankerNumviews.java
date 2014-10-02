package edu.nyu.cs.cs2580;

import java.util.Vector;

public class RankerNumviews extends Ranker {

	protected RankerNumviews(Index index) {
		super(index);
		// TODO Auto-generated constructor stub
	}

	
	//TODO: Add the specific run query implementation in class
	@Override
	public ScoredDocument runquery(String query, int did){
		

		// Get the document vector. For hw1, you don't have to worry about the
		// details of how index works.
		Document d = _index.getDoc(did);

		if (d == null) {
			return new ScoredDocument(did, "", 0);
		}		
		// Score the document with number of views
		return new ScoredDocument(did, d.get_title_string(), d.get_numviews());

	}

}
