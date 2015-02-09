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
import jp.co.sint.webshop.data.dto.CartHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「购物车履历(CART_HISTORY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author Kousen.
 *
 */
public interface CartHistoryDao extends GenericDao<CartHistory, Long> {

  /**
   * 指定されたorm_rowidを持つ购物车履历のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCartHistoryのインスタンス
   */
  CartHistory loadByRowid(Long id);

  /**
   * 主キー列の値を指定して购物车履历のインスタンスを取得します。
   *
   * @param customerCode 顾客编号
   * @param shopCode 店铺编号
   * @param skuCode SKU编号
   * @return 主キー列の値に対応するCartHistoryのインスタンス
   */
  CartHistory load(String customerCode, String shopCode, String skuCode);

  /**
   * 主キー列の値を指定して购物车履历が既に存在するかどうかを返します。
   *
   * @param customerCode 顾客编号
   * @param shopCode 店铺编号
   * @param skuCode SKU编号
   * @return 主キー列の値に対応するCartHistoryの行が存在すればtrue
   */
  boolean exists(String customerCode, String shopCode, String skuCode);

  /**
   * 新規CartHistoryをデータベースに追加します。
   *
   * @param obj 追加対象のCartHistory
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CartHistory obj, LoginInfo loginInfo);

  /**
   * 购物车履历を更新します。
   *
   * @param obj 更新対象のCartHistory
   * @param loginInfo ログイン情報
   */
  void update(CartHistory obj, LoginInfo loginInfo);

  /**
   * 购物车履历を削除します。
   *
   * @param obj 削除対象のCartHistory
   * @param loginInfo ログイン情報
   */
  void delete(CartHistory obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して购物车履历を削除します。
   *
   * @param customerCode 顾客编号
   * @param shopCode 店铺编号
   * @param skuCode SKU编号
   */
  void delete(String customerCode, String shopCode, String skuCode);

  /**
   * Queryオブジェクトを指定して购物车履历のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCartHistoryのリスト
   */
  List<CartHistory> findByQuery(Query query);

  /**
   * SQLを指定して购物车履历のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCartHistoryのリスト
   */
  List<CartHistory> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCartHistoryのリスト
   */
  List<CartHistory> loadAll();

}
