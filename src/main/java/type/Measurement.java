

/* First created by JCasGen Sun Oct 04 16:51:34 EDT 2015 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Mon Oct 12 15:10:51 EDT 2015
 * XML source: /home/junaraki/git/f15-11-791/template-projects/pi7/pi7-andrewid/src/main/resources/descriptors/typeSystem.xml
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
  }

    