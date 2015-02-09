package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.bean.front.mypage.AddressListBean;
import jp.co.sint.webshop.web.bean.front.mypage.AddressListBean.AddressListDetailBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.DisplayTransition;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2030310:アドレス帳一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressListInitAction extends WebFrontAction<AddressListBean> {

  private CustomerSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new CustomerSearchCondition();
  }

  protected CustomerSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CustomerSearchCondition getSearchCondition() {
    return this.condition;
  }

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

    AddressListBean bean = new AddressListBean();

    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    bean.setCustomerCode(customerCode);

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 顧客が存在しない/退会済み/退会依頼中の場合、トップ画面へ遷移
    if (service.isNotFound(customerCode) || service.isInactive(customerCode)) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    // parameter[0] : 処理完了パラメータ(delete)
//    String completeParam = "";
//    String[] parameter = getRequestParameter().getPathArgs();
//    if (parameter.length > 0) {
//      completeParam = parameter[0];
//    }

    // 検索条件を取得
    condition.setCustomerCode(customerCode);

    condition = getCondition();
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
	  	WebshopConfig config = DIContainer.getWebshopConfig();
	    condition.setPageSize(config.getMobilePageSize());
    }
  //20111227 os013 delete start
    // アドレス帳一覧
    SearchResult<CustomerAddress> result = service.getCustomerAddressList(condition);
 
    // アドレス帳が存在しない場合、トップ画面へ遷移
  //20120116 del by wjw  start
//    if (result.getRows().size() < 1) {
//      setNextUrl("/app/common/index");
//
//      getSessionContainer().logout();
//      return FrontActionResult.RESULT_SUCCESS;
//    }
  //20120116 del by wjw  end

    // ページ情報を追加
    bean.setPagerValue(PagerUtil.createValue(result));

    List<CustomerAddress> addressList = result.getRows();
    
    List<AddressListDetailBean> list = new ArrayList<AddressListDetailBean>();

    for (CustomerAddress addr : addressList) {
      AddressListDetailBean detail = new AddressListDetailBean();

      detail.setAddressNo(Long.toString(addr.getAddressNo()));
      detail.setAddressAlias(addr.getAddressAlias());
      detail.setAddressFirstName(addr.getAddressFirstName());
      detail.setAddressLastName(addr.getAddressLastName());
      detail.setPostalCode(addr.getPostalCode());
      detail.setAddress1(addr.getAddress1());
      detail.setAddress2(addr.getAddress2());
      detail.setAddress3(addr.getAddress3());
      detail.setAddress4(addr.getAddress4());
      detail.setPhoneNumber(addr.getPhoneNumber());
      //Add by V10-CH start
      detail.setMobileNumber(addr.getMobileNumber());
      //Add by V10-CH end

      // 削除/編集ボタン表示フラグ設定
      setDeleteDisplayControl(detail);
      setEditDisplayControl(detail);

      list.add(detail);
    }
    bean.setList(list);
  //20111227 os013 delete end
    
    String[] parameter = getRequestParameter().getPathArgs();
    String addressNo = "";
    String action = "";
    if (parameter.length > 0) {
      addressNo = parameter[0];
      if(parameter.length >1){
        action = parameter[1];
      }
    }
    
    // 更新処理の場合データ取得
    if (StringUtil.hasValue(addressNo) && StringUtil.isNullOrEmpty(action) ) {
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
    
    // 処理完了メッセージを設定する
//    setCompleteMessage(completeParam);
    
    
    
    setRequestBean(bean);
 // 前画面情報設定
    if (StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)) {
      DisplayTransition.add(getBean(), "/app/customer/customer_edit1/init", getSessionContainer());  
    }else{
      DisplayTransition.add(getBean(), "/app/mypage/mypage", getSessionContainer());
    }
    
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * テキスト入力:編集可能/次へボタン:表示<br>
   * 初期画面の場合は、登録/更新/戻るボタンは常に非表示
   */
  @Override
  public void prerender() {
    AddressListBean nextBean = (AddressListBean) getRequestBean();
    nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    nextBean.setDisplayNextButtonFlg(true);
    nextBean.setDisplayRegisterButtonFlg(true);
    nextBean.setDisplayUpdateButtonFlg(false);
    nextBean.setDisplayBackButtonFlg(false);
    nextBean.setDisplayListBackButtonFlg(true);

    // URLから取得したパラメータをセット
    // parameter[0] : アドレス帳番号, parameter[1] : 処理パラメータ
    String[] parameter = getRequestParameter().getPathArgs();
    
    // 如果有addressNo，代表是更新操作，显示更新按钮
    if(parameter.length > 0 && StringUtil.hasValue(parameter[0])) {
      nextBean.setDisplayRegisterButtonFlg(false);
      nextBean.setDisplayUpdateButtonFlg(true);
    }
    
    String completeParam = "";
    if (parameter.length > 1) {
      completeParam = parameter[1];
    }
    if (completeParam.equals("register")) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.front.mypage.AddressEditInitAction.0")));
//      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setDisplayNextButtonFlg(false);
      nextBean.setDisplayUpdateButtonFlg(false);
      nextBean.setDisplayRegisterButtonFlg(true);
    } else if (completeParam.equals("update")) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.front.mypage.AddressEditInitAction.0")));
//      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setDisplayUpdateButtonFlg(false);
      nextBean.setDisplayRegisterButtonFlg(true);
      nextBean.setDisplayNextButtonFlg(false);
    }   else if (completeParam.equals("delete")) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.front.mypage.AddressListInitAction.0")));
      nextBean.setDisplayUpdateButtonFlg(false);
      nextBean.setDisplayRegisterButtonFlg(true);
      
    }
    
    setRequestBean(nextBean);
  }
  
  /**
   * 画面上に表示される削除ボタンの表示/非表示を制御します。 「本人でない」時にボタンを表示します。
   * 
   * @param nextBean
   * @param delete
   */
  private void setDeleteDisplayControl(AddressListDetailBean detail) {
    // 10.1.3 10150 修正 ここから
    // if (detail.getAddressNo().equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO))) {
    if (detail.getAddressNo().equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO))) {
    // 10.1.3 10150 修正 ここまで
      detail.setDeleteDisplayFlg(false);
    } else {
      detail.setDeleteDisplayFlg(true);
    }
  }

  /**
   * 画面上に表示される編集ボタンの表示/非表示を制御します。<br>
   * 「本人でない」時にボタンを表示します。
   * 
   * @param nextBean
   */
  private void setEditDisplayControl(AddressListDetailBean detail) {
    // 10.1.3 10150 修正 ここから
    // if (detail.getAddressNo().equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO))) {
    //20120116 del by wjw  start
//    if (detail.getAddressNo().equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO))) {
//    // 10.1.3 10150 修正 ここまで
//      detail.setEditDisplayFlg(false);
//    } else {
//      detail.setEditDisplayFlg(true);
//    }
    detail.setEditDisplayFlg(true);
    //20120116 del by wjw  end
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
//  private void setCompleteMessage(String completeParam) {
//    if (completeParam.equals("delete")) {
//      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
//          .getString("web.action.front.mypage.AddressListInitAction.0")));
//    }
//  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
