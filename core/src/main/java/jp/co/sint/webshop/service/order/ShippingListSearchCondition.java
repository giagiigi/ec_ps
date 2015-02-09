package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DateUtil;

public class ShippingListSearchCondition extends SearchCondition {

  
  /** Serial Version UID */
  private static final long serialVersionUID = 1L;


  private int scale = 0;

  public int getScale() {
    return scale;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }
  
  /** 交易编号（淘宝） */
  private String searchFromTmallTid;
  
  /** 出荷番号 */
  private String searchShippingNo;

  /** 受注番号 */
  private String searchOrderNo;

  /** 顧客名 */
  private String searchCustomerName;

  /** 顧客名カナ */
  private String searchCustomerNameKana;

  /** 宛名 */
  private String searchAddressName;

  /** 電話番号 */
  private String searchCustomerPhoneNumber;
  
  /** 手机号码 */
  private String searchCustomerMobileNumber;
  
  /** ショップコード */
  private String searchShopCode;

  /** 配送種別番号 */
  private String searchDeliveryTypeNo;

  /** 宅配便伝票番号 */
  private String searchDeliverySlipNo;

  /** 受注日検索開始 */
  private String searchFromOrderDatetime;

  /** 受注日検索終了 */
  private String searchToOrderDatetime;

  /** 出荷日検索開始 */
  private String searchFromShippingDatetime;

  /** 出荷日検索終了 */
  private String searchToShippingDatetime;

  /** 出荷指示日検索開始 */
  private String searchFromShippingDirectDate;

  /** 出荷指示日検索終了 */
  private String searchToShippingDirectDate;

  /** 出荷指示日設定済のデータのみ */
  private boolean searchOnlySetDirectDate;

  /** 出荷指示日未設定のデータのみ */
  private boolean searchOnlyNotSetDirectDate;
  
  // 20120116 ysy add start
  /** 是否只是查找EC商品 */
  private boolean searchOnlyEcOrder;

  /** 是否只是查找Tmall商品 */
  private boolean searchOnlyTmallOrder;
  // 20120116 ysy add end

  private boolean searchOnlyJDOrder;
  
  /** 出荷状況 */
  private List<String> searchShippingStatus = new ArrayList<String>();

  /** データ連携済みデータ除去フラグ */
  private boolean removeDataTransportFlg;

  /** 売上確定データ除去フラグ */
  private boolean removeFixedSalesDataFlg;

  /** 返品区分 */
  private String searchReturnItemType;
  
  private String searchOrderFlg;

  /** ソート区分 */
  private String sortItem;

  // 20120116 ysy add start
  /** 配送種別番号 */
  private String searchDeliveryCompanyNo;
  
  private String searchDeliverySlipNo1;
  // 20120116 ysy add end
  
  /**
   * @return the sortItem
   */
  public String getSortItem() {
    return sortItem;
  }

  
  /**
 * @return the searchDeliverySlipNo1
 */
public String getSearchDeliverySlipNo1() {
	return searchDeliverySlipNo1;
}

/**
 * @param searchDeliverySlipNo1 the searchDeliverySlipNo1 to set
 */
public void setSearchDeliverySlipNo1(String searchDeliverySlipNo1) {
	this.searchDeliverySlipNo1 = searchDeliverySlipNo1;
}

/**
 * @return the searchOnlyEcOrder
 */
public boolean isSearchOnlyEcOrder() {
	return searchOnlyEcOrder;
}

/**
 * @param searchOnlyEcOrder the searchOnlyEcOrder to set
 */
public void setSearchOnlyEcOrder(boolean searchOnlyEcOrder) {
	this.searchOnlyEcOrder = searchOnlyEcOrder;
}

/**
 * @return the searchOnlyTmallOrder
 */
public boolean isSearchOnlyTmallOrder() {
	return searchOnlyTmallOrder;
}

/**
 * @param searchOnlyTmallOrder the searchOnlyTmallOrder to set
 */
public void setSearchOnlyTmallOrder(boolean searchOnlyTmallOrder) {
	this.searchOnlyTmallOrder = searchOnlyTmallOrder;
}


/**
 * @return the searchOnlyJDOrder
 */
public boolean isSearchOnlyJDOrder() {
  return searchOnlyJDOrder;
}


/**
 * @param searchOnlyJDOrder the searchOnlyJDOrder to set
 */
public void setSearchOnlyJDOrder(boolean searchOnlyJDOrder) {
  this.searchOnlyJDOrder = searchOnlyJDOrder;
}

/**
 * @return the searchDeliveryCompanyNo
 */
public String getSearchDeliveryCompanyNo() {
	return searchDeliveryCompanyNo;
}

/**
 * @param searchDeliveryCompanyNo the searchDeliveryCompanyNo to set
 */
public void setSearchDeliveryCompanyNo(String searchDeliveryCompanyNo) {
	this.searchDeliveryCompanyNo = searchDeliveryCompanyNo;
}

/**
   * @param sortItem
   *          the sortItem to set
   */
  public void setSortItem(String sortItem) {
    this.sortItem = sortItem;
  }

