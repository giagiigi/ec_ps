package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * キャンペーン情報のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CampaignInfoBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CampaignInfoDetailBean> list = new ArrayList<CampaignInfoDetailBean>();
  
  private List<CodeAttribute> codeList = new ArrayList<CodeAttribute>();

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<CampaignInfoDetailBean> getList() {
    return list;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CampaignInfoDetailBean> list) {
    this.list = list;
  }

  public static class CampaignInfoDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String shopName;

    private String campaignCode;

    private String campaignName;

    private String campaignStartDate;

    private String campaignEndDate;

    private String campaignDiscountRate;

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
  }

  
  /**
   * codeListを取得します。
   *
   * @return codeList
   */
  
  public List<CodeAttribute> getCodeList() {
    return codeList;
  }

  
  /**
   * codeListを設定します。
   *
   * @param codeList 
   *          codeList
   */
  public void setCodeList(List<CodeAttribute> codeList) {
    this.codeList = codeList;
  }

}
