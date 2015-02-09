package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GiftCardDetailListSummary implements Serializable {

  /**
     * 
     */
  private static final long serialVersionUID = 1L;

  /** 礼品卡编号 */
  private String cardCode;

  /** 礼品卡名称 */
  private String cardName;

  /** 发行时间 */
  private Date issueTime;

  /** 发行件数 */
  private Long issueNum;

  // 销售单价合计
  private BigDecimal totalSalePrice;

  // 面值合计
  private BigDecimal totalDenomination;

  // 激活金额
  private BigDecimal activateAmount;

  // 未激活金额
  private BigDecimal unactAmount;
  
  private BigDecimal useAmount;
  
  private BigDecimal leftAmount;

  
  /**
   * @return the issueTime
   */
  public Date getIssueTime() {
    return issueTime;
  }


  
  /**
   * @param issueTime the issueTime to set
   */
  public void setIssueTime(Date issueTime) {
    this.issueTime = issueTime;
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
   * @return the totalSalePrice
   */
  public BigDecimal getTotalSalePrice() {
    return totalSalePrice;
  }


  
  /**
   * @param totalSalePrice the totalSalePrice to set
   */
  public void setTotalSalePrice(BigDecimal totalSalePrice) {
    this.totalSalePrice = totalSalePrice;
  }


  
  /**
   * @return the totalDenomination
   */
  public BigDecimal getTotalDenomination() {
    return totalDenomination;
  }


  
  /**
   * @param totalDenomination the totalDenomination to set
   */
  public void setTotalDenomination(BigDecimal totalDenomination) {
    this.totalDenomination = totalDenomination;
  }


  
  /**
   * @return the activateAmount
   */
  public BigDecimal getActivateAmount() {
    return activateAmount;
  }


  
  /**
   * @param activateAmount the activateAmount to set
   */
  public void setActivateAmount(BigDecimal activateAmount) {
    this.activateAmount = activateAmount;
  }


  
  /**
   * @return the unactAmount
   */
  public BigDecimal getUnactAmount() {
    return unactAmount;
  }


  
  /**
   * @param unactAmount the unactAmount to set
   */
  public void setUnactAmount(BigDecimal unactAmount) {
    this.unactAmount = unactAmount;
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
   * @return the leftAmount
   */
  public BigDecimal getLeftAmount() {
    return leftAmount;
  }



  
  /**
   * @param leftAmount the leftAmount to set
   */
  public void setLeftAmount(BigDecimal leftAmount) {
    this.leftAmount = leftAmount;
  }

}
