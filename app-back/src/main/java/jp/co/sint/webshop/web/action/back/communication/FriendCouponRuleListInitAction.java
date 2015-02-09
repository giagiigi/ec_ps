package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.DateDistinguish;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U5106111001:朋友推荐优惠券选择查询 s
 * 
 * @author zhangzhengtao
 */
public class FriendCouponRuleListInitAction extends WebBackAction<FriendCouponRuleListBean> {

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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
//    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
//    // 分页查询
//    FriendCouponRuleCondition condition = new FriendCouponRuleCondition();
//    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
//    SearchResult<FriendCouponRule> result = service.selectAllFriendCouponRule(condition);
    // 封装进bean
    FriendCouponRuleListBean bean = new FriendCouponRuleListBean();
    List<CodeAttribute> couponIssueTypes = new ArrayList<CodeAttribute>();
    // 优惠劵类别下拉框选项设定
    couponIssueTypes.add(new NameValue("请选择", ""));
    for (CampaignType sk : CampaignType.values()) {
      couponIssueTypes.add(sk);
    }
    bean.setCouponIssueTypes(couponIssueTypes);
    
    //可行可能日期类型
    List<CodeAttribute> issueDateTypes = new ArrayList<CodeAttribute>();
    issueDateTypes.add(new NameValue("全部",null));
    for (DateDistinguish type : DateDistinguish.values()) {
      issueDateTypes.add(type);
    }
    bean.setIssueDateTypes(issueDateTypes);
    
    // bean.setCouponIssueType(CampaignType.PROPORTION.getValue());
//    bean.setPagerValue(PagerUtil.createValue(result));
//    bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
//    for (FriendCouponRule rule : result.getRows()) {
//      FriendCouponRuleBean detail = new FriendCouponRuleBean();
//      detail.setFriendCouponRuleNo(rule.getFriendCouponRuleNo());
//      detail.setFriendCouponRuleCn(rule.getFriendCouponRuleCn());
//      detail.setFriendCouponRuleJp(rule.getFriendCouponRuleJp());
//      detail.setFriendCouponRuleEn(rule.getFriendCouponRuleEn());
//      detail.setIssueDateType(NumUtil.toString(rule.getIssueDateType()));
//      if (rule.getIssueDateType() == 0) {
//        String month = null;
//        if (rule.getIssueDateNum() < 10) {
//          month = "0" + rule.getIssueDateNum();
//        } else {
//          month = NumUtil.toString(rule.getIssueDateNum());
//        }
//        detail.setIssueDateNum(month);
//        detail.setIssueStartDate(null);
//        detail.setIssueEndDate(null);
//      } else {
//        Date startDate = DateUtil.getMin();
//        Date endDate = DateUtil.getMin();
//        if (rule.getIssueStartDate() != null) {
//          startDate = rule.getIssueStartDate();
//        }
//        if (rule.getIssueEndDate() != null) {
//          endDate = rule.getIssueEndDate();
//        }
//        detail.setIssueStartDate(DateUtil.toDateTimeString(startDate, "yyyy/MM/dd HH:mm"));
//        detail.setIssueEndDate(DateUtil.toDateTimeString(endDate, "yyyy/MM/dd HH:mm"));
//        detail.setIssueDateNum(null);
//      }
//      detail.setOrderHistory(NumUtil.toString(rule.getOrderHistory()));
//      detail.setCouponAmountNumeric(NumUtil.toString(rule.getCouponAmount()));
//      detail.setPersonalUseLimit(NumUtil.toString(rule.getPersonalUseLimit()));
//      detail.setSiteUseLimit(NumUtil.toString(rule.getSiteUseLimit()));
//      detail.setMinUseOrderAmount(NumUtil.toString(rule.getMinUseOrderAmount()));
//      detail.setUseValidType(NumUtil.toString(rule.getUseValidType()));
//      detail.setUseValidNum(NumUtil.toString(rule.getUseValidNum()));
//      detail.setApplicableObjects(NumUtil.toString(rule.getApplicableObjects()));
//      detail.setObtainPoint(NumUtil.toString(rule.getObtainPoint()));
//      detail.setFixChar(rule.getFixChar());
//      detail.setMaxUseOrderAmount(NumUtil.toString(rule.getMaxUseOrderAmount()));
//      bean.getCouponList().add(detail);
//    }
    setNextUrl(null);
    this.setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示状態を取得します
   * 
   * @param displayFlg
   * @return 表示状態（表示/非表示）
   */
  public String getStatus(Long displayFlg) {
    String status = "";
    if (displayFlg.equals(0L)) {
      status = Messages.getString("web.action.back.communication.MailMagazineInitAction.1");
    } else {
      status = Messages.getString("web.action.back.communication.MailMagazineInitAction.2");
    }
    return status;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    FriendCouponRuleListBean requestBean = (FriendCouponRuleListBean) getRequestBean();
    BackLoginInfo login = getLoginInfo();
    requestBean.setDeleteButtonFlg(false);
    if (Permission.FriendCouponRule_DELETE_SHOP.isGranted(login)) {
      requestBean.setDeleteButtonFlg(true);
      requestBean.setRegisterNewDisplayFlg(true);
    }
    if (!Permission.FriendCouponRule_UPDATE_SHOP.isGranted(login)) {
      requestBean.setRegisterNewDisplayFlg(true);
      requestBean.setDisplayMode(null);
    }
    setRequestBean(requestBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FriendCouponRuleInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106111001";
  }
}
