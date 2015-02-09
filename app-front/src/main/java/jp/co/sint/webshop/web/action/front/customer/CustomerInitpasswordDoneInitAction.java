package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.CustomerInitpasswordDoneBean;


public class CustomerInitpasswordDoneInitAction  extends WebFrontAction<CustomerInitpasswordDoneBean> {

  @Override
  public WebActionResult callService() {
    
    return FrontActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    return true;
  }

}
