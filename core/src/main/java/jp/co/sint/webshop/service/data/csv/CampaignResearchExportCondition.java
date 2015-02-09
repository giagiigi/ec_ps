package jp.co.sint.webshop.service.data.csv;

import jp.co.sint.webshop.service.data.CsvConditionImpl;

public class CampaignResearchExportCondition extends CsvConditionImpl<CampaignResearchCsvSchema> {

  /** serial versionUID */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String campaignCode;

  /**
   * campaignCodeを取得します。
   * 
   * @return campaignCode
   */

  public String getCampaignCode() {
    return campaignCode;
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
