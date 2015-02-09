package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1061210:限时限量折扣编辑画面商品登录表示处理
 * 
 * @author System Integrator Corp.
 */
public class DiscountEditCommodityRegisterAction extends DiscountEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.DISCOUNT_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    DiscountEditBean bean = getBean();

    boolean validation = validateItems(bean, "relatedComdtyCode");

    if (validation) {
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

      CommodityHeader ch = catalogService.getCommodityHeader(getConfig().getSiteShopCode(), bean.getRelatedComdtyCode());

      if (ch == null) {
        // 商品不存在
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品" + bean.getRelatedComdtyCode()));
        validation = false;
      }

      if (validation) {

        if (!ch.getCommodityType().equals(CommodityType.GENERALGOODS.longValue())
            || (ch.getSetCommodityFlg() != null && ch.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue()))) {
          addErrorMessage("请选择普通商品登录。");
          validation = false;
        }

        DiscountCommodity dc = new DiscountCommodity();
        CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
        dc = communicationService.getDiscountCommodity(bean.getDiscountCode(), bean.getRelatedComdtyCode());
        if (dc != null) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_DISCOUNT_COMMODITY_ERROR, bean.getDiscountCode(),
              bean.getRelatedComdtyCode()));
          validation = false;
        } else {
          String discountName = catalogService.getLoginDiscountCommodityName(DateUtil.fromString(bean.getDiscountStartDatetime()
              + ":00", true), DateUtil.fromString(bean.getDiscountEndDatetime() + ":00", true), bean.getRelatedComdtyCode());
          if (StringUtil.hasValue(discountName)) {
            addErrorMessage("该商品在活动【" + discountName + "】已经登录。");
            validation = false;
          }
        }
      }
    }
    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    DiscountEditBean bean = getBean();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    DiscountCommodity dc = new DiscountCommodity();
    dc.setDiscountCode(bean.getDiscountCode());
    dc.setCommodityCode(bean.getRelatedComdtyCode());

    ServiceResult result = communicationService.insertDiscountCommodity(dc);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_DISCOUNT_COMMODITY_ERROR, bean.getDiscountCode(),
              bean.getRelatedComdtyCode()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    setNextUrl("/app/communication/discount_edit/select/" + bean.getDiscountCode() + "/commodityLogin");
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.DiscountEditCommodityRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106122004";
  }

}
