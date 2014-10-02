package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorPrecision extends Evaluator{
    privat int K;
    public EvaluatorNDCG(int K) {
        super();
        this.K = K;
    }

    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){

        double precision = 0.0;
        double RR = 0.0;
        while(it.hasNext()){
            ScoredDocument doc = it.next();
            if(query_revelance.containsKey(doc._did)){
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
