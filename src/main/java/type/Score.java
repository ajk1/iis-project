

/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



import org.apache.uima.jcas.cas.IntegerList;
import org.apache.uima.jcas.cas.FloatList;


/** 
 * Updated by JCasGen Mon Mar 21 15:57:29 EDT 2016
 * XML source: /Users/Terry/Desktop/CMU/16-spring/11792/project/OpinionMining/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Score extends ComponentAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Score.class);
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
  protected Score() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Score(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Score(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Score(JCas jcas, int begin, int end) {
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
  //* Feature: regressionScores

  /** getter for regressionScores - gets 
   * @generated
   * @return value of the feature 
   */
  public FloatList getRegressionScores() {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_regressionScores == null)
      jcasType.jcas.throwFeatMissing("regressionScores", "type.Score");
    return (FloatList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Score_Type)jcasType).casFeatCode_regressionScores)));}
    
  /** setter for regressionScores - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRegressionScores(FloatList v) {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_regressionScores == null)
      jcasType.jcas.throwFeatMissing("regressionScores", "type.Score");
    jcasType.ll_cas.ll_setRefValue(addr, ((Score_Type)jcasType).casFeatCode_regressionScores, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: classificationScores

  /** getter for classificationScores - gets 
   * @generated
   * @return value of the feature 
   */
  public IntegerList getClassificationScores() {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_classificationScores == null)
      jcasType.jcas.throwFeatMissing("classificationScores", "type.Score");
    return (IntegerList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Score_Type)jcasType).casFeatCode_classificationScores)));}
    
  /** setter for classificationScores - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setClassificationScores(IntegerList v) {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_classificationScores == null)
      jcasType.jcas.throwFeatMissing("classificationScores", "type.Score");
    jcasType.ll_cas.ll_setRefValue(addr, ((Score_Type)jcasType).casFeatCode_classificationScores, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: goldLabel

  /** getter for goldLabel - gets 
   * @generated
   * @return value of the feature 
   */
  public int getGoldLabel() {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_goldLabel == null)
      jcasType.jcas.throwFeatMissing("goldLabel", "type.Score");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Score_Type)jcasType).casFeatCode_goldLabel);}
    
  /** setter for goldLabel - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGoldLabel(int v) {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_goldLabel == null)
      jcasType.jcas.throwFeatMissing("goldLabel", "type.Score");
    jcasType.ll_cas.ll_setIntValue(addr, ((Score_Type)jcasType).casFeatCode_goldLabel, v);}    
  }

    