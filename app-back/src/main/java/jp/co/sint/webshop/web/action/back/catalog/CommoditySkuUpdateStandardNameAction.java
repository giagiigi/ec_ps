package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil; // 10.1.7 10327 追加
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
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
public class CommoditySkuUpdateStandardNameAction extends WebBackAction<CommoditySkuBean> {

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
    CommoditySkuBean bean = getBean();

    isValid = validateItems(getBean(), "commodityStandard1Name", "commodityStandard2Name");
    if (!isValid) {
      return isValid;
    }

    if (bean.getCommodityStandardNameValue().equals("0")) {
      if (StringUtil.hasValue(bean.getCommodityStandard1Name()) || StringUtil.hasValue(bean.getCommodityStandard2Name())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STANDARD_NAMES_SET_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.0")));
        isValid = false;
      }
    } else if (bean.getCommodityStandardNameValue().equals("1")) {
      if (StringUtil.isNullOrEmpty(bean.getCommodityStandard1Name())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.1")));
        isValid = false;
      }
      if (StringUtil.hasValue(bean.getCommodityStandard2Name())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STANDARD_NAME2_SET_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.2")));
        isValid = false;
      }
    } else if (bean.getCommodityStandardNameValue().equals("2")) {
      if (StringUtil.isNullOrEmpty(bean.getCommodityStandard1Name())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.1")));
        isValid = false;
      }
      if (StringUtil.isNullOrEmpty(bean.getCommodityStandard2Name())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.3")));
        isValid = false;
      }
      // 10.1.6 10258 追加 ここから
    } else {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.FAULT_STANDARD_COUNT_ERROR, Messages
          .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.6")));
      isValid = false;
      // 10.1.6 10258 追加 ここまで
    }
    // 10.1.6 10258 追加 ここから
    // 規格名称重複チェック
    if (StringUtil.hasValue(bean.getCommodityStandard1Name()) && StringUtil.hasValue(bean.getCommodityStandard2Name())) {
      if (bean.getCommodityStandard1Name().equals(bean.getCommodityStandard2Name())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.OVERLAPPED_VALUES, Messages
            .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.1"), Messages
            .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.3")));
        isValid = false;
      }
    }

    // 10.1.6 10258 追加 ここまで
    return isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommoditySkuBean bean = getBean();

    // 更新処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader header = service.getCommodityHeader(bean.getShopCode(), bean.getParentCommodityCode());
    // 10.1.7 10327 追加 ここから
    if (!service.canModifyStandardCount(bean.getShopCode(), bean.getParentCommodityCode())) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CHANGE_STANDARD_COUNT_ERROR));
      restoreStandardConfig(bean, header);
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }
    // 10.1.7 10327 追加 ここまで
    header.setCommodityStandard1Name(bean.getCommodityStandard1Name());
    header.setCommodityStandard2Name(bean.getCommodityStandard2Name());

    ServiceResult result = service.updateCommodityStandardName(header);

    // サービスエラーの有無をチェック
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.4")));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.STANDARD_NAME2_SET_ERROR, Messages
              .getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.2")));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CatalogServiceErrorContent.DELETE_COMMODITY_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.DELETE_COMMODITY_ERROR, bean.getParentCommodityCode()));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/catalog/commodity_sku/init/" + getBean().getShopCode() + "/" + getBean().getParentCommodityCode() + "/"
          + WebConstantCode.COMPLETE_UPDATE);
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuUpdateStandardNameAction.5");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014008";
  }
  
  // 10.1.7 10327 追加 ここから
  private void restoreStandardConfig(CommoditySkuBean bean, CommodityHeader header) {
    bean.setCommodityStandard1Name(header.getCommodityStandard1Name());
    bean.setCommodityStandard2Name(header.getCommodityStandard2Name());
    long stdCount = 0;
    if (StringUtil.hasValue(bean.getCommodityStandard1Name())) {
      stdCount++;
    }
    if (StringUtil.hasValue(bean.getCommodityStandard2Name())) {
      stdCount++;
    }
    bean.setCommodityStandardNameValue(NumUtil.toString(stdCount));
  }
  // 10.1.7 10327 追加 ここまで

}
