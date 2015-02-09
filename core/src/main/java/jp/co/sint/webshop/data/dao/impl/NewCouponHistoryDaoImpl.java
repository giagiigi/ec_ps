package jp.co.sint.webshop.data.dao.impl;

/**
 * 优惠券发行履历表
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
import jp.co.sint.webshop.data.dao.NewCouponHistoryDao;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 优惠券发行履历表
 * 
 * @author ob
 * 
 */
public class NewCouponHistoryDaoImpl implements NewCouponHistoryDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<NewCouponHistory, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public NewCouponHistoryDaoImpl() {
    genericDao = new GenericDaoImpl<NewCouponHistory, Long>(NewCouponHistory.class);
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
   * 删除优惠券发行履历
   * 
   * @param obj 删除的优惠券发行履历信息
   * @param loginInfo 删除人信息
   */
  public void delete(NewCouponHistory obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 根据Query对象查询优惠券发行履历信息列表。
   * 
   * @param query 查询条件信息
   * @return 优惠券发行履历表信息List
   */
  public List<NewCouponHistory> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * 根据sqlString查询优惠券发行履历信息
   * 
   * @param sqlString sql语句
   * @param params  参数值
   * @return 优惠券发行履历信息列表
   */
  public List<NewCouponHistory> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * 添加优惠券发行履历
   * 
   * @param obj 优惠券发行履历信息
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  public Long insert(NewCouponHistory obj, LoginInfo loginInfo) {
    return genericDao.insert(obj);
  }

  /**
   * 查询所有的优惠券发行履历信息
   * 
   * @return 全部的优惠券发行履历信息列表
   */
  public List<NewCouponHistory> loadAll() {
    return genericDao.loadAll();
  }
  
  /**
   * 根据(优惠券编号)查询优惠券发行履历信息
   * 
   * @param couponIssueNo 优惠券编号
   * @return 指定的优惠券发行履历信息
   */
  public NewCouponHistory load(String couponIssueNo) {

    String sqlString = "select * from new_coupon_history where coupon_issue_no = ?";
    List<NewCouponHistory> list = this.findByQuery(sqlString, couponIssueNo);

    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 更新任务
   * 
   * @param obj 任务信息
   * @param loginInfo 更新人信息
   */
  public void update(NewCouponHistory obj, LoginInfo loginInfo) {
    genericDao.update(obj,loginInfo);
  }

  /**
   * 删除任务
   * 
   * @param obj 删除的任务信息
   */
  public void delete(NewCouponHistory transactionObject) {
    genericDao.delete(transactionObject);
  }

  /**
   * 添加任务
   * 
   * @param obj 任务信息
   * @return 添加成功时返回任务编号
   */
  public Long insert(NewCouponHistory transactionObject) {
    return genericDao.insert(transactionObject);
  }

  /**
   * 更新任务
   * 
   * @param obj 任务信息
   */
  public void update(NewCouponHistory transactionObject) {
    genericDao.update(transactionObject);
  }

@Override
public NewCouponHistory loadByRowid(Long id) {
	// TODO Auto-generated method stub
	return null;
}
}