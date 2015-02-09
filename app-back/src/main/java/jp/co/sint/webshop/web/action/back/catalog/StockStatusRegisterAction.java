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
import jp.co.sint.webshop.web.bean.back.catalog.StockStatusBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1040610:在庫状況設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class StockStatusRegisterAction extends StockStatusBaseAction {

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

    // 画面で入力された値を取得する
    StockStatusBean reqBean = (StockStatusBean) getBean();

    // 登録用DTOを生成する
    StockStatus stockStatus = new StockStatus();

    // 画面から取得した値をDB登録用DTOへセットする
    setStockStatusData(reqBean.getEdit(), stockStatus);

    // 在庫状況設定を登録する
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = catalogService.insertStockStatus(stockStatus);

    // DBエラー有無のチェック
    if (result.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.catalog.StockStatusRegisterAction.0")));
        }
        logger.debug(error.toString());
      }
      return BackActionResult.SERVICE_ERROR;
    }

    // 登録完了パラメータを渡して、初期画面に遷移
    setNextUrl("/app/catalog/stock_status/init" + "/" + WebConstantCode.COMPLETE_INSERT);

    return BackActionResult.RESULT_SUCCESS;

  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockStatusRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104061003";
  }

}
