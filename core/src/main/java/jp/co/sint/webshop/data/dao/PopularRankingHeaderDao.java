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
import jp.co.sint.webshop.data.dto.PopularRankingHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「人気ランキングヘッダ(POPULAR_RANKING_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface PopularRankingHeaderDao extends GenericDao<PopularRankingHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ人気ランキングヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPopularRankingHeaderのインスタンス
   */
  PopularRankingHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して人気ランキングヘッダのインスタンスを取得します。
   *
   * @param popularRankingCountId 人気ランキング集計ID
   * @return 主キー列の値に対応するPopularRankingHeaderのインスタンス
   */
  PopularRankingHeader load(Long popularRankingCountId);

  /**
   * 主キー列の値を指定して人気ランキングヘッダが既に存在するかどうかを返します。
   *
   * @param popularRankingCountId 人気ランキング集計ID
   * @return 主キー列の値に対応するPopularRankingHeaderの行が存在すればtrue
   */
  boolean exists(Long popularRankingCountId);

  /**
   * 新規PopularRankingHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のPopularRankingHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(PopularRankingHeader obj, LoginInfo loginInfo);

  /**
   * 人気ランキングヘッダを更新します。
   *
   * @param obj 更新対象のPopularRankingHeader
   * @param loginInfo ログイン情報
   */
  void update(PopularRankingHeader obj, LoginInfo loginInfo);

  /**
   * 人気ランキングヘッダを削除します。
   *
   * @param obj 削除対象のPopularRankingHeader
   * @param loginInfo ログイン情報
   */
  void delete(PopularRankingHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して人気ランキングヘッダを削除します。
   *
   * @param popularRankingCountId 人気ランキング集計ID
   */
  void delete(Long popularRankingCountId);

  /**
   * Queryオブジェクトを指定して人気ランキングヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPopularRankingHeaderのリスト
   */
  List<PopularRankingHeader> findByQuery(Query query);

  /**
   * SQLを指定して人気ランキングヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPopularRankingHeaderのリスト
   */
  List<PopularRankingHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPopularRankingHeaderのリスト
   */
  List<PopularRankingHeader> loadAll();

}
