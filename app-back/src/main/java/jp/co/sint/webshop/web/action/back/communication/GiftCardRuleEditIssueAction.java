package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.CancelFlg;
import jp.co.sint.webshop.data.domain.CsvFlg;
import jp.co.sint.webshop.data.dto.GiftCardIssueHistory;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class GiftCardRuleEditIssueAction extends GiftCardRuleEditBaseAction {

  @Override
  public boolean authorize() {
    return Permission.GIFT_CARD_RULE_READ_SHOP.isGranted(getLoginInfo()) && Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  @Override
  public WebActionResult callService() {
    GiftCardRuleEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    GiftCardIssueHistory history = new GiftCardIssueHistory();

    history.setCardCode(bean.getCardCode());
    history.setCardHistoryNo(communicationService.getGiftCardIssueHistory(bean.getCardCode()));
    // 获取系统默认时间
    history.setIssueDate(DateUtil.getSysdate());

    history.setIssueNum(NumUtil.toLong(bean.getIssueNum()));
    history.setCsvFlg(CsvFlg.NOT_EXPORT.longValue());
    history.setCancelFlg(CancelFlg.NOT_CANCELLED.longValue());

    ServiceResult serviceResult = communicationService.insertGiftCardRuleIssueAction(history);

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
    setNextUrl("/app/communication/gift_card_rule_edit/init/" + WebConstantCode.Gift_ISSUE + "/" + bean.getCardCode());

    return BackActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    GiftCardRuleEditBean bean = getBean();
    boolean flg = false;
    // 判断issuenum是否为空↘
    if (StringUtil.hasValue(bean.getIssueNum())) {
      flg = validateItems(bean, "issueNum");
    } else {
      addErrorMessage("发行张数不能为空！");
      flg = false;
    }
    return flg;
  }
  

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
      return Messages.getString("web.action.back.communication.GiftCardRuleEditIssueAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
      return "5106141004";
   }

  
  

}
