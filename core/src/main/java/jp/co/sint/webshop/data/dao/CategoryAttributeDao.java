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
import jp.co.sint.webshop.data.dto.CategoryAttribute;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「カテゴリ属性名称(CATEGORY_ATTRIBUTE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CategoryAttributeDao extends GenericDao<CategoryAttribute, Long> {

  /**
   * 指定されたorm_rowidを持つカテゴリ属性名称のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCategoryAttributeのインスタンス
   */
  CategoryAttribute loadByRowid(Long id);

  /**
   * 主キー列の値を指定してカテゴリ属性名称のインスタンスを取得します。
   *
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   * @return 主キー列の値に対応するCategoryAttributeのインスタンス
   */
  CategoryAttribute load(String categoryCode, Long categoryAttributeNo);

  /**
   * 主キー列の値を指定してカテゴリ属性名称が既に存在するかどうかを返します。
   *
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   * @return 主キー列の値に対応するCategoryAttributeの行が存在すればtrue
   */
  boolean exists(String categoryCode, Long categoryAttributeNo);

  /**
   * 新規CategoryAttributeをデータベースに追加します。
   *
   * @param obj 追加対象のCategoryAttribute
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CategoryAttribute obj, LoginInfo loginInfo);

  /**
   * カテゴリ属性名称を更新します。
   *
   * @param obj 更新対象のCategoryAttribute
   * @param loginInfo ログイン情報
   */
  void update(CategoryAttribute obj, LoginInfo loginInfo);

  /**
   * カテゴリ属性名称を削除します。
   *
   * @param obj 削除対象のCategoryAttribute
   * @param loginInfo ログイン情報
   */
  void delete(CategoryAttribute obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してカテゴリ属性名称を削除します。
   *
   * @param categoryCode カテゴリコード
   * @param categoryAttributeNo カテゴリ属性番号
   */
  void delete(String categoryCode, Long categoryAttributeNo);

  /**
   * Queryオブジェクトを指定してカテゴリ属性名称のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCategoryAttributeのリスト
   */
  List<CategoryAttribute> findByQuery(Query query);

  /**
   * SQLを指定してカテゴリ属性名称のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCategoryAttributeのリスト
   */
  List<CategoryAttribute> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCategoryAttributeのリスト
   */
  List<CategoryAttribute> loadAll();

}
