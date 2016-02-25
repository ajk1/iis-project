

/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;


/** 
 * Updated by JCasGen Wed Feb 24 22:45:51 EST 2016
 * XML source: /Users/Terry/Desktop/CMU/16-spring/11792/project/OpinionMining/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Sentence extends ComponentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Sentence.class);
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
  protected Sentence() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Sentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Sentence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Sentence(JCas jcas, int begin, int end) {
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
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Sentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Sentence_Type)jcasType).casFeatCode_rawText);}
    
  /** setter for rawText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRawText(String v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_rawText == null)
      jcasType.jcas.throwFeatMissing("rawText", "type.Sentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((Sentence_Type)jcasType).casFeatCode_rawText, v);}    
   
    
  //*--------------*
  //* Feature: tokens

  /** getter for tokens - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getTokens() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Sentence");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_tokens)));}
    
  /** setter for tokens - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTokens(FSList v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_tokens == null)
      jcasType.jcas.throwFeatMissing("tokens", "type.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_tokens, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: bigrams

  /** getter for bigrams - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getBigrams() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_bigrams == null)
      jcasType.jcas.throwFeatMissing("bigrams", "type.Sentence");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_bigrams)));}
    
  /** setter for bigrams - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setBigrams(FSList v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_bigrams == null)
      jcasType.jcas.throwFeatMissing("bigrams", "type.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_bigrams, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: trigrams

  /** getter for trigrams - gets 
   * @generated
   * @return value of the feature 
   */
  public FSList getTrigrams() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_trigrams == null)
      jcasType.jcas.throwFeatMissing("trigrams", "type.Sentence");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_trigrams)));}
    
  /** setter for trigrams - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTrigrams(FSList v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_trigrams == null)
      jcasType.jcas.throwFeatMissing("trigrams", "type.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_trigrams, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    