package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum CouponEndTag implements MailTemplateTag {
  /** 姓名 */
  LAST_NAME("[@NAME@]", "姓", "山田"),
  
  //2013/04/15 优惠券对应 ob add start
  /** 优惠劵名称 */
  COUPON_NAME("[@COUPON_NAME@]", "优惠劵名称", "满200-50"),
  
  //2013/04/15 优惠券对应 ob add end
  
  /** 优惠劵编号 */
  COUPON_CODE("[@COUPON_CODE@]", "优惠劵编号", "100000001"),
  
  /** 发行理由 */
  REASON("[@REASON@]", "发行理由", "理由一"),
  
  /** 发型金额 */
  AMOUNT("[@AMOUNT@]", "发型金额", "100"),
  
  /** 最小购买金额 */
  MIN_AMOUNT("[@MIN_AMOUNT@]", "最小购买金额", "300"),
  
  /** 利用开始日期 */
  USE_START_DATE("[@USE_START_DATE@]", "利用开始日期", "2011/12/01 00:00:00"),
  
  /** 利用结束日期 */
  USE_END_DATE("[@USE_END_DATE@]", "利用结束日期", "2011/12/31 12:59:59");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private CouponEndTag(String value, String name, String dummyData) {
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
    return COUPON_END;
  }

  public boolean isRequired() {
    return required;
  }

}
