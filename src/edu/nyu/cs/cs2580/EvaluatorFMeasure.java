package edu.nyu.cs.cs2580;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluatorFMeasure extends Evaluator{
	private int K;
	
	public EvaluatorFMeasure() {
		super();
        this.K = K;
	}
	@Override
	public double evaluate(String query,
			HashMap<Integer, Double> query_relevance,
			ArrayList<ScoredDocument> query_rank) {
		double P = new EvaluatorPrecision(K).evaluate(query, query_relevance, query_rank);
		
		return 0;
	}

}