  /**
   * @return searchDeliverySlipNo
   */
  public String getSearchDeliverySlipNo() {
    return searchDeliverySlipNo;
  }

  /**
   * @param searchDeliverySlipNo
   *          設定する searchDeliverySlipNo
   */
  public void setSearchDeliverySlipNo(String searchDeliverySlipNo) {
    this.searchDeliverySlipNo = searchDeliverySlipNo;
  }

  /**
   * @return the searchDeliveryTypeNo
   */
  public String getSearchDeliveryTypeNo() {
    return searchDeliveryTypeNo;
  }

  /**
   * @param searchDeliveryTypeNo
   *          the searchDeliveryTypeNo to set
   */
  public void setSearchDeliveryTypeNo(String searchDeliveryTypeNo) {
    this.searchDeliveryTypeNo = searchDeliveryTypeNo;
  }

  /**
   * @return searchFromShippingDatetime
   */
  public String getSearchFromShippingDatetime() {
    return searchFromShippingDatetime;
  }

  /**
   * @param searchFromShippingDatetime
   *          設定する searchFromShippingDatetime
   */
  public void setSearchFromShippingDatetime(String searchFromShippingDatetime) {
    this.searchFromShippingDatetime = searchFromShippingDatetime;
  }

  /**
   * @return searchOrderNo
   */
  public String getSearchOrderNo() {
    return searchOrderNo;
  }

  /**
   * @param searchOrderNo
   *          設定する searchOrderNo
   */
  public void setSearchOrderNo(String searchOrderNo) {
    this.searchOrderNo = searchOrderNo;
  }

  /**
   * @return searchShippingNo
   */
  public String getSearchShippingNo() {
    return searchShippingNo;
  }

  /**
   * @param searchShippingNo
   *          設定する searchShippingNo
   */
  public void setSearchShippingNo(String searchShippingNo) {
    this.searchShippingNo = searchShippingNo;
  }

  /**
   * @return searchShippingStatus
   */
  public List<String> getSearchShippingStatus() {
    return searchShippingStatus;
  }

  /**
   * @param searchShippingStatus
   *          設定する searchShippingStatus
   */
  public void setSearchShippingStatus(List<String> searchShippingStatus) {
    this.searchShippingStatus = searchShippingStatus;
  }

  /**
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * @param searchShopCode
   *          設定する searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * @return searchToShippingDatetime
   */
  public String getSearchToShippingDatetime() {
    return searchToShippingDatetime;
  }

  /**
   * @param searchToShippingDatetime
   *          設定する searchToShippingDatetime
   */
  public void setSearchToShippingDatetime(String searchToShippingDatetime) {
    this.searchToShippingDatetime = searchToShippingDatetime;
  }

  /**
   * @return the searchCustomerNameKana
   */
  public String getSearchCustomerNameKana() {
    return searchCustomerNameKana;
  }

