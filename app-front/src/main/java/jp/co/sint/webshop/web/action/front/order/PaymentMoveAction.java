package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeCvs;
import jp.co.sint.webshop.web.bean.front.order.ConfirmBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.PointInfoBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020130:お支払い方法選択のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentMoveAction extends PaymentBaseAction {

  /**
   * 初期処理を実行します
   */
  @Override 
  public void init() {
    String move = getPathInfo(0);
    if (move.equals("confirm")) {
      PaymentBean bean = getBean();
      setBean(bean);
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    // URL不正チェック
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length < 1) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else if (tmp[0].equals("shipping")) {
      return true;
    }
    valid &= validateBean(getBean().getPointInfo());
    List<String> errorList = PaymentSupporterFactory.createPaymentSuppoerter().validatePayment(getBean().getOrderPayment());
    for (String error : errorList) {
      addErrorMessage(error);
      valid = false;
    }

    PointInfoBean pointInfo = getBean().getPointInfo();

    if (ValidatorUtil.moreThan(NumUtil.parse(pointInfo.getUsePoint()), pointInfo.getRestPoint())) {
      pointInfo.setUsePoint(NumUtil.toString(pointInfo.getRestPoint()));
      valid = false;
    }
    BigDecimal total = BigDecimalUtil.addAll(pointInfo.getTotalCommodityPrice(), pointInfo.getTotalGiftPrice(), pointInfo
        .getTotalShippingPrice());
    // modified by zhanghaibin start 2010-05-19
    if (BigDecimalUtil.isAbove(NumUtil.parse(pointInfo.getUsePoint()), BigDecimalUtil.multiply(total.setScale(0,
        BigDecimal.ROUND_UP), DIContainer.getWebshopConfig().getRmbPointRate()))) {
      addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
      pointInfo.setUsePoint(NumUtil.toString(BigDecimalUtil.multiply(total, DIContainer.getWebshopConfig().getRmbPointRate())));
      valid = false;
    }
    // modified by zhanghaibin end 2010-05-19
    // コンビニ決済だった場合、受取人名カナがあわせて15文字以上だった場合エラー
    for (PaymentTypeBase payment : getBean().getOrderPayment().getDisplayPaymentList()) {
      if (payment.isCvsPayment()) {
        PaymentTypeCvs cvsPayment = (PaymentTypeCvs) payment;
        if ((cvsPayment.getFirstNameKana() + cvsPayment.getLastNameKana()).length() > 15) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.STRING_OVER, Messages
              .getString("web.action.front.order.PaymentMoveAction.0"), "15"));
          valid = false;
        }
      }

      // 各お支払い方法の上限金額を超えていないかどうかチェック
      if (payment.getPaymentMethodCode().equals(getBean().getOrderPayment().getPaymentMethodCode())) {
        if (!payment.canAccept(getBean().getPointInfo().getTotalAllPrice())) {
          String max = NumUtil.formatCurrency(payment.getMaximum());
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_AMOUNT_OVER, payment.getPaymentMethodName(), max));
          valid = false;
        }
      }
    }

    return valid;
  }

  /**
   * 限界値のValitionチェックを行います。<br>
   */
  public boolean numberLimitValidation() {
    OrderContainer order = CartUtil.createOrderContainer(getBean().getCashier());
    ValidationSummary summary = DIContainer.getNumberLimitPolicy().isCorrect(order);
    if (summary.hasError()) {
      for (String error : summary.getErrorMessages()) {
        addErrorMessage(error);
      }
      return false;
    } else {
      return true;
    }

  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // 画面のポイント情報を設定
    createCashierFromDisplay();

    String move = getPathInfo(0);
    if (move.equals("shipping")) {
      ShippingBean bean = new ShippingBean();
      bean.setCashier(getBean().getCashier());
      setRequestBean(bean);
      setNextUrl("/app/order/shipping");
    } else if (move.equals("confirm")) {
      // confirmに対してcasheirを設定する
      ConfirmBean bean = new ConfirmBean();
      bean.setCashier(getBean().getCashier());
      if (numberLimitValidation()) {
        setRequestBean(bean);
        setNextUrl("/app/order/confirm");
      } else {
        setRequestBean(getBean());
      }
    } else {
      setNextUrl("/app/common/access_failed_error");
      return FrontActionResult.RESULT_SUCCESS;
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

}
