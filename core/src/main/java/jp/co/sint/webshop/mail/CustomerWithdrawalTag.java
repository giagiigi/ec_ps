package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum CustomerWithdrawalTag implements MailTemplateTag {

  /** 退会希望日 */
  WITHDRAWAL_REQUEST_DATE("[@WITHDRAWAL_REQUEST_DATE@]", "退会希望日", "2007/05/11", true),
  /** 退会日 */
  WITHDRAWAL_DATE("[@WITHDRAWAL_DATE@]", "退会日", "2007/05/12", true);

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private CustomerWithdrawalTag(String value, String name, String dummyData, boolean required) {
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
    return CUSTOMER;
  }

  public boolean isRequired() {
    return required;
  }

}
