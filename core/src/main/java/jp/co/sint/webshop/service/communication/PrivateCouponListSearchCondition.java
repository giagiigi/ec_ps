package jp.co.sint.webshop.service.communication;
 
import jp.co.sint.webshop.service.SearchCondition;

public class PrivateCouponListSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

    /** 优惠券规则编号 */
	private String searchCouponCode;

	/** 优惠券规则名称 */
	private String searchCouponName;

	// add by cs_yuli 20120515 start
	/** 优惠券规则英文名称 */ 
	private String searchCouponNameEn;

	/** 优惠券规则日文名称 */ 
	private String searchCouponNameJp;
	// add by cs_yuli 20120515 end
	/** 优惠券类别 */
	private String searchCouponType;

	/** 优惠券发行类别 */
	private String searchCampaignType;

	/** 优惠券发行开始日时(From) */
	private String searchMinIssueStartDatetimeFrom;

	/** 优惠券发行开始日时(To) */
	private String searchMinIssueStartDatetimeTo;

	/** 优惠券发行结束日时(From) */
	private String searchMinIssueEndDatetimeFrom;

	/** 优惠券发行结束日时(To) */
	private String searchMinIssueEndDatetimeTo;

	/** 优惠券利用开始日时(From) */
	private String searchMinUseStartDatetimeFrom;

	/** 优惠券利用开始日时(To) */
	private String searchMinUseStartDatetimeTo;

	/** 优惠券利用结束日时(From) */
	private String searchMinUseEndDatetimeFrom;

	/** 优惠券利用结束日时(To) */
	private String searchMinUseEndDatetimeTo;
	
	/** 发行状态 */
	private String searchCouponActivityStatus;

	/**
	 * searchCouponCodeを取得します。
	 * 
	 * @return searchCouponCode
	 */
	public String getSearchCouponCode() {
		return searchCouponCode;
	}

	/**
	 * searchCouponCodeを設定します。
	 * 
	 * @param searchCouponCode 
	 * 		searchCouponCode
	 */
	public void setSearchCouponCode(String searchCouponCode) {
		this.searchCouponCode = searchCouponCode;
	}

	/**
	 * searchCouponNameを取得します。
	 * 
	 * @return searchCouponName
	 */
	public String getSearchCouponName() {
		return searchCouponName;
	}

	/**
	 * searchCouponNameを設定します。
	 * 
	 * @param searchCouponName 
	 * 		searchCouponName
	 */
	public void setSearchCouponName(String searchCouponName) {
		this.searchCouponName = searchCouponName;
	}

	/**
	 * searchCouponTypeを取得します。
	 * 
	 * @return searchCouponType
	 */
	public String getSearchCouponType() {
		return searchCouponType;
	}

	/**
	 * searchCouponTypeを設定します。
	 * 
	 * @param searchCouponType 
	 * 		searchCouponType
	 */
	public void setSearchCouponType(String searchCouponType) {
		this.searchCouponType = searchCouponType;
	}

	/**
	 * searchCampaignTypeを取得します。
	 * 
	 * @return searchCampaignType
	 */
	public String getSearchCampaignType() {
		return searchCampaignType;
	}

	/**
	 * searchCampaignTypeを設定します。
	 * 
	 * @param searchCampaignType 
	 * 		searchCampaignType
	 */
	public void setSearchCampaignType(String searchCampaignType) {
		this.searchCampaignType = searchCampaignType;
	}

	/**
	 * searchMinIssueStartDatetimeFromを取得します。
	 * 
	 * @return searchMinIssueStartDatetimeFrom
	 */
	public String getSearchMinIssueStartDatetimeFrom() {
		return searchMinIssueStartDatetimeFrom;
	}

	/**
	 * searchMinIssueStartDatetimeFromを設定します。
	 * 
	 * @param searchMinIssueStartDatetimeFrom 
	 * 		searchMinIssueStartDatetimeFrom
	 */
	public void setSearchMinIssueStartDatetimeFrom(
			String searchMinIssueStartDatetimeFrom) {
		this.searchMinIssueStartDatetimeFrom = searchMinIssueStartDatetimeFrom;
	}

	/**
	 * searchMinIssueStartDatetimeToを取得します。
	 * 
	 * @return searchMinIssueStartDatetimeTo
	 */
	public String getSearchMinIssueStartDatetimeTo() {
		return searchMinIssueStartDatetimeTo;
	}

	/**
	 * searchMinIssueStartDatetimeToを設定します。
	 * 
	 * @param searchMinIssueStartDatetimeTo 
	 * 		searchMinIssueStartDatetimeTo
	 */
	public void setSearchMinIssueStartDatetimeTo(
			String searchMinIssueStartDatetimeTo) {
		this.searchMinIssueStartDatetimeTo = searchMinIssueStartDatetimeTo;
	}

	/**
	 * searchMinIssueEndDatetimeFromを取得します。
	 * 
	 * @return searchMinIssueEndDatetimeFrom
	 */
	public String getSearchMinIssueEndDatetimeFrom() {
		return searchMinIssueEndDatetimeFrom;
	}

	/**
	 * searchMinIssueEndDatetimeFromを設定します。
	 * 
	 * @param searchMinIssueEndDatetimeFrom 
	 * 		searchMinIssueEndDatetimeFrom
	 */
	public void setSearchMinIssueEndDatetimeFrom(
			String searchMinIssueEndDatetimeFrom) {
		this.searchMinIssueEndDatetimeFrom = searchMinIssueEndDatetimeFrom;
	}

	/**
	 * searchMinIssueEndDatetimeToを取得します。
	 * 
	 * @return searchMinIssueEndDatetimeTo
	 */
	public String getSearchMinIssueEndDatetimeTo() {
		return searchMinIssueEndDatetimeTo;
	}

	/**
	 * searchMinIssueEndDatetimeToを設定します。
	 * 
	 * @param searchMinIssueEndDatetimeTo 
	 * 		searchMinIssueEndDatetimeTo
	 */
	public void setSearchMinIssueEndDatetimeTo(String searchMinIssueEndDatetimeTo) {
		this.searchMinIssueEndDatetimeTo = searchMinIssueEndDatetimeTo;
	}

	/**
	 * searchMinUseStartDatetimeFromを取得します。
	 * 
	 * @return searchMinUseStartDatetimeFrom
	 */
	public String getSearchMinUseStartDatetimeFrom() {
		return searchMinUseStartDatetimeFrom;
	}

	/**
	 * searchMinUseStartDatetimeFromを設定します。
	 * 
	 * @param searchMinUseStartDatetimeFrom 
	 * 		searchMinUseStartDatetimeFrom
	 */
	public void setSearchMinUseStartDatetimeFrom(
			String searchMinUseStartDatetimeFrom) {
		this.searchMinUseStartDatetimeFrom = searchMinUseStartDatetimeFrom;
	}

	/**
	 * searchMinUseStartDatetimeToを取得します。
	 * 
	 * @return searchMinUseStartDatetimeTo
	 */
	public String getSearchMinUseStartDatetimeTo() {
		return searchMinUseStartDatetimeTo;
	}

	/**
	 * searchMinUseStartDatetimeToを設定します。
	 * 
	 * @param searchMinUseStartDatetimeTo 
	 * 		searchMinUseStartDatetimeTo
	 */
	public void setSearchMinUseStartDatetimeTo(String searchMinUseStartDatetimeTo) {
		this.searchMinUseStartDatetimeTo = searchMinUseStartDatetimeTo;
	}

	/**
	 * searchMinUseEndDatetimeFromを取得します。
	 * 
	 * @return searchMinUseEndDatetimeFrom
	 */
	public String getSearchMinUseEndDatetimeFrom() {
		return searchMinUseEndDatetimeFrom;
	}

	/**
	 * searchMinUseEndDatetimeFromを設定します。
	 * 
	 * @param searchMinUseEndDatetimeFrom 
	 * 		searchMinUseEndDatetimeFrom
	 */
	public void setSearchMinUseEndDatetimeFrom(String searchMinUseEndDatetimeFrom) {
		this.searchMinUseEndDatetimeFrom = searchMinUseEndDatetimeFrom;
	}

	/**
	 * searchMinUseEndDatetimeToを取得します。
	 * 
	 * @return searchMinUseEndDatetimeTo
	 */
	public String getSearchMinUseEndDatetimeTo() {
		return searchMinUseEndDatetimeTo;
	}

	/**
	 * searchMinUseEndDatetimeToを設定します。
	 * 
	 * @param searchMinUseEndDatetimeTo 
	 * 		searchMinUseEndDatetimeTo
	 */
	public void setSearchMinUseEndDatetimeTo(String searchMinUseEndDatetimeTo) {
		this.searchMinUseEndDatetimeTo = searchMinUseEndDatetimeTo;
	}

	/**
	 * searchCouponActivityStatusを取得します。
	 * 
	 * @return searchCouponActivityStatus
	 */
	public String getSearchCouponActivityStatus() {
		return searchCouponActivityStatus;
	}

	/**
	 * searchCouponActivityStatusを設定します。
	 * 
	 * @param searchCouponActivityStatus 
	 * 		searchCouponActivityStatus
	 */
	public void setSearchCouponActivityStatus(String searchCouponActivityStatus) {
		this.searchCouponActivityStatus = searchCouponActivityStatus;
	}

	/**
	 * @param searchCouponNameEn the searchCouponNameEn to set
	 */
	public void setSearchCouponNameEn(String searchCouponNameEn) {
		this.searchCouponNameEn = searchCouponNameEn;
	}

	/**
	 * @return the searchCouponNameEn
	 */
	public String getSearchCouponNameEn() {
		return searchCouponNameEn;
	}

	/**
	 * @param searchCouponNameJp the searchCouponNameJp to set
	 */
	public void setSearchCouponNameJp(String searchCouponNameJp) {
		this.searchCouponNameJp = searchCouponNameJp;
	}

	/**
	 * @return the searchCouponNameJp
	 */
	public String getSearchCouponNameJp() {
		return searchCouponNameJp;
	}
}
