package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DecryptUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.LoginBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;

/**
 *支付宝快捷登录
 */
public class FastpayInitAction extends WebFrontAction<LoginBean> {

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
    String  customerCode= DecryptUtil.decode64(getPathInfo(0));
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    
    CustomerInfo customerInfo=customerService.getCustomer(customerCode);
    LoginInfo login =WebLoginManager.createFrontLoginInfo(customerInfo.getCustomer());
    

    // ログイン情報をセッションに格納し、セッションを再作成する
    getSessionContainer().login(login);
    BackTransitionInfo afterLoginInfo = getSessionContainer().getAfterLoginInfo();
    if (StringUtil.hasValue(afterLoginInfo.getUrl())) {
      //20120116 os013 add start
      if(customerInfo.getCustomer().getCustomerKbn()==1L){
        setNextUrl("/app/customer/customer_edit1/init/cart");
      }else{
      //20120116 os013 add end
        setNextUrl("/app/cart/cart/move/shipping/00000000///");
      }
      
    }else{
      setNextUrl("/app/common/index");
    }   
    setRequestBean(afterLoginInfo.getBean());
    return FrontActionResult.RESULT_SUCCESS;
  }
  private String getPathInfo(int index) {
	    String[] tmp = getRequestParameter().getPathArgs();
	    if (tmp.length > index) {
	      return tmp[index];
	    }
	    return "";
  }
}
