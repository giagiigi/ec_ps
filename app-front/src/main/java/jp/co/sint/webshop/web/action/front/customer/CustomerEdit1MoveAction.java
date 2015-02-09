package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean; // 10.1.1 10005 追加
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit1Bean;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit1MoveAction extends CustomerEditBaseAction<CustomerEdit1Bean> {
//2013/04/01 优惠券对应 ob add start
  private static final String AUTH_CODE = "auth_code";
//2013/04/01 优惠券对应 ob add end
  // 10.1.1 10005 追加 ここから
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateTempBean();
  }
  // 10.1.1 10005 追加 ここまで

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 10.1.1 10005 追加 ここから
    CustomerConfirmBean confirmBean = (CustomerConfirmBean) getSessionContainer().getTempBean();
    confirmBean.setFailedTransitionFlg(false);

    getSessionContainer().setTempBean(confirmBean);
    // 10.1.1 10005 追加 ここまで

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 顧客が存在しない/退会済みの場合
    if (service.isNotFound(getLoginInfo().getCustomerCode()) || service.isInactive(getLoginInfo().getCustomerCode())) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    // 次画面URL設定
    setNextUrl("/app/mypage/customer_changepassword/init");
    
    //2013/04/01 优惠券对应 ob add start
    
    if(getRequestParameter().getPathArgs() != null 
        && getRequestParameter().getPathArgs().length>0 
        && AUTH_CODE.equals(getRequestParameter().getPathArgs()[0])){
      setNextUrl("/app/common/mobile_auth/init");
    }
    
   //2013/04/01 优惠券对应 ob add end
    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/customer/customer_edit1/back",
        getSessionContainer());

    setRequestBean(getBean());

    return FrontActionResult.RESULT_SUCCESS;
  }
}
