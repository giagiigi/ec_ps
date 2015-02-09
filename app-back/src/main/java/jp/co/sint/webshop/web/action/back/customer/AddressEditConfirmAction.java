package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.AddressEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030220:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressEditConfirmAction extends AddressEditBaseAction {

  // バリデーション結果
  private boolean successValidate = true;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 参照権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_UPDATE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    successValidate = super.validate();
    
    // add by V10-CH 170 start
    AddressEditBean bean = getBean();
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    // add by V10-CH 170 end
    // 20120108 os013 add start
    bean.setAddressScript(s.createAddressScript());
    bean.setPrefectureList(s.createPrefectureList());
    bean.setCityList(s.createCityList(bean.getPrefectureCode()));
    if(StringUtil.isNullOrEmpty(bean.getAreaCode())&& successValidate){
      bean.setAreaList(null);
    }else {
      bean.setAreaList(s.createAreaList(bean.getPrefectureCode(), bean.getCityCode()));
    }
    // 20120108 os013 add end
    return successValidate;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AddressEditBean bean = getBean();

    // 都道府県名を取得
    // modify by V10-CH 170 start
    // bean.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setPrefectureCode(bean.getPrefectureCode());
    // bean.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
    // bean.setAddress2(s.getCityName(bean.getPrefectureCode(),
    // bean.getCityCode()));
    // 20120108 os013 add start
    // 都道府県名を取得
    bean.setAddress1(s.getPrefectureName(bean.getPrefectureCode()));
    // 市区町村
    bean.setAddress2(s.getCityName(bean.getPrefectureCode(), bean.getCityCode()));
    // 町名・番地
    bean.setAddress3(s.getAreaName(bean.getAreaCode()));

    // 20120108 os013 add end

    // modify by V10-CH 170 start
    setRequestBean(bean);

    addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));

    return BackActionResult.RESULT_SUCCESS;
  }

  @Override
  public void prerender() {
    if (!successValidate) {
      return;
    }
    AddressEditBean nextBean = (AddressEditBean) getRequestBean();
    nextBean.setEditMode(WebConstantCode.DISPLAY_HIDDEN);

    if (StringUtil.hasValue(nextBean.getAddressNo())) {
      nextBean.setDisplayUpdateButtonFlg(true);
      nextBean.setDisplayRegisterButtonFlg(false);
    } else {
      nextBean.setDisplayUpdateButtonFlg(false);
      nextBean.setDisplayRegisterButtonFlg(true);
    }
    nextBean.setDisplayBackButtonFlg(true);
    nextBean.setDisplayNextButtonFlg(false);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.AddressEditConfirmAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103022002";
  }

}
