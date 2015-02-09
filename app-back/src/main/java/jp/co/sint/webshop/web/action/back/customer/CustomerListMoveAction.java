package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerListBean;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1030110:顧客マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerListMoveAction extends CustomerListBaseAction {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		// 遷移のみなので常にture
		return true;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {

		String customerCode = "";
		String displayMode = "";

		// parameter[0] : 次画面パラメータ , parameter[1] : 顧客コード
		String[] parameter = getRequestParameter().getPathArgs();
		if (parameter.length > 0) {
			displayMode = StringUtil.coalesce(parameter[0], "");
			if (parameter.length > 1) {
				customerCode = StringUtil.coalesce(parameter[1], "");
			}
		}

		// 顧客存在チェック
		CustomerService service = ServiceLocator
				.getCustomerService(getLoginInfo());
		boolean hasCustomer = true;
		if (StringUtil.hasValue(customerCode)) {
			if ((displayMode.equals("edit") || displayMode.equals("address")
					|| displayMode.equals("order")
					|| displayMode.equals("point") || displayMode
					.equals("coupon"))
					&& (service.isNotFound(customerCode) || service
							.isWithdrawed(customerCode))) {
				hasCustomer = false;
			}
			// ショップ管理者
			if (Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo())) {
				hasCustomer &= service.isShopCustomer(customerCode,
						getLoginInfo().getShopCode());
			}
		} else {
			if (displayMode.equals("edit") || displayMode.equals("address")
					|| displayMode.equals("order")
					|| displayMode.equals("point")
					|| displayMode.equals("coupon")
					|| Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo())) {
				hasCustomer = false;
			}
		}
		if (!hasCustomer) {
			addErrorMessage(WebMessage
					.get(
							ServiceErrorMessage.NO_DATA_ERROR,
							Messages
									.getString("web.action.back.customer.CustomerListMoveAction.0")));
			setRequestBean(getBean());

			return BackActionResult.RESULT_SUCCESS;
		}

		if (displayMode.equals("address")) {
			// アドレス帳一覧存在チェック
			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCustomerCode(customerCode);
			if (service.getCustomerAddressList(condition).getRowCount() < 1) {
				// delete by os012 20111213 start
				// addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
				// Messages.getString("web.action.back.customer.CustomerListMoveAction.1")));
				// setRequestBean(getBean());
				//
				// return BackActionResult.RESULT_SUCCESS;
				// delete by os012 20111213 start
			}
		}

		if (displayMode.equals("mail")) {
			if (!getBean().isMailDisplayFlg()) {
				addErrorMessage(WebMessage
						.get(CustomerErrorMessage.NOT_EXIST_INFORMATION_MAIL_ERROR));
				setRequestBean(getBean());

				return BackActionResult.RESULT_SUCCESS;
			}
			// 検索条件を保持
			CustomerListBean searchBean = getBean();

			CustomerSearchCondition condition = new CustomerSearchCondition();
			setSearchCondition(searchBean, condition);
			condition.setSearchCustomerStatus(CustomerStatus.MEMBER.getValue());
			condition.setSearchLoginLockedFlg(LoginLockedFlg.UNLOCKED
					.getValue());
			// 顧客の有無の判定を行う為、最大取得件数を2にする
			condition.setMaxFetchSize(2);

			// 検索結果リストを取得
			SearchResult<CustomerSearchInfo> result = service
					.findCustomer(condition);

			// 該当データなし
			if (result.getRowCount() == 0) {
				addErrorMessage(WebMessage
						.get(CustomerErrorMessage.INFORMATION_MAIL_NO_DATA));

				setRequestBean(searchBean);
				return BackActionResult.RESULT_SUCCESS;
			}

			InformationSendBean nextBean = new InformationSendBean();

			nextBean.setSearchCustomerFrom(searchBean.getSearchCustomerFrom());
			nextBean.setSearchCustomerTo(searchBean.getSearchCustomerTo());
			nextBean.setSearchCustomerGroupCode(searchBean
					.getSearchCustomerGroupCode());
			nextBean.setSearchTel(searchBean.getSearchTel());
			// Add by V10-Ch start
			nextBean.setSearchMobile(searchBean.getSearchMobile());
			// Add by V10-CH end
			nextBean.setSearchCustomerName(searchBean.getSearchCustomerName());
			nextBean.setSearchCustomerNameKana(searchBean
					.getSearchCustomerNameKana());
			nextBean.setSearchEmail(searchBean.getSearchEmail());
			nextBean.setSearchRequestMailType(searchBean
					.getSearchRequestMailType());
			nextBean.setSearchClientMailType(searchBean
					.getSearchClientMailType());
			setRequestBean(nextBean);
		}
		// 次画面のURLをセット
		setNextUrl(customerCode, displayMode);

		// 前画面情報設定
		if (getBean().getPagerValue() == null) {
			DisplayTransition.add(getBean(), "/app/customer/customer_list/",
					getSessionContainer());
		} else {
			DisplayTransition.add(getBean(),
					"/app/customer/customer_list/search_back",
					getSessionContainer());
		}

		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * 次画面のURLをセットします。
	 * 
	 * @param String
	 *            displayMode
	 * @param String
	 *            customerCode
	 */
	private void setNextUrl(String customerCode, String displayMode) {
		if (displayMode.equals("edit")) {
			setNextUrl("/app/customer/customer_edit/select/" + customerCode);
		} else if (displayMode.equals("address")) {
			setNextUrl("/app/customer/address_list/init/" + customerCode);
		} else if (displayMode.equals("order")) {
			setNextUrl("/app/customer/order_history/init/" + customerCode);
		} else if (displayMode.equals("point")) {
			setNextUrl("/app/customer/point_history/init/" + customerCode);
		} else if (displayMode.equals("coupon")) {
			setNextUrl("/app/customer/coupon_list/init/" + customerCode);
		} else if (displayMode.equals("mail")) {
			setNextUrl("/app/customer/information_send/init");
		} else {
			setNextUrl("/app/customer/customer_list");
		}
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return Messages
				.getString("web.action.back.customer.CustomerListMoveAction.2");
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "2103011003";
	}

}
