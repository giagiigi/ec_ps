package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

public class GiftCardRuleListMoveAction extends GiftCardRuleListBaseAction {

  @Override
  public boolean authorize() {
    return Permission.GIFT_CARD_RULE_READ_SHOP.isGranted(getLoginInfo());
  }

  @Override
  public WebActionResult callService() {
    if (getPathInfo(0).equals("register")) {
      setNextUrl("/app/communication/gift_card_rule_edit");
    } else if (getPathInfo(0).equals("edit")) {
      setNextUrl("/app/communication/gift_card_rule_edit/init/" + getPathInfo(1));
    }
    DisplayTransition.add(getBean(), "/app/communication/gift_card_rule_list/search/", getSessionContainer());
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

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleListMoveAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106141009";
  }

}
