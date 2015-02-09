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
import jp.co.sint.webshop.data.dao.GiftCardReturnApplyDao;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 在庫
 *
 * @author System Integrator Corp.
 *
 */
public class GiftCardReturnApplyDaoImpl implements GiftCardReturnApplyDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<GiftCardReturnApply, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public GiftCardReturnApplyDaoImpl() {
    genericDao = new GenericDaoImpl<GiftCardReturnApply, Long>(GiftCardReturnApply.class);
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
   * 指定されたorm_rowidを持つ在庫のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するStockのインスタンス
   */
  public GiftCardReturnApply loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して在庫のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockのインスタンス
   */
  public GiftCardReturnApply load(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT * FROM GIFT_CARD_RETURN_APPLY "
      + " WHERE order_No = ? ";
    List<GiftCardReturnApply> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して在庫が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockの行が存在すればtrue
   */
  public boolean exists(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "SELECT COUNT(*) FROM GIFT_CARD_RETURN_APPLY"
      + " WHERE order_No = ? ";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GiftCardReturnApply obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GiftCardReturnApply obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(GiftCardReturnApply obj) {
    genericDao.update(obj);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(GiftCardReturnApply obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(GiftCardReturnApply obj) {
    genericDao.delete(obj);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(GiftCardReturnApply obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して在庫を削除します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String orderNo) {
    Object[] params = new Object[]{orderNo};
    final String query = "DELETE FROM GIFT_CARD_RETURN_APPLY"
      + " WHERE order_No = ? ";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して在庫のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockのリスト
   */
  public List<GiftCardReturnApply> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して在庫のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockのリスト
   */
  public List<GiftCardReturnApply> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のStockのリスト
   */
  public List<GiftCardReturnApply> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public List<GiftCardReturnApply> loadByConfirmFlg(Long confirmFlg) {
    Object[] params = new Object[]{confirmFlg};
    final String query = "SELECT * FROM GIFT_CARD_RETURN_APPLY"
      + " WHERE confirm_Flg = ? ";
    List<GiftCardReturnApply> result = genericDao.findByQuery(query, params);
    return result;
  }

}
