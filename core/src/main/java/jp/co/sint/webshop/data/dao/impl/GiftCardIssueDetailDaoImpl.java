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
import jp.co.sint.webshop.data.dao.GiftCardIssueDetailDao;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 在庫
 *
 * @author System Integrator Corp.
 *
 */
public class GiftCardIssueDetailDaoImpl implements GiftCardIssueDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<GiftCardIssueDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public GiftCardIssueDetailDaoImpl() {
    genericDao = new GenericDaoImpl<GiftCardIssueDetail, Long>(GiftCardIssueDetail.class);
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
  public GiftCardIssueDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して在庫のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockのインスタンス
   */
  public GiftCardIssueDetail load(String cardCode) {
    Object[] params = new Object[]{cardCode};
    final String query = "SELECT * FROM gift_card_issue_detail"
      + " WHERE card_id = ?";
    List<GiftCardIssueDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  
  /**
   * 根据礼品卡编号及印刷编号精准查询。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するStockのインスタンス
   */
  public GiftCardIssueDetail load(String cardId,String cardCode){
    Object[] params = new Object[]{cardId,cardCode};
    final String query = "SELECT * FROM gift_card_issue_detail"
      + " WHERE card_id = ? and card_code = ?";
    List<GiftCardIssueDetail> result = genericDao.findByQuery(query, params);
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
  public boolean exists(String cardCode) {
    Object[] params = new Object[]{cardCode};
    final String query = "SELECT COUNT(*) FROM gift_card_issue_detail"
      + " WHERE card_id = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GiftCardIssueDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規Stockをデータベースに追加します。
   * @param obj 追加対象のStock
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(GiftCardIssueDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(GiftCardIssueDetail obj) {
    genericDao.update(obj);
  }

  /**
   * 在庫を更新します。
   * @param obj 更新対象のStock
   */
  public void update(GiftCardIssueDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(GiftCardIssueDetail obj) {
    genericDao.delete(obj);
  }

  /**
   * 在庫を削除します。
   * @param obj 削除対象のStock
   */
  public void delete(GiftCardIssueDetail obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して在庫を削除します。
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  public void delete(String cardCode) {
    Object[] params = new Object[]{cardCode};
    final String query = "DELETE FROM gift_card_issue_detail"
      + " WHERE card_id = ?";
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して在庫のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するStockのリスト
   */
  public List<GiftCardIssueDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して在庫のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するStockのリスト
   */
  public List<GiftCardIssueDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のStockのリスト
   */
  public List<GiftCardIssueDetail> loadAll() {
    return genericDao.loadAll();
  }

  @Override
  public List<GiftCardIssueDetail> loadByCardId(String cardCode) {
    Object[] params = new Object[]{cardCode};
    final String query = "SELECT * FROM gift_card_issue_detail"
        + " WHERE card_id = ?";
    List<GiftCardIssueDetail> result = genericDao.findByQuery(query, params);
    return result;
  }

}
