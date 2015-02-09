package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.AddressBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U2020210:ゲスト情報入力のアクションクラスです
 * 
 * @author OB.
 */
public class AddressMoveAction extends WebBackAction<AddressBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    
        UtilService s = ServiceLocator.getUtilService(getLoginInfo());
        getBean().getAddress().setAddressCityList(s.createCityList(getBean().getAddress().getAddressPrefectureCode()));
        getBean().getAddress().setAddressAreaList(
            s.createAreaList(getBean().getAddress().getAddressPrefectureCode(), getBean().getAddress().getAddressCityCode()));

        valid &= validateBean(getBean().getAddress());

        if (valid) {

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
              addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
              valid &= false;
            }
          }
          //add by lc 2012-03-08 start
          if (StringUtil.getLength(getBean().getAddress().getAddressLastName()) > 28) {
            addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
                .getString("web.action.back.order.OrderDetailUpdateAction.shipName")));
            valid &= false;
          }
          
          if (StringUtil.getLength(getBean().getAddress().getAddressAddress4()) > 200) {
            addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, Messages
                .getString("web.action.back.order.OrderDetailUpdateAction.shippingAddress4")));
            valid &= false;
          }
          //add by lc 2012-03-08 end
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
      CustomerAddress customerAddress = new CustomerAddress();
      createCustomerAddress(customerAddress);
  
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      ServiceResultImpl result = service.insertCustomerSelfAddress(customerAddress);
  
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error == CommonServiceErrorContent.NO_DATA_ERROR) {
            setNextUrl("/app/common/index");
          } else if (error == CommonServiceErrorContent.VALIDATION_ERROR) {
            return BackActionResult.SERVICE_ERROR;
          }
        }
        return BackActionResult.SERVICE_ERROR;
      }
  
      setNextUrl("/app/order/neworder_customer/move/shipping/" + getBean().getCustomerCode());

    return BackActionResult.RESULT_SUCCESS;
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
  

@Override
public boolean authorize() {
	return Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
}
/**
 * Action名の取得
 * 
 * @return Action名
 */
public String getActionName() {
  return "新接受订货登录（收货地址登录）登录处理";
}

/**
 * オペレーションコードの取得
 * 
 * @return オペレーションコード
 */
public String getOperationCode() {
  return "1102020002";
}
}
