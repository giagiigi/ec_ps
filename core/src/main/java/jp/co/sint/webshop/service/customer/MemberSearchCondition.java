package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.service.SearchCondition;

public class MemberSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String searchMobile;

  private String searchTel;

  private String searchCustomerCode;

  private String searchCustomerName;

  private String searchOrderNo;

  private String searchEmail;

  private String searchOrderType;

  private boolean searchWithOutExchangeOrder = false;

  private boolean searchWithOutReturnInfo = false;

  private boolean searchShippingMode = false;

  // 20110708 shiseido add start
  private boolean searchWithOutCancel = false;

  // 20110708 shiseido add end
  
  // soukai add 2012/01/31 ob start
  private String searchDeliverySlipNo;
  // soukai add 2012/01/31 ob end

  //20120323 ysy add start
  private String[] searchCustomerKbn = new String[0];
  
  private String searchCustomerGroupCode;
  //20120323 ysy add end
  /**
   * @return the searchMobile
   */
  public String getSearchMobile() {
    return searchMobile;
  }

  /**
   * @param searchMobile
   *          the searchMobile to set
   */
  public void setSearchMobile(String searchMobile) {
    this.searchMobile = searchMobile;
  }

  /**
   * @return the searchTel
   */
  public String getSearchTel() {
    return searchTel;
  }

  /**
   * @param searchTel
   *          the searchTel to set
   */
  public void setSearchTel(String searchTel) {
    this.searchTel = searchTel;
  }

  /**
   * @return the searchCustomerCode
   */
  public String getSearchCustomerCode() {
    return searchCustomerCode;
  }

  /**
   * @param searchCustomerCode
   *          the searchCustomerCode to set
   */
  public void setSearchCustomerCode(String searchCustomerCode) {
    this.searchCustomerCode = searchCustomerCode;
  }

  /**
   * @return the searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * @param searchCustomerName
   *          the searchCustomerName to set
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * @return the searchOrderNo
   */
  public String getSearchOrderNo() {
    return searchOrderNo;
  }

  /**
   * @param searchOrderNo
   *          the searchOrderNo to set
   */
  public void setSearchOrderNo(String searchOrderNo) {
    this.searchOrderNo = searchOrderNo;
  }

  /**
   * @return the searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  /**
   * @param searchEmail
   *          the searchEmail to set
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  /**
   * @return the searchOrderType
   */
  public String getSearchOrderType() {
    return searchOrderType;
  }

  /**
   * @param searchOrderType
   *          the searchOrderType to set
   */
  public void setSearchOrderType(String searchOrderType) {
    this.searchOrderType = searchOrderType;
  }

  /**
   * @return the searchWithOutExchangeOrder
   */
  public boolean isSearchWithOutExchangeOrder() {
    return searchWithOutExchangeOrder;
  }

  /**
   * @param searchWithOutExchangeOrder
   *          the searchWithOutExchangeOrder to set
   */
  public void setSearchWithOutExchangeOrder(boolean searchWithOutExchangeOrder) {
    this.searchWithOutExchangeOrder = searchWithOutExchangeOrder;
  }

  /**
   * @return the searchWithOutReturnInfo
   */
  public boolean isSearchWithOutReturnInfo() {
    return searchWithOutReturnInfo;
  }

  /**
   * @param searchWithOutReturnInfo
   *          the searchWithOutReturnInfo to set
   */
  public void setSearchWithOutReturnInfo(boolean searchWithOutReturnInfo) {
    this.searchWithOutReturnInfo = searchWithOutReturnInfo;
  }

  /**
   * @return the searchShippingMode
   */
  public boolean isSearchShippingMode() {
    return searchShippingMode;
  }

  /**
   * @param searchShippingMode
   *          the searchShippingMode to set
   */
  public void setSearchShippingMode(boolean searchShippingMode) {
    this.searchShippingMode = searchShippingMode;
  }

  /**
   * @return the searchWithOutCancel
   */
  public boolean isSearchWithOutCancel() {
    return searchWithOutCancel;
  }

  /**
   * @param searchWithOutCancel
   *          the searchWithOutCancel to set
   */
  public void setSearchWithOutCancel(boolean searchWithOutCancel) {
    this.searchWithOutCancel = searchWithOutCancel;
  }

/**
 * @return the searchDeliverySlipNo
 */
public String getSearchDeliverySlipNo() {
	return searchDeliverySlipNo;
}

/**
 * @param searchDeliverySlipNo the searchDeliverySlipNo to set
 */
public void setSearchDeliverySlipNo(String searchDeliverySlipNo) {
	this.searchDeliverySlipNo = searchDeliverySlipNo;
}



/**
 * @return the searchCustomerKbn
 */
public String[] getSearchCustomerKbn() {
  return searchCustomerKbn;
}


/**
 * @param searchCustomerKbn the searchCustomerKbn to set
 */
public void setSearchCustomerKbn(String[] searchCustomerKbn) {
  this.searchCustomerKbn = searchCustomerKbn;
}

/**
 * @return the searchCustomerGroupCode
 */
public String getSearchCustomerGroupCode() {
  return searchCustomerGroupCode;
}


/**
 * @param searchCustomerGroupCode the searchCustomerGroupCode to set
 */
public void setSearchCustomerGroupCode(String searchCustomerGroupCode) {
  this.searchCustomerGroupCode = searchCustomerGroupCode;
}


}
