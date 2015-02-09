package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030420:パスワード変更のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerChangepasswordBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String customerCode;

  @Required
  @Length(50)
  @Metadata(name = "現パスワード")
  private String oldPassword;

  @Required
  @Length(50)
  @Metadata(name = "新パスワード")
  private String newPassword;

  @Required
  @Length(50)
  @Metadata(name = "新パスワード確認")
  private String newPasswordConfirm;

  /** 更新日時順 */
  private Date updatedDatetime;

  /** 更新完了フラグ */
  private boolean updated;

  @Required
  @Length(4)
  @Metadata(name = "验证码")
  private String verificationCode;

  /**
   * updatedを返します。
   * 
   * @return the updated
   */
  public boolean isUpdated() {
    return updated;
  }

  /**
   * updatedを設定します。
   * 
   * @param updated
   *          設定する updated
   */
  public void setUpdated(boolean updated) {
    this.updated = updated;
  }

  /**
   * oldPasswordを取得します。
   * 
   * @return the oldPassword
   */
  public String getOldPassword() {
    return oldPassword;
  }

  /**
   * oldPasswordを設定します。
   * 
   * @param oldPassword
   *          oldPassword
   */
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
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

  // /**
  // * サブJSPを設定します。
  // */
  // @Override
  // public void setSubJspId() {
  // }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030420";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.CustomerChangepasswordBean.1");
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
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
   * @return the verificationCode
   */
  public String getVerificationCode() {
    return verificationCode;
  }

  /**
   * @param verificationCode
   *          the verificationCode to set
   */
  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
  }
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(Messages.getString(
    "web.bean.front.mypage.OrderHistoryBean.1"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
      
      topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.CustomerChangepasswordBean.1"), "/app/mypage/customer_changepassword")); //$NON-NLS-1$ //$NON-NLS-2$
    }else{
      topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.CustomerChangepasswordBean.1"), "/app/mypage/customer_changepassword")); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    return topicPath;
  }
}
