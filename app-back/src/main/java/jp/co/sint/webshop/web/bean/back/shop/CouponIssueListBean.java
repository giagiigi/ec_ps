package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050510:支払方法一覧のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CouponIssueListBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Metadata(name = "電子クーポンリスト")
  private List<CouponIssueDetail> couponIssueList = new ArrayList<CouponIssueDetail>();

  private String shopCode;

  @Required
  @Metadata(name = "電子クーポンコード")
  private String couponIssueNo;

  private String deleteCouponIssueNo;

  private String deleteShopCode;

  private boolean deleteButtonFlg;

  private boolean registerFlg;

  private boolean referFlg;

  private String paymentMethodDetailDisplay = WebConstantCode.DISPLAY_NONE;

  /**
   * U1050510:支払方法一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CouponIssueDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** shopCode */
    @Metadata(name = "店铺编号", order = 1)
    private String shopCode;

    /** クーポンルール番号 */
    private String couponIssueNo;

    /** クーポンルール番号 */
    private String couponPrice;

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

    private boolean registerButtonFlg;

    /** 削除ボタンを表示するかどうか */
    private boolean deleteButtonFlg;

    private boolean analysisButtonFlg;

    /**
     * deleteButtonFlgを取得します。
     * 
     * @return deleteButtonFlg
     */
    public boolean getDeleteButtonFlg() {
      return deleteButtonFlg;
    }

    /**
     * deleteButtonFlgを設定します。
     * 
     * @param deleteButtonFlg
     *          deleteButtonFlg
     */
    public void setDeleteButtonFlg(boolean deleteButtonFlg) {
      this.deleteButtonFlg = deleteButtonFlg;
    }

    public boolean isRegisterButtonFlg() {
      return registerButtonFlg;
    }

    public void setRegisterButtonFlg(boolean registerButtonFlg) {
      this.registerButtonFlg = registerButtonFlg;
    }

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

    public boolean getAnalysisButtonFlg() {
      return analysisButtonFlg;
    }

    public void setAnalysisButtonFlg(boolean analysisButtonFlg) {
      this.analysisButtonFlg = analysisButtonFlg;
    }

  }

  /**
   * deleteButtonFlgを取得します。
   * 
   * @return deleteButtonFlg
   */
  public boolean getDeleteButtonFlg() {
    return deleteButtonFlg;
  }

  /**
   * deleteButtonFlgを設定します。
   * 
   * @param deleteButtonFlg
   *          設定する deleteButtonFlg
   */
  public void setDeleteButtonFlg(boolean deleteButtonFlg) {
    this.deleteButtonFlg = deleteButtonFlg;
  }

  /**
   * registerFlgを取得します。
   * 
   * @return registerFlg
   */
  public boolean isRegisterFlg() {
    return registerFlg;
  }

  /**
   * registerFlgを設定します。
   * 
   * @param registerFlg
   *          registerFlg
   */
  public void setRegisterFlg(boolean registerFlg) {
    this.registerFlg = registerFlg;
  }

  /**
   * paymentMethodDetailDisplayを取得します。
   * 
   * @return paymentMethodDetailDisplay
   */
  public String getCouponIssueDetailDisplay() {
    return paymentMethodDetailDisplay;
  }

  /**
   * paymentMethodDetailDisplayを設定します。
   * 
   * @param paymentMethodDetailDisplay
   *          paymentMethodDetailDisplay
   */
  public void setCouponIssueDetailDisplay(String paymentMethodDetailDisplay) {
    this.paymentMethodDetailDisplay = paymentMethodDetailDisplay;
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
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
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

    // 削除用のリクエストを取得
    setDeleteShopCode(reqparam.get("deleteShopCode"));
    setDeleteCouponIssueNo(reqparam.get("deleteCouponIssueNo"));
    // 現在選択されているショップ、支払方法コードを取得

    setShopCode(reqparam.get("shopCode"));
    setCouponIssueNo(reqparam.get("couponIssueNo"));
  }

  public List<CouponIssueDetail> getCouponIssueList() {
    return couponIssueList;
  }

  public void setCouponIssueList(List<CouponIssueDetail> couponIssueList) {
    this.couponIssueList = couponIssueList;
  }

  public String getCouponIssueNo() {
    return couponIssueNo;
  }

  public void setCouponIssueNo(String couponIssueNo) {
    this.couponIssueNo = couponIssueNo;
  }

  public String getDeleteCouponIssueNo() {
    return deleteCouponIssueNo;
  }

  public void setDeleteCouponIssueNo(String deleteCouponIssueNo) {
    this.deleteCouponIssueNo = deleteCouponIssueNo;
  }

  public String getDeleteShopCode() {
    return deleteShopCode;
  }

  public void setDeleteShopCode(String deleteShopCode) {
    this.deleteShopCode = deleteShopCode;
  }

  public String getPaymentMethodDetailDisplay() {
    return paymentMethodDetailDisplay;
  }

  public void setPaymentMethodDetailDisplay(String paymentMethodDetailDisplay) {
    this.paymentMethodDetailDisplay = paymentMethodDetailDisplay;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1051050";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.CouponIssueListBean.0");
  }

  /**
   * referFlgを取得します。
   * 
   * @return referFlg
   */
  public boolean isReferFlg() {
    return referFlg;
  }

  /**
   * referFlgを設定します。
   * 
   * @param referFlg
   *          referFlg
   */
  public void setReferFlg(boolean referFlg) {
    this.referFlg = referFlg;
  }

}
