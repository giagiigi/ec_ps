package jp.co.sint.webshop.data.dao.impl;

/**
 * 优惠券规则表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.util.List;
import org.hibernate.SessionFactory;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.NewCouponRuleLssueInfoDao;
import jp.co.sint.webshop.data.dto.NewCouponRuleLssueInfo;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 优惠券规则表
 * 
 * @author ob
 * 
 */
public class NewCouponRuleLssueInfoDaoImpl implements NewCouponRuleLssueInfoDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<NewCouponRuleLssueInfo, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public NewCouponRuleLssueInfoDaoImpl() {
    genericDao = new GenericDaoImpl<NewCouponRuleLssueInfo, Long>(NewCouponRuleLssueInfo.class);
  }

  /**
   * 取得SessionFactory
   * 
   * @return SessionFactory实例
   */
  public SessionFactory getSessionFactory() {
    return this.sessionFactory;
  }

  /**
   * 设定SessionFactory
   * 
   * @param factory SessionFactory实例
   */
  public void setSessionFactory(SessionFactory factory) {
    this.sessionFactory = factory;
    genericDao.setSessionFactory(factory);
  }

  /**
   * 删除优惠券规则
   * 
   * @param obj 删除的优惠券规则信息
   * @param loginInfo 删除人信息
   */
  public void delete(NewCouponRuleLssueInfo obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 根据Query对象查询优惠券规则信息列表。
   * 
   * @param query 查询条件信息
   * @return 优惠券规则表信息List
   */
  public List<NewCouponRuleLssueInfo> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * 根据sqlString查询优惠券规则信息
   * 
   * @param sqlString sql语句
   * @param params  参数值
   * @return 优惠券规则信息列表
   */
  public List<NewCouponRuleLssueInfo> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * 添加优惠券规则
   * 
   * @param obj 优惠券规则信息
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  public Long insert(NewCouponRuleLssueInfo obj, LoginInfo loginInfo) {
    return genericDao.insert(obj);
  }

  /**
   * 查询所有的优惠券规则信息
   * 
   * @return 全部的优惠券规则信息列表
   */
  public List<NewCouponRuleLssueInfo> loadAll() {
    return genericDao.loadAll();
  }
  
  /**
   * 根据(优惠券规则编号)查询优惠券规则信息
   * 
   * @param couponCode 优惠券规则编号
   * @return 指定的优惠券规则信息
   */
  public NewCouponRuleLssueInfo load(String couponCode){

    String sqlString = "select * from new_coupon_rule_lssue_info where coupon_code = ? ";
    List<NewCouponRuleLssueInfo> list = this.findByQuery(sqlString, couponCode);

    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }
  
  /**
   * 主キー列の値を指定して优惠券规则_发行关联信息のインスタンスを取得します。
   * 
   * @param couponCode
   *          优惠券规则编号
   * @param couponUseNo
   *          明细编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoのインスタンス
   */
  public NewCouponRuleLssueInfo load(String couponCode, Long couponUseNo) {
    Object[] params = new Object[] { couponCode, couponUseNo };
    final String query = "SELECT * FROM new_coupon_rule_lssue_info WHERE coupon_code = ? AND coupon_use_no = ? ";
    List<NewCouponRuleLssueInfo> result = genericDao.findByQuery(query, params);
    if (result.size() > 0) {
      return result.get(0);
    } else {
      return null;
    }

  }
  
  public NewCouponRuleLssueInfo load(String couponCode, String typeCode, String flg) {
    String sqlString = "";
    
    if("lssueBrandRegister".equals(flg) || "lssueBrandDelete".equals(flg)){
      sqlString = "select * from new_coupon_rule_lssue_info where coupon_code = ? and brand_code = ?";
    }else if("lssueCategoryRegister".equals(flg) || "lssueCategoryDelete".equals(flg)){
      sqlString = "select * from new_coupon_rule_lssue_info where coupon_code = ? and category_code = ?";
    }else if("lssueRegister".equals(flg) || "delete".equals(flg)){
      sqlString = "select * from new_coupon_rule_lssue_info where coupon_code = ? and commodity_code = ?";
    }
    
    List<NewCouponRuleLssueInfo> list = this.findByQuery(sqlString, couponCode, typeCode);

    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }
  /**
   * 根据(优惠券规则编号)删除优惠券规则信息
   * 
   * @param couponCode 优惠券规则编号
   */
  public void delete(String couponCode) {

    String sqlString = "delete from new_coupon_rule_lssue_info where coupon_code = ?";
    this.findByQuery(sqlString, couponCode);
  }

  /**
   * 更新任务
   * 
   * @param obj 任务信息
   * @param loginInfo 更新人信息
   */
  public void update(NewCouponRuleLssueInfo obj, LoginInfo loginInfo) {
    genericDao.update(obj,loginInfo);
  }

  /**
   * 删除任务
   * 
   * @param obj 删除的任务信息
   */
  public void delete(NewCouponRuleLssueInfo transactionObject) {
    genericDao.delete(transactionObject);
  }

  /**
   * 添加任务
   * 
   * @param obj 任务信息
   * @return 添加成功时返回任务编号
   */
  public Long insert(NewCouponRuleLssueInfo transactionObject) {
    return genericDao.insert(transactionObject);
  }

  /**
   * 更新任务
   * 
   * @param obj 任务信息
   */
  public void update(NewCouponRuleLssueInfo transactionObject) {
    genericDao.update(transactionObject);
  }

@Override
public NewCouponRuleLssueInfo loadByRowid(Long id) {
	// TODO Auto-generated method stub
	return null;
}
}