package jp.co.sint.webshop.web.action.front.mypage;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.PointHistoryBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030710:ポイント履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointHistoryMoveAction extends WebFrontAction<PointHistoryBean> {

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

    String orderNo = "";
    String nextParam = "";

    // parameter[0] : 次画面パラメータ , parameter[1] : 受注番号
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      nextParam = StringUtil.coalesce(parameter[0], "");
      if (parameter.length > 1) {
        orderNo = StringUtil.coalesce(parameter[1], "");
      }
    }

    // 次画面URL設定
    if (nextParam.equals("order")) {
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      if (StringUtil.isNullOrEmpty(orderNo)) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      } else {
        getBean().setOrderNo(orderNo);
        ValidationSummary validateCustomer = BeanValidator.partialValidate(getBean(), "orderNo");
        if (validateCustomer.hasError()) {
          Logger logger = Logger.getLogger(this.getClass());
          for (String rs : validateCustomer.getErrorMessages()) {
            logger.debug(rs);
          }
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.PointHistoryMoveAction.1")));
          setRequestBean(getBean());
          setNextUrl(null);
          return FrontActionResult.RESULT_SUCCESS;
        }

        OrderContainer orderContainer = orderService.getOrder(orderNo);
        if (orderContainer.getOrderHeader() == null
            || !getLoginInfo().getCustomerCode().equals(orderContainer.getOrderHeader().getCustomerCode())) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages.getString("web.action.front.mypage.PointHistoryMoveAction.1")));
        } else {
          setNextUrl("/app/mypage/order_detail/init/" + orderNo);
        }
      }
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    // 前画面情報設定
    DisplayTransition.add(null, "/app/mypage/point_history/init", getSessionContainer());

    setRequestBean(getBean());
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
