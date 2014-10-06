package edu.nyu.cs.cs2580;

import java.util.Vector;
import java.util.Scanner;
import java.util.Collections;
public abstract class Ranker{

	// The indexer via which the document are retrieved
	protected Index _index;



	/**
	 *  
	 * Constructor : the construction of the Ranker 
	 * @param index_source
	 */
	protected Ranker(Index indexer){
		_index = indexer;
	}

	/**
	 * Process one query
	 * @param query the query passed by user
	 * @return Scored documents in decreasing order
	 */
	public Vector<ScoredDocument> runQuery(String query) {
		Vector < ScoredDocument > retrieval_results = new Vector < ScoredDocument > ();
		for (int i = 0; i < _index.numDocs(); ++i){
			retrieval_results.add(runquery(query, i));
		}
        Collections.sort(retrieval_results);
		return retrieval_results;
	}

	public abstract  ScoredDocument runquery(String query, int did);

    // A normalize function map [0 inf] to [0 1]
    // where normalize(inf) = 1 and normalize(0) = 0
    public double normalize(double in){
        double out = 1 - Math.pow(Math.E, -in);
        return out;
    }

	/**
	 * A Factory class that will return the Ranker object according to the parameter
	 * @author bdawada
	 *
	 */
	public static class Factory{
		public static Ranker getRankerByParameter(RankerType rankerType, Index indexer) {
			switch(rankerType){
			case COSINE:
				return new RankerCosine(indexer);
			case QL:
				return new RankerQL(indexer);
			case PHRASE:
				return new RankerPhrase(indexer);
			case LINEAR:
				return new RankerLinear(indexer);
			case NUMVIEWS:
				return new RankerNumviews(indexer);
			case NONE:
				return new RankerSimple(indexer);
			default:
				// Do nothing.
			}
			return null;
		}
	}
}
