package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ShopManagementServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.ShopEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050220:ショップマスタ登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopEditUpdateAction extends WebBackAction<ShopEditBean> {

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
    boolean authlization = false;
    BackLoginInfo loginInfo = getLoginInfo();

    if (Permission.SITE_MANAGER.isGranted(loginInfo)) {
      authlization = true;
    } else if (Permission.SHOP_MANAGER.isGranted(loginInfo)) {
      authlization = true;
    } else if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(loginInfo)
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(loginInfo)) {
      authlization = true;
    } else {
      authlization = false;
    }

    return authlization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShopEditBean shopEditBean = getBean();
    Shop shop = new Shop();
    shop.setShopCode(shopEditBean.getShopCode());
    shop.setOpenDatetime(DateUtil.fromString(shopEditBean.getOpenDatetime()));
    shop.setCloseDatetime(DateUtil.fromString(shopEditBean.getCloseDatetime()));
    shop.setShopName(shopEditBean.getShopName());
    shop.setShortShopName(shopEditBean.getShortShopName());
    shop.setPostalCode(shopEditBean.getPostCode());
    shop.setPrefectureCode(shopEditBean.getPrefectureCode());
    shop.setAddress1(shopEditBean.getAddress1());
    shop.setAddress2(shopEditBean.getAddress2());
    shop.setAddress3(shopEditBean.getAddress3());
    shop.setAddress4(shopEditBean.getAddress4());
    // modify by V10-CH 170 start
    String phoneNumber = "";
    if(StringUtil.hasValueAllOf(shopEditBean.getTel1(),shopEditBean.getTel2(),shopEditBean.getTel3())){
      phoneNumber = StringUtil.joint('-', shopEditBean.getTel1(), shopEditBean.getTel2(), shopEditBean.getTel3());
    }else if(StringUtil.hasValueAllOf(shopEditBean.getTel1(),shopEditBean.getTel2())){
      phoneNumber = StringUtil.joint('-', shopEditBean.getTel1(), shopEditBean.getTel2());
    }
//    String phoneNumber = shopEditBean.getTel1();
    shop.setCityCode(shopEditBean.getCityCode());
    // modify by V10-CH 170 start
    shop.setPhoneNumber(phoneNumber);
    //Add by V10-CH start
    String mobileNumber = shopEditBean.getMobileTel();
    shop.setMobileNumber(mobileNumber);
    //Add by V10-CH end
    shop.setEmail(shopEditBean.getEmail());
    shop.setPersonInCharge(shopEditBean.getPersonInCharge());
    shop.setShopIntroducedUrl(shopEditBean.getShopIntroducedUrl());
    shop.setUpdatedDatetime(shopEditBean.getUpdatedDate());
    shop.setCustomerCancelableFlg(shopEditBean.getCustomerCancelableFlg());
    shop.setShopType(1L);
    shop.setIcpCode(Messages
        .getString("web.action.back.shop.ShopEditConfirmAction.2"));
    
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    ServiceResult result = service.updateShop(shop);

    ShopEditBean nextBean = shopEditBean;
    // 登録処理の成功チェック

    if (result.hasError()) {
      nextBean.setNextButtonDisplayFlg(true);
      nextBean.setRegisterButtonDisplayFlg(false);
      nextBean.setUpdateButtonDisplayFlg(false);
      nextBean.setBackButtonDisplayFlg(false);

      nextBean.setDisplay(WebConstantCode.DISPLAY_EDIT);
      setNextUrl(null);
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(ShopManagementServiceErrorContent.SHOP_NO_DEF_FOUND_ERROR)) {
          addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.shop.ShopEditUpdateAction.0")));

          setRequestBean(nextBean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;

    } else {
      setNextUrl("/app/shop/shop_edit/select/" + shopEditBean.getShopCode() + "/" + WebConstantCode.COMPLETE_UPDATE);
    }
    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.ShopEditUpdateAction.1");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105022007";
  }

}
