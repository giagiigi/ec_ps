package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.PlanCommodity;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PlanRelatedBean;
import jp.co.sint.webshop.web.bean.back.communication.PlanRelatedBean.RelatedCommodityBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;

/**
 * U1060820:特集企划关联商品更新表示处理
 * 
 * @author System Integrator Corp.
 */
public class PlanRelatedCommodityUpdateAction extends WebBackAction<PlanRelatedBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.PLAN_READ_SHOP.isGranted(login) && Permission.PLAN_UPDATE_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    PlanRelatedBean bean = getBean();

    for (RelatedCommodityBean rcb : bean.getList()) {
      rcb.setDisplayOrder(getRequestParameter().get("sn" + rcb.getCommodityCode()));
    }

    for (RelatedCommodityBean rcb : bean.getList()) {
      PlanRelatedBean validateBean = new PlanRelatedBean();
      validateBean.setDisplayOrder(rcb.getDisplayOrder());
      boolean validation = validateItems(validateBean, "displayOrder");
      if (!validation) {
        return false;
      }
    }

    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    PlanRelatedBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    List<PlanCommodity> list = communicationService.getPlanCommodityList(bean.getPlanCode());

    List<PlanCommodity> newList = new ArrayList<PlanCommodity>();

    if (list.size() > 0) {
      for (PlanCommodity pc : list) {
        for (RelatedCommodityBean rcb : bean.getList()) {
          if (pc.getCommodityCode().equals(rcb.getCommodityCode())
              && bean.getDetailType().equals(NumUtil.toString(pc.getDetailType()))) {
            if (StringUtil.hasValue(rcb.getDisplayOrder())) {
              pc.setDisplayOrder(Long.parseLong(rcb.getDisplayOrder()));
            } else {
              pc.setDisplayOrder(null);
            }
            newList.add(pc);
            break;
          }
        }
      }

      ServiceResult updateResult = communicationService.updatePlanCommodity(newList);

      // エラー処理
      if (updateResult.hasError()) {
        for (ServiceErrorContent error : updateResult.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          } else {
            return BackActionResult.SERVICE_ERROR;
          }
        }
      }
    }

    addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "关联商品一览"));
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "特集企划关联商品更新表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106092001";
  }

}
