
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
 * Updated by JCasGen Wed Feb 24 22:45:51 EST 2016
 * @generated */
public class Token_Type extends ComponentAnnotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Token_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Token_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Token(addr, Token_Type.this);
  			   Token_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Token(addr, Token_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Token.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Token");
 
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
      jcas.throwFeatMissing("rawText", "type.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rawText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRawText(int addr, String v) {
        if (featOkTst && casFeat_rawText == null)
      jcas.throwFeatMissing("rawText", "type.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_rawText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentimentScore;
  /** @generated */
  final int     casFeatCode_sentimentScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getSentimentScore(int addr) {
        if (featOkTst && casFeat_sentimentScore == null)
      jcas.throwFeatMissing("sentimentScore", "type.Token");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_sentimentScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentimentScore(int addr, double v) {
        if (featOkTst && casFeat_sentimentScore == null)
      jcas.throwFeatMissing("sentimentScore", "type.Token");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_sentimentScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_negationScore;
  /** @generated */
  final int     casFeatCode_negationScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getNegationScore(int addr) {
        if (featOkTst && casFeat_negationScore == null)
      jcas.throwFeatMissing("negationScore", "type.Token");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_negationScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNegationScore(int addr, double v) {
        if (featOkTst && casFeat_negationScore == null)
      jcas.throwFeatMissing("negationScore", "type.Token");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_negationScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_amplificationScore;
  /** @generated */
  final int     casFeatCode_amplificationScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getAmplificationScore(int addr) {
        if (featOkTst && casFeat_amplificationScore == null)
      jcas.throwFeatMissing("amplificationScore", "type.Token");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_amplificationScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAmplificationScore(int addr, double v) {
        if (featOkTst && casFeat_amplificationScore == null)
      jcas.throwFeatMissing("amplificationScore", "type.Token");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_amplificationScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_reviewId;
  /** @generated */
  final int     casFeatCode_reviewId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getReviewId(int addr) {
        if (featOkTst && casFeat_reviewId == null)
      jcas.throwFeatMissing("reviewId", "type.Token");
    return ll_cas.ll_getStringValue(addr, casFeatCode_reviewId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setReviewId(int addr, String v) {
        if (featOkTst && casFeat_reviewId == null)
      jcas.throwFeatMissing("reviewId", "type.Token");
    ll_cas.ll_setStringValue(addr, casFeatCode_reviewId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Token_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_rawText = jcas.getRequiredFeatureDE(casType, "rawText", "uima.cas.String", featOkTst);
    casFeatCode_rawText  = (null == casFeat_rawText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rawText).getCode();

 
    casFeat_sentimentScore = jcas.getRequiredFeatureDE(casType, "sentimentScore", "uima.cas.Double", featOkTst);
    casFeatCode_sentimentScore  = (null == casFeat_sentimentScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentimentScore).getCode();

 
    casFeat_negationScore = jcas.getRequiredFeatureDE(casType, "negationScore", "uima.cas.Double", featOkTst);
    casFeatCode_negationScore  = (null == casFeat_negationScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_negationScore).getCode();

 
    casFeat_amplificationScore = jcas.getRequiredFeatureDE(casType, "amplificationScore", "uima.cas.Double", featOkTst);
    casFeatCode_amplificationScore  = (null == casFeat_amplificationScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_amplificationScore).getCode();

 
    casFeat_reviewId = jcas.getRequiredFeatureDE(casType, "reviewId", "uima.cas.String", featOkTst);
    casFeatCode_reviewId  = (null == casFeat_reviewId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reviewId).getCode();

  }
}



    