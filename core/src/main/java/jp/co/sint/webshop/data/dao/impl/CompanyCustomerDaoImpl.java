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
import jp.co.sint.webshop.data.dao.CompanyCustomerDao;
import jp.co.sint.webshop.data.dto.CompanyCustomer;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * キャンペーン
 *
 * @author System Integrator Corp.
 *
 */
public class CompanyCustomerDaoImpl implements CompanyCustomerDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CompanyCustomer, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CompanyCustomerDaoImpl() {
    genericDao = new GenericDaoImpl<CompanyCustomer, Long>(CompanyCustomer.class);
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
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCampaignのインスタンス
   */
  public CompanyCustomer loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignのインスタンス
   */
  public CompanyCustomer load(String companyCode) {
    Object[] params = new Object[]{companyCode};
    final String query = "SELECT * FROM COMPANY_CUSTOMER"
        + " WHERE COMPANY_CODE = ?";
    List<CompanyCustomer> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignの行が存在すればtrue
   */
  public boolean exists(String companyCode) {
    Object[] params = new Object[]{companyCode};
    final String query = "SELECT COUNT(*) FROM COMPANY_CUSTOMER"
      + " WHERE COMPANY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Campaignをデータベースに追加します。
   * @param obj 追加対象のCampaign
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CompanyCustomer obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Campaignをデータベースに追加します。
   * @param obj 追加対象のCampaign
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CompanyCustomer obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaign
   */
  public void update(CompanyCustomer obj) {
    genericDao.update(obj);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaign
   */
  public void update(CompanyCustomer obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaign
   */
  public void delete(CompanyCustomer obj) {
    genericDao.delete(obj);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaign
   */
  public void delete(CompanyCustomer obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してキャンペーンを削除します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   */
  public void delete(String companyCode) {
    Object[] params = new Object[]{companyCode};
    final String query = "DELETE FROM COMPANY_CUSTOMER"
        + " WHERE COMPANY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCampaignのリスト
   */
  public List<CompanyCustomer> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCampaignのリスト
   */
  public List<CompanyCustomer> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCampaignのリスト
   */
  public List<CompanyCustomer> loadAll() {
    return genericDao.loadAll();
  }

}
