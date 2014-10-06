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
        double RR = 0.0;
        double R = 0.0;
        int resultsNum = 11;

        ArrayList<Double> lv = new ArrayList<Double>();
        ArrayList<Double> precisionLs= new ArrayList<Double>();
        for(int i = 0; i < resultsNum; i++){
            lv.add(i*0.1);
            precisionLs.add(0.0);
        }


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

        if(R > 0){
            int lvi = 0;
            double prec= 0.0;
            double prevPrec= 0.0;
            double recall = 0.0;
            double prevRecall = 0.0;
            for(int i = 0; i < query_rank.size(); i++){
                ScoredDocument doc = query_rank.get(i);
                if(query_relevance.containsKey(doc._did)){
                    double rev = query_relevance.get(doc._did);
                    if(rev >= 5.0){
                        RR++;

                        prevPrec= prec;
                        prec = RR/(i+1);

                        prevRecall = recall;
                        recall = RR/R;
                        //System.out.println("R: " + RR/R + " P: " + RR/(i+1));

                        double level = lv.get(lvi);
                        while(level >= prevRecall && level <= recall && lvi < resultsNum){
                            // interleaving
                            double precision = (prevPrec*(recall - level) + prec*(level - prevRecall))/(recall - prevRecall);
                            precisionLs.set(lvi, precision);
                            //System.out.println(level + " " + precision);  
                            lvi++;
                            if(lvi < resultsNum)
                                level = lv.get(lvi);
                        }
                    }
                }
            }
        }
        return precisionLs;
    }

}
