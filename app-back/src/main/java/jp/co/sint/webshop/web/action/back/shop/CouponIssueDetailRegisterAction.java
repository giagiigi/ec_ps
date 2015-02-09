package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CouponIssueDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1050520:支払方法詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponIssueDetailRegisterAction extends WebBackAction<CouponIssueDetailBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CouponIssueDetailBean bean = getBean();
    boolean isValid = validateBean(bean);
    if (!ValidatorUtil.isCorrectOrder(bean.getBonusCouponStartDate(), bean.getBonusCouponEndDate())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.2"), Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.3")));
      isValid &= false;
    }
    if (!ValidatorUtil.isCorrectOrder(bean.getUseCouponStartDate(), bean.getUseCouponEndDate())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.4"), Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.5")));
      isValid &= false;
    }
    if (!ValidatorUtil.isCorrectOrder(bean.getBonusCouponStartDate(), bean.getUseCouponStartDate())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.2"), Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.4")));
      isValid &= false;
    }
    if (!ValidatorUtil.isCorrectOrder(bean.getBonusCouponEndDate(), bean.getUseCouponEndDate())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.3"), Messages
          .getString("web.action.back.shop.CouponIssueDetailRegisterAction.5")));
      isValid &= false;
    }
    return isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CouponIssueDetailBean bean = getBean();

    CouponIssue couponIssue = new CouponIssue();
    String shopCode = getLoginInfo().getShopCode();

    couponIssue.setCouponIssueNo(NumUtil.toLong(bean.getCouponIssueNo()));
    couponIssue.setShopCode(shopCode);
    couponIssue.setCouponName(bean.getCouponName());
    couponIssue.setCouponPrice(NumUtil.parse(bean.getCouponPrice()));
    couponIssue.setGetCouponPrice(NumUtil.parse(bean.getGetCouponPrice()));
    couponIssue.setBonusCouponStartDate(DateUtil.fromString(bean.getBonusCouponStartDate()));
    couponIssue.setBonusCouponEndDate(DateUtil.fromString(bean.getBonusCouponEndDate()));
    couponIssue.setUseCouponStartDate(DateUtil.fromString(bean.getUseCouponStartDate()));
    couponIssue.setUseCouponEndDate(DateUtil.fromString(bean.getUseCouponEndDate()));
    couponIssue.setUpdatedDatetime(bean.getUpdateDate());

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    Logger logger = Logger.getLogger(this.getClass());
    if (!bean.getUpdateModeFlg()) {
      ServiceResult result = service.insertCouponIssue(couponIssue);
      setRequestBean(bean);
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.shop.CouponIssueDetailRegisterAction.1")));
            return BackActionResult.RESULT_SUCCESS;
          }
        }
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
      setNextUrl("/app/shop/coupon_issue_detail/init/" + shopCode + "/" + NumUtil.toString(couponIssue.getCouponIssueNo())
          + "/new_register");

    } else if (bean.getUpdateModeFlg()) {

      ServiceResult result = service.updateCouponIssue(couponIssue);

      if (result.hasError()) {
        setNextUrl(null);
        setRequestBean(bean);
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.shop.CouponIssueDetailRegisterAction.1")));
            return BackActionResult.RESULT_SUCCESS;
          }
        }
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
      setRequestBean(bean);
      setNextUrl("/app/shop/coupon_issue_detail/init/" + shopCode + "/" + bean.getCouponIssueNo() + "/update_register");

    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CouponIssueDetailRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105052003";
  }

}
