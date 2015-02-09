package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader; // 10.1.6 10259 追加
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.bean.front.mypage.FavoriteListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030810:お気に入り商品のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class FavoriteListAddcartAction extends WebFrontAction<FavoriteListBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] params = getRequestParameter().getPathArgs();
    if (params.length < 3) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    String shopCode = params[0];
    String commodityCode = params[1];
    String skuCode = params[2];
    int quantity = 1;
    
    // 2012/12/19 促销对应 ob add start
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String commodityName = "";
    CommodityHeader commodityHeader = catalogService.getCommodityHeader(shopCode, commodityCode);
    if (commodityHeader == null) {
      commodityName = Messages.getString("web.action.front.mypage.FavoriteListAddcartAction.0");
    } else {
      commodityName = commodityHeader.getCommodityName();
    }
    
    List<CommodityCompositionContainer> compositionContainerList = new ArrayList<CommodityCompositionContainer>();
    
    if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityHeader.getSetCommodityFlg())) {
      compositionContainerList = getCompositionContainerList(shopCode, commodityCode);

      for (CommodityCompositionContainer child : compositionContainerList) {
        String compositionSkuCode = getStockOfAllSku(shopCode, child.getCommodityDetailList(), catalogService);
        skuCode = skuCode + ":" + compositionSkuCode;
      }
    }
    
    // 2012/12/19 促销对应 ob add end
    
    CartItem cartItem = getCart().get(shopCode, skuCode);
    if (cartItem == null) {
      cartItem = getCart().getReserve(shopCode, skuCode);
    }
    if (cartItem != null) {
      quantity = cartItem.getQuantity() + quantity;
    }

    // 2012/12/20 促销对应 ob update start
//    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
//    boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);
//    CommodityAvailability commodityAvailability = catalogService.isAvailable(shopCode, skuCode, quantity, isReserve);
//
//    // 10.1.6 10259 修正 ここから
//    //String commodityName = catalogService.getCommodityHeader(shopCode, commodityCode).getCommodityName();
//    String commodityName = "";
//    CommodityHeader commodityHeader = catalogService.getCommodityHeader(shopCode, commodityCode);
//    if (commodityHeader == null) {
//      commodityName = Messages.getString("web.action.front.mypage.FavoriteListAddcartAction.0");
//    } else {
//      commodityName = commodityHeader.getCommodityName();
//    }
//    // 10.1.6 10259 修正 ここまで
//    if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
//      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_DATA_ERROR, commodityName));
//    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
//      addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, commodityName));
//    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
//      addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityName));
//    } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
//      addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, String.valueOf(catalogService
//          .getAvailableStock(shopCode, skuCode))));
//    } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
//      addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_RESERVATION_OVER, commodityName, NumUtil.toString(catalogService
//          .getReservationAvailableStock(shopCode, skuCode))));
//    } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
//      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, commodityName));
//    } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
//      return true;
//    }
    
    if (checkAvailable(shopCode, commodityCode, skuCode, commodityHeader.getCommodityType(), commodityHeader
        .getSetCommodityFlg(),NumUtil.toLong(Integer.toString(quantity)), commodityName, true)) {
      
      return true;
    }
    // 2012/12/20 促销对应 ob update end
    
    

    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] params = getRequestParameter().getPathArgs();

    String shopCode = params[0];
    String skuCode = params[2];

    CartBean nextBean = new CartBean();
    List<String> errorMessageList = new ArrayList<String>();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    
    // 2012/12/19 促销对应 ob add start
    String commodityCode = params[1];
    CommodityHeader commodityHeader = service.getCommodityHeader(shopCode, commodityCode);
    List<CommodityCompositionContainer> compositionContainerList = new ArrayList<CommodityCompositionContainer>();

    if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityHeader.getSetCommodityFlg())) {
      compositionContainerList = getCompositionContainerList(shopCode, commodityCode);

      for (CommodityCompositionContainer child : compositionContainerList) {
        String compositionSkuCode = getStockOfAllSku(shopCode, child.getCommodityDetailList(), service);
        skuCode = skuCode + ":" + compositionSkuCode;
      }
    }
    // 2012/12/19 促销对应 ob add end

    int quantity = 0;
    if (getCart().get(shopCode, skuCode) != null) {
      quantity = getCart().get(shopCode, skuCode).getQuantity();
    } else if (getCart().getReserve(shopCode, skuCode) != null) {
      quantity = getCart().getReserve(shopCode, skuCode).getQuantity();
    }
    // 20111214 shen add start
    // if (!getCart().set(shopCode, skuCode, quantity + 1)) {
    if (!getCart().set(shopCode, skuCode, quantity + 1, getLoginInfo().getCustomerCode())) {
    // 20111214 shen add end
      CommodityInfo commodityInfo = service.getSkuInfo(shopCode, skuCode);
      errorMessageList.add(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityInfo.getHeader()
          .getCommodityName()));
    }

    // 2012/12/19 促销对应 ob add start
    // 套餐明细设定
    if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityHeader.getSetCommodityFlg())) {
      setComposition(shopCode, skuCode, commodityCode);
    }
    
    // 赠品促销活动的赠品信息设定
    setCampaignGiftInfo(shopCode, commodityCode, skuCode, String.valueOf(quantity));
    
    // 2012/12/19 促销对应 ob add end
    
    nextBean.setErrorMessageList(errorMessageList);

    setRequestBean(nextBean);
    setNextUrl("/app/cart/cart/init");

    return FrontActionResult.RESULT_SUCCESS;
  }

}
