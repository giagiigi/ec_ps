package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.TmallDeliveryRegion;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051510:配送地域删除处理
 * 
 * @author cxw
 */
public class DeliveryCompanyEditDeleteAction extends WebBackAction<DeliveryCompanyEditBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	public boolean authorize() {

		boolean authorization = false;
		if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
			authorization = Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo());
		} else {
			authorization = Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo());
		}
		return authorization;
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	public boolean validate() {
		return true;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {

		DeliveryCompanyEditBean editBean = getBean();

		// 配送地域不存在处理
		String[] params = getRequestParameter().getPathArgs();
		if (params.length < 2) {
			addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "被操作的配送地域"));
			setRequestBean(editBean);
			return BackActionResult.RESULT_SUCCESS;
		}

		if (params[params.length-1].equals("tmall")){
		  
		  // 根据公司编号及从页面参数中取得的地域编号从数据库中取出指定的配送地域信息
      if ("area".equals(params[0])) {
        ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
        TmallDeliveryRegion deliveryRegion = service.getDeliveryRegionTmall(editBean.getShopCode(), editBean.getDeliveryCompanyNo(), params[1]);
        if (null == deliveryRegion) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "被操作的配送地域"));
          setRequestBean(editBean);
          return BackActionResult.RESULT_SUCCESS;
        }
        
        // 删除配送地域处理
        ServiceResult result = service.deleteDeliveryRegionTmall(deliveryRegion);
        if (result.hasError()) {
          for (ServiceErrorContent error : result.getServiceErrorList()) {
            // 重复性错误报告
            if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
              addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, "配送地域"));
              return BackActionResult.RESULT_SUCCESS;
            }
          }
        }
      }

		
    } else {
      // 根据公司编号及从页面参数中取得的地域编号从数据库中取出指定的配送地域信息
      if ("area".equals(params[0])) {
        ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
        DeliveryRegion deliveryRegion = service.getDeliveryRegion(editBean.getShopCode(), editBean.getDeliveryCompanyNo(), params[1]);
        if (null == deliveryRegion) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "被操作的配送地域"));
          setRequestBean(editBean);
          return BackActionResult.RESULT_SUCCESS;
        }
        
        // 删除配送地域处理
        ServiceResult result = service.deleteDeliveryRegion(deliveryRegion);
        if (result.hasError()) {
          for (ServiceErrorContent error : result.getServiceErrorList()) {
            // 重复性错误报告
            if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
              addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, "配送地域"));
              return BackActionResult.RESULT_SUCCESS;
            }
          }
        }
      }
    }
		
		
		// 删除成功跳转处理
		setNextUrl("/app/shop/delivery_company_edit/init/" + getBean().getDeliveryCompanyNo() + "/" + WebConstantCode.COMPLETE_DELETE);
		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "配送地域删除处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "4105151008";
	}

}
