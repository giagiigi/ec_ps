package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040160:関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedDeleteAction extends RelatedBaseAction {

  /** 双方向 */
  public static final String INTERACTIVE_DELETE_FLG = "interactive";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    boolean auth = false;
    if (getConfig().isOne()) {
      auth = Permission.CAMPAIGN_DELETE_SITE.isGranted(login);
    } else {
      auth = Permission.CAMPAIGN_DELETE_SHOP.isGranted(login);
    }
    auth |= Permission.COMMODITY_DELETE.isGranted(login);

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    boolean isValid = false;

    RelatedBean reqBean = (RelatedBean) getBean();
    isValid = validateItems(reqBean, "effectualCode", "pictureMode", "searchShopCode");

    // チェックボックスが選択されているかをチェック
    if (!StringUtil.hasValueAllOf(ArrayUtil.toArray(reqBean.getCheckedCodeList(), String.class))) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.catalog.RelatedDeleteAction.0")));
      isValid = false;
    }

    return isValid;
  }

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

    String[] param = getRequestParameter().getPathArgs();

    if (param.length > 0 && param[0].equals(INTERACTIVE_DELETE_FLG)) {
      reqBean.setInteractiveDeleteFlg(true);
    } else {
      reqBean.setInteractiveDeleteFlg(false);
    }

    /*
     * URLパラメータで取得した関連付けの種類に応じて、インスタンス化するクラスを変更
     */
    RelatedBase related = RelatedBase.createNewInstance(reqBean.getPictureMode(), getLoginInfo());
    String[] values = ArrayUtil.toArray(reqBean.getCheckedCodeList(), String.class);
    ServiceResult result = related.delete(reqBean, values);

    if (result != null && result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.RelatedDeleteAction.1")));
        } else if (errorContent.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/related/complete/delete");
    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104016008";
  }

}
