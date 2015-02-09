package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.CouponFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.AddressBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

import org.apache.log4j.Logger;

/**
 * U1020120:新規受注（顧客選択）のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerMoveAction extends NeworderCustomerBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();

    String move = getPathInfo(0);
    if (move.equals("shipping") || move.equals("customer")) {
      if (StringUtil.isNullOrEmpty(move)) {
        auth = false;
      } else if (move.equals("shipping") && StringUtil.isNullOrEmpty(getPathInfo(1))) {
        auth = false;
      }
    } else {
      auth = false;
    }
    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getCart().getItemCount() < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.NeworderCustomerMoveAction.0")));
      return false;
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
    String move = getPathInfo(0);
    if (move.equals("customer")) {
      setNextUrl("/app/order/neworder_customer_register/init/");
      DisplayTransition.add(getBean(), "/app/order/neworder_customer/init/back", getSessionContainer());
    } else if (move.equals("shipping")) {
      String customerCode = getPathInfo(1);
      CustomerService custoemrSvc = ServiceLocator.getCustomerService(getLoginInfo());
      CustomerInfo customerInfo = custoemrSvc.getCustomer(customerCode);

      if (customerInfo !=null && customerInfo.getAddress()==null) {
    	  setNextUrl("/app/order/neworder_customer_register/init/");
          DisplayTransition.add(getBean(), "/app/order/neworder_customer/init/back", getSessionContainer());
    	  setNextUrl("/app/order/address/init/");
          AddressBean addressBean = new AddressBean();
          addressBean.setCustomerCode(customerInfo.getCustomer().getCustomerCode());
          setRequestBean(addressBean);
          return BackActionResult.RESULT_SUCCESS;
      }
      Cashier cashier = null;
      String cashierShopCode = this.getLoginInfo().getShopCode();
      if (getCart().get().size() > 0) {
        if (this.getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
          cashierShopCode = getCart().get().get(0).getShopCode();
        }
        cashier = CartUtil.createCashier(getCart(), cashierShopCode, customerInfo);
      } else if (getCart().getReserve().size() > 0) {
        CartItem item = getCart().getReserve().get(0);
        if (this.getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
          cashierShopCode = item.getShopCode();
        }
        cashier = CartUtil.createCashier(getCart(), cashierShopCode, customerInfo, new Sku(item.getShopCode(), item.getSkuCode()));
      } else {
        // 不正アクセスとしてログインへ飛ばす
        setNextUrl("/app/common/login");
        Logger logger = Logger.getLogger(this.getClass());
        logger.error(Messages.log("web.action.back.order.NeworderCustomerMoveAction.1"));
        return BackActionResult.RESULT_SUCCESS;
      }

      // ポイントの使用可非を取得
      SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());
      PointRule pointRule = siteService.getPointRule();
      PointFunctionEnabledFlg pointFlg = PointFunctionEnabledFlg.fromValue(pointRule.getPointFunctionEnabledFlg());
      if (pointFlg == PointFunctionEnabledFlg.ENABLED) {
        cashier.setUsablePoint(true);
      } else {
        cashier.setUsablePoint(false);
      }
      
      //クーポンの使用可否を取得
      CouponRule couponRule = siteService.getCouponRule();
      CouponFunctionEnabledFlg couponFlg = CouponFunctionEnabledFlg.fromValue(couponRule.getCouponFunctionEnabledFlg());
      if (couponFlg == CouponFunctionEnabledFlg.ENABLED) {
        cashier.setUsableCoupon(true);
      } else {
        cashier.setUsableCoupon(false);
      }

      NeworderShippingBean nextBean = new NeworderShippingBean();
      nextBean.setCashier(cashier);
      setRequestBean(nextBean);

      setNextUrl("/app/order/neworder_shipping/init/");
      DisplayTransition.add(getBean(), "/app/order/neworder_customer/init/back", getSessionContainer());
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCustomerMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102012002";
  }

}
