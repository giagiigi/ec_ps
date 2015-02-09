package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.DefaultFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyListBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyListBean.DeliveryCompanyDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1051510:配送公司一览初期表示处理
 * 
 * @author cxw
 */
public class DeliveryCompanyListInitAction extends WebBackAction<DeliveryCompanyListBean> {

	/**
	 * 验证权限
	 * 
	 * @return true
	 */
	@Override
	public boolean authorize() {

	    boolean authorization = false;
	    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
	      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
	    } else {
	      authorization = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo());
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
		return true;
	}

	/**
	 *  处理配送公司信息
	 * 
	 * @return 处理后结果
	 */
	@Override
	public WebActionResult callService() {
		
		// 实现配送公司明细一览
		ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());

		List<DeliveryCompany> deliveryCompanyList = shopService.getDeliveryCompanyList(getLoginInfo().getShopCode());
		List<DeliveryCompanyDetail> detailList = new ArrayList<DeliveryCompanyDetail>();

		for (DeliveryCompany company : deliveryCompanyList) {
			DeliveryCompanyDetail detail = new DeliveryCompanyDetail();
			detail.setDeliveryCompanyNo(company.getDeliveryCompanyNo());
			detail.setDeliveryCompanyName(company.getDeliveryCompanyName());
			detail.setDefaultFlg(company.getDefaultFlg().toString());
			detail.setDeliveryCompanyUrl(company.getDeliveryCompanyUrl());
			detail.setDeliveryDatetimeCommission(company.getDeliveryDatetimeCommission());
			detailList.add(detail);
		}

		DeliveryCompanyListBean companyBean = new DeliveryCompanyListBean();
		companyBean.setDeleveryDetailList(detailList);
		for (DeliveryCompanyDetail detail: detailList) {
		    // 设置默认配送公司
			companyBean.getDeliveryCompanyList().add(new NameValue(detail.getDeliveryCompanyName(),detail.getDeliveryCompanyNo()));
			if (DefaultFlg.DEFAULT.getValue().equals(detail.getDefaultFlg())){
				companyBean.setDefaultCompanyNo(detail.getDeliveryCompanyNo());
			}
		}
		setRequestBean(companyBean);
		setNextUrl(null);

		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * 画面表示按钮必要项初期化设定
	 */
	@Override
	public void prerender() {
		DeliveryCompanyListBean deliveryListBean = (DeliveryCompanyListBean) getRequestBean();
		deliveryListBean.setDisplayRegisterButtonFlg(isDisplayRegisterButton());
		deliveryListBean.setDisplaySelectButtonFlg(isDisplaySelectButton());
		String[] urlParam=getRequestParameter().getPathArgs();
    	if (urlParam != null && urlParam.length >= 1) {
    		if (WebConstantCode.COMPLETE_DELETE.equals(urlParam[0])) {
    			this.addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "配送公司"));
    		} else if (WebConstantCode.COMPLETE_UPDATE.equals(urlParam[0])){
    			this.addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "默认配送公司"));
    		}
    	}
		setRequestBean(deliveryListBean);
	}

	/**
	 * 选择按钮初期化显示
	 */
	private boolean isDisplaySelectButton() {
		BackLoginInfo login = getLoginInfo();
		WebshopConfig config = getConfig();
		if (config.getOperatingMode() == OperatingMode.ONE) {
			return Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login);
		} else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
			return Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login);
		}
		return false;
	}

	/**
	 * 新建按钮初期化显示
	 */
	private boolean isDisplayRegisterButton() {
		BackLoginInfo login = getLoginInfo();
		WebshopConfig config = getConfig();
		if (config.getOperatingMode() == OperatingMode.ONE) {
			return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login);
		} else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
			return Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login);
		}
		return false;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "配送公司一览初期表示处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "4105141001";
	}

}
