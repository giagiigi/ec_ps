package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.Messages;

public class CustomerEditVerificationAction extends CustomerEditBaseAction {

	@Override
	public WebActionResult callService() {
		getBean().setMobileNumber(getBean().getMobileNumberSucces());
		setRequestBean(getBean());
		// 查询出将要修改的顾客信息
		CustomerService service = ServiceLocator
				.getCustomerService(getLoginInfo());
		CustomerInfo ci = service.getCustomer(getBean().getCustomerCode());
		Customer cust = ci.getCustomer();
		// 将新属性封装进对象
		cust.setMobileNumber(getBean().getMobileNumber());
		cust.setAuthCode(getBean().getVerification());
		// 更新顾客信息
		ServiceResult customerResult = service.updateCustomer1(cust);

		if (customerResult.hasError()) {
			for (ServiceErrorContent result : customerResult
					.getServiceErrorList()) {
				if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
					return BackActionResult.SERVICE_VALIDATION_ERROR;
				} else if (result
						.equals(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR)) {
					addErrorMessage(WebMessage
							.get(
									ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
									Messages
											.getString("web.action.back.customer.CustomerEditRegisterAction.0")));
				} else if (result
						.equals(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR)) {
					addErrorMessage(WebMessage
							.get(
									ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
									Messages
											.getString("web.action.back.customer.CustomerEditRegisterAction.1")));
				} else {
					return BackActionResult.SERVICE_ERROR;
				}
			}
			setNextUrl(null);
		}
		return BackActionResult.RESULT_SUCCESS;
	}

	@Override
	public boolean authorize() {
		// 更新権限チェック
		BackLoginInfo login = getLoginInfo();
		return Permission.CUSTOMER_UPDATE.isGranted(login);
	}

	@Override
	public boolean validate() {

		CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
		boolean result = true;
		result = validateBean(getBean());
		if (result) {
			// 获得手机号码
			String mobilePhone = getBean().getMobileNumberSucces();
			// 获得验证码
			String authCode = getBean().getVerification();
			// 判断手机对应验证码是否存在有效（有效时间内）
			Long numFlg = service.getCount(mobilePhone, authCode);
			if (numFlg == 0L) {
				addErrorMessage("验证码无效或已超出有效期间");
				result = false;
			}
		}
		//重置已绑定手机号码
		if (!result) {
			String code = getBean().getCustomerCode();
			String mobileNumberSucces = service.getMobileNumber(code);
			getBean().setMobileNumberSucces(mobileNumberSucces);
			setRequestBean(getBean());
		}
		// 验证码
		return result;
	}
}
