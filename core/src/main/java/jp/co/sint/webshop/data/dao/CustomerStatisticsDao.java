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
import jp.co.sint.webshop.data.dto.CustomerStatistics;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「顧客統計(CUSTOMER_STATISTICS)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerStatisticsDao extends GenericDao<CustomerStatistics, Long> {

  /**
   * 指定されたorm_rowidを持つ顧客統計のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCustomerStatisticsのインスタンス
   */
  CustomerStatistics loadByRowid(Long id);

  /**
   * 主キー列の値を指定して顧客統計のインスタンスを取得します。
   *
   * @param customerStatisticsId 顧客統計ID
   * @return 主キー列の値に対応するCustomerStatisticsのインスタンス
   */
  CustomerStatistics load(Long customerStatisticsId);

  /**
   * 主キー列の値を指定して顧客統計が既に存在するかどうかを返します。
   *
   * @param customerStatisticsId 顧客統計ID
   * @return 主キー列の値に対応するCustomerStatisticsの行が存在すればtrue
   */
  boolean exists(Long customerStatisticsId);

  /**
   * 新規CustomerStatisticsをデータベースに追加します。
   *
   * @param obj 追加対象のCustomerStatistics
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerStatistics obj, LoginInfo loginInfo);

  /**
   * 顧客統計を更新します。
   *
   * @param obj 更新対象のCustomerStatistics
   * @param loginInfo ログイン情報
   */
  void update(CustomerStatistics obj, LoginInfo loginInfo);

  /**
   * 顧客統計を削除します。
   *
   * @param obj 削除対象のCustomerStatistics
   * @param loginInfo ログイン情報
   */
  void delete(CustomerStatistics obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して顧客統計を削除します。
   *
   * @param customerStatisticsId 顧客統計ID
   */
  void delete(Long customerStatisticsId);

  /**
   * Queryオブジェクトを指定して顧客統計のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerStatisticsのリスト
   */
  List<CustomerStatistics> findByQuery(Query query);

  /**
   * SQLを指定して顧客統計のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerStatisticsのリスト
   */
  List<CustomerStatistics> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCustomerStatisticsのリスト
   */
  List<CustomerStatistics> loadAll();

}
