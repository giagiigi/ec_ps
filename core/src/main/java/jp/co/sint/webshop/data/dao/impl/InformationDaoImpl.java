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
import jp.co.sint.webshop.data.dao.InformationDao;
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * お知らせ
 *
 * @author System Integrator Corp.
 *
 */
public class InformationDaoImpl implements InformationDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Information, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public InformationDaoImpl() {
    genericDao = new GenericDaoImpl<Information, Long>(Information.class);
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
   * 指定されたorm_rowidを持つお知らせのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するInformationのインスタンス
   */
  public Information loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してお知らせのインスタンスを取得します。
   * @param informationNo お知らせ番号
   * @return 主キー列の値に対応するInformationのインスタンス
   */
  public Information load(Long informationNo) {
    Object[] params = new Object[]{informationNo};
    final String query = "SELECT * FROM INFORMATION"
        + " WHERE INFORMATION_NO = ?";
    List<Information> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してお知らせが既に存在するかどうかを返します。
   * @param informationNo お知らせ番号
   * @return 主キー列の値に対応するInformationの行が存在すればtrue
   */
  public boolean exists(Long informationNo) {
    Object[] params = new Object[]{informationNo};
    final String query = "SELECT COUNT(*) FROM INFORMATION"
        + " WHERE INFORMATION_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Informationをデータベースに追加します。
   * @param obj 追加対象のInformation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Information obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Informationをデータベースに追加します。
   * @param obj 追加対象のInformation
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Information obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * お知らせを更新します。
   * @param obj 更新対象のInformation
   */
  public void update(Information obj) {
    genericDao.update(obj);
  }

  /**
   * お知らせを更新します。
   * @param obj 更新対象のInformation
   */
  public void update(Information obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * お知らせを削除します。
   * @param obj 削除対象のInformation
   */
  public void delete(Information obj) {
    genericDao.delete(obj);
  }

  /**
   * お知らせを削除します。
   * @param obj 削除対象のInformation
   */
  public void delete(Information obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してお知らせを削除します。
   * @param informationNo お知らせ番号
   */
  public void delete(Long informationNo) {
    Object[] params = new Object[]{informationNo};
    final String query = "DELETE FROM INFORMATION"
        + " WHERE INFORMATION_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してお知らせのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するInformationのリスト
   */
  public List<Information> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してお知らせのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するInformationのリスト
   */
  public List<Information> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のInformationのリスト
   */
  public List<Information> loadAll() {
    return genericDao.loadAll();
  }

}
