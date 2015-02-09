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
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 优惠券规则表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface NewCouponRuleDao extends GenericDao<NewCouponRule, Long> {

  /**
   * 添加优惠券规则
   * 
   * @param obj 优惠券规则
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(NewCouponRule obj, LoginInfo loginInfo);

  /**
   * 更新优惠券规则
   * 
   * @param obj 维修优惠券规则
   * @param loginInfo 更新人信息
   */
  void update(NewCouponRule obj, LoginInfo loginInfo);

  /**
   * 删除优惠券规则
   * 
   * @param obj 删除的优惠券规则
   * @param loginInfo 删除人信息
   */
  void delete(NewCouponRule obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询优惠券规则列表。
   * 
   * @param query 查询条件信息
   * @return 优惠券规则信息List
   */
  List<NewCouponRule> findByQuery(Query query);

  /**
   * 根据sqlString查询优惠券规则
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 优惠券规则表信息List
   */
  List<NewCouponRule> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的优惠券规则
   * 
   * @return 全部的优惠券规则列表
   */
  List<NewCouponRule> loadAll();
  
  /**
   * 根据(优惠券规则编号)查询优惠券规则
   * 
   * @param couponCode 优惠券规则编号
   * @return 指定的优惠券规则信息
   */
  public NewCouponRule load(String couponCode);

}