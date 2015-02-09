package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanRelatedBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;

public class PlanRelatedUpdateAction extends WebBackAction<PlanRelatedBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		BackLoginInfo login = getLoginInfo();
		return Permission.PLAN_READ_SHOP.isGranted(login)
				&& Permission.PLAN_UPDATE_SHOP.isGranted(login);
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		PlanRelatedBean bean = getBean();
		ValidationSummary summary = null;
		if (PlanDetailType.BRAND.getValue().equals(bean.getDetailType())) {
			summary = BeanValidator.partialValidate(getBean(), "brandCode");
		} else if (PlanDetailType.CATEGORY.getValue().equals(bean.getDetailType())) {
			summary = BeanValidator.partialValidate(getBean(), "categoryCode");
		} else {
			summary = BeanValidator.partialValidate(getBean(), "code", "name", "url");
		}
		ValidationSummary summary2 = BeanValidator.partialValidate(getBean(), "showCount", "displayOrder");
		summary.getErrors().addAll(summary2.getErrors());
		if (summary.hasError()) {
		  getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
		  return false;
    	}
		return true;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {
		PlanRelatedBean bean = getBean();
		CommunicationService communicationService = ServiceLocator
				.getCommunicationService(getLoginInfo());
		PlanDetail planDetail = null;
		if (PlanDetailType.BRAND.getValue().equals(bean.getDetailType())) {
		  planDetail = communicationService.getPlanDetail(bean.getPlanCode(), bean.getDetailType(), bean.getBrandCode());
		} else if (PlanDetailType.CATEGORY.getValue().equals(bean.getDetailType())) {
		  planDetail = communicationService.getPlanDetail(bean.getPlanCode(), bean.getDetailType(), bean.getCategoryCode());
		} else {
		  planDetail = communicationService.getPlanDetail(bean.getPlanCode(), bean.getDetailType(), bean.getCode());
		  planDetail.setDetailName(bean.getName());
		  planDetail.setDetailNameEn(bean.getNameEn());
		  planDetail.setDetailNameJp(bean.getNameJp());
		  planDetail.setDetailUrl(bean.getUrl());
		  planDetail.setDetailUrlEn(bean.getUrlEn());
		  planDetail.setDetailUrlJp(bean.getUrlJp());
		}
		if (StringUtil.hasValue(bean.getDisplayOrder())) {
		  planDetail.setDisplayOrder(NumUtil.toLong(bean.getDisplayOrder()));
		} else {
			planDetail.setDisplayOrder(null);
		}
		planDetail.setShowCommodityCount(NumUtil.toLong(bean.getShowCount()));
		ServiceResult result = communicationService.updatePlanDetail(planDetail);
		if (result.hasError()) {
		   for (ServiceErrorContent error : result.getServiceErrorList()) {
		     // 数据不存在
		     if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
			   addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,"企划关联明细"));
			   return BackActionResult.RESULT_SUCCESS;
		     }
		     if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
			   return BackActionResult.SERVICE_VALIDATION_ERROR;
		     }
	      }
		} else {
		  addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "企划关联明细"));
		}
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
		if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
			return "促销企划登录初期表示处理";
		} else if (PlanType.FEATURE.getValue().equals(
				getBean().getPlanTypeMode())) {
			return "特集企划登录初期表示处理";
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
			return "5106082001";
		} else if (PlanType.FEATURE.getValue().equals(
				getBean().getPlanTypeMode())) {
			return "5106092001";
		}
		return "";
	}
}
