package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum GroupChangeTag implements MailTemplateTag {
  /** 姓 */
  LAST_NAME("[@NAME@]", "姓", "山田"),
  /** グループ名 */
  GROUP_NAME("[@GROUP_NAME@]", "グループ名", "山田"),
  /** 月 */
  MONTH("[@MONTH@]", "月", "4"),
  /** 升级/降级 */
  GROUP_CHANGE_TYPE("[@GROUP_CHANGE_TYPE@]", "升级/降级", "升级"),
  /** 生年月日 */
  GROUP_RANGE("[@GROUP_RANGE@]", "升级差额", "100");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private GroupChangeTag(String value, String name, String dummyData) {
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
    return GROUP_CHANGE;
  }

  public boolean isRequired() {
    return required;
  }

}
