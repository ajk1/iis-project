

/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;


import org.apache.uima.jcas.cas.StringList;


/** 
 * Updated by JCasGen Wed Apr 06 22:47:28 EDT 2016
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
  //* Feature: bigramList

  /** getter for bigramList - gets 
   * @generated
   * @return value of the feature 
   */
  public StringList getBigramList() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_bigramList == null)
      jcasType.jcas.throwFeatMissing("bigramList", "type.Sentence");
    return (StringList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_bigramList)));}
    
  /** setter for bigramList - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setBigramList(StringList v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_bigramList == null)
      jcasType.jcas.throwFeatMissing("bigramList", "type.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_bigramList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: unigramList

  /** getter for unigramList - gets 
   * @generated
   * @return value of the feature 
   */
  public StringList getUnigramList() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_unigramList == null)
      jcasType.jcas.throwFeatMissing("unigramList", "type.Sentence");
    return (StringList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_unigramList)));}
    
  /** setter for unigramList - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setUnigramList(StringList v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_unigramList == null)
      jcasType.jcas.throwFeatMissing("unigramList", "type.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_unigramList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: trigramList

  /** getter for trigramList - gets 
   * @generated
   * @return value of the feature 
   */
  public StringList getTrigramList() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_trigramList == null)
      jcasType.jcas.throwFeatMissing("trigramList", "type.Sentence");
    return (StringList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_trigramList)));}
    
  /** setter for trigramList - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTrigramList(StringList v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_trigramList == null)
      jcasType.jcas.throwFeatMissing("trigramList", "type.Sentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((Sentence_Type)jcasType).casFeatCode_trigramList, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    