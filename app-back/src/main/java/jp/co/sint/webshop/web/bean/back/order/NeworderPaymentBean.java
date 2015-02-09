package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Point;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.action.back.order.PaymentSupporterFactory;
import jp.co.sint.webshop.web.bean.PaymentMethodBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020150:新規受注(決済方法指定)のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderPaymentBean extends NeworderBaseBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  // modified by zhanghaibin start 2010-05-19
  private BigDecimal commodityTotalPrice;

  private BigDecimal giftTotalPrice;

  private BigDecimal shippingTotalPrice;

  @Metadata(name = "注文合計金額")
  private BigDecimal totalPrice;

  @Metadata(name = "支払金額")
  private BigDecimal paymentPrice;

  // modified by zhanghaibin end 2010-05-19
  private UsePointBean usePointBean = new UsePointBean();

  @Metadata(name = "連絡事項")
  @Length(200)
  private String message;

  @Metadata(name = "注意事項")
  @Length(200)
  private String caution;

  private String allPointPayment;

  private PaymentMethodBean payment;

  private List<CodeAttribute> displayPaymentMethod;

  private boolean displayPointButton;

  private String displayPointEditMode;

  private String couponFunctionEnabledFlg;

  // add by V10-CH start
  private String couponTotalPrice;

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
    reqparam.copy(this);
    String[] pathArgs = reqparam.getPathArgs();
    String mode = "";
    if (pathArgs.length > 0) {
      mode = pathArgs[0];
    }
    if (!mode.equals("confirm")) {
      reqparam.copy(usePointBean);
    }

    setUseCouponList(Arrays.asList(reqparam.getAll("useCouponList")));
    PaymentSupporterFactory.createPaymentSuppoerter().copyOrderPaymentMethod(reqparam, payment);

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1020150";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.order.NeworderPaymentBean.0");
  }

  /**
   * U1020150:新規受注(決済方法指定)のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class UsePointBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Required
    // @Precision(precision = 10, scale = 2)
    @Point
    @Metadata(name = "使用ポイント")
    private String usePoint = "0";

    // modified by zhanghaibin start 2010-05-19
    private BigDecimal restPoint;

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
   * displayPaymentMethodを取得します。
   * 
   * @return displayPaymentMethod
   */
  public List<CodeAttribute> getDisplayPaymentMethod() {
    return displayPaymentMethod;
  }

  /**
   * displayPaymentMethodを設定します。
   * 
   * @param displayPaymentMethod
   *          displayPaymentMethod
   */
  public void setDisplayPaymentMethod(List<CodeAttribute> displayPaymentMethod) {
    this.displayPaymentMethod = displayPaymentMethod;
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
   * paymentを取得します。
   * 
   * @return payment
   */
  public PaymentMethodBean getPayment() {
    return payment;
  }

  /**
   * paymentを設定します。
   * 
   * @param payment
   *          payment
   */
  public void setPayment(PaymentMethodBean payment) {
    this.payment = payment;
  }

  /**
   * usePointBeanを取得します。
   * 
   * @return usePointBean
   */
  public UsePointBean getUsePointBean() {
    return usePointBean;
  }

  /**
   * usePointBeanを設定します。
   * 
   * @param usePointBean
   *          usePointBean
   */
  public void setUsePointBean(UsePointBean usePointBean) {
    this.usePointBean = usePointBean;
  }

  /**
   * displayPointButtonを取得します。
   * 
   * @return displayPointButton
   */
  public boolean isDisplayPointButton() {
    return displayPointButton;
  }

  /**
   * displayPointEditModeを取得します。
   * 
   * @return displayPointEditMode
   */
  public String getDisplayPointEditMode() {
    return displayPointEditMode;
  }

  /**
   * displayPointButtonを設定します。
   * 
   * @param displayPointButton
   *          displayPointButton
   */
  public void setDisplayPointButton(boolean displayPointButton) {
    this.displayPointButton = displayPointButton;
  }

  /**
   * displayPointEditModeを設定します。
   * 
   * @param displayPointEditMode
   *          displayPointEditMode
   */
  public void setDisplayPointEditMode(String displayPointEditMode) {
    this.displayPointEditMode = displayPointEditMode;
  }

  public BigDecimal getCommodityTotalPrice() {
    return commodityTotalPrice;
  }

  public void setCommodityTotalPrice(BigDecimal commodityTotalPrice) {
    this.commodityTotalPrice = commodityTotalPrice;
  }

  public BigDecimal getGiftTotalPrice() {
    return giftTotalPrice;
  }

  public void setGiftTotalPrice(BigDecimal giftTotalPrice) {
    this.giftTotalPrice = giftTotalPrice;
  }

  public BigDecimal getPaymentPrice() {
    return paymentPrice;
  }

  public void setPaymentPrice(BigDecimal paymentPrice) {
    this.paymentPrice = paymentPrice;
  }

  public BigDecimal getShippingTotalPrice() {
    return shippingTotalPrice;
  }

  public void setShippingTotalPrice(BigDecimal shippingTotalPrice) {
    this.shippingTotalPrice = shippingTotalPrice;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getAllPointPayment() {
    return allPointPayment;
  }

  public void setAllPointPayment(String allPointPayment) {
    this.allPointPayment = allPointPayment;
  }

  public String getCouponFunctionEnabledFlg() {
    return couponFunctionEnabledFlg;
  }

  public void setCouponFunctionEnabledFlg(String couponFunctionEnabledFlg) {
    this.couponFunctionEnabledFlg = couponFunctionEnabledFlg;
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

  public String getCouponTotalPrice() {
    return couponTotalPrice;
  }

  public void setCouponTotalPrice(String couponTotalPrice) {
    this.couponTotalPrice = couponTotalPrice;
  }

}
