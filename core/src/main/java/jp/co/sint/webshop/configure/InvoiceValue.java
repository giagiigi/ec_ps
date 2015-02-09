package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; 
import java.util.Locale;
 
import jp.co.sint.webshop.code.NameValue; 
import jp.co.sint.webshop.data.domain.LanguageCode; 
import jp.co.sint.webshop.utility.CodeUtil; 
import jp.co.sint.webshop.utility.StringUtil;
/**
 *  发票商品规格
 * @author System Integrator Corp.
 */
public class InvoiceValue implements Serializable {

  private static final long serialVersionUID = 1L; 
  private List<NameValue> invoiceCommodityNameList=new ArrayList<NameValue>();
  private List<NameValue> invoiceCommodityNameListByLanguage=new ArrayList<NameValue>();
  private List<List<NameValue>> invoiceCommodityNameByLanguageList=new ArrayList<List<NameValue>>();
  private List<NameValue> invoiceCommodityNameListLocal=new ArrayList<NameValue>();
  private String invoiceCommodityNameList0;
  private String invoiceCommodityNameList1;
  private String invoiceCommodityNameList2;
  private String invoiceCommodityNameList3;
  private String invoiceCommodityNameList4;
  private String invoiceCommodityNameList5;
  private String invoiceCommodityNameList6;
  private String invoiceCommodityNameList7;
  private String invoiceCommodityNameList8;
  private String invoiceCommodityNameList9;
  private String invoiceCommodityNameList10;
  private String invoiceCommodityNameList11;
  private String invoiceCommodityNameList12;
  private String invoiceCommodityNameList13;
  private String invoiceCommodityNameList14;
  private String invoiceCommodityNameList15;
  private String invoiceCommodityNameList16;
  private String invoiceCommodityNameList17;
  private String invoiceCommodityNameList18;
  private List<String> list=new ArrayList<String>();
/**
 * @param invoiceCommodityNameList the invoiceCommodityNameList to set
 */
public void setInvoiceCommodityNameList(List<NameValue> invoiceCommodityNameList) { 
	this.invoiceCommodityNameList = invoiceCommodityNameList;
}
/**
 * @param invoiceCommodityNameListByLanguage the invoiceCommodityNameListByLanguage to set
 */
public void setInvoiceCommodityNameListByLanguage(
		List<NameValue> invoiceCommodityNameListByLanguage) {
	this.invoiceCommodityNameListByLanguage = invoiceCommodityNameListByLanguage;
}
/**
 * @param invoiceCommodityNameByLanguageList the invoiceCommodityNameByLanguageList to set
 */
public void setInvoiceCommodityNameByLanguageList(
		List<List<NameValue>> invoiceCommodityNameByLanguageList) {
	this.invoiceCommodityNameByLanguageList = invoiceCommodityNameByLanguageList;
}

private Locale defaultLocale;
/**
 * @return the invoiceCommodityNameByLanguageList
 */
public List<List<NameValue>> getInvoiceCommodityNameByLanguageList() { 
	invoiceCommodityNameByLanguageList.clear();
	for(LanguageCode languageCode:Arrays.asList(LanguageCode.values())){
		if(languageCode.getValue().equals(Locale.US.getLanguage()+"-"+Locale.US.getCountry().toLowerCase())){  
		invoiceCommodityNameByLanguageList.add(getInvoiceCommodityNameByLanguage(Locale.US));
		}else if(languageCode.getValue().equals(Locale.CHINA.getLanguage()+"-"+Locale.CHINA.getCountry().toLowerCase())){  
		invoiceCommodityNameByLanguageList.add(getInvoiceCommodityNameByLanguage(Locale.CHINA));
		}else if(languageCode.getValue().equals(Locale.JAPAN.getLanguage()+"-"+Locale.JAPAN.getCountry().toLowerCase())){  
		invoiceCommodityNameByLanguageList.add(getInvoiceCommodityNameByLanguage(Locale.JAPAN));
		}
	}
	return invoiceCommodityNameByLanguageList;
}
/**
 * @return the invoiceCommodityNameListByLanguage
 */
public List<NameValue> getInvoiceCommodityNameListByLanguage() {
	invoiceCommodityNameListByLanguage.clear();
    getInvoiceCommodityLocalName();
	getNameValueList();  
	return invoiceCommodityNameListByLanguage;
}
/**
 * @return the invoiceCommodityNameList
 */
public List<NameValue> getInvoiceCommodityNameList() {  
	    invoiceCommodityNameList.clear();
	    getInvoiceCommodityNameByLocal();
		getNameValueList(); 
		return invoiceCommodityNameList;
}
/**
 * @return the invoiceCommodityNameListLocal
 */
public List<NameValue> getInvoiceCommodityNameListLocal() {
	invoiceCommodityNameListLocal.clear();
	    getInvoiceCommodityLocalName();
		getNameValueList(); 
		return invoiceCommodityNameListLocal;
}
  
/***
 * 
 */
private void getNameValueList(){
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList0, invoiceCommodityNameList0) ); 									
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList1, invoiceCommodityNameList1) ); 									
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList2, invoiceCommodityNameList2) ); 									
	    //invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList3, invoiceCommodityNameList3) ); 									
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList4, invoiceCommodityNameList4) ); 									
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList5, invoiceCommodityNameList5) ); 									
	    //invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList6, invoiceCommodityNameList6) ); 									
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList7, invoiceCommodityNameList7) ); 									
	    //invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList8, invoiceCommodityNameList8) ); 									
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList9, invoiceCommodityNameList9) ); 									
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList10, invoiceCommodityNameList10) );										
	    //invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList11, invoiceCommodityNameList11) );										
	    //invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList12, invoiceCommodityNameList12) );										
	    //invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList13, invoiceCommodityNameList13) );										
	    //invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList14, invoiceCommodityNameList14) );										
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList15, invoiceCommodityNameList15) );										
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList16, invoiceCommodityNameList16) );		
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList17, invoiceCommodityNameList17) );   
	    invoiceCommodityNameList.add(new NameValue(invoiceCommodityNameList18, invoiceCommodityNameList18) );  
}
/***
 * 
 */
