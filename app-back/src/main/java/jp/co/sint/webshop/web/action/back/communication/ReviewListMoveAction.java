package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.ReviewListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1060210:レビュー管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ReviewListMoveAction extends WebBackAction<ReviewListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.REVIEW_READ_SHOP.isGranted(getLoginInfo()) || Permission.REVIEW_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ReviewListBean reqBean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();

    if (urlParam[0].equals("contributor")) {
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      CustomerInfo customerInfo = service.getCustomer(urlParam[1]);
      if (customerInfo.getCustomer() == null || StringUtil.isNullOrEmpty(customerInfo.getCustomer().getCustomerCode())
          || service.isNotFound(customerInfo.getCustomer().getCustomerCode())
          || service.isWithdrawed(customerInfo.getCustomer().getCustomerCode())) {
        setRequestBean(reqBean);
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.communication.ReviewListMoveAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/customer/customer_edit/select/" + urlParam[1]);
    }

    DisplayTransition.add(getBean(), "/app/communication/review_list/search_back/", getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length < 2) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.ReviewListMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106021003";
  }

}
