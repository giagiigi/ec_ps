package jp.co.sint.webshop.web.bean.back.communication;

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
public class CampaignResearchBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String campaignCode;

  private String campaignName;

  private String campaignDiscountRate;

  private String campaignStartDate;

  private String campaignEndDate;

  private String orderAmount;

  private String totalSales;

  private boolean csvButtonDisplay;

  private String shopCode;

  private List<CampaignCommodityResearchBean> list = new ArrayList<CampaignCommodityResearchBean>();

  /**
   * U1060340:キャンペーン分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CampaignCommodityResearchBean implements Serializable {

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

  /**
   * csvButtonDisplayを取得します。
   * 
   * @return csvButtonDisplay
   */
  public boolean isCsvButtonDisplay() {
    return csvButtonDisplay;
  }

  /**
   * csvButtonDisplayを設定します。
   * 
   * @param csvButtonDisplay
   */
  public void setCsvButtonDisplay(boolean csvButtonDisplay) {
    this.csvButtonDisplay = csvButtonDisplay;
  }

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * campaignDiscountRateを取得します。
   * 
   * @return campaignDiscountRate
   */
  public String getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  /**
   * campaignEndDateを取得します。
   * 
   * @return campaignEndDate
   */
  public String getCampaignEndDate() {
    return campaignEndDate;
  }

  /**
   * campaignNameを取得します。
   * 
   * @return campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  /**
   * campaignStartDateを取得します。
   * 
   * @return campaignStartDate
   */
  public String getCampaignStartDate() {
    return campaignStartDate;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<CampaignCommodityResearchBean> getList() {
    return list;
  }

  /**
   * orderAmountを取得します。
   * 
   * @return orderAmount
   */
  public String getOrderAmount() {
    return orderAmount;
  }

  /**
   * totalSalesを取得します。
   * 
   * @return totalSales
   */
  public String getTotalSales() {
    return totalSales;
  }

  /**
   * campaignCodeを設定します。
   * 
   * @param campaignCode
   *          campaignCode
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  /**
   * campaignDiscountRateを設定します。
   * 
   * @param campaignDiscountRate
   *          campaignDiscountRate
   */
  public void setCampaignDiscountRate(String campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  /**
   * campaignEndDateを設定します。
   * 
   * @param campaignEndDate
   *          campaignEndDate
   */
  public void setCampaignEndDate(String campaignEndDate) {
    this.campaignEndDate = campaignEndDate;
  }

  /**
   * campaignNameを設定します。
   * 
   * @param campaignName
   *          campaignName
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  /**
   * campaignStartDateを設定します。
   * 
   * @param campaignStartDate
   *          campaignStartDate
   */
  public void setCampaignStartDate(String campaignStartDate) {
    this.campaignStartDate = campaignStartDate;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CampaignCommodityResearchBean> list) {
    this.list = list;
  }

  /**
   * orderAmountを設定します。
   * 
   * @param orderAmount
   *          orderAmount
   */
  public void setOrderAmount(String orderAmount) {
    this.orderAmount = orderAmount;
  }

  /**
   * totalSalesを設定します。
   * 
   * @param totalSales
   *          totalSales
   */
  public void setTotalSales(String totalSales) {
    this.totalSales = totalSales;
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
    return Messages.getString("web.bean.back.communication.CampaignResearchBean.0");
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

}
