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
import jp.co.sint.webshop.data.dto.AccessLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「アクセスログ(ACCESS_LOG)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface AccessLogDao extends GenericDao<AccessLog, Long> {

  /**
   * 指定されたorm_rowidを持つアクセスログのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するAccessLogのインスタンス
   */
  AccessLog loadByRowid(Long id);

  /**
   * 主キー列の値を指定してアクセスログのインスタンスを取得します。
   *
   * @param accessLogId アクセスログID
   * @return 主キー列の値に対応するAccessLogのインスタンス
   */
  AccessLog load(Long accessLogId);

  /**
   * 主キー列の値を指定してアクセスログが既に存在するかどうかを返します。
   *
   * @param accessLogId アクセスログID
   * @return 主キー列の値に対応するAccessLogの行が存在すればtrue
   */
  boolean exists(Long accessLogId);

  /**
   * 新規AccessLogをデータベースに追加します。
   *
   * @param obj 追加対象のAccessLog
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(AccessLog obj, LoginInfo loginInfo);

  /**
   * アクセスログを更新します。
   *
   * @param obj 更新対象のAccessLog
   * @param loginInfo ログイン情報
   */
  void update(AccessLog obj, LoginInfo loginInfo);

  /**
   * アクセスログを削除します。
   *
   * @param obj 削除対象のAccessLog
   * @param loginInfo ログイン情報
   */
  void delete(AccessLog obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してアクセスログを削除します。
   *
   * @param accessLogId アクセスログID
   */
  void delete(Long accessLogId);

  /**
   * Queryオブジェクトを指定してアクセスログのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するAccessLogのリスト
   */
  List<AccessLog> findByQuery(Query query);

  /**
   * SQLを指定してアクセスログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するAccessLogのリスト
   */
  List<AccessLog> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のAccessLogのリスト
   */
  List<AccessLog> loadAll();

}
