package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit1Bean;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit1BackAction extends CustomerEditBaseAction<CustomerEdit1Bean> {

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

    CustomerEdit1Bean bean = new CustomerEdit1Bean();
    CustomerConfirmBean confirmBean = (CustomerConfirmBean) this.getSessionContainer().getTempBean();

    // 保持されているBeanを入力1Beanに設定
    setTempToEdit1(bean, confirmBean);
    // 次ページ指定
    confirmBean.setNextPage("back");
    confirmBean.setFailedTransitionFlg(true);

    setRequestBean(bean);
    getSessionContainer().setTempBean(confirmBean);

    return FrontActionResult.RESULT_SUCCESS;

  }
}
