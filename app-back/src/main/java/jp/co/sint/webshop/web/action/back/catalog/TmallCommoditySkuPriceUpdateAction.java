package jp.co.sint.webshop.web.action.back.catalog;


import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean;
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
public class TmallCommoditySkuPriceUpdateAction extends TmallCommoditySkuBaseAction {

  private static final String UNIT_PRICE = "unit";

  private static final String DISCOUNT_PRICE = "discount";

  private static final String SUGGESTE_PRICE="suggeste";

  private static final String PURCHASE_PRICE="purchase";
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
    TmallCommoditySkuBean reqBean = getBean();
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    String mode = getRequestParameter().getPathArgs()[0];

    boolean hasValue = true;
    boolean hasPeriod = true;
    boolean isValid = true;
    String updatePrice = "";

    /** update by os014 2012-01-20 begin*/
    // 更新対象の価格のみバリデーションチェック(必須 & 数値チェック)
    if (UNIT_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getUnitPriceAll());
      isValid = validateItems(reqBean, "unitPriceAll");
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.9");
      reqBean.setDiscountPriceAll("");
      reqBean.setSuggestePriceAll("");
      reqBean.setPurchasePriceAll("");
    } else if (SUGGESTE_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getSuggestePriceAll());
      isValid = validateItems(reqBean, "suggestePriceAll");
      //hasPeriod = StringUtil.hasValueAnyOf(reqBean.getDiscountPriceStartDatetime(), reqBean.getDiscountPriceEndDatetime());
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.6");
      reqBean.setUnitPriceAll("");
      reqBean.setDiscountPriceAll("");
      reqBean.setPurchasePriceAll("");
    } else if (DISCOUNT_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getDiscountPriceAll());
      isValid = validateItems(reqBean, "discountPriceAll");
      // 10.1.4 10144 修正 ここから
      // hasPeriod = StringUtil.hasValueAnyOf(reqBean.getReservationStartDatetime(), reqBean.getReservationEndDatetime());
      hasPeriod = StringUtil.hasValueAnyOf(reqBean.getDiscountPriceStartDatetime(), reqBean.getDiscountPriceEndDatetime());
//      hasPeriod = StringUtil.hasValueAllOf(getBean().getReservationStartDatetime(), getBean().getReservationEndDatetime())
//                  && !(reservationStart.equals(DateUtil.getMin()) && reservationEnd.equals(DateUtil.getMin()));
      // 10.1.4 10144 修正 ここまで
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.8");
      reqBean.setSuggestePriceAll("");
      reqBean.setUnitPriceAll("");
      reqBean.setPurchasePriceAll("");
    } else if (PURCHASE_PRICE.equals(mode)) {
      hasValue = StringUtil.hasValue(reqBean.getPurchasePriceAll());
      isValid = validateItems(reqBean, "purchasePriceAll");
//      hasPeriod = StringUtil.hasValue(reqBean.getSalePriceChangeDatetime());
      updatePrice = Messages.getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.7");
      reqBean.setDiscountPriceAll("");
      reqBean.setSuggestePriceAll("");
      reqBean.setUnitPriceAll("");
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    /** update by os014 2012-01-20 end*/
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
      price = getBean().getUnitPriceAll();
    } else if (DISCOUNT_PRICE.equals(mode)) {
      type = CommodityPriceType.DISCOUNT_PRICE;
      price = getBean().getDiscountPriceAll();
    } else if (SUGGESTE_PRICE.equals(mode)) {
      type = CommodityPriceType.SUGGESTE_PRICE;
      price = getBean().getSuggestePriceAll();
    } else if (PURCHASE_PRICE.equals(mode)) {
      type = CommodityPriceType.PURCHASE_PRICE;
      price = getBean().getPurchasePriceAll();
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.updateCcommodityPriceAll(shopCode, commodityCode, type, NumUtil.parse(price));

    // サービスエラーの有無をチェック
    if (result.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    }

    // 更新完了パラメータを渡し、initアクションに遷移
//    setNextUrl("/app/catalog/tmall_commodity_sku/init/" + getBean().getShopCode()
//        + "/" + getBean().getParentCommodityCode() + "/" + mode);
 // 商品SKU画面へ遷移する
    setNextUrl("/app/catalog/tmall_commodity_sku/init" + "/" + commodityCode + "/" + shopCode + "/" +mode);
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
