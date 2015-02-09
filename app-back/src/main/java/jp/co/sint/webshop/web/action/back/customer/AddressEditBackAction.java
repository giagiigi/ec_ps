package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.AddressEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030220:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressEditBackAction extends AddressEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 更新権限チェック
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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AddressEditBean bean = getBean();
    //20120109 os013 add start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setAddressScript(s.createAddressScript());
    bean.setPrefectureList(s.createPrefectureList());
    bean.setCityList(s.createCityList(bean.getPrefectureCode()));
    bean.setAreaList(s.createAreaList(bean.getPrefectureCode(), bean.getCityCode()));
    //20120109 os013 add end
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * テキスト入力:編集可能/登録ボタン:表示<br>
   * 初期画面の場合は、戻るボタンは常に非表示
   */
  @Override
  public void prerender() {
    AddressEditBean nextBean = (AddressEditBean) getRequestBean();

    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);

    nextBean.setDisplayNextButtonFlg(true);
    nextBean.setDisplayRegisterButtonFlg(false);
    nextBean.setDisplayUpdateButtonFlg(false);
    nextBean.setDisplayBackButtonFlg(false);
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.AddressEditBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103022001";
  }

}
