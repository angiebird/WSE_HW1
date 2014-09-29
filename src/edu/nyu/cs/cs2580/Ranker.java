package edu.nyu.cs.cs2580;

import java.util.Vector;
import java.util.Scanner;

/*class Ranker {
  private Index _index;

  public Ranker(String index_source){
    _index = new Index(index_source);
  }

  public Vector < ScoredDocument > runquery(String query){
    Vector < ScoredDocument > retrieval_results = new Vector < ScoredDocument > ();
    for (int i = 0; i < _index.numDocs(); ++i){
      retrieval_results.add(runquery(query, i));
    }
    return retrieval_results;
  }

  public ScoredDocument runquery(String query, int did){

    // Build query vector
    Scanner s = new Scanner(query);
    Vector < String > qv = new Vector < String > ();
    while (s.hasNext()){
      String term = s.next();
      qv.add(term);
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
}*/


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
	public abstract Vector<ScoredDocument> runQuery(String query);

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
				// Plug in your QL Ranker
				return new RankerQL(indexer);
			case PHRASE:
				// Plug in your phrase Ranker
				break;
			case LINEAR:
				// Plug in your linear Ranker
				break;
			case NONE:
				return new RankerSimple(indexer);
			default:
				// Do nothing.
			}
			return null;
		}
	}
}
