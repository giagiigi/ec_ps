package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.AddressListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

import org.apache.log4j.Logger;

/**
 * U2030310:アドレス帳一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressListMoveAction extends WebFrontAction<AddressListBean> {

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

    String addressNo = "";
    String nextParam = "";

    // parameter[0] : 次画面パラメータ , parameter[1] : アドレス帳番号
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      nextParam = StringUtil.coalesce(parameter[0], "");
      if (parameter.length > 1) {
        addressNo = StringUtil.coalesce(parameter[1], "");
      }
    }

    // 顧客の存在/退会済みチェック
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (service.isNotFound(getLoginInfo().getCustomerCode()) || service.isInactive(getLoginInfo().getCustomerCode())) {
      setNextUrl("/app/common/index");
      setRequestBean(getBean());

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    // アドレスの存在チェック
    if (StringUtil.hasValue(addressNo)) {
      // アドレス帳番号チェック
      getBean().setAddressNo(addressNo);
      ValidationSummary validateCustomer = BeanValidator.partialValidate(getBean(), "addressNo");
      if (validateCustomer.hasError()) {
        Logger logger = Logger.getLogger(this.getClass());
        for (String rs : validateCustomer.getErrorMessages()) {
          logger.debug(rs);
        }
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.mypage.AddressListMoveAction.0")));
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }

      CustomerAddress address = service.getCustomerAddress(getLoginInfo().getCustomerCode(), Long.parseLong(addressNo));

      // アドレス帳が本人(更新時)、または存在しない場合エラー
      // 10.1.3 10150 修正 ここから
      // boolean isFalseAddress = (nextParam.equals("update") && addressNo.equals(Long.toString(CustomerConstant.SELFE_ADDRESS_NO)))
      //20120116 del by wjw  start
      //boolean isFalseAddress = (nextParam.equals("update") && addressNo.equals(Long.toString(CustomerConstant.SELF_ADDRESS_NO)))
      //|| (address == null);
      boolean isFalseAddress = (address == null);
      //20120116 del by wjw  end
      // 10.1.3 10150 修正 ここまで 
      if (isFalseAddress) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.mypage.AddressListMoveAction.0")));
        setRequestBean(getBean());
        return FrontActionResult.RESULT_SUCCESS;
      }
    } else {
      if (nextParam.equals("delivery") || nextParam.equals("update")) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
    }
    //PC
    if (StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)) {
      // 次画面URL設定
      if (nextParam.equals("delivery")) {
        setNextUrl("/app/mypage/delivery_history/init/" + addressNo);
      } else if (nextParam.equals("update")) {
        setNextUrl("/app/mypage/address_list/init/" + addressNo);
      } else if (nextParam.equals("register")) {
        setNextUrl("/app/mypage/address_list/init");
      } else {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
    }else{
      // 次画面URL設定
      if (nextParam.equals("delivery")) {
        setNextUrl("/app/mypage/delivery_history/init/" + addressNo);
      } else if (nextParam.equals("update")) {
        setNextUrl("/app/mypage/address_edit/init/" + addressNo);
      } else if (nextParam.equals("register")) {
        setNextUrl("/app/mypage/address_edit/init");
      } else {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
    }



    
    
    
    // 前画面情報設定
    DisplayTransition.add(null, "/app/mypage/address_list/init_back", getSessionContainer());

    return FrontActionResult.RESULT_SUCCESS;
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
