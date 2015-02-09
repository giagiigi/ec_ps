package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean;
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.PointInfoBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020130:お支払い方法選択のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentComputeAction extends PaymentBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // modified by zhanghaibin start 2010-05-18
    boolean valid = true;
    PointInfoBean pointInfo = getBean().getPointInfo();
    valid = validateBean(pointInfo);
    if (valid) {
      BigDecimal usePoint = NumUtil.parse(pointInfo.getUsePoint());
      if (!ValidatorUtil.moreThanOrEquals(NumUtil.parse(pointInfo.getUsePoint()), pointInfo.getRestPoint())) {
        BigDecimal smallUsePoint = DIContainer.getWebshopConfig().getRmbPointRate();
        if (BigDecimalUtil.isRemainder(usePoint, smallUsePoint)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.POINT_SMALL_NOT_ERROR, Messages
              .getString("web.action.front.order.PaymentComputeAction.0"), String.valueOf(smallUsePoint)));
          valid = false;
        }
      }
    }
    return valid;
    // modified by zhanghaibin end 2010-05-18
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PaymentBean bean = getBean();
    PointInfoBean pointInfo = getBean().getPointInfo();

    // modified by zhanghaibin start 2010-05-18
    if (ValidatorUtil.moreThan(NumUtil.parse(pointInfo.getUsePoint()), pointInfo.getRestPoint())) {
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveRestPoint(pointInfo.getRestPoint()));
    }
    BigDecimal total = BigDecimalUtil.addAll(pointInfo.getTotalCommodityPrice(), pointInfo.getTotalGiftPrice(), pointInfo
        .getTotalShippingPrice()).subtract(pointInfo.getCouponTotalPrice());
    if (BigDecimalUtil.isAbove(NumUtil.parse(pointInfo.getUsePoint()), total.multiply(
        DIContainer.getWebshopConfig().getRmbPointRate()).setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR))) {
      addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
      pointInfo.setUsePoint(PointUtil.getUsePointWhenUsePointAboveTotalPricePoint(total));
    }
    // modified by zhanghaibin end 2010-05-18

    createCashierFromDisplay();

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

}
