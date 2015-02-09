package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.ArrivalGoodsBean;
import jp.co.sint.webshop.web.exception.NoSuchCommodityException;

/**
 * U2040710:入荷お知らせ登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsInitAction extends WebFrontAction<ArrivalGoodsBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    ArrivalGoodsBean reqBean = new ArrivalGoodsBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    reqBean.setShopCode(urlParam[0]);
    reqBean.setCommodityCode(urlParam[1]);
    reqBean.setSkuCode(urlParam[2]);

    // 商品存在チェック
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = catalogService.getCommodityInfo(reqBean.getShopCode(), reqBean.getCommodityCode());
    CommodityInfo skuInfo = catalogService.getSkuInfo(reqBean.getShopCode(), reqBean.getSkuCode());
    if (commodityInfo == null || skuInfo == null) {
      throw new NoSuchCommodityException(reqBean.getShopCode(), reqBean.getSkuCode());
    }

    if (getLoginInfo().isLogin()) {
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      CustomerInfo customerInfo = service.getCustomer(getLoginInfo().getCustomerCode());

      if (customerInfo.getCustomer() != null) {
        reqBean.setEmail(customerInfo.getCustomer().getEmail());
      }
    }

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
    if (getRequestParameter().getPathArgs().length >= 3) {
      return true;
    }
    return false;
  }

}
