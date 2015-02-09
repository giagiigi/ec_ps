package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.InformationSendBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030610:情報メール送信のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class InformationSendSelectAction extends InformationSendBaseAction {

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

    // 選択項目をクリア
    if (StringUtil.isNullOrEmpty(bean.getTemplateCode())) {
      bean.setSubject("");
      bean.setFromAddress("");
      bean.setBccAddress("");
      bean.setContentType("");
      bean.setMailComposition("");
      bean.setSenderName("");
      bean.setUpdatedDatetime(null);
      bean.getMailDetailList().clear();

      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // メールテンプレートを取得
    getMailTemplate(bean);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
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

    setRequestBean(nextBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.InformationSendSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103061007";
  }

}
