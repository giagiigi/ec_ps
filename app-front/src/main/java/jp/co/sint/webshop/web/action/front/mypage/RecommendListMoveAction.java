package jp.co.sint.webshop.web.action.front.mypage;

import java.util.Date;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.RecommendListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030820:おすすめ商品のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RecommendListMoveAction extends WebFrontAction<RecommendListBean> {

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

    // 顧客の存在/退会済みチェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (service.isNotFound(getLoginInfo().getCustomerCode()) || service.isInactive(getLoginInfo().getCustomerCode())) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    String shopCode = "00000000";
    String commodityCode = "";
    String nextParam = "";

    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      nextParam = StringUtil.coalesce(parameter[0], "");
      //if (parameter.length > 1) {
      //  shopCode = StringUtil.coalesce(parameter[1], "");
      //}
      if (parameter.length > 1) {
        commodityCode = StringUtil.coalesce(parameter[1], "");
      }
    }

    // 次画面URL設定
    if (nextParam.equals("shop")) {
      // ショップ存在チェック
      if (StringUtil.isNullOrEmpty(shopCode)) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
      ShopManagementService shopSv = ServiceLocator.getShopManagementService(getLoginInfo());
      Shop shop = shopSv.getShop(shopCode);

      if (shop == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.mypage.RecommendListMoveAction.0")));
        setRequestBean(getBean());

        return FrontActionResult.RESULT_SUCCESS;
      } else {
        Date openDatetime = shop.getOpenDatetime();
        Date closeDatetime = shop.getCloseDatetime();
        if (openDatetime == null) {
          openDatetime = DateUtil.getMin();
        }
        if (closeDatetime == null) {
          closeDatetime = DateUtil.getMax();
        }
        closeDatetime = DateUtil.setHour(closeDatetime, 23);
        closeDatetime = DateUtil.setMinute(closeDatetime, 59);
        closeDatetime = DateUtil.setSecond(closeDatetime, 59);
        if (openDatetime.after(DateUtil.getSysdate()) || closeDatetime.before(DateUtil.getSysdate())) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.RecommendListMoveAction.0")));
          setRequestBean(getBean());

          return FrontActionResult.RESULT_SUCCESS;
        }
      }
      setNextUrl("/app/info/shopinfo/init/" + shopCode);
    } else if (nextParam.equals("commoditydetail")) {
      // 商品存在チェック
      if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
      CatalogService catalogSv = ServiceLocator.getCatalogService(getLoginInfo());
      CommodityInfo info = catalogSv.getCommodityInfo(shopCode, commodityCode);
      if (info == null || info.getHeader() == null || info.getDetail() == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.mypage.RecommendListMoveAction.1")));
        setRequestBean(getBean());

        return FrontActionResult.RESULT_SUCCESS;
      }
      setNextUrl("/app/catalog/detail/init/" + commodityCode);
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    // 前画面情報設定
    DisplayTransition.add(null, "/app/mypage/recommend_list/init", getSessionContainer());

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
