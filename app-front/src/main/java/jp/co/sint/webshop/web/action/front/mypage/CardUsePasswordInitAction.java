package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerCardInfo;
import jp.co.sint.webshop.data.dto.CustomerCardUseInfo;
import jp.co.sint.webshop.data.dto.GiftCardReturnConfirm;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.CardUsePasswordBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CardUsePasswordInitAction extends WebFrontAction<CardUsePasswordBean> {

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
    getRequestParameter().get("messId");
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("messId"))) {
      addInformationMessage(Messages.getString("web.action.front.order.GiftCardRule.10"));
    }

    CardUsePasswordBean bean = new CardUsePasswordBean();
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();
    CustomerService service = ServiceLocator.getCustomerService(login);
    CustomerCardInfo cardInfo = service.getCustomerCardInfoByCustomerCode(customerCode);
    CustomerCardUseInfo cardUseInfo = service.getCustomerCardUseInfoBycustomerCode(customerCode);
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    CustomerCardInfo cardInfoUnable = service.getCustomerCardInfoByCustomerCodeUnable(customerCode);
    GiftCardReturnConfirm confirmPrice = service.getGiftCardReturnConfirm(customerCode);
    
    Customer customer = dao.load(customerCode);
    if (StringUtil.hasValue(customer.getPaymentPassword())) {
      bean.setUpdatePasswordFlg(true);
    } else {
      bean.setUpdatePasswordFlg(false);
    }
    bean.setTotalDenomination(cardInfo.getDenomination().toString());
    bean.setAvalibleDenomination(cardInfo.getDenomination().add(confirmPrice.getReturnAmount()).subtract(cardUseInfo.getUseAmount()).subtract(cardInfoUnable.getDenomination()).toString());
    bean.setDisFlg(true);

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

 
  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * キャンセル完了表示
   */
  @Override
  public void prerender() {
  }

}
