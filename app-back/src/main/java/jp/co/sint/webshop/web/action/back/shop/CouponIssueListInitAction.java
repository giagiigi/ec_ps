package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.CouponIssueListBean;
import jp.co.sint.webshop.web.bean.back.shop.CouponIssueListBean.CouponIssueDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponIssueListInitAction extends CouponIssueListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    OperatingMode operatingMode = getConfig().getOperatingMode();

    if (operatingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
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
    // ログインユーザの支払方法を全件取得
    List<CouponIssueDetail> couponIssueList = getCouponIssueDetailList(getLoginInfo().getShopCode());

    CouponIssueListBean nextBean = new CouponIssueListBean();
    nextBean.setCouponIssueList(couponIssueList);
    setRequestBean(nextBean);

    if (getRequestParameter().getPathArgs().length > 0) {

      String information = "";
      if (getRequestParameter().getPathArgs()[0].equals(WebConstantCode.COMPLETE_DELETE)) {
        information = WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.shop.CouponIssueListInitAction.0"));
      }
      addInformationMessage(information);
    }
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CouponIssueListBean requestBean = (CouponIssueListBean) getRequestBean();

    boolean deleteButtonFlg = false;
    boolean registerFlg = false;
    boolean referFlg = false;

    if (Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      referFlg = true;
    }
    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo())) {
      deleteButtonFlg = true;
    }
    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      registerFlg = true;
    }

    requestBean.setDeleteButtonFlg(deleteButtonFlg);
    requestBean.setRegisterFlg(registerFlg);
    requestBean.setReferFlg(referFlg);
    setRequestBean(requestBean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CouponIssueListInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105051003";
  }

}
