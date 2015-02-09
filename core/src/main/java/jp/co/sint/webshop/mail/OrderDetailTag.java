package jp.co.sint.webshop.mail;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

public enum OrderDetailTag implements MailTemplateTag {

  /** 受注番号 */
  ORDER_NO("[@ORDER_NO@]", "受注番号", "000000"),
  /** ショップコード */
  SHOP_CODE("[@SHOP_CODE@]", "ショップコード", "shop001"),
  /** SKUコード */
  SKU_CODE("[@SKU_CODE@]", "SKUコード", "000001"),
  /** 商品コード */
  COMMODITY_CODE("[@COMMODITY_CODE@]", "商品コード", "000001"),
  /** 商品名称 */
  COMMODITY_NAME("[@COMMODITY_NAME@]", "商品名称", "コート"),
  /** 規格詳細1名称 */
  STANDARD_DETAIL1_NAME("[@STANDARD_DETAIL1_NAME@]", "規格詳細1名称", "L"),
  /** 規格詳細2名称 */
  STANDARD_DETAIL2_NAME("[@STANDARD_DETAIL2_NAME@]", "規格詳細2名称", "青"),
  /** 購入商品数 */
  PURCHASING_AMOUNT("[@PURCHASING_AMOUNT@]", "購入商品数", "1点"),
  /** 商品単価 */
  UNIT_PRICE("[@UNIT_PRICE@]", "商品単価", "1,000円"),
  /** 販売価格 */
  RETAIL_PRICE("[@RETAIL_PRICE@]", "販売価格", "1,000円"),
  //modify by V10-CH 170 start  
//  /** 商品消費税率 */
//  COMMODITY_TAX_RATE("[@COMMODITY_TAX_RATE@]", "商品消費税率", "5%"),
//  /** 商品消費税額 */
//  COMMODITY_TAX_PRICE("[@COMMODITY_TAX_PRICE@]", "商品消費税額", "50円"),
  //modify by V10-CH 170 end  
  /** キャンペーン名称 */
  CAMPAIGN_NAME("[@CAMPAIGN_NAME@]", "キャンペーン名称", "キャンペーン"),
  /** 商品別ポイント付与率 */
  COMMODITY_POINT_RATE("[@COMMODITY_POINT_RATE@]", "商品別ポイント付与率", "5%"),
  /** キャンペーン値引率 */
  CAMPAIGN_DISCOUNT_RATE("[@CAMPAIGN_DISCOUNT_RATE@]", "キャンペーン値引率", "1%");

  private String value;

  private String name;

  private String dummyData;

  private boolean required;

  private OrderDetailTag(String value, String name, String dummyData) {
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
    return ORDER_DETAIL;
  }

  public boolean isRequired() {
    return required;
  }

}
