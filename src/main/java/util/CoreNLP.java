package util;

import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CoreNLP {
    
    private static final StanfordCoreNLP pipeline;
    private static final Properties props = new Properties();
    static {
    	props.put("annotators","tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//        props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
        pipeline = new StanfordCoreNLP(props);
    }

    public static void annotate(Annotation annotation) {
        pipeline.annotate(annotation);
    }
    
}