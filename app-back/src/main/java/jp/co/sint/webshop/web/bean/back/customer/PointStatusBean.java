package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030310:ポイント利用状況のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PointStatusBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @AlphaNum2
  @Length(16)
  @Metadata(name = "ショップコード", order = 1)
  private String searchShopCode;

  private List<CodeAttribute> searchShopList;

  private String searchIssueType;

  private String searchPointIssueStatus;

  @Datetime
  @Metadata(name = "ポイント発行期間(From)")
  private String searchPointIssueStartDate;

  @Datetime
  @Metadata(name = "ポイント発行期間(To)")
  private String searchPointIssueEndDate;

  private String searchSummaryCondition;

  private String displayTarget;

  private PagerValue pagerValue;

  private List<PointStatusDetail> allList = new ArrayList<PointStatusDetail>();

  private List<PointStatusDetail> shopList = new ArrayList<PointStatusDetail>();

  private boolean csvExportButtonDisplayFlg;

  private Boolean usablePointSystem;

  /** 集計対象が全明細 */
  public static final String SUMMARY_TYPE_ALL = "0";

  /** 集計対象がショップ別 */
  public static final String SUMMARY_TYPE_SHOP = "1";

  /**
   * U1030310:ポイント利用状況のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PointStatusDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String shopName;

    private String customerCode;

    private String customerLastName;

    private String customerFirstName;

    private String issueTypeName;

    private String issuedStatusName;

    private String issuedPoint;

    private String orderNo;

    private String orderDate;

    private String paymentDate;

    private String ineffectivePoint;

    private String temporaryPoint;

    private String effectivePoint;

    private String pointIssueDatetime;

    /**
     * customerCodeを取得します。
     * 
     * @return customerCode
     */
    public String getCustomerCode() {
      return customerCode;
    }

    /**
     * customerFirstNameを取得します。
     * 
     * @return customerFirstName
     */
    public String getCustomerFirstName() {
      return customerFirstName;
    }

    /**
     * customerLastNameを取得します。
     * 
     * @return customerLastName
     */
    public String getCustomerLastName() {
      return customerLastName;
    }

    /**
     * effectivePointを取得します。
     * 
     * @return effectivePoint
     */
    public String getEffectivePoint() {
      return effectivePoint;
    }

    /**
     * ineffectivePointを取得します。
     * 
     * @return ineffectivePoint
     */
    public String getIneffectivePoint() {
      return ineffectivePoint;
    }

    /**
     * issuedPointを取得します。
     * 
     * @return issuedPoint
     */
    public String getIssuedPoint() {
      return issuedPoint;
    }

    /**
     * orderDateを取得します。
     * 
     * @return orderDate
     */
    public String getOrderDate() {
      return orderDate;
    }

    /**
     * orderNoを取得します。
     * 
     * @return orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    /**
     * paymentDateを取得します。
     * 
     * @return paymentDate
     */
    public String getPaymentDate() {
      return paymentDate;
    }

    /**
     * shopCodeを取得します。
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * shopNameを取得します。
     * 
     * @return shopName
     */
    public String getShopName() {
      return shopName;
    }

    /**
     * temporaryPointを取得します。
     * 
     * @return temporaryPoint
     */
    public String getTemporaryPoint() {
      return temporaryPoint;
    }

    /**
     * customerCodeを設定します。
     * 
     * @param customerCode
     *          customerCode
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }

    /**
     * customerFirstNameを設定します。
     * 
     * @param customerFirstName
     *          customerFirstName
     */
    public void setCustomerFirstName(String customerFirstName) {
      this.customerFirstName = customerFirstName;
    }

    /**
     * customerLastNameを設定します。
     * 
     * @param customerLastName
     *          customerLastName
     */
    public void setCustomerLastName(String customerLastName) {
      this.customerLastName = customerLastName;
    }

    /**
     * effectivePointを設定します。
     * 
     * @param effectivePoint
     *          effectivePoint
     */
    public void setEffectivePoint(String effectivePoint) {
      this.effectivePoint = effectivePoint;
    }

    /**
     * ineffectivePointを設定します。
     * 
     * @param ineffectivePoint
     *          ineffectivePoint
     */
    public void setIneffectivePoint(String ineffectivePoint) {
      this.ineffectivePoint = ineffectivePoint;
    }

    /**
     * issuedPointを設定します。
     * 
     * @param issuedPoint
     *          issuedPoint
     */
    public void setIssuedPoint(String issuedPoint) {
      this.issuedPoint = issuedPoint;
    }

    /**
     * orderDateを設定します。
     * 
     * @param orderDate
     *          orderDate
     */
    public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
    }

    /**
     * orderNoを設定します。
     * 
     * @param orderNo
     *          orderNo
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    /**
     * paymentDateを設定します。
     * 
     * @param paymentDate
     *          paymentDate
     */
    public void setPaymentDate(String paymentDate) {
      this.paymentDate = paymentDate;
    }

    /**
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * shopNameを設定します。
     * 
     * @param shopName
     *          shopName
     */
    public void setShopName(String shopName) {
      this.shopName = shopName;
    }

    /**
     * temporaryPointを設定します。
     * 
     * @param temporaryPoint
     *          temporaryPoint
     */
    public void setTemporaryPoint(String temporaryPoint) {
      this.temporaryPoint = temporaryPoint;
    }

    /**
     * issueTypeNameを取得します。
     * 
     * @return issueTypeName
     */
    public String getIssueTypeName() {
      return issueTypeName;
    }

    /**
     * issueTypeNameを設定します。
     * 
     * @param issueTypeName
     *          issueTypeName
     */
    public void setIssueTypeName(String issueTypeName) {
      this.issueTypeName = issueTypeName;
    }

    /**
     * issuedStatusNameを取得します。
     * 
     * @return issuedStatusName
     */
    public String getIssuedStatusName() {
      return issuedStatusName;
    }

    /**
     * issuedStatusNameを設定します。
     * 
     * @param issuedStatusName
     *          issuedStatusName
     */
    public void setIssuedStatusName(String issuedStatusName) {
      this.issuedStatusName = issuedStatusName;
    }

    /**
     * pointIssueDatetimeを取得します。
     * 
     * @return pointIssueDatetime
     */
    public String getPointIssueDatetime() {
      return pointIssueDatetime;
    }

    /**
     * pointIssueDatetimeを設定します。
     * 
     * @param pointIssueDatetime
     *          pointIssueDatetime
     */
    public void setPointIssueDatetime(String pointIssueDatetime) {
      this.pointIssueDatetime = pointIssueDatetime;
    }

  }

  /**
   * allListを取得します。
   * 
   * @return allList
   */
  public List<PointStatusDetail> getAllList() {
    return allList;
  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<PointStatusDetail> getShopList() {
    return shopList;
  }

  /**
   * allListを設定します。
   * 
   * @param allList
   *          allList
   */
  public void setAllList(List<PointStatusDetail> allList) {
    this.allList = allList;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<PointStatusDetail> shopList) {
    this.shopList = shopList;
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
    this.setSearchShopCode(reqparam.get("searchShopCode"));
    this.setSearchIssueType(reqparam.get("searchIssueType"));
    this.setSearchPointIssueStatus(reqparam.get("searchPointIssueStatus"));
    this.setSearchPointIssueStartDate(reqparam.getDateString("searchPointIssueStartDate"));
    this.setSearchPointIssueEndDate(reqparam.getDateString("searchPointIssueEndDate"));
    this.setSearchSummaryCondition(reqparam.get("searchSummaryCondition"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1030310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.PointStatusBean.0");
  }

  /**
   * searchIssueTypeを取得します。
   * 
   * @return searchIssueType
   */
  public String getSearchIssueType() {
    return searchIssueType;
  }

  /**
   * searchIssueTypeを設定します。
   * 
   * @param searchIssueType
   *          searchIssueType
   */
  public void setSearchIssueType(String searchIssueType) {
    this.searchIssueType = searchIssueType;
  }

  /**
   * searchPointIssueEndDateを取得します。
   * 
   * @return searchPointIssueEndDate
   */
  public String getSearchPointIssueEndDate() {
    return searchPointIssueEndDate;
  }

  /**
   * searchPointIssueEndDateを設定します。
   * 
   * @param searchPointIssueEndDate
   *          searchPointIssueEndDate
   */
  public void setSearchPointIssueEndDate(String searchPointIssueEndDate) {
    this.searchPointIssueEndDate = searchPointIssueEndDate;
  }

  /**
   * searchPointIssueStartDateを取得します。
   * 
   * @return searchPointIssueStartDate
   */
  public String getSearchPointIssueStartDate() {
    return searchPointIssueStartDate;
  }

  /**
   * searchPointIssueStartDateを設定します。
   * 
   * @param searchPointIssueStartDate
   *          searchPointIssueStartDate
   */
  public void setSearchPointIssueStartDate(String searchPointIssueStartDate) {
    this.searchPointIssueStartDate = searchPointIssueStartDate;
  }

  /**
   * searchPointIssueStatusを取得します。
   * 
   * @return searchPointIssueStatus
   */
  public String getSearchPointIssueStatus() {
    return searchPointIssueStatus;
  }

  /**
   * searchPointIssueStatusを設定します。
   * 
   * @param searchPointIssueStatus
   *          searchPointIssueStatus
   */
  public void setSearchPointIssueStatus(String searchPointIssueStatus) {
    this.searchPointIssueStatus = searchPointIssueStatus;
  }

  /**
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * searchSummaryConditionを取得します。
   * 
   * @return searchSummaryCondition
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
   * csvExportButtonDisplayFlgを取得します。
   * 
   * @return csvExportButtonDisplayFlg
   */
  public boolean isCsvExportButtonDisplayFlg() {
    return csvExportButtonDisplayFlg;
  }

  /**
   * csvExportButtonDisplayFlgを設定します。
   * 
   * @param csvExportButtonDisplayFlg
   *          csvExportButtonDisplayFlg
   */
  public void setCsvExportButtonDisplayFlg(boolean csvExportButtonDisplayFlg) {
    this.csvExportButtonDisplayFlg = csvExportButtonDisplayFlg;
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
   * displayTargetを取得します。
   * 
   * @return displayTarget
   */
  public String getDisplayTarget() {
    return displayTarget;
  }

  /**
   * displayTargetを設定します。
   * 
   * @param displayTarget
   *          displayTarget
   */
  public void setDisplayTarget(String displayTarget) {
    this.displayTarget = displayTarget;
  }

  /**
   * searchShopListを取得します。
   * 
   * @return searchShopList
   */
  public List<CodeAttribute> getSearchShopList() {
    return searchShopList;
  }

  /**
   * searchShopListを設定します。
   * 
   * @param searchShopList
   *          searchShopList
   */
  public void setSearchShopList(List<CodeAttribute> searchShopList) {
    this.searchShopList = searchShopList;
  }

  /**
   * usablePointSystemを取得します。
   * 
   * @return usablePointSystem
   */
  public Boolean getUsablePointSystem() {
    return usablePointSystem;
  }

  /**
   * usablePointSystemを設定します。
   * 
   * @param usablePointSystem
   *          usablePointSystem
   */
  public void setUsablePointSystem(Boolean usablePointSystem) {
    this.usablePointSystem = usablePointSystem;
  }

}
