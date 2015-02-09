package jp.co.sint.webshop.web.bean.front.customer;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;


public class CustomerInitpasswordDoneBean extends UIFrontBean{

  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  @Override
  public void createAttributes(RequestParameter reqparam) {
    
  }

  @Override
  public String getModuleId() {
    return "U2030510";
  }

  @Override
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.CustomerInitpasswordDoneBean.0");
  }

}
