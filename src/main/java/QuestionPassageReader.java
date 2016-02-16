import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

/**
 * This Collection Reader serves as a reader to parse your input. This is just template code, so you
 * need to implement actual code.
 */
public class QuestionPassageReader extends CollectionReader_ImplBase {
  final String PARAM_INPUTDIR = "InputDir";

  int mCurrentIndex;

  String mInputDir;

  ArrayList<File> mFiles;

  /*
   * This method seeks all the files in the input directory determined by param PARAM_INPUTDIR and
   * stores them into an array mFiles
   * 
   * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
   */
  public void initialize() throws ResourceInitializationException {
    mCurrentIndex = 0;
    mFiles = new ArrayList<File>();
    mInputDir = (String) getConfigParameterValue(PARAM_INPUTDIR);
    if (mInputDir != null) {
      File directory = new File(mInputDir);
      mCurrentIndex = 0;

      // get list of files (not subdirectories) in the specified directory
      File[] files = directory.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (files[i] != null && !files[i].isDirectory()) {
          mFiles.add(files[i]);
        }
      }
    }
  }

  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    // Import the CAS as a JCAS
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }

    // open input stream to file
    File file = (File) mFiles.get(mCurrentIndex++);
    BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
    try {
      byte[] contents = new byte[(int) file.length()];
      fis.read(contents);
      String text = new String(contents);
      ;
      // put document in CAS
      jcas.setDocumentText(text);
      System.out.printf("File: %s stored in CAS\n", file.getName());
    } finally {
      if (fis != null)
        fis.close();
    }
  }

  @Override
  public Progress[] getProgress() {
    // Here you set a Progress structure which corresponds to the current status of
    // the collection reader
    return new Progress[] { new ProgressImpl(mCurrentIndex, mFiles.size(), Progress.ENTITIES) };
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return mCurrentIndex < mFiles.size(); // Check if there are any files left to be read
  }

  @Override
  public void close() throws IOException {
  }
}
