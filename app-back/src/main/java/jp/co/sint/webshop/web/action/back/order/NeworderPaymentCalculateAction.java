package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderPaymentBean;
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
public class NeworderPaymentCalculateAction extends NeworderPaymentBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    valid = validateBean(getBean().getUsePointBean());
    if (valid) {
      BigDecimal usePoint = NumUtil.parse(getBean().getUsePointBean().getUsePoint());
      if (!ValidatorUtil.moreThanOrEquals(NumUtil.parse(getBean().getUsePointBean().getUsePoint()), getBean().getUsePointBean()
          .getRestPoint())) {
        BigDecimal smallUsePoint = DIContainer.getWebshopConfig().getRmbPointRate();
        if (BigDecimalUtil.isRemainder(usePoint, smallUsePoint)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.POINT_SMALL_NOT_ERROR, String.valueOf(smallUsePoint)));
          valid = false;
        }
      }
    }
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NeworderPaymentBean bean = getBean();
    UsePointBean pointInfo = bean.getUsePointBean();
    // modify V10-CN start
    if (ValidatorUtil.moreThan(NumUtil.parse(pointInfo.getUsePoint()), pointInfo.getRestPoint())) {
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveRestPoint(pointInfo.getRestPoint()));
    }
    BigDecimal totalPaymentPrice =  BigDecimalUtil.subtract(bean.getTotalPrice(), NumUtil.parse(bean.getCouponTotalPrice()));
    BigDecimal rmbPointRate = DIContainer.getWebshopConfig().getRmbPointRate();
    if (BigDecimalUtil.isAbove(NumUtil.parse(pointInfo.getUsePoint()), totalPaymentPrice.multiply(rmbPointRate).setScale(
        PointUtil.getAcquiredPointScale(), RoundingMode.CEILING))) {
      addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveTotalPricePoint(totalPaymentPrice));
    }
    // modify V10-CN end
    createCashierFromDisplay();

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderPaymentCalculateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102015001";
  }

}
