package jp.co.sint.webshop.data.dto;

/**
 * 优惠券规则_发行关联信息表
 * 
 * @author ob.
 * @version 1.0.0
 */
import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * AbstractNewCouponRule entity provides the base persistence definition of the
 * NewCouponRule
 * 
 * @author OB
 */

public class NewCouponRuleLssueInfo implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 优惠券规则编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "优惠券规则编号", order = 1)
  private String couponCode;

  /** 商品编号 */
  @Length(16)
  @Metadata(name = "商品编号", order = 2)
  private String commodityCode;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 3)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 4)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 5)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 6)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 7)
  private Date updatedDatetime;

  /** 品牌编号 */
  @Length(16)
  @Metadata(name = "品牌编号", order = 8)
  private String brandCode;

  /** 分类编号 */
  @Length(16)
  @Metadata(name = "分类编号", order = 9)
  private String categoryCode;

  /** 明细编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "明细编号", order = 10)
  private Long couponUseNo;

  /**
   * @return 优惠券规则编号
   */
  public String getCouponCode() {
    return couponCode;
  }

  /**
   * @param CouponCode
   *          设置优惠券规则编号
   */
  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  /**
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @param OrmRowid
   *          设置データ行ID
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param CreatedUser
   *          设置作成ユーザ
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param CreatedDatetime
   *          设置作成日時
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param UpdatedUser
   *          设置更新ユーザ
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param UpdatedDatetime
   *          设置更新日時
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @return the couponUseNo
   */
  public Long getCouponUseNo() {
    return couponUseNo;
  }

  /**
   * @param brandCode
   *          the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * @param couponUseNo
   *          the couponUseNo to set
   */
  public void setCouponUseNo(Long couponUseNo) {
    this.couponUseNo = couponUseNo;
  }

}
