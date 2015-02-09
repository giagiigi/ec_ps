package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「免邮促销(FreePostageRule)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */

public class FreePostageRule implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 免邮促销编号 */
  @Required
  @Length(16)
  @Metadata(name = "免邮促销编号", order = 1)
  private String freePostageCode;

  /** 免邮促销名称 */
  @Required
  @Length(50)
  @Metadata(name = "免邮促销名称", order = 2)
  private String freePostageName;

  /** 免邮开始日期 */
  @Required
  @Datetime
  @Metadata(name = "免邮开始日期", order = 3)
  private Date freeStartDate;

  /** 免邮结束日期 */
  @Required
  @Datetime
  @Metadata(name = "免邮结束日期", order = 4)
  private Date freeEndDate;

  /** 关联对象使用类型 */
  @Digit
  @Length(1)
  @Metadata(name = "关联对象使用类型", order = 5)
  private Long useType;

  /** 适用对象 */
  @Required
  @Digit
  @Length(1)
  @Metadata(name = "适用对象", order = 6)
  private Long applicableObject;

  /** 重量限定 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "重量限定", order = 7)
  private BigDecimal weightLimit;

  /** 最小购买金额 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "最小购买金额", order = 8)
  private BigDecimal minUseOrderAmount;

  /** 适用区域 */
  @Length(2000)
  @Metadata(name = "适用区域", order = 9)
  private String applicableArea;

  /** 促销商品 */
  @Metadata(name = "促销商品", order = 10)
  private String objectCommodity;

  /** 促销品牌 */
  @Metadata(name = "促销品牌", order = 11)
  private String objectBrand;

  /** 促销分类 */
  @Metadata(name = "促销分类", order = 12)
  private String objectCategory;

  /** 免邮发行代码 */
  @Metadata(name = "免邮发行代码", order = 13)
  private String objectIssueCode;

  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 14)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 15)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Datetime
  @Metadata(name = "作成日時", order = 16)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 17)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Datetime
  @Metadata(name = "更新日時", order = 18)
  private Date updatedDatetime;

  /** 目标Url */
  @Metadata(name = "目标Url", order = 19)
  private String targetUrl;

  /**
   * @return the freePostageCode
   */
  public String getFreePostageCode() {
    return freePostageCode;
  }

  /**
   * @return the freePostageName
   */
  public String getFreePostageName() {
    return freePostageName;
  }

  /**
   * @return the freeStartDate
   */
  public Date getFreeStartDate() {
    return DateUtil.immutableCopy(freeStartDate);
  }

  /**
   * @return the freeEndDate
   */
  public Date getFreeEndDate() {
    return DateUtil.immutableCopy(freeEndDate);
  }

  /**
   * @return the useType
   */
  public Long getUseType() {
    return useType;
  }

  /**
   * @return the applicableObject
   */
  public Long getApplicableObject() {
    return applicableObject;
  }

  /**
   * @return the weightLimit
   */
  public BigDecimal getWeightLimit() {
    return weightLimit;
  }

  /**
   * @return the minUseOrderAmount
   */
  public BigDecimal getMinUseOrderAmount() {
    return minUseOrderAmount;
  }

  /**
   * @return the applicableArea
   */
  public String getApplicableArea() {
    return applicableArea;
  }

  /**
   * @return the objectCommodity
   */
  public String getObjectCommodity() {
    return objectCommodity;
  }

  /**
   * @return the objectBrand
   */
  public String getObjectBrand() {
    return objectBrand;
  }

  /**
   * @return the objectCategory
   */
  public String getObjectCategory() {
    return objectCategory;
  }

  /**
   * @return the objectIssueCode
   */
  public String getObjectIssueCode() {
    return objectIssueCode;
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
    return DateUtil.immutableCopy(createdDatetime);
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
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @param freePostageCode
   *          the freePostageCode to set
   */
  public void setFreePostageCode(String freePostageCode) {
    this.freePostageCode = freePostageCode;
  }

  /**
   * @param freePostageName
   *          the freePostageName to set
   */
  public void setFreePostageName(String freePostageName) {
    this.freePostageName = freePostageName;
  }

  /**
   * @param freeStartDate
   *          the freeStartDate to set
   */
  public void setFreeStartDate(Date freeStartDate) {
    this.freeStartDate = DateUtil.immutableCopy(freeStartDate);
  }

  /**
   * @param freeEndDate
   *          the freeEndDate to set
   */
  public void setFreeEndDate(Date freeEndDate) {
    this.freeEndDate = DateUtil.immutableCopy(freeEndDate);
  }

  /**
   * @param useType
   *          the useType to set
   */
  public void setUseType(Long useType) {
    this.useType = useType;
  }

  /**
   * @param applicableObject
   *          the applicableObject to set
   */
  public void setApplicableObject(Long applicableObject) {
    this.applicableObject = applicableObject;
  }

  /**
   * @param weightLimit
   *          the weightLimit to set
   */
  public void setWeightLimit(BigDecimal weightLimit) {
    this.weightLimit = weightLimit;
  }

  /**
   * @param minUseOrderAmount
   *          the minUseOrderAmount to set
   */
  public void setMinUseOrderAmount(BigDecimal minUseOrderAmount) {
    this.minUseOrderAmount = minUseOrderAmount;
  }

  /**
   * @param applicableArea
   *          the applicableArea to set
   */
  public void setApplicableArea(String applicableArea) {
    this.applicableArea = applicableArea;
  }

  /**
   * @param objectCommodity
   *          the objectCommodity to set
   */
  public void setObjectCommodity(String objectCommodity) {
    this.objectCommodity = objectCommodity;
  }

  /**
   * @param objectBrand
   *          the objectBrand to set
   */
  public void setObjectBrand(String objectBrand) {
    this.objectBrand = objectBrand;
  }

  /**
   * @param objectCategory
   *          the objectCategory to set
   */
  public void setObjectCategory(String objectCategory) {
    this.objectCategory = objectCategory;
  }

  /**
   * @param objectIssueCode
   *          the objectIssueCode to set
   */
  public void setObjectIssueCode(String objectIssueCode) {
    this.objectIssueCode = objectIssueCode;
  }

  /**
   * @param ormRowid
   *          the ormRowid to set
   */
  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  /**
   * @param createdUser
   *          the createdUser to set
   */
  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  /**
   * @param createdDatetime
   *          the createdDatetime to set
   */
  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = DateUtil.immutableCopy(createdDatetime);
  }

  /**
   * @param updatedUser
   *          the updatedUser to set
   */
  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  /**
   * @param updatedDatetime
   *          the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return the targetUrl
   */
  public String getTargetUrl() {
    return targetUrl;
  }

  /**
   * @param targetUrl
   *          the targetUrl to set
   */
  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

}
