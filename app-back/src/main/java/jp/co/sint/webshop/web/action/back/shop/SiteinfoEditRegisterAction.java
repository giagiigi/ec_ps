package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.domain.ShopType;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.SiteManagementServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.SiteinfoEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1050110:サイト設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SiteinfoEditRegisterAction extends WebBackAction<SiteinfoEditBean> {

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
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SiteManagementService siteSvc = ServiceLocator.getSiteManagementService(getLoginInfo());
    SiteinfoEditBean bean = getBean();
    Shop shop = siteSvc.getSite();

    // データのコピー

    shop.setShopName(bean.getSiteName());
    shop.setShortShopName(bean.getShortSiteName());
    shop.setPostalCode(bean.getPostalCode());
    // add by V10-CH 170 start
    //shop.setPrefectureCode(bean.getPrefectureCode());
//    shop.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
//    shop.setAddress2(bean.getAddress2());
    shop.setPrefectureCode(bean.getPrefectureCode());
    shop.setCityCode(bean.getCityCode());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    shop.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
    shop.setAddress2(s.getCityName(bean.getPrefectureCode(), bean.getCityCode()));
    // add by V10-CH 170 end
    shop.setAddress3(bean.getAddress3());
    shop.setAddress4(bean.getAddress4());
//  add by shikui start 2010/04/27
    shop.setIcpCode(bean.getIcpCode());
//  add by shikui end 2010/04/27  
    //modify by V10-CH 170 start  
    String phoneNumber = "";
    if(StringUtil.hasValue(bean.getPhoneNumber1())){
      if(StringUtil.hasValue(bean.getPhoneNumber3())){
        phoneNumber = StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean.getPhoneNumber3());
      }else{
        phoneNumber = StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2());
      }
    }
    //modify by V10-CH 170 start   
    shop.setPhoneNumber(phoneNumber);
    //Add by V10-CH start
    shop.setMobileNumber(bean.getMobileNumber());
    //Add by V10-CH end
    shop.setEmail(bean.getMailAddress());
    shop.setPersonInCharge(bean.getPersonInCharge());
    shop.setShopType(ShopType.SITE.longValue());
//  add by shikui start 2010/04/27
    shop.setUpdatedDatetime(bean.getUpdateDatetime());
//  add by shikui end 2010/04/27    
    shop.setCustomerCancelableFlg(bean.getCustomerCancelableFlg());
    shop.setShopIntroducedUrl(bean.getShopIntroducedUrl());
    // サービス処理実行
    ServiceResult serviceResult = siteSvc.updateSite(shop);
    // サービス処理結果チェック
    if (serviceResult.hasError()) {
      for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー

          return BackActionResult.SERVICE_VALIDATION_ERROR;

        } else if (errorContent.equals(SiteManagementServiceErrorContent.SITE_NO_DEF_FOUND_ERROR)) {
          // サイト未登録エラー

          Logger logger = Logger.getLogger(this.getClass());
          logger.warn(errorContent.toString());
          return BackActionResult.SERVICE_ERROR;

        } else {
          // その他エラー
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setRequestBean(getBean());
      setNextUrl(null);
    } else {
      setNextUrl("/app/shop/siteinfo_edit/init/" + WebConstantCode.COMPLETE_REGISTER);
    }

    return BackActionResult.RESULT_SUCCESS;
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
    return Messages.getString("web.action.back.shop.SiteinfoEditRegisterAction.0");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105011004";
  }

}
