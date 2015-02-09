package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050220:ショップマスタ登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopEditConfirmAction extends WebBackAction<ShopEditBean> {

  // バリデーション結果
  private boolean successValidate = true;

  @Override
  public void init() {
    if (getLoginInfo().isShop()) {
      getBean().setShopCode(getLoginInfo().getShopCode());
    }
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (Permission.SITE_MANAGER.isGranted(loginInfo)) {
      authorization = true;
    } else if (Permission.SHOP_MANAGER.isGranted(loginInfo)) {
      authorization = true;
    } else if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(loginInfo)
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(loginInfo)) {
      authorization = true;
    } else {
      authorization = false;
    }

    return authorization;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShopEditBean requestBean = getBean();
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    Shop shop = service.getShop(requestBean.getShopCode());

    // 登録時に該当するショップコードのデータがDBにあった場合はエラー
    if (shop != null && !requestBean.isUpdateProcessFlg()) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
          .getString("web.action.back.shop.ShopEditConfirmAction.0")));
      setRequestBean(requestBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    ShopEditBean nextBean = getBean();

    nextBean.setShopCode(requestBean.getShopCode());
    nextBean.setOpenDatetime(requestBean.getOpenDatetime());
    nextBean.setCloseDatetime(requestBean.getCloseDatetime());
    nextBean.setShopName(requestBean.getShopName());
    nextBean.setShortShopName(requestBean.getShortShopName());
    nextBean.setPostCode(requestBean.getPostCode());
    nextBean.setAddress3(requestBean.getAddress3());
    nextBean.setAddress4(requestBean.getAddress4());
    nextBean.setTel1(requestBean.getTel1());
    // modify by V10-CH 170 start
    // nextBean.setPrefectureCode(requestBean.getPrefectureCode());
    // nextBean.setAddress1(PrefectureCode.fromValue(requestBean.getPrefectureCode()).getName());
    // nextBean.setAddress2(requestBean.getAddress2());
    nextBean.setPrefectureCode(requestBean.getPrefectureCode());
    nextBean.setCityCode(requestBean.getCityCode());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    nextBean.setAddress1(PrefectureCode.fromValue(requestBean.getPrefectureCode()).getName());
    nextBean.setAddress2(s.getCityName(requestBean.getPrefectureCode(), requestBean.getCityCode()));
    nextBean.setIcpCode(Messages.getString("web.action.back.shop.ShopEditConfirmAction.2"));

    nextBean.setTel2(requestBean.getTel2());
    nextBean.setTel3(requestBean.getTel3());
    // modify by V10-CH 170 end
    nextBean.setEmail(requestBean.getEmail());
    nextBean.setPersonInCharge(requestBean.getPersonInCharge());
    nextBean.setShopIntroducedUrl(requestBean.getShopIntroducedUrl());
    nextBean.setUpdateProcessFlg(requestBean.isUpdateProcessFlg());
    nextBean.setCustomerCancelableFlg(requestBean.getCustomerCancelableFlg());

    // 10.1.6 10271 追加 ここから
    nextBean.setDisplay(WebConstantCode.DISPLAY_HIDDEN);
    nextBean.setBackButtonDisplayFlg(true);
    if (nextBean.isUpdateProcessFlg()) {
      nextBean.setRegisterButtonDisplayFlg(false);
      nextBean.setUpdateButtonDisplayFlg(true);
    } else {
      nextBean.setRegisterButtonDisplayFlg(true);
      nextBean.setUpdateButtonDisplayFlg(false);
    }
    nextBean.setNextButtonDisplayFlg(false);
    nextBean.setDeleteButtonDisplayFlg(false);
    // 10.1.6 10271 追加 ここまで

    setRequestBean(nextBean);

    addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  // 10.1.6 10271 削除 ここから
  // /**
  // * 画面表示に必要な項目を設定・初期化します。
  // */
  // @Override
  // public void prerender() {
  // // 10.1.5 K00192 修正 ここから
  // // if (!successValidate) {
  // if (!validate()) {
  // // 10.1.5 K00192 修正 ここまで
  // return;
  // }
  // ShopEditBean nextBean = (ShopEditBean) getRequestBean();
  //
  // nextBean.setDisplay(WebConstantCode.DISPLAY_HIDDEN);
  // nextBean.setBackButtonDisplayFlg(true);
  // if (nextBean.isUpdateProcessFlg()) {
  // nextBean.setRegisterButtonDisplayFlg(false);
  // nextBean.setUpdateButtonDisplayFlg(true);
  // } else {
  // nextBean.setRegisterButtonDisplayFlg(true);
  // nextBean.setUpdateButtonDisplayFlg(false);
  // }
  // nextBean.setNextButtonDisplayFlg(false);
  // nextBean.setDeleteButtonDisplayFlg(false);
  // }
  // 10.1.6 10271 削除 ここまで
  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    successValidate = validateBean(getBean());
    // modify by V10-CH start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().setCityList(s.getCityNames(getBean().getPrefectureCode()));
    PhoneValidator validator = new PhoneValidator();

    if (StringUtil.isNullOrEmpty(getBean().getTel1()) && StringUtil.isNullOrEmpty(getBean().getTel2())
        && StringUtil.isNullOrEmpty(getBean().getTel3()) && StringUtil.isNullOrEmpty(getBean().getMobileTel())) {
      addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
      successValidate = false;
    } else {
      boolean phoneResult = validator.isValid(StringUtil.joint('-', getBean().getTel1(), getBean().getTel2(), getBean().getTel3()));
      if (!phoneResult) {
        addErrorMessage(validator.getMessage());
      }
      successValidate &= phoneResult;
    }
    // modify by V10-CH end
    return successValidate;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopEditConfirmAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105022002";
  }

}
