package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.CustomerResultBean;

/**
 * U2030240:お客様情報登録完了のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerResultMoveAction extends WebFrontAction<CustomerResultBean> {

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
    CustomerResultBean bean = getBean();

    if (StringUtil.isNullOrEmpty(bean.getEscapeNextUrl())) {
      setNextUrl("/app/common/index");
    } else {
      setNextUrl(bean.getEscapeNextUrl());
    }

    setRequestBean(getBean());
    return FrontActionResult.RESULT_SUCCESS;
  }
}
