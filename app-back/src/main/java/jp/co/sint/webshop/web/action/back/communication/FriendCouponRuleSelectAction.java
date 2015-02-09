package jp.co.sint.webshop.web.action.back.communication;

import java.util.Date;

import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U5106111003:朋友推荐优惠券选择查询
 * 
 * @author zhangzhengtao
 */
public class FriendCouponRuleSelectAction extends FriendCouponRuleInitAction {

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
    if (!Permission.FriendCouponRule_READ_SHOP.isGranted(login)) {
      return false;
    }
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    this.setRequestBean(getBean());
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] path = getRequestParameter().getPathArgs();
    // 从数据库中得到数据
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    FriendCouponRule friendcouponrule = service.selectFriendCouponRule(path[0]);
    // 封装进javabean
    FriendCouponRuleBean bean = getBean();
    bean.setFriendCouponRuleNo(friendcouponrule.getFriendCouponRuleNo());
    bean.setFriendCouponRuleCn(friendcouponrule.getFriendCouponRuleCn());
    bean.setFriendCouponRuleJp(friendcouponrule.getFriendCouponRuleJp());
    bean.setFriendCouponRuleEn(friendcouponrule.getFriendCouponRuleEn());
    bean.setIssueDateType(NumUtil.toString(friendcouponrule.getIssueDateType()));
    if (bean.getIssueDateType().equals("0")) {
      bean.setIssueDateNum(NumUtil.toString(friendcouponrule.getIssueDateNum()));
      bean.setIssueStartDate(null);
      bean.setIssueEndDate(null);
    } else {
      Date startDate = DateUtil.getMin();
      Date endDate = DateUtil.getMin();
      if (friendcouponrule.getIssueStartDate() != null) {
        startDate = friendcouponrule.getIssueStartDate();
      }
      if (friendcouponrule.getIssueEndDate() != null) {
        endDate = friendcouponrule.getIssueEndDate();
      }
      bean.setIssueStartDate(DateUtil.toDateTimeString(startDate, "yyyy/MM/dd HH:mm"));
      bean.setIssueEndDate(DateUtil.toDateTimeString(endDate, "yyyy/MM/dd HH:mm"));
      bean.setIssueDateNum(null);
    }
    bean.setOrderHistory(NumUtil.toString(friendcouponrule.getOrderHistory()));
 
    bean.setPersonalUseLimit(NumUtil.toString(friendcouponrule.getPersonalUseLimit()));
    bean.setSiteUseLimit(NumUtil.toString(friendcouponrule.getSiteUseLimit()));
    bean.setMinUseOrderAmount(NumUtil.toString(friendcouponrule.getMinUseOrderAmount()));
    bean.setUseValidType(NumUtil.toString(friendcouponrule.getUseValidType()));
    bean.setUseValidNum(NumUtil.toString(friendcouponrule.getUseValidNum()));
    bean.setApplicableObjects(NumUtil.toString(friendcouponrule.getApplicableObjects()));
    bean.setObtainPoint(NumUtil.toString(friendcouponrule.getObtainPoint()));
    bean.setFixChar(friendcouponrule.getFixChar());

    // 20140404 hdh add start
    bean.setCouponIssueType(NumUtil.toString(friendcouponrule.getCouponIssueType()));
    bean.setCouponUseNum(NumUtil.toString(friendcouponrule.getCouponUseNum()));
    bean.setFormerUsePoint(NumUtil.toString(friendcouponrule.getFormerUsePoint()));
    bean.setIssueObtainPoint(NumUtil.toString(friendcouponrule.getIssueObtainPoint()));
    bean.setMaxUseOrderAmount(NumUtil.toString(friendcouponrule.getMaxUseOrderAmount()));
    if(CampaignType.FIXED.getValue().equals(NumUtil.toString(friendcouponrule.getCouponIssueType()))){
      bean.setCouponAmountNumeric((friendcouponrule.getCouponAmount().toString()));
    }else if(CampaignType.PROPORTION.getValue().equals(NumUtil.toString(friendcouponrule.getCouponIssueType()))){
      
      bean.setRatio(NumUtil.toString(friendcouponrule.getCouponProportion()));
      
    }
   
    // 20140404 hdh add end
    
    // 判断月份数值并补0
    if (StringUtil.hasValue(bean.getIssueDateNum())) {
      String month = null;
      if (friendcouponrule.getIssueDateNum() < 10) {
        month = "0" + friendcouponrule.getIssueDateNum();
      } else {
        month = NumUtil.toString(friendcouponrule.getIssueDateNum());
      }
      bean.setIssueDateNum(month);
    }
    // 封装进bean
    bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
    this.setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FriendCouponRuleSelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106111003";
  }
}
