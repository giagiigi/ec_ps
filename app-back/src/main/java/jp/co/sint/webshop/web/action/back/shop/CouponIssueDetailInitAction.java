package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CouponIssueDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050520:支払方法詳細のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class CouponIssueDetailInitAction extends WebBackAction<CouponIssueDetailBean> {

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

    if (operatingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
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
    boolean validation = true;
    return validation;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CouponIssueDetailBean bean = new CouponIssueDetailBean();
    
    // 処理完了メッセージ存在時
    if (getRequestParameter().getPathArgs().length > 2) {
      if (getRequestParameter().getPathArgs()[2].equals("new_register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
            .getString("web.action.back.shop.CouponIssueDetailInitAction.0")));
      } else if (getRequestParameter().getPathArgs()[2].equals("update_register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.shop.CouponIssueDetailInitAction.0")));
      }
    }

    // URLパラメータに支払方法コードがあるかどうかで登録か更新か判断する。

    // 配列の[0]にはショップコード、[1]には支払方法コードが入る
    if (getRequestParameter().getPathArgs().length > 1) {
      String shopCode = getRequestParameter().getPathArgs()[0];
      String couponIssueNo = getRequestParameter().getPathArgs()[1];

      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      CouponIssue couponIssue = service.getCouponIssue(shopCode, NumUtil.toLong(couponIssueNo));

      //bean.setUpdateModeFlg(true);
      bean.setCouponIssueNo(String.valueOf(couponIssue.getCouponIssueNo()));
      bean.setCouponName(couponIssue.getCouponName());
      bean.setCouponPrice(NumUtil.toString(couponIssue.getCouponPrice()));
      bean.setGetCouponPrice(NumUtil.toString(couponIssue.getGetCouponPrice()));
      bean.setBonusCouponStartDate(DateUtil.toDateTimeString(couponIssue.getBonusCouponStartDate()));
      bean.setBonusCouponEndDate(DateUtil.toDateTimeString(couponIssue.getBonusCouponEndDate()));
      bean.setUseCouponStartDate(DateUtil.toDateTimeString(couponIssue.getUseCouponStartDate()));
      bean.setUseCouponEndDate(DateUtil.toDateTimeString(couponIssue.getUseCouponEndDate()));
      bean.setUpdateDate(couponIssue.getUpdatedDatetime());
      bean.setUpdateModeFlg(true);

    } else {
      bean.setUpdateModeFlg(false);
      bean.setCouponIssueNo("");
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。

   */
  @Override
  public void prerender() {

    CouponIssueDetailBean bean = (CouponIssueDetailBean) getRequestBean();

    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      bean.setUpdateFlg(true);
    } else {
      bean.setUpdateFlg(false);
    }

    // 支払方法リスト表示・非表示制御

    if (!bean.getUpdateModeFlg()) {
      bean.setRegisterModeDisplay(WebConstantCode.DISPLAY_EDIT);
      bean.setBankMoveButtonFlg(false);
    } else if (bean.getUpdateModeFlg()) {
      bean.setRegisterModeDisplay(WebConstantCode.DISPLAY_HIDDEN);
      bean.setBankMoveButtonFlg(true);
    }

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CouponIssueDetailInitAction.1");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105052001";
  }

}
