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
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「在庫(STOCK)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CustomerCardUseInfoDao extends GenericDao<CustomerCardUseInfo, Long> {

  /**
   * 指定されたorm_rowidを持つ在庫のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するStockのインスタンス
   */
  CustomerCardUseInfo loadByRowid(Long id);

  /**
   * 主キー列の値を指定して在庫のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockのインスタンス
   */
  CustomerCardUseInfo load(String customerCode,Long cardId,String orderNo);
  
  /**
   * 主キー列の値を指定して在庫のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockのインスタンス
   */
  List<CustomerCardUseInfo> loadByCustomerCode(String customerCode);
  
  List<CustomerCardUseInfo> loadByOrderNo(String orderNo);
  
  List<CustomerCardUseInfo> loadByOrderNoOrderBy(String orderNo,String innerJoin,String orderByMode);

  /**
   * 主キー列の値を指定して在庫が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockの行が存在すればtrue
   */
  boolean exists(String customerCode,Long cardId,String orderNo);

  /**
   * 新規Stockをデータベースに追加します。
   *
   * @param obj 追加対象のStock
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CustomerCardUseInfo obj, LoginInfo loginInfo);

  /**
   * 在庫を更新します。
   *
   * @param obj 更新対象のStock
   * @param loginInfo ログイン情報
   */
  void update(CustomerCardUseInfo obj, LoginInfo loginInfo);

  /**
   * 在庫を削除します。
   *
   * @param obj 削除対象のStock
   * @param loginInfo ログイン情報
   */
  void delete(CustomerCardUseInfo obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して在庫を削除します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  void delete(String customerCode,Long cardId,String orderNo);

  /**
   * Queryオブジェクトを指定して在庫のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockのリスト
   */
  List<CustomerCardUseInfo> findByQuery(Query query);

  /**
   * SQLを指定して在庫のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockのリスト
   */
  List<CustomerCardUseInfo> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のStockのリスト
   */
  List<CustomerCardUseInfo> loadAll();

}
