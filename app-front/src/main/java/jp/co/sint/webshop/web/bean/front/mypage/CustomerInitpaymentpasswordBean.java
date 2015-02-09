package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030430:パスワード再発行のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerInitpaymentpasswordBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Length(50)
  @AlphaNum2
  @Metadata(name = "新パスワード")
  private String newPassword;

  @Required
  @Length(50)
  @AlphaNum2
  @Metadata(name = "新パスワード確認")
  private String newPasswordConfirm;

  /** URLパラメータTOKEN */
  private String token;

  private String updateCustomerCode;

  /** 更新日時順 */
  private Date updatedDatetime;

  private boolean displayFlg;

  /**
   * newPasswordを取得します。
   * 
   * @return newPassword
   */
  public String getNewPassword() {
    return newPassword;
  }

  /**
   * newPasswordConfirmを取得します。
   * 
   * @return newPasswordConfirm
   */
  public String getNewPasswordConfirm() {
    return newPasswordConfirm;
  }

  /**
   * newPasswordを設定します。
   * 
   * @param newPassword
   *          newPassword
   */
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  /**
   * newPasswordConfirmを設定します。
   * 
   * @param newPasswordConfirm
   *          newPasswordConfirm
   */
  public void setNewPasswordConfirm(String newPasswordConfirm) {
    this.newPasswordConfirm = newPasswordConfirm;
  }

//  /**
//   * サブJSPを設定します。
//   */
//  @Override
//  public void setSubJspId() {
//  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    setNewPassword(reqparam.get("newPassword"));
    setNewPasswordConfirm(reqparam.get("newPasswordConfirm"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030430";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.CustomerInitpasswordBean.0");
  }

  /**
   * updateCustomerCodeを取得します。
   * 
   * @return updateCustomerCode
   */
  public String getUpdateCustomerCode() {
    return updateCustomerCode;
  }

  /**
   * updateCustomerCodeを設定します。
   * 
   * @param updateCustomerCode
   *          updateCustomerCode
   */
  public void setUpdateCustomerCode(String updateCustomerCode) {
    this.updateCustomerCode = updateCustomerCode;
  }

  /**
   * displayFlgを取得します。
   * 
   * @return displayFlg
   */
  public boolean isDisplayFlg() {
    return displayFlg;
  }

  /**
   * displayFlgを設定します。
   * 
   * @param displayFlg
   *          displayFlg
   */
  public void setDisplayFlg(boolean displayFlg) {
    this.displayFlg = displayFlg;
  }

  /**
   * updatedDatetimeを取得します。
   * 
   * @return the updatedDatetime
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

  /**
   * tokenを取得します。
   * 
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * tokenを設定します。
   * 
   * @param token
   *          token
   */
  public void setToken(String token) {
    this.token = token;
  }

}
