package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030610:情報メール送信のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSendInitAction extends InformationSendBaseAction {

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
    InformationSendBean bean = getBean();

    // 顧客検索条件を取得
    CustomerSearchCondition condition = new CustomerSearchCondition();
    setSearchCondition(bean, condition);

    // 検索結果リストを取得
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    SearchResult<CustomerSearchInfo> result = customerService.findCustomer(condition);

    // テンプレートリストを取得
    getTemplateList(bean);

    // 該当データなし
    if (result.getRowCount() == 0 || bean.getTemplateList().size() < 1) {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 

    // 処理完了メッセージ設定
    setCompleteMessage();

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   */
  private void setCompleteMessage() {
    // URLから取得したパラメータをセット
    // parameter[0] : 処理パラメータ
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length < 1) {
      return;
    }
    if (parameter[0].equals("send_mail")) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_MAILQUE_COMPLETE));
    }
    
    if(parameter[0].equals("send_sms")){
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_SMSQUE_COMPLETE));
    }
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    InformationSendBean nextBean = (InformationSendBean) getRequestBean();

    if (StringUtil.hasValue(nextBean.getTemplateCode())) {
      nextBean.setDisplayNextButtonFlg(true);
      nextBean.setModeDiv("edit");
    } else {
      nextBean.setDisplayNextButtonFlg(false);
      nextBean.setModeDiv("display");
    }
    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setDisplaySendButtonFlg(false);
    nextBean.setDisplayBackButtonFlg(false);
    nextBean.setDisplayMailTemplateCautionFlg(true);

    if (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      nextBean.setDisplayMailTemplateLinkFlg(true);
    } else {
      nextBean.setDisplayMailTemplateLinkFlg(false);
    }

    setRequestBean(nextBean);
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.InformationSendInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103061003";
  }

}