  /**
   * @param searchCustomerNameKana
   *          the searchCustomerNameKana to set
   */
  public void setSearchCustomerNameKana(String searchCustomerNameKana) {
    this.searchCustomerNameKana = searchCustomerNameKana;
  }

  /**
   * @return searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * @param searchCustomerName
   *          設定する searchCustomerName
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * @return searchCustomerTel
   */
  public String getSearchCustomerPhoneNumber() {
    return searchCustomerPhoneNumber;
  }

  /**
   * @param searchCustomerPhoneNumber
   *          設定する searchCustomerPhoneNumber
   */
  public void setSearchCustomerPhoneNumber(String searchCustomerPhoneNumber) {
    this.searchCustomerPhoneNumber = searchCustomerPhoneNumber;
  }

  /**
   * @return the searchReturnItemType
   */
  public String getSearchReturnItemType() {
    return searchReturnItemType;
  }

  /**
   * @param searchReturnItemType
   *          the searchReturnItemType to set
   */
  public void setSearchReturnItemType(String searchReturnItemType) {
    this.searchReturnItemType = searchReturnItemType;
  }

  /**
   * @return the searchAddressName
   */
  public String getSearchAddressName() {
    return searchAddressName;
  }

  /**
   * @param searchAddressName
   *          the searchAddressName to set
   */
  public void setSearchAddressName(String searchAddressName) {
    this.searchAddressName = searchAddressName;
  }

  /**
   * @return the searchFromShippingDirectDate
   */
  public String getSearchFromShippingDirectDate() {
    return searchFromShippingDirectDate;
  }

  /**
   * @param searchFromShippingDirectDate
   *          the searchFromShippingDirectDate to set
   */
  public void setSearchFromShippingDirectDate(String searchFromShippingDirectDate) {
    this.searchFromShippingDirectDate = searchFromShippingDirectDate;
  }

  /**
   * @return the searchToShippingDirectDate
   */
  public String getSearchToShippingDirectDate() {
    return searchToShippingDirectDate;
  }

  /**
   * @param searchToShippingDirectDate
   *          the searchToShippingDirectDate to set
   */
  public void setSearchToShippingDirectDate(String searchToShippingDirectDate) {
    this.searchToShippingDirectDate = searchToShippingDirectDate;
  }

  public List<String> isValid() {
    List<String> list = new ArrayList<String>();

    Date fromShippingDate = DateUtil.fromString(this.getSearchFromShippingDatetime());
    Date toShippingDate = DateUtil.fromString(this.getSearchToShippingDatetime());
    if (fromShippingDate != null && toShippingDate != null) {
      if (toShippingDate.before(fromShippingDate)) {
        list.add(Messages.getString("service.order.ShippingListSearchCondition.0"));
      }
    }

    Date fromDirectDate = DateUtil.fromString(this.getSearchFromShippingDirectDate());
    Date toDirectDate = DateUtil.fromString(this.getSearchToShippingDirectDate());
    if (fromDirectDate != null && toDirectDate != null) {
      if (toDirectDate.before(fromDirectDate)) {
        list.add(Messages.getString("service.order.ShippingListSearchCondition.1"));
      }
    }

    return list;
  }

  /**
   * removeDataTransportFlgを取得します。
   * 
   * @return removeDataTransportFlg
   */
  public boolean isRemoveDataTransportFlg() {
    return removeDataTransportFlg;
  }

  /**
   * removeDataTransportFlgを設定します。
   * 
   * @param removeDataTransportFlg
   *          removeDataTransportFlg
   */
  public void setRemoveDataTransportFlg(boolean removeDataTransportFlg) {
    this.removeDataTransportFlg = removeDataTransportFlg;
  }

  /**
   * removeFixedSalesDataFlgを取得します。
   * 
   * @return removeFixedSalesDataFlg
   */
  public boolean isRemoveFixedSalesDataFlg() {
    return removeFixedSalesDataFlg;
  }

