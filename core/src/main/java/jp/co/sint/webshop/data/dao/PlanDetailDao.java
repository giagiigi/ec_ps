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
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 企划关联品牌表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface PlanDetailDao extends GenericDao<PlanDetail, Long> {

  /**
   * 添加企划明细信息
   * 
   * @param obj 企划明细信息
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(PlanDetail obj, LoginInfo loginInfo);

  /**
   * 更新企划明细信息
   * 
   * @param obj 企划明细信息
   * @param loginInfo 更新人信息
   */
  void update(PlanDetail obj, LoginInfo loginInfo);

  /**
   * 删除企划明细信息
   * 
   * @param obj 删除的企划明细信息
   * @param loginInfo 删除人信息
   */
  void delete(PlanDetail obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询企划明细信息
   * 
   * @param query 查询条件信息
   * @return 企划明细信息List
   */
  List<PlanDetail> findByQuery(Query query);

  /**
   * 根据sqlString查询企划明细信息
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 企划明细信息List
   */
  List<PlanDetail> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的企划明细信息
   * 
   * @return 全部的企划明细信息
   */
  List<PlanDetail> loadAll();
  
  /**
   * 根据(企划编号)查询企划明细信息
   * 
   * @param planCode 企划编号
   * @return 指定的企划明细信息
   */
  public PlanDetail load(String planCode, String detailType, String detailCode);

}