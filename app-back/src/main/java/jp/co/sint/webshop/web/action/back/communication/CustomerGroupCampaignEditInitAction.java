package jp.co.sint.webshop.web.action.back.communication;


import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CustomerGroupCampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060520:顾客组别优惠登录のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignEditInitAction extends WebBackAction<CustomerGroupCampaignEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    String[] args = getRequestParameter().getPathArgs();
    if(args.length == 0 && Permission.CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP.isDenied(login)) {
      return false;
    }
    return Permission.CUSTOMER_GROUP_CAMPAIGN_READ_SHOP.isGranted(login);
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
    String[] args = getRequestParameter().getPathArgs();
	  
    // 処理完了メッセージ存在時
    if (args.length == 1) {
      if (getRequestParameter().getPathArgs()[0].equals("new_register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客组别优惠"));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      } else if (getRequestParameter().getPathArgs()[0].equals("update_register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "顾客组别优惠"));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    
	CustomerGroupCampaignEditBean bean = new CustomerGroupCampaignEditBean();
	
	if (args.length == 1) {
	  CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
	  CustomerGroupCampaign campaign = service.getCustomerGroupCampaign(args[0]);
      
      if (campaign != null) {
        bean.setCustomerGroupCampaignCode(campaign.getCampaignCode());
        bean.setCustomerGroupCampaignName(campaign.getCampaignName());
        //add by cs_yuli 20120521 start 
        bean.setCustomerGroupCampaignNameEn(campaign.getCampaignNameEn());
        bean.setCustomerGroupCampaignNameJp(campaign.getCampaignNameJp());
        //add by cs_yuli 20120521 end 
        bean.setRate(Long.toString(campaign.getCampaignProportion()));
        bean.setCampaignNumber(NumUtil.toString(campaign.getCampaignAmount()));
        bean.setDateFrom(DateUtil.toDateTimeString(campaign.getCampaignStartDatetime()));
        bean.setDateTo(DateUtil.toDateTimeString(campaign.getCampaignEndDatetime()));
        bean.setCampaignType(NumUtil.toString(campaign.getCampaignType()));
        bean.setMinOrderAmount(NumUtil.toString(campaign.getMinOrderAmount()));
        bean.setUpdateDatetime(campaign.getUpdatedDatetime());
        bean.setCustomerGroupCode(campaign.getCustomerGroupCode());
        bean.setRemarks(campaign.getMemo());
        // 20140311 hdh add start
        bean.setPersonalUseLimit(NumUtil.toString(campaign.getPersonalUseLimit()));
        // 20140311 hdh add end
        bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
      } else {
        bean = new CustomerGroupCampaignEditBean();
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "顾客组别优惠"));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
	} else if(args.length == 0)  {
	  bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
	  bean.setCampaignType(CampaignType.PROPORTION.getValue());
	}
	 if (Permission.CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP.isDenied(getLoginInfo())) {
       bean.setMode(WebConstantCode.DISPLAY_READONLY);
       bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
       bean.setUpdateAuthorizeFlg(false);
	 } else {
	   bean.setMode(WebConstantCode.DISPLAY_EDIT);
	   bean.setUpdateAuthorizeFlg(true);
	 }
	  
	// 顧客グループリストの取得
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCustomerGroupList(s.getCustomerGroupNames());
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客组别优惠登录初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106052001";
  }

}
