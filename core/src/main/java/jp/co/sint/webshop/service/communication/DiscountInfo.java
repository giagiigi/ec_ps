package jp.co.sint.webshop.service.communication;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.dto.DiscountHeader;

public class DiscountInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private DiscountHeader discountHeader;

  private List<DiscountDetail> detailList = new ArrayList<DiscountDetail>();

  public static class DiscountDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String commodityName;

    private BigDecimal unitPrice;

    private Long stockQuantity;

    private Date saleStartDatetime;

    private Date saleEndDatetime;

    private BigDecimal discountPrice;

    private Long customerMaxTotalNum;

    private Long siteMaxTotalNum;

    private Long useFlg;

    private String discountDirectionsCn;

    private String discountDirectionsJp;

    private String discountDirectionsEn;
    
    private Long rankCn;
    
    private Long rankJp;
    
    private Long rankEn;

    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
      return unitPrice;
    }

    /**
     * @return the stockQuantity
     */
    public Long getStockQuantity() {
      return stockQuantity;
    }

    /**
     * @return the saleStartDatetime
     */
    public Date getSaleStartDatetime() {
      return saleStartDatetime;
    }

    /**
     * @return the saleEndDatetime
     */
    public Date getSaleEndDatetime() {
      return saleEndDatetime;
    }

    /**
     * @return the discountPrice
     */
    public BigDecimal getDiscountPrice() {
      return discountPrice;
    }

    /**
     * @return the customerMaxTotalNum
     */
    public Long getCustomerMaxTotalNum() {
      return customerMaxTotalNum;
    }

    /**
     * @return the siteMaxTotalNum
     */
    public Long getSiteMaxTotalNum() {
      return siteMaxTotalNum;
    }

    /**
     * @return the useFlg
     */
    public Long getUseFlg() {
      return useFlg;
    }

    /**
     * @return the discountDirectionsCn
     */
    public String getDiscountDirectionsCn() {
      return discountDirectionsCn;
    }

    /**
     * @return the discountDirectionsJp
     */
    public String getDiscountDirectionsJp() {
      return discountDirectionsJp;
    }

    /**
     * @return the discountDirectionsEn
     */
    public String getDiscountDirectionsEn() {
      return discountDirectionsEn;
    }

    /**
     * @param useFlg
     *          the useFlg to set
     */
    public void setUseFlg(Long useFlg) {
      this.useFlg = useFlg;
    }

    /**
     * @param discountDirectionsCn
     *          the discountDirectionsCn to set
     */
    public void setDiscountDirectionsCn(String discountDirectionsCn) {
      this.discountDirectionsCn = discountDirectionsCn;
    }

    /**
     * @param discountDirectionsJp
     *          the discountDirectionsJp to set
     */
    public void setDiscountDirectionsJp(String discountDirectionsJp) {
      this.discountDirectionsJp = discountDirectionsJp;
    }

    /**
     * @param discountDirectionsEn
     *          the discountDirectionsEn to set
     */
    public void setDiscountDirectionsEn(String discountDirectionsEn) {
      this.discountDirectionsEn = discountDirectionsEn;
    }

    /**
     * @param commodityCode
     *          the commodityCode to set
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * @param commodityName
     *          the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * @param unitPrice
     *          the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * @param stockQuantity
     *          the stockQuantity to set
     */
    public void setStockQuantity(Long stockQuantity) {
      this.stockQuantity = stockQuantity;
    }

    /**
     * @param saleStartDatetime
     *          the saleStartDatetime to set
     */
    public void setSaleStartDatetime(Date saleStartDatetime) {
      this.saleStartDatetime = saleStartDatetime;
    }

    /**
     * @param saleEndDatetime
     *          the saleEndDatetime to set
     */
    public void setSaleEndDatetime(Date saleEndDatetime) {
      this.saleEndDatetime = saleEndDatetime;
    }

    /**
     * @param discountPrice
     *          the discountPrice to set
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
      this.discountPrice = discountPrice;
    }

    /**
     * @param customerMaxTotalNum
     *          the customerMaxTotalNum to set
     */
    public void setCustomerMaxTotalNum(Long customerMaxTotalNum) {
      this.customerMaxTotalNum = customerMaxTotalNum;
    }

    /**
     * @param siteMaxTotalNum
     *          the siteMaxTotalNum to set
     */
    public void setSiteMaxTotalNum(Long siteMaxTotalNum) {
      this.siteMaxTotalNum = siteMaxTotalNum;
    }

    
    /**
     * @return the rankCn
     */
    public Long getRankCn() {
      return rankCn;
    }

    
    /**
     * @param rankCn the rankCn to set
     */
    public void setRankCn(Long rankCn) {
      this.rankCn = rankCn;
    }

    
    /**
     * @return the rankJp
     */
    public Long getRankJp() {
      return rankJp;
    }

    
    /**
     * @param rankJp the rankJp to set
     */
    public void setRankJp(Long rankJp) {
      this.rankJp = rankJp;
    }

    
    /**
     * @return the rankEn
     */
    public Long getRankEn() {
      return rankEn;
    }

    
    /**
     * @param rankEn the rankEn to set
     */
    public void setRankEn(Long rankEn) {
      this.rankEn = rankEn;
    }
  }

  /**
   * @return the detailList
   */
  public List<DiscountDetail> getDetailList() {
    return detailList;
  }

  /**
   * @param detailList
   *          the detailList to set
   */
  public void setDetailList(List<DiscountDetail> detailList) {
    this.detailList = detailList;
  }

  /**
   * @return the discountHeader
   */
  public DiscountHeader getDiscountHeader() {
    return discountHeader;
  }

  /**
   * @param discountHeader
   *          the discountHeader to set
   */
  public void setDiscountHeader(DiscountHeader discountHeader) {
    this.discountHeader = discountHeader;
  }

}
