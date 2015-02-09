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
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「レビュー点数集計(REVIEW_SUMMARY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ReviewSummaryDao extends GenericDao<ReviewSummary, Long> {

  /**
   * 指定されたorm_rowidを持つレビュー点数集計のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するReviewSummaryのインスタンス
   */
  ReviewSummary loadByRowid(Long id);

  /**
   * 主キー列の値を指定してレビュー点数集計のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するReviewSummaryのインスタンス
   */
  ReviewSummary load(String shopCode, String commodityCode);

  /**
   * 主キー列の値を指定してレビュー点数集計が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するReviewSummaryの行が存在すればtrue
   */
  boolean exists(String shopCode, String commodityCode);

  /**
   * 新規ReviewSummaryをデータベースに追加します。
   *
   * @param obj 追加対象のReviewSummary
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ReviewSummary obj, LoginInfo loginInfo);

  /**
   * レビュー点数集計を更新します。
   *
   * @param obj 更新対象のReviewSummary
   * @param loginInfo ログイン情報
   */
  void update(ReviewSummary obj, LoginInfo loginInfo);

  /**
   * レビュー点数集計を削除します。
   *
   * @param obj 削除対象のReviewSummary
   * @param loginInfo ログイン情報
   */
  void delete(ReviewSummary obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してレビュー点数集計を削除します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   */
  void delete(String shopCode, String commodityCode);

  /**
   * Queryオブジェクトを指定してレビュー点数集計のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するReviewSummaryのリスト
   */
  List<ReviewSummary> findByQuery(Query query);

  /**
   * SQLを指定してレビュー点数集計のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するReviewSummaryのリスト
   */
  List<ReviewSummary> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のReviewSummaryのリスト
   */
  List<ReviewSummary> loadAll();

}
