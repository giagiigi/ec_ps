package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum ArrivalTag implements SmsTemplateTag {
  /** 到着予定日 */
  ARRIVAL_DATE("[@ARRIVAL_DATE@]", "到着予定日", "2007/01/15"),
  /** 到着時間 */
  ARRIVAL_TIME("[@ARRIVAL_TIME@]", "到着時間", "12時～15時(未入力の場合、「指定なし」)");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private ArrivalTag(String value, String name, String dummyData) {
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
