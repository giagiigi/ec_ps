package jp.co.sint.webshop.web.action.front.cart;

// import jp.co.sint.webshop.service.ServiceLocator; // 10.1.6 10268 削除
// import jp.co.sint.webshop.service.UtilService; // 10.1.6 10268 削除
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.cart.BlanketCartBean;

/**
 * U2020310:まとめてカートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BlanketCartInitAction extends BlanketCartBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    BlanketCartBean bean = new BlanketCartBean();
    // UtilService service = ServiceLocator.getUtilService(getLoginInfo()); // 10.1.6 10268 削除
    // bean.setShopList(service.getShopNames(true, false, true)); // 10.1.6 10268 削除
    bean.setFirstDisplay(true);

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

}
