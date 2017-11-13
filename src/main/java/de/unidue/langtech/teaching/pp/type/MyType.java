

/* First created by JCasGen Thu Sep 04 09:09:22 CEST 2014 */
package de.unidue.langtech.teaching.pp.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Sep 04 09:09:22 CEST 2014
 * XML source: /Users/Tobias/Documents/Eclipse/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/MyType.xml
 * @generated */
public class MyType extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MyType.class);
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
  protected MyType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MyType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MyType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MyType(JCas jcas, int begin, int end) {
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
  //* Feature: countLetterE

  /** getter for countLetterE - gets 
   * @generated
   * @return value of the feature 
   */
  public int getCountLetterE() {
    if (MyType_Type.featOkTst && ((MyType_Type)jcasType).casFeat_countLetterE == null)
      jcasType.jcas.throwFeatMissing("countLetterE", "de.unidue.langtech.teaching.pp.type.MyType");
    return jcasType.ll_cas.ll_getIntValue(addr, ((MyType_Type)jcasType).casFeatCode_countLetterE);}
    
  /** setter for countLetterE - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCountLetterE(int v) {
    if (MyType_Type.featOkTst && ((MyType_Type)jcasType).casFeat_countLetterE == null)
      jcasType.jcas.throwFeatMissing("countLetterE", "de.unidue.langtech.teaching.pp.type.MyType");
    jcasType.ll_cas.ll_setIntValue(addr, ((MyType_Type)jcasType).casFeatCode_countLetterE, v);}    
  }

    