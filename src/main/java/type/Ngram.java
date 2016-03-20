

/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;


import org.apache.uima.jcas.cas.FloatList;


/** 
 * Updated by JCasGen Sun Mar 20 18:11:57 EDT 2016
 * XML source: /Users/Terry/Desktop/CMU/16-spring/11792/project/OpinionMining/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Ngram extends ComponentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Ngram.class);
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
  protected Ngram() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Ngram(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Ngram(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Ngram(JCas jcas, int begin, int end) {
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
  //* Feature: n

  /** getter for n - gets 
   * @generated
   * @return value of the feature 
   */
  public int getN() {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_n == null)
      jcasType.jcas.throwFeatMissing("n", "type.Ngram");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Ngram_Type)jcasType).casFeatCode_n);}
    
  /** setter for n - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setN(int v) {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_n == null)
      jcasType.jcas.throwFeatMissing("n", "type.Ngram");
    jcasType.ll_cas.ll_setIntValue(addr, ((Ngram_Type)jcasType).casFeatCode_n, v);}    
   
    
  //*--------------*
  //* Feature: tokens

  /** getter for tokens - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getTokens() {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Ngram");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_tokens)));}
    
  /** setter for tokens - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTokens(FSList v) {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Ngram");
    jcasType.ll_cas.ll_setRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_tokens, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: rawText

  /** getter for rawText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRawText() {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Ngram");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Ngram_Type)jcasType).casFeatCode_rawText);}
    
  /** setter for rawText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRawText(String v) {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Ngram");
    jcasType.ll_cas.ll_setStringValue(addr, ((Ngram_Type)jcasType).casFeatCode_rawText, v);}    
   
    
  //*--------------*
  //* Feature: sentimentScore

  /** getter for sentimentScore - gets 
   * @generated
   * @return value of the feature 
   */
  public FloatList getSentimentScore() {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_sentimentScore == null)
      jcasType.jcas.throwFeatMissing("sentimentScore", "type.Ngram");
    return (FloatList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_sentimentScore)));}
    
  /** setter for sentimentScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentimentScore(FloatList v) {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_sentimentScore == null)
      jcasType.jcas.throwFeatMissing("sentimentScore", "type.Ngram");
    jcasType.ll_cas.ll_setRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_sentimentScore, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: negationScore

  /** getter for negationScore - gets 
   * @generated
   * @return value of the feature 
   */
  public FloatList getNegationScore() {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_negationScore == null)
      jcasType.jcas.throwFeatMissing("negationScore", "type.Ngram");
    return (FloatList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_negationScore)));}
    
  /** setter for negationScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNegationScore(FloatList v) {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_negationScore == null)
      jcasType.jcas.throwFeatMissing("negationScore", "type.Ngram");
    jcasType.ll_cas.ll_setRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_negationScore, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: amplificationScore

  /** getter for amplificationScore - gets 
   * @generated
   * @return value of the feature 
   */
  public FloatList getAmplificationScore() {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_amplificationScore == null)
      jcasType.jcas.throwFeatMissing("amplificationScore", "type.Ngram");
    return (FloatList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_amplificationScore)));}
    
  /** setter for amplificationScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAmplificationScore(FloatList v) {
    if (Ngram_Type.featOkTst && ((Ngram_Type)jcasType).casFeat_amplificationScore == null)
      jcasType.jcas.throwFeatMissing("amplificationScore", "type.Ngram");
    jcasType.ll_cas.ll_setRefValue(addr, ((Ngram_Type)jcasType).casFeatCode_amplificationScore, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    