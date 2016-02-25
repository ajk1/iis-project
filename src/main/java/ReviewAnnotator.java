import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import type.InputDocument;
import type.Measurement;
import type.Review;
import type.Score;

public class ReviewAnnotator extends JCasAnnotator_ImplBase {

  private Pattern mQuestionPattern = Pattern.compile(
	"\\{\"reviewerID\": \"(.*)\", "
	+ "\"asin\": \"(.*)\", "
	+ "\"reviewerName\": \"(.*)\", "
	+ "\"helpful\": \\[(\\d+), "
					+ "(\\d+)\\], "
	+ "\"reviewText\": \"(.*)\", "
	+ "\"overall\": (\\d).0, "
	+ "\"summary\": \"(.*)\", "
	+ "\"unixReviewTime\": (\\d*), "
	+ "\"reviewTime\": \"(.*)\"\\}");

//  "which (?<answerType>.+) (is|are|have been|should be|can|may) (?<focus>.+)(\\.|\\?|)",

  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Question Annotator Processing");
    // get document text from the CAS
    String docText = aJCas.getDocumentText();
    
    //input document
    InputDocument input = new InputDocument(aJCas);

    // search for all the questions in the text
    Matcher matcher = mQuestionPattern.matcher(docText);
    int pos = 0;
    while (matcher.find(pos)) {
      // found one - create annotation
      Review annotation = new Review(aJCas);
      annotation.setBegin(matcher.start());
      annotation.setEnd(matcher.end());
      annotation.setReviewerId(matcher.group(1));
      annotation.setProductId(matcher.group(2));
      annotation.setHelpfulness(Integer.parseInt(matcher.group(4)));
      annotation.setHelpfulness(Integer.parseInt(matcher.group(5))); 
      annotation.setRawText(matcher.group(6));
      // Add score with gold label
      Score s = new Score(aJCas);
      s.setGoldLabel(Integer.parseInt(matcher.group(7)));
      annotation.setScore(s);
      annotation.setSummary(matcher.group(8));
      annotation.setReviewTime(matcher.group(10));
      // Add empty measurement
      annotation.addToIndexes();
      pos = matcher.end();
      // System.out.printf("Added Q: %s - %s\n", matcher.group(1), matcher.group(2));
    }
  }

}
