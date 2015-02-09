package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.NewGiftCardBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewGiftCardPasswordRegisterAction extends NewGiftCardBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    NewGiftCardBean bean = getBean();
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    FrontLoginInfo login = getLoginInfo();
    
    String customerCode = login.getCustomerCode();
    Customer customer = dao.load(customerCode);
    String oldPassword = bean.getOldPassword();
    String password = bean.getPassword();
    String passwordRepeat = bean.getPasswordRepeat();
    if (StringUtil.isNullOrEmpty(password)) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.1"));
      return false;
    }
    if (StringUtil.isNullOrEmpty(passwordRepeat)) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.2"));
      return false;
    }
    if (!password.equals(passwordRepeat)) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.3"));
      return false;
    }
    
    PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
    if (!policy.isValidPassword(password)) {
      addErrorMessage(WebMessage.get(MypageErrorMessage.PASSWORD_POLICY_ERROR));
      return false;
    }
    if (customer.getPassword().equals(PasswordUtil.getDigest(password))) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.19"));
      return false;
    }
    
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length > 0) {
      if (StringUtil.isNullOrEmpty(oldPassword)) {
        addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.4"));
        return false;
      }
      if (!customer.getPaymentPassword().equals(PasswordUtil.getDigest(oldPassword))) {
        addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.5"));
        return false;
      }
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
    
    Customer customer = dao.load(customerCode);
    
    customer.setPaymentPassword(PasswordUtil.getDigest(bean.getPassword()));
    dao.update(customer);
    
    bean.setOldPassword(bean.getPassword());
    bean.setUpdatePasswordFlg(true);
    
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length > 0) {
      addInformationMessage(Messages.getString("web.action.front.order.GiftCardRule.6"));
    } else {
      addInformationMessage(Messages.getString("web.action.front.order.GiftCardRule.7"));
    }
    
    bean = initNewGiftCardBean(bean,customer);
    bean.setTabIndex("2");
    
    bean.setDisFlg(false);
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }
  


}
