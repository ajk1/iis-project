import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.metadata.CpeDescription;
import org.apache.uima.resource.ConfigurableResource;
import org.apache.uima.util.XMLInputSource;

public class Main {

  /**
   * This method is the main program and entry point of your system. It runs a Collection Processing
   * Engine (CPE).
   * 
   * @param args
   */
  public static void main(String[] args) throws Exception {
    // ### A guideline for implementing this method ###
    // This code gives you a template for a CPE. Still, you need to configure each individual
    // component.

    // Remember to set your ARGS while running the configuration
    // arg[0] is the INPUT_DIRECTORY where the passages and questions are located
    // arg[1] is the OUTPUT_DIRECTORY where the results will be stored
	String mode = args[0];
    String inputDir = args[1];
    String outputDir = args[2];
    String modelDir = args[3];
    String sizeLimit = "0";
    String readRecords = "false";
    if(args.length > 4) {
        sizeLimit = args[4];    	    	
    } 
    if(args.length > 5) {
    	readRecords = args[5];
    }

    // Instantiate CPE.
    CpeDescription cpeDesc = UIMAFramework.getXMLParser()
            .parseCpeDescription(new XMLInputSource("src/main/resources/descriptors/cpeDescriptor.xml"));
    CollectionProcessingEngine mCPE = UIMAFramework.produceCollectionProcessingEngine(cpeDesc);

    // Configure your collection reader with the given input directory. The code below assumes that
    // the collection reader has a parameter 'InputDir' to specify the input directory.
    ConfigurableResource cr = (ConfigurableResource) mCPE.getCollectionReader();
    cr.setConfigParameterValue("InputDir", "src/main/resources/" + inputDir);
    cr.reconfigure();

    // Configure your aggregate analysis engine here, if you want to.

    // Configure your CAS consumer with the given output directory. The code below assumes that the
    // CAS consumer has a parameter 'OutputDir' to specify the output directory. The code below
    // assumes that the CAS Consumer can be accessed at index 1 from the array of CasProcessors[]
    // mCPE.getCasProcessors().
    ConfigurableResource cc = (ConfigurableResource) mCPE.getCasProcessors()[1]; // <-- Careful with index
    cc.setConfigParameterValue("OutputDir", "src/main/resources/" + outputDir);
    cc.setConfigParameterValue("Mode", mode);
    cc.reconfigure();
    

    AnalysisEngine cp0 = (AnalysisEngine) mCPE.getCasProcessors()[0];
    cp0.setConfigParameterValue("Mode", mode);
    cp0.setConfigParameterValue("SizeLimit", sizeLimit);
    cp0.setConfigParameterValue("ModelDir", "src/main/resources/" + modelDir);
    cp0.setConfigParameterValue("ReadRecords", readRecords);
    cp0.reconfigure();
        

    // Create and register a Status Callback Listener.
    mCPE.addStatusCallbackListener(new StatusCallbackListenerImpl());

    // Run the CPE.
    System.out.println("[INFO] --- Starting CPE with mode: " + mode + " --- ");
    mCPE.process();
  }
  
}