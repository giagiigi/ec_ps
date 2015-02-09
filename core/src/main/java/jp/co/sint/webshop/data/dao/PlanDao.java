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
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 企划表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface PlanDao extends GenericDao<Plan, Long> {

  /**
   * 添加企划
   * 
   * @param obj 企划
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(Plan obj, LoginInfo loginInfo);

  /**
   * 更新企划
   * 
   * @param obj 维修企划
   * @param loginInfo 更新人信息
   */
  void update(Plan obj, LoginInfo loginInfo);

  /**
   * 删除企划
   * 
   * @param obj 删除的企划
   * @param loginInfo 删除人信息
   */
  void delete(Plan obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询企划列表。
   * 
   * @param query 查询条件信息
   * @return 企划信息List
   */
  List<Plan> findByQuery(Query query);

  /**
   * 根据sqlString查询企划
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 企划表信息List
   */
  List<Plan> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的企划
   * 
   * @return 全部的企划列表
   */
  List<Plan> loadAll();
  
  /**
   * 根据(企划编号)查询企划
   * 
   * @param planCode 企划编号
   * @return 指定的企划信息
   */
  public Plan load(String planCode);

}