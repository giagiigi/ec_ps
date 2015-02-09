package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.GoogleAnalysis;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.GoogleAnalyticsSettingBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * @author System Integrator Corp.
 */
public class GoogleAnalyticsSettingInitAction extends WebBackAction<GoogleAnalyticsSettingBean> {

  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login) || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login));
  }

  @Override
  public WebActionResult callService() {
    GoogleAnalyticsSettingBean reqBean = new GoogleAnalyticsSettingBean();

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    GoogleAnalysis googleanalysis = service.getGoogleAnalysis();

    reqBean.setEnableFlg(NumUtil.toString(googleanalysis.getEnabledFlg()));
    reqBean.setScriptText(googleanalysis.getScriptText());
    reqBean.setUpdatedDatetime(googleanalysis.getUpdatedDatetime());

    setRequestBean(reqBean);

    String parameter = "";

    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }

    if (parameter.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.back.shop.GoogleAnalyticsSettingInitAction.0")));
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {

    return true;
  }
  
  
  
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    GoogleAnalyticsSettingBean requestBean = (GoogleAnalyticsSettingBean) getRequestBean();
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
    return Messages.getString("web.action.back.shop.GoogleAnalyticsSettingInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "252023";
  }
  
  
  

}
