package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.ReviewPost;

public class ReviewList extends ReviewPost {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 商品名 */
  @Required
  private String commodityName;
  private String commodityNameEn;
  private String commodityNameJp;
//20111219 os013 add start
  private Long sign;
  
  private String janCode;
//20111219 os013 add end
  
  // 20120201 ysy add start
  private Long reviewDisplayType;
  // 20120201 ysy add end
  
  private Long discountMode;
  
  /**
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
 * @return the reviewDisplayType
 */
public Long getReviewDisplayType() {
	return reviewDisplayType;
}

/**
 * @param reviewDisplayType the reviewDisplayType to set
 */
public void setReviewDisplayType(Long reviewDisplayType) {
	this.reviewDisplayType = reviewDisplayType;
}

/**
   * commodityNameを設定します。
   * 
   * @param commodityName
   *          commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }
//20111219 os013 add start

  /**
   * signを取得します。
   * 
   * @return sign
   */
  public Long getSign() {
    return sign;
  }

  /**
   * signを設定します。
   * 
   * @param sign
   *          sign
   */
  public void setSign(Long sign) {
    this.sign = sign;
  }

  /**
   * 获得 janCode
   * 
   * @return janCode
   */
  public String getJanCode() {
    return janCode;
  }

  /**
   * 设定 janCode
   * @param janCode
   */
  public void setJanCode(String janCode) {
    this.janCode = janCode;
  }

  
  /**
   * @return the discountMode
   */
  public Long getDiscountMode() {
    return discountMode;
  }

  
  /**
   * @param discountMode the discountMode to set
   */
  public void setDiscountMode(Long discountMode) {
    this.discountMode = discountMode;
  }

/**
 * @param commodityNameEn the commodityNameEn to set
 */
public void setCommodityNameEn(String commodityNameEn) {
	this.commodityNameEn = commodityNameEn;
}

/**
 * @return the commodityNameEn
 */
public String getCommodityNameEn() {
	return commodityNameEn;
}

/**
 * @param commodityNameJp the commodityNameJp to set
 */
public void setCommodityNameJp(String commodityNameJp) {
	this.commodityNameJp = commodityNameJp;
}

/**
 * @return the commodityNameJp
 */
public String getCommodityNameJp() {
	return commodityNameJp;
}
  
//20111219 os013 add end
}
