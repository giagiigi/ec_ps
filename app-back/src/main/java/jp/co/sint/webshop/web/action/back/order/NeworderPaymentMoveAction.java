package jp.co.sint.webshop.web.action.back.order;

import java.math.RoundingMode;
import java.util.List;

import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeBase;
import jp.co.sint.webshop.web.bean.PaymentMethodBean.PaymentTypeCvs;
import jp.co.sint.webshop.web.bean.back.order.NeworderConfirmBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean.UsePointBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020150:新規受注(決済方法指定)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderPaymentMoveAction extends NeworderPaymentBaseAction {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    String move = getPathInfo(0);
    if (move.equals("confirm")) {
      // modified by zhanghaibin start 2010-05-19
      if (BigDecimalUtil.isAbove(NumUtil.parse(getBean().getUsePointBean().getUsePoint()), BigDecimalUtil.multiply(
          getBean().getTotalPrice(), DIContainer.getWebshopConfig().getRmbPointRate()).setScale(PointUtil.getAcquiredPointScale(),
          RoundingMode.CEILING))) {
        addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
        getBean().getUsePointBean().setUsePoint(PointUtil.getUsePointWhenUsePointAboveTotalPricePoint(getBean().getTotalPrice()));
      }
      // modified by zhanghaibin end 2010-05-19
    }
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = super.authorize();
    auth &= StringUtil.hasValue(getPathInfo(0));
    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String move = getPathInfo(0);
    if (move.equals("shipping")) {
      return true;
    } else {
      UsePointBean pointInfo = getBean().getUsePointBean();
      boolean valid = true;
      valid &= validateBean(pointInfo);
      List<String> errors = PaymentSupporterFactory.createPaymentSuppoerter().validatePayment(getBean().getPayment());
      for (String error : errors) {
        addErrorMessage(error);
        valid &= false;
      }
      // modified by zhanghaibin start 2010-05-19
      if (BigDecimalUtil.isAbove(NumUtil.parse(pointInfo.getUsePoint()), pointInfo.getRestPoint())) {
        pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveRestPoint(pointInfo.getRestPoint()));
      }
      if (BigDecimalUtil.isAbove(NumUtil.parse(pointInfo.getUsePoint()), BigDecimalUtil.multiply(getBean().getTotalPrice(),
          DIContainer.getWebshopConfig().getRmbPointRate()).setScale(PointUtil.getAcquiredPointScale(), RoundingMode.HALF_UP))) {
        addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
        pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveTotalPricePoint(getBean().getTotalPrice()));
      }
      // modified by zhanghaibin end 2010-05-19
      valid &= validateBean(getBean());
      // コンビニ決済だった場合、受取人名カナがあわせて15文字以上だった場合エラー
      for (PaymentTypeBase payment : getBean().getPayment().getDisplayPaymentList()) {
        if (payment.isCvsPayment()) {
          PaymentTypeCvs cvsPayment = (PaymentTypeCvs) payment;
          if ((cvsPayment.getFirstNameKana() + cvsPayment.getLastNameKana()).length() > 15) {
            addErrorMessage(WebMessage.get(ActionErrorMessage.STRING_OVER, Messages
                .getString("web.action.back.order.NeworderPaymentMoveAction.0"), "15"));
            valid &= false;
          }
        }
        // 各支払方法の上限金額を超えていないかどうかチェック
        if (payment.getPaymentMethodCode().equals(getBean().getPayment().getPaymentMethodCode())) {
          if (!payment.canAccept(getBean().getPaymentPrice())) {
            String max = NumUtil.formatCurrency(payment.getMaximum());
            addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_AMOUNT_OVER, payment.getPaymentMethodName(), max));
            valid &= false;
          }
        }
      }

      return valid;
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
      NeworderShippingBean bean = new NeworderShippingBean();
      bean.setCashier(getBean().getCashier());
      setRequestBean(bean);
      setNextUrl("/app/order/neworder_shipping");
    } else if (move.equals("confirm")) {
      // confirmに対してcasheirを設定する
      NeworderConfirmBean bean = new NeworderConfirmBean();
      bean.setCashier(getBean().getCashier());

      if (numberLimitValidation(getBean())) {
        setRequestBean(bean);
        setNextUrl("/app/order/neworder_confirm");
      } else {
        setRequestBean(getBean());
      }
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
    return Messages.getString("web.action.back.order.NeworderPaymentMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102015003";
  }

}
