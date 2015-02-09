package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * 免邮促销一览迁移处理
 * 
 * @author Kousen.
 */
public class FreePostageListMoveAction extends WebBackAction<FreePostageListBean> {

  @Override
  public boolean authorize() {
    return true;
  }

  @Override
  public boolean validate() {
    String mode = getPathInfo(0);
    if (StringUtil.isNullOrEmpty(mode)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    } else if (!(mode.equals("register") || mode.equals("edit"))) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    return true;
  }

  @Override
  public WebActionResult callService() {

    if (getPathInfo(0).equals("register")) {
      setNextUrl("/app/communication/free_postage_edit");
    } else if (getPathInfo(0).equals("edit")) {
      setNextUrl("/app/communication/free_postage_edit/select/" + getPathInfo(1));
    }

    DisplayTransition.add(getBean(), "/app/communication/free_postage_list/search_back", getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 取得指定參數值
   * 
   * @param index
   *          下標
   * @return 值
   */
  protected String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FreePostageListMoveAction.0");
  }

  public String getOperationCode() {
    return "5106131004";
  }
}
