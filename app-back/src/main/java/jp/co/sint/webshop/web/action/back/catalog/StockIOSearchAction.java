package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.StockIOSearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.StockIOBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040510:入出庫管理のアクションクラスです
 *
 * @author System Integrator Corp.
 */
public class StockIOSearchAction extends StockIOBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   *
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.STOCK_MANAGEMENT_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   *
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    StockIOBean reqBean = getBean();
    if (!validateBean(reqBean)) {
      result = false;
    }

    if (StringUtil.hasValueAllOf(getBean().getSearchStockIODateStart(), getBean().getSearchStockIODateEnd())) {
      if (!StringUtil.isCorrectRange(getBean().getSearchStockIODateStart(), getBean().getSearchStockIODateEnd())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        result = false;
      }
    }
    return result;
  }

  /**
   * アクションを実行します。
   *
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    StockIOBean reqBean = getBean();
    StockIOBean nextBean = new StockIOBean();

    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の設定
    StockIOSearchCondition condition = setSearchCondition(reqBean, nextBean);

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = service.getSkuInfo(nextBean.getSearchShopCode(), nextBean.getSearchSkuCode());

    if (commodityInfo == null) {
      addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "SKU"));
      setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;

    }

    nextBean.setCommodityName(getCommodityName(commodityInfo));
    nextBean.setStockManagementType(NumUtil.toString(commodityInfo.getHeader().getStockManagementType()));
    StockManagementType type = StockManagementType.fromValue(commodityInfo.getHeader().getStockManagementType());
    if (type != null) {
      nextBean.setStockManagenentTypeName(type.getName());
    }
    nextBean.setStockQuantity(commodityInfo.getStock().getStockQuantity());
    nextBean.setAllocatedQuantity(commodityInfo.getStock().getAllocatedQuantity());
    nextBean.setAvailableStockQuantity(service.getAvailableStock(nextBean.getSearchShopCode(), nextBean.getSearchSkuCode()));
    nextBean.setReservedQuantity(commodityInfo.getStock().getReservedQuantity());
    nextBean.setReservationLimit(commodityInfo.getStock().getReservationLimit());
    nextBean.setOneshotReservationLimit(commodityInfo.getStock().getOneshotReservationLimit());
    nextBean.setStockThreshold(commodityInfo.getStock().getStockThreshold());

    // AppScan対策のため、旧検索条件を保持
    nextBean.setOldSearchStockIODateStart(reqBean.getSearchStockIODateStart());
    nextBean.setOldSearchStockIODateEnd(reqBean.getSearchStockIODateEnd());

    // 入出庫履歴の取得
    List<StockIODetail> stockIOList = getStockIOList(nextBean, condition);
    if (stockIOList.isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    // 画面表示用Beanの作成
    createNextBean(nextBean, stockIOList);

    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    StockIOBean stockIOBean = (StockIOBean) getRequestBean();
    setDisplayControl(stockIOBean);
  }

  /**
   * Action名の取得
   *
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockIOSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   *
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104051004";
  }

}
