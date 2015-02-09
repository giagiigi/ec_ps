package jp.co.sint.webshop.web.action.back.order;
import jp.co.sint.webshop.ext.text.Messages;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderListBean;
import jp.co.sint.webshop.web.webutility.DisplayTransition;
/**
 *
 *jd拆单 详情页跳转
 */
public class JdOrderListMoveAction extends WebBackAction<OrderListBean> {

  /**
   * 注册用户的权限
   * 确认这个用户是否可以回去
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP) || getLoginInfo().hasPermission(Permission.ORDER_READ_SITE);
  }

  /**
   * 对于页面传值 进行验证
   * @reurn 错误的话 返回false;
   */
  
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    return false;
  }
  /**
   * 采取行动
   * @return 动作后执行结果
   */
  @Override
  public WebActionResult callService() {
    
    String orderNo = getRequestParameter().getPathArgs()[0];

    setNextUrl("/app/order/order_detail/init/" + orderNo);

    DisplayTransition.add(getBean(), "/app/order/jd_order_list/search", getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
    }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
  return Messages.getString("web.action.back.order.JdOrderListMoveAction.0");
  
  
  }

  /**
   * 取出操作码
   * @ruern 操作码
   */
  public String getOperationCode() {
    return "1102032010";
  }

}
