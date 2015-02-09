package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050220:ショップマスタ登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopEditSelectAction extends WebBackAction<ShopEditBean> {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
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

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = false;
    } else if (Permission.SITE_MANAGER.isGranted(loginInfo)) {
      authorization = true;
    } else if (Permission.SHOP_MANAGER.isGranted(loginInfo)) {
      authorization = true;
    } else if (Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(loginInfo)
        || Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(loginInfo)) {
      authorization = true;
    } else {
      authorization = false;
    }

    // ショップコード存在チェック
    if (getRequestParameter().getPathArgs().length < 1) {
      return false;
    }
    if (getLoginInfo().isShop()) {
      String shopCode = getRequestParameter().getPathArgs()[0];
      authorization &= shopCode.equals(getLoginInfo().getShopCode());
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

    String shopCode = "";

    // 一店舗モード、又はショップユーザの場合
    BackLoginInfo loginInfo = getLoginInfo();
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE) || Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(loginInfo)) {
      shopCode = loginInfo.getShopCode();
    } else {
      if (getRequestParameter().getPathArgs().length > 0) {
        if (!getRequestParameter().getPathArgs()[0].equals(WebConstantCode.COMPLETE_REGISTER)) {
          shopCode = getRequestParameter().getPathArgs()[0];
        }
      }
    }

    ShopEditBean shopEditBean = new ShopEditBean();

    // getPathArgs()の[0]にはショップコードが入る
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shopData = service.getShop(shopCode);

    shopEditBean.setShopCode(shopData.getShopCode());
    shopEditBean.setOpenDatetime(DateUtil.toDateString(shopData.getOpenDatetime()));
    if (shopData.getCloseDatetime() != null && !shopData.getCloseDatetime().equals(DateUtil.getMax())) {
      shopEditBean.setCloseDatetime(DateUtil.toDateString(shopData.getCloseDatetime()));
    }
    shopEditBean.setShopName(shopData.getShopName());
    shopEditBean.setShortShopName(shopData.getShortShopName());
    shopEditBean.setPostCode(shopData.getPostalCode());
    shopEditBean.setPrefectureCode(shopData.getPrefectureCode());
    shopEditBean.setAddress1(shopData.getAddress1());
    shopEditBean.setAddress2(shopData.getAddress2());
    shopEditBean.setAddress3(shopData.getAddress3());
    shopEditBean.setAddress4(shopData.getAddress4());
    shopEditBean.setEmail(shopData.getEmail());
    // add by V10-CH 170 start
    if(StringUtil.hasValue(shopData.getPhoneNumber())){
    String[] phoneNumber = shopData.getPhoneNumber().split("-");
    if (phoneNumber.length == 2) {
      shopEditBean.setTel1(phoneNumber[0]);
      shopEditBean.setTel2(phoneNumber[1]);
    }else if(phoneNumber.length == 3){
      shopEditBean.setTel1(phoneNumber[0]);
      shopEditBean.setTel2(phoneNumber[1]);
      shopEditBean.setTel3(phoneNumber[2]);
    }
    }else{
      shopEditBean.setTel1("");
      shopEditBean.setTel2("");
      shopEditBean.setTel3("");
    }
    //Add by V10-CH start
    if(StringUtil.hasValue(shopData.getMobileNumber())){
      shopEditBean.setMobileTel(shopData.getMobileNumber());
    }else{
      shopEditBean.setMobileTel("");
    }
    //Add by V10-CH end
//    if (StringUtil.hasValue(shopData.getPhoneNumber())) {
//      shopEditBean.setTel1(shopData.getPhoneNumber());
//    }
    shopEditBean.setCityCode(shopData.getCityCode());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    shopEditBean.setCityList(s.getCityNames(shopEditBean.getPrefectureCode()));
    // add by V10-CH 170 end
    shopEditBean.setPersonInCharge(shopData.getPersonInCharge());
    shopEditBean.setShopIntroducedUrl(shopData.getShopIntroducedUrl());
    shopEditBean.setUpdatedDate(shopData.getUpdatedDatetime());
    shopEditBean.setUpdateProcessFlg(true);
    shopEditBean.setCustomerCancelableFlg(shopData.getCustomerCancelableFlg());

    if (getRequestParameter().getPathArgs().length == 2) {
      if (getRequestParameter().getPathArgs()[1].equals(WebConstantCode.COMPLETE_REGISTER)) {
        addWarningMessage(WebMessage.get(CompleteMessage.SHOP_REGISTER_COMPLETE, shopEditBean.getShopName()));
      } else if (getRequestParameter().getPathArgs()[1].equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.shop.ShopEditSelectAction.0")));
      }
    }

    setRequestBean(shopEditBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo loginInfo = getLoginInfo();
    ShopEditBean requestBean = (ShopEditBean) getRequestBean();
    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(loginInfo)) {
      // 新規登録時は削除ボタンを表示しない
      if (!requestBean.isUpdateProcessFlg()) {
        requestBean.setDeleteButtonDisplayFlg(false);
      } else {
        requestBean.setDeleteButtonDisplayFlg(true);
      }
    }

    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(loginInfo) 
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(loginInfo)) {
      requestBean.setNextButtonDisplayFlg(true);
      requestBean.setDisplay(WebConstantCode.DISPLAY_EDIT);
    } else {
      requestBean.setNextButtonDisplayFlg(false);
      requestBean.setDisplay(WebConstantCode.DISPLAY_READONLY);
    }

    // ショップ管理者で、ショップ更新権限とデータIO権限を持つユーザのみコンテンツアップロード可能
    boolean isShopManager = requestBean.getShopCode().equals(loginInfo.getShopCode()) && loginInfo.isShop();
    boolean hasContentsUploadPermission = Permission.DATA_IO_ACCESS_SHOP.isGranted(loginInfo)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(loginInfo);
    requestBean.setContentsUploadFlg(isShopManager && hasContentsUploadPermission);

    requestBean.setRegisterButtonDisplayFlg(false);
    requestBean.setUpdateButtonDisplayFlg(false);
    requestBean.setBackButtonDisplayFlg(false);

    setRequestBean(requestBean);
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopEditSelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105022008";
  }

}
