package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1030510:顧客属性のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeDeleteattributechoiceAction extends WebBackAction<CustomerAttributeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // 10.1.7 10304 修正 ここから
    // return Permission.CUSTOMER_DELETE.isGranted(login);
    return Permission.CUSTOMER_UPDATE.isGranted(login);
    // 10.1.7 10304 修正 ここまで
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.customer.CustomerAttributeDeleteattributechoiceAction.0")));
      return false;
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
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerAttributeChoice dto = new CustomerAttributeChoice();
    CustomerAttributeBean bean = getBean();
    String[] urlParam = getRequestParameter().getPathArgs();

    if (urlParam[1].equals("new")) {
      try {
        int idx = Integer.parseInt(urlParam[0]);
        bean.getAttributeChoiceList().remove(idx);
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      } catch (RuntimeException e) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR,
            Messages.getString("web.action.back.customer.CustomerAttributeDeleteattributechoiceAction.0")));
        return BackActionResult.SERVICE_ERROR;
      }
    } else {
      Logger.getLogger(this.getClass()).debug(bean.getEditMode());
    }

    // 存在チェック
    dto.setCustomerAttributeNo(NumUtil.toLong(bean.getAttributeEdit().getCustomerAttributeNo()));
    dto.setCustomerAttributeChoicesNo(NumUtil.toLong(urlParam[0]));
    CustomerAttributeChoice attrib = cs.getAttributeChoice(dto);
    if (attrib == null) {
      setNextUrl("/app/customer/customer_attribute/select/" 
          + bean.getAttributeEdit().getCustomerAttributeNo() + "/deleteattribute");
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    bean.setDisplayChoicesList(WebConstantCode.VALUE_TRUE);

    // データベース削除処理
    dto.setCustomerAttributeNo(Long.parseLong(bean.getAttributeEdit().getCustomerAttributeNo()));
    dto.setCustomerAttributeChoicesNo(Long.parseLong(urlParam[0]));

    ServiceResult sResult = cs.deleteCustomerAttributeChoice(Long.parseLong(bean.getAttributeEdit().getCustomerAttributeNo()), Long
        .parseLong(urlParam[0]));

    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          setNextUrl("/app/customer/customer_attribute/select/" + bean.getAttributeEdit().getCustomerAttributeNo()
              + "/deleteattribute");
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/customer/customer_attribute/select/" 
          + bean.getAttributeEdit().getCustomerAttributeNo() + "/deleteattribute");
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
    return Messages.getString("web.action.back.customer.CustomerAttributeDeleteattributechoiceAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051002";
  }

}