  /**
   * removeFixedSalesDataFlgを設定します。
   * 
   * @param removeFixedSalesDataFlg
   *          removeFixedSalesDataFlg
   */
  public void setRemoveFixedSalesDataFlg(boolean removeFixedSalesDataFlg) {
    this.removeFixedSalesDataFlg = removeFixedSalesDataFlg;
  }

  /**
   * searchFromOrderDatetimeを取得します。
   * 
   * @return searchFromOrderDatetime
   */
  public String getSearchFromOrderDatetime() {
    return searchFromOrderDatetime;
  }

  /**
   * searchFromOrderDatetimeを設定します。
   * 
   * @param searchFromOrderDatetime
   *          searchFromOrderDatetime
   */
  public void setSearchFromOrderDatetime(String searchFromOrderDatetime) {
    this.searchFromOrderDatetime = searchFromOrderDatetime;
  }

  /**
   * searchToOrderDatetimeを取得します。
   * 
   * @return searchToOrderDatetime
   */
  public String getSearchToOrderDatetime() {
    return searchToOrderDatetime;
  }

  /**
   * searchToOrderDatetimeを設定します。
   * 
   * @param searchToOrderDatetime
   *          searchToOrderDatetime
   */
  public void setSearchToOrderDatetime(String searchToOrderDatetime) {
    this.searchToOrderDatetime = searchToOrderDatetime;
  }

  /**
   * searchOnlyNotSetDirectDateを取得します。
   * 
   * @return searchOnlyNotSetDirectDate
   */
  public boolean isSearchOnlyNotSetDirectDate() {
    return searchOnlyNotSetDirectDate;
  }

  /**
   * searchOnlyNotSetDirectDateを設定します。
   * 
   * @param searchOnlyNotSetDirectDate
   *          searchOnlyNotSetDirectDate
   */
  public void setSearchOnlyNotSetDirectDate(boolean searchOnlyNotSetDirectDate) {
    this.searchOnlyNotSetDirectDate = searchOnlyNotSetDirectDate;
  }

  /**
   * searchOnlySetDirectDateを取得します。
   * 
   * @return searchOnlySetDirectDate
   */
  public boolean isSearchOnlySetDirectDate() {
    return searchOnlySetDirectDate;
  }

  /**
   * searchOnlySetDirectDateを設定します。
   * 
   * @param searchOnlySetDirectDate
   *          searchOnlySetDirectDate
   */
  public void setSearchOnlySetDirectDate(boolean searchOnlySetDirectDate) {
    this.searchOnlySetDirectDate = searchOnlySetDirectDate;
  }

   /**
   * searchCustomerMobileNumberを取得します。
   *
   * @return searchCustomerMobileNumber searchCustomerMobileNumber
   */
  public String getSearchCustomerMobileNumber() {
    return searchCustomerMobileNumber;
  }

  
  /**
   * searchCustomerMobileNumberを設定します。
   *
   * @param searchCustomerMobileNumber 
   *          searchCustomerMobileNumber
   */
  public void setSearchCustomerMobileNumber(String searchCustomerMobileNumber) {
    this.searchCustomerMobileNumber = searchCustomerMobileNumber;
  }

  
  /**
   * @return the searchFromTmallTid
   */
  public String getSearchFromTmallTid() {
    return searchFromTmallTid;
  }

  
  /**
   * @param searchFromTmallTid the searchFromTmallTid to set
   */
  public void setSearchFromTmallTid(String searchFromTmallTid) {
    this.searchFromTmallTid = searchFromTmallTid;
  }

  
  /**
   * @return the searchOrderFlg
   */
  public String getSearchOrderFlg() {
    return searchOrderFlg;
  }

  
  /**
   * @param searchOrderFlg the searchOrderFlg to set
   */
  public void setSearchOrderFlg(String searchOrderFlg) {
    this.searchOrderFlg = searchOrderFlg;
  }

}
