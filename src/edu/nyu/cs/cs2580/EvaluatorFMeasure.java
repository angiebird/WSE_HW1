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
		double alpha = 0.50;
		double P = new EvaluatorPrecision(K).evaluate(query, query_relevance, query_rank);
		double R = new EvaluatorRecall(K).evaluate(query, query_relevance, query_rank);
		double F_meausre = Math.pow((alpha * (1/P) + (1-alpha)*(1/R)),-1);
		return F_meausre;
	}

}
