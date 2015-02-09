package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.data.domain.CancelFlg;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.data.dto.GiftCardIssueHistory;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class GiftCardRuleEditCancelAction extends GiftCardRuleEditBaseAction {

  public boolean authorize() {
    if (super.authorize()) {
      if (Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(getLoginInfo()) == false) {
        return false;
      }
    }
    return true;
  }

  public WebActionResult callService() {
    GiftCardRuleEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = null;
    ServiceResult resultDetail = null;
    GiftCardIssueHistory hist = null;

    if (getPathInfo(0).equals("num")) {
      // 根据卡编号和批次获取卡的激活状态
      List<String> statusAll = communicationService.getGiftCardIssueDetailCardStatus(bean.getCardCode(), getPathInfo(1));
      // 根据卡编号与批次获取GiftCardIssueHistory表信息
      hist = communicationService.getGiftCardIssueHis(bean.getCardCode(), getPathInfo(1));
      hist.setCancelFlg(CancelFlg.CANCELLED.longValue());
      // 修改history表的信息
      result = communicationService.updateHistory(hist);
      if (!statusAll.contains(1)) {
        resultDetail = communicationService.updateGiftCardIssueDetailStatus(bean.getCardCode(), getPathInfo(1));
      } else {
        // 根据卡编号和批次批量修改激活状态
        resultDetail = communicationService.updateGiftCardIssueDetail(bean.getCardCode(), getPathInfo(1));
      }
      if (resultDetail.hasError()) {
        for (ServiceErrorContent error : resultDetail.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          }
        }

        setNextUrl(null);
        addErrorMessage(WebMessage.get(ServiceErrorMessage.UPDATE_FAILED, bean.getCardCode()));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/communication/gift_card_rule_edit/init/" + WebConstantCode.Gift_CANCEL + "/" + bean.getCardCode());
      addErrorMessage(WebMessage.get(ServiceErrorMessage.UPDATE_FAILED, bean.getCardCode()));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    } else if (getPathInfo(0).equals("code")) {
      GiftCardIssueDetail detail = communicationService.getGiftCardIssueDetail(bean.getSearchCardId());
      detail.setCancelFlg(CancelFlg.CANCELLED.longValue());
      bean.setDetail(detail);
      result = communicationService.updateDetail(detail);
    }

    // 更新失败
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }

      setNextUrl(null);
      addErrorMessage(WebMessage.get(ServiceErrorMessage.UPDATE_FAILED, bean.getCardCode()));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新成功
    addInformationMessage("礼品卡取消成功");

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleEditCanAction.0");
  }

  public String getOperationCode() {
    return "5106141001";
  }

  /**
   * 取得指定參數值
   * 
   * @param index
   *          下標
   * @return 值
   */
  protected String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

  public boolean validate() {
    GiftCardRuleEditBean bean = getBean();
    String mode = getPathInfo(0);
    if (validateBean(bean) == false) {
      return false;
    }

    // 验证是否存在
    if (existGiftCard(bean.getCardCode()) == false) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, ""));
      setRequestBean(getBean());
      setBean(getBean());
      return false;
    }
    if (StringUtil.isNullOrEmpty(mode)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    } else if (!(mode.equals("num") || mode.equals("code"))) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    return true;
  }

}
