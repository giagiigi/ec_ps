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
import jp.co.sint.webshop.data.dao.CustomerCardUseInfoDao;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 在庫
 *
 * @author System Integrator Corp.
 *
 */
public class CustomerCardUseInfoDaoImpl implements CustomerCardUseInfoDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerCardUseInfo, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerCardUseInfoDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerCardUseInfo, Long>(CustomerCardUseInfo.class);
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
   * 指定されたorm_rowidを持つ在庫のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するStockのインスタンス
   */
  public CustomerCardUseInfo loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して在庫のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockのインスタンス
   */
  public CustomerCardUseInfo load(String customerCode,Long cardId,String orderNo) {
    Object[] params = new Object[]{customerCode,cardId,orderNo};
    final String query = "SELECT * FROM CUSTOMER_CARD_USE_INFO"
      + " WHERE CUSTOMER_CODE = ? AND CARD_ID = ? AND ORDER_NO = ? ";
    List<CustomerCardUseInfo> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して在庫が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockの行が存在すればtrue
   */
  public boolean exists(String customerCode,Long cardId,String orderNo) {
    Object[] params = new Object[]{customerCode,cardId,orderNo};
    final String query = "SELECT COUNT(*) FROM CUSTOMER_CARD_USE_INFO"
      + " WHERE CUSTOMER_CODE = ? AND CARD_ID = ? AND ORDER_NO = ? ";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerCardUseInfo obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CustomerCardUseInfo obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(CustomerCardUseInfo obj) {
    genericDao.update(obj);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(CustomerCardUseInfo obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(CustomerCardUseInfo obj) {
    genericDao.delete(obj);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(CustomerCardUseInfo obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して在庫を削除します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String customerCode,Long cardId,String orderNo) {
    Object[] params = new Object[]{customerCode,cardId,orderNo};
    final String query = "DELETE FROM CUSTOMER_CARD_USE_INFO"
      + " WHERE CUSTOMER_CODE = ? AND CARD_ID = ? AND ORDER_NO = ? ";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して在庫のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockのリスト
   */
  public List<CustomerCardUseInfo> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して在庫のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockのリスト
   */
  public List<CustomerCardUseInfo> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のStockのリスト
   */
  public List<CustomerCardUseInfo> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public List<CustomerCardUseInfo> loadByCustomerCode(String customerCode) {
    Object[] params = new Object[]{customerCode};
    final String query = "SELECT * FROM CUSTOMER_CARD_USE_INFO"
      + " WHERE CUSTOMER_CODE = ? ";
    List<CustomerCardUseInfo> result = genericDao.findByQuery(query, params);
    return result;
  }
  
  @Override
  public List<CustomerCardUseInfo> loadByOrderNo(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT * FROM CUSTOMER_CARD_USE_INFO"
      + " WHERE ORDER_NO = ? ";
    List<CustomerCardUseInfo> result = genericDao.findByQuery(query, params);
    return result;
  }
  
  @Override
  public List<CustomerCardUseInfo> loadByOrderNoOrderBy(String orderNo,String innerJoin,String orderBy) {
    Object[] params = new Object[]{orderNo};
    
    final String query = "SELECT CCUI.* FROM CUSTOMER_CARD_USE_INFO CCUI " + innerJoin
      + " WHERE ccui.ORDER_NO = ? " + orderBy ;
    List<CustomerCardUseInfo> result = genericDao.findByQuery(query, params);
    return result;
  }

}
