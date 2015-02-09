package jp.co.sint.webshop.web.action.front.customer;

import jp.co.sint.webshop.data.dao.CompanyCustomerDao;
import jp.co.sint.webshop.data.dto.CompanyCustomer;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.service.AuthorizationService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit1Bean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerResultBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.BackTransitionInfo;
import jp.co.sint.webshop.web.webutility.ClientType;

/**
 * U2030210:お客様情報登録1のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit1NextAction extends CustomerEditBaseAction<CustomerEdit1Bean> {

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
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0 && tmpArgs[0].equals("back")) {
      return true;
    }
    boolean result = true;

    CustomerEdit1Bean bean = getBean();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    // 更新処理
    //有顧客コード但会员区分为1时，用新規登録checking
    if(bean.getCustomerKbn()==null){
      bean.setCustomerKbn(0L);
    }
      if (StringUtil.hasValue(bean.getCustomerCode())&&bean.getCustomerKbn()!=1) {
        result = validateBean(getBean());
        
        if (StringUtil.getLength(bean.getLastName()) > 28) {
          addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
              .getString("web.action.front.customer.CustomerEdit1NextAction.7")));
          result = false;
        }
        // メールアドレス重複チェック
  
        if (StringUtil.hasValue(bean.getEmail())) {
          boolean deptEmail = service.isAvailableEmailUpdate(bean.getCustomerCode(), bean.getEmail());
          if (!deptEmail) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
                .getString("web.action.front.customer.CustomerEdit1NextAction.0")));
            result = false;
          }
        }
  
        // 顧客存在チェック
        CustomerInfo info = service.getCustomer(bean.getCustomerCode());
        if (info.getCustomer() == null) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.front.customer.CustomerEdit1NextAction.1")));
          result = false;
        }else{
            bean.setBirthDate(DateUtil.toDateString(info.getCustomer().getBirthDate()));
        }

      // 新規登録処理 
      } else {    
        
          // 新規登録処理 PC
          if (getClient().equals(ClientType.OTHER)){
            
              result = validateItems(getBean(), "email");
              
              if (StringUtil.hasValue(bean.getEmail())) {
                boolean deptEmail = service.isAvailableEmailInsert(bean.getEmail());
                if (!deptEmail) {
                  addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
                      .getString("web.action.front.customer.CustomerEdit1NextAction.0")));
                  result = false;
                }
              }
            
              if (StringUtil.isNullOrEmpty(bean.getPassword())) {
                addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
                    .getString("web.action.front.customer.CustomerEdit1NextAction.3")));
                result = false;
              }
                
              if (StringUtil.hasValue(bean.getPassword())) { 
                PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
                if (!policy.isValidPassword(bean.getPassword())) {
                  addErrorMessage(WebMessage.get(MypageErrorMessage.PASSWORD_POLICY_ERROR));
                  result = false;
                }
                if (StringUtil.isNullOrEmpty(bean.getPasswordCon())) {
                  addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
                      .getString("web.action.front.customer.CustomerEdit1NextAction.4")));
                  result = false;
                }else{
                  // 不一致エラー
                  if (StringUtil.hasValue(bean.getPasswordCon()) && !bean.getPassword().equals(bean.getPasswordCon())) {
                    addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_PASSWORD));
                    result = false;
                  }
                }
              }
              
              //从Session里取得系统生成的验证码
              String verifyCode=getSessionContainer().getVerifyCode();  
              //判断验证码是否输入
              if (StringUtil.isNullOrEmpty(bean.getVerificationCode())) {
                addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
                    .getString("web.action.front.customer.CustomerEdit1NextAction.6")));
                result = false;
              }else{  
                //验证码不一致エラー
                if(!verifyCode.equalsIgnoreCase(bean.getVerificationCode())){
                  addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_VERIFY_CODE));
                  result = false;
                }
              }
            
              //工会注册
              if (tmpArgs.length > 0 && tmpArgs[0].equals("company")) {
                  result = validateItems(getBean(), "companyCode");
                  CompanyCustomerDao ccDao = DIContainer.getDao(CompanyCustomerDao.class);
                  CompanyCustomer cc = ccDao.load(getBean().getCompanyCode());
                  if (cc == null ) {
                    addErrorMessage("web.action.front.customer.CustomerEdit1NextAction.10");
                    result = false;
                  } else if (StringUtil.hasValue(cc.getCustomerCode())) {
                    addErrorMessage("web.action.front.customer.CustomerEdit1NextAction.11");
                    result = false;
                  }
              }
            
          } else {
            
            result = validateBean(getBean());
            //验证生日是否输入
            if(!StringUtil.hasValue(bean.getBirthDate())){
              addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
                        .getString("web.action.front.customer.CustomerEdit1NextAction.8")));
              result=false;
            }
            if (StringUtil.getLength(bean.getLastName()) > 28) {
              addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
                  .getString("web.action.front.customer.CustomerEdit1NextAction.7")));
              result = false;
            }
     
            if(!StringUtil.isNullOrEmpty(bean.getEmail())){
              // メールアドレス重複チェック
               if (StringUtil.hasValue(bean.getEmail())) {
                 boolean deptEmail = service.isAvailableEmailInsert(bean.getEmail());
                 if (!deptEmail) {
                   addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
                       .getString("web.action.front.customer.CustomerEdit1NextAction.0")));
                   result = false;
                 }
               }
              if (StringUtil.isNullOrEmpty(bean.getEmailCon())) {
                addErrorMessage(Messages
                    .getString("web.action.front.customer.CustomerEdit1NextAction.5"));
                result = false;
              }else{
                // 不一致エラー
                if(!bean.getEmail().equals(bean.getEmailCon())){ 
                  addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_EMAIL));
                  result = false;
                }
              }
              
            }
      
            if (StringUtil.isNullOrEmpty(bean.getPassword())) {
              addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
                  .getString("web.action.front.customer.CustomerEdit1NextAction.3")));
              result = false;
            }
              
            if (StringUtil.hasValue(bean.getPassword())) { 
              PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
              if (!policy.isValidPassword(bean.getPassword())) {
                addErrorMessage(WebMessage.get(MypageErrorMessage.PASSWORD_POLICY_ERROR));
                result = false;
              }
              if (StringUtil.isNullOrEmpty(bean.getPasswordCon())) {
                addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
                    .getString("web.action.front.customer.CustomerEdit1NextAction.4")));
                result = false;
              }else{
                // 不一致エラー
                if (StringUtil.hasValue(bean.getPasswordCon()) && !bean.getPassword().equals(bean.getPasswordCon())) {
                  addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_PASSWORD));
                  result = false;
                }
              }
            }
          
            //从Session里取得系统生成的验证码
            String verifyCode=getSessionContainer().getVerifyCode();  
            //判断验证码是否输入
            if (StringUtil.isNullOrEmpty(bean.getVerificationCode())) {
              addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
                  .getString("web.action.front.customer.CustomerEdit1NextAction.6")));
              result = false;
            }else{  
              //验证码不一致エラー
              if(!verifyCode.equalsIgnoreCase(bean.getVerificationCode())){
                addErrorMessage(WebMessage.get(ValidationMessage.NOT_SAME_VERIFY_CODE));
                result = false;
              }
            }
            
          }
      }
    // add by V10-CH 170 start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    // add by V10-CH 170 end   
    
    return result;
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
      setNextUrl("/app/customer/customer_edit1/back");

      return FrontActionResult.RESULT_SUCCESS;
    }
    getSessionContainer().getVerifyCode();
    CustomerEdit1Bean edit1Bean = getBean();

    // 都道府県名を取得
    //modify by V10-CH 170 start
    //edit1Bean.setAddress1(PrefectureCode.fromValue(edit1Bean.getPrefectureCode()).getName());
    //modify by V10-CH 170 end
    // 入力内容1を保持
    
    //setEdit1ToTemp(edit1Bean, confirmBean);
    CustomerService customerSv = ServiceLocator.getCustomerService(getLoginInfo());
    //CustomerInfo info = new CustomerInfo();

    Customer customer = new Customer();
    // データベース更新処理
    ServiceResult customerResult;
    //更新场合
    if (StringUtil.hasValue(edit1Bean.getCustomerCode())) {
   // 顧客情報
      CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
      //顾客信息取得
      CustomerInfo info = service.getCustomer(edit1Bean.getCustomerCode());
      customer = info.getCustomer();
   
      //画面取得顾客信息赋值
      setEdit1ToTemp(edit1Bean,customer);
      //20111225 os013 add start
      if(edit1Bean.getCustomerKbn()==1){
        // パスワードをハッシュ化
        customer.setPassword(PasswordUtil.getDigest(customer.getPassword()));
        //会员区分
        customer.setCustomerKbn(0L);
      }
      //20111225 os013 add end
      
      //顾客信息更新
      customerResult = customerSv.updateCustomer1(customer);
   // DBエラーの有無によって次画面のURLを制御する
      if (customerResult.hasError()) {
        for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
          if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            // service内部Validationエラー
            return FrontActionResult.SERVICE_VALIDATION_ERROR;
          } else {
            return FrontActionResult.SERVICE_ERROR;
          }
        }
        setNextUrl(null);
        setRequestBean(edit1Bean);

        return FrontActionResult.RESULT_SUCCESS;
      }

     // edit1Bean.setComplete(true);

      // ログイン情報をセッションに格納し、セッションを再作成する
      CustomerInfo loginCustomer = service.getCustomer(edit1Bean.getCustomerCode());
      LoginInfo login = WebLoginManager.createFrontLoginInfo(loginCustomer.getCustomer(),getLoginInfo().isCoupon());
      getSessionContainer().login(login);
      //20111230 os013 add start
      //判断是否是购物中支付宝会员信息补足
      if(edit1Bean.isMoveFlg()){
    	  //购物中支付宝会员信息补足
    	  setNextUrl("/app/cart/cart/move/shipping/00000000///");
      }else{
      //20111230 os013 add end
    	  setNextUrl("/app/customer/customer_result");
      }
     

      CustomerResultBean nextBean = new CustomerResultBean();
      nextBean.setUpdateFlg(true);
      setRequestBean(nextBean);

      return FrontActionResult.RESULT_SUCCESS;
      
    }else{//登录场合
      // 顧客情報
      //画面取得顾客信息赋值
      setEdit1ToTemp(edit1Bean,customer);
      
      // 20141015 hdh add start
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      
      customer.setLanguageCode(currentLanguageCode);
      // 20141015 hdh add end
      
      String originalPassword = customer.getPassword();
      //顾客信息登录
      customerResult = customerSv.insertCustomer1(customer,edit1Bean.getCompanyCode());
      // 顧客情報更新時エラー
      if (customerResult.hasError()) {
        for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
          if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            // service内部Validationエラー
            return FrontActionResult.SERVICE_VALIDATION_ERROR;
          } else {
            return FrontActionResult.SERVICE_ERROR;
          }
        }
        setNextUrl("/app/customer/customer_edit1/init");

        setRequestBean(edit1Bean);
      }
      AuthorizationService authService = ServiceLocator.getAuthorizationService(getLoginInfo());
      ServiceResult authResult = authService.authorizeCustomer(customer.getLoginId(), originalPassword);
      //登録成功した情報で認証し、失敗する（＝登録できていない）場合は
      //明らかにエラー
      if (authResult.hasError()) {
          return FrontActionResult.SERVICE_ERROR;
      }

      LoginInfo login = WebLoginManager.createFrontLoginInfo(customer);
      getSessionContainer().login(login);
      BackTransitionInfo afterLoginInfo = getSessionContainer().getAfterLoginInfo();
      if (StringUtil.hasValue(afterLoginInfo.getUrl())) {
        //20120112 os013 add start 
        //从我的页面进入会员注册
        if(afterLoginInfo.getUrl().equals("/app/mypage/mypage")){
          setNextUrl("/app/customer/customer_result");
        }else{
        //20120112 os013 add end
          setNextUrl(afterLoginInfo.getUrl());
        }
      }else{
        setNextUrl("/app/customer/customer_result");
      }
      setRequestBean(edit1Bean);
      
      return FrontActionResult.RESULT_SUCCESS;
    }

    
    
    
    
//    getSessionContainer().setTempBean(confirmBean);
//    
//    //Add by V10-CH start
//    String key = "";
//    String value= "";
//    String url = "app/customer/customer_edit2/" + confirmBean.getNextPage();
//    Cookie cookie = new Cookie(key, value);   
//    cookie.setMaxAge(600); 
//    cookie.setPath(url);
//    //Add by V10-CH end
//    
//    // 次画面URL設定
//    if (confirmBean.isHasCustomerAttributeFlg()) {
//      setNextUrl("/app/customer/customer_edit2/" + confirmBean.getNextPage());
//    } else {
//      setNextUrl("/app/customer/customer_confirm/init");
//      setRequestBean(confirmBean);
//    }
//
//    // 前画面情報設定
//
//    DisplayTransition.add(getBean(), "/app/customer/customer_edit1/next/back", getSessionContainer());
//
    
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
