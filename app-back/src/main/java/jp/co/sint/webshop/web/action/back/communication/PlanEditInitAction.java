package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.PlanDetailHeadLine;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanEditBean;
import jp.co.sint.webshop.web.bean.back.communication.PlanEditBean.PlanEditDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030740:企划登录/更新のアクションクラスです
 * 
 * @author OB.
 */
public class PlanEditInitAction extends WebBackAction<PlanEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    String[] args = getRequestParameter().getPathArgs();
    if (args.length == 0 && Permission.PLAN_UPDATE_SHOP.isDenied(login)) {
      return false;
    }
    return Permission.PLAN_READ_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length == 0) {
      throw new URLNotFoundException();
    }
    getBean().setPlanTypeMode(getRequestParameter().getPathArgs()[0]);
    if (getBean().getPlanTypeMode().equals(PlanType.PROMOTION.getValue())
        || getBean().getPlanTypeMode().equals(PlanType.FEATURE.getValue())) {
    } else {
      throw new URLNotFoundException();
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
    String[] args = getRequestParameter().getPathArgs();

    // 20130724 txw update start
    boolean copyFlg = false;
    String copyPlanCode = "";
    // 20130724 txw update end

    PlanEditBean lastBean = getBean();
    PlanEditBean bean = new PlanEditBean();
    bean.setSuccessList(lastBean.getSuccessList());
    bean.setFailureList(lastBean.getFailureList());

    for (String s : bean.getFailureList()) {
      addErrorMessage(s);
    }
    for (String s : bean.getSuccessList()) {
      addInformationMessage(s);
    }

    bean.getSuccessList().clear();
    bean.getFailureList().clear();

    // 処理完了メッセージ存在時
    if (args.length == 3) {
      if (args[2].equals("delete")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "企划关联明细"));
      }
    }

    if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
      bean.setPlanDetailTypeList(DIContainer.getPlanDetailTypeValue().getSalePlanDetailTypes());
    } else {
      // 20130702 txw add start
      // bean.setPlanDetailTypeList(DIContainer.getPlanDetailTypeValue().getFeaturedPlanDetailTypes());
      // 20130702 txw add end
    }

    bean.setPlanTypeMode(args[0]);

    if (args.length >= 2) {
      // 20130724 txw update start
      if (args.length == 3) {
        if (args[2].equals("copy")) {
          copyFlg = true;
        } else if (!args[2].equals("delete")) {
          throw new URLNotFoundException();
        }
      }

      CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
      Plan plan = service.getPlan(args[1]);

      if (plan != null) {
        if (!NumUtil.toLong(getBean().getPlanTypeMode()).equals(plan.getPlanType())) {
          throw new URLNotFoundException();
        }
        if (copyFlg) {
          copyPlanCode = plan.getPlanCode();
        } else {
          bean.setPlanCode(plan.getPlanCode());
        }
        bean.setPlanName(plan.getPlanName());
        bean.setPlanType(plan.getPlanDetailType());
        bean.setDateFrom(DateUtil.toDateTimeString(plan.getPlanStartDatetime()));
        bean.setDateTo(DateUtil.toDateTimeString(plan.getPlanEndDatetime()));
        bean.setPlanDescription(plan.getPlanDescription());
        // add by cs_yuli 20120515 start
        bean.setPlanNameEn(plan.getPlanNameEn());
        bean.setPlanNameJp(plan.getPlanNameJp());
        bean.setPlanDescriptionEn(plan.getPlanDescriptionEn());
        bean.setPlanDescriptionJp(plan.getPlanDescriptionJp());
        // add by cs_yuli 20120515 end
        // 20130703 txw add start
        if (PlanType.FEATURE.getValue().equals(bean.getPlanTypeMode())) {
          bean.setTitle(plan.getTitle());
          bean.setTitleEn(plan.getTitleEn());
          bean.setTitleJp(plan.getTitleJp());
          bean.setDescription(plan.getDescription());
          bean.setDescriptionEn(plan.getDescriptionEn());
          bean.setDescriptionJp(plan.getDescriptionJp());
          bean.setKeyword(plan.getKeyword());
          bean.setKeywordEn(plan.getKeywordEn());
          bean.setKeywordJp(plan.getKeywordJp());
        }
        // 20130703 txw add end
        bean.setUpdateDatetime(plan.getUpdatedDatetime());
        bean.setRemarks(plan.getMemo());
        if (copyFlg) {
          bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
        } else {
          bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
          List<PlanDetailHeadLine> detailList = new ArrayList<PlanDetailHeadLine>();
          detailList = service.getPlanDetailByPlanCode(plan.getPlanCode());
          List<PlanEditDetailBean> list = new ArrayList<PlanEditDetailBean>();

          for (int i = 0; i < detailList.size(); i++) {
            PlanDetailHeadLine detail = detailList.get(i);
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
      } else {
        bean = new PlanEditBean();
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "企划"));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      // 20130724 txw update end
    } else if (args.length == 1) {
      bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    }

    // 20130724 txw add start
    if (copyFlg) {
      bean.setCopyFlg(copyFlg);
      bean.setCopyPlanCode(copyPlanCode);
    }
    // 20130724 txw add end

    if (Permission.PLAN_UPDATE_SHOP.isDenied(getLoginInfo())) {
      bean.setMode(WebConstantCode.DISPLAY_READONLY);
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
      bean.setUpdateAuthorizeFlg(false);
    } else {
      bean.setMode(WebConstantCode.DISPLAY_EDIT);
      bean.setUpdateAuthorizeFlg(true);
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
    } else if (PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
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
    } else if (PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106092001";
    }
    return "";
  }
}
