package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.catalog.TmallStockInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteTmallstockBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteTmallstockBean.CommodityConstituteTmallstockBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * TMALL库存分配更新处理
 * 
 * @author System Integrator Corp.
 */
public class CommodityConstituteTmallstockRegisterAction extends CommodityConstituteTmallstockBaseAction {

  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.COMMODITY_CONSTITUTE_UPDATE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    Long scaleAll = 0L;
    CommodityConstituteTmallstockBean bean = getBean();
    for (CommodityConstituteTmallstockBeanDetail detail : bean.getList()) {
      if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())
          && detail.getTmallUseFlg().equals(UsingFlg.VISIBLE.getName())) {
        String scale = getRequestParameter().get("scale" + detail.getCommodityCode());
        if (NumUtil.isNum(scale)) {
          if (Long.parseLong(scale) >= 0 && Long.parseLong(scale) <= 100) {
            scaleAll = scaleAll + Long.parseLong(scale);
          } else {
            addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SCALE_ERROR));
            return false;
          }
        } else {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SCALE_ERROR));
          return false;
        }
      }
    }
    if (scaleAll > 100) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SCALE_ERROR));
      return false;
    }
    return true;

    // String scale = "";
    // Long scaleAll = 0L;
    // Long tempSaleQuantity = 0L;
    // Long combSaleQuantity = 0L;
    // Long orgAllocated = 0L;
    // CommodityConstituteTmallstockBean bean = getBean();
    // for (CommodityConstituteTmallstockBeanDetail detail : bean.getList()) {
    // if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())
    // && detail.getTmallUseFlg().equals(UsingFlg.VISIBLE.getName())) {
    // scale = getRequestParameter().get("scale" + detail.getCommodityCode());
    // if (NumUtil.isNum(scale)) {
    // if (Long.parseLong(scale) >= 0 && Long.parseLong(scale) <= 100) {
    // scaleAll = scaleAll + Long.parseLong(scale);
    // detail.setScale(scale);
    // tempSaleQuantity = NumUtil.toLong(bean.getOrgSaleQuantity()) *
    // NumUtil.toLong(scale) / 100
    // / NumUtil.toLong(detail.getCombinationAmount());
    // detail.setTmallStock(NumUtil.toString(NumUtil.toLong(detail.getTmallAllocated())
    // + tempSaleQuantity
    // * NumUtil.toLong(detail.getCombinationAmount())));
    // combSaleQuantity = combSaleQuantity + tempSaleQuantity *
    // NumUtil.toLong(detail.getCombinationAmount());
    // } else {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SCALE_ERROR));
    // return false;
    // }
    // } else {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SCALE_ERROR));
    // return false;
    // }
    // } else if (detail.getCommodityCode().equals(bean.getOrgCommodityCode()))
    // {
    // orgAllocated = NumUtil.toLong(detail.getTmallAllocated());
    // }
    // }
    //
    // if (scaleAll > 100) {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SCALE_ERROR));
    // return false;
    // }
    // bean.setOrgStockTotal(NumUtil.toString(orgAllocated +
    // NumUtil.toLong(bean.getOrgSaleQuantity()) - combSaleQuantity));
    // return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityConstituteTmallstockBean bean = getBean();

    // 2014/06/06 库存更新对应 ob_卢 delete start
