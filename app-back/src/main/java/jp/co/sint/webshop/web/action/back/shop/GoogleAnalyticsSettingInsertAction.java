package jp.co.sint.webshop.web.action.back.shop;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.dto.GoogleAnalysis;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.GoogleAnalyticsSettingBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class GoogleAnalyticsSettingInsertAction extends WebBackAction<GoogleAnalyticsSettingBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    return (Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login) || Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login));
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
      if (getBean().getScriptText().isEmpty()) {
        validation = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.SCRIPT_TEXT, Messages
            .getString("web.action.back.shop.GoogleAnalyticsSettingInsertAction.0")));
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
    GoogleAnalyticsSettingBean bean = getBean();
    ServiceResult result = null;
    GoogleAnalysis googleAnalysis = service.getGoogleAnalysis();
    
    if(googleAnalysis.getScriptText()!=null){
      googleAnalysis.setEnabledFlg(Long.valueOf(bean.getEnableFlg()));
      googleAnalysis.setScriptText(bean.getScriptText());

      googleAnalysis.setUpdatedDatetime(bean.getUpdatedDatetime());

      result = service.updateGoogleAnalysis(googleAnalysis);
    }else{
//      googleAnalysis.setEnabledFlg(Long.valueOf(bean.getEnableFlg()));
      googleAnalysis.setScriptText(bean.getScriptText());
      googleAnalysis.setEnabledFlg(Long.valueOf(bean.getEnableFlg()));
      googleAnalysis.setgoogleAnalysisNo(Long.valueOf(bean.getGoogleAnalysisNo()));
//      googleAnalysis.setUpdatedDatetime(bean.getUpdatedDatetime());
      
      result = service.insertGoogleAnalysis(googleAnalysis);
    }

    
   
    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/google_analytics_setting/init/insert");
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.GoogleAnalyticsSettingInsertAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  /*
   * (non-Javadoc)
   * 
   * @see jp.co.sint.webshop.web.action.back.WebBackAction#getOperationCode()
   */
  public String getOperationCode() {
    return "4105031004";
  }

}
