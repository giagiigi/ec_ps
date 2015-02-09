package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;

import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.NewCampaignEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewCampaignEditInitAction extends
		WebBackAction<NewCampaignEditBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	public boolean authorize() {
		BackLoginInfo login = getLoginInfo();

	    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
	    // 時のみアクセス可能
	    boolean auth = Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login)
	        || (Permission.CAMPAIGN_UPDATE_SITE.isGranted(login) && getConfig().isOne());
	    return auth;
	}

	/**
	 * データモデルに格納された入力値の妥当性を検証します。
	 * 
	 * @return 入力値にエラーがなければtrue
	 */
	public boolean validate() {
		return true;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	public WebActionResult callService() {
		NewCampaignEditBean bean = new NewCampaignEditBean();
		// 活动类型初始化
		List<CodeAttribute> campaignTypeList = new ArrayList<CodeAttribute>();
		campaignTypeList.add(new NameValue("请选择", ""));
		for (CodeAttribute code : CampaignMainType.values()) {
			campaignTypeList
					.add(new NameValue(code.getName(), code.getValue()));
		}
		// 设置默认值
		bean.setCampaignType("");

		// 对象类型初始化
		List<CodeAttribute> campaignConditionTypeList = new ArrayList<CodeAttribute>();
		campaignConditionTypeList.add(new NameValue(
				NewCampaignConditionType.ORDER_COMMODITY.getName(),
				NewCampaignConditionType.ORDER_COMMODITY.getValue()));
		campaignConditionTypeList.add(new NameValue(
				NewCampaignConditionType.ORDER_ADDRESS.getName(),
				NewCampaignConditionType.ORDER_ADDRESS.getValue()));
		
		// 对象商品区分
		List<CodeAttribute> campaignConditionFlgList = new ArrayList<CodeAttribute>();
		campaignConditionFlgList.add(new NameValue(
				CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getName(),
				CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getValue()));
		campaignConditionFlgList.add(new NameValue(
				CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.getName(),
				CampaignConditionFlg.CAMPAIGN_CONDITION_ONLY.getValue()));
		// 设置默认值
		bean.setCampaignConditionFlg(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getValue());

		// 折扣类型
		List<CodeAttribute> discountTypeList = new ArrayList<CodeAttribute>();
		discountTypeList.add(new NameValue(CouponIssueType.PROPORTION.getName(),
		    CouponIssueType.PROPORTION.getValue()));
		discountTypeList.add(new NameValue(CouponIssueType.FIXED.getName(),
		    CouponIssueType.FIXED.getValue()));
		// 设置默认值
		bean.setDiscountType(CouponIssueType.PROPORTION.getValue());

		// 为页面bean设置值
		bean.setCampaignTypeList(campaignTypeList);
		bean.setCampaignConditionTypeList(campaignConditionTypeList);
		bean.setCampaignConditionFlgList(campaignConditionFlgList);
		bean.setDiscountTypeList(discountTypeList);
		bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
		bean.setDisplayLoginButtonFlg(true);
		bean.setDisplayUpdateButtonFlg(false);

		setRequestBean(bean);
		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.002");
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "5106102001";
	}

}
