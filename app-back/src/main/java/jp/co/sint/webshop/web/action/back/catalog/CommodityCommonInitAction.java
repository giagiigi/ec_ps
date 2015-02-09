package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean.CommodityCommonDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1040810:商品詳細レイアウトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCommonInitAction extends CommodityCommonBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
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

    Logger logger = Logger.getLogger(this.getClass());

    CommodityCommonBean reqBean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    reqBean.setSearchShopCode(getLoginInfo().getShopCode());

    List<CommodityLayout> layoutList = service.getCommodityLayoutList(reqBean.getSearchShopCode());

    // 表示領域用
    List<CommodityCommonDetailBean> displayList = new ArrayList<CommodityCommonDetailBean>();

    // 非表示領域用
    List<CommodityCommonDetailBean> unDisplayList = new ArrayList<CommodityCommonDetailBean>();

    setCommodityLayout(reqBean, layoutList, displayList, unDisplayList);

    // デフォルトの商品レイアウトデータを取得
    List<CommodityLayout> defaultLayoutList = service.getCommodityLayoutList(DIContainer.getWebshopConfig().getSiteShopCode());

    // 取得した商品レイアウトのデータの整合性を確認
    if (isNotExistPartsCode(displayList, unDisplayList, defaultLayoutList)) {
      if (getLoginInfo().isShop()) {
        logger.error(Messages.log("web.action.back.catalog.CommodityCommonInitAction.0"));
        addErrorMessage(WebMessage.get(CatalogErrorMessage.INIT_DATA_ERROR));
      }
    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    setDisplayControl();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCommonInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104081002";
  }

}
