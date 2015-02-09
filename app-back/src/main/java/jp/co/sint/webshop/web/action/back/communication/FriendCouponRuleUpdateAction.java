package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class FriendCouponRuleUpdateAction extends FriendCouponRuleInitAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    // 没有更新和查看权限,不显示
    if (!Permission.FriendCouponRule_UPDATE_SHOP.isGranted(login)) {
      return false;
    }
    return true;  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    FriendCouponRuleBean bean = getBean();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    if(StringUtil.hasValue(bean.getFriendCouponRuleNo())&& service.existFriendCouponIssueHistory(bean.getFriendCouponRuleNo())){
      addErrorMessage("发行规则已在使用中，不能更新！");
      return false;
    }
    
    boolean sus = true;
    //验证比率和金额
    if (checkCouponAmount(bean)) {
      sus=super.validate();
    } else {
      sus=false;
    }
    if (validateBean(bean)) {
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
        if(sus){
          if (DateUtil.fromString(bean.getIssueStartDate(), true).after(DateUtil.fromString(bean.getIssueEndDate(), true))) {
            addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
                .getString("web.action.back.communication.FriendCouponRuleAction.2")));
            sus = false;
          }
        }
      }
    }else{
      sus=false;
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
    FriendCouponRuleBean bean = getBean();
    // 得到单个对象friendcouponrule
    FriendCouponRule friendcouponrule = new FriendCouponRule();
    friendcouponrule.setFriendCouponRuleNo(bean.getFriendCouponRuleNo());
    friendcouponrule.setFriendCouponRuleCn(bean.getFriendCouponRuleCn());
    friendcouponrule.setFriendCouponRuleJp(bean.getFriendCouponRuleJp());
    friendcouponrule.setFriendCouponRuleEn(bean.getFriendCouponRuleEn());
    friendcouponrule.setIssueDateType(NumUtil.toLong(bean.getIssueDateType()));
    if (bean.getIssueDateType().equals("0")) {
      friendcouponrule.setIssueDateNum(NumUtil.toLong(bean.getIssueDateNum()));
      friendcouponrule.setIssueStartDate(DateUtil.fromString(null, true));
      friendcouponrule.setIssueEndDate(DateUtil.fromString(null, true));
    } else {
      friendcouponrule.setIssueDateNum(NumUtil.toLong(null));
      friendcouponrule.setIssueStartDate(DateUtil.fromString(bean.getIssueStartDate(), true));
      friendcouponrule.setIssueEndDate(DateUtil.fromString(bean.getIssueEndDate(), true));
    }
    friendcouponrule.setOrderHistory(NumUtil.toLong(bean.getOrderHistory()));

    friendcouponrule.setPersonalUseLimit(NumUtil.toLong(bean.getPersonalUseLimit()));
    friendcouponrule.setSiteUseLimit(NumUtil.toLong(bean.getSiteUseLimit()));
    friendcouponrule.setMinUseOrderAmount(NumUtil.parse(bean.getMinUseOrderAmount()));
    friendcouponrule.setUseValidType(NumUtil.toLong(bean.getUseValidType()));
    friendcouponrule.setUseValidNum(NumUtil.toLong(bean.getUseValidNum()));
    friendcouponrule.setApplicableObjects(NumUtil.toLong(bean.getApplicableObjects()));
    friendcouponrule.setObtainPoint(NumUtil.toLong(bean.getObtainPoint()));
    friendcouponrule.setFixChar(bean.getFixChar());
    
    // 20140404 hdh add start
    friendcouponrule.setCouponIssueType(NumUtil.toLong(bean.getCouponIssueType()));
    friendcouponrule.setIssueObtainPoint(NumUtil.toLong(bean.getIssueObtainPoint()));
    friendcouponrule.setFormerUsePoint(NumUtil.toLong(bean.getFormerUsePoint()));
    friendcouponrule.setCouponUseNum(NumUtil.toLong(bean.getCouponUseNum()));
    if(CampaignType.FIXED.getValue().equals(bean.getCouponIssueType())){
      friendcouponrule.setCouponAmount(NumUtil.parse(bean.getCouponAmountNumeric()));
    }else if(CampaignType.PROPORTION.getValue().equals(bean.getCouponIssueType())){
      friendcouponrule.setCouponProportion(NumUtil.toLong(bean.getRatio()));
    }
    friendcouponrule.setMaxUseOrderAmount(NumUtil.parse(bean.getMaxUseOrderAmount()));
    // 20140404 hdh add end;
    
    // 修改
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = service.updateFriendCouponRule(friendcouponrule);
    // 登録処理の成功チェック
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }
    addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
        .getString("web.bean.back.communication.FriendCouponRuleBean.0")));
    this.setRequestBean(bean);
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FriendCouponRuleRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106111004";
  }
}
