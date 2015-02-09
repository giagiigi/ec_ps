package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.PlanCommodity;
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.communication.PlanDetailHeadLine;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanEditBean;
import jp.co.sint.webshop.web.bean.back.communication.PlanEditBean.PlanEditDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030740:企划登录/更新のアクションクラスです
 * 
 * @author OB.
 */
public class PlanEditRegisterAction extends WebBackAction<PlanEditBean> {

  private Boolean actionFlg = true;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.PLAN_UPDATE_SHOP.isGranted(login) && Permission.PLAN_READ_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    PlanEditBean bean = getBean();
    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }
    // 20130702 txw add start
    if (PlanType.PROMOTION.getValue().equals(bean.getPlanTypeMode())) {
      if (StringUtil.isNullOrEmpty(bean.getPlanType())) {
        addErrorMessage("企划类型必须输入。");
        return false;
      }
    } else {
      if (StringUtil.getLength(bean.getPlanCode()) > 4) {
        addErrorMessage("特集企划的企划编号不能超过4位。");
        return false;
      }
    }
    // 20130702 txw add end
    if (!StringUtil.isCorrectRange(bean.getDateFrom(), bean.getDateTo())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      return false;
    }
    return true;
  }

  /**
   * 画面から入力された値をキャンペーンのDTOに設定します
   * 
   * @param campaign
   * @param bean
   */
  private void setPlan(Plan plan, PlanEditBean bean) {
    plan.setPlanCode(bean.getPlanCode());
    plan.setPlanName(bean.getPlanName());
    plan.setPlanNameEn(bean.getPlanNameEn());
    plan.setPlanNameJp(bean.getPlanNameJp());
    // 20130702 txw add start
    if (PlanType.PROMOTION.getValue().equals(bean.getPlanTypeMode())) {
      plan.setPlanDetailType(bean.getPlanType());
    } else {
      plan.setPlanDetailType(bean.getPlanCode());
      plan.setTitle(bean.getTitle());
      plan.setTitleEn(bean.getTitleEn());
      plan.setTitleJp(bean.getTitleJp());
      plan.setDescription(bean.getDescription());
      plan.setDescriptionEn(bean.getDescriptionEn());
      plan.setDescriptionJp(bean.getDescriptionJp());
      plan.setKeyword(bean.getKeyword());
      plan.setKeywordEn(bean.getKeywordEn());
      plan.setKeywordJp(bean.getKeywordJp());
    }
    // 20130702 txw add end
    plan.setPlanDescription(bean.getPlanDescription());
    plan.setPlanDescriptionEn(bean.getPlanDescriptionEn());
    plan.setPlanDescriptionJp(bean.getPlanDescriptionJp());
    plan.setMemo(bean.getRemarks());
    plan.setPlanEndDatetime(DateUtil.fromString(bean.getDateTo(), true));
    plan.setPlanEndDatetime(DateUtil.setSecond(plan.getPlanEndDatetime(), 59));
    plan.setPlanStartDatetime(DateUtil.fromString(bean.getDateFrom(), true));
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PlanEditBean bean = getBean();
    CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());

    CommunicationService communicationManagementService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = null;

    if (bean.getDisplayMode().equals(WebConstantCode.DISPLAY_READONLY)) {
      actionFlg = false;
      Plan plan = cs.getPlan(bean.getPlanCode());
      if (plan == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "企划"));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      setPlan(plan, bean);
      plan.setUpdatedDatetime(bean.getUpdateDatetime());
      result = communicationManagementService.updatePlan(plan);
      if (result.hasError()) {
        setRequestBean(bean);
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          // 存在性错误报告
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "企划"));
            return BackActionResult.RESULT_SUCCESS;
          }
          if (error.equals(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "企划期间"));
            return BackActionResult.RESULT_SUCCESS;
          }
          if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          }
        }
        return BackActionResult.SERVICE_ERROR;
      } else {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "企划"));
      }
    } else {
      actionFlg = true;
      if (cs.getPlan(bean.getPlanCode()) != null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "企划编号"));
        this.setRequestBean(bean);

        return BackActionResult.RESULT_SUCCESS;
      }
      Plan plan = new Plan();
      plan.setPlanType(NumUtil.toLong(bean.getPlanTypeMode()));
      setPlan(plan, bean);
      result = communicationManagementService.insertPlan(plan);
      if (result.hasError()) {
        setRequestBean(bean);
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          // 重复性错误报告
          if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "企划编号"));
            return BackActionResult.RESULT_SUCCESS;
          }

          if (error.equals(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "企划期间"));
            return BackActionResult.RESULT_SUCCESS;
          }
          if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          }
        }
        return BackActionResult.SERVICE_ERROR;
      } else {
        // 20130724 txw add start
        if (bean.isCopyFlg() && StringUtil.hasValue(bean.getCopyPlanCode())) {
          List<PlanDetail> detailList = new ArrayList<PlanDetail>();
          detailList = communicationManagementService.getPlanDetailList(bean.getCopyPlanCode());
          for (PlanDetail pd : detailList) {
            pd.setPlanCode(plan.getPlanCode());
            communicationManagementService.insertPlanDetail(pd);
          }

          List<PlanCommodity> commodityList = new ArrayList<PlanCommodity>();
          commodityList = communicationManagementService.getPlanCommodityList(bean.getCopyPlanCode());
          for (PlanCommodity pc : commodityList) {
            pc.setPlanCode(plan.getPlanCode());
            communicationManagementService.insertPlanCommodity(pc);
          }

          List<PlanDetailHeadLine> pdList = new ArrayList<PlanDetailHeadLine>();
          pdList = communicationManagementService.getPlanDetailByPlanCode(plan.getPlanCode());
          List<PlanEditDetailBean> list = new ArrayList<PlanEditDetailBean>();

          for (int i = 0; i < pdList.size(); i++) {
            PlanDetailHeadLine detail = pdList.get(i);
            PlanEditDetailBean editDetailBean = new PlanEditDetailBean();
            editDetailBean.setDetailCode(detail.getDetailCode());
            editDetailBean.setDetailName(detail.getDetailName());
            editDetailBean.setUrl(detail.getDetailUrl());
            editDetailBean.setShowCount(detail.getShowCommodityCount());
            editDetailBean.setDetailType(detail.getDetailType());
            editDetailBean.setDisplayOrder(detail.getDisplayOrder());
            list.add(editDetailBean);
          }
          bean.setDetailList(list);
        }
        // 20130724 txw add end
        bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "企划"));
      }
    }
    Plan plan = cs.getPlan(bean.getPlanCode());
    if (plan != null) {
      bean.setUpdateDatetime(plan.getUpdatedDatetime());
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
      if (!actionFlg) {
        return "促销企划更新处理";
      } else {
        return "促销企划登录处理";
      }
    } else if (PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      if (!actionFlg) {
        return "特集企划更新处理";
      } else {
        return "特集企划登录处理";
      }
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
      if (!actionFlg) {
        return "5106082002";
      } else {
        return "5106082003";
      }
    } else if (PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      if (!actionFlg) {
        return "5106092002";
      } else {
        return "5106092003";
      }
    }
    return "";
  }
}
