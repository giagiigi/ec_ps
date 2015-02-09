package jp.co.sint.webshop.service.data;

import java.io.File;

import jp.co.sint.webshop.utility.StringUtil;

public class IndexFloorImage implements ImageInfo {

  private String shopCode;

  private String contentStaticCode;

  private File contentsFile;

  private ImageType imageType;

  private String languageCode;

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

  public IndexFloorImage() {
  }

  public IndexFloorImage(String shopCode, String contentStaticCode, String languageCode) {
    setShopCode(shopCode);
    setContentStaticCode(contentStaticCode);
    setLanguageCode(languageCode);
  }

  public ContentsType getContentsType() {
    return ContentsType.INDEX_FLOOR_IMAGE;
  }

  /**
   * shopCodeを返します。
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the contentStaticCode
   */
  public String getContentStaticCode() {
    return contentStaticCode;
  }

  /**
   * @param contentStaticCode
   *          the contentStaticCode to set
   */
  public void setContentStaticCode(String contentStaticCode) {
    this.contentStaticCode = contentStaticCode;
  }

  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * @param languageCode
   *          the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  public String[] getSuffixies() {
    String[] suffixies = {
      ImageType.INDEX_FLOOR_IMAGE.getSuffix()
    };
    return suffixies;
  }

  /**
   * imageTypeを取得します。
   * 
   * @return imageType
   */
  public ImageType getImageType() {
    return imageType;
  }

  /**
   * imageTypeを設定します。
   * 
   * @param imageType
   *          imageType
   */
  public void setImageType(ImageType imageType) {
    this.imageType = imageType;
  }

  public String getUploadFileName() {
    String uploadFileName = "";
    if (StringUtil.hasValue(contentStaticCode) && imageType != null) {
      uploadFileName = contentStaticCode + imageType.getSuffix() + ".jpg";
    }
    return uploadFileName;
  }

}
