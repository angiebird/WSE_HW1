WSE_HW1
=======
cd ./src

* Compile code
  javac ./edu/nyu/cs/cs2580/*.java
  
* Run server
  java java edu.nyu.cs.cs2580.SearchEngine 25811../data/corpus.tsv
  
* Test 
  python test.py 11

* Click Log
  Use browser to do search and click the search results 
  http://localhost:25811/search?query=data%20mining&ranker=cosine&format=html
  
  Then check log with
  http://localhost:25811/log
  
* Ranker
  cosine: RankerCosine.java
  QL: RankerQL.java
  phrase: RankerPhrase.java
  numviews: RankerNumviews.java
  linear model: RankerLinear.java
  
* Evaluator
  precision: EvaluatorPrecision.java
  recall: EvaluatorRecall.java
  F0.50: EvaluatorFMeasure.java
  Precision at recall points: EvaluatorPrecisionRecall.java
  NDCG: EvaluatorNDCG.java
  Reciprocal: EvaluatorReciprocalRank.java
