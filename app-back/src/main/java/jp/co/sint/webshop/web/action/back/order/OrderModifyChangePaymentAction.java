package jp.co.sint.webshop.web.action.back.order;


import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyChangePaymentAction extends OrderModifyBaseAction {

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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderModifyBean bean = getBean();
    setDeliveryDatetimeInfo(bean);
    recomputePrice(bean);
    recomputePoint(bean);
    recomputePaymentCommission(bean);
    recomputePrice(bean);
    bean.setOperationMode("payment");
    bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    bean.setDiscountTypeList(createDiscountTypeList());
    bean.setPersonalCouponList(createPersonalCouponList());
    createDeliveryToBean(bean);
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
    return "接受订货修正支付设定处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023010";
  }

}
