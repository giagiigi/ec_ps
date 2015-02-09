package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum TmallShippingDetailTag implements MailTemplateTag {
  /** 商品名称 */
  UNIT_NAME("[@UNIT_NAME@]", "商品名称", "Tシャツ(白/Ｓ)", true),
  /** 販売価格 */
  RETAIL_PRICE("[@RETAIL_PRICE@]", "販売価格", "50円"),
  /** 購入商品数 */
  PURCHASING_AMOUNT("[@PURCHASING_AMOUNT@]", "購入商品数", "10"),
  /** ギフト名称 */
  GIFT_NAME("[@GIFT_NAME@]", "ギフト名称", "お歳暮"),
  /** ギフト価格 */
  GIFT_PRICE("[@GIFT_PRICE@]", "ギフト価格", "100円");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private TmallShippingDetailTag(String value, String name, String dummyData, boolean required) {
    init(value, name, dummyData, required);
  }

  private TmallShippingDetailTag(String value, String name, String dummyData) {
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
    return SHIPPING_DETAIL;
  }

  public boolean isRequired() {
    return required;
  }

}
