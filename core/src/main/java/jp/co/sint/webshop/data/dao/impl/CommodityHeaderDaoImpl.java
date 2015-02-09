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
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 商品ヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class CommodityHeaderDaoImpl implements CommodityHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CommodityHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CommodityHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<CommodityHeader, Long>(CommodityHeader.class);
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
   * 指定されたorm_rowidを持つ商品ヘッダのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityHeaderのインスタンス
   */
  public CommodityHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して商品ヘッダのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderのインスタンス
   */
  public CommodityHeader load(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode};
    final String query = "SELECT * FROM COMMODITY_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<CommodityHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して商品ヘッダが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderの行が存在すればtrue
   */
  public boolean exists(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode};
    final String query = "SELECT COUNT(*) FROM COMMODITY_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityHeaderをデータベースに追加します。
   * @param obj 追加対象のCommodityHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityHeaderをデータベースに追加します。
   * @param obj 追加対象のCommodityHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(CommodityHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 商品ヘッダを更新します。
   * @param obj 更新対象のCommodityHeader
   */
  public void update(CommodityHeader obj) {
    genericDao.update(obj);
  }

  /**
   * 商品ヘッダを更新します。
   * @param obj 更新対象のCommodityHeader
   */
  public void update(CommodityHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 商品ヘッダを削除します。
   * @param obj 削除対象のCommodityHeader
   */
  public void delete(CommodityHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * 商品ヘッダを削除します。
   * @param obj 削除対象のCommodityHeader
   */
  public void delete(CommodityHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して商品ヘッダを削除します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   */
  public void delete(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode};
    final String query = "DELETE FROM COMMODITY_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して商品ヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  public List<CommodityHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して商品ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  public List<CommodityHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommodityHeaderのリスト
   */
  public List<CommodityHeader> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public long updateByQuery(String sql, Object... params) {
    return genericDao.updateByQuery(sql, params);
  }
  
  public boolean isRepresentSkuCode(String shopCode, String skuCode) {
    Object[] params = new Object[]{shopCode, skuCode};
    final String query = "SELECT COUNT(*) FROM COMMODITY_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND REPRESENT_SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;
  }
  
  public CommodityHeader loadByRepSku(String shopCode, String RepSku) {
    Object[] params = new Object[]{shopCode, RepSku};
    final String query = "SELECT * FROM COMMODITY_HEADER"
        + " WHERE SHOP_CODE = ?"
        + " AND REPRESENT_SKU_CODE = ?";
    List<CommodityHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }
  }

//20121120 促销对应 ob add start
  public CommodityHeader loadBycommodityCode(String shopCode, String commodityCode,boolean flag,boolean resultFlg) {
	  Object[] params = new Object[]{shopCode, commodityCode};
	  String query="SELECT * FROM COMMODITY_HEADER"
	   + " WHERE SHOP_CODE = ? AND  COMMODITY_CODE = ?";
   
	    if(flag){
	    	query+=" AND COMMODITY_TYPE = 0";
	    }
	    if(resultFlg){
	    	query+=" AND COMMODITY_TYPE = 1";
	    }
	    List<CommodityHeader> result = genericDao.findByQuery(query, params);
	    if (result.size() > 0) {
	      return result.get(0);
	    } else {
	      return null;
	    }
	}
//20121120 促销对应 ob add end


}
