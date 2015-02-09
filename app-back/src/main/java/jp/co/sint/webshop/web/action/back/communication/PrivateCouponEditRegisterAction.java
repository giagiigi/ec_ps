package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060620:PRIVATEクーポンマスタのデータモデルです。
 * 
 * @author OB.
 */
public class PrivateCouponEditRegisterAction extends PrivateCouponEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo())
        && Permission.PRIVATE_COUPON_UPDATE_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    
    if (getBean().getCampaignType().equals(CouponIssueType.FIXED.getValue())) {
      if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(getBean().getMinUseOrderAmount()), new BigDecimal(getBean().getCouponAmount()) )) {
        addErrorMessage("利用最小购买金额必须大于优惠金额！");
        setRequestBean(getBean());
        setBean(getBean());
        return false;
      }
    }
    return super.validate();
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PrivateCouponEditBean bean = getBean();
    if (StringUtil.isNullOrEmpty(bean.getMaxUseOrderAmount())) {
      bean.setMaxUseOrderAmount("99999999");
    }
    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());

    // 判断优惠券规则编号是否已经存在
    NewCouponRule rule = svc.getPrivateCoupon(bean.getCouponCode());
    if (rule != null) {

      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "优惠券规则编号"));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    NewCouponRule newCouponRule = createNewCouponRuleBean(null);

    // 判断是否存在已经登录过的发行期间
    // 特别会员多条数据对应
    if (bean.getCouponTypeFlag() == 0 && svc.checkNewCouponRuleDuplicatedRegister(newCouponRule)) {
      // if (svc.checkNewCouponRuleDuplicatedRegister(newCouponRule)) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "发行期间"));
    } else {
      // 登录顾客别优惠券发行规则
      ServiceResult serviceResult = svc.insertNewCouponRule(newCouponRule);

      // 登录失败
      if (serviceResult.hasError()) {
        for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          }
        }
        return BackActionResult.SERVICE_ERROR;
      }

      setNextUrl("/app/communication/private_coupon_edit/init/edit/" + bean.getCouponCode() + "/"
          + WebConstantCode.COMPLETE_REGISTER);
    }

    if (StringUtil.hasValue(bean.getMaxUseOrderAmount()) && bean.getMaxUseOrderAmount().equals("99999999.00") && bean.getMaxUseOrderAmount().equals("99999999")) {
      bean.setMaxUseOrderAmount("");
    }
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客组别优惠券发行规则信息新建登录";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106062003";
  }

}
