package jp.co.sint.webshop.service.data;

import java.io.File;

import jp.co.sint.webshop.utility.StringUtil;

public class GiftImage implements ImageInfo {

  private String shopCode;

  private String giftCode;

  private File contentsFile;

  private ImageType imageType;

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

  public GiftImage() {
  }

  public GiftImage(String shopCode, String giftCode) {
    setShopCode(shopCode);
    setGiftCode(giftCode);
  }

  public ContentsType getContentsType() {
    return ContentsType.IMAGE_DATA_SHOP_GIFT;
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
   * giftCodeを返します。
   * 
   * @return the giftCode
   */
  public String getGiftCode() {
    return giftCode;
  }

  /**
   * giftCodeを設定します。
   * 
   * @param giftCode
   *          設定する giftCode
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  public String[] getSuffixies() {
    String[] suffixies = {
      ImageType.GIFT_IMAGE.getSuffix()
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
    if (StringUtil.hasValue(giftCode) && imageType != null) {
      uploadFileName = giftCode + imageType.getSuffix() + ".jpg";
    }
    return uploadFileName;
  }

}
