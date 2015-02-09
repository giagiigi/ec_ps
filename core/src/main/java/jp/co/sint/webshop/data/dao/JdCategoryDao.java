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
import jp.co.sint.webshop.data.dto.JdCategory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「京东カテゴリ(JD_Category)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface JdCategoryDao extends GenericDao<JdCategory, Long> {

  /**
   * 指定されたorm_rowidを持つカテゴリのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するJdCategoryのインスタンス
   */
  JdCategory loadByRowid(Long id);

  /**
   * 主キー列の値を指定して京东カテゴリのインスタンスを取得します。
   *
   * @param categoryId 类目编号
   * @return 主キー列の値に対応するJdCategoryのインスタンス
   */
  JdCategory load(String categoryId);

  /**
   * 主キー列の値を指定して京东カテゴリが既に存在するかどうかを返します。
   *
   * @param categoryId 类目编号
   * @return 主キー列の値に対応するJdCategoryの行が存在すればtrue
   */
  boolean exists(String categoryId);

  /**
   * 新規JdCategoryをデータベースに追加します。
   *
   * @param obj 追加対象のJdCategory
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdCategory obj, LoginInfo loginInfo);

  /**
   * 京东カテゴリを更新します。
   *
   * @param obj 更新対象のJdCategory
   * @param loginInfo ログイン情報
   */
  void update(JdCategory obj, LoginInfo loginInfo);

  /**
   * 京东カテゴリを削除します。
   *
   * @param obj 削除対象のJdCategory
   * @param loginInfo ログイン情報
   */
  void delete(JdCategory obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して京东カテゴリを削除します。
   *
   * @param categoryId 类目编号
   */
  void delete(String categoryId);

  /**
   * Queryオブジェクトを指定して京东カテゴリのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するJdCategoryのリスト
   */
  List<JdCategory> findByQuery(Query query);

  /**
   * SQLを指定して京东カテゴリのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するJdCategoryのリスト
   */
  List<JdCategory> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のJdCategoryのリスト
   */
  List<JdCategory> loadAll();
  
  /**
   * 查询所有子类别
   * @return
   */
  List<JdCategory> loadAllChild();

}
