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
public class GiftCardReturnApply implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 订单编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @Metadata(name = "订单编号", order = 1)
  private String orderNo;
  
  /** 礼品卡使用金额 */
  @Required
  @Length(11)
  @Metadata(name = "礼品卡使用金额", order = 2)
  private BigDecimal cardUseAmount;
  
  /** 客服指定退款金额 */
  @Required
  @Length(11)
  @Metadata(name = "客服指定退款金额", order = 3)
  private BigDecimal memberInfoAmount;
  
  /** 审批确认金额 */
  @Required
  @Length(11)
  @Metadata(name = "审批确认金额", order = 4)
  private BigDecimal confirmAmount;
  
  /** 确认标志 0：客服确认 1：审批确认 */
  @Required
  @Length(1)
  @Metadata(name = "确认标志 0：客服确认 1：审批确认", order = 5)
  private Long confirmFlg;
  
  /** 退款日期 */
  @Required
  @Metadata(name = "退款日期", order = 6)
  private Date returnDate;
  
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
  
  /** 订单编号 */
  @Length(16)
  @Metadata(name = "客户编号", order = 12)
  private String customerCode;
  

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
   * @return the cardUseAmount
   */
  public BigDecimal getCardUseAmount() {
    return cardUseAmount;
  }

  
  /**
   * @param cardUseAmount the cardUseAmount to set
   */
  public void setCardUseAmount(BigDecimal cardUseAmount) {
    this.cardUseAmount = cardUseAmount;
  }

  
  /**
   * @return the memberInfoAmount
   */
  public BigDecimal getMemberInfoAmount() {
    return memberInfoAmount;
  }

  
  /**
   * @param memberInfoAmount the memberInfoAmount to set
   */
  public void setMemberInfoAmount(BigDecimal memberInfoAmount) {
    this.memberInfoAmount = memberInfoAmount;
  }

  
  /**
   * @return the confirmAmount
   */
  public BigDecimal getConfirmAmount() {
    return confirmAmount;
  }

  
  /**
   * @param confirmAmount the confirmAmount to set
   */
  public void setConfirmAmount(BigDecimal confirmAmount) {
    this.confirmAmount = confirmAmount;
  }

  
  /**
   * @return the confirmFlg
   */
  public Long getConfirmFlg() {
    return confirmFlg;
  }

  
  /**
   * @param confirmFlg the confirmFlg to set
   */
  public void setConfirmFlg(Long confirmFlg) {
    this.confirmFlg = confirmFlg;
  }

  
  /**
   * @return the returnDate
   */
  public Date getReturnDate() {
    return returnDate;
  }

  
  /**
   * @param returnDate the returnDate to set
   */
  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
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

  


}
