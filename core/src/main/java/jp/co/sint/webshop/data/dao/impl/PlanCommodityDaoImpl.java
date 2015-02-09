package jp.co.sint.webshop.data.dao.impl;

/**
 * 企划关联商品表
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
import jp.co.sint.webshop.data.dao.PlanCommodityDao;
import jp.co.sint.webshop.data.dto.PlanCommodity;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 企划关联商品表
 * 
 * @author ob
 * 
 */
public class PlanCommodityDaoImpl implements PlanCommodityDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<PlanCommodity, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PlanCommodityDaoImpl() {
    genericDao = new GenericDaoImpl<PlanCommodity, Long>(PlanCommodity.class);
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
   * 删除企划关联商品
   * 
   * @param obj 删除的企划关联商品信息
   * @param loginInfo 删除人信息
   */
  public void delete(PlanCommodity obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 根据Query对象查询企划关联商品信息列表。
   * 
   * @param query 查询条件信息
   * @return 企划关联商品表信息List
   */
  public List<PlanCommodity> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * 根据sqlString查询企划关联商品信息
   * 
   * @param sqlString sql语句
   * @param params  参数值
   * @return 企划关联商品信息列表
   */
  public List<PlanCommodity> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * 添加企划关联商品
   * 
   * @param obj 企划关联商品信息
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  public Long insert(PlanCommodity obj, LoginInfo loginInfo) {
    return genericDao.insert(obj);
  }

  /**
   * 查询所有的企划关联商品信息
   * 
   * @return 全部的企划关联商品信息列表
   */
  public List<PlanCommodity> loadAll() {
    return genericDao.loadAll();
  }
  
  /**
   * 根据(企划编号)查询企划关联商品信息
   * 
   * @param planCode 企划编号
   * @return 指定的企划关联商品信息
   */
  public PlanCommodity load(String planCode, String detailType, String detailCode, String commodityCode) {

    String sqlString = "select * from plan_commodity where plan_code = ? and detail_type = ? and detail_code = ? and commodity_code = ?";
    List<PlanCommodity> list = this.findByQuery(sqlString, planCode, detailType, detailCode, commodityCode);

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
  public void update(PlanCommodity obj, LoginInfo loginInfo) {
    genericDao.update(obj,loginInfo);
  }

  /**
   * 删除任务
   * 
   * @param obj 删除的任务信息
   */
  public void delete(PlanCommodity transactionObject) {
    genericDao.delete(transactionObject);
  }

  /**
   * 添加任务
   * 
   * @param obj 任务信息
   * @return 添加成功时返回任务编号
   */
  public Long insert(PlanCommodity transactionObject) {
    return genericDao.insert(transactionObject);
  }

  /**
   * 更新任务
   * 
   * @param obj 任务信息
   */
  public void update(PlanCommodity transactionObject) {
    genericDao.update(transactionObject);
  }

@Override
public PlanCommodity loadByRowid(Long id) {
	// TODO Auto-generated method stub
	return null;
}
}