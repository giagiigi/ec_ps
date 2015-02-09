package jp.co.sint.webshop.service.data;

import java.io.File;

import jp.co.sint.webshop.utility.DIContainer;

public class CampaignContents implements ContentsInfo {

  private String shopCode;

  private String campaignCode;

  private File contentsFile;

  public CampaignContents() {
  }

  public CampaignContents(String shopCode, String campaignCode) {
    setShopCode(shopCode);
    setCampaignCode(campaignCode);
  }

  public ContentsType getContentsType() {
    if (DIContainer.getWebshopConfig().isOne()) {
      return ContentsType.CONTENT_SHOP_CAMPAIGN_SITE;
    } else {
      return ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP;
    }
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

  /**
   * contentsFileを取得します。
   * 
   * @return contentsFile
   */
  public File getContentsFile() {
    return contentsFile;
  }

  /**
   * contentsFileを設定します。
   * 
   * @param contentsFile
   *          設定する contentsFile
   */
  public void setContentsFile(File contentsFile) {
    this.contentsFile = contentsFile;
  }

}
