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
import jp.co.sint.webshop.data.dto.PopularRankingDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「人気ランキング詳細(POPULAR_RANKING_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface PopularRankingDetailDao extends GenericDao<PopularRankingDetail, Long> {

  /**
   * 指定されたorm_rowidを持つ人気ランキング詳細のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPopularRankingDetailのインスタンス
   */
  PopularRankingDetail loadByRowid(Long id);

  /**
   * 主キー列の値を指定して人気ランキング詳細のインスタンスを取得します。
   *
   * @param popularRankingCountId 人気ランキング集計ID
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するPopularRankingDetailのインスタンス
   */
  PopularRankingDetail load(Long popularRankingCountId, String shopCode, String commodityCode);

  /**
   * 主キー列の値を指定して人気ランキング詳細が既に存在するかどうかを返します。
   *
   * @param popularRankingCountId 人気ランキング集計ID
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するPopularRankingDetailの行が存在すればtrue
   */
  boolean exists(Long popularRankingCountId, String shopCode, String commodityCode);

  /**
   * 新規PopularRankingDetailをデータベースに追加します。
   *
   * @param obj 追加対象のPopularRankingDetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(PopularRankingDetail obj, LoginInfo loginInfo);

  /**
   * 人気ランキング詳細を更新します。
   *
   * @param obj 更新対象のPopularRankingDetail
   * @param loginInfo ログイン情報
   */
  void update(PopularRankingDetail obj, LoginInfo loginInfo);

  /**
   * 人気ランキング詳細を削除します。
   *
   * @param obj 削除対象のPopularRankingDetail
   * @param loginInfo ログイン情報
   */
  void delete(PopularRankingDetail obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して人気ランキング詳細を削除します。
   *
   * @param popularRankingCountId 人気ランキング集計ID
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   */
  void delete(Long popularRankingCountId, String shopCode, String commodityCode);

  /**
   * Queryオブジェクトを指定して人気ランキング詳細のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPopularRankingDetailのリスト
   */
  List<PopularRankingDetail> findByQuery(Query query);

  /**
   * SQLを指定して人気ランキング詳細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPopularRankingDetailのリスト
   */
  List<PopularRankingDetail> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPopularRankingDetailのリスト
   */
  List<PopularRankingDetail> loadAll();

}
