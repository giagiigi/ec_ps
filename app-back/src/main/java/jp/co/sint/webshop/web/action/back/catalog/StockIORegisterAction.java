package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.domain.StockIOType;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.StockOperationType;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.DatetimeValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.StockIOBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040510:入出庫管理のアクションクラスです
 *
 * @author System Integrator Corp.
 */
public class StockIORegisterAction extends StockIOBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   *
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
  // 10.1.2 10092 修正 ここから
//  return Permission.STOCK_MANAGEMENT_READ.isGranted(getLoginInfo());
  return Permission.STOCK_MANAGEMENT_UPDATE.isGranted(getLoginInfo());
  // 10.1.2 10092 修正 ここまで
  }

  /**
   * アクションを実行します。
   *
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    StockIOBean reqBean = getBean();

    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    StockIODetail detail = new StockIODetail();
    detail.setShopCode(getLoginInfo().getShopCode());
    detail.setSkuCode(reqBean.getEdit().getSkuCode());
    detail.setStockIOQuantity(NumUtil.toLong(reqBean.getEdit().getStockIOQuantity()));
    detail.setStockIOType(NumUtil.toLong(reqBean.getEdit().getStockIOType()));
    detail.setMemo(reqBean.getEdit().getMemo());

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    Stock stock = service.getStock(detail.getShopCode(), detail.getSkuCode());
    if (stock == null) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    Long limitCount = 99999999L;

    if (detail.getStockIOType().equals(StockOperationType.ENTRY.longValue())) {
      // 入出庫数と在庫数を加算した値が、在庫数の上限を超えた場合
      if (stock.getStockQuantity() + detail.getStockIOQuantity() > limitCount) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_AMOUNT_OVERFLOW_ERROR,
            Messages.getString("web.action.back.catalog.StockIORegisterAction.0"), NumUtil.toString(-limitCount),
            NumUtil.toString(limitCount)));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

    } else if (detail.getStockIOType().equals(StockOperationType.ALLOCATE.longValue())) {
      if (stock.getAllocatedQuantity() + detail.getStockIOQuantity() > limitCount) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_AMOUNT_OVERFLOW_ERROR,
            Messages.getString("web.action.back.catalog.StockIORegisterAction.1"), NumUtil.toString(-limitCount),
            NumUtil.toString(limitCount)));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

    } else if (detail.getStockIOType().equals(StockOperationType.RESERVING.longValue())) {
      if (stock.getReservedQuantity() + detail.getStockIOQuantity() > limitCount) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_AMOUNT_OVERFLOW_ERROR,
            Messages.getString("web.action.back.catalog.StockIORegisterAction.2"), NumUtil.toString(-limitCount),
            NumUtil.toString(limitCount)));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

    } else if (detail.getStockIOType().equals(StockOperationType.DELIVER.longValue())) {
      if (stock.getStockQuantity() - detail.getStockIOQuantity() < -limitCount) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_AMOUNT_OVERFLOW_ERROR,
            Messages.getString("web.action.back.catalog.StockIORegisterAction.0"), NumUtil.toString(-limitCount),
            NumUtil.toString(limitCount)));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

    } else if (detail.getStockIOType().equals(StockOperationType.DEALLOCATE.longValue())) {
      if (stock.getAllocatedQuantity() - detail.getStockIOQuantity() < -limitCount) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_AMOUNT_OVERFLOW_ERROR,
            Messages.getString("web.action.back.catalog.StockIORegisterAction.1"), NumUtil.toString(-limitCount),
            NumUtil.toString(limitCount)));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

    } else if (detail.getStockIOType().equals(StockOperationType.CANCEL_RESERVING.longValue())) {
      if (stock.getReservedQuantity() - detail.getStockIOQuantity() < -limitCount) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_AMOUNT_OVERFLOW_ERROR,
            Messages.getString("web.action.back.catalog.StockIORegisterAction.2"), NumUtil.toString(-limitCount),
            NumUtil.toString(limitCount)));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }

    }

    ServiceResult result = service.insertStockIO(detail);
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.StockIORegisterAction.3")));
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_NO_CHANGEABLE_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/catalog/stock_io/complete/edit");
    }
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   *
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    StockIOBean reqBean = getBean();
    boolean valid = validateBean(reqBean.getEdit());

    if (reqBean.getEdit().getStockIOType().equals(StockIOType.DELIVERY.getValue())
        || reqBean.getEdit().getStockIOType().equals(StockIOType.ENTRY.getValue())) {

      if (StringUtil.isNullOrEmpty(reqBean.getEdit().getMemo())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_IO_MEMO_REQUIRED_ERROR));
        valid &= false;
      }

      // 10.1.1 10026 削除 ここから
//      if (NumUtil.toLong(reqBean.getEdit().getStockIOQuantity()) < 1L) {
//        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_QUANTITY_ERROR));
//        valid &= false;
//       }
      // 10.1.1 10026 削除 ここまで
    // 10.1.1 10026 追加 ここから
    } else {
      // 入出庫事由を初期化
      reqBean.getEdit().setMemo("");
    // 10.1.1 10026 追加 ここまで
    }

    // 10.1.1 10026 追加 ここから
    if (valid && NumUtil.toLong(reqBean.getEdit().getStockIOQuantity()) < 1L) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_QUANTITY_ERROR));
      valid &= false;
    }
    // 10.1.1 10026 追加 ここまで

    // AppScan対策　リクエストで送られた日付が不正な場合は検索時に使用したものをコピー
    DatetimeValidator datetimeValidator = new DatetimeValidator("yyyy/MM/dd", "");
    if (StringUtil.hasValue(reqBean.getSearchStockIODateStart())) {
      if (!datetimeValidator.isValid(reqBean.getSearchStockIODateStart())) {
        reqBean.setSearchStockIODateStart(reqBean.getOldSearchStockIODateStart());
      }
    }
    if (StringUtil.hasValue(reqBean.getSearchStockIODateEnd())) {
      if (!datetimeValidator.isValid(reqBean.getSearchStockIODateEnd())) {
        reqBean.setSearchStockIODateEnd(reqBean.getOldSearchStockIODateEnd());
      }
    }

    return valid;
  }

  /**
   * Action名の取得
   *
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockIORegisterAction.4");
  }

  /**
   * オペレーションコードの取得
   *
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104051003";
  }

}
