package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.InquiryEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2060410:お問い合わせ入力のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class InquiryEditConfirmAction extends WebFrontAction<InquiryEditBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    InquiryEditBean bean = getBean();
    boolean result = true;
    // 商品、受注に関する問合せの場合は件名が選択されているかどうかのチェックを行わない
    if (!bean.isCommodityInquiryMode() && !bean.isOrderInquiryMode() && getBean().getCustomerInquirySubject().equals("0")) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,  Messages
          .getString("web.action.front.customer.InquiryEditConfirmAction.0")));
      result &= false;
    }

    result &= validateBean(bean);

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    InquiryEditBean bean = getBean();
    bean.setEditMode(WebConstantCode.DISPLAY_HIDDEN);
    bean.setReadonlyMode(WebConstantCode.DISPLAY_HIDDEN);
    bean.setConfirmButtonDisplay(false);
    bean.setCompleteButtonDisplay(true);
    bean.setBackButtonDisplay(true);

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