private void getInvoiceCommodityNameByLocal(){
	invoiceCommodityNameList0=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList0"),invoiceCommodityNameList0);				
	invoiceCommodityNameList1=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList1"),invoiceCommodityNameList1);				
	invoiceCommodityNameList2=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList2"),invoiceCommodityNameList2);				
	//invoiceCommodityNameList3=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList3"),invoiceCommodityNameList3);				
	invoiceCommodityNameList4=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList4"),invoiceCommodityNameList4);				
	invoiceCommodityNameList5=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList5"),invoiceCommodityNameList5);				
	//invoiceCommodityNameList6=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList6"),invoiceCommodityNameList6);				
	invoiceCommodityNameList7=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList7"),invoiceCommodityNameList7);				
	//invoiceCommodityNameList8=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList8"),invoiceCommodityNameList8);				
	invoiceCommodityNameList9=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList9"),invoiceCommodityNameList9);				
	invoiceCommodityNameList10=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList10"),invoiceCommodityNameList10);			
	//invoiceCommodityNameList11=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList11"),invoiceCommodityNameList11);			
	//invoiceCommodityNameList12=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList12"),invoiceCommodityNameList12);			
	//invoiceCommodityNameList13=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList13"),invoiceCommodityNameList13);			
	//invoiceCommodityNameList14=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList14"),invoiceCommodityNameList14);			
	invoiceCommodityNameList15=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList15"),invoiceCommodityNameList15);			
	invoiceCommodityNameList16=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList16"),invoiceCommodityNameList16);
	invoiceCommodityNameList17=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList17"),invoiceCommodityNameList17); 
	invoiceCommodityNameList18=StringUtil.coalesce(CodeUtil.getEntry("invoiceCommodityNameList18"),invoiceCommodityNameList18);    

}
private void getInvoiceCommodityLocalName(){
	invoiceCommodityNameList0=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList0"),invoiceCommodityNameList0);				
	invoiceCommodityNameList1=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList1"),invoiceCommodityNameList1);				
	invoiceCommodityNameList2=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList2"),invoiceCommodityNameList2);				
	//invoiceCommodityNameList3=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList3"),invoiceCommodityNameList3);				
	invoiceCommodityNameList4=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList4"),invoiceCommodityNameList4);				
	invoiceCommodityNameList5=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList5"),invoiceCommodityNameList5);				
	//invoiceCommodityNameList6=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList6"),invoiceCommodityNameList6);				
	invoiceCommodityNameList7=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList7"),invoiceCommodityNameList7);				
	//invoiceCommodityNameList8=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList8"),invoiceCommodityNameList8);				
	invoiceCommodityNameList9=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList9"),invoiceCommodityNameList9);				
	invoiceCommodityNameList10=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList10"),invoiceCommodityNameList10);			
	//invoiceCommodityNameList11=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList11"),invoiceCommodityNameList11);			
	//invoiceCommodityNameList12=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList12"),invoiceCommodityNameList12);			
	//invoiceCommodityNameList13=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList13"),invoiceCommodityNameList13);			
	//invoiceCommodityNameList14=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList14"),invoiceCommodityNameList14);			
	invoiceCommodityNameList15=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList15"),invoiceCommodityNameList15);			
	invoiceCommodityNameList16=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList16"),invoiceCommodityNameList16);			
	invoiceCommodityNameList17=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList17"),invoiceCommodityNameList17);
	invoiceCommodityNameList18=StringUtil.coalesce(CodeUtil.getEntryLocal("invoiceCommodityNameList18"),invoiceCommodityNameList18);
}
private List<NameValue> getInvoiceCommodityNameByLanguage(Locale locale){
	 List<NameValue> list=new  ArrayList<NameValue>();
	invoiceCommodityNameList0=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList0",locale),invoiceCommodityNameList0);				
	list.add(new NameValue(invoiceCommodityNameList0,invoiceCommodityNameList0));
	invoiceCommodityNameList1=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList1",locale),invoiceCommodityNameList1);				
	list.add(new NameValue(invoiceCommodityNameList1,invoiceCommodityNameList1));
	invoiceCommodityNameList2=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList2",locale),invoiceCommodityNameList2);				
	list.add(new NameValue(invoiceCommodityNameList2,invoiceCommodityNameList2));
	//invoiceCommodityNameList3=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList3",locale),invoiceCommodityNameList3);				
	//list.add(new NameValue(invoiceCommodityNameList3,invoiceCommodityNameList3));
	invoiceCommodityNameList4=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList4",locale),invoiceCommodityNameList4);				
	list.add(new NameValue(invoiceCommodityNameList4,invoiceCommodityNameList4));
	invoiceCommodityNameList5=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList5",locale),invoiceCommodityNameList5);				
	list.add(new NameValue(invoiceCommodityNameList5,invoiceCommodityNameList5));
	//invoiceCommodityNameList6=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList6",locale),invoiceCommodityNameList6);				
	//list.add(new NameValue(invoiceCommodityNameList6,invoiceCommodityNameList6));
	invoiceCommodityNameList7=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList7",locale),invoiceCommodityNameList7);				
	list.add(new NameValue(invoiceCommodityNameList7,invoiceCommodityNameList7));
	//invoiceCommodityNameList8=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList8",locale),invoiceCommodityNameList8);				
	//list.add(new NameValue(invoiceCommodityNameList8,invoiceCommodityNameList8));
	invoiceCommodityNameList9=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList9",locale),invoiceCommodityNameList9);				
	list.add(new NameValue(invoiceCommodityNameList9,invoiceCommodityNameList9));
	invoiceCommodityNameList10=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList10",locale),invoiceCommodityNameList10);			
	list.add(new NameValue(invoiceCommodityNameList10,invoiceCommodityNameList10));
	//invoiceCommodityNameList11=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList11",locale),invoiceCommodityNameList11);			
	//list.add(new NameValue(invoiceCommodityNameList11,invoiceCommodityNameList11));
	//invoiceCommodityNameList12=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList12",locale),invoiceCommodityNameList12);			
	//list.add(new NameValue(invoiceCommodityNameList12,invoiceCommodityNameList12));
	//invoiceCommodityNameList13=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList13",locale),invoiceCommodityNameList13);			
	//list.add(new NameValue(invoiceCommodityNameList13,invoiceCommodityNameList13));
	//invoiceCommodityNameList14=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList14",locale),invoiceCommodityNameList14);			
	//list.add(new NameValue(invoiceCommodityNameList14,invoiceCommodityNameList14));
	invoiceCommodityNameList15=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList15",locale),invoiceCommodityNameList15);			
	list.add(new NameValue(invoiceCommodityNameList15,invoiceCommodityNameList15));
	invoiceCommodityNameList16=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList16",locale),invoiceCommodityNameList16);			
	list.add(new NameValue(invoiceCommodityNameList16,invoiceCommodityNameList16));
	invoiceCommodityNameList17=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList17",locale),invoiceCommodityNameList17);     
  list.add(new NameValue(invoiceCommodityNameList17,invoiceCommodityNameList17));
  invoiceCommodityNameList18=StringUtil.coalesce(CodeUtil.getEntryByLanguage("invoiceCommodityNameList18",locale),invoiceCommodityNameList18);     
  list.add(new NameValue(invoiceCommodityNameList18,invoiceCommodityNameList18));
	return list;
}
/**
 * @param list the list to set
 */
