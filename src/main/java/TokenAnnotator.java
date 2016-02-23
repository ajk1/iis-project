import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

public class TokenAnnotator extends JCasAnnotator_ImplBase {


  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Token Annotator Processing");
    // get document text from the CAS
    String docText = aJCas.getDocumentText();

    // search for all the questions in the text
  }

}
