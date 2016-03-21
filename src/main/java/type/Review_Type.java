
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
 * Updated by JCasGen Mon Mar 21 16:35:27 EDT 2016
 * @generated */
public class Review_Type extends ComponentAnnotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Review_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Review_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Review(addr, Review_Type.this);
  			   Review_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Review(addr, Review_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Review.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Review");
 
  /** @generated */
  final Feature casFeat_reviewerId;
  /** @generated */
  final int     casFeatCode_reviewerId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getReviewerId(int addr) {
        if (featOkTst && casFeat_reviewerId == null)
      jcas.throwFeatMissing("reviewerId", "type.Review");
    return ll_cas.ll_getStringValue(addr, casFeatCode_reviewerId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setReviewerId(int addr, String v) {
        if (featOkTst && casFeat_reviewerId == null)
      jcas.throwFeatMissing("reviewerId", "type.Review");
    ll_cas.ll_setStringValue(addr, casFeatCode_reviewerId, v);}
    
  
 
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
      jcas.throwFeatMissing("rawText", "type.Review");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rawText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRawText(int addr, String v) {
        if (featOkTst && casFeat_rawText == null)
      jcas.throwFeatMissing("rawText", "type.Review");
    ll_cas.ll_setStringValue(addr, casFeatCode_rawText, v);}
    
  
 
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
      jcas.throwFeatMissing("tokens", "type.Review");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tokens);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTokens(int addr, int v) {
        if (featOkTst && casFeat_tokens == null)
      jcas.throwFeatMissing("tokens", "type.Review");
    ll_cas.ll_setRefValue(addr, casFeatCode_tokens, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentences;
  /** @generated */
  final int     casFeatCode_sentences;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSentences(int addr) {
        if (featOkTst && casFeat_sentences == null)
      jcas.throwFeatMissing("sentences", "type.Review");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sentences);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentences(int addr, int v) {
        if (featOkTst && casFeat_sentences == null)
      jcas.throwFeatMissing("sentences", "type.Review");
    ll_cas.ll_setRefValue(addr, casFeatCode_sentences, v);}
    
  
 
  /** @generated */
  final Feature casFeat_score;
  /** @generated */
  final int     casFeatCode_score;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getScore(int addr) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "type.Review");
    return ll_cas.ll_getRefValue(addr, casFeatCode_score);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setScore(int addr, int v) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "type.Review");
    ll_cas.ll_setRefValue(addr, casFeatCode_score, v);}
    
  
 
  /** @generated */
  final Feature casFeat_reviewTime;
  /** @generated */
  final int     casFeatCode_reviewTime;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getReviewTime(int addr) {
        if (featOkTst && casFeat_reviewTime == null)
      jcas.throwFeatMissing("reviewTime", "type.Review");
    return ll_cas.ll_getStringValue(addr, casFeatCode_reviewTime);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setReviewTime(int addr, String v) {
        if (featOkTst && casFeat_reviewTime == null)
      jcas.throwFeatMissing("reviewTime", "type.Review");
    ll_cas.ll_setStringValue(addr, casFeatCode_reviewTime, v);}
    
  
 
  /** @generated */
  final Feature casFeat_summary;
  /** @generated */
  final int     casFeatCode_summary;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSummary(int addr) {
        if (featOkTst && casFeat_summary == null)
      jcas.throwFeatMissing("summary", "type.Review");
    return ll_cas.ll_getStringValue(addr, casFeatCode_summary);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSummary(int addr, String v) {
        if (featOkTst && casFeat_summary == null)
      jcas.throwFeatMissing("summary", "type.Review");
    ll_cas.ll_setStringValue(addr, casFeatCode_summary, v);}
    
  
 
  /** @generated */
  final Feature casFeat_productId;
  /** @generated */
  final int     casFeatCode_productId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getProductId(int addr) {
        if (featOkTst && casFeat_productId == null)
      jcas.throwFeatMissing("productId", "type.Review");
    return ll_cas.ll_getStringValue(addr, casFeatCode_productId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setProductId(int addr, String v) {
        if (featOkTst && casFeat_productId == null)
      jcas.throwFeatMissing("productId", "type.Review");
    ll_cas.ll_setStringValue(addr, casFeatCode_productId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_helpfulness;
  /** @generated */
  final int     casFeatCode_helpfulness;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getHelpfulness(int addr) {
        if (featOkTst && casFeat_helpfulness == null)
      jcas.throwFeatMissing("helpfulness", "type.Review");
    return ll_cas.ll_getIntValue(addr, casFeatCode_helpfulness);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHelpfulness(int addr, int v) {
        if (featOkTst && casFeat_helpfulness == null)
      jcas.throwFeatMissing("helpfulness", "type.Review");
    ll_cas.ll_setIntValue(addr, casFeatCode_helpfulness, v);}
    
  
 
  /** @generated */
  final Feature casFeat_helpfulnessTotal;
  /** @generated */
  final int     casFeatCode_helpfulnessTotal;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getHelpfulnessTotal(int addr) {
        if (featOkTst && casFeat_helpfulnessTotal == null)
      jcas.throwFeatMissing("helpfulnessTotal", "type.Review");
    return ll_cas.ll_getIntValue(addr, casFeatCode_helpfulnessTotal);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHelpfulnessTotal(int addr, int v) {
        if (featOkTst && casFeat_helpfulnessTotal == null)
      jcas.throwFeatMissing("helpfulnessTotal", "type.Review");
    ll_cas.ll_setIntValue(addr, casFeatCode_helpfulnessTotal, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Review_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_reviewerId = jcas.getRequiredFeatureDE(casType, "reviewerId", "uima.cas.String", featOkTst);
    casFeatCode_reviewerId  = (null == casFeat_reviewerId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reviewerId).getCode();

 
    casFeat_rawText = jcas.getRequiredFeatureDE(casType, "rawText", "uima.cas.String", featOkTst);
    casFeatCode_rawText  = (null == casFeat_rawText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rawText).getCode();

 
    casFeat_tokens = jcas.getRequiredFeatureDE(casType, "tokens", "uima.cas.FSList", featOkTst);
    casFeatCode_tokens  = (null == casFeat_tokens) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokens).getCode();

 
    casFeat_sentences = jcas.getRequiredFeatureDE(casType, "sentences", "uima.cas.FSList", featOkTst);
    casFeatCode_sentences  = (null == casFeat_sentences) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentences).getCode();

 
    casFeat_score = jcas.getRequiredFeatureDE(casType, "score", "type.Score", featOkTst);
    casFeatCode_score  = (null == casFeat_score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_score).getCode();

 
    casFeat_reviewTime = jcas.getRequiredFeatureDE(casType, "reviewTime", "uima.cas.String", featOkTst);
    casFeatCode_reviewTime  = (null == casFeat_reviewTime) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reviewTime).getCode();

 
    casFeat_summary = jcas.getRequiredFeatureDE(casType, "summary", "uima.cas.String", featOkTst);
    casFeatCode_summary  = (null == casFeat_summary) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_summary).getCode();

 
    casFeat_productId = jcas.getRequiredFeatureDE(casType, "productId", "uima.cas.String", featOkTst);
    casFeatCode_productId  = (null == casFeat_productId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_productId).getCode();

 
    casFeat_helpfulness = jcas.getRequiredFeatureDE(casType, "helpfulness", "uima.cas.Integer", featOkTst);
    casFeatCode_helpfulness  = (null == casFeat_helpfulness) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_helpfulness).getCode();

 
    casFeat_helpfulnessTotal = jcas.getRequiredFeatureDE(casType, "helpfulnessTotal", "uima.cas.Integer", featOkTst);
    casFeatCode_helpfulnessTotal  = (null == casFeat_helpfulnessTotal) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_helpfulnessTotal).getCode();

  }
}



    