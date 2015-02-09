package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.DiscountType;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean;
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
public class NewCampaignEditRegisterAction extends NewCampaignEditBaseAction {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	public boolean authorize() {

		BackLoginInfo login = getLoginInfo();

	    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
	    // 時のみアクセス可能
	    boolean auth = Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)
	        || (Permission.CAMPAIGN_UPDATE_SITE.isGranted(login) && getConfig().isOne());
	    return auth;
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	public boolean validate() {
    NewCampaignEditBean bean = getBean();
    boolean flg = validateBean(bean);
    if (flg) {
      // 日付の大小関係チェック
      if (!StringUtil.isCorrectRange(bean.getCampaignStartDate(), bean.getCampaignEndDate())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        return false;
      }
      
      if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())
          && bean.getCheckList() != null && bean.getCheckList().size() < 1) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.CONDITION_TYPE_NO_CHOOSE));
        return false;
      }
      
      if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType())) {
        if (DiscountType.CUSTOMER.getValue().equals(bean.getDiscountType())
            && StringUtil.isNullOrEmpty(bean.getDiscoutRate())) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DISCOUNT_NOT_NULL, "折扣比例"));
          return false;
        } else if (DiscountType.COUPON.getValue().equals(bean.getDiscountType())
            && StringUtil.isNullOrEmpty(bean.getDiscountMoney())) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DISCOUNT_NOT_NULL, "折扣金额"));
          return false;
        }
      }
      
      CampaignMain campaignMain = new CampaignMain();
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      campaignMain = communicationService.loadCampaignMain(bean.getCampaignCode());
      if (campaignMain != null) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_REGISTER_ERROR, bean.getCampaignCode()));
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
    NewCampaignEditBean bean = getBean();
    CampaignLine campaignLine = new CampaignLine();
    // 为dto设置值
    setCampaignLine(bean, campaignLine);
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    ServiceResult result = null;
    // 向数据库中增加促销信息
    if (CampaignMainType.SHIPPING_CHARGE_FREE.getValue().equals(bean.getCampaignType())) {
      if (bean.getCheckList() != null && bean.getCheckList().size() > 0) {
        result = communicationService.saveCampagin(campaignLine, true, false);
      }

    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType()) && CampaignType.PROPORTION.getValue().equals(bean.getDiscountType())) {
      result = communicationService.saveCampagin(campaignLine, true, true);
    } else if (CampaignMainType.SALE_OFF.getValue().equals(bean.getCampaignType()) && CampaignType.FIXED.getValue().equals(bean.getDiscountType())) {
      result = communicationService.saveCampagin(campaignLine, true, true);
    } else if (CampaignMainType.MULTIPLE_GIFT.getValue().equals(bean.getCampaignType())) {
      result = communicationService.saveCampagin(campaignLine, true, true);
    } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {
      //赠品促销时默认设置 20140822 hdh add 
      campaignLine.getCampaignMain().setGiftAmount(1L);
      campaignLine.getCampaignMain().setMinCommodityNum(0L);
      //20140822 hdh add end 
      result = communicationService.saveCampagin(campaignLine, true, true);
    }

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

    setNextUrl("/app/communication/new_campaign_edit/select/" + bean.getCampaignCode() + "/register");
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
