package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.AddressEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030220:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressEditInitAction extends AddressEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 参照権限チェック
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
    } else {
      if (getLoginInfo().isSite()) {
        return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
      } else {
        return Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo());
      }
    }
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
    AddressEditBean bean = new AddressEditBean();

    String completeParam = "";

    // URLから取得したパラメータをセット
    // parameter[0] : 顧客コード, parameter[1] : アドレス帳番号, parameter[2] : 処理パラメータ
    String[] parameter = getRequestParameter().getPathArgs();

    if (parameter.length > 0) {
      bean.setCustomerCode(parameter[0]);
      if (parameter.length > 1) {
        bean.setAddressNo(parameter[1]);
        if (parameter.length > 2) {
          completeParam = parameter[2];
        }
      }
    }

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    // 顧客存在チェック
    if (StringUtil.isNullOrEmpty(bean.getCustomerCode()) || service.isNotFound(bean.getCustomerCode())
        || service.isWithdrawed(bean.getCustomerCode())) {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新処理の場合データ取得
    if (StringUtil.hasValue(bean.getAddressNo())) {

      // 本人アドレス編集エラー
      // 10.1.3 10150 修正 ここから
      // if
      // (bean.getAddressNo().equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO)))
      // {
    //20120116 del by wjw  start
//      if (bean.getAddressNo().equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO))) {
//        // 10.1.3 10150 修正 ここまで
//        setNextUrl("/app/common/dashboard/init/");
//        setRequestBean(getBean());
//
//        return BackActionResult.RESULT_SUCCESS;
//      }
    //20120116 del by wjw  end

      CustomerAddress address = service.getCustomerAddress(bean.getCustomerCode(), Long.parseLong(bean.getAddressNo()));

      // アドレス帳に存在しない場合
      if (address == null) {
        setNextUrl("/app/common/dashboard/init/");
        setRequestBean(getBean());

        return BackActionResult.RESULT_SUCCESS;
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
      // modify by V10-CH 170 start
      bean.setCityCode(address.getCityCode());
      // modify by V10-CH 170 end
      // 20120109 os013 add start
      // 区县
      bean.setAreaCode(address.getAreaCode());
      // 20120109 os013 add end
      if (StringUtil.hasValue(address.getPhoneNumber())) {
        // modify by V10-CH 170 start
        String[] phonNo = address.getPhoneNumber().split("-");
        if (phonNo.length == 2) {
          bean.setPhoneNumber1(phonNo[0]);
          bean.setPhoneNumber2(phonNo[1]);
          // modify by V10-CH 170 end
        } else if (phonNo.length == 3) {
          bean.setPhoneNumber1(phonNo[0]);
          bean.setPhoneNumber2(phonNo[1]);
          bean.setPhoneNumber3(phonNo[2]);
        }
      } else {
        bean.setPhoneNumber1("");
        bean.setPhoneNumber2("");
        bean.setPhoneNumber3("");
      }
      bean.setMobileNumber(address.getMobileNumber());
      bean.setUpdatedDatetime(address.getUpdatedDatetime());
    }

    // modify by V10-CH 170 start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    // 20120108 os013 add start
    bean.setAddressScript(s.createAddressScript());
    bean.setPrefectureList(s.createPrefectureList());
    bean.setCityList(s.createCityList(bean.getPrefectureCode()));
    bean.setAreaList(s.createAreaList(bean.getPrefectureCode(), bean.getCityCode()));
    // 20120108 os013 add end
    // modify by V10-CH 170 end
    // 処理完了メッセージ設定
    setCompleteMessage(completeParam);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage(String completeParam) {
    if (completeParam.equals("register")) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.back.customer.AddressEditInitAction.0")));
    } else if (completeParam.equals("update")) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.customer.AddressEditInitAction.0")));
    }
  }

  /**
   * 更新権限あり<br>
   * テキスト入力:編集可能/次へボタン:表示<br>
   * 更新権限なし<br>
   * テキスト入力:編集不可/次へボタン:非表示<br>
   * 初期画面の場合は、登録/更新/戻るボタンは常に非表示
   */
  @Override
  public void prerender() {
    AddressEditBean nextBean = (AddressEditBean) getRequestBean();

    BackLoginInfo login = getLoginInfo();
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
      nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
      nextBean.setDisplayNextButtonFlg(true);
    } else {
      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setDisplayNextButtonFlg(false);
    }
    nextBean.setDisplayRegisterButtonFlg(false);
    nextBean.setDisplayUpdateButtonFlg(false);
    nextBean.setDisplayBackButtonFlg(false);

    setRequestBean(nextBean);
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.AddressEditInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103022003";
  }

}
