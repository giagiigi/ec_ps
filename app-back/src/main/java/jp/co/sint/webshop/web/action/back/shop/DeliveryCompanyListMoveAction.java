package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1051410:配送公司跳转处理
 * 
 * @author cxw
 */
public class DeliveryCompanyListMoveAction extends WebBackAction<DeliveryCompanyListBean> {

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
		String shopCode = "";
		String deliveryCompanyNo = "";
		if (getRequestParameter().getPathArgs().length > 1) {
			shopCode = getLoginInfo().getShopCode();
			deliveryCompanyNo = getRequestParameter().getPathArgs()[1];

			ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
			DeliveryCompany company = service.getDeliveryCompany(shopCode, deliveryCompanyNo);

			if (company == null) {
				setNextUrl(null);
				setRequestBean(getBean());
				addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司"));
				return BackActionResult.RESULT_SUCCESS;
			}
		}

		String addition = "";
		if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(deliveryCompanyNo)) {
			addition =  deliveryCompanyNo;
		}
		
		// 选择跳转处理
	  setNextUrl("/app/shop/" + getRequestParameter().getPathArgs()[0] + "/init/" + addition);

		
		// 返回一览
		DisplayTransition.add(getBean(), "/app/shop/delivery_company_list/init", getSessionContainer());
		
		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "配送公司一览选择处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "4105141002";
	}

}
