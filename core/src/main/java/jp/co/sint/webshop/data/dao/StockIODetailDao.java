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
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「入出庫明細(STOCK_IO_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface StockIODetailDao extends GenericDao<StockIODetail, Long> {

  /**
   * 指定されたorm_rowidを持つ入出庫明細のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するStockIODetailのインスタンス
   */
  StockIODetail loadByRowid(Long id);

  /**
   * 主キー列の値を指定して入出庫明細のインスタンスを取得します。
   *
   * @param stockIOId 入出庫行ID
   * @return 主キー列の値に対応するStockIODetailのインスタンス
   */
  StockIODetail load(Long stockIOId);

  /**
   * 主キー列の値を指定して入出庫明細が既に存在するかどうかを返します。
   *
   * @param stockIOId 入出庫行ID
   * @return 主キー列の値に対応するStockIODetailの行が存在すればtrue
   */
  boolean exists(Long stockIOId);

  /**
   * 新規StockIODetailをデータベースに追加します。
   *
   * @param obj 追加対象のStockIODetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(StockIODetail obj, LoginInfo loginInfo);

  /**
   * 入出庫明細を更新します。
   *
   * @param obj 更新対象のStockIODetail
   * @param loginInfo ログイン情報
   */
  void update(StockIODetail obj, LoginInfo loginInfo);

  /**
   * 入出庫明細を削除します。
   *
   * @param obj 削除対象のStockIODetail
   * @param loginInfo ログイン情報
   */
  void delete(StockIODetail obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して入出庫明細を削除します。
   *
   * @param stockIOId 入出庫行ID
   */
  void delete(Long stockIOId);

  /**
   * Queryオブジェクトを指定して入出庫明細のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockIODetailのリスト
   */
  List<StockIODetail> findByQuery(Query query);

  /**
   * SQLを指定して入出庫明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockIODetailのリスト
   */
  List<StockIODetail> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のStockIODetailのリスト
   */
  List<StockIODetail> loadAll();

}
