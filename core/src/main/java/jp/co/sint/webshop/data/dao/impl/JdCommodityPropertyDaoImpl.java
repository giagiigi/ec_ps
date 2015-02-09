package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.JdCommodityPropertyDao;
import jp.co.sint.webshop.data.dto.JdCommodityProperty;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * JD商品属性关联表
 * 
 * @author OB
 * 
 */
public class JdCommodityPropertyDaoImpl implements JdCommodityPropertyDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<JdCommodityProperty, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public JdCommodityPropertyDaoImpl() {
    genericDao = new GenericDaoImpl<JdCommodityProperty, Long>(JdCommodityProperty.class);
  }

  /**
   * SessionFactoryを取得します
   * 
   * @return SessionFactoryのインスタンス
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * SessionFactoryを設定します
   * 
   * @param factory
   *          SessionFactoryのインスタンス
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 指定されたorm_rowidを持つJD商品属性关联表のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するJdCommodityPropertyのインスタンス
   */
  public JdCommodityProperty loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してJD商品属性关联表のインスタンスを取得します。
   * 
   * @param commodityCode 商品编号
   * @param propertyId 属性ID
   * @param valueId 属性值ID
   * @return 主キー列の値に対応するJdCommodityPropertyのインスタンス
   */
  public JdCommodityProperty load(String commodityCode, String propertyId, String valueId) {
    Object[] params = new Object[] { commodityCode, propertyId, valueId };
    final String query = "SELECT * FROM Jd_Commodity_Property"
        + " WHERE commodity_code = ? and property_id=? and value_id=?";
    List<JdCommodityProperty> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してJD商品属性关联表が既に存在するかどうかを返します。
   * 
   * @param commodityCode 商品编号
   * @param propertyId 属性ID
   * @param valueId 属性值ID
   * @return 主キー列の値に対応するJdCommodityPropertyの行が存在すればtrue
   */
  public boolean exists(String commodityCode, String propertyId, String valueId) {
    Object[] params = new Object[] { commodityCode, propertyId, valueId };
    final String query = "SELECT COUNT(*) FROM Jd_Commodity_Property"
        + " WHERE commodity_code = ? and property_id=? and value_id=?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のJdCommodityProperty
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdCommodityProperty obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のJdCommodityProperty
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdCommodityProperty obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * JD商品属性关联表を更新します。
   * 
   * @param obj
   *          更新対象のJdCommodityProperty
   */
  public void update(JdCommodityProperty obj) {
    genericDao.update(obj);
  }

  /**
   * JD商品属性关联表を更新します。
   * 
   * @param obj
   *          更新対象のJdCommodityProperty
   */
  public void update(JdCommodityProperty obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * JD商品属性关联表を削除します。
   * 
   * @param obj
   *          削除対象のJdCommodityProperty
   */
  public void delete(JdCommodityProperty obj) {
    genericDao.delete(obj);
  }

  /**
   * JD商品属性关联表を削除します。
   * 
   * @param obj
   *          削除対象のJdCommodityProperty
   */
  public void delete(JdCommodityProperty obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してJD商品属性关联表を削除します。
   * 
   * @param commodityCode 商品编号
   * @param propertyId 属性ID
   * @param valueId 属性值ID
   */
  public void delete(String commodityCode, String propertyId, String valueId) {
    Object[] params = new Object[] { commodityCode, propertyId, valueId };
    final String query = "DELETE FROM Jd_Property_Value WHERE commodity_code = ? and property_id=? and value_id=?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してJD商品属性关联表のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するJdCommodityPropertyのリスト
   */
  public List<JdCommodityProperty> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してJD商品属性关联表のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するJdCommodityPropertyのリスト
   */
  public List<JdCommodityProperty> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のJdCommodityPropertyのリスト
   */
  public List<JdCommodityProperty> loadAll() {
    return genericDao.loadAll();
  }

  /**
   * 指定して商品编号のJdCommodityPropertyリストを取得します。
   * @param commodityCode 商品编号
   * @return テーブルの全データ分のJdCommodityPropertyのリスト
   */
  public List<JdCommodityProperty> loadByCommodityCode(String commodityCode) {
    String sql = "select * from jd_commodity_property where commodity_code = ? ";
    return this.findByQuery(sql, commodityCode);
  }

  /**
   * 查询商品自定义的属性
   * @param commodityCode 商品编号
   */
  public List<JdCommodityProperty> loadInputProByCommodityCode(String commodityCode) {
    String sql = "SELECT * FROM JD_COMMODITY_PROPERTY WHERE COMMODITY_CODE = ? AND VALUE_ID = '0'";
    return this.findByQuery(sql, commodityCode);
  }
}
