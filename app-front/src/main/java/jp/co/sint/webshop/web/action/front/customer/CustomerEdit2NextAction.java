package jp.co.sint.webshop.web.action.front.customer;

import java.util.List;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean.CustomerAttributeListAnswerBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean.CustomerAttributeListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030220:お客様情報登録2のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit2NextAction extends CustomerEditBaseAction<CustomerEdit2Bean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (!validateTempBean()) {
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

    CustomerConfirmBean confirmBean = (CustomerConfirmBean) getSessionContainer().getTempBean();
    confirmBean.setFailedTransitionFlg(false);

    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0 && tmpArgs[0].equals("back")) {
      getSessionContainer().setTempBean(confirmBean);
      setNextUrl("/app/customer/customer_edit2/back");

      return FrontActionResult.RESULT_SUCCESS;
    }
    
    if (StringUtil.isNullOrEmpty(confirmBean.getPhoneNumber1()) && StringUtil.isNullOrEmpty(confirmBean.getPhoneNumber2())
        && StringUtil.isNullOrEmpty(confirmBean.getPhoneNumber3()) && StringUtil.isNullOrEmpty(confirmBean.getMobileNumber())) {
      addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
    }else{
      PhoneValidator phoneValidator = new PhoneValidator();
      boolean phoneResult = false;
      phoneResult = phoneValidator.isValid(StringUtil.joint('-', confirmBean.getPhoneNumber1(), confirmBean.getPhoneNumber2(), confirmBean
          .getPhoneNumber3()));
      if (!phoneResult) {
        addErrorMessage(phoneValidator.getMessage());
      }
    }
//    else{
//      if(StringUtil.hasValue(confirmBean.getPhoneNumber1()) || StringUtil.hasValue(confirmBean.getPhoneNumber2()) || StringUtil.hasValue(confirmBean.getPhoneNumber3())){
//        if(!(StringUtil.hasValueAllOf(confirmBean.getPhoneNumber1(),confirmBean.getPhoneNumber2()) && confirmBean.getPhoneNumber1().length()>1 && confirmBean.getPhoneNumber2().length()>5)){
//          addErrorMessage(WebMessage.get(ValidationMessage.TRUE_NUMBER));
//        }
//      }
//    }

    CustomerEdit2Bean edit2Bean = getBean();

    // 顧客属性入力チェック(システムエラー)
    boolean answerResult = true;
    List<CustomerAttributeListBean> edit2AttributeList = edit2Bean.getAttributeList();
    for (CustomerAttributeListBean list : edit2AttributeList) {
      if (validateBean(list)) {
        for (CustomerAttributeListAnswerBean answer : list.getAttributeAnswerList()) {
          if (validateBean(answer)) {
            continue;
          } else {
            answerResult = false;
          }
        }
      } else {
        answerResult = false;
      }
    }
    if (!answerResult) {
      return FrontActionResult.SERVICE_VALIDATION_ERROR;
    }

    // 入力内容2を保持
    setEdit2ToTemp(edit2Bean, confirmBean);

    // 次画面URL設定
    setNextUrl("/app/customer/customer_confirm/init");

    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/customer/customer_edit2/next/back", getSessionContainer());

    // 入力内容を保持
    getSessionContainer().setTempBean(confirmBean);

    setRequestBean(confirmBean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return true;
  }
}