public void setList(List<String> list) {
	this.list = list;
}
/**
 * @return the list
 */
public List<String> getList() {
	return list;
}
/**
 * @param invoiceCommodityNameList0 the invoiceCommodityNameList0 to set
 */
public void setInvoiceCommodityNameList0(String invoiceCommodityNameList0) {
	this.invoiceCommodityNameList0 = invoiceCommodityNameList0;
}
/**
 * @return the invoiceCommodityNameList0
 */
public String getInvoiceCommodityNameList0() {
	return invoiceCommodityNameList0;
}
/**
 * @param invoiceCommodityNameList1 the invoiceCommodityNameList1 to set
 */
public void setInvoiceCommodityNameList1(String invoiceCommodityNameList1) {
	this.invoiceCommodityNameList1 = invoiceCommodityNameList1;
}
/**
 * @return the invoiceCommodityNameList1
 */
public String getInvoiceCommodityNameList1() {
	return invoiceCommodityNameList1;
}
/**
 * @param invoiceCommodityNameList2 the invoiceCommodityNameList2 to set
 */
public void setInvoiceCommodityNameList2(String invoiceCommodityNameList2) {
	this.invoiceCommodityNameList2 = invoiceCommodityNameList2;
}
/**
 * @return the invoiceCommodityNameList2
 */
