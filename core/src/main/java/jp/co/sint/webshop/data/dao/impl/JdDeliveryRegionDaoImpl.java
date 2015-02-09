package jp.co.sint.webshop.data.dao.impl;

/**
 * 配送会社表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dao.JdDeliveryRegionDao;
import jp.co.sint.webshop.data.dto.JdDeliveryRegion;
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 配送会社表
 * 
 * @author ob
 * 
 */
public class JdDeliveryRegionDaoImpl implements JdDeliveryRegionDao, Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** Generic Dao */
	private GenericDaoImpl<JdDeliveryRegion, Long> genericDao;

	/** SessionFactory */
	private SessionFactory sessionFactory;

	/**
	 * Constructor:
	 */
	public JdDeliveryRegionDaoImpl() {
		genericDao = new GenericDaoImpl<JdDeliveryRegion, Long>(
		    JdDeliveryRegion.class);
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
	 * 删除配送会社
	 * 
	 * @param obj
	 *            删除的配送会社信息
	 * @param loginInfo
	 *            删除人信息
	 */
	public void delete(JdDeliveryRegion obj, LoginInfo loginInfo) {
		genericDao.delete(obj, loginInfo);
	}

	/**
	 * 根据Query对象查询配送会社信息列表。
	 * 
	 * @param query
	 *            查询条件信息
	 * @return 配送会社表信息List
	 */
	public List<JdDeliveryRegion> findByQuery(Query query) {
		return genericDao.findByQuery(query);
	}

	/**
	 * 根据sqlString查询配送会社信息
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param params
	 *            参数值
	 * @return 配送会社信息列表
	 */
	public List<JdDeliveryRegion> findByQuery(String sqlString, Object... params) {
		return genericDao.findByQuery(sqlString, params);
	}

	/**
	 * 添加配送会社
	 * 
	 * @param obj
	 *            配送会社信息
	 * @param loginInfo
	 *            添加人信息
	 * @return 添加成功时返回 jobNo任务编号
	 */
	public Long insert(JdDeliveryRegion obj, LoginInfo loginInfo) {
		return genericDao.insert(obj);
	}

	/**
	 * 查询所有的配送会社信息
	 * 
	 * @return 全部的配送会社信息列表
	 */
	public List<JdDeliveryRegion> loadAll() {
		return genericDao.loadAll();
	}


	/**
	 * 更新任务
	 * 
	 * @param obj
	 *            任务信息
	 * @param loginInfo
	 *            更新人信息
	 */
	public void update(JdDeliveryRegion obj, LoginInfo loginInfo) {
		genericDao.update(obj, loginInfo);
	}

	/**
	 * 删除任务
	 * 
	 * @param obj
	 *            删除的任务信息
	 */
	public void delete(JdDeliveryRegion transactionObject) {
		genericDao.delete(transactionObject);
	}

	/**
	 * 添加任务
	 * 
	 * @param obj
	 *            任务信息
	 * @return 添加成功时返回任务编号
	 */
	public Long insert(JdDeliveryRegion transactionObject) {
		return genericDao.insert(transactionObject);
	}

	/**
	 * 更新任务
	 * 
	 * @param obj
	 *            任务信息
	 */
	public void update(JdDeliveryRegion transactionObject) {
		genericDao.update(transactionObject);
	}

	/**
	 * 指定数据库行ID对应配送公司信息
	 * 
	 * @param id
	 *            数据行ID
	 * @return id对应应配送公司信息
	 */
	public JdDeliveryRegion loadByRowid(Long id) {
		return genericDao.loadByRowid(id);
	}

	/**
	 * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
	 * 
	 * @param shopCode
	 *            ショップコード
	 * @param campaignCode
	 *            キャンペーンコード
	 * @return 主キー列の値に対応するCampaignの行が存在すればtrue
	 */
	public boolean exists(String shopCode, String deliveryCompanyNo, String prefectureCode) {
		Object[] params = new Object[] { shopCode, deliveryCompanyNo, prefectureCode };
		final String query = "SELECT COUNT(*) FROM JD_DELIVERY_REGION"
				+ " WHERE SHOP_CODE = ?" + " AND DELIVERY_COMPANY_NO = ?" + " AND PREFECTURE_CODE = ?";
		Object result = genericDao.executeScalar(query, params);
		return ((Number) result).intValue() > 0;
	}

	/**
	 * 主キー列の値を指定してキャンペーンを削除します。
	 * 
	 * @param shopCode
	 *            ショップコード
	 * @param campaignCode
	 *            キャンペーンコード
	 */
	public void deleteAll(String shopCode, String deliveryCompanyNo) {
		Object[] params = new Object[] { shopCode, deliveryCompanyNo };
		final String query = "DELETE FROM JD_DELIVERY_REGION"
				+ " WHERE SHOP_CODE = ?" + " AND DELIVERY_COMPANY_NO = ?";
		genericDao.updateByQuery(query, params);
	}



}