package jp.co.sint.webshop.service.data;

import java.io.File;

import jp.co.sint.webshop.utility.StringUtil;

public class SkuImage implements ImageInfo {

  private String shopCode;

  private String skuCode;

  private File contentsFile;

  private ImageType imageType;

  public SkuImage() {
  }

  public SkuImage(String shopCode, String skuCode) {
    setShopCode(shopCode);
    setSkuCode(skuCode);
  }

  public String[] getSuffixies() {
    String[] suffixies = {
        ImageType.SKU_IMAGE_PC.getSuffix(), ImageType.SKU_IMAGE_MOBILE.getSuffix()
    };
    return suffixies;
  }

  public ContentsType getContentsType() {
    return ContentsType.IMAGE_DATA_SHOP_COMMODITY;
  }

  /**
   * skuCdoeを取得します。
   * 
   * @return skuCdoe
   */

  public String getSkuCode() {
    return skuCode;
  }

  /**
   * contentsFileを設定します。
   * 
   * @param contentsFile
   *          contentsFile
   */
  public void setContentsFile(File contentsFile) {
    this.contentsFile = contentsFile;
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
   * skuCodeを設定します。
   * 
   * @param skuCode
   *          skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
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
   * shopCodeを取得します。
   * 
   * @return shopCode
   */

  public String getShopCode() {
    return shopCode;
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
    if (StringUtil.hasValue(skuCode) && imageType != null) {
      uploadFileName = skuCode + imageType.getSuffix() + ".jpg";
    }
    return uploadFileName;
  }

}
