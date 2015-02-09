//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.TmallDeliveryLocationDao;
import jp.co.sint.webshop.data.dto.TmallDeliveryLocation;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 地域
 *
 * @author System Integrator Corp.
 *
 */
public class TmallDeliveryLocationDaoImpl implements TmallDeliveryLocationDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallDeliveryLocation, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TmallDeliveryLocationDaoImpl() {
    genericDao = new GenericDaoImpl<TmallDeliveryLocation, Long>(TmallDeliveryLocation.class);
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
   * DeliveryLocationを削除します。
   *
   * @param obj 削除対象のDeliveryLocation
   * @param loginInfo ログイン情報
   */
  public void delete(TmallDeliveryLocation obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param deliveryCompanyNo 配送会社コード
   * @param prefectureCode 地域コード
   * @param cityCode 都市コード
   * @param areaCode 県コード
   * @return 主キー列の値に対応するDeliveryLocationの行が存在すればtrue
   */
  public boolean exists(String shopCode, String deliveryCompanyNo, String prefectureCode, String cityCode, String areaCode) {
    Object[] params = new Object[] {shopCode, deliveryCompanyNo,prefectureCode,cityCode,areaCode};
    final String query = "SELECT COUNT(*) FROM TMALL_DELIVERY_LOCATION"
                      + " WHERE  SHOP_CODE = ?" 
                      + " AND    DELIVERY_COMPANY_NO = ?"
                      + " AND    PREFECTURE_CODE = ?"
                      + " AND    CITY_CODE = ?"
                      + " AND    AREA_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;
  }

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するDeliveryLocationのリスト
   */
  public List<TmallDeliveryLocation> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するDeliveryLocationのリスト
   */
  public List<TmallDeliveryLocation> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * 新規DeliveryLocationをデータベースに追加します。
   *
   * @param obj 追加対象のDeliveryLocation
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallDeliveryLocation obj, LoginInfo loginInfo) {
    return genericDao.insert(obj);
  }

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param deliveryCompanyNo 配送会社コード
   * @param prefectureCode 地域コード
   * @param cityCode 都市コード
   * @param areaCode 県コード
   * @return 主キー列の値に対応するDeliveryLocationのインスタンス
   */
  public TmallDeliveryLocation load(String shopCode, String deliveryCompanyNo, String prefectureCode, String cityCode, String areaCode) {
    Object[] params = new Object[] {shopCode,deliveryCompanyNo,prefectureCode,cityCode,areaCode};   
    String sqlString = "SELECT COUNT(*) FROM TMALL_DELIVERY_LOCATION"
      + " WHERE  SHOP_CODE = ?" 
      + " AND    DELIVERY_COMPANY_NO = ?"
      + " AND    PREFECTURE_CODE = ?"
      + " AND    CITY_CODE = ?"
      + " AND    AREA_CODE = ?";
    List<TmallDeliveryLocation> list = genericDao.findByQuery(sqlString, params);
    if (list != null && list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }
  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param deliveryCompanyNo 配送会社コード
   * @return 主キー列の値に対応するList<DeliveryLocation>のインスタンス
   */
  public List<TmallDeliveryLocation> load(String shopCode, String deliveryCompanyNo){
    Object[] params = new Object[] {shopCode,deliveryCompanyNo};   
    String sqlString = "SELECT * FROM TMALL_DELIVERY_LOCATION"
      + " WHERE  SHOP_CODE = ?" 
      + " AND    DELIVERY_COMPANY_NO = ?";
    List<TmallDeliveryLocation> list = genericDao.findByQuery(sqlString, params);
    if (list != null && list.size() > 0) {
      return list;
    } else {
      return null;
    }
  }
  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のDeliveryLocationのリスト
   */
  public List<TmallDeliveryLocation> loadAll() {
    return genericDao.loadAll();
  }

  /**
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するDeliveryLocationのインスタンス
   */
  public TmallDeliveryLocation loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * DeliveryLocationを更新します。
   *
   * @param obj 更新対象のDeliveryLocation
   * @param loginInfo ログイン情報
   */
  public void update(TmallDeliveryLocation obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);  
  }

  /**
   * DeliveryLocationを削除します。
   *
   * @param obj 削除対象のDeliveryLocation
   */
  public void delete(TmallDeliveryLocation transactionObject) {
    genericDao.delete(transactionObject);    
  }

  /**
   * 新規DeliveryLocationをデータベースに追加します。
   *
   * @param obj 追加対象のDeliveryLocation
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallDeliveryLocation newInstance) {
    return genericDao.insert(newInstance);
  }

  /**
   * DeliveryLocationを更新します。
   *
   * @param obj 更新対象のDeliveryLocation
   */
  public void update(TmallDeliveryLocation transactionObject) {
    genericDao.update(transactionObject);  
  }


}
