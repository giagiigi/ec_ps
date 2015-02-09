//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.service.campain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignDoings;



/**
 * 「キャンペーン(CAMPAIGN)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 * 
 */
public class CampaignInfo implements Serializable{

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** 电话号码 */
	private String mobileNumber;
	
	/** 顾客编码 */
	private String customerCode;

	/** 手机号码 */
	private String phoneNumber;
	
	/** 顾客姓名 */
	private String lastName;

  /** 省 */
	private String address1;

  /** 市 */
	private String address2;

  /** 区 */
	private String address3;

  /** 详细地址 */
	private String address4;
	
	/** 省份代码 */
	private String prefectureCode;
	
	/** 订单商品详细 */
	private List<OrderDetail> commodityList = new ArrayList<OrderDetail>();

  private String orderNo;
  
  // 2012/11/20 促销活动 ob add start
  private CampaignMain campaignMain;
  
  private List<CampaignCondition> conditionList;
  
  private CampaignDoings campaignDoings;
  
  private Date orderDateTime;
  // 2012/11/20 促销活动 ob add end
  /**
   * @return the mobileNumber
   */
  public String getMobileNumber() {
    return mobileNumber;
  }

  
  /**
   * @param mobileNumber the mobileNumber to set
   */
  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  
  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  
  /**
   * @param phoneNumber the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  
  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  
  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  
  /**
   * @return the address1
   */
  public String getAddress1() {
    return address1;
  }

  
  /**
   * @param address1 the address1 to set
   */
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  
  /**
   * @return the address2
   */
  public String getAddress2() {
    return address2;
  }

  
  /**
   * @param address2 the address2 to set
   */
  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  
  /**
   * @return the address3
   */
  public String getAddress3() {
    return address3;
  }

  
  /**
   * @param address3 the address3 to set
   */
  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  
  /**
   * @return the address4
   */
  public String getAddress4() {
    return address4;
  }

  
  /**
   * @param address4 the address4 to set
   */
  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  
  /**
   * @return the prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  
  /**
   * @param prefectureCode the prefectureCode to set
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  
  /**
   * @return the commodityList
   */
  public List<OrderDetail> getCommodityList() {
    return commodityList;
  }

  
  /**
   * @param commodityList the commodityList to set
   */
  public void setCommodityList(List<OrderDetail> commodityList) {
    this.commodityList = commodityList;
  }


  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }


  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }


  
  public CampaignMain getCampaignMain() {
    return campaignMain;
  }


  
  public void setCampaignMain(CampaignMain campaignMain) {
    this.campaignMain = campaignMain;
  }


  
  public List<CampaignCondition> getConditionList() {
    return conditionList;
  }


  
  public void setConditionList(List<CampaignCondition> conditionList) {
    this.conditionList = conditionList;
  }


  
  public CampaignDoings getCampaignDoings() {
    return campaignDoings;
  }


  
  public void setCampaignDoings(CampaignDoings campaignDoings) {
    this.campaignDoings = campaignDoings;
  }


public Date getOrderDateTime() {
	return orderDateTime;
}


public void setOrderDateTime(Date orderDateTime) {
	this.orderDateTime = orderDateTime;
}



/**
 * @return the customerCode
 */
public String getCustomerCode() {
  return customerCode;
}



/**
 * @param customerCode the customerCode to set
 */
public void setCustomerCode(String customerCode) {
  this.customerCode = customerCode;
}
	
	

}
