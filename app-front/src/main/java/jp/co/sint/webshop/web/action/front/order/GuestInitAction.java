package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.UIBean;
import jp.co.sint.webshop.web.bean.front.order.GuestBean;

/**
 * U2020210:ゲスト情報入力のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GuestInitAction extends WebFrontAction<GuestBean> {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    GuestBean bean;
    UIBean tmpBean = getSessionContainer().getTempBean();
    if (tmpBean instanceof GuestBean) {
      bean = (GuestBean) tmpBean;
    } else {
      bean = new GuestBean();
    }
    setBean(bean);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;

    String shopCode = getPathInfo(0);
    if (StringUtil.isNullOrEmpty(shopCode)) {
      valid = false;
    }

    Cart cart = getSessionContainer().getCart();
    if (cart == null) {
      valid = false;
    } else {
      List<CartItem> cartList = cart.get(shopCode);
      if (cartList.size() <= 0 && cart.getReserve(getPathInfo(1), getPathInfo(2)) == null) {
        valid = false;
      }
    }
    if (!valid) {
      setNextUrl("/app/common/index");
    }
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    GuestBean bean = getBean();
    bean.setSelectShopCode(getPathInfo(0));
    bean.setSelectReserveSkuSet(new Sku(getPathInfo(1), getPathInfo(2)));
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // 20120109 shen update start
    // getBean().getGuestOwner().setOwnerCityList(s.getCityNames(getBean().getGuestOwner().getOwnerPrefecture()));
    getBean().getGuestOwner().setAddressScript(s.createAddressScript());
    getBean().getGuestOwner().setOwnerPrefectureList(s.createPrefectureList());
    getBean().getGuestOwner().setOwnerCityList(s.createCityList(getBean().getGuestOwner().getOwnerPrefecture()));
    getBean().getGuestOwner().setOwnerAreaList(
        s.createAreaList(getBean().getGuestOwner().getOwnerPrefecture(), getBean().getGuestOwner().getOwnerCityCode()));
    // 20120109 shen update end

    // 20120112 shen add start
    Cart cart = getSessionContainer().getCart();
    bean.setCartItem(cart.get(bean.getSelectShopCode()));
    BigDecimal totalCommodityPrice = BigDecimal.ZERO;
    BigDecimal totalCommodityWeight = BigDecimal.ZERO;
    for (CartItem item : cart.get(bean.getSelectShopCode())) {
      totalCommodityPrice = BigDecimalUtil.add(totalCommodityPrice, BigDecimalUtil.multiply(item.getRetailPrice(), item
          .getQuantity()));
      totalCommodityWeight = BigDecimalUtil
          .add(totalCommodityWeight, BigDecimalUtil.multiply(item.getWeight(), item.getQuantity()));
    }
    bean.setTotalCommodityPrice(totalCommodityPrice);
    bean.setTotalCommodityWeight(totalCommodityWeight);
    // 20120112 shen add end

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }
}
