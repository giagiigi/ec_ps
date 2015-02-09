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
 * 优惠券信息
 * 
 * @author System Integrator Corp.
 */
public class MyCouponHistoryBean extends UIFrontBean  implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<MyCouponHistoryDetail> couponIssueList = new ArrayList<MyCouponHistoryDetail>();
  
  private PagerValue pagerValue;
  
  /**是否存在查询信息*/
  private boolean hasValue = Boolean.TRUE;
  /**
   * クーポン詳細情報
   *  
   * @author System Integrator Corp.
   */
  public static class MyCouponHistoryDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** クーポン名称 */
    private String couponName;
    
    /**クーポン名称(英文)*/
    private String couponNameEn;
    
    /**优惠券名称(日文)*/
    private String couponNameJp;

    /** 优惠金額 */
    private String couponAmount;

    /** 使用起始金額 */
    private String couponBeginAmount;

    /** クーポン利用开始日时 */
    private String useStartDatetime;

    /** クーポン利用结束日时 */
    private String useEndDatetime;

    /** 使用状態 */
    private String useStatus;
    
    /**固定金额优惠FLG*/
    private boolean fixedFlg = Boolean.FALSE;

    /**
     * 取得クーポン名称
     * 
     * @return
     */
    public String getCouponName() {
      return couponName;
    }

    /**
     * 设定クーポン名称
     * 
     * @param couponName
     */
    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    /**
     * 取得优惠金额
     * 
     * @return
     */
    public String getCouponAmount() {
      return couponAmount;
    }

    /**
     *  設定优惠金额
     * 
     * @param couponAmount
     */
    public void setCouponAmount(String couponAmount) {
      this.couponAmount = couponAmount;
    }

    /**
     * 取得使用起始金额
     * 
     * @return
     */
    public String getCouponBeginAmount() {
      return couponBeginAmount;
    }

    /**
     *  設定使用起始金额
     * 
     * @param couponBeginAmount
     */
    public void setCouponBeginAmount(String couponBeginAmount) {
      this.couponBeginAmount = couponBeginAmount;
    }

    /**
     * 取得クーポン利用开始日时
     * 
     * @return
     */
    public String getUseStartDatetime() {
      return useStartDatetime;
    }

    /**
     *  設定クーポン利用开始日时
     * 
     * @param useStartDatetime
     */
    public void setUseStartDatetime(String useStartDatetime) {
      this.useStartDatetime = useStartDatetime;
    }

    /**
     * 取得クーポン利用结束日时
     * 
     * @return
     */
    public String getUseEndDatetime() {
      return useEndDatetime;
    }

    /**
     *  設定クーポン利用结束日时
     * 
     * @param useEndDatetime
     */
    public void setUseEndDatetime(String useEndDatetime) {
      this.useEndDatetime = useEndDatetime;
    }

    /**
     * 取得使用状態
     * 
     * @return
     */
    public String getUseStatus() {
      return useStatus;
    }

    /**
     *  設定使用状態
     * 
     * @param useStatus
     */
    public void setUseStatus(String useStatus) {
      this.useStatus = useStatus;
    }
    /**
     * 取得クーポン名称(英文)
     * @return
     */
    public String getCouponNameEn() {
      return couponNameEn;
    }

    /**
     *  設定クーポン名称(英文)
     * @param couponNameEn
     */
    public void setCouponNameEn(String couponNameEn) {
      this.couponNameEn = couponNameEn;
    }

    /**
     * 取得クーポン名称(日文)
     * @return
     */
    public String getCouponNameJp() {
      return couponNameJp;
    }

    /**
     *  設定クーポン名称(日文)
     * @param couponNameJp
     */
    public void setCouponNameJp(String couponNameJp) {
      this.couponNameJp = couponNameJp;
    }

    /**
     * 固定金额优惠FLG
     * @return
     */
    public boolean isFixedFlg() {
      return fixedFlg;
    }

    /**
     * 固定金额优惠FLG
     * @param fixedFlg
     */
    public void setFixedFlg(boolean fixedFlg) {
      this.fixedFlg = fixedFlg;
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

  public List<MyCouponHistoryDetail> getCouponIssueList() {
    return couponIssueList;
  }

  public void setCouponIssueList(List<MyCouponHistoryDetail> couponIssueList) {
    this.couponIssueList = couponIssueList;
  }
  
  /**
   * 是否存在查询信息取得
   * @return
   */
  public boolean isHasValue() {
    return hasValue;
  }
  
  /**
   * 是否存在查询信息设定
   * @param hasValue
   */
  public void setHasValue(boolean hasValue) {
    this.hasValue = hasValue;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "";
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
  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.NewCouponListBean.0");
  }
  
  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.NewCouponListBean.0"), "/app/mypage/my_coupon_history/init"));
    return topicPath;
  }

}
