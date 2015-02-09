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
import jp.co.sint.webshop.data.dao.ImageUploadHistoryDao;
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 商品ヘッダ
 *
 * @author System Integrator Corp.
 *
 */
public class ImageUploadHistoryDaoImpl implements ImageUploadHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ImageUploadHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ImageUploadHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<ImageUploadHistory, Long>(ImageUploadHistory.class);
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
   * 主キー列の値を指定して商品ヘッダのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderのインスタンス
   */
  public ImageUploadHistory load(String shopCode, String skuCode) {
    Object[] params = new Object[]{shopCode, skuCode};
    final String query = "SELECT * FROM IMAGE_UPLOAD_HISTORY"
        + " WHERE SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    List<ImageUploadHistory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  
  public ImageUploadHistory loadByCommodityCode(String shopCode, String commodityCode) {
    Object[] params = new Object[]{shopCode, commodityCode};
    final String query = "SELECT * FROM IMAGE_UPLOAD_HISTORY"
        + " WHERE SHOP_CODE = ?"
        + " AND COMMODITY_CODE = ?";
    List<ImageUploadHistory> result = genericDao.findByQuery(query, params);
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
  public boolean exists(String shopCode, String skuCode) {
    Object[] params = new Object[]{shopCode, skuCode};
    final String query = "SELECT COUNT(*) FROM IMAGE_UPLOAD_HISTORY"
        + " WHERE SHOP_CODE = ?"
        + " AND SKU_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規CommodityHeaderをデータベースに追加します。
   * @param obj 追加対象のCommodityHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ImageUploadHistory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規CommodityHeaderをデータベースに追加します。
   * @param obj 追加対象のCommodityHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ImageUploadHistory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 商品ヘッダを更新します。
   * @param obj 更新対象のCommodityHeader
   */
  public void update(ImageUploadHistory obj) {
    genericDao.update(obj);
  }

  /**
   * 商品ヘッダを更新します。
   * @param obj 更新対象のCommodityHeader
   */
  public void update(ImageUploadHistory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * Queryオブジェクトを指定して商品ヘッダのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  public List<ImageUploadHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して商品ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  public List<ImageUploadHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のCommodityHeaderのリスト
   */
  public List<ImageUploadHistory> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public void delete(ImageUploadHistory transactionObject) {
    genericDao.delete(transactionObject);
  }

  @Override
  public ImageUploadHistory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }
  
  // 2014/05/02 京东WBS对应 ob_姚 add start
  @Override
  public int updateByQuery(String sql, Object... params) {
    return genericDao.updateByQuery(sql, params);
  }
  // 2014/05/02 京东WBS对应 ob_姚 add end
}
