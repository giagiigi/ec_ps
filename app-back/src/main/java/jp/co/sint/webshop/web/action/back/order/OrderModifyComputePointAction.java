package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.PointBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyComputePointAction extends OrderModifyBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    if (!isChangeAbleOrder()) {
      return false;
    }

    PointBean pointInfo = getBean().getPointInfo();
    boolean valid = validateBean(pointInfo);

    // 利用ポイントが使用可能ポイントより多い場合エラー
    if (valid) {
      if (pointInfo.isShort()) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.OVER_USABLE_POINT));
        valid = false;
      } else {
        if (!ValidatorUtil.moreThan(NumUtil.parse(pointInfo.getUsePoint()), NumUtil.parse(pointInfo
            .getRestPoint()))) {
          BigDecimal smallUsePoint = DIContainer.getWebshopConfig().getRmbPointRate();
          if (BigDecimalUtil.isRemainder(NumUtil.parse(pointInfo.getUsePoint()), smallUsePoint)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.POINT_SMALL_NOT_ERROR, String.valueOf(smallUsePoint)));
            valid = false;
          }
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
    recomputePoint(getBean());
    recomputePaymentCommission(getBean());
    recomputePrice(getBean());

    numberLimitValidation(createOrderContainer(getBean()));

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyComputePointAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023004";
  }

}
