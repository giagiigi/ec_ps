package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderConfirmBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020160:新規受注(確認)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderConfirmInitAction extends NeworderBaseAction<NeworderConfirmBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    Cashier cashier = getBean().getCashier();
    if (cashier == null) {
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
    // 支払情報が無い場合ダッシュボード画面に遷移
    Cashier cashier = getBean().getCashier();
    if (cashier == null || cashier.getPayment() == null) {
      setNextUrl("/app/common/dashboard/init/");
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
    NeworderConfirmBean bean = getBean();
    Cashier cashier = bean.getCashier();

    //soukai add 2012/01/07 ob start
    bean.setDiscountPrice(BigDecimal.ZERO);
    if (cashier.getDiscountPrice()!=null) {
    	bean.setDiscountPrice(cashier.getDiscountPrice());
    }
    cashier.getPayment().getSelectPayment().setPaymentCommission(cashier.getShippingList().get(0).getDeliveryDateCommssion());
    //soukai add 2012/01/07 ob end
    /*if (cashier.getPayment().getSelectPayment().getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
      // modify by V10-CH start
      // bean.setDisplayGrandTotalPrice(cashier.getGrandTotalPrice() +
      // cashier.getPayment().getSelectPayment().getPaymentCommission());
      bean.setDisplayGrandTotalPrice(cashier.getGrandTotalPrice().add(
          cashier.getPayment().getSelectPayment().getPaymentCommission()));
      // modify by V10-CH end
    } else {
      bean.setDisplayGrandTotalPrice(cashier.getGrandTotalPrice());
    }*/
    bean.setDisplayGrandTotalPrice(cashier.getGrandTotalPrice());
//  modify by V10-CH start
//    BigDecimal paymentPrice = BigDecimalUtil.subtract(bean.getDisplayGrandTotalPrice(), new BigDecimal(bean.getCashier().getUsePoint())
//        .divide(DIContainer.getWebshopConfig().getRmbPointRate(), PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR));
    BigDecimal paymentPrice = PointUtil.getTotalPyamentPrice(BigDecimalUtil.subtract(cashier.getGrandTotalPrice(), NumUtil.parse(cashier.getUseCoupon())), NumUtil.parse(bean.getCashier().getUsePoint()));
    if (BigDecimalUtil.isAbove(BigDecimal.ZERO, paymentPrice)) {
      bean.setPaymentPrice(BigDecimal.ZERO);
    } else {
      bean.setPaymentPrice(paymentPrice);
    }
  //soukai add 2012/01/07 ob start
    bean.setDisplayGrandTotalPrice(bean.getDisplayGrandTotalPrice().add(cashier.getTotalDeliveryDateCommssion()));
    bean.setPaymentPrice(bean.getDisplayGrandTotalPrice().subtract(bean.getDiscountPrice()));
  //soukai add 2012/01/07 ob end
//  modify by V10-CH start
    if (cashier.getPayment().getSelectPayment().getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
      bean.setNotPointInFull(false);
    } else {
      bean.setNotPointInFull(true);
    }
    if (cashier.isUsablePoint()) {
      //soukai update 2012/01/05 ob start
      //bean.setDisplayPoint(new BigDecimal(cashier.getUsePoint()));
    	bean.setDisplayPoint(BigDecimal.ZERO);
      //soukai update 2012/01/05 ob end
    } else {
      bean.setDisplayPoint(BigDecimal.ZERO);
    }
    
    //soukai add 2012/01/07 ob start
    //发票信息获取
    bean.setOrderInvoice(super.createInvoice(cashier));
//    OrderContainer order = CartUtil.createOrderContainer(cashier);
//    bean.setDeliveryCompanyName(order.getShippings().get(0).getShippingHeader().getDeliveryCompanyName());
    bean.setDeliveryCompanyName(createCompanyName(cashier));
    //soukai add 2012/01/07 ob end
    setRequestBean(bean);

    addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));
    return BackActionResult.RESULT_SUCCESS;
  }
  
  private String createCompanyName(Cashier cashier){
    DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
    DeliveryCompany deliveryCompany = dao.load(cashier.getDelivery().getDeliveryCompanyCode());
    
    return deliveryCompany.getDeliveryCompanyName();
  }
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderConfirmInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102016001";
  }

}
