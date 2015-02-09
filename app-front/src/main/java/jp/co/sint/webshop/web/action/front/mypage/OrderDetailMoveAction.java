package jp.co.sint.webshop.web.action.front.mypage;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.OrderDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030620:注文内容のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailMoveAction extends WebFrontAction<OrderDetailBean> {

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
    // 顧客の存在/退会済みチェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (service.isNotFound(getLoginInfo().getCustomerCode()) || service.isInactive(getLoginInfo().getCustomerCode())) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    String addressNo = "";

    // parameter[0] : アドレス帳番号
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      addressNo = StringUtil.coalesce(parameter[0], "");
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    // アドレス帳番号チェック
    getBean().setAddressNo(addressNo);
    ValidationSummary validateCustomer = BeanValidator.partialValidate(getBean(), "addressNo");
    if (validateCustomer.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (String rs : validateCustomer.getErrorMessages()) {
        logger.debug(rs);
      }
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.OrderDetailMoveAction.0")));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }

    // アドレスの存在チェック
    CustomerAddress address = service.getCustomerAddress(getLoginInfo().getCustomerCode(), Long.parseLong(addressNo));
    if (address == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.OrderDetailMoveAction.0")));
      setRequestBean(getBean());
      return FrontActionResult.RESULT_SUCCESS;
    }

    setNextUrl("/app/mypage/delivery_history/init/" + addressNo);

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/mypage/order_detail/init/" + getBean().getOrderNo(), getSessionContainer());

    return FrontActionResult.RESULT_SUCCESS;
  }
}
