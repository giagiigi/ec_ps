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
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「カテゴリ(CATEGORY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CategoryDao extends GenericDao<Category, Long> {

  /**
   * 指定されたorm_rowidを持つカテゴリのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCategoryのインスタンス
   */
  Category loadByRowid(Long id);

  /**
   * 主キー列の値を指定してカテゴリのインスタンスを取得します。
   *
   * @param categoryCode カテゴリコード
   * @return 主キー列の値に対応するCategoryのインスタンス
   */
  Category load(String categoryCode);

  /**
   * 主キー列の値を指定してカテゴリが既に存在するかどうかを返します。
   *
   * @param categoryCode カテゴリコード
   * @return 主キー列の値に対応するCategoryの行が存在すればtrue
   */
  boolean exists(String categoryCode);

  /**
   * 新規Categoryをデータベースに追加します。
   *
   * @param obj 追加対象のCategory
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Category obj, LoginInfo loginInfo);

  /**
   * カテゴリを更新します。
   *
   * @param obj 更新対象のCategory
   * @param loginInfo ログイン情報
   */
  void update(Category obj, LoginInfo loginInfo);

  /**
   * カテゴリを削除します。
   *
   * @param obj 削除対象のCategory
   * @param loginInfo ログイン情報
   */
  void delete(Category obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してカテゴリを削除します。
   *
   * @param categoryCode カテゴリコード
   */
  void delete(String categoryCode);

  /**
   * Queryオブジェクトを指定してカテゴリのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCategoryのリスト
   */
  List<Category> findByQuery(Query query);

  /**
   * SQLを指定してカテゴリのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCategoryのリスト
   */
  List<Category> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCategoryのリスト
   */
  List<Category> loadAll();

}
