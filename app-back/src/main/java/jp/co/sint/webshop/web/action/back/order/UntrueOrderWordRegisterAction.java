package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.UntrueOrderWord;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.UntrueOrderWordBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class UntrueOrderWordRegisterAction extends WebBackAction<UntrueOrderWordBean> {

  @Override
  public boolean authorize() {
    return Permission.UNTRUE_ORDER_WORD.isGranted(getLoginInfo());
	//  return true;
  }
  //app/order/shipping/payment_confirm
  //bean不能为空 验证
  @Override
  public boolean validate() {
    UntrueOrderWordBean bean = getBean();
    String issearchword= bean.getSearchWord();
    if(issearchword.trim().equals(""))
    {
      addErrorMessage(MessageFormat.format(Messages
          .getString("web.action.back.order.UntrueOrderWordRegisterAction.1"),true));
      return false;
    }else{
      //判断用户输入长度
      if(issearchword.length()>100){
        addErrorMessage(MessageFormat.format(Messages
            .getString("web.action.back.order.UntrueOrderWordRegisterAction.3"),true));
        return false;
      }
    }
    return true;
  }
  @Override
  public WebActionResult callService() {
    UntrueOrderWordBean bean = getBean();
    OrderService orderservice = ServiceLocator.getOrderService(getLoginInfo());
    UntrueOrderWord untrueOrderWord = new UntrueOrderWord();
    Long uowNo = DatabaseUtil.generateSequence(SequenceType.UNTRUE_ORDER_WORD_CODE_SEQ);  
    untrueOrderWord.setOrderWordName(bean.getSearchWord());
    untrueOrderWord.setOrderWordCode(uowNo.toString());
    orderservice.insertUntrueOrderWord(untrueOrderWord);
    setNextUrl("/app/order/untrue_order_word/init/"+"add");
    return BackActionResult.RESULT_SUCCESS;
   }  

  public String getActionName() {
    return "web.action.back.order.UntrueOrderWordRegisterAction.0";
  }
  
  public String getOperationCode() {
    return "1102051008";
  }
  
}
