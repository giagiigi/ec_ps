package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class PlanSummaryViewSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  /** 店铺编号 */
  private String shopCode;

    @AlphaNum2
	@Length(16)
	@Metadata(name = "企划编号")
	private String searchPlanCode;

	@Length(40)
	@Metadata(name = "企划名称")
	@Digit()
	private String searchPlanName;
	
	@Metadata(name = "企划分类")
	private String searchPlanDetailType;

	@Digit()
	@Length(1)
	@Metadata(name = "订单类别")
	private String searchOrderType;

	@Digit()
	@Length(1)
	@Metadata(name = "统计类别")
	private String searchStatisticsType;


	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划开始日时From")
	private String searchStartDateFrom;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划开始日时To")
	private String searchStartDateTo;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划结束日时From")
	private String searchEndDateFrom;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划结束日时To")
	private String searchEndDateTo;

	  
	  /** 企划类型模式 */
	private String planTypeMode;
	/***
	 * 取得企划类型模式
	 * @return planTypeMode
	 */
	public String getPlanTypeMode() {
		return planTypeMode;
	}
	/**
	 * 设定企划类型模式
	 * @param planTypeMode planTypeMode
	 */
	public void setPlanTypeMode(String planTypeMode) {
		this.planTypeMode = planTypeMode;
	}
	/**
	 * 取得shopCode
	 * @return shopCode
	 */
	public String getShopCode() {
		return shopCode;
	}
	/**
	 * 设定shopCode
	 * @param shopCode
	 */
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	/**
	 * 取得企划编号
	 * @return searchPlanCode
	 */
	public String getSearchPlanCode() {
		return searchPlanCode;
	}
	/**
	 * 设定企划编号
	 * @param searchPlanCode searchPlanCode
	 */
	public void setSearchPlanCode(String searchPlanCode) {
		this.searchPlanCode = searchPlanCode;
	}
	/**
	 * 取得企划名称
	 * @return searchPlanCode
	 */
	public String getSearchPlanName() {
		return searchPlanName;
	}
	/**
	 * 设定企划名称
	 * @param searchPlanName searchPlanName
	 */
	public void setSearchPlanName(String searchPlanName) {
		this.searchPlanName = searchPlanName;
	}
	/**
	 * 取得企划分类
	 * @return searchPlanDetailType
	 */
	public String getSearchPlanDetailType() {
		return searchPlanDetailType;
	}
	/**
	 * 设定企划分类
	 * @param searchPlanDetailType String
	 */
	public void setSearchPlanDetailType(String searchPlanDetailType) {
		this.searchPlanDetailType = searchPlanDetailType;
	}
	/**
	 * 取得订单类别
	 * @return searchPlanType
	 */
	public String getSearchOrderType() {
		return searchOrderType;
	}
	/**
	 * 设定订单类别
	 * @param searchOrderType searchOrderType
	 */
	public void setSearchOrderType(String searchOrderType) {
		this.searchOrderType = searchOrderType;
	}
	/**
	 * 取得统计类别
	 * @return searchStatisticsType
	 */
	public String getSearchStatisticsType() {
		return searchStatisticsType;
	}
	/**
	 * 设定统计类别
	 * @param searchStatisticsType searchStatisticsType
	 */
	public void setSearchStatisticsType(String searchStatisticsType) {
		this.searchStatisticsType = searchStatisticsType;
	}
	/**
	 * 取得企划开始时间From
	 * @return searchStartDateFrom
	 */
	public String getSearchStartDateFrom() {
		return searchStartDateFrom;
	}
	/**
	 * 设定企划开始时间From
	 * @param searchStartDateFrom searchStartDateFrom
	 */
	public void setSearchStartDateFrom(String searchStartDateFrom) {
		this.searchStartDateFrom = searchStartDateFrom;
	}
	/**
	 * 取得企划开始时间To
	 * @return searchStartDateTo
	 */
	public String getSearchStartDateTo() {
		return searchStartDateTo;
	}
	/**
	 * 设定企划开始时间To
	 * @param searchStartDateTo searchStartDateTo
	 */
	public void setSearchStartDateTo(String searchStartDateTo) {
		this.searchStartDateTo = searchStartDateTo;
	}
	/**
	 * 取得企划结束时间From
	 * @return searchEndDateFrom
	 */
	public String getSearchEndDateFrom() {
		return searchEndDateFrom;
	}
	/**
	 * 设定企划结束时间From
	 * @param searchEndDateFrom searchEndDateFrom
	 */
	public void setSearchEndDateFrom(String searchEndDateFrom) {
		this.searchEndDateFrom = searchEndDateFrom;
	}
	/**
	 * 取得企划结束时间To
	 * @return searchEndDateTo
	 */
	public String getSearchEndDateTo() {
		return searchEndDateTo;
	}
	/**
	 * 设定企划结束时间To
	 * @param searchEndDateTo searchEndDateTo
	 */
	public void setSearchEndDateTo(String searchEndDateTo) {
		this.searchEndDateTo = searchEndDateTo;
	}
	
	public boolean isValid() {
		boolean result = true;
		String startDateFrom = getSearchStartDateFrom();
		String startDateTo = getSearchStartDateTo();
		String endDateFrom = getSearchEndDateFrom();
		String endDateTo = getSearchEndDateTo();
		if (StringUtil.hasValueAllOf(startDateFrom, startDateTo)) {
			result &= StringUtil.isCorrectRange(startDateFrom, startDateTo);
		}
		if (StringUtil.hasValueAllOf(endDateFrom, endDateTo)) {
			result &= StringUtil.isCorrectRange(endDateFrom, endDateTo);
		}
		return result;

	}

}
