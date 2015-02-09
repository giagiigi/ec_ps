package jp.co.sint.webshop.web.action.back.catalog;
import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterEditBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class CommodityMasterEditRegisterAction extends WebBackAction<CommodityMasterEditBean> {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  @Override
  public boolean validate() {
    CommodityMasterEditBean bean = getBean();
    boolean valid = true;
    if(bean.getCommodityCode().isEmpty()){
      addErrorMessage(MessageFormat.format(Messages
          .getString("web.action.back.catalog.CommodityMasterEditRegisterAction.3"),true));
      valid=false;
    }
    if(bean.getCommodityName().isEmpty())
    {
      addErrorMessage(MessageFormat.format(Messages
          .getString("web.action.back.catalog.CommodityMasterEditRegisterAction.4"),true));
      valid=false;
    }
    return valid;
  }
  @Override
  public WebActionResult callService() {
    CommodityMasterEditBean bean = getBean();
   /* String cmecode =*/
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Long iscode = service.getCommodityCode1(bean.getCommodityCode());
    CommodityMaster cm = new CommodityMaster();
    cm.setShopCode("00000000");
    cm.setCommodityCode(bean.getCommodityCode());
    cm.setCommodityName(bean.getCommodityName());
    cm.setJdCommodityCode(bean.getJdCommodityCode());
    cm.setTmallCommodityCode(bean.getTmallCommodityCode());
    if(iscode==0){
      //主商品号 可以添加
      service.registerCommodityMaster(cm);
      bean.setDisplayLoginButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(true);
      setNextUrl("/app/catalog/commodity_master_edit/select/" + bean.getCommodityCode() + "/register");
    }else{
      addErrorMessage(MessageFormat.format(Messages
          .getString("web.action.back.catalog.CommodityMasterEditRegisterAction.2"),true));
    }
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
   }
  public String getActionName() { 
   return "web.action.back.catalog.CommodityMasterEditRegisterAction.0";
  }
  
  public String getOperationCode() {
    return "1102081004";
  }
}
