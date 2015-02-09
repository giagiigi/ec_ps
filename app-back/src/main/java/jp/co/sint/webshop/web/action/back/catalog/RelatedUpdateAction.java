package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1040160:関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedUpdateAction extends RelatedBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
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

    Logger logger = Logger.getLogger(this.getClass());

    RelatedBean reqBean = (RelatedBean) getBean();

    // ショップ管理者の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // URLパラメータで取得した関連付けの種類に応じて、インスタンス化するクラスを変更する
    RelatedBase related = RelatedBase.createNewInstance(reqBean.getPictureMode(), getLoginInfo());

    related.setEffectualCode(reqBean.getEffectualCode());

    // URLパラメータから取得したコードの存在チェックを実行する
    String[] checkValues = getRequestParameter().getAll("checkBox");
    String[] displayValues = getRequestParameter().getAll("displayOrderList");

    for (int i = 0; i < checkValues.length; i++) {
      if (related.isNotExist(reqBean)) {
        addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.catalog.RelatedUpdateAction.0")));
        logger.debug(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    
    // リスト数をチェックし、関連付けが0なら警告
    if (reqBean.getList().isEmpty()) {
      addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.RelatedUpdateAction.0")));
      logger.debug(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;      
    }

    // 更新処理を実行する
    for (int i = 0; i < displayValues.length; i++) {
      reqBean.getList().get(i).setDisplayOrder(displayValues[i]);
    }

    ServiceResult result = related.update(reqBean, checkValues);
    if (result != null && result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.RelatedUpdateAction.1")));
        } else if (errorContent.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/related/complete/update");

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
    return Messages.getString("web.action.back.catalog.RelatedUpdateAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104016006";
  }

}
