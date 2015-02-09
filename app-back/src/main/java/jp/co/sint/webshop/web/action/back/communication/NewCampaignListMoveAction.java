package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * 5106101002:促销活动一览のアクションクラスです
 * 
 * @author KS.
 */
public class NewCampaignListMoveAction extends NewCampaignListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
  String[] urlParam = getRequestParameter().getPathArgs();
  if (urlParam.length > 0 && (urlParam[0].equals("new"))) {
    if(Permission.CAMPAIGN_READ_SHOP.isGranted(getLoginInfo()) && Permission.CAMPAIGN_UPDATE_SHOP.isGranted(getLoginInfo()) 
        || Permission.CAMPAIGN_READ_SITE.isGranted(getLoginInfo()) && Permission.CAMPAIGN_UPDATE_SITE.isGranted(getLoginInfo())){
       return true;
     }else{
       return false;
     }
  }
  if (urlParam.length > 0 && urlParam[0].equals("select")) {
      if (Permission.CAMPAIGN_READ_SHOP.isGranted(getLoginInfo())
          || Permission.CAMPAIGN_READ_SITE.isGranted(getLoginInfo())) {
        return true;
      } else {
        return false;
      }
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
    // 由URL参数判断更新操作
    if (urlParam.length > 1) {
      campaignCode = urlParam[1];
      CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
      CampaignInfo campaignInfo = cs.getCampaignInfo(campaignCode);
      // 无更新数据判断
      if (campaignInfo == null || campaignInfo.getCampaignMain() == null) {
        setNextUrl(null);
        setRequestBean(getBean());
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "促销活动"));
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    // URL设定
    if (urlParam[0].equals("new")) {
      setNextUrl("/app/communication/new_campaign_edit");
    } else if (urlParam[0].equals("select")) {
      setNextUrl(getUrl("new_campaign_edit", urlParam[1]));
    } else {
      setRequestBean(getBean());
      return super.callService();
    }

    DisplayTransition.add(getBean(), "/app/communication/new_campaign_list/search_back", getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * コンテキストパス以降のnextUrlを生成します。
   * 
   * @param jspId
   * @param campaignCode
   * @return URL
   */
  private String getUrl(String jspId, String campaignCode) {
    return "/app/communication/" + jspId + "/select/" + campaignCode;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
      return "促销活动一览画面跳转处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
      return "5106101002";
   }

}
