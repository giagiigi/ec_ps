package jp.co.sint.webshop.service.impl;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.UserContext;

/**
 * ユーザコンテキスト実装クラスです。
 * 
 * @author System Integrator Corp.
 */
public class UserContextImpl implements UserContext {

  private LoginInfo loginInfo;

  /**
   * loginInfoを返します。
   * 
   * @return the loginInfo
   */
  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  /**
   * loginInfoを設定します。
   * 
   * @param loginInfo
   *          設定する loginInfo
   */
  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

}
