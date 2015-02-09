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
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「管理ユーザ(USER_ACCOUNT)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface UserAccountDao extends GenericDao<UserAccount, Long> {

  /**
   * 指定されたorm_rowidを持つ管理ユーザのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するUserAccountのインスタンス
   */
  UserAccount loadByRowid(Long id);

  /**
   * 主キー列の値を指定して管理ユーザのインスタンスを取得します。
   *
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserAccountのインスタンス
   */
  UserAccount load(Long userCode);

  /**
   * 主キー列の値を指定して管理ユーザが既に存在するかどうかを返します。
   *
   * @param userCode ユーザコード
   * @return 主キー列の値に対応するUserAccountの行が存在すればtrue
   */
  boolean exists(Long userCode);

  /**
   * 新規UserAccountをデータベースに追加します。
   *
   * @param obj 追加対象のUserAccount
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(UserAccount obj, LoginInfo loginInfo);

  /**
   * 管理ユーザを更新します。
   *
   * @param obj 更新対象のUserAccount
   * @param loginInfo ログイン情報
   */
  void update(UserAccount obj, LoginInfo loginInfo);

  /**
   * 管理ユーザを削除します。
   *
   * @param obj 削除対象のUserAccount
   * @param loginInfo ログイン情報
   */
  void delete(UserAccount obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して管理ユーザを削除します。
   *
   * @param userCode ユーザコード
   */
  void delete(Long userCode);

  /**
   * Queryオブジェクトを指定して管理ユーザのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するUserAccountのリスト
   */
  List<UserAccount> findByQuery(Query query);

  /**
   * SQLを指定して管理ユーザのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するUserAccountのリスト
   */
  List<UserAccount> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のUserAccountのリスト
   */
  List<UserAccount> loadAll();

}
