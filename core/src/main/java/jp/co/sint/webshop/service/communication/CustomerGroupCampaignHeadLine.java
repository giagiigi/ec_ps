package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.attribute.Required;

public class CustomerGroupCampaignHeadLine implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  /** 店铺编号 */
  private String shopCode;

  /** 优惠活动编号 */
  @Required
  private String campaignCode;

  /** 优惠活动名称 */
  private String campaignName;
  /** 优惠活动名称 */
  private String campaignNameEn;
  /** 优惠活动名称 */
  private String campaignNameJp;
  /** 顾客组别编号 */
  private String customerGroupCode;
  
  /** 顾客组别编号 */
  private String customerGroupName;
  
  //20120522 tuxinwei add start
  /** 顾客组别编号(英文) */
  private String customerGroupNameEn;
  
  /** 顾客组别编号(日文) */
  private String customerGroupNameJp;
  //20120522 tuxinwei add end
  
  /** 优惠类型 */
  private Long campaignType;

  /** 优惠比率 */
  private Long campaignProportion;
  
  /** 优惠金额 */
  private BigDecimal campaignAmount;

  /** 优惠活动开始日时 */
  private Date campaignStartDatetime;

  /** 优惠活动结束日时 */
  private Date campaignEndDatetime;

  /** 活动适用最小购买金额 */
  private BigDecimal minOrderAmount;
  
  /** 限制使用次数 */
  private BigDecimal personalUseLimit;

  /**
   * @return 店铺编号
   */
  public String getShopCode() {
    return shopCode;
  }
  /**
   * @param ShopCode 设置店铺编号
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return 优惠活动编号
   */
  public String getCampaignCode() {
    return campaignCode;
  }
  /**
   * @param campaignCode 设置优惠活动编号
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * @return 优惠活动名称
   */
  public String getCampaignName() {
    return campaignName;
  }
  /**
   * @param CampaignName 设置优惠活动名称
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * @return 会员组别编号
   */
  public String getCustomerGroupCode() {
    return customerGroupCode;
  }
  
  /**
   * @param CustomerGroupCode 设置会员组别编号
   */
  public void setCustomerGroupCode(String customerGroupCode) {
    this.customerGroupCode = customerGroupCode;
  }
  
  /**
   * @return 会员组别名称
   */
  public String getCustomerGroupName() {
	return customerGroupName;
  }
  
  /**
   * @param CustomerGroupCode 设置会员组别名称
   */
  public void setCustomerGroupName(String customerGroupName) {
	this.customerGroupName = customerGroupName;
  }
  
/**
   * @return 优惠类型
   */
  public Long getCampaignType() {
    return campaignType;
  }
  /**
   * @param CampaignType 设置优惠类型
   */
  public void setCampaignType(Long campaignType) {
    this.campaignType = campaignType;
  }

  /**
   * @return 优惠比率
   */
  public Long getCampaignProportion() {
	return campaignProportion;
  }
  
  /**
   * @param campaignProportion 设置优惠比率
   */
  public void setCampaignProportion(Long campaignProportion) {
	this.campaignProportion = campaignProportion;
  }
  
  /**
   * @return 优惠金额
   */
  public BigDecimal getCampaignAmount() {
 	return campaignAmount;
  }
  
  /**
   * @param campaignAmount 设置优惠金额
   */
  public void setCampaignAmount(BigDecimal campaignAmount) {
	this.campaignAmount = campaignAmount;
  }
/**
   * @return 优惠活动开始日时
   */
  public Date getCampaignStartDatetime() {
    return campaignStartDatetime;
  }
  /**
   * @param CampaignStartDatetime 设置优惠活动开始日时
   */
  public void setCampaignStartDatetime(Date campaignStartDatetime) {
    this.campaignStartDatetime = campaignStartDatetime;
  }

  /**
   * @return 优惠活动结束日时
   */
  public Date getCampaignEndDatetime() {
    return campaignEndDatetime;
  }
  /**
   * @param CampaignEndDatetime 设置优惠活动结束日时
   */
  public void setCampaignEndDatetime(Date campaignEndDatetime) {
    this.campaignEndDatetime = campaignEndDatetime;
  }

  /**
   * @return 活动适用最小购买金额
   */
  public BigDecimal getMinOrderAmount() {
    return minOrderAmount;
  }
  /**
   * @param MinOrderAmount 设置活动适用最小购买金额
   */
  public void setMinOrderAmount(BigDecimal minOrderAmount) {
    this.minOrderAmount = minOrderAmount;
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

/**
 * @return the customerGroupNameEn
 */
public String getCustomerGroupNameEn() {
  return customerGroupNameEn;
}

/**
 * @param customerGroupNameEn the customerGroupNameEn to set
 */
public void setCustomerGroupNameEn(String customerGroupNameEn) {
  this.customerGroupNameEn = customerGroupNameEn;
}

/**
 * @return the customerGroupNameJp
 */
public String getCustomerGroupNameJp() {
  return customerGroupNameJp;
}

/**
 * @param customerGroupNameJp the customerGroupNameJp to set
 */
public void setCustomerGroupNameJp(String customerGroupNameJp) {
  this.customerGroupNameJp = customerGroupNameJp;
}

/**
 * @return the personalUseLimit
 */
public BigDecimal getPersonalUseLimit() {
  return personalUseLimit;
}

/**
 * @param personalUseLimit the personalUseLimit to set
 */
public void setPersonalUseLimit(BigDecimal personalUseLimit) {
  this.personalUseLimit = personalUseLimit;
}

}
