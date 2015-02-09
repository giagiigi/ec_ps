package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CouponRuleBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050410:ポイントルールのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponRuleInitAction extends WebBackAction<CouponRuleBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
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
    CouponRuleBean reqBean = new CouponRuleBean();

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    CouponRule couponRule = service.getCouponRule();

    reqBean.setEnableFlg(Long.toString(couponRule.getCouponFunctionEnabledFlg()));
    reqBean.setPunchasePrice(NumUtil.toString(couponRule.getCouponInvestPurchasePrice()));
    reqBean.setUsebleAmount(NumUtil.toString(couponRule.getCouponAmount()));
    reqBean.setUpdatedDatetime(couponRule.getUpdatedDatetime());
    // add by zhanghaibin start 2010-05-18
    //reqBean.setSmallUseCoupon(NumUtil.toString(couponRule.getSmallUseCoupon()));
    //reqBean.setRmbCouponRate(NumUtil.toString(couponRule.getRmbCouponRate()));
    //reqBean.setAmplificationRate(NumUtil.toString(couponRule.getAmplificationRate()));
    // add by zhanghaibin end 2010-05-18
    
    setRequestBean(reqBean);

    String parameter = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }

    if (parameter.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.CouponruleEditInitAction.0")));
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CouponRuleBean requestBean = (CouponRuleBean) getRequestBean();
    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      requestBean.setDisplay(WebConstantCode.DISPLAY_EDIT);
      requestBean.setRegisterButtonFlg(true);
    } else {
      requestBean.setDisplay(WebConstantCode.DISPLAY_HIDDEN);
      requestBean.setRegisterButtonFlg(false);
    }
    setRequestBean(requestBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CouponruleEditInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105031001";
  }

}
