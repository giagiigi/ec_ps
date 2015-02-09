package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.OptionalCampaignListSearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignListBean;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignListBean.OptionalCampaignListBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author System OB.
 */
public class OptionalCampaignListSearchAction extends WebBackAction<OptionalCampaignListBean> {

  private OptionalCampaignListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new OptionalCampaignListSearchCondition();
  }

  protected OptionalCampaignListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected OptionalCampaignListSearchCondition getSearchCondition() {
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
    return Permission.OPTIONAL_CAMPAGIN_READ_SHOP.isGranted(login);
		
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    OptionalCampaignListBean bean = getBean();

    condition = getCondition();
    //soukai 2012/01/13 ob start
    condition.setSearchCouponCode(bean.getSearchCouponCode());
    //soukai 2012/01/13 ob end
//    condition.setSearchCampaignType(bean.getSearchCouponType());
    condition.setSearchCouponName(bean.getSearchCouponName());
//    condition.setSearchCouponType(CouponType.COMMON_DISTRIBUTION.getValue());
    condition.setSearchMinUseStartDatetimeFrom(bean.getSearchMinUseStartDatetimeFrom());
    condition.setSearchMinUseStartDatetimeTo(bean.getSearchMinUseStartDatetimeTo());
    condition.setSearchMinUseEndDatetimeFrom(bean.getSearchMinUseEndDatetimeFrom());
    condition.setSearchMinUseEndDatetimeTo(bean.getSearchMinUseEndDatetimeTo());
  //发行状态
    condition.setSearchCouponActivityStatus(bean.getSearchCouponActivityStatus());
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    SearchResult<OptionalCampaign> result = service.searchOptionalCampaignList(condition);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getOptionalCampaingList().clear();
    for (OptionalCampaign row : result.getRows()) {
      OptionalCampaignListBeanDetail detail = new OptionalCampaignListBeanDetail();
 
    	detail.setCampaignCode(row.getCampaignCode());
    	detail.setCampaignName(row.getCampaignName()); 
    	detail.setOptionalPrice(NumUtil.toString(row.getOptionalPrice()));
    	detail.setOptionalNum(NumUtil.toString(row.getOptionalNum()));
    	detail.setOrderLimitNum(NumUtil.toString(row.getOrderLimitNum()));
			
    	bean.getOptionalCampaingList().add(detail);
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
	OptionalCampaignListBean bean = getBean();
	
	// 検索条件のvalidationチェック
	isValid &= validateBean(bean);
    
    // 日付の大小関係チェック
    if(isValid){
	    
	    //优惠券利用开始日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinUseStartDatetimeFrom(), bean.getSearchMinUseStartDatetimeTo())) {
	        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinUseStartDatetimeFrom(), bean.getSearchMinUseStartDatetimeTo())) {
	          isValid &= false;
	          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "任意活动开始日时"));
	        }
	      }
	    
	    //优惠券利用结束日时
	    if (StringUtil.hasValueAllOf(bean.getSearchMinUseEndDatetimeFrom(), bean.getSearchMinUseEndDatetimeTo())) {
	        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinUseEndDatetimeFrom(), bean.getSearchMinUseEndDatetimeTo())) {
	          isValid &= false;
	          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "任意活动结束日时"));
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
    return "任意活动一览画面检索处理";
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
