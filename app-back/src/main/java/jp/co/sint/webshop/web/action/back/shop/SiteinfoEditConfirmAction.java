package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.SiteinfoEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050110:サイト設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SiteinfoEditConfirmAction extends WebBackAction<SiteinfoEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // add by V10-CH 170 start
    boolean result = validateBean(getBean());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().setCityList(s.getCityNames(getBean().getPrefectureCode()));
    
    if(StringUtil.isNullOrEmpty(getBean().getPhoneNumber1()) && StringUtil.isNullOrEmpty(getBean().getPhoneNumber2())
        && StringUtil.isNullOrEmpty(getBean().getPhoneNumber3()) && StringUtil.isNullOrEmpty(getBean().getMobileNumber())){
        addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
        result = false;
    }else{
      PhoneValidator phoneValidator = new PhoneValidator();
      boolean phoneResult = false;
      phoneResult = phoneValidator.isValid(StringUtil.joint('-', getBean().getPhoneNumber1(), getBean().getPhoneNumber2(), getBean()
          .getPhoneNumber3()));
      if (!phoneResult) {
        addErrorMessage(phoneValidator.getMessage());
      }
      result &= phoneResult;
    }
//    else{
//      if(StringUtil.hasValue(getBean().getPhoneNumber1()) || StringUtil.hasValue(getBean().getPhoneNumber2()) || StringUtil.hasValue(getBean().getPhoneNumber3())){
//        if(!(StringUtil.hasValueAllOf(getBean().getPhoneNumber1(),getBean().getPhoneNumber2()) && getBean().getPhoneNumber1().length()>1 && getBean().getPhoneNumber2().length()>5)){
//          addErrorMessage(WebMessage.get(ValidationMessage.TRUE_NUMBER));
//          result = false;
//        }
//      }
//    }
    // add by V10-CH 170 end
    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    SiteinfoEditBean bean = getBean();

    // 権限チェック
    bean.setRegisterButtonDisplay(true);
    bean.setConfirmButtonDisplay(false);
    bean.setBackButtonDisplay(true);

    bean.setReadonlyMode(WebConstantCode.DISPLAY_READONLY);
    setRequestBean(bean);

    addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.SiteinfoEditConfirmAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105011002";
  }

}
