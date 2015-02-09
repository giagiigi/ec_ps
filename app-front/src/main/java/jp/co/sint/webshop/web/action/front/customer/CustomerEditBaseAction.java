package jp.co.sint.webshop.web.action.front.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit1Bean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean;
import jp.co.sint.webshop.web.bean.front.customer.MobiCustomerEditBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean.CustomerAttributeListConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean.CustomerAttributeListAnswerBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit2Bean.CustomerAttributeListBean;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * お客様情報登録のアクションの基底クラスです。

 * 
 * @author System Integrator Corp.
 * @param <T>
 */
public abstract class CustomerEditBaseAction<T extends UIFrontBean> extends WebFrontAction<T> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CustomerConfirmBean confirmBean = (CustomerConfirmBean) getSessionContainer().getTempBean();

    if (confirmBean == null || confirmBean.isFailedTransitionFlg()) {
      setNextUrl("/app/common/index");
      getSessionContainer().logout();
      return false;
    }
    getSessionContainer().setTempBean(confirmBean);

    return true;
  }

  /**
   * CustomerConfirmBeanのバリデーションチェックを行います。

   */
  public boolean validateConfirm() {
    CustomerConfirmBean confirmBean = (CustomerConfirmBean) getSessionContainer().getTempBean();
    boolean validate = validateBean(confirmBean);
    if (!validate) {
      setNextUrl("/app/common/index");
      getSessionContainer().logout();
      return false;
    }
    getSessionContainer().setTempBean(confirmBean);

    return true;
  }

  /**
   * 共通Beanの存在チェックを行います。

   */
  public boolean validateTempBean() {
    CustomerConfirmBean confirmBean = (CustomerConfirmBean) getSessionContainer().getTempBean();
    if (confirmBean == null) {
      setNextUrl("/app/common/index");
      getSessionContainer().logout();
      return false;
    }
    getSessionContainer().setTempBean(confirmBean);

    return true;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * 保持されている入力内容を入力1Beanに設定

   * 
   * @param edit1Bean
   * @param confirmBean
   */
  public void setTempToEdit1(CustomerEdit1Bean edit1Bean, CustomerConfirmBean confirmBean) {
    // 顧客情報
    //顧客コード
    edit1Bean.setCustomerCode(confirmBean.getCustomerCode());
    //氏名(名)
    edit1Bean.setFirstName(confirmBean.getFirstName());
    //氏名(姓)
    edit1Bean.setLastName(confirmBean.getLastName());
    //氏名カナ(名)
    edit1Bean.setFirstNameKana(confirmBean.getFirstNameKana());
    //氏名カナ(姓)
    edit1Bean.setLastNameKana(confirmBean.getLastNameKana());
    //メールアドレス
    edit1Bean.setEmail(confirmBean.getEmail());
    //パスワード
    edit1Bean.setPassword(confirmBean.getPassword());
    //生年月日
    edit1Bean.setBirthDate(confirmBean.getBirthDate());
    //性別
    edit1Bean.setSex(Sex.fromName(confirmBean.getSex()).getValue());
    //情報メール
    edit1Bean.setRequestMailType(RequestMailType.fromName(confirmBean.getRequestMailType()).getValue());
    //更新日時
    edit1Bean.setUpdatedDatetimeCustomer(confirmBean.getUpdatedDatetimeCustomer());
    edit1Bean.setHasCustomerAttributeFlg(confirmBean.isHasCustomerAttributeFlg());

    // アドレス帳情報

    edit1Bean.setPostalCode(confirmBean.getPostalCode());
    edit1Bean.setPrefectureCode(confirmBean.getPrefectureCode());
    edit1Bean.setAddress1(confirmBean.getAddress1());
    edit1Bean.setAddress2(confirmBean.getAddress2());
    edit1Bean.setAddress3(confirmBean.getAddress3());
    edit1Bean.setAddress4(confirmBean.getAddress4());
    edit1Bean.setPhoneNumber1(confirmBean.getPhoneNumber1());
//  modify by V10-CH 170 start
    edit1Bean.setPhoneNumber2(confirmBean.getPhoneNumber2());
    edit1Bean.setPhoneNumber3(confirmBean.getPhoneNumber3());
    //Add by V10-CH start
    edit1Bean.setMobileNumber(confirmBean.getMobileNumber());
    //Add by V10-CH end
    edit1Bean.setCityCode(confirmBean.getCityCode());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    edit1Bean.setCityList(s.getCityNames(edit1Bean.getPrefectureCode()));
//  modify by V10-CH 170 end
    edit1Bean.setUpdatedDatetimeAddress(confirmBean.getUpdatedDatetimeAddress());
    edit1Bean.setDisplayMode(confirmBean.getDisplayMode());
  }

  /**
   * 入力1Beanを共通Beanに設定

   * 
   * @param edit1Bean
   * @param confirmBean
   */
  public void setEdit1ToTemp(CustomerEdit1Bean edit1Bean, Customer customer) {
    // 顧客情報

    //顧客コード
    customer.setCustomerCode(edit1Bean.getCustomerCode());
    //氏名(名)
    customer.setFirstName(edit1Bean.getFirstName());
    //氏名(姓)
    if (StringUtil.hasValue(edit1Bean.getLastName())) {
      customer.setLastName(edit1Bean.getLastName());
    } else {
      customer.setLastName(edit1Bean.getEmail().split("@")[0]);
    }
    //氏名カナ(名)
    customer.setFirstNameKana(edit1Bean.getFirstNameKana());
    //氏名カナ(姓)
    customer.setLastNameKana(edit1Bean.getLastNameKana());
    //メールアドレス
    customer.setEmail(edit1Bean.getEmail());
    //パスワード
    customer.setPassword(edit1Bean.getPassword());
    //生年月日
    if (StringUtil.hasValue(edit1Bean.getBirthDate())) {
      customer.setBirthDate(DateUtil.fromString(edit1Bean.getBirthDate()));
    } else {
      customer.setBirthDate(DateUtil.fromString("3000/01/01"));
    }
    //性別
    if (StringUtil.hasValue(edit1Bean.getSex())) {
      customer.setSex(Long.parseLong(Sex.fromValue(edit1Bean.getSex()).getValue()));
    } else {
      customer.setSex(Long.parseLong(Sex.UNKNOWN.getValue()));
    }
    //情報メール
    customer.setRequestMailType(Long.parseLong(RequestMailType.fromValue(edit1Bean.getRequestMailType()).getValue()));
    //更新日時
    customer.setUpdatedDatetime(edit1Bean.getUpdatedDatetimeCustomer());
    //ログイン失敗回数
    customer.setLoginErrorCount(0L);
    //顧客グループコード
    
    
    
    if(StringUtil.isNullOrEmpty(customer.getCustomerGroupCode())){
      customer.setCustomerGroupCode(CustomerConstant.DEFAULT_GROUP_CODE);
    }else{
      customer.setCustomerGroupCode(customer.getCustomerGroupCode());
    }
    //工会注册  20141011 hdh add start
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0 && tmpArgs[0].equals("company")) {
      if(StringUtil.hasValue(edit1Bean.getCompanyCode())){
        CustomerService customerSev = ServiceLocator.getCustomerService(getLoginInfo());
        CustomerGroup cg = customerSev.getCustomerGroup("4");
        if(cg !=null){
          customer.setCustomerGroupCode("4");
        }
      }
    }
    // 20141011 hdhd add end
    
    //ログインロックフラグ
    customer.setLoginLockedFlg(Long.parseLong(LoginLockedFlg.UNLOCKED.getValue()));
    //顧客ステータス
    customer.setCustomerStatus(Long.parseLong(CustomerStatus.MEMBER.getValue())); 
    //20111225 os013 add start
    //会员区分
    customer.setCustomerKbn(edit1Bean.getCustomerKbn());
    //导出区分
    customer.setExportKbn(0L);
    if (StringUtil.hasValue(edit1Bean.getLanguageCode())) {
      customer.setLanguageCode(edit1Bean.getLanguageCode());
    } else {
      customer.setLanguageCode("zh-cn");
    }
    
    customer.setErrorTimes(0L);
    
    customer.setLockFlg(0L);
    

  }
  /**
   * 入力1Beanを共通Beanに設定

   * 
   * @param edit1Bean
   * @param confirmBean
   */
  public void setEdit1ToTemp(MobiCustomerEditBean edit1Bean, Customer customer) {
    // 顧客情報
    
    //顧客コード
    customer.setCustomerCode(edit1Bean.getCustomerCode());
    //氏名(名)
    customer.setFirstName(edit1Bean.getFirstName());
    //氏名(姓)
    customer.setLastName(edit1Bean.getLastName());
    //氏名カナ(名)
    customer.setFirstNameKana(edit1Bean.getFirstNameKana());
    //氏名カナ(姓)
    customer.setLastNameKana(edit1Bean.getLastNameKana());
    //メールアドレス
    customer.setEmail(edit1Bean.getEmail());
    //パスワード
    customer.setPassword(edit1Bean.getPassword());
    //生年月日
    customer.setBirthDate(DateUtil.fromString(edit1Bean.getBirthDate()));
    //性別
    customer.setSex(Long.parseLong(Sex.fromValue(edit1Bean.getSex()).getValue()));
    //情報メール
    customer.setRequestMailType(Long.parseLong(RequestMailType.fromValue(edit1Bean.getRequestMailType()).getValue()));
    //更新日時
    customer.setUpdatedDatetime(edit1Bean.getUpdatedDatetimeCustomer());
    //ログイン失敗回数
    customer.setLoginErrorCount(0L);
    //顧客グループコード
    if(StringUtil.isNullOrEmpty(customer.getCustomerGroupCode())){
      customer.setCustomerGroupCode(CustomerConstant.DEFAULT_GROUP_CODE);
    }else{
      customer.setCustomerGroupCode(customer.getCustomerGroupCode());
    }
    //ログインロックフラグ
    customer.setLoginLockedFlg(Long.parseLong(LoginLockedFlg.UNLOCKED.getValue()));
    //顧客ステータス
    customer.setCustomerStatus(Long.parseLong(CustomerStatus.MEMBER.getValue())); 
    //20111225 os013 add start
    //会员区分
    customer.setCustomerKbn(edit1Bean.getCustomerKbn());
    //20111225 os013 add end
    //20120316 os013 add start
    //导出区分
    customer.setExportKbn(0L);
    //20120316 os013 add end
    //20120510 tuxinwei add start
    customer.setLanguageCode(edit1Bean.getLanguageCode());
    //20120510 tuxinwei add end
    customer.setErrorTimes(0L);
    
    customer.setLockFlg(0L);
    //    // アドレス帳情報
//
//    confirmBean.setPostalCode(edit1Bean.getPostalCode());
//    confirmBean.setPrefectureCode(edit1Bean.getPrefectureCode());
//    confirmBean.setAddress1(edit1Bean.getAddress1());
//    confirmBean.setAddress2(edit1Bean.getAddress2());
//    confirmBean.setAddress3(edit1Bean.getAddress3());
//    confirmBean.setAddress4(edit1Bean.getAddress4());
//    confirmBean.setPhoneNumber1(edit1Bean.getPhoneNumber1());
//    //modify by V10-CH 170 start
//    confirmBean.setPhoneNumber2(edit1Bean.getPhoneNumber2());
//    confirmBean.setPhoneNumber3(edit1Bean.getPhoneNumber3());
//    confirmBean.setMobileNumber(edit1Bean.getMobileNumber());
//    //modify by V10-CH 170 start
//    confirmBean.setUpdatedDatetimeAddress(edit1Bean.getUpdatedDatetimeAddress());
//
//    confirmBean.setDisplayMode(edit1Bean.getDisplayMode());
//    
//    // modify by V10-CH 170 start
//    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
//    confirmBean.setPrefectureCode(edit1Bean.getPrefectureCode());
//    confirmBean.setCityCode(edit1Bean.getCityCode());
//    confirmBean.setAddress1(PrefectureCode.fromValue(edit1Bean.getPrefectureCode()).getName());
//    confirmBean.setAddress2(s.getCityName(edit1Bean.getPrefectureCode(), edit1Bean.getCityCode()));
//    // modify by V10-CH 170 start
    
  }

  /**
   * 入力2Beanを共通Beanに設定

   * 
   * @param edit2Bean
   *          入力2Bean
   * @param confirmBean
   *          共通Bean
   */
  public void setEdit2ToTemp(CustomerEdit2Bean edit2Bean, CustomerConfirmBean confirmBean) {
    List<CustomerAttributeListConfirmBean> confirmAttributeList = new ArrayList<CustomerAttributeListConfirmBean>();

    List<CustomerAttributeListBean> edit2AttributeList = edit2Bean.getAttributeList();

    for (CustomerAttributeListBean edit : edit2AttributeList) {
      CustomerAttributeListConfirmBean confirmAttributeDetail = new CustomerAttributeListConfirmBean();

      confirmAttributeDetail.setCustomerAttributeNo(edit.getCustomerAttributeNo());
      confirmAttributeDetail.setCustomerAttributeName(edit.getCustomerAttributeName());
      confirmAttributeDetail.setCustomerAttributeType(edit.getCustomerAttributeType());

      // 単一選択肢の場合
      if (StringUtil.hasValue(edit.getAttributeAnswer())) {
        for (CodeAttribute ac : edit.getAttributeChoiceList()) {
          if (ac.getValue().equals(edit.getAttributeAnswer())) {
            confirmAttributeDetail.setDispAttributeAnswer(ac.getName());
          }
        }
      }
      // 複数選択肢の場合
      List<String> answerList = new ArrayList<String>();
      for (CustomerAttributeListAnswerBean answer : edit.getAttributeAnswerList()) {
        for (CodeAttribute choice : edit.getAttributeChoiceList()) {
          if (answer.getAttributeAnswerList().equals(choice.getValue())) {
            confirmAttributeDetail.getDispAttributeAnswerList().add(choice.getName());
          }
        }
        answerList.add(answer.getAttributeAnswerList());
      }
      confirmAttributeDetail.setAttributeAnswer(edit.getAttributeAnswer());
      confirmAttributeDetail.setAttributeAnswerList(answerList);

      confirmAttributeList.add(confirmAttributeDetail);
    }

    confirmBean.setAttributeList(confirmAttributeList);
  }

  /**
   * 顧客属性を表示します。

   * 
   * @param bean
   *          入力2Bean
   * @param mode
   *          モード

   */
  public void setAttributeList(CustomerEdit2Bean bean, String mode) {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    List<CustomerAttribute> getAttributeList = service.getAttributeList();
    List<CustomerAttributeListBean> attributeList = new ArrayList<CustomerAttributeListBean>();

    for (CustomerAttribute list : getAttributeList) {
      CustomerAttributeListBean attributeDetailList = new CustomerAttributeListBean();

      attributeDetailList.setCustomerAttributeNo(Long.toString(list.getCustomerAttributeNo()));
      attributeDetailList.setCustomerAttributeName(list.getCustomerAttributeName());
      attributeDetailList.setCustomerAttributeType(Long.toString(list.getCustomerAttributeType()));
      attributeDetailList.setAttributeTextName(Long.toString(list.getCustomerAttributeNo()));

      // 顧客属性選択肢の取得

      setAttributeChoiceList(list, attributeDetailList);

      // 顧客属性回答の取得
      if (mode.equals("update")) {
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
   *          顧客属性

   * @param attributeDetailList
   *          顧客属性選択肢
   */
  public void setAttributeChoiceList(CustomerAttribute attribute, CustomerAttributeListBean attributeDetailList) {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    List<CustomerAttributeChoice> getChoiceList = service.getAttributeChoiceList(attributeDetailList.getCustomerAttributeNo());
    List<CodeAttribute> choiceList = new ArrayList<CodeAttribute>();

    setDefaultSelect(choiceList, attribute.getCustomerAttributeType());
    for (CustomerAttributeChoice choice : getChoiceList) {
      choiceList.add(new NameValue(choice.getCustomerAttributeChoices(), Long.toString(choice.getCustomerAttributeChoicesNo())));
    }
    attributeDetailList.getAttributeChoiceList().addAll(choiceList);
  }

  /**
   * 顧客属性回答を取得します。

   * 
   * @param service
   * @param bean
   * @param attribute
   * @param attributeDetailList
   */
  private void setAttributeAnswerList(CustomerEdit2Bean bean, CustomerAttribute attribute,
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

      attributeDetailList.setAttributeAnswer(Long.toString(getAnswerList.get(0).getCustomerAttributeChoicesNo()));
    } else {
      // 複数選択の場合

      List<String> answerItem = new ArrayList<String>();
      List<CustomerAttributeListAnswerBean> answerList = new ArrayList<CustomerAttributeListAnswerBean>();
      for (CustomerAttributeAnswer answer : getAnswerList) {
        CustomerAttributeListAnswerBean answerEdit = new CustomerAttributeListAnswerBean();
        answerEdit.setAttributeAnswerList(Long.toString(answer.getCustomerAttributeChoicesNo()));
        answerList.add(answerEdit);
        answerItem.add(Long.toString(answer.getCustomerAttributeChoicesNo()));
      }
      attributeDetailList.setAttributeAnswerList(answerList);
      attributeDetailList.setAttributeAnswerItem(answerItem); // リスト表示用
    }
  }

  /**
   * 単一選択肢の場合、デフォルト値を設定
   * 
   * @param attributeChoiceList
   * @param attributeType
   */
  public void setDefaultSelect(List<CodeAttribute> attributeChoiceList, Long attributeType) {
    if (attributeType.equals(Long.parseLong(CustomerAttributeType.RADIO.getValue()))) {
      attributeChoiceList.add(new NameValue(Messages.getString(
      "web.action.front.customer.CustomerEditBaseAction.0"), ""));
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
