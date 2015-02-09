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

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.InquiryHeaderDao;
import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 咨询Header
 *
 * @author swj.
 *
 */
public class InquiryHeaderDaoImpl implements InquiryHeaderDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<InquiryHeader, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public InquiryHeaderDaoImpl() {
    genericDao = new GenericDaoImpl<InquiryHeader, Long>(InquiryHeader.class);
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
   * 指定されたorm_rowidを持つ咨询Headerのインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するInquiryHeaderのインスタンス
   */
  public InquiryHeader loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して咨询Headerのインスタンスを取得します。
   * @param inquiryHeaderNo 咨询编号
   * @return 主キー列の値に対応するInquiryHeaderのインスタンス
   */
  public InquiryHeader load(String inquiryHeaderNo) {
    Object[] params = new Object[]{inquiryHeaderNo};
    final String query = "SELECT * FROM INQUIRY_HEADER"
        + " WHERE INQUIRY_HEADER_NO = ?";
    List<InquiryHeader> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して咨询Headerが既に存在するかどうかを返します。
   * @param inquiryHeaderNo 咨询编号
   * @return 主キー列の値に対応するInquiryHeaderの行が存在すればtrue
   */
  public boolean exists(String inquiryHeaderNo) {
    Object[] params = new Object[]{inquiryHeaderNo};
    final String query = "SELECT COUNT(*) FROM INQUIRY_HEADER"
        + " WHERE INQUIRY_HEADER_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規InquiryHeaderをデータベースに追加します。
   * @param obj 追加対象のInquiryHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(InquiryHeader obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規InquiryHeaderをデータベースに追加します。
   * @param obj 追加対象のInquiryHeader
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(InquiryHeader obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 咨询Headerを更新します。
   * @param obj 更新対象のInquiryHeader
   */
  public void update(InquiryHeader obj) {
    genericDao.update(obj);
  }

  /**
   * 咨询Headerを更新します。
   * @param obj 更新対象のInquiryHeader
   */
  public void update(InquiryHeader obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 咨询Headerを削除します。
   * @param obj 削除対象のInquiryHeader
   */
  public void delete(InquiryHeader obj) {
    genericDao.delete(obj);
  }

  /**
   * 咨询Headerを削除します。
   * @param obj 削除対象のInquiryHeader
   */
  public void delete(InquiryHeader obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して咨询Headerを削除します。
   * @param inquiryHeaderNo 咨询编号
   */
  public void delete(String inquiryHeaderNo) {
    Object[] params = new Object[]{inquiryHeaderNo};
    final String query = "DELETE FROM INQUIRY_HEADER"
        + " WHERE INQUIRY_HEADER_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して咨询Headerのリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するInquiryHeaderのリスト
   */
  public List<InquiryHeader> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して咨询Headerのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するInquiryHeaderのリスト
   */
  public List<InquiryHeader> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のInquiryHeaderのリスト
   */
  public List<InquiryHeader> loadAll() {
    return genericDao.loadAll();
  }

}
