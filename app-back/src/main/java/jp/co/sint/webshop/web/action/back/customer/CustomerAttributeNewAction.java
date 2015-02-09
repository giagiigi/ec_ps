package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean.CustomerAttributeBeanDetail;
import jp.co.sint.webshop.web.bean.back.customer.CustomerAttributeBean.CustomerAttributeChoiceBeanDetail;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030510:顧客属性のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeNewAction extends WebBackAction<CustomerAttributeBean> {

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

    CustomerAttributeBean cBean = getBean();

    // 顧客属性の設定
    CustomerAttributeBeanDetail edit = new CustomerAttributeBeanDetail();
    edit.setCustomerAttributeName("");
    edit.setDisplayOrder("");
    edit.setCustomerAttributeType("");
    cBean.setAttributeEdit(edit);

    // 顧客属性選択肢の設定
    List<CustomerAttributeChoiceBeanDetail> choiceList = new ArrayList<CustomerAttributeChoiceBeanDetail>();
    cBean.setAttributeChoiceList(choiceList);
    CustomerAttributeChoiceBeanDetail editChoice = new CustomerAttributeChoiceBeanDetail();
    cBean.setAttributeChoiceEdit(editChoice);
    cBean.setDisplayChoicesList(WebConstantCode.VALUE_TRUE);

    cBean.setRegisterButtonDisplayFlg(WebConstantCode.VALUE_TRUE);
    cBean.setUpdateButtonDisplayFlg(WebConstantCode.VALUE_FALSE);
    this.setRequestBean(cBean);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CustomerAttributeNewAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103051004";
  }

}
