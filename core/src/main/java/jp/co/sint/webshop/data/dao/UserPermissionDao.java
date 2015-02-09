//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.UserPermission;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「管理ユーザ権限(USER_PERMISSION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface UserPermissionDao extends GenericDao<UserPermission, Long> {

  /**
   * 指定されたorm_rowidを持つ管理ユーザ権限のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するUserPermissionのインスタンス
   */
  UserPermission loadByRowid(Long id);

  /**
   * 主キー列の値を指定して管理ユーザ権限のインスタンスを取得します。
   *
   * @param permissionCode 権限コード
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserPermissionのインスタンス
   */
  UserPermission load(String permissionCode, Long userCode);

  /**
   * 主キー列の値を指定して管理ユーザ権限が既に存在するかどうかを返します。
   *
   * @param permissionCode 権限コード
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserPermissionの行が存在すればtrue
   */
  boolean exists(String permissionCode, Long userCode);

  /**
   * 新規UserPermissionをデータベースに追加します。
   *
   * @param obj 追加対象のUserPermission
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(UserPermission obj, LoginInfo loginInfo);

  /**
   * 管理ユーザ権限を更新します。
   *
   * @param obj 更新対象のUserPermission
   * @param loginInfo ログイン情報
   */
  void update(UserPermission obj, LoginInfo loginInfo);

  /**
   * 管理ユーザ権限を削除します。
   *
   * @param obj 削除対象のUserPermission
   * @param loginInfo ログイン情報
   */
  void delete(UserPermission obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して管理ユーザ権限を削除します。
   *
   * @param permissionCode 権限コード
   * @param userCode ユーザコード
   */
  void delete(String permissionCode, Long userCode);

  /**
   * Queryオブジェクトを指定して管理ユーザ権限のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するUserPermissionのリスト
   */
  List<UserPermission> findByQuery(Query query);

  /**
   * SQLを指定して管理ユーザ権限のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するUserPermissionのリスト
   */
  List<UserPermission> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のUserPermissionのリスト
   */
  List<UserPermission> loadAll();

}
