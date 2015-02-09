package jp.co.sint.webshop.web.action.front.customer;

import java.util.List;

import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.CustomerEdit1Bean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.common.AuthorizationErrorMessage;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit1InitAction extends CustomerEditBaseAction<CustomerEdit1Bean> {

  // 操作成功提示
  private static final String COMPLETE = "complete";
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
	String move = getPathInfo(0);
	
    CustomerEdit1Bean bean = new CustomerEdit1Bean();
    // 2013/04/17 优惠券对应 ob add end
    String url[] = this.getRequestParameter().getPathArgs();
    if (url != null && url.length > 0) {
      if (COMPLETE.equals(url[0])) {
        addInformationMessage(WebMessage.get(AuthorizationErrorMessage.MOBILE_COMPLETE));
      }
    }
    // 2013/04/17 优惠券对应 ob add end
    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();
    //20111225 os013 add start
    String customerKbn="";
    //20111225 os013 add end
    boolean update = false;
    CustomerInfo info=new CustomerInfo();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (StringUtil.hasValue(customerCode)) {
      //20111225 os013 add start
      //customerCode不为空并且customerKbn等于1时，表示支付宝会员还未成为正式会员
      info = service.getCustomer(customerCode);
      customerKbn=""+info.getCustomer().getCustomerKbn();
      if(!customerKbn.equals("1")){
      //20111225 os013 add end
        update = true;
      }
    }

   

    // 更新処理の場合データ取得
    CustomerConfirmBean confirmBean = new CustomerConfirmBean();
    Customer customer=new Customer();
    if (update) {
      info = service.getCustomer(customerCode);

      customer = info.getCustomer();
//      CustomerAddress address = info.getAddress();

      // 顧客/退会済み顧客、またはアドレス帳が存在しない場合、エラー画面へ遷移
      //20111213 lirong update start
      //if (service.isNotFound(customerCode) || service.isInactive(customerCode) || address == null) {
      if (service.isNotFound(customerCode) || service.isInactive(customerCode) ) {
      //20111213 lirong update end
        setNextUrl("/app/common/index");

        getSessionContainer().logout();
        return FrontActionResult.RESULT_SUCCESS;
      }

      // 顧客情報
      bean.setCustomerCode(customerCode);
      bean.setFirstName(customer.getFirstName());
      bean.setLastName(customer.getLastName());
      bean.setFirstNameKana(customer.getFirstNameKana());
      bean.setLastNameKana(customer.getLastNameKana());
      bean.setEmail(customer.getEmail());
      bean.setPassword(customer.getPassword());
      bean.setBirthDate(DateUtil.toDateString(customer.getBirthDate()));
      bean.setSex(Long.toString(customer.getSex()));
      bean.setRequestMailType(Long.toString(customer.getRequestMailType()));
      bean.setUpdatedDatetimeCustomer(customer.getUpdatedDatetime());
      //20111225 os013 add start
      bean.setCustomerKbn(customer.getCustomerKbn());
      //20111225 os013 add end
      //20120510 tuxinwei add start
      bean.setLanguageCode(customer.getLanguageCode());
      //20120510 tuxinwei add end
      
//      // アドレス帳情報
//      bean.setPostalCode(address.getPostalCode());
//      bean.setPrefectureCode(address.getPrefectureCode());
//      bean.setAddress1(address.getAddress1());
//      bean.setAddress2(address.getAddress2());
//      bean.setAddress3(address.getAddress3());
//      bean.setAddress4(address.getAddress4());
//      //modify by V10-CH 170 start
//      if(StringUtil.hasValue(address.getPhoneNumber())){
//        String [] phoneNo = address.getPhoneNumber().split("-");
//      if (phoneNo.length == 2) {
//        bean.setPhoneNumber1(phoneNo[0]);
//        bean.setPhoneNumber2(phoneNo[1]);
//      }else if (phoneNo.length == 3){
//        bean.setPhoneNumber1(phoneNo[0]);
//        bean.setPhoneNumber2(phoneNo[1]);
//        bean.setPhoneNumber3(phoneNo[2]);
//      }
//    }else{
//      address.setPhoneNumber("");
//      bean.setPhoneNumber1("");
//      bean.setPhoneNumber2("");
//      bean.setPhoneNumber3("");
//    }
      
      //Add by V10-CH start
//      bean.setMobileNumber(address.getMobileNumber());
      //Add by V10-CH end
      
//      if (StringUtil.hasValue(address.getPhoneNumber())) {
//        bean.setPhoneNumber1(address.getPhoneNumber());
//      }
      //modify by V10-CH 170 end

//      bean.setUpdatedDatetimeAddress(address.getUpdatedDatetime());
      bean.setDisplayMode("update");

      //  modify by V10-CH 170 start
//      bean.setCityCode(address.getCityCode());
      // modify by V10-CH 170 end
      // tmpBeanに情報を保持
      setEdit1ToTemp(bean, customer);

      // 2013/04/01 优惠券对应 ob add start
      if (getLoginInfo().isMobileCustomerFlg()) {
        bean.setMobile(getLoginInfo().getMobileNumber());
        bean.setValidateMobileFlg(Boolean.TRUE);
      } else if (StringUtil.hasValueAllOf(customer.getAuthCode(), customer.getMobileNumber())) {
        bean.setMobile(customer.getMobileNumber());
        bean.setValidateMobileFlg(Boolean.TRUE);
      }
      // 2013/04/01 优惠券对应 ob add end
    } else {
      //20111225 os013 add start
      if(customerKbn.equals("1")){
    	if(move.equals("cart")){
    		bean.setMoveFlg(true);
    	}
        //支付宝名
        bean.setLastName(info.getCustomer().getLastName());
        //支付宝用户编号
        bean.setTmallUserId(info.getCustomer().getTmallUserId());
        //会员区分
        bean.setCustomerKbn(info.getCustomer().getCustomerKbn());
        //更新日時
        bean.setUpdatedDatetimeCustomer(info.getCustomer().getUpdatedDatetime());
      }else{
        //会员区分
        bean.setCustomerKbn(0L);
      }
      //20111225 os013 add end
      bean.setDisplayMode("register");
      bean.setCustomerCode(customerCode);
      //20111212 lirong 修正 ここから
      //性别默认为女
      bean.setSex(Sex.FEMALE.getValue());
     // bean.setSex(Sex.MALE.getValue());
      // 10.1.4 K00171 修正 ここから
      //情報メール默认为希望する
       bean.setRequestMailType(RequestMailType.WANTED.getValue());
       //bean.setRequestMailType(RequestMailType.UNWANTED.getValue());
      // 10.1.4 K00171 修正 ここまで
     //20111212 lirong 修正 ここまで
       // 前画面情報設定
       DisplayTransition.add(getBean(), "/app/common/index", getSessionContainer());
    }

    // modify by V10-CH 170 start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    // modify by V10-CH 170 end
    // 顧客属性が存在するかチェック
    List<CustomerAttribute> getAttributeList = service.getAttributeList();
    if (getAttributeList.size() > 0) {
      bean.setHasCustomerAttributeFlg(true);
      
    } else {
      bean.setHasCustomerAttributeFlg(false);
    }

    setRequestBean(bean);

    // 次ページ指定
    confirmBean.setNextPage("init");

    // 入力内容を保持
    getSessionContainer().setTempBean(confirmBean);

    return FrontActionResult.RESULT_SUCCESS;
  }
  private String getPathInfo(int index) {
	    String[] tmp = getRequestParameter().getPathArgs();
	    if (tmp.length > index) {
	      return tmp[index];
	    }
	    return "";
  }
}
