package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerGroupCampaignBean;

/**
 * U1071010:顾客组别优惠分析
 * 
 * @author ob.cxw
 */
public class CustomerGroupCampaignInitAction extends WebBackAction<CustomerGroupCampaignBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
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

		CustomerGroupCampaignBean bean = new CustomerGroupCampaignBean();
		
		// 优惠种别下拉框选项设定
		List<CodeAttribute> campaignTypeList = new ArrayList<CodeAttribute>();
		
		campaignTypeList.add(new NameValue("请选择", ""));
		for (CampaignType type : CampaignType.values()) {
				campaignTypeList.add(type);
		}
		bean.setCampaignTypeList(campaignTypeList);

		// 取得顾客组别
		UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
		bean.setCustomerGroupList(utilService.getCustomerGroupNames());
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
		return "顾客组别优惠分析一览画面初期化处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "6107101001";
	}

}
