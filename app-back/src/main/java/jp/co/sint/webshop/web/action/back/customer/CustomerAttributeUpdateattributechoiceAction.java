package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030510:顧客属性のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeUpdateattributechoiceAction extends WebBackAction<CustomerAttributeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    // 権限チェック
    BackLoginInfo login = getLoginInfo();
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
      auth = true;
    } else {
      auth = false;
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CustomerAttributeBean bean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();

    bean.getAttributeChoiceList().get(Integer.parseInt(urlParam[1])).setCustomerAttributeChoices(
        getRequestParameter().getAll("customerAttributeChoiceName")[Integer.parseInt(urlParam[1])]);
    bean.getAttributeChoiceList().get(Integer.parseInt(urlParam[1])).setDisplayOrder(
        getRequestParameter().getAll("customerAttributeChoiceDisplayOrder")[Integer.parseInt(urlParam[1])]);

    return validateBean(bean.getAttributeChoiceList().get(Integer.parseInt(urlParam[1])));
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerAttributeChoice dto = new CustomerAttributeChoice();
    CustomerAttributeBean bean = getBean();
    String[] urlParam = getRequestParameter().getPathArgs();

    bean.setDisplayChoicesList(WebConstantCode.VALUE_TRUE);

    // データベース更新処理
    dto.setCustomerAttributeNo(Long.parseLong(bean.getAttributeEdit().getCustomerAttributeNo()));
    dto.setCustomerAttributeChoicesNo(Long.parseLong(urlParam[0]));
    CustomerAttributeChoice attrib = cs.getAttributeChoice(dto);
    if (attrib == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.CustomerAttributeUpdateattributechoiceAction.0")));
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    int updateTarget = Integer.parseInt(urlParam[1]);
    dto.setDisplayOrder(Long.parseLong(
        getRequestParameter().getAll("customerAttributeChoiceDisplayOrder")[updateTarget]));
    dto.setCustomerAttributeChoices(getRequestParameter().getAll("customerAttributeChoiceName")[updateTarget]);

    ServiceResult sResult = cs.updateCustomerAttributeChoice(dto);

    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.customer.CustomerAttributeUpdateattributechoiceAction.0")));
        } else if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/customer/customer_attribute/select/"
          + bean.getAttributeEdit().getCustomerAttributeNo() + "/updateattributeChoice");
    }

    this.setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerAttributeUpdateattributechoiceAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051009";
  }

}
