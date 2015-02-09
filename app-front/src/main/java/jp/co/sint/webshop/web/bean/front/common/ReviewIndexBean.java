package jp.co.sint.webshop.web.bean.front.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * インフォメーションのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class ReviewIndexBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<ReviewListBeanDetail> list = new ArrayList<ReviewListBeanDetail>();

  public static class ReviewListBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    private String reviewContributedDatetime;

    private String commodityName;

    private String reviewTitle;

    private String reviewDescription;
      
    private boolean displayLink;
    
    private String commodityCode;
    
    private String reviewScore;
    
    private String janCode;
    
    private Long reviewDisplayType;
    
    private String shopCode;
    
    private Long discountMode;

    private String nickName;
    
    /**
     * @return the reviewContributedDatetime
     */
    public String getReviewContributedDatetime() {
      return reviewContributedDatetime;
    }

    
    /**
     * @param reviewContributedDatetime the reviewContributedDatetime to set
     */
    public void setReviewContributedDatetime(String reviewContributedDatetime) {
      this.reviewContributedDatetime = reviewContributedDatetime;
    }

    
    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    
    /**
     * @param commodityName the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    
    /**
     * @return the reviewTitle
     */
    public String getReviewTitle() {
      return reviewTitle;
    }

    
    /**
     * @param reviewTitle the reviewTitle to set
     */
    public void setReviewTitle(String reviewTitle) {
      this.reviewTitle = reviewTitle;
    }

    
    /**
     * @return the reviewDescription
     */
    public String getReviewDescription() {
      return reviewDescription;
    }

    
    /**
     * @param reviewDescription the reviewDescription to set
     */
    public void setReviewDescription(String reviewDescription) {
      this.reviewDescription = reviewDescription;
    }

    
    /**
     * @return the displayLink
     */
    public boolean isDisplayLink() {
      return displayLink;
    }

    
    /**
     * @param displayLink the displayLink to set
     */
    public void setDisplayLink(boolean displayLink) {
      this.displayLink = displayLink;
    }

    
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
     * @return the janCode
     */
    public String getJanCode() {
      return janCode;
    }

    
    /**
     * @param janCode the janCode to set
     */
    public void setJanCode(String janCode) {
      this.janCode = janCode;
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
     * @return the nickName
     */
    public String getNickName() {
      return nickName;
    }


    
    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
      this.nickName = nickName;
    }
  }
  
  @Override
  public void createAttributes(RequestParameter reqparam) {
    
  }
  
  /**
   * @return the list
   */
  public List<ReviewListBeanDetail> getList() {
    return list;
  }
  
  /**
   * @param list the list to set
   */
  public void setList(List<ReviewListBeanDetail> list) {
    this.list = list;
  }
  
}
