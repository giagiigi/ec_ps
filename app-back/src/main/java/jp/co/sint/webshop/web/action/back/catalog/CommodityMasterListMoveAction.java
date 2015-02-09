package jp.co.sint.webshop.web.action.back.catalog;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterListBean;
import jp.co.sint.webshop.web.webutility.DisplayTransition;


public class CommodityMasterListMoveAction extends WebBackAction<CommodityMasterListBean> {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public WebActionResult callService() {
    String[] urlParam = getRequestParameter().getPathArgs();
    
    if (urlParam[0].equals("build")) {
      setNextUrl("/app/catalog/commodity_master_edit");
      }
      if(urlParam[0].equals("relation")){
        String commoditycode = urlParam[1];
        setNextUrl("/app/catalog/commodity_master_edit/select/" + commoditycode + "/move");
      }
    DisplayTransition.add(getBean(), "/app/catalog/commodity_master_list/init", getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
    }  
  
  public String getActionName() {
    return "web.action.back.catalog.CommodityMasterListMoveAction.0";
    
  }

  public String getOperationCode() {
    return "1102081009";
  }
  
}
