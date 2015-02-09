package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean.StockStatusDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040610:在庫状況設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class StockStatusDeleteAction extends StockStatusBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    // URLパラメータの取得
    // parameter[0]:在庫状況番号
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    String stockStatusNo = getRequestParameter().getPathArgs()[0];

    for (StockStatusDetailBean detail : getBean().getList()) {
      if (detail.getStockStatusNo().equals(stockStatusNo)) {
        return true;
      }
    }
    addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
        Messages.getString("web.action.back.catalog.StockStatusDeleteAction.0")));

    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // URLパラメータの取得
    // parameter[0]:在庫状況番号
    String stockStatusNo = getRequestParameter().getPathArgs()[0];

    // DB削除用DTOを生成する
    StockStatus stockStatus = new StockStatus();
    for (StockStatusDetailBean detail : getBean().getList()) {
      if (detail.getStockStatusNo().equals(stockStatusNo)) {
        // リクエストから取得した値をDB削除用DTOへセットする
        setStockStatusData(detail, stockStatus);
        stockStatus.setUpdatedUser(getLoginInfo().getUserCode());
        stockStatus.setUpdatedDatetime(detail.getUpdatedDatetime());
        break;
      }
    }

    // 商品系サービスを呼び出す
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    // 在庫状況設定を削除する
    ServiceResult result = catalogService.deleteStockStatus(stockStatus.getShopCode(), stockStatus.getStockStatusNo());

    // DBエラー有無のチェック
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.StockStatusDeleteAction.0")));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    // 削除完了パラメータを渡して、初期画面に遷移
    setNextUrl("/app/catalog/stock_status/init" + "/" + WebConstantCode.COMPLETE_DELETE); //$NON-NLS-2$

    return BackActionResult.RESULT_SUCCESS;

  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockStatusDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104061001";
  }

}
