package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

public class MemberInfoMoveAction extends WebBackAction<MemberInfoBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(targetPage())) {
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String targetPage = targetPage();
    // String customerCode = getBean().getCustomerInfo().getCustomerCode();
    // String customerCode ="0000000000010002";
    // 20111209 lirong add start
    String customerCode = getCustomerCode();
    // 20111209 lirong add end
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    if (targetPage.equals("edit") || targetPage.equals("address") || targetPage.equals("inquiryEdit")) {
      // 会员存在验证
      if (StringUtil.isNullOrEmpty(customerCode) || customerService.isNotFound(customerCode)) {
        setNextUrl("/app/service/member_info/init/delete");
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }

      // 会员已退会时
      if (customerService.isWithdrawed(customerCode)) {
        addErrorMessage(Messages.getString("web.action.back.service.MemberInfoInitAction.1"));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    if (targetPage.equals("edit")) {
      setNextUrl("/app/customer/customer_edit/select/" + customerCode);
    } else if (targetPage.equals("address")) {
      setNextUrl("/app/customer/address_list/init/" + customerCode);
    } else if (targetPage.equals("inquiryEdit")) {
      setNextUrl("/app/service/inquiry_edit/init/" + customerCode);
    } else if (targetPage.equals("order")) {
      // 订单编号存在验证
      OrderHeader orderHeader = orderService.getOrderHeader(targetNo());
      if (orderHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.service.MemberInfoMoveAction.1")));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/order/order_detail/init/" + targetNo());
    } else if (targetPage.equals("shipping")) {
      // 发货编号存在验证
      ShippingHeader shippingHeader = orderService.getShippingHeader(targetNo());
      if (shippingHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.service.MemberInfoMoveAction.2")));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/order/shipping_detail/init/" + targetNo());
    } else if (targetPage.equals("member")) {
      setNextUrl("/app/service/member_info/init");
    } else if (targetPage.equals("inquiry")) {
      setNextUrl("/app/service/inquiry_list/init");
    } else if (targetPage.equals("inquiryDetail")) {
      // 咨询编号存在验证
      InquiryHeader inquiryHeader = customerService.getInquiryHeader(targetNo());
      if (inquiryHeader == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.service.MemberInfoMoveAction.3")));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/service/inquiry_detail/init/" + targetNo());
    } else if (targetPage.equals("new")) {
      setNextUrl("/app/order/neworder_commodity");
      // 20131106 txw add start
    } else if (targetPage.equals("refund")) {
      setNextUrl("/app/service/card_refund/init/" + targetNo());
      // 20131106 txw add end
    } else {
      return BackActionResult.SERVICE_ERROR;
    }

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/service/member_info/history_back/" + getBean().getDisplayHistoryMode(),
        getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 从URL取得迁移画面。<BR>
   * 
   * @return targetPage
   */
  private String targetPage() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  // 20111209 lirong add start
  /**
   * URLから顧客コードを取得します。<BR>
   * 
   * @return customerCode
   */
  private String getCustomerCode() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 1) {
      return tmpArgs[1];
    } else {
      return "";
    }
  }

  // 20111209 lirong add end
  /**
   * 从URL取得订单编号、发货编号。<BR>
   * 
   * @return targetNo
   */
  private String targetNo() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 1) {
      return tmpArgs[1];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.MemberInfoMoveAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109011005";
  }
}
