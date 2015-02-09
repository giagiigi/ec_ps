package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GiftCardUseLogSummary implements Serializable {

  /**
     * 
     */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  private String cardId;

  private Date rechargeDate;

  private BigDecimal unitPrice;

  private BigDecimal denomination;

  private BigDecimal discountRate;

  private BigDecimal useAmount;

  private BigDecimal leftAmount;

  private String linkOrder ;


  /**
   * @return the customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * @param customerCode
   *          the customerCode to set
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
   * @param cardId
   *          the cardId to set
   */
  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  /**
   * @return the unitPrice
   */
  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  /**
   * @param unitPrice
   *          the unitPrice to set
   */
  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * @return the denomination
   */
  public BigDecimal getDenomination() {
    return denomination;
  }

  /**
   * @param denomination
   *          the denomination to set
   */
  public void setDenomination(BigDecimal denomination) {
    this.denomination = denomination;
  }

  /**
   * @return the discountRate
   */
  public BigDecimal getDiscountRate() {
    return discountRate;
  }

  /**
   * @param discountRate
   *          the discountRate to set
   */
  public void setDiscountRate(BigDecimal discountRate) {
    this.discountRate = discountRate;
  }

  /**
   * @return the useAmount
   */
  public BigDecimal getUseAmount() {
    return useAmount;
  }

  /**
   * @param useAmount
   *          the useAmount to set
   */
  public void setUseAmount(BigDecimal useAmount) {
    this.useAmount = useAmount;
  }

  /**
   * @return the leftAmount
   */
  public BigDecimal getLeftAmount() {
    return leftAmount;
  }

  /**
   * @param leftAmount
   *          the leftAmount to set
   */
  public void setLeftAmount(BigDecimal leftAmount) {
    this.leftAmount = leftAmount;
  }

  /**
   * @return the rechargeDate
   */
  public Date getRechargeDate() {
    return rechargeDate;
  }

  /**
   * @param rechargeDate
   *          the rechargeDate to set
   */
  public void setRechargeDate(Date rechargeDate) {
    this.rechargeDate = rechargeDate;
  }

  
  /**
   * @return the linkOrder
   */
  public String getLinkOrder() {
    return linkOrder;
  }

  
  /**
   * @param linkOrder the linkOrder to set
   */
  public void setLinkOrder(String linkOrder) {
    this.linkOrder = linkOrder;
  }

}
