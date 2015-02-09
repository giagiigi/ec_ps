package jp.co.sint.webshop.sms;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum CommodityHeaderTag implements SmsTemplateTag {

  /** 商品コード */
  COMMODITY_CODE("[@COMMODITY_CODE@]", "商品コード", "commodity001"),
  /** 商品名称 */
  COMMODITY_NAME("[@COMMODITY_NAME@]", "商品名称", "商品名称"),
  /** PC商品説明 */
  COMMODITY_DESCRIPTION_PC("[@COMMODITY_DESCRIPTION_PC@]", "PC商品説明", "PC商品説明"),
  /** 携帯商品説明 */
  COMMODITY_DESCRIPTION_MOBILE("[@COMMODITY_DESCRIPTION_MOBILE@]", "携帯商品説明", "携帯商品説明"),
  /** リンクURL */
  LINK_URL("[@LINK_URL@]", "リンクURL", "http://www.example.com"),
  /** 規格名称1 */
  COMMODITY_STANDARD1_NAME("[@COMMODITY_STANDARD1_NAME@]", "規格名称1", "サイズ"),
  /** 規格名称2 */
  COMMODITY_STANDARD2_NAME("[@COMMODITY_STANDARD2_NAME@]", "規格名称2", "色");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private CommodityHeaderTag(String value, String name, String dummyData) {
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
    return CUSTOMER_HEADER;
  }

  public boolean isRequired() {
    return required;
  }

}
