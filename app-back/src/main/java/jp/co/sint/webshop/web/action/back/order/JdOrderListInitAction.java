package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.JdOrderListBean;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.ext.text.Messages;
/**
 * jd订单拆分管理
 */
public class JdOrderListInitAction extends WebBackAction<JdOrderListBean> {
  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    JdOrderListBean bean = new JdOrderListBean();
    setBean(bean);
  }

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

  @Override
  public boolean validate() {
    return true;
  }
  
  @Override
  public WebActionResult callService() {
    JdOrderListBean bean = getBean();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    JdOrderListBean bean = (JdOrderListBean) getRequestBean();
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
    return Messages.getString("web.action.back.order.JdOrderListInitAction.0");  
  }
  
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102031002";
  }


}
