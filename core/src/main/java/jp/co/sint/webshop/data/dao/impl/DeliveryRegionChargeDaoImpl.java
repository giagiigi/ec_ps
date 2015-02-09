package jp.co.sint.webshop.data.dao.impl;

/**
 * 运费表
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
import jp.co.sint.webshop.data.dao.DeliveryRegionChargeDao;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 运费表
 * 
 * @author ob
 * 
 */
public class DeliveryRegionChargeDaoImpl implements DeliveryRegionChargeDao,
		Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** Generic Dao */
	private GenericDaoImpl<DeliveryRegionCharge, Long> genericDao;

	/** SessionFactory */
	private SessionFactory sessionFactory;

	/**
	 * Constructor:
	 */
	public DeliveryRegionChargeDaoImpl() {
		genericDao = new GenericDaoImpl<DeliveryRegionCharge, Long>(
				DeliveryRegionCharge.class);
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
	 * @param factory
	 *            SessionFactory实例
	 */
	public void setSessionFactory(SessionFactory factory) {
		this.sessionFactory = factory;
		genericDao.setSessionFactory(factory);
	}

	/**
	 * 删除运费
	 * 
	 * @param obj
	 *            删除的运费信息
	 * @param loginInfo
	 *            删除人信息
	 */
	public void delete(DeliveryRegionCharge obj, LoginInfo loginInfo) {
		genericDao.delete(obj, loginInfo);
	}

	/**
	 * 根据Query对象查询运费信息列表。
	 * 
	 * @param query
	 *            查询条件信息
	 * @return 运费表信息List
	 */
	public List<DeliveryRegionCharge> findByQuery(Query query) {
		return genericDao.findByQuery(query);
	}

	/**
	 * 根据sqlString查询运费信息
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param params
	 *            参数值
	 * @return 运费信息列表
	 */
	public List<DeliveryRegionCharge> findByQuery(String sqlString,
			Object... params) {
		return genericDao.findByQuery(sqlString, params);
	}

	/**
	 * 添加运费
	 * 
	 * @param obj
	 *            运费信息
	 * @param loginInfo
	 *            添加人信息
	 * @return 添加成功时返回 jobNo任务编号
	 */
	public Long insert(DeliveryRegionCharge obj, LoginInfo loginInfo) {
		return genericDao.insert(obj);
	}

	/**
	 * 查询所有的运费信息
	 * 
	 * @return 全部的运费信息列表
	 */
	public List<DeliveryRegionCharge> loadAll() {
		return genericDao.loadAll();
	}

	/**
	 * 根据(地域编号)查询运费信息
	 * 
	 * @param prefectureCode
	 *            地域编号
	 * @param deliveryCompanyNo
	 *            配送公司编号
	 * @return 指定的运费信息
	 */
	public DeliveryRegionCharge load(String prefectureCode, String deliveryCompanyNo) {

		String sqlString = "SELECT * FROM DELIVERY_REGION_CHARGE WHERE PREFECTURE_CODE = ? AND DELIVERY_COMPANY_NO = ?";
		List<DeliveryRegionCharge> list = this.findByQuery(sqlString, prefectureCode, deliveryCompanyNo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}else{// upd by lc 2012-08-22 for 当传入配送公司无对应运费时，尝试再次使用默认配送公司查询
		  list = this.findByQuery(sqlString, prefectureCode, "D000");
		  if (list != null && list.size() > 0) {
	      return list.get(0);
	    }
		}
		return null;
	}

	/**
	 * 更新任务
	 * 
	 * @param obj 任务信息
	 * @param loginInfo 更新人信息
	 */
	public void update(DeliveryRegionCharge obj, LoginInfo loginInfo) {
		genericDao.update(obj, loginInfo);
	}

	/**
	 * 删除任务
	 * 
	 * @param obj  删除的任务信息
	 */
	public void delete(DeliveryRegionCharge transactionObject) {
		genericDao.delete(transactionObject);
	}

	/**
	 * 添加任务
	 * 
	 * @param obj
	 *            任务信息
	 * @return 添加成功时返回任务编号
	 */
	public Long insert(DeliveryRegionCharge transactionObject) {
		return genericDao.insert(transactionObject);
	}

	/**
	 * 更新任务
	 * 
	 * @param obj
	 *            任务信息
	 */
	public void update(DeliveryRegionCharge transactionObject) {
		genericDao.update(transactionObject);
	}

	@Override
	public DeliveryRegionCharge loadByRowid(Long id) {
		return null;
	}
}