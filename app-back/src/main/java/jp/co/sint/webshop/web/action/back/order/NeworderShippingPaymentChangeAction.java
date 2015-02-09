package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.DeliveryBean.OrderCommodityBean;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

public class NeworderShippingPaymentChangeAction extends NeworderShippingBaseAction {

  @Override
  public boolean validate() {
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    for (DeliveryBean deliveryBean : getBean().getDeliveryList()) {
      for (OrderCommodityBean commodityBean : deliveryBean.getOrderCommodityList()) {
        if (commodityBean.getIsDiscountCommodity().equals("true")){
          //历史所有客户购买总数
          Long siteTotalAmount = catalogService.getHistoryBuyAmountTotal(commodityBean.getSkuCode());
          if (siteTotalAmount == null){
            siteTotalAmount = 0L;
          }
          String customerCode = getBean().getCustomerCode();
          if (StringUtil.hasValue(customerCode)) {
            DiscountCommodity dcBean = catalogService.getDiscountCommodityByCommodityCode(commodityBean.getSkuCode());
            //限购商品剩余可购买数量
            Long avalibleAmountTotal = dcBean.getSiteMaxTotalNum() - siteTotalAmount;
            if (avalibleAmountTotal <= 0L){
              addErrorMessage(commodityBean.getCommodityName() + "限购活动可购买数量为0。" );
            }
            Long historyNum = catalogService.getHistoryBuyAmount(commodityBean.getSkuCode(), customerCode);
            if (historyNum == null){
              historyNum = 0L;
            }
            if (dcBean.getCustomerMaxTotalNum() > historyNum){
              Long num = dcBean.getCustomerMaxTotalNum() - historyNum;
              if (num > avalibleAmountTotal){
                num = avalibleAmountTotal;
              }
              if (Long.parseLong(commodityBean.getPurchasingAmount()) > num ){
                addErrorMessage(commodityBean.getCommodityName() + "限购活动最多可购买数" + num + "个。" );
              }
            } else {
              addErrorMessage(commodityBean.getCommodityName() + "限购活动 已购买过个人最大数量 " + dcBean.getCustomerMaxTotalNum() + "个。");
            }
          }
        }
      }
    }
	  return true;
  }

  @Override
  public WebActionResult callService() {
   
    super.copyBeanToCashier();
    super.createCashierFromDisplay();
    Cashier cashier = getBean().getCashier();
    PaymentSupporter supporter = PaymentSupporterFactory.createPaymentSuppoerter();
    // 支付方式设定
    cashier.setPayment(supporter.createCashierPayment(getBean().getOrderPayment()));
    cashier.recomputeShippingCharge();
    NeworderShippingBean bean = super.createBeanFromCashier();
    createOutCardPrice();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   *
   * @return Action名
   */
  public String getActionName() {
    return "新接受订货登录(发送处设定)支付方式指定";
  }

  /**
   * オペレーションコードの取得
   *
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013008";
  }
}
