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
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「レビュー投稿(REVIEW_POST)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ReviewPostDao extends GenericDao<ReviewPost, Long> {

  /**
   * 指定されたorm_rowidを持つレビュー投稿のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するReviewPostのインスタンス
   */
  ReviewPost loadByRowid(Long id);

  /**
   * 主キー列の値を指定してレビュー投稿のインスタンスを取得します。
   *
   * @param reviewId レビューID
   * @return 主キー列の値に対応するReviewPostのインスタンス
   */
  ReviewPost load(Long reviewId);

  /**
   * 主キー列の値を指定してレビュー投稿が既に存在するかどうかを返します。
   *
   * @param reviewId レビューID
   * @return 主キー列の値に対応するReviewPostの行が存在すればtrue
   */
  boolean exists(Long reviewId);

  /**
   * 新規ReviewPostをデータベースに追加します。
   *
   * @param obj 追加対象のReviewPost
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ReviewPost obj, LoginInfo loginInfo);

  /**
   * レビュー投稿を更新します。
   *
   * @param obj 更新対象のReviewPost
   * @param loginInfo ログイン情報
   */
  void update(ReviewPost obj, LoginInfo loginInfo);

  /**
   * レビュー投稿を削除します。
   *
   * @param obj 削除対象のReviewPost
   * @param loginInfo ログイン情報
   */
  void delete(ReviewPost obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してレビュー投稿を削除します。
   *
   * @param reviewId レビューID
   */
  void delete(Long reviewId);

  /**
   * Queryオブジェクトを指定してレビュー投稿のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するReviewPostのリスト
   */
  List<ReviewPost> findByQuery(Query query);

  /**
   * SQLを指定してレビュー投稿のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するReviewPostのリスト
   */
  List<ReviewPost> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のReviewPostのリスト
   */
  List<ReviewPost> loadAll();

}
