package jp.co.sint.webshop.utility;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

public class Sku implements Serializable, Comparable<Sku> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** SKUコード */
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKUコード", order = 2)
  private String skuCode;

  /** 表示用商品名 */
  private String displayName;
  
  // 2012/11/28 促销对应 ob add start
  /** 商品区分 */
  private Long commodityType;
  // 2012/11/28 促销对应 ob add end
  
  //2012-12-5 促销对应 ob add start
  //促销活动编号
  private String campaignCode;
  //2012-12-5 促销对应 ob add end

  /**
   * 新しいSkuを生成します。
   */
  public Sku() {
    this.shopCode = "";
    this.skuCode = "";
  }

  /**
   * 引数で受け取ったショップコード、SKUコードを設定します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   */
  public Sku(String shopCode, String skuCode) {
    this.shopCode = shopCode;
    this.skuCode = skuCode;
  }

  // 2012/11/28 促销对应 ob add start
  /**
   * 引数で受け取ったショップコード、SKUコードを設定します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   */
  public Sku(String shopCode, String skuCode, Long commodityType) {
    this.shopCode = shopCode;
    this.skuCode = skuCode;
    this.commodityType = commodityType;
  }
  // 2012/11/28 促销对应 ob add end
  
  
  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }
  //2012-12-5 促销对应 ob add start
  /**
   * @return the campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  /**
   * @param campaignCode the campaignCode to set
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }
  //2012-12-5 促销对应 ob add end

  /**
   * SKUコードを取得します
   * 
   * @return SKUコード
   */
  public String getSkuCode() {
    return this.skuCode;
  }

  /**
   * ショップコードを設定します
   * 
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * SKUコードを設定します
   * 
   * @param val
   *          SKUコード
   */
  public void setSkuCode(String val) {
    this.skuCode = val;
  }

  /**
   * displayNameを取得します。
   * 
   * @return displayName
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * displayNameを設定します。
   * 
   * @param displayName
   *          displayName
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * @return the commodityType
   */
  public Long getCommodityType() {
    return commodityType;
  }

  /**
   * @param commodityType the commodityType to set
   */
  public void setCommodityType(Long commodityType) {
    this.commodityType = commodityType;
  }

  public int compareTo(Sku sku) {
    int s1 = this.getShopCode().compareTo(sku.getShopCode());
    int s2 = this.getSkuCode().compareTo(sku.getSkuCode());
    if (s1 != 0) {
      return s1;
    } else {
      return s2;
    }
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Sku)) {
      return false;
    }
    return this.equals((Sku) object);
  }

  public boolean equals(Sku sku) {
    if (!sku.getShopCode().equals(this.getShopCode())) {
      return false;
    }
    if (!sku.getSkuCode().equals(this.getSkuCode())) {
      return false;
    }
    // 10.1.4 10202, K00171 修正 ここから
    // return false;
    return true;
    // 10.1.4 10202, K00171 修正 ここまで
  }

  public int hashCode() {
    int result = this.getShopCode().hashCode();
    result += this.getSkuCode().hashCode();
    return result;
  }
}
