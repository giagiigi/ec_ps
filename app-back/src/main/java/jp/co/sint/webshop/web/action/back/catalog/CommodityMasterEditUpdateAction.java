package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
/**
 * tm/jd多SKU商品登录关联
 */
public class CommodityMasterEditUpdateAction extends WebBackAction<CommodityMasterEditBean> {
  /**
   * 初期処理を実行します。
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  
  @Override
  public boolean validate() {
    CommodityMasterEditBean bean = getBean();
    boolean valid = true;
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
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityMaster cm = new CommodityMaster();
    cm.setShopCode("00000000");
    cm.setCommodityCode(bean.getCommodityCode());
    cm.setCommodityName(bean.getCommodityName());
    
    if(bean.getJdCommodityCode().isEmpty()){
      cm.setJdCommodityCode(" ");
    }else{
    cm.setJdCommodityCode(bean.getJdCommodityCode());
    }
    if(bean.getTmallCommodityCode().isEmpty()){
      cm.setTmallCommodityCode(" ");
    }else{
      cm.setTmallCommodityCode(bean.getTmallCommodityCode());  
    }
    
    ServiceResult result = service.updateCommodityMaster(cm); 
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.DISCOUNT_NOT_EXIST_ERROR, bean.getCommodityCode()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }
    addInformationMessage(MessageFormat.format(Messages
        .getString("web.action.back.catalog.CommodityMasterEditUpdateAction.1"),true));
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * Action名の取得
   * @return Action名
   */
  public String getActionName() {
    
    return Messages.getString("web.action.back.catalog.CommodityMasterEditUpdateAction.0");
  }
  
  /**
   * オペレーションコードの取得
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102081006";
  }


}
