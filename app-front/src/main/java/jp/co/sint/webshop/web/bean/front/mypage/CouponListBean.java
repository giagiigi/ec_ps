package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050510:支払方法一覧のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CouponListBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  @Metadata(name = "電子クーポンリスト")
  private List<CouponIssueDetail> couponIssueList = new ArrayList<CouponIssueDetail>();

  /**
   * U1050510:支払方法一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CouponIssueDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String customerCouponId;

    /** shopCode */
    private String shopCode;

    /** クーポンルール番号 */
    private String couponIssueNo;

    /** クーポンルール番号 */
    private String couponPrice;

    private String useFlg;

    /** クーポンルール番号 */
    private String getCouponPrice;

    /** クーポン管理側名 */
    private String couponIssueName;

    /** クーポン発行開始日 */
    private String bonusCouponStartDate;

    /** クーポン発行終了日 */
    private String bonusCouponEndDate;

    /** クーポン使用開始日 */
    private String useCouponStartDate;

    /** クーポン使用終了日 */
    private String useCouponEndDate;

    private String useDate;

    private String orderNo;

    public String getShopCode() {
      return shopCode;
    }

    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    public String getCouponIssueNo() {
      return couponIssueNo;
    }

    public void setCouponIssueNo(String couponIssueNo) {
      this.couponIssueNo = couponIssueNo;
    }

    public String getBonusCouponEndDate() {
      return bonusCouponEndDate;
    }

    public void setBonusCouponEndDate(String bonusCouponEndDate) {
      this.bonusCouponEndDate = bonusCouponEndDate;
    }

    public String getBonusCouponStartDate() {
      return bonusCouponStartDate;
    }

    public void setBonusCouponStartDate(String bonusCouponStartDate) {
      this.bonusCouponStartDate = bonusCouponStartDate;
    }

    public String getCouponIssueName() {
      return couponIssueName;
    }

    public void setCouponIssueName(String couponIssueName) {
      this.couponIssueName = couponIssueName;
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

    public String getCouponPrice() {
      return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
      this.couponPrice = couponPrice;
    }

    public String getGetCouponPrice() {
      return getCouponPrice;
    }

    public void setGetCouponPrice(String getCouponPrice) {
      this.getCouponPrice = getCouponPrice;
    }

    public String getUseFlg() {
      return useFlg;
    }

    public void setUseFlg(String useFlg) {
      this.useFlg = useFlg;
    }

    public String getOrderNo() {
      return orderNo;
    }

    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    public String getUseDate() {
      return useDate;
    }

    public void setUseDate(String useDate) {
      this.useDate = useDate;
    }

    public String getCustomerCouponId() {
      return customerCouponId;
    }

    public void setCustomerCouponId(String customerCouponId) {
      this.customerCouponId = customerCouponId;
    }

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

  public List<CouponIssueDetail> getCouponIssueList() {
    return couponIssueList;
  }

  public void setCouponIssueList(List<CouponIssueDetail> couponIssueList) {
    this.couponIssueList = couponIssueList;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.shop.CouponListBean.0");
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

}
