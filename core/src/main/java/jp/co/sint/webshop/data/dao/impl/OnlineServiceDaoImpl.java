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
import jp.co.sint.webshop.data.dao.OnlineServiceDao;
import jp.co.sint.webshop.data.dto.OnlineService;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 *
 * @author System Integrator Corp.
 *
 */
public class OnlineServiceDaoImpl implements OnlineServiceDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<OnlineService, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public OnlineServiceDaoImpl() {
    genericDao = new GenericDaoImpl<OnlineService, Long>(OnlineService.class);
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
   * 指定されたorm_rowidを持つOnlineServiceのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するOnlineServiceのインスタンス
   */
  public OnlineService loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してOnlineServiceのインスタンスを取得します。
   * @param OnlineServiceNo
   * @return 主キー列の値に対応するOnlineServiceのインスタンス
   */
  public OnlineService load(Long OnlineServiceNo) {
    Object[] params = new Object[]{OnlineServiceNo};
    final String query = "SELECT * FROM ONLINE_SERVICE"
        + " WHERE ONLINE_SERVICE_NO = ?";
    List<OnlineService> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   * @param OnlineService番号
   * @return 主キー列の値に対応するOnlineServiceの行が存在すればtrue
   */
  public boolean exists(Long OnlineServiceNo) {
    Object[] params = new Object[]{OnlineServiceNo};
    final String query = "SELECT COUNT(*) FROM ONLINE_SERVICE"
        + " WHERE ONLINE_SERVICE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 检索shopCode对应项
   * 
   */
  public boolean isHaveOnline(String shopCode){
    Object[] params = new Object[]{shopCode};
    final String query = "SELECT COUNT(*) FROM ONLINE_SERVICE" 
      +" WHERE SHOP_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;
  }
  
  
  
  /**
   * 新規OnlineServiceをデータベースに追加します。
   * @param obj 追加対象のOnlineService
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OnlineService obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規OnlineServiceをデータベースに追加します。
   * @param obj 追加対象のOnlineService
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(OnlineService obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
    
    
  }

  /**
   * OnlineServiceを更新します。
   * @param obj 更新対象のOnlineService
   */
  public void update(OnlineService obj) {
    genericDao.update(obj);
  }

  /**
   * OnlineServiceを更新します。
   * @param obj 更新対象のOnlineService
   */
  public void update(OnlineService obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * OnlineServiceを削除します。
   * @param obj 削除対象のOnlineService
   */
  public void delete(OnlineService obj) {
    genericDao.delete(obj);
  }

  /**
   * OnlineServiceを削除します。
   * @param obj 削除対象のOnlineService
   */
  public void delete(OnlineService obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してOnlineServiceを削除します。
   * @param OnlineService番号
   */
  public void delete(Long OnlineServiceNo) {
    Object[] params = new Object[]{OnlineServiceNo};
    final String query = "DELETE FROM ONLINE_SERVICE"
        + " WHERE ONLINE_SERVICE_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してOnlineServiceのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOnlineServiceのリスト
   */
  public List<OnlineService> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してOnlineServiceのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOnlineServiceのリスト
   */
  public List<OnlineService> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のOnlineServiceのリスト
   */
  public List<OnlineService> loadAll() {
    return genericDao.loadAll();
  }
  
  
}
