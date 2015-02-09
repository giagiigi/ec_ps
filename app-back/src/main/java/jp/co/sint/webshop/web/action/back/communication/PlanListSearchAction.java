package jp.co.sint.webshop.web.action.back.communication;


import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.PlanSearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanListBean;
import jp.co.sint.webshop.web.bean.back.communication.PlanListBean.PlanDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;


/**
 * U1030740:企划一览のアクションクラスです
 * 
 * @author OB.
 */
public class PlanListSearchAction extends WebBackAction<PlanListBean> {
	
  private PlanSearchCondition condition;

  protected PlanSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected PlanSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new PlanSearchCondition();
  }

  
  
  
  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
	return Permission.PLAN_READ_SHOP.isGranted(getLoginInfo());
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

    condition = getCondition();
    // 日付の大小関係チェック
    condition.setPlanStartDateFrom(getBean().getSearchStartDateFrom());
    condition.setPlanStartDateTo(getBean().getSearchStartDateTo());
    condition.setPlanEndDateFrom(getBean().getSearchEndDateFrom());
    condition.setPlanEndDateTo(getBean().getSearchEndDateTo());

    if (condition.isValid()) {
	  return true;
    } else {
	  if (StringUtil.hasValueAllOf(condition.getPlanStartDateFrom(), condition.getPlanStartDateTo())) {
	    if (!StringUtil.isCorrectRange(condition.getPlanStartDateFrom(), condition.getPlanStartDateTo())) {
	      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "企划开始时间"));
		}
	  }
	  if (StringUtil.hasValueAllOf(condition.getPlanEndDateFrom(), condition.getPlanEndDateTo())) {
	    if (!StringUtil.isCorrectRange(condition.getPlanEndDateFrom(), condition.getPlanEndDateTo())) {
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
	PlanListBean bean = getBean();
	CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
	condition = getCondition();
	condition.setPlanDetailType(bean.getPlanType());
    condition.setPlanCode(bean.getPlanCode());
    condition.setPlanName(bean.getPlanName());
    condition.setPlanNameEn(bean.getPlanNameEn());
    condition.setPlanNameJp(bean.getPlanNameJp());
    condition.setPlanStartDateFrom(getBean().getSearchStartDateFrom());
    condition.setPlanStartDateTo(getBean().getSearchStartDateTo());
    condition.setPlanEndDateFrom(getBean().getSearchEndDateFrom());
    condition.setPlanEndDateTo(getBean().getSearchEndDateTo());
    condition.setPlanType(getBean().getPlanTypeMode());
    condition.setCampaignStatus(getBean().getSearchCampaignStatus());
    SearchResult<Plan> result = service.getPlanList(condition);
    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);
    
    List<PlanDetailBean> detailList = new ArrayList<PlanDetailBean>();
    bean.getList().clear();
    for (Plan plan : result.getRows()) {
    	PlanDetailBean detail = new PlanDetailBean();
        detail.setPlanCode(plan.getPlanCode());
        detail.setPlanName(plan.getPlanName());
        detail.setPlanType(DIContainer.getPlanDetailTypeValue().getPaymentTimesName(NumUtil.toLong(bean.getPlanTypeMode()), plan.getPlanDetailType()));
        detail.setStartDate(DateUtil.toDateTimeString(plan.getPlanStartDatetime()));
        detail.setEndDate(DateUtil.toDateTimeString(plan.getPlanEndDatetime()));
        detailList.add(detail);
      }
    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setList(detailList);
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
	if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "促销企划管理画面检索处理";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划管理画面检索处理";
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
	  return "5106081002";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106091002";
	}
    return "";
   }

}
