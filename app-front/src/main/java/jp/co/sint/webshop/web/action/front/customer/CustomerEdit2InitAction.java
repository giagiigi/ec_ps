package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean;

/**
 * U2030220:お客様情報登録2のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit2InitAction extends CustomerEditBaseAction<CustomerEdit2Bean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (!super.validate()) {
      return false;
    }
    if (!validateConfirm()) {
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

    // 共通Beanを取得
    CustomerConfirmBean confirmBean = (CustomerConfirmBean) getSessionContainer().getTempBean();

    // 顧客コードを取得
    String customerCode = "";
    customerCode = confirmBean.getCustomerCode();

    String mode = "";

    if (StringUtil.hasValue(customerCode)) {
      mode = "update";
    } else {
      mode = "register";
    }

    CustomerEdit2Bean nextBean = new CustomerEdit2Bean();
    nextBean.setCustomerCode(customerCode);

    // 顧客属性取得
    setAttributeList(nextBean, mode);

    // 入力内容を保持
    confirmBean.setFailedTransitionFlg(true);
    getSessionContainer().setTempBean(confirmBean);
    // 入力内容2を保持
    setEdit2ToTemp(nextBean, confirmBean);

    setRequestBean(nextBean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
