package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.data.SkuImage;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean.CCommoditySkuDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommoditySkuDeleteAction extends TmallCommoditySkuBaseAction {

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

    String updateSkuCode = this.getDeleteSkuCode();

    if (StringUtil.isNullOrEmpty(updateSkuCode)) {
      // エラーメッセージを追加
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      return false;
    }

    // リストから、URLパラメータで渡されたskuCodeに該当する
    // レコードを探し、見つかればvalidate
    for (CCommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(getDeleteSkuCode())) {
        return validateBean(detail);
      }
    }

    // ここに来るのはリストに該当レコードがなかった場合なので
    // その旨のエラーメッセージを追加
    addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TmallCommoditySkuBean reqBean = getBean();
    String skuCode = this.getDeleteSkuCode();

    // 削除用DTOを作成
    CCommoditySkuDetailBean deleteSku = new CCommoditySkuDetailBean();
    for (CCommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(skuCode)) {
        deleteSku = detail;
        break;
      }
    }

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    DataIOService dataIOService = ServiceLocator.getDataIOService(getLoginInfo());

    ServiceResult result = service.deleteCCommoditySku(deleteSku.getShopCode(), deleteSku.getSkuCode());

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.catalog.CommoditySkuDeleteAction.0")));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.DELETE_SKU_ERROR, deleteSku.getSkuCode()));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    } else {
      // 商品SKU画像削除
      SkuImage info = new SkuImage(deleteSku.getShopCode(), deleteSku.getSkuCode());
      ServiceResult ioResult = dataIOService.deleteImage(info);
      if (ioResult.hasError()) {
        for (ServiceErrorContent error : ioResult.getServiceErrorList()) {
          if (error == DataIOServiceErrorContent.FILE_DELETE_ERROR) {
            addWarningMessage(WebMessage.get(DataIOErrorMessage.FILE_DELETE_FAILED, Messages
                .getString("web.action.back.catalog.CommoditySkuDeleteAction.1")
                + deleteSku.getSkuCode()));
          }
        }
      }

    }

    setNextUrl("/app/catalog/tmall_commodity_sku/init/" + getBean().getParentCommodityCode() + "/" + getBean().getShopCode() + "/"
        + WebConstantCode.COMPLETE_DELETE);

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private String getDeleteSkuCode() {

    String[] params = getRequestParameter().getPathArgs();
    String skuCode = "";
    if (params.length > 0) {
      skuCode = params[0];
    }

    return skuCode;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014001";
  }

}
