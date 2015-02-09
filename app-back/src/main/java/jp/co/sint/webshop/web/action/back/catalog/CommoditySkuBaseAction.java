package jp.co.sint.webshop.web.action.back.catalog;

import java.util.Date;

import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean.CommoditySkuDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;

/**
 * U1040140:商品SKUのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CommoditySkuBaseAction extends WebBackAction<CommoditySkuBean> {

  public static final String COMPLETE_FILE_DELETE = "fileDelete";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public abstract boolean validate();

  public boolean validatePeriod(CommoditySkuDetailBean sku) {
    boolean isValid = true;

    isValid = validateBean(sku);

    // 特別価格が入力された場合は、特別価格期間が設定されているかチェック
    if (StringUtil.hasValue(sku.getDiscountPrice())) {
      if (!StringUtil.hasValueAnyOf(getBean().getDiscountPriceStartDatetime(), getBean().getDiscountPriceEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR,
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.0"),
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.1")));
        isValid = false;
      }
    }

    // 予約価格が入力された場合は、予約期間が設定されているかチェック
    // 10.1.4 10144 修正 ここから
//    if (StringUtil.hasValue(sku.getReservationPrice())) {
//      if (!StringUtil.hasValueAnyOf(getBean().getReservationStartDatetime(), getBean().getReservationEndDatetime())) {
    Date reservationStart = DateUtil.fromString(getBean().getReservationStartDatetime(), true);
    Date reservationEnd = DateUtil.fromString(getBean().getReservationEndDatetime(), true);
    if (StringUtil.hasValue(sku.getReservationPrice())) {
      if (StringUtil.isNullOrEmptyAnyOf(getBean().getReservationStartDatetime(), getBean().getReservationEndDatetime())
          || (reservationStart.equals(DateUtil.getMin()) && reservationEnd.equals(DateUtil.getMin()))) {
    // 10.1.4 10144 修正 ここまで
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR,
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.2"),
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.3")));
        isValid = false;
      }
    }

    // 改定価格が入力された場合は、価格改定日が設定されているかチェック
    if (StringUtil.hasValue(sku.getChangeUnitPrice())) {
      if (StringUtil.isNullOrEmpty(getBean().getSalePriceChangeDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR,
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.4"),
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.5")));
        isValid = false;
      }
    }

    // 予約上限数、注文毎予約上限数の大小チェック
    if (StringUtil.hasValueAllOf(sku.getOneshotReservationLimit(), sku.getReservationLimit())) {
      if (!ValidatorUtil.lessThanOrEquals(NumUtil.toLong(sku.getOneshotReservationLimit()), NumUtil.toLong(sku
          .getReservationLimit()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.6")));
        isValid = false;
      }
    }

    // 特別価格期間が設定されている場合は、特別価格の必須チェック
    if (StringUtil.hasValueAnyOf(getBean().getDiscountPriceStartDatetime(), getBean().getDiscountPriceEndDatetime())) {
      if (StringUtil.isNullOrEmpty(sku.getDiscountPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR,
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.1"),
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.0")));
        isValid = false;
      }
    }

    // 予約期間が設定されている場合は、予約価格の必須チェック
    // 10.1.4 10144 修正 ここから
//    Date reservationStart = DateUtil.fromString(getBean().getReservationStartDatetime(), true);
//    Date reservationEnd = DateUtil.fromString(getBean().getReservationEndDatetime(), true);
//    if (StringUtil.hasValueAnyOf(getBean().getReservationStartDatetime(), getBean().getReservationEndDatetime())
//        && !(reservationStart.equals(DateUtil.getMin()) && reservationEnd.equals(DateUtil.getMin()))) {
    if (StringUtil.hasValueAllOf(getBean().getReservationStartDatetime(), getBean().getReservationEndDatetime())
        && !(reservationStart.equals(DateUtil.getMin()) && reservationEnd.equals(DateUtil.getMin()))) {
    // 10.1.4 10144 修正 ここまで
      if (StringUtil.isNullOrEmpty(sku.getReservationPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR,
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.3"),
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.2")));
        isValid = false;
      }
    }

    // 価格改定日が設定されている場合は、改定価格の必須チェック
    if (StringUtil.hasValue(getBean().getSalePriceChangeDatetime())) {
      if (StringUtil.isNullOrEmpty(sku.getChangeUnitPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR,
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.5"),
            Messages.getString("web.action.back.catalog.CommoditySkuBaseAction.4")));
        isValid = false;
      }
    }

    return isValid;
  }

}
