package edu.nyu.cs.cs2580;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluatorFMeasure extends Evaluator{
	private int K;
    private double alpha = 0.50;
	
	public EvaluatorFMeasure(double alpha, int K) {
		super();
        this.K = K;
        this.alpha = alpha;
	}
	@Override
	public double evaluate(String query,
			HashMap<Integer, Double> query_relevance,
			ArrayList<ScoredDocument> query_rank) {
		double P = new EvaluatorPrecision(K).evaluate(query, query_relevance, query_rank);
		double R = new EvaluatorRecall(K).evaluate(query, query_relevance, query_rank);
		double F_measure = Math.pow((alpha * (1/P) + (1-alpha)*(1/R)),-1);
		return F_measure;
	}

}
