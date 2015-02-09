package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CouponStatusBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;


public class CouponStatusInitAction extends WebBackAction<CouponStatusBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_COUPON_READ.isGranted(login);
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
    CouponStatusBean bean = new CouponStatusBean();
    bean.setCsvExportButtonDisplayFlg(Permission.CUSTOMER_COUPON_IO.isGranted(login));

    //bean.setCsvExportButtonDisplayFlg(Permission.CUSTOMER_Coupon_IO.isGranted(login));

//    bean.setSearchSummaryCondition(CouponStatusBean.SUMMARY_TYPE_ALL);
//    bean.setDisplayTarget("all");

    // ショップのリスト取得
//    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
//    bean.setSearchShopList(service.getShopNamesDefaultAllShop(false, false));
//    bean.setSearchShopCode(getLoginInfo().getShopCode());

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.CouponStatusInitAction.0");
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
