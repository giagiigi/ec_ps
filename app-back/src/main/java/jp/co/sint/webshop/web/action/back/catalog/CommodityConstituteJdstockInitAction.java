package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.JdStockInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteJdstockBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * 京东库存分配初始表示处理
 * 
 * @author System Integrator Corp.
 */
public class CommodityConstituteJdstockInitAction extends CommodityConstituteJdstockBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return super.authorize();
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    BackLoginInfo login = getLoginInfo();
    String commodityCode = getRequestParameter().getPathArgs()[0];
    CommodityConstituteJdstockBean bean = new CommodityConstituteJdstockBean();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    JdStockInfo tsi = service.getOrgJdStockInfo(commodityCode);

    List<JdStockInfo> list = service.getJdStockInfo(commodityCode);

    if (tsi == null || list.size() < 1) {
      setNextUrl("/app/catalog/commodity_constitute/init");
      return BackActionResult.RESULT_SUCCESS;
    }

    addDataToBean(bean, tsi, list);

    if (Permission.COMMODITY_CONSTITUTE_UPDATE.isGranted(login)) {
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setEditMode(WebConstantCode.DISPLAY_READONLY);
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "京东库存分配初期显示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104015007";
  }

}
