package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040160:関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedRegisterAction extends RelatedBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    RelatedBean reqBean = (RelatedBean) getBean();

    // ショップ管理者の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    if (StringUtil.isNullOrEmpty(reqBean.getSearchShopCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.RelatedRegisterAction.0")));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // URLパラメータで取得した関連付けの種類に応じて、インスタンス化するクラスを変更する
    RelatedBase related = RelatedBase.createNewInstance(reqBean.getPictureMode(), getLoginInfo());

    ServiceResult result = related.register(reqBean);
    if (result != null && result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_DEFAULT_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.RelatedRegisterAction.1")));
        } else if (errorContent.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/related/complete/insert");
    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    BackLoginInfo login = getLoginInfo();
    // 一店舗版のときはキャンペーン更新_サイト、それ以外のときはキャンペーン更新_ショップの権限が必要
    if (getConfig().isOne()) {
      auth = Permission.CAMPAIGN_UPDATE_SITE.isGranted(login);
    } else {
      auth = Permission.CAMPAIGN_UPDATE_SHOP.isGranted(login);
    }
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      auth = true;
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    RelatedBean reqBean = (RelatedBean) getBean();
    if (reqBean.getEdit().getSortNum()!=null && NumUtil.toLong(reqBean.getEdit().getSortNum()) < 0L){
      addErrorMessage("商品显示顺序请输入大于0的数字！");
      return false;
    }
    return validateBean(reqBean.getEdit());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedRegisterAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104016004";
  }

}
