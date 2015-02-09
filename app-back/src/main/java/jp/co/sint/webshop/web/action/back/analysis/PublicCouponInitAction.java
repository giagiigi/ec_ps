package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.PublicCouponBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author OB.
 */
public class PublicCouponInitAction extends WebBackAction<PublicCouponBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	public boolean authorize() {

		BackLoginInfo login = getLoginInfo();
		if (null == login) {
			return false;
		}
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
		
		PublicCouponBean bean = createBean();

		setRequestBean(bean);
        setBean(bean);
		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * 初始化画面Bean
	 * 
	 * @return 画面Bean
	 */
	private PublicCouponBean createBean() {
		
		PublicCouponBean bean = new PublicCouponBean();
		List<CodeAttribute> couponIssueTypes = new ArrayList<CodeAttribute>();

		// 优惠劵类别下拉框选项设定
		couponIssueTypes.add(new NameValue("请选择", ""));
		for (CampaignType sk : CampaignType.values()) {
			couponIssueTypes.add(sk);
		}
		bean.setCouponTypes(couponIssueTypes);
		
		//活动状态
		List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();
		couponActivityStatusList.add(new NameValue("全部", ""));
		
		for (ActivityStatus sk : ActivityStatus.values()) {
			couponActivityStatusList.add(sk);
		}
		bean.setCouponActivityStatusList(couponActivityStatusList);
		return bean;

	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "公共优惠券发行规则一览画面初期化处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "6107141001";
	}

}
