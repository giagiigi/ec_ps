package jp.co.sint.webshop.service.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OwnerCardDetail implements Serializable {
  
  /** serial version UID */
  private static final long serialVersionUID = 1L;
  
  private String cardId;
  
  private BigDecimal denomination;
  
  private Date rechargeDate;
  
  private Long cardStatus; 
  
  private Date cardEndDate;
  
  private BigDecimal avalibleAmount;

  
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
   * @return the avalibleAmount
   */
  public BigDecimal getAvalibleAmount() {
    return avalibleAmount;
  }

  
  /**
   * @param avalibleAmount the avalibleAmount to set
   */
  public void setAvalibleAmount(BigDecimal avalibleAmount) {
    this.avalibleAmount = avalibleAmount;
  }
  
  
}
