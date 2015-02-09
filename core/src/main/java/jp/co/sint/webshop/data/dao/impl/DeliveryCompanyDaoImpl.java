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
import jp.co.sint.webshop.data.hibernate.GenericDaoImpl;
import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 配送会社表
 * 
 * @author ob
 * 
 */
public class DeliveryCompanyDaoImpl implements DeliveryCompanyDao, Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** Generic Dao */
	private GenericDaoImpl<DeliveryCompany, Long> genericDao;

	/** SessionFactory */
	private SessionFactory sessionFactory;

	/**
	 * Constructor:
	 */
	public DeliveryCompanyDaoImpl() {
		genericDao = new GenericDaoImpl<DeliveryCompany, Long>(
				DeliveryCompany.class);
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
	public void delete(DeliveryCompany obj, LoginInfo loginInfo) {
		genericDao.delete(obj, loginInfo);
	}

	/**
	 * 根据Query对象查询配送会社信息列表。
	 * 
	 * @param query
	 *            查询条件信息
	 * @return 配送会社表信息List
	 */
	public List<DeliveryCompany> findByQuery(Query query) {
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
	public List<DeliveryCompany> findByQuery(String sqlString, Object... params) {
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
	public Long insert(DeliveryCompany obj, LoginInfo loginInfo) {
		return genericDao.insert(obj);
	}

	/**
	 * 查询所有的配送会社信息
	 * 
	 * @return 全部的配送会社信息列表
	 */
	public List<DeliveryCompany> loadAll() {
		return genericDao.loadAll();
	}

	/**
	 * 根据(店铺编号)查询配送会社信息
	 * 
	 * @param shopCode
	 *            店铺编号
	 * @return 指定的配送会社信息
	 */
	public DeliveryCompany load(String shopCode, String deliveryCompanyNo) {
		Object[] params = new Object[] { shopCode, deliveryCompanyNo };
		String sqlString = "SELECT * FROM DELIVERY_COMPANY WHERE SHOP_CODE = ? AND DELIVERY_COMPANY_NO = ?";
		List<DeliveryCompany> list = genericDao.findByQuery(sqlString, params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 更新任务
	 * 
	 * @param obj
	 *            任务信息
	 * @param loginInfo
	 *            更新人信息
	 */
	public void update(DeliveryCompany obj, LoginInfo loginInfo) {
		genericDao.update(obj, loginInfo);
	}

	/**
	 * 删除任务
	 * 
	 * @param obj
	 *            删除的任务信息
	 */
	public void delete(DeliveryCompany transactionObject) {
		genericDao.delete(transactionObject);
	}

	/**
	 * 添加任务
	 * 
	 * @param obj
	 *            任务信息
	 * @return 添加成功时返回任务编号
	 */
	public Long insert(DeliveryCompany transactionObject) {
		return genericDao.insert(transactionObject);
	}

	/**
	 * 更新任务
	 * 
	 * @param obj
	 *            任务信息
	 */
	public void update(DeliveryCompany transactionObject) {
		genericDao.update(transactionObject);
	}

	/**
	 * 指定数据库行ID对应配送公司信息
	 * 
	 * @param id
	 *            数据行ID
	 * @return id对应应配送公司信息
	 */
	public DeliveryCompany loadByRowid(Long id) {
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
	public boolean exists(String shopCode, String deliveryCompanyNo) {
		Object[] params = new Object[] { shopCode, deliveryCompanyNo };
		final String query = "SELECT COUNT(*) FROM DELIVERY_COMPANY"
				+ " WHERE SHOP_CODE = ?" + " AND DELIVERY_COMPANY_NO = ?";
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
	public void delete(String shopCode, String deliveryCompanyNo) {
		Object[] params = new Object[] { shopCode, deliveryCompanyNo };
		final String query = "DELETE FROM DELIVERY_COMPANY"
				+ " WHERE SHOP_CODE = ?" + " AND DELIVERY_COMPANY_NO = ?";
		genericDao.updateByQuery(query, params);
	}

	/**
	 * 根据(店铺编号)查询配送会社
	 * 
	 * @param shopCode
	 *            店铺编号
	 * @return 指定的配送会社信息
	 */
	public DeliveryCompany load(String deliveryCompanyNo) {
	    String sqlString = "select * from delivery_company where delivery_company_no = ?";
	    List<DeliveryCompany> list = this.findByQuery(sqlString, deliveryCompanyNo);

	    if (list != null && list.size() > 0) {
	      return list.get(0);
	    }
	    return null;
	}

}