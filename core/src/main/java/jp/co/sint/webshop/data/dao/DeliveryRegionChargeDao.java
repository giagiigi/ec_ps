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
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 运费表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface DeliveryRegionChargeDao extends GenericDao<DeliveryRegionCharge, Long> {

  /**
   * 添加运费
   * 
   * @param obj 运费
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(DeliveryRegionCharge obj, LoginInfo loginInfo);

  /**
   * 更新运费
   * 
   * @param obj 维修运费
   * @param loginInfo 更新人信息
   */
  void update(DeliveryRegionCharge obj, LoginInfo loginInfo);

  /**
   * 删除运费
   * 
   * @param obj 删除的运费
   * @param loginInfo 删除人信息
   */
  void delete(DeliveryRegionCharge obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询运费列表。
   * 
   * @param query 查询条件信息
   * @return 运费信息List
   */
  List<DeliveryRegionCharge> findByQuery(Query query);

  /**
   * 根据sqlString查询运费
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 运费表信息List
   */
  List<DeliveryRegionCharge> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的运费
   * 
   * @return 全部的运费列表
   */
  List<DeliveryRegionCharge> loadAll();
  
  /**
   * 根据(地域编号)查询运费
   * 
   * @param prefectureCode 地域编号
   * @param deliveryCompanyNo 配送公司编号
   * @return 指定的运费信息
   */
  public DeliveryRegionCharge load(String prefectureCode, String deliveryCompanyNo);

}