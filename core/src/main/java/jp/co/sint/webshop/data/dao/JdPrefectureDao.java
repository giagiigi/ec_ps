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
import jp.co.sint.webshop.data.dto.JdPrefecture;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「都道府県(PREFECTURE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface JdPrefectureDao extends GenericDao<JdPrefecture, Long> {

  /**
   * 指定されたorm_rowidを持つ都道府県のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPrefectureのインスタンス
   */
  JdPrefecture loadByRowid(Long id);

  /**
   * 主キー列の値を指定して都道府県のインスタンスを取得します。
   *
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するPrefectureのインスタンス
   */
  JdPrefecture load(String prefectureCode);

  /**
   * 主キー列の値を指定して都道府県が既に存在するかどうかを返します。
   *
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するPrefectureの行が存在すればtrue
   */
  boolean exists(String prefectureCode);

  /**
   * 新規Prefectureをデータベースに追加します。
   *
   * @param obj 追加対象のPrefecture
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdPrefecture obj, LoginInfo loginInfo);

  /**
   * 都道府県を更新します。
   *
   * @param obj 更新対象のPrefecture
   * @param loginInfo ログイン情報
   */
  void update(JdPrefecture obj, LoginInfo loginInfo);

  /**
   * 都道府県を削除します。
   *
   * @param obj 削除対象のPrefecture
   * @param loginInfo ログイン情報
   */
  void delete(JdPrefecture obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して都道府県を削除します。
   *
   * @param prefectureCode 都道府県コード
   */
  void delete(String prefectureCode);

  /**
   * Queryオブジェクトを指定して都道府県のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPrefectureのリスト
   */
  List<JdPrefecture> findByQuery(Query query);

  /**
   * SQLを指定して都道府県のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPrefectureのリスト
   */
  List<JdPrefecture> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPrefectureのリスト
   */
  List<JdPrefecture> loadAll();
  
  public List<JdPrefecture> loadAllByOrmRowid();

}
