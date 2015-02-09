package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean.CommoditySkuDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommoditySkuUpdateAction extends CommoditySkuBaseAction {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    getBean().setCommodityStandardNameValue(getBean().getEscapeCommodityStandardNameValue());
    getBean().setCommodityStandard1Name(getBean().getEscapeCommodityStandard1Name());
    getBean().setCommodityStandard2Name(getBean().getEscapeCommodityStandard2Name());
  }

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

    boolean isValid = true;

    String updateSkuCode = this.getUpdateSkuCode();

    if (StringUtil.isNullOrEmpty(updateSkuCode)) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      return false;
    }

    CommoditySkuDetailBean sku = null;
    for (CommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(getUpdateSkuCode())) {
        sku = detail;
      }
    }

    if (sku == null) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      return false;
    }

    // 各価格と適用期間の相互チェック
    isValid = validatePeriod(sku);

    CommoditySkuDetailBean updateSku = null;
    for (CommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(this.getUpdateSkuCode())) {
        updateSku = detail;
      }
    }
    if (getBean().getEscapeCommodityStandardNameValue().equals("0")) {
      if (StringUtil.hasValue(updateSku.getStandardDetail1Name()) || StringUtil.hasValue(updateSku.getStandardDetail2Name())) {
        addErrorMessage(MessageFormat.format(
            Messages.getString("web.action.back.catalog.CommoditySkuUpdateAction.0"), this.getUpdateSkuCode())
            + WebMessage.get(CatalogErrorMessage.STANDARD_NAMES_SET_ERROR, "規格詳細"));
        isValid = false;
      }
    } else if (getBean().getEscapeCommodityStandardNameValue().equals("1")) {
      if (StringUtil.isNullOrEmpty(updateSku.getStandardDetail1Name())) {
        addErrorMessage(WebMessage
            .get(
                ValidationMessage.REQUIRED_ERROR,
                MessageFormat
                    .format(
                        Messages.getString("web.action.back.catalog.CommoditySkuUpdateAction.1"),
                        this.getUpdateSkuCode(), getBean().getCommodityStandard1Name())));
        isValid = false;
      }
      if (StringUtil.hasValue(updateSku.getStandardDetail2Name())) {
        addErrorMessage(MessageFormat.format(
            Messages.getString("web.action.back.catalog.CommoditySkuUpdateAction.0"), this.getUpdateSkuCode())
            + WebMessage.get(CatalogErrorMessage.STANDARD_NAME2_SET_ERROR, Messages.getString("web.action.back.catalog.CommoditySkuUpdateAction.3")));
        isValid = false;
      }
    } else if (getBean().getEscapeCommodityStandardNameValue().equals("2")) {
      if (StringUtil.isNullOrEmpty(updateSku.getStandardDetail1Name())) {
        addErrorMessage(WebMessage
            .get(
                ValidationMessage.REQUIRED_ERROR,
                MessageFormat
                    .format(
                        Messages.getString("web.action.back.catalog.CommoditySkuUpdateAction.1"),
                        this.getUpdateSkuCode(), getBean().getCommodityStandard1Name())));
        isValid = false;
      }
      if (StringUtil.isNullOrEmpty(updateSku.getStandardDetail2Name())) {
        addErrorMessage(WebMessage
            .get(
                ValidationMessage.REQUIRED_ERROR,
                MessageFormat
                    .format(
                        Messages.getString("web.action.back.catalog.CommoditySkuUpdateAction.1"),
                        this.getUpdateSkuCode(), getBean().getCommodityStandard2Name())));
        isValid = false;
      }
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

    CommoditySkuDetailBean updateSku = null;
    for (CommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(this.getUpdateSkuCode())) {
        updateSku = detail;
      }
    }

    if (updateSku == null) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新用DTOの作成
    CommodityDetail sku = new CommodityDetail();
    sku.setShopCode(getLoginInfo().getShopCode());
    sku.setSkuCode(updateSku.getSkuCode());
    sku.setCommodityCode(getBean().getParentCommodityCode());
    sku.setUnitPrice(NumUtil.parse(updateSku.getUnitPrice()));
    sku.setDiscountPrice(NumUtil.parse(updateSku.getDiscountPrice(), null));
    sku.setReservationPrice(NumUtil.parse(updateSku.getReservationPrice(), null));
    sku.setChangeUnitPrice(NumUtil.parse(updateSku.getChangeUnitPrice(), null));
    sku.setJanCode(updateSku.getJanCode());
    sku.setStandardDetail1Name(updateSku.getStandardDetail1Name());
    sku.setStandardDetail2Name(updateSku.getStandardDetail2Name());
    sku.setDisplayOrder(NumUtil.toLong(updateSku.getDisplayOrder(), null));
    sku.setUpdatedDatetime(updateSku.getUpdateDatetime());
    // 20120104 ysy add start
    sku.setWeight(NumUtil.parse(updateSku.getWeight()));
    sku.setUseFlg(NumUtil.toLong(updateSku.getUseFlg()));   
    // 20120104 ysy add end

    Stock stock = new Stock();
    stock.setShopCode(getLoginInfo().getShopCode());
    stock.setSkuCode(updateSku.getSkuCode());
    stock.setCommodityCode(getBean().getParentCommodityCode());
    stock.setReservationLimit(NumUtil.toLong(updateSku.getReservationLimit(), null));
    stock.setOneshotReservationLimit(NumUtil.toLong(updateSku.getOneshotReservationLimit(), null));
    stock.setStockThreshold(NumUtil.toLong(updateSku.getStockThreshold(), null));
    stock.setUpdatedUser(getLoginInfo().getLoginId());

    // 更新処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.updateCommoditySku(sku, stock);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "SKU"));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.FAULT_STANDARD_DETAIL_NAME_SET_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/catalog/commodity_sku/init/" + getBean().getShopCode() + "/" + getBean().getParentCommodityCode() + "/"
        + WebConstantCode.COMPLETE_UPDATE);

    setRequestBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
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
    return Messages.getString("web.action.back.catalog.CommoditySkuUpdateAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014007";
  }

}
