package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum ShippingHeaderTag implements SmsTemplateTag {
  /**
   * shippingHeader
   */
  /** 宛名：姓 */
  ADDRESS_LAST_NAME("[@ADDRESS_LAST_NAME@]", "宛名：姓", "山田", false),
  /** 郵便番号 */
  POSTAL_CODE("[@POSTAL_CODE@]", "郵便番号", "100002", false),
  /** 住所1 */
  ADDRESS1("[@ADDRESS1@]", "住所1", "東京都", false),
  /** 住所2 */
  ADDRESS2("[@ADDRESS2@]", "住所2", "千代田区", false),
  /** 住所3 */
  ADDRESS3("[@ADDRESS3@]", "住所3", "千代田", false),
  /** 電話番号 */
  PHONE_NUMBER("[@PHONE_NUMBER@]", "電話番号", "000-000-0000"),
  /** 手机号码 */
  MOBILE_NUMBER("[@MOBILE_NUMBER@]","手机号码","00000000000"),
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
  SHIPPING_SUM_PRICE("[@SHIPPING_SUM_PRICE@]", "配送先小計", "1,200円");

  
  
  
  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private ShippingHeaderTag(String value, String name, String dummyData, boolean required) {
    init(value, name, dummyData, required);
  }

  private ShippingHeaderTag(String value, String name, String dummyData) {
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
    return SHIPPING_HEADER;
  }

  public boolean isRequired() {
    return required;
  }

}
