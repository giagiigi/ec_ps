package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditUpdateAction extends CommodityEditBaseAction {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityEditBean reqBean = getBean();

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    // SKU情報の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = service.getCommodityInfo(shopCode, reqBean.getCommodityCode());

    if (commodityInfo == null || commodityInfo.getHeader() == null || commodityInfo.getDetail() == null
        || commodityInfo.getStock() == null) {
      setNextUrl("/app/catalog/commodity_list/init/nodata");
      return BackActionResult.RESULT_SUCCESS;
    }

    // DTOに入力値をセット
    setCommodityData(commodityInfo, reqBean);

    // 更新処理
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult sResult = catalogService.updateCommodityInfo(commodityInfo);
    if (sResult.hasError()) {
      setRequestBean(reqBean);
      for (ServiceErrorContent error : sResult.getServiceErrorList()) {
        if (CommonServiceErrorContent.NO_DATA_ERROR.equals(error)) {
          setNextUrl("/app/catalog/commodity_list/init/nodata");
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    // 次画面のBeanを設定する
    setRequestBean(reqBean);

    // 完了パラメータを渡して、初期画面へ遷移する
    setNextUrl("/app/catalog/commodity_edit/select/" + shopCode + "/" + reqBean.getCommodityCode() + "/edit" + "/"
        + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012009";
  }

}
