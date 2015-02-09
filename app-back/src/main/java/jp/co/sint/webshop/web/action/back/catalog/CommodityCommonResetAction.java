package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1040810:商品詳細レイアウトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCommonResetAction extends CommodityCommonBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());

    CommodityCommonBean reqBean = getBean();

    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    ServiceResult result = service.resetCommodityLayout(reqBean.getSearchShopCode());
    if (result.hasError()) {
      logger.error(Messages.log("web.action.back.catalog.CommodityCommonResetAction.0"));
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/catalog/commodity_common/complete/update");

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCommonResetAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104081004";
  }

}
