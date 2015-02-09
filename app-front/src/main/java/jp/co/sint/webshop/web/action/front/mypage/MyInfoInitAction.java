package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.MyInfoBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MyInfoInitAction extends  WebFrontAction<MyInfoBean> {


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

    MyInfoBean bean = new MyInfoBean();

    // String url[] = this.getRequestParameter().getPathArgs();
    // if (url != null && url.length > 0) {
    // if (COMPLETE.equals(url[0])) {
    // addInformationMessage(WebMessage.get(AuthorizationErrorMessage.MOBILE_COMPLETE));
    // }
    // }
    FrontLoginInfo login = getLoginInfo();

    String customerCode = login.getCustomerCode();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo info = service.getCustomer(customerCode);
    Customer customer = info.getCustomer();

    // 顧客情報
    bean.setCustomerCode(customerCode);
    bean.setLastName(customer.getLastName());
    bean.setEmail(customer.getEmail());
    bean.setBirthDate(DateUtil.toDateString(customer.getBirthDate()));
    
    if("3000/01/01".equals(bean.getBirthDate())){
      bean.setBirthdayUpdateFlag(true);
    }else{
      bean.setBirthdayUpdateFlag(false);
    }
    
    bean.setSex(Long.toString(customer.getSex()));
    bean.setRequestMailType(Long.toString(customer.getRequestMailType()));

    bean.setLanguageCode(customer.getLanguageCode());
    bean.setOldMobileNumber(customer.getMobileNumber());
    
    List<CodeAttribute> sexList = new ArrayList<CodeAttribute>();
    for (Sex sex : Sex.values()) {
      if(!sex.getValue().equals(Sex.UNKNOWN.getValue())){
        sexList.add(sex);
      }
    }
    
    bean.setSexList(sexList);
    
    List<CodeAttribute> requestMailTypes = new ArrayList<CodeAttribute>();
    for (RequestMailType type : RequestMailType.values()) {
        requestMailTypes.add(type);
    }
    
    bean.setRequestMailTypeList(requestMailTypes);
    
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
