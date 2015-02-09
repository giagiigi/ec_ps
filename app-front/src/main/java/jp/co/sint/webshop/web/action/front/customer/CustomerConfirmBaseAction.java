package jp.co.sint.webshop.web.action.front.customer;

import java.util.List;

import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean.CustomerAttributeListConfirmBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;

/**
 * U2030230:お客様情報登録確認のアクションの基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class CustomerConfirmBaseAction extends WebFrontAction<CustomerConfirmBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CustomerConfirmBean bean = getBean();
    boolean result = validateBean(bean);
    if(result){
      if (StringUtil.isNullOrEmpty(bean.getPhoneNumber1()) && StringUtil.isNullOrEmpty(bean.getPhoneNumber2())
          && StringUtil.isNullOrEmpty(bean.getPhoneNumber3()) && StringUtil.isNullOrEmpty(bean.getMobileNumber())) {
        addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
        result = false;
      }
      else{
        PhoneValidator phoneValidator = new PhoneValidator();
        boolean phoneResult = false;
        phoneResult = phoneValidator.isValid(StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean
            .getPhoneNumber3()));
        if (!phoneResult) {
          addErrorMessage(phoneValidator.getMessage());
        }
        result &= phoneResult;
      }
//      else{
//        if(StringUtil.hasValue(bean.getPhoneNumber1()) || StringUtil.hasValue(bean.getPhoneNumber2()) || StringUtil.hasValue(bean.getPhoneNumber3())){
//          if(!(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2()) && bean.getPhoneNumber1().length()>1 && bean.getPhoneNumber2().length()>5)){
//            addErrorMessage(WebMessage.get(ValidationMessage.TRUE_NUMBER));
//            result = false;
//          }
//        }
//      }
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
   * 取得した顧客属性・回答を設定します。
   * 
   * @param bean
   * @param answerList
   */
  public void setCustomerAttributeAnswers(CustomerConfirmBean bean, List<CustomerAttributeAnswer> answerList) {
    for (CustomerAttributeListConfirmBean confirm : bean.getAttributeList()) {

      CustomerAttributeAnswer attribute;

      // 単一選択の場合
      if (confirm.getCustomerAttributeType().equals(CustomerAttributeType.RADIO.getValue())) {
        if (StringUtil.hasValue(confirm.getAttributeAnswer())) {
          attribute = new CustomerAttributeAnswer();
          attribute.setCustomerAttributeNo(Long.parseLong(confirm.getCustomerAttributeNo()));
          attribute.setCustomerAttributeChoicesNo(Long.parseLong(confirm.getAttributeAnswer()));
          attribute.setCustomerCode(bean.getCustomerCode());
          answerList.add(attribute);
        }
      } else {
        // 複数選択の場合
        if (confirm.getAttributeAnswerList().size() > 0) {
          for (String answer : confirm.getAttributeAnswerList()) {
            attribute = new CustomerAttributeAnswer();
            attribute.setCustomerAttributeNo(Long.parseLong(confirm.getCustomerAttributeNo()));
            attribute.setCustomerAttributeChoicesNo(Long.parseLong(answer));
            attribute.setCustomerCode(bean.getCustomerCode());
            answerList.add(attribute);
          }
        }
      }
    }
  }

  /**
   * 登録データを設定
   * 
   * @param bean
   * @param customer
   * @param address
   */
  public void setEditData(CustomerConfirmBean bean, Customer customer, CustomerAddress address) {
    // 顧客情報
    customer.setCustomerCode(bean.getCustomerCode());
    customer.setFirstName(bean.getFirstName());
    customer.setLastName(bean.getLastName());
    customer.setFirstNameKana(bean.getFirstNameKana());
    customer.setLastNameKana(bean.getLastNameKana());
    customer.setEmail(bean.getEmail());
    customer.setBirthDate(DateUtil.fromString(bean.getBirthDate()));
    customer.setSex(Long.parseLong(Sex.fromName(bean.getSex()).getValue()));
    customer.setRequestMailType(Long.parseLong(RequestMailType.fromName(bean.getRequestMailType()).getValue()));
    customer.setCustomerStatus(Long.parseLong(CustomerStatus.MEMBER.getValue()));

    // アドレス情報
    address.setCustomerCode(bean.getCustomerCode());
    // 10.1.3 10150 修正 ここから
    // address.setAddressNo(CustomerConstant.SELFE_ADDRESS_NO);
    // address.setAddressAlias(CustomerConstant.SELFE_ADDRESS_AIES);
    address.setAddressNo(CustomerConstant.SELF_ADDRESS_NO);
    address.setAddressAlias(CustomerConstant.SELF_ADDRESS_ALIAS);
    // 10.1.3 10150 修正 ここまで
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
//  modify by V10-CH 170 start
    if(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2(),bean.getPhoneNumber3())){
      address.setPhoneNumber(StringUtil.joint('-',bean.getPhoneNumber1(),bean.getPhoneNumber2(),bean.getPhoneNumber3()));
    }else if(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2())){
      address.setPhoneNumber(StringUtil.joint('-',bean.getPhoneNumber1(),bean.getPhoneNumber2()));
    }else{
      address.setPhoneNumber("");
    }
    
    //Add by V10-CH start
    address.setMobileNumber(bean.getMobileNumber());
    //Add by V10-CH end
    address.setCityCode(bean.getCityCode());
//  modify by V10-CH 170 end
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
