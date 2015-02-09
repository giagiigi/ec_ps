//
// Copyright(C) 2007-2008 OB.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.NewCouponRuleLssueInfo;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 优惠券规则表
 * 
 * @author ob.
 * @version 1.0.0
 */
public interface NewCouponRuleLssueInfoDao extends GenericDao<NewCouponRuleLssueInfo, Long> {

  /**
   * 添加优惠券规则
   * 
   * @param obj
   *          优惠券规则
   * @param loginInfo
   *          添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(NewCouponRuleLssueInfo obj, LoginInfo loginInfo);

  /**
   * 更新优惠券规则
   * 
   * @param obj
   *          维修优惠券规则
   * @param loginInfo
   *          更新人信息
   */
  void update(NewCouponRuleLssueInfo obj, LoginInfo loginInfo);

  /**
   * 删除优惠券规则
   * 
   * @param obj
   *          删除的优惠券规则
   * @param loginInfo
   *          删除人信息
   */
  void delete(NewCouponRuleLssueInfo obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询优惠券规则列表。
   * 
   * @param query
   *          查询条件信息
   * @return 优惠券规则信息List
   */
  List<NewCouponRuleLssueInfo> findByQuery(Query query);

  /**
   * 根据sqlString查询优惠券规则
   * 
   * @param sqlString
   *          sql语句
   * @param params
   *          参数值
   * @return 优惠券规则表信息List
   */
  List<NewCouponRuleLssueInfo> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的优惠券规则
   * 
   * @return 全部的优惠券规则列表
   */
  List<NewCouponRuleLssueInfo> loadAll();

  /**
   * 根据(优惠券规则编号)查询优惠券规则
   * 
   * @param couponCode
   *          优惠券规则编号
   * @return 指定的优惠券规则信息
   */
  public NewCouponRuleLssueInfo load(String couponCode);

  public NewCouponRuleLssueInfo load(String couponCode, Long couponUseNo);

  public NewCouponRuleLssueInfo load(String couponCode, String typeCode, String flg);

  /**
   * 根据(优惠券规则编号)删除优惠券规则
   * 
   * @param couponCode
   *          优惠券规则编号
   */
  public void delete(String couponCode);

}
