package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.text.*;
/**
 * 企划明细类别
 * 
 * @author System Integrator Corp.
 */
public class PlanDetailTypeValue implements Serializable {

  private static final long serialVersionUID = 1L;

  	//促销企划明细分类List
	private List<NameValue> salePlanDetailTypes = new ArrayList<NameValue>();
	
	//特集企划明细分类List
	private List<NameValue> featuredPlanDetailTypes = new ArrayList<NameValue>();

	/**
	 * @return the salePlanDetailTypes
	 */
	public List<NameValue> getSalePlanDetailTypes() {
		return salePlanDetailTypes;
	}

	/**
	 * @param salePlanDetailTypes the salePlanDetailTypes to set
	 */
	public void setSalePlanDetailTypes(List<String> salePlanDetailTypes) {
		if (salePlanDetailTypes != null && salePlanDetailTypes.size() > 0) {
			String strType = "";
			for (int i = 0; i < salePlanDetailTypes.size(); i++) {	
				if (i > 0) {
					strType = strType + "/" + salePlanDetailTypes.get(i);
				} else {
					strType = salePlanDetailTypes.get(i);
				}
			}
			this.salePlanDetailTypes = NameValue.asList(strType);
		}
		NameValue nameValue = new NameValue(Messages.getString("code.ConstantResource.0"),"");
		this.salePlanDetailTypes.add(0, nameValue);
	}

	/**
	 * @return the featuredPlanDetailTypes
	 */
	public List<NameValue> getFeaturedPlanDetailTypes() {
		return featuredPlanDetailTypes;
	}

	/**
	 * @param featuredPlanDetailTypes the featuredPlanDetailTypes to set
	 */
	public void setFeaturedPlanDetailTypes(List<String> featuredPlanDetailTypes) {
		if (featuredPlanDetailTypes != null && featuredPlanDetailTypes.size() > 0) {
			String strType = "";
			for (int i = 0; i < featuredPlanDetailTypes.size(); i++) {	
				if (i > 0) {
					strType = strType + "/" + featuredPlanDetailTypes.get(i);
				} else {
					strType = featuredPlanDetailTypes.get(i);
				}
			}
			this.featuredPlanDetailTypes = NameValue.asList(strType);
		}
		NameValue nameValue = new NameValue(Messages.getString("code.ConstantResource.0"),"");
		this.featuredPlanDetailTypes.add(0, nameValue);
	}

	/**
	 * 企划明细名称取得
	 * @return paymentTimesValue
	 *         お支払回数コード
	 */
	public String getPaymentTimesName(Long planType,String planDetailType) {
		if (PlanType.PROMOTION.longValue().equals(planType)) {
			if (!StringUtil.isNullOrEmpty(planDetailType) && this.salePlanDetailTypes != null) {
				for (int i = 0; i < salePlanDetailTypes.size(); i++){
					if (planDetailType.equals(salePlanDetailTypes.get(i).getValue())) {
						return salePlanDetailTypes.get(i).getName();
					}
				}
			}
		} else if (PlanType.FEATURE.longValue().equals(planType)) {
			if (!StringUtil.isNullOrEmpty(planDetailType) && this.featuredPlanDetailTypes != null) {
				for (int i = 0; i < featuredPlanDetailTypes.size(); i++){
					if (planDetailType.equals(featuredPlanDetailTypes.get(i).getValue())) {
						return featuredPlanDetailTypes.get(i).getName();
					}
				}
			}
		} 
		return "";
	}
  
}