public String getInvoiceCommodityNameList2() {
	return invoiceCommodityNameList2;
}
/**
 * @param invoiceCommodityNameList3 the invoiceCommodityNameList3 to set
 */
public void setInvoiceCommodityNameList3(String invoiceCommodityNameList3) {
	this.invoiceCommodityNameList3 = invoiceCommodityNameList3;
}
/**
 * @return the invoiceCommodityNameList3
 */
public String getInvoiceCommodityNameList3() {
	return invoiceCommodityNameList3;
}
/**
 * @param invoiceCommodityNameList4 the invoiceCommodityNameList4 to set
 */
public void setInvoiceCommodityNameList4(String invoiceCommodityNameList4) {
	this.invoiceCommodityNameList4 = invoiceCommodityNameList4;
}
/**
 * @return the invoiceCommodityNameList4
 */
public String getInvoiceCommodityNameList4() {
	return invoiceCommodityNameList4;
}
/**
 * @param invoiceCommodityNameList5 the invoiceCommodityNameList5 to set
 */
public void setInvoiceCommodityNameList5(String invoiceCommodityNameList5) {
	this.invoiceCommodityNameList5 = invoiceCommodityNameList5;
}
/**
 * @return the invoiceCommodityNameList5
 */
public String getInvoiceCommodityNameList5() {
	return invoiceCommodityNameList5;
}
/**
 * @param invoiceCommodityNameList6 the invoiceCommodityNameList6 to set
 */
public void setInvoiceCommodityNameList6(String invoiceCommodityNameList6) {
	this.invoiceCommodityNameList6 = invoiceCommodityNameList6;
}
/**
 * @return the invoiceCommodityNameList6
 */
public String getInvoiceCommodityNameList6() {
	return invoiceCommodityNameList6;
}
/**
 * @param invoiceCommodityNameList7 the invoiceCommodityNameList7 to set
 */
public void setInvoiceCommodityNameList7(String invoiceCommodityNameList7) {
	this.invoiceCommodityNameList7 = invoiceCommodityNameList7;
}
/**
 * @return the invoiceCommodityNameList7
 */
public String getInvoiceCommodityNameList7() {
	return invoiceCommodityNameList7;
}
/**
 * @param invoiceCommodityNameList8 the invoiceCommodityNameList8 to set
 */
public void setInvoiceCommodityNameList8(String invoiceCommodityNameList8) {
	this.invoiceCommodityNameList8 = invoiceCommodityNameList8;
}
/**
 * @return the invoiceCommodityNameList8
 */
public String getInvoiceCommodityNameList8() {
	return invoiceCommodityNameList8;
}
/**
 * @param invoiceCommodityNameList9 the invoiceCommodityNameList9 to set
 */
public void setInvoiceCommodityNameList9(String invoiceCommodityNameList9) {
	this.invoiceCommodityNameList9 = invoiceCommodityNameList9;
}
/**
 * @return the invoiceCommodityNameList9
 */
