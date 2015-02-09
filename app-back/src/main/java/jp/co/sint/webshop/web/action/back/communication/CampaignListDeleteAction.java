package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.data.CampaignContents;
import jp.co.sint.webshop.service.data.ContentsInfo;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CampaignListDeleteAction extends CampaignListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      auth = Permission.CAMPAIGN_DELETE_SHOP.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      auth = Permission.CAMPAIGN_DELETE_SITE.isGranted(getLoginInfo()) || Permission.CAMPAIGN_DELETE_SHOP.isGranted(getLoginInfo());
    }

    return auth;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());

    Date today = DateUtil.truncateDate(DateUtil.getSysdate());
    String[] campaignCodeArray = getRequestParameter().getAll("campaignCode");

    List<String> successList = new ArrayList<String>();
    List<String> failureList = new ArrayList<String>();

    for (String deleteCode : campaignCodeArray) {
      boolean result = true;
      // 1.キャンペーンデータ削除
      String[] code = deleteCode.split("/");

      if (code.length < 2) {
        result &= false;
        failureList.add(WebMessage.get(ActionErrorMessage.DELETE_ERROR, Messages
            .getString("web.action.back.communication.CampaignListDeleteAction.0")));
        continue;
      }

      String shopCode = code[0];
      String campaignCode = code[1];
      Campaign campaign = communicationService.getCampaign(shopCode, campaignCode);

      // キャンペーン期間内
      if (DateUtil.isPeriodDate(campaign.getCampaignStartDate(), campaign.getCampaignEndDate(), today)) {
        result &= false;
        failureList.add(WebMessage.get(CommunicationErrorMessage.DELETE_EFFECT_CAMPAIGN_ERROR)
            + MessageFormat.format(Messages.getString(
                "web.action.back.communication.CampaignListDeleteAction.1"), campaignCode));
        continue;
      }

      ServiceResult delCampaignResult = communicationService.deleteCampaign(shopCode, campaignCode);

      if (delCampaignResult.hasError()) {
        result &= false;
        for (ServiceErrorContent error : delCampaignResult.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            failureList.add(Message.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(Messages
                .getString("web.action.back.communication.CampaignListDeleteAction.2"),
                campaignCode)));
          } else if (error.equals(CommunicationServiceErrorContent.DELETE_CAMPAIGN_ERROR)) {
            failureList.add(Message.get(CommunicationErrorMessage.DELETE_CAMPAIGN_ERROR, Messages
                .getString("web.action.back.communication.CampaignListDeleteAction.3") + campaignCode));
          }
        }
      }

      // 2.コンテンツ削除
      ContentsInfo contentsInfo = new CampaignContents(shopCode, campaignCode);
      ContentsSearchCondition condition = new ContentsSearchCondition();
      condition.setShopCode(shopCode);
      condition.setCampaignCode(campaignCode);
      if (DIContainer.getWebshopConfig().isOne()) {
        condition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SITE);
      } else {
        condition.setContentsType(ContentsType.CONTENT_SHOP_CAMPAIGN_SHOP);
      }

      if (service.contentsExists(condition)) {
        ServiceResult delContentsResult = service.deleteContents(contentsInfo);
        if (delContentsResult.hasError()) {
          result &= false;
          for (ServiceErrorContent error : delContentsResult.getServiceErrorList()) {
            if (error.equals(DataIOServiceErrorContent.CONTENTS_DELETE_ERROR)) {
              addErrorMessage(Message.get(ActionErrorMessage.DELETE_ERROR, MessageFormat.format(Messages
                  .getString("web.action.back.communication.CampaignListDeleteAction.4"),
                  campaignCode)));
            }
          }
        }
      }

      if (result) {
        successList.add(WebMessage.get(CompleteMessage.DELETE_COMPLETE, MessageFormat.format(Messages
            .getString("web.action.back.communication.CampaignListDeleteAction.2"), campaignCode)));
      }
    }

    for (String s : failureList) {
      addErrorMessage(s);
    }

    if (successList.size() == campaignCodeArray.length) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.communication.CampaignListDeleteAction.0")));
    } else {
      for (String s : successList) {
        addInformationMessage(s);
      }
    }

    // 再検索
    setRequestBean(getBean());
    return super.callService();
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    // キャンペーンが1つも選択されていない場合は削除不可
    String[] campaignCodeArray = getRequestParameter().getAll("campaignCode");
    if (StringUtil.isNullOrEmpty(campaignCodeArray[0]) && campaignCodeArray.length <= 1) {
      getDisplayMessage().addError(
          WebMessage.get(ActionErrorMessage.NO_CHECKED, Messages
              .getString("web.action.back.communication.CampaignListDeleteAction.0")));
      return false;
    }
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.CampaignListDeleteAction.5");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106031001";
  }

}
