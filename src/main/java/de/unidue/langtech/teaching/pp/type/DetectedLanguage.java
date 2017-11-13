

/* First created by JCasGen Tue Oct 15 15:43:58 CEST 2013 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Oct 15 15:51:15 CEST 2013
 * XML source: /home/zesch/workspace_new/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/LanguageType.xml
 * @generated */
public class DetectedLanguage extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DetectedLanguage.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DetectedLanguage() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DetectedLanguage(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DetectedLanguage(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DetectedLanguage(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: language

  /** getter for language - gets 
   * @generated */
  public String getLanguage() {
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "de.unidue.langtech.teaching.pp.type.DetectedLanguage");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_language);}
    
  /** setter for language - sets  
   * @generated */
  public void setLanguage(String v) {
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "de.unidue.langtech.teaching.pp.type.DetectedLanguage");
    jcasType.ll_cas.ll_setStringValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_language, v);}    
  }

    