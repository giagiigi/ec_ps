package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030610:情報メール送信のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSendConfirmAction extends InformationSendBaseAction {

  // バリデーション結果
  private boolean successValidate = true;

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    successValidate = super.validate();
    return successValidate;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    InformationSendBean bean = getBean();
    bean.setModeDiv("display");

    addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    if (!successValidate) {
      return;
    }
    InformationSendBean nextBean = (InformationSendBean) getRequestBean();

    nextBean.setEditMode(WebConstantCode.DISPLAY_HIDDEN);
    nextBean.setDisplayNextButtonFlg(false);
    nextBean.setDisplaySendButtonFlg(true);
    nextBean.setDisplayBackButtonFlg(true);
    nextBean.setDisplayMailTemplateCautionFlg(false);
    nextBean.setDisplayMailTemplateLinkFlg(false);
    //Add by v10-CH start
    nextBean.setDisplaySmsTemplateCautionFlg(false);
    nextBean.setDisplaySmsTemplateLinkFlg(false);
    //Add by V10-CH end
    setRequestBean(nextBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.InformationSendConfirmAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103061002";
  }

}
