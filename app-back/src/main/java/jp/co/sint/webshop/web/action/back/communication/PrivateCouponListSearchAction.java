package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponListBean;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponListBean.PrivateCouponListBeanDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060610:PRIVATEクーポン管理のアクションクラスです
 * 
 * @author System OB.
 */
public class PrivateCouponListSearchAction extends WebBackAction<PrivateCouponListBean> {

  private PrivateCouponListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new PrivateCouponListSearchCondition();
  }

  protected PrivateCouponListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected PrivateCouponListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
	  return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    PrivateCouponListBean bean = getBean();

    condition = getCondition();
    //优惠券规则编号
    condition.setSearchCouponCode(bean.getSearchCouponCode());
    //优惠券规则名称
    condition.setSearchCouponName(bean.getSearchCouponName());
    condition.setSearchCouponNameEn(bean.getSearchCouponNameEn());
    condition.setSearchCouponNameJp(bean.getSearchCouponNameJp());
    //优惠券类别
    condition.setSearchCouponType(bean.getSearchCouponType());
    //优惠券发行类别
    condition.setSearchCampaignType(bean.getSearchCampaignType());
    //优惠券发行开始日时(From)
    condition.setSearchMinIssueStartDatetimeFrom(bean.getSearchMinIssueStartDatetimeFrom());
    //优惠券发行开始日时(To)
    condition.setSearchMinIssueStartDatetimeTo(bean.getSearchMinIssueStartDatetimeTo());
    //优惠券发行结束日时(From)
    condition.setSearchMinIssueEndDatetimeFrom(bean.getSearchMinIssueEndDatetimeFrom());
    //优惠券发行结束日时(To)
    condition.setSearchMinIssueEndDatetimeTo(bean.getSearchMinIssueEndDatetimeTo());
    //优惠券利用开始日时(From)
    condition.setSearchMinUseStartDatetimeFrom(bean.getSearchMinUseStartDatetimeFrom());
    //优惠券利用开始日时(To)
    condition.setSearchMinUseStartDatetimeTo(bean.getSearchMinUseStartDatetimeTo());
    //优惠券利用结束日时(From)
    condition.setSearchMinUseEndDatetimeFrom(bean.getSearchMinUseEndDatetimeFrom());
    //优惠券利用结束日时(To)
    condition.setSearchMinUseEndDatetimeTo(bean.getSearchMinUseEndDatetimeTo());
    //发行状态
    condition.setSearchCouponActivityStatus(bean.getSearchCouponActivityStatus());

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    
    //根据查询条件取得顾客别优惠券发行规则信息List
    SearchResult<NewCouponRule> result = service.searchNewCouponRuleList(condition, true);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getPrivateCouponList().clear();
    //生成画面一览信息
    for (NewCouponRule row : result.getRows()) {
    	PrivateCouponListBeanDetail detail = new PrivateCouponListBeanDetail();

    	//优惠券规则编号
    	detail.setCouponCode(row.getCouponCode()); 
    	//优惠券规则名称
    	detail.setCouponName(row.getCouponName()); 
    	//优惠券类别
    	detail.setCouponType(CouponType.fromValue(row.getCouponType()).getName()); 
    	//优惠券发行类别
    	detail.setCampaignType(CampaignType.fromValue(row.getCouponIssueType()).getName()); 
    	//优惠券金额
    	detail.setCouponAmount(String.valueOf(row.getCouponAmount()));
    	//优惠券比例
    	detail.setCouponProportion(NumUtil.toString(row.getCouponProportion()));
    	//优惠券发行开始日时
    	detail.setMinIssueStartDatetime(DateUtil.toDateTimeString(row.getMinIssueStartDatetime()));
    	//优惠券发行结束日时
    	detail.setMinIssueEndDatetime(DateUtil.toDateTimeString(row.getMinIssueEndDatetime())); 
    	//优惠券发行最小购买金额
    	if(row.getMinIssueOrderAmount() == null){
    		detail.setMinIssueOrderAmount("");
    	} else {
    		detail.setMinIssueOrderAmount(String.valueOf(row.getMinIssueOrderAmount()));
    	}
    	//优惠券利用开始日时
    	detail.setMinUseStartDatetime(DateUtil.toDateTimeString(row.getMinUseStartDatetime())); 
    	//优惠券利用结束日时
    	detail.setMinUseEndDatetime(DateUtil.toDateTimeString(row.getMinUseEndDatetime())); 
    	
    	detail.setMinUseEndNum(NumUtil.toString(row.getMinUseEndNum()));
    	
    	//优惠券利用最小购买金额
    	detail.setMinUseOrderAmount(String.valueOf((row.getMinUseOrderAmount()))); 
    	detail.setAfterText(CampaignType.PROPORTION.longValue().equals(row.getCouponIssueType()));
    	
    	bean.getPrivateCouponList().add(detail);
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
	PrivateCouponListBean bean = getBean();
	
	// 検索条件のvalidationチェック
	isValid &= validateBean(bean);
    
    // 日付の大小関係チェック
    if(isValid){
	    //优惠券发行开始日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinIssueStartDatetimeFrom(), bean.getSearchMinIssueStartDatetimeTo())) {
	      if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinIssueStartDatetimeFrom(), bean.getSearchMinIssueStartDatetimeTo())) {
	        isValid &= false;
	        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券发行开始日时"));
	      }
	    }
	    
	    //优惠券发行结束日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinIssueEndDatetimeFrom(), bean.getSearchMinIssueEndDatetimeTo())) {
	        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinIssueEndDatetimeFrom(), bean.getSearchMinIssueEndDatetimeTo())) {
	          isValid &= false;
	          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券发行结束日时"));
	        }
	      }
	    
	    //优惠券利用开始日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinUseStartDatetimeFrom(), bean.getSearchMinUseStartDatetimeTo())) {
	        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinUseStartDatetimeFrom(), bean.getSearchMinUseStartDatetimeTo())) {
	          isValid &= false;
	          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "优惠券利用开始日时"));
	        }
	      }
	    
	    //优惠券利用结束日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinUseEndDatetimeFrom(), bean.getSearchMinUseEndDatetimeTo())) {
	        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinUseEndDatetimeFrom(), bean.getSearchMinUseEndDatetimeTo())) {
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
    return "顾客别优惠券发行规则检索处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106061004";
  }

}
