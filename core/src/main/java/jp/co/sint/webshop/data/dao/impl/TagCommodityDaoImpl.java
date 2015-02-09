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
import jp.co.sint.webshop.data.dao.TagCommodityDao;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * タグ商品
 *
 * @author System Integrator Corp.
 *
 */
public class TagCommodityDaoImpl implements TagCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TagCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TagCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<TagCommodity, Long>(TagCommodity.class);
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
   * 指定されたorm_rowidを持つタグ商品のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するTagCommodityのインスタンス
   */
  public TagCommodity loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してタグ商品のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するTagCommodityのインスタンス
   */
  public TagCommodity load(String shopCode, String tagCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, tagCode, commodityCode};
    final String query = "SELECT * FROM TAG_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND TAG_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<TagCommodity> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してタグ商品が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するTagCommodityの行が存在すればtrue
   */
  public boolean exists(String shopCode, String tagCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, tagCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM TAG_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND TAG_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規TagCommodityをデータベースに追加します。
   * @param obj 追加対象のTagCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TagCommodity obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TagCommodityをデータベースに追加します。
   * @param obj 追加対象のTagCommodity
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TagCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * タグ商品を更新します。
   * @param obj 更新対象のTagCommodity
   */
  public void update(TagCommodity obj) {
    genericDao.update(obj);
  }

  /**
   * タグ商品を更新します。
   * @param obj 更新対象のTagCommodity
   */
  public void update(TagCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * タグ商品を削除します。
   * @param obj 削除対象のTagCommodity
   */
  public void delete(TagCommodity obj) {
    genericDao.delete(obj);
  }

  /**
   * タグ商品を削除します。
   * @param obj 削除対象のTagCommodity
   */
  public void delete(TagCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してタグ商品を削除します。
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @param commodityCode 商品コード
   */
  public void delete(String shopCode, String tagCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, tagCode, commodityCode};
    final String query = "DELETE FROM TAG_COMMODITY"
        + " WHERE SHOP_CODE = ?"
        + " AND TAG_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してタグ商品のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTagCommodityのリスト
   */
  public List<TagCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してタグ商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTagCommodityのリスト
   */
  public List<TagCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTagCommodityのリスト
   */
  public List<TagCommodity> loadAll() {
    return genericDao.loadAll();
  }

}
