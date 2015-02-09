package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum ShippingHeaderTag implements MailTemplateTag {
  /** 宛名：姓 */
  ADDRESS_LAST_NAME("[@ADDRESS_LAST_NAME@]", "宛名：姓", "山田", true),
  //modify by V10-CH 170 start
//  /** 宛名：名 */
//  ADDRESS_FIRST_NAME("[@ADDRESS_FIRST_NAME@]", "宛名：名", "太郎", true),
  //  /** 宛名姓カナ */
//  ADDRESS_LAST_NAME_KANA("[@ADDRESS_LAST_NAME_KANA@]", "宛名姓カナ", "ヤマダ"),
//  /** 宛名名カナ */
//  ADDRESS_FIRST_NAME_KANA("[@ADDRESS_FIRST_NAME_KANA@]", "宛名名カナ", "タロウ"),
//  /** 郵便番号 */
  //modify by V10-CH 170 end  
  POSTAL_CODE("[@POSTAL_CODE@]", "郵便番号", "100002", true),
  /** 住所1 */
  ADDRESS1("[@ADDRESS1@]", "住所1", "東京都", true),
  /** 住所2 */
  ADDRESS2("[@ADDRESS2@]", "住所2", "千代田区", true),
  /** 住所3 */
  ADDRESS3("[@ADDRESS3@]", "住所3", "千代田", true),
  //modify by V10-CH 170 start
//  /** 住所4 */
//  ADDRESS4("[@ADDRESS4@]", "住所4", "×××マンション"),
  //modify by V10-CH 170 end
  /** 電話番号 */
  PHONE_NUMBER("[@PHONE_NUMBER@]", "電話番号", "000-000-0000"),
  /** 手机号码 */
  MOBILE_NUMBER("[@MOBILE_NUMBER@]","手机号码","00000000000"),
  /** 送料 */
  //upd by lc 2012-04-12
  SHIPPING_CHARGE("[@SHIPPING_CHARGE@]", "送料", "100円"),
  //SHIPPING_CHARGE("[@SHIPPING_CHARGE@]", "送料", "100円", true),
  /** 配送指定日 */
  DELIVERY_APPOINTED_DATE("[@DELIVERY_APPOINTED_DATE@]", "配送指定日", "2007/01/15"),
  /** 配送指定時間 */
  DELIVERY_APPOINTED_TIME("[@DELIVERY_APPOINTED_TIME@]", "配送指定時間", "12時～15時(未入力の場合、「指定なし」)"),
  /** 配送種別名称 */
  DELIVERY_TYPE_NAME("[@DELIVERY_TYPE_NAME@]", "配送種別名称", "通常便"),
  // 10.1.3 10137 追加 ここから
  /** 宅配便伝票番号 */
  DELIVERY_SLIP_NO("[@DELIVERY_SLIP_NO@]", "宅配便伝票番号", "0123456789"),
  // 10.1.3 10137 追加 ここまで
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
