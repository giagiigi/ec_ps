package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.GiftCardRuleDao;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.NewGiftCardBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewGiftCardActiveAction extends NewGiftCardBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if(StringUtil.isNullOrEmpty(getBean().getGiftCardPasswd())){
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
    NewGiftCardBean bean = getBean();
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    GiftCardRuleDao ruleDao = DIContainer.getDao(GiftCardRuleDao.class);
    int errorTimes = DIContainer.getWebshopConfig().getErrorTimes();
    
    Customer customer = dao.load(customerCode);
    CustomerService service = ServiceLocator.getCustomerService(login);
    GiftCardIssueDetail cardDetail = service.getGiftCardIssueDetailByPassWord(bean.getGiftCardPasswd());
    if (cardDetail == null ) {
      customer.setErrorTimes(customer.getErrorTimes() + 1);
      if (customer.getErrorTimes() >= errorTimes || customer.getLockFlg()==1L) {
        bean.setErrorTimesFlg(true);
        customer.setLockFlg(1L);
        addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.8"));
      } else {
        addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.9"));
      }
      dao.update(customer);

      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    // 0 未激活    1 激活
    if (cardDetail.getCardStatus().equals(1L) ){
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.9"));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    // 0 未取消   1 取消
    if (cardDetail.getCancelFlg().equals(1L) ){
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.9"));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    
    GiftCardRule rule = ruleDao.load(cardDetail.getCardCode());
    if (rule == null ) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.9"));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    ServiceResult result = service.giftCardActivateProcess(customer,cardDetail,rule);

    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR) {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
    }
    
    bean = initNewGiftCardBean(bean,customer);
    
    addInformationMessage(Messages.getString("web.action.front.order.GiftCardRule.10"));
    
//    if(StringUtil.isNullOrEmpty(customer.getPaymentPassword())){
//      setNextUrl("/app/mypage/card_use_password/init?messId=1");
//    }
    
    bean.setTabIndex("0");
    
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }
 
  /**
   * キャンセル完了表示
   */
  @Override
  public void prerender() {
  }

}
