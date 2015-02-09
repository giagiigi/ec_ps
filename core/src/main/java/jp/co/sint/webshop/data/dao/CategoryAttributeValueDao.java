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
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「カテゴリ属性値(CATEGORY_ATTRIBUTE_VALUE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CategoryAttributeValueDao extends GenericDao<CategoryAttributeValue, Long> {

  /**
   * 指定されたorm_rowidを持つカテゴリ属性値のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCategoryAttributeValueのインスタンス
   */
  CategoryAttributeValue loadByRowid(Long id);

  /**
   * 主キー列の値を指定してカテゴリ属性値のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCategoryAttributeValueのインスタンス
   */
  CategoryAttributeValue load(String shopCode, String categoryCode, Long categoryAttributeNo, String commodityCode);

  /**
   * 主キー列の値を指定してカテゴリ属性値が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCategoryAttributeValueの行が存在すればtrue
   */
  boolean exists(String shopCode, String categoryCode, Long categoryAttributeNo, String commodityCode);

  /**
   * 新規CategoryAttributeValueをデータベースに追加します。
   *
   * @param obj 追加対象のCategoryAttributeValue
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CategoryAttributeValue obj, LoginInfo loginInfo);

  /**
   * カテゴリ属性値を更新します。
   *
   * @param obj 更新対象のCategoryAttributeValue
   * @param loginInfo ログイン情報
   */
  void update(CategoryAttributeValue obj, LoginInfo loginInfo);

  /**
   * カテゴリ属性値を削除します。
   *
   * @param obj 削除対象のCategoryAttributeValue
   * @param loginInfo ログイン情報
   */
  void delete(CategoryAttributeValue obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してカテゴリ属性値を削除します。
   *
   * @param shopCode ショップコード
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   * @param commodityCode 商品コード
   */
  void delete(String shopCode, String categoryCode, Long categoryAttributeNo, String commodityCode);

  /**
   * Queryオブジェクトを指定してカテゴリ属性値のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCategoryAttributeValueのリスト
   */
  List<CategoryAttributeValue> findByQuery(Query query);

  /**
   * SQLを指定してカテゴリ属性値のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCategoryAttributeValueのリスト
   */
  List<CategoryAttributeValue> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCategoryAttributeValueのリスト
   */
  List<CategoryAttributeValue> loadAll();

  List<CategoryAttributeValue> loadAllByCommodityCode(String shopCode,String commodityCode);
}
