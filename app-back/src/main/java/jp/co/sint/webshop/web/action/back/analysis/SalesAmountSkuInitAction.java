package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountSkuBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1070830:商品別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountSkuInitAction extends WebBackAction<SalesAmountSkuBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    if (Permission.ANALYSIS_READ_SITE.isGranted(login) || Permission.ANALYSIS_READ_SHOP.isGranted(login)) {
      return true;
    }
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SalesAmountSkuBean requestBean = new SalesAmountSkuBean();

    BackLoginInfo login = getLoginInfo();
    UtilService utilService = ServiceLocator.getUtilService(login);

    requestBean.setShopNameList(utilService.getShopNamesDefaultAllShop(false, false));
    if (login.isShop() || getConfig().isOne()) {
      requestBean.setShopCodeCondition(login.getShopCode());
      requestBean.setShopCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    } else {
      requestBean.setShopCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
    }

    String yesterday = DateUtil.toDateString(DateUtil.addDate(DateUtil.getSysdate(), -1));

    requestBean.setSearchStartDate(yesterday);
    requestBean.setSearchEndDate(yesterday);

    setRequestBean(requestBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    SalesAmountSkuBean requestBean = (SalesAmountSkuBean) getRequestBean();

    if (Permission.ANALYSIS_DATA_SITE.isGranted(login) || Permission.ANALYSIS_DATA_SHOP.isGranted(login)) {
      requestBean.setExportAuthority(true);
    }

    setRequestBean(requestBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountSkuInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107083002";
  }

}
