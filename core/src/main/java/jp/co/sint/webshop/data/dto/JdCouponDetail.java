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
public class JdCouponDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 优惠排序明细编号 */
  @PrimaryKey(2)
  @Required 
  @Length(16)
  @Metadata(name = " 优惠排序明细编号", order = 1)
  private Long couponNo; 
  
  /** 优惠金额 */
  @Precision(precision = 10, scale = 2)
  @Metadata(name = " 优惠金额", order = 2)
  private BigDecimal couponPrice; 
  
  /** 优惠类型 */
  @Length(50)
  @Metadata(name = " 优惠类型", order = 3)
  private String couponType; 
  
  /** 订单编号 */
  @PrimaryKey(1)
  @Required 
  @Length(16)
  @Metadata(name = " 订单编号", order = 4)
  private String orderNo; 
  
  /** 京东sku编号 */
  @Length(24)
  @Metadata(name = " 京东sku编号", order = 5)
  private String skuCode; 
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 6)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 7)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 8)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 9)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 10)
  private Date updatedDatetime;

  
  /**
   * @return the couponNo
   */
  public Long getCouponNo() {
    return couponNo;
  }

  
  /**
   * @return the couponPrice
   */
  public BigDecimal getCouponPrice() {
    return couponPrice;
  }

  
  /**
   * @return the couponType
   */
  public String getCouponType() {
    return couponType;
  }

  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  
  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
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
   * @param couponNo the couponNo to set
   */
  public void setCouponNo(Long couponNo) {
    this.couponNo = couponNo;
  }

  
  /**
   * @param couponPrice the couponPrice to set
   */
  public void setCouponPrice(BigDecimal couponPrice) {
    this.couponPrice = couponPrice;
  }

  
  /**
   * @param couponType the couponType to set
   */
  public void setCouponType(String couponType) {
    this.couponType = couponType;
  }

  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  
  /**
   * @param skuCode the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
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
