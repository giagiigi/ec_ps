package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShippingInitAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    Cashier cashier = getBean().getCashier();
    // 10.1.4 10199 修正 ここから
    // if (cashier == null) {
    if (cashier == null || cashier.getShippingList().size() == 0) {
      // 10.1.4 10199 修正 ここまで
      setNextUrl("/app/common/index");
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
    getBean().setMessage(getBean().getCashier().getMessage());
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerCardInfo cardInfo = service.getCustomerCardInfoByCustomerCode(getLoginInfo().getCustomerCode());
    CustomerCardUseInfo cardUseInfo = service.getCustomerCardUseInfoBycustomerCode(getLoginInfo().getCustomerCode());
    CustomerCardInfo cardInfoUnable = service.getCustomerCardInfoByCustomerCodeUnable(getLoginInfo().getCustomerCode());
    GiftCardReturnConfirm confirmPrice = service.getGiftCardReturnConfirm(getLoginInfo().getCustomerCode());
    
    BigDecimal avalibleMoney = cardInfo.getDenomination().add(confirmPrice.getReturnAmount()).subtract(cardUseInfo.getUseAmount()).subtract(cardInfoUnable.getDenomination());
    if (BigDecimalUtil.equals(avalibleMoney, BigDecimal.ZERO)) {
      getBean().setTotalAmount("0.00");
    } else {
      getBean().setTotalAmount(avalibleMoney.toString());
    }
    
    // 发票信息设定
    List<CodeAttribute> commodityNameList = new ArrayList<CodeAttribute>();
    commodityNameList.add(new NameValue(Messages.getString("web.action.front.order.ShippingInitAction.0"), ""));
    for (NameValue invoiceCommodityName : DIContainer.getInvoiceValue().getInvoiceCommodityNameList()) {
      commodityNameList.add(new NameValue(invoiceCommodityName.getName(), invoiceCommodityName.getValue()));
    }
    getBean().setInvoiceCommodityNameList(commodityNameList);
    getBean().setOrderInvoice(createInvoice(getBean().getCashier()));
    // 折扣信息设定
    createDiscount(getBean().getCashier());
    // upd by lc 2012-09-06 start for 重复加载地址脚本导致前端访问过慢
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().setAddressScript(s.createAddressScript());
    // upd by lc 2012-09-06 end
    refreshItems(false, false);
    setRequestBean(createShippingBeanFromCashier());
    // 有会员折扣并且没有个人优惠的场合，默认使用会员折扣
    boolean nextDiscount = false;

    // 支付方式选择初始设定
    if (StringUtil.isNullOrEmpty(getBean().getOrderPayment().getPaymentMethodCode())) {
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      List<OrderHeader> orderHeaderList = orderService.getCustomerUsePaymentMethodList(getLoginInfo().getCustomerCode());
      if (orderHeaderList != null && orderHeaderList.size() > 0) {
        for (OrderHeader orderHeader : orderHeaderList) {
          for (PaymentTypeBase paymentTypeBase : getBean().getOrderPayment().getDisplayPaymentList()) {
            if (paymentTypeBase.getPaymentMethodCode().equals(orderHeader.getPaymentMethodNo().toString())) {
              getBean().getOrderPayment().setPaymentMethodCode(paymentTypeBase.getPaymentMethodCode());
              if(!getClient().equals(ClientType.OTHER)){
                if(getBean().getOrderPayment().getPaymentMethodCode().equals("10005")){
                  getBean().getOrderPayment().setPaymentMethodCode("10004");
                }
              }else{  // 20141031 hdh add 订单记录支付方式为国际信用卡则当前订单支付方式初始化为货到付款(仅英文，日文)
                String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
                if(getBean().getOrderPayment().getPaymentMethodCode().equals("10006") &&
                    (LanguageCode.En_Us.getValue().equals(currentLanguageCode) 
                        ||LanguageCode.Ja_Jp.getValue().equals(currentLanguageCode))){
                  getBean().getOrderPayment().setPaymentMethodCode("10002");
                }
              }
              if (nextDiscount) {
                setNextUrl("/app/order/shipping/payment_confirm/nextDiscount");
              } else {
                setNextUrl("/app/order/shipping/payment_confirm");
              }
              return FrontActionResult.RESULT_SUCCESS;
            }
          }
        }
      } else {
        getBean().getOrderPayment().setPaymentMethodCode(getBean().getOrderPayment().getDisplayPaymentList().get(1).getPaymentMethodCode());
        setNextUrl("/app/order/shipping/payment_confirm");
      }
      
    }
    if (getBean().getAddAddressCheckList() != null && getBean().getAddAddressCheckList().size() > 0){
      if(StringUtil.isNullOrEmpty(getBean().getVerificationBean().getMobileNumber()) && StringUtil.isNullOrEmpty(getBean().getVerificationBean().getPhoneNumber())){
        getBean().getVerificationBean().setMobileNumber(getBean().getAddAddressCheckList().get(0).getMobileNumber());
        getBean().getVerificationBean().setPhoneNumber(getBean().getAddAddressCheckList().get(0).getPhoneNumber());
      }
    }
    return FrontActionResult.RESULT_SUCCESS;
  }

}
