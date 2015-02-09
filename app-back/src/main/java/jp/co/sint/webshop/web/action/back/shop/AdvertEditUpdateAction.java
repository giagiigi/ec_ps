package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.AdvertType;
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.SiteManagementServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.AdvertEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1050110:サイト設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AdvertEditUpdateAction extends WebBackAction<AdvertEditBean> {

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
    AdvertEditBean bean = getBean();
    Advert advert;
    if (bean.getAdvertType().equals(AdvertType.CUSTOMER_REGISTER_COMPLETE.getValue())) {
      advert = siteSvc.getAdvert(NumUtil.toLong(bean.getCustomerAdvertType()));
    } else {
      advert = siteSvc.getAdvert(NumUtil.toLong(bean.getOrderAdvertType()));
    }

    // データのコピー
    advert.setAdvertEnabledFlg(NumUtil.toLong(bean.getEnableFlg()));
    advert.setAdvertText(bean.getAdvertText());
    advert.setUpdatedDatetime(bean.getUpdateDatetime());
    
    ServiceResult serviceResult = siteSvc.updateAdvert(advert);
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
      setNextUrl("/app/shop/advert_edit/init/" + WebConstantCode.COMPLETE_UPDATE);
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
    return Messages.getString("web.action.back.shop.AdvertEditUpdateAction.0");
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
