package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U2030320:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressEditInitAction extends AddressEditBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AddressEditBean bean = new AddressEditBean();

    // URLから取得したパラメータをセット
    // parameter[0] : アドレス帳番号, parameter[1] : 処理パラメータ
    String[] parameter = getRequestParameter().getPathArgs();
    String addressNo = "";

    if (parameter.length > 0) {
      addressNo = parameter[0];
    }

    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 顧客が存在しない/退会済み/退会依頼中の場合、トップ画面へ遷移
    if (service.isNotFound(customerCode) || service.isInactive(customerCode)) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    bean.setCustomerCode(customerCode);

    // 更新処理の場合データ取得
    if (StringUtil.hasValue(addressNo)) {
      // アドレス帳番号チェック
      bean.setAddressNo(addressNo);
      ValidationSummary validateCustomer = BeanValidator.partialValidate(bean, "addressNo");
      if (validateCustomer.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        for (String rs : validateCustomer.getErrorMessages()) {
          logger.debug(rs);
        }
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.front.mypage.AddressEditInitAction.0")));
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }

      CustomerAddress address = service.getCustomerAddress(customerCode, Long.parseLong(addressNo));

      // アドレス帳が本人、または存在しない場合エラー
      // 10.1.3 10150 修正 ここから
      // boolean isFalseAddress = addressNo.equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO)) || (address == null);
      //20120116 del by wjw  start
      //boolean isFalseAddress = addressNo.equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO)) || (address == null);
      //20120116 del by wjw  end
      // 10.1.3 10150 修正 ここまで
      boolean isFalseAddress = (address == null);
      if (isFalseAddress) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.front.mypage.AddressEditInitAction.0")));
        bean.setAddressNo("");
        setRequestBean(bean);
        return FrontActionResult.RESULT_SUCCESS;
      }

      bean.setAddressNo(Long.toString(address.getAddressNo()));
      bean.setAddressAlias(address.getAddressAlias());
      bean.setAddressFirstName(address.getAddressFirstName());
      bean.setAddressLastName(address.getAddressLastName());
      bean.setAddressFirstNameKana(address.getAddressFirstNameKana());
      bean.setAddressLastNameKana(address.getAddressLastNameKana());
      bean.setPostalCode(address.getPostalCode());
      bean.setPrefectureCode(address.getPrefectureCode());
      bean.setAddress1(address.getAddress1());
      bean.setAddress2(address.getAddress2());
      bean.setAddress3(address.getAddress3());
      bean.setAddress4(address.getAddress4());
      //modify by V10-CH 170 start
        if(StringUtil.hasValue(address.getPhoneNumber())){
        String[] phonNo = address.getPhoneNumber().split("-");
        if (phonNo.length == 2) {
          bean.setPhoneNumber1(phonNo[0]);
          bean.setPhoneNumber2(phonNo[1]);
        } else if(phonNo.length == 3){
          bean.setPhoneNumber1(phonNo[0]);
          bean.setPhoneNumber2(phonNo[1]);
          bean.setPhoneNumber3(phonNo[2]);
        }
        bean.setPhoneNumber(address.getPhoneNumber());
        }else{
          address.setPhoneNumber("");
          bean.setPhoneNumber1("");
          bean.setPhoneNumber2("");
          bean.setPhoneNumber3("");
          bean.setPhoneNumber("");
        }
      
      //Add by V10-CH start
      if(StringUtil.hasValue(address.getMobileNumber())){
        bean.setMobileNumber(address.getMobileNumber());
      }
      //Add by V10-CH end
      bean.setCityCode(address.getCityCode());
      //20120108 os013 add start
      bean.setAreaCode(address.getAreaCode());
      //20120108 os013 add end
      bean.setUpdatedDatetime(address.getUpdatedDatetime());

    }
    // modify by V10-CH 170 start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    //20120108 os013 add start
    bean.setAddressScript(s.createAddressScript());
    bean.setPrefectureList(s.createPrefectureList());
    bean.setCityList(s.createCityList(bean.getPrefectureCode()));
    bean.setAreaList(s.createAreaList(bean.getPrefectureCode(),bean.getCityCode()));
    //20120108 os013 add end
    // modify by V10-CH 170 end
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * テキスト入力:編集可能/次へボタン:表示<br>
   * 初期画面の場合は、登録/更新/戻るボタンは常に非表示
   */
  @Override
  public void prerender() {
    AddressEditBean nextBean = (AddressEditBean) getRequestBean();
    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setDisplayNextButtonFlg(true);
    nextBean.setDisplayRegisterButtonFlg(false);
    nextBean.setDisplayUpdateButtonFlg(false);
    nextBean.setDisplayBackButtonFlg(false);
    nextBean.setDisplayListBackButtonFlg(true);

    // URLから取得したパラメータをセット
    // parameter[0] : アドレス帳番号, parameter[1] : 処理パラメータ
    String[] parameter = getRequestParameter().getPathArgs();

    String completeParam = "";
    if (parameter.length > 1) {
      completeParam = parameter[1];
    }
    if (completeParam.equals("register")) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.front.mypage.AddressEditInitAction.0")));
      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setDisplayNextButtonFlg(false);
    } else if (completeParam.equals("update")) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.front.mypage.AddressEditInitAction.0")));
      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setDisplayNextButtonFlg(false);
    }
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
