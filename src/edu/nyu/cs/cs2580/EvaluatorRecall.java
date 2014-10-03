package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorRecall extends Evaluator{
    private int K;
    public EvaluatorRecall(int K) {
        super();
        this.K = K;
    }
    
    // TODO: where is 
    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){
    	
    	// Make sure this is iterating over the correct list
    	Iterator<ScoredDocument> it = query_rank.iterator();
        double recall = 0.0;
        double RR = 0.0;
        double R = 0.0;
       
        for(int i = 0; i <= K; i++){
            ScoredDocument doc = query_rank.get(i);
            if(query_relevance.containsKey(doc._did)){
                double rel = query_relevance.get(doc._did);
                if (rel >= 5.0 && i < k){
                    RR++;
                }
                if(rel >= 5.0) R++;
            }
        }
        recall = RR / R;
        return recall;
    }
}
