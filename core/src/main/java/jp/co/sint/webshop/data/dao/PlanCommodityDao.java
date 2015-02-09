//
//Copyright(C) 2007-2008 OB.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.dto.PlanCommodity;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 企划关联商品表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface PlanCommodityDao extends GenericDao<PlanCommodity, Long> {

  /**
   * 添加企划关联商品
   * 
   * @param obj 企划关联商品
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(PlanCommodity obj, LoginInfo loginInfo);

  /**
   * 更新企划关联商品
   * 
   * @param obj 维修企划关联商品
   * @param loginInfo 更新人信息
   */
  void update(PlanCommodity obj, LoginInfo loginInfo);

  /**
   * 删除企划关联商品
   * 
   * @param obj 删除的企划关联商品
   * @param loginInfo 删除人信息
   */
  void delete(PlanCommodity obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询企划关联商品列表。
   * 
   * @param query 查询条件信息
   * @return 企划关联商品信息List
   */
  List<PlanCommodity> findByQuery(Query query);

  /**
   * 根据sqlString查询企划关联商品
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 企划关联商品表信息List
   */
  List<PlanCommodity> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的企划关联商品
   * 
   * @return 全部的企划关联商品列表
   */
  List<PlanCommodity> loadAll();
  
  /**
   * 根据(企划编号)查询企划关联商品
   * 
   * @param planCode 企划编号
   * @return 指定的企划关联商品信息
   */
  public PlanCommodity load(String planCode, String detailType, String detailCode, String commodityCode);

}