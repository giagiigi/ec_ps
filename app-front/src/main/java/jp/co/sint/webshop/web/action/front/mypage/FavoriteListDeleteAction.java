package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.FavoriteListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;

/**
 * U2030810:お気に入り商品のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class FavoriteListDeleteAction extends WebFrontAction<FavoriteListBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length < 2) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    FavoriteListBean bean = getBean();
    FrontLoginInfo login = getLoginInfo();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    String[] parameter = getRequestParameter().getPathArgs();

    // データベース更新処理
    ServiceResult serviceResult = service.deleteFavoriteCommodity(login.getCustomerCode(), parameter[0], parameter[1]);

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          setNextUrl("/app/common/index");

          getSessionContainer().logout();
          return FrontActionResult.RESULT_SUCCESS;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/mypage/favorite_list/init/delete");
    }
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
