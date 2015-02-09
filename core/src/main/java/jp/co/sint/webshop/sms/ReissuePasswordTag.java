package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum ReissuePasswordTag implements SmsTemplateTag {

  /** パスワード再登録URL */
  REISSUE_PASSWORD_URL("[@REISSUE_PASSWORD_URL@]", "パスワード再登録URL",
      "http://www.example.com/xxxfront/app/customer/customer_initpassword/init/abcdefghijklmn", true);

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private ReissuePasswordTag(String value, String name, String dummyData, boolean required) {
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
    return REISSUE_PASSWORD;
  }

  public boolean isRequired() {
    return required;
  }

}
