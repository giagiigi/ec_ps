package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2030320:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressEditBackAction extends AddressEditBaseAction {

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
    //20120109 os013 add start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().setAddressScript(s.createAddressScript());
    getBean().setPrefectureList(s.createPrefectureList());
    getBean().setCityList(s.createCityList(getBean().getPrefectureCode()));
    getBean().setAreaList(s.createAreaList(getBean().getPrefectureCode(), getBean().getCityCode()));
    //20120109 os013 add end
    setRequestBean(getBean());
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * テキスト入力:編集可能/次へボタン:表示<br>
   * 初期画面の場合は、登録/更新/戻るボタンは常に非表示
   */
  @Override
  public void prerender() {
    AddressEditBean nextBean = (AddressEditBean) getRequestBean();

    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setDisplayNextButtonFlg(true);
    nextBean.setDisplayRegisterButtonFlg(false);
    nextBean.setDisplayUpdateButtonFlg(false);
    nextBean.setDisplayBackButtonFlg(false);
    nextBean.setDisplayListBackButtonFlg(true);
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
