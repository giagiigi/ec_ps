package jp.co.sint.webshop.web.action.back.order;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.UntrueOrderWordBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 *虚假订单关键词删除
 */
public class UntrueOrderWordDeleteAction extends WebBackAction<UntrueOrderWordBean> {

  @Override
  public boolean authorize() {
    return Permission.UNTRUE_ORDER_WORD.isGranted(getLoginInfo());
  }
  
  @Override
  public boolean validate() {    
    return true;
  }
  
  @Override
  public WebActionResult callService() {
    String[] urlParam = getRequestParameter().getPathArgs();
    OrderService orderservice = ServiceLocator.getOrderService(getLoginInfo());
    String uowcode =urlParam[0];
    orderservice.deleteUntrueOrderWord(uowcode);
    setNextUrl("/app/order/untrue_order_word/init/"+"delete");
    return BackActionResult.RESULT_SUCCESS;
   }  

  public String getActionName() {
    return Messages.getString("web.action.back.order.UntrueOrderWordDeleteAction.0");
  }

  public String getOperationCode() {
    return "1102051010";
  }

}
