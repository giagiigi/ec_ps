package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum PointExpiredTag implements MailTemplateTag {
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
  /** ポイント残高 */
  EXPIRING_REST_POINT("[@EXPIRING_REST_POINT@]", "ポイント残高", "100pt"),
  /** ポイント失効日 */
  POINT_EXPIRED_DATE("[@POINT_EXPIRED_DATE@]", "ポイント失効日", "2010/01/01");

  private PointExpiredTag(String value, String name, String dummyData) {
    init(value, name, dummyData, false);
  }

  private void init(String initValue, String initName, String initDummyData, boolean initRequired) {
    this.value = initValue;
    this.name = initName;
    this.dummyData = initDummyData;
    this.required = initRequired;
  }

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

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
    return POINT_EXPIRED;
  }

  public boolean isRequired() {
    return required;
  }

}
