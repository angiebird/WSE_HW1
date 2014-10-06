package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorAvgPrecision extends Evaluator{
    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){
        Iterator<ScoredDocument> it = query_rank.iterator();
        double RR = 0.0;
        double AP = 0.0;
        int i = 0;
        while(it.hasNext()){
            i++;
            ScoredDocument doc = it.next();
            if(query_relevance.containsKey(doc._did)){
                double rel = query_relevance.get(doc._did);
                if (rel >= 5.0){
                    RR += 1;
                    AP += RR/i;
                }
            }
        }
        if(RR > 0)
            AP = AP/RR;
        return AP;
    }
}
