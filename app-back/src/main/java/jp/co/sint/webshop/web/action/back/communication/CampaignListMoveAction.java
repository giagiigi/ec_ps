package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignListMoveAction extends CampaignListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;

    BackLoginInfo login = getLoginInfo();
    auth = Permission.CAMPAIGN_READ_SITE.isGranted(login) || Permission.CAMPAIGN_READ_SHOP.isGranted(login);

    String[] urlParam = getRequestParameter().getPathArgs();

    if (urlParam.length > 0) {
      String command = urlParam[0];
      // 新規登録・更新の場合は更新権限が必要。
      if (command.equals("new") || command.equals("edit")) {
        auth &= Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)
            || (Permission.CAMPAIGN_UPDATE_SITE.isGranted(login) && getConfig().isOne());
      }
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    return (urlParam.length > 0 && urlParam[0].equals("new")) || (urlParam.length > 2);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] urlParam = getRequestParameter().getPathArgs();
    // urlParam[1]:ショップコード、urlParam[2]:キャンペーンコード

    if (urlParam.length > 2) { // 遷移先が新規登録画面以外の場合、キャンペーンの存在チェックを行う
      CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
      Campaign campaign = svc.getCampaign(urlParam[1], urlParam[2]);
      if (campaign == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.communication.CampaignListMoveAction.0")));
        setRequestBean(getBean());
        return super.callService();
      }
    }

    if (urlParam[0].equals("new")) {
      setNextUrl("/app/communication/campaign_edit/init");
    } else if (urlParam[0].equals("edit")) {
      setNextUrl(getUrl("campaign_edit", urlParam[1], urlParam[2]));
    } else if (urlParam[0].equals("related")) {

      RelatedBean relatedBean = new RelatedBean();
      relatedBean.setSearchShopCode(urlParam[1]);
      relatedBean.setEffectualCode(urlParam[2]);
      relatedBean.setPictureMode("campaign");
      setNextUrl("/app/catalog/related/init/");
      setRequestBean(relatedBean);
      // 前画面情報設定
      DisplayTransition.add(getBean(), "/app/communication/campaign_list/search_back", getSessionContainer());
      return BackActionResult.RESULT_SUCCESS;

    } else if (urlParam[0].equals("research")) {
      setNextUrl(getUrl("campaign_research", urlParam[1], urlParam[2]));
    } else {
      addErrorMessage(WebMessage.get(CommunicationErrorMessage.MOVE_ACTION_ERROR,
          Messages.getString("web.action.back.communication.CampaignListMoveAction.1")));
      setRequestBean(getBean());
      return super.callService();
    }

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/communication/campaign_list/search_back", getSessionContainer());
    setRequestBean(getBean());
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
  private String getUrl(String jspId, String shopCode, String campaignCode) {
    return "/app/communication/" + jspId + "/init/" + shopCode + "/" + campaignCode;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.CampaignListMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106031003";
  }

}
