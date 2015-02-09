package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060310:キャンペーン一覧のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CampaignListBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CampaignListDetailBean> list = new ArrayList<CampaignListDetailBean>();

  private PagerValue pagerValue;

  private boolean displayShopNameColumn = false;

  /**
   * U2060310:キャンペーン一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CampaignListDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String shopName;

    private String campaignCode;

    private String campaignName;

    private String campaignStartDate;

    private String campaignEndDate;

    private String campaignDiscountRate;

    private String campaignCommodityCount;

    private boolean campaignExists;

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
     * campaignExistsを取得します。
     * 
     * @return campaignExists
     */
    public boolean isCampaignExists() {
      return campaignExists;
    }

    /**
     * campaignExistsを設定します。
     * 
     * @param campaignExists
     *          campaignExists
     */
    public void setCampaignExists(boolean campaignExists) {
      this.campaignExists = campaignExists;
    }

    /**
     * campaignCommodityCountを取得します。
     * 
     * @return campaignCommodityCount
     */

    public String getCampaignCommodityCount() {
      return campaignCommodityCount;
    }

    /**
     * campaignCommodityCountを設定します。
     * 
     * @param campaignCommodityCount
     *          campaignCommodityCount
     */
    public void setCampaignCommodityCount(String campaignCommodityCount) {
      this.campaignCommodityCount = campaignCommodityCount;
    }

  }

  /**
   * キャンペーンlistを取得します。
   * 
   * @return list
   */
  public List<CampaignListDetailBean> getList() {
    return list;
  }

  /**
   * キャンペーンlistを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CampaignListDetailBean> list) {
    this.list = list;
  }

  /**
   * displayShopNameColumnを取得します。
   * 
   * @return displayShopNameColumn
   */
  public boolean isDisplayShopNameColumn() {
    return displayShopNameColumn;
  }

  /**
   * displayShopNameColumnを設定します。
   * 
   * @param displayShopNameColumn
   *          displayShopNameColumn
   */
  public void setDisplayShopNameColumn(boolean displayShopNameColumn) {
    this.displayShopNameColumn = displayShopNameColumn;
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
    return "U2060310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.CampaignListBean.0");
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
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.catalog.CampaignListBean.0"), "/app/catalog/campaign_list/init"));
    return topicPath;
  }
}
