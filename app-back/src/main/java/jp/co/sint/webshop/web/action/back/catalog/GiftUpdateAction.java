package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040310:ギフトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftUpdateAction extends GiftBaseAction {

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
    return validateBean(getBean().getEdit());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 画面で入力された値を取得
    GiftBean reqBean = getBean();

    // 更新用DTO作成
    Gift gift = new Gift();
    setGiftData(reqBean.getEdit(), gift);
    gift.setUpdatedUser(getLoginInfo().getUserCode());
    gift.setUpdatedDatetime(getBean().getEdit().getUpdateDatetime());

    // 更新サービス呼び出し
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.updateGift(gift);

    // DBエラーの有無をチェック
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.GiftUpdateAction.0")));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      setNextUrl(null);
      return BackActionResult.SERVICE_ERROR;
    }

    // 更新完了パラメータをURLに追加し、initアクションに遷移
    setNextUrl("/app/catalog/gift/init/" + WebConstantCode.COMPLETE_UPDATE);
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.GiftUpdateAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104031007";
  }

}
