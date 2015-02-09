package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.InquiryEditBean;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2060410:お問い合わせ入力のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class InquiryEditBackAction extends WebFrontAction<InquiryEditBean> {

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
    setRequestBean(getBean());
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    InquiryEditBean bean = (InquiryEditBean) getRequestBean();

    if (StringUtil.hasValue(bean.getCustomerCode())) {
      bean.setEditMode(WebConstantCode.DISPLAY_HIDDEN);
    } else {
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    }
    bean.setReadonlyMode(WebConstantCode.DISPLAY_EDIT);

    bean.setConfirmButtonDisplay(true);
    bean.setCompleteButtonDisplay(false);
    bean.setBackButtonDisplay(false);

  }

}
