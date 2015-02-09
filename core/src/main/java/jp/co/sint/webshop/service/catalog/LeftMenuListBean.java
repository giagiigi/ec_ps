package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

public class LeftMenuListBean implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String commodityCode;
  
  private String brandCode;
  
  private String brandName;
  //add by cs_yuli 20120524 start
  private String brandNameEn; 
  
  private String brandNameJp; 
  //add by cs_yuli 20120524 end
  private String categoryPath;

  private String categoryAttribute1;
  //add by cs_yuli 20120524 start
  private String categoryAttribute1En;

  private String categoryAttribute1Jp;
  //add by cs_yuli 20120524 end
  private String reviewScore;
  
  private String price;
  
  private Long commodityPopularRank;

  
  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  
  /**
   * @param commodityCode the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  
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
   * @return the categoryPath
   */
  public String getCategoryPath() {
    return categoryPath;
  }

  
  /**
   * @param categoryPath the categoryPath to set
   */
  public void setCategoryPath(String categoryPath) {
    this.categoryPath = categoryPath;
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


/**
 * @param brandNameEn the brandNameEn to set
 */
public void setBrandNameEn(String brandNameEn) {
	this.brandNameEn = brandNameEn;
}


/**
 * @return the brandNameEn
 */
public String getBrandNameEn() {
	return brandNameEn;
}


/**
 * @param brandNameJp the brandNameJp to set
 */
public void setBrandNameJp(String brandNameJp) {
	this.brandNameJp = brandNameJp;
}


/**
 * @return the brandNameJp
 */
public String getBrandNameJp() {
	return brandNameJp;
}


/**
 * @param categoryAttribute1En the categoryAttribute1En to set
 */
public void setCategoryAttribute1En(String categoryAttribute1En) {
	this.categoryAttribute1En = categoryAttribute1En;
}


/**
 * @return the categoryAttribute1En
 */
public String getCategoryAttribute1En() {
	return categoryAttribute1En;
}


/**
 * @param categoryAttribute1Jp the categoryAttribute1Jp to set
 */
public void setCategoryAttribute1Jp(String categoryAttribute1Jp) {
	this.categoryAttribute1Jp = categoryAttribute1Jp;
}


/**
 * @return the categoryAttribute1Jp
 */
public String getCategoryAttribute1Jp() {
	return categoryAttribute1Jp;
}
  
}
