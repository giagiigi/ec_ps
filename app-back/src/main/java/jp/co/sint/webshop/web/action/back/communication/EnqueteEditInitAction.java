package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditInitAction extends EnqueteEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    EnqueteEditBean nextBean = new EnqueteEditBean();
    // 初期表示では常に設問エリアと選択肢エリアを非表示
    nextBean.setEnqueteButtonDisplay(true);
    nextBean.setQuestionsAreaDisplay(false);
    nextBean.setChoicesAreaDisplay(false);
    nextBean.setMainEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setDetailEditMode(WebConstantCode.DISPLAY_EDIT);
    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012005";
  }

}
