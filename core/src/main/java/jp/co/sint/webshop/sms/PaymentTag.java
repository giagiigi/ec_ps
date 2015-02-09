package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum PaymentTag implements SmsTemplateTag {

  /** 支払方法名称 */
  PAYMENT_METHOD_NAME("[@PAYMENT_METHOD_NAME@]", "支払方法名称", "銀行振込", false),
  /** 支払手数料 */
  PAYMENT_COMMISSION("[@PAYMENT_COMMISSION@]", "支払手数料", "300円", false),
  //modify by V10-CH 170 start  
//  /** 支払手数料消費税額 */
//  PAYMENT_COMMISSION_TAX_PRICE("[@PAYMENT_COMMISSION_TAX_PRICE@]", "支払手数料消費税額", "15円"),
  //modify by V10-CH 170 end  
  /** 支払明細 */
  PAYMENT_DETAIL("[@PAYMENT_DETAIL@]", "支払明細", "***** 支払方法が表示されます *****");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private PaymentTag(String value, String name, String dummyData, boolean required) {
    init(value, name, dummyData, required);
  }

  private PaymentTag(String value, String name, String dummyData) {
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
    return PAYMENT;
  }

  public boolean isRequired() {
    return required;
  }

}
