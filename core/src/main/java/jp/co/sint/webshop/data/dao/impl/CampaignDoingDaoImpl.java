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
import jp.co.sint.webshop.data.dao.CampaignDoingDao;
import jp.co.sint.webshop.data.dto.CampaignDoing;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * キャンペーン
 *
 * @author System Integrator Corp.
 *
 */
public class CampaignDoingDaoImpl implements CampaignDoingDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CampaignDoing, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CampaignDoingDaoImpl() {
    genericDao = new GenericDaoImpl<CampaignDoing, Long>(CampaignDoing.class);
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
   * @return idに対応するCampaignDoingのインスタンス
   */
  public CampaignDoing loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignDoingのインスタンス
   */
  public CampaignDoing load(String campaignCode) {
    Object[] params = new Object[]{campaignCode};
    final String query = "SELECT * FROM CAMPAIGN_DOINGS"
        + " WHERE CAMPAIGN_CODE = ?";
    List<CampaignDoing> result = genericDao.findByQuery(query, params);
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
   * @return 主キー列の値に対応するCampaignDoingの行が存在すればtrue
   */
  public boolean exists(String campaignCode) {
    Object[] params = new Object[]{campaignCode};
    final String query = "SELECT COUNT(*) FROM CAMPAIGN_DOINGS"
        + " WHERE CAMPAIGN_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CampaignDoingをデータベースに追加します。
   * @param obj 追加対象のCampaignDoing
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CampaignDoing obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CampaignDoingをデータベースに追加します。
   * @param obj 追加対象のCampaignDoing
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CampaignDoing obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaignDoing
   */
  public void update(CampaignDoing obj) {
    genericDao.update(obj);
  }

  /**
   * キャンペーンを更新します。
   * @param obj 更新対象のCampaignDoing
   */
  public void update(CampaignDoing obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaignDoing
   */
  public void delete(CampaignDoing obj) {
    genericDao.delete(obj);
  }

  /**
   * キャンペーンを削除します。
   * @param obj 削除対象のCampaignDoing
   */
  public void delete(CampaignDoing obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してキャンペーンを削除します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   */
  public void delete(String campaignCode) {
    Object[] params = new Object[]{ campaignCode};
    final String query = "DELETE FROM CAMPAIGN_DOINGS"
        + " WHERE CAMPAIGN_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCampaignDoingのリスト
   */
  public List<CampaignDoing> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCampaignDoingのリスト
   */
  public List<CampaignDoing> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCampaignDoingのリスト
   */
  public List<CampaignDoing> loadAll() {
    return genericDao.loadAll();
  }

}
