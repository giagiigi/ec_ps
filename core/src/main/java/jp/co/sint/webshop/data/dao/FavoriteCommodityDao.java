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
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「お気に入り商品(FAVORITE_COMMODITY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface FavoriteCommodityDao extends GenericDao<FavoriteCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つお気に入り商品のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するFavoriteCommodityのインスタンス
   */
  FavoriteCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定してお気に入り商品のインスタンスを取得します。
   *
   * @param customerCode 顧客コード
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するFavoriteCommodityのインスタンス
   */
  FavoriteCommodity load(String customerCode, String shopCode, String skuCode);

  /**
   * 主キー列の値を指定してお気に入り商品が既に存在するかどうかを返します。
   *
   * @param customerCode 顧客コード
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するFavoriteCommodityの行が存在すればtrue
   */
  boolean exists(String customerCode, String shopCode, String skuCode);

  /**
   * 新規FavoriteCommodityをデータベースに追加します。
   *
   * @param obj 追加対象のFavoriteCommodity
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(FavoriteCommodity obj, LoginInfo loginInfo);

  /**
   * お気に入り商品を更新します。
   *
   * @param obj 更新対象のFavoriteCommodity
   * @param loginInfo ログイン情報
   */
  void update(FavoriteCommodity obj, LoginInfo loginInfo);

  /**
   * お気に入り商品を削除します。
   *
   * @param obj 削除対象のFavoriteCommodity
   * @param loginInfo ログイン情報
   */
  void delete(FavoriteCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してお気に入り商品を削除します。
   *
   * @param customerCode 顧客コード
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  void delete(String customerCode, String shopCode, String skuCode);

  /**
   * Queryオブジェクトを指定してお気に入り商品のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するFavoriteCommodityのリスト
   */
  List<FavoriteCommodity> findByQuery(Query query);

  /**
   * SQLを指定してお気に入り商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するFavoriteCommodityのリスト
   */
  List<FavoriteCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のFavoriteCommodityのリスト
   */
  List<FavoriteCommodity> loadAll();

}
