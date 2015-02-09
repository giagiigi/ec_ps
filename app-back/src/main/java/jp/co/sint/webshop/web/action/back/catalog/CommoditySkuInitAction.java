package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.StandardCombinationCheckQuery;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean.CommoditySkuDetailBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.file.UploadContents;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommoditySkuInitAction extends CommoditySkuBaseAction {

  private static final String UNIT_PRICE = "unit";

  private static final String DISCOUNT_PRICE = "discount";

  private static final String RESERVATION_PRICE = "reservation";

  private static final String CHANGE_UNIT_PRICE = "change";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;
    authorization = Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= getLoginInfo().getShopCode().equals(shopCode);
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    String[] params = getRequestParameter().getPathArgs();
    if (params.length >= 2) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));

    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // params[0]:ショップコード, params[1]:商品コード, params[2]:完了コード
    String[] params = getRequestParameter().getPathArgs();
    String shopCode = params[0];
    String commodityCode = params[1];

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader parent = service.getCommodityHeader(shopCode, commodityCode);
    List<CommodityDetail> details = service.getCommoditySku(shopCode, commodityCode);
    if (parent == null || details.isEmpty()) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    CommoditySkuBean reqBean = new CommoditySkuBean();

    // 共通項目の設定
    reqBean.setShopCode(parent.getShopCode());
    reqBean.setParentCommodityCode(parent.getCommodityCode());
    reqBean.setParentCommodityName(parent.getCommodityName());
    reqBean.setStandardDetail1Name(parent.getCommodityStandard1Name());
    reqBean.setStandardDetail2Name(parent.getCommodityStandard2Name());
    reqBean.setDiscountPriceStartDatetime(DateUtil.toDateTimeString(parent.getDiscountPriceStartDatetime()));
    reqBean.setDiscountPriceEndDatetime(DateUtil.toDateTimeString(parent.getDiscountPriceEndDatetime()));
    reqBean.setReservationStartDatetime(DateUtil.toDateTimeString(parent.getReservationStartDatetime()));
    reqBean.setReservationEndDatetime(DateUtil.toDateTimeString(parent.getReservationEndDatetime()));
    reqBean.setSalePriceChangeDatetime(DateUtil.toDateTimeString(parent.getChangeUnitPriceDatetime()));
    reqBean.setUnitPricePrimaryAll("");
    reqBean.setDiscountPriceAll("");
    reqBean.setReservationPriceAll("");
    reqBean.setChangeUnitPriceAll("");
    reqBean.setStockManagementType(NumUtil.toString(parent.getStockManagementType()));

    List<CodeAttribute> commodityStandardNameList = new ArrayList<CodeAttribute>();
    commodityStandardNameList.add(new NameValue(Messages.getString("web.action.back.catalog.CommoditySkuInitAction.1"), "0"));
    commodityStandardNameList.add(new NameValue(Messages.getString("web.action.back.catalog.CommoditySkuInitAction.2"), "1"));
    commodityStandardNameList.add(new NameValue(Messages.getString("web.action.back.catalog.CommoditySkuInitAction.3"), "2"));
    reqBean.setCommodityStandardNameList(commodityStandardNameList);
    // 20120104 ysy add start
    List<CodeAttribute> useFlgList = new ArrayList<CodeAttribute>();
    useFlgList.add(new NameValue(Messages.getString("web.action.back.catalog.CommoditySkuInitAction.13"), "0"));
    useFlgList.add(new NameValue(Messages.getString("web.action.back.catalog.CommoditySkuInitAction.14"), "1"));
    reqBean.setUseFlgList(useFlgList);
    // 20120104 ysy add end
    if (StringUtil.hasValueAllOf(parent.getCommodityStandard1Name(), parent.getCommodityStandard2Name())) {
      reqBean.setCommodityStandardNameValue("2");
    } else if (StringUtil.hasValue(parent.getCommodityStandard1Name())) {
      reqBean.setCommodityStandardNameValue("1");
    } else {
      reqBean.setCommodityStandardNameValue("0");
    }
    // 商品規格名称の重複チェック
    int standardCount = NumUtil.toLong(reqBean.getCommodityStandardNameValue()).intValue();
    if (standardCount != 0) {
      StandardCombinationCheckQuery checkQuery = new StandardCombinationCheckQuery(reqBean.getShopCode(), reqBean
          .getParentCommodityCode(), standardCount);
      String standardCombination = DatabaseUtil.executeScalar(checkQuery).toString();
      if (standardCombination.equals("1")) {
        addWarningMessage(WebMessage.get(CatalogErrorMessage.STANDARD_NAME_DUPLICATED_ERROR));
      }
    }

    reqBean.setCommodityStandard1Name(parent.getCommodityStandard1Name());
    reqBean.setCommodityStandard2Name(parent.getCommodityStandard2Name());
    reqBean.setEscapeCommodityStandardNameValue(reqBean.getCommodityStandardNameValue());
    reqBean.setEscapeCommodityStandard1Name(parent.getCommodityStandard1Name());
    reqBean.setEscapeCommodityStandard2Name(parent.getCommodityStandard2Name());

    // 一覧表示用項目の設定
    List<CommoditySkuDetailBean> detailList = new ArrayList<CommoditySkuDetailBean>();
    StockManagementType stockManagementType = StockManagementType.fromValue(parent.getStockManagementType());
    for (CommodityDetail detail : details) {
      CommoditySkuDetailBean sku = new CommoditySkuDetailBean();
      sku.setShopCode(detail.getShopCode());
      sku.setSkuCode(detail.getSkuCode());
      sku.setCommodityCode(detail.getCommodityCode());
      sku.setUnitPrice(NumUtil.toString(detail.getUnitPrice()));
      sku.setDiscountPrice(NumUtil.toString(detail.getDiscountPrice()));
      sku.setReservationPrice(NumUtil.toString(detail.getReservationPrice()));
      sku.setChangeUnitPrice(NumUtil.toString(detail.getChangeUnitPrice()));
      sku.setJanCode(detail.getJanCode());
      sku.setStandardDetail1Name(detail.getStandardDetail1Name());
      sku.setStandardDetail2Name(detail.getStandardDetail2Name());
      if (StringUtil.isNullOrEmpty(sku.getStandardDetail1Name())) {
        if (reqBean.getCommodityStandardNameValue().equals("1") || reqBean.getCommodityStandardNameValue().equals("2")) {
          addWarningMessage(WebMessage
              .get(ValidationMessage.REQUIRED_ERROR, MessageFormat.format(Messages
                  .getString("web.action.back.catalog.CommoditySkuInitAction.4"), sku.getSkuCode(), parent
                  .getCommodityStandard1Name())));
        }
      }
      if (StringUtil.isNullOrEmpty(sku.getStandardDetail2Name())) {
        if (reqBean.getCommodityStandardNameValue().equals("2")) {
          addWarningMessage(WebMessage
              .get(ValidationMessage.REQUIRED_ERROR, MessageFormat.format(Messages
                  .getString("web.action.back.catalog.CommoditySkuInitAction.4"), sku.getSkuCode(), parent
                  .getCommodityStandard2Name())));
        }
      }
      sku.setUpdateDatetime(detail.getUpdatedDatetime());
      sku.setDisplayOrder(NumUtil.toString(detail.getDisplayOrder()));
      // 20120104 ysy add start
      sku.setWeight(NumUtil.toString(detail.getWeight()));     
      sku.setUseFlg(NumUtil.toString(detail.getUseFlg()));
      // 20120104 ysy add end
      Stock stock = service.getStock(detail.getShopCode(), detail.getSkuCode());
      sku.setReservationLimit(NumUtil.toString(stock.getReservationLimit()));
      sku.setOneshotReservationLimit(NumUtil.toString(stock.getOneshotReservationLimit()));

      if (!NumUtil.isNull(stock.getStockThreshold()) && stock.getStockThreshold() == 0) {
        sku.setStockThreshold(null);
      } else {
        sku.setStockThreshold(NumUtil.toString(stock.getStockThreshold()));
      }
     

      // 在庫管理区分が"在庫管理する(在庫数表示)"または"在庫管理する(在庫状況表示)"の場合は在庫数取得
      if (stockManagementType == StockManagementType.WITH_QUANTITY || stockManagementType == StockManagementType.WITH_STATUS) {
        sku.setStockQuantity(stock.getStockQuantity());
        boolean reserve = service.isReserve(detail.getShopCode(), detail.getSkuCode());
        Long availableStockQuantity;
        if (reserve) {
          availableStockQuantity = service.getReservationAvailableStock(detail.getShopCode(), detail.getSkuCode());
        } else {
          availableStockQuantity = service.getAvailableStock(detail.getShopCode(), detail.getSkuCode());
        }
        sku.setAvailableStockQuantity(availableStockQuantity);
      }

      if (detail.getSkuCode().equals(parent.getRepresentSkuCode())) {
        sku.setRepresentFlg(true);
      }

      detailList.add(sku);
    }
    reqBean.setList(detailList);

    List<CodeAttribute> skuImageList = new ArrayList<CodeAttribute>();
    for (CommoditySkuDetailBean skuDetail : detailList) {
      skuImageList.add(new NameValue("(PC)&nbsp;&nbsp;&nbsp;" + skuDetail.getSkuCode(), skuDetail.getSkuCode() + "/"
          + UploadContents.SKU_IMAGE_PC));
    }
    for (CommoditySkuDetailBean skuDetail : detailList) {
      skuImageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommoditySkuInitAction.5")//$NON-NLS-1$
          + skuDetail.getSkuCode(), skuDetail.getSkuCode() + "/" + UploadContents.SKU_IMAGE_MOBILE));
    }
    reqBean.setSkuImageList(skuImageList);

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    CommoditySkuBean reqBean = (CommoditySkuBean) getRequestBean();

    // 価格一括テーブル、選択ボタン、更新ボタン、登録行
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.setRegisterRowDisplayFlg(true);
      reqBean.setPriceAllTableDisplayFlg(true);
      reqBean.setUpdateButtonDisplayFlg(true);
      reqBean.setSelectButtonDisplayFlg(true);
      reqBean.setUploadTableDisplayFlg(true);
      reqBean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
    }

    BackLoginInfo login = getLoginInfo();

    if (Permission.STOCK_MANAGEMENT_READ.isGranted(login) || Permission.STOCK_MANAGEMENT_UPDATE.isGranted(login)) {
      reqBean.setMoveStockIOLinkDisplayFlg(true);
    }

    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());
    for (CommoditySkuDetailBean detail : reqBean.getList()) {
      // 削除ボタン
      if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo()) && reqBean.getList().size() > 1) {
        detail.setDisplayDeleteButton(true);
      }
      if (detail.isRepresentFlg()) {
        detail.setDisplayDeleteButton(false);
      }

      // 画像削除ボタン
      detail.setDisplayImageDeleteButton(service.skuImageExists(detail.getShopCode(), detail.getSkuCode(), true));
      detail.setDiaplayMobileImageDeleteButton(service.skuImageExists(detail.getShopCode(), detail.getSkuCode(), false));
    }

    setCompleteMessage();

    setRequestBean(reqBean);

  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert 更新完了時：update 削除完了時：delete 商品画像アップロード時 : upload <BR>
   * 
   * @param complete
   *          処理完了パラメータ
   */
  private void setCompleteMessage() {

    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length >= 3) {
      complete = params[2];
    }

    if (WebConstantCode.COMPLETE_INSERT.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "SKU"));
    } else if (WebConstantCode.COMPLETE_UPDATE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "SKU"));
    } else if (WebConstantCode.COMPLETE_DELETE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "SKU"));
    } else if (COMPLETE_FILE_DELETE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.6")));
    } else if (WebConstantCode.COMPLETE_UPLOAD.equals(complete)) {
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      if (messageBean != null) {
        if (messageBean.getResult().equals(ResultType.SUCCESS)) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPLOAD_COMPLETE, Messages
              .getString("web.action.back.catalog.CommoditySkuInitAction.7")));
        } else {
          addErrorMessage(WebMessage.get(ActionErrorMessage.UPLOAD_FAILED, Messages
              .getString("web.action.back.catalog.CommoditySkuInitAction.7")));
        }

        List<UploadResult> resultList = messageBean.getUploadDetailList();
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
    } else if (UNIT_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.8")));
    } else if (DISCOUNT_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.9")));
    } else if (RESERVATION_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.10")));
    } else if (CHANGE_UNIT_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.11")));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuInitAction.12");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104014002";
  }

}
