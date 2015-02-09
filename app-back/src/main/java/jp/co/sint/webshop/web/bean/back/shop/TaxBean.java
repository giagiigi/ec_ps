package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Percentage;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050410:消費税マスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class TaxBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private TaxDetail taxRegister = new TaxDetail();

  private List<TaxDetail> taxList = new ArrayList<TaxDetail>();

  private boolean displayRegisterPartFlg;

  /**
   * U1050410:消費税マスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class TaxDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Metadata(name = "消費税番号")
    private String taxNo;

    @Required
    @Datetime
    @Metadata(name = "適用開始日")
    private String appliedStartDate;

    @Required
    @Digit
    @Length(3)
    @Percentage
    @Metadata(name = "消費税率")
    private String taxRate;

    private boolean displayDeleteButtonFlg;

    /**
     * displayDeleteButtonFlgを取得します。
     * 
     * @return displayDeleteButtonFlg
     */
    public boolean getDisplayDeleteButtonFlg() {
      return displayDeleteButtonFlg;
    }

    /**
     * displayDeleteButtonFlgを設定します。
     * 
     * @param displayDeleteButtonFlg
     *          displayDeleteButtonFlg
     */
    public void setDisplayDeleteButtonFlg(boolean displayDeleteButtonFlg) {
      this.displayDeleteButtonFlg = displayDeleteButtonFlg;
    }

    /**
     * appliedStartDateを取得します。
     * 
     * @return appliedStartDate
     */
    public String getAppliedStartDate() {
      return appliedStartDate;
    }

    /**
     * taxNoを取得します。
     * 
     * @return taxNo
     */
    public String getTaxNo() {
      return taxNo;
    }

    /**
     * taxRateを取得します。
     * 
     * @return taxRate
     */
    public String getTaxRate() {
      return taxRate;
    }

    /**
     * appliedStartDateを設定します。
     * 
     * @param appliedStartDate
     *          appliedStartDate
     */
    public void setAppliedStartDate(String appliedStartDate) {
      this.appliedStartDate = appliedStartDate;
    }

    /**
     * taxNoを設定します。
     * 
     * @param taxNo
     *          taxNo
     */
    public void setTaxNo(String taxNo) {
      this.taxNo = taxNo;
    }

    /**
     * taxRateを設定します。
     * 
     * @param taxRate
     *          taxRate
     */
    public void setTaxRate(String taxRate) {
      this.taxRate = taxRate;
    }

  }

  /**
   * taxListを取得します。
   * 
   * @return taxList
   */
  public List<TaxDetail> getTaxList() {
    return taxList;
  }

  /**
   * taxRegisterを取得します。
   * 
   * @return taxRegister
   */
  public TaxDetail getTaxRegister() {
    return taxRegister;
  }

  /**
   * taxListを設定します。
   * 
   * @param taxList
   *          taxList
   */
  public void setTaxList(List<TaxDetail> taxList) {
    this.taxList = taxList;
  }

  /**
   * taxRegisterを設定します。
   * 
   * @param taxRegister
   *          taxRegister
   */
  public void setTaxRegister(TaxDetail taxRegister) {
    this.taxRegister = taxRegister;
  }

  /**
   * displayRegisterPartFlgを取得します。
   * 
   * @return displayRegisterPartFlg
   */
  public boolean getDisplayRegisterPartFlg() {
    return displayRegisterPartFlg;
  }

  /**
   * displayRegisterPartFlgを設定します。
   * 
   * @param displayRegisterPartFlg
   *          displayRegisterPartFlg
   */
  public void setDisplayRegisterPartFlg(boolean displayRegisterPartFlg) {
    this.displayRegisterPartFlg = displayRegisterPartFlg;
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

    TaxDetail detail = new TaxDetail();

    detail.setAppliedStartDate(reqparam.getDateString("appliedStartDate"));
    detail.setTaxRate(reqparam.get("taxRate"));

    // 削除されるtaxNoを設定
    detail.setTaxNo(reqparam.get("deleteTaxNo"));

    setTaxRegister(detail);

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050410";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.TaxBean.0");
  }
}
