

/* First created by JCasGen Sun Oct 04 16:51:34 EDT 2015 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Thu Feb 25 22:28:29 EST 2016
 * XML source: /Users/Terry/Desktop/CMU/16-spring/11792/project/OpinionMining/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Measurement extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Measurement.class);
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
  protected Measurement() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Measurement(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Measurement(JCas jcas) {
    super(jcas);
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
  //* Feature: tp

  /** getter for tp - gets The number of true positives
   * @generated
   * @return value of the feature 
   */
  public int getTp() {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_tp == null)
      jcasType.jcas.throwFeatMissing("tp", "type.Measurement");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Measurement_Type)jcasType).casFeatCode_tp);}
    
  /** setter for tp - sets The number of true positives 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTp(int v) {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_tp == null)
      jcasType.jcas.throwFeatMissing("tp", "type.Measurement");
    jcasType.ll_cas.ll_setIntValue(addr, ((Measurement_Type)jcasType).casFeatCode_tp, v);}    
   
    
  //*--------------*
  //* Feature: fn

  /** getter for fn - gets The number of false negatives
   * @generated
   * @return value of the feature 
   */
  public int getFn() {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_fn == null)
      jcasType.jcas.throwFeatMissing("fn", "type.Measurement");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Measurement_Type)jcasType).casFeatCode_fn);}
    
  /** setter for fn - sets The number of false negatives 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFn(int v) {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_fn == null)
      jcasType.jcas.throwFeatMissing("fn", "type.Measurement");
    jcasType.ll_cas.ll_setIntValue(addr, ((Measurement_Type)jcasType).casFeatCode_fn, v);}    
   
    
  //*--------------*
  //* Feature: fp

  /** getter for fp - gets The number of false positives
   * @generated
   * @return value of the feature 
   */
  public int getFp() {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_fp == null)
      jcasType.jcas.throwFeatMissing("fp", "type.Measurement");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Measurement_Type)jcasType).casFeatCode_fp);}
    
  /** setter for fp - sets The number of false positives 
   * @generated
   * @param v value to set into the feature 
   */
  public void setFp(int v) {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_fp == null)
      jcasType.jcas.throwFeatMissing("fp", "type.Measurement");
    jcasType.ll_cas.ll_setIntValue(addr, ((Measurement_Type)jcasType).casFeatCode_fp, v);}    
   
    
  //*--------------*
  //* Feature: L1

  /** getter for L1 - gets 
   * @generated
   * @return value of the feature 
   */
  public double getL1() {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_L1 == null)
      jcasType.jcas.throwFeatMissing("L1", "type.Measurement");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Measurement_Type)jcasType).casFeatCode_L1);}
    
  /** setter for L1 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setL1(double v) {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_L1 == null)
      jcasType.jcas.throwFeatMissing("L1", "type.Measurement");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Measurement_Type)jcasType).casFeatCode_L1, v);}    
   
    
  //*--------------*
  //* Feature: L2

  /** getter for L2 - gets 
   * @generated
   * @return value of the feature 
   */
  public double getL2() {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_L2 == null)
      jcasType.jcas.throwFeatMissing("L2", "type.Measurement");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Measurement_Type)jcasType).casFeatCode_L2);}
    
  /** setter for L2 - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setL2(double v) {
    if (Measurement_Type.featOkTst && ((Measurement_Type)jcasType).casFeat_L2 == null)
      jcasType.jcas.throwFeatMissing("L2", "type.Measurement");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Measurement_Type)jcasType).casFeatCode_L2, v);}    
  }

    