package jp.co.sint.webshop.web.action.back.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LockFlg;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean.CustomerAttributeListAnswerBean;
import jp.co.sint.webshop.web.bean.back.customer.CustomerEditBean.CustomerAttributeListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030120:顧客登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CustomerEditBaseAction extends WebBackAction<CustomerEditBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    boolean result = true;

    CustomerEditBean bean = getBean();

    result = validateBean(getBean());

    // パスワードチェック(新規登録時)
    if (StringUtil.isNullOrEmpty(bean.getCustomerCode())) {
      // 必須入力エラー

      if (StringUtil.isNullOrEmpty(bean.getPassword())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.back.customer.CustomerEditBaseAction.0")));
        result = false;
      }
      // email确认没有录入 add by os012 20111216 start
      if (StringUtil.isNullOrEmpty(bean.getEmailCon()) && !StringUtil.isNullOrEmpty(bean.getEmail())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.back.customer.CustomerEditBaseAction.3")));
        result = false;
      }
      // email确认没有 add by os012 20111216 end

      if (StringUtil.isNullOrEmpty(bean.getPasswordCon()) && !StringUtil.isNullOrEmpty(bean.getPassword())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.back.customer.CustomerEditBaseAction.1")));
        result = false;
      }
      // add by lc 2012-03-08 start
      if (StringUtil.getLength(bean.getLastName()) > 28) {
        addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.customerName")));
        result &= false;
      }
      // add by lc 2012-03-08 end

      if (StringUtil.hasValue(bean.getPassword())) {
        // パスワードチェックエラー
        PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
        if (!policy.isValidPassword(bean.getPassword())) {
          addErrorMessage(WebMessage.get(CustomerErrorMessage.PASSWORD_POLICY_ERROR));
          result = false;
        }
        // email不一致 add by yl 20111212 start

        if (StringUtil.hasValue(bean.getEmailCon()) && !bean.getEmail().equals(bean.getEmailCon())) {
          addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_EMAIL));
          result = false;
        }
        // 不一致エラー
        if (StringUtil.hasValue(bean.getPasswordCon()) && !bean.getPassword().equals(bean.getPasswordCon())) {
          addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_PASSWORD));
          result = false;
        }

      }
    }

    if (result) {
      // delete by yl 2011 start
      // 電話番号チェック
      // PhoneValidator phoneValidator = new PhoneValidator();
      // // modify by V10-CH 170 start
      // boolean phoneResult = false;
      // phoneResult = phoneValidator.isValid(StringUtil.joint('-',
      // bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean
      // .getPhoneNumber3()));
      // if (!phoneResult) {
      // addErrorMessage(phoneValidator.getMessage());
      // }
      // result &= phoneResult;
      //      
      // if (StringUtil.isNullOrEmpty(bean.getPhoneNumber1()) &&
      // StringUtil.isNullOrEmpty(bean.getPhoneNumber2())
      // && StringUtil.isNullOrEmpty(bean.getPhoneNumber3()) &&
      // StringUtil.isNullOrEmpty(bean.getMobileNumber())) {
      // addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
      // result = false;
      // delete by yl 2011 end
      return result;
      // }
      // Add by V10-CH end
    }
    return result;

  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * 顧客属性を表示します。
   * 
   * @param bean
   * @param update
   */
  public void setAttributeList(CustomerEditBean bean, boolean update) {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    List<CustomerAttribute> getAttributeList = service.getAttributeList();
    List<CustomerAttributeListBean> attributeList = new ArrayList<CustomerAttributeListBean>();

    for (CustomerAttribute list : getAttributeList) {
      CustomerAttributeListBean attributeDetailList = new CustomerAttributeListBean();

      attributeDetailList.setCustomerAttributeNo(NumUtil.toString(list.getCustomerAttributeNo()));
      attributeDetailList.setCustomerAttributeName(list.getCustomerAttributeName());
      attributeDetailList.setCustomerAttributeType(NumUtil.toString(list.getCustomerAttributeType()));
      attributeDetailList.setAttributeTextName(NumUtil.toString(list.getCustomerAttributeNo()));

      // 顧客属性選択肢の取得

      setAttributeChoiceList(list, attributeDetailList);

      // 顧客属性回答の取得
      if (update) {
        setAttributeAnswerList(bean, list, attributeDetailList);
      }

      attributeList.add(attributeDetailList);
    }

    bean.setAttributeList(attributeList);
  }

  /**
   * 顧客属性選択肢を取得します。
   * 
   * @param attribute
   * @param attributeDetailList
   */
  private void setAttributeChoiceList(CustomerAttribute attribute, CustomerAttributeListBean attributeDetailList) {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    List<CustomerAttributeChoice> getChoiceList = service.getAttributeChoiceList(attributeDetailList.getCustomerAttributeNo());
    List<CodeAttribute> choiceList = new ArrayList<CodeAttribute>();

    setDefaultSelect(choiceList, attribute.getCustomerAttributeType());
    for (CustomerAttributeChoice choice : getChoiceList) {
      choiceList.add(new NameValue(choice.getCustomerAttributeChoices(), NumUtil.toString(choice.getCustomerAttributeChoicesNo())));
    }
    attributeDetailList.getAttributeChoiceList().addAll(choiceList);
  }

  /**
   * 顧客属性回答を取得します。
   * 
   * @param bean
   * @param attribute
   * @param attributeDetailList
   */
  private void setAttributeAnswerList(CustomerEditBean bean, CustomerAttribute attribute,
      CustomerAttributeListBean attributeDetailList) {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    CustomerSearchCondition condition = new CustomerSearchCondition();
    condition.setCustomerAttributeNo(attributeDetailList.getCustomerAttributeNo());
    condition.setCustomerCode(bean.getCustomerCode());

    List<CustomerAttributeAnswer> getAnswerList = service.getCustomerAttributeAnswer(condition);
    if (getAnswerList.size() < 1) {
      return;
    }

    if (attribute.getCustomerAttributeType().equals(Long.parseLong(CustomerAttributeType.RADIO.getValue()))) {
      // 単一選択の場合

      attributeDetailList.setAttributeAnswer(NumUtil.toString(getAnswerList.get(0).getCustomerAttributeChoicesNo()));
    } else {
      // 複数選択の場合

      List<String> answerItem = new ArrayList<String>();
      List<CustomerAttributeListAnswerBean> answerList = new ArrayList<CustomerAttributeListAnswerBean>();
      for (CustomerAttributeAnswer answer : getAnswerList) {
        CustomerAttributeListAnswerBean answerEdit = new CustomerAttributeListAnswerBean();

        answerEdit.setAttributeAnswerList(NumUtil.toString(answer.getCustomerAttributeChoicesNo()));
        answerList.add(answerEdit);
        answerItem.add(NumUtil.toString(answer.getCustomerAttributeChoicesNo()));
      }
      attributeDetailList.setAttributeAnswerList(answerList);
      attributeDetailList.setAttributeAnswerItem(answerItem);
    }
  }

  /**
   * 単一選択肢の場合、デフォルト値を設定
   * 
   * @param attributeChoiceList
   * @param attributeType
   */
  private void setDefaultSelect(List<CodeAttribute> attributeChoiceList, Long attributeType) {
    if (attributeType.equals(Long.parseLong(CustomerAttributeType.RADIO.getValue()))) {
      attributeChoiceList.add(new NameValue(Messages.getString("web.action.back.customer.CustomerEditBaseAction.2"), ""));
    }
  }

  /**
   * 取得した顧客属性・回答を設定します。
   * 
   * @param bean
   * @param answerList
   */
  public void setCustomerAttributeAnswers(CustomerEditBean bean, List<CustomerAttributeAnswer> answerList) {
    for (CustomerAttributeListBean list : bean.getAttributeList()) {

      CustomerAttributeAnswer attribute;

      // 単一選択の場合

      if (list.getCustomerAttributeType().equals(CustomerAttributeType.RADIO.getValue())) {
        if (StringUtil.hasValue(list.getAttributeAnswer())) {
          attribute = new CustomerAttributeAnswer();
          attribute.setCustomerAttributeNo(Long.parseLong(list.getCustomerAttributeNo()));
          attribute.setCustomerAttributeChoicesNo(Long.parseLong(list.getAttributeAnswer()));
          attribute.setCustomerCode(bean.getCustomerCode());
          answerList.add(attribute);
        }
      } else {
        // 複数選択の場合

        if (list.getAttributeAnswerList().size() > 0) {
          for (String aa : list.getAttributeAnswerItem()) {
            attribute = new CustomerAttributeAnswer();
            attribute.setCustomerAttributeNo(Long.parseLong(list.getCustomerAttributeNo()));
            attribute.setCustomerAttributeChoicesNo(Long.parseLong(aa));
            attribute.setCustomerCode(bean.getCustomerCode());
            answerList.add(attribute);
          }
        }
      }
    }
  }

  /**
   * DB更新顧客データをセット
   * 
   * @param customer
   * @param bean
   */
  public void setCustomerData(Customer customer, CustomerEditBean bean) {
    // 顧客情報
    customer.setCustomerGroupCode(bean.getCustomerGroupCode());
    customer.setFirstName(bean.getFirstName());
    customer.setLastName(bean.getLastName());
    customer.setFirstNameKana(bean.getFirstNameKana());
    customer.setLastNameKana(bean.getLastNameKana());
    customer.setEmail(bean.getEmail());
    customer.setBirthDate(DateUtil.fromString(bean.getBirthDate()));
    customer.setSex(NumUtil.toLong(bean.getSex()));
    customer.setRequestMailType(NumUtil.toLong(bean.getRequestMailType()));
    customer.setLoginErrorCount(NumUtil.toLong(bean.getLoginErrorCount()));
    customer.setLoginLockedFlg(NumUtil.toLong(bean.getLoginLockedFlg()));
    customer.setCaution(bean.getCaution());
    if (bean.isReceivedWithdrawalNotice()) {
      customer.setCustomerStatus(CustomerStatus.RECEIVED_WITHDRAWAL_NOTICE.longValue());
    } else {
      customer.setCustomerStatus(CustomerStatus.MEMBER.longValue());
    }
    // 20120316 os013 add start
    // 会员区分
    customer.setCustomerKbn(0L);
    // 导出区分
    customer.setExportKbn(0L);
    // 20120316 os013 add end
    // 20120510 tuxinwei add start
    customer.setLanguageCode(bean.getLanguageCode());
    // 20120510 tuxinwei add end
    // 20131101 txw add start
    if (StringUtil.hasValue(bean.getErrorTimes())) {
      customer.setErrorTimes(NumUtil.toLong(bean.getErrorTimes()));
    } else {
      customer.setErrorTimes(0L);
    }
    if (StringUtil.hasValue(bean.getLockFlg())) {
      customer.setLockFlg(NumUtil.toLong(bean.getLockFlg()));
    } else {
      customer.setLockFlg(LockFlg.UNLOCK.longValue());
    }
    // 20131101 txw add end

  }

  /**
   * DB更新アドレス帳データをセット
   * 
   * @param address
   * @param bean
   */
  public void setAddressData(CustomerAddress address, CustomerEditBean bean) {
    // アドレス情報
    address.setAddressFirstName(bean.getFirstName());
    address.setAddressLastName(bean.getLastName());
    address.setAddressFirstNameKana(bean.getFirstNameKana());
    address.setAddressLastNameKana(bean.getLastNameKana());
    address.setPostalCode(bean.getPostalCode());
    address.setPrefectureCode(bean.getPrefectureCode());
    address.setAddress1(bean.getAddress1());
    address.setAddress2(bean.getAddress2());
    address.setAddress3(bean.getAddress3());
    address.setAddress4(bean.getAddress4());
    // modify by yl 20111212 start
    // modify by V10-CH 170 start
    // if(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2(),bean.getPhoneNumber3())){
    // address.setPhoneNumber(StringUtil.joint('-', bean.getPhoneNumber1(),
    // bean.getPhoneNumber2(), bean.getPhoneNumber3()));
    // }else
    // if(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2())){
    // address.setPhoneNumber(StringUtil.joint('-', bean.getPhoneNumber1(),
    // bean.getPhoneNumber2()));
    // }else{
    // address.setPhoneNumber("");
    // }
    // add by yl 20111212 start
    if (address != null) {
      address.setPhoneNumber(address.getPhoneNumber());
      address.setMobileNumber(address.getMobileNumber());
    }
    // add by yl 20111212 end
    // modify by yl 20111212 end
    // address.setPhoneNumber(bean.getPhoneNumber1());
    address.setCityCode(bean.getCityCode());
    // modify by V10-CH 170 end
    // Add by V10-CH start
    // address.setMobileNumber(bean.getMobileNumber());
    // Add by V10-CH end
  }
}
