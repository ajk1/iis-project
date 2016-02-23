import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import rank.CompositeRanker;
import rank.IRanker;
import rank.NgramRanker;
import rank.OtherRanker;
import type.Measurement;
import type.Passage;
import type.Question;

/**
 * This CAS Consumer generates the report file with the method metrics
 */
public class ReviewScoreWriter extends CasConsumer_ImplBase {
  final String PARAM_OUTPUTDIR = "OutputDir";

  final String OUTPUT_FILENAME = "ErrorAnalysis.csv";

  File mOutputDir;

  IRanker ngramRanker, otherRanker;

  CompositeRanker compositeRanker;

  @Override
  public void initialize() throws ResourceInitializationException {
//    String mOutputDirStr = (String) getConfigParameterValue(PARAM_OUTPUTDIR);
//    if (mOutputDirStr != null) {
//      mOutputDir = new File(mOutputDirStr);
//      if (!mOutputDir.exists()) {
//        mOutputDir.mkdirs();
//      }
//    }
//
//    // Initialize rankers
//    compositeRanker = new CompositeRanker();
//    ngramRanker = new NgramRanker();
//    otherRanker = new OtherRanker();
//    compositeRanker.addRanker(ngramRanker);
//    compositeRanker.addRanker(otherRanker);
  }

  @Override
  public void processCas(CAS arg0) throws ResourceProcessException {
//    System.out.println(">> Review Score Writer Processing");
//    // Import the CAS as a aJCas
//    JCas aJCas = null;
//    File outputFile = null;
//    PrintWriter writer = null;
//    try {
//      aJCas = arg0.getJCas();
//      try {
//        outputFile = new File(Paths.get(mOutputDir.getAbsolutePath(), OUTPUT_FILENAME).toString());
//        outputFile.getParentFile().mkdirs();
//        writer = new PrintWriter(outputFile);
//      } catch (FileNotFoundException e) {
//        System.out.printf("Output file could not be written: %s\n",
//                Paths.get(mOutputDir.getAbsolutePath(), OUTPUT_FILENAME).toString());
//        return;
//      }
//
//      writer.println("question_id,tp,fn,fp,precision,recall,f1");
//      // Retrieve all the questions for printout
//      List<Question> allQuestions = UimaUtils.getAnnotations(aJCas, Question.class);
//      List<Question> subsetOfQuestions = RandomUtils.getRandomSubset(allQuestions, 10);
//
//      // TODO: Here one needs to sort the questions in ascending order of their question ID
//
//      for (Question question : subsetOfQuestions) {
//        List<Passage> passages = UimaUtils.convertFSListToList(question.getPassages(), Passage.class);
//
//        // TODO: Use the following three lists of ranked passages for your error analysis
//        List<Passage> ngramRankedPassages = ngramRanker.rank(question, passages);
//        List<Passage> otherRankedPassages = otherRanker.rank(question, passages);
//        List<Passage> compositeRankedPassages = compositeRanker.rank(question, passages);
//
//        Measurement m = question.getMeasurement();
//
//        // TODO: Calculate actual precision, recall and F1
//        double precision = 0.0;
//        double recall = 0.0;
//        double f1 = 0.0;
//
//        writer.printf("%s,%d,%d,%d,%.3f,%.3f,%.3f\n", question.getId(), m.getTp(), m.getFn(),
//                m.getFp(), precision, recall, f1);
//      }
//    } catch (CASException e) {
//      try {
//        throw new CollectionException(e);
//      } catch (CollectionException e1) {
//        e1.printStackTrace();
//      }
//    } finally {
//      if (writer != null)
//        writer.close();
//    }
  }
}
