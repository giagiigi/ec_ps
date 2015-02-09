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
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 配送会社表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
public interface DeliveryCompanyDao extends GenericDao<DeliveryCompany, Long> {

  /**
   * 添加配送会社
   * 
   * @param obj 配送会社
   * @param loginInfo 添加人信息
   * @return 添加成功时返回 jobNo任务编号
   */
  Long insert(DeliveryCompany obj, LoginInfo loginInfo);

  /**
   * 更新配送会社
   * 
   * @param obj 维修配送会社
   * @param loginInfo 更新人信息
   */
  void update(DeliveryCompany obj, LoginInfo loginInfo);

  /**
   * 删除配送会社
   * 
   * @param obj 删除的配送会社
   * @param loginInfo 删除人信息
   */
  void delete(DeliveryCompany obj, LoginInfo loginInfo);

  /**
   * 根据Query对象查询配送会社列表。
   * 
   * @param query 查询条件信息
   * @return 配送会社信息List
   */
  List<DeliveryCompany> findByQuery(Query query);

  /**
   * 根据sqlString查询配送会社
   * 
   * @param sqlString sql语句
   * @param params 参数值
   * @return 配送会社表信息List
   */
  List<DeliveryCompany> findByQuery(String sqlString, Object... params);

  /**
   * 查询所有的配送会社
   * 
   * @return 全部的配送会社列表
   */
  List<DeliveryCompany> loadAll();
  
  /**
   * 根据(店铺编号)查询配送会社
   * 
   * @param shopCode 店铺编号
   * @return 指定的配送会社信息
   */
  DeliveryCompany load(String shopCode, String deliveryCompanyNo);
  
  /**
   * 根据(店铺编号)查询配送会社
   * 
   * @param shopCode 店铺编号
   * @return 指定的配送会社信息
   */
  DeliveryCompany load(String deliveryCompanyNo);
  
  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignの行が存在すればtrue
   */
  boolean exists(String shopCode, String deliveryCompanyNo);

}