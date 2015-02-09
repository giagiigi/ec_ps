package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.dto.CampaignDoings;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewCampaignEditGiftUpdateAction extends NewCampaignEditBaseAction {

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

    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        auth = Permission.CAMPAIGN_DELETE_SHOP.isGranted(login)
            || (Permission.CAMPAIGN_DELETE_SITE.isGranted(login) && getConfig().isOne());
      }
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    NewCampaignEditBean bean = getBean();

    // 新建关联商品登录
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("login".equals(getRequestParameter().getPathArgs()[0])) {
        CampaignLine campaignLine = new CampaignLine();
        CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
        campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());
        boolean flag = validateBean(bean.getGiftCommodityBean());
        if (!flag) {
          return false;
        } else {
          boolean commodityFlg = isCommodity(bean, false, true);
          boolean existFlg = isGiftExists(bean, campaignLine);
          boolean resultFlg = isGift(bean);
          boolean sameFlg = isSamePeriod(bean);
          if (commodityFlg) {
            // 商品不存在
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品"
                + bean.getGiftCommodityBean().getGiftComdtyCode()));
            return false;
          } else if (resultFlg) {
            // 不是通常品
            addErrorMessage(WebMessage.get(CommunicationErrorMessage.NO_GIFT_COMMODITY));
            return false;
          } else if (existFlg) {
            // 在数据中存在
            addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_COMMODITY_ERROR, "赠送"));
            return false;
          } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {
            if (sameFlg) {
              // 同一赠品不能同时适用于多个特定商品的促销活动
              addErrorMessage(WebMessage.get(CommunicationErrorMessage.NO_SPECIAL_MANY_ERROR, bean
                  .getGiftCommodityBean().getGiftComdtyCode()));
              return false;
            }
          }
        }

      } else if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        if (bean.getCheckedGiftCode().size() < 1
            || !StringUtil.hasValueAllOf(ArrayUtil.toArray(getBean().getCheckedGiftCode(), String.class))) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.COMMODITY_NO_CHOOSE, "赠送商品"));
          return false;

        }
      }
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    NewCampaignEditBean bean = getBean();
    CampaignLine campaignLine = new CampaignLine();
    CampaignDoings doings = new CampaignDoings();
    // 为dto设置值
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    boolean flag = false;
    ServiceResult result = null;
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "login".equals(getRequestParameter().getPathArgs()[0])) {
      campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());
      campaignLine.getCampaignMain().setUpdatedDatetime(bean.getUpdateDatetime());
      doings.setCampaignCode(bean.getCampaignCode());
      doings.setCampaignType(Long.valueOf(bean.getCampaignType()));
      doings.setAttributrValue(setGiftJoin(bean, campaignLine));
      campaignLine.setCampaignDoings(doings);

      flag = true;

    }
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "delete".equals(getRequestParameter().getPathArgs()[0])) {
      campaignLine = communicationService.loadCampaignLine(bean.getCampaignCode());

      if (campaignLine.getCampaignDoings() != null) {

        String str = "";
        for (int i = 0; i < bean.getGiftList().size(); i++) {
          boolean equalFlg = false;

          for (int j = 0; j < bean.getCheckedGiftCode().size(); j++) {
            if (bean.getCheckedGiftCode().get(j).equals(bean.getGiftList().get(i).getGiftComdtyCode())) {
              equalFlg = true;
              break;
            }
          }

          if (!equalFlg) {
            if (StringUtil.isNullOrEmpty(str)) {
              str += bean.getGiftList().get(i).getGiftComdtyCode();
            } else {
              str += "," + bean.getGiftList().get(i).getGiftComdtyCode();
            }

          }
        }
        campaignLine.getCampaignDoings().setAttributrValue(str);
      }
      campaignLine.getCampaignMain().setUpdatedDatetime(bean.getUpdateDatetime());
      result = communicationService.updateCampaignDoings(campaignLine);
      if (result.hasError()) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DELETE_FAILED_ERROR, "赠送商品"));
        return BackActionResult.RESULT_SUCCESS;
      } else {
        setNextUrl("/app/communication/new_campaign_edit/select/" + bean.getCampaignCode() + "/giftDelete");
      }
    }

    if (flag) {
      // 向数据库中增加促销信息
      if (CampaignMainType.MULTIPLE_GIFT.getValue().equals(bean.getCampaignType())) {
        result = communicationService.updateCampaignLine(campaignLine, true, false, true);
      } else if (CampaignMainType.GIFT.getValue().equals(bean.getCampaignType())) {
        result = communicationService.updateCampaignLine(campaignLine, true, false, true);
      }

      if (result.hasError()) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_FAILED_ERROR, "赠送商品"));
        return BackActionResult.RESULT_SUCCESS;

      } else {
        setNextUrl("/app/communication/new_campaign_edit/select/" + bean.getCampaignCode() + "/giftLogin");
      }
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.006");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102005";
  }

}
