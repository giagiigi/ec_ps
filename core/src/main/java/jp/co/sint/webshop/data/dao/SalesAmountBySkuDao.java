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
import jp.co.sint.webshop.data.dto.SalesAmountBySku;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「SKU別売上集計(SALES_AMOUNT_BY_SKU)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface SalesAmountBySkuDao extends GenericDao<SalesAmountBySku, Long> {

  /**
   * 指定されたorm_rowidを持つSKU別売上集計のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するSalesAmountBySkuのインスタンス
   */
  SalesAmountBySku loadByRowid(Long id);

  /**
   * 主キー列の値を指定してSKU別売上集計のインスタンスを取得します。
   *
   * @param salesAmountBySkuId SKU別売上集計ID
   * @return 主キー列の値に対応するSalesAmountBySkuのインスタンス
   */
  SalesAmountBySku load(Long salesAmountBySkuId);

  /**
   * 主キー列の値を指定してSKU別売上集計が既に存在するかどうかを返します。
   *
   * @param salesAmountBySkuId SKU別売上集計ID
   * @return 主キー列の値に対応するSalesAmountBySkuの行が存在すればtrue
   */
  boolean exists(Long salesAmountBySkuId);

  /**
   * 新規SalesAmountBySkuをデータベースに追加します。
   *
   * @param obj 追加対象のSalesAmountBySku
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(SalesAmountBySku obj, LoginInfo loginInfo);

  /**
   * SKU別売上集計を更新します。
   *
   * @param obj 更新対象のSalesAmountBySku
   * @param loginInfo ログイン情報
   */
  void update(SalesAmountBySku obj, LoginInfo loginInfo);

  /**
   * SKU別売上集計を削除します。
   *
   * @param obj 削除対象のSalesAmountBySku
   * @param loginInfo ログイン情報
   */
  void delete(SalesAmountBySku obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してSKU別売上集計を削除します。
   *
   * @param salesAmountBySkuId SKU別売上集計ID
   */
  void delete(Long salesAmountBySkuId);

  /**
   * Queryオブジェクトを指定してSKU別売上集計のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するSalesAmountBySkuのリスト
   */
  List<SalesAmountBySku> findByQuery(Query query);

  /**
   * SQLを指定してSKU別売上集計のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するSalesAmountBySkuのリスト
   */
  List<SalesAmountBySku> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のSalesAmountBySkuのリスト
   */
  List<SalesAmountBySku> loadAll();

}
