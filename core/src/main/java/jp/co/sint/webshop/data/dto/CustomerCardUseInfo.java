//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
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
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 礼品卡规则
 * 
 * @author System Integrator Corp.
 */
public class CustomerCardUseInfo implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 客户编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "客户编号", order = 1)
  private String customerCode;
  
  /** 卡号 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "卡号", order = 2)
  private String cardId;
  
  /** 订单编号 */
  @PrimaryKey(3)
  @Required
  @Length(16)
  @Metadata(name = "订单编号", order = 3)
  private String orderNo;
  
  /** 使用金额 */
  @Required
  @Length(11)
  @Metadata(name = "使用金额", order = 4)
  private BigDecimal useAmount;

  /** 使用日期 */
  @Required
  @Metadata(name = "使用日期", order = 5)
  private Date useDate;
  
  @Required
  @Length(1)
  @Metadata(name = "使用状态", order = 7)
  private Long useStatus;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 8)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 9)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 10)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 11)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 12)
  private Date updatedDatetime;
  

  /**
   * @return the ormRowid
   */
  public Long getOrmRowid() {
    return ormRowid;
  }

  /**
   * @param ormRowid the ormRowid to set
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
   * @param createdUser the createdUser to set
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
   * @param createdDatetime the createdDatetime to set
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
   * @param updatedUser the updatedUser to set
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
   * @param updatedDatetime the updatedDatetime to set
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  
  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  
  /**
   * @param customerCode the customerCode to set
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  
  /**
   * @return the cardId
   */
  public String getCardId() {
    return cardId;
  }

  
  /**
   * @param cardId the cardId to set
   */
  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  
  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  
  /**
   * @param orderNo the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  
  /**
   * @return the useAmount
   */
  public BigDecimal getUseAmount() {
    return useAmount;
  }

  
  /**
   * @param useAmount the useAmount to set
   */
  public void setUseAmount(BigDecimal useAmount) {
    this.useAmount = useAmount;
  }

  
  /**
   * @return the useDate
   */
  public Date getUseDate() {
    return useDate;
  }

  
  /**
   * @param useDate the useDate to set
   */
  public void setUseDate(Date useDate) {
    this.useDate = useDate;
  }

  
  /**
   * @return the useStatus
   */
  public Long getUseStatus() {
    return useStatus;
  }

  
  /**
   * @param useStatus the useStatus to set
   */
  public void setUseStatus(Long useStatus) {
    this.useStatus = useStatus;
  }

  

}
