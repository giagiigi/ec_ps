//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.ReviewPostDao;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * レビュー投稿
 *
 * @author System Integrator Corp.
 *
 */
public class ReviewPostDaoImpl implements ReviewPostDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ReviewPost, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ReviewPostDaoImpl() {
    genericDao = new GenericDaoImpl<ReviewPost, Long>(ReviewPost.class);
  }

  /**
   * SessionFactoryを取得します
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactoryを設定します
   * @param factory SessionFactoryのインスタンス
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 指定されたorm_rowidを持つレビュー投稿のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するReviewPostのインスタンス
   */
  public ReviewPost loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してレビュー投稿のインスタンスを取得します。
   * @param reviewId レビューID
   * @return 主キー列の値に対応するReviewPostのインスタンス
   */
  public ReviewPost load(Long reviewId) {
    Object[] params = new Object[]{reviewId};
    final String query = "SELECT * FROM REVIEW_POST"
        + " WHERE REVIEW_ID = ?";
    List<ReviewPost> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してレビュー投稿が既に存在するかどうかを返します。
   * @param reviewId レビューID
   * @return 主キー列の値に対応するReviewPostの行が存在すればtrue
   */
  public boolean exists(Long reviewId) {
    Object[] params = new Object[]{reviewId};
    final String query = "SELECT COUNT(*) FROM REVIEW_POST"
        + " WHERE REVIEW_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規ReviewPostをデータベースに追加します。
   * @param obj 追加対象のReviewPost
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ReviewPost obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規ReviewPostをデータベースに追加します。
   * @param obj 追加対象のReviewPost
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ReviewPost obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * レビュー投稿を更新します。
   * @param obj 更新対象のReviewPost
   */
  public void update(ReviewPost obj) {
    genericDao.update(obj);
  }

  /**
   * レビュー投稿を更新します。
   * @param obj 更新対象のReviewPost
   */
  public void update(ReviewPost obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * レビュー投稿を削除します。
   * @param obj 削除対象のReviewPost
   */
  public void delete(ReviewPost obj) {
    genericDao.delete(obj);
  }

  /**
   * レビュー投稿を削除します。
   * @param obj 削除対象のReviewPost
   */
  public void delete(ReviewPost obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してレビュー投稿を削除します。
   * @param reviewId レビューID
   */
  public void delete(Long reviewId) {
    Object[] params = new Object[]{reviewId};
    final String query = "DELETE FROM REVIEW_POST"
        + " WHERE REVIEW_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してレビュー投稿のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するReviewPostのリスト
   */
  public List<ReviewPost> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してレビュー投稿のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するReviewPostのリスト
   */
  public List<ReviewPost> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のReviewPostのリスト
   */
  public List<ReviewPost> loadAll() {
    return genericDao.loadAll();
  }

}
