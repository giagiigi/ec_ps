package jp.co.sint.webshop.web.action.back.service;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.service.CardRefundBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class CardRefundRegisterAction extends CardRefundBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.MEMBER_INFO_REFUND);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean flg = validateBean(getBean());
    
    // 礼品卡退款金额必须大于0
    if (flg) {
      BigDecimal refundPrice = new BigDecimal(getBean().getRefundPrice());
      if (BigDecimalUtil.isBelowOrEquals(refundPrice, BigDecimal.ZERO)) {
        addErrorMessage(Messages.getString("web.action.back.service.CardRefundRegisterAction.2"));
        flg = false;
      }
    }
    
    // 所有退款金额不能大于订单使用金额
    if (flg) {
      BigDecimal usedTotalDenomination = new BigDecimal(getBean().getCardUsedPrice());
      BigDecimal refundTotalDenomination = new BigDecimal(getBean().getRefundPrice());
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      List<CustomerCardInfo> cciList = communicationService.getCustomerCardInfoList(getBean().getOrderNo());
      for (CustomerCardInfo cci : cciList) {
        refundTotalDenomination = refundTotalDenomination.add(cci.getDenomination());
      }

      if (BigDecimalUtil.isAbove(refundTotalDenomination, usedTotalDenomination)) {
        addErrorMessage(Messages.getString("web.action.back.service.CardRefundRegisterAction.1"));
        flg = false;
      }
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

    CardRefundBean bean = getBean();

    GiftCardReturnApply gcra = new GiftCardReturnApply();

    setBeanToDto(bean, gcra);

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult serviceResult = communicationService.insertGiftCardReturnApply(gcra);

    // 登录失败
    if (serviceResult.hasError()) {
      setNextUrl(null);
      for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, gcra.getOrderNo()));
      setRequestBean(getBean());
      setBean(getBean());
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/service/card_refund/init/" + bean.getOrderNo() + "/" + WebConstantCode.COMPLETE_REGISTER);
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.CardRefundRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109051002";
  }
}
