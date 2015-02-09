package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.AdditionalAddressBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2020120:お届け先設定のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingAddNewAddressAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    AdditionalAddressBean bean = getBean().getAdditionalAddressEdit();
    getBean().setAdditionalBlock(WebConstantCode.DISPLAY_BLOCK);

    // 20120106 shen update start
    refreshItems(true, true);
    // 20120106 shen update end

    boolean valid = validateBean(bean);

    if (!valid) {
      return false;
    }

    // 20120106 shen update start
    /*
     * // modify by V10-CH 170 start if
     * (StringUtil.isNullOrEmpty(getBean().getAdditionalAddressEdit
     * ().getAdditionalPhoneNumber1()) &&
     * StringUtil.isNullOrEmpty(getBean().getAdditionalAddressEdit
     * ().getAdditionalPhoneNumber2()) &&
     * StringUtil.isNullOrEmpty(getBean().getAdditionalAddressEdit
     * ().getAdditionalPhoneNumber3()) &&
     * StringUtil.isNullOrEmpty(getBean().getAdditionalAddressEdit
     * ().getAdditionalMobileNumber())) {
     * addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER)); valid =
     * false; } // modify by V10-CH 170 end // delete by V10-CH 170 start
     * PhoneValidator validator = new PhoneValidator(); boolean phoneResult =
     * validator.isValid(StringUtil.joint('-',
     * getBean().getAdditionalAddressEdit().getAdditionalPhoneNumber1(),
     * getBean().getAdditionalAddressEdit().getAdditionalPhoneNumber2(),
     * getBean().getAdditionalAddressEdit() .getAdditionalPhoneNumber3())); if
     * (!phoneResult) { addErrorMessage(validator.getMessage()); } valid &=
     * phoneResult; // delete by V10-CH 170 end
     */
    if (StringUtil.getLength(getBean().getAdditionalAddressEdit().getAdditionalAddressLastName()) > 28) {
      addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
          .getString("web.action.front.customer.CustomerEdit1NextAction.7")));
      valid &= false;
    }
    // 区县必须输入
    if (StringUtil.isNullOrEmpty(getBean().getAdditionalAddressEdit().getAdditionalAreaCode())
        && getBean().getAdditionalAddressEdit().getAdditionalAreaList().size() > 1) {
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
          .getString("web.action.front.order.ShippingAddNewAddressAction.1")));
      valid &= false;
    }

    // 固定电话格式
    if (StringUtil.hasValueAnyOf(getBean().getAdditionalAddressEdit().getAdditionalPhoneNumber1(), getBean()
        .getAdditionalAddressEdit().getAdditionalPhoneNumber2(), getBean().getAdditionalAddressEdit().getAdditionalPhoneNumber3())) {
      PhoneValidator phoneValidator = new PhoneValidator();
      valid &= phoneValidator.isValid(StringUtil.joint('-', getBean().getAdditionalAddressEdit().getAdditionalPhoneNumber1(),
          getBean().getAdditionalAddressEdit().getAdditionalPhoneNumber2(), getBean().getAdditionalAddressEdit()
              .getAdditionalPhoneNumber3()));
      if (!valid) {
        addErrorMessage(phoneValidator.getMessage());
      }
    } else {
      // 固定电话和手机必须输入一项
      if (StringUtil.isNullOrEmpty(getBean().getAdditionalAddressEdit().getAdditionalMobileNumber())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.NO_NUMBER));
        valid &= false;
      }
    }
    // 20120106 shen update end

    return valid;
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
    // modify by V10-CH 170 start
    // address.setPrefectureCode(bean.getAdditionalPrefectureCode());
    // address.setAddress1(PrefectureCode.fromValue(bean.getAdditionalPrefectureCode()).getName());
    // address.setAddress2(bean.getAdditionalAddress2());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    address.setPrefectureCode(bean.getAdditionalPrefectureCode());
    address.setCityCode(bean.getAdditionalCityCode());
    // 20120106 shen add start
    address.setAreaCode(bean.getAdditionalAreaCode());
    // 20120106 shen add end
    // 20120106 shen update start
    // address.setAddress1(PrefectureCode.fromValue(bean.getAdditionalPrefectureCode()).getName());
    address.setAddress1(s.getPrefectureName(bean.getAdditionalPrefectureCode()));
    // 20120106 shen update end
    address.setAddress2(s.getCityName(bean.getAdditionalPrefectureCode(), bean.getAdditionalCityCode()));
    // modify by V10-CH 170 end
    // 20120106 shen update start
    // address.setAddress3(bean.getAdditionalAddress3());
    address.setAddress3(s.getAreaName(bean.getAdditionalAreaCode()));
    // 20120106 shen update end
    address.setAddress4(bean.getAdditionalAddress4());
    address.setPhoneNumber(bean.getAdditionalPhoneNumber());
    // Add by V10-CH start
    address.setMobileNumber(bean.getAdditionalMobileNumber());
    // Add by V10-CH end

    // データベース登録処理
    ServiceResult serviceResult = service.insertCustomerAddress(address);

    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.front.order.ShippingAddNewAddressAction.0")));
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
    } else {
      getBean().setAdditionalAddressEdit(new AdditionalAddressBean());
      // 10.1.1 10055 追加 ここから
      // 最新のアドレス帳を取得
      getBean().setAddAddressCheckList(createAddressCheckList(getBean().getCashier()));
      getBean().setAddAddressSelectList(createAddressSelectList(getBean().getAddAddressCheckList()));
      // 10.1.1 10055 追加 ここまで
      setNextUrl("/app/order/shipping/init");
    }
    getBean().getCashier().setMessage(getBean().getMessage());

    setRequestBean(getBean());

    return FrontActionResult.RESULT_SUCCESS;
  }

}
