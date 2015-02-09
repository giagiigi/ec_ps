package jp.co.sint.webshop.data.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.NewCouponRuleUseInfoDao;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.utility.StringUtil;

public class NewCouponRuleUseInfoDaoImpl implements NewCouponRuleUseInfoDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<NewCouponRuleUseInfo, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public NewCouponRuleUseInfoDaoImpl() {
    genericDao = new GenericDaoImpl<NewCouponRuleUseInfo, Long>(NewCouponRuleUseInfo.class);
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
   * 指定されたorm_rowidを持つ优惠券规则_使用关联信息のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応する优惠券规则_使用关联信息のインスタンス
   */
  public NewCouponRuleUseInfo loadByRowid(Long id) {
    return genericDao.loadByRowid(id);
  }

  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息のインスタンスを取得します。
   * 
   * @param couponCode
   *          优惠券规则编号
   * @param couponUseNo
   *          明细编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoのインスタンス
   */
  public NewCouponRuleUseInfo load(String couponCode, Long couponUseNo) {
    Object[] params = new Object[] { couponCode, couponUseNo };
    final String query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND coupon_use_no = ? ";
    List<NewCouponRuleUseInfo> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode 优惠券规则编号
   * @return 优惠券规则_发行关联信息
   * 
   */
  public List<NewCouponRuleUseInfo> load(String couponCode, boolean trueOrFalse){
    Object[] params = new Object[] { couponCode };
    String query="";
    if(trueOrFalse){
      query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND brand_code is not null ";
    }else{
      query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND import_commodity_type is not null ";
    }
    
    List<NewCouponRuleUseInfo> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }
  }
  /**
   * 优惠券编号和商品编号の値を指定して优惠券规则_使用关联信息のインスタンスを取得します。
   *
   * @param couponCode 优惠券规则编号
   * @param couponUseNo 明细编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoのインスタンス
   */
  public List<NewCouponRuleUseInfo> load(String couponCode, String useCommodityCode, String flg){
    Object[] params;
    if(!StringUtil.hasValue(useCommodityCode)){
      params = new Object[] { couponCode };
    }else{
      params = new Object[] { couponCode, useCommodityCode };
    }
    
    String query = "";
    if("brandRegister".equals(flg) || "brandDelete".equals(flg)){
      query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND brand_code = ? ";
      // 20130929 txw add start
    }else if("categoryRegister".equals(flg) || "categoryDelete".equals(flg)){
        query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND category_code = ? ";
     // 20130929 txw add end
    }else if("useRegister".equals(flg) || "useDelete".equals(flg)){
      query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND commodity_code = ? ";
    }else if("importRegister".equals(flg)){
      if(!StringUtil.hasValue(useCommodityCode)){
        query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND import_commodity_type is not null ";
      }else{
        query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND import_commodity_type = ? ";
      }
      // 20130929 txw add start
    } else if("categoryLoad".equals(flg)) {
      query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND category_code is not null ";
      // 20130929 txw add end
    }
    
    List<NewCouponRuleUseInfo> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }
  }
  public List<NewCouponRuleUseInfo> load(String couponCode,String flg){
    Object[] params = new Object[] { couponCode };
    String query = "SELECT  * FROM new_coupon_rule_use_info WHERE coupon_code = ? order by coupon_use_no desc limit 1 ";
    List<NewCouponRuleUseInfo> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result;
    } else {
      return null;
    }
  }
  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息のインスタンスを取得します。
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoののリスト
   */
  public List<NewCouponRuleUseInfo> load(String couponCode) {
    Object[] params = new Object[] { couponCode };
    final String query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? ";
    List<NewCouponRuleUseInfo> result = genericDao.findByQuery(query, params);

    return result;
  }

  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息が既に存在するかどうかを返します。
   * 
   * @param couponCode
   *          优惠券规则编号
   * @param couponUseNo
   *          明细编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoの行が存在すればtrue
   */
  public boolean exists(String couponCode, Long couponUseNo) {
    Object[] params = new Object[] { couponCode, couponUseNo };
    final String query = "SELECT * FROM new_coupon_rule_use_info WHERE coupon_code = ? AND coupon_use_no = ? ";
    List<NewCouponRuleUseInfo> result = genericDao.findByQuery(query, params);

    return ((Number) result).intValue() > 0;

  }

  /**
   * 新規NewCouponRuleUseInfoをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のNewCouponRuleUseInfo
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(NewCouponRuleUseInfo obj) {
    return genericDao.insert(obj);
  }

  /**
   * 新規NewCouponRuleUseInfoをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のNewCouponRuleUseInfo
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  public Long insert(NewCouponRuleUseInfo obj, LoginInfo loginInfo) {
    return genericDao.insert(obj, loginInfo);
  }

  /**
   * 优惠券规则_使用关联信息を更新します。
   * 
   * @param obj
   *          更新対象のNewCouponRuleUseInfo
   * @param loginInfo
   *          ログイン情報
   */
  public void update(NewCouponRuleUseInfo obj) {
    genericDao.update(obj);
  }

  /**
   * 优惠券规则_使用关联信息を更新します。
   * 
   * @param obj
   *          更新対象のNewCouponRuleUseInfo
   * @param loginInfo
   *          ログイン情報
   */
  public void update(NewCouponRuleUseInfo obj, LoginInfo loginInfo) {
    genericDao.update(obj, loginInfo);
  }

  /**
   * 优惠券规则_使用关联信息を削除します。
   * 
   * @param obj
   *          削除対象のNewCouponRuleUseInfo
   * @param loginInfo
   *          ログイン情報
   */
  public void delete(NewCouponRuleUseInfo obj) {
    genericDao.delete(obj);
  }

  /**
   * 优惠券规则_使用关联信息を削除します。
   * 
   * @param obj
   *          削除対象のNewCouponRuleUseInfo
   * @param loginInfo
   *          ログイン情報
   */
  public void delete(NewCouponRuleUseInfo obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息を削除します。
   * 
   * @param couponCode
   *          优惠券规则编号
   * @param couponUseNo
   *          明细编号
   */
  public void delete(String couponCode, Long couponUseNo) {
    Object[] params = new Object[] { couponCode, couponUseNo };
    final String query = "DELETE FROM new_coupon_rule_use_info WHERE coupon_code = ? AND coupon_use_no = ? ";
    genericDao.updateByQuery(query, params);
  }
  /**
   * 根据优惠券编号和商品编号の値を指定して优惠券规则_使用关联信息を削除します。
   *
   * @param couponCode 优惠券规则编号
   * @param couponUseNo 明细编号
   */
  public void delete(String couponCode, String useCommodityCode, String flg){
    Object[] params = new Object[] { couponCode, useCommodityCode };
    String query="";
    if("useRegister".equals(flg)){
      query= "DELETE FROM new_coupon_rule_use_info WHERE coupon_code = ? AND brand_code = ? ";
    }else if("brandRegister".equals(flg)){
      query= "DELETE FROM new_coupon_rule_use_info WHERE coupon_code = ? AND commodity_code = ? ";
    }else if("delete".equals(flg)){
      query= "DELETE FROM new_coupon_rule_use_info WHERE coupon_code = ? AND commodity_code = ? ";
    }
    
    genericDao.updateByQuery(query, params);
  }

  /**
   * Queryオブジェクトを指定して优惠券规则_使用关联信息のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するNewCouponRuleUseInfoのリスト
   */
  public List<NewCouponRuleUseInfo> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * SQLを指定して优惠券规则_使用关联信息のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するNewCouponRuleUseInfoのリスト
   */
  public List<NewCouponRuleUseInfo> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のNewCouponRuleUseInfoのリスト
   */
  public List<NewCouponRuleUseInfo> loadAll() {
    return genericDao.loadAll();
  }

}
