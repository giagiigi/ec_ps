package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean.CommodityEditHeaderBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean.CommodityEditSkuBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CommodityEditBaseAction extends WebBackAction<CommodityEditBean> {

  /** 画面モード:新規登録 */
  public static final String MODE_NEW = "new";

  /** 画面モード:更新 */
  public static final String MODE_UPDATE = "update";

  /** 特別価格 */
  public static final String DISCOUNT = "discount";

  /** 予約価格 */
  public static final String RESERVATION = "reservation";

  /** 改定価格 */
  public static final String CHANGE = "change";

  /** 商品別ポイント付与率 */
  public static final String POINT = "point";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean isValid = true;
    CommodityEditBean reqBean = getBean();
    CommodityEditHeaderBean header = reqBean.getHeader();
    CommodityEditSkuBean detail = reqBean.getSku();
    isValid &= validateBean(reqBean);
    isValid &= validateBean(header);
    if (reqBean.getMode().equals(MODE_NEW) && StringUtil.isNullOrEmpty(detail.getRepresentSkuCode())
        && BeanValidator.partialValidate(reqBean, "commodityCode").isValid()) {
      detail.setRepresentSkuCode(reqBean.getCommodityCode());
    }
    isValid &= validateBean(detail);

    // 在庫管理区分="在庫管理する(状況表示)"の場合は、在庫状況表示の必須チェック
    if (header.getStockManagementType().equals((StockManagementType.WITH_STATUS.getValue()))) {
      if (StringUtil.isNullOrEmpty(header.getStockStatusNo())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.STOCK_STATUS_SET_ERROR));
        isValid &= false;
      }
    }
    // 予約上限数、注文毎予約上限数が両方入力されている場合は、大小チェック

    if (StringUtil.hasValueAllOf(detail.getReservationLimit(), detail.getOneshotReservationLimit())) {
      Long oneshotReservationLimit = NumUtil.toLong(detail.getOneshotReservationLimit());
      Long reservationLimit = NumUtil.toLong(detail.getReservationLimit());
      if (!ValidatorUtil.lessThanOrEquals(oneshotReservationLimit, reservationLimit)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.0")));
        isValid &= false;
      }
    }
    if (!isValid) { // 個別入力チェック
      setRequestBean(reqBean);
      return false;
    }
    isValid &= validateDateRange(); // 日付の順序チェック

    if (!isValid) {
      setRequestBean(reqBean);
      return false;
    }
    // 期間の必須チェック

    if (StringUtil.hasValue(header.getCommodityPointRate())) { // ポイント付与率が入力されている場合

      isValid &= validateRequiredPeriod(POINT);
    }
    if (StringUtil.hasValue(detail.getDiscountPrice())) { // 特別価格が入力されている場合
      isValid &= validateRequiredPeriod(DISCOUNT);
    }
    if (StringUtil.hasValue(detail.getReservationPrice())) { // 予約価格が入力されている場合
      isValid &= validateRequiredPeriod(RESERVATION);
    }
    if (StringUtil.hasValue(detail.getChangeUnitPrice())) { // 改定価格が入力されている場合
      isValid &= validateRequiredPeriod(CHANGE);
    }
    if (!isValid) {
      setRequestBean(reqBean);
      return false;
    }
    // 期間の相関チェック

    // 商品別ポイント付与期間が設定されている場合

    if (StringUtil.hasValueAnyOf(header.getCommodityPointStartDatetime(), header.getCommodityPointEndDatetime())) {
      if (StringUtil.isNullOrEmpty(header.getCommodityPointRate())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.1"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.2")));
        isValid &= false;
      }
    }
    // 特別価格期間が設定されている場合
    if (StringUtil.hasValueAnyOf(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
      // 特別価格が入力されているかチェック

      if (StringUtil.isNullOrEmpty(detail.getDiscountPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.3"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.4")));
        isValid &= false;
      }
    }
    // 販売開始日時が設定されている場合
    if (StringUtil.hasValue(header.getSaleStartDatetime())) {
      // 特別価格開始日時が設定されていて、かつ販売開始日時 < 特別価格開始日時かチェック

      if (StringUtil.hasValue(header.getDiscountPriceStartDatetime())
          && !ValidatorUtil.isCorrectOrder(header.getSaleStartDatetime(), header.getDiscountPriceStartDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.5"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.6")));
        isValid &= false;
      }
      // 特別価格終了日時が設定されている場合は、特別価格開始日時が入力されているかチェック
      if (StringUtil.hasValue(header.getDiscountPriceEndDatetime())
          && StringUtil.isNullOrEmpty(header.getDiscountPriceStartDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.7"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.8")));
        isValid &= false;
      }
    }
    // 販売終了日時が設定されている場合
    if (StringUtil.hasValue(header.getSaleEndDatetime())) {
      // 特別価格終了日時が設定されいて、かつ特別価格終了日時 < 販売終了日時かチェック

      if (StringUtil.hasValue(header.getDiscountPriceEndDatetime())
          && !ValidatorUtil.isCorrectOrder(header.getDiscountPriceEndDatetime(), header.getSaleEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.9"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.10")));
        isValid &= false;
      }
      // 特別価格開始日時が設定されている場合は、特別価格終了日時が入力されているかチェック
      if (StringUtil.hasValue(header.getDiscountPriceStartDatetime())
          && StringUtil.isNullOrEmpty(header.getDiscountPriceEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.11"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.12")));
        isValid &= false;
      }
    }
    // 予約開始日時が設定されている場合
    if (StringUtil.hasValue(header.getReservationStartDatetime())) {
      // 予約価格が入力されているかチェック

      if (StringUtil.isNullOrEmpty(detail.getReservationPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.13"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.14")));
        isValid &= false;
      }
      // 予約終了日時が入力されているかチェック

      if (!StringUtil.hasValue(header.getReservationEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.15"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.16")));
        isValid &= false;
      }
      // 販売開始日時が入力されているかチェック

      if (StringUtil.isNullOrEmpty(header.getSaleStartDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.17"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.5")));
        isValid &= false;
      }
      // 予約終了日時 < 販売開始日時かチェック

      if (!ValidatorUtil.isCorrectOrder(header.getReservationEndDatetime(), header.getSaleStartDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.16"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.5")));
        isValid &= false;
      }
      // 予約終了日時が設定されている場合
    } else if (StringUtil.hasValue(header.getReservationEndDatetime())) {
      // 予約価格が入力されているかチェック

      if (StringUtil.isNullOrEmpty(detail.getReservationPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.13"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.14")));
        isValid &= false;
      }
      // 販売開始日時が入力されているかチェック

      if (StringUtil.isNullOrEmpty(header.getSaleStartDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.13"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.5")));
        isValid &= false;
      }
      // 予約終了日時 < 販売開始日時かチェック

      if (!ValidatorUtil.isCorrectOrder(header.getReservationEndDatetime(), header.getSaleStartDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.16"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.5")));
        isValid &= false;
      }
    }
    // 価格改定日が入力されている場合

    if (StringUtil.hasValue(header.getSalePriceChangeDatetime())) {
      if (StringUtil.isNullOrEmpty(detail.getChangeUnitPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.18"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.19")));
        isValid &= false;
      }
    }
    return isValid;
  }

  /**
   * 商品情報DTOに商品登録の内容を設定します。
   * 
   * @param commodityInfo
   * @param reqBean
   */
  public void setCommodityData(CommodityInfo commodityInfo, CommodityEditBean reqBean) {

    CommodityEditHeaderBean headerBean = reqBean.getHeader();
    CommodityEditSkuBean skuBean = reqBean.getSku();

    CommodityHeader header = commodityInfo.getHeader();
    CommodityDetail detail = commodityInfo.getDetail();
    Stock stock = commodityInfo.getStock();

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    // DB登録用DTOに値を設定する
    header.setShopCode(shopCode);
    header.setCommodityCode(reqBean.getCommodityCode());
    header.setCommodityName(headerBean.getCommodityName());
    header.setSaleFlg(NumUtil.toLong(headerBean.getSaleFlg()));
    header.setRepresentSkuCode(skuBean.getRepresentSkuCode());
    header.setRepresentSkuUnitPrice(NumUtil.parse(skuBean.getUnitPrice()));
    header.setDisplayClientType(NumUtil.toLong(headerBean.getDisplayClientType()));
    header.setArrivalGoodsFlg(NumUtil.toLong(headerBean.getArrivalGoodsFlg()));
    header.setDeliveryTypeNo(NumUtil.toLong(headerBean.getDeliveryTypeNo()));
    header.setRecommendCommodityRank(NumUtil.toLong(headerBean.getRecommendCommodityRank(), null));
    header.setCommodityPointRate(NumUtil.toLong(headerBean.getCommodityPointRate(), null));
    header.setCommodityPointStartDatetime(DateUtil.fromString(headerBean.getCommodityPointStartDatetime(), true));
    header.setCommodityPointEndDatetime(DateUtil.fromString(headerBean.getCommodityPointEndDatetime(), true));
    header.setSaleStartDatetime(DateUtil.fromString(headerBean.getSaleStartDatetime(), true));
    header.setSaleEndDatetime(DateUtil.fromString(headerBean.getSaleEndDatetime(), true));
    header.setDiscountPriceStartDatetime(DateUtil.fromString(headerBean.getDiscountPriceStartDatetime(), true));
    header.setDiscountPriceEndDatetime(DateUtil.fromString(headerBean.getDiscountPriceEndDatetime(), true));
    header.setReservationStartDatetime(DateUtil.fromString(headerBean.getReservationStartDatetime(), true));
    header.setReservationEndDatetime(DateUtil.fromString(headerBean.getReservationEndDatetime(), true));
    header.setChangeUnitPriceDatetime(DateUtil.fromString(headerBean.getSalePriceChangeDatetime(), false));
    header.setStockStatusNo(NumUtil.toLong(headerBean.getStockStatusNo(), null));
    header.setCommodityDescriptionPc(headerBean.getCommodityDescription());
    header.setCommodityDescriptionMobile(headerBean.getCommodityDescriptionShort());
    header.setCommoditySearchWords(headerBean.getCommoditySearchWords());
    header.setShadowSearchWords(StringUtil.toSearchKeywords(headerBean.getCommoditySearchWords() + reqBean.getCommodityCode()
        + headerBean.getCommodityName()));
    header.setLinkUrl(headerBean.getLinkUrl());
    header.setUpdatedDatetime(headerBean.getUpdateDatetime());
    // 20120109 ysy add start
    header.setCommodityNameEn(headerBean.getCommodityNameEn());
    header.setCommodityNameJp(headerBean.getCommodityNameJp());
    header.setCommodityDescriptionPcEn(headerBean.getCommodityDescriptionEn());
    header.setCommodityDescriptionPcJp(headerBean.getCommodityDescriptionJp());
    header.setCommodityDescriptionMobileEn(headerBean.getCommodityDescriptionShortEn());
    header.setCommodityDescriptionMobileJp(headerBean.getCommodityDescriptionShortJp());
    header.setBrandCode(headerBean.getBrandCode());
    // 20120119 ysy add start
    header.setBrand(headerBean.getBrand());
    header.setWarningFlag(headerBean.getWarningFlag());
    header.setReturnFlg(NumUtil.toLong(headerBean.getReturnFlag()));
    header.setLeadTime(NumUtil.toLong(headerBean.getLeadTime()));
    header.setSaleFlag(headerBean.getSaleFlag());
    header.setSpecFlag(headerBean.getSpecFlag());
    // 20120109 ysy add end
    //add by twh 2012-12-4 17:16:27 start
    //套装商品登录时，必须的字段填充默认值
    if(!reqBean.isDisplaySuitCommodity()){
      header.setCommodityType(NumUtil.toLong(headerBean.getCommodityType()));
      header.setSetCommodityFlg(NumUtil.toLong(headerBean.getSetCommodityFlg()));
      header.setReturnFlg(NumUtil.toLong(headerBean.getReturnFlag()));
      detail.setWeight(new BigDecimal("0"));
      detail.setUseFlg(NumUtil.toLong("1"));
      stock.setAllocatedTmall(NumUtil.toLong("0"));
      stock.setStockTotal(NumUtil.toLong("0"));
      stock.setStockTmall(NumUtil.toLong("0"));
      stock.setShareRecalcFlag((NumUtil.toLong("0")));
      header.setCommodityTaxType(TaxType.INCLUDED.longValue());
      header.setStockManagementType(NumUtil.toLong("0"));
      header.setCategoryPath("/~0");
      if(reqBean.getCommodityCodeDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)){
        detail.setUnitPrice(new BigDecimal(0));
      }
    }else{
      header.setCommodityTaxType(TaxType.NO_TAX.longValue());
      header.setStockManagementType(NumUtil.toLong(headerBean.getStockManagementType()));
      detail.setUnitPrice(NumUtil.parse(skuBean.getUnitPrice(), null));
    }
    //add by twh 2012-12-4 17:16:27 end
    detail.setUnitPrice(NumUtil.parse(skuBean.getUnitPrice(), null));
    detail.setShopCode(shopCode);
    detail.setSkuCode(skuBean.getRepresentSkuCode());
    detail.setCommodityCode(reqBean.getCommodityCode());
    detail.setJanCode(skuBean.getJanCode());
    detail.setDiscountPrice(NumUtil.parse(skuBean.getDiscountPrice(), null));
    detail.setReservationPrice(NumUtil.parse(skuBean.getReservationPrice(), null));
    detail.setChangeUnitPrice(NumUtil.parse(skuBean.getChangeUnitPrice(), null));
    detail.setUpdatedDatetime(skuBean.getUpdateDatetime());
    stock.setShopCode(shopCode);
    stock.setSkuCode(skuBean.getRepresentSkuCode());
    stock.setCommodityCode(reqBean.getCommodityCode());
    stock.setReservationLimit(NumUtil.toLong(skuBean.getReservationLimit(), null));
    stock.setOneshotReservationLimit(NumUtil.toLong(skuBean.getOneshotReservationLimit(), null));
    stock.setStockThreshold(NumUtil.toLong(skuBean.getStockThreshold(), null));

    commodityInfo.setHeader(header);
    commodityInfo.setDetail(detail);
    commodityInfo.setStock(stock);
  }

  /**
   * 画面に表示するボタンの制御を行います。
   * 
   * @param reqBean
   */
  public void setDisplayControl(CommodityEditBean reqBean) {

    // ショップユーザで商品参照権限を持つ場合

    if (Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo())) {
      reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_READONLY);
      reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setDisplayMoveSkuButton(true);
      reqBean.setDisplayRegisterButton(false);
      reqBean.setDisplayUpdateButton(false);
      reqBean.setDisplayUploadTable(false);
      reqBean.setDisplayPreviewButton(true);
      reqBean.setReadOnlyMode(true);
      reqBean.setDisplayImageTable(true);
    }

    // ショップユーザで商品登録・更新権限を持つ場合
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      if (reqBean.getMode().equals(MODE_NEW)) {
        reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_READONLY);
        reqBean.setDisplayMoveSkuButton(false);
        reqBean.setDisplayUploadTable(false);
        reqBean.setDisplayImageTable(false);
        reqBean.setDisplayImageDeleteButton(false);
        reqBean.setDisplayThumbnailDeleteButton(false);
        reqBean.setDisplayMobileThumbnailDeleteButton(false);
        reqBean.setDisplayRepresentSkuImageDeleteButton(false);
        reqBean.setDisplayMobileRepresentSkuImageDeleteButton(false);
      } else if (reqBean.getMode().equals(MODE_UPDATE)) {
        reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
        reqBean.setDisplayMoveSkuButton(true);
        reqBean.setDisplayUploadTable(true);
        reqBean.setDisplayImageTable(true);

        DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());
        reqBean.setDisplayImageDeleteButton(service.commodityImageExists(reqBean.getShopCode(), reqBean.getCommodityCode(), true));
        reqBean.setDisplayThumbnailDeleteButton(service.thumbnailImageExists(reqBean.getShopCode(), reqBean.getCommodityCode(),
            true));
        reqBean.setDisplayMobileThumbnailDeleteButton(service.thumbnailImageExists(reqBean.getShopCode(), reqBean
            .getCommodityCode(), false));
        reqBean.setDisplayRepresentSkuImageDeleteButton(service.skuImageExists(reqBean.getShopCode(), reqBean.getSku()
            .getRepresentSkuCode(), true));
        reqBean.setDisplayMobileRepresentSkuImageDeleteButton(service.skuImageExists(reqBean.getShopCode(), reqBean.getSku()
            .getRepresentSkuCode(), false));
      } else {
        reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_READONLY);
        reqBean.setDisplayMoveSkuButton(false);
        reqBean.setDisplayUploadTable(false);
        reqBean.setDisplayImageTable(false);
        reqBean.setDisplayImageDeleteButton(false);
        reqBean.setDisplayThumbnailDeleteButton(false);
        reqBean.setDisplayMobileThumbnailDeleteButton(false);
        reqBean.setDisplayRepresentSkuImageDeleteButton(false);
        reqBean.setDisplayMobileRepresentSkuImageDeleteButton(false);
      }
      reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_READONLY);
      reqBean.setDisplayNextButton(false);
    }

  }

  /**
   * 特別価格、予約価格、改定価格、商品別ポイント付与率が設定されている場合に 各期間の必須チェックを行います
   * 
   * @param periodType
   * @return 各期間のチェック結果
   */
  public boolean validateRequiredPeriod(String periodType) {
    String[] params;
    String error = "";
    if (periodType.equals(DISCOUNT)) {
      params = new String[] {
          getBean().getHeader().getDiscountPriceStartDatetime(), getBean().getHeader().getDiscountPriceEndDatetime()
      };
      error = WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.4"), Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.3"));
    } else if (periodType.equals(RESERVATION)) {
      params = new String[] {
          getBean().getHeader().getReservationStartDatetime(), getBean().getHeader().getReservationEndDatetime()
      };
      error = WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.14"), Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.13"));
    } else if (periodType.equals(CHANGE)) {
      params = new String[] {
        getBean().getHeader().getSalePriceChangeDatetime()
      };
      error = WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.19"), Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.18"));
    } else if (periodType.equals(POINT)) {
      params = new String[] {
          getBean().getHeader().getCommodityPointStartDatetime(), getBean().getHeader().getCommodityPointEndDatetime()
      };
      error = WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.2"), Messages
          .getString("web.action.back.catalog.CommodityEditBaseAction.1"));
    } else {
      params = new String[] {};
    }

    if (!StringUtil.hasValueAnyOf(params)) {
      addErrorMessage(error);
      return false;
    }

    return true;
  }

  /**
   * 販売期間、特別価格期間、予約期間、商品別ポイント付与期間の開始、終了日時 を順序チェックを行います。
   * 
   * @return 各期間のチェック結果(どれか一つでもエラーならfalse)
   */
  public boolean validateDateRange() {
    boolean isValid = true;
    CommodityEditHeaderBean header = getBean().getHeader();
    // 販売開始 < 販売終了かチェック

    if (StringUtil.hasValueAllOf(header.getSaleStartDatetime(), header.getSaleEndDatetime())) {
      if (!StringUtil.isCorrectRange(header.getSaleStartDatetime(), header.getSaleEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.5"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.10")));
        isValid = false;
      }
    }
    // 特別価格開始 < 特別価格終了かチェック

    if (StringUtil.hasValueAllOf(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
      if (!StringUtil.isCorrectRange(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.8"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.9")));
        isValid = false;
      }
    }
    // 予約開始 < 予約終了かチェック

    if (StringUtil.hasValueAllOf(header.getReservationStartDatetime(), header.getReservationEndDatetime())) {
      if (!StringUtil.isCorrectRange(header.getReservationStartDatetime(), header.getReservationEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.15"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.16")));
        isValid = false;
      }
    }

    // 价格改定日不在销售区间

    if (StringUtil.hasValueAllOf(header.getSalePriceChangeDatetime(), header.getSaleStartDatetime(), header.getSaleEndDatetime())) {
      if (!DateUtil.isPeriodDate(DateUtil.fromString(header.getSaleStartDatetime()), DateUtil.fromString(header
          .getSaleEndDatetime()), DateUtil.fromString(header.getSalePriceChangeDatetime()))) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.PRICE_REVISION_DATE_ERROR));
        isValid = false;
      }
    }

    // ポイント付与開始 < ポイント付与終了かチェック

    if (StringUtil.hasValueAllOf(header.getCommodityPointStartDatetime(), header.getCommodityPointEndDatetime())) {
      if (!StringUtil.isCorrectRange(header.getCommodityPointStartDatetime(), header.getCommodityPointEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.20"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.21")));
        isValid = false;
      }
    }
    return isValid;

  }
}
