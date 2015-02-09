package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.JdSuitCommodity;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.StockService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.SetCommodityCompositionBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * 套餐设定のアクションクラスです
 * 
 * @author KS.
 */
public class SetCommodityCompositionAllocationAction extends SetCommodityCompositionInitAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    SetCommodityCompositionBean bean = getBean();
    if (StringUtil.isNullOrEmpty(bean.getInputJdScaleValue())) {
      addErrorMessage("比例值必须输入");
      return false;
    }
    if (!validateBean(bean)) {
      return false;
    }
    // 明细商品不能超过15件
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader header = service.getCommodityHeader("00000000", bean.getSearchCommodityCode());
    if (header != null && StringUtil.hasValue(header.getOriginalCommodityCode())) {
      addErrorMessage("组合品不能设定为套装明细商品");
      setRequestBean(bean);
      return false;
    }
    List<SetCommodityComposition> values = service.getSetCommodityInfo(bean.getShopCode(), bean.getCommodityCode());
    if (values == null ) {
      addErrorMessage("未关联明细商品");
      setRequestBean(bean);
      return false;
    }
    if (values.size() == 0 ) {
      addErrorMessage("未关联明细商品");
      setRequestBean(bean);
      return false;
    }
    
    if (values != null && values.size() >= Integer.valueOf(DIContainer.getWebshopConfig().getIndexCompositionLimit())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.NUMBER_OUT_ERROR));
      setRequestBean(bean);
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

    StockService service = ServiceLocator.getStockService(getLoginInfo());
    SetCommodityCompositionBean reqBean = getBean();
    
    // 计算套装分配库存数
    Long jdScaleValue = NumUtil.toLong(reqBean.getInputJdScaleValue());
    
    JdSuitCommodity suitCommodity = new JdSuitCommodity();
    suitCommodity.setCommodityCode(reqBean.getCommodityCode());
    suitCommodity.setScaleValue(jdScaleValue);

    //库存再分配处理
    ServiceResultImpl result = new ServiceResultImpl();
    
    //查询JD套装表比率值
    JdSuitCommodity jdSuitCommodity = service.getJdSuitCommodity(reqBean.getCommodityCode());
    if (jdSuitCommodity == null || (jdSuitCommodity != null && jdSuitCommodity.getScaleValue() > 0L) || jdScaleValue > 0L) {

      //TMAPI连协失败商品编号列表
      List<String> tmallApiFailCodeList = new ArrayList<String>();
      //JDAPI连协失败商品编号列表
      List<String> jdApiFailCodeList = new ArrayList<String>();
      //有效库存错误商品编号列表
      List<String> stockFailCodeList = new ArrayList<String>();
      result = (ServiceResultImpl)service.jdStockRedistribution(jdSuitCommodity, suitCommodity, tmallApiFailCodeList, jdApiFailCodeList, stockFailCodeList);
      
      if(result.hasError()){
        for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
          if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            return BackActionResult.SERVICE_ERROR;
          } else if (errorContent == OrderServiceErrorContent.ORDER_DOWN_INTERFACE_FAILED) {
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
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      } else {
        //TMAPI连协失败商品编号列表
        List<String> tmallCynchroFailCodeList = new ArrayList<String>();
        //JDAPI连协失败商品编号列表
        List<String> jdCynchroFailCodeList = new ArrayList<String>();
        //不存在商品编号列表
        List<String> commodityHasNotCodeList = new ArrayList<String>();
        service.stockCynchroApi(tmallCynchroFailCodeList, jdCynchroFailCodeList, commodityHasNotCodeList);
        
        //发送邮件
        if (tmallCynchroFailCodeList.size() > 0 || jdCynchroFailCodeList.size() > 0 || commodityHasNotCodeList.size() > 0) {
          service.sendStockCynchroMail(tmallCynchroFailCodeList, jdCynchroFailCodeList, commodityHasNotCodeList);
          setRequestBean(reqBean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
    }
    
    addInformationMessage("套装商品比例设定成功");
    setRequestBean(reqBean);
    super.callService();
    
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
    return Messages.getString("套餐明细登录处理");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104019002";
  }

}
