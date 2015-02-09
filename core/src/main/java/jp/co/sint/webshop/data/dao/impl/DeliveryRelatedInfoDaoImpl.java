 package jp.co.sint.webshop.data.dao.impl;

/**
 * 配送公司关联情报表
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
import jp.co.sint.webshop.data.dao.DeliveryRelatedInfoDao;
import jp.co.sint.webshop.data.dto.DeliveryRelatedInfo;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 配送公司关联情报
 * 
 * @author ob
 * 
 */
public class DeliveryRelatedInfoDaoImpl implements DeliveryRelatedInfoDao, Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** Generic Dao */
	private GenericDaoImpl<DeliveryRelatedInfo, Long> genericDao;

	/** SessionFactory */
	private SessionFactory sessionFactory;

	/**
	 * Constructor:
	 */
	public DeliveryRelatedInfoDaoImpl() {
		genericDao = new GenericDaoImpl<DeliveryRelatedInfo, Long>(DeliveryRelatedInfo.class);
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
	 * 删除配送公司关联情报
	 * 
	 * @param obj
	 *            删除的配送公司关联情报信息
	 * @param loginInfo
	 *            删除人信息
	 */
	public void delete(DeliveryRelatedInfo obj, LoginInfo loginInfo) {
		genericDao.delete(obj, loginInfo);
	}

	/**
	 * 根据Query对象查询配送公司关联情报信息列表。
	 * 
	 * @param query
	 *            查询条件信息
	 * @return 配送公司关联情报表信息List
	 */
	public List<DeliveryRelatedInfo> findByQuery(Query query) {
		return genericDao.findByQuery(query);
	}

	/**
	 * 根据sqlString查询配送公司关联情报信息
	 * 
	 * @param sqlString
	 *            sql语句
	 * @param params
	 *            参数值
	 * @return 配送公司关联情报信息列表
	 */
	public List<DeliveryRelatedInfo> findByQuery(String sqlString, Object... params) {
		return genericDao.findByQuery(sqlString, params);
	}

	/**
	 * 添加配送公司关联情报
	 * 
	 * @param obj
	 *            配送公司关联情报信息
	 * @param loginInfo
	 *            添加人信息
	 * @return 添加成功时返回 jobNo任务编号
	 */
	public Long insert(DeliveryRelatedInfo obj, LoginInfo loginInfo) {
		return genericDao.insert(obj);
	}

	/**
	 * 根据(店铺编号)查询配送公司关联情报信息
	 * 
	 * @param shopCode
	 *            店铺编号
	 * @return 指定的配送公司关联情报信息
	 */
	public List<DeliveryRelatedInfo> load(String shopCode, String deliveryCompanyNo,String prefectureCode) {

		Object[] params = new Object[]{shopCode, deliveryCompanyNo,prefectureCode};
		String sqlString = "SELECT * FROM DELIVERY_RELATED_INFO WHERE SHOP_CODE = ? " +
				"AND DELIVERY_COMPANY_NO = ? AND PREFECTURE_CODE=? ORDER BY COD_TYPE,DELIVERY_DATE_TYPE,DELIVERY_APPOINTED_TIME_TYPE" +
				",COALESCE(DELIVERY_APPOINTED_START_TIME,99),COALESCE(DELIVERY_APPOINTED_END_TIME,99)";
		return genericDao.findByQuery(sqlString, params);
	}

	/**
	 * 更新任务
	 * 
	 * @param obj 任务信息
	 * @param loginInfo 更新人信息
	 */
	public void update(DeliveryRelatedInfo obj, LoginInfo loginInfo) {
		genericDao.update(obj, loginInfo);
	}

	/**
	 * 删除任务
	 * 
	 * @param obj
	 *            删除的任务信息
	 */
	public void delete(DeliveryRelatedInfo transactionObject) {
		genericDao.delete(transactionObject);
	}

	/**
	 * 添加任务
	 * 
	 * @param obj
	 *            任务信息
	 * @return 添加成功时返回任务编号
	 */
	public Long insert(DeliveryRelatedInfo transactionObject) {
		return genericDao.insert(transactionObject);
	}

	/**
	 * 更新任务
	 * @param obj 任务信息
	 */
	public void update(DeliveryRelatedInfo transactionObject) {
		genericDao.update(transactionObject);
	}

	/**
	 * 指定数据库行ID对应配送公司信息
	 * 
	 * @param id 数据行ID
	 * @return id对应应配送公司信息
	 */
	public DeliveryRelatedInfo loadByRowid(Long id) {
		return genericDao.loadByRowid(id);
	}


    /**
	 * 主キー列の値を指定してキャンペーンを削除します。
	 * @return テーブル全データ分のCustomerGroupのリスト
	 */
    public List<DeliveryRelatedInfo> loadAll() {
	    return genericDao.loadAll();
    }

	/* (non-Javadoc)
	 * @see jp.co.sint.webshop.data.dao.DeliveryRelatedInfoDao#load(java.lang.String)
	 */
	@Override
	public DeliveryRelatedInfo load(String deliveryRelatedInfoNo) {
		Object[] params = new Object[]{deliveryRelatedInfoNo};
		String sqlString = "SELECT * FROM DELIVERY_RELATED_INFO WHERE DELIVERY_RELATED_INFO_NO = ? ";
		 List<DeliveryRelatedInfo> result = genericDao.findByQuery(sqlString, params);
		    if (result.size() > 0) {
		      return result.get(0);
		    } else {
		      return null;
		    }
	}
}