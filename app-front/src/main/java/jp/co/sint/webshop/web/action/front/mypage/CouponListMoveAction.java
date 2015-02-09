package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CouponListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponListMoveAction extends WebFrontAction<CouponListBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length < 1) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 顧客存在チェック
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    if (service.isNotFound(customerCode) || service.isInactive(customerCode)) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    String nextParam = "";

    // parameter[0] : 次画面パラメータ
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      nextParam = StringUtil.coalesce(parameter[0], "");
    }

    if (nextParam.equals("order_detail")) {
      String orderNo = "";
      if (parameter.length < 2 || StringUtil.isNullOrEmpty(parameter[1])) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      } else {
        orderNo = parameter[1];
      }

      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      OrderHeader orderHeader = orderService.getOrderHeader(orderNo);
      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.mypage.MypageMoveAction.1")));
        setRequestBean(getBean());
        setNextUrl(null);
        return FrontActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/mypage/order_detail/init/" + orderNo);
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/mypage/coupon_list/init", getSessionContainer());

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
