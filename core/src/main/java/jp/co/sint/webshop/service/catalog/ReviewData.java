package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

/**
 * @author System Integrator Corp.
 */
public class ReviewData implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private String reviewScore;
  
  private Long commodityCount;
  
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


}
