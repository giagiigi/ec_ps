package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.order.OrderHeadline;
import jp.co.sint.webshop.service.order.OrderListSearchCondition;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.OrderHistoryBean;
import jp.co.sint.webshop.web.bean.back.customer.OrderHistoryBean.OrderHistoryDetail;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1030150:注文履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OrderHistoryInitAction extends WebBackAction<OrderHistoryBean> {

  private OrderListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new OrderListSearchCondition();
  }

  protected OrderListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected OrderListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = getLoginInfo().hasPermission(Permission.CUSTOMER_READ)
          && getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
    } else {
      authorization = ((getLoginInfo().isSite() && Permission.CUSTOMER_READ.isGranted(getLoginInfo())) || getLoginInfo().isShop()
          && Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo()))
          && getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP);
    }

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String customerCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(customerCode)) {
        return false;
      }

      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      authorization &= service.isShopCustomer(customerCode, getLoginInfo().getShopCode());
    }

    return authorization;
  }

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

    OrderHistoryBean bean = new OrderHistoryBean();
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    String[] urlParam = getRequestParameter().getPathArgs();
    // 存在チェック
    if (urlParam.length > 0) {
      boolean hasCustomer = true;
      if (cs.isNotFound(urlParam[0]) || cs.isWithdrawed(urlParam[0])) {
        hasCustomer = false;
      }

      if (!hasCustomer) {
        setNextUrl("/app/common/dashboard/init/");
        setRequestBean(getBean());

        return BackActionResult.RESULT_SUCCESS;
      }
    } else {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 顧客情報表示
    CustomerInfo info = cs.getCustomer(urlParam[0]);

    bean.setCustomerCode(info.getCustomer().getCustomerCode());
    bean.setEmail(info.getCustomer().getEmail());
    bean.setLastName(info.getCustomer().getLastName());
    bean.setFirstName(info.getCustomer().getFirstName());
    //modify by os012 20111213 start
    if(info.getAddress()!=null)
    {
	    bean.setPhoneNumber(info.getAddress().getPhoneNumber());
	    // Add by V10-CH start
	    bean.setMobileNumber(info.getAddress().getMobileNumber());
	    // Add by V10-CH end
    }
    //modify by os012 20111213 end
    // 検索条件を取得
    condition = new OrderListSearchCondition();

    // サイト管理者の場合は、全ての受注を参照できる。
    if ((getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE))
        && Permission.CUSTOMER_READ.isGranted(getLoginInfo())) {
      condition.setSiteAdmin(true);
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && ((getLoginInfo().isSite() && Permission.CUSTOMER_READ.isGranted(getLoginInfo())) || getLoginInfo().isShop()
            && Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo()))) {
      condition.setShopCode(getLoginInfo().getShopCode());
    }

    condition.setCustomerCode(urlParam[0]);
    condition.setPaymentStatus("");
    condition.setSearchListSort(OrderHistoryBean.ORDER_DATETIME_DESC);
    condition.setSearchFixedSalesDataFlg(true);
    condition.setSearchDataTransportFlg(true);
    condition = getCondition();
    // 受注ステータスを全て指定
    String[] values = new String[OrderStatus.values().length];
    int i = 0;
    for (OrderStatus status : OrderStatus.values()) {
      values[i] = status.getValue();
      i++;
    }
    condition.setOrderStatus(values);

    // 出荷ステータス:未出荷/一部出荷/全出荷
    condition.setShippingStatusSummary(ShippingStatusSummary.SHIPPED_ALL.getValue(),
        ShippingStatusSummary.IN_PROCESSING.getValue(), ShippingStatusSummary.PARTIAL_SHIPPED.getValue(),
        ShippingStatusSummary.NOT_SHIPPED.getValue());

    // 返品状況:返品なし/一部返品/全返品
    condition.setReturnStatusSummary(new String[] {
        ReturnStatusSummary.NOT_RETURNED.getValue(), ReturnStatusSummary.PARTIAL_RETURNED.getValue(),
        ReturnStatusSummary.RETURNED_ALL.getValue()
    });

    // 受注履歴取得
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    SearchResult<OrderHeadline> result = service.searchOrderList(condition);

    bean.getList().clear();

    if (result != null) {
      // オーバーフローチェック
      prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

      bean.setPagerValue(PagerUtil.createValue(result));

      List<OrderHeadline> orderList = result.getRows();
      List<OrderHistoryDetail> list = bean.getList();

      for (OrderHeadline oh : orderList) {
        OrderHistoryDetail detail = new OrderHistoryDetail();
        detail.setOrderNo(oh.getOrderNo());
        detail.setOrderDatetime(oh.getOrderDatetime());
        if (oh.getPaymentDate() != null) {
          detail.setPaymentDate(oh.getPaymentDate());
        }
        detail.setPaymentMethodName(oh.getPaymentMethodName());
        // 10.1.3 10126 修正 ここから
        // detail.setTotalPrice(oh.getTotalAmount());
        detail.setTotalPrice(NumUtil.toString(
            BigDecimalUtil.subtract(BigDecimalUtil.add(NumUtil.parse(oh.getTotalAmount()), oh.getPaymentCommission()),  NumUtil.parse(oh.getDiscountPrice()))));
        // 10.1.3 10126 修正 ここまで
        detail.setOrderStatus(OrderStatus.fromValue(oh.getOrderStatus()).getName());

        list.add(detail);
      }

      bean.setList(list);
    }

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.OrderHistoryInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103015001";
  }

}
