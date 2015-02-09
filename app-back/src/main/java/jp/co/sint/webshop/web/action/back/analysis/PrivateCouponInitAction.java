package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.PrivateCouponBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author OB.
 */
public class PrivateCouponInitAction extends WebBackAction<PrivateCouponBean> {

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
		
		return Permission.ANALYSIS_READ_SITE.isGranted(login);
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
		PrivateCouponBean bean = new PrivateCouponBean();

		List<CodeAttribute> couponTypes = new ArrayList<CodeAttribute>();
		List<CodeAttribute> couponIssueTypes = new ArrayList<CodeAttribute>();

		// 优惠劵发行类别下拉框选项设定
		couponIssueTypes.add(new NameValue("请选择", ""));
		for (CampaignType sk : CampaignType.values()) {
			couponIssueTypes.add(sk);
		}
		bean.setCouponTypes(couponIssueTypes);

		// 优惠劵类别下拉框选项设定
		couponTypes.add(new NameValue("请选择", ""));
		for (CouponType sk : CouponType.values()) {
			if (!(sk.getValue().equals(CouponType.COMMON_DISTRIBUTION.getValue()))
					&& !(sk.getValue().equals(CouponType.CUSTOMER_GROUP.getValue()))) {
				couponTypes.add(sk);
			}
		}
		
		bean.setCouponIssueTypes(couponTypes);

		// 优惠劵发行状态下拉框选项设定
		List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();
		couponActivityStatusList.add(new NameValue("全部", ""));
		for (ActivityStatus sk : ActivityStatus.values()) {
			couponActivityStatusList.add(sk);
		}
		bean.setCouponActivityStatusList(couponActivityStatusList);

		setRequestBean(bean);
		setBean(bean);
		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * 画面表示に必要な項目を設定・初期化します。
	 */
	@Override
	public void prerender() {
		PrivateCouponBean bean = getBean();
		// 当登录用户有活动登录/更新权限时
		if (Permission.ANALYSIS_DATA_SITE.isGranted(getLoginInfo())) {

			bean.setCSVDisplayFlg(true);
		}else{
			bean.setCSVDisplayFlg(false);
		}
		setRequestBean(bean);

	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "顾客别优惠券发行分析一览画面初期化处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "6107111001";
	}

}
