package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040170:一括関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedListRegisterAction extends WebBackAction<RelatedListBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public WebActionResult callService() {
    RelatedListBean relatedList = getBean();

    // チェックボックスにチェックされたもののリストを取得する
    String[] values = getRequestParameter().getAll("checkBox");

    // pictureModeの種類に応じて、インスタンス化するクラスを変更する
    // タグ:tag_list ギフト:gift_list キャンペーン:campaign_list
    RelatedListBase relatedListBase = RelatedListBase.createNewInstance(relatedList.getPictureMode(), getLoginInfo());

    // 登録処理を実行する
    ServiceResult result = relatedListBase.register(relatedList, values);

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/catalog/related_list/init/register");
    setRequestBean(relatedList);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo()) || Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedListRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104017002";
  }

}
