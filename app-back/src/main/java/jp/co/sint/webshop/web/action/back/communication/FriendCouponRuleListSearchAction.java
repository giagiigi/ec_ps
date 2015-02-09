package jp.co.sint.webshop.web.action.back.communication;

import java.util.Date;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.FriendCouponRuleCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleListBean;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleListBean.FriendCouponRuleBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System OB.
 */
public class FriendCouponRuleListSearchAction extends WebBackAction<FriendCouponRuleListBean> {

  private FriendCouponRuleCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new FriendCouponRuleCondition();
  }

  protected FriendCouponRuleCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected FriendCouponRuleCondition getSearchCondition() {
    return this.condition;
  }

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
		if (Permission.PUBLIC_COUPON_READ_SHOP.isGranted(login)) {
			return true;
		}
		return false;
		
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    FriendCouponRuleListBean bean = getBean();

    condition = getCondition();
    //soukai 2012/01/13 ob start
    condition.setSearchCouponCode(bean.getSearchCouponCode());
    //soukai 2012/01/13 ob end
    condition.setSearchCampaignType(bean.getSearchCouponType());
    condition.setSearchCouponName(bean.getSearchCouponName());
    condition.setSearchMinIssueStartDatetimeFrom(bean.getSearchMinIssueStartDatetimeFrom());
    condition.setSearchMinIssueStartDatetimeTo(bean.getSearchMinIssueStartDatetimeTo());
    condition.setSearchMinIssueEndDatetimeFrom(bean.getSearchMinIssueEndDatetimeFrom());
    condition.setSearchMinIssueEndDatetimeTo(bean.getSearchMinIssueEndDatetimeTo());
    condition.setSearchIssueDateType(bean.getSearchIssueDateType());
    condition.setSearchIssueDateNum(bean.getSearchIssueDateNum());
  //发行状态
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<FriendCouponRule> result = service.selectAllFriendCouponRule(condition);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getCouponList().clear();
    for (FriendCouponRule rule : result.getRows()) {
      FriendCouponRuleBean detail = new FriendCouponRuleBean();
      detail.setFriendCouponRuleNo(rule.getFriendCouponRuleNo());
      detail.setFriendCouponRuleCn(rule.getFriendCouponRuleCn());
      detail.setFriendCouponRuleJp(rule.getFriendCouponRuleJp());
      detail.setFriendCouponRuleEn(rule.getFriendCouponRuleEn());
      detail.setIssueDateType(NumUtil.toString(rule.getIssueDateType()));
      if (rule.getIssueDateType() == 0) {
        String month = null;
        if (rule.getIssueDateNum() < 10) {
          month = "0" + rule.getIssueDateNum();
        } else {
          month = NumUtil.toString(rule.getIssueDateNum());
        }
        detail.setIssueDateNum(month);
        detail.setIssueStartDate(null);
        detail.setIssueEndDate(null);
      } else {
        Date startDate = DateUtil.getMin();
        Date endDate = DateUtil.getMin();
        if (rule.getIssueStartDate() != null) {
          startDate = rule.getIssueStartDate();
        }
        if (rule.getIssueEndDate() != null) {
          endDate = rule.getIssueEndDate();
        }
        detail.setIssueStartDate(DateUtil.toDateTimeString(startDate, "yyyy/MM/dd HH:mm"));
        detail.setIssueEndDate(DateUtil.toDateTimeString(endDate, "yyyy/MM/dd HH:mm"));
        detail.setIssueDateNum(null);
      }
      detail.setOrderHistory(NumUtil.toString(rule.getOrderHistory()));
      detail.setCouponAmountNumeric(NumUtil.toString(rule.getCouponAmount()));
      detail.setPersonalUseLimit(NumUtil.toString(rule.getPersonalUseLimit()));
      detail.setSiteUseLimit(NumUtil.toString(rule.getSiteUseLimit()));
      detail.setMinUseOrderAmount(NumUtil.toString(rule.getMinUseOrderAmount()));
      detail.setUseValidType(NumUtil.toString(rule.getUseValidType()));
      detail.setUseValidNum(NumUtil.toString(rule.getUseValidNum()));
      detail.setApplicableObjects(NumUtil.toString(rule.getApplicableObjects()));
      detail.setObtainPoint(NumUtil.toString(rule.getObtainPoint()));
      detail.setFixChar(rule.getFixChar());
      detail.setMaxUseOrderAmount(NumUtil.toString(rule.getMaxUseOrderAmount()));
      bean.getCouponList().add(detail);
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
	boolean isValid = true;
	FriendCouponRuleListBean bean = getBean();
	
	// 検索条件のvalidationチェック
	isValid &= validateBean(bean);
    
    // 日付の大小関係チェック
    if(isValid){
	    
	    //优惠券利用开始日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinIssueStartDatetimeFrom(), bean.getSearchMinIssueStartDatetimeTo())) {
	        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinIssueStartDatetimeFrom(), bean.getSearchMinIssueStartDatetimeTo())) {
	          isValid &= false;
	          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券利用开始日时"));
	        }
	      }
	    
	    //优惠券利用结束日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinIssueEndDatetimeFrom(), bean.getSearchMinIssueEndDatetimeTo())) {
	        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinIssueEndDatetimeFrom(), bean.getSearchMinIssueEndDatetimeTo())) {
	          isValid &= false;
	          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券利用结束日时"));
	        }
	      }
    }
 
    return isValid;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "公共优惠券发行规则一览画面检索处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106071003";
  }

}
