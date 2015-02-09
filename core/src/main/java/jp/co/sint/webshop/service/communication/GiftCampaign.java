package jp.co.sint.webshop.service.communication;

import java.io.Serializable;


public class GiftCampaign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String attributrValue;
	
	private String campaignCode;
	
	
	public String getAttributrValue() {
		return attributrValue;
	}
	public void setAttributrValue(String attributrValue) {
		this.attributrValue = attributrValue;
	}
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	
}
