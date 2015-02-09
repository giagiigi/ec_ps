package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.StockIOType;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.StockIOSearchCondition;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.StockIOBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040510:入出庫管理のアクションクラスです
 *
 * @author System Integrator Corp.
 */
public class StockIOCompleteAction extends StockIOBaseAction {

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.StockIOCompleteAction.0");

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   *
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.STOCK_MANAGEMENT_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   *
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    StockIOBean reqBean = getBean();
    StockIOBean nextBean = new StockIOBean();

    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の設定
    StockIOSearchCondition condition = setSearchCondition(reqBean, nextBean);

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = service.getSkuInfo(nextBean.getSearchShopCode(), nextBean.getSearchSkuCode());

    if (commodityInfo == null) {
      // 検索条件の初期値設定
      nextBean.setSearchStockIODateStart(DateUtil.toDateString(DateUtil.addDate(DateUtil.getSysdate(), -7)));
      nextBean.setSearchStockIODateEnd(DateUtil.toDateString(DateUtil.getSysdate()));
      List<String> searchStockIOTypeList = new ArrayList<String>();
      for (StockIOType stockIOType : StockIOType.values()) {
        searchStockIOTypeList.add(stockIOType.getValue());
      }
      nextBean.setSearchStockIOType(searchStockIOTypeList);
      setRequestBean(nextBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    nextBean.setCommodityName(getCommodityName(commodityInfo));
    nextBean.setStockManagementType(NumUtil.toString(commodityInfo.getHeader().getStockManagementType()));
    StockManagementType type = StockManagementType.fromValue(commodityInfo.getHeader().getStockManagementType());
    if (type != null) {
      nextBean.setStockManagenentTypeName(type.getName());
    }
    nextBean.setStockQuantity(commodityInfo.getStock().getStockQuantity());
    nextBean.setAllocatedQuantity(commodityInfo.getStock().getAllocatedQuantity());
    nextBean.setAvailableStockQuantity(service.getAvailableStock(nextBean.getSearchShopCode(), nextBean.getSearchSkuCode()));
    nextBean.setReservedQuantity(commodityInfo.getStock().getReservedQuantity());
    nextBean.setReservationLimit(commodityInfo.getStock().getReservationLimit());
    nextBean.setOneshotReservationLimit(commodityInfo.getStock().getOneshotReservationLimit());
    nextBean.setStockThreshold(commodityInfo.getStock().getStockThreshold());

    // 入出庫履歴の取得
    List<StockIODetail> stockIOList = getStockIOList(nextBean, condition);

    // 画面表示用Beanの作成
    createNextBean(nextBean, stockIOList);

    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    StockIOBean stockIOBean = (StockIOBean) getRequestBean();
    setDisplayControl(stockIOBean);

    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 1) {
      completeParam = param[0];
    }

    if (completeParam.equals(WebConstantCode.DISPLAY_EDIT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, PICTURE_NAME));
    } else if (completeParam.startsWith(WebConstantCode.COMPLETE_UPLOAD)) {
      // appscan対策のためstartsWithでURLパラメータを比較する
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      // 10.1.4 10198 追加 ここから
      if (messageBean == null) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_FAILED));
        return;
      }
      // 10.1.4 10198 追加 ここまで

      List<UploadResult> resultList = messageBean.getUploadDetailList();

      if (messageBean.getResult().equals(ResultType.SUCCESS)) {
        addInformationMessage(WebMessage.get(CompleteMessage.CSV_IMPORT_COMPLETE));
      } else if (messageBean.getResult().equals(ResultType.FAILED)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_FAILED));
      } else {
        addWarningMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_PARTIAL, ""));
      }

      for (UploadResult ur : resultList) {

        for (String s : ur.getInformationMessage()) {
          addInformationMessage(s);
        }
        for (String s : ur.getWarningMessage()) {
          addWarningMessage(s);
        }
        for (String s : ur.getErrorMessage()) {
          addErrorMessage(s);
        }

      }
    }
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   *
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   *
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * Action名の取得
   *
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockIOCompleteAction.1");
  }

  /**
   * オペレーションコードの取得
   *
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104051001";
  }

}
