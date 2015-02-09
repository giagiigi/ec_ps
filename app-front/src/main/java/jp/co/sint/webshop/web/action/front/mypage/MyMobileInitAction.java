package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MyInfoBean;
import jp.co.sint.webshop.web.bean.front.mypage.MyMobileBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MyMobileInitAction extends  WebFrontAction<MyMobileBean> {


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

    MyMobileBean bean = new MyMobileBean();

    String[] url=getRequestParameter().getPathArgs();
    if (url != null && url.length > 0) {
      bean.setUrl(url[0]);
    }
    
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = service.getCustomer(getLoginInfo().getCustomerCode());
    if(info ==null){
      setNextUrl("/app/common/index");
      return FrontActionResult.RESULT_SUCCESS;
    }
    Customer customer = info.getCustomer();
    bean.setLanguageCode(customer.getLanguageCode());

    // 顧客情報
    setRequestBean(bean);
    
    

    return FrontActionResult.RESULT_SUCCESS;
  }
  
  @Override
  public void prerender() {

    String[] pathInfo = getRequestParameter().getPathArgs();
    if (pathInfo.length > 0) {
        if("suc".equals(pathInfo[0])){
          addInformationMessage(Messages.getString("web.action.front.mypage.MyInfoUpdateAction.0"));
        }
    }
  }


}
