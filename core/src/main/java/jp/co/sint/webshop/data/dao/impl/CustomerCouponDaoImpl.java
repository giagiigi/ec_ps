package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.CustomerCouponDao;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;


public class CustomerCouponDaoImpl implements CustomerCouponDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerCoupon, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerCouponDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerCoupon, Long>(CustomerCoupon.class);
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
   * @return idに対応するCustomerCouponのインスタンス
   */
  public CustomerCoupon loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してクーポンルールのインスタンスを取得します。
   * @param couponIssueNo クーポンルール番号
   * @return 主キー列の値に対応するCustomerCouponのインスタンス
   */
  public CustomerCoupon load(Long customerCouponId) {
    Object[] params = new Object[]{customerCouponId};
    final String query = "SELECT * FROM CUSTOMER_COUPON"
        + " WHERE CUSTOMER_COUPON_ID = ? ";
    List<CustomerCoupon> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してクーポンルールが既に存在するかどうかを返します。
   * @param couponIssueNo クーポンルール番号
   * @return 主キー列の値に対応するCustomerCouponの行が存在すればtrue
   */
  public boolean exists(Long customerCouponId) {
    Object[] params = new Object[]{customerCouponId};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_COUPON"
        + " WHERE CUSTOMER_COUPON_ID = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CustomerCouponをデータベースに追加します。
   * @param obj 追加対象のCustomerCoupon
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerCoupon obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CustomerCouponをデータベースに追加します。
   * @param obj 追加対象のCustomerCoupon
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerCoupon obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * クーポンルールを更新します。
   * @param obj 更新対象のCustomerCoupon
   */
  public void update(CustomerCoupon obj) {
    genericDao.update(obj);
  }

  /**
   * クーポンルールを更新します。
   * @param obj 更新対象のCustomerCoupon
   */
  public void update(CustomerCoupon obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * クーポンルールを削除します。
   * @param obj 削除対象のCustomerCoupon
   */
  public void delete(CustomerCoupon obj) {
    genericDao.delete(obj);
  }

  /**
   * クーポンルールを削除します。
   * @param obj 削除対象のCustomerCoupon
   */
  public void delete(CustomerCoupon obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してクーポンルールを削除します。
   * @param couponIssueNo クーポンルール番号
   */
  public void delete(Long customerCouponId) {
    Object[] params = new Object[]{customerCouponId};
    final String query = "DELETE FROM CUSTOMER_COUPON"
        + " WHERE CUSTOMER_COUPON_ID = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してクーポンルールのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCustomerCouponのリスト
   */
  public List<CustomerCoupon> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してクーポンルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCustomerCouponのリスト
   */
  public List<CustomerCoupon> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCustomerCouponのリスト
   */
  public List<CustomerCoupon> loadAll() {
    return genericDao.loadAll();
  }

}
