package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public class FriendCouponRuleEditUpdateAction extends FriendCouponRuleEditBaseAction {

  public boolean validate() {

    FriendCouponRuleEditBean bean = getBean();

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    if (StringUtil.hasValue(bean.getFriendCouponRuleNo()) && service.existFriendCouponIssueHistory(bean.getFriendCouponRuleNo())) {
      addErrorMessage("发行规则已在使用中，不能更新！");
      return false;
    }

    // 验证是否存在
    if (checktoDuplicate(bean.getFriendCouponRuleNo()) == false) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "公共优惠劵规则"));
      setRequestBean(getBean());
      setBean(getBean());
      return false;
    }

    if (bean.getCouponIssueType().equals(CouponIssueType.FIXED.getValue())) {
      if (BigDecimalUtil.isBelow(new BigDecimal(getBean().getMinUseOrderAmount()), new BigDecimal(getBean()
          .getCouponAmountNumeric()))) {
        addErrorMessage("利用最小购买金额必须大于优惠金额！");
        setRequestBean(getBean());
        setBean(getBean());
        return false;
      }
    }
    // 验证比率和金额
    boolean sus = true;
    // 验证比率和金额
    if (!checkCouponAmount(bean)) {
      sus = false;
    } 
    if (sus && validateBean(bean)) {
      if (bean.getIssueDateType().equals("0")) {
        if (!NumUtil.isNum(bean.getIssueDateNum())) {
          addErrorMessage("请输入月份值！");
          sus = false;
        }
      }else if (bean.getIssueDateType().equals("1")) {
        if (DateUtil.isCorrect(bean.getIssueStartDate())) {
          if (!DateUtil.isCorrect(bean.getIssueEndDate())) {
            addErrorMessage("请输入发行可能结束日期！");
            sus = false;
          }
        } else {
          addErrorMessage("请输入发行可能开始日期！");
          sus = false;
        }
        if (sus) {
          if (DateUtil.fromString(bean.getIssueStartDate(), true).after(DateUtil.fromString(bean.getIssueEndDate(), true))) {
            addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
                .getString("web.action.back.communication.FriendCouponRuleAction.2")));
            sus = false;
          }
        }
      }
    }else {
      sus = false;
    }
    return sus;

  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    FriendCouponRuleEditBean bean = getBean();

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据规则编号得到优惠规则对象
    FriendCouponRule couponRule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());

    if (!StringUtil.isNullOrEmpty(bean.getMaxUseOrderAmount())
        && Double.parseDouble(bean.getMaxUseOrderAmount()) < Double.parseDouble(bean.getMinUseOrderAmount())) {
      setNextUrl(null);
      addErrorMessage("利用最大购买金额不为空时，必须比利用最小购买金额大。");
      setBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }
    // 将页面bean的值存放到dto中
    couponRule = setDTOfromBean(bean, couponRule);
    ServiceResult result = communicationService.updateFriendCouponRule(couponRule);

    // 更新失败
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      setNextUrl(null);
      addErrorMessage(WebMessage.get(ServiceErrorMessage.UPDATE_FAILED, bean.getFriendCouponRuleCn()));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新成功
    setNextUrl("/app/communication/friend_coupon_rule_edit/init/" + WebConstantCode.COMPLETE_UPDATE + "/"
        + couponRule.getFriendCouponRuleNo());

    setRequestBean(getBean());
    setBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "朋友优惠券发行规则详细画面更新处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106072003";
  }

  public boolean authorize() {

    if (super.authorize()) {
      if (Permission.PUBLIC_COUPON_UPDATE_SHOP.isGranted(getLoginInfo()) == false) {
        return false;
      }
    }
    return true;
  }

}
