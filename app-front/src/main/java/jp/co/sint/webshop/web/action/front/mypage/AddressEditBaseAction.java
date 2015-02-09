package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030320:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class AddressEditBaseAction extends WebFrontAction<AddressEditBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    AddressEditBean bean = getBean();
    boolean result = validateBean(bean);
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // getBean().setCityList(s.getCityNames(getBean().getPrefectureCode()));
    // 20120108 os013 add start
    getBean().setAddressScript(s.createAddressScript());
    getBean().setPrefectureList(s.createPrefectureList());
    getBean().setCityList(s.createCityList(getBean().getPrefectureCode()));
    getBean().setAreaList(s.createAreaList(getBean().getPrefectureCode(), getBean().getCityCode()));
    // 20120903 add by yyq
    if (getBean().getAreaList().size() == 1) {
      getBean().setAreaList(null);
    }
    if(getBean().getAreaList() != null){
      if (getBean().getAreaList().size() > 1 && StringUtil.isNullOrEmpty(getBean().getAreaCode())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.front.order.AddressMoveAction.0")));
        result = false;
      }
    }
    // 20120903 add by yyq
    
//  modify by V10-CH 170 start
    if (StringUtil.isNullOrEmpty(bean.getPhoneNumber1()) && StringUtil.isNullOrEmpty(bean.getPhoneNumber2())
        && StringUtil.isNullOrEmpty(bean.getPhoneNumber3()) && StringUtil.isNullOrEmpty(bean.getMobileNumber())) {
      addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
       result = false;
    }
    if(!StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2())){
     if(result){ 
       PhoneValidator phoneValidator = new PhoneValidator();
       boolean phoneResult = false;
     phoneResult = phoneValidator.isValid(StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean
         .getPhoneNumber3()));
     if (!phoneResult) {
       addErrorMessage(phoneValidator.getMessage());
     }
     result &= phoneResult;}
    }
//  modify by V10-CH 170 end
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
    
	    if (StringUtil.hasValueAllOf(bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean.getPhoneNumber3())) {
	      address.setPhoneNumber(StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean.getPhoneNumber3()));
	    } else if (StringUtil.hasValueAllOf(bean.getPhoneNumber1(), bean.getPhoneNumber2())) {
	      address.setPhoneNumber(StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2()));
	    } else {
	      address.setPhoneNumber("");
	    }
    

    // Add by V10-CH start
    address.setMobileNumber(bean.getMobileNumber());
    // Add by V10-CH end
    // add by V10-CH 170 start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    address.setCityCode(bean.getCityCode());
    address.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
    address.setAddress2(s.getCityName(bean.getPrefectureCode(), bean.getCityCode()));

    // add by V10-CH 170 end
    // 20120108 os013 add start
    address.setAreaCode(bean.getAreaCode());
    address.setAddress3(s.getAreaName(bean.getAreaCode()));
    // 20120108 os013 add end
    return address;
  }

}
