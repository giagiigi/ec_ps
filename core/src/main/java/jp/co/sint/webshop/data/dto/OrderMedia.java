package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.MediaType;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「订单媒体(OrderMedia)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author System Integrator Corp.
 */

public class OrderMedia implements Serializable, WebshopEntity {

  private static final long serialVersionUID = 1L;

  /** 订单号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "订单号", order = 1)
  private String orderNo;

  /** 免邮促销编号 */
  @Required
  @Length(16)
  @Metadata(name = "免邮促销编号", order = 2)
  private String freePostageCode;

  /** 免邮促销名称 */
  @Required
  @Length(50)
  @Metadata(name = "免邮促销名称", order = 3)
  private String freePostageName;

  /** 流入媒体编号 */
  @Required
  @Length(20)
  @Metadata(name = "流入媒体编号", order = 4)
  private String mediaCode;

  /** 流入媒体类型 */
  @Required
  @Length(1)
  @Domain(MediaType.class)
  @Metadata(name = "流入媒体类型", order = 5)
  private Long mediaType;

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
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @return the mediaCode
   */
  public String getMediaCode() {
    return mediaCode;
  }

  /**
   * @return the mediaType
   */
  public Long getMediaType() {
    return mediaType;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @param mediaCode
   *          the mediaCode to set
   */
  public void setMediaCode(String mediaCode) {
    this.mediaCode = mediaCode;
  }

  /**
   * @param mediaType
   *          the mediaType to set
   */
  public void setMediaType(Long mediaType) {
    this.mediaType = mediaType;
  }

}
