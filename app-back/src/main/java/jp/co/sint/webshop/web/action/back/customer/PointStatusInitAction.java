package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.PointStatusBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030310:ポイント利用状況のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointStatusInitAction extends WebBackAction<PointStatusBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_POINT_READ.isGranted(login);
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

    // ボタン制御
    PointStatusBean bean = new PointStatusBean();
    bean.setCsvExportButtonDisplayFlg(Permission.CUSTOMER_POINT_IO.isGranted(login));

    bean.setSearchSummaryCondition(PointStatusBean.SUMMARY_TYPE_ALL);
    bean.setDisplayTarget("all");

    // ショップのリスト取得
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    bean.setSearchShopList(service.getShopNamesDefaultAllShop(false, false));
    bean.setSearchShopCode(getLoginInfo().getShopCode());

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointStatusInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103031002";
  }

}
