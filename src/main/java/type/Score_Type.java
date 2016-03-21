
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
 * Updated by JCasGen Mon Mar 21 16:35:27 EDT 2016
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
  final Feature casFeat_regressionScores;
  /** @generated */
  final int     casFeatCode_regressionScores;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getRegressionScores(int addr) {
        if (featOkTst && casFeat_regressionScores == null)
      jcas.throwFeatMissing("regressionScores", "type.Score");
    return ll_cas.ll_getRefValue(addr, casFeatCode_regressionScores);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRegressionScores(int addr, int v) {
        if (featOkTst && casFeat_regressionScores == null)
      jcas.throwFeatMissing("regressionScores", "type.Score");
    ll_cas.ll_setRefValue(addr, casFeatCode_regressionScores, v);}
    
  
 
  /** @generated */
  final Feature casFeat_classificationScores;
  /** @generated */
  final int     casFeatCode_classificationScores;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getClassificationScores(int addr) {
        if (featOkTst && casFeat_classificationScores == null)
      jcas.throwFeatMissing("classificationScores", "type.Score");
    return ll_cas.ll_getRefValue(addr, casFeatCode_classificationScores);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setClassificationScores(int addr, int v) {
        if (featOkTst && casFeat_classificationScores == null)
      jcas.throwFeatMissing("classificationScores", "type.Score");
    ll_cas.ll_setRefValue(addr, casFeatCode_classificationScores, v);}
    
  
 
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

 
    casFeat_regressionScores = jcas.getRequiredFeatureDE(casType, "regressionScores", "uima.cas.FloatList", featOkTst);
    casFeatCode_regressionScores  = (null == casFeat_regressionScores) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_regressionScores).getCode();

 
    casFeat_classificationScores = jcas.getRequiredFeatureDE(casType, "classificationScores", "uima.cas.IntegerList", featOkTst);
    casFeatCode_classificationScores  = (null == casFeat_classificationScores) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_classificationScores).getCode();

 
    casFeat_goldLabel = jcas.getRequiredFeatureDE(casType, "goldLabel", "uima.cas.Integer", featOkTst);
    casFeatCode_goldLabel  = (null == casFeat_goldLabel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_goldLabel).getCode();

  }
}



    