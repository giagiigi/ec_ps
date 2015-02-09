package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderShippingBean.AdditionalAddressBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020130:新規受注(配送先設定)のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
// 10.1.2 10073 修正 ここから
// public class NeworderShippingAddNewAddressAction extends WebBackAction<NeworderShippingBean> {
public class NeworderShippingAddNewAddressAction extends NeworderShippingBaseAction {
// 10.1.2 10073 修正 ここまで

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.CUSTOMER_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    AdditionalAddressBean bean = getBean().getAdditionalAddressEdit();
    boolean result = validateBean(bean);

    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().setAddressPrefectureList(s.createPrefectureList());
    getBean().setAddressCityList(s.createCityList(getBean().getAdditionalAddressEdit().getAdditionalPrefectureCode()));
    getBean().setAddressAreaList(s.createAreaList(getBean().getAdditionalAddressEdit().getAdditionalPrefectureCode(), 
    		getBean().getAdditionalAddressEdit().getAdditionalCityCode()));
    //add by lc 2012-03-08 start
    if (StringUtil.getLength(getBean().getAdditionalAddressEdit().getAdditionalAddressLastName()) > 28) {
      addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
          .getString("web.action.back.order.OrderDetailUpdateAction.shipName")));
      result &= false;
    }
    
    if (StringUtil.getLength(getBean().getAdditionalAddressEdit().getAdditionalAddress4()) > 200) {
      addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, Messages
          .getString("web.action.back.order.OrderDetailUpdateAction.shippingAddress4")));
      result &= false;
    }
    //add by lc 2012-03-08 end
   if (StringUtil.isNullOrEmpty(bean.getAdditionalPhoneNumber1()) && StringUtil.isNullOrEmpty(bean.getAdditionalPhoneNumber2())
       && StringUtil.isNullOrEmpty(bean.getAdditionalPhoneNumber3()) && StringUtil.isNullOrEmpty(bean.getAdditionalMobileNumber())) {
     addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
     result = false;
   }else{
     PhoneValidator phoneValidator = new PhoneValidator();
     boolean phoneResult = false;
     phoneResult = phoneValidator.isValid(StringUtil.joint('-', bean.getAdditionalPhoneNumber1(), bean.getAdditionalPhoneNumber2(), bean
         .getAdditionalPhoneNumber3()));
     if (!phoneResult) {
       addErrorMessage(phoneValidator.getMessage());
     }
     result &= phoneResult;
   }
//   else{
//     if(StringUtil.hasValue(bean.getAdditionalPhoneNumber1()) || StringUtil.hasValue(bean.getAdditionalPhoneNumber2()) || StringUtil.hasValue(bean.getAdditionalPhoneNumber3())){
//       if(!(StringUtil.hasValueAllOf(bean.getAdditionalPhoneNumber1(),bean.getAdditionalPhoneNumber2()) && bean.getAdditionalPhoneNumber1().length()>1 && bean.getAdditionalPhoneNumber2().length()>5)){
//         addErrorMessage(WebMessage.get(ValidationMessage.TRUE_NUMBER));
//         result = false;
//       }
//     }
//   }
   //Add by V10-CH end
//      PhoneValidator validator = new PhoneValidator();
//      if (!validator.isValid(phoneNo)) {
//        addErrorMessage(validator.getMessage());
//        result = false;
//      }

//  modify by V10-CH 170 end
    getBean().setAdditionalBlock(WebConstantCode.DISPLAY_BLOCK);
    // add by V10-CH 170 start

    bean.setAdditionalCityList(s.getCityNames(bean.getAdditionalPrefectureCode()));
    // add by V10-CH 170 end
    return result;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AdditionalAddressBean bean = getBean().getAdditionalAddressEdit();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 画面から取得した値をDB登録用Beanへセットする
    CustomerAddress address = new CustomerAddress();
    address.setCustomerCode(getBean().getCustomerCode());
    address.setAddressAlias(bean.getAdditionalAddressAlias());
    address.setAddressFirstName(bean.getAdditionalAddressFirstName());
    address.setAddressLastName(bean.getAdditionalAddressLastName());
    address.setAddressFirstNameKana(bean.getAdditionalAddressFirstNameKana());
    address.setAddressLastNameKana(bean.getAdditionalAddressLastNameKana());
    address.setPostalCode(bean.getAdditionalPostalCode());
    address.setPrefectureCode(bean.getAdditionalPrefectureCode());
    //modify by V10-CH 170 start 
    //address.setAddress1(PrefectureCode.fromValue(bean.getAdditionalPrefectureCode()).getName());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    address.setAddress1(s.getPrefectureName(bean.getAdditionalPrefectureCode()));
    address.setAddress2(s.getCityName(bean.getAdditionalPrefectureCode(), bean.getAdditionalCityCode()));
    address.setPrefectureCode(bean.getAdditionalPrefectureCode());
    address.setCityCode(bean.getAdditionalCityCode());
    //modify by V10-CH 170 end
    address.setAddress3(s.getAreaName(bean.getAdditionalAddress3()));
    address.setAreaCode(bean.getAdditionalAddress3());
    address.setAddress4(bean.getAdditionalAddress4());
    
    // modify by V10-CH 170 start
     if (StringUtil.hasValueAllOf(bean.getAdditionalPhoneNumber1(),
     bean.getAdditionalPhoneNumber2(), bean
     .getAdditionalPhoneNumber3())) {
     address.setPhoneNumber(StringUtil.joint('-',
     bean.getAdditionalPhoneNumber1(), bean.getAdditionalPhoneNumber2(), bean
     .getAdditionalPhoneNumber3()));
        }else if(StringUtil.hasValueAllOf(bean.getAdditionalPhoneNumber1(),
            bean.getAdditionalPhoneNumber2())){
          address.setPhoneNumber(StringUtil.joint('-',bean.getAdditionalPhoneNumber1(),bean.getAdditionalPhoneNumber2()));
        }else{
          address.setPhoneNumber("");
        }
//    if (StringUtil.hasValue(bean.getAdditionalPhoneNumber1())) {
//      address.setPhoneNumber(bean.getAdditionalPhoneNumber1());
//    }
    //modify by V10-CH 170 start 
     
   //   Add by V10-CH start
     if (StringUtil.hasValueAllOf(bean.getAdditionalMobileNumber())) {
       address.setMobileNumber(bean.getAdditionalMobileNumber());
            }
   //   Add by V10-CH end 
    // データベース登録処理
    ServiceResult serviceResult = service.insertCustomerAddress(address);

    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー

          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.customer.NeworderShippingAddNewAddressAction.0")));
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      getBean().setAdditionalAddressEdit(new AdditionalAddressBean());
      // 10.1.2 10073 追加 ここから
      // 最新のアドレス帳を取得
      getBean().setAddAddressCheckList(createAddressCheckList(getBean().getCashier()));
      getBean().setDispAddressList(createAddressList(getBean().getCashier()));
      // 10.1.2 10073 追加 ここまで      
      setNextUrl("/app/order/neworder_shipping/init");
    }

    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderShippingAddNewAddressAction.1");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102013002";
  }

}
