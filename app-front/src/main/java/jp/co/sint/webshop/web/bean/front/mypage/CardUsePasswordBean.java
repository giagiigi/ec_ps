package jp.co.sint.webshop.web.bean.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030110:マイページのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CardUsePasswordBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String totalDenomination;

  private String avalibleDenomination;

  private String password;

  private String email;

  private String oldPassword;

  private String passwordRepeat;

  private boolean updatePasswordFlg = false;

  private boolean disFlg = false;

  @Override
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);

  }

  @Override
  public String getModuleId() {
    return "";
  }

  @Override
  public String getModuleName() {
    return "支付密码设定";
  }

  /**
   * @return the totalDenomination
   */
  public String getTotalDenomination() {
    return totalDenomination;
  }

  /**
   * @param totalDenomination
   *          the totalDenomination to set
   */
  public void setTotalDenomination(String totalDenomination) {
    this.totalDenomination = totalDenomination;
  }

  /**
   * @return the avalibleDenomination
   */
  public String getAvalibleDenomination() {
    return avalibleDenomination;
  }

  /**
   * @param avalibleDenomination
   *          the avalibleDenomination to set
   */
  public void setAvalibleDenomination(String avalibleDenomination) {
    this.avalibleDenomination = avalibleDenomination;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the passwordRepeat
   */
  public String getPasswordRepeat() {
    return passwordRepeat;
  }

  /**
   * @param passwordRepeat
   *          the passwordRepeat to set
   */
  public void setPasswordRepeat(String passwordRepeat) {
    this.passwordRepeat = passwordRepeat;
  }

  /**
   * @return the updatePasswordFlg
   */
  public boolean isUpdatePasswordFlg() {
    return updatePasswordFlg;
  }

  /**
   * @param updatePasswordFlg
   *          the updatePasswordFlg to set
   */
  public void setUpdatePasswordFlg(boolean updatePasswordFlg) {
    this.updatePasswordFlg = updatePasswordFlg;
  }

  /**
   * @return the oldPassword
   */
  public String getOldPassword() {
    return oldPassword;
  }

  /**
   * @param oldPassword
   *          the oldPassword to set
   */
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    if (!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)) {
      topicPath.add(new NameValue(Messages.getString("web.bean.front.mypage.OrderHistoryBean.1"), "/app/mypage/mypage")); //$NON-NLS-1$ //$NON-NLS-2$
      topicPath.add(new NameValue(
          Messages.getString("web.action.front.order.GiftCardRule.22"), "/app/mypage/card_use_password/init")); //$NON-NLS-1$ //$NON-NLS-2$
    } else {
      topicPath.add(new NameValue(
          Messages.getString("web.action.front.order.GiftCardRule.22"), "/app/mypage/card_use_password/init")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    return topicPath;
  }

  /**
   * @return the disFlg
   */
  public boolean isDisFlg() {
    return disFlg;
  }

  /**
   * @param disFlg
   *          the disFlg to set
   */
  public void setDisFlg(boolean disFlg) {
    this.disFlg = disFlg;
  }

}
