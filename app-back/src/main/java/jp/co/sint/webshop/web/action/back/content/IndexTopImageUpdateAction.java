package jp.co.sint.webshop.web.action.back.content;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.content.IndexTopImageBean;
import jp.co.sint.webshop.web.bean.back.content.IndexTopImageBean.IndexTopImageDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1100110:首页TOP内容更新处理
 * 
 * @author KS.
 */
public class IndexTopImageUpdateAction extends IndexTopImageBaseAction {

  public boolean authorize() {
    return Permission.INDEX_TOP_IMAGE_UPDATE.isGranted(getLoginInfo());
  }

  public WebActionResult callService() {
    String imgName = getRequestParameter().getPathArgs()[1];
    boolean resultFlg = setImgInfo(imgName, getBean().getEditBean());
    if (!resultFlg) {
      addErrorMessage("更新失败！");
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    setNextUrl("/app/content/index_top_image/init/" + getBean().getLanguageCode() + "/update");
    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  public boolean validate() {
    String[] param = getRequestParameter().getPathArgs();
    IndexTopImageDetailBean editBean = getBean().getEditBean();
    if (param.length == 2) {
      editBean.setImgLink(getRequestParameter().get("imgLink" + param[0]));
      editBean.setImgAlt(getRequestParameter().get("imgAlt" + param[0]));
      String order = getRequestParameter().get("imgOrder" + param[0]);
      editBean.setImgOrder(order);
      if(NumUtil.toLong(param[0]) >= 2 && NumUtil.toLong(param[0]) <= 6) {
        if(!(StringUtil.hasValue(order) && NumUtil.isNum(order) && NumUtil.toLong(order) >= 1 && NumUtil.toLong(order) <= 5)) {
          addErrorMessage("图片顺序必须为1~5之间的整数！");
          return false;
        }
      } else if(NumUtil.toLong(param[0]) >= 7 && NumUtil.toLong(param[0]) <= 9) {
        if(!(StringUtil.hasValue(order) && NumUtil.isNum(order) && NumUtil.toLong(order) >= 1 && NumUtil.toLong(order) <= 3)) {
          addErrorMessage("图片顺序必须为1~3之间的整数！");
          return false;
        }
      }
    } else {
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
    return Messages.getString("web.bean.back.content.IndexTopImageUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109011002";
  }

}
