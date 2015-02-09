package jp.co.sint.webshop.web.action.back.order;


import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020110:新規受注(商品選択)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityCalculateAction extends NeworderCommodityBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    calculate();

    getBean().setCartCommodityList(createBeanFromCart());
    // 2012/11/22 促销对应 新建订单_商品选择  ob add start
    // 把购物车的可领取赠品显示到画面购物车list中
    getBean().setCartOtherGiftCommodityList(createBeanFromCartOfGift());
    // 设置可领取商品List
    multipleGiftProcess(getBean(), false, true);
    // 2012/11/22 促销对应 新建订单_商品选择  ob add end
    setRequestBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCommodityCalculateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102011002";
  }

}
