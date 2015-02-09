package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CouponIssueListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponIssueListMoveAction extends WebBackAction<CouponIssueListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    OperatingMode mode = getConfig().getOperatingMode();
    if (mode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (mode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (mode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (mode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= shopCode.equals(getLoginInfo().getShopCode());

    }

    return authorization;
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
    String shopCode = "";
    String coupouIssueNo = "";
    if (getRequestParameter().getPathArgs().length > 2) {
      shopCode = getRequestParameter().getPathArgs()[1];
      coupouIssueNo = getRequestParameter().getPathArgs()[2];

      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      CouponIssue couponIssue = service.getCouponIssue(shopCode, NumUtil.toLong(coupouIssueNo));

      if (couponIssue == null) {
        setNextUrl(null);
        setRequestBean(getBean());
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.PaymentmethodListMoveEditAction.0")));
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    String displayMode = "";

    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      displayMode = StringUtil.coalesce(parameter[0], "");
    }
    
    DisplayTransition.add(getBean(), "/app/shop/coupon_issue_list", getSessionContainer());
    String addition = "";
    if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(coupouIssueNo)) {
      addition = shopCode + "/" + coupouIssueNo;
    }
    setNextUrl(addition, displayMode);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 次画面のURLをセットします。
   * 
   * @param String
   *          displayMode
   * @param String
   *          customerCode
   */
  private void setNextUrl(String addtion, String displayMode) {
    if (displayMode.equals("research")) {
      setNextUrl("/app/shop/coupon_research/init/" + addtion);
    } else {
      setNextUrl("/app/shop/coupon_issue_detail/init/" + addtion);
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodListMoveEditAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105051004";
  }

}
