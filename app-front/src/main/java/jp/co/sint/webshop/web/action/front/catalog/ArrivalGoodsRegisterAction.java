package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ArrivalGoodsBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2040710:入荷お知らせ登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsRegisterAction extends WebFrontAction<ArrivalGoodsBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ArrivalGoodsBean reqBean = getBean();

    ArrivalGoods arrivalGoods = new ArrivalGoods();
    arrivalGoods.setShopCode(reqBean.getShopCode());
    arrivalGoods.setSkuCode(reqBean.getSkuCode());
    arrivalGoods.setEmail(reqBean.getEmail());

    if (getLoginInfo().isLogin()) {
      arrivalGoods.setCustomerCode(getLoginInfo().getCustomerCode());
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.insertArrivalGoods(arrivalGoods);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.front.catalog.ArrivalGoodsRegisterAction.0")));
          setRequestBean(reqBean);
          setNextUrl(null);
          return FrontActionResult.RESULT_SUCCESS;
        }
      }
      return FrontActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/catalog/detail/complete/" + reqBean.getShopCode() + "/" + reqBean.getCommodityCode()
        + "/register_arrival_goods");

    setRequestBean(reqBean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 入荷お知らせ登録可能かどうかのチェック
    // 通常商品(公開されている)、入荷お知らせ受け付け可、在庫管理する、有効在庫が0
    ArrivalGoodsBean reqBean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = service.getSkuInfo(reqBean.getShopCode(), reqBean.getSkuCode());

    boolean result = true;

    if (!service.isListed(reqBean.getShopCode(), reqBean.getCommodityCode())) {
      result &= false;
    }

    if (service.isReserve(reqBean.getShopCode(), reqBean.getSkuCode())) {
      result &= false;
    }

    if (commodityInfo.getHeader().getArrivalGoodsFlg().equals(ArrivalGoodsFlg.UNACCEPTABLE.longValue())) {
      result &= false;
    }

    if (commodityInfo.getHeader().getStockManagementType().equals(StockManagementType.NONE.longValue())) {
      result &= false;
    }

    if (commodityInfo.getHeader().getStockManagementType().equals(StockManagementType.NOSTOCK.longValue())) {
      result &= false;
    }

    Long stockQuantity = service.getAvailableStock(reqBean.getShopCode(), reqBean.getSkuCode());
    if (stockQuantity == null || stockQuantity >= 1) {
      result &= false;
    }

    if (!result) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.ARRIVAL_GOODS_ERROR));
      return false;
    }

    result &= validateBean(getBean());

    return result;
  }

}
