package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorPrecision extends Evaluator{
    private int K;
    public EvaluatorPrecision(int K) {
        super();
        this.K = K;
    }

    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){
    	
        double precision = 0.0;
        double RR = 0.0;
        for(int i = 0; i <= K; i++){
            ScoredDocument doc = query_rank.get(i);
            if(query_relevance.containsKey(doc._did)){
                RR ++;
            }
        }
        
        if (K != 0)
            precision = RR / K;
        else
            precision = 1.0;
        return precision;
    }
}
