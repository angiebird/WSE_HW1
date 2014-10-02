package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorRecall extends Evaluator{
    privat int K;
    public EvaluatorNDCG(int K) {
        super();
        this.K = K;
    }

    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){

        double recall = 0.0;
        double RR = 0.0;
        double R = 0.0;
        int i = 0;
        while(it.hasNext()){
            i++;
            ScoredDocument doc = it.next();
            if(query_revelance.containsKey(doc._did) && i < K)
                RR ++;
            
            if(query_revelance.containsKey(doc._did))
                R ++;
        }
        
        recall = RR / R;
        return recall;
    }
}
