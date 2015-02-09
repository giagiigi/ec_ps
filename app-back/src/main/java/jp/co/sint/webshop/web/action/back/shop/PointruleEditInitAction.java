package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PointruleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050310:ポイントルールのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointruleEditInitAction extends WebBackAction<PointruleEditBean> {

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
    PointruleEditBean reqBean = new PointruleEditBean();

    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());

    PointRule pointRule = service.getPointRule();

    reqBean.setEnableFlg(Long.toString(pointRule.getPointFunctionEnabledFlg()));
    reqBean.setPointPeriod(Long.toString(pointRule.getPointPeriod()));
    reqBean.setPointRate(Long.toString(pointRule.getPointRate()));
    reqBean.setPunchasePrice(NumUtil.toString(pointRule.getPointInvestPurchasePrice()));
    reqBean.setBonusPointRate(Long.toString(pointRule.getBonusPointTermRate()));
    reqBean.setBonusPointStart(DateUtil.toDateString(pointRule.getBonusPointStartDate()));
    reqBean.setBonusPointEnd(DateUtil.toDateString(pointRule.getBonusPointEndDate()));
    String setBonusPointDate = "";
    if (pointRule.getBonusPointDate() != null) {
      setBonusPointDate = Long.toString(pointRule.getBonusPointDate());
    }
    reqBean.setBonusPointDate(setBonusPointDate);
    reqBean.setCustomerRegisterPoint(NumUtil.toString(pointRule.getCustomerRegisterPoint()));
    reqBean.setFirstPoint(NumUtil.toString(pointRule.getFirstPurchaseInvestPoint()));
    reqBean.setReviewPoint(NumUtil.toString(pointRule.getReviewContributedPoint()));
    reqBean.setUpdatedDatetime(pointRule.getUpdatedDatetime());
    // add by zhanghaibin start 2010-05-18
    //reqBean.setSmallUsePoint(NumUtil.toString(pointRule.getSmallUsePoint()));
    //reqBean.setRmbPointRate(NumUtil.toString(pointRule.getRmbPointRate()));
    //reqBean.setAmplificationRate(NumUtil.toString(pointRule.getAmplificationRate()));
    // add by zhanghaibin end 2010-05-18
    
    setRequestBean(reqBean);

    String parameter = "";
    if (getRequestParameter().getPathArgs().length > 0) {
      parameter = getRequestParameter().getPathArgs()[0];
    }

    if (parameter.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.PointruleEditInitAction.0")));
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    PointruleEditBean requestBean = (PointruleEditBean) getRequestBean();
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
    return Messages.getString("web.action.back.shop.PointruleEditInitAction.1");
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
