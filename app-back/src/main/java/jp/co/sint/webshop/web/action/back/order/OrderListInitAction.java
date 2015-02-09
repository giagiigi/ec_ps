package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.ReturnStatusSummary;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean.RegisterPaymentDateBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020210:受注入金管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderListInitAction extends WebBackAction<OrderListBean> {

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {

    OrderListBean bean = new OrderListBean();

    List<String> searchOrderStatusList = new ArrayList<String>();

    searchOrderStatusList.add(OrderStatus.ORDERED.getValue());

    bean.setSearchOrderStatus(searchOrderStatusList);

    List<String> searchShippingStatusList = new ArrayList<String>();
    List<String> searchReturnStatusSummary = new ArrayList<String>();

    // デフォルトチェック
    searchShippingStatusList.add(ShippingStatusSummary.NOT_SHIPPED.getValue());
    searchShippingStatusList.add(ShippingStatusSummary.IN_PROCESSING.getValue());
    searchShippingStatusList.add(ShippingStatusSummary.PARTIAL_SHIPPED.getValue());
    searchShippingStatusList.add(ShippingStatusSummary.SHIPPED_ALL.getValue());
    
    

    searchReturnStatusSummary.add(ReturnStatusSummary.NOT_RETURNED.getValue());
    // add by lc 2012-03-15 start
    bean.setSearchFixedSalesDataFlg(true);
    // add by lc 2012-03-15 end
    bean.setSearchShippingStatusSummary(searchShippingStatusList);
    bean.setSearchReturnStatusSummary(searchReturnStatusSummary);
      
    bean.setSearchPaymentStatus(PaymentStatus.PAID.getValue());

    bean.setOrderByOrderNo(true);

    RegisterPaymentDateBean registerPaymentDateBean = new RegisterPaymentDateBean();
    registerPaymentDateBean.setRegisterPaymentDatetime(DateUtil.getSysdateString());

    bean.setRegisterPaymentDateBean(registerPaymentDateBean);

    List<CodeAttribute> paymentMethodList = new ArrayList<CodeAttribute>();
    paymentMethodList.add(new NameValue("-----", ""));
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    List<PaymentMethod> paymentMethod = service.getAllPaymentMethodList(getLoginInfo().getShopCode());
    for (PaymentMethod payment : paymentMethod) {
      CodeAttribute addPayment = new NameValue(payment.getPaymentMethodName(), payment.getPaymentMethodNo().toString());
      paymentMethodList.add(addPayment);
    }

    bean.setPaymentMethodList(paymentMethodList);

    setRequestBean(bean);
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
    } else {
      authorization = getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP)
          || getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
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

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderListBean bean = (OrderListBean) getRequestBean();

    if (Permission.ORDER_READ_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_READ_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityRead(true);
    }
    if (Permission.ORDER_UPDATE_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityUpdate(true);
    }
    if (Permission.ORDER_MODIFY_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityDelete(true);
    }
    if (Permission.ORDER_DATA_IO_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_DATA_IO_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityIO(true);
    }

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021002";
  }

}
