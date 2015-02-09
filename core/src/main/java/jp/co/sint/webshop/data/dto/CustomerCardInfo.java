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
public class CustomerCardInfo implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 礼品卡规则 */
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
  
  /** 礼品卡规则 */
  @Required
  @Length(16)
  @Metadata(name = "礼品卡规则编号", order = 3)
  private String cardCode;
  
  /** 礼品卡名称 */
  @Length(50)
  @Metadata(name = "礼品卡名称", order = 4)
  private String cardName;
  
  /** 面值 */
  @Required
  @Length(11)
  @Metadata(name = "面值", order = 5)
  private BigDecimal denomination;
  
  /** 有效期 */
  @Required
  @Metadata(name = "有效期", order = 6)
  private Date cardEndDate;
  
  /** 退款备注 */
  @Length(50)
  @Metadata(name = "退款备注", order = 7)
  private String memo;
  
  /** 充值日期 */
  @Required
  @Metadata(name = "充值日期", order = 8)
  private Date rechargeDate;
  
  @Required
  @Length(1)
  @Metadata(name = "卡状态", order = 9)
  private Long cardStatus;
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 10)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 11)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 12)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 13)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 14)
  private Date updatedDatetime;
  
  /** 订单编号 */
  @Length(16)
  @Metadata(name = "订单编号", order = 15)
  private String orderNo;

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
   * @return the denomination
   */
  public BigDecimal getDenomination() {
    return denomination;
  }

  
  /**
   * @param denomination the denomination to set
   */
  public void setDenomination(BigDecimal denomination) {
    this.denomination = denomination;
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
   * @return the rechargeDate
   */
  public Date getRechargeDate() {
    return rechargeDate;
  }

  
  /**
   * @param rechargeDate the rechargeDate to set
   */
  public void setRechargeDate(Date rechargeDate) {
    this.rechargeDate = rechargeDate;
  }

  
  /**
   * @return the cardStatus
   */
  public Long getCardStatus() {
    return cardStatus;
  }

  
  /**
   * @param cardStatus the cardStatus to set
   */
  public void setCardStatus(Long cardStatus) {
    this.cardStatus = cardStatus;
  }

  
  /**
   * @return the cardCode
   */
  public String getCardCode() {
    return cardCode;
  }

  
  /**
   * @param cardCode the cardCode to set
   */
  public void setCardCode(String cardCode) {
    this.cardCode = cardCode;
  }

  
  /**
   * @return the cardName
   */
  public String getCardName() {
    return cardName;
  }

  
  /**
   * @param cardName the cardName to set
   */
  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  
  /**
   * @return the cardEndDate
   */
  public Date getCardEndDate() {
    return cardEndDate;
  }

  
  /**
   * @param cardEndDate the cardEndDate to set
   */
  public void setCardEndDate(Date cardEndDate) {
    this.cardEndDate = cardEndDate;
  }

  
  /**
   * @return the memo
   */
  public String getMemo() {
    return memo;
  }

  
  /**
   * @param memo the memo to set
   */
  public void setMemo(String memo) {
    this.memo = memo;
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


}
