package jp.co.sint.webshop.service.data;

import java.io.File;

public class ShopComplianceContents implements ContentsInfo {

  public ShopComplianceContents() {
  }

  public ShopComplianceContents(String shopCode) {
    this.shopCode = shopCode;
  }

  private File contentsFile;

  private String shopCode;

  public ContentsType getContentsType() {
    return ContentsType.CONTENT_SHOP_COMPLIANCE;
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
   *          contentsFile
   */
  public void setContentsFile(File contentsFile) {
    this.contentsFile = contentsFile;
  }

}
