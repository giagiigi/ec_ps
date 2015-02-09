package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum ShopInfoTag implements MailTemplateTag {
  /** ショップ名称 */
  SHOP_NAME("[@SHOP_NAME@]", "ショップ名称", "ショップ名称", true),
  /** ショップ紹介URL */
  SHOP_INTRODUCED_URL("[@SHOP_INTRODUCED_URL@]", "ショップ紹介URL", "http://www.example.com/"),
  /** メールアドレス */
  EMAIL("[@EMAIL@]", "メールアドレス", "test@example.com"),
  /** 郵便番号 */
  POSTAL_CODE("[@POSTAL_CODE@]", "郵便番号", "100002"),
  /** 住所1 */
  ADDRESS1("[@ADDRESS1@]", "住所1", "東京都"),
  /** 住所2 */
  ADDRESS2("[@ADDRESS2@]", "住所2", "千代田区"),
  /** 住所3 */
  ADDRESS3("[@ADDRESS3@]", "住所3", "千代田"),
  //modify by V10-CH 170 start  
//  /** 住所4 */
//  ADDRESS4("[@ADDRESS4@]", "住所4", "×××マンション"),
  //modify by V10-CH 170 end  
  /** 電話番号 */
  PHONE_NUMBER("[@PHONE_NUMBER@]", "電話番号", "000-000-0000"),
  //Add by V10-CH start
  /** 手机号码 */ 
  MOBILE_NUMBER("[@MOBILE_NUMBER@]","手机号码","00000000000"),
  //Add by V10-CH end 
  /** 担当者 */
  PERSON_IN_CHARGE("[@PERSON_IN_CHARGE@]", "担当者", "担当者");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private ShopInfoTag(String value, String name, String dummyData) {
    init(value, name, dummyData, false);
  }

  private ShopInfoTag(String value, String name, String dummyData, boolean required) {
    init(value, name, dummyData, required);

  }

  private void init(String initValue, String initName, String initDummyData, boolean initRequired) {
    this.value = initValue;
    this.name = initName;
    this.dummyData = initDummyData;
    this.required = initRequired;
  }

  public String getDummyData() {
    return StringUtil.coalesce(CodeUtil.getDumyData(this), this.dummyData);
  }

  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  public String getValue() {
    return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
  }

  public String getTagDiv() {
    return SHOP_INFO;
  }

  public boolean isRequired() {
    return required;
  }

}
