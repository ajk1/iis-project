

/* First created by JCasGen Sat Apr 23 16:19:35 EDT 2016 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Sat Apr 23 16:19:37 EDT 2016
 * XML source: /Users/Terry/Desktop/CMU/16-spring/11792/project/OpinionMining/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Config extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Config.class);
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
  protected Config() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Config(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Config(JCas jcas) {
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
  //* Feature: InputFileName

  /** getter for InputFileName - gets 
   * @generated
   * @return value of the feature 
   */
  public String getInputFileName() {
    if (Config_Type.featOkTst && ((Config_Type)jcasType).casFeat_InputFileName == null)
      jcasType.jcas.throwFeatMissing("InputFileName", "type.Config");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Config_Type)jcasType).casFeatCode_InputFileName);}
    
  /** setter for InputFileName - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setInputFileName(String v) {
    if (Config_Type.featOkTst && ((Config_Type)jcasType).casFeat_InputFileName == null)
      jcasType.jcas.throwFeatMissing("InputFileName", "type.Config");
    jcasType.ll_cas.ll_setStringValue(addr, ((Config_Type)jcasType).casFeatCode_InputFileName, v);}    
  }

    