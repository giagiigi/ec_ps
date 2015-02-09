package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030710:ポイント履歴のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PointHistoryBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<PointHistoryDetail> list = new ArrayList<PointHistoryDetail>();

  private String lastName;

  private String firstName;

  private String restPoint;

  private String pointExpirationDate;

  @Length(16)
  @Digit
  @Metadata(name = "受注番号")
  private String orderNo;

  private PagerValue pagerValue;

  /**
   * U2030710:ポイント履歴のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PointHistoryDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopName;

    private String shopCode;

    private String issuedTypes;

    private String pointIssueDatetime;

    private String useDivision;

    private String acquisitionPoint;

    private String usePoint;

    private String orderNo;

    /**
     * acquisitionPointを取得します。
     * 
     * @return acquisitionPoint
     */
    public String getAcquisitionPoint() {
      return acquisitionPoint;
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
     * issuedTypesを取得します。
     * 
     * @return issuedTypes
     */
    public String getIssuedTypes() {
      return issuedTypes;
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
     * useDivisionを取得します。
     * 
     * @return useDivision
     */
    public String getUseDivision() {
      return useDivision;
    }

    /**
     * usePointを取得します。
     * 
     * @return usePoint
     */
    public String getUsePoint() {
      return usePoint;
    }

    /**
     * acquisitionPointを設定します。
     * 
     * @param acquisitionPoint
     *          acquisitionPoint
     */
    public void setAcquisitionPoint(String acquisitionPoint) {
      this.acquisitionPoint = acquisitionPoint;
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

    /**
     * issuedTypesを設定します。
     * 
     * @param issuedTypes
     *          issuedTypes
     */
    public void setIssuedTypes(String issuedTypes) {
      this.issuedTypes = issuedTypes;
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
     * useDivisionを設定します。
     * 
     * @param useDivision
     *          useDivision
     */
    public void setUseDivision(String useDivision) {
      this.useDivision = useDivision;
    }

    /**
     * usePointを設定します。
     * 
     * @param usePoint
     *          usePoint
     */
    public void setUsePoint(String usePoint) {
      this.usePoint = usePoint;
    }

  }

  /**
   * firstNameを取得します。
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * lastNameを取得します。
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * pointExpirationDateを取得します。
   * 
   * @return pointExpirationDate
   */
  public String getPointExpirationDate() {
    return pointExpirationDate;
  }

  /**
   * ポイント履歴詳細リストを取得します。
   * 
   * @return list
   */
  public List<PointHistoryDetail> getList() {
    return list;
  }

  /**
   * restPointを取得します。
   * 
   * @return restPoint
   */
  public String getRestPoint() {
    return restPoint;
  }

  /**
   * firstNameを設定します。
   * 
   * @param firstName
   *          firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * lastNameを設定します。
   * 
   * @param lastName
   *          lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * pointExpirationDateを設定します。
   * 
   * @param pointExpirationDate
   *          pointExpirationDate
   */
  public void setPointExpirationDate(String pointExpirationDate) {
    this.pointExpirationDate = pointExpirationDate;
  }

  /**
   * ポイント履歴詳細リストを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<PointHistoryDetail> list) {
    this.list = list;
  }

  /**
   * restPointを設定します。
   * 
   * @param restPoint
   *          restPoint
   */
  public void setRestPoint(String restPoint) {
    this.restPoint = restPoint;
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

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030710";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.PointHistoryBean.0");
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
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(
        Messages.getString("web.bean.front.mypage.PointHistoryBean.1"), "/app/mypage/mypage"));
    topicPath.add(new NameValue(
        Messages.getString("web.bean.front.mypage.PointHistoryBean.0"),
        "/app/mypage/point_history/init"));
    return topicPath;
  }

  /**
   * orderNoを返します。
   * 
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          設定する orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
}
