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
import jp.co.sint.webshop.web.bean.front.order.PaymentBean.PointInfoBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020130:お支払い方法選択のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentUsePointAction extends PaymentBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // modified by zhanghaibin start 2009-01-12
    boolean valid = true;
    PointInfoBean pointInfo = getBean().getPointInfo();
    valid = true;//valid & validateBean(pointInfo);
    if (valid) {
      BigDecimal usePoint = NumUtil.parse(pointInfo.getUsePoint());
      BigDecimal total = BigDecimalUtil.addAll(pointInfo.getTotalCommodityPrice(), pointInfo.getTotalGiftPrice(), pointInfo
          .getTotalShippingPrice()).subtract(pointInfo.getCouponTotalPrice());
      BigDecimal restPoint = pointInfo.getRestPoint();
      BigDecimal rmbPointRate = DIContainer.getWebshopConfig().getRmbPointRate();
      if (ValidatorUtil.moreThanOrEquals(restPoint, BigDecimalUtil.multiply(BigDecimalUtil.multiply(total, new BigDecimal(
          DIContainer.getWebshopConfig().getPointMultiple())), DIContainer.getWebshopConfig().getRmbPointRate()))) {
        usePoint = BigDecimalUtil.multiply(total, rmbPointRate).setScale(PointUtil.getAcquiredPointScale(), RoundingMode.CEILING);
        pointInfo.setUsePoint(NumUtil.toString(usePoint));
      }
      if (BigDecimalUtil.isRemainder(usePoint, rmbPointRate)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.POINT_SMALL_NOT_ERROR, Messages
            .getString("web.action.front.order.PaymentUsePointAction.0"), String.valueOf(rmbPointRate)));
        valid = false;
      }
    }
    return valid;
    // modified by zhanghaibin end 2009-01-12
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PointInfoBean pointInfo = getBean().getPointInfo();
    BigDecimal usePoint = BigDecimal.ZERO;
    BigDecimal total = BigDecimalUtil.addAll(pointInfo.getTotalCommodityPrice(), pointInfo.getTotalGiftPrice(), pointInfo
        .getTotalShippingPrice()).subtract(pointInfo.getCouponTotalPrice());
    if (BigDecimalUtil.isAbove(BigDecimal.ZERO, total)) {
      total = BigDecimal.ZERO;
    }
    // SiteManagementService service =
    // ServiceLocator.getSiteManagementService(getLoginInfo());
    // PointRule rule = service.getPointRule();
    // Long smallUsePoint= rule.getSmallUsePoint().longValue();
    BigDecimal restPoint = pointInfo.getRestPoint();
    BigDecimal rmbPointRate = DIContainer.getWebshopConfig().getRmbPointRate();
    if (BigDecimalUtil.isAboveOrEquals(restPoint, BigDecimalUtil.multiply(total, rmbPointRate))) {
      usePoint = BigDecimalUtil.multiply(total, rmbPointRate).setScale(PointUtil.getAcquiredPointScale(), RoundingMode.CEILING);
      addInformationMessage(WebMessage.get(OrderErrorMessage.POINT_IN_FULL));
    } else {
      //restPoint = restPoint.divide(rmbPointRate, PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR).multiply(rmbPointRate).setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
      usePoint = restPoint.setScale(PointUtil.getAcquiredPointScale(), RoundingMode.FLOOR);
    }

    pointInfo.setUsePoint(NumUtil.toString(usePoint));
    createCashierFromDisplay();

    setRequestBean(getBean());

    return FrontActionResult.RESULT_SUCCESS;
  }

}