public String getInvoiceCommodityNameList9() {
	return invoiceCommodityNameList9;
}
/**
 * @param invoiceCommodityNameList10 the invoiceCommodityNameList10 to set
 */
public void setInvoiceCommodityNameList10(String invoiceCommodityNameList10) {
	this.invoiceCommodityNameList10 = invoiceCommodityNameList10;
}
/**
 * @return the invoiceCommodityNameList10
 */
public String getInvoiceCommodityNameList10() {
	return invoiceCommodityNameList10;
}
/**
 * @param invoiceCommodityNameList11 the invoiceCommodityNameList11 to set
 */
public void setInvoiceCommodityNameList11(String invoiceCommodityNameList11) {
	this.invoiceCommodityNameList11 = invoiceCommodityNameList11;
}
/**
 * @return the invoiceCommodityNameList11
 */
public String getInvoiceCommodityNameList11() {
	return invoiceCommodityNameList11;
}
/**
 * @param invoiceCommodityNameList12 the invoiceCommodityNameList12 to set
 */
public void setInvoiceCommodityNameList12(String invoiceCommodityNameList12) {
	this.invoiceCommodityNameList12 = invoiceCommodityNameList12;
}
/**
 * @return the invoiceCommodityNameList12
 */
public String getInvoiceCommodityNameList12() {
	return invoiceCommodityNameList12;
}
/**
 * @param invoiceCommodityNameList13 the invoiceCommodityNameList13 to set
 */
public void setInvoiceCommodityNameList13(String invoiceCommodityNameList13) {
	this.invoiceCommodityNameList13 = invoiceCommodityNameList13;
}
/**
 * @return the invoiceCommodityNameList13
 */
public String getInvoiceCommodityNameList13() {
	return invoiceCommodityNameList13;
}
/**
 * @param invoiceCommodityNameList15 the invoiceCommodityNameList15 to set
 */
public void setInvoiceCommodityNameList15(String invoiceCommodityNameList15) {
	this.invoiceCommodityNameList15 = invoiceCommodityNameList15;
}
/**
 * @return the invoiceCommodityNameList15
 */
public String getInvoiceCommodityNameList15() {
	return invoiceCommodityNameList15;
}
/**
 * @param invoiceCommodityNameList14 the invoiceCommodityNameList14 to set
 */
public void setInvoiceCommodityNameList14(String invoiceCommodityNameList14) {
	this.invoiceCommodityNameList14 = invoiceCommodityNameList14;
}
/**
 * @return the invoiceCommodityNameList14
 */
public String getInvoiceCommodityNameList14() {
	return invoiceCommodityNameList14;
}
/**
 * @param invoiceCommodityNameList16 the invoiceCommodityNameList16 to set
 */
public void setInvoiceCommodityNameList16(String invoiceCommodityNameList16) {
	this.invoiceCommodityNameList16 = invoiceCommodityNameList16;
}
/**
 * @return the invoiceCommodityNameList16
 */
public String getInvoiceCommodityNameList16() {
	return invoiceCommodityNameList16;
}
/**
 * @param invoiceCommodityNameListLocal the invoiceCommodityNameListLocal to set
 */
public void setInvoiceCommodityNameListLocal(
		List<NameValue> invoiceCommodityNameListLocal) {
	this.invoiceCommodityNameListLocal = invoiceCommodityNameListLocal;
}
/**
 * @param defaultLocale the defaultLocale to set
 */
public void setDefaultLocale(Locale defaultLocale) {
	this.defaultLocale = defaultLocale;
}
/**
 * @return the defaultLocale
 */
public Locale getDefaultLocale() {
	return 	defaultLocale;
}

/**
 * @return the invoiceCommodityNameList17
 */
public String getInvoiceCommodityNameList17() {
  return invoiceCommodityNameList17;
}

/**
 * @param invoiceCommodityNameList17 the invoiceCommodityNameList17 to set
 */
public void setInvoiceCommodityNameList17(String invoiceCommodityNameList17) {
  this.invoiceCommodityNameList17 = invoiceCommodityNameList17;
}

/**
 * @return the invoiceCommodityNameList18
 */
public String getInvoiceCommodityNameList18() {
  return invoiceCommodityNameList18;
}

/**
 * @param invoiceCommodityNameList18 the invoiceCommodityNameList18 to set
 */
public void setInvoiceCommodityNameList18(String invoiceCommodityNameList18) {
  this.invoiceCommodityNameList18 = invoiceCommodityNameList18;
}

}
