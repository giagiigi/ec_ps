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
import jp.co.sint.webshop.data.dao.TagDao;
import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * タグ
 *
 * @author System Integrator Corp.
 *
 */
public class TagDaoImpl implements TagDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Tag, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public TagDaoImpl() {
    genericDao = new GenericDaoImpl<Tag, Long>(Tag.class);
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
   * 指定されたorm_rowidを持つタグのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するTagのインスタンス
   */
  public Tag loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してタグのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @return 主キー列の値に対応するTagのインスタンス
   */
  public Tag load(String shopCode, String tagCode) {
    Object[] params = new Object[]{shopCode, tagCode};
    final String query = "SELECT * FROM TAG"
        + " WHERE SHOP_CODE = ?"
        + " AND TAG_CODE = ?";
    List<Tag> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してタグが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @return 主キー列の値に対応するTagの行が存在すればtrue
   */
  public boolean exists(String shopCode, String tagCode) {
    Object[] params = new Object[]{shopCode, tagCode};
    final String query = "SELECT COUNT(*) FROM TAG"
        + " WHERE SHOP_CODE = ?"
        + " AND TAG_CODE = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Tagをデータベースに追加します。
   * @param obj 追加対象のTag
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Tag obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Tagをデータベースに追加します。
   * @param obj 追加対象のTag
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(Tag obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * タグを更新します。
   * @param obj 更新対象のTag
   */
  public void update(Tag obj) {
    genericDao.update(obj);
  }

  /**
   * タグを更新します。
   * @param obj 更新対象のTag
   */
  public void update(Tag obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * タグを削除します。
   * @param obj 削除対象のTag
   */
  public void delete(Tag obj) {
    genericDao.delete(obj);
  }

  /**
   * タグを削除します。
   * @param obj 削除対象のTag
   */
  public void delete(Tag obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定してタグを削除します。
   * @param shopCode ショップコード
   * @param tagCode タグコード
   */
  public void delete(String shopCode, String tagCode) {
    Object[] params = new Object[]{shopCode, tagCode};
    final String query = "DELETE FROM TAG"
        + " WHERE SHOP_CODE = ?"
        + " AND TAG_CODE = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定してタグのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTagのリスト
   */
  public List<Tag> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してタグのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTagのリスト
   */
  public List<Tag> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のTagのリスト
   */
  public List<Tag> loadAll() {
    return genericDao.loadAll();
  }

}
