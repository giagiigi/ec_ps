package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.AddressBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020210:ゲスト情報入力のアクションクラスです
 * 
 * @author Kousen.
 */
public class AddressMoveAction extends WebFrontAction<AddressBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;

    if (StringUtil.isNullOrEmpty(getPathInfo(0))) {
      return false;
    }

    if (getPathInfo(0).equals("shipping")) {
      UtilService s = ServiceLocator.getUtilService(getLoginInfo());
      getBean().getAddress().setAddressCityList(s.createCityList(getBean().getAddress().getAddressPrefectureCode()));
      getBean().getAddress().setAddressAreaList(
          s.createAreaList(getBean().getAddress().getAddressPrefectureCode(), getBean().getAddress().getAddressCityCode()));

      valid &= validateBean(getBean().getAddress());

      if (valid) {
        if (StringUtil.getLength(getBean().getAddress().getAddressLastName()) > 28) {
          addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
              .getString("web.action.front.customer.CustomerEdit1NextAction.7")));
          valid &= false;
        }
        // 区县必须输入
        if (StringUtil.isNullOrEmpty(getBean().getAddress().getAddressAreaCode())
            && getBean().getAddress().getAddressAreaList().size() > 1) {
          addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
              .getString("web.action.front.order.AddressMoveAction.0")));
          valid &= false;
        }

        // 固定电话格式
        if (StringUtil.hasValueAnyOf(getBean().getAddress().getPhoneNumber1(), getBean().getAddress().getPhoneNumber2(), getBean()
            .getAddress().getPhoneNumber3())) {
          PhoneValidator phoneValidator = new PhoneValidator();
          valid &= phoneValidator.isValid(StringUtil.joint('-', getBean().getAddress().getPhoneNumber1(), getBean().getAddress()
              .getPhoneNumber2(), getBean().getAddress().getPhoneNumber3()));
          if (!valid) {
            addErrorMessage(phoneValidator.getMessage());
          }
        } else {
          // 固定电话和手机必须输入一项
          if (StringUtil.isNullOrEmpty(getBean().getAddress().getMobileNumber())) {
            addErrorMessage(WebMessage.get(ActionErrorMessage.NO_NUMBER));
            valid &= false;
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
    if (getPathInfo(0).equals("shipping")) {
      CustomerAddress customerAddress = new CustomerAddress();
      createCustomerAddress(customerAddress);

      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      ServiceResultImpl result = service.insertCustomerSelfAddress(customerAddress);

      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error == CommonServiceErrorContent.NO_DATA_ERROR) {
            setNextUrl("/app/common/index");
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESTART_CASHIER));
          } else if (error == CommonServiceErrorContent.VALIDATION_ERROR) {
            return FrontActionResult.SERVICE_ERROR;
          }
        }
        return FrontActionResult.SERVICE_ERROR;
      }

      setNextUrl("/app/cart/cart/move/shipping/" + getBean().getShopCode() + "///");
    } else if (getPathInfo(0).equals("cart")) {
      setNextUrl("/app/cart/cart");
    } else {
      setNextUrl("/app/common/index");
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

  private void createCustomerAddress(CustomerAddress customerAddress) {
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    customerAddress.setCustomerCode(getBean().getCustomerCode());
    customerAddress.setAddressNo(CustomerConstant.SELF_ADDRESS_NO);
    customerAddress.setAddressAlias(CustomerConstant.SELF_ADDRESS_ALIAS);
    customerAddress.setAddressLastName(getBean().getAddress().getAddressLastName());
    customerAddress.setPostalCode(getBean().getAddress().getAddressPostalCode());
    customerAddress.setPrefectureCode(getBean().getAddress().getAddressPrefectureCode());
    customerAddress.setCityCode(getBean().getAddress().getAddressCityCode());
    customerAddress.setAreaCode(getBean().getAddress().getAddressAreaCode());
    customerAddress.setAddress1(s.getPrefectureName(getBean().getAddress().getAddressPrefectureCode()));
    customerAddress.setAddress2(s.getCityName(getBean().getAddress().getAddressPrefectureCode(), getBean().getAddress()
        .getAddressCityCode()));
    customerAddress.setAddress3(s.getAreaName(getBean().getAddress().getAddressAreaCode()));
    customerAddress.setAddress4(getBean().getAddress().getAddressAddress4());
    customerAddress.setMobileNumber(getBean().getAddress().getMobileNumber());
    String phoneNumber = "";
    if (StringUtil.hasValueAllOf(getBean().getAddress().getPhoneNumber1(), getBean().getAddress().getPhoneNumber2(), getBean()
        .getAddress().getPhoneNumber3())) {
      phoneNumber = StringUtil.joint('-', getBean().getAddress().getPhoneNumber1(), getBean().getAddress().getPhoneNumber2(),
          getBean().getAddress().getPhoneNumber3());
    } else if (StringUtil.hasValueAllOf(getBean().getAddress().getPhoneNumber1(), getBean().getAddress().getPhoneNumber2())) {
      phoneNumber = StringUtil.joint('-', getBean().getAddress().getPhoneNumber1(), getBean().getAddress().getPhoneNumber2());
    }
    customerAddress.setPhoneNumber(phoneNumber);
    customerAddress.setAddressFirstNameKana("カナ");
    customerAddress.setAddressLastNameKana("カナ"); 
    customerAddress.setAddressFirstName(" ");
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

}
