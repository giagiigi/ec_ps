package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderConfirmListBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020510:受注確認管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderConfirmListInitAction extends WebBackAction<OrderConfirmListBean> {

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {

    OrderConfirmListBean bean = new OrderConfirmListBean();


    bean.setOrderType(OrderType.ALL.getValue());
    bean.setOrderFlg(OrderFlg.NOT_CHECKED.getValue());
    bean.setContentFlg("2");
    bean.setConfirmFlg("0");
    bean.setOrderByOrderNo(true);
    
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      bean.setAuthority(getLoginInfo().hasPermission(Permission.CONFIRM_OBJECT_IO_SITE));
    } else {
      bean.setAuthority(getLoginInfo().hasPermission(Permission.CONFIRM_OBJECT_IO_SHOP) || getLoginInfo().hasPermission(Permission.CONFIRM_OBJECT_IO_SITE) );
    }
    
    setRequestBean(bean);
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
    } else {
      authorization = getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP)
          || getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
    }

    return authorization;
  }

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

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderConfirmListBean bean = (OrderConfirmListBean) getRequestBean();

    if (Permission.ORDER_READ_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_READ_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityRead(true);
    }
    if (Permission.ORDER_UPDATE_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityUpdate(true);
    }
    if (Permission.ORDER_MODIFY_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_MODIFY_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityDelete(true);
    }
    if (Permission.ORDER_DATA_IO_SHOP.isGranted(getLoginInfo()) || Permission.ORDER_DATA_IO_SITE.isGranted(getLoginInfo())) {
      bean.setAuthorityIO(true);
    }

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderConfirmListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021008";
  }

}
