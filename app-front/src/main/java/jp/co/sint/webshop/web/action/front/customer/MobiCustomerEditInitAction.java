package jp.co.sint.webshop.web.action.front.customer;

import java.util.List;

import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.customer.CustomerConfirmBean;
import jp.co.sint.webshop.web.bean.front.customer.MobiCustomerEditBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U2030210:お客様情報登録1のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MobiCustomerEditInitAction extends CustomerEditBaseAction<MobiCustomerEditBean> {

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
	
    MobiCustomerEditBean bean = new MobiCustomerEditBean();

    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();
    String customerKbn="";
    boolean update = false;
    CustomerInfo info=new CustomerInfo();
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    if (StringUtil.hasValue(customerCode)) {
      //customerCode不为空并且customerKbn等于1时，表示支付宝会员还未成为正式会员
      info = service.getCustomer(customerCode);
      customerKbn=""+info.getCustomer().getCustomerKbn();
      if(!customerKbn.equals("1")){
        update = true;
      }
    }

    // 更新処理の場合データ取得
    CustomerConfirmBean confirmBean = new CustomerConfirmBean();
    Customer customer=new Customer();
    if (update) {
      info = service.getCustomer(customerCode);
      customer = info.getCustomer();

      // 顧客/退会済み顧客、またはアドレス帳が存在しない場合、エラー画面へ遷移
      if (service.isNotFound(customerCode) || service.isInactive(customerCode) ) {
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
      bean.setCustomerKbn(customer.getCustomerKbn());
      bean.setLanguageCode(customer.getLanguageCode());
      bean.setDisplayMode("update");

      // tmpBeanに情報を保持
      setEdit1ToTemp(bean, customer);
    } else {
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
    	  String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    	  if(StringUtil.isNotNull(currentLanguageCode)){
    		  bean.setLanguageCode(currentLanguageCode);
    	  }
        bean.setCustomerKbn(0L);
      }
      bean.setDisplayMode("register");
      bean.setCustomerCode(customerCode);
      //性别默认为女
      bean.setSex(Sex.FEMALE.getValue());
      //情報メール默认为希望する
       bean.setRequestMailType(RequestMailType.WANTED.getValue());
       // 前画面情報設定
       DisplayTransition.add(getBean(), "/app/common/index", getSessionContainer());
    }

    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCityList(s.getCityNames(bean.getPrefectureCode()));
    bean.setAgree(LoginLockedFlg.LOCKED.getValue());
    bean.setRequestMailType(LoginLockedFlg.LOCKED.getValue());
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
