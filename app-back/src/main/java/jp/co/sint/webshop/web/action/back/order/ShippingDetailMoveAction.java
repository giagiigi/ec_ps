package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.ShippingDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1020420:出荷管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingDetailMoveAction extends WebBackAction<ShippingDetailBean> {

  private String orderNo;
  private String shippingNo;
  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = false;

    ShippingDetailBean reqBean = getBean();
    String[] params = getRequestParameter().getPathArgs();

    if (params.length > 0) {
      orderNo = params[0];
      if (params.length > 1) {
        shippingNo = params[1];
      }
      reqBean.getOrderHeader().setOrderNo(orderNo);
      result = validateItems(reqBean.getOrderHeader(), "orderNo");
    } else {
      orderNo = "";
      shippingNo = "";
      result = false;
    }

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShippingDetailBean reqBean = getBean();

    DisplayTransition.add(reqBean, "/app/order/shipping_detail/init/"+ shippingNo, getSessionContainer());
    setNextUrl("/app/order/order_detail/init/" + orderNo);
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    ShippingDetailBean bean = (ShippingDetailBean) getRequestBean();

    if (DataTransportStatus.fromValue(bean.getOrderHeader().getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
      addInformationMessage(WebMessage.get(InformationMessage.DATA_TRANSPORTED));
    }
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingDetailMoveAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102042002";
  }

}
