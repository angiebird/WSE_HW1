package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorNDCG extends Evaluator{
    private int K;
    public EvaluatorNDCG(int K) {
        super();
        this.K = K;
    }

    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){

        double DCG = 0;
        double MAX_DCG = 0; // The maximum DCG score associates to the query
        int j = 1;
        for(int i = 1; i <= K; i++){
            ScoredDocument doc = query_rank.get(i-1);
            if(query_relevance.containsKey(doc._did)){
                double rel = query_relevance.get(doc._did);
                DCG += rel/(Math.log(i+1)/Math.log(2));
                MAX_DCG += 10/(Math.log(j+1)/Math.log(2));
                j++;
            }
        }
        if(MAX_DCG == 0.0){
            return 0;
        }
        else{
            return DCG/MAX_DCG;
        }
    }
}
