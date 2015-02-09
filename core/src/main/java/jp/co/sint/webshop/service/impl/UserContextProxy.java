package jp.co.sint.webshop.service.impl;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.UserContext;

/**
 * ユーザコンテキストをThreadLocalで使用するためのプロキシ・クラスです。
 * 
 * @author System Integrator Corp.
 */
public class UserContextProxy implements UserContext {

  private ThreadLocal<UserContextImpl> thlocal = new ThreadLocal<UserContextImpl>();

  public LoginInfo getLoginInfo() {
    return getImpl().getLoginInfo();
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    getImpl().setLoginInfo(loginInfo);
  }

  private UserContextImpl getImpl() {
    UserContextImpl impl = thlocal.get();
    if (impl == null) {
      impl = new UserContextImpl();
      thlocal.set(impl);
    }
    return impl;
  }
}
