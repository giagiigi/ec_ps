package jp.co.sint.webshop.web.action.front.order;

import java.util.List;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartUtil;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.GuestBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.bean.front.order.GuestBean.GuestOwnerBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020210:ゲスト情報入力のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GuestMoveAction extends WebFrontAction<GuestBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;

    if (StringUtil.isNullOrEmpty(getPathInfo(0))) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    if (getPathInfo(0).equals("shipping")) {
      UtilService s = ServiceLocator.getUtilService(getLoginInfo());
      getBean().getGuestOwner().setOwnerCityList(s.createCityList(getBean().getGuestOwner().getOwnerPrefecture()));
      getBean().getGuestOwner().setOwnerAreaList(
          s.createAreaList(getBean().getGuestOwner().getOwnerPrefecture(), getBean().getGuestOwner().getOwnerCityCode()));

      valid &= validateBean(getBean().getGuestOwner());

      if (valid) {
        // 区县必须输入
        if (StringUtil.isNullOrEmpty(getBean().getGuestOwner().getOwnerAreaCode())
            && getBean().getGuestOwner().getOwnerAreaList().size() > 1) {
          addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
              .getString("web.action.front.order.GuestMoveAction.0")));
          valid &= false;
        }

        // 固定电话验证
        if (StringUtil.hasValueAnyOf(getBean().getGuestOwner().getPhoneNumber1(), getBean().getGuestOwner().getPhoneNumber2(),
            getBean().getGuestOwner().getPhoneNumber3())) {
          PhoneValidator phoneValidator = new PhoneValidator();
          valid &= phoneValidator.isValid(StringUtil.joint('-', getBean().getGuestOwner().getPhoneNumber1(), getBean()
              .getGuestOwner().getPhoneNumber2(), getBean().getGuestOwner().getPhoneNumber3()));
          if (!valid) {
            addErrorMessage(phoneValidator.getMessage());
          }
        } else {
          // 固定电话和手机必须输入一项
          if (StringUtil.isNullOrEmpty(getBean().getGuestOwner().getMobileNumber())) {
            addErrorMessage(WebMessage.get(ActionErrorMessage.NO_NUMBER));
            valid = false;
          }
        }
      }
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
    String move = getPathInfo(0);
    if (move.equals("shipping")) {

      CustomerInfo customerInfo = createCustomerInfo();

      // ゲストログイン情報生成

      FrontLoginInfo login = WebLoginManager.createFrontGuestLoginInfo(customerInfo.getCustomer());
      getSessionContainer().setLoginInfo(login);
      // 画面情報をtmpBeanにコピーする
      getSessionContainer().setTempBean(getBean());

      // Cashier生成
      Cashier cashier = getBean().getCashier();

      if (getCart() == null || StringUtil.isNullOrEmpty(getBean().getSelectShopCode())
          || getBean().getSelectReserveSkuSet() == null) {
        setNextUrl("/app/common/index");
        return FrontActionResult.RESULT_SUCCESS;
      }

      if (cashier == null) {
        cashier = CartUtil
            .createCashier(getCart(), getBean().getSelectShopCode(), customerInfo, getBean().getSelectReserveSkuSet());
        cashier.setUsablePoint(false);
      } else {
        if (cashier.isReserve()) {
          // 予約の場合最初の配送の最初の商品を予約商品コードとする

          List<CashierShipping> shippingList = cashier.getShippingList();
          List<CartCommodityInfo> commodityInfoList;
          if (shippingList == null || shippingList.size() < 1) {
            setNextUrl("/app/cart/cart");
            return FrontActionResult.RESULT_SUCCESS;
          } else {
            commodityInfoList = shippingList.get(0).getCommodityInfoList();
          }
          if (commodityInfoList == null || commodityInfoList.size() < 1) {
            setNextUrl("/app/cart/cart");
            return FrontActionResult.RESULT_SUCCESS;
          } else {
            CartCommodityInfo commodityInfo = commodityInfoList.get(0);
            cashier = CartUtil.createCashier(getCart(), cashier.getPaymentShopCode(), customerInfo, new Sku(commodityInfo
                .getShopCode(), commodityInfo.getSkuCode()));
          }
        } else {
          cashier = CartUtil.createCashier(getCart(), cashier.getPaymentShopCode(), customerInfo);
        }
      }

      // Cashierをshippingにコピー
      ShippingBean bean = new ShippingBean();
      bean.setCashier(cashier);
      setRequestBean(bean);

      // 遷移
      setNextUrl("/app/order/shipping");

    } else if (move.equals("cart")) {
      setNextUrl("/app/cart/cart");
    }

    // ログイン後遷移URLのクリア
    getSessionContainer().getAfterLoginInfo();

    return FrontActionResult.RESULT_SUCCESS;
  }

  private CustomerInfo createCustomerInfo() {
    GuestOwnerBean bean = getBean().getGuestOwner();
    CustomerInfo customerInfo = new CustomerInfo();

    Customer customer = new Customer();
    customer.setCustomerCode(CustomerConstant.GUEST_CUSTOMER_CODE);
    customer.setLastName(bean.getOwnerLastName());
    customer.setFirstName(bean.getOwnerFirstName());
    customer.setLastNameKana(bean.getOwnerLastNameKana());
    customer.setFirstNameKana(bean.getOwnerFirstNameKana());
    customer.setLoginId(bean.getOwnerEmail());
    customer.setEmail(bean.getOwnerEmail());
    customerInfo.setCustomer(customer);

    CustomerAddress customerAddress = new CustomerAddress();
    customerAddress.setAddressNo(CustomerConstant.SELF_ADDRESS_NO);
    customerAddress.setAddressLastName(bean.getOwnerLastName());
    customerAddress.setAddressFirstName(bean.getOwnerFirstName());
    customerAddress.setAddressLastNameKana(bean.getOwnerLastNameKana());
    customerAddress.setAddressFirstNameKana(bean.getOwnerFirstNameKana());
    customerAddress.setPostalCode(bean.getOwnerPostalCode());
    customerAddress.setPrefectureCode(bean.getOwnerPrefecture());
    customerAddress.setCityCode(bean.getOwnerCityCode());
    customerAddress.setAreaCode(bean.getOwnerAreaCode());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    customerAddress.setAddress1(s.getPrefectureName(bean.getOwnerPrefecture()));
    customerAddress.setAddress2(s.getCityName(bean.getOwnerPrefecture(), bean.getOwnerCityCode()));
    customerAddress.setAddress3(s.getAreaName(bean.getOwnerAreaCode()));
    customerAddress.setAddress4(bean.getOwnerAddress4());
    String phoneNumber = "";
    if (StringUtil.hasValueAllOf(bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean.getPhoneNumber3())) {
      phoneNumber = StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean.getPhoneNumber3());
    } else if (StringUtil.hasValueAllOf(bean.getPhoneNumber1(), bean.getPhoneNumber2())) {
      phoneNumber = StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2());
    }
    customerAddress.setPhoneNumber(phoneNumber);
    customerAddress.setMobileNumber(bean.getMobileNumber());

    customerInfo.setAddress(customerAddress);

    return customerInfo;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }
}
