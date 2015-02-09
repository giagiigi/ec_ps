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
public class CommodityMasterEditCommodityDeleteAction extends WebBackAction<CommodityMasterListBean> {

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
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String skucode =urlParam[0];
    String commodityCode = urlParam[1];
    service.deleteCommodityEdit(skucode);
    /**
     * 返回删除成功提示消息
     */   setNextUrl("/app/catalog/commodity_master_edit/select/" + commodityCode + "/update");
     return BackActionResult.RESULT_SUCCESS;
  }

  public String getActionName() {
    
    return Messages.getString("web.action.back.catalog.CommodityMasterEditCommodityDeleteAction.0");
  }

  public String getOperationCode() {
    return "1102081001";
  }

}
