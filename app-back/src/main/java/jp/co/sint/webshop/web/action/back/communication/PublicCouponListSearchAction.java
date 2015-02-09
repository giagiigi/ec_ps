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
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponListBean;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponListBean.PrivateCouponListBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System OB.
 */
public class PublicCouponListSearchAction extends WebBackAction<PublicCouponListBean> {

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

	PublicCouponListBean bean = getBean();

    condition = getCondition();
    //soukai 2012/01/13 ob start
    condition.setSearchCouponCode(bean.getSearchCouponCode());
    //soukai 2012/01/13 ob end
    condition.setSearchCampaignType(bean.getSearchCouponType());
    condition.setSearchCouponName(bean.getSearchCouponName());
    condition.setSearchCouponType(CouponType.COMMON_DISTRIBUTION.getValue());
    condition.setSearchMinUseStartDatetimeFrom(bean.getSearchMinUseStartDatetimeFrom());
    condition.setSearchMinUseStartDatetimeTo(bean.getSearchMinUseStartDatetimeTo());
    condition.setSearchMinUseEndDatetimeFrom(bean.getSearchMinUseEndDatetimeFrom());
    condition.setSearchMinUseEndDatetimeTo(bean.getSearchMinUseEndDatetimeTo());
  //发行状态
    condition.setSearchCouponActivityStatus(bean.getSearchCouponActivityStatus());
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<NewCouponRule> result = service.searchNewCouponRuleList(condition,false);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getPrivateCouponList().clear();
    for (NewCouponRule row : result.getRows()) {
    	PrivateCouponListBeanDetail detail = new PrivateCouponListBeanDetail();
 
    	//soukai add 2012/01/13 ob start
    	detail.setCouponCode(row.getCouponCode());
    	//soukai add 2012/01/13 ob end
    	detail.setCouponName(row.getCouponName()); 
    	detail.setCouponCode(row.getCouponCode()); 
    	detail.setCouponIssueType(CampaignType.fromValue(row.getCouponIssueType()).getName()); 
  
    	// soukai update 2012/01/14 ob start
    	//类别是比例的时候显示"%"
			if (CampaignType.PROPORTION.getName().equals(
					detail.getCouponIssueType())) {
//				detail.setCouponAmount(String.valueOf(row.getCouponAmount())
//						+ "%");
				detail.setProportion(true);
				detail.setCouponProportion(String.valueOf(row.getCouponProportion()));
				
			} else {
				detail.setProportion(false);
				detail.setCouponAmount(String.valueOf(row.getCouponAmount()));
			}
		// soukai update 2012/01/14 ob end
			
		detail.setMinUseOrderAmount(String.valueOf(row.getMinUseOrderAmount()));
		detail.setPersonalUseLimit(String.valueOf(row.getPersonalUseLimit()));
		detail.setSiteUseLimit(String.valueOf(row.getSiteUseLimit()));		
    	detail.setCouponType(CouponType.fromValue(row.getCouponType()).getName()); 
    	detail.setMinUseStartDatetime(DateUtil.toDateTimeString(row.getMinUseStartDatetime())); 
    	detail.setMinUseEndDatetime(DateUtil.toDateTimeString(row.getMinUseEndDatetime())); 
    	detail.setMinUseOrderAmount(String.valueOf((row.getMinUseOrderAmount()))); 
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
	PublicCouponListBean bean = getBean();
	
	// 検索条件のvalidationチェック
	isValid &= validateBean(bean);
    
    // 日付の大小関係チェック
    if(isValid){
	    
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
