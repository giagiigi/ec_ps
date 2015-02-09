package jp.co.sint.webshop.service.alipay;

 
import java.math.BigDecimal;

import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;

public class AlipayManager {

  private PaymentAlipayProvider provider;

  private PaymentAlipayParameter parameter;

  private LoginInfo loginInfo = ServiceLoginInfo.getInstance();

  public AlipayManager() {
  }

  public PaymentAlipayResult payment(OrderContainer container) {

    // 支付宝支付
    initParam(container);

    PaymentAlipayResult result = provider.payment(parameter);

    return result;
  }

  public PaymentAlipayResult find(OrderContainer container) {

    // 支付宝查询
    initParam(container);
    
    PaymentAlipayResult result = provider.find(parameter);
    
    return result;
  }
  
  public PaymentAlipayResult close(OrderContainer container) {

    // 支付宝关闭
    initParam(container);
    
    PaymentAlipayResult result = provider.close(parameter);
    
    return result;
  }

  /**
   * 初始化参数
   * 
   * @param container
   */
  private void initParam(OrderContainer orderContainer) {
    if (provider == null) {
      provider = DIContainer.get("AlipayCard");
    }
    
    if (parameter == null) {
      parameter = provider.createParameterInstance();
    }
    
    /** 店铺编号 */
    parameter.setShopCode(orderContainer.getOrderHeader().getShopCode());

    /** 订单编号 */
    parameter.setOrderId(orderContainer.getOrderHeader().getOrderNo());

    /** 支付金额 */
    // 20120202 shen update start
    // parameter.setAmount(orderContainer.getTotalAmount());
    BigDecimal totalAmount = BigDecimalUtil.add(orderContainer.getTotalAmount(), orderContainer.getOrderHeader().getPaymentCommission());
    BigDecimal discountPrice = NumUtil.coalesce(orderContainer.getOrderHeader().getDiscountPrice(), BigDecimal.ZERO);
    BigDecimal giftCardUsePrice = NumUtil.coalesce(orderContainer.getOrderHeader().getGiftCardUsePrice(), BigDecimal.ZERO);
    totalAmount = BigDecimalUtil.subtract(totalAmount, discountPrice);
    totalAmount = BigDecimalUtil.subtract(totalAmount, giftCardUsePrice);
    parameter.setAmount(totalAmount);
    // 20120202 shen update end

    /** 商品描述 */
    String commodityNames = "";
    int commodityCount = 0;
    for (int i =0;i<orderContainer.getOrderDetails().size();i++){
    	
    //  if (CommodityType.COMMODITY_SIMPLE.longValue().equals(orderContainer.getOrderDetails().get(i).getCommodityType())){
        commodityCount = commodityCount + 1;
        if (commodityCount > 1){
          commodityNames = commodityNames + "..等";
          break;
        }
        commodityNames = orderContainer.getOrderDetails().get(i).getCommodityName();
     // }
    }
    parameter.setCommodityNames(commodityNames);
    
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    PaymentMethodSuite method = service.getPaymentMethod(orderContainer.getOrderHeader().getShopCode(), orderContainer
        .getOrderHeader().getPaymentMethodNo());
    if (method != null && method.getPaymentMethod() != null) {
      parameter.setMerchantId(method.getPaymentMethod().getMerchantId());
      parameter.setSecretKey(method.getPaymentMethod().getSecretKey());
      parameter.setServiceId(method.getPaymentMethod().getServiceId());
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
