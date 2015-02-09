package jp.co.sint.webshop.service.communication;
 
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class PlanSearchCondition extends SearchCondition {

	/** uid */
	private static final long serialVersionUID = 1L;

	/** 企划编号 */
	private String planCode;

	/** 企划名称 */
	private String planName;
	// add by cs_yuli 20120515 start
	/** 企划英文名称 ***/
	private String planNameEn;
	/** 企划日文名称 ***/
	private String planNameJp;

	// add by cs_yuli 20120515 end
	/** 企划类别 */
	private String planType;

	/** 企划明细类别 */
	private String planDetailType;

	/** 企划开始期间From */
	private String planStartDateFrom;

	/** 企划开始期间To */
	private String planStartDateTo;

	/** 企划结束期间From */
	private String planEndDateFrom;

	/** 企划结束期间To */
	private String planEndDateTo;

	private String campaignStatus;

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getPlanDetailType() {
		return planDetailType;
	}

	public void setPlanDetailType(String planDetailType) {
		this.planDetailType = planDetailType;
	}

	public String getPlanStartDateFrom() {
		return planStartDateFrom;
	}

	public void setPlanStartDateFrom(String planStartDateFrom) {
		this.planStartDateFrom = planStartDateFrom;
	}

	public String getPlanStartDateTo() {
		return planStartDateTo;
	}

	public void setPlanStartDateTo(String planStartDateTo) {
		this.planStartDateTo = planStartDateTo;
	}

	public String getPlanEndDateFrom() {
		return planEndDateFrom;
	}

	public void setPlanEndDateFrom(String planEndDateFrom) {
		this.planEndDateFrom = planEndDateFrom;
	}

	public String getPlanEndDateTo() {
		return planEndDateTo;
	}

	public void setPlanEndDateTo(String planEndDateTo) {
		this.planEndDateTo = planEndDateTo;
	}

	public String getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public boolean isValid() {

		boolean result = true;

		String startDateFrom = getPlanStartDateFrom();
		String startDateTo = getPlanStartDateTo();
		String endDateFrom = getPlanEndDateFrom();
		String endDateTo = getPlanEndDateTo();

		if (StringUtil.hasValueAllOf(startDateFrom, startDateTo)) {
			result &= StringUtil.isCorrectRange(startDateFrom, startDateTo);
		}
		if (StringUtil.hasValueAllOf(endDateFrom, endDateTo)) {
			result &= StringUtil.isCorrectRange(endDateFrom, endDateTo);
		}
		return result;

	}

	/**
	 * @param planNameEn the planNameEn to set
	 */
	public void setPlanNameEn(String planNameEn) {
		this.planNameEn = planNameEn;
	}

	/**
	 * @return the planNameEn
	 */
	public String getPlanNameEn() {
		return planNameEn;
	}

	/**
	 * @param planNameJp the planNameJp to set
	 */
	public void setPlanNameJp(String planNameJp) {
		this.planNameJp = planNameJp;
	}

	/**
	 * @return the planNameJp
	 */
	public String getPlanNameJp() {
		return planNameJp;
	}

}
