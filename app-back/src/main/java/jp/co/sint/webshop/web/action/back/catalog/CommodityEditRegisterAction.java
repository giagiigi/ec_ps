package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Stock;
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
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditRegisterAction extends CommodityEditBaseAction {

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

    // DB登録用DTOを生成する
    CommodityInfo commodityInfo = new CommodityInfo();
    commodityInfo.setHeader(new CommodityHeader());
    commodityInfo.setDetail(new CommodityDetail());
    commodityInfo.setStock(new Stock());

    // DTOに入力値をセット
    setCommodityData(commodityInfo, reqBean);

    // 登録処理
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult sResult = catalogService.insertCommodityInfo(commodityInfo);
    if (sResult.hasError()) {
      setRequestBean(reqBean);
      for (ServiceErrorContent error : sResult.getServiceErrorList()) {
        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.catalog.CommodityEditRegisterAction.0")));
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    // 次画面のBeanを設定する
    setRequestBean(reqBean);

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    // 完了パラメータを渡して、初期画面へ遷移する
    setNextUrl("/app/catalog/commodity_edit/select/" + shopCode + "/"
        + reqBean.getCommodityCode() + "/edit" + "/"
        + WebConstantCode.COMPLETE_INSERT);

    return BackActionResult.RESULT_SUCCESS;

  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012007";
  }

}
