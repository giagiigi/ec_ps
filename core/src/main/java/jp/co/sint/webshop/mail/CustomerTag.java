package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum CustomerTag implements MailTemplateTag {

  /** メールアドレス */
  // 10.1.1 10003 修正 ここから
  // EMAIL("[@EMAIL@]", "メールアドレス", "test@example.com"),
  CUSTOMER_EMAIL("[@CUSTOMER_EMAIL@]", "メールアドレス", "test@example.com"),
  // 10.1.1 10003 修正 ここまで
  /** ポイント残高 */
  REST_POINT("[@REST_POINT@]", "ポイント残高", "***** ポイント残高に関する文章が表示されます *****");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private CustomerTag(String value, String name, String dummyData) {
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
    return CUSTOMER;
  }

  public boolean isRequired() {
    return required;
  }

}
