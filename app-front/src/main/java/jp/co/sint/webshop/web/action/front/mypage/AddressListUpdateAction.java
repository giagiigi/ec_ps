package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.TransactionTokenAction;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.bean.front.mypage.AddressListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030320:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
// 10.1.4 10195 修正 ここから
// public class AddressEditUpdateAction extends AddressEditBaseAction {
public class AddressListUpdateAction extends WebFrontAction<AddressListBean> implements TransactionTokenAction {
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
    if (StringUtil.isNullOrEmpty(bean.getAddressNo())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.mypage.AddressEditUpdateAction.0")));
      result = false;
    }

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    AddressListBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    
    
    //IE 默认值无法获得
    bean.setAddressFirstNameKana("カナ");
    bean.setAddressLastNameKana("カナ");
    bean.setAddressFirstName("カナ");
    
    CustomerAddress address = setAddressData(bean);
    address.setAddressNo(Long.parseLong(bean.getAddressNo()));
    address.setUpdatedDatetime(bean.getUpdatedDatetime());

    // データベース更新処理
    ServiceResult serviceResult = service.updateCustomerAddress(address);

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.AddressEditUpdateAction.1")));
        } else if (result.equals(CustomerServiceErrorContent.ADDRESS_NO_DEF_FOUND_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.AddressEditUpdateAction.0")));
        } else if (result.equals(CustomerServiceErrorContent.ADDRESS_NO_DEF_FOUND_ERROR)
            || result.equals(CustomerServiceErrorContent.SELF_ADDRESS_UPDATE_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.AddressEditUpdateAction.0")));
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);

    } else {
      // upd by lc 2012-07-11 start
      setNextUrl("/app/mypage/address_list/init/" + address.getAddressNo() + "/update");
      // upd by lc 2012-07-11 end
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
