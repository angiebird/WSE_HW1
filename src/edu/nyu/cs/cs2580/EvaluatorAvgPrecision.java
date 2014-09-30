package edu.nyu.cs.cs2580;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class EvaluatorAvgPrecision extends Evaluator{
    @Override
    public double evaluate( String query,
        HashMap < Integer , Double > query_revelance,
        ArrayList<ScoredDocument > query_rank){
        /*
         Implement your evaluator here
         */

        /* Unmark this to see the input
        Iterator<ScoredDocument> it = query_rank.iterator();
        while(it.hasNext()){
            ScoredDocument doc = it.next();
            System.out.println(query + "\t" + doc._did + "\t" + doc._title + "\t" + doc._score);
        }
        for (Map.Entry< Integer , Double > entry : query_revelance.entrySet()){
            int did = entry.getKey();
            double rev = entry.getValue();
            System.out.println(did + "\t" + rev);
        }
        */
        return 0;
    }
}
