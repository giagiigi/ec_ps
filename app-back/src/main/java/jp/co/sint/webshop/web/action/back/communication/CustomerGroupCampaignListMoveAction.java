package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1060510:顾客组别优惠规则管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignListMoveAction extends CustomerGroupCampaignListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
	String[] urlParam = getRequestParameter().getPathArgs();
	if (urlParam.length > 0 && urlParam[0].equals("new")) {
	  if (Permission.CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP.isDenied(getLoginInfo())) {
		return false;
	  }
	}
    return Permission.CUSTOMER_GROUP_CAMPAIGN_READ_SHOP.isGranted(getLoginInfo());
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
    String campaignCode = "";
    if (urlParam.length > 1) {
      campaignCode = urlParam[1];
      CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
      CustomerGroupCampaign customerGroupCampaign = cs.getCustomerGroupCampaign(campaignCode);

      if (customerGroupCampaign == null) {
        setNextUrl(null);
        setRequestBean(getBean());
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "顾客组别优惠"));
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    
    if (urlParam[0].equals("new")) {
        setNextUrl("/app/communication/customer_group_campaign_edit/init");
      } else if (urlParam[0].equals("edit")) {
        setNextUrl(getUrl("customer_group_campaign_edit", urlParam[1]));
      } else {
        setRequestBean(getBean());
        return super.callService();
      }

    DisplayTransition.add(getBean(), "/app/communication/customer_group_campaign_list/search_back", getSessionContainer());
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
    return "/app/communication/" + jspId + "/init/" + campaignCode;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客组别优惠规则管理变迁处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106051002";
  }
  

}
