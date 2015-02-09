package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.JdDeliveryRegion;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1051410:配送地域跳转处理
 * 
 * @author cxw
 */
public class DeliveryJdCompanyEditMoveAction extends WebBackAction<DeliveryCompanyListBean> {

	/**
	 * 验证权限
	 * 
	 * @return true
	 */
	@Override
	public boolean authorize() {
		boolean authorization = false;
		if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
			authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
		} else {
			authorization = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo());
		}
		return authorization;
	}

	/**
	 * 入力值验证
	 * 
	 * @return 入力值为true
	 */
	@Override
	public boolean validate() {
		return true;
	}

	/**
	 * 数据处理
	 * 
	 * @return 处理结果
	 */
	@Override
	public WebActionResult callService() {

		// 设定配送地域错误处理
		String[] params = getRequestParameter().getPathArgs();
		if (params.length < 2) {
			setNextUrl(null);
			setRequestBean(getBean());
			addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司及地域"));
			return BackActionResult.RESULT_SUCCESS;
		}

		// 取得指定配送地域相关信息
		ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    JdDeliveryRegion deliveryRegion = service.getDeliveryRegionJd(getLoginInfo().getShopCode(), params[0], params[1]);
    if (deliveryRegion == null) {
      setNextUrl(null);
      setRequestBean(getBean());
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司及地域"));
      return BackActionResult.RESULT_SUCCESS;
    }

		// 返回一览
		DisplayTransition.add(getBean(), "/app/shop/delivery_jd_company_edit/init/" + params[0], getSessionContainer());
		
    // 配送希望日跳转处理
	  setNextUrl("/app/shop/delivery_jd_company_date/init/" + params[0] + "/" + params[1]);


		
		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "配送地域跳转处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "4105141003";
	}

}
