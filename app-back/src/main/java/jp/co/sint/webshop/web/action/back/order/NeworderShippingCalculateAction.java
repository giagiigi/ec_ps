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
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020130:新規受注(配送先設定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderShippingCalculateAction extends NeworderShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean validation = true;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    for (DeliveryBean deliveryBean : getBean().getDeliveryList()) {
      for (OrderCommodityBean commodityBean : deliveryBean.getOrderCommodityList()) {
        validation &= validateBean(commodityBean);
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
              return false;
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
                return false;
              }
            } else {
              addErrorMessage(commodityBean.getCommodityName() + "限购活动 已购买过个人最大数量 " + dcBean.getCustomerMaxTotalNum() + "个。");
              return false ;
            }
          }
        }
      }
    }

    if (validation) {
      validation &= minStockValidation();
      if (validation) {
        validation &= shippingValidation();
        copyBeanToCashier();
        validation &= numberLimitValidation(getBean());
      }
      if (validation) {
        validation &= discountCouponLimtedValidation();
      }
    }


    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NeworderShippingBean bean = getBean();
    Cashier cashier = bean.getCashier();
    
    isCashOnDeliveryOnly();
    cashier.recomputeShippingCharge();
    super.createCashierFromDisplay();
    //2012-11-23 促销对应 ob update start
    //setRequestBean(createBeanFromCashier());
    recreateOtherGiftList();
    NeworderShippingBean newBean = createBeanFromCashier();
    
    
    setRequestBean(newBean);
    //2012-11-23 促销对应 ob update end
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderShippingCalculateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013003";
  }

}
