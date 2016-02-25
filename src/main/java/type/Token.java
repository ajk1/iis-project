

/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Wed Feb 24 22:45:51 EST 2016
 * XML source: /Users/Terry/Desktop/CMU/16-spring/11792/project/OpinionMining/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Token extends ComponentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Token.class);
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
  protected Token() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Token(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Token(JCas jcas, int begin, int end) {
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
  //* Feature: rawText

  /** getter for rawText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRawText() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_rawText);}
    
  /** setter for rawText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRawText(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_rawText, v);}    
   
    
  //*--------------*
  //* Feature: sentimentScore

  /** getter for sentimentScore - gets 
   * @generated
   * @return value of the feature 
   */
  public double getSentimentScore() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_sentimentScore == null)
      jcasType.jcas.throwFeatMissing("sentimentScore", "type.Token");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Token_Type)jcasType).casFeatCode_sentimentScore);}
    
  /** setter for sentimentScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentimentScore(double v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_sentimentScore == null)
      jcasType.jcas.throwFeatMissing("sentimentScore", "type.Token");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Token_Type)jcasType).casFeatCode_sentimentScore, v);}    
   
    
  //*--------------*
  //* Feature: negationScore

  /** getter for negationScore - gets 
   * @generated
   * @return value of the feature 
   */
  public double getNegationScore() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_negationScore == null)
      jcasType.jcas.throwFeatMissing("negationScore", "type.Token");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Token_Type)jcasType).casFeatCode_negationScore);}
    
  /** setter for negationScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNegationScore(double v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_negationScore == null)
      jcasType.jcas.throwFeatMissing("negationScore", "type.Token");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Token_Type)jcasType).casFeatCode_negationScore, v);}    
   
    
  //*--------------*
  //* Feature: amplificationScore

  /** getter for amplificationScore - gets 
   * @generated
   * @return value of the feature 
   */
  public double getAmplificationScore() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_amplificationScore == null)
      jcasType.jcas.throwFeatMissing("amplificationScore", "type.Token");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Token_Type)jcasType).casFeatCode_amplificationScore);}
    
  /** setter for amplificationScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAmplificationScore(double v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_amplificationScore == null)
      jcasType.jcas.throwFeatMissing("amplificationScore", "type.Token");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Token_Type)jcasType).casFeatCode_amplificationScore, v);}    
   
    
  //*--------------*
  //* Feature: reviewId

  /** getter for reviewId - gets 
   * @generated
   * @return value of the feature 
   */
  public String getReviewId() {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_reviewId == null)
      jcasType.jcas.throwFeatMissing("reviewId", "type.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type)jcasType).casFeatCode_reviewId);}
    
  /** setter for reviewId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setReviewId(String v) {
    if (Token_Type.featOkTst && ((Token_Type)jcasType).casFeat_reviewId == null)
      jcasType.jcas.throwFeatMissing("reviewId", "type.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type)jcasType).casFeatCode_reviewId, v);}    
  }

    