package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.JdCategoryDao;
import jp.co.sint.webshop.data.dto.JdCategory;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 京东カテゴリ
 * 
 * @author OB
 * 
 */
public class JdCategoryDaoImpl implements JdCategoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<JdCategory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public JdCategoryDaoImpl() {
    genericDao = new GenericDaoImpl<JdCategory, Long>(JdCategory.class);
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
   * 指定されたorm_rowidを持つ京东カテゴリのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するCategoryのインスタンス
   */
  public JdCategory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して京东カテゴリのインスタンスを取得します。
   * 
   * @param categoryId
   *          类目编号
   * @return 主キー列の値に対応する京东カテゴリのインスタンス
   */
  public JdCategory load(String categoryId) {
    Object[] params = new Object[] { categoryId };
    final String query = "SELECT * FROM jd_category WHERE category_id = ?";
    List<JdCategory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して京东カテゴリが既に存在するかどうかを返します。
   * 
   * @param categoryId
   *          类目编号
   * @return 主キー列の値に対応する京东カテゴリの行が存在すればtrue
   */
  public boolean exists(String categoryId) {
    Object[] params = new Object[] { categoryId };
    final String query = "SELECT COUNT(*) FROM jd_category WHERE category_id = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規京东カテゴリをデータベースに追加します。
   * 
   * @param obj
   *          追加対象の京东カテゴリ
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdCategory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規京东カテゴリをデータベースに追加します。
   * 
   * @param obj
   *          追加対象の京东カテゴリ
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(JdCategory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 京东カテゴリを更新します。
   * 
   * @param obj
   *          更新対象の京东カテゴリ
   */
  public void update(JdCategory obj) {
    genericDao.update(obj);
  }

  /**
   * 京东カテゴリを更新します。
   * 
   * @param obj
   *          更新対象の京东カテゴリ
   */
  public void update(JdCategory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 京东カテゴリを削除します。
   * 
   * @param obj
   *          削除対象の京东カテゴリ
   */
  public void delete(JdCategory obj) {
    genericDao.delete(obj);
  }

  /**
   * 京东カテゴリを削除します。
   * 
   * @param obj
   *          削除対象の京东カテゴリ
   */
  public void delete(JdCategory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して京东カテゴリを削除します。
   * 
   * @param categoryId
   *          类目编号
   */
  public void delete(String categoryId) {
    Object[] params = new Object[] { categoryId };
    final String query = "DELETE FROM jd_category WHERE category_id = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して京东カテゴリのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当する京东カテゴリのリスト
   */
  public List<JdCategory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して京东カテゴリのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当する京东カテゴリのリスト
   */
  public List<JdCategory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分の京东カテゴリのリスト
   */
  public List<JdCategory> loadAll() {
    return genericDao.loadAll();
  }

  /**
   * 查询所有子类别
   */
  @Override
  public List<JdCategory> loadAllChild() {
    final String query = "SELECT * FROM Jd_Category WHERE is_parent = 0 ";

    return findByQuery(query);
  }

}
