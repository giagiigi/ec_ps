package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.BankKana;
import jp.co.sint.webshop.data.attribute.JbaAccountNo;
import jp.co.sint.webshop.data.attribute.JbaBankCode;
import jp.co.sint.webshop.data.attribute.JbaBranchCode;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050530:金融機関設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodBankBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private BankDetail registerBank = new BankDetail();

  private BankDetail deleteBank = new BankDetail();

  private List<BankDetail> bankList = new ArrayList<BankDetail>();

  private boolean buttonDisplayFlg;

  private String displayMode;

  private String shopCode;

  private String paymentMethodNo;

  private String processMode;

  private boolean deleteButtonDisplayFlg;

  /**
   * U1050530:金融機関設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class BankDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(4)
    //modify by V10-CH 170 start
    //@Pattern(message = "4桁の数字で入力してください", value = "[0-9]{4}")
    @JbaBankCode
    //modify by V10-CH 170 end
    @Metadata(name = "金融機関コード")
    private String bankCode;

    @Required
    @Length(40)
    @Metadata(name = "金融機関名")
    private String bankName;

    @Required
    @Length(40)
    @BankKana
    @Metadata(name = "金融機関名カナ")
    private String bankKana;

    @Required
    @Length(3)
    //modify by V10-CH 170 start
    //@Pattern(message = "3桁の数字で入力してください", value = "[0-9]{3}")
    @JbaBranchCode
    //modify by V10-CH 170 end
    @Metadata(name = "支店コード")
    private String bankBranchCode;

    @Required
    @Length(40)
    @Metadata(name = "支店名")
    private String bankBranchName;

    @Required
    @Length(40)
    @BankKana
    @Metadata(name = "支店名カナ")
    private String bankBranchNameKana;

    private List<CodeAttribute> accountTypeList = new ArrayList<CodeAttribute>();

    @Required
    @Length(1)
    @Metadata(name = "口座種類")
    private String accountType;

    @Required
    @Length(7)
    //@Pattern(message = "7桁以下の数字で入力してください", value = "[0-9]{1,7}")
    @JbaAccountNo
    @Metadata(name = "口座番号")
    private String accountNo;

    @Required
    @Length(40)
    @BankKana
    @Metadata(name = "口座名義人カナ")
    private String accountName;

    private Date updatedDatetime;

    /**
     * accountTypeListを取得します。
     * 
     * @return accountTypeList
     */
    public List<CodeAttribute> getAccountTypeList() {
      return accountTypeList;
    }

    /**
     * accountTypeListを設定します。
     * 
     * @param accountTypeList
     *          accountTypeList
     */
    public void setAccountTypeList(List<CodeAttribute> accountTypeList) {
      this.accountTypeList = accountTypeList;
    }

    /**
     * accountNameを取得します。
     * 
     * @return accountName
     */
    public String getAccountName() {
      return accountName;
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
     * accountTypeを取得します。
     * 
     * @return accountType
     */
    public String getAccountType() {
      return accountType;
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
     * bankBranchNameを取得します。
     * 
     * @return bankBranchName
     */
    public String getBankBranchName() {
      return bankBranchName;
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
     * bankCodeを取得します。
     * 
     * @return bankCode
     */
    public String getBankCode() {
      return bankCode;
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
     * bankNameを取得します。
     * 
     * @return bankName
     */
    public String getBankName() {
      return bankName;
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
     * accountNoを設定します。
     * 
     * @param accountNo
     *          accountNo
     */
    public void setAccountNo(String accountNo) {
      this.accountNo = accountNo;
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
     * bankBranchCodeを設定します。
     * 
     * @param bankBranchCode
     *          bankBranchCode
     */
    public void setBankBranchCode(String bankBranchCode) {
      this.bankBranchCode = bankBranchCode;
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
     * bankBranchNameKanaを設定します。
     * 
     * @param bankBranchNameKana
     *          bankBranchNameKana
     */
    public void setBankBranchNameKana(String bankBranchNameKana) {
      this.bankBranchNameKana = bankBranchNameKana;
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
     * bankKanaを設定します。
     * 
     * @param bankKana
     *          bankKana
     */
    public void setBankKana(String bankKana) {
      this.bankKana = bankKana;
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
   * bankListを取得します。
   * 
   * @return bankList
   */
  public List<BankDetail> getBankList() {
    return bankList;
  }

  /**
   * registerBankを取得します。
   * 
   * @return registerBank
   */
  public BankDetail getRegisterBank() {
    return registerBank;
  }

  /**
   * bankListを設定します。
   * 
   * @param bankList
   *          bankList
   */
  public void setBankList(List<BankDetail> bankList) {
    this.bankList = bankList;
  }

  /**
   * registerBankを設定します。
   * 
   * @param registerBank
   *          registerBank
   */
  public void setRegisterBank(BankDetail registerBank) {
    this.registerBank = registerBank;
  }

  /**
   * buttonDisplayFlgを取得します。
   * 
   * @return buttonDisplayFlg
   */
  public boolean getButtonDisplayFlg() {
    return buttonDisplayFlg;
  }

  /**
   * buttonDisplayFlgを設定します。
   * 
   * @param buttonDisplayFlg
   *          buttonDisplayFlg
   */
  public void setButtonDisplayFlg(boolean buttonDisplayFlg) {
    this.buttonDisplayFlg = buttonDisplayFlg;
  }

  /**
   * displayModeを取得します。
   * 
   * @return displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * displayModeを設定します。
   * 
   * @param displayMode
   *          displayMode
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
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
   * paymentMethodNoを取得します。
   * 
   * @return paymentMethodNo
   */
  public String getPaymentMethodNo() {
    return paymentMethodNo;
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
   * deleteBankを取得します。
   * 
   * @return deleteBank
   */
  public BankDetail getDeleteBank() {
    return deleteBank;
  }

  /**
   * deleteBankを設定します。
   * 
   * @param deleteBank
   *          deleteBank
   */
  public void setDeleteBank(BankDetail deleteBank) {
    this.deleteBank = deleteBank;
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
    deleteBank.setBankCode(reqparam.get("deleteBankCode"));
    deleteBank.setBankBranchCode(reqparam.get("deleteBankBranchCode"));
    deleteBank.setAccountNo(reqparam.get("deleteAccountNo"));
    registerBank.setBankCode(reqparam.get("bankCode"));
    registerBank.setBankName(reqparam.get("bankName"));
    registerBank.setBankKana(reqparam.get("bankKana"));
    registerBank.setBankBranchCode(reqparam.get("bankBranchCode"));
    registerBank.setBankBranchName(reqparam.get("bankBranchName"));
    registerBank.setBankBranchNameKana(reqparam.get("bankBranchNameKana"));
    registerBank.setAccountType(reqparam.get("accountType"));
    registerBank.setAccountNo(reqparam.get("accountNo"));
    registerBank.setAccountName(reqparam.get("accountName"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050530";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.PaymentmethodBankBean.0");
  }

  /**
   * processModeを取得します。
   * 
   * @return processMode
   */
  public String getProcessMode() {
    return processMode;
  }

  /**
   * processModeを設定します。
   * 
   * @param processMode
   *          processMode
   */
  public void setProcessMode(String processMode) {
    this.processMode = processMode;
  }

  /**
   * deleteButtonDisplayFlgを取得します。
   * 
   * @return deleteButtonDisplayFlg
   */
  public boolean isDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  /**
   * deleteButtonDisplayFlgを設定します。
   * 
   * @param deleteButtonDisplayFlg
   *          deleteButtonDisplayFlg
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
  }

}
