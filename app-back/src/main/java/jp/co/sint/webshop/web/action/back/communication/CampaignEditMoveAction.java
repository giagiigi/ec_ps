package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.communication.CampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1060320:キャンペーンマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CampaignEditMoveAction extends WebBackAction<CampaignEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
    // 時のみアクセス可能
    boolean auth = Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)
        || (Permission.CAMPAIGN_UPDATE_SITE.isGranted(login) && getConfig().isOne());

    return auth;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CampaignEditBean campaignBean = getBean();
    String shopCode = campaignBean.getShopCode();
    String campaignCode = campaignBean.getCampaignCode();

    String[] path = getRequestParameter().getPathArgs();

    if (path.length > 0) { // キャンペーンの存在チェック
      CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
      Campaign campaign = svc.getCampaign(shopCode, campaignCode);
      if (campaign == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.communication.CampaignEditMoveAction.0")));
        setRequestBean(campaignBean);
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    if (path.length > 0 && path[0].equals("related")) {

      RelatedBean relatedBean = new RelatedBean();
      relatedBean.setSearchShopCode(shopCode);
      relatedBean.setEffectualCode(campaignCode);
      relatedBean.setPictureMode("campaign");

      setNextUrl("/app/catalog/related/init/");
      setRequestBean(relatedBean);

    } else {
      setNextUrl("/app/communication/campaign_edit/init/" + shopCode + "/" + campaignCode);
      setRequestBean(campaignBean);
    }

    // 遷移元情報をセッションに保存
    DisplayTransition.add(getBean(), "/app/communication/campaign_edit/init/"
        + shopCode + "/" + campaignCode,
        getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.CampaignEditMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106032002";
  }

}
