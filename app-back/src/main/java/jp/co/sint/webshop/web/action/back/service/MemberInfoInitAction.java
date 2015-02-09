package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class MemberInfoInitAction extends WebBackAction<MemberInfoBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_USER_DATA_READ);
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
    MemberInfoBean bean = new MemberInfoBean();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    MemberInfoBean bean = (MemberInfoBean) getRequestBean();
    bean.setDisplayInquiryButton(Permission.SERVICE_COMPLAINT_DATA_READ.isGranted(getLoginInfo()));
    bean.setDisplayCustomerEditButton(Permission.CUSTOMER_READ.isGranted(getLoginInfo()));
    bean.setDisplayAddressButton(Permission.CUSTOMER_READ.isGranted(getLoginInfo()));
    bean.setDisplayPointButton(Permission.CUSTOMER_POINT_READ.isGranted(getLoginInfo()));
    bean.setDisplayInquiryEditButton(Permission.SERVICE_COMPLAINT_DATA_UPDATE.isGranted(getLoginInfo()));
    bean.setDisplayShippingButton(Permission.SHIPPING_READ_SITE.isGranted(getLoginInfo()));
//    bean.setDisplayReturnButton(Permission.RETURN_DATA_MANAGEMENT.isGranted(getLoginInfo()));
    bean.setDisplayTryButton(Permission.SHIPPING_READ_SITE.isGranted(getLoginInfo()));
    bean.setDisplaySkinButton(true);
    bean.setDisplayOrderLink(Permission.ORDER_READ_SITE.isGranted(getLoginInfo()));
//    bean.setDisplayReturnEditButton(bean.isDisplayReturnButton() && Permission.RETURN_DATA_READ.isGranted(getLoginInfo()));
//    bean.setDisplayReturnGoodsConfirmButton(bean.isDisplayReturnButton()
//        && Permission.RETURN_GOODS_DATA_CONFIRM.isGranted(getLoginInfo()));
//    bean.setDisplayReturnRefundConfirmButton(bean.isDisplayReturnButton()
//        && Permission.RETURN_REFUND_DATA_CONFIRM.isGranted(getLoginInfo()));
//    bean.setDisplayReturnCompleteButton(bean.isDisplayReturnButton() && Permission.RETURN_COMPLETE_DATA.isGranted(getLoginInfo()));

    setCompleteMessage();
  }

  /**
   * 从URL取得完成的操作。
   * 
   * @return targetMode
   */
  private String completeParam() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage() {
    String completeParam = completeParam();
    if (StringUtil.hasValue(completeParam)) {
      if (completeParam.equals("delete")) {
        addErrorMessage(Messages.getString("web.action.back.service.MemberInfoInitAction.1"));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.MemberInfoInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109011001";
  }
}
