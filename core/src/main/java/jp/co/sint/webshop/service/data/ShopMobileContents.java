package jp.co.sint.webshop.service.data;

import java.io.File;

public class ShopMobileContents implements ContentsInfo {
  
  private File contentsFile;
  
  private String shopCode;

  
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
   * @param shopCode 設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public ShopMobileContents(String shopCode) {
    setShopCode(shopCode);
  }

  public ContentsType getContentsType() {
    return ContentsType.CONTENT_SHOP_MOBILE;
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
   * @param contentsFile 設定する contentsFile
   */
  public void setContentsFile(File contentsFile) {
    this.contentsFile = contentsFile;
  }

}
