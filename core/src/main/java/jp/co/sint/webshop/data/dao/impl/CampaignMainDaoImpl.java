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
import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * キャンペーン
 *
 * @author System Integrator Corp.
 *
 */
public class CampaignMainDaoImpl implements CampaignMainDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CampaignMain, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CampaignMainDaoImpl() {
    genericDao = new GenericDaoImpl<CampaignMain, Long>(CampaignMain.class);
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
   * @return idに対応するCampaignMainのインスタンス
   */
  public CampaignMain loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignMainのインスタンス
   */
  public CampaignMain load(String campaignCode) {
    Object[] params = new Object[]{campaignCode};
    final String query = "SELECT * FROM CAMPAIGN_MAIN"
        + " WHERE CAMPAIGN_CODE = ?";
    List<CampaignMain> result = genericDao.findByQuery(query, params);
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
   * @return 主キー列の値に対応するCampaignMainの行が存在すればtrue
   */
  public boolean exists(String campaignCode) {
    Object[] params = new Object[]{campaignCode};
    final String query = "SELECT COUNT(*) FROM CAMPAIGN_MAIN"
        + " WHERE CAMPAIGN_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CampaignMainをデータベースに追加します。
   * @param obj 追加対象のCampaignMain
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CampaignMain obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CampaignMainをデータベースに追加します。
   * @param obj 追加対象のCampaignMain
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CampaignMain obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaignMain
   */
  public void update(CampaignMain obj) {
    genericDao.update(obj);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaignMain
   */
  public void update(CampaignMain obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaignMain
   */
  public void delete(CampaignMain obj) {
    genericDao.delete(obj);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaignMain
   */
  public void delete(CampaignMain obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してキャンペーンを削除します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   */
  public void delete(String campaignCode) {
    Object[] params = new Object[]{ campaignCode};
    final String query = "DELETE FROM CAMPAIGN_MAIN"
        + " WHERE CAMPAIGN_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCampaignMainのリスト
   */
  public List<CampaignMain> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCampaignMainのリスト
   */
  public List<CampaignMain> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCampaignMainのリスト
   */
  public List<CampaignMain> loadAll() {
    return genericDao.loadAll();
  }

}
