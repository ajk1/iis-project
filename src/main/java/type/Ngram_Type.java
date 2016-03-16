
/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Wed Mar 16 15:16:14 EDT 2016
 * @generated */
public class Ngram_Type extends ComponentAnnotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Ngram_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Ngram_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Ngram(addr, Ngram_Type.this);
  			   Ngram_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Ngram(addr, Ngram_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Ngram.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Ngram");
 
  /** @generated */
  final Feature casFeat_n;
  /** @generated */
  final int     casFeatCode_n;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getN(int addr) {
        if (featOkTst && casFeat_n == null)
      jcas.throwFeatMissing("n", "type.Ngram");
    return ll_cas.ll_getIntValue(addr, casFeatCode_n);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setN(int addr, int v) {
        if (featOkTst && casFeat_n == null)
      jcas.throwFeatMissing("n", "type.Ngram");
    ll_cas.ll_setIntValue(addr, casFeatCode_n, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tokens;
  /** @generated */
  final int     casFeatCode_tokens;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTokens(int addr) {
        if (featOkTst && casFeat_tokens == null)
      jcas.throwFeatMissing("tokens", "type.Ngram");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tokens);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTokens(int addr, int v) {
        if (featOkTst && casFeat_tokens == null)
      jcas.throwFeatMissing("tokens", "type.Ngram");
    ll_cas.ll_setRefValue(addr, casFeatCode_tokens, v);}
    
  
 
  /** @generated */
  final Feature casFeat_rawText;
  /** @generated */
  final int     casFeatCode_rawText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRawText(int addr) {
        if (featOkTst && casFeat_rawText == null)
      jcas.throwFeatMissing("rawText", "type.Ngram");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rawText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRawText(int addr, String v) {
        if (featOkTst && casFeat_rawText == null)
      jcas.throwFeatMissing("rawText", "type.Ngram");
    ll_cas.ll_setStringValue(addr, casFeatCode_rawText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentimentScore;
  /** @generated */
  final int     casFeatCode_sentimentScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSentimentScore(int addr) {
        if (featOkTst && casFeat_sentimentScore == null)
      jcas.throwFeatMissing("sentimentScore", "type.Ngram");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sentimentScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentimentScore(int addr, int v) {
        if (featOkTst && casFeat_sentimentScore == null)
      jcas.throwFeatMissing("sentimentScore", "type.Ngram");
    ll_cas.ll_setRefValue(addr, casFeatCode_sentimentScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_negationScore;
  /** @generated */
  final int     casFeatCode_negationScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getNegationScore(int addr) {
        if (featOkTst && casFeat_negationScore == null)
      jcas.throwFeatMissing("negationScore", "type.Ngram");
    return ll_cas.ll_getRefValue(addr, casFeatCode_negationScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNegationScore(int addr, int v) {
        if (featOkTst && casFeat_negationScore == null)
      jcas.throwFeatMissing("negationScore", "type.Ngram");
    ll_cas.ll_setRefValue(addr, casFeatCode_negationScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_amplificationScore;
  /** @generated */
  final int     casFeatCode_amplificationScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAmplificationScore(int addr) {
        if (featOkTst && casFeat_amplificationScore == null)
      jcas.throwFeatMissing("amplificationScore", "type.Ngram");
    return ll_cas.ll_getRefValue(addr, casFeatCode_amplificationScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAmplificationScore(int addr, int v) {
        if (featOkTst && casFeat_amplificationScore == null)
      jcas.throwFeatMissing("amplificationScore", "type.Ngram");
    ll_cas.ll_setRefValue(addr, casFeatCode_amplificationScore, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Ngram_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_n = jcas.getRequiredFeatureDE(casType, "n", "uima.cas.Integer", featOkTst);
    casFeatCode_n  = (null == casFeat_n) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_n).getCode();

 
    casFeat_tokens = jcas.getRequiredFeatureDE(casType, "tokens", "uima.cas.FSList", featOkTst);
    casFeatCode_tokens  = (null == casFeat_tokens) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokens).getCode();

 
    casFeat_rawText = jcas.getRequiredFeatureDE(casType, "rawText", "uima.cas.String", featOkTst);
    casFeatCode_rawText  = (null == casFeat_rawText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rawText).getCode();

 
    casFeat_sentimentScore = jcas.getRequiredFeatureDE(casType, "sentimentScore", "uima.cas.FloatList", featOkTst);
    casFeatCode_sentimentScore  = (null == casFeat_sentimentScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentimentScore).getCode();

 
    casFeat_negationScore = jcas.getRequiredFeatureDE(casType, "negationScore", "uima.cas.FloatList", featOkTst);
    casFeatCode_negationScore  = (null == casFeat_negationScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_negationScore).getCode();

 
    casFeat_amplificationScore = jcas.getRequiredFeatureDE(casType, "amplificationScore", "uima.cas.FloatList", featOkTst);
    casFeatCode_amplificationScore  = (null == casFeat_amplificationScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_amplificationScore).getCode();

  }
}



    