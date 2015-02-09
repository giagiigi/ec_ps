package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030620:注文内容のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private OrderDetailCustomerBean customer;

  private OrderDetailPaymentBean payment;
  
  // 20120201 ysy add start
  private OrderDetailInvoiceBean invoice;
  // 20120201 ysy add end

  private List<OrderDetailHistoryDeliveryBean> deliverylist = new ArrayList<OrderDetailHistoryDeliveryBean>();
	// add by lc 2012-03-22 start
  private List<ReturnCommodityBean> returnCommodityList = new ArrayList<ReturnCommodityBean>();
	// add by lc 2012-03-22 end
  @Length(16)
  @Digit
  @Metadata(name = "受注番号")
  private String orderNo;
  
  private String orderDatetime;
  
  private String orderStatus;

  private String commodityTotalPrice;

  private String shippingChargeSum;

  private String paymentCommissionSum;

  private String usedPointSum;

  private String acquiredPointSum;

  private String totalGiftPrice;

  private String orderTotalPrice;

  private String grandTotalPrice;
  
  // 20120131 ysy add start
  private String discountPrice;
  // 20120131 ysy add end

  private String totoalCouponPrice;

  private String paymentMethod;

  private boolean isNotPointInFull;

  private String message;

  private String paymentDate;

  private Date updatedDatetime;

  private boolean cancelButtonDisplayFlg;

  // add by V10-CH start
  private boolean usedPointSumDisplayFlg = false;

  private boolean acquiredPointSumDisplayFlg = false;

  private boolean usedCouponSumDisplayFlg = false;
  
  private String giftCardUsePrice;
  
  private String outerCardUsePrice;

  // add by V10-CH end

  @Length(8)
  @Digit
  @Metadata(name = "アドレス帳番号")
  private String addressNo;

  private Object paymentFormObject;

  // 银联支付信息表示Flg
  private boolean displayChinapayInfo = false;

  // 支付宝支付信息表示Flg
  private boolean displayAlipayInfo = false;
  
  private String pattern = "order_detail";
  
  /**
   * U2030620:注文内容のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderDetailCustomerBean implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private String postalCode;

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    private String email;
    
    private String phoneNumber;

    // Add by V10-CH start
    private String mobileNumber;

    // Add by V10-CH end

    /**
     * mobileNumberを取得します。
     * 
     * @return mobileNumber mobileNumber
     */
    public String getMobileNumber() {
      return mobileNumber;
    }

    /**
     * mobileNumberを設定します。
     * 
     * @param mobileNumber
     *          mobileNumber
     */
    public void setMobileNumber(String mobileNumber) {
      this.mobileNumber = mobileNumber;
    }

    /**
     * firstNameを取得します。
     * 
     * @return the firstName
     */
    public String getFirstName() {
      return firstName;
    }

    /**
     * lastNameを取得します。
     * 
     * @return the lastName
     */
    public String getLastName() {
      return lastName;
    }

    /**
     * address1を取得します。
     * 
     * @return the address1
     */
    public String getAddress1() {
      return address1;
    }

    /**
     * address2を取得します。
     * 
     * @return the address2
     */
    public String getAddress2() {
      return address2;
    }

    /**
     * address3を取得します。
     * 
     * @return the address3
     */
    public String getAddress3() {
      return address3;
    }

    /**
     * address4を取得します。
     * 
     * @return the address4
     */
    public String getAddress4() {
      return address4;
    }

    /**
     * emailを取得します。
     * 
     * @return the email
     */
    public String getEmail() {
      return email;
    }

    /**
     * phoneNumberを取得します。
     * 
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
      return phoneNumber;
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
     * @return the postalCode
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
     * address1を設定します。
     * 
     * @param address1
     *          address1
     */
    public void setAddress1(String address1) {
      this.address1 = address1;
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
     * address3を設定します。
     * 
     * @param address3
     *          address3
     */
    public void setAddress3(String address3) {
      this.address3 = address3;
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
     * emailを設定します。
     * 
     * @param email
     *          email
     */
    public void setEmail(String email) {
      this.email = email;
    }

    /**
     * phoneNumberを設定します。
     * 
     * @param phoneNumber
     *          phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }
    
  }

  // 20120201 ysy add start
  /**
   * 订单发票信息*/
  public static class OrderDetailInvoiceBean implements Serializable {
	  /** serial version uid */
	  private static final long serialVersionUID = 1L;
	  
	  private String commodityName;
	  
	  private Long invoiceType;
	  
	  private String companyName;

	  private String customerName;
	  
	  private String taxpayerCode;
	  
	  private String address;
	  
	  private String tel;
	  
	  private String bankName;
	  
	  private String bankNo;


	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the commodityName
	 */
	public String getCommodityName() {
		return commodityName;
	}

	/**
	 * @param commodityName the commodityName to set
	 */
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	/**
	 * @return the invoiceType
	 */
	public Long getInvoiceType() {
		return invoiceType;
	}

	/**
	 * @param invoiceType the invoiceType to set
	 */
	public void setInvoiceType(Long invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the taxpayerCode
	 */
	public String getTaxpayerCode() {
		return taxpayerCode;
	}

	/**
	 * @param taxpayerCode the taxpayerCode to set
	 */
	public void setTaxpayerCode(String taxpayerCode) {
		this.taxpayerCode = taxpayerCode;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankNo
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * @param bankNo the bankNo to set
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	  
  }
  // 20120201 ysy add end
  
  /**
   * U2030620:注文内容のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderDetailPaymentBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String paymentMethodName;

    private String paymentMethodNo;
    
    private String cvsName;

    private String receiptNo;
    
    private String receiptUrl;

    private String paymentLimitDate;

    private String paymentCommission;
    
//  M17N 10361 追加 ここから
//  alipayと銀聯支払区分
    private boolean paymentFlg;
//  M17N 10361 追加 ここまで

    /**
     * paymentMethodNameを取得します。
     * 
     * @return the paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
    }

    /**
     * paymentCommissionを取得します。
     * 
     * @return the paymentCommission
     */
    public String getPaymentCommission() {
      return paymentCommission;
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
     * paymentCommissionを設定します。
     * 
     * @param paymentCommission
     *          paymentCommission
     */
    public void setPaymentCommission(String paymentCommission) {
      this.paymentCommission = paymentCommission;
    }

    /**
     * cvsNameを取得します。
     * 
     * @return cvsName
     */
    public String getCvsName() {
      return cvsName;
    }

    /**
     * cvsNameを設定します。
     * 
     * @param cvsName
     *          cvsName
     */
    public void setCvsName(String cvsName) {
      this.cvsName = cvsName;
    }

    /**
     * paymentLimitDateを取得します。
     * 
     * @return paymentLimitDate
     */
    public String getPaymentLimitDate() {
      return paymentLimitDate;
    }

    /**
     * paymentLimitDateを設定します。
     * 
     * @param paymentLimitDate
     *          paymentLimitDate
     */
    public void setPaymentLimitDate(String paymentLimitDate) {
      this.paymentLimitDate = paymentLimitDate;
    }

    /**
     * receiptNoを取得します。
     * 
     * @return receiptNo
     */
    public String getReceiptNo() {
      return receiptNo;
    }

    /**
     * receiptNoを設定します。
     * 
     * @param receiptNo
     *          receiptNo
     */
    public void setReceiptNo(String receiptNo) {
      this.receiptNo = receiptNo;
    }

    /**
     * receiptUrlを取得します。
     * 
     * @return receiptUrl
     */
    public String getReceiptUrl() {
      return receiptUrl;
    }

    /**
     * receiptUrlを設定します。
     * 
     * @param receiptUrl
     *          receiptUrl
     */
    public void setReceiptUrl(String receiptUrl) {
      this.receiptUrl = receiptUrl;
    }

    
    public boolean isPaymentFlg() {
      return paymentFlg;
    }

    
    public void setPaymentFlg(boolean paymentFlg) {
      this.paymentFlg = paymentFlg;
    }

    
    /**
     * @return the paymentMethodNo
     */
    public String getPaymentMethodNo() {
      return paymentMethodNo;
    }

    
    /**
     * @param paymentMethodNo the paymentMethodNo to set
     */
    public void setPaymentMethodNo(String paymentMethodNo) {
      this.paymentMethodNo = paymentMethodNo;
    }


  }

  public static class ReturnCommodityBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String commodityCode;

    private String commodityName;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private String salePrice;

    private String commodityGiftName;

    private String commodityGiftFee;

    private String commodityAmount;

    private String intermediateTotal;

    private String skuCode;

    
    /**
     * @return the shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    
    /**
     * @param shopCode the shopCode to set
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    
    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    
    /**
     * @param commodityCode the commodityCode to set
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    
    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    
    /**
     * @param commodityName the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    
    /**
     * @return the standardDetail1Name
     */
    public String getStandardDetail1Name() {
      return standardDetail1Name;
    }

    
    /**
     * @param standardDetail1Name the standardDetail1Name to set
     */
    public void setStandardDetail1Name(String standardDetail1Name) {
      this.standardDetail1Name = standardDetail1Name;
    }

    
    /**
     * @return the standardDetail2Name
     */
    public String getStandardDetail2Name() {
      return standardDetail2Name;
    }

    
    /**
     * @param standardDetail2Name the standardDetail2Name to set
     */
    public void setStandardDetail2Name(String standardDetail2Name) {
      this.standardDetail2Name = standardDetail2Name;
    }

    
    /**
     * @return the salePrice
     */
    public String getSalePrice() {
      return salePrice;
    }

    
    /**
     * @param salePrice the salePrice to set
     */
    public void setSalePrice(String salePrice) {
      this.salePrice = salePrice;
    }

    
    /**
     * @return the commodityGiftName
     */
    public String getCommodityGiftName() {
      return commodityGiftName;
    }

    
    /**
     * @param commodityGiftName the commodityGiftName to set
     */
    public void setCommodityGiftName(String commodityGiftName) {
      this.commodityGiftName = commodityGiftName;
    }

    
    /**
     * @return the commodityGiftFee
     */
    public String getCommodityGiftFee() {
      return commodityGiftFee;
    }

    
    /**
     * @param commodityGiftFee the commodityGiftFee to set
     */
    public void setCommodityGiftFee(String commodityGiftFee) {
      this.commodityGiftFee = commodityGiftFee;
    }

    
    /**
     * @return the commodityAmount
     */
    public String getCommodityAmount() {
      return commodityAmount;
    }

    
    /**
     * @param commodityAmount the commodityAmount to set
     */
    public void setCommodityAmount(String commodityAmount) {
      this.commodityAmount = commodityAmount;
    }

    
    /**
     * @return the intermediateTotal
     */
    public String getIntermediateTotal() {
      return intermediateTotal;
    }

    
    /**
     * @param intermediateTotal the intermediateTotal to set
     */
    public void setIntermediateTotal(String intermediateTotal) {
      this.intermediateTotal = intermediateTotal;
    }

    
    /**
     * @return the skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    
    /**
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }
  }
  
  /**
   * U2030620:注文内容のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderDetailHistoryDeliveryBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shippingNo;

    private List<OrderDetailHistoryCommodityBean> commodityList = new ArrayList<OrderDetailHistoryCommodityBean>();
    
    private List<OrderDetailHistoryCommodityBean> commodityReturnList = new ArrayList<OrderDetailHistoryCommodityBean>();

    private String addressNo;

    private String addressAlias;

    private String addressLastName;

    private String addressFirstName;

    private String deliveryTypeName;

    private String postalCode;

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    private String deliveryAppointedDate;

    private String deliveryAppointedStartTime;

    private String deliveryAppointedEndTime;

    private String deliveryRemark;

    private String deliverySlipNo;

    private String deliverySlipName;

    private String deliveryPercelUrl;

    private String shippingCharge;

    private String deliveryGiftFee;

    private String phoneNumber;

    // Add by V10-CH start
    private String mobileNumber;

    // Add by V10-CH end

    private String shippingDate;

    private String shopName;

    private boolean addressLink;


    /**
	 * @return the commodityReturnList
	 */
	public List<OrderDetailHistoryCommodityBean> getCommodityReturnList() {
		return commodityReturnList;
	}

	/**
     * addressLinkを取得します。
     * 
     * @return addressLink
     */
    public boolean isAddressLink() {
      return addressLink;
    }

    /**
     * addressLinkを設定します。
     * 
     * @param addressLink
     *          addressLink
     */
    public void setAddressLink(boolean addressLink) {
      this.addressLink = addressLink;
    }

    /**
     * shopNameを返します。
     * 
     * @return the shopName
     */
    public String getShopName() {
      return shopName;
    }

    /**
     * shopNameを設定します。
     * 
     * @param shopName
     *          設定する shopName
     */
    public void setShopName(String shopName) {
      this.shopName = shopName;
    }

    /**
     * shippingDateを取得します。
     * 
     * @return the shippingDate
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
     * address1を取得します。
     * 
     * @return address1
     */
    public String getAddress1() {
      return address1;
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
     * address3を取得します。
     * 
     * @return address3
     */
    public String getAddress3() {
      return address3;
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
     * addressFirstNameを取得します。
     * 
     * @return addressFirstName
     */
    public String getAddressFirstName() {
      return addressFirstName;
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
     * commodityListを取得します。
     * 
     * @return commodityList
     */
    public List<OrderDetailHistoryCommodityBean> getCommodityList() {
      return commodityList;
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
     * deliveryAppointedEndTimeを取得します。
     * 
     * @return deliveryAppointedEndTime
     */
    public String getDeliveryAppointedEndTime() {
      return deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedStartTimeを取得します。
     * 
     * @return deliveryAppointedStartTime
     */
    public String getDeliveryAppointedStartTime() {
      return deliveryAppointedStartTime;
    }

    /**
     * deliveryGiftFeeを取得します。
     * 
     * @return deliveryGiftFee
     */
    public String getDeliveryGiftFee() {
      return deliveryGiftFee;
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
     * deliverySlipNoを取得します。
     * 
     * @return deliverySlipNo
     */
    public String getDeliverySlipNo() {
      return deliverySlipNo;
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
     * shippingChargeを取得します。
     * 
     * @return shippingCharge
     */
    public String getShippingCharge() {
      return shippingCharge;
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
     * address2を設定します。
     * 
     * @param address2
     *          address2
     */
    public void setAddress2(String address2) {
      this.address2 = address2;
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
     * address4を設定します。
     * 
     * @param address4
     *          address4
     */
    public void setAddress4(String address4) {
      this.address4 = address4;
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
     * addressLastNameを設定します。
     * 
     * @param addressLastName
     *          addressLastName
     */
    public void setAddressLastName(String addressLastName) {
      this.addressLastName = addressLastName;
    }

    /**
     * commodityListを設定します。
     * 
     * @param commodityList
     *          commodityList
     */
    public void setCommodityList(List<OrderDetailHistoryCommodityBean> commodityList) {
      this.commodityList = commodityList;
    }
    // 20120209 ysy
    public void setCommodityReturnList(List<OrderDetailHistoryCommodityBean> commodityReturnList) {
        this.commodityReturnList = commodityReturnList;
      }
    // 20120209 ysy

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
     * deliveryAppointedEndTimeを設定します。
     * 
     * @param deliveryAppointedEndTime
     *          deliveryAppointedEndTime
     */
    public void setDeliveryAppointedEndTime(String deliveryAppointedEndTime) {
      this.deliveryAppointedEndTime = deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedStartTimeを設定します。
     * 
     * @param deliveryAppointedStartTime
     *          deliveryAppointedStartTime
     */
    public void setDeliveryAppointedStartTime(String deliveryAppointedStartTime) {
      this.deliveryAppointedStartTime = deliveryAppointedStartTime;
    }

    /**
     * deliveryGiftFeeを設定します。
     * 
     * @param deliveryGiftFee
     *          deliveryGiftFee
     */
    public void setDeliveryGiftFee(String deliveryGiftFee) {
      this.deliveryGiftFee = deliveryGiftFee;
    }

    /**
     * deliveryGiftNameを設定します。
     * 
     * @param deliveryGiftName
     *          deliveryGiftName
     */
    public void setDeliveryRemark(String deliveryGiftName) {
      this.deliveryRemark = deliveryGiftName;
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
     * deliveryTypeNameを設定します。
     * 
     * @param deliveryTypeName
     *          deliveryTypeName
     */
    public void setDeliveryTypeName(String deliveryTypeName) {
      this.deliveryTypeName = deliveryTypeName;
    }

    /**
     * postalCodeを取得します。
     * 
     * @return the postalCode
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
     * shippingChargeを設定します。
     * 
     * @param shippingCharge
     *          shippingCharge
     */
    public void setShippingCharge(String shippingCharge) {
      this.shippingCharge = shippingCharge;
    }

    /**
     * addressNoを取得します。
     * 
     * @return the addressNo
     */
    public String getAddressNo() {
      return addressNo;
    }

    /**
     * addressNoを設定します。
     * 
     * @param addressNo
     *          addressNo
     */
    public void setAddressNo(String addressNo) {
      this.addressNo = addressNo;
    }

    /**
     * phoneNumberを取得します。
     * 
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
      return phoneNumber;
    }

    /**
     * phoneNumberを設定します。
     * 
     * @param phoneNumber
     *          phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }

    /**
     * deliveryPercelUrlを取得します。
     * 
     * @return the deliveryPercelUrl
     */
    public String getDeliveryPercelUrl() {
      return deliveryPercelUrl;
    }

    /**
     * deliveryPercelUrlを設定します。
     * 
     * @param deliveryPercelUrl
     *          deliveryPercelUrl
     */
    public void setDeliveryPercelUrl(String deliveryPercelUrl) {
      this.deliveryPercelUrl = deliveryPercelUrl;
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
     * addressAliasを取得します。
     * 
     * @return addressAlias
     */
    public String getAddressAlias() {
      return addressAlias;
    }

    /**
     * addressAliasを設定します。
     * 
     * @param addressAlias
     *          addressAlias
     */
    public void setAddressAlias(String addressAlias) {
      this.addressAlias = addressAlias;
    }

    /**
     * mobileNumberを取得します。
     * 
     * @return mobileNumber mobileNumber
     */
    public String getMobileNumber() {
      return mobileNumber;
    }

    /**
     * mobileNumberを設定します。
     * 
     * @param mobileNumber
     *          mobileNumber
     */
    public void setMobileNumber(String mobileNumber) {
      this.mobileNumber = mobileNumber;
    }

    
    /**
     * @return the deliverySlipName
     */
    public String getDeliverySlipName() {
      return deliverySlipName;
    }

    
    /**
     * @param deliverySlipName the deliverySlipName to set
     */
    public void setDeliverySlipName(String deliverySlipName) {
      this.deliverySlipName = deliverySlipName;
    }

  }

  // 20120209 ysy add start
  /** 取得部分退货信息 */
  public static class getReturnOrderDetail implements Serializable{
	  
	  private static final long serialVersionUID = 1L;
	  
	  private String shopCode;

	    private String commodityCode;

	    private String commodityName;

	    private String standardDetail1Name;

	    private String standardDetail2Name;

	    private String salePrice;

	    private String commodityGiftName;

	    private String commodityGiftFee;

	    private String commodityAmount;

	    private String intermediateTotal;

	    private String skuCode;
	    
	    private Long returnItemType;

		/**
		 * @return the shopCode
		 */
		public String getShopCode() {
			return shopCode;
		}

		/**
		 * @param shopCode the shopCode to set
		 */
		public void setShopCode(String shopCode) {
			this.shopCode = shopCode;
		}

		/**
		 * @return the commodityCode
		 */
		public String getCommodityCode() {
			return commodityCode;
		}

		/**
		 * @param commodityCode the commodityCode to set
		 */
		public void setCommodityCode(String commodityCode) {
			this.commodityCode = commodityCode;
		}

		/**
		 * @return the commodityName
		 */
		public String getCommodityName() {
			return commodityName;
		}

		/**
		 * @param commodityName the commodityName to set
		 */
		public void setCommodityName(String commodityName) {
			this.commodityName = commodityName;
		}

		/**
		 * @return the standardDetail1Name
		 */
		public String getStandardDetail1Name() {
			return standardDetail1Name;
		}

		/**
		 * @param standardDetail1Name the standardDetail1Name to set
		 */
		public void setStandardDetail1Name(String standardDetail1Name) {
			this.standardDetail1Name = standardDetail1Name;
		}

		/**
		 * @return the standardDetail2Name
		 */
		public String getStandardDetail2Name() {
			return standardDetail2Name;
		}

		/**
		 * @param standardDetail2Name the standardDetail2Name to set
		 */
		public void setStandardDetail2Name(String standardDetail2Name) {
			this.standardDetail2Name = standardDetail2Name;
		}

		/**
		 * @return the salePrice
		 */
		public String getSalePrice() {
			return salePrice;
		}

		/**
		 * @param salePrice the salePrice to set
		 */
		public void setSalePrice(String salePrice) {
			this.salePrice = salePrice;
		}

		/**
		 * @return the commodityGiftName
		 */
		public String getCommodityGiftName() {
			return commodityGiftName;
		}

		/**
		 * @param commodityGiftName the commodityGiftName to set
		 */
		public void setCommodityGiftName(String commodityGiftName) {
			this.commodityGiftName = commodityGiftName;
		}

		/**
		 * @return the commodityGiftFee
		 */
		public String getCommodityGiftFee() {
			return commodityGiftFee;
		}

		/**
		 * @param commodityGiftFee the commodityGiftFee to set
		 */
		public void setCommodityGiftFee(String commodityGiftFee) {
			this.commodityGiftFee = commodityGiftFee;
		}

		/**
		 * @return the commodityAmount
		 */
		public String getCommodityAmount() {
			return commodityAmount;
		}

		/**
		 * @param commodityAmount the commodityAmount to set
		 */
		public void setCommodityAmount(String commodityAmount) {
			this.commodityAmount = commodityAmount;
		}

		/**
		 * @return the intermediateTotal
		 */
		public String getIntermediateTotal() {
			return intermediateTotal;
		}

		/**
		 * @param intermediateTotal the intermediateTotal to set
		 */
		public void setIntermediateTotal(String intermediateTotal) {
			this.intermediateTotal = intermediateTotal;
		}

		/**
		 * @return the skuCode
		 */
		public String getSkuCode() {
			return skuCode;
		}

		/**
		 * @param skuCode the skuCode to set
		 */
		public void setSkuCode(String skuCode) {
			this.skuCode = skuCode;
		}

		/**
		 * @return the returnItemType
		 */
		public Long getReturnItemType() {
			return returnItemType;
		}

		/**
		 * @param returnItemType the returnItemType to set
		 */
		public void setReturnItemType(Long returnItemType) {
			this.returnItemType = returnItemType;
		}
  }
  // 20120209 ysy add end
  /**
   * U2030620:注文内容のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OrderDetailHistoryCommodityBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String commodityCode;

    private String commodityName;

    private String standardDetail1Name;

    private String standardDetail2Name;

    private String salePrice;

    private String commodityGiftName;

    private String commodityGiftFee;

    private String commodityAmount;

    private String intermediateTotal;

    private String skuCode;
    
    private boolean commoditySet;
    
    // 20120202 ysy add start
    private Long returnItemType;
    // 20120202 ysy add end
    
    // 2012/12/20 ob add start
    private boolean giftFlg;
    // 2012/12/20 ob add end

    /**
     * commodityAmountを取得します。
     * 
     * @return commodityAmount
     */
    public String getCommodityAmount() {
      return commodityAmount;
    }

    /**
	 * @return the returnItemType
	 */
	public Long getReturnItemType() {
		return returnItemType;
	}

	/**
	 * @param returnItemType the returnItemType to set
	 */
	public void setReturnItemType(Long returnItemType) {
		this.returnItemType = returnItemType;
	}

	/**
     * commodityGiftFeeを取得します。
     * 
     * @return commodityGiftFee
     */
    public String getCommodityGiftFee() {
      return commodityGiftFee;
    }

    /**
     * commodityGiftNameを取得します。
     * 
     * @return commodityGiftName
     */
    public String getCommodityGiftName() {
      return commodityGiftName;
    }

    /**
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * intermediateTotalを取得します。
     * 
     * @return intermediateTotal
     */
    public String getIntermediateTotal() {
      return intermediateTotal;
    }

    /**
     * salePriceを取得します。
     * 
     * @return salePrice
     */
    public String getSalePrice() {
      return salePrice;
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
     * commodityAmountを設定します。
     * 
     * @param commodityAmount
     *          commodityAmount
     */
    public void setCommodityAmount(String commodityAmount) {
      this.commodityAmount = commodityAmount;
    }

    /**
     * commodityGiftFeeを設定します。
     * 
     * @param commodityGiftFee
     *          commodityGiftFee
     */
    public void setCommodityGiftFee(String commodityGiftFee) {
      this.commodityGiftFee = commodityGiftFee;
    }

    /**
     * commodityGiftNameを設定します。
     * 
     * @param commodityGiftName
     *          commodityGiftName
     */
    public void setCommodityGiftName(String commodityGiftName) {
      this.commodityGiftName = commodityGiftName;
    }

    /**
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * intermediateTotalを設定します。
     * 
     * @param intermediateTotal
     *          intermediateTotal
     */
    public void setIntermediateTotal(String intermediateTotal) {
      this.intermediateTotal = intermediateTotal;
    }

    /**
     * salePriceを設定します。
     * 
     * @param salePrice
     *          salePrice
     */
    public void setSalePrice(String salePrice) {
      this.salePrice = salePrice;
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
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * commodityCodeを取得します。
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
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
     * commodityCodeを設定します。
     * 
     * @param commodityCode
     *          commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
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
     * @return the giftFlg
     */
    public boolean isGiftFlg() {
      return giftFlg;
    }

    /**
     * @param giftFlg the giftFlg to set
     */
    public void setGiftFlg(boolean giftFlg) {
      this.giftFlg = giftFlg;
    }

    
    /**
     * @return the commoditySet
     */
    public boolean isCommoditySet() {
      return commoditySet;
    }

    
    /**
     * @param commoditySet the commoditySet to set
     */
    public void setCommoditySet(boolean commoditySet) {
      this.commoditySet = commoditySet;
    }

  }

  /**
   * paymentDateを設定します。
   * 
   * @param paymentDate
   *          設定する paymentDate
   */
  public void setPaymentDate(String paymentDate) {
    this.paymentDate = paymentDate;
  }

  /**
   * isNotPointInFullを返します。
   * 
   * @return the isNotPointInFull
   */
  public boolean isNotPointInFull() {
    return isNotPointInFull;
  }

  /**
   * isNotPointInFullを設定します。
   * 
   * @param notPointInFull
   *          設定する isNotPointInFull
   */
  public void setNotPointInFull(boolean notPointInFull) {
    this.isNotPointInFull = notPointInFull;
  }

  /**
   * grandTotalPriceを返します。
   * 
   * @return the grandTotalPrice
   */
  public String getGrandTotalPrice() {
    return grandTotalPrice;
  }

  /**
   * grandTotalPriceを設定します。
   * 
   * @param grandTotalPrice
   *          設定する grandTotalPrice
   */
  public void setGrandTotalPrice(String grandTotalPrice) {
    this.grandTotalPrice = grandTotalPrice;
  }
  

  /**
 * @return the discountPrice
 */
public String getDiscountPrice() {
	return discountPrice;
}

/**
 * @param discountPrice the discountPrice to set
 */
public void setDiscountPrice(String discountPrice) {
	this.discountPrice = discountPrice;
}

/**
   * addressNoを取得します。
   * 
   * @return addressNo
   */
  public String getAddressNo() {
    return addressNo;
  }

  /**
   * addressNoを設定します。
   * 
   * @param addressNo
   *          addressNo
   */
  public void setAddressNo(String addressNo) {
    this.addressNo = addressNo;
  }

  /**
   * commodityTotalPriceを取得します。
   * 
   * @return commodityTotalPrice
   */
  public String getCommodityTotalPrice() {
    return commodityTotalPrice;
  }

  /**
   * deliverylistを取得します。
   * 
   * @return deliverylist
   */
  public List<OrderDetailHistoryDeliveryBean> getDeliverylist() {
    return deliverylist;
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
   * orderTotalPriceを取得します。
   * 
   * @return orderTotalPrice
   */
  public String getOrderTotalPrice() {
    return orderTotalPrice;
  }

  /**
   * paymentCommissionSumを取得します。
   * 
   * @return paymentCommissionSum
   */
  public String getPaymentCommissionSum() {
    return paymentCommissionSum;
  }

  /**
   * paymentMethodを取得します。
   * 
   * @return paymentMethod
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * shippingChargeSumを取得します。
   * 
   * @return shippingChargeSum
   */
  public String getShippingChargeSum() {
    return shippingChargeSum;
  }

  /**
   * usedPointSumを取得します。
   * 
   * @return usedPointSum
   */
  public String getUsedPointSum() {
    return usedPointSum;
  }

  /**
   * commodityTotalPriceを設定します。
   * 
   * @param commodityTotalPrice
   *          commodityTotalPrice
   */
  public void setCommodityTotalPrice(String commodityTotalPrice) {
    this.commodityTotalPrice = commodityTotalPrice;
  }

  /**
   * deliverylistを設定します。
   * 
   * @param deliverylist
   *          deliverylist
   */
  public void setDeliverylist(List<OrderDetailHistoryDeliveryBean> deliverylist) {
    this.deliverylist = deliverylist;
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
   * orderTotalPriceを設定します。
   * 
   * @param orderTotalPrice
   *          orderTotalPrice
   */
  public void setOrderTotalPrice(String orderTotalPrice) {
    this.orderTotalPrice = orderTotalPrice;
  }

  /**
   * paymentCommissionSumを設定します。
   * 
   * @param paymentCommissionSum
   *          paymentCommissionSum
   */
  public void setPaymentCommissionSum(String paymentCommissionSum) {
    this.paymentCommissionSum = paymentCommissionSum;
  }

  /**
   * paymentMethodを設定します。
   * 
   * @param paymentMethod
   *          paymentMethod
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /**
   * shippingChargeSumを設定します。
   * 
   * @param shippingChargeSum
   *          shippingChargeSum
   */
  public void setShippingChargeSum(String shippingChargeSum) {
    this.shippingChargeSum = shippingChargeSum;
  }

  /**
   * usedPointSumを設定します。
   * 
   * @param usedPointSum
   *          usedPointSum
   */
  public void setUsedPointSum(String usedPointSum) {
    this.usedPointSum = usedPointSum;
  }

  /**
   * サブJSPを設定します。
   */
//  @Override
//  public void setSubJspId() {
//
//  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030620";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.OrderDetailBean.0");
  }

  /**
   * cancelButtonDisplayFlgを取得します。
   * 
   * @return cancelButtonDisplayFlg
   */
  public boolean isCancelButtonDisplayFlg() {
    return cancelButtonDisplayFlg;
  }

  /**
   * cancelButtonDisplayFlgを設定します。
   * 
   * @param cancelButtonDisplayFlg
   *          cancelButtonDisplayFlg
   */
  public void setCancelButtonDisplayFlg(boolean cancelButtonDisplayFlg) {
    this.cancelButtonDisplayFlg = cancelButtonDisplayFlg;
  }

  /**
   * customerを取得します。
   * 
   * @return the customer
   */
  public OrderDetailCustomerBean getCustomer() {
    return customer;
  }

  /**
   * paymentを取得します。
   * 
   * @return the payment
   */
  public OrderDetailPaymentBean getPayment() {
    return payment;
  }

  /**
   * customerを設定します。
   * 
   * @param customer
   *          customer
   */
  public void setCustomer(OrderDetailCustomerBean customer) {
    this.customer = customer;
  }

  /**
 * @return the invoice
 */
public OrderDetailInvoiceBean getInvoice() {
	return invoice;
}

/**
 * @param invoice the invoice to set
 */
public void setInvoice(OrderDetailInvoiceBean invoice) {
	this.invoice = invoice;
}

/**
   * paymentを設定します。
   * 
   * @param payment
   *          payment
   */
  public void setPayment(OrderDetailPaymentBean payment) {
    this.payment = payment;
  }

  /**
   * totalGiftPriceを取得します。
   * 
   * @return the totalGiftPrice
   */
  public String getTotalGiftPrice() {
    return totalGiftPrice;
  }

  /**
   * totalGiftPriceを設定します。
   * 
   * @param totalGiftPrice
   *          totalGiftPrice
   */
  public void setTotalGiftPrice(String totalGiftPrice) {
    this.totalGiftPrice = totalGiftPrice;
  }

  /**
   * acquiredPointSumを取得します。
   * 
   * @return the acquiredPointSum
   */
  public String getAcquiredPointSum() {
    return acquiredPointSum;
  }

  /**
   * acquiredPointSumを設定します。
   * 
   * @param acquiredPointSum
   *          acquiredPointSum
   */
  public void setAcquiredPointSum(String acquiredPointSum) {
    this.acquiredPointSum = acquiredPointSum;
  }

  /**
   * messageを取得します。
   * 
   * @return the message
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
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderDetailBean.1"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderDetailBean.2"), "/app/mypage/order_history/init"));
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderDetailBean.0"), "/app/mypage/order_detail/init/"
        + orderNo));
    return topicPath;
  }

  /**
   * paymentDateを返します。
   * 
   * @return the paymentDate
   */
  public String getPaymentDate() {
    return paymentDate;
  }

  public boolean isAcquiredPointSumDisplayFlg() {
    return acquiredPointSumDisplayFlg;
  }

  public void setAcquiredPointSumDisplayFlg(boolean acquiredPointSumDisplayFlg) {
    this.acquiredPointSumDisplayFlg = acquiredPointSumDisplayFlg;
  }

  public boolean isUsedCouponSumDisplayFlg() {
    return usedCouponSumDisplayFlg;
  }

  public void setUsedCouponSumDisplayFlg(boolean usedCouponSumDisplayFlg) {
    this.usedCouponSumDisplayFlg = usedCouponSumDisplayFlg;
  }

  public boolean isUsedPointSumDisplayFlg() {
    return usedPointSumDisplayFlg;
  }

  public void setUsedPointSumDisplayFlg(boolean usedPointSumDisplayFlg) {
    this.usedPointSumDisplayFlg = usedPointSumDisplayFlg;
  }

  public String getTotoalCouponPrice() {
    return totoalCouponPrice;
  }

  public void setTotoalCouponPrice(String totoalCouponPrice) {
    this.totoalCouponPrice = totoalCouponPrice;
  }

  
  /**
   * @return the returnCommoditylist
   */
  public List<ReturnCommodityBean> getReturnCommodityList() {
    return returnCommodityList;
  }

  
  /**
   * @param returnCommoditylist the returnCommoditylist to set
   */
  public void setReturnCommodityList(List<ReturnCommodityBean> returnCommodityList) {
    this.returnCommodityList = returnCommodityList;
  }

  
  /**
   * @return the paymentFormObject
   */
  public Object getPaymentFormObject() {
    return paymentFormObject;
  }

  
  /**
   * @param paymentFormObject the paymentFormObject to set
   */
  public void setPaymentFormObject(Object paymentFormObject) {
    this.paymentFormObject = paymentFormObject;
  }

  
  /**
   * @return the displayChinapayInfo
   */
  public boolean isDisplayChinapayInfo() {
    return displayChinapayInfo;
  }

  
  /**
   * @param displayChinapayInfo the displayChinapayInfo to set
   */
  public void setDisplayChinapayInfo(boolean displayChinapayInfo) {
    this.displayChinapayInfo = displayChinapayInfo;
  }

  
  /**
   * @return the displayAlipayInfo
   */
  public boolean isDisplayAlipayInfo() {
    return displayAlipayInfo;
  }

  
  /**
   * @param displayAlipayInfo the displayAlipayInfo to set
   */
  public void setDisplayAlipayInfo(boolean displayAlipayInfo) {
    this.displayAlipayInfo = displayAlipayInfo;
  }

  
  /**
   * @return the pattern
   */
  public String getPattern() {
    return pattern;
  }

  
  /**
   * @param pattern the pattern to set
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  
  /**
   * @return the orderDatetime
   */
  public String getOrderDatetime() {
    return orderDatetime;
  }

  
  /**
   * @param orderDatetime the orderDatetime to set
   */
  public void setOrderDatetime(String orderDatetime) {
    this.orderDatetime = orderDatetime;
  }

  
  /**
   * @return the orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  
  /**
   * @param orderStatus the orderStatus to set
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  
  /**
   * @return the giftCardUsePrice
   */
  public String getGiftCardUsePrice() {
    return giftCardUsePrice;
  }

  
  /**
   * @param giftCardUsePrice the giftCardUsePrice to set
   */
  public void setGiftCardUsePrice(String giftCardUsePrice) {
    this.giftCardUsePrice = giftCardUsePrice;
  }

  
  /**
   * @return the outerCardUsePrice
   */
  public String getOuterCardUsePrice() {
    return outerCardUsePrice;
  }

  
  /**
   * @param outerCardUsePrice the outerCardUsePrice to set
   */
  public void setOuterCardUsePrice(String outerCardUsePrice) {
    this.outerCardUsePrice = outerCardUsePrice;
  }

}
