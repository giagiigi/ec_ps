package jp.co.sint.webshop.web.action.back.catalog;
 
import jp.co.sint.webshop.service.Permission; 
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandListBackAction extends BrandListBaseAction {
 

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		boolean authorization = true; 
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
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {

		BrandListBean reqBean = getBean();
		// ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
		if (getLoginInfo().isShop() || getConfig().isOne()) {
			reqBean.setSearchShopCode(getLoginInfo().getShopCode());
		} 
		reqBean.setMode(WebConstantCode.DISPLAY_READONLY); 
		setRequestBean(reqBean); 
		reqBean.setSearchResultTableDisplayFlg(false);
		reqBean.setSearchTableDisplayFlg(false);
		reqBean.setUpdateButtonDisplayFlg(false);
		reqBean.setRegisterButtonDisplayFlg(false);
		reqBean.setRegisterTableDisplayFlg(true);

		if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
			reqBean.setDisplayNextButton(true);
			reqBean.setBrandEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
		} else {
			reqBean.setMode(WebConstantCode.DISPLAY_HIDDEN);
			reqBean.setBrandEditDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
			reqBean.setDisplayNextButton(false);
		} 
		return BackActionResult.RESULT_SUCCESS;
	}

	 

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return Messages
				.getString("web.action.back.catalog.BrandListBackAction.1");
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "3104012001";
	}

}
