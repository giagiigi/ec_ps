//
//Copyright(C) 2007-2008 OB.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;
import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.JdDeliveryRelatedInfo;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 配送公司关联情报
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface JdDeliveryRelatedInfoDao extends GenericDao<JdDeliveryRelatedInfo, Long> {

  /**
   * 添加配送公司关联情报
   * 
   * @param obj 配送公司关联情报
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(JdDeliveryRelatedInfo obj, LoginInfo loginInfo);

  /**
   * 更新配送公司关联情报
   * 
   * @param obj 配送公司关联情报
   * @param loginInfo 更新人信息
   */
  void update(JdDeliveryRelatedInfo obj, LoginInfo loginInfo);

  /**
   * 删除配送公司关联情报
   * 
   * @param obj 删除的配送公司关联情报
   * @param loginInfo 删除人信息
   */
  void delete(JdDeliveryRelatedInfo obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询配送公司关联情报列表。
   * 
   * @param query 查询条件信息
   * @return 配送公司关联情报信息List
   */
  List<JdDeliveryRelatedInfo> findByQuery(Query query);

  /**
   * 根据sqlString查询配送公司关联情报
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 配送公司关联情报表信息List
   */
  List<JdDeliveryRelatedInfo> findByQuery(String sqlString, Object... params);

  
  /**
   * 根据(店铺编号)查询配送公司关联情报
   * 
   * @param shopCode 店铺编号
   * @return List<DeliveryRelatedInfo>
   */
  List<JdDeliveryRelatedInfo> load(String shopCode, String deliveryCompanyNo,String prefectureCode);
  
  /**
   * 根据(配送关联编号)查询配送公司关联情报
   * 
   * @param deliveryRelatedInfoNo 配送关联编号
   * @return DeliveryRelatedInfo
   */
  JdDeliveryRelatedInfo load(String deliveryRelatedInfoNo);

}