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
import jp.co.sint.webshop.data.dto.UserAccessLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「管理側アクセスログ(USER_ACCESS_LOG)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface UserAccessLogDao extends GenericDao<UserAccessLog, Long> {

  /**
   * 指定されたorm_rowidを持つ管理側アクセスログのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するUserAccessLogのインスタンス
   */
  UserAccessLog loadByRowid(Long id);

  /**
   * 主キー列の値を指定して管理側アクセスログのインスタンスを取得します。
   *
   * @param userAccessLogId 管理側アクセスログID
   * @return 主キー列の値に対応するUserAccessLogのインスタンス
   */
  UserAccessLog load(Long userAccessLogId);

  /**
   * 主キー列の値を指定して管理側アクセスログが既に存在するかどうかを返します。
   *
   * @param userAccessLogId 管理側アクセスログID
   * @return 主キー列の値に対応するUserAccessLogの行が存在すればtrue
   */
  boolean exists(Long userAccessLogId);

  /**
   * 新規UserAccessLogをデータベースに追加します。
   *
   * @param obj 追加対象のUserAccessLog
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(UserAccessLog obj, LoginInfo loginInfo);

  /**
   * 管理側アクセスログを更新します。
   *
   * @param obj 更新対象のUserAccessLog
   * @param loginInfo ログイン情報
   */
  void update(UserAccessLog obj, LoginInfo loginInfo);

  /**
   * 管理側アクセスログを削除します。
   *
   * @param obj 削除対象のUserAccessLog
   * @param loginInfo ログイン情報
   */
  void delete(UserAccessLog obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して管理側アクセスログを削除します。
   *
   * @param userAccessLogId 管理側アクセスログID
   */
  void delete(Long userAccessLogId);

  /**
   * Queryオブジェクトを指定して管理側アクセスログのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するUserAccessLogのリスト
   */
  List<UserAccessLog> findByQuery(Query query);

  /**
   * SQLを指定して管理側アクセスログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するUserAccessLogのリスト
   */
  List<UserAccessLog> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のUserAccessLogのリスト
   */
  List<UserAccessLog> loadAll();

}
