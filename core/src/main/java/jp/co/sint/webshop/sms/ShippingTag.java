package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum ShippingTag implements SmsTemplateTag {
/**
 *shipingTag 
 */
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
  MOBILE_NUMBER("[@MOBILE_NUMBER@]", "手机号码", "00000000000"),
  /** 手数料金額 */
  COMMISSION_PRICE("[@COMMISSION_PRICE@]", "手数料金額", "500円"),
  /** お支払い合計金額 */
  PAYMENT_TOTAL_PRICE("[@PAYMENT_TOTAL_PRICE@]", "お支払い合計金額", "1,300円"),
  /** 連絡事項 */
  MESSAGE("[@MESSAGE@]", "連絡事項", "早めにお召し上がり下さい。"),

  /**
   * shippingHeaderTag
   */
  /** 宛名：姓 */
  ADDRESS_LAST_NAME("[@ADDRESS_LAST_NAME@]", "宛名：姓", "山田", false),
  /** 送料 */
  SHIPPING_CHARGE("[@SHIPPING_CHARGE@]", "送料", "100円", false),
  /** 配送指定日 */
  DELIVERY_APPOINTED_DATE("[@DELIVERY_APPOINTED_DATE@]", "配送指定日", "2007/01/15"),
  /** 配送指定時間 */
  DELIVERY_APPOINTED_TIME("[@DELIVERY_APPOINTED_TIME@]", "配送指定時間", "12時～15時(未入力の場合、「指定なし」)"),
  /** 配送種別名称 */
  DELIVERY_TYPE_NAME("[@DELIVERY_TYPE_NAME@]", "配送種別名称", "通常便"),
  /** 宅配便伝票番号 */
  DELIVERY_SLIP_NO("[@DELIVERY_SLIP_NO@]", "宅配便伝票番号", "0123456789"),
  /** 配送先商品小計 */
  SHIPPING_COMMODITY_SUM_PRICE("[@SHIPPING_COMMODITY_SUM_PRICE@]", "配送先商品小計", "2,000円"),
  /** 配送先ギフト小計 */
  SHIPPING_GIFT_SUM_PRICE("[@SHIPPING_GIFT_SUM_PRICE@]", "配送先ギフト小計", "500円"),
  /** 配送先小計 */
  SHIPPING_SUM_PRICE("[@SHIPPING_SUM_PRICE@]", "配送先小計", "1,200円"),
  
  /**
   * shippingDetailTag
   */
  /** 商品名称 */
  UNIT_NAME("[@UNIT_NAME@]", "商品名称", "Tシャツ(白/Ｓ)", false),
  /** 販売価格 */
  RETAIL_PRICE("[@RETAIL_PRICE@]", "販売価格", "50円"),
  /** 購入商品数 */
  PURCHASING_AMOUNT("[@PURCHASING_AMOUNT@]", "購入商品数", "10"),
  /** ギフト名称 */
  GIFT_NAME("[@GIFT_NAME@]", "ギフト名称", "お歳暮"),
  /** ギフト価格 */
  GIFT_PRICE("[@GIFT_PRICE@]", "ギフト価格", "100円");
  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private ShippingTag(String value, String name, String dummyData) {
    init(value, name, dummyData, false);
  }
  private ShippingTag(String value, String name, String dummyData, boolean required) {
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
    return SHIPPING;
  }

  public boolean isRequired() {
    return required;
  }

}
