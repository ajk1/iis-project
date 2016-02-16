
/* First created by JCasGen Sun Oct 04 16:51:34 EDT 2015 */
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
 * Updated by JCasGen Mon Oct 12 15:10:51 EDT 2015
 * @generated */
public class Measurement_Type extends TOP_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Measurement_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Measurement_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Measurement(addr, Measurement_Type.this);
  			   Measurement_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Measurement(addr, Measurement_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Measurement.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Measurement");
 
  /** @generated */
  final Feature casFeat_tp;
  /** @generated */
  final int     casFeatCode_tp;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTp(int addr) {
        if (featOkTst && casFeat_tp == null)
      jcas.throwFeatMissing("tp", "type.Measurement");
    return ll_cas.ll_getIntValue(addr, casFeatCode_tp);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTp(int addr, int v) {
        if (featOkTst && casFeat_tp == null)
      jcas.throwFeatMissing("tp", "type.Measurement");
    ll_cas.ll_setIntValue(addr, casFeatCode_tp, v);}
    
  
 
  /** @generated */
  final Feature casFeat_fn;
  /** @generated */
  final int     casFeatCode_fn;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getFn(int addr) {
        if (featOkTst && casFeat_fn == null)
      jcas.throwFeatMissing("fn", "type.Measurement");
    return ll_cas.ll_getIntValue(addr, casFeatCode_fn);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFn(int addr, int v) {
        if (featOkTst && casFeat_fn == null)
      jcas.throwFeatMissing("fn", "type.Measurement");
    ll_cas.ll_setIntValue(addr, casFeatCode_fn, v);}
    
  
 
  /** @generated */
  final Feature casFeat_fp;
  /** @generated */
  final int     casFeatCode_fp;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getFp(int addr) {
        if (featOkTst && casFeat_fp == null)
      jcas.throwFeatMissing("fp", "type.Measurement");
    return ll_cas.ll_getIntValue(addr, casFeatCode_fp);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFp(int addr, int v) {
        if (featOkTst && casFeat_fp == null)
      jcas.throwFeatMissing("fp", "type.Measurement");
    ll_cas.ll_setIntValue(addr, casFeatCode_fp, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Measurement_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tp = jcas.getRequiredFeatureDE(casType, "tp", "uima.cas.Integer", featOkTst);
    casFeatCode_tp  = (null == casFeat_tp) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tp).getCode();

 
    casFeat_fn = jcas.getRequiredFeatureDE(casType, "fn", "uima.cas.Integer", featOkTst);
    casFeatCode_fn  = (null == casFeat_fn) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_fn).getCode();

 
    casFeat_fp = jcas.getRequiredFeatureDE(casType, "fp", "uima.cas.Integer", featOkTst);
    casFeatCode_fp  = (null == casFeat_fp) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_fp).getCode();

  }
}



    