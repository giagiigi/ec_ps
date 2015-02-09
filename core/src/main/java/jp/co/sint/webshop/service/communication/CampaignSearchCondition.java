package jp.co.sint.webshop.service.communication;
 
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class CampaignSearchCondition extends SearchCondition {

	/** uid */
	private static final long serialVersionUID = 1L;

	/** 活动编号 */
	private String campaignCode;

	/** 活动名称 */
	private String campaignName;

	/** 活动英文名称 */
	private String campaignNameEn;
	
	/** 活动日文名称 */
	private String campaignNameJp;
    
	/** 活动类型 */
	private String campaignType;

	/** 活动开始时间(From) */
	private String campaignStartDateFrom;

	/** 活动结束时间(To) */
	private String campaignEndDateTo;
	
	/** 活动进行状态 */
	private String searchCampaignStatus;

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCampaignNameEn() {
		return campaignNameEn;
	}
	
	public void setCampaignNameEn(String campaignNameEn) {
		this.campaignNameEn = campaignNameEn;
	}

	public String getCampaignNameJp() {
		return campaignNameJp;
	}

	public void setCampaignNameJp(String campaignNameJp) {
		this.campaignNameJp = campaignNameJp;
	}

	public String getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	public String getCampaignStartDateFrom() {
		return campaignStartDateFrom;
	}

	public void setCampaignStartDateFrom(String campaignStartDateFrom) {
		this.campaignStartDateFrom = campaignStartDateFrom;
	}

	public String getCampaignEndDateTo() {
		return campaignEndDateTo;
	}

	public void setCampaignEndDateTo(String campaignEndDateTo) {
		this.campaignEndDateTo = campaignEndDateTo;
	}

	public String getSearchCampaignStatus() {
		return searchCampaignStatus;
	}

	public void setSearchCampaignStatus(String searchCampaignStatus) {
		this.searchCampaignStatus = searchCampaignStatus;
	}

  //活动期间检查
	public boolean isValid() {

		boolean result = true;

		String startDateFrom = getCampaignStartDateFrom();
		
		String endDateTo = getCampaignEndDateTo();

		if (StringUtil.hasValueAllOf(startDateFrom, endDateTo)) {
			result &= StringUtil.isCorrectRange(startDateFrom, endDateTo);
		}
		return result;

	}

}
