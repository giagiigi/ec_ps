package jp.co.sint.webshop.web.action.front.cart;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.communication.DiscountInfo;
import jp.co.sint.webshop.service.communication.DiscountInfo.DiscountDetail;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean.CommodityCartBean;
import jp.co.sint.webshop.web.bean.front.order.AddressBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2020110:ショッピングカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CartMoveAction extends CartBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    List<DiscountHeader> disountHeaderList = communicationService.getActiveDiscountHeaderList();
    // 2012/11/27 促销对应 ob add start
    for (ShopCartBean shopBean : getBean().getShopCartBean()) {
      // 20140922 hdh add start 限时限量商品种类数量限制
      if(disountHeaderList!=null &&disountHeaderList.size()>0){
        for(DiscountHeader dh :disountHeaderList){
          if(StringUtil.isNullOrEmpty(dh.getCommodityTypeNum())){
            continue;
          }
          Long comodityTypeNum = 0L;
          DiscountInfo discountInfo = communicationService.getDiscountInfo(dh.getDiscountCode());
          if (discountInfo != null && discountInfo.getDetailList() != null && discountInfo.getDetailList().size() > 0) {
            for (DiscountDetail detail : discountInfo.getDetailList()) {
              for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
                if ("false".equals(commodityBean.getIsDiscountCommodity())) {  //不是限时限量折扣
                  continue;
                }
                if (commodityBean.getCommodityCode().equals(detail.getCommodityCode())) {
                  comodityTypeNum++;
                  break;
                }
              }
            }
          }
          if(dh.getCommodityTypeNum()<comodityTypeNum){
            addErrorMessage(MessageFormat.format(Messages.getString("web.action.front.cart.CartMoveAction.001"),dh.getCommodityTypeNum()));
            return false;
          }
          
        }
      }
      // 20140922 hdh  add end 限时限量商品种类数量限制
      for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
        
        if (commodityBean.getIsDiscountCommodity().equals("true")){
          //历史所有客户购买总数
          Long siteTotalAmount = catalogService.getHistoryBuyAmountTotal(commodityBean.getSkuCode());
          if (siteTotalAmount == null){
            siteTotalAmount = 0L;
          }
          String customerCode = getLoginInfo().getCustomerCode();
          if (StringUtil.hasValue(customerCode)) {
            DiscountCommodity dcBean = catalogService.getDiscountCommodityByCommodityCode(commodityBean.getSkuCode());
            //限购商品剩余可购买数量
            Long avalibleAmountTotal = dcBean.getSiteMaxTotalNum() - siteTotalAmount;
            if (avalibleAmountTotal <= 0L){
              addErrorMessage(commodityBean.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.0") );
              return false;
            }
            Long historyNum = catalogService.getHistoryBuyAmount(commodityBean.getSkuCode(), customerCode);
            if (historyNum == null){
              historyNum = 0L;
            }
            if (dcBean.getCustomerMaxTotalNum() > historyNum){
              Long num = dcBean.getCustomerMaxTotalNum() - historyNum;
              if (num > avalibleAmountTotal){
                num = avalibleAmountTotal;
              }
              if (Long.parseLong(commodityBean.getCommodityAmount()) > num ){
                addErrorMessage(commodityBean.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.1") + num + Messages.getString("web.action.front.order.DiscountCommodity.2") );
                return false;
              }
            } else {
              addErrorMessage(commodityBean.getCommodityName() + Messages.getString("web.action.front.order.DiscountCommodity.3"));
              return false ;
            }
          }
        }
        
      }
    }
    
    // 2012/11/27 促销对应 ob add end
    valid &= super.validate();

    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length < 1) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else if ((getPathInfo(0).equals("shipping") || getPathInfo(0).equals("check_shipping") && !getPathInfo(0).equals("favorite"))
        && getPathInfo(1).equals("")) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else if (!getPathInfo(0).equals("shipping") && !getPathInfo(0).equals("check_shipping") && !getPathInfo(0).equals("continue")
        && !getPathInfo(0).equals("favorite")) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
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
    //session中赠品重置  20140725 hdh
    giftReset(getCart(), "00000000");
    
    String move = getPathInfo(0);
    // 20111225 os013 add start
    // 获取会员区分，等于1是支付宝登录会员相当与游客购买
    String customerKbn = "";
    if (StringUtil.hasValue(getLoginInfo().getCustomerCode())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      CustomerInfo loginCustomer = customerService.getCustomer(getLoginInfo().getCustomerCode());
      customerKbn = "" + loginCustomer.getCustomer().getCustomerKbn();
    }
    if (customerKbn.equals("1")) {
      move = "customerEdit";
    }
    // 20111225 os013 add end
    if (move.equals("check_shipping")) {
      String url = "/app/cart/cart/move/shipping/";
      url += getPathInfo(1) + "/";
      url += getPathInfo(2) + "/";
      url += getPathInfo(3) + "/";
      setNextUrl(url);
      // 20111229 os013 add start
      // 支付宝登录用户支付前先跳转到顾客注册画面进行会员信息补足
    } else if (move.equals("customerEdit")) {
      setNextUrl("/app/customer/customer_edit1/init/cart");
      return FrontActionResult.RESULT_SUCCESS;
      // 20111229 os013 add end
    } else if (move.equals("shipping")) {
      if (getLoginInfo().isGuest()) {
        setNextUrl("/app/order/guest/init/" + getPathInfo(1) + "/" + getPathInfo(2) + "/" + getPathInfo(3));
      } else {
        // 20120104 shen add start
        CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
        CustomerInfo customer = service.getCustomer(getLoginInfo().getCustomerCode(), true);
        if (customer.getAddress() == null) {
          AddressBean requestBean = new AddressBean();
          requestBean.setCustomerCode(customer.getCustomer().getCustomerCode());
          requestBean.setShopCode(getPathInfo(1));
          setRequestBean(requestBean);
          setNextUrl("/app/order/address");
        } else {
          // 20120104 shen add end
          copyBeanToCart();
          createBeanFromCart();
          WebshopConfig config = DIContainer.getWebshopConfig();

          if (getCart().getItemCount() == 0) {
            addErrorMessage(WebMessage.get(CartDisplayMessage.NO_CART_ITEM));
            setRequestBean(getBean());
            return FrontActionResult.RESULT_SUCCESS;
          }
          boolean hasCart = true;
          if (StringUtil.hasValue(getPathInfo(2)) && StringUtil.hasValue(getPathInfo(3))) {
            if (getCart().getReserve(getPathInfo(2), getPathInfo(3)) == null) {
              hasCart = false;
            }
            if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.ONE) {
              hasCart &= config.getSiteShopCode().equals(getPathInfo(1));
            }
          } else if (config.getOperatingMode() == OperatingMode.SHOP) {
            if (getCart().get(getPathInfo(1)).isEmpty()) {
              hasCart = false;
            }
          } else {
            hasCart = config.getSiteShopCode().equals(getPathInfo(1));
          }
          if (!hasCart) {
            addErrorMessage(WebMessage.get(CartDisplayMessage.FALSE_CART_ITEM));
            setRequestBean(getBean());
            return FrontActionResult.RESULT_SUCCESS;
          }

          Cashier cashier = CartUtil.createCashier(getCart(), getPathInfo(1), customer, new Sku(getPathInfo(2), getPathInfo(3)));
          
          // 2012/12/03 促销对应 ob add start
          resetSkuCodeOfCashier(cashier);
          // 2012/12/03 促销对应 ob add end
          cashier.setUsablePoint(false);
          cashier.setUsableCoupon(false);

          ShippingBean requestBean = new ShippingBean();
          requestBean.setCashier(cashier);
          requestBean.setLanguageCode(customer.getCustomer().getLanguageCode());
          setRequestBean(requestBean);
          setNextUrl("/app/order/shipping");
        }
      }
    } else if (move.equals("favorite")) {
      String url = "/app/cart/cart/favorite/";
      url += getPathInfo(1) + "/";
      url += getPathInfo(2);
      setNextUrl(url);
      return FrontActionResult.RESULT_SUCCESS;
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    DisplayTransition.add(getBean(), "/app/cart/cart/init/", getSessionContainer());
    return FrontActionResult.RESULT_SUCCESS;
  }

  private void resetSkuCodeOfCashier(Cashier cashier) {
    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        if (CommodityType.GIFT.longValue().equals(commodity.getCommodityType()) && commodity.getSkuCode().contains("~")) {
          commodity.setSkuCode(commodity.getSkuCode().split("~")[1]);
        }
      }
    }
  }
  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }
}
