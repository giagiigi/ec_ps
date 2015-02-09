package jp.co.sint.webshop.web.action.front.common;

import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.NewCouponHistory;
import jp.co.sint.webshop.service.AuthorizationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.AuthorizationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.LoginBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.common.AuthorizationErrorMessage;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;

/**
 * U2010110:ログインのアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class LoginAuthAction extends WebFrontAction<LoginBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    LoginBean bean = getBean();

    bean.setFailed(true);
    // バリデーションエラーと認証エラーのメッセージは同じものを表示する
    List<ValidationResult> resultList = BeanValidator.validate(getBean()).getErrors();
    if (resultList.size() > 0) {
      this.addErrorMessage(WebMessage.get(AuthorizationErrorMessage.AUTHORIZATION_ERROR, new String[0]));
      setRequestBean(bean);
      setNextUrl(null);
      return FrontActionResult.RESULT_SUCCESS;
    }

    try {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());

      // 前回ログイン日時を退避するため顧客情報を取得
      Customer backCustomer = customerService.getCustomerByLoginId(bean.getLoginId());
      Date lastLoginDate = null;
      if (backCustomer != null) {
        lastLoginDate = backCustomer.getLoginDatetime();
      }

      AuthorizationService authService = ServiceLocator.getAuthorizationService(getLoginInfo());

      ServiceResult authResult = authService.authorizeCustomer(bean.getLoginId(), bean.getPassword());

      if (authResult.hasError()) {
        for (ServiceErrorContent errorContent : authResult.getServiceErrorList()) {
          if (errorContent.equals(AuthorizationServiceErrorContent.CUSTOMER_NOT_FOUND)
              || errorContent.equals(AuthorizationServiceErrorContent.CUSTOMER_LOCK)
              || errorContent.equals(AuthorizationServiceErrorContent.CUSTOMER_PASSWORD_UNMATCH)
              //20111226 os013 add start
              //支付宝登录会员不让认证
              ||errorContent.equals(AuthorizationServiceErrorContent.CUSTOMER_KBN)) {
        	  //20111226 os013 add end
            this.addErrorMessage(WebMessage.get(AuthorizationErrorMessage.AUTHORIZATION_ERROR, new String[0]));
          } else {
            // サービス実行エラー
            return FrontActionResult.SERVICE_ERROR;
          }
        }
        setRequestBean(bean);
        setNextUrl(null);
        return FrontActionResult.RESULT_SUCCESS;
      }

      // 顧客情報を取得してログイン情報を作成する(退避していた前回ログイン日時を復元)
      Customer customer = customerService.getCustomerByLoginId(bean.getLoginId());
      customer.setLoginDatetime(lastLoginDate);
      NewCouponHistory couponhistory = customerService.getNewCouponHistory(customer.getCustomerCode());
      LoginInfo login = WebLoginManager.createFrontLoginInfo(customer,couponhistory);

      // ログイン情報をセッションに格納し、セッションを再作成する
      getSessionContainer().login(login);
      
      BackTransitionInfo afterLoginInfo = getSessionContainer().getAfterLoginInfo();
      if (StringUtil.hasValue(afterLoginInfo.getUrl())) {
        // 20111215 shen add start
        if (afterLoginInfo.getUrl().startsWith("/app/cart/cart/move/shipping/")) {
          addCartItem(customer.getCustomerCode(), true);
        } else {
          addCartItem(customer.getCustomerCode(), false);
        }
        // 20111215 shen add end
        setNextUrl(afterLoginInfo.getUrl());
      } else {
        // 20111215 shen add start
        addCartItem(customer.getCustomerCode(), false);
        // 20111215 shen add end
        setNextUrl("/app/common/index");
      }
      bean.setFailed(false);
      setRequestBean(afterLoginInfo.getBean());
      
    } catch (RuntimeException e) {
      return FrontActionResult.SERVICE_ERROR;
    }

    return FrontActionResult.RESULT_SUCCESS;
  }
  
  // 20111215 shen add start
  public void addCartItem(String customerCode, boolean deleteHistory) {
    if (deleteHistory) {
      // 购物车履历清空
      getCart().clearCartHistroy(getLoginInfo().getCustomerCode());
    } else {
      // 购物车履历商品添加到购物车中
      getCart().setCartItemFromHistory(customerCode);
    }
    // 购物车履历更新
    getCart().insertCartHistory(customerCode);
  }
  // 20111215 shen add end

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }
  // 10.1.8 10394 追加 ここから
  /**
   * Actionの処理が終了後にSessionをrenewするかどうかを返します。<BR>
   * 
   * @return true-セッションをrenewする false-セッションをrenewしない
   */
  @Override
  public boolean isSessionRenew() {
    return true;
  }
  // 10.1.8 10394 追加 ここまで
}
