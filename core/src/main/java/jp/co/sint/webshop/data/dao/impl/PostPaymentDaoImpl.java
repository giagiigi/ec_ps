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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.PostPaymentDao;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;
//05-20 Add start
import jp.co.sint.webshop.data.dto.PostPayment;
//05-20 Add end
import org.hibernate.SessionFactory;
/** 
 * 金融機関
 *
 * @author System Integrator Corp.
 *
 */
public class PostPaymentDaoImpl implements PostPaymentDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PostPayment, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PostPaymentDaoImpl() {
    genericDao = new GenericDaoImpl<PostPayment, Long>(PostPayment.class);
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
   * 指定されたorm_rowidを持つ金融機関のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するPostPaymentのインスタンス
   */
  public PostPayment loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して金融機関のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号
   * @param accountNo 口座番号
   * @return 主キー列の値に対応するPostPaymentのインスタンス
   */
  public PostPayment load(String shopCode, Long paymentMethodNo, String postAccountNo) {
    Object[] params = new Object[]{shopCode, paymentMethodNo, postAccountNo};
    final String query = "SELECT * FROM POST_PAYMENT"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?"
        + " AND POST_ACCOUNT_NO = ?";
    List<PostPayment> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  
  /**
   * 主キー列の値を指定して金融機関が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号

   * @return 主キー列の値に対応するPostPaymentの行が存在すればtrue
   */
  public boolean exists(String shopCode, Long paymentMethodNo, String postAccountNo) {
    Object[] params = new Object[]{shopCode, paymentMethodNo, postAccountNo};
    final String query = "SELECT COUNT(*) FROM POST_PAYMENT"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?"
        + " AND POST_ACCOUNT_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規PostPaymentをデータベースに追加します。
   * @param obj 追加対象のPostPayment
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PostPayment obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規PostPaymentをデータベースに追加します。
   * @param obj 追加対象のPostPayment
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(PostPayment obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 金融機関を更新します。
   * @param obj 更新対象のPostPayment
   */
  public void update(PostPayment obj) {
    genericDao.update(obj);
  }

  /**
   * 金融機関を更新します。
   * @param obj 更新対象のPostPayment
   */
  public void update(PostPayment obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 金融機関を削除します。
   * @param obj 削除対象のPostPayment
   */
  public void delete(PostPayment obj) {
    genericDao.delete(obj);
  }

  /**
   * 金融機関を削除します。
   * @param obj 削除対象のPostPayment
   */
  public void delete(PostPayment obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して金融機関を削除します。
   * @param shopCode ショップコード
   * @param paymentMethodNo 支払方法番号

   */
  public void delete(String shopCode, Long paymentMethodNo, String postAccountNo) {
    Object[] params = new Object[]{shopCode, paymentMethodNo, postAccountNo};
    final String query = "DELETE FROM POST_PAYMENT"
        + " WHERE SHOP_CODE = ?"
        + " AND PAYMENT_METHOD_NO = ?"
        + " AND POST_ACCOUNT_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して金融機関のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPostPaymentのリスト
   */
  public List<PostPayment> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して金融機関のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPostPaymentのリスト
   */
  public List<PostPayment> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のPostPaymentのリスト
   */
  public List<PostPayment> loadAll() {
    return genericDao.loadAll();
  }

}
