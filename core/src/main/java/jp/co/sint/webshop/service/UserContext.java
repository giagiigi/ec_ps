package jp.co.sint.webshop.service;

/**
 * サービス実行時のユーザーコンテキストです。
 * 
 * @author System Integrator Corp.
 */
public interface UserContext {

  void setLoginInfo(LoginInfo loginInfo);

  LoginInfo getLoginInfo();

}
