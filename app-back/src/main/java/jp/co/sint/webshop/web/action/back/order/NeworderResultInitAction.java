package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderResultBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020170:新規受注(完了画面)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderResultInitAction extends NeworderBaseAction<NeworderResultBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NeworderResultBean bean = getBean();

    Cart cart = getCart();

    // カートの中身が空ならカートをクリアする
    if (cart.getItemCount() == 0) {
      cart.clear();
      bean.setDisplayContinue(false);
    } else {
      bean.setDisplayContinue(true);
    }

    // 権限があれば、受注明細へのリンクを表示する
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      bean.setDisplayOrderLink(getLoginInfo().hasPermission(Permission.ORDER_READ_SITE));
    } else {
      bean.setDisplayOrderLink(getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP)
          || getLoginInfo().hasPermission(Permission.ORDER_READ_SITE));
    }
    // 権限があれば、顧客明細へのリンクを表示する
    bean.setDisplayCustomerLink(getLoginInfo().hasPermission(Permission.CUSTOMER_READ));

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderResultInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102017001";
  }

}
