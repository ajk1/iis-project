package rank;

import java.util.ArrayList;
import java.util.List;

import type.Passage;
import type.Question;

/**
 * This class provides a skeletal implementation of interface IRanker.
 */
public abstract class AbstractRanker implements IRanker {

  /**
   * Sorts the given list of passages associated with the given question, and returns a ranked list
   * of passages. A subclass needs to implement this method.
   * 
   * @param question
   * @param passages
   */
  public List<Passage> rank(Question question, List<Passage> passages) {
    // TODO Complete the implementation of this method.

    // Score all the given passages and sort them in List object 'rankedPassages' below.
    List<Passage> rankedPassages = new ArrayList<Passage>();

    return rankedPassages;
  }

  /**
   * Returns a score of the given passage associated with the given question. A subclass needs to
   * implement this method.  A subclass needs to implement this method.
   * 
   * @param question
   * @param passage
   * @return
   */
  public abstract Double score(Question question, Passage passage);

}
