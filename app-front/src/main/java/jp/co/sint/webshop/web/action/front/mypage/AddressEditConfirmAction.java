package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2030320:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressEditConfirmAction extends AddressEditBaseAction {

  // バリデーション結果
  private boolean successValidate = true;

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    successValidate = super.validate();
    if (StringUtil.getLength(getBean().getAddressLastName()) > 28) {
      addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
          .getString("web.action.front.customer.CustomerEdit1NextAction.7")));
      successValidate = false;
    }
    // add by V10-CH 170 start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // getBean().setCityList(s.getCityNames(getBean().getPrefectureCode()));
    // 20120108 os013 add start
    getBean().setAddressScript(s.createAddressScript());
    getBean().setPrefectureList(s.createPrefectureList());
    getBean().setCityList(s.createCityList(getBean().getPrefectureCode()));
    getBean().setAreaList(s.createAreaList(getBean().getPrefectureCode(), getBean().getCityCode()));
    // 20120903 add by yyq
    if (getBean().getAreaList().size() == 1) {
      getBean().setAreaList(null);
    }
    if(getBean().getAreaList() != null){
      if (getBean().getAreaList().size() > 1 && StringUtil.isNullOrEmpty(getBean().getAreaCode())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.front.order.AddressMoveAction.0")));
        successValidate = false;
      }
    }
    // 20120903 add by yyq
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
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // 都道府県名を取得
    // bean.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
    // 20120108 os013 add start
    // 都道府県名を取得
    bean.setAddress1(s.getPrefectureName(bean.getPrefectureCode()));
    // 市区町村
    bean.setAddress2(s.getCityName(bean.getPrefectureCode(), bean.getCityCode()));
    // 町名・番地
    bean.setAddress3(s.getAreaName(bean.getAreaCode()));
    // 20120108 os013 add end

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * テキスト入力:HIDDEN<br>
   * 次へボタン:非表示/戻るボタン:表示<br>
   * アドレス帳番号が存在する:登録ボタン:非表示, 更新ボタン:表示<br>
   * アドレス帳番号が存在する:登録ボタン:表示, 更新ボタン:非表示
   */
  @Override
  public void prerender() {
    if (!successValidate) {
      return;
    }

    AddressEditBean nextBean = (AddressEditBean) getRequestBean();

    nextBean.setEditMode(WebConstantCode.DISPLAY_HIDDEN);
    nextBean.setDisplayNextButtonFlg(false);
    if (StringUtil.hasValue(nextBean.getAddressNo())) {
      nextBean.setDisplayRegisterButtonFlg(false);
      nextBean.setDisplayUpdateButtonFlg(true);
    } else {
      nextBean.setDisplayRegisterButtonFlg(true);
      nextBean.setDisplayUpdateButtonFlg(false);
    }
    nextBean.setDisplayBackButtonFlg(true);
    nextBean.setDisplayListBackButtonFlg(false);
  }
}
