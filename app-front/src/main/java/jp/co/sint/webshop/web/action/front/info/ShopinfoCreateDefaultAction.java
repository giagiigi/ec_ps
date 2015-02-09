package jp.co.sint.webshop.web.action.front.info;

import java.util.Date;

import jp.co.sint.webshop.data.domain.ShopType;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.ShopinfoBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * U2050210:ショップ情報のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopinfoCreateDefaultAction extends WebFrontAction<ShopinfoBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    // 10.1.6 10283 修正 ここから
    // addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    // return false;
    throw new URLNotFoundException();
    // 10.1.6 10283 修正 ここまで
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String shopCode = getRequestParameter().getPathArgs()[0];

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = service.getShop(shopCode);

    // ショップ情報が存在しなかった場合エラー
    if (shop == null) {
      // 10.1.6 10283 修正 ここから
      // throw new NoSuchShopException();
      throw new URLNotFoundException();
      // 10.1.6 10283 修正 ここまで
    }

    // ショップが開店していなかった場合エラー
    Date closeDatetime = shop.getCloseDatetime();
    if (closeDatetime == null) {
      closeDatetime = DateUtil.getMax();
    }
    closeDatetime = DateUtil.setHour(closeDatetime, 23);
    closeDatetime = DateUtil.setMinute(closeDatetime, 59);
    closeDatetime = DateUtil.setSecond(closeDatetime, 59);
    if (!DateUtil.isPeriodDate(shop.getOpenDatetime(), closeDatetime)) {
      // 10.1.6 10283 修正 ここから
      // throw new NoSuchShopException();
      throw new URLNotFoundException();
      // 10.1.6 10283 修正 ここまで
    }

    if (DIContainer.getWebshopConfig().isOne()) {
      setNextUrl("/app/info/siteinfo");
    } else if (ShopType.fromValue(shop.getShopType()) == ShopType.SITE) {
      setNextUrl("/app/info/siteinfo");
    }

    ShopinfoBean bean = new ShopinfoBean();
    bean.setShopInfo(shop);

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

}
