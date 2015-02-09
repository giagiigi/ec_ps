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
import jp.co.sint.webshop.data.dao.AdvertDao;
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * ポイントルール
 *
 * @author System Integrator Corp.
 *
 */
public class AdvertDaoImpl implements AdvertDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Advert, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public AdvertDaoImpl() {
    genericDao = new GenericDaoImpl<Advert, Long>(Advert.class);
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
   * 指定されたorm_rowidを持つポイントルールのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するAdvertのインスタンス
   */
  public Advert loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してポイントルールのインスタンスを取得します。
   * @param advertNo ポイントルール番号
   * @return 主キー列の値に対応するAdvertのインスタンス
   */
  public Advert load(Long advertNo) {
    Object[] params = new Object[]{advertNo};
    final String query = "SELECT * FROM ADVERT"
        + " WHERE ADVERT_NO = ?";
    List<Advert> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   * @param advertNo ポイントルール番号
   * @return 主キー列の値に対応するAdvertの行が存在すればtrue
   */
  public boolean exists(Long advertNo) {
    Object[] params = new Object[]{advertNo};
    final String query = "SELECT COUNT(*) FROM ADVERT"
        + " WHERE ADVERT_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Advertをデータベースに追加します。
   * @param obj 追加対象のAdvert
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Advert obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Advertをデータベースに追加します。
   * @param obj 追加対象のAdvert
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Advert obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * ポイントルールを更新します。
   * @param obj 更新対象のAdvert
   */
  public void update(Advert obj) {
    genericDao.update(obj);
  }

  /**
   * ポイントルールを更新します。
   * @param obj 更新対象のAdvert
   */
  public void update(Advert obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * ポイントルールを削除します。
   * @param obj 削除対象のAdvert
   */
  public void delete(Advert obj) {
    genericDao.delete(obj);
  }

  /**
   * ポイントルールを削除します。
   * @param obj 削除対象のAdvert
   */
  public void delete(Advert obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してポイントルールを削除します。
   * @param advertNo ポイントルール番号
   */
  public void delete(Long advertNo) {
    Object[] params = new Object[]{advertNo};
    final String query = "DELETE FROM ADVERT"
        + " WHERE ADVERT_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してポイントルールのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するAdvertのリスト
   */
  public List<Advert> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してポイントルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するAdvertのリスト
   */
  public List<Advert> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のAdvertのリスト
   */
  public List<Advert> loadAll() {
    return genericDao.loadAll();
  }

}
