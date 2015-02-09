package jp.co.sint.webshop.data.dao.impl;

/**
 * 会员组别优惠表
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
import jp.co.sint.webshop.data.dao.CustomerGroupCampaignDao;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 会员组别优惠表
 * 
 * @author ob
 * 
 */
public class CustomerGroupCampaignDaoImpl implements CustomerGroupCampaignDao, Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** Generic Dao */
  private GenericDaoImpl<CustomerGroupCampaign, Long> genericDao;

  /** SessionFactory */
  private SessionFactory sessionFactory;

  /**
   * Constructor:
   */
  public CustomerGroupCampaignDaoImpl() {
    genericDao = new GenericDaoImpl<CustomerGroupCampaign, Long>(CustomerGroupCampaign.class);
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
   * 删除会员组别优惠
   * 
   * @param obj 删除的会员组别优惠信息
   * @param loginInfo 删除人信息
   */
  public void delete(CustomerGroupCampaign obj, LoginInfo loginInfo) {
    genericDao.delete(obj, loginInfo);
  }

  /**
   * 根据Query对象查询会员组别优惠信息列表。
   * 
   * @param query 查询条件信息
   * @return 会员组别优惠表信息List
   */
  public List<CustomerGroupCampaign> findByQuery(Query query) {
    return genericDao.findByQuery(query);
  }

  /**
   * 根据sqlString查询会员组别优惠信息
   * 
   * @param sqlString sql语句
   * @param params  参数值
   * @return 会员组别优惠信息列表
   */
  public List<CustomerGroupCampaign> findByQuery(String sqlString, Object... params) {
    return genericDao.findByQuery(sqlString, params);
  }

  /**
   * 添加会员组别优惠
   * 
   * @param obj 会员组别优惠信息
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  public Long insert(CustomerGroupCampaign obj, LoginInfo loginInfo) {
    return genericDao.insert(obj);
  }

  /**
   * 查询所有的会员组别优惠信息
   * 
   * @return 全部的会员组别优惠信息列表
   */
  public List<CustomerGroupCampaign> loadAll() {
    return genericDao.loadAll();
  }
  
  /**
   * 根据(店铺编号)查询会员组别优惠信息
   * 
   * @param shopCode 店铺编号
   * @return 指定的会员组别优惠信息
   */
  public CustomerGroupCampaign load(String campaignCode) {

    String sqlString = "select * from customer_group_campaign where campaign_code = ?";
    List<CustomerGroupCampaign> list = this.findByQuery(sqlString, campaignCode);

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
  public void update(CustomerGroupCampaign obj, LoginInfo loginInfo) {
    genericDao.update(obj,loginInfo);
  }

  /**
   * 删除任务
   * 
   * @param obj 删除的任务信息
   */
  public void delete(CustomerGroupCampaign transactionObject) {
    genericDao.delete(transactionObject);
  }

  /**
   * 添加任务
   * 
   * @param obj 任务信息
   * @return 添加成功时返回任务编号
   */
  public Long insert(CustomerGroupCampaign transactionObject) {
    return genericDao.insert(transactionObject);
  }

  /**
   * 更新任务
   * 
   * @param obj 任务信息
   */
  public void update(CustomerGroupCampaign transactionObject) {
    genericDao.update(transactionObject);
  }

@Override
public CustomerGroupCampaign loadByRowid(Long id) {
	// TODO Auto-generated method stub
	return null;
}
}