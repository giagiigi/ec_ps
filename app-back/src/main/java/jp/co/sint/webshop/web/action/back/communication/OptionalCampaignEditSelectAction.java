package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OptionalCampaignEditSelectAction extends OptionalCampaignEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.OPTIONAL_CAMPAGIN_READ_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    String[] param = getRequestParameter().getPathArgs();
    OptionalCampaign campaign = new OptionalCampaign();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    String shopCode =getLoginInfo().getShopCode();
    if (param.length > 1) {
      campaign = communicationService.loadOptionalCampaign(shopCode,param[0]);
      if (campaign == null) {
        throw new URLNotFoundException();
      } else {
        if ("register".equals(param[1])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, param[0]));
        } else if ("update".equals(param[1])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_SUCCESS_INFO, param[0]));
        } else if ("relatedLogin".equals(param[1])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "关联商品"));
        } else if ("relatedDelete".equals(param[1])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "关联商品"));
        } 
      }
    } else if (param.length == 1) {
      campaign = communicationService.loadOptionalCampaign(shopCode,param[0]);
      if (campaign == null) {
        throw new URLNotFoundException();
      }
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
  public WebActionResult callService() {
    OptionalCampaignEditBean bean = new OptionalCampaignEditBean();
    String shopCode =getLoginInfo().getShopCode();

    String[] param = getRequestParameter().getPathArgs();

    OptionalCampaign campaign = new OptionalCampaign();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    campaign = communicationService.loadOptionalCampaign(shopCode,param[0]);
    if (campaign != null) {
      // 为bean赋值
      setOptionalCampaignEditBean(bean, campaign);
      setRelateCommodity(bean, campaign);
    } else {
      throw new URLNotFoundException();
    }
    if (Permission.CAMPAIGN_DELETE_SITE.isGranted(getLoginInfo())
        || Permission.CAMPAIGN_DELETE_SHOP.isGranted(getLoginInfo())) {
      bean.setDisplayDeleteFlg(true);
    } else {
      bean.setDisplayDeleteFlg(false);
    }
    if (!Permission.CAMPAIGN_UPDATE_SITE.isGranted(getLoginInfo())
        && !Permission.CAMPAIGN_UPDATE_SHOP.isGranted(getLoginInfo())) {
      // 控制活动登录按钮
      bean.setDisplayLoginButtonFlg(false);
      // 控制活动更新按钮
      bean.setDisplayUpdateButtonFlg(false);
      // 控制关联商品按钮
      bean.setDisRelatedButtonFlg(false);
      // 只有查看功能的按钮
      bean.setDisplayReadFlg(true);
    } else {
      bean.setDisplayReadFlg(false);
    }
    bean.setDisRelatedButtonFlg(true);
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 设计关联商品
   * 
   * @param bean
   * @param campaignLine
   */


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.007");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102006";
  }
  
}
