package jp.co.sint.webshop.service.data;

import java.io.File;

import jp.co.sint.webshop.utility.StringUtil;

public class CommodityImage implements ImageInfo {

  private String shopCode;

  private String commodityCode;

  private File contentsFile;

  private ImageType imageType;

  public CommodityImage() {
  }

  public CommodityImage(String shopCode, String commodityCode) {
    setShopCode(shopCode);
    setCommodityCode(commodityCode);
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

  public CommodityImage(String shopCode) {
    setShopCode(shopCode);
  }

  public ContentsType getContentsType() {
    return ContentsType.IMAGE_DATA_SHOP_COMMODITY;
  }

  /**
   * commodityCodeを返します。
   * 
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
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

  public String[] getSuffixies() {
    String[] suffixies = {
        ImageType.COMMODITY_IMAGE_PC.getSuffix(), ImageType.COMMODITY_IMAGE_MOBILE.getSuffix(),
        ImageType.COMMODITY_THUMBNAIL_PC.getSuffix(), ImageType.COMMODITY_THUMBNAIL_MOBILE.getSuffix()
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
    if (StringUtil.hasValue(commodityCode) && imageType != null) {
      uploadFileName = commodityCode + imageType.getSuffix() + ".jpg";
    }
    return uploadFileName;
  }

}
