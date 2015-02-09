package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.EmailForSearch;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Phone;
import jp.co.sint.webshop.service.SearchCondition;

public class ShopListSearchCondition extends SearchCondition {

  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Metadata(name = "ショップコード")
  private String shopCode;

  private String shopName;

  @Length(18)
  @Phone
  @Metadata(name = "電話番号")
  private String tel;

  //Add by V10-CH start
  @Length(11)
  @MobileNumber
  @Metadata(name = "手机号码")
  private String mobileTel;
  //Add by V10-CH end
  
  @Length(256)
  @EmailForSearch
  @Metadata(name = "メールアドレス")
  private String email;

  private String shopStatus;

  /**
   * @return shopStatus
   */
  public String getShopStatus() {
    return shopStatus;
  }

  /**
   * @param shopStatus
   *          設定する shopStatus
   */
  public void setShopStatus(String shopStatus) {
    this.shopStatus = shopStatus;
  }

  /**
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          設定する email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * @param shopName
   *          設定する shopName
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * @return tel
   */
  public String getTel() {
    return tel;
  }

  /**
   * @param tel
   *          設定する tel
   */
  public void setTel(String tel) {
    this.tel = tel;
  }

  
  /**
   * mobileTelを取得します。
   *
   * @return mobileTel mobileTel
   */
  public String getMobileTel() {
    return mobileTel;
  }

  
  /**
   * mobileTelを設定します。
   *
   * @param mobileTel 
   *          mobileTel
   */
  public void setMobileTel(String mobileTel) {
    this.mobileTel = mobileTel;
  }

}
