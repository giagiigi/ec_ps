package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class GiftCardRuleEditUpdateAction extends GiftCardRuleEditBaseAction {

  public boolean authorize() {
    return Permission.GIFT_CARD_RULE_READ_SHOP.isGranted(getLoginInfo())
        && Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  public WebActionResult callService() {
    GiftCardRuleEditBean bean = getBean();
    
    if( bean.getCardName().trim().equals("") ||
        BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getWeight()), BigDecimal.ZERO) || 
        BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getUnitPrice()), BigDecimal.ZERO) ||
        BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getDenomination()), BigDecimal.ZERO)){
      if(bean.getCardName().trim().equals("")){
        addErrorMessage("礼品卡名称不能全空格");
      }
      if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getWeight()), BigDecimal.ZERO)) {
        addErrorMessage("卡重量不能小于等于0");
      }
      if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getUnitPrice()), BigDecimal.ZERO)) {
        addErrorMessage("单价不能小于等于0");
      }
      if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getDenomination()), BigDecimal.ZERO)) {
        addErrorMessage("面值不能小于等于0");
      }
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
    }
    
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    GiftCardRule giftCardRule = communicationService.getGiftCardRule(bean.getCardCode());
    setBeanToDto(giftCardRule, bean);

    ServiceResult result = communicationService.updateGiftCardRule(giftCardRule);

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
    setNextUrl("/app/communication/gift_card_rule_edit/init/" + WebConstantCode.COMPLETE_UPDATE + "/" + bean.getCardCode());

    setRequestBean(bean);
    setBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleEditUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106141007";
  }

  public boolean validate() {
    GiftCardRuleEditBean bean = getBean();
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
    return true;
  }

}
