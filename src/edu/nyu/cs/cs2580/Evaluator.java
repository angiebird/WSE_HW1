package edu.nyu.cs.cs2580;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class Evaluator {

  public static void main(String[] args) throws IOException {
    HashMap < String , HashMap < Integer , Double > > relevance_judgments =
      new HashMap < String , HashMap < Integer , Double > >();
    if (args.length < 1){
      System.out.println("need to provide relevance_judgments");
      return;
    }
    String p = args[0];
    // first read the relevance judgments into the HashMap
    readRelevanceJudgments(p,relevance_judgments);
    // now evaluate the results from stdin
    evaluateStdin(relevance_judgments);
  }

  public static void readRelevanceJudgments(
    String p,HashMap < String , HashMap < Integer , Double > > relevance_judgments){
    try {
      BufferedReader reader = new BufferedReader(new FileReader(p));
      try {
        String line = null;
        while ((line = reader.readLine()) != null){
          // parse the query,did,relevance line
          Scanner s = new Scanner(line).useDelimiter("\t");
          String query = s.next();
          int did = Integer.parseInt(s.next());
          String grade = s.next();
          double rel = 0.0;
          // convert to binary relevance
          if (grade.equals("Perfect")){
            rel = 10.0;
          }
          else if(grade.equals("Excellent")){
            rel = 7.0;
          }
          else if(grade.equals("Good")){
            rel = 5.0;
          }
          else if(grade.equals("Fair")){
            rel = 1.0;
          }
          else if(grade.equals("Bad")){
            rel = 0.0;
          }
          if (relevance_judgments.containsKey(query) == false){
            HashMap < Integer , Double > qr = new HashMap < Integer , Double >();
            relevance_judgments.put(query,qr);
          }
          HashMap < Integer , Double > qr = relevance_judgments.get(query);
          qr.put(did,rel);
        }
      } finally {
        reader.close();
      }
    } catch (IOException ioe){
      System.err.println("Oops " + ioe.getMessage());
    }
  }

  public abstract double evaluate( String query,
    HashMap < Integer , Double > query_revelance,
    ArrayList<ScoredDocument > query_rank);

  public static void evaluateStdin(
    HashMap < String , HashMap < Integer , Double > > relevance_judgments){
    HashMap< String, ArrayList <ScoredDocument> > rank_result = readStdin(relevance_judgments);

    String query = new String();

    for (Map.Entry<String, ArrayList <ScoredDocument>> entry : rank_result.entrySet()) {
        query = entry.getKey();
        // break here since the stdin only have one query
        break;
    }
    
    HashMap < Integer , Double > query_revelance = relevance_judgments.get(query);
    ArrayList< ScoredDocument > query_rank = rank_result.get(query);

    // Add your evaluator here
    ArrayList< Double > score = new ArrayList<Double>();
    
    Evaluator eval = new EvaluatorPrecision(1);
    score.add(eval.evaluate(query, query_revelance, query_rank));
    
    eval = new EvaluatorPrecision(5);
    score.add(eval.evaluate(query, query_revelance, query_rank));
    
    eval = new EvaluatorPrecision(10);
    score.add(eval.evaluate(query, query_revelance, query_rank));

    eval = new EvaluatorRecall(1);
    score.add(eval.evaluate(query, query_revelance, query_rank));

    eval = new EvaluatorRecall(5);
    score.add(eval.evaluate(query, query_revelance, query_rank));

    eval = new EvaluatorRecall(10);
    score.add(eval.evaluate(query, query_revelance, query_rank));
    
     eval = new EvaluatorAvgPrecision();
    score.add(eval.evaluate(query, query_revelance, query_rank));

    eval = new EvaluatorNDCG(1);
    score.add(eval.evaluate(query, query_revelance, query_rank));

    eval = new EvaluatorNDCG(5);
    score.add(eval.evaluate(query, query_revelance, query_rank));

    eval = new EvaluatorNDCG(10);
    score.add(eval.evaluate(query, query_revelance, query_rank));
    
    
    
    
    /*
    For example:
    
    eval = new EvaluatorPrecision(1);
    score.add(eval.evaluate(query, query_revelance, query_rank));
    
    eval = new EvaluatorPrecision(5);
    score.add(eval.evaluate(query, query_revelance, query_rank));
    
    eval = new EvaluatorPrecision(10);
    score.add(eval.evaluate(query, query_revelance, query_rank));

    eval = new EvaluatorRecall(1);
    score.add(eval.evaluate(query, query_revelance, query_rank));

    ...
    */
    for(int i = 0; i < score.size(); i++){
        if(i!=0)
            System.out.print(" " + score.get(i));
        else
            System.out.print(score.get(i));
    }
    System.out.print("\n");
  }

  // parsing the stdin to a HashMap
  public static HashMap< String, ArrayList< ScoredDocument > > readStdin(
    HashMap < String , HashMap < Integer , Double > > relevance_judgments){
    HashMap< String, ArrayList< ScoredDocument > > rank_result = new HashMap< String, ArrayList < ScoredDocument > >();
    // only consider one query per call    
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      
      String line = null;
      while ((line = reader.readLine()) != null){
        Scanner s = new Scanner(line).useDelimiter("\t");
        String query = s.next();
        int did = Integer.parseInt(s.next());
      	String title = s.next();
      	double rel = Double.parseDouble(s.next());
      	if (relevance_judgments.containsKey(query) == false){
      	  throw new IOException("query not found");
      	}
        if(!rank_result.containsKey(query)){
            rank_result.put(query, new ArrayList<ScoredDocument>());
        }
        rank_result.get(query).add(new ScoredDocument(did, title, rel));
      }
    } catch (Exception e){
      System.err.println("Error:" + e.getMessage());
    }
    return rank_result;
  }
}

