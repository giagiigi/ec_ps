package jp.co.sint.webshop.web.action.back.customer;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030110:顧客マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerListSearchAction extends CustomerListBaseAction {

	private CustomerSearchCondition condition;

	/**
	 * 初期処理を実行します
	 */
	@Override
	public void init() {
		condition = new CustomerSearchCondition();
	}

	protected CustomerSearchCondition getCondition() {
		return PagerUtil
				.createSearchCondition(getRequestParameter(), condition);
	}

	protected CustomerSearchCondition getSearchCondition() {
		return this.condition;
	}

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		// 参照権限チェック
		boolean result = false;
		BackLoginInfo login = getLoginInfo();
		if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
				|| getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
			result = Permission.CUSTOMER_READ.isGranted(getLoginInfo());
		} else {
			if (getLoginInfo().isSite()) {
				result = Permission.CUSTOMER_READ.isGranted(getLoginInfo());
			} else {
				result = Permission.CUSTOMER_READ_SHOP
						.isGranted(getLoginInfo());
			}
		}
		if (Permission.CUSTOMER_READ_SHOP.isGranted(login)) {
			CustomerListBean nextBean = getBean();
			nextBean.setShop(true);
			setRequestBean(nextBean);
		}
		return result;
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		boolean result = true;
		CustomerListBean bean = getBean();

		// bean(検索条件)のvalidationチェック
		result = validateBean(bean);

		// 顧客コードの大小チェック
		condition.setSearchCustomerFrom(bean.getSearchCustomerFrom());
		condition.setSearchCustomerTo(bean.getSearchCustomerTo());
		setSearchCondition(bean, condition);
		condition = getCondition();

		if (!condition.isValid()) {
			// 検索条件不正エラーメッセージの設定
			addErrorMessage(WebMessage
					.get(
							ActionErrorMessage.COMPARISON_ERROR,
							Messages
									.getString("web.action.back.customer.CustomerListSearchAction.0")));
			setRequestBean(bean);

			result = false;
		}

		return result;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {

		CustomerListBean nextBean = getBean();

		// 顧客種別リストを取得
		UtilService s = ServiceLocator.getUtilService(getLoginInfo());
		nextBean.setSearchCustomerGroupList(s.getCustomerGroupNames());

		// 検索結果リストを取得
		CustomerService service = ServiceLocator
				.getCustomerService(getLoginInfo());
		SearchResult<CustomerSearchInfo> result = service
				.findCustomer(condition);

		// 検索結果0件チェック
		if (result.getRowCount() == 0) {
			addWarningMessage(WebMessage
					.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
		}
		// オーバーフローチェック
		if (result.isOverflow()) {
			this.addWarningMessage(WebMessage.get(
					ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil
							.formatNumber("" + result.getRowCount()), ""
							+ NumUtil.formatNumber(""
									+ condition.getMaxFetchSize())));
		}

		// ページ情報を追加
		nextBean.setPagerValue(PagerUtil.createValue(result));

		// 結果一覧を作成
		List<CustomerSearchInfo> customerList = result.getRows();
		setCustomerList(nextBean, customerList);

		setRequestBean(nextBean);

		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * beanのcreateAttributeを実行するかどうかの設定
	 * 
	 * @return true - 実行する false-実行しない
	 */
	public boolean isCallCreateAttribute() {
		if (getUrlPath().length() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 削除完了・エラーメッセージ表示
	 */
	@Override
	public void prerender() {
		if (getUrlPath().equals("withdrawal")) {
			if (getBean().isSeccessWithdrawal()) {
				addInformationMessage(WebMessage
						.get(CompleteMessage.CUSTOMER_WITHDRAWED_COMPLETE));
			}
			if (getBean().getErrorCustomerCode() != null) {
				for (String customerCode : getBean().getErrorCustomerCode()) {
					addErrorMessage(MessageFormat
							.format(
									Messages
											.getString("web.action.back.customer.CustomerListSearchAction.1"),
									customerCode)
							+ WebMessage
									.get(CustomerErrorMessage.DELETE_CUSTOMER_ORDER_ERROR));
				}
			}
		}
		// 10.1.4 10114 追加 ここから
		// 削除権限チェック
		if (Permission.CUSTOMER_DELETE.isGranted(getLoginInfo())) {
			CustomerListBean nextBean = (CustomerListBean) getRequestBean();
			nextBean.setDeleteDisplayFlg(true);
		}
		// 10.1.4 10114 追加 ここまで

		// 登録権限チェック
		if (Permission.CUSTOMER_UPDATE.isGranted(getLoginInfo())) {
			CustomerListBean nextBean = (CustomerListBean) getRequestBean();
			if (StringUtil.isNullOrEmpty(nextBean.getSearchRequestMailType())
					|| nextBean.getSearchRequestMailType().equals(
							RequestMailType.UNWANTED.getValue())) {
				nextBean.setHasNoRequestCustomer(true);
			} else {
				nextBean.setHasNoRequestCustomer(false);
			}
		}
	}

	/**
	 * URLから処理パラメータを取得
	 * 
	 * @return 処理パラメータ
	 */
	private String getUrlPath() {
		String[] args = getRequestParameter().getPathArgs();
		if (args.length > 0) {
			return args[0];
		}
		return "";
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return Messages
				.getString("web.action.back.customer.CustomerListSearchAction.2");
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "2103011004";
	}

}
