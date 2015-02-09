package jp.co.sint.webshop.service.data;

import java.io.File;

import jp.co.sint.webshop.utility.DIContainer;

public class ShopinfoContents implements ContentsInfo {

  private String shopCode;

  private File contentsFile;

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

  public ShopinfoContents() {
  }

  public ShopinfoContents(String shopCode) {
    setShopCode(shopCode);
  }

  public ContentsType getContentsType() {
    if (DIContainer.getWebshopConfig().isOne()) {
      return ContentsType.CONTENT_SHOP_SHOPINFO_SITE;
    } else {
      return ContentsType.CONTENT_SHOP_SHOPINFO_SHOP;
    }
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
