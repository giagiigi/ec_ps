package jp.co.sint.webshop.web.action.back.catalog;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommoditySku;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;

public class CommodityMasterEditCommodityRegisterAction extends WebBackAction<CommodityMasterEditBean> {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  //app/order/shipping/payment_confirm
  //bean不能为空 验证
  @Override
  public boolean validate() {
    CommodityMasterEditBean bean = getBean();

    boolean validation = validateItems(bean, "relatedComdtyCode");

    if (validation) {
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
            
      CommodityHeader ch = catalogService.getCommodityHeader(getConfig().getSiteShopCode(), bean.getRelatedComdtyCode());
            
      if (ch == null) {
        // 商品不存在
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品" + bean.getRelatedComdtyCode()));
        validation = false;
      }

      if (validation) {

        if (!ch.getCommodityType().equals(CommodityType.GENERALGOODS.longValue())
            || (ch.getSetCommodityFlg() != null && ch.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue()))){
          addErrorMessage("请选择普通商品登录。");
          validation = false;
        }
        Long iscode = catalogService.getCommoditySkuCode(bean.getCommodityCode(),bean.getRelatedComdtyCode());
        if(iscode!=0){
          addErrorMessage("该商品在主商品【" + bean.getCommodityCode() + "】已经登录,不能重复登录。");
          validation = false;
          return validation;
        }
        List<String>  iscodes =new ArrayList<String>();
        iscodes=catalogService.getCommoditySkuCodes(bean.getRelatedComdtyCode());
        if(!iscodes.isEmpty()){
         for (String codes : iscodes) {
           addErrorMessage("该商品在主商品【" + codes + "】已经登录,不能重复登录。");
           validation = false;
        } 
          
        }
      }
    }
    
        return validation;
      
  }
  @Override
  public WebActionResult callService() {
    CommodityMasterEditBean bean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
     String skucode = bean.getRelatedComdtyCode();
     String commoditycode =bean.getCommodityCode();
   
     Long iscode = service.getCommoditySkuCode(commoditycode,skucode);
     bean.getJdUseFlg();
    if(iscode!=0){
       //返回不能添加
     }
      CommoditySku cs=new CommoditySku();
      cs.setShopCode("00000000");
      cs.setCommodityCode(bean.getCommodityCode());
      cs.setSkuCode(bean.getRelatedComdtyCode());
      Long jduseflag=Long.parseLong(bean.getJdUseFlg());
      cs.setJdUseFlag(jduseflag);
      Long tmalluseflag = Long.parseLong(bean.getTmallUseFlg());
      cs.setTmallUseFlag(tmalluseflag);
      service.registerCommoditySku(cs);
     setRequestBean(bean);
     setNextUrl("/app/catalog/commodity_master_edit/select/" + bean.getCommodityCode() + "/commodityregister");
     return BackActionResult.RESULT_SUCCESS;
   }  

  public String getActionName() {
   return "web.action.back.catalog.CommodityMasterEditCommodityRegisterAction.0";
   
  }
  
  public String getOperationCode() {
    return "1102081002";
  }
  
}
