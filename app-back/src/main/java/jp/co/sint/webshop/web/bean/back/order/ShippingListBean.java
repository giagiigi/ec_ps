package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020410:出荷管理のデータモデルです。

 * 
 * @author System Integrator Corp.
 */
public class ShippingListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String uploadFile;

  private String useHeader = "false";

  private List<NameValue> csvHeaderType = NameValue.asList("true:"
      + Messages.getString("web.bean.back.order.ShippingListBean.4")
      + "/false:"
      + Messages.getString("web.bean.back.order.ShippingListBean.5"));

  private List<CodeAttribute> searchShippingStatusList;

  private List<ShippingSearchedListBean> list = new ArrayList<ShippingSearchedListBean>();

  private InputDirectBean edit = new InputDirectBean();

  private ShippingReportBean reportEdit = new ShippingReportBean();

  private List<String> errorMessageDetailList = new ArrayList<String>();

  private boolean authorityIO;

  private boolean sortAscFlg = false;

  /** ショップ */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップ")
  private String searchShopCode;

  /** 出荷番号 */
  @Length(16)
  //update by lc 2012-03-06
  // @Digit
  @Metadata(name = "出荷番号")
  private String searchShippingNo;

  /** 受注番号 */
  @Length(16)
  //update by lc 2012-03-06
  // @Digit
  @Metadata(name = "受注番号")
  private String searchOrderNo;

  /** 宛名 */
  @Length(40)
  @Metadata(name = "宛名")
  private String searchAddressName;

  /** 顧客名 */
  @Length(40)
  @Metadata(name = "顧客名")
  private String searchCustomerName;

  /** 顧客名カナ */
  @Length(80)
  @Kana
  @Metadata(name = "顧客名カナ")
  private String searchCustomerNameKana;

  /** 電話番号 */
  @Digit(allowNegative = false)
  @Length(18)
  @Metadata(name = "電話番号")
  private String searchCustomerPhoneNumber;

  /** 手机号码 */
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码")
  private String searchCustomerMobileNumber;
  
  /** 配送種別 */
  @Length(8)
  @Digit
  @Metadata(name = "配送種別")
  private String searchDeliveryTypeNo;

  /** 宅配便伝票番号 */
  @Length(30)
  @Digit
  @Metadata(name = "宅配便伝票番号")
  private String searchDeliverySlipNo;
  
  // 20120130 ysy add start
  private String searchDeliverySlipNo1;
  // 20120130 ysy add end

  /** 出荷ステータス */
  private List<String> searchShippingStatus = new ArrayList<String>();

  /** 受注日From */
  @Datetime
  @Metadata(name = "受注日(From)")
  private String searchFromOrderDate;

  /** 受注日To */
  @Datetime
  @Metadata(name = "受注日(To)")
  private String searchToOrderDate;

  /** 交易编号 */
  @Length(16)
  @Metadata(name = "交易编号", order = 6)
  private String searchFromTmallTid;
  
  /** 出荷日From */
  @Datetime
  @Metadata(name = "出荷日(From)")
  private String searchFromShippingDatetime;

  /** 出荷日To */
  @Datetime
  @Metadata(name = "出荷日(To)")
  private String searchToShippingDatetime;

  /** 出荷指示日From */
  @Datetime
  @Metadata(name = "出荷指示日(From)")
  private String searchFromShippingDirectDate;

  /** 出荷指示From */
  @Datetime
  @Metadata(name = "出荷指示日(To)")
  private String searchToShippingDirectDate;

  /** 出荷指示日未指定のデータを含むかどうか */
  private String searchShippingDirectStatus;
  
  // 20120116 ysy add start
  private String searchShippingOrderStatus;
  
  private boolean searchOnlyEcOrder;
  
  private boolean searchOnlyTmallOrder;
  
  private boolean searchOnlyJDOrder;
  // 20120116 ysy add end

  /** 返品区分 */
  @Metadata(name = "返品区分")
  private String searchReturnItemType;

  /** ショップリスト */
  private List<CodeAttribute> searchShopList = new ArrayList<CodeAttribute>();

  /** チェックボックスリスト */
  private List<String> shippingCheck = new ArrayList<String>();

  /** 配送種別リスト */
  private List<CodeAttribute> searchDeliveryType = new ArrayList<CodeAttribute>();

  /** 配送種別リスト表示フラグ */
  private boolean deliveryTypeDisplayFlg;

  /** データ連携済みフラグ */
  private boolean searchDataTransportFlg;

  /** 売上確定データフラグ */
  private boolean searchFixedSalesDataFlg;

  /** ページャ */
  private PagerValue pagerValue = new PagerValue();

  /** ショッププルダウン表示 */
  private boolean shopListDisplayFlg;

  /** 出荷指示・取消し表示 */
  private boolean shippingInstructionsDisplayFlg;

  /** CSV登録表示 */
  private boolean shippingImportDisplayFlg;

  /** DB更新成功フラグ */
  private boolean seccessUpdate;

  private String advancedSearchMode;

  // 20120116 ysy add start
  /** 配送公司 */
  @Length(16)
  @Metadata(name = "配送公司")
  private String searchDeliveryCompanyNo;
  
  /** 配送公司リスト */
  private List<CodeAttribute> searchDeliveryCompany = new ArrayList<CodeAttribute>();

  /** 配送公司リスト表示フラグ */
  private boolean deliveryCompanyDisplayFlg;
  // 20120116 ysy add end

  /**
   * U1020410:出荷管理のサブモデルです。

   * 
   * @author System Integrator Corp.
   */
  public static class InputDirectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 出荷指示日 */
    @Required
    @Datetime
    @Metadata(name = "出荷指示日")
    private String inputShippingDate;

    /** 出荷指示日入力時、出荷指示も行うフラグ */
    private boolean updateWithShipping;

    /**
     * updateWithShippingを取得します。

     * 
     * @return updateWithShipping
     */
    public boolean isUpdateWithShipping() {
      return updateWithShipping;
    }

    /**
     * updateWithShippingを設定します。

     * 
     * @param updateWithShipping
     *          updateWithShipping
     */
    public void setUpdateWithShipping(boolean updateWithShipping) {
      this.updateWithShipping = updateWithShipping;
    }

    /**
     * inputShippingDateを取得します。

     * 
     * @return inputShippingDate
     */
    public String getInputShippingDate() {
      return inputShippingDate;
    }

    /**
     * inputShippingDateを設定します。

     * 
     * @param inputShippingDate
     *          inputShippingDate
     */
    public void setInputShippingDate(String inputShippingDate) {
      this.inputShippingDate = inputShippingDate;
    }

  }

  /**
   * U1020410:出荷管理のサブモデルです。

   * 
   * @author System Integrator Corp.
   */
  public static class ShippingReportBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 出荷指示日 */
    @Required
    @Datetime
    @Metadata(name = "出荷日")
    private String shippingReportDate;

    /**
     * shippingReportDateを取得します。

     * 
     * @return shippingReportDate
     */
    public String getShippingReportDate() {
      return shippingReportDate;
    }

    /**
     * shippingReportDateを設定します。

     * 
     * @param shippingReportDate
     *          shippingReportDate
     */
    public void setShippingReportDate(String shippingReportDate) {
      this.shippingReportDate = shippingReportDate;
    }

  }

  /**
   * U1020410:出荷管理のサブモデルです。

   * 
   * @author System Integrator Corp.
   */
  public static class ShippingSearchedListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shippingCheckbox;

    private String shippingNo;

    private String memo;

    private String orderNo;

    private String orderDatetime;

    private String addressLastName;

    private String addressFirstName;

    private String customerLastName;

    private String customerFirstName;

    private String deliveryTypeName;

    private String shippingDirectDate;

    private String deliverySlipNo;

    private String arrivalDate;

    private String delivereyAppointedDate;

    private String shippingDate;

    private String shippingStatus;

    private Date updatedDatetime;

    private Date orderUpdatedDatetime;

    private String caution;

    private String message;

    private String deliveryRemark;

    // 20120116 ysy add start
    private String deliveryCompanyName;
    
    private String deliveryCompanyNo;
    
    private String deliverySlipNo1;
    // 20120116 ysy add end
    
    /** 宅配便伝票URL */
    private String parcelUrl;

    
    /**
	 * @return the deliverySlipNo1
	 */
	public String getDeliverySlipNo1() {
		return deliverySlipNo1;
	}

	/**
	 * @param deliverySlipNo1 the deliverySlipNo1 to set
	 */
	public void setDeliverySlipNo1(String deliverySlipNo1) {
		this.deliverySlipNo1 = deliverySlipNo1;
	}

	/**
     * parcelUrlを取得します。

     * 
     * @return parcelUrl
     */
    public String getParcelUrl() {
      return parcelUrl;
    }

    /**
     * parcelUrlを設定します。

     * 
     * @param parcelUrl
     *          parcelUrl
     */
    public void setParcelUrl(String parcelUrl) {
      this.parcelUrl = parcelUrl;
    }

    
    /**
	 * @return the deliveryCompanyNo
	 */
	public String getDeliveryCompanyNo() {
		return deliveryCompanyNo;
	}

	/**
	 * @param deliveryCompanyNo the deliveryCompanyNo to set
	 */
	public void setDeliveryCompanyNo(String deliveryCompanyNo) {
		this.deliveryCompanyNo = deliveryCompanyNo;
	}

	/**
	 * @return the deliveryCompanyName
	 */
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}

	/**
	 * @param deliveryCompanyName the deliveryCompanyName to set
	 */
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}

	/**
     * orderUpdatedDatetimeを取得します。

     * 
     * @return orderUpdatedDatetime
     */
    public Date getOrderUpdatedDatetime() {
      return DateUtil.immutableCopy(orderUpdatedDatetime);
    }

    /**
     * orderUpdatedDatetimeを設定します。

     * 
     * @param orderUpdatedDatetime
     *          orderUpdatedDatetime
     */
    public void setOrderUpdatedDatetime(Date orderUpdatedDatetime) {
      this.orderUpdatedDatetime = DateUtil.immutableCopy(orderUpdatedDatetime);
    }

    /**
     * @return the remarkImageDisplayFlg
     */
    public boolean isRemarkImageDisplayFlg() {
      // 連絡事項、注意事項（管理側のみ参照）、配送先備考のいずれかが入力されている場合はtrue
      return StringUtil.hasValueAnyOf(this.getCaution(), this.getMessage(), this.getDeliveryRemark());
    }

    /**
     * @return 連絡事項、注意事項（管理側のみ参照）、配送先備考に入力されていれば、該当する文字列を取得します。

     */
    public String getRemarksForDisplay() {
      StringBuilder builder = new StringBuilder();
      if (StringUtil.hasValue(this.getMessage())) {
        builder.append(Messages.getString("web.bean.back.order.ShippingListBean.0"));
        builder.append(this.getMessage());
        builder.append(" ");
      }
      if (StringUtil.hasValue(this.getCaution())) {
        builder.append(Messages.getString("web.bean.back.order.ShippingListBean.1"));
        builder.append(this.getCaution());
        builder.append(" ");
      }
      if (StringUtil.hasValue(this.getDeliveryRemark())) {
        builder.append(Messages.getString("web.bean.back.order.ShippingListBean.2"));
        builder.append(this.getDeliveryRemark());
        builder.append(" ");
      }
      return builder.toString();
    }

    /**
     * updatedDatetimeを取得します。

     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを設定します。

     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * deloveryTypeNameを取得します。

     * 
     * @return deloveryTypeName
     */
    public String getDeliveryTypeName() {
      return deliveryTypeName;
    }

    /**
     * memoを取得します。

     * 
     * @return memo
     */
    public String getMemo() {
      return memo;
    }

    /**
     * orderDatetimeを取得します。

     * 
     * @return orderDatetime
     */
    public String getOrderDatetime() {
      return orderDatetime;
    }

    /**
     * orderNoを取得します。

     * 
     * @return orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    /**
     * shippingCheckboxを取得します。

     * 
     * @return shippingCheckbox
     */
    public String getShippingCheckbox() {
      return shippingCheckbox;
    }

    /**
     * shippingDateを取得します。

     * 
     * @return shippingDate
     */
    public String getShippingDate() {
      return shippingDate;
    }

    /**
     * shippingDirectDateを取得します。

     * 
     * @return shippingDirectDate
     */
    public String getShippingDirectDate() {
      return shippingDirectDate;
    }

    /**
     * shippingNoを取得します。

     * 
     * @return shippingNo
     */
    public String getShippingNo() {
      return shippingNo;
    }

    /**
     * shippingSlipNoを取得します。

     * 
     * @return shippingSlipNo
     */
    public String getDeliverySlipNo() {
      return deliverySlipNo;
    }

    /**
     * shippingStatusを取得します。

     * 
     * @return shippingStatus
     */
    public String getShippingStatus() {
      return shippingStatus;
    }

    /**
     * addressFirstNameを取得します。

     * 
     * @return addressFirstName
     */
    public String getAddressFirstName() {
      return addressFirstName;
    }

    /**
     * addressFirstNameを設定します。

     * 
     * @param addressFirstName
     *          addressFirstName
     */
    public void setAddressFirstName(String addressFirstName) {
      this.addressFirstName = addressFirstName;
    }

    /**
     * addressLastNameを取得します。

     * 
     * @return addressLastName
     */
    public String getAddressLastName() {
      return addressLastName;
    }

    /**
     * addressLastNameを設定します。

     * 
     * @param addressLastName
     *          addressLastName
     */
    public void setAddressLastName(String addressLastName) {
      this.addressLastName = addressLastName;
    }

    /**
     * customerFirstNameを取得します。

     * 
     * @return customerFirstName
     */
    public String getCustomerFirstName() {
      return customerFirstName;
    }

    /**
     * customerFirstNameを設定します。

     * 
     * @param customerFirstName
     *          customerFirstName
     */
    public void setCustomerFirstName(String customerFirstName) {
      this.customerFirstName = customerFirstName;
    }

    /**
     * customerLastNameを取得します。

     * 
     * @return customerLastName
     */
    public String getCustomerLastName() {
      return customerLastName;
    }

    /**
     * customerLastNameを設定します。

     * 
     * @param customerLastName
     *          customerLastName
     */
    public void setCustomerLastName(String customerLastName) {
      this.customerLastName = customerLastName;
    }

    /**
     * deloveryTypeNameを設定します。

     * 
     * @param deloveryTypeName
     *          deloveryTypeName
     */
    public void setDeliveryTypeName(String deloveryTypeName) {
      this.deliveryTypeName = deloveryTypeName;
    }

    /**
     * memoを設定します。

     * 
     * @param memo
     *          memo
     */
    public void setMemo(String memo) {
      this.memo = memo;
    }

    /**
     * orderDatetimeを設定します。

     * 
     * @param orderDatetime
     *          orderDatetime
     */
    public void setOrderDatetime(String orderDatetime) {
      this.orderDatetime = orderDatetime;
    }

    /**
     * orderNoを設定します。

     * 
     * @param orderNo
     *          orderNo
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    /**
     * shippingCheckboxを設定します。

     * 
     * @param shippingCheckbox
     *          shippingCheckbox
     */
    public void setShippingCheckbox(String shippingCheckbox) {
      this.shippingCheckbox = shippingCheckbox;
    }

    /**
     * shippingDateを設定します。

     * 
     * @param shippingDate
     *          shippingDate
     */
    public void setShippingDate(String shippingDate) {
      this.shippingDate = shippingDate;
    }

    /**
     * shippingDirectDateを設定します。

     * 
     * @param shippingDirectDate
     *          shippingDirectDate
     */
    public void setShippingDirectDate(String shippingDirectDate) {
      this.shippingDirectDate = shippingDirectDate;
    }

    /**
     * shippingNoを設定します。

     * 
     * @param shippingNo
     *          shippingNo
     */
    public void setShippingNo(String shippingNo) {
      this.shippingNo = shippingNo;
    }

    /**
     * shippingSlipNoを設定します。

     * 
     * @param deliverySlipNo
     *          宅配伝票番号
     */
    public void setDeliverySlipNo(String deliverySlipNo) {
      this.deliverySlipNo = deliverySlipNo;
    }

    /**
     * shippingStatusを設定します。

     * 
     * @param shippingStatus
     *          shippingStatus
     */
    public void setShippingStatus(String shippingStatus) {
      this.shippingStatus = shippingStatus;
    }

    /**
     * arrivalDateを取得します。

     * 
     * @return arrivalDate
     */
    public String getArrivalDate() {
      return arrivalDate;
    }

    /**
     * arrivalDateを設定します。

     * 
     * @param arrivalDate
     *          arrivalDate
     */
    public void setArrivalDate(String arrivalDate) {
      this.arrivalDate = arrivalDate;
    }

    /**
     * cautionを取得します。

     * 
     * @return caution
     */
    public String getCaution() {
      return caution;
    }

    /**
     * cautionを設定します。

     * 
     * @param caution
     *          caution
     */
    public void setCaution(String caution) {
      this.caution = caution;
    }

    /**
     * messageを取得します。

     * 
     * @return message
     */
    public String getMessage() {
      return message;
    }

    /**
     * messageを設定します。

     * 
     * @param message
     *          message
     */
    public void setMessage(String message) {
      this.message = message;
    }

    /**
     * deliveryRemarkを取得します。

     * 
     * @return deliveryRemark
     */
    public String getDeliveryRemark() {
      return deliveryRemark;
    }

    /**
     * deliveryRemarkを設定します。

     * 
     * @param deliveryRemark
     *          deliveryRemark
     */
    public void setDeliveryRemark(String deliveryRemark) {
      this.deliveryRemark = deliveryRemark;
    }

    public String getDelivereyAppointedDate() {
      return delivereyAppointedDate;
    }

    public void setDelivereyAppointedDate(String delivereyAppointedDate) {
      this.delivereyAppointedDate = delivereyAppointedDate;
    }

  }

  /**
   * U1020410:出荷管理のサブモデルです。

   * 
   * @author System Integrator Corp.
   */
  public static class ExportCsvBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** CSV種類 */
    @Required
    private String shippingCsvType;

    /**
     * shippingCsvTypeを取得します。

     * 
     * @return shippingCsvType
     */
    public String getShippingCsvType() {
      return shippingCsvType;
    }

    /**
     * shippingCsvTypeを設定します。

     * 
     * @param shippingCsvType
     *          shippingCsvType
     */
    public void setShippingCsvType(String shippingCsvType) {
      this.shippingCsvType = shippingCsvType;
    }

  }

  /**
   * seccessUpdateを取得します。

   * 
   * @return seccessUpdate
   */
  public boolean isSeccessUpdate() {
    return seccessUpdate;
  }

  /**
   * seccessUpdateを設定します。

   * 
   * @param seccessUpdate
   *          seccessUpdate
   */
  public void setSeccessUpdate(boolean seccessUpdate) {
    this.seccessUpdate = seccessUpdate;
  }

  /**
   * searchAddressNameを取得します。

   * 
   * @return searchAddressName
   */
  public String getSearchAddressName() {
    return searchAddressName;
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
   * searchDeliverySlipNoを取得します。

   * 
   * @return searchDeliverySlipNo
   */
  public String getSearchDeliverySlipNo() {
    return searchDeliverySlipNo;
  }

  /**
   * searchFromShippingDatetimeを取得します。

   * 
   * @return searchFromShippingDatetime
   */
  public String getSearchFromShippingDatetime() {
    return searchFromShippingDatetime;
  }

  /**
   * searchOrderNoを取得します。

   * 
   * @return searchOrderNo
   */
  public String getSearchOrderNo() {
    return searchOrderNo;
  }

  /**
   * searchShippingNoを取得します。

   * 
   * @return searchShippingNo
   */
  public String getSearchShippingNo() {
    return searchShippingNo;
  }

  /**
   * searchShopCodeを取得します。

   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * searchToShippingDatetimeを取得します。

   * 
   * @return searchToShippingDatetime
   */
  public String getSearchToShippingDatetime() {
    return searchToShippingDatetime;
  }

  /**
   * searchAddressNameを設定します。

   * 
   * @param searchAddressName
   *          searchAddressName
   */
  public void setSearchAddressName(String searchAddressName) {
    this.searchAddressName = searchAddressName;
  }

  /**
   * searchDeliverySlipNoを設定します。

   * 
   * @param searchDeliverySlipNo
   *          searchDeliverySlipNo
   */
  public void setSearchDeliverySlipNo(String searchDeliverySlipNo) {
    this.searchDeliverySlipNo = searchDeliverySlipNo;
  }

  /**
   * searchDeliveryTypeNoを取得します。

   * 
   * @return searchDeliveryTypeNo
   */
  public String getSearchDeliveryTypeNo() {
    return searchDeliveryTypeNo;
  }

  /**
   * searchDeliveryTypeNoを設定します。

   * 
   * @param searchDeliveryTypeNo
   *          searchDeliveryTypeNo
   */
  public void setSearchDeliveryTypeNo(String searchDeliveryTypeNo) {
    this.searchDeliveryTypeNo = searchDeliveryTypeNo;
  }

  /**
   * searchFromShippingDatetimeを設定します。

   * 
   * @param searchFromShippingDatetime
   *          searchFromShippingDatetime
   */
  public void setSearchFromShippingDatetime(String searchFromShippingDatetime) {
    this.searchFromShippingDatetime = searchFromShippingDatetime;
  }

  /**
   * searchOrderNoを設定します。

   * 
   * @param searchOrderNo
   *          searchOrderNo
   */
  public void setSearchOrderNo(String searchOrderNo) {
    this.searchOrderNo = searchOrderNo;
  }

  /**
   * searchShippingNoを設定します。

   * 
   * @param searchShippingNo
   *          searchShippingNo
   */
  public void setSearchShippingNo(String searchShippingNo) {
    this.searchShippingNo = searchShippingNo;
  }

  /**
   * searchShopCodeを設定します。

   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * searchFromShippingDirectDateを取得します。

   * 
   * @return searchFromShippingDirectDate
   */
  public String getSearchFromShippingDirectDate() {
    return searchFromShippingDirectDate;
  }

  /**
   * searchFromShippingDirectDateを設定します。

   * 
   * @param searchFromShippingDirectDate
   *          searchFromShippingDirectDate
   */
  public void setSearchFromShippingDirectDate(String searchFromShippingDirectDate) {
    this.searchFromShippingDirectDate = searchFromShippingDirectDate;
  }

  /**
   * searchToShippingDirectDateを取得します。

   * 
   * @return searchToShippingDirectDate
   */
  public String getSearchToShippingDirectDate() {
    return searchToShippingDirectDate;
  }

  /**
   * searchToShippingDirectDateを設定します。

   * 
   * @param searchToShippingDirectDate
   *          searchToShippingDirectDate
   */
  public void setSearchToShippingDirectDate(String searchToShippingDirectDate) {
    this.searchToShippingDirectDate = searchToShippingDirectDate;
  }

  /**
   * searchToShippingDatetimeを設定します。

   * 
   * @param searchToShippingDatetime
   *          searchToShippingDatetime
   */
  public void setSearchToShippingDatetime(String searchToShippingDatetime) {
    this.searchToShippingDatetime = searchToShippingDatetime;
  }

  /**
   * searchDeliveryTypeを取得します。

   * 
   * @return searchDeliveryType
   */
  public List<CodeAttribute> getSearchDeliveryType() {
    return searchDeliveryType;
  }

  /**
   * searchShopListを取得します。

   * 
   * @return searchShopList
   */
  public List<CodeAttribute> getSearchShopList() {
    return searchShopList;
  }

  /**
   * searchDeliveryTypeを設定します。

   * 
   * @param searchDeliveryType
   *          searchDeliveryType
   */
  public void setSearchDeliveryType(List<CodeAttribute> searchDeliveryType) {
    this.searchDeliveryType = searchDeliveryType;
  }

  /**
   * searchShopListを設定します。

   * 
   * @param searchShopList
   *          searchShopList
   */
  public void setSearchShopList(List<CodeAttribute> searchShopList) {
    this.searchShopList = searchShopList;
  }

  /**
   * searchShippingStatusListを取得します。

   * 
   * @return searchShippingStatusList
   */
  public List<CodeAttribute> getSearchShippingStatusList() {
    return searchShippingStatusList;
  }

  /**
   * searchShippingStatusListを設定します。

   * 
   * @param searchShippingStatusList
   *          searchShippingStatusList
   */
  public void setSearchShippingStatusList(List<CodeAttribute> searchShippingStatusList) {
    this.searchShippingStatusList = searchShippingStatusList;
  }

  /**
   * searchShippingStatusを取得します。

   * 
   * @return searchShippingStatus
   */
  public List<String> getSearchShippingStatus() {
    return searchShippingStatus;
  }

  /**
   * searchShippingStatusを設定します。

   * 
   * @param searchShippingStatus
   *          searchShippingStatus
   */
  public void setSearchShippingStatus(List<String> searchShippingStatus) {
    this.searchShippingStatus = searchShippingStatus;
  }

  /**
   * サブJSPを設定します。

   */
  @Override
  public void setSubJspId() {

  }

  /**
   * モジュールIDを取得します。

   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020410";
  }

  /**
   * モジュール名を取得します。

   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.ShippingListBean.3");
  }

  /**
   * pagerValueを取得します。

   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。

   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * searchCustomerNameを取得します。

   * 
   * @return searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * searchCustomerNameを設定します。

   * 
   * @param searchCustomerName
   *          searchCustomerName
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを取得します。

   * 
   * @return searchCustomerNameKana
   */
  public String getSearchCustomerNameKana() {
    return searchCustomerNameKana;
  }

  /**
   * searchCustomerNameKanaを設定します。

   * 
   * @param searchCustomerNameKana
   *          searchCustomerNameKana
   */
  public void setSearchCustomerNameKana(String searchCustomerNameKana) {
    this.searchCustomerNameKana = searchCustomerNameKana;
  }

  /**
   * searchCustomerPhoneNumberを取得します。

   * 
   * @return searchCustomerPhoneNumber
   */
  public String getSearchCustomerPhoneNumber() {
    return searchCustomerPhoneNumber;
  }

  /**
   * searchCustomerPhoneNumberを設定します。

   * 
   * @param searchCustomerPhoneNumber
   *          searchCustomerPhoneNumber
   */
  public void setSearchCustomerPhoneNumber(String searchCustomerPhoneNumber) {
    this.searchCustomerPhoneNumber = searchCustomerPhoneNumber;
  }

  /**
   * リクエストパラメータから値を取得します。

   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

    reqparam.copy(this);

    this.setSearchFromShippingDatetime(reqparam.getDateString("searchFromShippingDatetime"));
    this.setSearchToShippingDatetime(reqparam.getDateString("searchToShippingDatetime"));
    this.setSearchFromShippingDirectDate(reqparam.getDateString("searchFromShippingDirectDate"));
    this.setSearchToShippingDirectDate(reqparam.getDateString("searchToShippingDirectDate"));
    this.setSearchFromOrderDate(reqparam.getDateString("searchFromOrderDate"));
    this.setSearchToOrderDate(reqparam.getDateString("searchToOrderDate"));

    this.setSearchReturnItemType(reqparam.get("searchReturnItemType"));
    String uhd = reqparam.get("useHeader");
    if (StringUtil.hasValue(uhd) && (uhd.equalsIgnoreCase("true") || uhd.equalsIgnoreCase("false"))) {
      this.setUseHeader(uhd);
    } else {
      this.setUseHeader("false");
    }
    if (reqparam.get("sortAsc").equals("true")) {
      this.setSortAscFlg(true);
    } else {
      this.setSortAscFlg(false);
    }
    this.setUploadFile(reqparam.get("uploadFile"));

    edit.setInputShippingDate(reqparam.getDateString("inputShippingDate"));
    edit.setUpdateWithShipping(StringUtil.hasValue(reqparam.get("updateWithShipping")));

    reportEdit.setShippingReportDate(reqparam.getDateString("shippingReportDate"));

    String[] check = reqparam.getAll("shippingCheckbox");
    List<String> checkList = new ArrayList<String>();
    if (check != null && check.length > 0) {
      for (String str : check) {
        if (StringUtil.hasValue(str)) {
          checkList.add(str);
        }
      }
    }
    this.setShippingCheck(checkList);

    List<String> statusList = new ArrayList<String>();
    for (String s : reqparam.getAll("searchShippingStatusList")) {
      statusList.add(s);
    }
    if (StringUtil.hasValue(reqparam.get("uploadType")) && reqparam.get("uploadType").equals("import-shipping_report")) {
      return;
    } else {
      this.setSearchShippingStatus(statusList);
    }
    // 20120118 ysy add start
    this.setSearchOnlyEcOrder(true);
    this.setSearchOnlyTmallOrder(true);
    this.setSearchOnlyJDOrder(true);
    this.setSearchOnlyEcOrder(reqparam.get("searchShippingOrderStatus") == "0");
    this.setSearchOnlyTmallOrder(reqparam.get("searchShippingOrderStatus") == "1");
    this.setSearchOnlyJDOrder(reqparam.get("searchShippingOrderStatus") == "2");
    
    // 20120118 ysy add end

    this.setSearchDataTransportFlg(StringUtil.hasValue(reqparam.get("searchDataTransportFlg")));
    this.setSearchFixedSalesDataFlg(StringUtil.hasValue(reqparam.get("searchFixedSalesDataFlg")));
  }

  /**
   * searchReturnItemTypeを取得します。

   * 
   * @return searchReturnItemType
   */
  public String getSearchReturnItemType() {
    return searchReturnItemType;
  }

  /**
   * searchReturnItemTypeを設定します。

   * 
   * @param searchReturnItemType
   *          searchReturnItemType
   */
  public void setSearchReturnItemType(String searchReturnItemType) {
    this.searchReturnItemType = searchReturnItemType;
  }

  /**
   * shippingCheckを取得します。

   * 
   * @return shippingCheck
   */
  public List<String> getShippingCheck() {
    return shippingCheck;
  }

  /**
   * shippingCheckを設定します。

   * 
   * @param shippingCheck
   *          shippingCheck
   */
  public void setShippingCheck(List<String> shippingCheck) {
    this.shippingCheck = shippingCheck;
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
   * shippingInstructionsDisplayFlgを取得します。

   * 
   * @return shippingInstructionsDisplayFlg
   */
  public boolean isShippingInstructionsDisplayFlg() {
    return shippingInstructionsDisplayFlg;
  }

  /**
   * shippingInstructionsDisplayFlgを設定します。

   * 
   * @param shippingInstructionsDisplayFlg
   *          shippingInstructionsDisplayFlg
   */
  public void setShippingInstructionsDisplayFlg(boolean shippingInstructionsDisplayFlg) {
    this.shippingInstructionsDisplayFlg = shippingInstructionsDisplayFlg;
  }

  /**
   * shopListDisplayFlgを取得します。

   * 
   * @return shopListDisplayFlg
   */
  public boolean isShopListDisplayFlg() {
    return shopListDisplayFlg;
  }

  /**
   * shopListDisplayFlgを設定します。

   * 
   * @param shopListDisplayFlg
   *          shopListDisplayFlg
   */
  public void setShopListDisplayFlg(boolean shopListDisplayFlg) {
    this.shopListDisplayFlg = shopListDisplayFlg;
  }

  /**
   * editを取得します。

   * 
   * @return edit
   */
  public InputDirectBean getEdit() {
    return edit;
  }

  /**
   * editを設定します。

   * 
   * @param edit
   *          edit
   */
  public void setEdit(InputDirectBean edit) {
    this.edit = edit;
  }

  /**
   * listを取得します。

   * 
   * @return list
   */
  public List<ShippingSearchedListBean> getList() {
    return list;
  }

  /**
   * listを設定します。

   * 
   * @param list
   *          list
   */
  public void setList(List<ShippingSearchedListBean> list) {
    this.list = list;
  }

  /**
   * shippingImportDisplayFlgを取得します。

   * 
   * @return shippingImportDisplayFlg
   */
  public boolean isShippingImportDisplayFlg() {
    return shippingImportDisplayFlg;
  }

  /**
   * shippingImportDisplayFlgを設定します。

   * 
   * @param shippingImportDisplayFlg
   *          shippingImportDisplayFlg
   */
  public void setShippingImportDisplayFlg(boolean shippingImportDisplayFlg) {
    this.shippingImportDisplayFlg = shippingImportDisplayFlg;
  }

  /**
   * uploadFileを取得します。

   * 
   * @return uploadFile
   */
  public String getUploadFile() {
    return uploadFile;
  }

  /**
   * uploadFileを設定します。

   * 
   * @param uploadFile
   *          uploadFile
   */
  public void setUploadFile(String uploadFile) {
    this.uploadFile = uploadFile;
  }

  /**
   * useHeaderを返します。

   * 
   * @return the useHeader
   */
  public String getUseHeader() {
    return useHeader;
  }

  /**
   * useHeaderを設定します。

   * 
   * @param useHeader
   *          設定する useHeader
   */
  public void setUseHeader(String useHeader) {
    this.useHeader = useHeader;
  }

  /**
   * csvHeaderTypeを取得します。

   * 
   * @return csvHeaderType
   */
  public List<NameValue> getCsvHeaderType() {
    return csvHeaderType;
  }

  /**
   * csvHeaderTypeを設定します。

   * 
   * @param csvHeaderType
   *          csvHeaderType
   */
  public void setCsvHeaderType(List<NameValue> csvHeaderType) {
    this.csvHeaderType = csvHeaderType;
  }

  /**
   * errorMessageDetailListを取得します。

   * 
   * @return errorMessageDetailList
   */
  public List<String> getErrorMessageDetailList() {
    return errorMessageDetailList;
  }

  /**
   * errorMessageDetailListを設定します。

   * 
   * @param errorMessageDetailList
   *          errorMessageDetailList
   */
  public void setErrorMessageDetailList(List<String> errorMessageDetailList) {
    this.errorMessageDetailList = errorMessageDetailList;
  }

  /**
   * advancedSearchModeを取得します。

   * 
   * @return advancedSearchMode
   */
  public String getAdvancedSearchMode() {
    return advancedSearchMode;
  }

  /**
   * advancedSearchModeを設定します。

   * 
   * @param advancedSearchMode
   *          advancedSearchMode
   */
  public void setAdvancedSearchMode(String advancedSearchMode) {
    this.advancedSearchMode = advancedSearchMode;
  }

  /**
   * authorityIOを取得します。

   * 
   * @return authorityIO
   */
  public boolean getAuthorityIO() {
    return authorityIO;
  }

  /**
   * authorityIOを設定します。

   * 
   * @param authorityIO
   *          authorityIO
   */
  public void setAuthorityIO(boolean authorityIO) {
    this.authorityIO = authorityIO;
  }

  /**
   * deliveryTypeDisplayFlgを取得します。

   * 
   * @return deliveryTypeDisplayFlg
   */
  public boolean isDeliveryTypeDisplayFlg() {
    return deliveryTypeDisplayFlg;
  }

  /**
   * deliveryTypeDisplayFlgを設定します。

   * 
   * @param deliveryTypeDisplayFlg
   *          deliveryTypeDisplayFlg
   */
  public void setDeliveryTypeDisplayFlg(boolean deliveryTypeDisplayFlg) {
    this.deliveryTypeDisplayFlg = deliveryTypeDisplayFlg;
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
 * @return the searchDeliveryCompany
 */
public List<CodeAttribute> getSearchDeliveryCompany() {
	return searchDeliveryCompany;
}

/**
 * @param searchDeliveryCompany the searchDeliveryCompany to set
 */
public void setSearchDeliveryCompany(List<CodeAttribute> searchDeliveryCompany) {
	this.searchDeliveryCompany = searchDeliveryCompany;
}

/**
 * @return the deliveryCompanyDisplayFlg
 */
public boolean isDeliveryCompanyDisplayFlg() {
	return deliveryCompanyDisplayFlg;
}

/**
 * @param deliveryCompanyDisplayFlg the deliveryCompanyDisplayFlg to set
 */
public void setDeliveryCompanyDisplayFlg(boolean deliveryCompanyDisplayFlg) {
	this.deliveryCompanyDisplayFlg = deliveryCompanyDisplayFlg;
}

/**
   * sortAscFlgを取得します。

   * 
   * @return sortAscFlg
   */
  public boolean isSortAscFlg() {
    return sortAscFlg;
  }

  /**
   * sortAscFlgを設定します。

   * 
   * @param sortAscFlg
   *          sortAscFlg
   */
  public void setSortAscFlg(boolean sortAscFlg) {
    this.sortAscFlg = sortAscFlg;
  }

  /**
   * searchDataTransportFlgを取得します。

   * 
   * @return searchDataTransportFlg
   */
  public boolean isSearchDataTransportFlg() {
    return searchDataTransportFlg;
  }

  /**
   * searchDataTransportFlgを設定します。

   * 
   * @param searchDataTransportFlg
   *          searchDataTransportFlg
   */
  public void setSearchDataTransportFlg(boolean searchDataTransportFlg) {
    this.searchDataTransportFlg = searchDataTransportFlg;
  }

  /**
   * searchFixedSalesDataFlgを取得します。

   * 
   * @return searchFixedSalesDataFlg
   */
  public boolean isSearchFixedSalesDataFlg() {
    return searchFixedSalesDataFlg;
  }

  /**
   * searchFixedSalesDataFlgを設定します。

   * 
   * @param searchFixedSalesDataFlg
   *          searchFixedSalesDataFlg
   */
  public void setSearchFixedSalesDataFlg(boolean searchFixedSalesDataFlg) {
    this.searchFixedSalesDataFlg = searchFixedSalesDataFlg;
  }

  /**
   * searchFromOrderDateを取得します。

   * 
   * @return searchFromOrderDate
   */
  public String getSearchFromOrderDate() {
    return searchFromOrderDate;
  }

  /**
   * searchFromOrderDateを設定します。

   * 
   * @param searchFromOrderDate
   *          searchFromOrderDate
   */
  public void setSearchFromOrderDate(String searchFromOrderDate) {
    this.searchFromOrderDate = searchFromOrderDate;
  }

  /**
   * searchToOrderDateを取得します。

   * 
   * @return searchToOrderDate
   */
  public String getSearchToOrderDate() {
    return searchToOrderDate;
  }

  /**
   * searchToOrderDateを設定します。

   * 
   * @param searchToOrderDate
   *          searchToOrderDate
   */
  public void setSearchToOrderDate(String searchToOrderDate) {
    this.searchToOrderDate = searchToOrderDate;
  }

  /**
   * reportEditを取得します。

   * 
   * @return reportEdit
   */
  public ShippingReportBean getReportEdit() {
    return reportEdit;
  }

  /**
   * reportEditを設定します。

   * 
   * @param reportEdit
   *          reportEdit
   */
  public void setReportEdit(ShippingReportBean reportEdit) {
    this.reportEdit = reportEdit;
  }

  /**
   * 出荷指示日選択区分を取得します。

   * 
   * @return 出荷指示日選択区分のリスト

   */
  public List<NameValue> getShippingDirectDateStatus() {
    return NameValue.asList(":"
        + Messages.getString("web.bean.back.order.ShippingListBean.6")
        + "/0:"
        + Messages.getString("web.bean.back.order.ShippingListBean.7")
        + "/1:"
        + Messages.getString("web.bean.back.order.ShippingListBean.8"));
  }

  // 20120116 ysy add start
  public List<NameValue> getShippingOrderStatus() {
	    return NameValue.asList(":"
	        + Messages.getString("web.bean.back.order.ShippingListBean.9")
	        + "/0:"
	        + Messages.getString("web.bean.back.order.ShippingListBean.10")
	        + "/1:"
	        + Messages.getString("web.bean.back.order.ShippingListBean.11")
	        + "/2:"
	        + Messages.getString("web.bean.back.order.ShippingListBean.12"));
	  }
  // 20120116 ysy add end
  /**
 * @return the searchShippingOrderStatus
 */
public String getSearchShippingOrderStatus() {
	return searchShippingOrderStatus;
}

/**
 * @param searchShippingOrderStatus the searchShippingOrderStatus to set
 */
public void setSearchShippingOrderStatus(String searchShippingOrderStatus) {
	this.searchShippingOrderStatus = searchShippingOrderStatus;
}

/**
   * searchShippingDirectStatusを取得します。

   * 
   * @return searchShippingDirectStatus
   */
  public String getSearchShippingDirectStatus() {
    return searchShippingDirectStatus;
  }

  /**
   * searchShippingDirectStatusを設定します。

   * 
   * @param searchShippingDirectStatus
   *          searchShippingDirectStatus
   */
  public void setSearchShippingDirectStatus(String searchShippingDirectStatus) {
    this.searchShippingDirectStatus = searchShippingDirectStatus;
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
}
