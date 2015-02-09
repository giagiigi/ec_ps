package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityCompositionBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCommodityGiftBean.GiftCommodityDetailBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020110:新規受注(商品選択)のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCommodityClearCartAction extends NeworderCommodityBaseAction {

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
    // 2012/11/22 促销对应 新建订单_商品选择  ob add start
  	String actionFlg = "";
  	String[] tmp = getRequestParameter().getPathArgs();
  	if (tmp.length > 0) {
  		actionFlg = tmp[0];
  	}
  	//套餐信息不显示
  	if (StringUtil.hasValue(actionFlg)
  			&& WebFrameworkConstants.CLEAR_COMMODITY_COMPOSITION.equals(actionFlg)) {
  		getBean().setCommodityCompositionBean(new NeworderCommodityCompositionBean());
  		getBean().setCommodityComposition(false);
  		setRequestBean(getBean());
  		return BackActionResult.RESULT_SUCCESS;
  	}
    // 2012/11/22 促销对应 新建订单_商品选择  ob add end
    getCart().clear();

    getBean().setCartCommodityList(createBeanFromCart());
    // 2012/11/22 促销对应 新建订单_商品选择  ob add start
    getBean().getGiftCommodityBean().setGiftCommodityBeanList(new ArrayList<GiftCommodityDetailBean>());
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
    return Messages.getString("web.action.back.order.NeworderCommodityClearCartAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102011003";
  }

}
