package jp.co.sint.webshop.web.bean.front.customer;

import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;


public class CustomerSendpasswordDoneBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  String email;
  
  @Override
  public void createAttributes(RequestParameter reqparam) {
    setEmail(reqparam.get("email"));
  }

  @Override
  public String getModuleId() {
    return "U2030490";
  }

  @Override
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.CustomerSendpasswordDoneBean.0");
  }

  
  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  
  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

}
