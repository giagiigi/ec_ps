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
import jp.co.sint.webshop.data.dao.CampaignCommodityDao;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * キャンペーン対象商品
 *
 * @author System Integrator Corp.
 *
 */
public class CampaignCommodityDaoImpl implements CampaignCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CampaignCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CampaignCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<CampaignCommodity, Long>(CampaignCommodity.class);
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
   * 指定されたorm_rowidを持つキャンペーン対象商品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCampaignCommodityのインスタンス
   */
  public CampaignCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してキャンペーン対象商品のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCampaignCommodityのインスタンス
   */
  public CampaignCommodity load(String shopCode, String campaignCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, campaignCode, commodityCode};
    final String query = "SELECT * FROM CAMPAIGN_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND CAMPAIGN_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<CampaignCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してキャンペーン対象商品が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCampaignCommodityの行が存在すればtrue
   */
  public boolean exists(String shopCode, String campaignCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, campaignCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM CAMPAIGN_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND CAMPAIGN_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CampaignCommodityをデータベースに追加します。
   * @param obj 追加対象のCampaignCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CampaignCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CampaignCommodityをデータベースに追加します。
   * @param obj 追加対象のCampaignCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CampaignCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * キャンペーン対象商品を更新します。
   * @param obj 更新対象のCampaignCommodity
   */
  public void update(CampaignCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * キャンペーン対象商品を更新します。
   * @param obj 更新対象のCampaignCommodity
   */
  public void update(CampaignCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * キャンペーン対象商品を削除します。
   * @param obj 削除対象のCampaignCommodity
   */
  public void delete(CampaignCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * キャンペーン対象商品を削除します。
   * @param obj 削除対象のCampaignCommodity
   */
  public void delete(CampaignCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してキャンペーン対象商品を削除します。
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @param commodityCode 商品コード
   */
  public void delete(String shopCode, String campaignCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, campaignCode, commodityCode};
    final String query = "DELETE FROM CAMPAIGN_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND CAMPAIGN_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してキャンペーン対象商品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCampaignCommodityのリスト
   */
  public List<CampaignCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してキャンペーン対象商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCampaignCommodityのリスト
   */
  public List<CampaignCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCampaignCommodityのリスト
   */
  public List<CampaignCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
