package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityListUpdateAction extends WebBackAction<CommodityListBean> {

  private static final String UPDATE_MODE_START = "start";

  private static final String UPDATE_MODE_STOP = "stop";

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
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // URLパラメータのチェック
    String updateMode = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      updateMode = getRequestParameter().getPathArgs()[0];
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }

    boolean isValid = updateMode.equals(UPDATE_MODE_START) || updateMode.equals(UPDATE_MODE_STOP);
    if (!isValid) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }

    if (getBean().getCheckedCommodityList().size() < 1
        || !StringUtil.hasValueAllOf(ArrayUtil.toArray(getBean().getCheckedCommodityList(), String.class))) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.catalog.CommodityListUpdateAction.0")));
      return false;
    }

    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String updateMode = getRequestParameter().getPathArgs()[0];

    // 更新処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result;

    if (getConfig().isOne()) {
      getBean().setSearchShopCode(getLoginInfo().getShopCode());
    }
    if (updateMode.equals(UPDATE_MODE_START)) {
      result = service
          .updateCommoditySaleType(getBean().getSearchShopCode(), getBean().getCheckedCommodityList(), SaleFlg.FOR_SALE);
    } else {
      result = service.updateCommoditySaleType(getBean().getSearchShopCode(), getBean().getCheckedCommodityList(),
          SaleFlg.DISCONTINUED);
    }

    if (result.hasError()) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.UPDATE_FAILED,
          Messages.getString("web.action.back.catalog.CommodityListUpdateAction.1")));
    }

    setRequestBean(getBean());
    setNextUrl("/app/catalog/commodity_list/search/update");

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityListUpdateAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104011007";
  }

}
