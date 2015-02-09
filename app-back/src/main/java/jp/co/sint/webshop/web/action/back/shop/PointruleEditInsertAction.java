package jp.co.sint.webshop.web.action.back.shop;

import java.util.Date;

import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PointruleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1050310:ポイントルールのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointruleEditInsertAction extends WebBackAction<PointruleEditBean> {

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
    Date startDate = DateUtil.fromString(getBean().getBonusPointStart());
    Date endDate = DateUtil.fromString(getBean().getBonusPointEnd());

    if (startDate != null && endDate != null) {
      if (DateUtil.fromString(getBean().getBonusPointStart()).after(DateUtil.fromString(getBean().getBonusPointEnd()))) {
        validation = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM,
            Messages.getString("web.action.back.shop.PointruleEditInsertAction.0")));
      }
    }

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
    PointruleEditBean bean = getBean();

    PointRule rule = service.getPointRule();

    rule.setPointFunctionEnabledFlg(Long.valueOf(bean.getEnableFlg()));
    rule.setPointPeriod(Long.valueOf(bean.getPointPeriod()));
    rule.setPointRate(Long.valueOf(bean.getPointRate()));
    rule.setPointInvestPurchasePrice(NumUtil.parse(bean.getPunchasePrice()));
    rule.setBonusPointTermRate(Long.valueOf(bean.getBonusPointRate()));
    rule.setBonusPointStartDate(DateUtil.fromString(bean.getBonusPointStart()));
    rule.setBonusPointEndDate(DateUtil.fromString(bean.getBonusPointEnd()));
    if (StringUtil.hasValue(bean.getBonusPointDate())) {
      rule.setBonusPointDate(Long.valueOf(bean.getBonusPointDate()));
    } else {
      rule.setBonusPointDate(0L);
    }
    rule.setCustomerRegisterPoint(NumUtil.parse(bean.getCustomerRegisterPoint()));
    rule.setFirstPurchaseInvestPoint(NumUtil.parse(bean.getFirstPoint()));
    rule.setReviewContributedPoint(NumUtil.parse(bean.getReviewPoint()));
    rule.setUpdatedDatetime(bean.getUpdatedDatetime());
    // add by zhanghaibin start 2010-05-18
    //rule.setSmallUsePoint(NumUtil.parse(bean.getSmallUsePoint()));
    //rule.setRmbPointRate(NumUtil.parse(bean.getRmbPointRate()));
    //rule.setAmplificationRate(NumUtil.parse(bean.getAmplificationRate()));
    // add by zhanghaibin end 2010-05-18
    ServiceResult result = service.updatePointRule(rule);

    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn(result.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/shop/pointrule_edit/init/insert");
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PointruleEditInsertAction.1");
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
