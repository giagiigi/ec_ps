package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.CustomerSendpasswordBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;

/**
 * U2030410:パスワード再登録URL送信のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerSendpasswordInitAction extends WebFrontAction<CustomerSendpasswordBean> {

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
	CustomerSendpasswordBean bean;
    // URLから取得したパラメータをセット
    // parameter[0] : 処理パラメータ
    String[] parameter = getRequestParameter().getPathArgs();

    if (parameter.length > 0) {
      setCompleteMessage(parameter[0]);
      bean = (CustomerSendpasswordBean)getBean();
      bean.setSendMail(true);
    }else{
    	bean = new CustomerSendpasswordBean();
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage(String completeParam) {
    if (completeParam.equals("sendmail")) {
      addInformationMessage(WebMessage.get(CompleteMessage.SENDMAIL_COMPLETE, ""));
    }
  }
  
  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
