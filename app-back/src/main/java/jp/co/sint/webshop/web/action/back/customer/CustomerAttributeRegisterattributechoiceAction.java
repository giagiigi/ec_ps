package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean.CustomerAttributeChoiceBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030510:顧客属性のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeRegisterattributechoiceAction extends WebBackAction<CustomerAttributeBean> {

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
    return validateBean(bean.getAttributeChoiceEdit());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerAttributeBean bean = getBean();
    String[] urlParam = getRequestParameter().getPathArgs();

    bean.setDisplayChoicesList(WebConstantCode.VALUE_TRUE);

    // 属性選択肢登録
    if (urlParam[0].equals("new")) {
      // 新規登録の場合はセッションに格納する。
      List<CustomerAttributeChoiceBeanDetail> choiceList = bean.getAttributeChoiceList();
      CustomerAttributeChoiceBeanDetail detail = new CustomerAttributeChoiceBeanDetail();

      detail.setCustomerAttributeChoices(getRequestParameter().get("customerAttributeChoiceNameEdit"));
      detail.setDisplayOrder(getRequestParameter().get("customerAttributeChoiceDisplayOrderEdit"));
      choiceList.add(detail);

      bean.setAttributeChoiceList(choiceList);
      setNextUrl(null);
    } else if (urlParam[0].equals("modify")) {
      // 更新時の場合はデータベースに格納する。
      CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
      CustomerAttributeChoice attributeChoice = new CustomerAttributeChoice();
      attributeChoice.setCustomerAttributeNo(Long.parseLong(bean.getAttributeEdit().getCustomerAttributeNo()));
      attributeChoice.setCustomerAttributeChoices(getRequestParameter().get("customerAttributeChoiceNameEdit"));
      attributeChoice.setDisplayOrder(Long.parseLong(
          getRequestParameter().get("customerAttributeChoiceDisplayOrderEdit")));

      cs.insertCustomerAttributeChoice(attributeChoice);
      setNextUrl("/app/customer/customer_attribute/select/"
          + bean.getAttributeEdit().getCustomerAttributeNo() + "/registerattribute");
    }

    // 登録部フォームクリア
    CustomerAttributeChoiceBeanDetail edit = bean.getAttributeChoiceEdit();
    edit.setCustomerAttributeChoices("");
    edit.setDisplayOrder("");

    this.setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerAttributeRegisterattributechoiceAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051006";
  }

}
