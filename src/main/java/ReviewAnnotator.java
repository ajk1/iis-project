import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import type.Review;
import type.Score;
import util.Utils;

public class ReviewAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	private int sizeLimit;

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

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
	}

  
  
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Review Annotator Processing");
//    System.out.println("... sizeLimit: " + sizeLimit);
    // get document text from the CAS
    String docText = aJCas.getDocumentText();
    
    //input document
    //TODO: inputdoc 
//    InputDocument input = new InputDocument(aJCas);
//    input.addToIndexes();
//    input.setComponentId("Collection Reader");
    List<Review> buffer = new LinkedList<Review>();

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
      
      //TODO: Score as attributes
      annotation.setGoldLabel(Integer.parseInt(matcher.group(7)));
      annotation.setSummary(matcher.group(8));
      annotation.setReviewTime(matcher.group(10));
      // Add empty measurement
      annotation.addToIndexes();
      pos = matcher.end();
      buffer.add(annotation);

    }
    System.out.printf("... Parsed %d reviews %n", buffer.size());

    //TODO: no need to link reviews to input doc
//    input.setReviews(Utils.fromCollectionToFSList(aJCas, buffer));

    
  }

}
