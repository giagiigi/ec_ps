package jp.co.sint.webshop.web.action.back.catalog;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterListBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 *TM/JD多SKU商品关联 删除
 */
public class CommodityMasterListDeleteAction extends WebBackAction<CommodityMasterListBean> {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  
  @Override
  public boolean validate() {
    boolean valid ;
    String[] urlParam = getRequestParameter().getPathArgs();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String commodityCode =urlParam[0];
    Long commodityskuflg= service.getCommoditySku(commodityCode);
    if(commodityskuflg==0){
      valid=true;
    }else{
      addErrorMessage("编号"+commodityCode+"无法删除，请先删除多SKU关联中子商品信息");
      valid=false;
    }
    return valid;
  }
  
  @Override
  public WebActionResult callService() {
    String[] urlParam = getRequestParameter().getPathArgs();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String commodityCode =urlParam[0];
      service.deleteCommodityMaster(commodityCode);
      
      setNextUrl("/app/catalog/commodity_master_list/init/"+"deletemaster");
    return BackActionResult.RESULT_SUCCESS;
  }

  public String getActionName() {
  
  return Messages.getString("web.action.back.catalog.CommodityMasterListDeleteAction.0");
  }

  public String getOperationCode() {
    return "1102081007";
  }

}
