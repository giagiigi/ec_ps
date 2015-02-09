package jp.co.sint.webshop.web.action.front.catalog;

import java.util.LinkedHashMap; // 10.1.4 10193 追加
import java.util.Map; // 10.1.4 10193 追加

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2040510:商品詳細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DetailAddArrivalGoodsAction extends DetailBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // 10.1.4 10193 修正 ここから
    // return true;
    Map<String, String> target = new LinkedHashMap<String, String>();
    target.put(Messages.getString("web.action.front.catalog.DetailAddArrivalGoodsAction.0"), getBean().getShopCode());
    target.put(Messages.getString("web.action.front.catalog.DetailAddArrivalGoodsAction.1"), getBean().getCommodityCode());
    target.put(Messages.getString("web.action.front.catalog.DetailAddArrivalGoodsAction.2"), getBean().getSkuCode());
    return checkRequiredValue(target);
    // 10.1.4 10193 修正 ここまで
  }

  /**
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityDetailBean reqBean = getBean();

    if (isNotSelectedSku(reqBean)) {
      return FrontActionResult.RESULT_SUCCESS;
    }

    if (existSku(reqBean)) {
      setNextUrl("/app/catalog/arrival_goods/init/" + getBean().getShopCode() + "/" + reqBean.getCommodityCode() + "/"
          + reqBean.getSkuCode());
      return FrontActionResult.RESULT_SUCCESS;
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

}
