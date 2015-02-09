package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Pattern;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020420:出荷管理明細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingDetailBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private boolean displayFlg;

  private boolean displayDirectFlg;

  private boolean siteOperationFlg;

  private boolean shippingReportDisplayFlg;

  private OrderHeaderBean orderHeader = new OrderHeaderBean();

  private DeliveryHeaderBean deliveryHeader = new DeliveryHeaderBean();

  private List<DeliveryDetailBean> deliveryDetailList = new ArrayList<DeliveryDetailBean>();

  private String updateEditMode;

  private String updateDirectEditMode;

  /** 出荷指示日入力時、出荷指示も行うフラグ */
  private boolean updateWithShipping;

  /**
   * U1020420:出荷管理明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderHeaderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 受注番号 */
    @Required
    @Length(16)
    // @Digit
    @Metadata(name = "受注番号")
    private String orderNo;

    /** 受注日時 */
    private String orderDatetime;

    /** 連絡事項 */
    @Length(200)
    @Metadata(name = "連絡事項")
    private String message;

    /** 注意事項（管理側のみ参照） */
    @Length(200)
    @Metadata(name = "注意事項（管理側のみ参照）")
    private String caution;

    /** 顧客コード */
    private String customerCode;

    /** お客様名 */
    private String customerFirstName;

    /** お客様姓 */
    private String customerLastName;

    /** お客様名カナ */
    private String customerFirstNameKana;

    /** お客様姓カナ */
    private String customerLastNameKana;

    /** お客様電話番号 */
    private String customerPhoneNumber;

    /** 手机号码 */
    private String customerMobileNumber;

    /** お客様メールアドレス */
    private String customerEmail;

    /** 支払方法 */
    private String paymentMethodName;

    /** 後先払いフラグ */
    private String advanceLaterName;

    /** データ連携フラグ */
    private String dataTransportStatus;

    /** 更新ユーザ */
    private String orderHeaderUpdatedUser;

    /** 更新日時 */
    private Date orderHeaderUpdatedDatetime;

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
     * customerCodeを取得します。
     * 
     * @return customerCode
     */
    public String getCustomerCode() {
      return customerCode;
    }

    /**
     * customerCodeを設定します。
     * 
     * @param customerCode
     *          customerCode
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }

    /**
     * customerEmailを取得します。
     * 
     * @return customerEmail
     */
    public String getCustomerEmail() {
      return customerEmail;
    }

    /**
     * customerEmailを設定します。
     * 
     * @param customerEmail
     *          customerEmail
     */
    public void setCustomerEmail(String customerEmail) {
      this.customerEmail = customerEmail;
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
     * customerFirstNameKanaを取得します。
     * 
     * @return customerFirstNameKana
     */
    public String getCustomerFirstNameKana() {
      return customerFirstNameKana;
    }

    /**
     * customerFirstNameKanaを設定します。
     * 
     * @param customerFirstNameKana
     *          customerFirstNameKana
     */
    public void setCustomerFirstNameKana(String customerFirstNameKana) {
      this.customerFirstNameKana = customerFirstNameKana;
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
     * customerLastNameKanaを取得します。
     * 
     * @return customerLastNameKana
     */
    public String getCustomerLastNameKana() {
      return customerLastNameKana;
    }

    /**
     * customerLastNameKanaを設定します。
     * 
     * @param customerLastNameKana
     *          customerLastNameKana
     */
    public void setCustomerLastNameKana(String customerLastNameKana) {
      this.customerLastNameKana = customerLastNameKana;
    }

    /**
     * customerPhoneNumberを取得します。
     * 
     * @return customerPhoneNumber
     */
    public String getCustomerPhoneNumber() {
      return customerPhoneNumber;
    }

    /**
     * customerPhoneNumberを設定します。
     * 
     * @param customerPhoneNumber
     *          customerPhoneNumber
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
      this.customerPhoneNumber = customerPhoneNumber;
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
     * orderHeaderUpdatedDatetimeを取得します。
     * 
     * @return orderHeaderUpdatedDatetime
     */
    public Date getOrderHeaderUpdatedDatetime() {
      return DateUtil.immutableCopy(orderHeaderUpdatedDatetime);
    }

    /**
     * orderHeaderUpdatedDatetimeを設定します。
     * 
     * @param orderHeaderUpdatedDatetime
     *          orderHeaderUpdatedDatetime
     */
    public void setOrderHeaderUpdatedDatetime(Date orderHeaderUpdatedDatetime) {
      this.orderHeaderUpdatedDatetime = DateUtil.immutableCopy(orderHeaderUpdatedDatetime);
    }

    /**
     * orderHeaderUpdatedUserを取得します。
     * 
     * @return orderHeaderUpdatedUser
     */
    public String getOrderHeaderUpdatedUser() {
      return orderHeaderUpdatedUser;
    }

    /**
     * orderHeaderUpdatedUserを設定します。
     * 
     * @param orderHeaderUpdatedUser
     *          orderHeaderUpdatedUser
     */
    public void setOrderHeaderUpdatedUser(String orderHeaderUpdatedUser) {
      this.orderHeaderUpdatedUser = orderHeaderUpdatedUser;
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
     * orderNoを設定します。
     * 
     * @param orderNo
     *          orderNo
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    /**
     * advanceLaterNameを取得します。
     * 
     * @return advanceLaterName
     */
    public String getAdvanceLaterName() {
      return advanceLaterName;
    }

    /**
     * advanceLaterNameを設定します。
     * 
     * @param advanceLaterName
     *          advanceLaterName
     */
    public void setAdvanceLaterName(String advanceLaterName) {
      this.advanceLaterName = advanceLaterName;
    }

    /**
     * paymentMethodNameを取得します。
     * 
     * @return paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
    }

    /**
     * paymentMethodNameを設定します。
     * 
     * @param paymentMethodName
     *          paymentMethodName
     */
    public void setPaymentMethodName(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
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
     * orderDatetimeを設定します。
     * 
     * @param orderDatetime
     *          orderDatetime
     */
    public void setOrderDatetime(String orderDatetime) {
      this.orderDatetime = orderDatetime;
    }

    /**
     * dataTransportStatusを取得します。
     * 
     * @return dataTransportStatus
     */
    public String getDataTransportStatus() {
      return dataTransportStatus;
    }

    /**
     * dataTransportStatusを設定します。
     * 
     * @param dataTransportStatus
     *          dataTransportStatus
     */
    public void setDataTransportStatus(String dataTransportStatus) {
      this.dataTransportStatus = dataTransportStatus;
    }

    
    /**
     * customerMobileNumberを取得します。
     *
     * @return customerMobileNumber customerMobileNumber
     */
    public String getCustomerMobileNumber() {
      return customerMobileNumber;
    }

    
    /**
     * customerMobileNumberを設定します。
     *
     * @param customerMobileNumber 
     *          customerMobileNumber
     */
    public void setCustomerMobileNumber(String customerMobileNumber) {
      this.customerMobileNumber = customerMobileNumber;
    }

  }

  /**
   * U1020420:出荷管理明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DeliveryHeaderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 出荷番号 */
    @Required
    @Length(16)
    // @Digit
    @Metadata(name = "出荷番号")
    private String shippingNo;

    /** 出荷日 */
    @Required
    @Datetime
    @Metadata(name = "出荷日")
    private String shippingDate;

    @Required
    @Datetime
    @Metadata(name = "出荷指示日")
    private String shippingDirectDate;

    /** 宅配便伝票番号 */
    @Length(30)
    @Pattern(value = "[0-9]{0,30}", message = "30桁以下の数値を入力してください")
    @Metadata(name = "宅配便伝票番号")
    private String deliverySlipNo;

    /** 到着日 */
    @Datetime
    @Metadata(name = "到着予定日")
    private String arrivalDate;

    /** 到着時間Start */
    @Metadata(name = "到着予定時刻(From)")
    private String arrivalTimeStart;

    /** 到着時間End */
    @Metadata(name = "到着予定時刻(To)")
    private String arrivalTimeEnd;

    /** 出荷ステータス */
    private String shippingStatus;

    /** 更新ユーザ */
    private String shippingHeaderUpdatedUser;

    /** 更新日時 */
    private Date shippingHeaderUpdatedDatetime;

    /** 元出荷番号 */
    private String originalShippingNo;

    /** 返品日 */
    private String returnItemDate;

    /** 返品損金額 */
    private String returnItemLossMoney;

    /** 返品区分 */
    private String returnItemType;

    /** ショップコード */
    private String shopCode;

    /** ショップ名 */
    private String shopName;

    /** 配送種別番号 */
    private String deliveryTypeNo;

    /** 配送種別名 */
    private String deliveryTypeName;

    /** 宛名姓 */
    private String lastName;

    /** 宛名名 */
    private String firstName;

    /** 宛名姓カナ */
    private String lastNameKana;

    /** 宛名名カナ */
    private String firstNameKana;

    /** 郵便番号 */
    private String postalCode;

    /** 都道府県コード */
    private String prefectureCode;

    /** 都道府県 */
    private String address1;

    /** 住所2 */
    private String address2;

    /** 住所3 */
    private String address3;

    /** 住所4 */
    private String address4;

    /** 配送指定日 */
    private String deliveryAppointedDate;

    /** 配送指定時間開始 */
    private String deliveryAppointedTimeStart;

    /** 配送指定時間終了 */
    private String deliveryAppointedTimeEnd;

    /** 配送先備考 */
    private String deliveryRemark;

    /** 送料 */
    private String deliveryCharge;

    /** 送料消費税区分 */
    private String deliveryChargeTaxType;

    /** 配送先電話番号 */
    private String deliveryTel;
    
    /** 配送手机号码 */
    private String deliveryMobile;

    /** 売上確定ステータス */
    private String fixedSalesStatus;
    
    // 20120130 ysy add start
    private String deliveryCompanyNo;
    
    private String deliveryCompanyName;
    
    private String deliveryShipNoSum;
    // 20120130 ysy add end
 
    
	/**
     * fixedSalesStatusを取得します。
     * 
     * @return fixedSalesStatus
     */
    public String getFixedSalesStatus() {
      return fixedSalesStatus;
    }

    /**
	 * @return the deliveryShipNoSum
	 */
	public String getDeliveryShipNoSum() {
		return deliveryShipNoSum;
	}

	/**
	 * @param deliveryShipNoSum the deliveryShipNoSum to set
	 */
	public void setDeliveryShipNoSum(String deliveryShipNoSum) {
		this.deliveryShipNoSum = deliveryShipNoSum;
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
     * fixedSalesStatusを設定します。
     * 
     * @param fixedSalesStatus
     *          fixedSalesStatus
     */
    public void setFixedSalesStatus(String fixedSalesStatus) {
      this.fixedSalesStatus = fixedSalesStatus;
    }

    /**
     * address1を取得します。
     * 
     * @return address1
     */
    public String getAddress1() {
      return address1;
    }

    /**
     * address1を設定します。
     * 
     * @param address1
     *          address1
     */
    public void setAddress1(String address1) {
      this.address1 = address1;
    }

    /**
     * address2を取得します。
     * 
     * @return address2
     */
    public String getAddress2() {
      return address2;
    }

    /**
     * address2を設定します。
     * 
     * @param address2
     *          address2
     */
    public void setAddress2(String address2) {
      this.address2 = address2;
    }

    /**
     * address3を取得します。
     * 
     * @return address3
     */
    public String getAddress3() {
      return address3;
    }

    /**
     * address3を設定します。
     * 
     * @param address3
     *          address3
     */
    public void setAddress3(String address3) {
      this.address3 = address3;
    }

    /**
     * address4を取得します。
     * 
     * @return address4
     */
    public String getAddress4() {
      return address4;
    }

    /**
     * address4を設定します。
     * 
     * @param address4
     *          address4
     */
    public void setAddress4(String address4) {
      this.address4 = address4;
    }

    /**
     * deliveryAppointedDateを取得します。
     * 
     * @return deliveryAppointedDate
     */
    public String getDeliveryAppointedDate() {
      return deliveryAppointedDate;
    }

    /**
     * deliveryAppointedDateを設定します。
     * 
     * @param deliveryAppointedDate
     *          deliveryAppointedDate
     */
    public void setDeliveryAppointedDate(String deliveryAppointedDate) {
      this.deliveryAppointedDate = deliveryAppointedDate;
    }

    /**
     * deliveryAppointedTimeEndを取得します。
     * 
     * @return deliveryAppointedTimeEnd
     */
    public String getDeliveryAppointedTimeEnd() {
      return deliveryAppointedTimeEnd;
    }

    /**
     * deliveryAppointedTimeEndを設定します。
     * 
     * @param deliveryAppointedTimeEnd
     *          deliveryAppointedTimeEnd
     */
    public void setDeliveryAppointedTimeEnd(String deliveryAppointedTimeEnd) {
      this.deliveryAppointedTimeEnd = deliveryAppointedTimeEnd;
    }

    /**
     * deliveryAppointedTimeStartを取得します。
     * 
     * @return deliveryAppointedTimeStart
     */
    public String getDeliveryAppointedTimeStart() {
      return deliveryAppointedTimeStart;
    }

    /**
     * deliveryAppointedTimeStartを設定します。
     * 
     * @param deliveryAppointedTimeStart
     *          deliveryAppointedTimeStart
     */
    public void setDeliveryAppointedTimeStart(String deliveryAppointedTimeStart) {
      this.deliveryAppointedTimeStart = deliveryAppointedTimeStart;
    }

    /**
     * deliveryChargeを取得します。
     * 
     * @return deliveryCharge
     */
    public String getDeliveryCharge() {
      return deliveryCharge;
    }

    /**
     * deliveryChargeを設定します。
     * 
     * @param deliveryCharge
     *          deliveryCharge
     */
    public void setDeliveryCharge(String deliveryCharge) {
      this.deliveryCharge = deliveryCharge;
    }

    /**
     * deliveryChargeTaxTypeを取得します。
     * 
     * @return deliveryChargeTaxType
     */
    public String getDeliveryChargeTaxType() {
      return deliveryChargeTaxType;
    }

    /**
     * deliveryChargeTaxTypeを設定します。
     * 
     * @param deliveryChargeTaxType
     *          deliveryChargeTaxType
     */
    public void setDeliveryChargeTaxType(String deliveryChargeTaxType) {
      this.deliveryChargeTaxType = deliveryChargeTaxType;
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

    /**
     * deliveryTelを取得します。
     * 
     * @return deliveryTel
     */
    public String getDeliveryTel() {
      return deliveryTel;
    }

    /**
     * deliveryTelを設定します。
     * 
     * @param deliveryTel
     *          deliveryTel
     */
    public void setDeliveryTel(String deliveryTel) {
      this.deliveryTel = deliveryTel;
    }

    /**
     * deliveryTypeNameを取得します。
     * 
     * @return deliveryTypeName
     */
    public String getDeliveryTypeName() {
      return deliveryTypeName;
    }

    /**
     * deliveryTypeNameを設定します。
     * 
     * @param deliveryTypeName
     *          deliveryTypeName
     */
    public void setDeliveryTypeName(String deliveryTypeName) {
      this.deliveryTypeName = deliveryTypeName;
    }

    /**
     * deliveryTypeNoを取得します。
     * 
     * @return deliveryTypeNo
     */
    public String getDeliveryTypeNo() {
      return deliveryTypeNo;
    }

    /**
     * deliveryTypeNoを設定します。
     * 
     * @param deliveryTypeNo
     *          deliveryTypeNo
     */
    public void setDeliveryTypeNo(String deliveryTypeNo) {
      this.deliveryTypeNo = deliveryTypeNo;
    }

    /**
     * firstNameを取得します。
     * 
     * @return firstName
     */
    public String getFirstName() {
      return firstName;
    }

    /**
     * firstNameを設定します。
     * 
     * @param firstName
     *          firstName
     */
    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    /**
     * lastNameを取得します。
     * 
     * @return lastName
     */
    public String getLastName() {
      return lastName;
    }

    /**
     * lastNameを設定します。
     * 
     * @param lastName
     *          lastName
     */
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    /**
     * postalCodeを取得します。
     * 
     * @return postalCode
     */
    public String getPostalCode() {
      return postalCode;
    }

    /**
     * postalCodeを設定します。
     * 
     * @param postalCode
     *          postalCode
     */
    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }

    /**
     * prefectureCodeを取得します。
     * 
     * @return prefectureCode
     */
    public String getPrefectureCode() {
      return prefectureCode;
    }

    /**
     * prefectureCodeを設定します。
     * 
     * @param prefectureCode
     *          prefectureCode
     */
    public void setPrefectureCode(String prefectureCode) {
      this.prefectureCode = prefectureCode;
    }

    /**
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * shopNameを取得します。
     * 
     * @return shopName
     */
    public String getShopName() {
      return shopName;
    }

    /**
     * shopNameを設定します。
     * 
     * @param shopName
     *          shopName
     */
    public void setShopName(String shopName) {
      this.shopName = shopName;
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
     * arrivalTimeEndを取得します。
     * 
     * @return arrivalTimeEnd
     */
    public String getArrivalTimeEnd() {
      return arrivalTimeEnd;
    }

    /**
     * arrivalTimeEndを設定します。
     * 
     * @param arrivalTimeEnd
     *          arrivalTimeEnd
     */
    public void setArrivalTimeEnd(String arrivalTimeEnd) {
      this.arrivalTimeEnd = arrivalTimeEnd;
    }

    /**
     * arrivalTimeStartを取得します。
     * 
     * @return arrivalTimeStart
     */
    public String getArrivalTimeStart() {
      return arrivalTimeStart;
    }

    /**
     * arrivalTimeStartを設定します。
     * 
     * @param arrivalTimeStart
     *          arrivalTimeStart
     */
    public void setArrivalTimeStart(String arrivalTimeStart) {
      this.arrivalTimeStart = arrivalTimeStart;
    }

    /**
     * deliverySlipNoを取得します。
     * 
     * @return deliverySlipNo
     */
    public String getDeliverySlipNo() {
      return deliverySlipNo;
    }

    /**
     * deliverySlipNoを設定します。
     * 
     * @param deliverySlipNo
     *          deliverySlipNo
     */
    public void setDeliverySlipNo(String deliverySlipNo) {
      this.deliverySlipNo = deliverySlipNo;
    }

    /**
     * originalShippingNoを取得します。
     * 
     * @return originalShippingNo
     */
    public String getOriginalShippingNo() {
      return originalShippingNo;
    }

    /**
     * originalShippingNoを設定します。
     * 
     * @param originalShippingNo
     *          originalShippingNo
     */
    public void setOriginalShippingNo(String originalShippingNo) {
      this.originalShippingNo = originalShippingNo;
    }

    /**
     * returnItemDateを取得します。
     * 
     * @return returnItemDate
     */
    public String getReturnItemDate() {
      return returnItemDate;
    }

    /**
     * returnItemDateを設定します。
     * 
     * @param returnItemDate
     *          returnItemDate
     */
    public void setReturnItemDate(String returnItemDate) {
      this.returnItemDate = returnItemDate;
    }

    /**
     * returnItemLossMoneyを取得します。
     * 
     * @return returnItemLossMoney
     */
    public String getReturnItemLossMoney() {
      return returnItemLossMoney;
    }

    /**
     * returnItemLossMoneyを設定します。
     * 
     * @param returnItemLossMoney
     *          returnItemLossMoney
     */
    public void setReturnItemLossMoney(String returnItemLossMoney) {
      this.returnItemLossMoney = returnItemLossMoney;
    }

    /**
     * returnItemTypeを取得します。
     * 
     * @return returnItemType
     */
    public String getReturnItemType() {
      return returnItemType;
    }

    /**
     * returnItemTypeを設定します。
     * 
     * @param returnItemType
     *          returnItemType
     */
    public void setReturnItemType(String returnItemType) {
      this.returnItemType = returnItemType;
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
     * shippingDateを設定します。
     * 
     * @param shippingDate
     *          shippingDate
     */
    public void setShippingDate(String shippingDate) {
      this.shippingDate = shippingDate;
    }

    /**
     * shippingHeaderUpdatedDatetimeを取得します。
     * 
     * @return shippingHeaderUpdatedDatetime
     */
    public Date getShippingHeaderUpdatedDatetime() {
      return DateUtil.immutableCopy(shippingHeaderUpdatedDatetime);
    }

    /**
     * shippingHeaderUpdatedDatetimeを設定します。
     * 
     * @param shippingHeaderUpdatedDatetime
     *          shippingHeaderUpdatedDatetime
     */
    public void setShippingHeaderUpdatedDatetime(Date shippingHeaderUpdatedDatetime) {
      this.shippingHeaderUpdatedDatetime = DateUtil.immutableCopy(shippingHeaderUpdatedDatetime);
    }

    /**
     * shippingHeaderUpdatedUserを取得します。
     * 
     * @return shippingHeaderUpdatedUser
     */
    public String getShippingHeaderUpdatedUser() {
      return shippingHeaderUpdatedUser;
    }

    /**
     * shippingHeaderUpdatedUserを設定します。
     * 
     * @param shippingHeaderUpdatedUser
     *          shippingHeaderUpdatedUser
     */
    public void setShippingHeaderUpdatedUser(String shippingHeaderUpdatedUser) {
      this.shippingHeaderUpdatedUser = shippingHeaderUpdatedUser;
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
     * shippingNoを設定します。
     * 
     * @param shippingNo
     *          shippingNo
     */
    public void setShippingNo(String shippingNo) {
      this.shippingNo = shippingNo;
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
     * shippingStatusを設定します。
     * 
     * @param shippingStatus
     *          shippingStatus
     */
    public void setShippingStatus(String shippingStatus) {
      this.shippingStatus = shippingStatus;
    }

    /**
     * firstNameKanaを取得します。
     * 
     * @return firstNameKana
     */
    public String getFirstNameKana() {
      return firstNameKana;
    }

    /**
     * firstNameKanaを設定します。
     * 
     * @param firstNameKana
     *          firstNameKana
     */
    public void setFirstNameKana(String firstNameKana) {
      this.firstNameKana = firstNameKana;
    }

    /**
     * lastNameKanaを取得します。
     * 
     * @return lastNameKana
     */
    public String getLastNameKana() {
      return lastNameKana;
    }

    /**
     * lastNameKanaを設定します。
     * 
     * @param lastNameKana
     *          lastNameKana
     */
    public void setLastNameKana(String lastNameKana) {
      this.lastNameKana = lastNameKana;
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
     * shippingDirectDateを設定します。
     * 
     * @param shippingDirectDate
     *          shippingDirectDate
     */
    public void setShippingDirectDate(String shippingDirectDate) {
      this.shippingDirectDate = shippingDirectDate;
    }

    
    /**
     * deliveryMobileを取得します。
     *
     * @return deliveryMobile deliveryMobile
     */
    public String getDeliveryMobile() {
      return deliveryMobile;
    }

    
    /**
     * deliveryMobileを設定します。
     *
     * @param deliveryMobile 
     *          deliveryMobile
     */
    public void setDeliveryMobile(String deliveryMobile) {
      this.deliveryMobile = deliveryMobile;
    }

  }

  /**
   * U1020420:出荷管理明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DeliveryDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 出荷明細番号 */
    private String shippingDetailNo;

    /** SKUコード */
    private String skuCode;

    /** 商品名 */
    private String comodityName;

    /** 規格詳細1名称 */
    private String standardDetail1Name;

    /** 規格詳細2名称 */
    private String standardDetail2Name;

    /** 購入商品数 */
    private String purchasingAmount;

    /** 販売価格 */
    private String retailPrice;

    /** 商品消費税額 */
    private String commodityTaxType;

    /** 販売時消費税額 */
    private String retailTaxPrice;

    /** ギフトコード */
    private String detailGiftCode;

    /** ギフト名 */
    private String detailGiftName;

    /** ギフト価格 */
    private String detailGiftPrice;

    /** ギフト消費税率 */
    private String detailGiftTaxRate;

    /** ギフト消費税区分 */
    private String detailGiftTaxType;

    /** 小計 */
    private String detailSummaryPrice;
    
    // 20120130 ysy add start
    private String deliveryShipNoSum;
    // 20120130 ysy add end
    
    /**
	 * @return the deliveryShipNoSum
	 */
	public String getDeliveryShipNoSum() {
		return deliveryShipNoSum;
	}

	/**
	 * @param deliveryShipNoSum the deliveryShipNoSum to set
	 */
	public void setDeliveryShipNoSum(String deliveryShipNoSum) {
		this.deliveryShipNoSum = deliveryShipNoSum;
	}

	/**
     * comodityNameを取得します。
     * 
     * @return comodityName
     */
    public String getComodityName() {
      return comodityName;
    }

    /**
     * comodityNameを設定します。
     * 
     * @param comodityName
     *          comodityName
     */
    public void setComodityName(String comodityName) {
      this.comodityName = comodityName;
    }

    /**
     * standardDetail1Nameを取得します。
     * 
     * @return standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    /**
     * standardDetail2Nameを取得します。
     * 
     * @return standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    /**
     * standardDetail1Nameを設定します。
     * 
     * @param standardDetail1Name
     *          standardDetail1Name
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    /**
     * standardDetail2Nameを設定します。
     * 
     * @param standardDetail2Name
     *          standardDetail2Name
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    /**
     * detailGiftCodeを取得します。
     * 
     * @return detailGiftCode
     */
    public String getDetailGiftCode() {
      return detailGiftCode;
    }

    /**
     * detailGiftCodeを設定します。
     * 
     * @param detailGiftCode
     *          detailGiftCode
     */
    public void setDetailGiftCode(String detailGiftCode) {
      this.detailGiftCode = detailGiftCode;
    }

    /**
     * detailGiftNameを取得します。
     * 
     * @return detailGiftName
     */
    public String getDetailGiftName() {
      return detailGiftName;
    }

    /**
     * detailGiftNameを設定します。
     * 
     * @param detailGiftName
     *          detailGiftName
     */
    public void setDetailGiftName(String detailGiftName) {
      this.detailGiftName = detailGiftName;
    }

    /**
     * detailGiftPriceを取得します。
     * 
     * @return detailGiftPrice
     */
    public String getDetailGiftPrice() {
      return detailGiftPrice;
    }

    /**
     * detailGiftPriceを設定します。
     * 
     * @param detailGiftPrice
     *          detailGiftPrice
     */
    public void setDetailGiftPrice(String detailGiftPrice) {
      this.detailGiftPrice = detailGiftPrice;
    }

    /**
     * detailGiftTaxTypeを取得します。
     * 
     * @return detailGiftTaxType
     */
    public String getDetailGiftTaxType() {
      return detailGiftTaxType;
    }

    /**
     * detailGiftTaxTypeを設定します。
     * 
     * @param detailGiftTaxType
     *          detailGiftTaxType
     */
    public void setDetailGiftTaxType(String detailGiftTaxType) {
      this.detailGiftTaxType = detailGiftTaxType;
    }

    /**
     * detailSummaryPriceを取得します。
     * 
     * @return detailSummaryPrice
     */
    public String getDetailSummaryPrice() {
      return detailSummaryPrice;
    }

    /**
     * detailSummaryPriceを設定します。
     * 
     * @param detailSummaryPrice
     *          detailSummaryPrice
     */
    public void setDetailSummaryPrice(String detailSummaryPrice) {
      this.detailSummaryPrice = detailSummaryPrice;
    }

    /**
     * purchasingAmountを取得します。
     * 
     * @return purchasingAmount
     */
    public String getPurchasingAmount() {
      return purchasingAmount;
    }

    /**
     * purchasingAmountを設定します。
     * 
     * @param purchasingAmount
     *          purchasingAmount
     */
    public void setPurchasingAmount(String purchasingAmount) {
      this.purchasingAmount = purchasingAmount;
    }

    /**
     * retailPriceを取得します。
     * 
     * @return retailPrice
     */
    public String getRetailPrice() {
      return retailPrice;
    }

    /**
     * retailPriceを設定します。
     * 
     * @param retailPrice
     *          retailPrice
     */
    public void setRetailPrice(String retailPrice) {
      this.retailPrice = retailPrice;
    }

    /**
     * retailTaxPriceを取得します。
     * 
     * @return retailTaxPrice
     */
    public String getRetailTaxPrice() {
      return retailTaxPrice;
    }

    /**
     * retailTaxPriceを設定します。
     * 
     * @param retailTaxPrice
     *          retailTaxPrice
     */
    public void setRetailTaxPrice(String retailTaxPrice) {
      this.retailTaxPrice = retailTaxPrice;
    }

    /**
     * shippingDetailNoを取得します。
     * 
     * @return shippingDetailNo
     */
    public String getShippingDetailNo() {
      return shippingDetailNo;
    }

    /**
     * shippingDetailNoを設定します。
     * 
     * @param shippingDetailNo
     *          shippingDetailNo
     */
    public void setShippingDetailNo(String shippingDetailNo) {
      this.shippingDetailNo = shippingDetailNo;
    }

    /**
     * skuCodeを取得します。
     * 
     * @return skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    /**
     * skuCodeを設定します。
     * 
     * @param skuCode
     *          skuCode
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    /**
     * commodityTaxTypeを取得します。
     * 
     * @return commodityTaxType
     */
    public String getCommodityTaxType() {
      return commodityTaxType;
    }

    /**
     * commodityTaxTypeを設定します。
     * 
     * @param commodityTaxType
     *          commodityTaxType
     */
    public void setCommodityTaxType(String commodityTaxType) {
      this.commodityTaxType = commodityTaxType;
    }

    /**
     * detailGiftTaxRateを取得します。
     * 
     * @return detailGiftTaxRate
     */
    public String getDetailGiftTaxRate() {
      return detailGiftTaxRate;
    }

    /**
     * detailGiftTaxRateを設定します。
     * 
     * @param detailGiftTaxRate
     *          detailGiftTaxRate
     */
    public void setDetailGiftTaxRate(String detailGiftTaxRate) {
      this.detailGiftTaxRate = detailGiftTaxRate;
    }

  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.updateWithShipping = StringUtil.hasValue(reqparam.get("updateWithShipping"));
    this.orderHeader.caution = reqparam.get("caution");
    this.orderHeader.message = reqparam.get("message");
    this.deliveryHeader.deliverySlipNo = reqparam.get("deliverySlipNo");
    this.deliveryHeader.shippingDate = reqparam.getDateString("shippingDate");
    this.deliveryHeader.arrivalDate = reqparam.getDateString("arrivalDate");
    this.deliveryHeader.arrivalTimeStart = reqparam.get("arrivalTimeStart");
    this.deliveryHeader.arrivalTimeEnd = reqparam.get("arrivalTimeEnd");
    this.deliveryHeader.shippingDirectDate = reqparam.getDateString("shippingDirectDate");
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020420";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.ShippingDetailBean.0");
  }

  /**
   * displayFlgを取得します。
   * 
   * @return displayFlg
   */
  public boolean isDisplayFlg() {
    return displayFlg;
  }

  /**
   * displayFlgを設定します。
   * 
   * @param displayFlg
   *          displayFlg
   */
  public void setDisplayFlg(boolean displayFlg) {
    this.displayFlg = displayFlg;
  }

  /**
   * deliveryHeaderを取得します。
   * 
   * @return deliveryHeader
   */
  public DeliveryHeaderBean getDeliveryHeader() {
    return deliveryHeader;
  }

  /**
   * deliveryHeaderを設定します。
   * 
   * @param deliveryHeader
   *          deliveryHeader
   */
  public void setDeliveryHeader(DeliveryHeaderBean deliveryHeader) {
    this.deliveryHeader = deliveryHeader;
  }

  /**
   * deliveryDetailListを取得します。
   * 
   * @return deliveryDetailList
   */
  public List<DeliveryDetailBean> getDeliveryDetailList() {
    return deliveryDetailList;
  }

  /**
   * deliveryDetailListを設定します。
   * 
   * @param deliveryDetailList
   *          deliveryDetailList
   */
  public void setDeliveryDetailList(List<DeliveryDetailBean> deliveryDetailList) {
    this.deliveryDetailList = deliveryDetailList;
  }

  /**
   * orderHeaderを取得します。
   * 
   * @return orderHeader
   */
  public OrderHeaderBean getOrderHeader() {
    return orderHeader;
  }

  /**
   * orderHeaderを設定します。
   * 
   * @param orderHeader
   *          orderHeader
   */
  public void setOrderHeader(OrderHeaderBean orderHeader) {
    this.orderHeader = orderHeader;
  }

  /**
   * siteOperationFlgを取得します。
   * 
   * @return siteOperationFlg
   */
  public boolean getSiteOperationFlg() {
    return siteOperationFlg;
  }

  /**
   * siteOperationFlgを設定します。
   * 
   * @param siteOperationFlg
   *          siteOperationFlg
   */
  public void setSiteOperationFlg(boolean siteOperationFlg) {
    this.siteOperationFlg = siteOperationFlg;
  }

  /**
   * updateEditModeを取得します。
   * 
   * @return updateEditMode
   */
  public String getUpdateEditMode() {
    return updateEditMode;
  }

  /**
   * updateEditModeを設定します。
   * 
   * @param updateEditMode
   *          updateEditMode
   */
  public void setUpdateEditMode(String updateEditMode) {
    this.updateEditMode = updateEditMode;
  }

  /**
   * shippingReportDisplayFlgを取得します。
   * 
   * @return shippingReportDisplayFlg
   */
  public boolean isShippingReportDisplayFlg() {
    return shippingReportDisplayFlg;
  }

  /**
   * shippingReportDisplayFlgを設定します。
   * 
   * @param shippingReportDisplayFlg
   *          shippingReportDisplayFlg
   */
  public void setShippingReportDisplayFlg(boolean shippingReportDisplayFlg) {
    this.shippingReportDisplayFlg = shippingReportDisplayFlg;
  }

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
   * displayDirectFlgを取得します。
   * 
   * @return displayDirectFlg
   */
  public boolean isDisplayDirectFlg() {
    return displayDirectFlg;
  }

  /**
   * displayDirectFlgを設定します。
   * 
   * @param displayDirectFlg
   *          displayDirectFlg
   */
  public void setDisplayDirectFlg(boolean displayDirectFlg) {
    this.displayDirectFlg = displayDirectFlg;
  }

  /**
   * updateDirectEditModeを取得します。
   * 
   * @return updateDirectEditMode
   */
  public String getUpdateDirectEditMode() {
    return updateDirectEditMode;
  }

  /**
   * updateDirectEditModeを設定します。
   * 
   * @param updateDirectEditMode
   *          updateDirectEditMode
   */
  public void setUpdateDirectEditMode(String updateDirectEditMode) {
    this.updateDirectEditMode = updateDirectEditMode;
  }

}
