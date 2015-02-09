package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * 
 */
public class GiftCardRuleListInitAction extends GiftCardRuleListBaseAction {

  @Override
  public boolean authorize() {

    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    // 没有更新和查看权限,不显示
    if (!Permission.GIFT_CARD_RULE_READ_SHOP.isGranted(login)) {
      return false;
    }
    return true;
  }

  @Override
  public WebActionResult callService() {
    GiftCardRuleListBean bean = new GiftCardRuleListBean();
    setRequestBean(bean);
    setBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    return true;
  }

  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleListInitAction.0");
  }
  
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106141008";
  }
  
  
  
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    GiftCardRuleListBean bean = getBean();

    if (Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(login)) {
      bean.setRegisterNewDisplayFlg(true);
      setRequestBean(bean);
    }
  }
  
  

}
