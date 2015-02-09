package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030610:情報メール送信のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSendBackAction extends InformationSendBaseAction {

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
    bean.setModeDiv("edit");

    // メールテンプレート画面から遷移してきた場合
    if (setParameter().equals("back_mail_template")) {
      // テンプレートリストを取得
      getTemplateList(bean);

      if (StringUtil.hasValue(bean.getTemplateCode())) {
        // メールテンプレートを取得
        getMailTemplate(bean);
      } else {
        bean.setModeDiv("display");
      }
    }

    //Add by V10-CH start
//    if (setParameter().equals("back_sms_template")) {
//      getTemplateList(bean);
//      if (StringUtil.hasValue(bean.getTemplateCode())) {
//        getSmsTemplate(bean);
//      } else {
//        bean.setModeDiv("display");
//      }
//    }
    //Add by V10-CH end
    
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    InformationSendBean nextBean = (InformationSendBean) getRequestBean();

    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setDisplayNextButtonFlg(true);
    nextBean.setDisplaySendButtonFlg(false);
    nextBean.setDisplayBackButtonFlg(false);
    nextBean.setDisplayMailTemplateCautionFlg(true);
    //Add by V10-CH start
    nextBean.setDisplaySmsTemplateCautionFlg(true);
    //Add by V10-CH end
    if (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      nextBean.setDisplayMailTemplateLinkFlg(true);
      nextBean.setDisplaySmsTemplateLinkFlg(true);
    } else {
      nextBean.setDisplayMailTemplateLinkFlg(false);
      nextBean.setDisplaySmsTemplateLinkFlg(false);
    }

    setRequestBean(nextBean);
  }

  /**
   * URLから取得したパラメータを返します。
   */
  private String setParameter() {
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      return parameter[0];
    }
    return "";
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
    return Messages.getString("web.action.back.customer.InformationSendBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103061001";
  }

}
