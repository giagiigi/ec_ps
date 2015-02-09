package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public abstract class PrivateCouponEditBaseAction extends WebBackAction<PrivateCouponEditBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean isValid = true;
    PrivateCouponEditBean bean = getBean();

    ValidationSummary summary = BeanValidator.validate(bean);
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      isValid &= false;
    }

    // 优惠券发行类别 = 比例,优惠券比例必须输入
    if (CampaignType.PROPORTION.getValue().equals(bean.getCampaignType())) {
      if (StringUtil.isNullOrEmpty(bean.getCouponProportion())) {
        addErrorMessage("优惠比例" + Messages.getString("validation.RequiredValidator.0"));
        isValid &= false;
      } else {
        if (Double.parseDouble(bean.getCouponProportion()) <= 0) {
          addErrorMessage("优惠比例必须大于零");
          isValid &= false;
        }
      }

      // 优惠券发行类别 = 固定金额，优惠金额必须输入
    } else {
      if (StringUtil.isNullOrEmpty(bean.getCouponAmount())) {
        addErrorMessage("优惠金额" + Messages.getString("validation.RequiredValidator.0"));
        isValid &= false;
      }
    }

    // 优惠发行方式=购买发行时：发行期间From、发行期间To、发行最小购买金额 必须输入
    if (CouponType.PURCHASE_DISTRIBUTION.getName().equals(bean.getCouponType())) {

      // 发行期间From
      if (StringUtil.isNullOrEmpty(bean.getMinIssueStartDatetime())) {
        addErrorMessage("发行期间From" + Messages.getString("validation.RequiredValidator.0"));
        isValid &= false;
      }
      // 发行期间To
      if (StringUtil.isNullOrEmpty(bean.getMinIssueEndDatetime())) {
        addErrorMessage("发行期间To" + Messages.getString("validation.RequiredValidator.0"));
        isValid &= false;
      }
      // 发行最小购买金额
      if (StringUtil.isNullOrEmpty(bean.getMinIssueOrderAmount())) {
        addErrorMessage("发行最小购买金额" + Messages.getString("validation.RequiredValidator.0"));
        isValid &= false;
      }
    }

    // 优惠券发行开始日时
    if (StringUtil.hasValueAllOf(getBean().getMinIssueStartDatetime(), bean.getMinIssueEndDatetime())) {
      if (!ValidatorUtil.isCorrectOrder(bean.getMinIssueStartDatetime(), bean.getMinIssueEndDatetime())) {
        isValid &= false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券发行日时"));
      }
    }

    // 优惠券利用开始日时
    if (StringUtil.hasValueAllOf(bean.getMinUseStartDatetime(), bean.getMinUseEndDatetime())) {
      if (!ValidatorUtil.isCorrectOrder(bean.getMinUseStartDatetime(), bean.getMinUseEndDatetime())) {
        isValid &= false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券利用日时"));
      }
    }
    // 2013/04/01 优惠券对应 ob delete start
    // //发行期间小于利用期间时
    // if (StringUtil.hasValueAllOf(bean.getMinIssueEndDatetime(),
    // bean.getMinUseStartDatetime())) {
    // if (!ValidatorUtil.isCorrectOrder(bean.getMinIssueEndDatetime(),
    // bean.getMinUseStartDatetime())) {
    // isValid &= false;
    // addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
    // "发行期间与利用期间"));
    // }
    // }
    // 2013/04/01 优惠券对应 ob delete end
    return isValid;
  }

  /**
   * 顾客别优惠券发行规则DTO作成
   * 
   * @param newCouponRule
   *          顾客别优惠券发行规则
   * @return 新的顾客别优惠券发行规则信息
   */
  public NewCouponRule createNewCouponRuleBean(NewCouponRule newCouponRule) {

    PrivateCouponEditBean bean = getBean();
    if (newCouponRule == null) {
      newCouponRule = new NewCouponRule();
    }
    // 优惠券规则编号
    newCouponRule.setCouponCode(bean.getCouponCode());
    // 优惠券规则名称
    newCouponRule.setCouponName(bean.getCouponName());
    newCouponRule.setCouponNameEn(bean.getCouponNameEn());
    newCouponRule.setCouponNameJp(bean.getCouponNameJp());
    if (StringUtil.hasValue(bean.getUseType())){
      newCouponRule.setUseType(Long.parseLong(bean.getUseType()));
    } else {
      newCouponRule.setUseType(0L);
    }

    // 优惠券发行方式
    newCouponRule.setCouponType(CouponType.fromName(bean.getCouponType()).longValue());
    // 2013/04/02 优惠券对应 ob add start
    // 适用对象
    newCouponRule.setApplicableObjects(NumUtil.toLong(bean.getApplicableObject()));
    // 2013/04/02 优惠券对应 ob add end
    // 适用区域
    newCouponRule.setApplicableArea("");
    // 优惠券发行类别
    newCouponRule.setCouponIssueType(NumUtil.toLong(bean.getCampaignType()));
    // 优惠券发行类别 = 比例
    if (CampaignType.PROPORTION.longValue().equals(newCouponRule.getCouponIssueType())) {
      // 优惠券比例
      newCouponRule.setCouponProportion(NumUtil.toLong(bean.getCouponProportion()));
      // 优惠券金额
      newCouponRule.setCouponAmount(BigDecimal.valueOf(Double.valueOf(0)));
      // 优惠券发行类别 = 固定金额
    } else {
      // 优惠券比例
      newCouponRule.setCouponProportion((null));
      // 优惠券金额
      newCouponRule.setCouponAmount(BigDecimal.valueOf(Double.valueOf(bean.getCouponAmount())));
    }
    // 2013/04/02 优惠券对应 ob add start
    // //个人最大利用回数
    // newCouponRule.setPersonalUseLimit(BigDecimal.valueOf(Double.valueOf(0)));
    // //SITE最大利用回数
    // newCouponRule.setSiteUseLimit(BigDecimal.valueOf(Double.valueOf(0)));
    // 个人最大利用回数
    newCouponRule.setPersonalUseLimit(BigDecimal.valueOf(Double.valueOf(bean.getPersonalUseLimit())));
    // SITE最大利用回数
    newCouponRule.setSiteUseLimit(BigDecimal.valueOf(Double.valueOf(bean.getSiteUseLimit())));
    // 有无指定商品
    newCouponRule.setRelatedCommodityFlg(NumUtil.toLong(bean.getRelatedCommodityFlg()));
    
//    if (CouponType.PURCHASE_DISTRIBUTION.longValue().equals(newCouponRule.getCouponType())) {
      // 优惠券发行开始日时
      newCouponRule.setMinIssueStartDatetime(DateUtil.fromString(bean.getMinIssueStartDatetime(), true));
      // 优惠券发行结束日时
      newCouponRule.setMinIssueEndDatetime(DateUtil.fromString(bean.getMinIssueEndDatetime().substring(0, 17) + "59", true));
      // 优惠券利用开始日时
      newCouponRule.setMinUseStartDatetime(DateUtil.fromString(DateUtil.toDateTimeString(DateUtil.getMin()), true));
      // 优惠券利用结束日时
      newCouponRule.setMinUseEndDatetime(DateUtil.fromString(DateUtil.toDateTimeString(DateUtil.getMin()).substring(0, 17) + "59", true));
      // 优惠券发行最小购买金额
      newCouponRule.setMinIssueOrderAmount(BigDecimal.valueOf(Double.valueOf(bean.getMinIssueOrderAmount())));
//    } else {
//      // 优惠券发行开始日时
//      newCouponRule.setMinIssueStartDatetime(DateUtil.fromString(DateUtil.toDateTimeString(DateUtil.getMin()), true));
//      // 优惠券发行结束日时
//      newCouponRule.setMinIssueEndDatetime(DateUtil.fromString(DateUtil.toDateTimeString(DateUtil.getMin()).substring(0, 17) + "59", true));
//      // 优惠券利用开始日时
//      newCouponRule.setMinUseStartDatetime(DateUtil.fromString(bean.getMinUseStartDatetime(), true));
//      // 优惠券利用结束日时
//      newCouponRule.setMinUseEndDatetime(DateUtil.fromString(bean.getMinUseEndDatetime().substring(0, 17) + "59", true));
//      // 发行理由
//      newCouponRule.setIssueReason(bean.getIssueReason());
//    }

    // 优惠券利用最小购买金额
    newCouponRule.setMinUseOrderAmount(BigDecimal.valueOf(Double.valueOf(bean.getMinUseOrderAmount())));
    // 优惠券利用最大购买金额
    if(StringUtil.hasValue(bean.getMaxUseOrderAmount())){
      newCouponRule.setMaxUseOrderAmount(BigDecimal.valueOf(Double.valueOf(bean.getMaxUseOrderAmount())));
    }else{
//      newCouponRule.setMaxUseOrderAmount(BigDecimal.valueOf(Double.valueOf(0)));
    }
    // 优惠券发行金额类别
    newCouponRule.setBeforeAfterDiscountType(NumUtil.toLong(bean.getBeforeAfterDiscountType()));
    // 优惠券利用开始日期
    newCouponRule.setMinUseStartNum(NumUtil.toLong(bean.getMinUseStartNum()));
    // 优惠券利用结束日期
    newCouponRule.setMinUseEndNum(NumUtil.toLong(bean.getMinUseEndNum()));
    // 2013/04/02 优惠券对应 ob add end
    // 备注
    newCouponRule.setMemo(bean.getMemo());
    newCouponRule.setApplicableObjects(NumUtil.toLong(bean.getApplicableObject()));

    if (StringUtil.hasValue(bean.getExchangePointAmount())) {
      newCouponRule.setExchangePointAmount(NumUtil.toLong(bean.getExchangePointAmount()));
    }
    
    return newCouponRule;
  }

  /**
   * 给画面Bean设置顾客别优惠券发行规则信息
   * 
   * @return 画面Bean
   */
  public PrivateCouponEditBean createBean(PrivateCouponEditBean bean) {
    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    NewCouponRule newCouponRule = svc.getPrivateCoupon(bean.getCouponCode());

    if (newCouponRule != null) {
      // 优惠券规则编号
      bean.setCouponCode(newCouponRule.getCouponCode());
      // 优惠券规则名称
      bean.setCouponName(newCouponRule.getCouponName());
      bean.setCouponNameEn(newCouponRule.getCouponNameEn());
      bean.setCouponNameJp(newCouponRule.getCouponNameJp());
      if (newCouponRule.getUseType() != null){
        bean.setUseType(newCouponRule.getUseType().toString());
      } else {
        bean.setUseType("0");
      }
      // 优惠券发行方式
      bean.setCouponType(CouponType.fromValue(newCouponRule.getCouponType()).getName());
      // 2013/04/02 优惠券对应 ob add start
      // 适用对象
      bean.setApplicableObject(String.valueOf(newCouponRule.getApplicableObjects()));
      // 利用最大购买金额
      if(newCouponRule.getMaxUseOrderAmount() != null){
        bean.setMaxUseOrderAmount(String.valueOf(newCouponRule.getMaxUseOrderAmount()));
      }
      // 个人最大利用回数
      bean.setPersonalUseLimit(String.valueOf(newCouponRule.getPersonalUseLimit()));
      // site最大利用回数
      bean.setSiteUseLimit(String.valueOf(newCouponRule.getSiteUseLimit()));
      // 优惠券发行金额类别
      bean.setBeforeAfterDiscountType(String.valueOf(newCouponRule.getBeforeAfterDiscountType()));
      // 有无指定商品
      bean.setRelatedCommodityFlg(String.valueOf(newCouponRule.getRelatedCommodityFlg()));
      // 2013/04/02 优惠券对应 ob add start
      // 优惠券发行类别
      bean.setCampaignType(String.valueOf(newCouponRule.getCouponIssueType()));

      if (CampaignType.FIXED.longValue().equals(newCouponRule.getCouponIssueType())) {
        // 优惠券金额
        bean.setCouponAmount(String.valueOf(newCouponRule.getCouponAmount()));
      } else {
        // 优惠比例
        bean.setCouponProportion(NumUtil.toString(newCouponRule.getCouponProportion()));
      }
      // 发行期间From
      bean.setMinIssueStartDatetime(DateUtil.toDateTimeString(newCouponRule.getMinIssueStartDatetime()));
      // 发行期间To
      bean.setMinIssueEndDatetime(DateUtil.toDateTimeString(newCouponRule.getMinIssueEndDatetime()));
      // 发行最小购买金额
      bean.setMinIssueOrderAmount(String.valueOf(newCouponRule.getMinIssueOrderAmount()));
      // 利用期间From
      bean.setMinUseStartDatetime(DateUtil.toDateTimeString(newCouponRule.getMinUseStartDatetime()));
      // 利用期间To
      bean.setMinUseEndDatetime(DateUtil.toDateTimeString(newCouponRule.getMinUseEndDatetime()));
      // 优惠券利用开始日期
      bean.setMinUseStartNum(String.valueOf(newCouponRule.getMinUseStartNum()));
      // 优惠券利用结束日期
      bean.setMinUseEndNum(String.valueOf(newCouponRule.getMinUseEndNum()));
      // 利用最小购买金额
      bean.setMinUseOrderAmount(String.valueOf(newCouponRule.getMinUseOrderAmount()));
      // 发行理由
      bean.setIssueReason(newCouponRule.getIssueReason());
      // 备注
      bean.setMemo(newCouponRule.getMemo());
      // 更新时间
      bean.setUpdateDatetime(newCouponRule.getUpdatedDatetime());

      // 画面更新按钮显示
      if (Permission.PRIVATE_COUPON_UPDATE_SHOP.isGranted(getLoginInfo())) {
        bean.setUpdateAuthorizeFlag(true);
      } else {
        bean.setCouponDisplayMode(WebConstantCode.DISPLAY_READONLY);
      }

      // 根据优惠券发送方式设置画面显示项目
      if (CouponType.SPECIAL_MEMBER_DISTRIBUTION.longValue().equals(newCouponRule.getCouponType())) {
        bean.setCouponTypeFlag(1);
      }
      
      bean.setExchangePointAmount(NumUtil.toString(newCouponRule.getExchangePointAmount()));
    } else {
      return new PrivateCouponEditBean();
    }
    return bean;

  }
}
