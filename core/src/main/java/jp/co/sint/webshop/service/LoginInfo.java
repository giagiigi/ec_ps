package jp.co.sint.webshop.service;

import java.io.Serializable;

import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.utility.UserAgent;

/**
 * ログイン情報を表現する共通インターフェイスです。
 * 
 * @author System Integrator Corp.
 */
public interface LoginInfo extends Serializable {

  /**
   * ログインIDを取得します。
   * 
   * @return ログインID
   */
  String getLoginId();

  /**
   * ログインユーザーの名前を取得します。
   * 
   * @return ログインユーザーの名前
   */
  String getName();

  /**
   * ログイン中かどうかを返します。
   * 
   * @return ログイン中であればtrueを返します。
   */
  boolean isLogin();

  /**
   * isLoginとは逆に「ログインしていない」かどうかを返します。
   * 
   * @return ログイン中であればfalseを返します。
   */
  boolean isNotLogin();

  /**
   * 指定された権限がこのログインに付与されているか確認します。
   * 
   * @return 指定された権限がこのログインに付与されていればtrue
   */
  boolean hasPermission(Permission p);

  /**
   * ユーザーエージェントを取得します。
   * 
   * @return ユーザーエージェント
   */
  UserAgent getUserAgent();

  /**
   * ログイン種別を返します。
   * 
   * @return ログイン種別
   */
  LoginType getLoginType();

  /**
   * このログインによる操作を記録するための、書式付けられた文字列を返します。
   * 
   * @return 記録用に書式付けられた文字列
   */
  String getRecordingFormat();

  boolean isCoupon();
  
  boolean isVip();
}
