package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.OnlineService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.OnlineserviceEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1051010:在线客服のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class OnlineserviceEditInsertAction extends WebBackAction<OnlineserviceEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login) || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login));
//    return Permission.ONlINE_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean validation = true;
    validation = validateBean(getBean());
    

    if (getBean().getEnableFlg().equals("1")) {
      if (getBean().getScriptText1().isEmpty()) {
        validation = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.SCRIPT_TEXT1,
            Messages.getString("web.action.back.shop.OnlineserviceEditInsertAction.0")));
      }
      if (getBean().getScriptText2().isEmpty()) {
        validation = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.SCRIPT_TEXT2,
            Messages.getString("web.action.back.shop.OnlineserviceEditInsertAction.0")));
      }
      if (getBean().getScriptText3().isEmpty()) {
        validation = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.SCRIPT_TEXT3,
            Messages.getString("web.action.back.shop.OnlineserviceEditInsertAction.0")));
      }
    }

    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    OnlineserviceEditBean bean = getBean();
    //modify by V10-CH start
    BackLoginInfo login = getLoginInfo();
    OnlineService onlineService = service.getOnlineService(login.getShopCode());
    //modify by V10-Ch end
    onlineService.setEnabledFlg(Long.valueOf(bean.getEnableFlg()));
    onlineService.setScriptText1(bean.getScriptText1());
    onlineService.setScriptText2(bean.getScriptText2());
    onlineService.setScriptText3(bean.getScriptText3());
    //Add by V10-CH start
    onlineService.setOnlineServiceNo(bean.getOnline_service_no());
    onlineService.setShopCode(bean.getShopCode());
    //add by V10-CH end
    onlineService.setUpdatedDatetime(bean.getUpdatedDatetime());
    
    //判断如果在线中有该店铺的信息则执行修改，没有则添加
    //modify by V10-CH start
    ServiceResult result = null;
    boolean isHaveonline = service.isHaveOnline(onlineService.getShopCode());
    if(isHaveonline){
       result = service.updateOnlineService(onlineService);
    }else{
       result = service.insertOnlineService(onlineService);
    }
    //modify by V10-CH end
    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/onlineservice_edit/init/insert");
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.OnlineserviceEditInsertAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105031004";
  }

}
