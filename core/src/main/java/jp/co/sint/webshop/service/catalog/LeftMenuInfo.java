package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class LeftMenuInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String brandCode;
  
  private String brandName;
  
  private String reviewScore;
  
  private String price;
  
  private String priceName;

  private String categoryAttribute;

  private String categoryAttribute1;
  
  private Long commodityCount;
  
  private Long commodityPopularRank;

  
  /**
   * @return the brandCode
   */
  public String getBrandCode() {
    return brandCode;
  }

  
  /**
   * @param brandCode the brandCode to set
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  
  /**
   * @return the brandName
   */
  public String getBrandName() {
    return brandName;
  }

  
  /**
   * @param brandName the brandName to set
   */
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  
  /**
   * @return the reviewScore
   */
  public String getReviewScore() {
    return reviewScore;
  }

  
  /**
   * @param reviewScore the reviewScore to set
   */
  public void setReviewScore(String reviewScore) {
    this.reviewScore = reviewScore;
  }

  
  /**
   * @return the price
   */
  public String getPrice() {
    return price;
  }

  
  /**
   * @param price the price to set
   */
  public void setPrice(String price) {
    this.price = price;
  }

  
  /**
   * @return the priceName
   */
  public String getPriceName() {
    return priceName;
  }

  
  /**
   * @param priceName the priceName to set
   */
  public void setPriceName(String priceName) {
    this.priceName = priceName;
  }

  
  /**
   * @return the categoryAttribute
   */
  public String getCategoryAttribute() {
    return categoryAttribute;
  }

  
  /**
   * @param categoryAttribute the categoryAttribute to set
   */
  public void setCategoryAttribute(String categoryAttribute) {
    this.categoryAttribute = categoryAttribute;
  }

  
  /**
   * @return the categoryAttribute1
   */
  public String getCategoryAttribute1() {
    return categoryAttribute1;
  }

  
  /**
   * @param categoryAttribute1 the categoryAttribute1 to set
   */
  public void setCategoryAttribute1(String categoryAttribute1) {
    this.categoryAttribute1 = categoryAttribute1;
  }

  
  /**
   * @return the commodityCount
   */
  public Long getCommodityCount() {
    return commodityCount;
  }

  
  /**
   * @param commodityCount the commodityCount to set
   */
  public void setCommodityCount(Long commodityCount) {
    this.commodityCount = commodityCount;
  }

  
  /**
   * @return the commodityPopularRank
   */
  public Long getCommodityPopularRank() {
    return commodityPopularRank;
  }

  
  /**
   * @param commodityPopularRank the commodityPopularRank to set
   */
  public void setCommodityPopularRank(Long commodityPopularRank) {
    this.commodityPopularRank = commodityPopularRank;
  }
}
