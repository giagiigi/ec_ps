package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.OnlineService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.OnlineserviceEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;


/**
 * U1051010:在线客服のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OnlineserviceEditInitAction extends WebBackAction<OnlineserviceEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login) || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login));
//    return Permission.ONlINE_READ_SITE.isGranted(getLoginInfo());
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

    OnlineserviceEditBean reqBean = new OnlineserviceEditBean();

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    BackLoginInfo login = getLoginInfo();
    OnlineService onlineService = service.getOnlineService(login.getShopCode());
    if(!(onlineService.getOrmRowid()==-1)){
      reqBean.setEnableFlg(Long.toString(onlineService.getEnabledFlg()));
      reqBean.setScriptText1(onlineService.getScriptText1());
      reqBean.setScriptText2(onlineService.getScriptText2());
      reqBean.setScriptText3(onlineService.getScriptText3());
      //Add by V10-CH start
      reqBean.setOnline_service_no(onlineService.getOnlineServiceNo());
      reqBean.setShopCode(getLoginInfo().getShopCode());
      //Add by V10-CH end
      reqBean.setUpdatedDatetime(onlineService.getUpdatedDatetime());
      setRequestBean(reqBean);
    }else{
      reqBean.setEnableFlg(Long.toString(onlineService.getEnabledFlg()));
      reqBean.setShopCode(onlineService.getShopCode());
      setRequestBean(reqBean);
    }
    
    String parameter = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }

    if (parameter.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.OnlineserviceEditInitAction.0")));
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OnlineserviceEditBean requestBean = (OnlineserviceEditBean) getRequestBean();
    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      requestBean.setDisplay(WebConstantCode.DISPLAY_EDIT);
      requestBean.setRegisterButtonFlg(true);
    } else {
      requestBean.setDisplay(WebConstantCode.DISPLAY_HIDDEN);
      requestBean.setRegisterButtonFlg(false);
    }
    setRequestBean(requestBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.OnlineserviceEditInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105031003";
  }

}
