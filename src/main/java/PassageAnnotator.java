import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import type.Passage;
import type.Question;

public class PassageAnnotator extends JCasAnnotator_ImplBase {
  private Pattern mPassagePattern = Pattern.compile("(\\d{4}) ([A-Z0-9]+\\.\\d{4}) (-?[12]) (.*)");

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Passage Annotator Processing");
    // get document text from the CAS
    String docText = aJCas.getDocumentText();

    Map<String, Question> questionMap = new HashMap<String, Question>();
    List<Question> questions = UimaUtils.getAnnotations(aJCas, Question.class);
    for (Question question : questions) {
      questionMap.put(question.getId(), question);
    }

    // search for all the questions in the text
    Matcher matcher = mPassagePattern.matcher(docText);
    int pos = 0;
    while (matcher.find(pos)) {
      String questionId = matcher.group(1);
      Question question = questionMap.get(questionId);

      // found one - create passage
      Passage passage = new Passage(aJCas);
      passage.setBegin(matcher.start());
      passage.setEnd(matcher.end());
      passage.setQuestion(question);
      passage.setSourceDocId(matcher.group(2));
      passage.setLabel(!(matcher.group(3).compareTo("-1") == 0));
      passage.setText(matcher.group(4));
      passage.addToIndexes();
      pos = matcher.end();
      // System.out.printf("Added P: %s-%s - %s\n", matcher.group(1), matcher.group(2),
      // matcher.group(4));

      question.setPassages(UimaUtils.appendFSList(aJCas, question.getPassages(), passage, Passage.class));
    }
  }

}
