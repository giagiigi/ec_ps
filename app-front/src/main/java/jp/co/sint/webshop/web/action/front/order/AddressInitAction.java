package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.AddressBean;
import jp.co.sint.webshop.web.bean.front.order.AddressBean.CustomerAddressBean;

/**
 * U2020210:ゲスト情報入力のアクションクラスです
 * 
 * @author Kousen.
 */
public class AddressInitAction extends WebFrontAction<AddressBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    AddressBean bean = getBean();
    if (bean == null) {
      setNextUrl("/app/common/index");
      return false;
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
    AddressBean bean = getBean();
    CustomerAddressBean address = bean.getAddress();
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo cust = service.getCustomer(bean.getCustomerCode());
    if(cust.getCustomer() !=null){
      bean.setCustomerName(cust.getCustomer().getLastName());
    }
    
    bean.setAddressScript(s.createAddressScript());
    address.setAddressPrefectureList(s.createPrefectureList());
    address.setAddressCityList(s.createCityList(address.getAddressPrefectureCode()));
    address.setAddressAreaList(s.createAreaList(address.getAddressPrefectureCode(), address.getAddressCityCode()));

    Cart cart = getSessionContainer().getCart();
    bean.setCartItem(cart.get(bean.getShopCode()));
    BigDecimal totalCommodityPrice = BigDecimal.ZERO;
    BigDecimal totalCommodityWeight = BigDecimal.ZERO;
    for (CartItem item : cart.get(bean.getShopCode())) {
      totalCommodityPrice = BigDecimalUtil.add(totalCommodityPrice, BigDecimalUtil.multiply(item.getRetailPrice(), item
          .getQuantity()));
      totalCommodityWeight = BigDecimalUtil
          .add(totalCommodityWeight, BigDecimalUtil.multiply(item.getWeight(), item.getQuantity()));
    }
    bean.setTotalCommodityPrice(totalCommodityPrice);
    bean.setTotalCommodityWeight(totalCommodityWeight);

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

}
