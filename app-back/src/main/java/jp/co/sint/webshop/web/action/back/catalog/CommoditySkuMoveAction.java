package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

import org.apache.log4j.Logger;

/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommoditySkuMoveAction extends WebBackAction<CommoditySkuBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = true;

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 1) {

      String shopCode = getRequestParameter().getPathArgs()[1];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }
      authorization &= shopCode.equals(getLoginInfo().getShopCode());
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length >= 1) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());

    // URLパラメータの取得
    // URL形式: ～/catalog/commodity_list/move/【SKUコード】
    String shopCode = getLoginInfo().getShopCode();
    String skuCode = getRequestParameter().getPathArgs()[0];

    // URLパラメータに従い、遷移先を制御
    String nextUrl = "/app/catalog/stock_io/init/" + skuCode;

    // 商品存在チェック
    CatalogService catalogSv = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = catalogSv.getSkuInfo(shopCode, skuCode);
    if (commodity == null || commodity.getHeader() == null || commodity.getDetail() == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.CommoditySkuMoveAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    // 遷移元情報をセッションに保存
    DisplayTransition.add(getBean(),
        "/app/catalog/commodity_sku/init/" + shopCode + "/" 
        + commodity.getHeader().getCommodityCode(), getSessionContainer());

    setNextUrl(nextUrl);
    logger.debug("nextUrl: " + nextUrl);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014003";
  }

}
