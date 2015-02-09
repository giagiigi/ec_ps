package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerEditSelectAction extends CustomerEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 権限チェック(参照：更新)
    if (getLoginInfo().isSite()) {
      return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
    } else {
      if (getConfig().isShop()) {
        if (StringUtil.isNullOrEmpty(getCustomerCode())) {
          return false;
        }
        return Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo());
      } else {
        return Permission.CUSTOMER_READ.isGranted(getLoginInfo());
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
   * URLから顧客コードを取得します。<BR>
   * 
   * @return customerCode
   */
  private String getCustomerCode() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * URLから処理パラメータを取得します。<BR>
   * 
   * @return completeParam
   */
  private String getCompleteParam() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 1) {
      return tmpArgs[1];
    } else {
      return "";
    }
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerEditBean bean = new CustomerEditBean();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    String customerCode = getCustomerCode();
    String completeParam = getCompleteParam();
    // 加的查询
    String mobileNumberSucces = service.getMobileNumber(customerCode);
    bean.setMobileNumberSucces(mobileNumberSucces);
    // 顧客グループリストの取得
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCustomerGroupList(s.getCustomerGroupNames());
    bean.setCustomerCode(customerCode);

    // 自ショップで受注処理を行っているか確認

    if (Permission.CUSTOMER_READ_SHOP.isGranted(getLoginInfo())) {
      if (StringUtil.isNullOrEmpty(customerCode) || !service.isShopCustomer(customerCode, getLoginInfo().getShopCode())) {
        setNextUrl("/app/common/dashboard/init/");
        setRequestBean(getBean());

        return BackActionResult.RESULT_SUCCESS;
      }
    }

    if (StringUtil.isNullOrEmpty(customerCode)) {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新処理の場合データ取得

    CustomerInfo info = service.getCustomer(customerCode);
    Customer customer = info.getCustomer();
    CustomerAddress address = info.getAddress();

    // 顧客・アドレスが存在しない場合
    // delete by os012 20111213 start
    // if (service.isNotFound(customerCode) || address == null) {
    // setNextUrl("/app/common/dashboard/init/");
    // setRequestBean(getBean());
    //
    // return BackActionResult.RESULT_SUCCESS;
    // }
    // 顧客がすでに退会済みの場合
    if (service.isWithdrawed(customerCode) && !completeParam.equals("withdrawal")) {
      setNextUrl("/app/common/dashboard/init/");
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 顧客情報

    bean.setCustomerCode(customerCode);
    bean.setCustomerGroupCode(customer.getCustomerGroupCode());
    bean.setFirstName(customer.getFirstName());
    bean.setLastName(customer.getLastName());
    bean.setFirstNameKana(customer.getFirstNameKana());
    bean.setLastNameKana(customer.getLastNameKana());
    bean.setEmail(customer.getEmail());
    // add by yl 20111212 start
    // bean.setEmailCon(customer.getEmail());
    // add by yl 20111212 end
    // 20120510 tuxinwei add start
    bean.setLanguageCode(customer.getLanguageCode());
    // 20120510 tuxinwei add end
    bean.setPassword(customer.getPassword());
    bean.setBirthDate(DateUtil.toDateString(customer.getBirthDate()));
    bean.setSex(NumUtil.toString(customer.getSex()));
    bean.setRequestMailType(NumUtil.toString(customer.getRequestMailType()));
    bean.setLoginErrorCount(NumUtil.toString(customer.getLoginErrorCount()));
    bean.setCreatedDatetime(DateUtil.toDateTimeString(customer.getCreatedDatetime()));
    bean.setLoginLockedFlg(NumUtil.toString(customer.getLoginLockedFlg()));
    // 20131101 txw add start
    bean.setErrorTimes(NumUtil.toString(customer.getErrorTimes()));
    bean.setLockFlg(NumUtil.toString(customer.getLockFlg()));
    // 20131101 txw add end
    if (customer.getCustomerStatus().equals(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue())) {
      bean.setReceivedWithdrawalNotice(true);
    } else {
      bean.setReceivedWithdrawalNotice(false);
    }
    bean.setCaution(customer.getCaution());
    bean.setUpdatedDatetimeCustomer(customer.getUpdatedDatetime());

    // アドレス帳情報
    // modify by os012 20111213 start
    if (address != null) {
      bean.setPostalCode(address.getPostalCode());
      // modify by V10-CH 170 start
      bean.setPrefectureCode(address.getPrefectureCode());
      // modify by V10-CH 170 end
      bean.setAddress1(address.getAddress1());
      bean.setAddress2(address.getAddress2());
      bean.setAddress3(address.getAddress3());
      bean.setAddress4(address.getAddress4());
      // modify by V10-CH 170 start
      if (completeParam.equals("withdrawal")) {
        bean.setPhoneNumber1("****");
        bean.setPhoneNumber2("********");
        bean.setPhoneNumber3("******");
      }
      String[] phonNo = {};
      if (StringUtil.isNullOrEmpty(address.getPhoneNumber())) {
        address.setPhoneNumber("");
      } else {
        phonNo = address.getPhoneNumber().split("-");
        if (phonNo.length == 2) {
          bean.setPhoneNumber1(phonNo[0]);
          bean.setPhoneNumber2(phonNo[1]);
          // modify by V10-CH 170 start
        } else if (phonNo.length == 3) {
          bean.setPhoneNumber1(phonNo[0]);
          bean.setPhoneNumber2(phonNo[1]);
          bean.setPhoneNumber3(phonNo[2]);
        }
      }

      // Add by V10-CH start
      bean.setMobileNumber(address.getMobileNumber());
      // Add by V10-CH end
      bean.setUpdatedDatetimeAddress(address.getUpdatedDatetime());
      // modify by V10-CH 170 start
      bean.setCityCode(address.getCityCode());
      bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
      // modify by V10-CH 170 end
    }
    // modify by os012 20111213 end
    // 処理完了メッセージ設定

    setCompleteMessage(completeParam);

    // 顧客属性をセット

    setAttributeList(bean, true);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage(String completeParam) {
    if (StringUtil.hasValue(completeParam)) {
      if (completeParam.equals("register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
            .getString("web.action.back.customer.CustomerEditSelectAction.0")));
      } else if (completeParam.equals("update")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.customer.CustomerEditSelectAction.0")));
      } else if (completeParam.equals("withdrawal")) {
        addInformationMessage(WebMessage.get(CompleteMessage.CUSTOMER_WITHDRAWED_COMPLETE));
      }
    }
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    CustomerEditBean nextBean = (CustomerEditBean) getRequestBean();

    // 更新権限チェック
    if (Permission.CUSTOMER_UPDATE.isGranted(login)) {
      nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
      nextBean.setNextButtonDisplayFlg(true);
    } else {
      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setNextButtonDisplayFlg(false);
    }
    // 削除処理後

    if ((getDisplayMessage().getInformation().size() > 0 && getDisplayMessage().getInformation().get(0).equals(
        WebMessage.get(CompleteMessage.CUSTOMER_WITHDRAWED_COMPLETE)))
        || (getDisplayMessage().getErrors().size() > 0 && getDisplayMessage().getErrors().get(0).equals(
            WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR, Messages
                .getString("web.action.back.customer.CustomerEditSelectAction.0"))))) {
      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setNextButtonDisplayFlg(false);
      nextBean.setNotWithdrawedFlg(false);
    } else {
      nextBean.setNotWithdrawedFlg(true);
    }
    // 削除権限チェック
    if (Permission.CUSTOMER_DELETE.isGranted(login)) {
      if (nextBean.isNotWithdrawedFlg()) {
        nextBean.setWithdrawalDisplayFlg(true);
      } else {
        nextBean.setWithdrawalDisplayFlg(false);
      }
    }
    // ポイント履歴ボタン表示設定

    if (Permission.CUSTOMER_POINT_READ.isGranted(login)) {
      nextBean.setPointDisplayFlg(true);
    }
    // クーポン履歴ボタン表示設定

    if (Permission.CUSTOMER_COUPON_READ.isGranted(login)) {
      nextBean.setCouponDisplayFlg(true);
    }

    // 受注履歴ボタン表示設定

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      nextBean.setOrderDisplayFlg(getLoginInfo().hasPermission(Permission.ORDER_READ_SITE));
    } else {
      nextBean.setOrderDisplayFlg(getLoginInfo().hasPermission(Permission.ORDER_READ_SHOP));
    }

    // 初期表示時は常に登録/更新/戻るボタンを非表示にセット

    nextBean.setRegisterButtonDisplayFlg(false);
    nextBean.setUpdateButtonDisplayFlg(false);
    nextBean.setBackButtonDisplayFlg(false);
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
    return Messages.getString("web.action.back.customer.CustomerEditSelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103012008";
  }

}
