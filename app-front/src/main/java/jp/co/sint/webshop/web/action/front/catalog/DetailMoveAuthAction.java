package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.exception.NoSuchCommodityException;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
//import jp.co.sint.webshop.web.message.front.catalog.CatalogErrorMessage; // 10.1.7 10308 削除
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2040510:商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailMoveAuthAction extends DetailBaseAction {

  /** モード定数:レビューをつけるボタンクリック時 */
  private static final String MODE_REVIEW = "review";

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParams = getRequestParameter().getPathArgs();

    if (urlParams.length < 2) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] urlParams = getRequestParameter().getPathArgs();
    CommodityDetailBean bean = getBean();

    String shopCode = "00000000";
    String commodityCode = urlParams[0];
    bean.setShopCode(shopCode);
    bean.setCommodityCode(commodityCode);

    // 商品情報の取得
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodity = catalogService.getCommodityHeader(shopCode, commodityCode);
    if (commodity == null || commodity.getCommodityCode() == null) {
      throw new NoSuchCommodityException(shopCode, commodityCode);
    }

    if (urlParams[1].equals(MODE_REVIEW)) {
      // 顧客の同一商品への投稿回数チェック
      CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
      //20111220 os013 add start
      //orderNo等于0表示没有订单号
      String orderNo="0";
      //if (service.isAlreadyPostReview(commodity.getShopCode(), commodity.getCommodityCode(), getLoginInfo().getCustomerCode())) {
      if (service.isAlreadyPostReview(commodity.getShopCode(), commodity.getCommodityCode(), getLoginInfo().getCustomerCode(),orderNo)) {
      // 20111220 os013 add end 
        // 10.1.7 10308 修正 ここから
        // addErrorMessage(WebMessage.get(CatalogErrorMessage.DUPLICATED_REVIEW_ERROR));
        setNextUrl("/app/catalog/detail/complete/" + commodityCode + "/" + DUPLICATED_REVIEW_POST);
        // 10.1.7 10308 修正 ここまで
      } else {
        // 前画面情報を設定
        String path = "/app/catalog/detail/init/" + commodity.getCommodityCode();
        DisplayTransition.add(getBean(), path, getSessionContainer());

        setNextUrl("/app/catalog/review_edit/init/" + commodityCode);
      }
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    createCommodityInfo(bean);
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }
}
