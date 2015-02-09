package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.TmallBrand;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandListConfirmAction extends BrandListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  private boolean validationResult = false;

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    validationResult = super.validate();
    BrandListBean reqBean = getBean();
    if (reqBean.isSearchTableDisplayFlg() == false) {
      validationResult = validateBean(reqBean.getEdit());
    } else {
      validationResult = true;
    }
    return validationResult;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    BrandListBean reqBean = getBean();

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    TmallBrand tmallBrand = catalogService.getTmallBrand(reqBean.getEdit().getTmallBrandCode());
    if (tmallBrand == null) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.TMALL_BRAND_ERROR));
      validationResult &= false;
    } else {
      reqBean.getEdit().setTmallBrandName(tmallBrand.getTmallBrandName());
    }

    setRequestBean(reqBean);

    if (validationResult) { // エラーがひとつも存在しなければ確認メッセージを表示する
      addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));
    }

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    BrandListBean reqBean = (BrandListBean) getRequestBean();

    if (validationResult) {
      if (reqBean.getMode().equals(WebConstantCode.DISPLAY_READONLY)) {
        reqBean.setRegisterButtonDisplayFlg(false);
        reqBean.setUpdateButtonDisplayFlg(true);
      } else {
        reqBean.setRegisterButtonDisplayFlg(true);
        reqBean.setUpdateButtonDisplayFlg(false);
      }
      reqBean.setMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setBrandEditDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setSearchResultTableDisplayFlg(false);
      reqBean.setSearchTableDisplayFlg(false);
      reqBean.setRegisterTableDisplayFlg(true);
      reqBean.setDisplayNextButton(false);
    } else {
      reqBean = getBean();
    }

    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.BrandListRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "0320100106";
  }

}
