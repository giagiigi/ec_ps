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
import jp.co.sint.webshop.data.dao.ArrivalGoodsDao;
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 商品入荷お知らせ
 *
 * @author System Integrator Corp.
 *
 */
public class ArrivalGoodsDaoImpl implements ArrivalGoodsDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<ArrivalGoods, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public ArrivalGoodsDaoImpl() {
    genericDao = new GenericDaoImpl<ArrivalGoods, Long>(ArrivalGoods.class);
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
   * 指定されたorm_rowidを持つ商品入荷お知らせのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するArrivalGoodsのインスタンス
   */
  public ArrivalGoods loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して商品入荷お知らせのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するArrivalGoodsのインスタンス
   */
  public ArrivalGoods load(String shopCode, String skuCode, String email) {
    Object[] params = new Object[]{shopCode, skuCode, email};
    final String query = "SELECT * FROM ARRIVAL_GOODS"
        + " WHERE SHOP_CODE = ?"
        + " AND SKU_CODE = ?"
        + " AND EMAIL = ?";
    List<ArrivalGoods> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して商品入荷お知らせが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するArrivalGoodsの行が存在すればtrue
   */
  public boolean exists(String shopCode, String skuCode, String email) {
    Object[] params = new Object[]{shopCode, skuCode, email};
    final String query = "SELECT COUNT(*) FROM ARRIVAL_GOODS"
        + " WHERE SHOP_CODE = ?"
        + " AND SKU_CODE = ?"
        + " AND EMAIL = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規ArrivalGoodsをデータベースに追加します。
   * @param obj 追加対象のArrivalGoods
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ArrivalGoods obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規ArrivalGoodsをデータベースに追加します。
   * @param obj 追加対象のArrivalGoods
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(ArrivalGoods obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 商品入荷お知らせを更新します。
   * @param obj 更新対象のArrivalGoods
   */
  public void update(ArrivalGoods obj) {
    genericDao.update(obj);
  }

  /**
   * 商品入荷お知らせを更新します。
   * @param obj 更新対象のArrivalGoods
   */
  public void update(ArrivalGoods obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 商品入荷お知らせを削除します。
   * @param obj 削除対象のArrivalGoods
   */
  public void delete(ArrivalGoods obj) {
    genericDao.delete(obj);
  }

  /**
   * 商品入荷お知らせを削除します。
   * @param obj 削除対象のArrivalGoods
   */
  public void delete(ArrivalGoods obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して商品入荷お知らせを削除します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @param email メールアドレス
   */
  public void delete(String shopCode, String skuCode, String email) {
    Object[] params = new Object[]{shopCode, skuCode, email};
    final String query = "DELETE FROM ARRIVAL_GOODS"
        + " WHERE SHOP_CODE = ?"
        + " AND SKU_CODE = ?"
        + " AND EMAIL = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して商品入荷お知らせのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するArrivalGoodsのリスト
   */
  public List<ArrivalGoods> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して商品入荷お知らせのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するArrivalGoodsのリスト
   */
  public List<ArrivalGoods> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のArrivalGoodsのリスト
   */
  public List<ArrivalGoods> loadAll() {
    return genericDao.loadAll();
  }

}
