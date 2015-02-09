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
 * 礼品卡发行履历
 * 
 * @author System Integrator Corp.
 */
public class GiftCardIssueDetail implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 礼品卡规则编号 */
  @Required
  @Length(16)
  @Metadata(name = "礼品卡规则编号", order = 1)
  private String cardCode;
  
  /** 礼品卡批次 */
  @Required
  @Length(16)
  @Metadata(name = "礼品卡批次", order = 2)
  private Long cardHistoryNo;
  
  /** 卡号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "卡号", order = 3)
  private String cardId;
  
  /** 密码 */
  @Required
  @Length(15)
  @Metadata(name = "密码", order = 4)
  private String passWord;
  
  /** 发行日期 */
  @Required
  @Metadata(name = "发行日期", order = 5)
  private Date issueDate;
  
  /** 面值 */
  @Required
  @Length(16)
  @Metadata(name = "面值", order = 6)
  private BigDecimal denomination;

  /** 卡状态 */
  @Required
  @Length(1)
  @Metadata(name = "卡状态", order = 7)
  private Long cardStatus;
  
  /** 取消标志 */
  @Required
  @Length(1)
  @Metadata(name = "取消标志", order = 8)
  private Long cancelFlg;
  
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 7)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 8)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 9)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 10)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 11)
  private Date updatedDatetime;
  
  /** 有效期 */
  @Required
  @Metadata(name = "有效期", order = 12)
  private Long effectiveYears;
  
  @Required
  @Length(50)
  @Metadata(name = "礼品卡规则名称", order = 13)
  private String cardName;
  
  /** 面值 */
  @Required
  @Length(16)
  @Metadata(name = "单价", order = 14)
  private BigDecimal unitPrice;

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
   * @return the cardHistoryNo
   */
  public Long getCardHistoryNo() {
    return cardHistoryNo;
  }

  
  /**
   * @param cardHistoryNo the cardHistoryNo to set
   */
  public void setCardHistoryNo(Long cardHistoryNo) {
    this.cardHistoryNo = cardHistoryNo;
  }

  
  /**
   * @return the issueDate
   */
  public Date getIssueDate() {
    return issueDate;
  }

  
  /**
   * @param issueDate the issueDate to set
   */
  public void setIssueDate(Date issueDate) {
    this.issueDate = issueDate;
  }

  
  /**
   * @return the cancelFlg
   */
  public Long getCancelFlg() {
    return cancelFlg;
  }

  
  /**
   * @param cancelFlg the cancelFlg to set
   */
  public void setCancelFlg(Long cancelFlg) {
    this.cancelFlg = cancelFlg;
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
   * @return the passWord
   */
  public String getPassWord() {
    return passWord;
  }

  
  /**
   * @param passWord the passWord to set
   */
  public void setPassWord(String passWord) {
    this.passWord = passWord;
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
   * @return the effectiveYears
   */
  public Long getEffectiveYears() {
    return effectiveYears;
  }

  
  /**
   * @param effectiveYears the effectiveYears to set
   */
  public void setEffectiveYears(Long effectiveYears) {
    this.effectiveYears = effectiveYears;
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
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  
  /**
   * @param unitPrice the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  

  


}
