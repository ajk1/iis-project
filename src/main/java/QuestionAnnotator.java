import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import type.Measurement;
import type.Question;

public class QuestionAnnotator extends JCasAnnotator_ImplBase {

  private Pattern mQuestionPattern = Pattern.compile("(\\d{4}) QUESTION (.*)");

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Question Annotator Processing");
    // get document text from the CAS
    String docText = aJCas.getDocumentText();

    // search for all the questions in the text
    Matcher matcher = mQuestionPattern.matcher(docText);
    int pos = 0;
    while (matcher.find(pos)) {
      // found one - create annotation
      Question annotation = new Question(aJCas);
      annotation.setBegin(matcher.start());
      annotation.setEnd(matcher.end());
      annotation.setId(matcher.group(1));
      annotation.setSentence(matcher.group(2));
      // Add empty measurement
      annotation.setMeasurement(new Measurement(aJCas));
      annotation.addToIndexes();
      pos = matcher.end();
      // System.out.printf("Added Q: %s - %s\n", matcher.group(1), matcher.group(2));
    }
  }

}
