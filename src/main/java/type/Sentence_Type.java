
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
 * Updated by JCasGen Sat Apr 23 16:19:37 EDT 2016
 * @generated */
public class Sentence_Type extends ComponentAnnotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Sentence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Sentence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Sentence(addr, Sentence_Type.this);
  			   Sentence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Sentence(addr, Sentence_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Sentence.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Sentence");
 
  /** @generated */
  final Feature casFeat_rawText;
  /** @generated */
  final int     casFeatCode_rawText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRawText(int addr) {
        if (featOkTst && casFeat_rawText == null)
      jcas.throwFeatMissing("rawText", "type.Sentence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_rawText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRawText(int addr, String v) {
        if (featOkTst && casFeat_rawText == null)
      jcas.throwFeatMissing("rawText", "type.Sentence");
    ll_cas.ll_setStringValue(addr, casFeatCode_rawText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_bigramList;
  /** @generated */
  final int     casFeatCode_bigramList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getBigramList(int addr) {
        if (featOkTst && casFeat_bigramList == null)
      jcas.throwFeatMissing("bigramList", "type.Sentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_bigramList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setBigramList(int addr, int v) {
        if (featOkTst && casFeat_bigramList == null)
      jcas.throwFeatMissing("bigramList", "type.Sentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_bigramList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_unigramList;
  /** @generated */
  final int     casFeatCode_unigramList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getUnigramList(int addr) {
        if (featOkTst && casFeat_unigramList == null)
      jcas.throwFeatMissing("unigramList", "type.Sentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_unigramList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setUnigramList(int addr, int v) {
        if (featOkTst && casFeat_unigramList == null)
      jcas.throwFeatMissing("unigramList", "type.Sentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_unigramList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_trigramList;
  /** @generated */
  final int     casFeatCode_trigramList;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTrigramList(int addr) {
        if (featOkTst && casFeat_trigramList == null)
      jcas.throwFeatMissing("trigramList", "type.Sentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_trigramList);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTrigramList(int addr, int v) {
        if (featOkTst && casFeat_trigramList == null)
      jcas.throwFeatMissing("trigramList", "type.Sentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_trigramList, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Sentence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_rawText = jcas.getRequiredFeatureDE(casType, "rawText", "uima.cas.String", featOkTst);
    casFeatCode_rawText  = (null == casFeat_rawText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_rawText).getCode();

 
    casFeat_bigramList = jcas.getRequiredFeatureDE(casType, "bigramList", "uima.cas.StringList", featOkTst);
    casFeatCode_bigramList  = (null == casFeat_bigramList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_bigramList).getCode();

 
    casFeat_unigramList = jcas.getRequiredFeatureDE(casType, "unigramList", "uima.cas.StringList", featOkTst);
    casFeatCode_unigramList  = (null == casFeat_unigramList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_unigramList).getCode();

 
    casFeat_trigramList = jcas.getRequiredFeatureDE(casType, "trigramList", "uima.cas.StringList", featOkTst);
    casFeatCode_trigramList  = (null == casFeat_trigramList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_trigramList).getCode();

  }
}



    