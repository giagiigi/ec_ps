package jp.co.sint.webshop.web.action.front.common;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.MobileAuthBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * @author System Integrator Corp.
 */
public class MobileAuthInitAction extends WebFrontAction<MobileAuthBean> {


  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    MobileAuthBean bean = new MobileAuthBean();
    String[] url=getRequestParameter().getPathArgs();
    if (url != null && url.length > 0) {
      bean.setUrl(url[0]);
    }
    FrontLoginInfo login = getLoginInfo();
    // 顧客情報を取得する
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    Customer customer = service.getCustomerByLoginId(login.getLoginId());
    if (!login.isMobileCustomerFlg()) {
      bean.setLanguageCode(customer.getLanguageCode());
      if((customer != null && StringUtil.hasValueAllOf(customer.getAuthCode(), customer.getMobileNumber()))){
        bean.setRegisterCustomerFlg(Boolean.TRUE);
        bean.setOldMobileNumber(customer.getMobileNumber());
      }
    }else{
      bean.setRegisterCustomerFlg(Boolean.TRUE);
      bean.setOldMobileNumber(login.getMobileNumber());
      bean.setLanguageCode(customer.getLanguageCode());
    }

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

}
