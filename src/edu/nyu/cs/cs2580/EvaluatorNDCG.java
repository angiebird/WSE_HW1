package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorNDCG extends Evaluator{
    privat int K;
    public EvaluatorNDCG(int K) {
        super();
        this.K = K;
    }

    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){

        double DCG = 0;
        for(int i = 1; i <= K; i++){
            ScoredDocument doc = query_rank.get(i);
            if(query_relevance.containsKey(doc._did)){
                double rel = query_relevance.get(doc._did);
                DCG += rel/(Math.log(i+1)/Math.log(2));
            }
        }
        return DCG;
    }
}
