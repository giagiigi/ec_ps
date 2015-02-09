package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CouponRuleBean;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1050310:ポイントルールのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponRuleUpdateAction extends WebBackAction<CouponRuleBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean validation = true;
    validation = validateBean(getBean());

    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    CouponRuleBean bean = getBean();

    CouponRule rule = service.getCouponRule();

    rule.setCouponFunctionEnabledFlg(Long.valueOf(bean.getEnableFlg()));
    rule.setCouponAmount(NumUtil.toLong(bean.getUsebleAmount()));
    rule.setCouponInvestPurchasePrice(NumUtil.parse(bean.getPunchasePrice()));
    
    rule.setUpdatedDatetime(bean.getUpdatedDatetime());
    ServiceResult result = service.updateCouponRule(rule);

    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/coupon_rule/init/update");
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CouponRuleInsertAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105031002";
  }

}
