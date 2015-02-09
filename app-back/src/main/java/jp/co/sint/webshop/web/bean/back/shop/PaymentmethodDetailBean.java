package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.BankKana;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.JbaAccountNo;
import jp.co.sint.webshop.data.attribute.JbaBankCode;
import jp.co.sint.webshop.data.attribute.JbaBranchCode;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Pattern;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050520:支払方法詳細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodDetailBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Length(25)
  @Metadata(name = "支払方法名", order = 1)
  private String paymentMethodName;

  @Metadata(name = "登録更新モード")
  private boolean updateModeFlg;

  @Required
  @Metadata(name = "支払区分", order = 2)
  private String paymentType;

  @Length(11)
  @Currency
  @Precision(precision = 10, scale = 2)
  @Range(min = 0, max = 9999999)
  @Metadata(name = "手数料", order = 3)
  private String paymentCommission;

  @Length(11)
  @Currency
  @Precision(precision = 10, scale = 2)
  @Range(min = 0, max = 9999999)
  @Metadata(name = "订单金额临界值")
  private String orderPriceThreshold;

  private List<NameValue> paymentTypeList = new ArrayList<NameValue>();

  @Required
  @Metadata(name = "表示区分", order = 4)
  private String paymentMethodDisplayType;

  @Required
  @Metadata(name = "消費税区分", order = 5)
  private String paymentCommissionTaxType;

  private TaxType[] taxType = TaxType.values();

  @Digit
  @Length(3)
  @Range(min = 0, max = 999)
  @Metadata(name = "消費税率", order = 6)
  private String paymentCommissionTaxRate;

  @Required
  @Metadata(name = "後先区分", order = 7)
  private String advanceLaterType;

  @Metadata(name = "支払方法コード")
  private String paymentMethodNo;

  @Metadata(name = "口座種類リスト")
  private List<NameValue> accountTypeList = new ArrayList<NameValue>();

  private Date updateDate;

  private boolean updateFlg;

  private String registerModeDisplay;

  private boolean bankMoveButtonFlg;

  private BankingInfo bankingInfo;

  private OtherInfo otherInfo;

  private BankOtherInfo bankOtherInfo;

  private PostOtherInfo postOtherInfo;

  private PostalInfo postalInfo;

  /**
   * U1050520:支払方法詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class BankingInfo implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "手数料")
    private String paymentCommission;

    @Required
    @Length(40)
    @Metadata(name = "金融機関名", order = 1)
    private String bankName;

    @Required
    @Length(40)
    @Metadata(name = "支店名", order = 2)
    private String bankBranchName;

    @Required
    @BankKana
    @Length(40)
    @Metadata(name = "金融機関名カナ", order = 3)
    private String bankKana;

    @Required
    @BankKana
    @Length(40)
    @Metadata(name = "支店名カナ", order = 4)
    private String bankBranchNameKana;

    @Required
    // modify by V10-CH 170 start
    // @Pattern(message = "4桁の数字で入力してください", value = "[0-9]{4}")
    @JbaBankCode
    // modify by V10-CH 170 end
    @Length(4)
    @Metadata(name = "金融機関コード", order = 5)
    private String bankCode;

    @Required
    // modify by V10-CH 170 start
    // @Pattern(message = "3桁の数字で入力してください", value = "[0-9]{3}")
    @JbaBranchCode
    // modify by V10-CH 170 end
    @Length(3)
    @Metadata(name = "支店コード", order = 6)
    private String bankBranchCode;

    @Required
    @Metadata(name = "口座種類", order = 7)
    private String accountType;

    @Required
    // @Pattern(message = "7桁以下の数字で入力してください", value = "[0-9]{1,7}")
    @JbaAccountNo
    @Length(7)
    @Metadata(name = "口座番号", order = 8)
    private String accountNo;

    @Required
    @BankKana
    @Length(40)
    @Metadata(name = "口座名義人カナ", order = 9)
    private String accountName;

    /**
     * accountNameを取得します。
     * 
     * @return accountName
     */
    public String getAccountName() {
      return accountName;
    }

    /**
     * accountNameを設定します。
     * 
     * @param accountName
     *          accountName
     */
    public void setAccountName(String accountName) {
      this.accountName = accountName;
    }

    /**
     * accountNoを取得します。
     * 
     * @return accountNo
     */
    public String getAccountNo() {
      return accountNo;
    }

    /**
     * accountNoを設定します。
     * 
     * @param accountNo
     *          accountNo
     */
    public void setAccountNo(String accountNo) {
      this.accountNo = accountNo;
    }

    /**
     * accountTypeを取得します。
     * 
     * @return accountType
     */
    public String getAccountType() {
      return accountType;
    }

    /**
     * accountTypeを設定します。
     * 
     * @param accountType
     *          accountType
     */
    public void setAccountType(String accountType) {
      this.accountType = accountType;
    }

    /**
     * bankBranchCodeを取得します。
     * 
     * @return bankBranchCode
     */
    public String getBankBranchCode() {
      return bankBranchCode;
    }

    /**
     * bankBranchCodeを設定します。
     * 
     * @param bankBranchCode
     *          bankBranchCode
     */
    public void setBankBranchCode(String bankBranchCode) {
      this.bankBranchCode = bankBranchCode;
    }

    /**
     * bankBranchNameを取得します。
     * 
     * @return bankBranchName
     */
    public String getBankBranchName() {
      return bankBranchName;
    }

    /**
     * bankBranchNameを設定します。
     * 
     * @param bankBranchName
     *          bankBranchName
     */
    public void setBankBranchName(String bankBranchName) {
      this.bankBranchName = bankBranchName;
    }

    /**
     * bankBranchNameKanaを取得します。
     * 
     * @return bankBranchNameKana
     */
    public String getBankBranchNameKana() {
      return bankBranchNameKana;
    }

    /**
     * bankBranchNameKanaを設定します。
     * 
     * @param bankBranchNameKana
     *          bankBranchNameKana
     */
    public void setBankBranchNameKana(String bankBranchNameKana) {
      this.bankBranchNameKana = bankBranchNameKana;
    }

    /**
     * bankCodeを取得します。
     * 
     * @return bankCode
     */
    public String getBankCode() {
      return bankCode;
    }

    /**
     * bankCodeを設定します。
     * 
     * @param bankCode
     *          bankCode
     */
    public void setBankCode(String bankCode) {
      this.bankCode = bankCode;
    }

    /**
     * bankKanaを取得します。
     * 
     * @return bankKana
     */
    public String getBankKana() {
      return bankKana;
    }

    /**
     * bankKanaを設定します。
     * 
     * @param bankKana
     *          bankKana
     */
    public void setBankKana(String bankKana) {
      this.bankKana = bankKana;
    }

    /**
     * bankNameを取得します。
     * 
     * @return bankName
     */
    public String getBankName() {
      return bankName;
    }

    /**
     * bankNameを設定します。
     * 
     * @param bankName
     *          bankName
     */
    public void setBankName(String bankName) {
      this.bankName = bankName;
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
     * paymentCommissionを設定します。
     * 
     * @param paymentCommission
     *          paymentCommission
     */
    public void setPaymentCommission(String paymentCommission) {
      this.paymentCommission = paymentCommission;
    }

  }

  /**
   * U1050520:支払方法詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class BankOtherInfo implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Digit
    @Length(2)
    @Range(min = 0, max = 60)
    @Metadata(name = "支払期限日数")
    private String bankPaymentLimitDays;

    /**
     * bankPaymentLimitDaysを取得します。
     * 
     * @return bankPaymentLimitDays
     */
    public String getBankPaymentLimitDays() {
      return bankPaymentLimitDays;
    }

    /**
     * bankPaymentLimitDaysを設定します。
     * 
     * @param bankPaymentLimitDays
     *          bankPaymentLimitDays
     */
    public void setBankPaymentLimitDays(String bankPaymentLimitDays) {
      this.bankPaymentLimitDays = bankPaymentLimitDays;
    }

  }

  // setpostPaymentLimitDays
  public static class PostOtherInfo implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Digit
    @Length(2)
    @Range(min = 0, max = 60)
    @Metadata(name = "支払期限日数")
    private String postPaymentLimitDays;

    /**
     * postPaymentLimitDaysを取得します。
     * 
     * @return postPaymentLimitDays
     */
    public String getPostPaymentLimitDays() {
      return postPaymentLimitDays;
    }

    /**
     * postPaymentLimitDaysを設定します。
     * 
     * @param postPaymentLimitDays
     *          postPaymentLimitDays
     */
    public void setPostPaymentLimitDays(String postPaymentLimitDays) {
      this.postPaymentLimitDays = postPaymentLimitDays;
    }

  }

  /**
   * U1050520:支払方法詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class OtherInfo implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Currency
    @Range(min = 0, max = 99999999)
    @Length(11)
    @Metadata(name = "手数料")
    private String paymentCommission;

    @Required
    @AlphaNum2
    @Length(100)
    @Metadata(name = "マーチャントID", order = 1)
    private String merchantId;

    @AlphaNum2
    @Length(50)
    @Metadata(name = "サービスID", order = 2)
    private String serviceId;

    @Required
    @AlphaNum2
    @Length(100)
    @Metadata(name = "秘密鍵", order = 2)
    private String secretKey;

    @Digit
    @Length(2)
    @Range(min = 0, max = 60)
    @Metadata(name = "支払期限日数", order = 3)
    private String paymentLimitDays;

    @Metadata(name = "コンビニ決済利用区分", order = 4)
    private String cvsEnableType;

    @Metadata(name = "電子マネー決済利用区分", order = 5)
    private String digitalCashEnableType;

    /**
     * paymentLimitDaysを取得します。
     * 
     * @return paymentLimitDays
     */
    public String getPaymentLimitDays() {
      return paymentLimitDays;
    }

    /**
     * paymentLimitDaysを設定します。
     * 
     * @param paymentLimitDays
     *          paymentLimitDays
     */
    public void setPaymentLimitDays(String paymentLimitDays) {
      this.paymentLimitDays = paymentLimitDays;
    }

    /**
     * merchantIdを取得します。
     * 
     * @return merchantId
     */
    public String getMerchantId() {
      return merchantId;
    }

    /**
     * merchantIdを設定します。
     * 
     * @param merchantId
     *          merchantId
     */
    public void setMerchantId(String merchantId) {
      this.merchantId = merchantId;
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
     * paymentCommissionを設定します。
     * 
     * @param paymentCommission
     *          paymentCommission
     */
    public void setPaymentCommission(String paymentCommission) {
      this.paymentCommission = paymentCommission;
    }

    /**
     * secretKeyを取得します。
     * 
     * @return secretKey
     */
    public String getSecretKey() {
      return secretKey;
    }

    /**
     * secretKeyを設定します。
     * 
     * @param secretKey
     *          secretKey
     */
    public void setSecretKey(String secretKey) {
      this.secretKey = secretKey;
    }

    /**
     * cvsEnableTypeを取得します。
     * 
     * @return cvsEnableType
     */
    public String getCvsEnableType() {
      return cvsEnableType;
    }

    /**
     * cvsEnableTypeを設定します。
     * 
     * @param cvsEnableType
     *          cvsEnableType
     */
    public void setCvsEnableType(String cvsEnableType) {
      this.cvsEnableType = cvsEnableType;
    }

    /**
     * digitalCashEnableTypeを取得します。
     * 
     * @return digitalCashEnableType
     */
    public String getDigitalCashEnableType() {
      return digitalCashEnableType;
    }

    /**
     * digitalCashEnableTypeを設定します。
     * 
     * @param digitalCashEnableType
     *          digitalCashEnableType
     */
    public void setDigitalCashEnableType(String digitalCashEnableType) {
      this.digitalCashEnableType = digitalCashEnableType;
    }

    /**
     * serviceIdを取得します。
     * 
     * @return serviceId serviceId
     */
    public String getServiceId() {
      return serviceId;
    }

    /**
     * serviceIdを設定します。
     * 
     * @param serviceId
     *          serviceId
     */
    public void setServiceId(String serviceId) {
      this.serviceId = serviceId;
    }

  }

  /**
   * advanceLaterTypeを取得します。
   * 
   * @return advanceLaterType
   */
  public String getAdvanceLaterType() {
    return advanceLaterType;
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
   * paymentMethodNoを取得します。
   * 
   * @return paymentMethodNo
   */
  public String getPaymentMethodNo() {
    return paymentMethodNo;
  }

  /**
   * paymentMethodDisplayTypeを取得します。
   * 
   * @return paymentMethodDisplayType
   */
  public String getPaymentMethodDisplayType() {
    return paymentMethodDisplayType;
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
   * paymentTypeを取得します。
   * 
   * @return paymentType
   */
  public String getPaymentType() {
    return paymentType;
  }

  /**
   * paymentTypeListを取得します。
   * 
   * @return paymentTypeList
   */
  public List<NameValue> getPaymentTypeList() {
    return paymentTypeList;
  }

  /**
   * registerModeを取得します。
   * 
   * @return updateModeFlg
   */
  public boolean getUpdateModeFlg() {
    return updateModeFlg;
  }

  /**
   * advanceLaterTypeを設定します。
   * 
   * @param advanceLaterType
   *          advanceLaterType
   */
  public void setAdvanceLaterType(String advanceLaterType) {
    this.advanceLaterType = advanceLaterType;
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
   * paymentMethodNoを設定します。
   * 
   * @param paymentMethodNo
   *          paymentMethodNo
   */
  public void setPaymentMethodNo(String paymentMethodNo) {
    this.paymentMethodNo = paymentMethodNo;
  }

  /**
   * paymentMethodDisplayTypeを設定します。
   * 
   * @param paymentMethodDisplayType
   *          paymentMethodDisplayType
   */
  public void setPaymentMethodDisplayType(String paymentMethodDisplayType) {
    this.paymentMethodDisplayType = paymentMethodDisplayType;
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
   * paymentTypeを設定します。
   * 
   * @param paymentType
   *          paymentType
   */
  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  /**
   * paymentTypeListを設定します。
   * 
   * @param paymentTypeList
   *          paymentTypeList
   */
  public void setPaymentTypeList(List<NameValue> paymentTypeList) {
    this.paymentTypeList = paymentTypeList;
  }

  /**
   * registerModeを設定します。
   * 
   * @param updateModeFlg
   *          updateModeFlg
   */
  public void setUpdateModeFlg(boolean updateModeFlg) {
    this.updateModeFlg = updateModeFlg;
  }

  /**
   * accountTypeListを取得します。
   * 
   * @return accountTypeList
   */
  public List<NameValue> getAccountTypeList() {
    return accountTypeList;
  }

  /**
   * accountTypeListを設定します。
   * 
   * @param accountTypeList
   *          accountTypeList
   */
  public void setAccountTypeList(List<NameValue> accountTypeList) {
    this.accountTypeList = accountTypeList;
  }

  /**
   * bankingInfoを取得します。
   * 
   * @return bankingInfo
   */
  public BankingInfo getBankingInfo() {
    return bankingInfo;
  }

  /**
   * bankingInfoを設定します。
   * 
   * @param bankingInfo
   *          bankingInfo
   */
  public void setBankingInfo(BankingInfo bankingInfo) {
    this.bankingInfo = bankingInfo;
  }

  /**
   * updateFlgを取得します。
   * 
   * @return updateFlg
   */
  public boolean getUpdateFlg() {
    return updateFlg;
  }

  /**
   * updateFlgを設定します。
   * 
   * @param updateFlg
   *          updateFlg
   */
  public void setUpdateFlg(boolean updateFlg) {
    this.updateFlg = updateFlg;
  }

  /**
   * registerModeDisplayを取得します。
   * 
   * @return registerModeDisplay
   */
  public String getRegisterModeDisplay() {
    return registerModeDisplay;
  }

  /**
   * registerModeDisplayを設定します。
   * 
   * @param registerModeDisplay
   *          registerModeDisplay
   */
  public void setRegisterModeDisplay(String registerModeDisplay) {
    this.registerModeDisplay = registerModeDisplay;
  }

  /**
   * paymentCommissionTaxRateを取得します。
   * 
   * @return paymentCommissionTaxRate
   */
  public String getPaymentCommissionTaxRate() {
    return paymentCommissionTaxRate;
  }

  /**
   * paymentCommissionTaxRateを設定します。
   * 
   * @param paymentCommissionTaxRate
   *          paymentCommissionTaxRate
   */
  public void setPaymentCommissionTaxRate(String paymentCommissionTaxRate) {
    this.paymentCommissionTaxRate = paymentCommissionTaxRate;
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
   * otherInfoを取得します。
   * 
   * @return otherInfo
   */
  public OtherInfo getOtherInfo() {
    return otherInfo;
  }

  /**
   * otherInfoを設定します。
   * 
   * @param otherInfo
   *          otherInfo
   */
  public void setOtherInfo(OtherInfo otherInfo) {
    this.otherInfo = otherInfo;
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

    BankingInfo bankingInformation = new BankingInfo();
    setBankingInfo(bankingInformation);
    // add V10-CH start
    PostalInfo postalInfo = new PostalInfo();
    setPostalInfo(postalInfo);

    getPostalInfo().setPostAccountName(reqparam.get("postAccountName"));
    getPostalInfo().setPostAccountNo(reqparam.get("postAccountNo"));

    PostOtherInfo postOtherInformation = new PostOtherInfo();
    postOtherInformation.setPostPaymentLimitDays(reqparam.get("postPaymentLimitDays"));
    setPostOtherInfo(postOtherInformation);
    // add V10-CH end

    OtherInfo otherInformation = new OtherInfo();
    setOtherInfo(otherInformation);

    BankOtherInfo bankOtherInformation = new BankOtherInfo();
    setBankOtherInfo(bankOtherInformation);

    setPaymentType(reqparam.get("paymentType"));
    setAdvanceLaterType(reqparam.get("advanceLaterType"));
    setPaymentMethodName(reqparam.get("paymentMethodName"));
    setPaymentCommission(reqparam.get("paymentCommission"));
    getBankingInfo().setPaymentCommission(reqparam.get("paymentCommission"));
    getOtherInfo().setPaymentCommission(reqparam.get("paymentCommission"));
    setPaymentMethodDisplayType(reqparam.get("paymentMethodDisplayType"));
    // modify V10-CH start
    // setPaymentCommissionTaxType(reqparam.get("paymentCommissionTaxType"));
    setPaymentCommissionTaxType(TaxType.NO_TAX.getValue());
    // modify V10-CH end

    getBankingInfo().setBankCode(reqparam.get("bankCode"));
    getBankingInfo().setBankName(reqparam.get("bankName"));
    getBankingInfo().setBankKana(reqparam.get("bankKana"));
    getBankingInfo().setBankBranchCode(reqparam.get("bankBranchCode"));
    getBankingInfo().setBankBranchName(reqparam.get("bankBranchName"));
    getBankingInfo().setBankBranchNameKana(reqparam.get("bankBranchNameKana"));
    getBankingInfo().setAccountType(reqparam.get("accountType"));
    getBankingInfo().setAccountName(reqparam.get("accountName"));
    getBankingInfo().setAccountNo(reqparam.get("accountNo"));
    getOtherInfo().setMerchantId(reqparam.get("merchantId"));
    getOtherInfo().setSecretKey(reqparam.get("secretKey"));
    // v10-ch-pg add start
    getOtherInfo().setServiceId(reqparam.get("serviceId"));
    // v10-ch-pg add end
    getOtherInfo().setPaymentLimitDays(reqparam.get("paymentLimitDays"));
    getOtherInfo().setCvsEnableType(reqparam.get("cvsEnableType"));
    getOtherInfo().setDigitalCashEnableType(reqparam.get("digitalCashEnableType"));
    getBankOtherInfo().setBankPaymentLimitDays(reqparam.get("bankPaymentLimitDays"));

    // 订单金额临界值
    setOrderPriceThreshold(reqparam.get("orderPriceThreshold"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050520";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.PaymentmethodDetailBean.0");
  }

  /**
   * updateDateを取得します。
   * 
   * @return updateDate
   */
  public Date getUpdateDate() {
    return DateUtil.immutableCopy(updateDate);
  }

  /**
   * updateDateを設定します。
   * 
   * @param updateDate
   *          updateDate
   */
  public void setUpdateDate(Date updateDate) {
    this.updateDate = DateUtil.immutableCopy(updateDate);
  }

  /**
   * bankMoveButtonFlgを取得します。
   * 
   * @return bankMoveButtonFlg
   */
  public boolean getBankMoveButtonFlg() {
    return bankMoveButtonFlg;
  }

  /**
   * bankMoveButtonFlgを設定します。
   * 
   * @param bankMoveButtonFlg
   *          bankMoveButtonFlg
   */
  public void setBankMoveButtonFlg(boolean bankMoveButtonFlg) {
    this.bankMoveButtonFlg = bankMoveButtonFlg;
  }

  /**
   * taxTypeを取得します。
   * 
   * @return taxType
   */
  public TaxType[] getTaxType() {
    return ArrayUtil.immutableCopy(this.taxType);
  }

  /**
   * taxTypeを設定します。
   * 
   * @param taxType
   *          taxType
   */
  public void setTaxType(TaxType[] taxType) {
    this.taxType = ArrayUtil.immutableCopy(taxType);
  }

  /**
   * bankOtherInfoを取得します。
   * 
   * @return bankOtherInfo
   */
  public BankOtherInfo getBankOtherInfo() {
    return bankOtherInfo;
  }

  /**
   * bankOtherInfoを設定します。
   * 
   * @param bankOtherInfo
   *          bankOtherInfo
   */
  public void setBankOtherInfo(BankOtherInfo bankOtherInfo) {
    this.bankOtherInfo = bankOtherInfo;
  }

  // add by V10-CH 170 start
  /**
   * U1050520:支払方法詳細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class PostalInfo implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(40)
    @Metadata(name = "收款人姓名", order = 1)
    private String postAccountName;

    @Required
    @Pattern(message = "8位以下的数字", value = "[0-9]{1,7}")
    @Length(7)
    @Metadata(name = "商户客户号", order = 2)
    private String postAccountNo;

    @Length(40)
    @Metadata(name = "收款人姓名")
    private String shopCode;

    public String getPostAccountName() {
      return postAccountName;
    }

    public void setPostAccountName(String postAccountName) {
      this.postAccountName = postAccountName;
    }

    public String getPostAccountNo() {
      return postAccountNo;
    }

    public void setPostAccountNo(String postAccountNo) {
      this.postAccountNo = postAccountNo;
    }

    /**
     * shopCodeを取得します。
     * 
     * @return shopCode shopCode
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
  }

  /**
   * postalInfoを取得します。
   * 
   * @return postalInfo postalInfo
   */
  public PostalInfo getPostalInfo() {
    return postalInfo;
  }

  /**
   * postalInfoを設定します。
   * 
   * @param postalInfo
   *          postalInfo
   */
  public void setPostalInfo(PostalInfo postalInfo) {
    this.postalInfo = postalInfo;
  }

  // Add by V10-CH start
  /**
   * postOtherInfoを取得します。
   * 
   * @return postOtherInfo postOtherInfo
   */
  public PostOtherInfo getPostOtherInfo() {
    return postOtherInfo;
  }

  /**
   * postOtherInfoを設定します。
   * 
   * @param postOtherInfo
   *          postOtherInfo
   */
  public void setPostOtherInfo(PostOtherInfo postOtherInfo) {
    this.postOtherInfo = postOtherInfo;
  }

  public String getOrderPriceThreshold() {
    return orderPriceThreshold;
  }

  public void setOrderPriceThreshold(String orderPriceThreshold) {
    this.orderPriceThreshold = orderPriceThreshold;
  }

  // add by V10-CH 170 end
}
