

/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Thu Feb 25 22:28:29 EST 2016
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
  //* Feature: regressionScore

  /** getter for regressionScore - gets 
   * @generated
   * @return value of the feature 
   */
  public double getRegressionScore() {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_regressionScore == null)
      jcasType.jcas.throwFeatMissing("regressionScore", "type.Score");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Score_Type)jcasType).casFeatCode_regressionScore);}
    
  /** setter for regressionScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRegressionScore(double v) {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_regressionScore == null)
      jcasType.jcas.throwFeatMissing("regressionScore", "type.Score");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Score_Type)jcasType).casFeatCode_regressionScore, v);}    
   
    
  //*--------------*
  //* Feature: classificationScore

  /** getter for classificationScore - gets 
   * @generated
   * @return value of the feature 
   */
  public int getClassificationScore() {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_classificationScore == null)
      jcasType.jcas.throwFeatMissing("classificationScore", "type.Score");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Score_Type)jcasType).casFeatCode_classificationScore);}
    
  /** setter for classificationScore - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setClassificationScore(int v) {
    if (Score_Type.featOkTst && ((Score_Type)jcasType).casFeat_classificationScore == null)
      jcasType.jcas.throwFeatMissing("classificationScore", "type.Score");
    jcasType.ll_cas.ll_setIntValue(addr, ((Score_Type)jcasType).casFeatCode_classificationScore, v);}    
   
    
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

    