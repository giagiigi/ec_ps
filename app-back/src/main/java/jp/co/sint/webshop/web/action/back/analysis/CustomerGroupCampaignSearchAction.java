package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewList;
import jp.co.sint.webshop.service.analysis.CustomerGroupCampaignSummaryViewSearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerGroupCampaignBean;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerGroupCampaignBean.CustomerGroupCampaignDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1071010:顾客组别优惠分析检索处理
 * 
 * @author ob.cxw
 */
public class CustomerGroupCampaignSearchAction extends WebBackAction<CustomerGroupCampaignBean> {

	// 检索条件
	private CustomerGroupCampaignSummaryViewSearchCondition condition;

	protected CustomerGroupCampaignSummaryViewSearchCondition getCondition() {
		return PagerUtil.createSearchCondition(getRequestParameter(), condition);
	}

	protected CustomerGroupCampaignSummaryViewSearchCondition getSearchCondition() {
		return this.condition;
	}

	/**
	 * 初期処理を実行します
	 */
	@Override
	public void init() {
		condition = new CustomerGroupCampaignSummaryViewSearchCondition();
	}

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		// 验证检索结果
		ValidationSummary summary = BeanValidator.validate(getBean());
		if (summary.hasError()) {
			getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
			return false;
		}

		// 日期检索条件
		condition = getCondition();

		condition.setCampaignStartDateFrom(getBean().getSearchStartDateFrom());
		condition.setCampaignStartDateTo(getBean().getSearchStartDateTo());
		condition.setCampaignEndDateFrom(getBean().getSearchEndDateFrom());
		condition.setCampaignEndDateTo(getBean().getSearchEndDateTo());

		// 比较检索日期，如果设置前日期比后日期大，则报错
		if (condition.isValid()) {
			return true;
		} else {
			if (StringUtil.hasValueAllOf(condition.getCampaignStartDateFrom(),
					condition.getCampaignStartDateTo())) {
				if (!StringUtil.isCorrectRange(condition.getCampaignStartDateFrom(), condition.getCampaignStartDateTo())) {
					addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "优惠开始时间"));
				}
			}
			if (StringUtil.hasValueAllOf(condition.getCampaignEndDateFrom(), condition.getCampaignEndDateTo())) {
				if (!StringUtil.isCorrectRange(condition.getCampaignEndDateFrom(), condition.getCampaignEndDateTo())) {
					addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "优惠结束时间"));
				}
			}
		}

		return false;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {

		// 设定检索条件
		CustomerGroupCampaignBean bean = getBean();
		AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());
		condition = getCondition();
		condition.setCampaignCode(bean.getSearchCampaignCode());
		condition.setCampaignName(bean.getSearchCampaignName());
		condition.setCampaignStartDateFrom(bean.getSearchStartDateFrom());
		condition.setCampaignStartDateTo(bean.getSearchStartDateTo());
		condition.setCampaignEndDateFrom(bean.getSearchEndDateFrom());
		condition.setCampaignEndDateTo(bean.getSearchEndDateTo());
		condition.setCampaignType(bean.getSearchCampaignType());
		condition.setCustomerGroupCode(bean.getSearchCustomerCode());
		condition.setCampaignStatus(bean.getSearchCampaignStatus());

		SearchResult<CustomerGroupCampaignSummaryViewList> result = service.getCustomerGroupCampaignSummaryViewList(condition);

		// オーバーフローチェック
		prepareSearchWarnings(result, SearchWarningType.BOTH);

		// 获得检索信息
		List<CustomerGroupCampaignDetail> campaignDetail = new ArrayList<CustomerGroupCampaignDetail>();
		bean.getList().clear();
		for (CustomerGroupCampaignSummaryViewList viewList : result.getRows()) {
			CustomerGroupCampaignDetail detail = new CustomerGroupCampaignDetail();
			detail.setCampaignCode(viewList.getCampaignCode());
			detail.setCampaignName(viewList.getCampaignName());
			detail.setCampaignNameEn(viewList.getCampaignNameEn());
			detail.setCampaignNameJp(viewList.getCampaignNameJp());
			detail.setCustomerGroupCode(viewList.getCustomerGroupCode());
			detail.setCustomerGroupName(viewList.getCustomerGroupName());
			detail.setCustomerGroupNameEn(viewList.getCustomerGroupNameEn());
			detail.setCustomerGroupNameJp(viewList.getCustomerGroupNameJp());
			detail.setOrderTotalPrice(viewList.getOrderTotalPrice());
			detail.setOrderTotalCount(NumUtil.toString(viewList.getOrderTotalCount()));
			detail.setOrderUnitPrice(viewList.getOrderUnitPrice());
			detail.setCampaignTotalPrice(viewList.getCampaignTotalPrice());
			detail.setCancelTotalPrice(viewList.getCancelTotalPrice());
			detail.setCancelTotalCount(NumUtil.toString(viewList.getCancelTotalCount()));
			detail.setCancelUnitPrice(viewList.getCancelUnitPrice());
			detail.setCancelCampaignPrice(viewList.getCancelCampaignPrice());

			campaignDetail.add(detail);
		}

		// 优惠活动信息分页
		bean.setPagerValue(PagerUtil.createValue(result));
		bean.setList(campaignDetail);

		// 设定顾客组别
		UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
		bean.setCustomerGroupList(utilService.getCustomerGroupNames());
		setRequestBean(bean);

		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * 画面表示に必要な項目を設定・初期化します。
	 */
	@Override
	public void prerender() {
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "顾客组别优惠分析一览画面检索处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "6107101002";
	}

}
