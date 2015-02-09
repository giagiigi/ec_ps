package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.PrivateCouponListSummary;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.PublicCouponBean;
import jp.co.sint.webshop.web.bean.back.analysis.PublicCouponBean.PrivateCouponListBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System OB.
 */
public class PublicCouponSearchAction extends WebBackAction<PublicCouponBean> {

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
		if (Permission.ANALYSIS_READ_SITE.isGranted(login)) {
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

	  PublicCouponBean bean = getBean();

    condition = getCondition();
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
    SearchResult<PrivateCouponListSummary> result = service.searchNewCouponRuleList_analysis(condition,false);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getPrivateCouponList().clear();
    for (PrivateCouponListSummary row : result.getRows()) {
    	
    	PrivateCouponListBeanDetail detail = new PrivateCouponListBeanDetail();

    	//优惠券规则编号
    	detail.setCouponCode(row.getCouponCode());
    	//优惠券规则名称
    	detail.setCouponName(row.getCouponName());
    	//优惠券类别
		for (CouponType sk : CouponType.values()) {
			if(sk.getValue().equals(String.valueOf(row.getCouponType()))){
				detail.setCouponType(sk.getName());
			}
		}
		
    	//发行件数
    	detail.setWholesaleCount(String.valueOf(row.getIssueTotalCount()));
    	//订单金额
    	detail.setOrderAmount(String.valueOf(row.getOrderTotalPrice()));
    	//订单件数
    	detail.setOrderCount(row.getOrderTotalCount());
    	//订单单价
    	detail.setOrderPrice(String.valueOf(row.getOrderUnitPrice()));
    	//优惠金额
    	detail.setCampaignAmount(String.valueOf(row.getCampaignTotalPrice()));
    	//新订单件数
    	detail.setNewOrderCount(row.getFirstOrderCount());
    	//旧订单件数
    	detail.setOldOrderCount(Long.valueOf(row.getOrderTotalCount()) - Long.valueOf(row.getFirstOrderCount()));
    	//已取消订单金额
    	detail.setAbrogateOrderAmount(String.valueOf(row.getCancelTotalPrice()));
    	//已取消订单件数
    	detail.setAbrogateOrderCount(row.getCancelTotalCount());
    	//已取消订单单价
    	detail.setAbrogateOrderPrice(String.valueOf(row.getCancelUnitPrice()));
    	//已取消优惠金额
    	detail.setAbrogateCampaignAmount(String.valueOf(row.getCancelCampaignPrice()));
    	
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
	PublicCouponBean bean = getBean();
	
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
    return "6107141002";
  }

}
