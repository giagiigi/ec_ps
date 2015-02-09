package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.PrivateCouponListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * U1060610:PRIVATEクーポン管理のアクションクラスです
 * 
 * @author OB.
 */
public class PrivateCouponListInitAction extends WebBackAction<PrivateCouponListBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		return Permission.PRIVATE_COUPON_READ_SHOP.isGranted(getLoginInfo());
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
		PrivateCouponListBean bean = createBean();

		setRequestBean(bean);

		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * 初始化画面Bean
	 * 
	 * @return 画面Bean
	 */
	private PrivateCouponListBean createBean() {
		PrivateCouponListBean bean = new PrivateCouponListBean();
		List<CodeAttribute> couponTypes = new ArrayList<CodeAttribute>();
		List<CodeAttribute> campaignTypes = new ArrayList<CodeAttribute>();
		List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();
		BackLoginInfo login = getLoginInfo();

		// 优惠劵类别下拉框选项设定
		couponTypes.add(new NameValue("请选择", ""));
		for (CouponType sk : CouponType.values()) {
			if (!sk.getValue().equals(CouponType.COMMON_DISTRIBUTION.getValue()) 
					&& !sk.getValue().equals(CouponType.CUSTOMER_GROUP.getValue())) {
				couponTypes.add(sk);
			}
		}
		bean.setCouponTypes(couponTypes);

		// 优惠劵发行类别下拉框选项设定
		campaignTypes.add(new NameValue("请选择", ""));
		for (CampaignType sk : CampaignType.values()) {
			campaignTypes.add(sk);
		}
		bean.setCampaignTypes(campaignTypes);
		
		// 优惠劵发行状态下拉框选项设定
		couponActivityStatusList.add(new NameValue("全部", ""));
		for (ActivityStatus sk : ActivityStatus.values()) {
			couponActivityStatusList.add(sk);
		}
		bean.setCouponActivityStatusList(couponActivityStatusList);
		
		// 当登录用户有活动登录/更新权限时
		if (Permission.PRIVATE_COUPON_UPDATE_SHOP.isGranted(login)) {

			bean.setRegisterNewDisplayFlg(true);
			setRequestBean(bean);
		}

		// 当登录用户有活动删除权限时
		if (Permission.PRIVATE_COUPON_DELETE_SHOP.isGranted(login)) {

			bean.setDeleteButtonDisplayFlg(true);
			setRequestBean(bean);
		}

		return bean;

	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return "顾客别优惠券发行规则一览处理";
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "5106061001";
	}

}
