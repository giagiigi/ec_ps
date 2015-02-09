package jp.co.sint.webshop.service.communication;

import java.io.Serializable;

public class PlanDetailHeadLine implements Serializable {

	/** uid */
	private static final long serialVersionUID = 1L;

	private String detailCode;
	
	private String detailType;

	private String detailName;

	private String showCommodityCount;

	private String displayOrder;
	
	private String detailUrl;

	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getShowCommodityCount() {
		return showCommodityCount;
	}

	public void setShowCommodityCount(String showCommodityCount) {
		this.showCommodityCount = showCommodityCount;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
	

}
