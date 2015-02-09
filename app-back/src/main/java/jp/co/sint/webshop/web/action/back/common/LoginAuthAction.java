package jp.co.sint.webshop.web.action.back.common;

import java.util.List;

import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.service.AuthorizationService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.AuthorizationServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.common.LoginBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.common.AuthorizationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1010110:ログインのアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class LoginAuthAction extends WebBackAction<LoginBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    LoginBean bean = getBean();

    // バリデーションエラーと認証エラーのメッセージは同じものを表示する
    List<ValidationResult> resultList = BeanValidator.validate(getBean()).getErrors();
    if (resultList.size() > 0) {
      this.addErrorMessage(WebMessage.get(AuthorizationErrorMessage.AUTHORIZATION_ERROR, new String[0]));
      setRequestBean(bean);
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 既にログイン済みなら不正アクセスとしてログアウトする
    if (getLoginInfo().isLogin()) {
      setNextUrl("/app/common/logout");
      return BackActionResult.RESULT_SUCCESS;
    }

    // 一店舗版ならコード定義よりショップコードを設定する
    if (getConfig().isOne()) {
      bean.setShopCode(getConfig().getSiteShopCode());
    }

    AuthorizationService authService = ServiceLocator.getAuthorizationService(getLoginInfo());
    ServiceResult authResult = authService.authorizeUser(bean.getShopCode(), bean.getLoginId(), bean.getPassword());

    if (authResult.hasError()) {
      for (ServiceErrorContent errorContent : authResult.getServiceErrorList()) {
        if (errorContent.equals(AuthorizationServiceErrorContent.USER_ACCOUNT_NOT_FOUND)
            || errorContent.equals(AuthorizationServiceErrorContent.USER_ACCOUNT_LOCK)
            || errorContent.equals(AuthorizationServiceErrorContent.USER_ACCOUNT_PASSWORD_UNMATCH)
            || errorContent.equals(AuthorizationServiceErrorContent.USER_PERMISSION_NOT_FOUND)
            || errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)
            || errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          this.addErrorMessage(WebMessage.get(AuthorizationErrorMessage.AUTHORIZATION_ERROR, new String[0]));
        } else {
          // サービス実行エラー
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setRequestBean(bean);
      setNextUrl(null);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 管理ユーザを取得してセッションに格納
    SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());
    UserAccount account = siteService.getUserAccountByLoginId(bean.getShopCode(), bean.getLoginId());

    LoginInfo login = WebLoginManager.createBackLoginInfo(account);

    // ログイン情報をセッションに格納し、セッションを再作成する
    getSessionContainer().login(login);

    setNextUrl("/app/common/dashboard");
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
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

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    LoginBean bean = (LoginBean) getRequestBean();
    if (bean == null) {
      bean = new LoginBean();
    }
    if (getConfig().isMall() || getConfig().isShop()) {
      bean.setOperatingModeIsMall(true);
    } else if (getConfig().isOne()) {
      bean.setOperatingModeIsMall(false);
    }

    setRequestBean(bean);
  }

  /**
   * Actionの処理が終了後にSessionをrenewするかどうかを返します。<BR>
   * 
   * @return true-セッションをrenewする false-セッションをrenewしない
   */
  @Override
  public boolean isSessionRenew() {
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.common.LoginAuthAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "0101011001";
  }

}
