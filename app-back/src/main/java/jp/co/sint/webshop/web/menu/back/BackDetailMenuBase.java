package jp.co.sint.webshop.web.menu.back;

import jp.co.sint.webshop.service.Permission;

/**
 * 管理側メニュー制御のインターフェースです。
 * 
 * @author System Integrator Corp.
 */
public interface BackDetailMenuBase {

  /**
   * 画面のURLを取得します。
   * 
   * @return 各画面のURL
   */
  String getUrl();

  /**
   * 画面の名称を取得します。
   * 
   * @return 各画面の名称
   */
  String getLabel();

  /**
   * 画面のモジュールIDを取得します。
   * 
   * @return モジュールID
   */
  String getModuleId();

  /**
   * 画面の権限情報を取得します。
   * 
   * @return 各画面の権限情報
   */
  Permission[] getPermissions();
}
