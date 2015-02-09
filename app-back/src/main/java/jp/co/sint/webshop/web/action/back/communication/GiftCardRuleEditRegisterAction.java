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
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * 礼品卡登录处理
 * 
 * @author System Integrator Corp.
 */
public class GiftCardRuleEditRegisterAction extends GiftCardRuleEditBaseAction {

  public boolean validate() {
    GiftCardRuleEditBean bean = getBean();
    boolean flg = validateBean(bean);
    // 验证是否存在
    if (flg && checktoDuplicate(bean.getCardCode()) == true) {

      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_DEFAULT_ERROR));
      setRequestBean(getBean());
      setBean(getBean());
      return flg = false;
    }
    return flg;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
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
    
    GiftCardRule dto = new GiftCardRule();
    dto.setCardCode(bean.getCardCode());
    dto.setCardName(bean.getCardName().trim());
    dto.setEffectiveYears(Long.parseLong(bean.getEffectiveYears()));
    dto.setWeight(new BigDecimal(bean.getWeight()));
    dto.setUnitPrice(new BigDecimal(bean.getUnitPrice()));
    dto.setDenomination(new BigDecimal(bean.getDenomination()));

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult serviceResult = communicationService.insertGiftCardRule(dto);

    // 失败
    if (serviceResult.hasError()) {
      setNextUrl(null);
      for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, bean.getCardCode()));
      setRequestBean(getBean());
      return BackActionResult.SERVICE_ERROR;
    }
    setRequestBean(getBean());
    // 成功
    setNextUrl("/app/communication/gift_card_rule_edit/init/" + WebConstantCode.COMPLETE_INSERT + "/" + bean.getCardCode());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleEditRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106141005";
  }

  public boolean authorize() {
    if(super.authorize()){
      if (Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(getLoginInfo()) == false) {
        return false;
      }
    }
    return true;
  }

}
