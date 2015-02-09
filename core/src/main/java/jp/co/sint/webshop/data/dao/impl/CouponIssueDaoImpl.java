package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.CouponIssueDao;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;


public class CouponIssueDaoImpl implements CouponIssueDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CouponIssue, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CouponIssueDaoImpl() {
    genericDao = new GenericDaoImpl<CouponIssue, Long>(CouponIssue.class);
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
   * 指定されたorm_rowidを持つクーポンルールのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCouponIssueのインスタンス
   */
  public CouponIssue loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してクーポンルールのインスタンスを取得します。
   * @param CouponIssueNo クーポンルール番号
   * @return 主キー列の値に対応するCouponIssueのインスタンス
   */
  public CouponIssue load(String shopCode, Long CouponIssueNo) {
    Object[] params = new Object[]{shopCode, CouponIssueNo};
    final String query = "SELECT * FROM COUPON_ISSUE"
        + " WHERE SHOP_CODE = ? AND COUPON_ISSUE_NO = ?";
    List<CouponIssue> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してクーポンルールが既に存在するかどうかを返します。
   * @param CouponIssueNo クーポンルール番号
   * @return 主キー列の値に対応するCouponIssueの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long CouponIssueNo) {
    Object[] params = new Object[]{shopCode, CouponIssueNo};
    final String query = "SELECT COUNT(*) FROM COUPON_ISSUE"
        + " WHERE SHOP_CODE = ? AND COUPON_ISSUE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CouponIssueをデータベースに追加します。
   * @param obj 追加対象のCouponIssue
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CouponIssue obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CouponIssueをデータベースに追加します。
   * @param obj 追加対象のCouponIssue
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CouponIssue obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * クーポンルールを更新します。
   * @param obj 更新対象のCouponIssue
   */
  public void update(CouponIssue obj) {
    genericDao.update(obj);
  }

  /**
   * クーポンルールを更新します。
   * @param obj 更新対象のCouponIssue
   */
  public void update(CouponIssue obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * クーポンルールを削除します。
   * @param obj 削除対象のCouponIssue
   */
  public void delete(CouponIssue obj) {
    genericDao.delete(obj);
  }

  /**
   * クーポンルールを削除します。
   * @param obj 削除対象のCouponIssue
   */
  public void delete(CouponIssue obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してクーポンルールを削除します。
   * @param CouponIssueNo クーポンルール番号
   */
  public void delete(String shopCode, Long CouponIssueNo) {
    Object[] params = new Object[]{shopCode, CouponIssueNo};
    final String query = "DELETE FROM COUPON_ISSUE"
        + " WHERE SHOP_CODE = ? AND COUPON_ISSUE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してクーポンルールのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCouponIssueのリスト
   */
  public List<CouponIssue> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してクーポンルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCouponIssueのリスト
   */
  public List<CouponIssue> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCouponIssueのリスト
   */
  public List<CouponIssue> loadAll() {
    return genericDao.loadAll();
  }

}
