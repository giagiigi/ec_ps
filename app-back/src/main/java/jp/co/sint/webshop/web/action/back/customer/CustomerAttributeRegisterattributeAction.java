package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerAttribute;
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
public class CustomerAttributeRegisterattributeAction extends WebBackAction<CustomerAttributeBean> {

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

    boolean validResult = false;

    boolean attribResult = validateBean(bean.getAttributeEdit());
    getRequestParameter();

    if (attribResult) {
      validResult = true;
    }

    return validResult;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerAttribute dto = new CustomerAttribute();
    List<CustomerAttributeChoice> choiceList = new ArrayList<CustomerAttributeChoice>();
    CustomerAttributeBean bean = getBean();

    bean.setDisplayChoicesList(WebConstantCode.VALUE_TRUE);

    // データベース登録処理
    // 属性登録
    dto.setCustomerAttributeName(bean.getAttributeEdit().getCustomerAttributeName());
    dto.setCustomerAttributeType(Long.parseLong(bean.getAttributeEdit().getCustomerAttributeType()));
    dto.setDisplayOrder(Long.parseLong(bean.getAttributeEdit().getDisplayOrder()));
    dto.setDisplayFlg(1L);

    // 属性選択肢登録
    if (!getRequestParameter().get("customerAttributeChoiceName").equals("")) {
      for (int i = 0; i < getRequestParameter().getAll("customerAttributeChoiceName").length; i++) {
        CustomerAttributeChoice detail = new CustomerAttributeChoice();

        detail.setCustomerAttributeChoices(getRequestParameter().getAll("customerAttributeChoiceName")[i]);
        detail.setDisplayOrder(Long.parseLong(
            getRequestParameter().getAll("customerAttributeChoiceDisplayOrder")[i]));
        choiceList.add(detail);
      }
    }

    ServiceResult sResult = cs.insertCustomerAttribute(dto, choiceList);
    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
          return BackActionResult.RESULT_SUCCESS;
        } else if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/customer/customer_attribute/init/registerattribute");
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
    return Messages.getString("web.action.back.customer.CustomerAttributeRegisterattributeAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051005";
  }

}
