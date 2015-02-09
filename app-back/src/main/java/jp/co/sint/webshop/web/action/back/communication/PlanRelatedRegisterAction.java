package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.service.CatalogService;
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
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class PlanRelatedRegisterAction extends WebBackAction<PlanRelatedBean> {

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
		CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
		CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
		PlanDetail planDetail = new PlanDetail();
		if (PlanDetailType.BRAND.getValue().equals(bean.getDetailType())) {
			planDetail.setDetailCode(bean.getBrandCode());
			Brand brand = cs.getBrand(getLoginInfo().getShopCode(), bean.getBrandCode());
			if (brand!=null){
				planDetail.setDetailName(brand.getBrandName());
			}
		} else if (PlanDetailType.CATEGORY.getValue().equals(
				bean.getDetailType())) {
			planDetail.setDetailCode(bean.getCategoryCode());
			Category category = cs.getCategory(bean.getCategoryCode());
			if (category!=null){
				planDetail.setDetailName(category.getCategoryNamePc());
			}
		} else {
			planDetail.setDetailCode(bean.getCode());
			planDetail.setDetailName(bean.getName());
			planDetail.setDetailNameEn(bean.getNameEn());
			planDetail.setDetailNameJp(bean.getNameJp());
			planDetail.setDetailUrl(bean.getUrl());
			planDetail.setDetailUrlEn(bean.getUrlEn());
			planDetail.setDetailUrlJp(bean.getUrlJp());
		}
		planDetail.setPlanCode(bean.getPlanCode());
		planDetail.setDetailType(NumUtil.toLong(bean.getDetailType()));
		if (StringUtil.hasValue(bean.getDisplayOrder())) {
			planDetail.setDisplayOrder(NumUtil.toLong(bean.getDisplayOrder()));
		}
		planDetail.setShowCommodityCount(NumUtil.toLong(bean.getShowCount()));
		ServiceResult result = communicationService.insertPlanDetail(planDetail);
		if (result.hasError()) {
		   for (ServiceErrorContent error : result.getServiceErrorList()) {
		     // 重复性错误报告
		     if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
			   addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,"企划明细"));
			   setRequestBean(bean);
			   return BackActionResult.RESULT_SUCCESS;
		     }
		     if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
				   addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,"企划关联明细"));
				   setRequestBean(bean);
				   return BackActionResult.RESULT_SUCCESS;
			     }
		     if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
			   return BackActionResult.SERVICE_VALIDATION_ERROR;
		     }
	      }
		} else {
		  bean.setIsUpdateFlg(true);
		  bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
		  addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "企划关联明细"));
		}
		// 品牌
		  if (PlanDetailType.BRAND.longValue().equals(planDetail.getDetailType())) {
			Brand brand = cs.getBrand(getLoginInfo().getShopCode(), planDetail.getDetailCode());
			bean.setBrandName(brand.getBrandName());
		  // 商品分类
		  } else if (PlanDetailType.CATEGORY.longValue().equals(planDetail.getDetailType())) {
			bean.setCategoryName(cs.getCategory(planDetail.getDetailCode()).getCategoryNamePc());
			bean.setCategoryPath(cs.getCategory(planDetail.getDetailCode()).getPath());
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
