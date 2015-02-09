package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.domain.CampaignType; 
import jp.co.sint.webshop.data.dto.CustomerGroupCampaign;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent; 
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CustomerGroupCampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060520:顾客组别优惠登录のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignEditRegisterAction extends WebBackAction<CustomerGroupCampaignEditBean> {
	
  private Boolean actionFlg = true;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_GROUP_CAMPAIGN_UPDATE_SHOP.isGranted(login) && Permission.CUSTOMER_GROUP_CAMPAIGN_READ_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CustomerGroupCampaignEditBean bean = getBean();
    ValidationSummary summary = BeanValidator.partialValidate(getBean(), "customerGroupCampaignCode", "customerGroupCampaignName",
        "customerGroupCode", "campaignType", "personalUseLimit","customerGroupCampaignNameEn","customerGroupCampaignNameJp");
    // 当优惠种别选择的是比例时，优惠率
    if (CampaignType.PROPORTION.getValue().equals(bean.getCampaignType())) {
      ValidationSummary  summary2= BeanValidator.partialValidate(getBean(), "rate");
      summary.getErrors().addAll(summary2.getErrors());

      if (!StringUtil.isNullOrEmpty(bean.getRate() ) && !summary2.hasError()) {
        if (Double.parseDouble(bean.getRate()) <= 0) {
          addErrorMessage("优惠比例必须大于零");
          return false;
        }
      }
    } else {
      ValidationSummary summary2 = BeanValidator.partialValidate(getBean(), "campaignNumber");
      summary.getErrors().addAll(summary2.getErrors());
      if(summary2.isValid()&& BeanValidator.partialValidate(getBean(), "minOrderAmount").isValid() ){
        if(Double.parseDouble(getBean().getCampaignNumber())>Double.parseDouble(getBean().getMinOrderAmount())){
          addErrorMessage("折扣金额不能超过最小购买金额");
          return false;
        }
      }
    }
    ValidationSummary summary3 = BeanValidator.partialValidate(getBean(), "dateFrom", "dateTo", "minOrderAmount", "remarks");
    summary.getErrors().addAll(summary3.getErrors());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }
    if (!StringUtil.isCorrectRange(bean.getDateFrom(), bean.getDateTo())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      return false;
    }
    return true;
  }
  
  /**
   * 画面から入力された値をキャンペーンのDTOに設定します
   * 
   * @param campaign
   * @param bean
   */
  private void setCampaign(CustomerGroupCampaign customerGroupCampaign, CustomerGroupCampaignEditBean bean) {
  customerGroupCampaign.setShopCode(getLoginInfo().getShopCode());
	 //edit by cs_yuli 20120521 start  	
	customerGroupCampaign.setCampaignName(bean.getCustomerGroupCampaignName()); 
	customerGroupCampaign.setCampaignNameEn(bean.getCustomerGroupCampaignNameEn()); 
	customerGroupCampaign.setCampaignNameJp(bean.getCustomerGroupCampaignNameJp()); 
    //edit by cs_yuli 20120521 end   
	
	customerGroupCampaign.setCampaignCode(bean.getCustomerGroupCampaignCode());
	customerGroupCampaign.setCampaignType(NumUtil.toLong(bean.getCampaignType()));
	if (StringUtil.hasValue(bean.getCustomerGroupCode())) {
		customerGroupCampaign.setCustomerGroupCode(bean.getCustomerGroupCode());
	} else {
		customerGroupCampaign.setCustomerGroupCode(null);
	}
	customerGroupCampaign.setCampaignEndDatetime(DateUtil.fromString(bean.getDateTo(), true));
	customerGroupCampaign.setCampaignEndDatetime(DateUtil.setSecond(customerGroupCampaign.getCampaignEndDatetime(), 59));
	customerGroupCampaign.setCampaignStartDatetime(DateUtil.fromString(bean.getDateFrom(), true));
	customerGroupCampaign.setCampaignProportion(NumUtil.toLong(bean.getRate()));
	customerGroupCampaign.setCampaignAmount(NumUtil.parse(bean.getCampaignNumber()));
	customerGroupCampaign.setMinOrderAmount(NumUtil.parse(bean.getMinOrderAmount()));
	customerGroupCampaign.setMemo(bean.getRemarks());
	// 20140311 hdh add start
	customerGroupCampaign.setPersonalUseLimit(NumUtil.parse(bean.getPersonalUseLimit()));
	// 20140311 hdh add end
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	CustomerGroupCampaignEditBean bean = getBean();
	
	CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
	
	CommunicationService communicationManagementService = ServiceLocator.getCommunicationService(getLoginInfo());
	ServiceResult result = null;
	
	if (bean.getDisplayMode().equals(WebConstantCode.DISPLAY_READONLY)) {
	  actionFlg = false;
      CustomerGroupCampaign customerGroupCampaign  = cs.getCustomerGroupCampaign(bean.getCustomerGroupCampaignCode());
      if (customerGroupCampaign == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,"顾客组别优惠"));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      
      setCampaign(customerGroupCampaign, bean);
      customerGroupCampaign.setUpdatedDatetime(bean.getUpdateDatetime());
      result = communicationManagementService.updateCustomerGroupCampaign(customerGroupCampaign);
      if (result.hasError()) {
	    setRequestBean(bean);
	    for (ServiceErrorContent error : result.getServiceErrorList()) {
		  // 重复性错误报告
		  if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
			addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,"顾客组别优惠"));
			return BackActionResult.RESULT_SUCCESS;
		  }
		  // 顾客组别不存在错误报告
		  if (error.equals(CustomerServiceErrorContent.CUSTOMERGROUP_DELETED_ERROR)) {
			addErrorMessage(WebMessage.get(CommunicationErrorMessage.CUSTOMER_GROUP_CAMPAIGN_NO_DATA_ERROR));
			return BackActionResult.RESULT_SUCCESS;
		  }
		  // 活动期间重复报告
		  if (error.equals(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR)) {
		    addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "优惠期间"));
			return BackActionResult.RESULT_SUCCESS;
		  }
		  if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
			return BackActionResult.SERVICE_VALIDATION_ERROR;
		  }
	    }
	    return BackActionResult.SERVICE_ERROR;
	  } else {
		  addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "顾客组别优惠"));
	  }
	} else {
	  actionFlg = true;
	  if (cs.getCustomerGroupCampaign(bean.getCustomerGroupCampaignCode()) != null) {
		addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "优惠活动编号"));
		this.setRequestBean(bean);
		
		return BackActionResult.RESULT_SUCCESS;
	  }
	  CustomerGroupCampaign customerGroupCampaign = new CustomerGroupCampaign();
	  setCampaign(customerGroupCampaign, bean);
	  result = communicationManagementService.insertCustomerGroupCampaign(customerGroupCampaign);
	  if (result.hasError()) {
	    setRequestBean(bean);
	    for (ServiceErrorContent error : result.getServiceErrorList()) {
		  // 重复性错误报告
		  if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
			addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "优惠活动编号"));
			return BackActionResult.RESULT_SUCCESS;
		  }
		  // 顾客组别不存在错误报告
		  if (error.equals(CustomerServiceErrorContent.CUSTOMERGROUP_DELETED_ERROR)) {
			addErrorMessage(WebMessage.get(CommunicationErrorMessage.CUSTOMER_GROUP_CAMPAIGN_NO_DATA_ERROR));
			return BackActionResult.RESULT_SUCCESS;
		  }
		  // 活动期间重复报告
		  if (error.equals(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR)) {
		    addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "优惠期间"));
			return BackActionResult.RESULT_SUCCESS;
		  }
		  if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
			return BackActionResult.SERVICE_VALIDATION_ERROR;
		  }
	    }
	    return BackActionResult.SERVICE_ERROR;
	  } else {
		  bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
		  addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "顾客组别优惠"));
	  }
	}
	CustomerGroupCampaign campaign = cs.getCustomerGroupCampaign(bean.getCustomerGroupCampaignCode());
	if (campaign != null) {
	  bean.setUpdateDatetime(campaign.getUpdatedDatetime());
	}
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
	if (!actionFlg) {
	  return "顾客组别优惠更新处理";
	} else {
	  return "顾客组别优惠登录处理";
	}
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
	if (!actionFlg) {
      return "5106052002";
	} else {
	  return "5106052003";
	}
  }

}
