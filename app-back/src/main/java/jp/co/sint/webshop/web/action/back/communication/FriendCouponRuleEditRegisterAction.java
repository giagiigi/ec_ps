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
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public class FriendCouponRuleEditRegisterAction extends FriendCouponRuleEditBaseAction {

  public boolean validate() {
    FriendCouponRuleEditBean bean = getBean();

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
      } else if (bean.getIssueDateType().equals("1")) {
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

      if (!StringUtil.isNullOrEmpty(getBean().getMinUseOrderAmount())) {
        if (!NumUtil.isNum(getBean().getMinUseOrderAmount())) {
          addErrorMessage("最小购买金额必须为数字");
          setRequestBean(getBean());
          setBean(getBean());
          return false;
        }
      }
      if (!StringUtil.isNullOrEmpty(getBean().getCouponAmountNumeric())) {
        if (!NumUtil.isNum(getBean().getCouponAmountNumeric())) {
          addErrorMessage("优惠金额必须为数字");
          setRequestBean(getBean());
          setBean(getBean());
          return false;
        }
      }
      if (!StringUtil.isNullOrEmpty(getBean().getMinUseOrderAmount())
          && !StringUtil.isNullOrEmpty(getBean().getCouponAmountNumeric())) {
        if (bean.getCouponIssueType().equals(CouponIssueType.FIXED.getValue())) {
          if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(getBean().getMinUseOrderAmount()), new BigDecimal(getBean()
              .getCouponAmountNumeric()))) {
            addErrorMessage("利用最小购买金额必须大于优惠金额！");
            setRequestBean(getBean());
            setBean(getBean());
            return false;
          }
        }
      }
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
    FriendCouponRule rule = new FriendCouponRule();

    // 设定规则编号
    rule.setFriendCouponRuleNo(bean.getFriendCouponRuleNo());
    // 将页面bean的值存放到dto中
    rule = setDTOfromBean(bean, rule);

    // 追加默认为（优惠券发行金额类别:折扣前金额）
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult serviceResult = communicationService.addFriendCouponRule(rule);

    // 登录失败
    if (serviceResult.hasError()) {
      setNextUrl(null);
      for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, bean.getFriendCouponRuleCn()));
      setRequestBean(getBean());
      setBean(getBean());
      return BackActionResult.SERVICE_ERROR;
    }

    // 添加成功
    setNextUrl("/app/communication/friend_coupon_rule_edit/init/" + WebConstantCode.COMPLETE_INSERT + "/"
        + rule.getFriendCouponRuleNo());
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
    return "公共优惠券发行规则详细画面新建登录处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106072002";
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
