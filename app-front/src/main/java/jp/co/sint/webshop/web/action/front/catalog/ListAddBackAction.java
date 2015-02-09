package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.web.action.WebActionResult;

/**
 * U2040410:商品一覧のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ListAddBackAction extends ListInitAction {

  @Override
  public boolean validate() {
    return true;
  }
  
  public WebActionResult callService() {
    return super.callService();
  }
  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

}
