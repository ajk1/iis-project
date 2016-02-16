package rank;

import java.util.List;

import type.Passage;
import type.Question;

public interface IRanker {

  /**
   * Sorts the given list of passages associated with the given question, and returns a ranked list
   * of passages.
   * 
   * @param question
   * @param passages
   */
  public List<Passage> rank(Question question, List<Passage> passages);

  /**
   * Returns a score of the given passage associated with the given question.
   * 
   * @param question
   * @param passage
   * @return a score of the passage
   */
  public Double score(Question question, Passage passage);

}
