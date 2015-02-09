package jp.co.sint.webshop.web.action.back.catalog;

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
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean.CommoditySkuDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
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
public class CommoditySkuRegisterAction extends CommoditySkuBaseAction {

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

    CommoditySkuDetailBean sku = getBean().getEdit();

    isValid = validatePeriod(sku);

    if (getBean().getEscapeCommodityStandardNameValue().equals("0")) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.REGISTER_SKU_ERROR));
      isValid = false;
    } else if (getBean().getEscapeCommodityStandardNameValue().equals("1")) {
      if (StringUtil.isNullOrEmpty(sku.getStandardDetail1Name())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, getBean().getCommodityStandard1Name()));
        isValid = false;
      }
      if (StringUtil.hasValue(sku.getStandardDetail2Name())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STANDARD_NAME2_SET_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuRegisterAction.0")));
        isValid = false;
      }
    } else if (getBean().getEscapeCommodityStandardNameValue().equals("2")) {
      if (StringUtil.isNullOrEmpty(sku.getStandardDetail1Name())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, getBean().getCommodityStandard1Name()));
        isValid = false;
      }
      if (StringUtil.isNullOrEmpty(sku.getStandardDetail2Name())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, getBean().getCommodityStandard2Name()));
        isValid = false;
      }
    }

    // 各価格と適用期間の相互チェック
    return isValid;

  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommoditySkuBean reqBean = getBean();

    BackLoginInfo login = getLoginInfo();

    // 登録用DTO作成
    CommodityDetail sku = new CommodityDetail();
    sku.setShopCode(login.getShopCode());
    sku.setSkuCode(reqBean.getEdit().getSkuCode());
    sku.setCommodityCode(reqBean.getParentCommodityCode());
    sku.setUnitPrice(NumUtil.parse(reqBean.getEdit().getUnitPrice()));
    sku.setDiscountPrice(NumUtil.parse(reqBean.getEdit().getDiscountPrice(), null));
    sku.setReservationPrice(NumUtil.parse(reqBean.getEdit().getReservationPrice(), null));
    sku.setChangeUnitPrice(NumUtil.parse(reqBean.getEdit().getChangeUnitPrice(), null));
    sku.setJanCode(reqBean.getEdit().getJanCode());
    sku.setStandardDetail1Name(reqBean.getEdit().getStandardDetail1Name());
    sku.setStandardDetail2Name(reqBean.getEdit().getStandardDetail2Name());
    sku.setDisplayOrder(NumUtil.toLong(reqBean.getEdit().getDisplayOrder(), null));

    Stock stock = new Stock();
    stock.setShopCode(login.getShopCode());
    stock.setSkuCode(reqBean.getEdit().getSkuCode());
    stock.setCommodityCode(reqBean.getParentCommodityCode());
    stock.setReservationLimit(NumUtil.toLong(reqBean.getEdit().getReservationLimit(), null));
    stock.setOneshotReservationLimit(NumUtil.toLong(reqBean.getEdit().getOneshotReservationLimit(), null));
    stock.setStockThreshold(NumUtil.toLong(reqBean.getEdit().getStockThreshold(), null));

    // 登録処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.insertCommoditySku(sku, stock);

    // サービスエラーの有無をチェック
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, "SKU"));
          setNextUrl(null);
          setRequestBean(reqBean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.FAULT_STANDARD_DETAIL_NAME_SET_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/catalog/commodity_sku/init/" + getBean().getShopCode() + "/" + reqBean.getParentCommodityCode() + "/"
        + WebConstantCode.COMPLETE_INSERT);

    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014005";
  }

}
