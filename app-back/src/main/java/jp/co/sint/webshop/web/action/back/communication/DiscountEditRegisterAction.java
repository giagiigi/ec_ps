package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1061210:限时限量折扣编辑画面登录表示处理
 * 
 * @author System Integrator Corp.
 */
public class DiscountEditRegisterAction extends DiscountEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.DISCOUNT_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    DiscountEditBean bean = getBean();
    boolean flg = validateItems(bean, "discountCode", "discountName", "discountStartDatetime", "discountEndDatetime");
    if (flg) {
      // 日付の大小関係チェック
      if (!StringUtil.isCorrectRange(bean.getDiscountStartDatetime(), bean.getDiscountEndDatetime())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        return false;
      }

      DiscountHeader dh = new DiscountHeader();
      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      dh = communicationService.getDiscountHeader(bean.getDiscountCode());
      if (dh != null) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_DISCOUNT_ERROR, bean.getDiscountCode()));
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
    DiscountEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    DiscountHeader dh = new DiscountHeader();
    setDiscountHeader(bean, dh);

    ServiceResult result = communicationService.insertDiscountHeader(dh);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_DISCOUNT_ERROR, bean.getDiscountCode()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    setNextUrl("/app/communication/discount_edit/select/" + bean.getDiscountCode() + "/register");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.DiscountEditRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106122002";
  }

}
