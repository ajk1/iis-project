
/* First created by JCasGen Wed Feb 24 22:18:09 EST 2016 */
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

/** 
 * Updated by JCasGen Thu Feb 25 22:28:29 EST 2016
 * @generated */
public class Score_Type extends ComponentAnnotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Score_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Score_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Score(addr, Score_Type.this);
  			   Score_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Score(addr, Score_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Score.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Score");
 
  /** @generated */
  final Feature casFeat_regressionScore;
  /** @generated */
  final int     casFeatCode_regressionScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public double getRegressionScore(int addr) {
        if (featOkTst && casFeat_regressionScore == null)
      jcas.throwFeatMissing("regressionScore", "type.Score");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_regressionScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRegressionScore(int addr, double v) {
        if (featOkTst && casFeat_regressionScore == null)
      jcas.throwFeatMissing("regressionScore", "type.Score");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_regressionScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_classificationScore;
  /** @generated */
  final int     casFeatCode_classificationScore;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getClassificationScore(int addr) {
        if (featOkTst && casFeat_classificationScore == null)
      jcas.throwFeatMissing("classificationScore", "type.Score");
    return ll_cas.ll_getIntValue(addr, casFeatCode_classificationScore);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setClassificationScore(int addr, int v) {
        if (featOkTst && casFeat_classificationScore == null)
      jcas.throwFeatMissing("classificationScore", "type.Score");
    ll_cas.ll_setIntValue(addr, casFeatCode_classificationScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_goldLabel;
  /** @generated */
  final int     casFeatCode_goldLabel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getGoldLabel(int addr) {
        if (featOkTst && casFeat_goldLabel == null)
      jcas.throwFeatMissing("goldLabel", "type.Score");
    return ll_cas.ll_getIntValue(addr, casFeatCode_goldLabel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGoldLabel(int addr, int v) {
        if (featOkTst && casFeat_goldLabel == null)
      jcas.throwFeatMissing("goldLabel", "type.Score");
    ll_cas.ll_setIntValue(addr, casFeatCode_goldLabel, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Score_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_regressionScore = jcas.getRequiredFeatureDE(casType, "regressionScore", "uima.cas.Double", featOkTst);
    casFeatCode_regressionScore  = (null == casFeat_regressionScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_regressionScore).getCode();

 
    casFeat_classificationScore = jcas.getRequiredFeatureDE(casType, "classificationScore", "uima.cas.Integer", featOkTst);
    casFeatCode_classificationScore  = (null == casFeat_classificationScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_classificationScore).getCode();

 
    casFeat_goldLabel = jcas.getRequiredFeatureDE(casType, "goldLabel", "uima.cas.Integer", featOkTst);
    casFeatCode_goldLabel  = (null == casFeat_goldLabel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_goldLabel).getCode();

  }
}



    