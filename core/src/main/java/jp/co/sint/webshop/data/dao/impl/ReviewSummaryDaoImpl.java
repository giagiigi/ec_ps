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
import jp.co.sint.webshop.data.dao.ReviewSummaryDao;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * レビュー点数集計
 *
 * @author System Integrator Corp.
 *
 */
public class ReviewSummaryDaoImpl implements ReviewSummaryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ReviewSummary, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ReviewSummaryDaoImpl() {
    genericDao = new GenericDaoImpl<ReviewSummary, Long>(ReviewSummary.class);
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
   * 指定されたorm_rowidを持つレビュー点数集計のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するReviewSummaryのインスタンス
   */
  public ReviewSummary loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してレビュー点数集計のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するReviewSummaryのインスタンス
   */
  public ReviewSummary load(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode};
    final String query = "SELECT * FROM REVIEW_SUMMARY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<ReviewSummary> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してレビュー点数集計が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するReviewSummaryの行が存在すればtrue
   */
  public boolean exists(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM REVIEW_SUMMARY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規ReviewSummaryをデータベースに追加します。
   * @param obj 追加対象のReviewSummary
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ReviewSummary obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規ReviewSummaryをデータベースに追加します。
   * @param obj 追加対象のReviewSummary
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ReviewSummary obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * レビュー点数集計を更新します。
   * @param obj 更新対象のReviewSummary
   */
  public void update(ReviewSummary obj) {
    genericDao.update(obj);
  }

  /**
   * レビュー点数集計を更新します。
   * @param obj 更新対象のReviewSummary
   */
  public void update(ReviewSummary obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * レビュー点数集計を削除します。
   * @param obj 削除対象のReviewSummary
   */
  public void delete(ReviewSummary obj) {
    genericDao.delete(obj);
  }

  /**
   * レビュー点数集計を削除します。
   * @param obj 削除対象のReviewSummary
   */
  public void delete(ReviewSummary obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してレビュー点数集計を削除します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   */
  public void delete(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode};
    final String query = "DELETE FROM REVIEW_SUMMARY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してレビュー点数集計のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するReviewSummaryのリスト
   */
  public List<ReviewSummary> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してレビュー点数集計のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するReviewSummaryのリスト
   */
  public List<ReviewSummary> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のReviewSummaryのリスト
   */
  public List<ReviewSummary> loadAll() {
    return genericDao.loadAll();
  }

}
