package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030999:商品券使用状况Bean
 * 
 * @author swj
 */
public class CouponStatusBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Datetime
  @Metadata(name = "优惠券发行日(From)")
  private String searchIssueFromDate;

  @Datetime
  @Metadata(name = "优惠券发行日(To)")
  private String searchIssueToDate;

  private String searchCouponStatus;

  private String searchSummaryCondition;

  private boolean displayTarget;

  private PagerValue pagerValue;

  private List<CouponStatusDetail> detailList = new ArrayList<CouponStatusDetail>();

  private List<CouponStatusDetail> totalList = new ArrayList<CouponStatusDetail>();

  private boolean csvExportButtonDisplayFlg;
  
  /** 集計対象が全明細 */
  public static final String SUMMARY_TYPE_DETAIL = "0";

  /** 集計対象がショップ別 */
  public static final String SUMMARY_TYPE_TOTAL = "1";

  public static class CouponStatusDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String customerCouponId;

    private String customerCode;

    private String couponIssueNo;

    private String useFlg;

    private String couponPrice;

    private String issueDate;

    private String useCouponStartDate;

    private String useCouponEndDate;

    private String couponName;

    private String orderNo;

    private String useDate;

    private String customerName;

    public String getUseDate() {
      return useDate;
    }

    public void setUseDate(String useDate) {
      this.useDate = useDate;
    }

    public String getCouponIssueNo() {
      return couponIssueNo;
    }

    public void setCouponIssueNo(String couponIssueNo) {
      this.couponIssueNo = couponIssueNo;
    }

    public String getCouponName() {
      return couponName;
    }

    public void setCouponName(String couponName) {
      this.couponName = couponName;
    }

    public String getCouponPrice() {
      return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
      this.couponPrice = couponPrice;
    }

    public String getCustomerCode() {
      return customerCode;
    }

    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }

    public String getCustomerCouponId() {
      return customerCouponId;
    }

    public void setCustomerCouponId(String customerCouponId) {
      this.customerCouponId = customerCouponId;
    }

    public String getIssueDate() {
      return issueDate;
    }

    public void setIssueDate(String issueDate) {
      this.issueDate = issueDate;
    }

    public String getOrderNo() {
      return orderNo;
    }

    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    public String getUseCouponEndDate() {
      return useCouponEndDate;
    }

    public void setUseCouponEndDate(String useCouponEndDate) {
      this.useCouponEndDate = useCouponEndDate;
    }

    public String getUseCouponStartDate() {
      return useCouponStartDate;
    }

    public void setUseCouponStartDate(String useCouponStartDate) {
      this.useCouponStartDate = useCouponStartDate;
    }

    public String getUseFlg() {
      return useFlg;
    }

    public void setUseFlg(String useFlg) {
      this.useFlg = useFlg;
    }

    public String getCustomerName() {
      return customerName;
    }

    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

  }

  /**
   * @return the detailList
   */
  public List<CouponStatusDetail> getDetailList() {
    return detailList;
  }

  /**
   * @param detailList
   *          the detailList to set
   */
  public void setDetailList(List<CouponStatusDetail> detailList) {
    this.detailList = detailList;
  }

  /**
   * @return the searchIssueFromDate
   */
  public String getSearchIssueFromDate() {
    return searchIssueFromDate;
  }

  /**
   * @param searchIssueFromDate
   *          the searchIssueFromDate to set
   */
  public void setSearchIssueFromDate(String searchIssueFromDate) {
    this.searchIssueFromDate = searchIssueFromDate;
  }

  /**
   * @return the searchIssueToDate
   */
  public String getSearchIssueToDate() {
    return searchIssueToDate;
  }

  /**
   * @param searchIssueToDate
   *          the searchIssueToDate to set
   */
  public void setSearchIssueToDate(String searchIssueToDate) {
    this.searchIssueToDate = searchIssueToDate;
  }

  public String getSearchCouponStatus() {
    return searchCouponStatus;
  }

  public void setSearchCouponStatus(String searchCouponStatus) {
    this.searchCouponStatus = searchCouponStatus;
  }

  /**
   * @return the totalList
   */
  public List<CouponStatusDetail> getTotalList() {
    return totalList;
  }

  /**
   * @param totalList
   *          the totalList to set
   */
  public void setTotalList(List<CouponStatusDetail> totalList) {
    this.totalList = totalList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    setSearchIssueFromDate(reqparam.getDateString("searchIssueFromDate"));
    setSearchIssueToDate(reqparam.getDateString("searchIssueToDate"));
    setSearchCouponStatus(reqparam.get("searchCouponStatus"));
    setSearchSummaryCondition(reqparam.get("searchSummaryCondition"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1030999";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.CouponStatusBean.0");
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
   * displayTargetを取得します。
   * 
   * @return displayTarget displayTarget
   */
  public boolean isDisplayTarget() {
    return displayTarget;
  }

  /**
   * displayTargetを設定します。
   * 
   * @param displayTarget
   *          displayTarget
   */
  public void setDisplayTarget(boolean displayTarget) {
    this.displayTarget = displayTarget;
  }

  /**
   * searchSummaryConditionを取得します。
   * 
   * @return searchSummaryCondition searchSummaryCondition
   */
  public String getSearchSummaryCondition() {
    return searchSummaryCondition;
  }

  /**
   * searchSummaryConditionを設定します。
   * 
   * @param searchSummaryCondition
   *          searchSummaryCondition
   */
  public void setSearchSummaryCondition(String searchSummaryCondition) {
    this.searchSummaryCondition = searchSummaryCondition;
  }

  /**
   * csvHeaderTypeを取得します。
   * 
   * @return csvHeaderType
   */
  public List<CodeAttribute> getCsvHeaderType() {
    List<CodeAttribute> csvHeaderType = new ArrayList<CodeAttribute>();
    csvHeaderType.add(new NameValue(Messages.getString("web.bean.back.catalog.StockIOBean.1"), WebConstantCode.VALUE_TRUE));
    csvHeaderType.add(new NameValue(Messages.getString("web.bean.back.catalog.StockIOBean.2"), WebConstantCode.VALUE_FALSE));

    return csvHeaderType;
  }

  
  public boolean isCsvExportButtonDisplayFlg() {
    return csvExportButtonDisplayFlg;
  }

  
  public void setCsvExportButtonDisplayFlg(boolean csvExportButtonDisplayFlg) {
    this.csvExportButtonDisplayFlg = csvExportButtonDisplayFlg;
  }
}
