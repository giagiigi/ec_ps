package jp.co.sint.webshop.data.dao.impl;

/**
 * 企划表
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
import jp.co.sint.webshop.data.dao.PlanDao;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 企划表
 * 
 * @author ob
 * 
 */
public class PlanDaoImpl implements PlanDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<Plan, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public PlanDaoImpl() {
    genericDao = new GenericDaoImpl<Plan, Long>(Plan.class);
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
   * 删除企划
   * 
   * @param obj 删除的企划信息
   * @param loginInfo 删除人信息
   */
  public void delete(Plan obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 根据Query对象查询企划信息列表。
   * 
   * @param query 查询条件信息
   * @return 企划表信息List
   */
  public List<Plan> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * 根据sqlString查询企划信息
   * 
   * @param sqlString sql语句
   * @param params  参数值
   * @return 企划信息列表
   */
  public List<Plan> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * 添加企划
   * 
   * @param obj 企划信息
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  public Long insert(Plan obj, LoginInfo loginInfo) {
    return genericDao.insert(obj);
  }

  /**
   * 查询所有的企划信息
   * 
   * @return 全部的企划信息列表
   */
  public List<Plan> loadAll() {
    return genericDao.loadAll();
  }
  
  /**
   * 根据(企划编号)查询企划信息
   * 
   * @param planCode 企划编号
   * @return 指定的企划信息
   */
  public Plan load(String planCode) {

    String sqlString = "select * from plan where plan_code = ?";
    List<Plan> list = this.findByQuery(sqlString, planCode);

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
  public void update(Plan obj, LoginInfo loginInfo) {
    genericDao.update(obj,loginInfo);
  }

  /**
   * 删除任务
   * 
   * @param obj 删除的任务信息
   */
  public void delete(Plan transactionObject) {
    genericDao.delete(transactionObject);
  }

  /**
   * 添加任务
   * 
   * @param obj 任务信息
   * @return 添加成功时返回任务编号
   */
  public Long insert(Plan transactionObject) {
    return genericDao.insert(transactionObject);
  }

  /**
   * 更新任务
   * 
   * @param obj 任务信息
   */
  public void update(Plan transactionObject) {
    genericDao.update(transactionObject);
  }

@Override
public Plan loadByRowid(Long id) {
	// TODO Auto-generated method stub
	return null;
}
}