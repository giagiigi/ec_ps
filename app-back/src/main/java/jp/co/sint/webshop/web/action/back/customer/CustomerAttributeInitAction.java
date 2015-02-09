package jp.co.sint.webshop.web.action.back.customer;

import java.util.List;

import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean.CustomerAttributeBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030510:顧客属性のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeInitAction extends WebBackAction<CustomerAttributeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
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

    CustomerAttributeBean bean = new CustomerAttributeBean();

    // 顧客属性一覧取得
    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    List<CustomerAttribute> attribute = cs.getAttributeList();

    List<CustomerAttributeBeanDetail> list = bean.getAttributeList();
    for (CustomerAttribute ca : attribute) {
      CustomerAttributeBeanDetail detail = new CustomerAttributeBeanDetail();

      detail.setCustomerAttributeNo(Long.toString(ca.getCustomerAttributeNo()));
      detail.setCustomerAttributeName(ca.getCustomerAttributeName());
      detail.setCustomerAttributeTypeName(CustomerAttributeType.fromValue(Long.toString(ca.getCustomerAttributeType())).getName());
      detail.setDisplayOrder(Long.toString(ca.getDisplayOrder()));

      list.add(detail);
    }

    bean.setAttributeList(list);

    // 登録部
    CustomerAttributeBeanDetail edit = bean.getAttributeEdit();
    edit.setCustomerAttributeNo("");
    edit.setCustomerAttributeName("");
    edit.setCustomerAttributeType("");
    edit.setDisplayOrder("");

    bean.setDisplayChoicesList("false");

    // 完了メッセージ設定
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length > 0) {
      if (urlParam[0].equals("registerattribute")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerAttributeInitAction.0")));
      } else if (urlParam[0].equals("updateattribute")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerAttributeInitAction.0")));
      } else if (urlParam[0].equals("deleteattribute")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.customer.CustomerAttributeInitAction.0")));
      }
    }

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面上に表示されるボタンの表示/非表示を制御します。<BR>
   * 新規顧客登録ボタン/更新ボタン/削除ボタン<BR>
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    CustomerAttributeBean bean = (CustomerAttributeBean) getRequestBean();

    // 更新権限チェック
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
      bean.setUpdateMode(true);
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setEditMode(WebConstantCode.DISPLAY_READONLY);
    }
    // 削除権限チェック
    bean.setDeleteMode(Permission.CUSTOMER_DELETE.isGranted(login));
    bean.setDisplayDeleteChoiceButton(Permission.CUSTOMER_UPDATE.isGranted(login)); // 10.1.7 10304 追加
    
    setRequestBean(bean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerAttributeInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051003";
  }

}
