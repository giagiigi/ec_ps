package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1070810:売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountMoveAction extends SalesAmountSearchAction {

  @Override
  public boolean authorize() {
    boolean authorization;

    BackLoginInfo login = getLoginInfo();
    authorization = Permission.ANALYSIS_READ_SITE.isGranted(login) || Permission.ANALYSIS_READ_SHOP.isGranted(login);

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= login.getShopCode().equals(shopCode);
    }

    return authorization;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SalesAmountBean bean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();

    String shopCode = urlParam[0];

    // ショップ存在チェック
    if (StringUtil.isNullOrEmpty(shopCode)) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.analysis.SalesAmountMoveAction.0")));
      setRequestBean(bean);

      return BackActionResult.RESULT_SUCCESS;
    }
    ShopManagementService shopSv = ServiceLocator.getShopManagementService(getLoginInfo());
    Shop shop = shopSv.getShop(shopCode);

    if (shop == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.analysis.SalesAmountMoveAction.0")));
      setRequestBean(bean);

      return BackActionResult.RESULT_SUCCESS;
    }

    // 日付チェック
    boolean validate = validateItems(bean, "searchYear", "searchMonth");
    if (!validate) {
      setRequestBean(bean);

      return BackActionResult.RESULT_SUCCESS;
    }
    setNextUrl("/app/analysis/sales_amount_shop/init/" + shopCode + "/" + bean.getSearchYear() + "/" + bean.getSearchMonth());

    // 前画面情報設定
    DisplayTransition.add(bean, "/app/analysis/sales_amount/search_back", getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    setNextUrl("/app/analysis/sales_amount/init");
    return false;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
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
    return Messages.getString("web.action.back.analysis.SalesAmountMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107081003";
  }

}
