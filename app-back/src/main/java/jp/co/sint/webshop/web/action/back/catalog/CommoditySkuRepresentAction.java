package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean.CommoditySkuDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommoditySkuRepresentAction extends WebBackAction<CommoditySkuBean> {

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

    String updateSkuCode = this.getUpdateSkuCode();

    if (StringUtil.isNullOrEmpty(updateSkuCode)) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      return false;
    }

    // リストから、URLパラメータで渡されたskuCodeに該当する
    // レコードを探し、見つかればvalidate
    for (CommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(getUpdateSkuCode())) {
        return validateBean(detail);
      }
    }

    // ここに来るのはリストに該当レコードがなかった場合なので、その旨のエラーメッセージを追加
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

    String updateSkuCode = this.getUpdateSkuCode();

    // 代表SKUの更新
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader header = service.getCommodityHeader(getLoginInfo().getShopCode(), getBean().getParentCommodityCode());
    if (header == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.CommoditySkuRepresentAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新用DTOに値をセット
    header.setRepresentSkuCode(updateSkuCode);
    header.setRepresentSkuUnitPrice(getUpdateUnitPrice(updateSkuCode));
    ServiceResult result = service.updateCommodityHeader(header);

    // サービスエラーの有無をチェック
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, 
              Messages.getString("web.action.back.catalog.CommoditySkuRepresentAction.0")));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    // 代表設定の完了パラメータを渡し、initアクションに遷移
    setNextUrl("/app/catalog/commodity_sku/init/" + getBean().getShopCode() + "/" + getBean().getParentCommodityCode() + "/update");

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  private BigDecimal getUpdateUnitPrice(String updateSkuCode) {
    BigDecimal unitPrice = BigDecimal.ZERO;

    for (CommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(updateSkuCode)) {
        unitPrice = NumUtil.parse(detail.getUnitPrice());
      }
    }

    return unitPrice;
  }

  private String getUpdateSkuCode() {

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
    return Messages.getString("web.action.back.catalog.CommoditySkuRepresentAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014006";
  }

}
