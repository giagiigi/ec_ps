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
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「カテゴリ陳列商品(CATEGORY_COMMODITY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CategoryCommodityDao extends GenericDao<CategoryCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つカテゴリ陳列商品のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCategoryCommodityのインスタンス
   */
  CategoryCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定してカテゴリ陳列商品のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCategoryCommodityのインスタンス
   */
  CategoryCommodity load(String shopCode, String categoryCode, String commodityCode);

  /**
   * 主キー列の値を指定してカテゴリ陳列商品が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCategoryCommodityの行が存在すればtrue
   */
  boolean exists(String shopCode, String categoryCode, String commodityCode);

  /**
   * 新規CategoryCommodityをデータベースに追加します。
   *
   * @param obj 追加対象のCategoryCommodity
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CategoryCommodity obj, LoginInfo loginInfo);

  /**
   * カテゴリ陳列商品を更新します。
   *
   * @param obj 更新対象のCategoryCommodity
   * @param loginInfo ログイン情報
   */
  void update(CategoryCommodity obj, LoginInfo loginInfo);

  /**
   * カテゴリ陳列商品を削除します。
   *
   * @param obj 削除対象のCategoryCommodity
   * @param loginInfo ログイン情報
   */
  void delete(CategoryCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してカテゴリ陳列商品を削除します。
   *
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param commodityCode 商品コード
   */
  void delete(String shopCode, String categoryCode, String commodityCode);

  /**
   * Queryオブジェクトを指定してカテゴリ陳列商品のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCategoryCommodityのリスト
   */
  List<CategoryCommodity> findByQuery(Query query);

  /**
   * SQLを指定してカテゴリ陳列商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCategoryCommodityのリスト
   */
  List<CategoryCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCategoryCommodityのリスト
   */
  List<CategoryCommodity> loadAll();

  List<CategoryCommodity> loadAllByCommodityCode(String shopCode,String commodityCode);
}
