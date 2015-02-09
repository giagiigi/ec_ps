package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean.CustomerAttributeBeanDetail;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean.CustomerAttributeChoiceBeanDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030510:顧客属性のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeSelectAction extends WebBackAction<CustomerAttributeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 権限チェック
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
    } else {
      if (getLoginInfo().isSite()) {
        return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
      } else {
        return Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo());
      }
    }
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] urlParam = getRequestParameter().getPathArgs();
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerAttributeBean cBean = getBean();

    // 顧客属性の取得
    CustomerAttribute attrib = cs.getAttribute(Long.parseLong(urlParam[0]));
    if (attrib == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.CustomerAttributeSelectAction.0")));
      this.setRequestBean(cBean);
      return BackActionResult.RESULT_SUCCESS;
    }
    CustomerAttributeBeanDetail edit = new CustomerAttributeBeanDetail();
    edit.setCustomerAttributeNo(Long.toString(attrib.getCustomerAttributeNo()));
    edit.setCustomerAttributeName(attrib.getCustomerAttributeName());
    edit.setDisplayOrder(Long.toString(attrib.getDisplayOrder()));
    edit.setCustomerAttributeType(Long.toString(attrib.getCustomerAttributeType()));
    edit.setUpdatedDatetime(attrib.getUpdatedDatetime());
    cBean.setAttributeEdit(edit);

    // 顧客属性選択肢の取得
    List<CustomerAttributeChoice> choice = cs.getAttributeChoiceList(urlParam[0]);

    List<CustomerAttributeChoiceBeanDetail> choicelist = new ArrayList<CustomerAttributeChoiceBeanDetail>();

    for (CustomerAttributeChoice ac : choice) {
      CustomerAttributeChoiceBeanDetail detail = new CustomerAttributeChoiceBeanDetail();

      detail.setCustomerAttributeNo(Long.toString(ac.getCustomerAttributeNo()));
      detail.setCustomerAttributeChoiceNo(Long.toString(ac.getCustomerAttributeChoicesNo()));
      detail.setCustomerAttributeChoices(ac.getCustomerAttributeChoices());
      detail.setDisplayOrder(Long.toString(ac.getDisplayOrder()));
      detail.setUpdatedDatetime(ac.getUpdatedDatetime());

      choicelist.add(detail);
    }

    cBean.setAttributeChoiceList(choicelist);
    cBean.setDisplayChoicesList(WebConstantCode.VALUE_TRUE);

    cBean.setRegisterButtonDisplayFlg(WebConstantCode.VALUE_FALSE);
    cBean.setUpdateButtonDisplayFlg(WebConstantCode.VALUE_TRUE);

    // 完了メッセージ設定
    if (urlParam.length > 1) {
      if (urlParam[1].equals("registerattribute")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerAttributeSelectAction.1")));
      } else if (urlParam[1].equals("updateattribute")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerAttributeSelectAction.0")));
      } else if (urlParam[1].equals("updateattributeChoice")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerAttributeSelectAction.1")));
      } else if (urlParam[1].equals("deleteattribute")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerAttributeSelectAction.1")));
      }
    }

    this.setRequestBean(cBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerAttributeSelectAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051007";
  }

}
