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
import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「在庫状況(STOCK_STATUS)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface StockStatusDao extends GenericDao<StockStatus, Long> {

  /**
   * 指定されたorm_rowidを持つ在庫状況のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するStockStatusのインスタンス
   */
  StockStatus loadByRowid(Long id);

  /**
   * 主キー列の値を指定して在庫状況のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param stockStatusNo 在庫状況番号
   * @return 主キー列の値に対応するStockStatusのインスタンス
   */
  StockStatus load(String shopCode, Long stockStatusNo);

  /**
   * 主キー列の値を指定して在庫状況が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param stockStatusNo 在庫状況番号
   * @return 主キー列の値に対応するStockStatusの行が存在すればtrue
   */
  boolean exists(String shopCode, Long stockStatusNo);

  /**
   * 新規StockStatusをデータベースに追加します。
   *
   * @param obj 追加対象のStockStatus
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(StockStatus obj, LoginInfo loginInfo);

  /**
   * 在庫状況を更新します。
   *
   * @param obj 更新対象のStockStatus
   * @param loginInfo ログイン情報
   */
  void update(StockStatus obj, LoginInfo loginInfo);

  /**
   * 在庫状況を削除します。
   *
   * @param obj 削除対象のStockStatus
   * @param loginInfo ログイン情報
   */
  void delete(StockStatus obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して在庫状況を削除します。
   *
   * @param shopCode ショップコード
   * @param stockStatusNo 在庫状況番号
   */
  void delete(String shopCode, Long stockStatusNo);

  /**
   * Queryオブジェクトを指定して在庫状況のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockStatusのリスト
   */
  List<StockStatus> findByQuery(Query query);

  /**
   * SQLを指定して在庫状況のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockStatusのリスト
   */
  List<StockStatus> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のStockStatusのリスト
   */
  List<StockStatus> loadAll();

}
