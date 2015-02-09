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
import jp.co.sint.webshop.data.dto.OrderMailHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「受注メール送信履歴(ORDER_MAIL_HISTORY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface OrderMailHistoryDao extends GenericDao<OrderMailHistory, Long> {

  /**
   * 指定されたorm_rowidを持つ受注メール送信履歴のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するOrderMailHistoryのインスタンス
   */
  OrderMailHistory loadByRowid(Long id);

  /**
   * 主キー列の値を指定して受注メール送信履歴のインスタンスを取得します。
   *
   * @param orderMailHistoryId 受注メール送信履歴ID
   * @return 主キー列の値に対応するOrderMailHistoryのインスタンス
   */
  OrderMailHistory load(Long orderMailHistoryId);

  /**
   * 主キー列の値を指定して受注メール送信履歴が既に存在するかどうかを返します。
   *
   * @param orderMailHistoryId 受注メール送信履歴ID
   * @return 主キー列の値に対応するOrderMailHistoryの行が存在すればtrue
   */
  boolean exists(Long orderMailHistoryId);

  /**
   * 新規OrderMailHistoryをデータベースに追加します。
   *
   * @param obj 追加対象のOrderMailHistory
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(OrderMailHistory obj, LoginInfo loginInfo);

  /**
   * 受注メール送信履歴を更新します。
   *
   * @param obj 更新対象のOrderMailHistory
   * @param loginInfo ログイン情報
   */
  void update(OrderMailHistory obj, LoginInfo loginInfo);

  /**
   * 受注メール送信履歴を削除します。
   *
   * @param obj 削除対象のOrderMailHistory
   * @param loginInfo ログイン情報
   */
  void delete(OrderMailHistory obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して受注メール送信履歴を削除します。
   *
   * @param orderMailHistoryId 受注メール送信履歴ID
   */
  void delete(Long orderMailHistoryId);

  /**
   * Queryオブジェクトを指定して受注メール送信履歴のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOrderMailHistoryのリスト
   */
  List<OrderMailHistory> findByQuery(Query query);

  /**
   * SQLを指定して受注メール送信履歴のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOrderMailHistoryのリスト
   */
  List<OrderMailHistory> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のOrderMailHistoryのリスト
   */
  List<OrderMailHistory> loadAll();

}
