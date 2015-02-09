package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.CustomerSendpasswordDoneBean;


public class CustomerSendpasswordDoneInitAction extends WebFrontAction<CustomerSendpasswordDoneBean> {

  @Override
  public WebActionResult callService() {
    
    return FrontActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    CustomerSendpasswordDoneBean bean = getBean();
    String[] params = getRequestParameter().getPathArgs();  
    if(params.length > 0) {
      bean.setEmail(params[0]);
      setRequestBean(bean);
      return true;
    } else {
      return false;
    }
  }

}
