package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.GiftCardLogBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

/**
 * U1060310:キャンペーン管理のアクションクラスです
 * 
 * @author OB.
 */
public class GiftCardLogInitAction extends WebBackAction<GiftCardLogBean> {

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
	  GiftCardLogBean bean = new GiftCardLogBean();

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
		return "礼品卡使用状况分析一览画面初期化处理";
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
