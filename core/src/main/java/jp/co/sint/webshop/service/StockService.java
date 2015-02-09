package jp.co.sint.webshop.service;

import java.util.List;

import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.service.stock.StockTempInfo;

/**
 * SI Web Shopping 10 库存管理サービス(OrderService)仕様<br>
 * 
 * @see jp.co.sint.webshop.service.result.ServiceErrorContent
 * @author System Integrator Corp.
 */
public interface StockService {

  /**
   * 库存重新分配。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>库存的重新分配。
   * <ol>
   * <li>参数事物处理对象。</li>
   * <li>参数需要重新计算库存的商品编号。</li>
   * <li>参数TMAPI连协失败商品编号列表。</li>
   * <li>参数JDAPI连协失败商品编号列表。</li>
   * <li>有效库存错误商品编号列表</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>参数商品编号不能为NULL。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>无。</dd>
   * </dl>
   * </p>
   * 
   * @param transactionManager
   *          事物对象
   * @param commodityCode
   *          重新分配库存的商品编号
   * @param tmapi
   *          TMAPI连协失败商品编号列表  
   * @param jdapi
   *          JDAPI连协失败商品编号列表  
   * @param errorStockCommodityCode
   *          有效库存错误商品编号列表                           
   *                            
   * @return サービス実行結果。
   */
  ServiceResult stockRecalculation(TransactionManager transactionManager, String commodityCode,List<String> tmapi,List<String> jdapi,List<String> errorStockCommodityCode,StockTempInfo stockTempInfo);
  ServiceResult stockRecalculation(TransactionManager transactionManager, String commodityCode,List<String> tmapi,List<String> jdapi,List<String> errorStockCommodityCode,StockTempInfo stockTempInfo,boolean batchFlg);
  /**
   * 库存增量登录。
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>库存增量登录。
   * <ol>
   * <li>参数事物处理对象。</li>
   * <li>参数库存增量信息。</li>
   * </ol>
   * </dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>前提条件: </b></dt>
   * <dd>库存增量信息不能为NULL。</dd>
   * </dl>
   * </p>
   * <p>
   * <dl>
   * <dt><b>事後条件: </b></dt>
   * <dd>无。</dd>
   * </dl>
   * </p>
   * 
   * @param transactionManager
   *          事物对象
   * @param stockTempInfo
   *          库存增量信息                     
   *                            
   * @return 无
   */
  void updateStockTemp(TransactionManager transactionManager,StockTempInfo stockTempInfo);
  
  boolean tmallStockCyncroApi(Long tmallCatalogId, Long tmallEffectiveStock, String tmallSkuCode);

  /**
   * 引当数变更事件
   * <p>
   * <dl>
   * <dt><b>処理概要: </b></dt>
   * <dd>引当数发生变动时，自动生成引当数变更临时数据
   *
   * @param orderNo
   *          订单编号
   *        stockChangeType
   *          库存变更区分
   */
  public void setStockTempForAllocatedQuantity(String orderNo, Long stockChangeType);
  
  /**
   * 库存同步失败通知邮件发送处理
   * @param tmallCynchroFailCodeList 天猫库存同步失败商品编号一览
   * @param jdCynchroFailCodeList 京东库存同步失败商品编号一览
   * @param commodityHasNotCodeList 不存在商品编号一览
   */
  void sendStockCynchroMail(List<String> tmallCynchroFailCodeList, List<String> jdCynchroFailCodeList,
      List<String> commodityHasNotCodeList);


  //2014/06/10 库存更新对应 ob_李先超 add start
  /**
   * 取得商品同期化返回结果
   * @param 淘宝API连协失败商品编号列表 tmallErrorList
   *        京东API连协失败商品编号列表 jdErrorList
   *        不存在商品编号列表 noDataErrorList
   * @return 无
   */
  void stockCynchroApi(List<String> tmallErrorList, List<String> jdErrorList, List<String> noDataErrorList);
  
  /**
   * 取得JD套装品信息
   * @param 套装商品编号 commodityCode
   * @return JD套装品信息
   */
  JdSuitCommodity getJdSuitCommodity(String commodityCode);
  
  /**
   * 取得库存再分配处理返回信息
   * @param 套装商品dto suitCommodity
   * @return 库存再分配处理返回信息
   */
  ServiceResult jdStockRedistribution(JdSuitCommodity oldJDSuitCommDto, JdSuitCommodity suitCommodityBean, List<String> tmallApiFailCodeList, List<String> jdApiFailCodeList, List<String> stockFailCodeList);
  
  /**
   * 取得Tmall套装品信息
   * @param 套装商品编号 commodityCode
   * @return Tmall套装品信息
   */
  TmallSuitCommodity getTmallSuitCommodity(String commodityCode);
  
  /**
   * 取得库存再分配处理返回信息
   * @param 套装商品dto suitCommodity
   * @return 库存再分配处理返回信息
   */
  ServiceResult tmallStockRedistribution(TmallSuitCommodity oldTMSuitCommDto, TmallSuitCommodity suitCommodityBean, List<String> tmallApiFailCodeList, List<String> jdApiFailCodeList, List<String> stockFailCodeList);
  
  /**
   * 库存再计算处理
   * @return 库存再计算状态
   */
  ServiceResult stockRecomputingApi();
  // 2014/06/10 库存更新对应 ob_李先超 add end
}
