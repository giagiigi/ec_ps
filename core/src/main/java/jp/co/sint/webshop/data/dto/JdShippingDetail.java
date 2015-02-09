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
public class JdShippingDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 发货编号 */
  @PrimaryKey(1)
  @Required 
  @Length(16)
  @Metadata(name = "发货编号", order = 1)
  private String shippingNo; 
  
  /** 发货明細番号 */
  @PrimaryKey(2)
  @Required 
  @Length(16)
  @Metadata(name = "发货明細番号", order = 2)
  private Long shippingDetailNo; 
  
  /** 店铺编号 */
  @Required 
  @Length(16)
  @Metadata(name = " 店铺编号", order = 3)
  private String shopCode; 
  
  /** 商品编号 */
  @Required 
  @Length(24)
  @Metadata(name = " 商品编号", order = 4)
  private String skuCode; 
  
  /** 商品单价 */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "商品单价", order = 5)
  private BigDecimal unitPrice; 
  
  /** discount_amount */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "discountPrice", order = 6)
  private BigDecimal discountPrice; 
  
  /** discount_amount */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "discountAmount", order = 7)
  private BigDecimal discountAmount; 
  
  /** 京东价 */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "京东价", order = 8)
  private BigDecimal retailPrice; 
  
  /** retail_tax */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "retailTax", order = 9)
  private BigDecimal retailTax; 
  
  /** 购买商品数量 */
  @Required 
  @Length(8)
  @Metadata(name = "购买商品数量", order = 10)
  private Long purchasingAmount; 
  
  /** gift_code */
  @Length(16)
  @Metadata(name = " giftCode", order = 11)
  private String giftCode; 
  
  /** gift_name */
  @Length(40)
  @Metadata(name = " giftName", order = 12)
  private String giftName; 
  
  /** gift_price */
  @Required 
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "giftPrice", order = 13)
  private BigDecimal giftPrice; 
  
  /** gift_tax_rate */
  @Length(3)
  @Metadata(name = "giftTaxRate", order = 14)
  private Long giftTaxRate; 
  
  /** gift_tax */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "giftTax", order = 15)
  private BigDecimal giftTax; 
  
  /** gift_tax_type */
  @Required
  @Length(1)
  @Metadata(name = "giftTaxType", order = 16)
  private Long giftTaxType; 
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 17)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 18)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 19)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 20)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 21)
  private Date updatedDatetime;

  
  /**
   * @return the shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }

  
  /**
   * @return the shippingDetailNo
   */
  public Long getShippingDetailNo() {
    return shippingDetailNo;
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
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  
  /**
   * @return the discountPrice
   */
  public BigDecimal getDiscountPrice() {
    return discountPrice;
  }

  
  /**
   * @return the discountAmount
   */
  public BigDecimal getDiscountAmount() {
    return discountAmount;
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
   * @return the purchasingAmount
   */
  public Long getPurchasingAmount() {
    return purchasingAmount;
  }

  
  /**
   * @return the giftCode
   */
  public String getGiftCode() {
    return giftCode;
  }

  
  /**
   * @return the giftName
   */
  public String getGiftName() {
    return giftName;
  }

  
  /**
   * @return the giftPrice
   */
  public BigDecimal getGiftPrice() {
    return giftPrice;
  }

  
  /**
   * @return the giftTaxRate
   */
  public Long getGiftTaxRate() {
    return giftTaxRate;
  }

  
  /**
   * @return the giftTax
   */
  public BigDecimal getGiftTax() {
    return giftTax;
  }

  
  /**
   * @return the giftTaxType
   */
  public Long getGiftTaxType() {
    return giftTaxType;
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
   * @param shippingNo the shippingNo to set
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }

  
  /**
   * @param shippingDetailNo the shippingDetailNo to set
   */
  public void setShippingDetailNo(Long shippingDetailNo) {
    this.shippingDetailNo = shippingDetailNo;
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
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  
  /**
   * @param discountPrice the discountPrice to set
   */
  public void setDiscountPrice(BigDecimal discountPrice) {
    this.discountPrice = discountPrice;
  }

  
  /**
   * @param discountAmount the discountAmount to set
   */
  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
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
   * @param purchasingAmount the purchasingAmount to set
   */
  public void setPurchasingAmount(Long purchasingAmount) {
    this.purchasingAmount = purchasingAmount;
  }

  
  /**
   * @param giftCode the giftCode to set
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  
  /**
   * @param giftName the giftName to set
   */
  public void setGiftName(String giftName) {
    this.giftName = giftName;
  }

  
  /**
   * @param giftPrice the giftPrice to set
   */
  public void setGiftPrice(BigDecimal giftPrice) {
    this.giftPrice = giftPrice;
  }

  
  /**
   * @param giftTaxRate the giftTaxRate to set
   */
  public void setGiftTaxRate(Long giftTaxRate) {
    this.giftTaxRate = giftTaxRate;
  }

  
  /**
   * @param giftTax the giftTax to set
   */
  public void setGiftTax(BigDecimal giftTax) {
    this.giftTax = giftTax;
  }

  
  /**
   * @param giftTaxType the giftTaxType to set
   */
  public void setGiftTaxType(Long giftTaxType) {
    this.giftTaxType = giftTaxType;
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

}
