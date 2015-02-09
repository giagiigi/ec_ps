package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum PaymentReminderTag implements SmsTemplateTag {

  /** 受注番号 */
  ORDER_NO("[@ORDER_NO@]", "受注番号", "000000"),
  /** 姓 */
  LAST_NAME("[@NAME@]", "姓", "山田", true),
  //modify by V10-CH 170 start  
//  /** 名 */
//  FIRST_NAME("[@FIRST_NAME@]", "名", "太郎", true),
//  /** 姓カナ */
//  LAST_NAME_KANA("[@LAST_NAME_KANA@]", "姓カナ", "ヤマダ"),
//  /** 名カナ */
//  FIRST_NAME_KANA("[@FIRST_NAME_KANA@]", "名カナ", "タロウ"),
  //modify by V10-CH 170 end  
  /** メールアドレス */
  EMAIL("[@EMAIL@]", "メールアドレス", "test@example.com"),
  /** 電話番号 */
  PHONE_NUMBER("[@PHONE_NUMBER@]", "電話番号", "000-000-0000"),
  //Add by V10-CH start
  MONILE_NUMBER("[@MOBILE_NUMBER@]","手机号码","00000000000"),
  //Add by V10-CH end
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
  /** 受注日時 */
  ORDER_DATETIME("[@ORDER_DATETIME@]", "受注日時", "2007/01/01 10:58:50"),
  /** 商品合計 */
  SUM_COMMODITY_PRICE("[@SUM_COMMODITY_PRICE@]", "商品合計", "1,000円"),
  /** ｷﾞﾌﾄ合計 */
  SUM_GIFT_PRICE("[@SUM_GIFT_PRICE@]", "ギフト合計", "500円"),
  /** 送料合計 */
  SUM_SHIPPING_CHARGE("[@SUM_SHIPPING_CHARGE@]", "送料合計", "100円"),
  /** お支払合計 */
  SUM_ALL_PRICE("[@SUM_ALL_PRICE@]", "お支払合計", "***** ポイント使用時、ポイント情報が表示されます *****\r\nお支払合計：1,000円");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private PaymentReminderTag(String value, String name, String dummyData, boolean required) {
    init(value, name, dummyData, required);
  }

  private PaymentReminderTag(String value, String name, String dummyData) {
    init(value, name, dummyData, false);
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
    return PAYMENT_CONFIRM;
  }

  public boolean isRequired() {
    return required;
  }

}
