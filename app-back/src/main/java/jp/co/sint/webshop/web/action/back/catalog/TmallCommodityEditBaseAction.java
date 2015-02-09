package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.configure.WebShopSpecialConfig;
import jp.co.sint.webshop.data.domain.SyncFlagJd;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditHeaderBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditSkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallPropertyBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallPropertyValueBean;
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
public abstract class TmallCommodityEditBaseAction extends WebBackAction<TmallCommodityEditBean> {

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

  /** SKU */
  public static final String SKU = "SKU";

  public static final String CONFIRM = "confirm";

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
    TmallCommodityEditBean reqBean = getBean();
    TmallCommodityEditHeaderBean header = reqBean.getHeader();
    TmallCommodityEditSkuBean detail = reqBean.getSku();

    if (header.getCommodityType() == null || header.getCommodityType() != 1) {
      isValid &= validateBean(reqBean);
      // isValid &= validateBean(header);
      for (int i = 0; i < reqBean.getCommodityPropertysList().size(); i++) {
        isValid &= validateBean(reqBean.getCommodityPropertysList().get(i));
      }
      for (int i = 0; i < reqBean.getCommodityStandardPopList().size(); i++) {
        isValid &= validateBean(reqBean.getCommodityStandardPopList().get(i));
      }
      if (reqBean.getMode().equals(MODE_NEW) && StringUtil.isNullOrEmpty(detail.getRepresentSkuCode())
          && BeanValidator.partialValidate(reqBean, "commodityCode").isValid()) {
        detail.setRepresentSkuCode(reqBean.getCommodityCode());
      }
      isValid &= validateBean(detail);

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
      if (StringUtil.hasValue(reqBean.getCommodityDiscountPrice())) { // 特別価格が入力されている場合
        isValid &= validateRequiredPeriod(DISCOUNT);
      }
      if (StringUtil.hasValue(reqBean.getCommodityTmallDiscountPrice())) { // Tmall特別価格が入力されている場合
        //isValid &= validateRequiredPeriod(DISCOUNT);
      }
      if (!isValid) {
        setRequestBean(reqBean);
        return false;
      }
//      if (StringUtil.hasValueAnyOf(reqBean.getCommodityDiscountPrice(), reqBean.getCommodityTmallDiscountPrice())) {
//        if (!StringUtil.hasValueAllOf(reqBean.getCommodityDiscountPrice(), reqBean.getCommodityTmallDiscountPrice())) {
//          addErrorMessage("淘宝和官网的特价必须同时设定");
//          isValid &= false;
//        }
//        if (!StringUtil.hasValueAnyOf(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
//          addErrorMessage("要做特价设定，必须设定特价期间");
//          isValid &= false;
//        }
//      }
      
      if (StringUtil.hasValue(reqBean.getCommodityDiscountPrice())) {
        if (!StringUtil.hasValueAnyOf(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
          addErrorMessage("要做特价设定，必须设定特价期间");
          isValid &= false;
        }
      }
      // 特別価格期間が設定されている場合
      if (StringUtil.hasValueAnyOf(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
        // EC特別価格が入力されているかチェック
        if (StringUtil.isNullOrEmpty(reqBean.getCommodityDiscountPrice())) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
              .getString("web.action.back.catalog.CommodityEditBaseAction.3"), Messages
              .getString("web.action.back.catalog.CCommodityEditBaseAction.0")));
          isValid &= false;
        }
        // 2014/07/21 update: 当设置特价期间后，京东售价不设为必填项
//        if (header.getTcategoryId() != null) {
//          // TMALL特別価格が入力されているかチェック
//          if (StringUtil.isNullOrEmpty(reqBean.getCommodityTmallDiscountPrice())) {
//            addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
//                .getString("web.action.back.catalog.CommodityEditBaseAction.3"), Messages
//                .getString("web.action.back.catalog.CCommodityEditBaseAction.1")));
//            isValid &= false;
//          }
//        }
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
    } else {
      // 非空验证和
      if (StringUtil.isNullOrEmpty(header.getCommodityName())) {
        addErrorMessage("礼品名称必须输入");
        isValid &= false;
      } else {
        if (header.getCommodityName().length() > 50) {
          addErrorMessage("礼品名称长度不能大于50位");
          isValid &= false;
        }
      }
      if (StringUtil.isNullOrEmpty(header.getCommodityNameEn())) {
        addErrorMessage("礼品英文名称必须输入");
        isValid &= false;
      } else {
        if (header.getCommodityNameEn().length() > 200) {
          addErrorMessage("礼品英文名称长度不能大于200位");
          isValid &= false;
        }
      }
      if (StringUtil.isNullOrEmpty(header.getCommodityNameJp())) {
        addErrorMessage("礼品日文名称必须输入");
        isValid &= false;
      } else {
        if (header.getCommodityNameJp().length() > 200) {
          addErrorMessage("礼品日文名称长度不能大于200位");
          isValid &= false;
        }
      }
      if (StringUtil.isNullOrEmpty(header.getBuyerCode())) {
        addErrorMessage("采购人代码必须输入");
        isValid &= false;
      } else {
        if (header.getBuyerCode().length() > 8) {
          addErrorMessage("采购人代码长度不能大于8位");
          isValid &= false;
        }
      }
      if (StringUtil.isNullOrEmpty(header.getSupplierCode())) {
        addErrorMessage("供应商编号必须输入");
        isValid &= false;
      } else {
        if (header.getSupplierCode().length() > 8) {
          addErrorMessage("供应商编号长度不能大于8位");
          isValid &= false;
        }
      }
      if (NumUtil.isNull(detail.getMinimumOrder())) {
        addErrorMessage("Min采购数必须输入");
        isValid &= false;
      } else {
        if (detail.getMinimumOrder().toString().length() > 8) {
          addErrorMessage("Min采购数长度不能大于8位");
          isValid &= false;
        }
      }
      if (NumUtil.isNull(detail.getMaximumOrder())) {
        addErrorMessage("Max采购数必须输入");
        isValid &= false;
      } else {
        if (detail.getMaximumOrder().toString().length() > 8) {
          addErrorMessage("Max采购数长度不能大于8位");
          isValid &= false;
        }
      }
      if (NumUtil.isNull(detail.getOrderMultiple())) {
        addErrorMessage("最小单位采购数必须输入");
        isValid &= false;
      } else {
        if (detail.getOrderMultiple().toString().length() > 8) {
          addErrorMessage("最小单位采购数长度不能大于8位");
          isValid &= false;
        }
      }
      if (NumUtil.isNull(header.getLeadTime())) {
        addErrorMessage("最短进货周期必须输入");
        isValid &= false;
      } else {
        if (header.getLeadTime().toString().length() > 2) {
          addErrorMessage("最短进货周期长度不能大于2位");
          isValid &= false;
        }
      }
      if (NumUtil.isNull(detail.getWeight())) {
        addErrorMessage("礼品重量必须输入");
        isValid &= false;
      } else {
        if (detail.getWeight().floatValue() > 99999999.999) {
          addErrorMessage("礼品重量长度不能大于8位");
          isValid &= false;
        }
      }
      if (NumUtil.isNull(detail.getStockWarning())) {
        addErrorMessage("库存警告数必须输入");
        isValid &= false;
      } else {
        if (detail.getStockWarning().toString().length() > 8) {
          addErrorMessage("库存警告数长度不能大于8位");
          isValid &= false;
        }
      }
      if (!NumUtil.isNull(header.getInBoundLifeDays())) {
        if (header.getInBoundLifeDays().toString().length() > 4) {
          addErrorMessage("入库效期长度不能大于4位");
          isValid &= false;
        }
      }
      if (!NumUtil.isNull(header.getOutBoundLifeDays())) {
        if (header.getOutBoundLifeDays().toString().length() > 4) {
          addErrorMessage("出库效期长度不能大于4位");
          isValid &= false;
        }
      }
      if (!NumUtil.isNull(header.getShelfLifeAlertDays())) {
        if (header.getShelfLifeAlertDays().toString().length() > 4) {
          addErrorMessage("失效预警长度不能大于4位");
          isValid &= false;
        }
      }
    }

