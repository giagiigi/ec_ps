package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.TransactionTokenAction;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.AddressEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;

/**
 * U2030320:アドレス帳登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
// 10.1.4 10195 修正 ここから
// public class AddressEditRegisterAction extends AddressEditBaseAction {
public class AddressEditRegisterAction extends AddressEditBaseAction implements TransactionTokenAction {
// 10.1.4 10195 修正 ここまで

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    AddressEditBean bean = getBean();

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
//        setNextUrl("/app/mypage/address_list");
//      }
      //  upd by lc 2012-03-26 end
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
