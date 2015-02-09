package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060210:レビュー一覧のデータモデルです。
 * 
 * @author OB
 */
public class ReviewListBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  private PagerValue pagerValue;

  private List<ReviewListBeanDetail> list = new ArrayList<ReviewListBeanDetail>();

  /**
   * U2060210:レビュー一覧詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class ReviewListBeanDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;



    private String reviewContributedDatetime;

    private String commodityName;

    private String reviewTitle;

    private String reviewDescription;
      
    // 20110504 shiseidou add start
    private boolean displayLink;
    
    private String commodityCode;
    
    private String reviewScore;
    
    // 20110504 shiseidou add end
    
    // 20110527 shiseidou add start
    private String janCode;
    // 20110527 shiseidou add end
    
    // 20120201 ysy add start
    private Long reviewDisplayType;
    
    private String shopCode;
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
     * reviewContributedDatetimeを取得します。
     * 
     * @return reviewContributedDatetime
     */
    public String getReviewContributedDatetime() {
      return reviewContributedDatetime;
    }

    /**
     * reviewDescriptionを取得します。
     * 
     * @return reviewDescription
     */
    public String getReviewDescription() {
      return reviewDescription;
    }

    /**
     * reviewTitleを取得します。
     * 
     * @return reviewTitle
     */
    public String getReviewTitle() {
      return reviewTitle;
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

    /**
     * reviewContributedDatetimeを設定します。
     * 
     * @param reviewContributedDatetime
     *          reviewContributedDatetime
     */
    public void setReviewContributedDatetime(String reviewContributedDatetime) {
      this.reviewContributedDatetime = reviewContributedDatetime;
    }

    /**
     * reviewDescriptionを設定します。
     * 
     * @param reviewDescription
     *          reviewDescription
     */
    public void setReviewDescription(String reviewDescription) {
      this.reviewDescription = reviewDescription;
    }

    /**
     * reviewTitleを設定します。
     * 
     * @param reviewTitle
     *          reviewTitle
     */
    public void setReviewTitle(String reviewTitle) {
      this.reviewTitle = reviewTitle;
    }


    // 20110504 shiseidou add start
    /**
     * displayLinkを取得します。
     * 
     * @return displayLink
     */
    public boolean isDisplayLink() {
      return displayLink;
    }

    /**
     * displayLinkを設定します。
     * 
     * @param displayLink
     *        displayLink
     */
    public void setDisplayLink(boolean displayLink) {
      this.displayLink = displayLink;
    }

    /**
     * commodityCodeを取得します。
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * commodityCodeを設定します。
     * 
     * @param commodityCode
     *        commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    
    /**
     * 获得 reviewScore
     * 
     * @return reviewScore
     */
    
    public String getReviewScore() {
      return reviewScore;
    }

    
    /**
     * 设定 reviewScore
     * @param reviewScore
     */
    public void setReviewScore(String reviewScore) {
      this.reviewScore = reviewScore;
    }
    
    // 20110504 shiseidou add end
    
    // 20110527 shiseidou add start
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
    // 20110527 shiseidou add end


    
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

    
    

  }
  
  
  public List<ReviewListBeanDetail> getList() {
    return list;
  }

  
  public void setList(List<ReviewListBeanDetail> list) {
    this.list = list;
  }
  
  

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    
  }

  @Override
  public String getModuleId() {
    return "U2060210";
  }
  

  @Override
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.ReviewListBean.0");
  }
  
  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
//    topicPath.add(new NameValue(Messages.getString(
//        "web.bean.front.mypage.ReviewListBean.1"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
    topicPath.add(new NameValue(Messages.getString(
        "web.bean.front.mypage.ReviewListBean.0"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
    return topicPath;
  }
}