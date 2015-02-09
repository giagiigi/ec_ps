package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum BirthDayTag implements MailTemplateTag {
  /** 姓 */
  LAST_NAME("[@NAME@]", "姓", "山田"),
  //modify by V10-CH 170 start  
//  /** 名 */
//  FIRST_NAME("[@FIRST_NAME@]", "名", "太郎"),
//  /** 姓カナ */
//  LAST_NAME_KANA("[@LAST_NAME_KANA@]", "姓カナ", "ヤマダ"),
//  /** 名カナ */
//  FIRST_NAME_KANA("[@FIRST_NAME_KANA@]", "名カナ", "タロウ"),
  //modify by V10-CH 170 start  
  /** 生年月日 */
  BIRTH_DATE("[@BIRTH_DATE@]", "生年月日", "1980/10/11"),
 
  /** 优惠劵名称 */
  COUPON_NAME("[@COUPON_NAME@]", "优惠劵名称" , "生日优惠劵"),

  /** 优惠劵有效期（开始） */
  COUPON_START_DATE("[@COUPON_START_DATE@]", "优惠劵有效期（开始）" , "2013/01/01"),
  
  /** 优惠劵有效期（结束） */
  COUPON_END_DATE("[@COUPON_END_DATE@]", "优惠劵有效期（结束）" , "2014/01/01");
  
  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private BirthDayTag(String value, String name, String dummyData) {
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
    return BIRTHDAY;
  }

  public boolean isRequired() {
    return required;
  }

}
