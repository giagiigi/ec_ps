package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class CouponHistoryListBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CouponHistoryListDetail> list = new ArrayList<CouponHistoryListDetail>();

  public static class CouponHistoryListDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 优惠券编号 */
    private String couponIssueNo;

    /** 优惠券规则编号 */
    private String couponCode;

    /** 优惠券明细编号 */
    private String couponIssueDetailNo;

    /** 优惠券发行类别（0:比例；1:固定金额） */
    private String couponIssueType;

    /** 优惠券发行日时 */
    private String couponIssueDatetime;

    /** 优惠券比例 */
    private Long couponProportion;

    /** 优惠金额 */
    private String couponAmount;

    /** 优惠券利用最小购买金额 */
    private String minUseOrderAmount;

    /** 优惠券利用开始日时 */
    private String useStartDatetime;

    /** 优惠券利用结束日时 */
    private String useEndDatetime;

    /** 发行理由 */
    private String issueReason;

    /** 顾客编号 */
    private String customerCode;

    /** 发行原订单编号 */
    private String issueOrderNo;

    /** 使用订单编号 */
    private String useOrderNo;

    /** 使用状态 */
    private String useStatus;
    
    // 20120131 ysy add start
    /** 优惠券名称*/
    private String couponName;
    // 20120131 ysy add end
    
    
    public String getCouponIssueNo() {
      return couponIssueNo;
    }

    /**
	 * @return the couponName
	 */
	public String getCouponName() {
		return couponName;
	}

	/**
	 * @param couponName the couponName to set
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public void setCouponIssueNo(String couponIssueNo) {
      this.couponIssueNo = couponIssueNo;
    }

    public String getCouponCode() {
      return couponCode;
    }

    public void setCouponCode(String couponCode) {
      this.couponCode = couponCode;
    }

    public String getCouponIssueDetailNo() {
      return couponIssueDetailNo;
    }

    public void setCouponIssueDetailNo(String couponIssueDetailNo) {
      this.couponIssueDetailNo = couponIssueDetailNo;
    }

    public String getCouponIssueType() {
      return couponIssueType;
    }

    public void setCouponIssueType(String couponIssueType) {
      this.couponIssueType = couponIssueType;
    }

    public String getCouponIssueDatetime() {
      return couponIssueDatetime;
    }

    public void setCouponIssueDatetime(String couponIssueDatetime) {
      this.couponIssueDatetime = couponIssueDatetime;
    }

    public Long getCouponProportion() {
      return couponProportion;
    }

    public void setCouponProportion(Long couponProportion) {
      this.couponProportion = couponProportion;
    }

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

    public String getUseStartDatetime() {
      return useStartDatetime;
    }

    public void setUseStartDatetime(String useStartDatetime) {
      this.useStartDatetime = useStartDatetime;
    }

    public String getUseEndDatetime() {
      return useEndDatetime;
    }

    public void setUseEndDatetime(String useEndDatetime) {
      this.useEndDatetime = useEndDatetime;
    }

    public String getIssueReason() {
      return issueReason;
    }

    public void setIssueReason(String issueReason) {
      this.issueReason = issueReason;
    }

    public String getCustomerCode() {
      return customerCode;
    }

    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }

    public String getIssueOrderNo() {
      return issueOrderNo;
    }

    public void setIssueOrderNo(String issueOrderNo) {
      this.issueOrderNo = issueOrderNo;
    }

    public String getUseOrderNo() {
      return useOrderNo;
    }

    public void setUseOrderNo(String useOrderNo) {
      this.useOrderNo = useOrderNo;
    }

    public String getUseStatus() {
      return useStatus;
    }

    public void setUseStatus(String useStatus) {
      this.useStatus = useStatus;
    }

  }

  @Override
  public void createAttributes(RequestParameter reqparam) {

  }

  @Override
  public String getModuleId() {
    return "U1050510";
  }

  @Override
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.CouponHistoryListBean.0");
  }

  public List<CouponHistoryListDetail> getList() {
    return list;
  }

  public void setList(List<CouponHistoryListDetail> list) {
    this.list = list;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.CouponHistoryListBean.1"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.CouponHistoryListBean.0"),
        "/app/mypage/couponhistory_list/init"));
    return topicPath;
  }
}
