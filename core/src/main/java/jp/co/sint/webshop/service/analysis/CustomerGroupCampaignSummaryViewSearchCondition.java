package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class CustomerGroupCampaignSummaryViewSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;
  
  /** 店铺编号 */
  private String shopCode;

  /** 优惠活动编号 */
  private String campaignCode;

  /** 优惠活动名称 */
  private String campaignName;

  /** 优惠活动名称 */
  private String campaignNameEn;

  /** 优惠活动名称 */
  private String campaignNameJp;
  
  /** 顾客组别 */
  private String customerGroupCode;
  
  /** 优惠种别 */
  private String CampaignType;

  /** 优惠开始期间From */
  private String campaignStartDateFrom;
  
  /** 优惠开始期间To */
  private String campaignStartDateTo;
  
  /** 优惠结束期间From */
  private String campaignEndDateFrom;

  /** 优惠结束期间To */
  private String campaignEndDateTo;
  
  /** 活动状态 */
  private String campaignStatus;
  
  public String getShopCode() {
	return shopCode;
  }

  public void setShopCode(String shopCode) {
	this.shopCode = shopCode;
  }

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

  public String getCustomerGroupCode() {
	return customerGroupCode;
  }

  public void setCustomerGroupCode(String customerGroupCode) {
	this.customerGroupCode = customerGroupCode;
  }

public String getCampaignType() {
	return CampaignType;
  }

  public void setCampaignType(String campaignType) {
	CampaignType = campaignType;
  }

  public String getCampaignStartDateFrom() {
	return campaignStartDateFrom;
  }

  public void setCampaignStartDateFrom(String campaignStartDateFrom) {
	this.campaignStartDateFrom = campaignStartDateFrom;
  }

  public String getCampaignStartDateTo() {
	return campaignStartDateTo;
  }

  public void setCampaignStartDateTo(String campaignStartDateTo) {
	this.campaignStartDateTo = campaignStartDateTo;
  }

  public String getCampaignEndDateFrom() {
	return campaignEndDateFrom;
  }

  public void setCampaignEndDateFrom(String campaignEndDateFrom) {
	this.campaignEndDateFrom = campaignEndDateFrom;
  }

  public String getCampaignEndDateTo() {
	return campaignEndDateTo;
  }

  public void setCampaignEndDateTo(String campaignEndDateTo) {
	this.campaignEndDateTo = campaignEndDateTo;
  }

  
  public String getCampaignStatus() {
	return campaignStatus;
  }

  public void setCampaignStatus(String campaignStatus) {
	this.campaignStatus = campaignStatus;
  }

public boolean isValid() {

    boolean result = true;

    String startDateFrom = getCampaignStartDateFrom();
    String startDateTo = getCampaignStartDateTo();
    String endDateFrom = getCampaignEndDateFrom();
    String endDateTo = getCampaignEndDateTo();

    if (StringUtil.hasValueAllOf(startDateFrom, startDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, startDateTo);
    }
    if (StringUtil.hasValueAllOf(endDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(endDateFrom, endDateTo);
    }
    return result;

  }

/**
 * @param campaignNameEn the campaignNameEn to set
 */
public void setCampaignNameEn(String campaignNameEn) {
	this.campaignNameEn = campaignNameEn;
}

/**
 * @return the campaignNameEn
 */
public String getCampaignNameEn() {
	return campaignNameEn;
}

/**
 * @param campaignNameJp the campaignNameJp to set
 */
public void setCampaignNameJp(String campaignNameJp) {
	this.campaignNameJp = campaignNameJp;
}

/**
 * @return the campaignNameJp
 */
public String getCampaignNameJp() {
	return campaignNameJp;
}

}
