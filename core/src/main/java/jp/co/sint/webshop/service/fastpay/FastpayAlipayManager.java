package jp.co.sint.webshop.service.fastpay;

import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.DIContainer;

public class FastpayAlipayManager {

  private FastpayAlipayProvider provider;

  private FastpayAlipayParameter parameter;

  private LoginInfo loginInfo = ServiceLoginInfo.getInstance();

  public FastpayAlipayManager() {
  }

  public FastpayAlipayResult fastLogin() {

    // 支付宝快捷登录
    initParam();

    FastpayAlipayResult result = provider.payment(parameter);

    return result;
  }

  public FastpayAlipayResult find() {

    // 支付宝查询
    initParam();
    
    FastpayAlipayResult result = provider.find(parameter);
    
    return result;
  }
  
  public FastpayAlipayResult close() {

    // 支付宝关闭
    initParam();
    
    FastpayAlipayResult result = provider.close(parameter);
    
    return result;
  }

  /**
   * 初始化参数
   * 
   * @param container
   */
  private void initParam() {
    if (provider == null) {
      provider = DIContainer.get("FastpayAlipayCard");
    }
    
    if (parameter == null) {
      parameter = provider.createParameterInstance();
    }
    
    /** 店铺编号 */
    String shopCode="00000000";
    parameter.setShopCode(shopCode);


    //支付宝信息取得
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    PaymentMethod method =  service.getAlipayPayMethodInfo();;
    if (method != null) {
      parameter.setMerchantId(method.getMerchantId());
      parameter.setSecretKey(method.getSecretKey());
      parameter.setServiceId("alipay.auth.authorize");
    }
  }
  

  private LoginInfo getLoginInfo() {
    LoginInfo login = loginInfo;
    if (login == null) {
      login = ServiceLoginInfo.getInstance();
    }
    return login;
  }

}

