package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public class OptionalCampaignEditInitAction extends OptionalCampaignEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    
    OptionalCampaignEditBean bean = new OptionalCampaignEditBean();

    bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    bean.setDisplayLoginButtonFlg(true);
    bean.setDisplayUpdateButtonFlg(false);
    bean.setDisRelatedButtonFlg(false);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "任意活动详细画面初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106072001";
  }

  public boolean authorize() {

    BackLoginInfo login = getLoginInfo();

    return Permission.OPTIONAL_CAMPAGIN_READ_SHOP.isGranted(login) && getConfig().isOne();
  }

  public boolean validate() {
    return true;
  }

}