//    String shopCode = getConfig().getSiteShopCode();
//    // 淘宝订单下载 START
//    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
//    int successFlg = orderService.OrderDownLoadCommon("", "", false);
//    // -1是下载失败，-2为下载中
//    if (successFlg == -1) {
//      // 订单下载失败
//      this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_DOWNLOADING_ERROR));
//      setRequestBean(bean);
//      return BackActionResult.RESULT_SUCCESS;
//    } else if (successFlg == -2) {
//      // 订单下载中
//      this.addErrorMessage(WebMessage.get(CatalogErrorMessage.ORDER_DOWNLOADING));
//      setRequestBean(bean);
//      return BackActionResult.RESULT_SUCCESS;
//    }
    // 淘宝订单下载 END
    // 2014/06/06 库存更新对应 ob_卢 delete end
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    // 重新取得数据
    TmallStockInfo tsi = service.getOrgTmallStockInfo(bean.getOrgCommodityCode());
    List<TmallStockInfo> list = service.getTmallStockInfo(bean.getOrgCommodityCode());
    
    bean.getList().clear();
    addDataToBean(bean, tsi, list);

    // 2014/06/06 库存更新对应 ob_卢 add start
    // 更新在库比例及分配库存

    List<TmallStockAllocation> stockAlloctionList = new ArrayList<TmallStockAllocation>();
    for (CommodityConstituteTmallstockBeanDetail detail : bean.getList()) {
      if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())){
        TmallStockAllocation stockAlloction = new TmallStockAllocation();
        stockAlloction.setShopCode(getConfig().getSiteShopCode());
        stockAlloction.setCommodityCode(detail.getCommodityCode());
        String scale = getRequestParameter().get("scale" + detail.getCommodityCode());
        stockAlloction.setScaleValue(NumUtil.toLong(scale));
        stockAlloctionList.add(stockAlloction);
      }
    }
    List<String> tmallApiFailCodeList = new ArrayList<String>();                                      
    List<String> jdApiFailCodeList = new ArrayList<String>();                                     
    List<String> stockFailCodeList = new ArrayList<String>();   
    ServiceResult result = service.updateTmallStockAll(bean.getOrgCommodityCode(), stockAlloctionList,
        tmallApiFailCodeList, jdApiFailCodeList, stockFailCodeList);

    if(result.hasError()){
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.RUN_TIME_ERROR) {
          return BackActionResult.SERVICE_ERROR;
        }else if (content == OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED) {
          addErrorMessage("当前京东订单同步失败，请等待一段时间再进行处理。");
          setRequestBean(getBean());
          setNextUrl(null);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      addErrorMessage("库存再分配处理失败，错误信息如下：");
      if(tmallApiFailCodeList.size() > 0){
        StringBuilder sb = new StringBuilder("以下商品淘宝连协失败:<br/>");
        createMessage(tmallApiFailCodeList, sb);
        addErrorMessage(sb.toString());
      }
      if(jdApiFailCodeList.size() > 0){
        StringBuilder sb = new StringBuilder("以下商品京东连协失败:<br/>");
        createMessage(jdApiFailCodeList, sb);
        addErrorMessage(sb.toString());
      }
      if(stockFailCodeList.size() > 0){
        StringBuilder sb = new StringBuilder("以下商品的有效库存小于0:<br/>");
        createMessage(stockFailCodeList, sb);
        addErrorMessage(sb.toString());
      }
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 同期化处理
    List<String> tmallCynchroFailCodeList = new ArrayList<String>();                                          
    List<String> jdCynchroFailCodeList = new ArrayList<String>();                                         
    List<String> commodityHasNotCodeList = new ArrayList<String>();                                         

    StockService stockService = ServiceLocator.getStockService(getLoginInfo());
    stockService.stockCynchroApi(tmallCynchroFailCodeList, jdCynchroFailCodeList, commodityHasNotCodeList);
    
    // 同期化失败 发送邮件
    if(tmallCynchroFailCodeList.size() > 0 || jdCynchroFailCodeList.size() > 0 || commodityHasNotCodeList.size() > 0){
      stockService.sendStockCynchroMail(tmallCynchroFailCodeList, jdCynchroFailCodeList, commodityHasNotCodeList);
    }
    
    // 2014/06/06 库存更新对应 ob_卢 add end
    
    // 2014/06/06 库存更新对应 ob_卢 delete start

//    Long allStock = 0L;
//    TmallStockAllocationDao dao = DIContainer.getDao(TmallStockAllocationDao.class);
//    for (CommodityConstituteTmallstockBeanDetail detail : bean.getList()) {
//      if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())
//          && detail.getTmallUseFlg().equals(UsingFlg.VISIBLE.getName())) {
//        String scale = getRequestParameter().get("scale" + detail.getCommodityCode());
//        detail.setScale(scale);
//        
//        Long tempSaleQuantity = 0L;
//        // 总有效库存 * 百分比
//        tempSaleQuantity = NumUtil.toLong(bean.getOrgSaleQuantity()) * NumUtil.toLong(detail.getScale()) / 100L;
//        // 组有效库存数 / 组数
//        tempSaleQuantity = tempSaleQuantity / NumUtil.toLong(detail.getCombinationAmount());
//        // 分配后的库存数 = 引单数 + 可分配库存数
//        Long tmallStock = NumUtil.toLong(detail.getTmallAllocated()) + tempSaleQuantity
//            * NumUtil.toLong(detail.getCombinationAmount());
//        detail.setTmallStock(NumUtil.toString(tmallStock));
//        // 分配组合商品的总库存
//        allStock = allStock + tmallStock;
//
//        TmallStockAllocation stockA = dao.load(shopCode, detail.getCommodityCode());
//        stockA.setStockQuantity(NumUtil.toLong(detail.getTmallStock()));
//        stockA.setScaleValue(Long.parseLong(detail.getScale()));
//        ServiceResult result = service.updateTmallStockAllocation(stockA);
//        if (result.hasError()) {
//          for (ServiceErrorContent content : result.getServiceErrorList()) {
//            if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
//              addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, stockA.getSkuCode()));
//              setRequestBean(bean);
//              return BackActionResult.RESULT_SUCCESS;
//            } else if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
//              return BackActionResult.SERVICE_VALIDATION_ERROR;
//            }
//          }
//          return BackActionResult.SERVICE_ERROR;
//        }
//      }
//    }

//    // 库存上传淘宝
//    StockDao stockDao = DIContainer.getDao(StockDao.class);
//
//    Stock stock = stockDao.load(getConfig().getSiteShopCode(), bean.getOrgCommodityCode());
//    
//    boolean upTmallFlg = true;
//    for (CommodityConstituteTmallstockBeanDetail detail : bean.getList()) {
//      if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())
//          && detail.getTmallUseFlg().equals(UsingFlg.VISIBLE.getName())) {
//
//        // 分配前库存数 - 分配后库存数
//        Long addCAmount = NumUtil.toLong(detail.getTmallStock()) - NumUtil.toLong(detail.getBeforeTmallStock());
//        // 分差库存数 / 组数
//        addCAmount = addCAmount / NumUtil.toLong(detail.getCombinationAmount());
//        if (addCAmount != 0L) {
//          stock.setCommodityCode(detail.getCommodityCode());
//          stock.setSkuCode(detail.getCommodityCode());
//          stock.setStockTmall(addCAmount);
//          // 上传库存
//          upTmallFlg = service.tmallSkuCodeUp(stock);
//          if (!upTmallFlg) {
//            // 错误信息上传未成功。
//            this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SKUUP_ERROR, detail.getCommodityCode()));
//            setRequestBean(bean);
//            return BackActionResult.RESULT_SUCCESS;
//          }
//        }
//        // 原商品
//      } else if (detail.getCommodityCode().equals(bean.getOrgCommodityCode())) {
//        // 总库存 - 组合库存总数 - 分配前库存数
//        Long addCAmount = NumUtil.toLong(bean.getOrgStockTotal()) - allStock - NumUtil.toLong(detail.getBeforeTmallStock());
//        if (addCAmount != 0L) {
//          stock.setCommodityCode(detail.getCommodityCode());
//          stock.setSkuCode(detail.getCommodityCode());
//          stock.setStockTmall(addCAmount);
//          // 上传库存
//          upTmallFlg = service.tmallSkuCodeUp(stock);
//          if (!upTmallFlg) {
//            // 错误信息上传未成功。
//            this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SKUUP_ERROR, detail.getCommodityCode()));
//            setRequestBean(bean);
//            return BackActionResult.RESULT_SUCCESS;
//          }
//        }
//      }
//    }
    // 2014/06/06 库存更新对应 ob_卢 delete end

    
    setNextUrl("/app/catalog/commodity_constitute_tmallstock/init/" + bean.getOrgCommodityCode());
    return BackActionResult.RESULT_SUCCESS;

    // boolean upFlg = false;
    // for (CommodityConstituteTmallstockBeanDetail detail : bean.getList()) {
    // if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())
    // && detail.getTmallUseFlg().equals(UsingFlg.VISIBLE.getName())) {
    //
    // Stock stock = stockDao.load(getConfig().getSiteShopCode(),
    // detail.getCommodityCode()); // Stock信息取得
    // stock.setStockTmall(NumUtil.toLong(detail.getTmallStock()));
    //
    // upFlg = service.tmallSkuCodeUp(stock);
    // if (upFlg = false) {
    // // 错误信息上传未成功。
    // this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SKUUP_ERROR,
    // detail.getCommodityCode()));
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // }
    //
    // TmallStockAllocation tsa = new TmallStockAllocation();
    // // DIContainer.getWebshopConfig().getSiteShopCode()
    // tsa.setShopCode(getConfig().getSiteShopCode());
    // tsa.setSkuCode(detail.getCommodityCode());
    // tsa.setOriginalCommodityCode(bean.getOrgCommodityCode());
    // tsa.setCommodityCode(detail.getCommodityCode());
    // tsa.setStockQuantity(NumUtil.toLong(detail.getTmallStock()));
    // tsa.setAllocatedQuantity(Long.parseLong(detail.getTmallAllocated()));
    // tsa.setScaleValue(Long.parseLong(detail.getScale()));
    //
    // ServiceResult result = service.updateTmallStockAllocation(tsa);
    // if (result.hasError()) {
    // for (ServiceErrorContent content : result.getServiceErrorList()) {
    // if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
    // addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
    // tsa.getSkuCode()));
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // } else if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
    // return BackActionResult.SERVICE_VALIDATION_ERROR;
    // }
    // }
    // return BackActionResult.SERVICE_ERROR;
    // }
    // } else if (detail.getCommodityCode().equals(bean.getOrgCommodityCode()))
    // {
    // Stock stock = stockDao.load(getConfig().getSiteShopCode(),
    // detail.getCommodityCode()); // Stock信息取得
    // stock.setStockTmall(NumUtil.toLong(bean.getOrgStockTotal()));
    //
    // upFlg = service.tmallSkuCodeUp(stock);
    // if (upFlg = false) {
    // // 错误信息上传未成功。
    // this.addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_SKUUP_ERROR,
    // detail.getCommodityCode()));
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // }
    // }
    // }
    //
    // setNextUrl("/app/catalog/commodity_constitute_tmallstock/init/" +
    // bean.getOrgCommodityCode());
    // return BackActionResult.RESULT_SUCCESS;
  }

  // 2014/06/19 库存更新对应 ob_卢 add start
  private void createMessage(List<String> apiFailCodeList, StringBuilder sb) {
    Set<String> apiFailCodeSet = new HashSet<String>();
    for (String commodityCode : apiFailCodeList) {
      apiFailCodeSet.add(commodityCode);
    }

    int listSize = 0;
    for (String commodityCode : apiFailCodeSet) {
      listSize++;
      sb.append(commodityCode);
      if (listSize < apiFailCodeSet.size()) {
        sb.append(",");
      }
      if (listSize % 8 == 0) {
        sb.append("<br/>");
      }
    }
  }
  // 2014/06/19 库存更新对应 ob_卢 add end
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.catalog.CommodityConstituteTmallstockRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104015006";
  }

}
