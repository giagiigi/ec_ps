package jp.co.sint.webshop.web.action.back.analysis;


import java.util.List;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.PlanSummaryViewInfo;
import jp.co.sint.webshop.service.analysis.PlanSummaryViewSearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.PlanAnalysisBean;
import jp.co.sint.webshop.web.bean.back.analysis.PlanAnalysisBean.PlanAnalysisDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1071210:企划分析检索处理のアクションクラスです
 * 
 * @author OB.
 */
public class PlanAnalysisSearchAction extends WebBackAction<PlanAnalysisBean> {

	// 检索条件
	private PlanSummaryViewSearchCondition condition;

	protected PlanSummaryViewSearchCondition getCondition() {
		return PagerUtil.createSearchCondition(getRequestParameter(), condition);
	}

	protected PlanSummaryViewSearchCondition getSearchCondition() {
		return this.condition;
	}

	/**
	 * 初期処理を実行します
	 */
	@Override
	public void init() {
		condition = new PlanSummaryViewSearchCondition();
	}

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		if((Permission.ANALYSIS_READ_SHOP.isGranted(getLoginInfo()) && !getConfig().isOne())
				|| Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo()))
		{
			//设定查询的值
			setCondtion(getCondition(), getBean());
			return true;
		}
		return false;
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		ValidationSummary summary = BeanValidator.validate(getBean());
	    if (summary.hasError()) {
	      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
	      return false;
	    }
	    if (condition.isValid()) {
		  return true;
	    } else {
		  if (StringUtil.hasValueAllOf(condition.getSearchStartDateFrom(), condition.getSearchStartDateTo())) {
		    if (!StringUtil.isCorrectRange(condition.getSearchStartDateFrom(), condition.getSearchStartDateTo())) {
		      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "企划开始时间"));
			}
		  }
		  if (StringUtil.hasValueAllOf(condition.getSearchEndDateFrom(), condition.getSearchEndDateTo())) {
		    if (!StringUtil.isCorrectRange(condition.getSearchEndDateFrom(), condition.getSearchEndDateTo())) {
		      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "企划结束时间"));
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
		PlanAnalysisBean bean = getBean();
		AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());
	    SearchResult<PlanSummaryViewInfo> result = service.getPlanSummaryViewList(condition);
	    // 添加提示信息
	    prepareSearchWarnings(result, SearchWarningType.BOTH);
	    // 获得检索信息
	    setPlanAnalysisDetail(result.getRows(), bean);
	    bean.setPagerValue(PagerUtil.createValue(result));
		setRequestBean(bean);

		return BackActionResult.RESULT_SUCCESS;
	}
	/**
	 * 设定查询参数
	 * @param condition PlanSummaryViewSearchCondition
	 * @param bean PlanAnalysisBean
	 */
	public void setCondtion(PlanSummaryViewSearchCondition condition,PlanAnalysisBean bean)
	{
	    condition.setShopCode(getLoginInfo().getShopCode());
	    condition.setSearchPlanCode(bean.getSearchPlanCode());
	    condition.setSearchPlanName(bean.getSearchPlanName());
	    condition.setSearchStartDateFrom(bean.getSearchStartDateFrom());
	    condition.setSearchStartDateTo(bean.getSearchStartDateTo());
	    condition.setSearchEndDateFrom(bean.getSearchEndDateFrom());
	    condition.setSearchEndDateTo(bean.getSearchEndDateTo());
	    condition.setSearchPlanDetailType(bean.getSearchPlanDetailType());
	    condition.setSearchOrderType(bean.getSearchOrderType());
	    condition.setSearchStatisticsType(bean.getSearchStatisticsType());
	    condition.setPlanTypeMode(bean.getPlanTypeMode());
	    
	}
	/**
	 * 获得检索信息
	 * @param list List<PlanSummaryViewInfo>
	 * @param bean PlanAnalysisBean
	 */
	public void setPlanAnalysisDetail(List<PlanSummaryViewInfo> list,PlanAnalysisBean bean)
	{
		bean.getList().clear();
		for (PlanSummaryViewInfo view : list) {
			PlanAnalysisDetail detail = new PlanAnalysisDetail();
			detail.setCampaignTotalPrice(view.getCampaignTotalPrice());
			detail.setCancleCampaignPrice(view.getCancelCampaignPrice());
			detail.setCancelTotalPrice(view.getCancelTotalPrice());
			detail.setCancelUnitPrice(view.getCancelUnitPrice());
			detail.setOrderTotalCount(view.getOrderTotalCount());
			detail.setOrderTotalPrice(view.getOrderTotalPrice());
			detail.setPlanCode(view.getPlanCode());
			detail.setPlanDescription(view.getPlanDescription());
			detail.setPlanDescriptionEn(view.getPlanDescriptionEn());
			detail.setPlanDescriptionJp(view.getPlanDescriptionJp());
			detail.setPlanName(view.getPlanName());
			detail.setPlanNameEn(view.getPlanNameEn());
			detail.setPlanNameJp(view.getPlanNameJp());
			detail.setPlanType(view.getPlanType());
			detail.setShopCode(view.getShopCode());
			detail.setPlanDetailType(view.getPlanDetailType());
			detail.setDetailName(view.getDetailName());
			if(view.getSummaryType()==1)
			{
				detail.setSummaryName("商品分类");
			}else if(view.getSummaryType()==2)
			{
				detail.setSummaryName("品牌");
			}
			detail.setOrderType(view.getOrderType());
			detail.setCancleTotalCount(view.getCancelTotalCount());
			
			bean.getList().add(detail);
		}
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
		if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
			  return "促销企划分析画面检索处理";
			} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
		      return "特集企划分析画面检索处理";
			}
		    return "";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
	    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
			return "6107121002";
		} else if (PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
			return "6107131002";
		}
		return "";
	}
	  /**
	   * 获取页面传过来的查询信息
	   * @return boolean
	   */
	  public boolean isCallCreateAttribute() {
			return true;
		}
}
