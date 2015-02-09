//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.TmallCategoryDao;
import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 受注ヘッダ
 * 
 * @author System Integrator Corp.
 */
public class TmallCategoryDaoImpl implements TmallCategoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<TmallCategory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TmallCategoryDaoImpl() {
    genericDao = new GenericDaoImpl<TmallCategory, Long>(TmallCategory.class);
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
   * 指定されたorm_rowidを持つ受注ヘッダのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するTmallOrderHeaderのインスタンス
   */
  public TmallCategory loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   * 
   * @param orderNo
   *          受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderのインスタンス
   */
  public TmallCategory load(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "SELECT * FROM TMALL_CATEGORY" + " WHERE CATEGORY_CODE = ?";
    List<TmallCategory> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   * 
   * @param orderNo
   *          受注番号
   * @return 主キー列の値に対応するTmallOrderHeaderの行が存在すればtrue
   */
  public boolean exists(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "SELECT COUNT(*) FROM Tmall_Category" + " WHERE category_code = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のTmallOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallCategory obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規TmallOrderHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のTmallOrderHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(TmallCategory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 受注ヘッダを更新します。
   * 
   * @param obj
   *          更新対象のTmallOrderHeader
   */
  public void update(TmallCategory obj) {
    genericDao.update(obj);
  }

  /**
   * 受注ヘッダを更新します。
   * 
   * @param obj
   *          更新対象のTmallOrderHeader
   */
  public void update(TmallCategory obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 受注ヘッダを削除します。
   * 
   * @param obj
   *          削除対象のTmallOrderHeader
   */
  public void delete(TmallCategory obj) {
    genericDao.delete(obj);
  }

  /**
   * 受注ヘッダを削除します。
   * 
   * @param obj
   *          削除対象のTmallOrderHeader
   */
  public void delete(TmallCategory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   * 
   * @param orderNo
   *          受注番号
   */
  public void delete(String orderNo) {
    Object[] params = new Object[] {
      orderNo
    };
    final String query = "DELETE FROM Tmall_Category" + " WHERE category_code = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して受注ヘッダのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するTmallOrderHeaderのリスト
   */
  public List<TmallCategory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して受注ヘッダのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するTmallOrderHeaderのリスト
   */
  public List<TmallCategory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブル全データ分のTmallOrderHeaderのリスト
   */
  public List<TmallCategory> loadAll() {
    return genericDao.loadAll();
  }
  //add by os012 20120207 start
  /**
   * 同级别所有
   */
  @Override
  public List<TmallCategory> loadAllCategory(String categoryId) {
    Object[] params = new Object[] {
      categoryId
    };
    final String query = "SELECT CATEGORY_CODE, CATEGORY_NAME,PARENT_CODE FROM TMALL_CATEGORY WHERE  PARENT_CODE=(SELECT  PARENT_CODE "
        + "FROM TMALL_CATEGORY WHERE CATEGORY_CODE=?)";
    return findByQuery(query, params);
  }

  /**
   * 子类同级别所有
   */
  @Override
  public List<TmallCategory> loadAllChild(String categoryId) {
    Object[] params = new Object[] {
      categoryId
    };
    final String query = "SELECT CATEGORY_CODE, CATEGORY_NAME,PARENT_CODE FROM TMALL_CATEGORY WHERE   PARENT_CODE=?";
    return findByQuery(query, params);
  }

  /**
   * 父类同级别所有
   */
  @Override
  public List<TmallCategory> loadAllFather(String categoryId) {
    Object[] params = new Object[] {
      categoryId
    };
    final String query = "SELECT CATEGORY_CODE, CATEGORY_NAME, PARENT_CODE, IS_PARENT, IS_SHOP_CATEGORY, "
        + " ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME"
        + "FROM TMALL_CATEGORY where PARENT_CODE=(SELECT  PARENT_CODE   FROM TMALL_CATEGORY where CATEGORY_CODE= (SELECT  PARENT_CODE"
        + " FROM TMALL_CATEGORY where CATEGORY_CODE=?)) ";
    return findByQuery(query, params);
  }

  /**
   * 所有第一级
   */
  @Override
  public List<TmallCategory> loadAllFather() {
    final String query = "SELECT CATEGORY_CODE, CATEGORY_NAME, PARENT_CODE, IS_PARENT, IS_SHOP_CATEGORY, "
        + " ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME FROM TMALL_CATEGORY where PARENT_CODE='0' ";
    return findByQuery(query);
  } 

  /**
   *级别所有
   */
  @Override
  public List<TmallCategory> loadAllCategory() {
    final String query = "SELECT * FROM TMall_Category WHERE 1=1 ";
    return findByQuery(query);
  }
  //add by os012 20120207 end
  
  /**
   * 
   */
  @Override
  public List<TmallCategory> loadAllChild() {
    final String query = "SELECT * FROM TMall_Category WHERE is_parent=0 AND is_shop_category=0";
    return findByQuery(query);
  }
}
