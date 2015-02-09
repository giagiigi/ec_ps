package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewCampaignEditSelectAction extends NewCampaignEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    if (Permission.CAMPAIGN_READ_SHOP.isGranted(getLoginInfo())
        || Permission.CAMPAIGN_READ_SITE.isGranted(getLoginInfo())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    String[] param = getRequestParameter().getPathArgs();
    CampaignMain campaignMain = new CampaignMain();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    if (param.length > 1) {
      campaignMain = communicationService.loadCampaignMain(param[0]);
      if (campaignMain == null) {
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
        } else if ("area".equals(param[1])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "配送地域"));
        } else if ("giftLogin".equals(param[1])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "赠送商品"));
        } else if ("giftDelete".equals(param[1])) {
          this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "赠送商品"));
        }
      }
    } else if (param.length == 1) {
      campaignMain = communicationService.loadCampaignMain(param[0]);
      if (campaignMain == null) {
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
    NewCampaignEditBean bean = new NewCampaignEditBean();

    String[] param = getRequestParameter().getPathArgs();

    CampaignLine campaignLine = new CampaignLine();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    campaignLine = communicationService.loadCampaignLine(param[0]);
    if (campaignLine != null) {
      // 为bean赋值
      setNewCampaignEditBean(bean, campaignLine);
      setRelateAndAreaLogin(bean, campaignLine);
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
      // 控制赠送商品按钮
      bean.setDisGiftButtonFlg(false);
      // 控制地域按钮
      bean.setDisAreaButtonFlg(false);
      // 只有查看功能的按钮
      bean.setDisplayReadFlg(true);
    } else {
      bean.setDisplayReadFlg(false);
    }
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 控制关联商品和地域登录按钮的显示
   * 
   * @param bean
   * @param campaignLine
   */
  public void setRelateAndAreaLogin(NewCampaignEditBean bean, CampaignLine campaignLine) {
    if (campaignLine.getConditionList() != null) {
      if (campaignLine.getConditionList().size() > 1) {
        if (CampaignMainType.SHIPPING_CHARGE_FREE.longValue().equals(campaignLine.getCampaignMain().getCampaignType())) {
          bean.setDisRelatedButtonFlg(true);
          bean.setDisAreaButtonFlg(true);

        } else {
          bean.setDisRelatedButtonFlg(true);
          bean.setDisAreaButtonFlg(true);
          bean.setDisGiftButtonFlg(true);
        }

      } else if (campaignLine.getConditionList().size() == 1) {
        if (CampaignMainType.SHIPPING_CHARGE_FREE.longValue().equals(campaignLine.getCampaignMain().getCampaignType())) {
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(
              campaignLine.getConditionList().get(0).getCampaignConditionType())) {
            bean.setDisRelatedButtonFlg(true);
            bean.setDisAreaButtonFlg(false);
            bean.setDisGiftButtonFlg(true);
          } else if (NewCampaignConditionType.ORDER_ADDRESS.longValue().equals(
              campaignLine.getConditionList().get(0).getCampaignConditionType())) {
            bean.setDisRelatedButtonFlg(false);
            bean.setDisAreaButtonFlg(true);
            bean.setDisGiftButtonFlg(true);
          }
        } else {
          bean.setDisRelatedButtonFlg(true);
          bean.setDisAreaButtonFlg(true);
          bean.setDisGiftButtonFlg(true);
        }

      } else {
        bean.setDisRelatedButtonFlg(false);
        bean.setDisAreaButtonFlg(false);
        bean.setDisGiftButtonFlg(true);
      }
    }
  }

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
