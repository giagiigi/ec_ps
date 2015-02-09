package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060340:キャンペーン分析のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CouponResearchBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String couponIssueNo;

  private String couponName;

  private String couponPrice;

  private String getCouponPrice;

  private String bonusCouponStartDate;

  private String bonusCouponEndDate;

  private String useCoupoStartDate;

  private String useCouponIssueEndDate;

  private String shopCode;

  /* 优惠券发行总金额 */
  private String issueTotalPrice;

  /* 优惠券使用总金额 */
  private String usedTotalPrice;

  /* 含优惠券订单的总金额 */
  private String orderTotalPrice;

  /* 未使用金额 */
  private String enableTotalPrice;

  /* 废弃金额 */
  private String disableTotalPrice;

  /* 过期金额 */
  private String overdueTotalPrice;

  /* 未激活金额 */
  private String phantomTotalPrice;

  private List<CouponCommodityResearchBean> list = new ArrayList<CouponCommodityResearchBean>();

  /**
   * U1060340:キャンペーン分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CouponCommodityResearchBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String commodityName;

    private String commoditySalesAmount;

    /**
     * commodityCodeを取得します。
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
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
     * commoditySalesAmountを取得します。
     * 
     * @return commoditySalesAmount
     */
    public String getCommoditySalesAmount() {
      return commoditySalesAmount;
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
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * commoditySalesAmountを設定します。
     * 
     * @param commoditySalesAmount
     *          commoditySalesAmount
     */
    public void setCommoditySalesAmount(String commoditySalesAmount) {
      this.commoditySalesAmount = commoditySalesAmount;
    }

  }

  public String getBonusCouponEndDate() {
    return bonusCouponEndDate;
  }

  public void setBonusCouponEndDate(String bonusCouponEndDate) {
    this.bonusCouponEndDate = bonusCouponEndDate;
  }

  public String getBonusCouponStartDate() {
    return bonusCouponStartDate;
  }

  public void setBonusCouponStartDate(String bonusCouponStartDate) {
    this.bonusCouponStartDate = bonusCouponStartDate;
  }

  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

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

  public String getGetCouponPrice() {
    return getCouponPrice;
  }

  public void setGetCouponPrice(String getCouponPrice) {
    this.getCouponPrice = getCouponPrice;
  }

  public List<CouponCommodityResearchBean> getList() {
    return list;
  }

  public void setList(List<CouponCommodityResearchBean> list) {
    this.list = list;
  }

  public String getUseCouponIssueEndDate() {
    return useCouponIssueEndDate;
  }

  public void setUseCouponIssueEndDate(String useCouponIssueEndDate) {
    this.useCouponIssueEndDate = useCouponIssueEndDate;
  }

  public String getUseCoupoStartDate() {
    return useCoupoStartDate;
  }

  public void setUseCoupoStartDate(String useCoupoStartDate) {
    this.useCoupoStartDate = useCoupoStartDate;
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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060340";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.CouponResearchBean.0");
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

  public String getIssueTotalPrice() {
    return issueTotalPrice;
  }

  public void setIssueTotalPrice(String issueTotalPrice) {
    this.issueTotalPrice = issueTotalPrice;
  }

  public String getOrderTotalPrice() {
    return orderTotalPrice;
  }

  public void setOrderTotalPrice(String orderTotalPrice) {
    this.orderTotalPrice = orderTotalPrice;
  }

  public String getUsedTotalPrice() {
    return usedTotalPrice;
  }

  public void setUsedTotalPrice(String usedTotalPrice) {
    this.usedTotalPrice = usedTotalPrice;
  }

  public String getDisableTotalPrice() {
    return disableTotalPrice;
  }

  public void setDisableTotalPrice(String disableTotalPrice) {
    this.disableTotalPrice = disableTotalPrice;
  }

  public String getEnableTotalPrice() {
    return enableTotalPrice;
  }

  public void setEnableTotalPrice(String enableTotalPrice) {
    this.enableTotalPrice = enableTotalPrice;
  }

  public String getOverdueTotalPrice() {
    return overdueTotalPrice;
  }

  public void setOverdueTotalPrice(String overdueTotalPrice) {
    this.overdueTotalPrice = overdueTotalPrice;
  }

  public String getPhantomTotalPrice() {
    return phantomTotalPrice;
  }

  public void setPhantomTotalPrice(String phantomTotalPrice) {
    this.phantomTotalPrice = phantomTotalPrice;
  }

}
