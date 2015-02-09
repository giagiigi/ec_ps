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
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 礼品卡发行履历
 * 
 * @author System Integrator Corp.
 */
public class GiftCardIssueHistory implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 礼品卡规则编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "礼品卡规则编号", order = 1)
  private String cardCode;
  
  /** 礼品卡批次 */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @Metadata(name = "礼品卡批次", order = 2)
  private Long cardHistoryNo;
  
  /** 发行日期 */
  @Required
  @Metadata(name = "发行日期", order = 3)
  private Date issueDate;
  
  /** 发行数量 */
  @Required
  @Length(16)
  @Metadata(name = "发行数量", order = 4)
  private Long issueNum;

  /** csv导出标志 */
  @Required
  @Length(1)
  @Metadata(name = "csv导出标志", order = 5)
  private Long csvFlg;
  
  /** 面值 */
  @Required
  @Length(1)
  @Metadata(name = "取消标志", order = 6)
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
   * @return the issueNum
   */
  public Long getIssueNum() {
    return issueNum;
  }

  
  /**
   * @param issueNum the issueNum to set
   */
  public void setIssueNum(Long issueNum) {
    this.issueNum = issueNum;
  }

  
  /**
   * @return the csvFlg
   */
  public Long getCsvFlg() {
    return csvFlg;
  }

  
  /**
   * @param csvFlg the csvFlg to set
   */
  public void setCsvFlg(Long csvFlg) {
    this.csvFlg = csvFlg;
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

  


}
