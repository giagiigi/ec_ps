package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class AttributeData implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  private String shopCode;

  private String categoryAttribute;

  private String categoryAttribute1;
  
  private Long commodityCount;
  
  private Long commodityPopularRank;
  
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
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }


  
  /**
   * @param shopCode the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
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
