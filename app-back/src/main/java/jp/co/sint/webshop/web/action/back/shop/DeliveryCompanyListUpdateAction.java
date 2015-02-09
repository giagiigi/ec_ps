package jp.co.sint.webshop.web.action.back.shop;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyListBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051410:配送公司信息删除
 * 
 * @author cxw
 */
public class DeliveryCompanyListUpdateAction extends WebBackAction<DeliveryCompanyListBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		
	    boolean authorization = false;
	    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
	      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
	    } else {
	      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
	    }
	    return authorization;
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	@Override
	public boolean validate() {
		if (StringUtil.isNullOrEmpty(getBean().getDefaultCompanyNo())) {
			throw new URLNotFoundException(); 
		}
		return true;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {
		DeliveryCompanyListBean bean = getBean();

		// 默认配送公司设定处理
	    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

	    ServiceResult result = service.setDefaultDeliveryCompany(bean.getDefaultCompanyNo());

	    if (result.hasError()) {
	    	 for (ServiceErrorContent error : result.getServiceErrorList()) {
	    	        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
	    	        	addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "配送公司"));
	    	        	setRequestBean(bean);
	    	        	return BackActionResult.RESULT_SUCCESS;
	    	        } else {
	    	        	  Logger logger = Logger.getLogger(this.getClass());
	    	    	      logger.warn(result.toString());
	    	    	      return BackActionResult.SERVICE_ERROR;
	    	        }
	    	 }
	    
	    } else {
	      setRequestBean(bean);
	      setNextUrl("/app/shop/delivery_company_list/init/" + WebConstantCode.COMPLETE_UPDATE);
	    }

	    return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "配送公司一览默认配送公司设定处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "4105141003";
	}

}
