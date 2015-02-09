package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum PasswordConfirmationTag implements MailTemplateTag {

  /** 更新日時 */
  UPDATE_DATETIME("[@UPDATE_DATETIME@]", "更新日時", "2007/02/10 00:00:00");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private PasswordConfirmationTag(String value, String name, String dummyData) {
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
    return PASSWORD_CONFIRMATION;
  }

  public boolean isRequired() {
    return required;
  }

}
