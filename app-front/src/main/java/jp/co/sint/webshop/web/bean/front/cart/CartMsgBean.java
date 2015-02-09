package jp.co.sint.webshop.web.bean.front.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2020110:ショッピングカートのデータモデルです。
 * 
 * @author Kousen.
 */
public class CartMsgBean extends UIFrontBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> prefectureList = new ArrayList<CodeAttribute>();

  private String selPrefectureCode;

  private BigDecimal shippingCharge;

  private BigDecimal cartPriceForCharge;

  private BigDecimal cartWeightForCharge;

  private BigDecimal cartPriceForCoupon;

  private BigDecimal couponPrice;

  private Long couponProportion;

  /**
   * @return the selPrefectureCode
   */
  public String getSelPrefectureCode() {
    return selPrefectureCode;
  }

  /**
   * @param selPrefectureCode
   *          the selPrefectureCode to set
   */
  public void setSelPrefectureCode(String selPrefectureCode) {
    this.selPrefectureCode = selPrefectureCode;
  }

  /**
   * @return the prefectureList
   */
  public List<CodeAttribute> getPrefectureList() {
    return prefectureList;
  }

  /**
   * @param prefectureList
   *          the prefectureList to set
   */
  public void setPrefectureList(List<CodeAttribute> prefectureList) {
    this.prefectureList = prefectureList;
  }

  /**
   * @return the shippingCharge
   */
  public BigDecimal getShippingCharge() {
    return shippingCharge;
  }

  /**
   * @param shippingCharge
   *          the shippingCharge to set
   */
  public void setShippingCharge(BigDecimal shippingCharge) {
    this.shippingCharge = shippingCharge;
  }

  /**
   * @return the cartPriceForCharge
   */
  public BigDecimal getCartPriceForCharge() {
    return cartPriceForCharge;
  }

  /**
   * @param cartPriceForCharge
   *          the cartPriceForCharge to set
   */
  public void setCartPriceForCharge(BigDecimal cartPriceForCharge) {
    this.cartPriceForCharge = cartPriceForCharge;
  }

  /**
   * @return the cartWeightForCharge
   */
  public BigDecimal getCartWeightForCharge() {
    return cartWeightForCharge;
  }

  /**
   * @param cartWeightForCharge
   *          the cartWeightForCharge to set
   */
  public void setCartWeightForCharge(BigDecimal cartWeightForCharge) {
    this.cartWeightForCharge = cartWeightForCharge;
  }

  /**
   * @return the cartPriceForCoupon
   */
  public BigDecimal getCartPriceForCoupon() {
    return cartPriceForCoupon;
  }

  /**
   * @param cartPriceForCoupon
   *          the cartPriceForCoupon to set
   */
  public void setCartPriceForCoupon(BigDecimal cartPriceForCoupon) {
    this.cartPriceForCoupon = cartPriceForCoupon;
  }

  /**
   * @return the couponPrice
   */
  public BigDecimal getCouponPrice() {
    return couponPrice;
  }

  /**
   * @param couponPrice
   *          the couponPrice to set
   */
  public void setCouponPrice(BigDecimal couponPrice) {
    this.couponPrice = couponPrice;
  }

  /**
   * @return the couponProportion
   */
  public Long getCouponProportion() {
    return couponProportion;
  }

  /**
   * @param couponProportion
   *          the couponProportion to set
   */
  public void setCouponProportion(Long couponProportion) {
    this.couponProportion = couponProportion;
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
    this.setSelPrefectureCode(reqparam.get("selPrefectureCode"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2020110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.cart.CartBean.0");
  }

}
