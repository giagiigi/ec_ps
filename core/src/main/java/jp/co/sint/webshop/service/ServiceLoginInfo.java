package jp.co.sint.webshop.service;

import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.utility.UserAgent;

public final class ServiceLoginInfo implements LoginInfo {

  private static final long serialVersionUID = 1L;

  private UserAgent userAgent;

  private ServiceLoginInfo() {
    userAgent = new UserAgent();
  }

  public String getLoginId() {
    return "SYSTEM";
  }

  public String getName() {
    return "システム管理者";
  }

  public boolean hasPermission(Permission p) {
    return true;
  }

  public boolean isLogin() {
    return true;
  }

  public boolean isNotLogin() {
    return !isLogin();
  }

  public static LoginInfo getInstance() {
    return new ServiceLoginInfo();
  }

  public LoginType getLoginType() {
    return LoginType.SYSTEM;
  }

  public UserAgent getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(UserAgent userAgent) {
    this.userAgent = userAgent;
  }

  public String getRecordingFormat() {
    return "SYSTEM:0:0:0";
  }
  
  public String getShopCode(){
    return null;
  }

  @Override
  public boolean isCoupon() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isVip() {
    // TODO Auto-generated method stub
    return false;
  }
}
