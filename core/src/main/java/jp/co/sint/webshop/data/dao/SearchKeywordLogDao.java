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
import jp.co.sint.webshop.data.dto.SearchKeywordLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「検索キーワードログ(SEARCH_KEYWORD_LOG)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface SearchKeywordLogDao extends GenericDao<SearchKeywordLog, Long> {

  /**
   * 指定されたorm_rowidを持つ検索キーワードログのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するSearchKeywordLogのインスタンス
   */
  SearchKeywordLog loadByRowid(Long id);

  /**
   * 主キー列の値を指定して検索キーワードログのインスタンスを取得します。
   *
   * @param searchKeywordLogId 検索キーワードログID
   * @return 主キー列の値に対応するSearchKeywordLogのインスタンス
   */
  SearchKeywordLog load(Long searchKeywordLogId);

  /**
   * 主キー列の値を指定して検索キーワードログが既に存在するかどうかを返します。
   *
   * @param searchKeywordLogId 検索キーワードログID
   * @return 主キー列の値に対応するSearchKeywordLogの行が存在すればtrue
   */
  boolean exists(Long searchKeywordLogId);

  /**
   * 新規SearchKeywordLogをデータベースに追加します。
   *
   * @param obj 追加対象のSearchKeywordLog
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(SearchKeywordLog obj, LoginInfo loginInfo);

  /**
   * 検索キーワードログを更新します。
   *
   * @param obj 更新対象のSearchKeywordLog
   * @param loginInfo ログイン情報
   */
  void update(SearchKeywordLog obj, LoginInfo loginInfo);

  /**
   * 検索キーワードログを削除します。
   *
   * @param obj 削除対象のSearchKeywordLog
   * @param loginInfo ログイン情報
   */
  void delete(SearchKeywordLog obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して検索キーワードログを削除します。
   *
   * @param searchKeywordLogId 検索キーワードログID
   */
  void delete(Long searchKeywordLogId);

  /**
   * Queryオブジェクトを指定して検索キーワードログのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するSearchKeywordLogのリスト
   */
  List<SearchKeywordLog> findByQuery(Query query);

  /**
   * SQLを指定して検索キーワードログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するSearchKeywordLogのリスト
   */
  List<SearchKeywordLog> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のSearchKeywordLogのリスト
   */
  List<SearchKeywordLog> loadAll();

}
