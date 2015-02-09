package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.domain.DefaultFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051510:配送公司详细追加处理
 * 
 * @author cxw
 */
public class DeliveryCompanyEditRegisterAction extends WebBackAction<DeliveryCompanyEditBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {

		boolean authorization = false;
		if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
			authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
		} else {
			authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
		}
		return authorization;
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		return validateBean(getBean());
		
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {
		
		// 配送公司追加处理
		ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
		DeliveryCompanyEditBean editBean = getBean();
		DeliveryCompany company = new DeliveryCompany();

		company.setShopCode(getLoginInfo().getShopCode());
		company.setDeliveryCompanyNo(editBean.getDeliveryCompanyNo());
		company.setDeliveryCompanyName(editBean.getDeliveryCompanyName());
		company.setDeliveryDatetimeCommission(NumUtil.parse(editBean.getDeliveryDatetimeCommission()));
		company.setDeliveryCompanyUrl(editBean.getDeliveryCompanyUrl());
		company.setUseFlg(Long.parseLong(editBean.getDisplayFlg()));

		// 验证配送公司编号是否存在
		if(service.getDeliveryCompanyByNo(editBean.getDeliveryCompanyNo()) != null) {
			addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "配送公司编号"));
			this.setRequestBean(editBean);
			
			return BackActionResult.RESULT_SUCCESS;
		}
		
		// 设定默认公司
		List<DeliveryCompany> deliveryCompanyList = service.getDeliveryCompanyList(getLoginInfo().getShopCode());
		if (deliveryCompanyList !=null && deliveryCompanyList.size()>0) {
			company.setDefaultFlg(DefaultFlg.NOT_DEFAULT.longValue());
		} else {
			company.setDefaultFlg(DefaultFlg.DEFAULT.longValue());
		}

		// 追加成功处理
		ServiceResult result = service.insertDeliveryCompany(company);
		if (result.hasError()) {
			for (ServiceErrorContent content : result.getServiceErrorList()) {
				if(content.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
					addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "配送公司编号"));
					return BackActionResult.RESULT_SUCCESS;
				} else if (content.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
					return BackActionResult.SERVICE_VALIDATION_ERROR;
				} else {
					return BackActionResult.SERVICE_ERROR;
				}
			}
		}
		this.setRequestBean(editBean);
		
		// 追加后跳转处理 
		setNextUrl("/app/shop/delivery_company_edit/init/" + company.getDeliveryCompanyNo()+"/"+WebConstantCode.COMPLETE_INSERT);
		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "配送公司详细追加处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "4105151004";
	}

}
