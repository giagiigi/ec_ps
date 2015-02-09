package jp.co.sint.webshop.web.bean.back.communication;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.CampaignType; 
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050910:顾客组别优惠设定登录/更新のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 顾客组别列表 */
  private List<CodeAttribute> customerGroupList = new ArrayList<CodeAttribute>();
  

  private Date updateDatetime;
  
  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "活动编号", order = 1)
  private String customerGroupCampaignCode;
  
  @Required
  @Length(40)
  @Metadata(name = "活动名称", order = 2)
  private String customerGroupCampaignName;
  @Required
  @Length(40)
  @Metadata(name = "活动名称(英文)")
  private String customerGroupCampaignNameEn; 
  @Required
  @Length(40)
  @Metadata(name = "活动名称(日文)")
  private String customerGroupCampaignNameJp;
  @Length(16)
  @Metadata(name = "顾客组别", order = 3)
  private String customerGroupCode;
  
  @Required
  @Domain(CampaignType.class)
  @Metadata(name = "折扣类型", order = 4)
  private String campaignType;
  
  @Required
  @Length(3)
  @Digit
  @Range(min = 0, max = 100)
  @Metadata(name = "折扣比例", order = 5)
  private String rate;
  
  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "折扣金额", order = 6)
  private String campaignNumber;
  
  @Required
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "活动开始时间", order = 7)
  private String dateFrom;
  
  @Required
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "活动结束时间", order = 8)
  private String dateTo;
  
  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "最小购买金额", order = 9)
  private String minOrderAmount;
  
  @Length(200)
  @Metadata(name = "备注", order = 10)
  private String remarks;
  
  @Required
  @Length(8)
  @Digit
  @Range(min = 1, max = 99999999)
  @Metadata(name = "限制使用次数", order = 11)
  private String personalUseLimit;
  
  private String displayMode;
  
  private String mode;
  
  private Boolean updateAuthorizeFlg;

  public String getCustomerGroupCampaignCode() {
	return customerGroupCampaignCode;
  }

  public void setCustomerGroupCampaignCode(String customerGroupCampaignCode) {
	this.customerGroupCampaignCode = customerGroupCampaignCode;
  }

  public String getCustomerGroupCampaignName() {
	return customerGroupCampaignName;
  }

  public void setCustomerGroupCampaignName(String customerGroupCampaignName) {
	this.customerGroupCampaignName = customerGroupCampaignName;
  }

  public String getCustomerGroupCode() {
	return customerGroupCode;
  }

  public void setCustomerGroupCode(String customerGroupCode) {
	this.customerGroupCode = customerGroupCode;
  }

  public String getRate() {
	return rate;
  }

  public void setRate(String rate) {
 	this.rate = rate;
  }

  public String getCampaignNumber() {
	return campaignNumber;
  }

  public void setCampaignNumber(String campaignNumber) {
	this.campaignNumber = campaignNumber;
  }

  public String getCampaignType() {
	return campaignType;
  }

  public void setCampaignType(String campaignType) {
 	this.campaignType = campaignType;
  }

  public String getDateFrom() {
	return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
	this.dateFrom = dateFrom;
  }

  public String getDateTo() {
	return dateTo;
  }

  public void setDateTo(String dateTo) {
	this.dateTo = dateTo;
  }

  public String getMinOrderAmount() {
	return minOrderAmount;
  }

  public void setMinOrderAmount(String minOrderAmount) {
	this.minOrderAmount = minOrderAmount;
  }

  public String getRemarks() {
	return remarks;
  }

   public void setRemarks(String remarks) {
	this.remarks = remarks;
  }

  public List<CodeAttribute> getCustomerGroupList() {
	return customerGroupList;
  }

  public void setCustomerGroupList(List<CodeAttribute> customerGroupList) {
	this.customerGroupList = customerGroupList;
  }

  public String getDisplayMode() {
	return displayMode;
  }

  public void setDisplayMode(String displayMode) {
	this.displayMode = displayMode;
  }
  
  public String getMode() {
	return mode;
  }

  public void setMode(String mode) {
	this.mode = mode;
  }

  public Boolean getUpdateAuthorizeFlg() {
	return updateAuthorizeFlg;
  }

  public void setUpdateAuthorizeFlg(Boolean updateAuthorizeFlg) {
	this.updateAuthorizeFlg = updateAuthorizeFlg;
  }

  /**
   * updateDatetimeを取得します。
   * 
   * @return updateDatetime
   */
  public Date getUpdateDatetime() {
    return DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * updateDatetimeを設定します。
   * 
   * @param updateDatetime
   *          設定する updateDatetime
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
	if (WebConstantCode.DISPLAY_EDIT.equals(displayMode)) {
	  setCustomerGroupCampaignCode(reqparam.get("customerGroupCampaignCode"));
	}
	setCustomerGroupCampaignName(reqparam.get("customerGroupCampaignName"));
	setCustomerGroupCampaignNameEn(reqparam.get("customerGroupCampaignNameEn"));
	setCustomerGroupCampaignNameJp(reqparam.get("customerGroupCampaignNameJp"));
	setDateFrom(reqparam.getDateTimeString("dateFrom"));
	setDateTo(reqparam.getDateTimeString("dateTo"));
	setCustomerGroupCode(reqparam.get("customerGroupCode"));
	setCampaignType(reqparam.get("campaignType"));
	setMinOrderAmount(reqparam.get("minOrderAmount"));
	setRate(reqparam.get("rate"));
	setCampaignNumber(reqparam.get("campaignNumber"));
	setRemarks(reqparam.get("remarks"));
	setPersonalUseLimit(reqparam.get("personalUseLimit"));
  }
  
  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060520";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return  Messages.getString("web.bean.back.communication.CustomerGroupCampaignEditBean.0");
  }

  @Override
  public void setSubJspId() {
	
  }

/**
 * @param customerGroupCampaignNameEn the customerGroupCampaignNameEn to set
 */
public void setCustomerGroupCampaignNameEn(String customerGroupCampaignNameEn) {
	this.customerGroupCampaignNameEn = customerGroupCampaignNameEn;
}

/**
 * @return the customerGroupCampaignNameEn
 */
public String getCustomerGroupCampaignNameEn() {
	return customerGroupCampaignNameEn;
}

/**
 * @param customerGroupCampaignNameJp the customerGroupCampaignNameJp to set
 */
public void setCustomerGroupCampaignNameJp(String customerGroupCampaignNameJp) {
	this.customerGroupCampaignNameJp = customerGroupCampaignNameJp;
}

/**
 * @return the customerGroupCampaignNameJp
 */
public String getCustomerGroupCampaignNameJp() {
	return customerGroupCampaignNameJp;
}


/**
 * @return the personalUseLimit
 */
public String getPersonalUseLimit() {
  return personalUseLimit;
}


/**
 * @param personalUseLimit the personalUseLimit to set
 */
public void setPersonalUseLimit(String personalUseLimit) {
  this.personalUseLimit = personalUseLimit;
}


}
