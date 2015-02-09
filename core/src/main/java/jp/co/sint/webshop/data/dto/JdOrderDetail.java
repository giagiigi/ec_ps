//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto; 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date; 
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity; 
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata; 
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/** 
 * 「タグ(JdCouponDetail)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class JdOrderDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 订单编号 */
  @PrimaryKey(1)
  @Required 
  @Length(16)
  @Metadata(name = "订单编号", order = 1)
  private String orderNo; 
  
  /** 店铺编号 */
  @PrimaryKey(2)
  @Required 
  @Length(16)
  @Metadata(name = "店铺编号", order = 2)
  private String shopCode; 
  
  /** 商品编号 */
  @PrimaryKey(3)
  @Required 
  @Length(24)
  @Metadata(name = "商品编号", order = 3)
  private String skuCode; 
  
  /** 商品编号 */
  @Required 
  @Length(16)
  @Metadata(name = "商品编号", order = 4)
  private String commodityCode; 
  
  /** 商品名称 */
  @Required 
  @Length(50)
  @Metadata(name = "商品名称", order = 5)
  private String commodityName; 
  
  /** 規格詳細1名称 */
  @Length(20)
  @Metadata(name = "規格詳細1名称", order = 6)
  private String standardDetail1Name; 
  
  /** 規格詳細2名称 */
  @Length(20)
  @Metadata(name = "規格詳細2名称", order = 7)
  private String standardDetail2Name; 
  
  /** 购买商品数 */
  @Required 
  @Length(8)
  @Metadata(name = "购买商品数", order = 8)
  private Long purchasingAmount; 
  
  /** 商品单价 */
  @Required 
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "商品单价", order = 9)
  private BigDecimal unitPrice; 
  
  /** 实际销售价格（京东价） */
  @Required 
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "实际销售价格（京东价）", order = 10)
  private BigDecimal retailPrice; 
  
  /** 販売時消費税額 */
  @Required 
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "販売時消費税額", order = 11)
  private BigDecimal retailTax; 
  
  /** commodity_tax_rate */
  @Length(3)
  @Metadata(name = "commodityTaxRate", order = 12)
  private Long commodityTaxRate; 
  
  /** commodity_tax */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "commodityTax", order = 13)
  private BigDecimal commodityTax; 
  
  /** commodity_tax_type */
  @Required
  @Length(1)
  @Metadata(name = "commodityTaxType", order = 14)
  private Long commodityTaxType; 
  
  /** campaign_code */
  @Length(16)
  @Metadata(name = "campaignCode", order = 15)
  private String campaignCode; 
  
  /** campaign_name */
  @Length(50)
  @Metadata(name = "campaignName", order = 16)
  private String campaignName; 
  
  /** campaign_discount_rate */
  @Length(3)
  @Metadata(name = "campaignDiscountRate", order = 17)
  private Long campaignDiscountRate; 
  
  /** applied_point_rate */
  @Required
  @Length(8)
  @Metadata(name = "appliedPointRate", order = 18)
  private Long appliedPointRate; 
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 19)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 20)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 21)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 22)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 23)
  private Date updatedDatetime;
  
  /** sale_plan_code */
  @Length(16)
  @Metadata(name = "salePlanCode", order = 24)
  private String salePlanCode; 
  
  /** sale_plan_name */
  @Length(50)
  @Metadata(name = "salePlanName", order = 25)
  private String salePlanName; 
  
  /** featured_plan_code */
  @Length(16)
  @Metadata(name = "featuredPlanCode", order = 26)
  private String featuredPlanCode; 
  
  /** featured_plan_name */
  @Length(50)
  @Metadata(name = "featuredPlanName", order = 27)
  private String featuredPlanName; 
  
  /** 品牌编号 */
  @Length(16)
  @Metadata(name = "品牌编号", order = 28)
  private String brandCode; 
  
  /** 品牌名称 */
  @Length(50)
  @Metadata(name = "品牌名称", order = 29)
  private String brandName; 
  
  /** jd_sku_code */
  @Length(24)
  @Metadata(name = "jdSkuCode", order = 30)
  private String jdSkuCode; 
  
  /** 京东上商品的标识编号 */
  @Length(16)
  @Metadata(name = "京东上商品的标识编号", order = 31)
  private String jdCommodityCode;

  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  
  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  
  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  
  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  
  /**
   * @return the commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  
  /**
   * @return the standardDetail1Name
   */
  public String getStandardDetail1Name() {
    return standardDetail1Name;
  }

  
  /**
   * @return the standardDetail2Name
   */
  public String getStandardDetail2Name() {
    return standardDetail2Name;
  }

  
  /**
   * @return the purchasingAmount
   */
  public Long getPurchasingAmount() {
    return purchasingAmount;
  }

  
  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  
  /**
   * @return the retailPrice
   */
  public BigDecimal getRetailPrice() {
    return retailPrice;
  }

  
  /**
   * @return the retailTax
   */
  public BigDecimal getRetailTax() {
    return retailTax;
  }

  
  /**
   * @return the commodityTaxRate
   */
  public Long getCommodityTaxRate() {
    return commodityTaxRate;
  }

  
  /**
   * @return the commodityTax
   */
  public BigDecimal getCommodityTax() {
    return commodityTax;
  }

  
  /**
   * @return the commodityTaxType
   */
  public Long getCommodityTaxType() {
    return commodityTaxType;
  }

  
  /**
   * @return the campaignCode
   */
  public String getCampaignCode() {
    return campaignCode;
  }

  
  /**
   * @return the campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  
  /**
   * @return the campaignDiscountRate
   */
  public Long getCampaignDiscountRate() {
    return campaignDiscountRate;
  }

  
  /**
   * @return the appliedPointRate
   */
  public Long getAppliedPointRate() {
    return appliedPointRate;
  }

  
  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  
  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  
  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  
  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  
  /**
   * @return the salePlanCode
   */
  public String getSalePlanCode() {
    return salePlanCode;
  }

  
  /**
   * @return the salePlanName
   */
  public String getSalePlanName() {
    return salePlanName;
  }

  
  /**
   * @return the featuredPlanCode
   */
  public String getFeaturedPlanCode() {
    return featuredPlanCode;
  }

  
  /**
   * @return the featuredPlanName
   */
  public String getFeaturedPlanName() {
    return featuredPlanName;
  }

  
  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  
  /**
   * @return the brandName
   */
  public String getBrandName() {
    return brandName;
  }

  
  /**
   * @return the jdSkuCode
   */
  public String getJdSkuCode() {
    return jdSkuCode;
  }

  
  /**
   * @return the jdCommodityCode
   */
  public String getJdCommodityCode() {
    return jdCommodityCode;
  }

  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  
  /**
   * @param shopCode the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  
  /**
   * @param skuCode the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  
  /**
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
  /**
   * @param commodityName the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  
  /**
   * @param standardDetail1Name the standardDetail1Name to set
   */
  public void setStandardDetail1Name(String standardDetail1Name) {
    this.standardDetail1Name = standardDetail1Name;
  }

  
  /**
   * @param standardDetail2Name the standardDetail2Name to set
   */
  public void setStandardDetail2Name(String standardDetail2Name) {
    this.standardDetail2Name = standardDetail2Name;
  }

  
  /**
   * @param purchasingAmount the purchasingAmount to set
   */
  public void setPurchasingAmount(Long purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
  }

  
  /**
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  
  /**
   * @param retailPrice the retailPrice to set
   */
  public void setRetailPrice(BigDecimal retailPrice) {
    this.retailPrice = retailPrice;
  }

  
  /**
   * @param retailTax the retailTax to set
   */
  public void setRetailTax(BigDecimal retailTax) {
    this.retailTax = retailTax;
  }

  
  /**
   * @param commodityTaxRate the commodityTaxRate to set
   */
  public void setCommodityTaxRate(Long commodityTaxRate) {
    this.commodityTaxRate = commodityTaxRate;
  }

  
  /**
   * @param commodityTax the commodityTax to set
   */
  public void setCommodityTax(BigDecimal commodityTax) {
    this.commodityTax = commodityTax;
  }

  
  /**
   * @param commodityTaxType the commodityTaxType to set
   */
  public void setCommodityTaxType(Long commodityTaxType) {
    this.commodityTaxType = commodityTaxType;
  }

  
  /**
   * @param campaignCode the campaignCode to set
   */
  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  
  /**
   * @param campaignName the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  
  /**
   * @param campaignDiscountRate the campaignDiscountRate to set
   */
  public void setCampaignDiscountRate(Long campaignDiscountRate) {
    this.campaignDiscountRate = campaignDiscountRate;
  }

  
  /**
   * @param appliedPointRate the appliedPointRate to set
   */
  public void setAppliedPointRate(Long appliedPointRate) {
    this.appliedPointRate = appliedPointRate;
  }

  
  /**
   * @param ormRowid the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  
  /**
   * @param createdUser the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  
  /**
   * @param createdDatetime the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  
  /**
   * @param updatedUser the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  
  /**
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  
  /**
   * @param salePlanCode the salePlanCode to set
   */
  public void setSalePlanCode(String salePlanCode) {
    this.salePlanCode = salePlanCode;
  }

  
  /**
   * @param salePlanName the salePlanName to set
   */
  public void setSalePlanName(String salePlanName) {
    this.salePlanName = salePlanName;
  }

  
  /**
   * @param featuredPlanCode the featuredPlanCode to set
   */
  public void setFeaturedPlanCode(String featuredPlanCode) {
    this.featuredPlanCode = featuredPlanCode;
  }

  
  /**
   * @param featuredPlanName the featuredPlanName to set
   */
  public void setFeaturedPlanName(String featuredPlanName) {
    this.featuredPlanName = featuredPlanName;
  }

  
  /**
   * @param brandCode the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  
  /**
   * @param brandName the brandName to set
   */
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  
  /**
   * @param jdSkuCode the jdSkuCode to set
   */
  public void setJdSkuCode(String jdSkuCode) {
    this.jdSkuCode = jdSkuCode;
  }

  
  /**
   * @param jdCommodityCode the jdCommodityCode to set
   */
  public void setJdCommodityCode(String jdCommodityCode) {
    this.jdCommodityCode = jdCommodityCode;
  }

}
