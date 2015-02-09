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
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「休日(HOLIDAY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface HolidayDao extends GenericDao<Holiday, Long> {

  /**
   * 指定されたorm_rowidを持つ休日のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するHolidayのインスタンス
   */
  Holiday loadByRowid(Long id);

  /**
   * 主キー列の値を指定して休日のインスタンスを取得します。
   *
   * @param holidayId 休日ID
   * @return 主キー列の値に対応するHolidayのインスタンス
   */
  Holiday load(Long holidayId);

  /**
   * 主キー列の値を指定して休日が既に存在するかどうかを返します。
   *
   * @param holidayId 休日ID
   * @return 主キー列の値に対応するHolidayの行が存在すればtrue
   */
  boolean exists(Long holidayId);

  /**
   * 新規Holidayをデータベースに追加します。
   *
   * @param obj 追加対象のHoliday
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Holiday obj, LoginInfo loginInfo);

  /**
   * 休日を更新します。
   *
   * @param obj 更新対象のHoliday
   * @param loginInfo ログイン情報
   */
  void update(Holiday obj, LoginInfo loginInfo);

  /**
   * 休日を削除します。
   *
   * @param obj 削除対象のHoliday
   * @param loginInfo ログイン情報
   */
  void delete(Holiday obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して休日を削除します。
   *
   * @param holidayId 休日ID
   */
  void delete(Long holidayId);

  /**
   * Queryオブジェクトを指定して休日のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するHolidayのリスト
   */
  List<Holiday> findByQuery(Query query);

  /**
   * SQLを指定して休日のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するHolidayのリスト
   */
  List<Holiday> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のHolidayのリスト
   */
  List<Holiday> loadAll();

}
