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
 * @author System Integrator Corp.
 */
public class PrivateCouponExchangeBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue;

  /** 有效积分 */
  private Long goodPoint;

  private List<PrivateCouponExchangeBeanDetail> list = new ArrayList<PrivateCouponExchangeBeanDetail>();

  private List<PrivateCouponUseDetail> useList = new ArrayList<PrivateCouponUseDetail>();
  
  private List<FriendIssueHistoryDetail> issueList = new ArrayList<FriendIssueHistoryDetail>();

  public static class PrivateCouponUseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 优惠金额
    private String couponAmount;

    // 优惠券利用最小购买金额
    private String minUseOrderAmount;

    // 有效期限(开始)
    private String minUseStartDatetime;

    // 有效期限(结束)
    private String minUseEndDatetime;

    // 优惠劵规则代码
    private String couponCode;

    // 优惠劵规则名称
    private String couponName;

    // Link表示制御Flag
    private Boolean displayLink = false;
    
    // 兑换所需点数
    private String exchangePointAmount;
    
    // 优惠券利用结束日期（金额、特别会员使用）
    private String minUseEndNum;
    
    // 优惠类型 false-金额  true-比率
    private Boolean couponFlag = false;
    
    
    public String getCouponAmount() {
      return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    public String getMinUseOrderAmount() {
      return minUseOrderAmount;
    }

    public void setMinUseOrderAmount(String minUseOrderAmount) {
      this.minUseOrderAmount = minUseOrderAmount;
    }

    public String getMinUseStartDatetime() {
      return minUseStartDatetime;
    }

    public void setMinUseStartDatetime(String minUseStartDatetime) {
      this.minUseStartDatetime = minUseStartDatetime;
    }

    public String getMinUseEndDatetime() {
      return minUseEndDatetime;
    }

    public void setMinUseEndDatetime(String minUseEndDatetime) {
      this.minUseEndDatetime = minUseEndDatetime;
    }

    public String getCouponCode() {
      return couponCode;
    }

    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

    public String getCouponName() {
      return couponName;
    }

    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    public Boolean getDisplayLink() {
      return displayLink;
    }

    public void setDisplayLink(Boolean displayLink) {
      this.displayLink = displayLink;
    }

    
    public String getExchangePointAmount() {
      return exchangePointAmount;
    }

    
    public void setExchangePointAmount(String exchangePointAmount) {
      this.exchangePointAmount = exchangePointAmount;
    }

    
    public String getMinUseEndNum() {
      return minUseEndNum;
    }

    
    public void setMinUseEndNum(String minUseEndNum) {
      this.minUseEndNum = minUseEndNum;
    }

    
    public Boolean getCouponFlag() {
      return couponFlag;
    }
    
    public void setCouponFlag(Boolean couponFlag) {
      this.couponFlag = couponFlag;
    }
  }

  public static class PrivateCouponExchangeBeanDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 顾客名称
    private String customerName;

    // 发行日期
    private String couponIssueDate;

    // 优惠劵编号
    private String couponCode;

    // 优惠劵金额
    private String couponAmount;

    // 所得积分
    private Long allPoint;

    // 利用回数
    private Long couponNum;
    
    // 订单号
    private String orderNo;
    
    /**
     * @return the customerName
     */
    public String getCustomerName() {
      return customerName;
    }

    /**
     * @param customerName
     *          the customerName to set
     */
    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

    /**
     * @return the couponIssueDate
     */
    public String getCouponIssueDate() {
      return couponIssueDate;
    }

    /**
     * @param couponIssueDate
     *          the couponIssueDate to set
     */
    public void setCouponIssueDate(String couponIssueDate) {
      this.couponIssueDate = couponIssueDate;
    }

    /**
     * @return the couponCode
     */
    public String getCouponCode() {
      return couponCode;
    }

    /**
     * @param couponCode
     *          the couponCode to set
     */
    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

    /**
     * @return the couponAmount
     */
    public String getCouponAmount() {
      return couponAmount;
    }

    /**
     * @param couponAmount
     *          the couponAmount to set
     */
    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    /**
     * @return the allPoint
     */
    public Long getAllPoint() {
      return allPoint;
    }

    /**
     * @param allPoint
     *          the allPoint to set
     */
    public void setAllPoint(Long allPoint) {
      this.allPoint = allPoint;
    }

    public Long getCouponNum() {
      return couponNum;
    }

    public void setCouponNum(Long couponNum) {
      this.couponNum = couponNum;
    }

    
    /**
     * @return the orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    
    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }
    
  }

   public static class FriendIssueHistoryDetail implements Serializable{
     
       private static final long serialVersionUID = 1L;
       
       // 发行日期
       private String issueDate;
       // 优惠券编号
       private String couponCode;
       
       //优惠券类型
       private String couponIssueType;
       
       //优惠金额
       private String couponAmount;
       
       //优惠比例
       
       private String couponProportion;
       
       //发行积分
       private String issueObtainPoint;
      
      /**
       * @return the issueDate
       */
      public String getIssueDate() {
        return issueDate;
      }
      
      /**
       * @param issueDate the issueDate to set
       */
      public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
      }

      /**
       * @return the couponCode
       */
      public String getCouponCode() {
        return couponCode;
      }
      
      /**
       * @param couponCode the couponCode to set
       */
      public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
      }
      
      /**
       * @return the couponIssueType
       */
      public String getCouponIssueType() {
        return couponIssueType;
      }
      
      /**
       * @param couponIssueType the couponIssueType to set
       */
      public void setCouponIssueType(String couponIssueType) {
        this.couponIssueType = couponIssueType;
      }

      
      /**
       * @return the couponAmount
       */
      public String getCouponAmount() {
        return couponAmount;
      }

      
      /**
       * @param couponAmount the couponAmount to set
       */
      public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
      }

      
      /**
       * @return the couponProportion
       */
      public String getCouponProportion() {
        return couponProportion;
      }

      
      /**
       * @param couponProportion the couponProportion to set
       */
      public void setCouponProportion(String couponProportion) {
        this.couponProportion = couponProportion;
      }

      
      /**
       * @return the issueObtainPoint
       */
      public String getIssueObtainPoint() {
        return issueObtainPoint;
      }

      
      /**
       * @param issueObtainPoint the issueObtainPoint to set
       */
      public void setIssueObtainPoint(String issueObtainPoint) {
        this.issueObtainPoint = issueObtainPoint;
      }
    
     
   }
   
   
  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.PrivateCouponExchangeBean.0");
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue pagerValue
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

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderDetailBean.1"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.PrivateCouponExchangeBean.0"),
        "/app/mypage/private_coupon_exchange/init"));
    return topicPath;
  }

  /**
   * @return the goodPoint
   */
  public Long getGoodPoint() {
    return goodPoint;
  }

  /**
   * @param goodPoint
   *          the goodPoint to set
   */
  public void setGoodPoint(Long goodPoint) {
    this.goodPoint = goodPoint;
  }

  /**
   * @return the list
   */
  public List<PrivateCouponExchangeBeanDetail> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<PrivateCouponExchangeBeanDetail> list) {
    this.list = list;
  }

  public List<PrivateCouponUseDetail> getUseList() {
    return useList;
  }

  public void setUseList(List<PrivateCouponUseDetail> useList) {
    this.useList = useList;
  }

  
  /**
   * @return the issueList
   */
  public List<FriendIssueHistoryDetail> getIssueList() {
    return issueList;
  }

  
  /**
   * @param issueList the issueList to set
   */
  public void setIssueList(List<FriendIssueHistoryDetail> issueList) {
    this.issueList = issueList;
  }
  
 

}
