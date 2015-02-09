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
import jp.co.sint.webshop.data.dao.InquiryDetailDao;
import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

import org.hibernate.SessionFactory;
/** 
 * 咨询详细
 *
 * @author swj.
 *
 */
public class InquiryDetailDaoImpl implements InquiryDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<InquiryDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public InquiryDetailDaoImpl() {
    genericDao = new GenericDaoImpl<InquiryDetail, Long>(InquiryDetail.class);
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
   * 指定されたorm_rowidを持つ咨询详细のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するInquiryDetailのインスタンス
   */
  public InquiryDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して咨询详细のインスタンスを取得します。
   * @param inquiryHeaderNo 咨询编号
   * @param inquiryDetailNo 咨询明细编号
   * @return 主キー列の値に対応するInquiryDetailのインスタンス
   */
  public InquiryDetail load(String inquiryHeaderNo, String inquiryDetailNo) {
    Object[] params = new Object[]{inquiryHeaderNo, inquiryDetailNo};
    final String query = "SELECT * FROM INQUIRY_DETAIL"
        + " WHERE INQUIRY_HEADER_NO = ? AND INQUIRY_DETAIL_NO = ?";
    List<InquiryDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定して咨询详细が既に存在するかどうかを返します。
   * @param inquiryHeaderNo 咨询编号
   * @param inquiryDetailNo 咨询明细编号
   * @return 主キー列の値に対応するInquiryDetailの行が存在すればtrue
   */
  public boolean exists(String inquiryHeaderNo, String inquiryDetailNo) {
    Object[] params = new Object[]{inquiryHeaderNo, inquiryDetailNo};
    final String query = "SELECT COUNT(*) FROM INQUIRY_DETAIL"
        + " WHERE INQUIRY_HEADER_NO = ? AND INQUIRY_DETAIL_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規InquiryDetailをデータベースに追加します。
   * @param obj 追加対象のInquiryDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(InquiryDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規InquiryDetailをデータベースに追加します。
   * @param obj 追加対象のInquiryDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(InquiryDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 咨询详细を更新します。
   * @param obj 更新対象のInquiryDetail
   */
  public void update(InquiryDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 咨询详细を更新します。
   * @param obj 更新対象のInquiryDetail
   */
  public void update(InquiryDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 咨询详细を削除します。
   * @param obj 削除対象のInquiryDetail
   */
  public void delete(InquiryDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 咨询详细を削除します。
   * @param obj 削除対象のInquiryDetail
   */
  public void delete(InquiryDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して咨询详细を削除します。
   * @param inquiryHeaderNo 咨询编号
   * @param InquiryDetailNo 咨询明细编号
   */
  public void delete(String inquiryHeaderNo, String inquiryDetailNo) {
    Object[] params = new Object[]{inquiryHeaderNo, inquiryDetailNo};
    final String query = "DELETE FROM INQUIRY_DETAIL"
        + " WHERE INQUIRY_HEADER_NO = ? AND INQUIRY_DETAIL_NO = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して咨询详细のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するInquiryDetailのリスト
   */
  public List<InquiryDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して咨询详细のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するInquiryDetailのリスト
   */
  public List<InquiryDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のInquiryDetailのリスト
   */
  public List<InquiryDetail> loadAll() {
    return genericDao.loadAll();
  }

}
