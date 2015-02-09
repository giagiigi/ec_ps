package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.StockIOType;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.StockIOBean;

/**
 * U1040510:入出庫管理のアクションクラスです
 *
 * @author System Integrator Corp.
 */
public class StockIOInitAction extends StockIOBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   *
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.STOCK_MANAGEMENT_READ.isGranted(getLoginInfo());
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
   * アクションを実行します。
   *
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    StockIOBean reqBean = new StockIOBean();

    String[] params = getRequestParameter().getPathArgs();
    if (params.length > 0) {
      reqBean.setSearchSkuCode(params[0]);
    }

    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 初期表示時は直近7日分の履歴を表示
    reqBean.setSearchStockIODateStart(DateUtil.toDateString(DateUtil.addDate(DateUtil.getSysdate(), -7)));
    reqBean.setSearchStockIODateEnd(DateUtil.toDateString(DateUtil.getSysdate()));
    reqBean.setOldSearchStockIODateStart(DateUtil.toDateString(DateUtil.addDate(DateUtil.getSysdate(), -7)));
    reqBean.setOldSearchStockIODateEnd(DateUtil.toDateString(DateUtil.getSysdate()));

    List<String> searchStockIOTypeList = new ArrayList<String>();
    for (StockIOType stockIOType : StockIOType.values()) {
      searchStockIOTypeList.add(stockIOType.getValue());
    }
    reqBean.setSearchStockIOType(searchStockIOTypeList);

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = service.getSkuInfo(reqBean.getSearchShopCode(), reqBean.getSearchSkuCode());

    if (commodityInfo != null) {
      reqBean.setCommodityName(getCommodityName(commodityInfo));
      reqBean.setStockManagementType(NumUtil.toString(commodityInfo.getHeader().getStockManagementType()));
      StockManagementType type = StockManagementType.fromValue(commodityInfo.getHeader().getStockManagementType());
      if (type != null) {
        reqBean.setStockManagenentTypeName(type.getName());
      }
      reqBean.setStockQuantity(commodityInfo.getStock().getStockQuantity());
      reqBean.setAllocatedQuantity(commodityInfo.getStock().getAllocatedQuantity());
      reqBean.setAvailableStockQuantity(service.getAvailableStock(reqBean.getSearchShopCode(), reqBean.getSearchSkuCode()));
      reqBean.setReservedQuantity(commodityInfo.getStock().getReservedQuantity());
      reqBean.setReservationLimit(commodityInfo.getStock().getReservationLimit());
      reqBean.setOneshotReservationLimit(commodityInfo.getStock().getOneshotReservationLimit());
      reqBean.setStockThreshold(commodityInfo.getStock().getStockThreshold());
    }

    // 入出庫履歴の取得
    List<StockIODetail> stockIOList = new ArrayList<StockIODetail>();

    // 画面表示用Beanの作成
    createNextBean(reqBean, stockIOList);

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    StockIOBean stockIOBean = (StockIOBean) getRequestBean();

    // 更新処理に関する表示制御を実施
    if (NumUtil.isNull(stockIOBean.getStockQuantity())) {
      stockIOBean.setRegisterTableDisplayFlg(false);
    } else {
      if (Permission.STOCK_MANAGEMENT_UPDATE.isGranted(getLoginInfo())) {
        stockIOBean.setRegisterTableDisplayFlg(true);
      } else {
        stockIOBean.setRegisterTableDisplayFlg(false);
      }
    }

    if (Permission.STOCK_MANAGEMENT_DATA_IO.isGranted(getLoginInfo())) {
      stockIOBean.setUploadTableDisplayFlg(true);
    } else {
      stockIOBean.setUploadTableDisplayFlg(false);
    }
  }

  /**
   * Action名の取得
   *
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockIOInitAction.0");
  }

  /**
   * オペレーションコードの取得
   *
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104051002";
  }

}