    return isValid;
  }

  /**
   * 如果参数 value 为空(null) 返回空字符串
   * 
   * @param value
   * @return
   */
  protected String replaceEmptyString(String value) {
    if (value == null) {
      return "";
    } else {
      return value;
    }
  }

  protected TmallCommodityEditHeaderBean buildBeanByCcheader(CCommodityHeader header) {
    TmallCommodityEditHeaderBean headerBean = new TmallCommodityEditHeaderBean();
    headerBean.setCommodityCode(header.getCommodityCode());
    headerBean.setOriginalCommodityCode(header.getOriginalCommodityCode());
    headerBean.setTmallCommoditySearchWords(header.getTmallCommoditySearchWords());
    headerBean.setCombinationAmount(NumUtil.toString(header.getCombinationAmount()));
    headerBean.setRepresentSkuCode(header.getRepresentSkuCode());
    headerBean.setBigFlag(NumUtil.toString(header.getBigFlag()));
    headerBean.setCommodityCode(header.getCommodityCode());
    headerBean.setExportFlagErp(header.getExportFlagErp());
    headerBean.setExportFlagWms(header.getExportFlagWms());
    headerBean.setSyncFlagEc(NumUtil.toString(header.getSyncFlagEc()));
    headerBean.setSaleFlgEc(NumUtil.toString(header.getSaleFlgEc()));
    headerBean.setImportCommodityTypeEc(NumUtil.toString(header.getImportCommodityType()));
    headerBean.setClearCommodityTypeEc(NumUtil.toString(header.getClearCommodityType()));
    headerBean.setReserveCommodityType1(NumUtil.toString(header.getReserveCommodityType1()));
    headerBean.setReserveCommodityType2(NumUtil.toString(header.getReserveCommodityType2()));
    headerBean.setReserveCommodityType3(NumUtil.toString(header.getReserveCommodityType3()));
    headerBean.setNewReserveCommodityType1(NumUtil.toString(header.getNewReserveCommodityType1()));
    headerBean.setNewReserveCommodityType2(NumUtil.toString(header.getNewReserveCommodityType2()));
    headerBean.setNewReserveCommodityType3(NumUtil.toString(header.getNewReserveCommodityType3()));
    headerBean.setNewReserveCommodityType4(NumUtil.toString(header.getNewReserveCommodityType4()));
    headerBean.setNewReserveCommodityType5(NumUtil.toString(header.getNewReserveCommodityType5()));
    headerBean.setTmallMjsFlg(NumUtil.toString(header.getTmallMjsFlg()));
    // headerBean.setSaleFlgTmall(NumUtil.toString(header.getSaleFlgTmall()));
    // //update by os014 2012-02-07
    headerBean.setCommodityName(replaceEmptyString(header.getCommodityName()));
    headerBean.setSyncTmallTime(toDateTimeString(header.getSyncTimeTmall()));
    headerBean.setSaleFlag(header.getSaleFlag());
    headerBean.setSpecFlag(header.getSpecFlag());
    headerBean.setSaleStartDatetime(toDateTimeString(header.getSaleStartDatetime()));
    headerBean.setSaleEndDatetime(toDateTimeString(header.getSaleEndDatetime()));
    headerBean.setDiscountPriceStartDatetime(DateUtil.toDateTimeString(header.getDiscountPriceStartDatetime()));
    headerBean.setDiscountPriceEndDatetime(DateUtil.toDateTimeString(header.getDiscountPriceEndDatetime()));
    // 20120314 os013 add start
    headerBean.setCommodityNameEn(replaceEmptyString(header.getCommodityNameEn()));
    headerBean.setCommodityNameJp(replaceEmptyString(header.getCommodityNameJp()));
    // 20120314 os013 add end
    // 20120322 os013 add start
    headerBean.setRepresentSkuUnitPrice(header.getRepresentSkuUnitPrice());
    // 20120322 os013 add end
    headerBean.setBuyerCode(replaceNull(header.getBuyerCode()));
    headerBean.setSupplierCode(replaceNull(header.getSupplierCode()));
    headerBean.setLeadTime(header.getLeadTime());
    //2014/4/28 京东WBS对应 ob_李 add start
    headerBean.setAdvertContent(replaceNull(header.getAdvertContent()));
    //2014/4/28 京东WBS对应 ob_李 add end
    headerBean.setCommodityDescription1(replaceNull(header.getCommodityDescription1()));
    headerBean.setCommodityDescription1En(replaceNull(header.getCommodityDescription1En()));
    headerBean.setCommodityDescription1Jp(replaceNull(header.getCommodityDescription1Jp()));
    headerBean.setCommodityDescription2(replaceNull(header.getCommodityDescription2()));
    headerBean.setCommodityDescription2En(replaceNull(header.getCommodityDescription2En()));
    headerBean.setCommodityDescription2Jp(replaceNull(header.getCommodityDescription2Jp()));
    headerBean.setCommodityDescription3(replaceNull(header.getCommodityDescription3()));
    headerBean.setCommodityDescription3En(replaceNull(header.getCommodityDescription3En()));
    headerBean.setCommodityDescription3Jp(replaceNull(header.getCommodityDescription3Jp()));
    headerBean.setCommodityDescriptionShort(header.getCommodityDescriptionShort());
    headerBean.setCommodityDescriptionShortEn(header.getCommodityDescriptionShortEn());
    headerBean.setCommodityDescriptionShortJp(header.getCommodityDescriptionShortJp());
    headerBean.setCommoditySearchWords(replaceNull(header.getCommoditySearchWords()));
    headerBean.setBrandCode(replaceNull(header.getBrandCode()));
    headerBean.setTcategoryId(header.getTmallCategoryId());
    headerBean.setOriginalPlace(replaceNull(header.getOriginalPlace()));
    headerBean.setOriginalCode(replaceNull(header.getOriginalCode()));
    headerBean.setStandard1Id(replaceNull(header.getStandard1Id()));
    headerBean.setStandard1Name(replaceNull(header.getStandard1Name()));
    headerBean.setStandard2Id(replaceNull(header.getStandard2Id()));
    headerBean.setStandard2Name(replaceNull(header.getStandard2Name()));
    headerBean.setReturnFlg(String.valueOf(header.getReturnFlg()));
    headerBean.setKeywordCn2(header.getKeywordCn2());
    headerBean.setKeywordEn2(header.getKeywordEn2());
    headerBean.setKeywordJp2(header.getKeywordJp2());
    if (header.getHotFlgEn() != null) {
      headerBean.setHotFlgEn(header.getHotFlgEn().toString());
    } else {
      headerBean.setHotFlgEn("");
    }
    if (header.getHotFlgJp() != null) {
      headerBean.setHotFlgJp(header.getHotFlgJp().toString());
    } else {
      headerBean.setHotFlgJp("");
    }

    // 20130703 txw add start
    headerBean.setTitle(header.getTitle());
    headerBean.setTitleEn(header.getTitleEn());
    headerBean.setTitleJp(header.getTitleJp());
    headerBean.setDescription(header.getDescription());
    headerBean.setDescriptionEn(header.getDescriptionEn());
    headerBean.setDescriptionJp(header.getDescriptionJp());
    headerBean.setKeyword(header.getKeyword());
    headerBean.setKeywordEn(header.getKeywordEn());
    headerBean.setKeywordJp(header.getKeywordJp());
    // 20130703 txw add end

    // update by os014 2012-03-06 退货标志换成三种 供应商换货，供应商退货，顾客退货 begin
    /**
     * 将从数据库取出的值转换成二进制，如果转换后不足三位补零。
     */
    String binaryString = Integer.toBinaryString(Integer.parseInt(String.valueOf(header.getReturnFlg())));
    String[] tempStr = new String[3];
    if (binaryString.length() == 3) {
      headerBean.setShopChangeFlag(binaryString.substring(0, 1));
      headerBean.setShopReturnFlag(binaryString.substring(1, 2));
      headerBean.setCustReturnFlag(binaryString.substring(2));
    } else {
      int index = 0;
      for (int i = binaryString.length(); i > 0; i--, index++) {
        tempStr[index] = binaryString.substring(i - 1, i);
      }
      headerBean.setShopChangeFlag(tempStr[2] == null ? "0" : tempStr[2]);
      headerBean.setShopReturnFlag(tempStr[1] == null ? "0" : tempStr[1]);
      headerBean.setCustReturnFlag(tempStr[0] == null ? "0" : tempStr[0]);
    }
    // update by os014 2012-03-06 退货标志换成三种 供应商换货，供应商退货，顾客退货 end
    // add by cs_yuli 20120605 start
    headerBean.setInBoundLifeDays(header.getInBoundLifeDays());
    headerBean.setOutBoundLifeDays(header.getOutBoundLifeDays());
    headerBean.setShelfLifeAlertDays(header.getShelfLifeAlertDays());
    // add by cs_yuli 20120605 end
    headerBean.setShelfLifeDays(NumUtil.toString(header.getShelfLifeDays()));
    headerBean.setShelfLifeFlag(NumUtil.toString(header.getShelfLifeFlag()));
    headerBean.setWarningFlag(replaceNull(header.getWarningFlag()));
    headerBean.setMaterial1(header.getMaterial1());
    headerBean.setMaterial2(header.getMaterial2());
    headerBean.setMaterial3(header.getMaterial3());
    headerBean.setMaterial4(header.getMaterial4());
    headerBean.setMaterial5(header.getMaterial5());
    headerBean.setMaterial6(header.getMaterial6());
    headerBean.setMaterial7(header.getMaterial7());
    headerBean.setMaterial8(header.getMaterial8());
    headerBean.setMaterial9(header.getMaterial9());
    headerBean.setMaterial10(header.getMaterial10());
    headerBean.setMaterial11(header.getMaterial11());
    headerBean.setMaterial12(header.getMaterial12());
    headerBean.setMaterial13(header.getMaterial13());
    headerBean.setMaterial14(header.getMaterial14());
    headerBean.setMaterial15(header.getMaterial15());
    headerBean.setUpdateDatetime(header.getUpdatedDatetime());

    headerBean.setIngredientName1(header.getIngredientName1());
    headerBean.setIngredientVal1(header.getIngredientVal1());
    headerBean.setIngredientName2(header.getIngredientName2());
    headerBean.setIngredientVal2(header.getIngredientVal2());
    headerBean.setIngredientName3(header.getIngredientName3());
    headerBean.setIngredientVal3(header.getIngredientVal3());
    headerBean.setIngredientName4(header.getIngredientName4());
    headerBean.setIngredientVal4(header.getIngredientVal4());
    headerBean.setIngredientName5(header.getIngredientName5());
    headerBean.setIngredientVal5(header.getIngredientVal5());
    headerBean.setIngredientName6(header.getIngredientName6());
    headerBean.setIngredientVal6(header.getIngredientVal6());
    headerBean.setIngredientName7(header.getIngredientName7());
    headerBean.setIngredientVal7(header.getIngredientVal7());
    headerBean.setIngredientName8(header.getIngredientName8());
    headerBean.setIngredientVal8(header.getIngredientVal8());
    headerBean.setIngredientName9(header.getIngredientName9());
    headerBean.setIngredientVal9(header.getIngredientVal9());
    headerBean.setIngredientName10(header.getIngredientName10());
    headerBean.setIngredientVal10(header.getIngredientVal10());
    headerBean.setIngredientName11(header.getIngredientName11());
    headerBean.setIngredientVal11(header.getIngredientVal11());
    headerBean.setIngredientName12(header.getIngredientName12());
    headerBean.setIngredientVal12(header.getIngredientVal12());
    headerBean.setIngredientName13(header.getIngredientName13());
    headerBean.setIngredientVal13(header.getIngredientVal13());
    headerBean.setIngredientName14(header.getIngredientName14());
    headerBean.setIngredientVal14(header.getIngredientVal14());
    headerBean.setIngredientName15(header.getIngredientName15());
    headerBean.setIngredientVal15(header.getIngredientVal15());
    // 食品安全相关
    headerBean.setFoodSecurityPrdLicenseNo(header.getFoodSecurityPrdLicenseNo());
    headerBean.setFoodSecurityDesignCode(header.getFoodSecurityDesignCode());
    headerBean.setFoodSecurityFactory(header.getFoodSecurityFactory());
    headerBean.setFoodSecurityFactorySite(header.getFoodSecurityFactorySite());
    headerBean.setFoodSecurityContact(header.getFoodSecurityContact());
    headerBean.setFoodSecurityMix(header.getFoodSecurityMix());
    headerBean.setFoodSecurityPlanStorage(header.getFoodSecurityPlanStorage());
    headerBean.setFoodSecurityPeriod(header.getFoodSecurityPeriod());
    headerBean.setFoodSecurityFoodAdditive(header.getFoodSecurityFoodAdditive());
    headerBean.setFoodSecuritySupplier(header.getFoodSecuritySupplier());
    headerBean.setFoodSecurityProductDateStart(DateUtil.toDateString(header.getFoodSecurityProductDateStart()));
    headerBean.setFoodSecurityProductDateEnd(DateUtil.toDateString(header.getFoodSecurityProductDateEnd()));
    headerBean.setFoodSecurityStockDateStart(DateUtil.toDateString(header.getFoodSecurityStockDateStart()));
    headerBean.setFoodSecurityStockDateEnd(DateUtil.toDateString(header.getFoodSecurityStockDateEnd()));
    headerBean.setOriginalPlaceEn(header.getOriginalPlaceEn());
    headerBean.setOriginalPlaceJp(header.getOriginalPlaceJp());
    headerBean.setCommodityType(header.getCommodityType());
    //套装商品区分(0：对象外　1：对象内)
    if (header.getSetCommodityFlg() != null) {
      if (header.getSetCommodityFlg() == 1L) {
        headerBean.setSuitFlg(true);
      } else {
        headerBean.setSuitFlg(false);
      }
    } else {
      headerBean.setSuitFlg(false);
    }
    return headerBean;
  }

  /**
   * 商品情報DTOに商品登録の内容を設定します。
   * 
   * @param commodityInfo
   * @param reqBean
   */
  protected CCommodityHeader buildHeader(TmallCommodityEditHeaderBean resource) {
    if (resource == null) {
      return null;
    }
    CCommodityHeader header = new CCommodityHeader();
    header.setCommodityCode(resource.getCommodityCode());
    header.setOriginalCommodityCode(resource.getOriginalCommodityCode());
    header.setTmallCommoditySearchWords(resource.getTmallCommoditySearchWords());
    if (StringUtil.hasValue(resource.getCombinationAmount())) {
      header.setCombinationAmount(NumUtil.toLong(resource.getCombinationAmount()));
    }
    header.setBrandCode(replaceNull(resource.getBrandCode()));
    header.setRepresentSkuCode(resource.getRepresentSkuCode());
    header.setRepresentSkuUnitPrice(resource.getRepresentSkuUnitPrice());
    header.setSyncFlagEc(NumUtil.toLong(resource.getSyncFlagEc()));
    
    // 20141120 hdh udpate start
    header.setSyncFlagTmall(SyncFlagJd.SYNCVISIBLE.longValue());
    // 20141120 hdh udpate end
    
    header.setExportFlagErp(resource.getExportFlagErp());
    header.setExportFlagWms(resource.getExportFlagWms());
    header.setCommodityName(replaceNull(resource.getCommodityName()));
    header.setCommodityNameEn(replaceNull(resource.getCommodityNameEn()));
    header.setCommodityNameJp(replaceNull(resource.getCommodityNameJp()));
    header.setSupplierCode(replaceNull(resource.getSupplierCode()));
    header.setBuyerCode(replaceNull(resource.getBuyerCode()));
    header.setLeadTime(resource.getLeadTime());
    header.setSaleStartDatetime(DateUtil.fromString(resource.getSaleStartDatetime(), true));
    header.setSaleEndDatetime(DateUtil.fromString(resource.getSaleEndDatetime(), true));
    header.setDiscountPriceStartDatetime(DateUtil.fromString(resource.getDiscountPriceStartDatetime(), true));
    header.setDiscountPriceEndDatetime(DateUtil.fromString(resource.getDiscountPriceEndDatetime(), true));
    header.setTmallCategoryId(resource.getTcategoryId());
    header.setStandard1Name(replaceNull(resource.getStandard1Name()));
    header.setStandard2Name(replaceNull(resource.getStandard2Name()));
    header.setBrandCode(replaceNull(resource.getBrandCode()));
    header.setSaleFlgEc(NumUtil.toLong(resource.getSaleFlgEc()));
    header.setImportCommodityType(NumUtil.toLong(resource.getImportCommodityTypeEc()));
    header.setClearCommodityType(NumUtil.toLong(resource.getClearCommodityTypeEc()));
    header.setReserveCommodityType1(NumUtil.toLong(resource.getReserveCommodityType1()));
    header.setReserveCommodityType2(NumUtil.toLong(resource.getReserveCommodityType2()));
    header.setReserveCommodityType3(NumUtil.toLong(resource.getReserveCommodityType3()));
    header.setNewReserveCommodityType1(NumUtil.toLong(resource.getNewReserveCommodityType1()));
    header.setNewReserveCommodityType2(NumUtil.toLong(resource.getNewReserveCommodityType2()));
    header.setNewReserveCommodityType3(NumUtil.toLong(resource.getNewReserveCommodityType3()));
    header.setNewReserveCommodityType4(NumUtil.toLong(resource.getNewReserveCommodityType4()));
    header.setNewReserveCommodityType5(NumUtil.toLong(resource.getNewReserveCommodityType5()));
    header.setTmallMjsFlg(NumUtil.toLong(resource.getTmallMjsFlg()));
    // header.setSaleFlgTmall(NumUtil.toLong(resource.getSaleFlgTmall()));
    // //update by os014 2012-02-07
    header.setSpecFlag(replaceNull(resource.getSpecFlag()));
    header.setWarningFlag(replaceNull(resource.getWarningFlag()));
    header.setCommoditySearchWords(replaceNull(resource.getCommoditySearchWords()));
    //2014/4/28 京东WBS对应 ob_李 add start
    header.setAdvertContent(replaceNull(resource.getAdvertContent()));
    //2014/4/28 京东WBS对应 ob_李 add end
    header.setCommodityDescription1(replaceNull(resource.getCommodityDescription1()));
    header.setCommodityDescription1En(replaceNull(resource.getCommodityDescription1En()));
    header.setCommodityDescription1Jp(replaceNull(resource.getCommodityDescription1Jp()));
    header.setCommodityDescription2(replaceNull(resource.getCommodityDescription2()));
    header.setCommodityDescription2En(replaceNull(resource.getCommodityDescription2En()));
    header.setCommodityDescription2Jp(replaceNull(resource.getCommodityDescription2Jp()));
    header.setCommodityDescription3(replaceNull(resource.getCommodityDescription3()));
    header.setCommodityDescription3En(replaceNull(resource.getCommodityDescription3En()));
    header.setCommodityDescription3Jp(replaceNull(resource.getCommodityDescription3Jp()));
    header.setCommodityDescriptionShort(replaceNull(resource.getCommodityDescriptionShort()));
    header.setCommodityDescriptionShortEn(replaceNull(resource.getCommodityDescriptionShortEn()));
    header.setCommodityDescriptionShortJp(replaceNull(resource.getCommodityDescriptionShortJp()));
    header.setKeywordCn2(replaceNull(resource.getKeywordCn2()));
    header.setKeywordEn2(replaceNull(resource.getKeywordEn2()));
    header.setKeywordJp2(replaceNull(resource.getKeywordJp2()));
    if (!StringUtil.isNullOrEmpty(resource.getOriginalCode())) {
      String originalCode = resource.getOriginalCode();
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      OriginalPlace op = utilService.getOriginalPlaceByCode(originalCode);
      header.setOriginalPlace(op.getOriginalPlaceNameCn());
      header.setOriginalPlaceEn(op.getOriginalPlaceNameEn());
      header.setOriginalPlaceJp(op.getOriginalPlaceNameJp());
      header.setOriginalCode(op.getOriginalCode());
    }
    if (resource.getCommodityType() == null || resource.getCommodityType() != 1L) {
      // update by os014 2012-03-06 退货标志换成三种 供应商换货，供应商退货，顾客退货 begin
      // header.setReturnFlg(NumUtil.toLong(resource.getReturnFlg()));
      String returnFlgStr = resource.getShopChangeFlag() + resource.getShopReturnFlag() + resource.getCustReturnFlag();
      header.setReturnFlg(NumUtil.toLong(String.valueOf(Integer.valueOf(returnFlgStr, 2))));
      // update by os014 2012-03-06 退货标志换成三种 供应商换货，供应商退货，顾客退货 end
    } else {
      header.setReturnFlg(0L);
    }

    header.setSaleFlag(replaceNull(resource.getSaleFlag()));
    header.setBigFlag(NumUtil.toLong(resource.getBigFlag()));
    // add by cs_yuli 20120605 start
    header.setInBoundLifeDays(resource.getInBoundLifeDays());
    header.setOutBoundLifeDays(resource.getOutBoundLifeDays());
    header.setShelfLifeAlertDays(resource.getShelfLifeAlertDays());
    // add by cs_yuli 20120605 end

    header.setMaterial1(replaceNull(resource.getMaterial1()));
    header.setMaterial2(replaceNull(resource.getMaterial2()));
    header.setMaterial3(replaceNull(resource.getMaterial3()));
    header.setMaterial4(replaceNull(resource.getMaterial4()));
    header.setMaterial5(replaceNull(resource.getMaterial5()));
    header.setMaterial6(replaceNull(resource.getMaterial6()));
    header.setMaterial7(replaceNull(resource.getMaterial7()));
    header.setMaterial8(replaceNull(resource.getMaterial8()));
    header.setMaterial9(replaceNull(resource.getMaterial9()));
    header.setMaterial10(replaceNull(resource.getMaterial10()));
    header.setMaterial11(replaceNull(resource.getMaterial11()));
    header.setMaterial12(replaceNull(resource.getMaterial12()));
    header.setMaterial13(replaceNull(resource.getMaterial13()));
    header.setMaterial14(replaceNull(resource.getMaterial14()));
    header.setMaterial15(replaceNull(resource.getMaterial15()));
    header.setUpdatedDatetime(resource.getUpdateDatetime());

    header.setIngredientName1(replaceNull(resource.getIngredientName1()));
    header.setIngredientVal1(replaceNull(resource.getIngredientVal1()));
    header.setIngredientName2(replaceNull(resource.getIngredientName2()));
    header.setIngredientVal2(replaceNull(resource.getIngredientVal2()));
    header.setIngredientName3(replaceNull(resource.getIngredientName3()));
    header.setIngredientVal3(replaceNull(resource.getIngredientVal3()));
    header.setIngredientName4(replaceNull(resource.getIngredientName4()));
    header.setIngredientVal4(replaceNull(resource.getIngredientVal4()));
    header.setIngredientName5(replaceNull(resource.getIngredientName5()));
    header.setIngredientVal5(replaceNull(resource.getIngredientVal5()));
    header.setIngredientName6(replaceNull(resource.getIngredientName6()));
    header.setIngredientVal6(replaceNull(resource.getIngredientVal6()));
    header.setIngredientName7(replaceNull(resource.getIngredientName7()));
    header.setIngredientVal7(replaceNull(resource.getIngredientVal7()));
    header.setIngredientName8(replaceNull(resource.getIngredientName8()));
    header.setIngredientVal8(replaceNull(resource.getIngredientVal8()));
    header.setIngredientName9(replaceNull(resource.getIngredientName9()));
    header.setIngredientVal9(replaceNull(resource.getIngredientVal9()));
    header.setIngredientName10(replaceNull(resource.getIngredientName10()));
    header.setIngredientVal10(replaceNull(resource.getIngredientVal10()));
    header.setIngredientName11(replaceNull(resource.getIngredientName11()));
    header.setIngredientVal11(replaceNull(resource.getIngredientVal11()));
    header.setIngredientName12(replaceNull(resource.getIngredientName12()));
    header.setIngredientVal12(replaceNull(resource.getIngredientVal12()));
    header.setIngredientName13(replaceNull(resource.getIngredientName13()));
    header.setIngredientVal13(replaceNull(resource.getIngredientVal13()));
    header.setIngredientName14(replaceNull(resource.getIngredientName14()));
    header.setIngredientVal14(replaceNull(resource.getIngredientVal14()));
    header.setIngredientName15(replaceNull(resource.getIngredientName15()));
    header.setIngredientVal15(replaceNull(resource.getIngredientVal15()));

    // 食品安全相关
    header.setFoodSecurityPrdLicenseNo(replaceNull(resource.getFoodSecurityPrdLicenseNo()));
    header.setFoodSecurityDesignCode(replaceNull(resource.getFoodSecurityDesignCode()));
    header.setFoodSecurityFactory(replaceNull(resource.getFoodSecurityFactory()));
    header.setFoodSecurityFactorySite(replaceNull(resource.getFoodSecurityFactorySite()));
    header.setFoodSecurityContact(replaceNull(resource.getFoodSecurityContact()));
    header.setFoodSecurityMix(replaceNull(resource.getFoodSecurityMix()));
    header.setFoodSecurityPlanStorage(replaceNull(resource.getFoodSecurityPlanStorage()));
    header.setFoodSecurityPeriod(replaceNull(resource.getFoodSecurityPeriod()));
    header.setFoodSecurityFoodAdditive(replaceNull(resource.getFoodSecurityFoodAdditive()));
    header.setFoodSecuritySupplier(replaceNull(resource.getFoodSecuritySupplier()));
    header.setFoodSecurityProductDateStart(DateUtil.fromString(resource.getFoodSecurityProductDateStart(), false));
    header.setFoodSecurityProductDateEnd(DateUtil.fromString(resource.getFoodSecurityProductDateEnd(), false));
    header.setFoodSecurityStockDateStart(DateUtil.fromString(resource.getFoodSecurityStockDateStart(), false));
    header.setFoodSecurityStockDateEnd(DateUtil.fromString(resource.getFoodSecurityStockDateEnd(), false));
    header.setCommodityType(resource.getCommodityType());
    if (StringUtil.hasValue(resource.getHotFlgEn())) {
      header.setHotFlgEn(Long.parseLong(resource.getHotFlgEn()));
    }
    if (StringUtil.hasValue(resource.getHotFlgJp())) {
      header.setHotFlgJp(Long.parseLong(resource.getHotFlgJp()));
    }

    // 20130703 txw add start
    header.setTitle(resource.getTitle());
    header.setTitleEn(resource.getTitleEn());
    header.setTitleJp(resource.getTitleJp());
    header.setDescription(resource.getDescription());
    header.setDescriptionEn(resource.getDescriptionEn());
    header.setDescriptionJp(resource.getDescriptionJp());
    header.setKeyword(resource.getKeyword());
    header.setKeywordEn(resource.getKeywordEn());
    header.setKeywordJp(resource.getKeywordJp());
    // 20130703 txw add end
    boolean updateShelfLifFlag = false;
    WebShopSpecialConfig spciConfig = DIContainer.getWebShopSpecialConfig();
    if(spciConfig.getShelfLifeUserList()!=null && spciConfig.getShelfLifeUserList().size() > 0){
      for(String userId:spciConfig.getShelfLifeUserList()){
        if(StringUtil.isNull(userId)){
          continue;
        }
        if(userId.equals(getLoginInfo().getLoginId())){
          updateShelfLifFlag = true;
          header.setShelfLifeDays(NumUtil.toLong(resource.getShelfLifeDays()));
          header.setShelfLifeFlag(NumUtil.toLong(resource.getShelfLifeFlag()));
          break;
        }
      }
    }
    //无权限更新则恢复初始值
    if(!updateShelfLifFlag){
      CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
      CCommodityHeader ccheader=  service.getCCommodityHeader(getLoginInfo().getShopCode(), resource.getCommodityCode());
      header.setShelfLifeDays(ccheader.getShelfLifeDays());
      header.setShelfLifeFlag(ccheader.getShelfLifeFlag());
    }
    
    return header;
  }

  protected String replaceNull(String value) {
    if ("".equals(value)) {
      return null;
    }
    return value;
  }

  /**
   * 画面に表示するボタンの制御を行います。
   * 
   * @param reqBean
   */
  public void setDisplayControl(TmallCommodityEditBean reqBean) {

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
        reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
        reqBean.setDisplayMoveSkuButton(false);
        reqBean.setDisplayUploadTable(false);
        reqBean.setDisplayImageTable(false);
        reqBean.setDisplayImageDeleteButton(false);
        reqBean.setDisplayThumbnailDeleteButton(false);
        reqBean.setDisplayMobileThumbnailDeleteButton(false);
        reqBean.setDisplayRepresentSkuImageDeleteButton(false);
        reqBean.setDisplayMobileRepresentSkuImageDeleteButton(false);
      } else if (reqBean.getMode().equals(MODE_UPDATE)) {
        // 如果商品以同期到tmall商品编号不能修改
        String syncTmallDate = reqBean.getHeader().getSyncTmallTime();
        if (syncTmallDate == null || "".equals(syncTmallDate)) {
          reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);// commodityCodeDisplayMode
        } else {
          reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
        }
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
      reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
      reqBean.setDisplayNextButton(true);
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
    }
    // else if (periodType.equals(RESERVATION)) {
    // params = new String[] {
    // getBean().getHeader().getReservationStartDatetime(),
    // getBean().getHeader().getReservationEndDatetime()
    // };
    // error = WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.14"),
    // Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.13"));
    // } else if (periodType.equals(CHANGE)) {
    // params = new String[] {
    // getBean().getHeader().getSalePriceChangeDatetime()
    // };
    // error = WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.19"),
    // Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.18"));
    // } else if (periodType.equals(POINT)) {
    // params = new String[] {
    // getBean().getHeader().getCommodityPointStartDatetime(),
    // getBean().getHeader().getCommodityPointEndDatetime()
    // };
    // error = WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.2"), Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.1"));
    // }
    else {
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
    // CommodityEditHeaderBean
    TmallCommodityEditHeaderBean header = getBean().getHeader();
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
    //
    if (StringUtil.hasValueAllOf(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
      if (!StringUtil.isCorrectRange(header.getDiscountPriceStartDatetime(), header.getDiscountPriceEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.8"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.9")));
        isValid = false;
      }
    }

    if (StringUtil.hasValueAllOf(header.getFoodSecurityProductDateStart(), header.getFoodSecurityProductDateEnd())) {
      if (!StringUtil.isCorrectRange(header.getFoodSecurityProductDateStart(), header.getFoodSecurityProductDateEnd())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.22"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.23")));
        isValid = false;
      }
    }

    if (StringUtil.hasValueAllOf(header.getFoodSecurityStockDateStart(), header.getFoodSecurityStockDateEnd())) {
      if (!StringUtil.isCorrectRange(header.getFoodSecurityStockDateStart(), header.getFoodSecurityStockDateEnd())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.24"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.25")));
        isValid = false;
      }
    }

    if (StringUtil.hasValueAllOf(header.getFoodSecurityProductDateStart(), header.getFoodSecurityStockDateStart())) {
      if (!StringUtil.isCorrectRange(header.getFoodSecurityProductDateStart(), header.getFoodSecurityStockDateStart())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.22"), Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.24")));
        isValid = false;
      }
    }

    if (!StringUtil.isNullOrEmpty(header.getFoodSecurityProductDateStart())) {
      if (!StringUtil.isCorrectRange(header.getFoodSecurityProductDateStart(), DateUtil.getSysdateString())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditBaseAction.22"), "当前日期"));
        isValid = false;
      }
    }
    // 予約開始 < 予約終了かチェック
    // if (StringUtil.hasValueAllOf(header.getReservationStartDatetime(),
    // header.getReservationEndDatetime())) {
    // if (!StringUtil.isCorrectRange(header.getReservationStartDatetime(),
    // header.getReservationEndDatetime())) {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR,
    // Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.15"),
    // Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.16")));
    // isValid = false;
    // }
    // }

    // 价格改定日不在销售区间
    // if (StringUtil.hasValueAllOf(header.getSalePriceChangeDatetime(),
    // header.getSaleStartDatetime(), header.getSaleEndDatetime())) {
    // if
    // (!DateUtil.isPeriodDate(DateUtil.fromString(header.getSaleStartDatetime()),
    // DateUtil.fromString(header
    // .getSaleEndDatetime()),
    // DateUtil.fromString(header.getSalePriceChangeDatetime()))) {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.PRICE_REVISION_DATE_ERROR));
    // isValid = false;
    // }
    // }

    // ポイント付与開始 < ポイント付与終了かチェック
    // if (StringUtil.hasValueAllOf(header.getCommodityPointStartDatetime(),
    // header.getCommodityPointEndDatetime())) {
    // if (!StringUtil.isCorrectRange(header.getCommodityPointStartDatetime(),
    // header.getCommodityPointEndDatetime())) {
    // addErrorMessage(WebMessage.get(CatalogErrorMessage.DATE_RANGE_ERROR,
    // Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.20"),
    // Messages
    // .getString("web.action.back.catalog.CommodityEditBaseAction.21")));
    // isValid = false;
    // }
    // }
    return isValid;

  }

  /**
   * Date型の値をStringに変換する。<BR>
   * システム最大(or最小)日時が設定されている場合は、<BR>
   * 未入力と判断し、""(空文字)を設定する
   * 
   * @param date
   * @return dateString
   */
  protected String toDateTimeString(Date date) {
    String dateString = null;

    try {
      if (date.equals(DateUtil.getMin()) || date.equals(DateUtil.getMax())) {
        return "";
      }

      dateString = DateUtil.toDateTimeString(date);
    } catch (NullPointerException e) {
      dateString = "";
    }
    return dateString;
  }

  /**
   * 组装属性ID与属性值ID p 属性ID pn 属性name v 属性值ID集合 vn 属性值名称集合
   */
  // protected List<PropertyKey> buildKey(List<String> p,List<String>
  // pn,List<String> v,List<String> vn){
  // List<PropertyKey> result = new ArrayList<PropertyKey>();
  // if(p.size()>0&&v.size()>0){
  // result.add(new CategoryViewUtil().new
  // PropertyKey(p.get(0),pn.get(0),v,vn));
  // }
  // return result;
  // }
  // protected PropertyKeys buildPropertyKeys(){
  // CategoryViewUtil util = new CategoryViewUtil();
  // TmallCommodityEditBean reqBean = getBean();
  // PropertyKeys keys = util.new PropertyKeys();
  // //生成商品属性集合对象
  // for(int i=0;i<CategoryViewUtil.MAXPROPERTYSIZE;i++){
  // Class clazz = reqBean.getClass();
  // String pId = "getP"+i;
  // String pName = "getPn"+i;
  // String vId = "getV"+i;
  // String vName = "getVn"+i;
  // try {
  // //获取属性Id
  // List<String> plist = (List<String>)clazz.getMethod(pId,
  // null).invoke(reqBean);
  // //获取属性值Id
  // List<String> vlist = (List<String>)clazz.getMethod(vId,
  // null).invoke(reqBean);
  // //获取属性名称
  // List<String> pnlist = (List<String>)clazz.getMethod(pName,
  // null).invoke(reqBean);
  // //获取属性值名称
  // List<String> vnlist = (List<String>)clazz.getMethod(vName,
  // null).invoke(reqBean);
  // if(plist.size()!=0&&vlist.size()!=0){
  // List<PropertyKey> pKeys = buildKey(plist,pnlist, vlist,vnlist);
  // keys.copyKeyList(pKeys);
  // }
  // } catch (IllegalArgumentException e) {
  // e.printStackTrace();
  // continue;
  // } catch (SecurityException e) {
  // e.printStackTrace();
  // continue;
  // } catch (IllegalAccessException e) {
  // e.printStackTrace();
  // continue;
  // } catch (InvocationTargetException e) {
  // e.printStackTrace();
  // continue;
  // } catch (NoSuchMethodException e) {
  // e.printStackTrace();
  // continue;
  // }
  // }
  // return keys;
  // }
  protected TmallCommodityEditSkuBean buildSkuBean(CCommodityDetail detail) {
    TmallCommodityEditSkuBean bean = new TmallCommodityEditSkuBean();
    bean.setDiscountPrice(NumUtil.toString(detail.getDiscountPrice()));
    bean.setPurchasePrice(NumUtil.toString(detail.getPurchasePrice()));
    bean.setRepresentSkuCode(detail.getSkuCode());
    bean.setSkuName(detail.getSkuName());
    bean.setUnitPrice(NumUtil.toString(detail.getUnitPrice()));
    bean.setTmallUnitPrice(NumUtil.toString(detail.getTmallUnitPrice()));
    bean.setTmallDiscountPrice(NumUtil.toString(detail.getTmallDiscountPrice()));
    bean.setMinimumOrder(detail.getMinimumOrder());
    bean.setMaximumOrder(detail.getMaximumOrder());
    bean.setOrderMultiple(detail.getOrderMultiple());
    bean.setStockWarning(detail.getStockWarning());
    bean.setWeight(detail.getWeight());
    return bean;
  }

  protected boolean validateSelectItem(List<TmallCommodityProperty> propertys, TmallPropertyValue value) {
    for (int i = 0; i < propertys.size(); i++) {
      TmallCommodityProperty property = propertys.get(i);
      if (value.getPropertyId().equals(property.getPropertyId()) && value.getValueId().equals(property.getValueId())) {
        return true;
      }
    }
    return false;
  }

  // protected String buildCategoryPropertyHtml(String commodityCode,String
  // categoryId){
  // CatalogService catalogService =
  // ServiceLocator.getCatalogService(getLoginInfo());
  // /**
  // * 查询类别
  // */
  // List<TmallProperty> list =
  // catalogService.loadTmallPropertiesByCategoryId(categoryId);
  //   
  //    
  // /**
  // * 查询商品包含的属性
  // */
  // List<TmallCommodityProperty> propertys =
  // catalogService.loadPropertyByCommodityCode(commodityCode);
  // for(int i=0;i<list.size();i++){
  // TmallProperty pros=list.get(i);
  // List<TmallPropertyValue> valueList = pros.getPropertyValueContain();
  // for(TmallPropertyValue value : valueList){
  // value.setIsSelect(validateSelectItem(propertys,value));
  // }
  // }
  // //build页面HTML文本
  // String htmlStr = new CategoryViewUtil().buildHtml(list);
  // return htmlStr;
  // }
  public static List<TmallCommodityProperty> copyTmallCommodityProperty(List<TmallPropertyBean> target,
      List<TmallPropertyBean> source) {
    List<TmallCommodityProperty> sourceList1 = convertCommodityPropertyBeanListToDto(source);
    List<TmallCommodityProperty> sourceList2 = convertCommodityPropertyBeanListToDto(target);
    for (int i = 0; i < sourceList2.size(); i++) {
      sourceList1.add(sourceList2.get(i));
    }
    return sourceList1;
  }

  public static List<TmallCommodityProperty> convertCommodityPropertyBeanListToDto(List<TmallPropertyBean> beans) {
    List<TmallCommodityProperty> propertys = new ArrayList<TmallCommodityProperty>();
    for (TmallPropertyBean pro : beans) {
      for (TmallPropertyValueBean value : pro.getValues()) {
        TmallCommodityProperty property = new TmallCommodityProperty();
        property.setPropertyId(pro.getPropertyId());
        property.setValueId(value.getValueId());
        property.setValueText(value.getValueName());
        property.setCommodityCode(pro.getCommodityCode());
        propertys.add(property);
      }
    }
    return propertys;
  }
  
  /**
   * 获取url参数skuCode
   * @return
   */
  protected String getUpdateSkuCode() {
    String[] params = getRequestParameter().getPathArgs();
    String skuCode = "";
    if (params.length > 1) {
      skuCode = params[1];
    }
    return skuCode;
  }
}
