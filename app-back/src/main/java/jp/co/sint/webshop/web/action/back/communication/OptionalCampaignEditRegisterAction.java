package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OptionalCampaignEditRegisterAction extends OptionalCampaignEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {

    BackLoginInfo login = getLoginInfo();

    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
    // 時のみアクセス可能
    return Permission.OPTIONAL_CAMPAGIN_UPDATE_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    OptionalCampaignEditBean bean = getBean();
    boolean flg = validateBean(bean);
    if (flg) {
      // 日付の大小関係チェック
      if (!StringUtil.isCorrectRange(bean.getCampaignStartDate(), bean.getCampaignEndDate())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        return false;
      }

      String shopCode = getLoginInfo().getShopCode();
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      OptionalCampaign campaign = communicationService.loadOptionalCampaign(shopCode, bean.getCampaignCode());
      if (campaign != null) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_REGISTER_ERROR, bean.getCampaignCode()));
        return false;
      }
      
      if(StringUtil.hasValue(bean.getOptionalPrice()) && 
          BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getOptionalPrice()), BigDecimal.ZERO)){
        addErrorMessage("活动价格不能为小于0");
        return false;
      }
    }

    return flg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    OptionalCampaignEditBean bean = getBean();
    OptionalCampaign campaign = new OptionalCampaign();
    // 为dto设置值
    setOptionalCampaign(bean, campaign);
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    ServiceResult result = null;
    result = communicationService.insertOptionalCampaign(campaign);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommunicationServiceErrorContent.DUPLICATED_CODE_ERROR)) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_REGISTER_ERROR, bean.getCampaignCode()));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_FAILED_ERROR, bean.getCampaignCode()));
          return BackActionResult.RESULT_SUCCESS;
        }
      }

    } else {
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
      bean.setDisplayLoginButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(true);
    }

    setNextUrl("/app/communication/optional_campaign_edit/select/" + bean.getCampaignCode() + "/register");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.003");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102002";
  }

}
