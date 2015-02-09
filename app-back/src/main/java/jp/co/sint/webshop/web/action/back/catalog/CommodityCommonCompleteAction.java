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
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040810:商品詳細レイアウトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityCommonCompleteAction extends CommodityCommonBaseAction {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityCommonBean reqBean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    List<CommodityLayout> layoutList = service.getCommodityLayoutList(reqBean.getSearchShopCode());

    // 表示領域用
    List<CommodityCommonDetailBean> displayList = new ArrayList<CommodityCommonDetailBean>();

    // 非表示領域用
    List<CommodityCommonDetailBean> unDisplayList = new ArrayList<CommodityCommonDetailBean>();

    setCommodityLayout(reqBean, layoutList, displayList, unDisplayList);

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    setDisplayControl();

    // 完了情報の制御
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 1) {
      completeParam = param[0];
    }

    if (completeParam.equals("update")) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
          Messages.getString("web.action.back.catalog.CommodityCommonCompleteAction.0")));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityCommonCompleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104081001";
  }

}
