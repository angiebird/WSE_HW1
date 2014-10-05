package edu.nyu.cs.cs2580;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluatorPrecisionRecall extends Evaluator{
	public EvaluatorPrecisionRecall() {
		super();
	}
	@Override
	public double evaluate(String query,
			HashMap<Integer, Double> query_relevance,
			ArrayList<ScoredDocument> query_rank) {
		return 0;
	}
	public ArrayList<Double> evaluate2(String query,
			HashMap<Integer, Double> query_relevance,
			ArrayList<ScoredDocument> query_rank) {
        double lv = 0.1;
        double RR = 0.0;
        double R = 0.0;
        double precision = 0.0;
        ArrayList<Double> precisionLs = new ArrayList<Double>();
        precisionLs.add(0.0); // precision at recall 0.0 is 0.0

        // Calculate the number of all revelance documents;
        for(int i = 0; i < query_rank.size(); i++){
            ScoredDocument doc = query_rank.get(i);
            if(query_relevance.containsKey(doc._did)){
                double rev = query_relevance.get(doc._did);
                if(rev >= 5.0){
                    R++;
                }
            }
        }

        for(int i = 0; i < query_rank.size(); i++){
            ScoredDocument doc = query_rank.get(i);
            if(query_relevance.containsKey(doc._did)){
                double rev = query_relevance.get(doc._did);
                if(rev >= 5.0){
                    RR++;
                    precision = RR/R;
                    if(precision >= lv){
                        precisionLs.add(precision);
                        lv = lv + 0.1;
                    }
                }
            }
        }
        return precisionLs;
    }

}
