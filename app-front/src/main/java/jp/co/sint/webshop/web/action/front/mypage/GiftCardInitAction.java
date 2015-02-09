package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.GiftCardBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2030110:マイページのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftCardInitAction extends WebFrontAction<GiftCardBean> {

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
    GiftCardBean bean = new GiftCardBean();
    FrontLoginInfo login = getLoginInfo();
    String customerCode = login.getCustomerCode();
    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    int errorTimes = DIContainer.getWebshopConfig().getErrorTimes();
    
    Customer customer = dao.load(customerCode);
    if (customer.getErrorTimes() >= errorTimes || customer.getLockFlg()==1L) {
      bean.setErrorTimesFlg(true);
      customer.setLockFlg(1L);
      addErrorMessage(Messages.getString("web.action.front.order.GiftCardRule.8"));
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    
    if (getLoginInfo().isMobileCustomerFlg()) {
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    } else if (StringUtil.hasValueAllOf(customer.getAuthCode(), customer.getMobileNumber())) {
      setRequestBean(bean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    
    setNextUrl("/app/common/mobile_auth/init/giftCard");
    

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
