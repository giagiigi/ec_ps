package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050510:支払方法一覧のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodListBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Metadata(name = "支払方法リスト")
  private List<PaymentMethodDetail> paymentMethodList = new ArrayList<PaymentMethodDetail>();

  @Required
  @Metadata(name = "登録用手数料")
  private PaymentCommissionDetail commissionRegister = new PaymentCommissionDetail();

  @Metadata(name = "手数料リスト")
  private List<PaymentCommissionDetail> commissionList = new ArrayList<PaymentCommissionDetail>();

  private String shopCode;

  private String commissionPaymentMethodName;

  private String commissionTaxTypeName;

  @Required
  @Metadata(name = "手数料支払方法コード")
  private String paymentMethodNo;

  private String deleteShopCode;

  private String deletePaymentMethodNo;

  private boolean deleteButtonFlg;

  private boolean registerFlg;

  private boolean referFlg;

  private String paymentMethodDetailDisplay = WebConstantCode.DISPLAY_NONE;

  private boolean pointUseFlg;

  /**
   * U1050510:支払方法一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PaymentMethodDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Metadata(name = "ショップコード")
    private String shopCode;

    @Metadata(name = "支払方法コード")
    private String paymentMethodNo;

    @Metadata(name = "支払方法名")
    private String paymentMethodName;

    @Metadata(name = "支払区分")
    private String paymentMethodType;

    @Metadata(name = "後先払い区分")
    private String advanceLaterFlg;

    @Metadata(name = "支払手数料区分")
    private String paymentCommissionTaxType;

    /** 手数料ボタンを表示するかどうか */
    private boolean commissionDisplayFlg;

    /** 手数料が登録されているかどうか */
    private boolean commissionRegistered;

    /** 削除ボタンを表示するかどうか */
    private boolean deleteButtonFlg;

    
    /**
     * deleteButtonFlgを取得します。
     *
     * @return deleteButtonFlg
     */
    public boolean isDeleteButtonFlg() {
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

    /**
     * commissionDisplayFlgを取得します。
     * 
     * @return commissionDisplayFlg
     */
    public boolean isCommissionDisplayFlg() {
      return commissionDisplayFlg;
    }

    /**
     * commissionDisplayFlgを設定します。
     * 
     * @param commissionDisplayFlg
     *          commissionDisplayFlg
     */
    public void setCommissionDisplayFlg(boolean commissionDisplayFlg) {
      this.commissionDisplayFlg = commissionDisplayFlg;
    }

    /**
     * advanceLaterFlgを取得します。
     * 
     * @return advanceLaterFlg
     */
    public String getAdvanceLaterFlg() {
      return advanceLaterFlg;
    }

    /**
     * advanceLaterFlgを設定します。
     * 
     * @param advanceLaterFlg
     *          advanceLaterFlg
     */
    public void setAdvanceLaterFlg(String advanceLaterFlg) {
      this.advanceLaterFlg = advanceLaterFlg;
    }

    /**
     * paymentMethodNoを取得します。
     * 
     * @return paymentMethodNo
     */
    public String getPaymentMethodNo() {
      return paymentMethodNo;
    }

    /**
     * paymentMethodNameを取得します。
     * 
     * @return paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
    }

    /**
     * paymentMethodTypeを取得します。
     * 
     * @return paymentMethodType
     */
    public String getPaymentMethodType() {
      return paymentMethodType;
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
     * paymentMethodNoを設定します。
     * 
     * @param paymentMethodNo
     *          paymentMethodNo
     */
    public void setPaymentMethodNo(String paymentMethodNo) {
      this.paymentMethodNo = paymentMethodNo;
    }

    /**
     * paymentMethodNameを設定します。
     * 
     * @param paymentMethodName
     *          paymentMethodName
     */
    public void setPaymentMethodName(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
    }

    /**
     * paymentMethodTypeを設定します。
     * 
     * @param paymentMethodType
     *          paymentMethodType
     */
    public void setPaymentMethodType(String paymentMethodType) {
      this.paymentMethodType = paymentMethodType;
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
     * paymentCommissionTaxTypeを取得します。
     * 
     * @return paymentCommissionTaxType
     */
    public String getPaymentCommissionTaxType() {
      return paymentCommissionTaxType;
    }

    /**
     * paymentCommissionTaxTypeを設定します。
     * 
     * @param paymentCommissionTaxType
     *          paymentCommissionTaxType
     */
    public void setPaymentCommissionTaxType(String paymentCommissionTaxType) {
      this.paymentCommissionTaxType = paymentCommissionTaxType;
    }

    /**
     * commissionRegisteredを取得します。
     * 
     * @return commissionRegistered
     */

    public boolean isCommissionRegistered() {
      return commissionRegistered;
    }

    /**
     * commissionRegisteredを設定します。
     * 
     * @param commissionRegistered
     *          commissionRegistered
     */
    public void setCommissionRegistered(boolean commissionRegistered) {
      this.commissionRegistered = commissionRegistered;
    }

  }

  /**
   * U1050510:支払方法一覧のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PaymentCommissionDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Metadata(name = "データ行")
    private String ormRowid;

    @Metadata(name = "購入金額(From)")
    private String priceFrom;

    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "購入金額(To)")
    private String priceTo;

    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "手数料")
    private String paymentCommission;

    private Date updatedDatetime;

    /**
     * ormRowidを取得します。
     * 
     * @return ormRowid
     */
    public String getOrmRowid() {
      return ormRowid;
    }

    /**
     * paymentCommissionを取得します。
     * 
     * @return paymentCommission
     */
    public String getPaymentCommission() {
      return paymentCommission;
    }

    /**
     * priceFromを取得します。
     * 
     * @return priceFrom
     */
    public String getPriceFrom() {
      return priceFrom;
    }

    /**
     * priceToを取得します。
     * 
     * @return priceTo
     */
    public String getPriceTo() {
      return priceTo;
    }

    /**
     * ormRowidを設定します。
     * 
     * @param ormRowid
     *          ormRowid
     */
    public void setOrmRowid(String ormRowid) {
      this.ormRowid = ormRowid;
    }

    /**
     * paymentCommissionを設定します。
     * 
     * @param paymentCommission
     *          paymentCommission
     */
    public void setPaymentCommission(String paymentCommission) {
      this.paymentCommission = paymentCommission;
    }

    /**
     * priceFromを設定します。
     * 
     * @param priceFrom
     *          priceFrom
     */
    public void setPriceFrom(String priceFrom) {
      this.priceFrom = priceFrom;
    }

    /**
     * priceToを設定します。
     * 
     * @param priceTo
     *          priceTo
     */
    public void setPriceTo(String priceTo) {
      this.priceTo = priceTo;
    }

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

  }

  /**
   * commissionListを取得します。
   * 
   * @return commissionList
   */
  public List<PaymentCommissionDetail> getCommissionList() {
    return commissionList;
  }

  /**
   * commissionRegisterを取得します。
   * 
   * @return commissionRegister
   */
  public PaymentCommissionDetail getCommissionRegister() {
    return commissionRegister;
  }

  /**
   * paymentMethodNoを取得します。
   * 
   * @return paymentMethodNo
   */
  public String getPaymentMethodNo() {
    return paymentMethodNo;
  }

  /**
   * paymentMethodListを取得します。
   * 
   * @return paymentMethodList
   */
  public List<PaymentMethodDetail> getPaymentMethodList() {
    return paymentMethodList;
  }

  /**
   * commissionListを設定します。
   * 
   * @param commissionList
   *          commissionList
   */
  public void setCommissionList(List<PaymentCommissionDetail> commissionList) {
    this.commissionList = commissionList;
  }

  /**
   * commissionRegisterを設定します。
   * 
   * @param commissionRegister
   *          commissionRegister
   */
  public void setCommissionRegister(PaymentCommissionDetail commissionRegister) {
    this.commissionRegister = commissionRegister;
  }

  /**
   * paymentMethodNoを設定します。
   * 
   * @param paymentMethodNo
   *          paymentMethodNo
   */
  public void setPaymentMethodNo(String paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  /**
   * paymentMethodListを設定します。
   * 
   * @param paymentMethodList
   *          paymentMethodList
   */
  public void setPaymentMethodList(List<PaymentMethodDetail> paymentMethodList) {
    this.paymentMethodList = paymentMethodList;
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
  public String getPaymentMethodDetailDisplay() {
    return paymentMethodDetailDisplay;
  }

  /**
   * paymentMethodDetailDisplayを設定します。
   * 
   * @param paymentMethodDetailDisplay
   *          paymentMethodDetailDisplay
   */
  public void setPaymentMethodDetailDisplay(String paymentMethodDetailDisplay) {
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
    setDeletePaymentMethodNo(reqparam.get("deletePaymentmethodNo"));

    // 現在選択されているショップ、支払方法コードを取得
    setShopCode(reqparam.get("shopCode"));
    setPaymentMethodNo(reqparam.get("paymentmethodNo"));

    // 手数料リストを行IDごとに連想配列で取得
    for (PaymentCommissionDetail commissionDetail : commissionList) {
      Map<String, String> map = reqparam.getListDataWithKey(commissionDetail.getOrmRowid(), "ormRowid", "updatePaymentCommission");
      commissionDetail.setPaymentCommission(map.get("updatePaymentCommission"));
    }

    // 手数料登録部の値を取得
    PaymentCommissionDetail registerCommission = new PaymentCommissionDetail();
    registerCommission.setPriceTo(reqparam.get("registerPriceTo"));
    registerCommission.setPaymentCommission(reqparam.get("registerPaymentCommission"));
    setCommissionRegister(registerCommission);
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
    return Messages.getString("web.bean.back.shop.PaymentmethodListBean.0");
  }

  /**
   * deletePaymentMethodNoを取得します。
   * 
   * @return deletePaymentMethodNo
   */
  public String getDeletePaymentMethodNo() {
    return deletePaymentMethodNo;
  }

  /**
   * deletePaymentMethodNoを設定します。
   * 
   * @param deletePaymentMethodNo
   *          deletePaymentMethodNo
   */
  public void setDeletePaymentMethodNo(String deletePaymentMethodNo) {
    this.deletePaymentMethodNo = deletePaymentMethodNo;
  }

  /**
   * deleteShopCodeを取得します。
   * 
   * @return deleteShopCode
   */
  public String getDeleteShopCode() {
    return deleteShopCode;
  }

  /**
   * deleteShopCodeを設定します。
   * 
   * @param deleteShopCode
   *          deleteShopCode
   */
  public void setDeleteShopCode(String deleteShopCode) {
    this.deleteShopCode = deleteShopCode;
  }

  /**
   * commissionPaymentMethodNameを取得します。
   * 
   * @return commissionPaymentMethodName
   */
  public String getCommissionPaymentMethodName() {
    return commissionPaymentMethodName;
  }

  /**
   * commissionPaymentMethodNameを設定します。
   * 
   * @param commissionPaymentMethodName
   *          commissionPaymentMethodName
   */
  public void setCommissionPaymentMethodName(String commissionPaymentMethodName) {
    this.commissionPaymentMethodName = commissionPaymentMethodName;
  }

  /**
   * commissionTaxTypeNameを取得します。
   * 
   * @return commissionTaxTypeName
   */
  public String getCommissionTaxTypeName() {
    return commissionTaxTypeName;
  }

  /**
   * commissionTaxTypeNameを設定します。
   * 
   * @param commissionTaxTypeName
   *          commissionTaxTypeName
   */
  public void setCommissionTaxTypeName(String commissionTaxTypeName) {
    this.commissionTaxTypeName = commissionTaxTypeName;
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

  /**
   * pointUseFlgを取得します。
   * 
   * @return pointUseFlg
   */
  public boolean isPointUseFlg() {
    return pointUseFlg;
  }

  /**
   * pointUseFlgを設定します。
   * 
   * @param pointUseFlg
   *          設定する pointUseFlg
   */
  public void setPointUseFlg(boolean pointUseFlg) {
    this.pointUseFlg = pointUseFlg;
  }

}
