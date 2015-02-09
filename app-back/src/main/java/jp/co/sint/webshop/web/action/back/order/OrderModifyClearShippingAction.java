package jp.co.sint.webshop.web.action.back.order;

import java.util.List;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class OrderModifyClearShippingAction extends OrderModifyBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = false;
    if (getPathArgsLength() < 3) {
      // URLPathのサイズチェック
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else {
      // クリア配送先情報存在チェック
      ShippingHeaderBean detail = getBean().getShippingHeaderBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode());
      if (detail == null) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      } else {
        // 配送先がなくなる削除は不可
        if (getBean().getShippingHeaderList().size() <= 1) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DELETE_ALL_DELIVERY));
          return false;
        }
        valid = true;
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
    OrderModifyBean bean = getBean();

    ShippingHeaderBean header = bean.getShippingHeaderBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode());

    List<ShippingHeaderBean> headerList = bean.getShippingHeaderList();
    headerList.remove(header);

    // 配送先が1箇所の場合削除ボタン非表示
    if (headerList.size() == 1) {
      headerList.get(0).setDeletable(false);
    }

    recomputePrice(bean);
    recomputePoint(bean);
    recomputePaymentCommission(bean);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private int getPathArgsLength() {
    return getRequestParameter().getPathArgs().length;
  }

  private String getPathArg(int index) {
    if (getPathArgsLength() > index) {
      return getRequestParameter().getPathArgs()[index];
    }
    return "";
  }

  private String getPathShopCode() {
    return getPathArg(0);
  }

  private String getPathAddressNo() {
    return getPathArg(1);
  }

  private String getPathDeliveryTypeCode() {
    return getPathArg(2);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyClearShippingAction.0");
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
