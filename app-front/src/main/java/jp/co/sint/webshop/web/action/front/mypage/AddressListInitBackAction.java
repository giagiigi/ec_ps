package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030310:アドレス帳一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AddressListInitBackAction extends AddressListInitAction {

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  protected CustomerSearchCondition getCondition() {
    return PagerUtil.createSearchConditionFromBean(getBean(), getSearchCondition());
  }
}
