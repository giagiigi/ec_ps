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
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 会员组别优惠表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface CustomerGroupCampaignDao extends GenericDao<CustomerGroupCampaign, Long> {

  /**
   * 添加会员组别优惠
   * 
   * @param obj 会员组别优惠
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(CustomerGroupCampaign obj, LoginInfo loginInfo);

  /**
   * 更新会员组别优惠
   * 
   * @param obj 维修会员组别优惠
   * @param loginInfo 更新人信息
   */
  void update(CustomerGroupCampaign obj, LoginInfo loginInfo);

  /**
   * 删除会员组别优惠
   * 
   * @param obj 删除的会员组别优惠
   * @param loginInfo 删除人信息
   */
  void delete(CustomerGroupCampaign obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询会员组别优惠列表。
   * 
   * @param query 查询条件信息
   * @return 会员组别优惠信息List
   */
  List<CustomerGroupCampaign> findByQuery(Query query);

  /**
   * 根据sqlString查询会员组别优惠
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 会员组别优惠表信息List
   */
  List<CustomerGroupCampaign> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的会员组别优惠
   * 
   * @return 全部的会员组别优惠列表
   */
  List<CustomerGroupCampaign> loadAll();
  
  /**
   * 根据(店铺编号)查询会员组别优惠
   * 
   * @param shopCode 店铺编号
   * @return 指定的会员组别优惠信息
   */
  public CustomerGroupCampaign load(String shopCode);

}