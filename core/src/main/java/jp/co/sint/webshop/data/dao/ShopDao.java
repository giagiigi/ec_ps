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
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「ショップ(SHOP)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ShopDao extends GenericDao<Shop, Long> {

  /**
   * 指定されたorm_rowidを持つショップのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するShopのインスタンス
   */
  Shop loadByRowid(Long id);

  /**
   * 主キー列の値を指定してショップのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @return 主キー列の値に対応するShopのインスタンス
   */
  Shop load(String shopCode);

  /**
   * 主キー列の値を指定してショップが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @return 主キー列の値に対応するShopの行が存在すればtrue
   */
  boolean exists(String shopCode);

  /**
   * 新規Shopをデータベースに追加します。
   *
   * @param obj 追加対象のShop
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Shop obj, LoginInfo loginInfo);

  /**
   * ショップを更新します。
   *
   * @param obj 更新対象のShop
   * @param loginInfo ログイン情報
   */
  void update(Shop obj, LoginInfo loginInfo);

  /**
   * ショップを削除します。
   *
   * @param obj 削除対象のShop
   * @param loginInfo ログイン情報
   */
  void delete(Shop obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してショップを削除します。
   *
   * @param shopCode ショップコード
   */
  void delete(String shopCode);

  /**
   * Queryオブジェクトを指定してショップのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShopのリスト
   */
  List<Shop> findByQuery(Query query);

  /**
   * SQLを指定してショップのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShopのリスト
   */
  List<Shop> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のShopのリスト
   */
  List<Shop> loadAll();

}
