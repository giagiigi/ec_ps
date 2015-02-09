package jp.co.sint.webshop.web.bean.front.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.action.front.order.PaymentSupporterFactory;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020130:お支払い方法選択のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentBean extends OrderBean {

  private static final long serialVersionUID = 1L;

  /**
   * cardExpirationYearListを返します。
   * 
   * @return the cardExpirationYearList
   */
  public List<NameValue> getCardExpirationYearList() {
    return cardExpirationYearList;
  }

  /**
   * cardExpirationYearListを設定します。
   * 
   * @param cardExpirationYearList
   *          設定する cardExpirationYearList
   */
  public void setCardExpirationYearList(List<NameValue> cardExpirationYearList) {
    this.cardExpirationYearList = cardExpirationYearList;
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
    // お支払い情報取得
    PaymentSupporterFactory.createPaymentSuppoerter().copyOrderPaymentMethod(reqparam, orderPayment);

    setUseCouponList(Arrays.asList(reqparam.getAll("useCouponList")));

    String mode = "";
    if (reqparam.getPathArgs().length > 0) {
      mode = reqparam.getPathArgs()[0];
    }

    if (pointInfo != null && getCashier().isUsablePoint() && !mode.equals("shipping") && !mode.equals("confirm")) {
      pointInfo.setUsePoint(reqparam.get("usePoint"));
    }
    
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2020130";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.order.PaymentBean.0");
  }

  /** お支払い情報 */
  private PaymentMethodBean orderPayment;

  private PointInfoBean pointInfo;

  private List<String> errorMessages = new ArrayList<String>();

  private List<NameValue> cardExpirationYearList = new ArrayList<NameValue>();

  private boolean displayPointButton;

  private String displayPointEditMode;
  
  private String couponFunctionEnabledFlg;

  /**
   * U2020130:お支払い方法選択のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PointInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // modified by zhanghaibin start 2010-05-19
    private BigDecimal totalCommodityPrice = BigDecimal.ZERO;;

    private BigDecimal totalGiftPrice = BigDecimal.ZERO;;

    private BigDecimal totalShippingPrice = BigDecimal.ZERO;
    
    //add by V10-CH start
    private BigDecimal couponTotalPrice = BigDecimal.ZERO;
    //add by V10-CH end

    @Required
    // @Precision(precision = 10, scale = 2)
    @Point
    @Metadata(name = "使用ポイント", order = 1)
    private String usePoint;

    private BigDecimal totalAllPrice = BigDecimal.ZERO;;

    private BigDecimal restPoint = BigDecimal.ZERO;;

    // modified by zhanghaibin end 2010-05-19

    /**
     * usePointを取得します。
     * 
     * @return usePoint
     */
    public String getUsePoint() {
      return usePoint;
    }

    /**
     * usePointを設定します。
     * 
     * @param usePoint
     *          usePoint
     */
    public void setUsePoint(String usePoint) {
      this.usePoint = usePoint;
    }

    public BigDecimal getRestPoint() {
      return restPoint;
    }

    public void setRestPoint(BigDecimal restPoint) {
      this.restPoint = restPoint;
    }

    public BigDecimal getTotalAllPrice() {
      return totalAllPrice;
    }

    public void setTotalAllPrice(BigDecimal totalAllPrice) {
      this.totalAllPrice = totalAllPrice;
    }

    public BigDecimal getTotalCommodityPrice() {
      return totalCommodityPrice;
    }

    public void setTotalCommodityPrice(BigDecimal totalCommodityPrice) {
      this.totalCommodityPrice = totalCommodityPrice;
    }

    public BigDecimal getTotalGiftPrice() {
      return totalGiftPrice;
    }

    public void setTotalGiftPrice(BigDecimal totalGiftPrice) {
      this.totalGiftPrice = totalGiftPrice;
    }

    public BigDecimal getTotalShippingPrice() {
      return totalShippingPrice;
    }

    public void setTotalShippingPrice(BigDecimal totalShippingPrice) {
      this.totalShippingPrice = totalShippingPrice;
    }

    
    public BigDecimal getCouponTotalPrice() {
      return couponTotalPrice;
    }

    
    public void setCouponTotalPrice(BigDecimal couponTotalPrice) {
      this.couponTotalPrice = couponTotalPrice;
    }

  }

  // add by V10-CH start

  private List<CouponBean> couponList = new ArrayList<CouponBean>();

  private List<String> useCouponList = new ArrayList<String>();

  public static class CouponBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerCouponId;
    
    private String selectCouponId;

    private String couponName;

    private String couponPrice;

    private String useEndDate;

    public String getCouponName() {
      return couponName;
    }

    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    public String getCouponPrice() {
      return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
      this.couponPrice = couponPrice;
    }

    public String getUseEndDate() {
      return useEndDate;
    }

    public void setUseEndDate(String useEndDate) {
      this.useEndDate = useEndDate;
    }

    public static long getSerialVersionUID() {
      return serialVersionUID;
    }

    public String getCustomerCouponId() {
      return customerCouponId;
    }

    public void setCustomerCouponId(String customerCouponId) {
      this.customerCouponId = customerCouponId;
    }

    
    public String getSelectCouponId() {
      return selectCouponId;
    }

    
    public void setSelectCouponId(String selectCouponId) {
      this.selectCouponId = selectCouponId;
    }

  }

  // add by V10-CH end

  /**
   * orderPaymentを取得します。
   * 
   * @return orderPayment
   */
  public PaymentMethodBean getOrderPayment() {
    return orderPayment;
  }

  /**
   * orderPaymentを設定します。
   * 
   * @param orderPayment
   *          orderPayment
   */
  public void setOrderPayment(PaymentMethodBean orderPayment) {
    this.orderPayment = orderPayment;
  }

  /**
   * pointInfoを取得します。
   * 
   * @return pointInfo
   */
  public PointInfoBean getPointInfo() {
    return pointInfo;
  }

  /**
   * pointInfoを設定します。
   * 
   * @param pointInfo
   *          pointInfo
   */
  public void setPointInfo(PointInfoBean pointInfo) {
    this.pointInfo = pointInfo;
  }

  /**
   * errorMessagesを取得します。
   * 
   * @return the errorMessages
   */
  public List<String> getErrorMessages() {
    return errorMessages;
  }

  /**
   * errorMessagesを設定します。
   * 
   * @param errorMessages
   *          errorMessages
   */
  public void setErrorMessages(List<String> errorMessages) {
    this.errorMessages = errorMessages;
  }

  /**
   * displayPointButtonを返します。
   * 
   * @return the displayPointButton
   */
  public boolean isDisplayPointButton() {
    return displayPointButton;
  }

  /**
   * displayPointEditModeを返します。
   * 
   * @return the displayPointEditMode
   */
  public String getDisplayPointEditMode() {
    return displayPointEditMode;
  }

  /**
   * displayPointButtonを設定します。
   * 
   * @param displayPointButton
   *          設定する displayPointButton
   */
  public void setDisplayPointButton(boolean displayPointButton) {
    this.displayPointButton = displayPointButton;
  }

  /**
   * displayPointEditModeを設定します。
   * 
   * @param displayPointEditMode
   *          設定する displayPointEditMode
   */
  public void setDisplayPointEditMode(String displayPointEditMode) {
    this.displayPointEditMode = displayPointEditMode;
  }

  public List<CouponBean> getCouponList() {
    return couponList;
  }

  public void setCouponList(List<CouponBean> couponList) {
    this.couponList = couponList;
  }

  public List<String> getUseCouponList() {
    return useCouponList;
  }

  public void setUseCouponList(List<String> useCouponList) {
    this.useCouponList = useCouponList;
  }

  
  public String getCouponFunctionEnabledFlg() {
    return couponFunctionEnabledFlg;
  }

  
  public void setCouponFunctionEnabledFlg(String couponFunctionEnabledFlg) {
    this.couponFunctionEnabledFlg = couponFunctionEnabledFlg;
  }

}
