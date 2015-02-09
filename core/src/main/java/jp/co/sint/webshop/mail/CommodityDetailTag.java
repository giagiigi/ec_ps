package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum CommodityDetailTag implements MailTemplateTag {
  /** SKUコード */
  SKU_CODE("[@SKU_CODE@]", "SKUコード", "sku001"),
  /** 販売価格 */
  RETAIL_PRICE("[@RETAIL_PRICE@]", "販売価格", "1,000円"),
  /** JANコード */
  JAN_CODE("[@JAN_CODE@]", "JANコード", "123-4567"),
  /** 規格詳細1名称 */
  STANDARD_DETAIL1_NAME("[@STANDARD_DETAIL1_NAME@]", "規格詳細1名称", "L"),
  /** 規格詳細2名称 */
  STANDARD_DETAIL2_NAME("[@STANDARD_DETAIL2_NAME@]", "規格詳細2名称", "青");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private CommodityDetailTag(String value, String name, String dummyData) {
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
    return CUSTOMER_DETAIL;
  }

  public boolean isRequired() {
    return required;
  }

}
