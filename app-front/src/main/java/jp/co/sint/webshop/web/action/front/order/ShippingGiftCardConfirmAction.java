package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020120:お届け先設定のアクションクラスです
 * 
 * @author Kousen.
 */
public class ShippingGiftCardConfirmAction extends ShippingBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ShippingBean bean = getBean();
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    
    if (StringUtil.isNullOrEmpty(bean.getUseAmount())) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.11"));
      return false;
    }
    
    if (StringUtil.isNullOrEmpty(bean.getPaymentPassword())) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.12"));
      return false;
    }
    
    Customer customer = dao.load(customerCode);
    if (StringUtil.isNullOrEmpty(customer.getPaymentPassword())) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.13"));
      return false;
    }
    
    if (!customer.getPaymentPassword().equals(PasswordUtil.getDigest(bean.getPaymentPassword()))) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.13"));
      return false;
    }
    
    if (!NumUtil.isDecimal(bean.getUseAmount())){
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.14"));
      return false;
    }
    
    if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(bean.getUseAmount()), BigDecimal.ZERO)) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.15"));
      return false;
    }
    bean.getCashier().setGiftCardUseAmount(new BigDecimal("0"));
    if (BigDecimalUtil.isAbove(new BigDecimal(bean.getUseAmount()), new BigDecimal(bean.getTotalAmount()))){
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.16"));
      return false;
    }
    
    if (BigDecimalUtil.isAbove(new BigDecimal(bean.getUseAmount()), bean.getCashier().getPaymentTotalPrice().subtract(bean.getCashier().getOuterCardUseAmount()))) {
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.17"));
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
    ShippingBean bean = getBean();

    if (BigDecimalUtil.isAbove(new BigDecimal(bean.getUseAmount()), bean.getCashier().getPaymentTotalPrice().subtract(bean.getCashier().getOuterCardUseAmount()))) {
      bean.setUseAmountRight(bean.getCashier().getPaymentTotalPrice().subtract(bean.getCashier().getOuterCardUseAmount()).toString());
    } else {
      bean.setUseAmountRight(bean.getUseAmount());
    }

    bean.getCashier().setGiftCardUseAmount(new BigDecimal(bean.getUseAmountRight()));
    createOutCardPrice();
    addInformationMessage(Messages.getString("web.action.front.order.GiftCardRule.23"));
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

}
