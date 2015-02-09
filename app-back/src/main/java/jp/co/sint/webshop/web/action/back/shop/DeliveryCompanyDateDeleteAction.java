package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.TmallDeliveryRelatedInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyDateBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;

/**
 * 执行时间段的删除功能
 * @author 
 */
public class DeliveryCompanyDateDeleteAction extends DeliveryCompanyDateBaseAction {

  /**
   * 
   * @return 
   */
  @Override
  public boolean authorize() {
	   boolean authorization = false;
	   String[] params=getRequestParameter().getPathArgs();
	   //URL参数至少1个
	   if(params.length==0)
		 {
		    return false;
		 }
	   if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
	     authorization = Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo());
	   } else {
	     authorization = Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo());
	   }
	   return authorization;
  }

  /**
   * @return 
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * 查询所以配送时间间隔和配送希望日
   * @return WebActionResult
   */
  public WebActionResult callService() {
		DeliveryCompanyDateBean bean=getBean();
		String[] params=getRequestParameter().getPathArgs();
		ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    if (params.length > 0 && params[params.length-1].equals("tmall")){
      
      TmallDeliveryRelatedInfo info = service.getDeliveryRelatedInfoTmall(params[0]);
      if (info ==null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送希望日时关联情报"));
      }else {
        if (!bean.getPrefectureCode().equals(info.getPrefectureCode()) || !bean.getDeliveryCompanyNo().equals(info.getDeliveryCompanyNo())) {
          throw new URLNotFoundException(); 
        } else {
          //执行删除功能
          ServiceResult serviceResult=service.deleteDeliveryRelatedInfoTmall(info);
          if (serviceResult.hasError()) {
            Logger logger = Logger.getLogger(this.getClass());
              logger.warn(serviceResult.toString());
              return BackActionResult.SERVICE_ERROR;
          } else {
            addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "配送希望日时关联情报"));
          }
        }
      }
      List<TmallDeliveryRelatedInfo> result=service.getDeliveryRelatedInfoListTmall(getLoginInfo().getShopCode(), 
          bean.getDeliveryCompanyNo(), bean.getPrefectureCode());
      //设定配送希望日时一览画面
      setDeliveryRegionDateTmall(result,bean);
      
    } else {
      
      DeliveryRelatedInfo info = service.getDeliveryRelatedInfo(params[0]);
      if (info ==null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送希望日时关联情报"));
      }else {
        if (!bean.getPrefectureCode().equals(info.getPrefectureCode()) || !bean.getDeliveryCompanyNo().equals(info.getDeliveryCompanyNo())) {
          throw new URLNotFoundException(); 
        } else {
          //执行删除功能
          ServiceResult serviceResult=service.deleteDeliveryRelatedInfo(info);
          if (serviceResult.hasError()) {
            Logger logger = Logger.getLogger(this.getClass());
              logger.warn(serviceResult.toString());
              return BackActionResult.SERVICE_ERROR;
          } else {
            addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "配送希望日时关联情报"));
          }
        }
      }
      List<DeliveryRelatedInfo> result=service.getDeliveryRelatedInfoList(getLoginInfo().getShopCode(), 
          bean.getDeliveryCompanyNo(), bean.getPrefectureCode());
      //设定配送希望日时一览画面
      setDeliveryRegionDate(result,bean);
      
    }

		setRequestBean(bean);
	  return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "配送希望日时关联情报删除处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105161003";
  }

}
