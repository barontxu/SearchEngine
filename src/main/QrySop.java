/** 
 *  Copyright (c) 2015, Carnegie Mellon University.  All Rights Reserved.
 */
import java.io.*;

/**
 *  The root class of all query operators that use a retrieval model
 *  to determine whether a query matches a document and to calculate a
 *  score for the document.  This class has two main purposes.  First, it
 *  allows query operators to easily recognize any nested query
 *  operator that returns a document scores (e.g., #AND (a #OR(b c)).
 *  Second, it is a place to store data structures and methods that are
 *  common to all query operators that calculate document scores.
 */
public abstract class QrySop extends Qry {
	//The weight of this QrySop
	private double weight = Double.NaN;
  /**
   *  Initialize the query operator (and its arguments), including any
   *  internal iterators.  If the query operator is of type QryIop, it
   *  is fully evaluated, and the results are stored in an internal
   *  inverted list that may be accessed via the internal iterator.
   *  @param r A retrieval model that guides initialization
   *  @throws IOException Error accessing the Lucene index.
   */
  public void initialize(RetrievalModel r) throws IOException {
    for (Qry q_i: this.args) {
      q_i.initialize (r);
    }
  }
  
  /**
   *  Get score for the doc that docIteratorHasMatch matched.
   *  @param r The retrieval model that determines how scores are calculated.
   *  @return The document score.
   *  @throws IOException Error accessing the Lucene index
   */
  public abstract double getScore (RetrievalModel r)
    throws IOException;
  
  /**
   *  Support for Indri best match model. Call this method in a all the subclasses of QrySop
   *  when the document does not have a match.
   *  @param r A retrieval model that guides initialization
   *  @param docid The specific doc id associated with the score
   *  @throws IOException Error accessing the Lucene index.
   */
  public abstract double getDefaultScore(RetrievalModel r, int docid) throws IOException;
  
  
  public double getWeight(){
	  return this.weight;
  }
  
  public void setWeight(double weight){
	  this.weight = weight;
  }
}
