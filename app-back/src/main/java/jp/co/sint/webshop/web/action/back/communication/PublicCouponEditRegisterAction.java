package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.BeforeAfterDiscountType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public class PublicCouponEditRegisterAction extends PublicCouponEditBaseAction {

  public boolean validate() {
    PublicCouponEditBean bean = getBean();

    // 验证是否存在
    if (checktoDuplicate(bean.getCouponId()) == true) {

      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_DEFAULT_ERROR));
      setRequestBean(getBean());
      setBean(getBean());

      return false;
    }
    if (!StringUtil.isNullOrEmpty(getBean().getMinUseOrderAmount())) {
      if (!NumUtil.isNum(getBean().getMinUseOrderAmount())) {
        addErrorMessage("最小购买金额必须为数字");
        setRequestBean(getBean());
        setBean(getBean());
        return false;
      }
    }
    if (!StringUtil.isNullOrEmpty(getBean().getMoney())) {
      if (!NumUtil.isNum(getBean().getMoney())) {
        addErrorMessage("优惠金额必须为数字");
        setRequestBean(getBean());
        setBean(getBean());
        return false;
      }
    }
    if (!StringUtil.isNullOrEmpty(getBean().getMinUseOrderAmount()) && !StringUtil.isNullOrEmpty(getBean().getMoney())) {
      if (bean.getCouponIssueType().equals(CouponIssueType.FIXED.getValue())) {
        if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(getBean().getMinUseOrderAmount()), new BigDecimal(getBean().getMoney()))) {
          addErrorMessage("利用最小购买金额必须大于优惠金额！");
          setRequestBean(getBean());
          setBean(getBean());
          return false;
        }
      }
    }

    // 验证比率和金额
    if (checkCouponAmount(bean)) {
      return super.validate();
    } else {
      return false;
    }
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    PublicCouponEditBean bean = getBean();
    NewCouponRule newCouponRule = new NewCouponRule();

    // 设定规则编号
    newCouponRule.setCouponCode(bean.getCouponId());
    // 将页面bean的值存放到dto中
    newCouponRule = setDTOfromBean(bean, newCouponRule);

    // 追加默认为（优惠券发行金额类别:折扣前金额）
    newCouponRule.setBeforeAfterDiscountType(BeforeAfterDiscountType.BEFOREDISCOUNT.longValue());
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult serviceResult = communicationService.insertNewCouponRule(newCouponRule);

    // 登录失败
    if (serviceResult.hasError()) {
      setNextUrl(null);
      for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      addErrorMessage(WebMessage.get(DataIOErrorMessage.CONTENTS_REGISTER_FAILED, bean.getCouponName()));
      setRequestBean(getBean());
      setBean(getBean());
      return BackActionResult.SERVICE_ERROR;
    }

    // 添加成功
    setNextUrl("/app/communication/public_coupon_edit/init/" + WebConstantCode.COMPLETE_INSERT + "/"
        + newCouponRule.getCouponCode());
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
