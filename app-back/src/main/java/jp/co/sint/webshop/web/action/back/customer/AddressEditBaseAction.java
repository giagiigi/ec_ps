package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.AddressEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class AddressEditBaseAction extends WebBackAction<AddressEditBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    AddressEditBean bean = getBean();
    boolean result = validateBean(bean);
    if (result) {
      //modify by V10-CH start
      PhoneValidator validator = new PhoneValidator();
     
      if (StringUtil.isNullOrEmpty(bean.getPhoneNumber1()) && StringUtil.isNullOrEmpty(bean.getPhoneNumber2())
          && StringUtil.isNullOrEmpty(bean.getPhoneNumber3()) && StringUtil.isNullOrEmpty(bean.getMobileNumber())) {
        addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
        result = false;
      }else{
        boolean phoneResult = validator.isValid(StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean
            .getPhoneNumber3()));
        if (!phoneResult) {
          addErrorMessage(validator.getMessage());
        }
        result &= phoneResult;
      }
      //modify by V10-CH end
      //add by lc 2012-03-08 start
      if (StringUtil.getLength(bean.getAddressLastName()) > 28) {
        addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.shipName")));
        result &= false;
      }
      
      if (StringUtil.getLength(bean.getAddress4()) > 200) {
        addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.shippingAddress4")));
        result &= false;
      }
		//add by lc 2012-03-08 end
    }
    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * 入力したアドレス帳情報を、CustomerAddressへセットします。
   * 
   * @param bean
   * @return address
   */
  public CustomerAddress setAddressData(AddressEditBean bean) {
    CustomerAddress address = new CustomerAddress();
    address.setCustomerCode(bean.getCustomerCode());
    address.setAddressAlias(bean.getAddressAlias());
    address.setAddressFirstName(bean.getAddressFirstName());
    address.setAddressLastName(bean.getAddressLastName());
    address.setAddressFirstNameKana(bean.getAddressFirstNameKana());
    address.setAddressLastNameKana(bean.getAddressLastNameKana());
    address.setPostalCode(bean.getPostalCode());
    address.setPrefectureCode(bean.getPrefectureCode());
    address.setAddress1(bean.getAddress1());
    address.setAddress2(bean.getAddress2());
    address.setAddress3(bean.getAddress3());
    address.setAddress4(bean.getAddress4());
    address.setPhoneNumber(bean.getPhoneNumber());
    //Add by V10-CH start
    address.setMobileNumber(bean.getMobileNumber());
    //Add by V10-CH end
    // modify by V10-CH 170 start
    address.setCityCode(bean.getCityCode());
    // modify by V10-CH 170 end
    //20120109 os013 add start
    address.setAreaCode(bean.getAreaCode());
    //20120109 os013 add end
    return address;
  }
}
