package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1030740:促销企划一览のアクションクラスです
 * 
 * @author OB.
 */
public class PlanListMoveAction extends PlanListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
	String[] urlParam = getRequestParameter().getPathArgs();
	if (urlParam.length > 0 && urlParam[0].equals("new")) {
	  if (Permission.PLAN_UPDATE_SHOP.isDenied(getLoginInfo())) {
		return false;
	  }
	}
    return Permission.PLAN_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    return (urlParam.length > 0 && urlParam[0].equals("new")) || (urlParam.length > 1);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	String[] urlParam = getRequestParameter().getPathArgs();
    String planCode = "";
    if (urlParam.length > 1) {
    	planCode = urlParam[1];
      CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
      Plan customerGroupCampaign = cs.getPlan(planCode);

      if (customerGroupCampaign == null) {
        setNextUrl(null);
        setRequestBean(getBean());
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "促销企划"));
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    
    if (urlParam[0].equals("new")) {
        // 20130724 txw update start
        if(StringUtil.hasValue(planCode)) {
          setNextUrl("/app/communication/plan_edit/init/" + getBean().getPlanTypeMode() + "/" + planCode + "/copy");
        } else {
          setNextUrl("/app/communication/plan_edit/init/" + getBean().getPlanTypeMode());
        }
        // 20130724 txw update start
      } else if (urlParam[0].equals("edit")) {
        setNextUrl(getUrl("plan_edit", urlParam[1]));
      } else {
        setRequestBean(getBean());
        return super.callService();
      }

    DisplayTransition.add(getBean(), "/app/communication/plan_list/search_back", getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * コンテキストパス以降のnextUrlを生成します。
   * 
   * @param jspId
   * @param shopCode
   * @param campaignCode
   * @return URL
   */
  private String getUrl(String jspId, String campaignCode) {
    return "/app/communication/" + jspId + "/init/" + getBean().getPlanTypeMode() + "/" + campaignCode;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
	if (PlanType.PROMOTION.getValue().equals(getBean().getPlanTypeMode())) {
	  return "促销企划管理画面变迁处理";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "特集企划管理画面变迁处理";
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
	  return "5106081004";
	} else if(PlanType.FEATURE.getValue().equals(getBean().getPlanTypeMode())) {
      return "5106091004";
	}
    return "";
   }

}
