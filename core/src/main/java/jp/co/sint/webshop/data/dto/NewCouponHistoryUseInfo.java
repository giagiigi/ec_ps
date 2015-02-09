package jp.co.sint.webshop.data.dto;

/**
 * 优惠券发行履历
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
 * 优惠券发行履历
 * 
 * @author OB
 */

public class NewCouponHistoryUseInfo implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 优惠券编号 */
  @PrimaryKey(1)
  @Required
  @Length(20)
  @Metadata(name = "优惠券编号", order = 1)
  private String couponIssueNo;

  /** 明细编号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "明细编号", order = 2)
  private Long couponUseNo;

  /** 商品编号 */
  @Length(16)
  @Metadata(name = "商品编号", order = 3)
  private String commodityCode;

  /** 品牌编号 */
  @Length(16)
  @Metadata(name = "品牌编号", order = 4)
  private String brandCode;

  /** 商品区分品（进口品...） */
  @Length(1)
  @Metadata(name = "商品区分品", order = 5)
  private Long importCommodityType;

  /** データ行ID */
  @Required
  @Digit
  @Metadata(name = "データ行ID", order = 6)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 7)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 8)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 9)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 10)
  private Date updatedDatetime;

  /** 分类编号 */
  @Length(16)
  @Metadata(name = "分类编号", order = 11)
  private String categoryCode;

  /**
   * @return the couponIssueNo
   */
  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  /**
   * @param couponIssueNo
   *          the couponIssueNo to set
   */
  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  /**
   * @return the couponUseNo
   */
  public Long getCouponUseNo() {
    return couponUseNo;
  }

  /**
   * @param couponUseNo
   *          the couponUseNo to set
   */
  public void setCouponUseNo(Long couponUseNo) {
    this.couponUseNo = couponUseNo;
  }

  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  /**
   * @param brandCode
   *          the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  /**
   * @return the importCommodityType
   */
  public Long getImportCommodityType() {
    return importCommodityType;
  }

  /**
   * @param importCommodityType
   *          the importCommodityType to set
   */
  public void setImportCommodityType(Long importCommodityType) {
    this.importCommodityType = importCommodityType;
  }

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @return the createdUser
   */
  public String getCreatedUser() {
    return createdUser;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @return the createdDatetime
   */
  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  /**
   * @return the updatedUser
   */
  public String getUpdatedUser() {
    return updatedUser;
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @return the updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * @param categoryCode
   *          the categoryCode to set
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

}
