package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum TmallShippingTag implements MailTemplateTag {

  /** 受注番号 */
  ORDER_NO("[@ORDER_NO@]", "受注番号", "000000"),
  /** 受注日時 */
  ORDER_DATETIME("[@ORDER_DATETIME@]", "受注日時", "2007/01/01 10:58:50"),
  /** 姓 */
  LAST_NAME("[@NAME@]", "姓", "山田"),
  //modify by V10-CH 170 start  
//  /** 名 */
//  FIRST_NAME("[@FIRST_NAME@]", "名", "太郎"),
//  /** 姓カナ */
//  LAST_NAME_KANA("[@LAST_NAME_KANA@]", "姓カナ", "ヤマダ"),
//  /** 名カナ */
//  FIRST_NAME_KANA("[@FIRST_NAME_KANA@]", "名カナ", "タロウ"),
  //modify by V10-CH 170 end  
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
  /** 住所3 */
  ADDRESS4("[@ADDRESS4@]", "住所4", "详细地址"),
  //modify by V10-CH 170 start  
//  /** 住所4 */
//  ADDRESS4("[@ADDRESS4@]", "住所4", "×××マンション"),
  //modify by V10-CH 170 end  
  /** 電話番号 */
  PHONE_NUMBER("[@PHONE_NUMBER@]", "電話番号", "000-000-0000"),
  //Add by V10-CH start
  MOBILE_NUMBER("[@MOBILE_NUMBER@]", "手机号码", "00000000000"),
  //Add by V10-CH end
  /** 手机号码 */
  /** 手数料金額 */
  COMMISSION_PRICE("[@COMMISSION_PRICE@]", "手数料金額", "500円"),
  /** お支払い合計金額 */
  PAYMENT_TOTAL_PRICE("[@PAYMENT_TOTAL_PRICE@]", "お支払い合計金額", "1,300円"),
  /** 連絡事項 */
  MESSAGE("[@MESSAGE@]", "連絡事項", "早めにお召し上がり下さい。"),
  /** 支付方式名称 */
  PAYMENT_METHOD_NAME("[@PAYMENT_METHOD_NAME@]", "支付方式名称", "支付宝Alipay"),
  /** 店铺优惠金额 */
  TMALL_SHOP_PRICE("[@TMALL_SHOP_PRICE@]", "店铺优惠金额", "500.00"),
  /** 商城优惠金额 */
  TMALL_CITY_PRICE("[@TMALL_CITY_PRICE@]", "商城优惠金额", "500.00"),
  /** 商城使用积分 */
  TMALL_USED_POINT("[@TMALL_USED_POINT@]", "商城使用积分", "500.00"),
  /** 优惠金额(优惠券等除满就送以外) */
  TMALL_DISCOUNT_PRICE("[@TMALL_DISCOUNT_PRICE@]", "优惠金额", "500.00"),
  /** 满就送活动优惠金额 */
  TMALL_MJS_DISCOUNT("[@TMALL_MJS_DISCOUNT@]", "满就送优惠金额", "500.00"),
  /** 物流公司 */
  TMALL_DELIVERY_COMPANY_NAME("[@TMALL_DELIVERY_COMPANY_NAME@]", "物流公司", "大众佐川");
  

  

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private TmallShippingTag(String value, String name, String dummyData) {
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
    return SHIPPING;
  }

  public boolean isRequired() {
    return required;
  }

}
