package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.TransactionTokenAction;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.bean.front.mypage.AddressListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;

/**
 * U2030320:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
// 10.1.4 10195 修正 ここから
// public class AddressEditRegisterAction extends AddressEditBaseAction {
public class AddressListRegisterAction extends WebFrontAction<AddressListBean> implements TransactionTokenAction {
// 10.1.4 10195 修正 ここまで

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    AddressListBean bean = getBean();
    boolean result = validateBean(bean);
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().setAddressScript(s.createAddressScript());
    getBean().setPrefectureList(s.createPrefectureList());
    getBean().setCityList(s.createCityList(getBean().getPrefectureCode()));
    getBean().setAreaList(s.createAreaList(getBean().getPrefectureCode(), getBean().getCityCode()));
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
    
    boolean successValidate = true;
    successValidate = result && successValidate;
    
    if (StringUtil.getLength(getBean().getAddressLastName()) > 28) {
      addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
          .getString("web.action.front.customer.CustomerEdit1NextAction.7")));
      successValidate = false;
    }
    return successValidate;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    AddressListBean bean = getBean();
    
    //IE 默认值无法获得
    bean.setAddressFirstNameKana("カナ");
    bean.setAddressLastNameKana("カナ");
    bean.setAddressFirstName("カナ");

    // 画面から取得した値をDB登録用Beanへセットする
    CustomerAddress address = setAddressData(bean);
    


    // データベース登録処理
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    ServiceResult serviceResult = service.insertCustomerAddress(address);

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.AddressEditRegisterAction.0")));
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      //  upd by lc 2012-03-26 start
        setNextUrl("/app/mypage/address_list/init/" + address.getAddressNo() + "/register");
//      if(StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)){
//        setNextUrl("/app/mypage/address_list/init_back");
//      }else{
//        setNextUrl("/app/mypage/address_list/init/register");
//      }
      //  upd by lc 2012-03-26 end
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
  

  /**
   * 入力したアドレス帳情報を、CustomerAddressへセットします。
   * 
   * @param bean
   * @return address
   */
  public CustomerAddress setAddressData(AddressListBean bean) {
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
