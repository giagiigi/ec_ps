package jp.co.sint.webshop.service.alipayaddress;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.DIContainer;

public class AlipayAddressManager {

  private AlipayAddressProvider provider;

  private AlipayAddressParameter parameter;

  private LoginInfo loginInfo = ServiceLoginInfo.getInstance();

  public AlipayAddressManager() {
  }

  public AlipayAddressResult fastLogin() {

    // 支付宝快捷登录
    initParam();

    AlipayAddressResult result = provider.payment(parameter);

    return result;
  }

  public AlipayAddressResult find() {

    // 支付宝查询
    initParam();
    
    AlipayAddressResult result = provider.find(parameter);
    
    return result;
  }
  
  public AlipayAddressResult close() {

    // 支付宝关闭
    initParam();
    
    AlipayAddressResult result = provider.close(parameter);
    
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


    
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    PaymentMethodSuite method = service.getPaymentMethod(shopCode,10004L);
    if (method != null && method.getPaymentMethod() != null) {
      parameter.setMerchantId(method.getPaymentMethod().getMerchantId());
      parameter.setSecretKey(method.getPaymentMethod().getSecretKey());
     // parameter.setServiceId("alipay.auth.authorize");
      //parameter.setServiceId("user.logistics.address.query");
      parameter.setServiceId("create_partner_trade_by_buyer");
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

