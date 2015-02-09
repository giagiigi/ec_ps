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
import jp.co.sint.webshop.data.dto.Reminder;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「リマインダ(REMINDER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ReminderDao extends GenericDao<Reminder, Long> {

  /**
   * 指定されたorm_rowidを持つリマインダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するReminderのインスタンス
   */
  Reminder loadByRowid(Long id);

  /**
   * 主キー列の値を指定してリマインダのインスタンスを取得します。
   *
   * @param reissuanceKey 再発行キー
   * @return 主キー列の値に対応するReminderのインスタンス
   */
  Reminder load(String reissuanceKey);

  /**
   * 主キー列の値を指定してリマインダが既に存在するかどうかを返します。
   *
   * @param reissuanceKey 再発行キー
   * @return 主キー列の値に対応するReminderの行が存在すればtrue
   */
  boolean exists(String reissuanceKey);

  /**
   * 新規Reminderをデータベースに追加します。
   *
   * @param obj 追加対象のReminder
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Reminder obj, LoginInfo loginInfo);

  /**
   * リマインダを更新します。
   *
   * @param obj 更新対象のReminder
   * @param loginInfo ログイン情報
   */
  void update(Reminder obj, LoginInfo loginInfo);

  /**
   * リマインダを削除します。
   *
   * @param obj 削除対象のReminder
   * @param loginInfo ログイン情報
   */
  void delete(Reminder obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してリマインダを削除します。
   *
   * @param reissuanceKey 再発行キー
   */
  void delete(String reissuanceKey);

  /**
   * Queryオブジェクトを指定してリマインダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するReminderのリスト
   */
  List<Reminder> findByQuery(Query query);

  /**
   * SQLを指定してリマインダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するReminderのリスト
   */
  List<Reminder> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のReminderのリスト
   */
  List<Reminder> loadAll();

}
