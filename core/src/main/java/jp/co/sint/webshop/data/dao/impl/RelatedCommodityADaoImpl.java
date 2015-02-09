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
import jp.co.sint.webshop.data.dao.RelatedCommodityADao;
import jp.co.sint.webshop.data.dto.RelatedCommodityA;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 手動リコメンド
 *
 * @author System Integrator Corp.
 *
 */
public class RelatedCommodityADaoImpl implements RelatedCommodityADao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<RelatedCommodityA, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public RelatedCommodityADaoImpl() {
    genericDao = new GenericDaoImpl<RelatedCommodityA, Long>(RelatedCommodityA.class);
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
   * 指定されたorm_rowidを持つ手動リコメンドのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するRelatedCommodityAのインスタンス
   */
  public RelatedCommodityA loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して手動リコメンドのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkCommodityCode リンク商品コード
   * @return 主キー列の値に対応するRelatedCommodityAのインスタンス
   */
  public RelatedCommodityA load(String shopCode, String commodityCode, String linkCommodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode, linkCommodityCode};
    final String query = "SELECT * FROM RELATED_COMMODITY_A"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND LINK_COMMODITY_CODE = ?";
    List<RelatedCommodityA> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して手動リコメンドが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkCommodityCode リンク商品コード
   * @return 主キー列の値に対応するRelatedCommodityAの行が存在すればtrue
   */
  public boolean exists(String shopCode, String commodityCode, String linkCommodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode, linkCommodityCode};
    final String query = "SELECT COUNT(*) FROM RELATED_COMMODITY_A"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND LINK_COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規RelatedCommodityAをデータベースに追加します。
   * @param obj 追加対象のRelatedCommodityA
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RelatedCommodityA obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規RelatedCommodityAをデータベースに追加します。
   * @param obj 追加対象のRelatedCommodityA
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(RelatedCommodityA obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 手動リコメンドを更新します。
   * @param obj 更新対象のRelatedCommodityA
   */
  public void update(RelatedCommodityA obj) {
    genericDao.update(obj);
  }

  /**
   * 手動リコメンドを更新します。
   * @param obj 更新対象のRelatedCommodityA
   */
  public void update(RelatedCommodityA obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 手動リコメンドを削除します。
   * @param obj 削除対象のRelatedCommodityA
   */
  public void delete(RelatedCommodityA obj) {
    genericDao.delete(obj);
  }

  /**
   * 手動リコメンドを削除します。
   * @param obj 削除対象のRelatedCommodityA
   */
  public void delete(RelatedCommodityA obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して手動リコメンドを削除します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkCommodityCode リンク商品コード
   */
  public void delete(String shopCode, String commodityCode, String linkCommodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode, linkCommodityCode};
    final String query = "DELETE FROM RELATED_COMMODITY_A"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?"
        + " AND LINK_COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して手動リコメンドのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRelatedCommodityAのリスト
   */
  public List<RelatedCommodityA> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して手動リコメンドのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRelatedCommodityAのリスト
   */
  public List<RelatedCommodityA> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のRelatedCommodityAのリスト
   */
  public List<RelatedCommodityA> loadAll() {
    return genericDao.loadAll();
  }

}
