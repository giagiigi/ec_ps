package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.AddressListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030310:アドレス帳一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressListDeleteAction extends WebFrontAction<AddressListBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // アドレス番号が存在しない場合エラー
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length < 1) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.mypage.AddressListDeleteAction.0")));
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    AddressListBean bean = getBean();

    // parameter[0] : 処理完了パラメータ(delete)
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      bean.setDeleteAddressNo(parameter[0]);
    }

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 顧客存在チェック
    if (service.isNotFound(bean.getCustomerCode()) || service.isInactive(bean.getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.mypage.AddressListDeleteAction.1")));

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }

    Long addressNo = Long.parseLong(bean.getDeleteAddressNo());

    // 本人削除エラー
    // 10.1.3 10150 修正 ここから
    // if (addressNo.equals(CustomerConstant.SELFE_ADDRESS_NO)) {
    if (addressNo.equals(CustomerConstant.SELF_ADDRESS_NO)) {
    // 10.1.3 10150 修正 ここまで
      addErrorMessage(WebMessage.get(MypageErrorMessage.DELETE_SELF_ADDRESS_ERROR));
      setRequestBean(bean);
      setNextUrl(null);

      return FrontActionResult.RESULT_SUCCESS;
    }
    // データベース更新処理
    ServiceResult serviceResult = service.deleteCustomerAddress(bean.getCustomerCode(), addressNo);

    // DBエラーの有無によって次画面のURLを制御する
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.mypage.AddressListDeleteAction.1")));
        } else if (result.equals(CustomerServiceErrorContent.ADDRESS_DELETED_ERROR)) {
          setNextUrl("/app/mypage/address_list/init/"+ addressNo +"/delete");

          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/mypage/address_list/init/"+ addressNo +"/delete");
    }
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
}
