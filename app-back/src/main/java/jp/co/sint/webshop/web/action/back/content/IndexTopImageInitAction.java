package jp.co.sint.webshop.web.action.back.content;

import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.content.IndexTopImageBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1100110:首页TOP内容初始表示处理
 * 
 * @author KS.
 */
public class IndexTopImageInitAction extends IndexTopImageBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.INDEX_TOP_IMAGE_READ.isGranted(getLoginInfo());
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
    String[] param = getRequestParameter().getPathArgs();

    IndexTopImageBean bean = new IndexTopImageBean();

    if (param.length < 1) {
      bean.setLanguageCode(LanguageCode.Zh_Cn.getValue());
      bean.setList(getDetailList(LanguageCode.Zh_Cn.getValue()));
    } else if (param.length < 2) {
      bean.setLanguageCode(param[0]);
      bean.setList(getDetailList(bean.getLanguageCode()));
    } else if (param.length < 3) {
      bean.setLanguageCode(param[0]);
      bean.setList(getDetailList(bean.getLanguageCode()));
      if ("update".equals(param[1])) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "图片信息"));
      } else if ("upload".equals(param[1])) {
        addInformationMessage("图片已上传。");
      }
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定?初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    IndexTopImageBean bean = (IndexTopImageBean) getRequestBean();

    // 更新権限チェック
    if (Permission.INDEX_TOP_IMAGE_UPDATE.isGranted(login)) {
      bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.content.IndexTopImageInitAction.0");
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
