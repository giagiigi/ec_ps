package jp.co.sint.webshop.web.action.back.order;

import java.util.List;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020110:新規受注(商品選択)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityInitAction extends NeworderCommodityBaseAction {

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
    NeworderCommodityBean bean = new NeworderCommodityBean();
    if (isRenew()) {
      getCart().clear();
    }

    bean.setSearchCategoryList(createCategoryTree());
    // 10.1.2 10089 追加 ここから
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    List<CategoryInfo> categoryList = catalogSvc.getAllCategory();
    bean.setCategoryNodeInfoList(categoryList);
    // 10.1.2 10089 追加 ここまで

    // カートからカート情報を生成する
    bean.setCartCommodityList(createBeanFromCart());

    // 2012/11/22 促销对应 新建订单_商品选择  ob add start
    // 把购物车的可领取赠品显示到画面购物车list中
    bean.setCartOtherGiftCommodityList(createBeanFromCartOfGift());
    // 设置可领取商品List
    multipleGiftProcess(bean, false, true);
    // 2012/11/22 促销对应 新建订单_商品选择  ob add end

    // ショップのリスト取得
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    bean.setShopList(service.getShopNamesDefaultAllShop(false, false));
    bean.setSearchShopCode(getLoginInfo().getShopCode());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    return isRenew();
  }

  /**
   * 新規に遷移してきたかどうかの判定メソッド
   * 
   * @return 新規に遷移してきたらtrue
   */
  private boolean isRenew() {
    String[] tmp = getRequestParameter().getPathArgs();
    return (tmp.length <= 0 || !tmp[0].equals("back"));
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCommodityInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102011004";
  }

}
