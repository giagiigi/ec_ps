package jp.co.sint.webshop.web.action.back.catalog;

import java.util.Date; // 10.1.4 10144 追加

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.DateUtil; // 10.1.4 10144 追加
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommoditySkuPriceUpdateAction extends WebBackAction<CommoditySkuBean> {

  private static final String UNIT_PRICE = "unit";

  private static final String DISCOUNT_PRICE = "discount";

  private static final String RESERVATION_PRICE = "reservation";

  private static final String CHANGE_UNIT_PRICE = "change";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CommoditySkuBean reqBean = getBean();
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    String mode = getRequestParameter().getPathArgs()[0];

    boolean hasValue = true;
    boolean hasPeriod = true;
    boolean isValid = true;
    String updatePrice = "";

    // 更新対象の価格のみバリデーションチェック(必須 & 数値チェック)
    if (UNIT_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getUnitPricePrimaryAll());
      isValid = validateItems(reqBean, "unitPricePrimaryAll");
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.0");
      reqBean.setDiscountPriceAll("");
      reqBean.setChangeUnitPriceAll("");
      reqBean.setReservationPriceAll("");
    } else if (DISCOUNT_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getDiscountPriceAll());
      isValid = validateItems(reqBean, "discountPriceAll");
      hasPeriod = StringUtil.hasValueAnyOf(reqBean.getDiscountPriceStartDatetime(), reqBean.getDiscountPriceEndDatetime());
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.1");
      reqBean.setUnitPricePrimaryAll("");
      reqBean.setChangeUnitPriceAll("");
      reqBean.setReservationPriceAll("");
    } else if (RESERVATION_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getReservationPriceAll());
      isValid = validateItems(reqBean, "reservationPriceAll");
      // 10.1.4 10144 修正 ここから
      // hasPeriod = StringUtil.hasValueAnyOf(reqBean.getReservationStartDatetime(), reqBean.getReservationEndDatetime());
      Date reservationStart = DateUtil.fromString(getBean().getReservationStartDatetime(), true);
      Date reservationEnd = DateUtil.fromString(getBean().getReservationEndDatetime(), true);
      hasPeriod = StringUtil.hasValueAllOf(getBean().getReservationStartDatetime(), getBean().getReservationEndDatetime())
                  && !(reservationStart.equals(DateUtil.getMin()) && reservationEnd.equals(DateUtil.getMin()));
      // 10.1.4 10144 修正 ここまで
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.2");
      reqBean.setDiscountPriceAll("");
      reqBean.setChangeUnitPriceAll("");
      reqBean.setUnitPricePrimaryAll("");
    } else if (CHANGE_UNIT_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getChangeUnitPriceAll());
      isValid = validateItems(reqBean, "changeUnitPriceAll");
      hasPeriod = StringUtil.hasValue(reqBean.getSalePriceChangeDatetime());
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.3");
      reqBean.setDiscountPriceAll("");
      reqBean.setUnitPricePrimaryAll("");
      reqBean.setReservationPriceAll("");
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    if (!hasValue) {
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, updatePrice));
    }
    if (!hasPeriod) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, updatePrice, updatePrice
          + Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.4")));
    }

    return hasValue && hasPeriod && isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String mode = getRequestParameter().getPathArgs()[0];

    // 更新処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String shopCode = getLoginInfo().getShopCode();
    String commodityCode = getBean().getParentCommodityCode();
    CommodityPriceType type = null;
    String price = "";
    if (UNIT_PRICE.equals(mode)) {
      type = CommodityPriceType.UNIT_PRICE;
      price = getBean().getUnitPricePrimaryAll();
    } else if (DISCOUNT_PRICE.equals(mode)) {
      type = CommodityPriceType.DISCOUNT_PRICE;
      price = getBean().getDiscountPriceAll();
    } else if (RESERVATION_PRICE.equals(mode)) {
      type = CommodityPriceType.RESERVATION_PRICE;
      price = getBean().getReservationPriceAll();
    } else if (CHANGE_UNIT_PRICE.equals(mode)) {
      type = CommodityPriceType.CHANGE_UNIT_PRICE;
      price = getBean().getChangeUnitPriceAll();
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.updatePriceAll(shopCode, commodityCode, type, NumUtil.parse(price));

    // サービスエラーの有無をチェック
    if (result.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    // 更新完了パラメータを渡し、initアクションに遷移
    setNextUrl("/app/catalog/commodity_sku/init/" + getBean().getShopCode()
        + "/" + getBean().getParentCommodityCode() + "/" + mode);

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.5");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014004";
  }

}
