package jp.co.sint.webshop.service;

/**
 * ログイン情報を保持するサービスのためのインターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface StatefulService {

  /**
   * ログイン情報を取得します。
   * 
   * @return ログイン情報
   */
  LoginInfo getLoginInfo();

  /**
   * ログイン情報を設定します。
   * 
   * @param loginInfo
   *          ログイン情報
   */
  void setLoginInfo(LoginInfo loginInfo);

}
