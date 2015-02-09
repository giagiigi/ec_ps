package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;

/**
 * U2040510:商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailCompleteAction extends DetailBaseAction {

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

    CommodityDetailBean reqBean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      reqBean.setShopCode("00000000");
      reqBean.setCommodityCode(urlParam[0]);
    }

    reqBean.setPreview(false);
    createCommodityInfo(reqBean);

    setRequestBean(reqBean);
    setNextUrl(null);

    return FrontActionResult.RESULT_SUCCESS;
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

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    String[] params = getRequestParameter().getPathArgs();
    if (params.length >= 2) {
      setDisplayMessage(params[1]);
    }
  }
}
