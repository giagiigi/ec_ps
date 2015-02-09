//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.LanguageType;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「宣传品活动规则表(propaganda_activity_rule)」テーブルの1行分のレコードを表すDTO(Data Transfer
 * Object)です。
 * 
 * @author System Integrator Corp.
 */
public class PropagandaActivityRule implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 活动编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "活动编号", order = 1)
  private String activityCode;

  /** 活动名称 */
  @Required
  @Length(100)
  @Metadata(name = "活动名称", order = 2)
  private String activityName;

  /** 订单区分 */
  @Required
  @Length(1)
  @Domain(OrderType.class)
  @Metadata(name = "订单区分", order = 3)
  private Long orderType;

  /** 语言区分 */
  @Required
  @Length(1)
  @Domain(LanguageType.class)
  @Metadata(name = "语言区分", order = 4)
  private Long languageCode;

  /** 活动开始时间 */
  @Required
  @Metadata(name = "活动开始时间", order = 5)
  private Date activityStartDatetime;

  /** 活动结束时间 */
  @Required
  @Metadata(name = "活动结束时间", order = 6)
  private Date activityEndDatetime;

  /** 配送区域 */
  @Metadata(name = "配送区域", order = 7)
  private String deliveryArea;

  /** 行ID */
  @Required
  @Length(38)
  @Metadata(name = "行ID", order = 8)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 新建用户 */
  @Required
  @Length(100)
  @Metadata(name = "新建用户", order = 9)
  private String createdUser;

  /** 新建时间 */
  @Required
  @Metadata(name = "新建时间", order = 10)
  private Date createdDatetime;

  /** 更新用户 */
  @Required
  @Length(100)
  @Metadata(name = "更新用户", order = 11)
  private String updatedUser;

  /** 更新时间 */
  @Required
  @Metadata(name = "更新时间", order = 12)
  private Date updatedDatetime;

  /**
   * データ行IDを取得します
   * 
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   * 
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   * 
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   * 
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   * 
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * @return the activityCode
   */
  public String getActivityCode() {
    return activityCode;
  }

  /**
   * @return the activityName
   */
  public String getActivityName() {
    return activityName;
  }

  /**
   * @return the orderType
   */
  public Long getOrderType() {
    return orderType;
  }

  /**
   * @return the languageCode
   */
  public Long getLanguageCode() {
    return languageCode;
  }

  /**
   * @return the activityStartDatetime
   */
  public Date getActivityStartDatetime() {
    return DateUtil.immutableCopy(activityStartDatetime);
  }

  /**
   * @return the activityEndDatetime
   */
  public Date getActivityEndDatetime() {
    return DateUtil.immutableCopy(activityEndDatetime);
  }

  /**
   * @return the deliveryArea
   */
  public String getDeliveryArea() {
    return deliveryArea;
  }

  /**
   * @param activityCode
   *          the activityCode to set
   */
  public void setActivityCode(String activityCode) {
    this.activityCode = activityCode;
  }

  /**
   * @param activityName
   *          the activityName to set
   */
  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  /**
   * @param orderType
   *          the orderType to set
   */
  public void setOrderType(Long orderType) {
    this.orderType = orderType;
  }

  /**
   * @param languageCode
   *          the languageCode to set
   */
  public void setLanguageCode(Long languageCode) {
    this.languageCode = languageCode;
  }

  /**
   * @param activityStartDatetime
   *          the activityStartDatetime to set
   */
  public void setActivityStartDatetime(Date activityStartDatetime) {
    this.activityStartDatetime = DateUtil.immutableCopy(activityStartDatetime);
  }

  /**
   * @param activityEndDatetime
   *          the activityEndDatetime to set
   */
  public void setActivityEndDatetime(Date activityEndDatetime) {
    this.activityEndDatetime = DateUtil.immutableCopy(activityEndDatetime);
  }

  /**
   * @param deliveryArea
   *          the deliveryArea to set
   */
  public void setDeliveryArea(String deliveryArea) {
    this.deliveryArea = deliveryArea;
  }

}
