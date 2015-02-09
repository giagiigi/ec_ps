package jp.co.sint.webshop.web.action.back.content;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.content.IndexFloorBean;
import jp.co.sint.webshop.web.bean.back.content.IndexFloorBean.IndexFloorDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1100210:首页楼层内容更新处理
 * 
 * @author KS.
 */
public class IndexFloorUpdateAction extends IndexFloorBaseAction {

  public boolean authorize() {
    return Permission.INDEX_FLOOR_UPDATE.isGranted(getLoginInfo());
  }

  public WebActionResult callService() {
    if (getRequestParameter().getPathArgs().length == 1) {
      setTitle();
      setNextUrl("/app/content/index_floor/select/" + getBean().getLanguageCode() + "/updateTitle");
    } else {
      String imgName = getRequestParameter().getPathArgs()[1];
      boolean resultFlg = setImgInfo(imgName);
      if (!resultFlg) {
        addErrorMessage("更新失败！");
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }

      setNextUrl("/app/content/index_floor/select/" + getBean().getLanguageCode() + "/update");
    }

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  public boolean validate() {
    String[] param = getRequestParameter().getPathArgs();
    IndexFloorDetailBean editBean = getBean().getEditBean();
    if (param.length == 2) {
      editBean.setImgLink(getRequestParameter().get("imgLink" + param[0]));
      editBean.setImgAlt(getRequestParameter().get("imgAlt" + param[0]));
    } else if (param.length < 1) {
      throw new URLNotFoundException();
    }
    return validateBean(editBean);
  }

  /**
   * 画面表示に必要な項目を設定?初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    IndexFloorBean bean = (IndexFloorBean) getRequestBean();

    // 更新権限チェック
    if (Permission.INDEX_FLOOR_UPDATE.isGranted(login)) {
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
    return Messages.getString("web.bean.back.content.IndexFloorUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109021002";
  }

}
