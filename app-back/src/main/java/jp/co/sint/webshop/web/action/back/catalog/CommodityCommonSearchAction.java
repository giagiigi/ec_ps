package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityCommonBean.CommodityCommonDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040810:商品詳細レイアウトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCommonSearchAction extends CommodityCommonBaseAction {

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
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

	// 請求bean作成
    CommodityCommonBean reqBean = getBean();
    
    //カタログサービスを取得するオブジェクトを作成	
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // ショップかの判定
    if (getLoginInfo().isShop()) {
    
      // ショップコードを設定する
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 商品レイアウトの一覧リスト内容を取得する
    List<CommodityLayout> layoutList = service.getCommodityLayoutList(reqBean.getSearchShopCode());

    // 商品詳細レイアウトのサブモデル
    // 表示領域用
    List<CommodityCommonDetailBean> displayList = new ArrayList<CommodityCommonDetailBean>();

    // 非表示領域用
    List<CommodityCommonDetailBean> unDisplayList = new ArrayList<CommodityCommonDetailBean>();

    setCommodityLayout(reqBean, layoutList, displayList, unDisplayList);

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
    return Messages.getString("web.action.back.catalog.CommodityCommonSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104081005";
  }

}
