

/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.IntegerArray;


/** 
 * Updated by JCasGen Thu Feb 25 22:28:29 EST 2016
 * XML source: /Users/Terry/Desktop/CMU/16-spring/11792/project/OpinionMining/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Review extends ComponentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Review.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Review() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Review(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Review(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Review(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: reviewerId

  /** getter for reviewerId - gets 
   * @generated
   * @return value of the feature 
   */
  public String getReviewerId() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_reviewerId == null)
      jcasType.jcas.throwFeatMissing("reviewerId", "type.Review");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Review_Type)jcasType).casFeatCode_reviewerId);}
    
  /** setter for reviewerId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setReviewerId(String v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_reviewerId == null)
      jcasType.jcas.throwFeatMissing("reviewerId", "type.Review");
    jcasType.ll_cas.ll_setStringValue(addr, ((Review_Type)jcasType).casFeatCode_reviewerId, v);}    
   
    
  //*--------------*
  //* Feature: rawText

  /** getter for rawText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRawText() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Review");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Review_Type)jcasType).casFeatCode_rawText);}
    
  /** setter for rawText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRawText(String v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Review");
    jcasType.ll_cas.ll_setStringValue(addr, ((Review_Type)jcasType).casFeatCode_rawText, v);}    
   
    
  //*--------------*
  //* Feature: tokens

  /** getter for tokens - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getTokens() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Review");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Review_Type)jcasType).casFeatCode_tokens)));}
    
  /** setter for tokens - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTokens(FSList v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Review");
    jcasType.ll_cas.ll_setRefValue(addr, ((Review_Type)jcasType).casFeatCode_tokens, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: sentences

  /** getter for sentences - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getSentences() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_sentences == null)
      jcasType.jcas.throwFeatMissing("sentences", "type.Review");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Review_Type)jcasType).casFeatCode_sentences)));}
    
  /** setter for sentences - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentences(FSList v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_sentences == null)
      jcasType.jcas.throwFeatMissing("sentences", "type.Review");
    jcasType.ll_cas.ll_setRefValue(addr, ((Review_Type)jcasType).casFeatCode_sentences, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: score

  /** getter for score - gets 
   * @generated
   * @return value of the feature 
   */
  public Score getScore() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "type.Review");
    return (Score)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Review_Type)jcasType).casFeatCode_score)));}
    
  /** setter for score - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setScore(Score v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "type.Review");
    jcasType.ll_cas.ll_setRefValue(addr, ((Review_Type)jcasType).casFeatCode_score, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: bigrams

  /** getter for bigrams - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getBigrams() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_bigrams == null)
      jcasType.jcas.throwFeatMissing("bigrams", "type.Review");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Review_Type)jcasType).casFeatCode_bigrams)));}
    
  /** setter for bigrams - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setBigrams(FSList v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_bigrams == null)
      jcasType.jcas.throwFeatMissing("bigrams", "type.Review");
    jcasType.ll_cas.ll_setRefValue(addr, ((Review_Type)jcasType).casFeatCode_bigrams, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: trigrams

  /** getter for trigrams - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getTrigrams() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_trigrams == null)
      jcasType.jcas.throwFeatMissing("trigrams", "type.Review");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Review_Type)jcasType).casFeatCode_trigrams)));}
    
  /** setter for trigrams - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTrigrams(FSList v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_trigrams == null)
      jcasType.jcas.throwFeatMissing("trigrams", "type.Review");
    jcasType.ll_cas.ll_setRefValue(addr, ((Review_Type)jcasType).casFeatCode_trigrams, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: reviewTime

  /** getter for reviewTime - gets 
   * @generated
   * @return value of the feature 
   */
  public String getReviewTime() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_reviewTime == null)
      jcasType.jcas.throwFeatMissing("reviewTime", "type.Review");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Review_Type)jcasType).casFeatCode_reviewTime);}
    
  /** setter for reviewTime - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setReviewTime(String v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_reviewTime == null)
      jcasType.jcas.throwFeatMissing("reviewTime", "type.Review");
    jcasType.ll_cas.ll_setStringValue(addr, ((Review_Type)jcasType).casFeatCode_reviewTime, v);}    
   
    
  //*--------------*
  //* Feature: summary

  /** getter for summary - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSummary() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_summary == null)
      jcasType.jcas.throwFeatMissing("summary", "type.Review");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Review_Type)jcasType).casFeatCode_summary);}
    
  /** setter for summary - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSummary(String v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_summary == null)
      jcasType.jcas.throwFeatMissing("summary", "type.Review");
    jcasType.ll_cas.ll_setStringValue(addr, ((Review_Type)jcasType).casFeatCode_summary, v);}    
   
    
  //*--------------*
  //* Feature: productId

  /** getter for productId - gets 
   * @generated
   * @return value of the feature 
   */
  public String getProductId() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_productId == null)
      jcasType.jcas.throwFeatMissing("productId", "type.Review");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Review_Type)jcasType).casFeatCode_productId);}
    
  /** setter for productId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setProductId(String v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_productId == null)
      jcasType.jcas.throwFeatMissing("productId", "type.Review");
    jcasType.ll_cas.ll_setStringValue(addr, ((Review_Type)jcasType).casFeatCode_productId, v);}    
   
    
  //*--------------*
  //* Feature: helpfulness

  /** getter for helpfulness - gets 
   * @generated
   * @return value of the feature 
   */
  public int getHelpfulness() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_helpfulness == null)
      jcasType.jcas.throwFeatMissing("helpfulness", "type.Review");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Review_Type)jcasType).casFeatCode_helpfulness);}
    
  /** setter for helpfulness - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHelpfulness(int v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_helpfulness == null)
      jcasType.jcas.throwFeatMissing("helpfulness", "type.Review");
    jcasType.ll_cas.ll_setIntValue(addr, ((Review_Type)jcasType).casFeatCode_helpfulness, v);}    
   
    
  //*--------------*
  //* Feature: helpfulnessTotal

  /** getter for helpfulnessTotal - gets 
   * @generated
   * @return value of the feature 
   */
  public int getHelpfulnessTotal() {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_helpfulnessTotal == null)
      jcasType.jcas.throwFeatMissing("helpfulnessTotal", "type.Review");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Review_Type)jcasType).casFeatCode_helpfulnessTotal);}
    
  /** setter for helpfulnessTotal - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHelpfulnessTotal(int v) {
    if (Review_Type.featOkTst && ((Review_Type)jcasType).casFeat_helpfulnessTotal == null)
      jcasType.jcas.throwFeatMissing("helpfulnessTotal", "type.Review");
    jcasType.ll_cas.ll_setIntValue(addr, ((Review_Type)jcasType).casFeatCode_helpfulnessTotal, v);}    
  }

    