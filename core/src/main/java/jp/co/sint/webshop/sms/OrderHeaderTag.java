package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum OrderHeaderTag implements SmsTemplateTag {

  /** 受注番号 */
  ORDER_NO("[@ORDER_NO@]", "受注番号", "000000"),
  
  /** 受注日時 */
  ORDER_DATETIME("[@ORDER_DATETIME@]", "受注日時", "2007/01/01 10:58:50"),
  
  /** 姓 */
  LAST_NAME("[@NAME@]", "姓", "山田"),
  
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
  
  /** 電話番号 */
  PHONE_NUMBER("[@PHONE_NUMBER@]", "電話番号", "000-000-0000"),
  
  /** 手机号码 */
  MOBILE_NUMBER("[@MOBILE_NUMBER@]","手机号码","0000000000"),
  
  /** 商品合計金額 */
  COMMODITY_TOTAL_PRICE("[@COMMODITY_TOTAL_PRICE@]", "商品合計金額", "1,000円"),
  
  /** 送料合計金額 */
  SHIPPING_CHARGE_TOTAL_PRICE("[@SHIPPING_CHARGE_TOTAL_PRICE@]", "送料合計金額", "100円"),
  
  /** ギフト合計金額 */
  GIFT_TOTAL_PRICE("[@GIFT_TOTAL_PRICE@]", "ギフト合計金額", "500円"),
  
  /** 手数料金額 */
  COMMISSION_PRICE("[@COMMISSION_PRICE@]", "手数料金額", "500円"),
  
  /** お支払い合計金額 */
  PAYMENT_TOTAL_PRICE("[@PAYMENT_TOTAL_PRICE@]", "お支払い合計金額", "***** ポイント使用時、ポイント情報が表示されます *****\r\nお支払い合計金額：1,300円", false),
  
  /** 連絡事項 */
  MESSAGE("[@MESSAGE@]", "連絡事項", "できれば急ぎでお願いします。");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private OrderHeaderTag(String value, String name, String dummyData) {
    init(value, name, dummyData, false);
  }

  private OrderHeaderTag(String value, String name, String dummyData, boolean initRequired) {
    init(value, name, dummyData, initRequired);
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
    return ORDER_HEADER;
  }

  public boolean isRequired() {
    return required;
  }

}
