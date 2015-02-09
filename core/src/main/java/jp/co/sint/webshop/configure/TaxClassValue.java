package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;

/**
 * 税率区分
 * 
 * @author System Integrator Corp.
 */
public class TaxClassValue implements Serializable {

  private static final long serialVersionUID = 1L;

  //税率区分
	private List<NameValue> taxClass = new ArrayList<NameValue>();
	

	/**
	 * @return the taxClass
	 */
	public List<NameValue> getTaxClass() {
		return taxClass;
	}

	/**
	 * @param taxClass the taxClass to set
	 */
	public void setTaxClass(List<String> taxClass) {
		if (taxClass != null && taxClass.size() > 0) {
			String strTaxClass = "";
			for (int i = 0; i < taxClass.size(); i++) {	
				if (i > 0) {
				  strTaxClass = strTaxClass + "/" + taxClass.get(i);
				} else {
				  strTaxClass = taxClass.get(i);
				}
			}
			this.taxClass = NameValue.asList(strTaxClass);
		}
		NameValue nameValue = new NameValue("请选择", "");
		this.taxClass.add(0, nameValue);
	}
}

