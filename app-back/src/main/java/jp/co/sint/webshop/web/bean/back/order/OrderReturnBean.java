package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020260:受注返品のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class OrderReturnBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<ShippingBean> shippingList = new ArrayList<ShippingBean>();

  @Metadata(name = "受注番号")
  private String orderNo;

  @Metadata(name = "受注状況")
  private String orderStatus;

  @Metadata(name = "データ連携フラグ")
  private Long dataTransportStatus;

  @Length(200)
  @Metadata(name = "連絡事項")
  private String message;

  @Length(200)
  @Metadata(name = "注意事項（管理側のみ参照）")
  private String caution;

  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  // 10.1.2 10075, 10076 修正 ここから
  // @Metadata(name = "金額調整")
  @Metadata(name = "送料・金額調整")
  // 10.1.2 10075, 10076 修正 ここまで
  private String returnShippingCharge;

  @Metadata(name = "返品金額")
  private BigDecimal returnCharge;

  @Metadata(name = "返品商品")
  private String shopSkuCode;

  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "単価(税込)")
  private String salesPrice;

  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "ギフト価格(税込)")
  private String detailGiftPrice;

  @Required
  @Length(4)
  @Digit // 10.1.2 10076 追加
  @Range(min = 0, max = 9999)
  @Metadata(name = "数量")
  private String purchasingAmount;

  @Metadata(name = "返品伝票番号")
  private String returnShippingNo;

  @Metadata(name = "顧客コード")
  private String customerCode;

  private String dispStatus;

  private Date orderUpdateDatetime;

  private boolean confirmButtonDisplay;

  private boolean registerButtonDisplay;

  private List<CodeAttribute> returnSkuList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> returnShopList = new ArrayList<CodeAttribute>();

  private String returnShopCode;

  /**
   * returnShopCodeを取得します。
   * 
   * @return returnShopCode
   */
  public String getReturnShopCode() {
    return returnShopCode;
  }

  /**
   * returnShopCodeを設定します。
   * 
   * @param returnShopCode
   *          returnShopCode
   */
  public void setReturnShopCode(String returnShopCode) {
    this.returnShopCode = returnShopCode;
  }

  /**
   * returnShopListを取得します。
   * 
   * @return returnShopList
   */
  public List<CodeAttribute> getReturnShopList() {
    return returnShopList;
  }

  /**
   * returnShopListを設定します。
   * 
   * @param returnShopList
   *          returnShopList
   */
  public void setReturnShopList(List<CodeAttribute> returnShopList) {
    this.returnShopList = returnShopList;
  }

  /**
   * orderUpdateDatetimeを取得します。
   * 
   * @return the orderUpdateDatetime
   */
  public Date getOrderUpdateDatetime() {
    return DateUtil.immutableCopy(orderUpdateDatetime);
  }

  /**
   * orderUpdateDatetimeを設定します。
   * 
   * @param orderUpdateDatetime
   *          orderUpdateDatetime
   */
  public void setOrderUpdateDatetime(Date orderUpdateDatetime) {
    this.orderUpdateDatetime = DateUtil.immutableCopy(orderUpdateDatetime);
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
    this.returnShippingCharge = reqparam.get("returnShippingCharge");
    this.salesPrice = reqparam.get("salesPrice");
    this.message = reqparam.get("message");
    this.detailGiftPrice = reqparam.get("detailGiftPrice");
    this.caution = reqparam.get("caution");
    this.purchasingAmount = reqparam.get("purchasingAmount");
    this.shopSkuCode = reqparam.get("shopSkuCode");
    this.returnShopCode = reqparam.get("returnShopCode");
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020260";
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
   * dataTransportFlgを取得します。
   * 
   * @return dataTransportFlg
   */
  public Long getDataTransportStatus() {
    return dataTransportStatus;
  }

  /**
   * dataTransportFlgを設定します。
   * 
   * @param dataTransportStatus
   *          データ連携フラグ
   */
  public void setDataTransportStatus(Long dataTransportStatus) {
    this.dataTransportStatus = dataTransportStatus;
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
   * dispStatusを取得します。
   * 
   * @return dispStatus
   */
  public String getDispStatus() {
    return dispStatus;
  }

  /**
   * dispStatusを設定します。
   * 
   * @param dispStatus
   *          dispStatus
   */
  public void setDispStatus(String dispStatus) {
    this.dispStatus = dispStatus;
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
   * orderStatusを取得します。
   * 
   * @return orderStatus
   */
  public String getOrderStatus() {
    return orderStatus;
  }

  /**
   * orderStatusを設定します。
   * 
   * @param orderStatus
   *          orderStatus
   */
  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
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
   * returnChargeを取得します。
   * 
   * @return returnCharge
   */
  public BigDecimal getReturnCharge() {
    return returnCharge;
  }

  /**
   * returnChargeを設定します。
   * 
   * @param returnCharge
   *          returnCharge
   */
  public void setReturnCharge(BigDecimal returnCharge) {
    this.returnCharge = returnCharge;
  }

  /**
   * salesPriceを取得します。
   * 
   * @return salesPrice
   */
  public String getSalesPrice() {
    return salesPrice;
  }

  /**
   * salesPriceを設定します。
   * 
   * @param salesPrice
   *          salesPrice
   */
  public void setSalesPrice(String salesPrice) {
    this.salesPrice = salesPrice;
  }

  /**
   * shippingListを取得します。
   * 
   * @return shippingList
   */
  public List<ShippingBean> getShippingList() {
    return shippingList;
  }

  /**
   * shippingListを設定します。
   * 
   * @param shippingList
   *          shippingList
   */
  public void setShippingList(List<ShippingBean> shippingList) {
    this.shippingList = shippingList;
  }

  /**
   * shopSkuCodeを取得します。
   * 
   * @return shopSkuCode
   */
  public String getShopSkuCode() {
    return shopSkuCode;
  }

  /**
   * shopSkuCodeを設定します。
   * 
   * @param shopSkuCode
   *          shopSkuCode
   */
  public void setShopSkuCode(String shopSkuCode) {
    this.shopSkuCode = shopSkuCode;
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.OrderReturnBean.0");
  }

  /**
   * U1020260:受注返品のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ShippingBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();

    private String shippingNo;

    private Long shippingStatus;

    private Long deliveryTypeNo;

    private String deliveryCharge;

    private String shopCode;

    private String shopName;

    private String itemLossMoney;

    private boolean returnItem;

    private boolean deleteButtonDisplay;

    private boolean cancelReturnItemFlg;

    /**
     * U1020260:受注返品のサブモデルです。
     * 
     * @author System Integrator Corp.
     */
    public static class ShippingDetailBean implements Serializable {

      /** serial version uid */
      private static final long serialVersionUID = 1L;

      private String detailSummaryPrice;

      private String skuCode;

      private String comodityName;

      private String standardDetailName;

      private String salesPrice;

      private String detailGiftPrice;

      private String purchasingAmount;

      private String shippingDetailBeanNo;

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
       * salesPriceを取得します。
       * 
       * @return salesPrice
       */
      public String getSalesPrice() {
        return salesPrice;
      }

      /**
       * salesPriceを設定します。
       * 
       * @param salesPrice
       *          salesPrice
       */
      public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
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
       * standardDetail1Nameを取得します。
       * 
       * @return standardDetail1Name
       */
      public String getStandardDetailName() {
        return standardDetailName;
      }

      /**
       * standardDetailNameを設定します。
       * 
       * @param standardDetailName
       *          規格詳細名称
       */
      public void setStandardDetailName(String standardDetailName) {
        this.standardDetailName = standardDetailName;
      }

      /**
       * shippingDetailBeanNoを取得します。
       * 
       * @return shippingDetailBeanNo
       */
      public String getShippingDetailBeanNo() {
        return shippingDetailBeanNo;
      }

      /**
       * shippingDetailBeanNoを設定します。
       * 
       * @param shippingDetailBeanNo
       *          shippingDetailBeanNo
       */
      public void setShippingDetailBeanNo(String shippingDetailBeanNo) {
        this.shippingDetailBeanNo = shippingDetailBeanNo;
      }

    }

    /**
     * itemLossMoneyを取得します。
     * 
     * @return itemLossMoney
     */
    public String getItemLossMoney() {
      return itemLossMoney;
    }

    /**
     * itemLossMoneyを設定します。
     * 
     * @param itemLossMoney
     *          itemLossMoney
     */
    public void setItemLossMoney(String itemLossMoney) {
      this.itemLossMoney = itemLossMoney;
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
     * shippingDetailBeanを取得します。
     * 
     * @return shippingDetailList
     */
    public List<ShippingDetailBean> getShippingDetailList() {
      return shippingDetailList;
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
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
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
     * deliveryChargeを設定します。
     * 
     * @param deliveryCharge
     *          deliveryCharge
     */
    public void setDeliveryCharge(String deliveryCharge) {
      this.deliveryCharge = deliveryCharge;
    }

    /**
     * shippingDetailBeanを設定します。
     * 
     * @param shippingDetailList
     *          shippingDetailList
     */
    public void setShippingDetailList(List<ShippingDetailBean> shippingDetailList) {
      this.shippingDetailList = shippingDetailList;
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
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
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
     * returnItemを取得します。
     * 
     * @return returnItem
     */
    public boolean isReturnItem() {
      return returnItem;
    }

    /**
     * returnItemを設定します。
     * 
     * @param returnItem
     *          returnItem
     */
    public void setReturnItem(boolean returnItem) {
      this.returnItem = returnItem;
    }

    /**
     * shippingStatusを取得します。
     * 
     * @return shippingStatus
     */
    public Long getShippingStatus() {
      return shippingStatus;
    }

    /**
     * shippingStatusを設定します。
     * 
     * @param shippingStatus
     *          shippingStatus
     */
    public void setShippingStatus(Long shippingStatus) {
      this.shippingStatus = shippingStatus;
    }

    /**
     * deliveryTypeNoを取得します。
     * 
     * @return deliveryTypeNo
     */
    public Long getDeliveryTypeNo() {
      return deliveryTypeNo;
    }

    /**
     * deliveryTypeNoを設定します。
     * 
     * @param deliveryTypeNo
     *          deliveryTypeNo
     */
    public void setDeliveryTypeNo(Long deliveryTypeNo) {
      this.deliveryTypeNo = deliveryTypeNo;
    }

    /**
     * deleteButtonDisplayを取得します。
     * 
     * @return deleteButtonDisplay
     */
    public boolean isDeleteButtonDisplay() {
      return deleteButtonDisplay;
    }

    /**
     * deleteButtonDisplayを設定します。
     * 
     * @param deleteButtonDisplay
     *          deleteButtonDisplay
     */
    public void setDeleteButtonDisplay(boolean deleteButtonDisplay) {
      this.deleteButtonDisplay = deleteButtonDisplay;
    }

    /**
     * cancelReturnItemFlgを取得します。
     * 
     * @return cancelReturnItemFlg
     */
    public boolean isCancelReturnItemFlg() {
      return cancelReturnItemFlg;
    }

    /**
     * cancelReturnItemFlgを設定します。
     * 
     * @param cancelReturnItemFlg
     *          cancelReturnItemFlg
     */
    public void setCancelReturnItemFlg(boolean cancelReturnItemFlg) {
      this.cancelReturnItemFlg = cancelReturnItemFlg;
    }

  }

  /**
   * returnShippingNoを取得します。
   * 
   * @return returnShippingNo
   */
  public String getReturnShippingNo() {
    return returnShippingNo;
  }

  /**
   * returnShippingNoを設定します。
   * 
   * @param returnShippingNo
   *          returnShippingNo
   */
  public void setReturnShippingNo(String returnShippingNo) {
    this.returnShippingNo = returnShippingNo;
  }

  /**
   * returnSkuListを取得します。
   * 
   * @return returnSkuList
   */
  public List<CodeAttribute> getReturnSkuList() {
    return returnSkuList;
  }

  /**
   * returnSkuListを設定します。
   * 
   * @param returnSkuList
   *          returnSkuList
   */
  public void setReturnSkuList(List<CodeAttribute> returnSkuList) {
    this.returnSkuList = returnSkuList;
  }

  /**
   * returnShippingChargeを取得します。
   * 
   * @return returnShippingCharge
   */
  public String getReturnShippingCharge() {
    return returnShippingCharge;
  }

  /**
   * returnShippingChargeを設定します。
   * 
   * @param returnShippingCharge
   *          returnShippingCharge
   */
  public void setReturnShippingCharge(String returnShippingCharge) {
    this.returnShippingCharge = returnShippingCharge;
  }

  /**
   * confirmButtonDisplayを取得します。
   * 
   * @return confirmButtonDisplay
   */
  public boolean isConfirmButtonDisplay() {
    return confirmButtonDisplay;
  }

  /**
   * confirmButtonDisplayを設定します。
   * 
   * @param confirmButtonDisplay
   *          confirmButtonDisplay
   */
  public void setConfirmButtonDisplay(boolean confirmButtonDisplay) {
    this.confirmButtonDisplay = confirmButtonDisplay;
  }

  /**
   * registerButtonDisplayを取得します。
   * 
   * @return registerButtonDisplay
   */
  public boolean isRegisterButtonDisplay() {
    return registerButtonDisplay;
  }

  /**
   * registerButtonDisplayを設定します。
   * 
   * @param registerButtonDisplay
   *          registerButtonDisplay
   */
  public void setRegisterButtonDisplay(boolean registerButtonDisplay) {
    this.registerButtonDisplay = registerButtonDisplay;
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

}
