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
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「ポイント履歴(POINT_HISTORY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface PointHistoryDao extends GenericDao<PointHistory, Long> {

  /**
   * 指定されたorm_rowidを持つポイント履歴のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPointHistoryのインスタンス
   */
  PointHistory loadByRowid(Long id);

  /**
   * 主キー列の値を指定してポイント履歴のインスタンスを取得します。
   *
   * @param pointHistoryId ポイント履歴ID
   * @return 主キー列の値に対応するPointHistoryのインスタンス
   */
  PointHistory load(Long pointHistoryId);

  /**
   * 主キー列の値を指定してポイント履歴が既に存在するかどうかを返します。
   *
   * @param pointHistoryId ポイント履歴ID
   * @return 主キー列の値に対応するPointHistoryの行が存在すればtrue
   */
  boolean exists(Long pointHistoryId);

  /**
   * 新規PointHistoryをデータベースに追加します。
   *
   * @param obj 追加対象のPointHistory
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(PointHistory obj, LoginInfo loginInfo);

  /**
   * ポイント履歴を更新します。
   *
   * @param obj 更新対象のPointHistory
   * @param loginInfo ログイン情報
   */
  void update(PointHistory obj, LoginInfo loginInfo);

  /**
   * ポイント履歴を削除します。
   *
   * @param obj 削除対象のPointHistory
   * @param loginInfo ログイン情報
   */
  void delete(PointHistory obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してポイント履歴を削除します。
   *
   * @param pointHistoryId ポイント履歴ID
   */
  void delete(Long pointHistoryId);

  /**
   * Queryオブジェクトを指定してポイント履歴のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPointHistoryのリスト
   */
  List<PointHistory> findByQuery(Query query);

  /**
   * SQLを指定してポイント履歴のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPointHistoryのリスト
   */
  List<PointHistory> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPointHistoryのリスト
   */
  List<PointHistory> loadAll();

}
