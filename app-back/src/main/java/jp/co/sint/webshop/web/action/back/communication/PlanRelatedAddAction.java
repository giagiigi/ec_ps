package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.PlanCommodity;
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
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class PlanRelatedAddAction extends WebBackAction<PlanRelatedBean> {

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
		ValidationSummary summary = BeanValidator.partialValidate(getBean(), "commodityCode");
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
		String[] codeStringArray = bean.getCommodityCode().split("\r\n");
		CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
		String detailCode = "";
		if (PlanDetailType.BRAND.getValue().equals(bean.getDetailType())) {
		    detailCode = bean.getBrandCode();
		 } else if (PlanDetailType.CATEGORY.getValue().equals(bean.getDetailType())) {
			detailCode = bean.getCategoryCode();
		 } else if (PlanDetailType.FREE.getValue().equals(bean.getDetailType())) {
			detailCode = bean.getCode();
		 }
		List<String> successList = new ArrayList<String>();
		List<String> failureList = new ArrayList<String>();
		for (int i = 0; i < codeStringArray.length; i++) {
		  PlanCommodity planCommodity = new PlanCommodity();
		  planCommodity.setCommodityCode(codeStringArray[i]);
		  planCommodity.setDetailType(NumUtil.toLong(bean.getDetailType()));
		  planCommodity.setDetailCode(detailCode);
		  planCommodity.setPlanCode(bean.getPlanCode());
		  if (codeStringArray.length == 1 && StringUtil.hasValue(bean.getCommodityDisplayOrder())) {
			planCommodity.setDisplayOrder(NumUtil.toLong(bean.getCommodityDisplayOrder()));
		  }
		  ServiceResult result = communicationService.insertPlanCommodity(planCommodity);
		  if (result.hasError()) {
			  for (ServiceErrorContent content : result.getServiceErrorList()) {
  				if (content.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
  				  if (PlanDetailType.CATEGORY.longValue().equals(planCommodity.getDetailType())) {
  					failureList.add(WebMessage.get(CommunicationErrorMessage.PLAN_CATEGORY_COMMODITY, codeStringArray[i]));
  				  } else if (PlanDetailType.BRAND.longValue().equals(planCommodity.getDetailType())) {
  					failureList.add(WebMessage.get(CommunicationErrorMessage.PLAN_BARND_COMMODITY, codeStringArray[i]));
  				  }
  				}
  				if (content.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
            failureList.add("商品编号" + codeStringArray[i] + " 已存在.");
          }
			  }
		  } else {
			  successList.add(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, MessageFormat.format("关联商品(商品编号：{0})",
					  planCommodity.getCommodityCode())));
		  }
		}
		if (failureList.size() > 0 ) {
		  bean.setSuccessList(successList);
		  bean.setFailureList(failureList);
		  setNextUrl("/app/communication/plan_related/init/" + bean.getPlanCode() + 
					"/" + bean.getDetailType() + "/" + detailCode);
		} else {
		  setNextUrl("/app/communication/plan_related/init/" + bean.getPlanCode() + 
					"/" + bean.getDetailType() + "/" + detailCode + "/" + WebConstantCode.COMPLETE_REGISTER);
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
