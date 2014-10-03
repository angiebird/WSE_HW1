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
    	
    	// Iteratte only over the query_rank till K
    	Iterator<ScoredDocument> it = query_rank.iterator();
        double precision = 0.0;
        double RR = 0.0;
        while(it.hasNext()){
            ScoredDocument doc = it.next();
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
