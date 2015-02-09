package jp.co.sint.webshop.service.data;

public class ContentsFile {

  private String contentsIndexHtml;

  private String sitelogoImagePrefix;

  private String qrcodeImagePrefix;

  private String commodityImagePrefix;

  private String commodityMobileImagePrefix;

  private String thumbnailImagePrefix;

  private String giftImagePrefix;

  private String imageExtension;

  private String mobileGuideFileName;

  private String mobileComplianceFileName;

  private String mobilePrivacyFileName;

  private String mobileRuletouseFileName;

  public ContentsFile() {

  }

  /**
   * @return the mobileRuletouseFileName
   */
  public String getMobileRuletouseFileName() {
    return mobileRuletouseFileName;
  }

  /**
   * @param mobileRuletouseFileName
   *          the mobileRuletouseFileName to set
   */
  public void setMobileRuletouseFileName(String mobileRuletouseFileName) {
    this.mobileRuletouseFileName = mobileRuletouseFileName;
  }

  /**
   * commodityImagePrefixを取得します。
   * 
   * @return commodityImagePrefix
   */
  public String getCommodityImagePrefix() {
    return commodityImagePrefix;
  }

  /**
   * commodityImagePrefixを設定します。
   * 
   * @param commodityImagePrefix
   *          commodityImagePrefix
   */
  public void setCommodityImagePrefix(String commodityImagePrefix) {
    this.commodityImagePrefix = commodityImagePrefix;
  }

  /**
   * commodityMobileImagePrefixを取得します。
   * 
   * @return commodityMobileImagePrefix
   */
  public String getCommodityMobileImagePrefix() {
    return commodityMobileImagePrefix;
  }

  /**
   * commodityMobileImagePrefixを設定します。
   * 
   * @param commodityMobileImagePrefix
   *          commodityMobileImagePrefix
   */
  public void setCommodityMobileImagePrefix(String commodityMobileImagePrefix) {
    this.commodityMobileImagePrefix = commodityMobileImagePrefix;
  }

  /**
   * contentsIndexHtmlを取得します。
   * 
   * @return contentsIndexHtml
   */
  public String getContentsIndexHtml() {
    return contentsIndexHtml;
  }

  /**
   * contentsIndexHtmlを設定します。
   * 
   * @param contentsIndexHtml
   *          contentsIndexHtml
   */
  public void setContentsIndexHtml(String contentsIndexHtml) {
    this.contentsIndexHtml = contentsIndexHtml;
  }

  /**
   * giftImagePrefixを取得します。
   * 
   * @return giftImagePrefix
   */
  public String getGiftImagePrefix() {
    return giftImagePrefix;
  }

  /**
   * giftImagePrefixを設定します。
   * 
   * @param giftImagePrefix
   *          giftImagePrefix
   */
  public void setGiftImagePrefix(String giftImagePrefix) {
    this.giftImagePrefix = giftImagePrefix;
  }

  /**
   * imageExtensionを取得します。
   * 
   * @return imageExtension
   */
  public String getImageExtension() {
    return imageExtension;
  }

  /**
   * imageExtensionを設定します。
   * 
   * @param imageExtension
   *          imageExtension
   */
  public void setImageExtension(String imageExtension) {
    this.imageExtension = imageExtension;
  }

  /**
   * qrcodeImagePrefixを取得します。
   * 
   * @return qrcodeImagePrefix
   */
  public String getQrcodeImagePrefix() {
    return qrcodeImagePrefix;
  }

  /**
   * qrcodeImagePrefixを設定します。
   * 
   * @param qrcodeImagePrefix
   *          qrcodeImagePrefix
   */
  public void setQrcodeImagePrefix(String qrcodeImagePrefix) {
    this.qrcodeImagePrefix = qrcodeImagePrefix;
  }

  /**
   * sitelogoImagePrefixを取得します。
   * 
   * @return sitelogoImagePrefix
   */
  public String getSitelogoImagePrefix() {
    return sitelogoImagePrefix;
  }

  /**
   * sitelogoImagePrefixを設定します。
   * 
   * @param sitelogoImagePrefix
   *          sitelogoImagePrefix
   */
  public void setSitelogoImagePrefix(String sitelogoImagePrefix) {
    this.sitelogoImagePrefix = sitelogoImagePrefix;
  }

  /**
   * thumbnailImagePrefixを取得します。
   * 
   * @return thumbnailImagePrefix
   */
  public String getThumbnailImagePrefix() {
    return thumbnailImagePrefix;
  }

  /**
   * thumbnailImagePrefixを設定します。
   * 
   * @param thumbnailImagePrefix
   *          thumbnailImagePrefix
   */
  public void setThumbnailImagePrefix(String thumbnailImagePrefix) {
    this.thumbnailImagePrefix = thumbnailImagePrefix;
  }

  /**
   * mobilePrivacyFileNameを取得します。
   * 
   * @return mobilePrivacyFileName
   */
  public String getMobilePrivacyFileName() {
    return mobilePrivacyFileName;
  }

  /**
   * mobilePrivacyFileNameを設定します。
   * 
   * @param mobilePrivacyFileName
   *          設定する mobilePrivacyFileName
   */
  public void setMobilePrivacyFileName(String mobilePrivacyFileName) {
    this.mobilePrivacyFileName = mobilePrivacyFileName;
  }

  /**
   * mobileComplianceFileNameを取得します。
   * 
   * @return mobileComplianceFileName
   */
  public String getMobileComplianceFileName() {
    return mobileComplianceFileName;
  }

  /**
   * mobileComplianceFileNameを設定します。
   * 
   * @param mobileComplianceFileName
   *          設定する mobileComplianceFileName
   */
  public void setMobileComplianceFileName(String mobileComplianceFileName) {
    this.mobileComplianceFileName = mobileComplianceFileName;
  }

  /**
   * mobileGuideFileNameを取得します。
   * 
   * @return mobileGuideFileName
   */
  public String getMobileGuideFileName() {
    return mobileGuideFileName;
  }

  /**
   * mobileGuideFileNameを設定します。
   * 
   * @param mobileGuideFileName
   *          設定する mobileGuideFileName
   */
  public void setMobileGuideFileName(String mobileGuideFileName) {
    this.mobileGuideFileName = mobileGuideFileName;
  }

}
