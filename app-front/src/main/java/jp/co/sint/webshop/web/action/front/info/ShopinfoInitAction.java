package jp.co.sint.webshop.web.action.front.info;

import java.util.Date;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.ShopinfoBean;
// import jp.co.sint.webshop.web.exception.NoSuchShopException; // 10.1.6 10283
// 削除
import jp.co.sint.webshop.web.exception.URLNotFoundException; // 10.1.6 10283 追加

/**
 * U2050210:ショップ情報のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ShopinfoInitAction extends WebFrontAction<ShopinfoBean> {

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
    // throw new NoSuchShopException();
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
    // URLパラメータの[0]にショップコードが入る
    String shopCode = getRequestParameter().getPathArgs()[0];

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = service.getShop(shopCode);

    // shopCodeに該当するショップが無い場合エラー
    if (shop == null) {
      // 10.1.6 10283 修正 ここから
      // throw new NoSuchShopException(shopCode);
      throw new URLNotFoundException();
      // 10.1.6 10283 修正 ここまで
    }

    // 該当するショップが現在開店中で無い場合もエラー
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
      // 10.1.6 10283 修正 ここから
      // throw new NoSuchShopException(shopCode);
      throw new URLNotFoundException();
      // 10.1.6 10283 修正 ここまで
    }

    ShopinfoBean bean = new ShopinfoBean();
    Shop shopInfo = service.getShop(shopCode);
    bean.setShopInfo(shopInfo);
    setRequestBean(bean);

    setNextUrl("/contents/shop/" + shopCode + "/info");
    return FrontActionResult.RESULT_SUCCESS;
  }
}
