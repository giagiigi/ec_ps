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
import jp.co.sint.webshop.data.dto.JdBatchTime; 
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「タグ(TAG)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface JdBatchTimeDao extends GenericDao<JdBatchTime, Long> {

  /**
   * 指定されたorm_rowidを持つタグのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するTagのインスタンス
   */
  JdBatchTime loadByRowid(Long id);

  /**
   * 主キー列の値を指定してタグのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param BatchTimeCode タグコード
   * @return 主キー列の値に対応するBatchTimeのインスタンス
   */
  JdBatchTime load(); 
  /**
   * 主キー列の値を指定してタグが既に存在するかどうかを返します。 
   * @return 主キー列の値に対応するBatchTimeの行が存在すればtrue
   */
  boolean exists();

  /**
   * 新規BatchTimeをデータベースに追加します。
   *
   * @param obj 追加対象のBatchTime
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdBatchTime obj, LoginInfo loginInfo);

  /**
   * タグを更新します。
   *
   * @param obj 更新対象のBatchTime
   * @param loginInfo ログイン情報
   */
  void update(JdBatchTime obj, LoginInfo loginInfo);

  /**
   * タグを削除します。
   *
   * @param obj 削除対象のBatchTime
   * @param loginInfo ログイン情報
   */
  void delete(JdBatchTime obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してタグを削除します。
   * 
   */
  void delete();

  /**
   * Queryオブジェクトを指定してタグのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するBatchTimeのリスト
   */
  List<JdBatchTime> findByQuery(Query query);

  /**
   * SQLを指定してタグのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するBatchTimeのリスト
   */
  List<JdBatchTime> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のBatchTimeのリスト
   */
  List<JdBatchTime> loadAll();

}
