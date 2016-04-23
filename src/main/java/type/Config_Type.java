
/* First created by JCasGen Sat Apr 23 16:19:35 EDT 2016 */
package type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.cas.TOP_Type;

/** 
 * Updated by JCasGen Sat Apr 23 17:40:16 EDT 2016
 * @generated */
public class Config_Type extends TOP_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Config_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Config_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Config(addr, Config_Type.this);
  			   Config_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Config(addr, Config_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Config.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Config");
 
  /** @generated */
  final Feature casFeat_InputFileName;
  /** @generated */
  final int     casFeatCode_InputFileName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getInputFileName(int addr) {
        if (featOkTst && casFeat_InputFileName == null)
      jcas.throwFeatMissing("InputFileName", "type.Config");
    return ll_cas.ll_getStringValue(addr, casFeatCode_InputFileName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInputFileName(int addr, String v) {
        if (featOkTst && casFeat_InputFileName == null)
      jcas.throwFeatMissing("InputFileName", "type.Config");
    ll_cas.ll_setStringValue(addr, casFeatCode_InputFileName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ModelNames;
  /** @generated */
  final int     casFeatCode_ModelNames;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getModelNames(int addr) {
        if (featOkTst && casFeat_ModelNames == null)
      jcas.throwFeatMissing("ModelNames", "type.Config");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ModelNames);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setModelNames(int addr, int v) {
        if (featOkTst && casFeat_ModelNames == null)
      jcas.throwFeatMissing("ModelNames", "type.Config");
    ll_cas.ll_setRefValue(addr, casFeatCode_ModelNames, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Config_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_InputFileName = jcas.getRequiredFeatureDE(casType, "InputFileName", "uima.cas.String", featOkTst);
    casFeatCode_InputFileName  = (null == casFeat_InputFileName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_InputFileName).getCode();

 
    casFeat_ModelNames = jcas.getRequiredFeatureDE(casType, "ModelNames", "uima.cas.StringList", featOkTst);
    casFeatCode_ModelNames  = (null == casFeat_ModelNames) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ModelNames).getCode();

  }
}



    