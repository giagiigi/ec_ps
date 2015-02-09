package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerAttributeAnswer;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.AddressBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerRegisterBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020190:新規受注（顧客登録）のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerRegisterRegisterAction extends NeworderBaseAction<NeworderCustomerRegisterBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return customerValidate();
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerInfo customerInfo = createCustomerInfo();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
  //soukai update 2012/1/2 ob start
    //ServiceResult customerResult = service.insertCustomer(customerInfo);
    ServiceResult customerResult = service.insertCustomers(customerInfo);
  //soukai update 2012/1/2 ob end
    if (customerResult.hasError()) {
      for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
        if (result.equals(CustomerServiceErrorContent.DUPLICATED_EMAIL_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.order.NeworderCustomerRegisterRegisterAction.0")));
        } else if (result.equals(CustomerServiceErrorContent.DUPLICATED_LOGINID_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.order.NeworderCustomerRegisterRegisterAction.1")));
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }
    
    //soukai add 2012/01/05 ob start
    setNextUrl("/app/order/address/init/");
    AddressBean addressBean = new AddressBean();
    addressBean.setCustomerCode(customerInfo.getCustomer().getCustomerCode());
    setRequestBean(addressBean);
    return BackActionResult.RESULT_SUCCESS;
    //soukai add 2012/01/05 ob end
    //soukai delete 2012/01/05 ob start
    /*customerInfo = service.getCustomer(customerInfo.getCustomer().getCustomerCode());

    Cashier cashier = null;
    // 決済ショップコードの取得
    String cashierShopCode = this.getLoginInfo().getShopCode();
    if (getCart().get().size() > 0) {
      if (this.getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
        // ショップ個別決済の場合配送ショップコードが決済ショップコードとなる

        cashierShopCode = getCart().get().get(0).getShopCode();
      }
      cashier = CartUtil.createCashier(getCart(), cashierShopCode, customerInfo);
    } else if (getCart().getReserve().size() > 0) {
      CartItem item = getCart().getReserve().get(0);
      if (this.getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
        // ショップ個別決済の場合配送ショップコードが決済ショップコードとなる

        cashierShopCode = item.getShopCode();
      }
      cashier = CartUtil.createCashier(getCart(), cashierShopCode, customerInfo, new Sku(item.getShopCode(), item.getSkuCode()));
    } else {
      // 不正アクセスとして認証処理エラーと同様にログインへ飛ばす
      setNextUrl("/app/common/login");
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(Messages.log("web.action.back.order.NeworderCustomerRegisterRegisterAction.2"));
      return BackActionResult.RESULT_SUCCESS;
    }

    // ポイントの使用可非を取得
    SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = siteService.getPointRule();
    PointFunctionEnabledFlg pointFlg = PointFunctionEnabledFlg.fromValue(pointRule.getPointFunctionEnabledFlg());
    if (pointFlg == PointFunctionEnabledFlg.ENABLED) {
      cashier.setUsablePoint(true);
    } else {
      cashier.setUsablePoint(false);
    }
    
    //クーポンの使用可否を取得
    CouponRule couponRule = siteService.getCouponRule();
    CouponFunctionEnabledFlg couponFlg = CouponFunctionEnabledFlg.fromValue(couponRule.getCouponFunctionEnabledFlg());
    if (couponFlg == CouponFunctionEnabledFlg.ENABLED) {
      cashier.setUsableCoupon(true);
    } else {
      cashier.setUsableCoupon(false);
    }

    NeworderShippingBean nextBean = new NeworderShippingBean();
    nextBean.setCashier(cashier);
    setRequestBean(nextBean);

    setNextUrl("/app/order/neworder_shipping/init/");
    DisplayTransition.add(getBean(), "/app/order/neworder_customer/init/back", getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;*/
    //soukai delete 2012/01/05 ob end
  }

  /**
   * 顧客情報を作成します。

   * 
   * @return 顧客情報
   */
  public CustomerInfo createCustomerInfo() {
    NeworderCustomerRegisterBean bean = getBean();
    CustomerInfo info = new CustomerInfo();

    // 顧客情報
    Customer customer = new Customer();

    customer.setCustomerGroupCode(CustomerConstant.DEFAULT_GROUP_CODE);
    customer.setFirstName(bean.getFirstName());
    customer.setLastName(bean.getLastName());
    customer.setFirstNameKana(bean.getFirstNameKana());
    customer.setLastNameKana(bean.getLastNameKana());
    customer.setEmail(bean.getEmail());
    customer.setBirthDate(DateUtil.fromString(bean.getBirthDate()));
    customer.setSex(Long.parseLong(bean.getSex()));
    customer.setRequestMailType(Long.parseLong(bean.getRequestMailType()));
    customer.setCaution(bean.getCaution());
    customer.setCustomerStatus(Long.parseLong(CustomerStatus.MEMBER.getValue()));
    customer.setPassword(bean.getPassword());
    customer.setLoginErrorCount(0L);
    customer.setLoginLockedFlg(LoginLockedFlg.UNLOCKED.longValue());
    //soukai add 2012/01/05 ob start
    customer.setRestPoint(new BigDecimal("0"));
    //soukai add 2012/01/05 ob end
    // アドレス情報
    CustomerAddress address = new CustomerAddress();
  //soukai delete 2012/1/2 ob start
    // 10.1.3 10150 修正 ここから
    // address.setAddressNo(CustomerConstant.SELFE_ADDRESS_NO);
    // address.setAddressAlias(CustomerConstant.SELFE_ADDRESS_AIES);
    /*address.setAddressNo(CustomerConstant.SELF_ADDRESS_NO);
    address.setAddressAlias(CustomerConstant.SELF_ADDRESS_ALIAS);
    // 10.1.3 10150 修正 ここまで
    address.setAddressFirstName(bean.getFirstName());
    address.setAddressLastName(bean.getLastName());
    address.setAddressFirstNameKana(bean.getFirstNameKana());
    address.setAddressLastNameKana(bean.getLastNameKana());
    address.setPostalCode(bean.getPostalCode());
    address.setPrefectureCode(bean.getPrefectureCode());
    //modify by V10-CH 170 start
//    address.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
//    address.setAddress2(bean.getAddress2());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    address.setAddress1(PrefectureCode.fromValue(bean.getPrefectureCode()).getName());
    address.setAddress2(s.getCityName(bean.getPrefectureCode(), bean.getCityCode()));
    address.setCityCode(bean.getCityCode());
    //modify by V10-CH 170 end
    address.setAddress3(bean.getAddress3());
    address.setAddress4(bean.getAddress4());

    //modify by V10-CH 170 start
    if(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2(),bean.getPhoneNumber3())){
        address.setPhoneNumber(StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2(), bean.getPhoneNumber3()));
      }else if(StringUtil.hasValueAllOf(bean.getPhoneNumber1(),bean.getPhoneNumber2())){
        address.setPhoneNumber(StringUtil.joint('-', bean.getPhoneNumber1(), bean.getPhoneNumber2()));
    }else{
      address.setPhoneNumber("");
    }
   
    //address.setPhoneNumber(bean.getPhoneNumber1());
    //modify by V10-CH 170 end
    
    //Add by V10-CH start
    address.setMobileNumber(bean.getMobileNumber());*/
    //Add by V10-CH end
  //soukai delete 2012/1/2 ob end
    
    //add by lc 2012-03-30 start
    //导出区分
    customer.setExportKbn(0L);
    //add by lc 2012-03-30 end
    customer.setLanguageCode("zh-cn");
    
    List<CustomerAttributeAnswer> answerList = new ArrayList<CustomerAttributeAnswer>();

    info.setCustomer(customer);
    info.setAddress(address);
    info.setAnswerList(answerList);

    return info;
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCustomerRegisterRegisterAction.3");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102019004";
  }

}
