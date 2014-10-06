package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorReciprocalRank extends Evaluator{
    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_relevance,
        ArrayList<ScoredDocument > query_rank){
        Iterator<ScoredDocument> it = query_rank.iterator();
        
        int i = 0;
        while(it.hasNext()){
            i++;
            ScoredDocument doc = it.next();
            if(query_revelance.containsKey(doc._did)){
                double rel = query_relevance.get(doc._did);
                if (rel >= 5.0){
                    break;
                }
            }
        }
        return 1/i;
    }
}
