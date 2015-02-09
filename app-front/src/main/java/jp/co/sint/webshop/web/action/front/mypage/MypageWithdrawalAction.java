package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MypageBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class MypageWithdrawalAction extends WebFrontAction<MypageBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    MypageBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (service.hasNotPaymentOrder(bean.getCustomerCode())) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.REQUESTED_WITHDRAWAL_CUSTOMER_NOT_PAYMENT_ORDER_ERROR));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    CustomerInfo info = service.getCustomer(bean.getCustomerCode());
    Customer customer = info.getCustomer();

    // 顧客が存在しない/退会済みの場合
    if (service.isNotFound(bean.getCustomerCode()) || service.isInactive(bean.getCustomerCode())) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    // 顧客退会処理
    ServiceResult serviceResult = service.withdrawalRequest(bean.getCustomerCode(), customer.getUpdatedDatetime());

    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CustomerServiceErrorContent.EXIST_NOT_PAYMENT_ORDER_ERROR)) {
          addErrorMessage(WebMessage.get(MypageErrorMessage.REQUESTED_WITHDRAWAL_CUSTOMER_NOT_PAYMENT_ORDER_ERROR));
          bean.setDisplayReceivedWithdrawalNotice(false);

          setNextUrl(null);
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        }
      }
    }

    setRequestBean(bean);

    setNextUrl("/app/common/index");
    getSessionContainer().logout();

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
