package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.catalog.JdStockInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteJdstockBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteJdstockBean.CommodityConstituteJdstockBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;

/**
 * 京东库存分配更新处理
 * 
 * @author System Integrator Corp.
 */
public class CommodityConstituteJdstockRegisterAction extends CommodityConstituteJdstockBaseAction {

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
    CommodityConstituteJdstockBean bean = getBean();
    for (CommodityConstituteJdstockBeanDetail detail : bean.getList()) {
      if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())
          && detail.getJdUseFlg().equals(UsingFlg.VISIBLE.getName())) {
        String scale = getRequestParameter().get("scale" + detail.getCommodityCode());
        if (NumUtil.isNum(scale)) {
          if (Long.parseLong(scale) >= 0 && Long.parseLong(scale) <= 100) {
            scaleAll = scaleAll + Long.parseLong(scale);
          } else {
            addErrorMessage(WebMessage.get(CatalogErrorMessage.JD_SCALE_ERROR));
            return false;
          }
        } else {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.JD_SCALE_ERROR));
          return false;
        }
      }
    }
    if (scaleAll > 100) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.JD_SCALE_ERROR));
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityConstituteJdstockBean bean = getBean();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    // 重新取得数据
    JdStockInfo tsi = service.getOrgJdStockInfo(bean.getOrgCommodityCode());
    List<JdStockInfo> list = service.getJdStockInfo(bean.getOrgCommodityCode());
    
    bean.getList().clear();
    addDataToBean(bean, tsi, list);

    // 更新在库比例及分配库存

    List<JdStockAllocation> stockAlloctionList = new ArrayList<JdStockAllocation>();
    for (CommodityConstituteJdstockBeanDetail detail : bean.getList()) {
      if (!detail.getCommodityCode().equals(bean.getOrgCommodityCode())){
        JdStockAllocation stockAlloction = new JdStockAllocation();
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
    ServiceResult result = service.updateJdStockAll(bean.getOrgCommodityCode(), stockAlloctionList,
        tmallApiFailCodeList, jdApiFailCodeList, stockFailCodeList);

    if(result.hasError()){
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.RUN_TIME_ERROR) {
          return BackActionResult.SERVICE_ERROR;
        } else if (content == OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED) {
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
    
    setNextUrl("/app/catalog/commodity_constitute_jdstock/init/" + bean.getOrgCommodityCode());
    return BackActionResult.RESULT_SUCCESS;

  }
  
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
  
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "京东库存分配更新处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104015008";
  }

}
