package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.SmsTemplateDetailDao;
import jp.co.sint.webshop.data.dto.SmsTemplateDetail;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * メールテンプレート明細
 *
 * @author System Integrator Corp.
 *exists
 */
public class SmsTemplateDetailDaoImpl implements SmsTemplateDetailDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<SmsTemplateDetail, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public SmsTemplateDetailDaoImpl() {
    genericDao = new GenericDaoImpl<SmsTemplateDetail, Long>(SmsTemplateDetail.class);
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
   * 指定されたorm_rowidを持つメールテンプレート明細のインスタンスを取得します。
   * @param id 対象のorm_rowid
   * @return idに対応するSmsTemplateDetailのインスタンス
   */
  public SmsTemplateDetail loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定してメールテンプレート明細のインスタンスを取得します。
   * @param shopCode ショップコード
   * @param smsType メールタイプ
   * @param smsTemplateNo メールテンプレート番号
   * @return 主キー列の値に対応するSmsTemplateDetailのインスタンス
   */
  public SmsTemplateDetail load(String shopCode, String smsType, Long smsTemplateNo) {
    Object[] params = new Object[]{shopCode, smsType, smsTemplateNo};
    final String query = "SELECT * FROM SMS_TEMPLATE_DETAIL"
        + " WHERE SHOP_CODE = ?"
        + " AND SMS_TYPE = ?"
        + " AND SMS_TEMPLATE_NO = ?";
    List<SmsTemplateDetail> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }

  /**
   * 主キー列の値を指定してメールテンプレート明細が既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param smsType メールタイプ
   * @param smsTemplateNo メールテンプレート番号
   * @return 主キー列の値に対応するSmsTemplateDetailの行が存在すればtrue
   */
  public boolean exists(String shopCode, String smsType, Long smsTemplateNo) {
    Object[] params = new Object[]{shopCode, smsType, smsTemplateNo};
    final String query = "SELECT COUNT(*) FROM SMS_TEMPLATE_DETAIL"
        + " WHERE SHOP_CODE = ?"
        + " AND SMS_TYPE = ?"
        + " AND SMS_TEMPLATE_NO = ?";
    Object result = genericDao.executeScalar(query, params);
    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規SmsTemplateDetailをデータベースに追加します。
   * @param obj 追加対象のSmsTemplateDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SmsTemplateDetail obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規SmsTemplateDetailをデータベースに追加します。
   * @param obj 追加対象のSmsTemplateDetail
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(SmsTemplateDetail obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * メールテンプレート明細を更新します。
   * @param obj 更新対象のSmsTemplateDetail
   */
  public void update(SmsTemplateDetail obj) {
    genericDao.update(obj);
  }

  /**
   * メールテンプレート明細を更新します。
   * @param obj 更新対象のSmsTemplateDetail
   */
  public void update(SmsTemplateDetail obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * Queryオブジェクトを指定してメールテンプレート明細のリストを取得します。
   * @param query Queryオブジェクト
   * @return 検索結果に相当するSmsTemplateDetailのリスト
   */
  public List<SmsTemplateDetail> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定してメールテンプレート明細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するSmsTemplateDetailのリスト
   */
  public List<SmsTemplateDetail> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブル全データ分のSmsTemplateDetailのリスト
   */
  public List<SmsTemplateDetail> loadAll() {
    return genericDao.loadAll();
  }

  public void delete(SmsTemplateDetail obj) {
    genericDao.delete(obj);
  }
}
