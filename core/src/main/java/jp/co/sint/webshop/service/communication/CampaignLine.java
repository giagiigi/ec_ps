package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.data.dto.CampaignMain;

public class CampaignLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//促销
	private CampaignMain campaignMain;
	//促销条件
	CampaignCondition  campaignCondition;
	
	//促销行为
	private CampaignDoings campaignDoings;
	
	private List<CampaignCondition> conditionList=new ArrayList<CampaignCondition>();

	public List<CampaignCondition> getConditionList() {
		return conditionList;
	}
	public void setConditionList(List<CampaignCondition> conditionList) {
		this.conditionList = conditionList;
	}
	public CampaignMain getCampaignMain() {
		return campaignMain;
	}
	public void setCampaignMain(CampaignMain campaignMain) {
		this.campaignMain = campaignMain;
	}
	
	public CampaignCondition getCampaignCondition() {
		return campaignCondition;
	}
	public void setCampaignCondition(CampaignCondition campaignCondition) {
		this.campaignCondition = campaignCondition;
	}
	public CampaignDoings getCampaignDoings() {
		return campaignDoings;
	}
	public void setCampaignDoings(CampaignDoings campaignDoings) {
		this.campaignDoings = campaignDoings;
	}
	
	
}
