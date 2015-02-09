package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.domain.UseStatus;
import jp.co.sint.webshop.data.domain.WarningFlag;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.service.catalog.SetCommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.DetailCartComposition;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.DetailCartDetail;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.DetailCartGift;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.RecommendSet;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.RequestParameter;

import org.apache.log4j.Logger;

/**
 * U2040510:商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailCartSubAction extends WebSubAction {

  private static final String STANDARD_BOTH = "both";

  private static final String STANDARD_ONLY1 = "only1";

  private static final String STANDARD_ONLY2 = "only2";

  private static final String STANDARD_NONE = "none";

  private String standardDetail1Name = "";

  private String standardDetail2Name = "";

  /**
   * アクションを実行します。 戻り値なし
   */
  @Override
  public void callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if (sesContainer.getSession() != null) {
      logger.info("当前DetailCartSubAction:sessionID=" + sesContainer.getSession().getId()
          + "开始记录--------------------------------------------------------------------");
    } else {
      logger.info("当前DetailCartSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    String shopCode = "00000000";
    String commodityCode = "";

    String[] urlParam = getRequestParameter().getPathArgs();
    // upd by lc 2012-12-24 start
    if (urlParam.length == 1) {
      // shopCode = urlParam[0];
      commodityCode = urlParam[0];
    } else if (urlParam.length > 1) {
      commodityCode = urlParam[1];
    }
    // upd by lc 2012-12-24 end
    DetailCartBean reqBean = new DetailCartBean();

    boolean isPreview = checkPreviewDigest((DetailCartBean) getBean());

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CommodityContainer> containerList = null;
    if (isPreview) {
      // プレビューの場合は販売可能かどうかを無視してデータを取得する。
      containerList = service.getCommoditySkuList(shopCode, commodityCode, false, DisplayClientType.PC);
      reqBean = new DetailCartBean();
    } else {
      containerList = service.getCommoditySkuList(shopCode, commodityCode, true, DisplayClientType.PC);
    }

    if (containerList.size() == 0) {
      // リクエストパラメータに"preview"を含む場合は、プレビュー用にデータを設定する
      if (isPreview) {
        setPreviewData(reqBean);
        setBean(reqBean);
      }
      if (sesContainer.getSession() != null) {
        logger.info("当前DetailCartSubAction:sessionID=" + sesContainer.getSession().getId()
            + "结束记录--------------------------------------------------------------------");
      } else {
        logger.info("当前DetailCartSubAction:session缺失，结束记录--------------------------------------------------------------------");
      }
      return;
    }

    DataIOService ioService = ServiceLocator.getDataIOService(getLoginInfo());
    if (containerList.size() > 0) {
      CommodityContainer container = containerList.get(0);
      reqBean.setStandardSize(Long.valueOf(containerList.size()));

      Campaign campaign = service.getAppliedCampaignInfo(container.getCommodityHeader().getShopCode(), container
          .getCommodityHeader().getCommodityCode());
      TaxUtil tu = DIContainer.get("TaxUtil");
      Long taxRate = tu.getTaxRate();
      Price price = new Price(container.getCommodityHeader(), container.getCommodityDetail(), campaign, taxRate);

      // 商品の共通情報を設定する
      setCommonInfo(reqBean, container, price, service);

      // 規格名の設定から規格商品の種類を設定する
      String standardAssortment = setStandardMode(container);

      // 商品別ポイントの期間内かどうかを設定する
      reqBean.setCommodityPointPeriod(price.isPoint());

      // 各種期間の設定から、画面に表示する価格を設定する
      setDisplayPriceMode(reqBean, price);

      // 規格情報を設定する
      setStandardList(shopCode, reqBean, containerList, ioService, standardAssortment);

      // ギフトプルダウンを設定する
      setGiftList(shopCode, commodityCode, reqBean, service, ioService);

    }

    // 商品に関連付いているタグの一覧を取得
    reqBean.setTagList(service.getTagCommodityList(shopCode, commodityCode));

    // リクエストパラメータに"preview"を含む場合は、プレビュー用にデータを設定する
    if (isPreview) {
      setPreviewData(reqBean);
    }
    
    // 20140227 txw add start desc:临近效期提醒
    reqBean.setNearMsg(service.getNearCommodityMsg(shopCode, commodityCode));
    // 20140227 txw add end desc:临近效期提醒

    setBean(reqBean);

    if (sesContainer.getSession() != null) {
      logger.info("当前DetailCartSubAction:sessionID=" + sesContainer.getSession().getId()
          + "结束记录--------------------------------------------------------------------");
    } else {
      logger.info("当前DetailCartSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
  }

  /**
   * 
   */
  private void setCommonInfo(DetailCartBean reqBean, CommodityContainer container, Price price, CatalogService service) {
    CommodityHeader ch = container.getCommodityHeader();
    // 20130807 txw add start
    DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(ch.getCommodityCode());
    // 20130807 txw add end
    reqBean.setShopCode(ch.getShopCode());
    reqBean.setShopName(container.getShop().getShopName());
    reqBean.setCommodityCode(ch.getCommodityCode());
    // add by cs_yuli 20120514 start
    UtilService uService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setCommodityName(uService.getNameByLanguage(ch.getCommodityName(), ch.getCommodityNameEn(), ch.getCommodityNameJp()));
    // 20130807 txw update start
    if (dc != null) {
      reqBean.setCommodityDescriptionShort(uService.getNameByLanguage(dc.getDiscountDirectionsCn(), dc.getDiscountDirectionsEn(),
          dc.getDiscountDirectionsJp()));
      //  20130913 yyq add start
      if (dc.getCustomerMaxTotalNum() != null && dc.getCustomerMaxTotalNum() > 0) {
        reqBean.setCustomerMaxTotalNum(dc.getCustomerMaxTotalNum());
      }
      //  20130913 yyq add end
    } else {
      reqBean.setCommodityDescriptionShort(uService.getNameByLanguage(ch.getCommodityDescriptionMobile(), ch
          .getCommodityDescriptionMobileEn(), container.getCommodityHeader().getCommodityDescriptionMobileJp()));
    }
    // 20130807 txw update end
    reqBean.setCommodityStandard1Name(uService.getNameByLanguage(ch.getCommodityStandard1Name(), ch.getCommodityStandard1NameEn(),
        ch.getCommodityStandard1NameJp()));
    reqBean.setCommodityStandard2Name(uService.getNameByLanguage(ch.getCommodityStandard2Name(), ch.getCommodityStandard2NameEn(),
        ch.getCommodityStandard2NameJp()));
    reqBean.setCommodityDescription(uService.getNameByLanguage(ch.getCommodityDescriptionPc(), ch.getCommodityDescriptionPcEn(), ch
        .getCommodityDescriptionPcJp()));
    reqBean.setBrandName(uService.getNameByLanguage(container.getBrand().getBrandName(),
        container.getBrand().getBrandEnglishName(), container.getBrand().getBrandJapaneseName()));
    reqBean.setBrandDescription(uService.getNameByLanguage(container.getBrand().getBrandDescription(), container.getBrand()
        .getBrandDescriptionEn(), container.getBrand().getBrandDescriptionJp()));

    CommodityHeader c = service.getCommodityHeader(reqBean.getShopCode(), reqBean.getCommodityCode());
    reqBean.setOriginalPlace(uService.getNameByLanguage(c.getOriginalPlace(), c.getOriginalPlaceEn(), c.getOriginalPlaceJp()));
    if (!StringUtil.isNullOrEmpty(c.getOriginalPlaceEn())) {
      reqBean.setOriginalPlaceEn(c.getOriginalPlaceEn());
    }
    if (!StringUtil.isNullOrEmpty(c.getOriginalCode())) {
      reqBean.setOriginalCode(c.getOriginalCode());
    }
    reqBean.setShelfLifeDays(c.getShelfLifeDays());
    reqBean.setShelfLifeFlag(c.getShelfLifeFlag());
    // add by cs_yuli 20120514 end
    reqBean.setCommodityPointPeriod(price.isPoint());
    reqBean.setReservationPeriod(price.isReserved());
    reqBean.setDiscountPeriod(price.isDiscount());
    // add by wjw 20120104 start
    reqBean.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
    reqBean.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
    if (price.getDiscountRate().intValue() >= DIContainer.getWebshopConfig().getDisplayDiscountRateLimit()) {
      reqBean.setDisplayDiscountRate(true);
    } else {
      reqBean.setDisplayDiscountRate(false);
    }
    // add by wjw 20120104 start
    if (ch.getDiscountPriceEndDatetime() != null) {
      // 20120413 shen update start
      // reqBean.setDiscountPriceEndDatetime(DateUtil.toDateTimeString(ch.getDiscountPriceEndDatetime(),
      // DateUtil.DEFAULT_DATETIME_FORMAT));
      reqBean.setDiscountPriceEndDatetime(toDateTimeString(ch.getDiscountPriceEndDatetime()));
      // 20120413 shen update end
    }
    reqBean.setCommodityPointRate(NumUtil.toString(ch.getCommodityPointRate()));
    reqBean.setLinkUrl(ch.getLinkUrl());
    reqBean.setStockManagementType(NumUtil.toString(ch.getStockManagementType()));
    reqBean.setStockSufficientMessage(container.getStockStatus().getStockSufficientMessage());
    reqBean.setStockSufficientThreshold(container.getStockStatus().getStockSufficientThreshold());
    reqBean.setOutOfStockMessage(container.getStockStatus().getOutOfStockMessage());
    reqBean.setRepresentSkuCode(ch.getRepresentSkuCode());

    reqBean.setSelectedSkuCode(getRequestParameter().get("selectedSkuCode"));

    if (container.getReviewSummary().getReviewScore() != null) {
      reqBean.setReviewScore(Long.toString(container.getReviewSummary().getReviewScore()));
    }
    if (container.getReviewSummary().getReviewCount() != null) {
      reqBean.setReviewCount(Long.toString(container.getReviewSummary().getReviewCount()));
    }

    reqBean.setStockManagementType(NumUtil.toString(ch.getStockManagementType()));
    reqBean.setDeliveryTypeCode(NumUtil.toString(ch.getDeliveryTypeNo()));
    reqBean.setRecommendCommodityRank(NumUtil.toString(ch.getRecommendCommodityRank()));
    reqBean.setCommodityNameEn(ch.getCommodityNameEn());
    reqBean.setBrandCode(ch.getBrandCode());

    // Campaign campaign = service.getAppliedCampaignInfo(ch.getShopCode(),
    // ch.getCommodityCode());
    if (price.getCampaignInfo() != null && price.getCampaignInfo().getCampaignCode() != null) {
      // price.setCampaign(campaign.getCampaignCode());
      reqBean.setCampaignCode(price.getCampaignInfo().getCampaignCode());
    }

    reqBean.setCommodityTaxType(NumUtil.toString(ch.getCommodityTaxType()));
    if (container.getCommodityLayout().getDisplayFlg().equals(DisplayFlg.VISIBLE.longValue())) {
      reqBean.setDisplayReview(true);
    } else {
      reqBean.setDisplayReview(false);
    }
    // 商品警告表示
    if (StringUtil.hasValue(ch.getWarningFlag()) && WarningFlag.WARNINGFLAG.getValue().equals(ch.getWarningFlag())) {
      reqBean.setCommodityMessage(DIContainer.getWebshopConfig().getCommodityMessage());
    }
    // 20130809 txw add start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setTitle(utilService.getNameByLanguage(ch.getTitle(), ch.getTitleEn(), ch.getTitleJp()));
    reqBean.setDescription(utilService.getNameByLanguage(ch.getDescription(), ch.getDescriptionEn(), ch.getDescriptionJp()));
    reqBean.setKeyword(utilService.getNameByLanguage(ch.getKeyword(), ch.getKeywordEn(), ch.getKeywordJp()));
    // 20130809 txw add end
  }

  /**
   * 
   */
  private void setStandardList(String shopCode, DetailCartBean reqBean, List<CommodityContainer> list, DataIOService ioService,
      String standardAssortment) {
    LinkedHashSet<String> standardName1Set = new LinkedHashSet<String>();
    LinkedHashSet<String> standardName2Set = new LinkedHashSet<String>();
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    List<NameValue> nameList = new ArrayList<NameValue>();
    nameList.add(new NameValue(Messages.getString("web.action.front.catalog.DetailCartSubAction.0"), ""));
    StockDao sDao = DIContainer.getDao(StockDao.class);
    List<DetailCartDetail> cartList = new ArrayList<DetailCartDetail>();
    List<DetailCartDetail> skuImageList = new ArrayList<DetailCartDetail>();

    DataIOService dataIOService = ServiceLocator.getDataIOService(getLoginInfo());
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());

    // 10.1.4 K00175 追加 ここから
    Campaign campaign = catalogSvc.getAppliedCampaignBySku(reqBean.getShopCode(), reqBean.getCommodityCode());
    CommodityHeader header = list.get(0).getCommodityHeader();
    Map<Sku, CommodityAvailability> availabilityMap = catalogSvc.getAvailablilityMap(reqBean.getShopCode(), reqBean
        .getCommodityCode(), 1, isReserved(header));
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    // 10.1.4 K00175 追加 ここまで
    for (CommodityContainer sh : list) {
      // 10.1.4 K00175 削除 ここから
      // Campaign campaign =
      // catalogSvc.getAppliedCampaignBySku(sh.getCommodityHeader().getShopCode(),
      // sh.getCommodityHeader()
      // .getCommodityCode());
      // TaxUtil tu = DIContainer.get("TaxUtil");
      // Long taxRate = tu.getTaxRate();
      // 10.1.4 K00175 削除 ここまで
      Price price = new Price(sh.getCommodityHeader(), sh.getCommodityDetail(), campaign, taxRate);
      DetailCartDetail cartDetail = new DetailCartDetail();
      cartDetail.setSkuCode(sh.getCommodityDetail().getSkuCode());

      if (reqBean.getStandardSize() > 1) {
        cartDetail.setDiscountPrice(getFormatPrice(price.getDiscountPrice()));
        cartDetail.setReservationPrice(getFormatPrice(price.getReservationPrice()));
        cartDetail.setUnitPrice(getFormatPrice(price.getUnitPrice()));
        cartDetail.setAvailableStockQuantity(getFormatNumber(sh.getContainerAddInfo().getAvailableStockQuantity()));
        // 2012/12/25 促销对应 ob add start
        if (StockManagementType.NONE.getValue().equals(reqBean.getStockManagementType())
            || StockManagementType.NOSTOCK.getValue().equals(reqBean.getStockManagementType())) {
          cartDetail.setAvailableStockQuantity(NumUtil.toString(Long.MAX_VALUE));
        }
        // 2012/12/25 促销对应 ob add end
        // add by wjw 20120104 start
        cartDetail.setDiscountPrices(getFormatPrice(price.getDiscountPrices().abs()));
        cartDetail.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
        if (price.getDiscountRate().intValue() >= DIContainer.getWebshopConfig().getDisplayDiscountRateLimit()) {
          cartDetail.setDisplayDiscountRate(true);
        } else {
          cartDetail.setDisplayDiscountRate(false);
        }
        // add by wjw 20120104 start

      } else {
        // 20130807 txw add start

        cartDetail.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
        cartDetail.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
        cartDetail.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
        cartDetail.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
        
        cartDetail.setAvailableStockQuantity(getFormatNumberTag(sh.getContainerAddInfo().getAvailableStockQuantity()));
        // 2012/12/25 促销对应 ob add start
        if (StockManagementType.NONE.getValue().equals(reqBean.getStockManagementType())
            || StockManagementType.NOSTOCK.getValue().equals(reqBean.getStockManagementType())) {
          cartDetail.setAvailableStockQuantity(NumUtil.toString(Long.MAX_VALUE));
        }
        // 2012/12/25 促销对应 ob add end
        // add by wjw 20120104 start
        cartDetail.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
        if (price.getDiscountRate().intValue() >= DIContainer.getWebshopConfig().getDisplayDiscountRateLimit()) {
          cartDetail.setDisplayDiscountRate(true);
        } else {
          cartDetail.setDisplayDiscountRate(false);
        }
        // add by wjw 20120104 start
        // 20130807 txw update end
      }
      cartDetail.setWeight(NumUtil.parseStringWithoutZero(sh.getCommodityDetail().getWeight()));
      if (sh.getCommodityHeader().getDiscountPriceEndDatetime() != null) {
        // 20120413 shen update start
        // cartDetail.setDiscountPriceEndDatetime(DateUtil.toDateTimeString(sh.getCommodityHeader().getDiscountPriceEndDatetime(),
        // DateUtil.DEFAULT_DATETIME_FORMAT));
        cartDetail.setDiscountPriceStartDatetime(toDateTimeString(sh.getCommodityHeader().getDiscountPriceStartDatetime()));
        cartDetail.setDiscountPriceEndDatetime(toDateTimeString(sh.getCommodityHeader().getDiscountPriceEndDatetime()));
        // 20120413 shen update end
      }
      // edit by cs_yuli 20120521 start
      standardDetail1Name = utilService.getNameByLanguage(sh.getCommodityDetail().getStandardDetail1Name(), sh.getCommodityDetail()
          .getStandardDetail1NameEn(), sh.getCommodityDetail().getStandardDetail1NameJp());
      standardDetail2Name = utilService.getNameByLanguage(sh.getCommodityDetail().getStandardDetail2Name(), sh.getCommodityDetail()
          .getStandardDetail2NameEn(), sh.getCommodityDetail().getStandardDetail2NameJp());
      cartDetail.setStandardDetail1Name(standardDetail1Name);
      cartDetail.setStandardDetail2Name(standardDetail2Name);
      // edit by cs_yuli 20120521 end

      cartDetail.setArrivalGoodsFlg(isArraivalGoodsDisplay(sh, price));
      setStockStatus(reqBean, sh, cartDetail);
      setStockStatusMessageList(reqBean, sh);

      // 有効在庫数チェック
      // 10.1.4 K00175 修正 ここから
      // if (catalogSvc.isAvailable(reqBean.getShopCode(),
      // sh.getCommodityDetail().getSkuCode(), 1, price.isReserved()).equals(
      // CommodityAvailability.AVAILABLE)) {
      // cartDetail.setDisplayCartButton(true);
      // } else {
      // cartDetail.setDisplayCartButton(false);
      // }

      CommodityAvailability availability = availabilityMap
          .get(new Sku(reqBean.getShopCode(), sh.getCommodityDetail().getSkuCode()));

      // 2012/11/16 促销对应 ob add start
      CommodityHeader commodityHeader = catalogSvc.getCommodityHeader(shopCode, sh.getCommodityHeader().getCommodityCode());
      if (commodityHeader.getSetCommodityFlg() != null
          && commodityHeader.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue())) {
        if (CommodityAvailability.STOCK_SHORTAGE.equals(availability) || CommodityAvailability.OUT_OF_STOCK.equals(availability)) {
          availability = CommodityAvailability.AVAILABLE;
        }
      }
      // 2012/11/16 促销对应 ob add end

      // add by twh 2013-6-25 start 组合商品对应
      if (StringUtil.hasValue(commodityHeader.getOriginalCommodityCode())) {
        Stock stock = sDao.load(shopCode, commodityHeader.getOriginalCommodityCode());
        if (stock.getStockQuantity() - stock.getAllocatedQuantity() >= commodityHeader.getCombinationAmount()) {
          availability = CommodityAvailability.AVAILABLE;
        }

      }
      // add by twh 2013-6-25 end 组合商品对应

      cartDetail.setDisplayCartButton(CommodityAvailability.AVAILABLE.equals(availability));
      // 10.1.4 K00175 修正 ここまで
      cartDetail.setSetCommodityFlg(false);
      // 2012/11/16 促销对应 ob add start
      // 套餐商品
      if (commodityHeader.getSetCommodityFlg() != null
          && commodityHeader.getSetCommodityFlg().equals(SetCommodityFlg.OBJECTIN.longValue())) {
        reqBean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());

        if (!getSetCommodity(cartDetail, sh, reqBean, catalogSvc)) {
          cartDetail.setDisplayCartButton(false);
          cartDetail.setAvailableStockQuantity("0");
        } else {
          List<String> compositionSkuCodeList = new ArrayList<String>();

          for (DetailCartComposition child : reqBean.getCompositionList()) {
            String compositionSkuCode = getStockOfAllSku(shopCode, child.getStander(), catalogSvc);
            if (StringUtil.hasValue(compositionSkuCode)) {
              compositionSkuCodeList.add(compositionSkuCode);
            }
          }

          Long availableStock = catalogSvc.getAvailableStock(shopCode, sh.getCommodityDetail().getSkuCode(), true,
              compositionSkuCodeList, null);
          cartDetail.setAvailableStockQuantity(NumUtil.toString(availableStock));
        }
        //计算套装实际价格
        SetCommodityComposition suit = catalogSvc.getSuitSalePrice(cartDetail.getSkuCode());
        cartDetail.setDiscountPrice(sh.getCommodityDetail().getUnitPrice().toString());
        BigDecimal discountPrice = suit.getRetailPrice();
        BigDecimal unitPrice = new BigDecimal(cartDetail.getUnitPrice());
//        BigDecimal discountPrices = unitPrice.add(discountPrice.negate());
        cartDetail.setDiscountPrice(discountPrice.toString());
        cartDetail.setDiscountPrices(unitPrice.subtract(discountPrice).toString());
        cartDetail.setSetCommodityFlg(true);
      }
      if (CommodityAvailability.AVAILABLE.equals(availability)) {
        Long CampaignAvailability = catalogSvc.campaignAvailability(reqBean.getCommodityCode(), reqBean.getShopCode(), null);
        if (CampaignAvailability == null || CampaignAvailability == 0L) {
          cartDetail.setDisplayCartButton(false);
          cartDetail.setAvailableStockQuantity("0");
        } else {
          // 创建推荐套餐信息
          getRecommendSetCommodity(sh, reqBean, catalogSvc);

        }
      }
      // 2012/11/16 促销对应 ob add end

      // SKU単位の規格リストを作成
      createStandardList(standardAssortment, standardName1Set, standardName2Set, nameList, sh, cartDetail);

      // 
      if (sh.getCommodityDetail().getUseFlg().equals(UseStatus.USED.longValue())) {
        cartDetail.setUseFlg(true);
      } else {
        cartDetail.setUseFlg(false);
      }
      // 组合商品对应
      if (StringUtil.hasValue(sh.getCommodityHeader().getOriginalCommodityCode())) {
        Stock stock = sDao.load(shopCode, sh.getCommodityHeader().getOriginalCommodityCode());
        cartDetail.setOriginalCommodityCode(sh.getCommodityHeader().getOriginalCommodityCode());
        cartDetail.setCombinationAmount(sh.getCommodityHeader().getCombinationAmount());
        cartDetail.setDiscountPrice(BigDecimalUtil.multiply(new BigDecimal(cartDetail.getDiscountPrice()),
            sh.getCommodityHeader().getCombinationAmount()).toString());
        cartDetail.setReservationPrice(BigDecimalUtil.multiply(price.getReservationPrice(),
            sh.getCommodityHeader().getCombinationAmount()).toString());
        cartDetail.setUnitPrice(BigDecimalUtil.multiply(price.getUnitPrice(), sh.getCommodityHeader().getCombinationAmount())
            .toString());
        cartDetail.setDiscountPrices(BigDecimalUtil.multiply(price.getDiscountPrices().abs(),
            sh.getCommodityHeader().getCombinationAmount()).toString());
        cartDetail.setAvailableStockQuantity(getFormatNumberTag((stock.getStockQuantity() - stock.getAllocatedQuantity())
            / sh.getCommodityHeader().getCombinationAmount()));
      }
      
      DiscountCommodity dc = catalogSvc.getDiscountCommodityByCommodityCode(header.getCommodityCode());
      if (dc != null) {
        reqBean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());

        cartDetail.setDiscountPrice(NumUtil.toString(dc.getDiscountPrice()));

        cartDetail.setDiscountPrices(NumUtil.toString(new BigDecimal(cartDetail.getUnitPrice()).subtract(dc.getDiscountPrice())));
      }
      cartList.add(cartDetail);
      // upd by lc 2012-12-13 start
      /*
       * if (dataIOService.skuImageExists(reqBean.getShopCode(),
       * sh.getCommodityDetail().getSkuCode(), true)) { DetailCartDetail
       * skuImage = new DetailCartDetail();
       * skuImage.setSkuCode(sh.getCommodityDetail().getSkuCode()); // edit by
       * cs_yuli 20120521 start
       * skuImage.setStandardDetail1Name(standardDetail1Name);
       * skuImage.setStandardDetail2Name(standardDetail1Name); // edit by
       * cs_yuli 20120521 end skuImageList.add(skuImage); }
       */
      List<String> imgList = dataIOService.readDetailImages(reqBean.getShopCode(), sh.getCommodityDetail().getSkuCode(), true);
      for (String str : imgList) {
        DetailCartDetail skuImage = new DetailCartDetail();
        skuImage.setSkuCode(str);
        skuImage.setStandardDetail1Name(standardDetail1Name);
        skuImage.setStandardDetail2Name(standardDetail1Name);
        skuImageList.add(skuImage);
      }
      // upd by lc 2012-12-13 start
    }

    reqBean.setCommodityImageExists(dataIOService.commodityImageExists(shopCode, reqBean.getCommodityCode(), true));
    reqBean.setCartDetailList(cartList);
    reqBean.setSkuImageList(skuImageList);

    reqBean.setStandardList(new ArrayList<CodeAttribute>(nameList));

    List<String> standardName1List = new ArrayList<String>(standardName1Set);
    List<String> standardName2List = new ArrayList<String>(standardName2Set);
    reqBean.setStandardName1List(standardName1List);
    reqBean.setStandardName2List(standardName2List);
    reqBean.setStandardAssortment(standardAssortment);

    if (nameList.size() == 1 || nameList.size() == 2) {
      nameList.remove(0);
    }
  }

  /**
   * 取得套餐明細商品中庫存量最多的商品的Sku編號
   */
  private String getStockOfAllSku(String shopCode, List<CodeAttribute> commodityDetailList, CatalogService catalogSvc) {
    Long availableStock = 0L;
    String skuCode = "";

    for (CodeAttribute detail : commodityDetailList) {
      CommodityAvailability availability = catalogSvc.isAvailable(shopCode, detail.getValue(), 1, false);
      if (StringUtil.hasValue(detail.getValue()) && CommodityAvailability.AVAILABLE.equals(availability)) {
        Long stockTemp = catalogSvc.getAvailableStock(shopCode, detail.getValue());
        if (stockTemp == null) {

          continue;

        } else if (stockTemp == -1L) {

          skuCode = detail.getValue();
          break;

        } else if (stockTemp >= availableStock) {

          skuCode = detail.getValue();
          availableStock = stockTemp;

        }
      }
    }

    return skuCode;
  }

  /**
   * @param reqBean
   * @param sh
   * @param cartDetail
   */
  private void setStockStatus(DetailCartBean reqBean, CommodityContainer sh, DetailCartDetail cartDetail) {
    if (reqBean.getStockManagementType().equals(StockManagementType.WITH_STATUS.getValue())) {
      if (sh.getContainerAddInfo().getAvailableStockQuantity() == null) {
        cartDetail.setStockStatusMessage(sh.getStockStatus().getStockSufficientMessage());
      } else if (sh.getContainerAddInfo().getAvailableStockQuantity() <= 0) {
        cartDetail.setStockStatusMessage(sh.getStockStatus().getOutOfStockMessage());
      } else if (0 < sh.getContainerAddInfo().getAvailableStockQuantity()
          && sh.getContainerAddInfo().getAvailableStockQuantity() <= sh.getStockStatus().getStockSufficientThreshold()) {
        cartDetail.setStockStatusMessage(sh.getStockStatus().getStockLittleMessage());
      } else {
        cartDetail.setStockStatusMessage(sh.getStockStatus().getStockSufficientMessage());
      }
    } else {
      // delete by cs_yuli 20120528 start
      // if
      // (reqBean.getStockManagementType().equals(StockManagementType.WITH_QUANTITY.getValue()))
      // {
      // if
      // (NumUtil.parse(DIContainer.getWebshopConfig().getStockMessage().split(":")[0]).longValue()
      // <= sh.getContainerAddInfo().getAvailableStockQuantity()) {
      // cartDetail.setStockStatusMessage(DIContainer.getWebshopConfig().getStockMessage().split(":")[1]);
      // delete by cs_yuli 20120528 end
      if (reqBean.getStockManagementType().equals(StockManagementType.WITH_QUANTITY.getValue())) {
        if (NumUtil.parse(DIContainer.getStockValue().getStockMessage().split(":")[0]).longValue() <= sh.getContainerAddInfo()
            .getAvailableStockQuantity()) {
          cartDetail.setStockStatusMessage(DIContainer.getStockValue().getStockMessage().split(":")[1]);
        } else {
          cartDetail.setStockStatusMessage("");
        }
      } else {
        cartDetail.setStockStatusMessage("");
      }
    }
  }

  /**
   * @param reqBean
   * @param sh
   * @param cartDetail
   */
  private void setStockStatusMessageList(DetailCartBean reqBean, CommodityContainer sh) {
    if (reqBean.getStockManagementType().equals(StockManagementType.WITH_STATUS.getValue())) {
      if (sh.getContainerAddInfo().getAvailableStockQuantity() == null) {
        reqBean.setStockSufficientMessage(sh.getStockStatus().getStockSufficientMessage());
      } else if (sh.getContainerAddInfo().getAvailableStockQuantity() <= 0) {
        reqBean.setOutOfStockMessage(sh.getStockStatus().getOutOfStockMessage());
      } else if (0 < sh.getContainerAddInfo().getAvailableStockQuantity()
          && sh.getContainerAddInfo().getAvailableStockQuantity() <= sh.getStockStatus().getStockSufficientThreshold()) {
        reqBean.setStockLittleMessage(sh.getStockStatus().getStockLittleMessage());
      } else {
        reqBean.setStockSufficientMessage(sh.getStockStatus().getStockSufficientMessage());
      }
    }
  }

  private String getFormatPrice(Number price) {
    if (NumUtil.isNull(price)) {
      return "";
    } else {
      return NumUtil.formatCurrency(price);
    }
  }

  /**
   * 
   */
  private String getFormatNumber(Long num) {
    if (NumUtil.isNull(num)) {
      return "";
    } else {
      return java.text.NumberFormat.getNumberInstance(java.util.Locale.JAPAN).format(num);
    }
  }

  /**
   * 
   */
  private String getFormatNumberTag(Long num) {
    if (NumUtil.isNull(num)) {
      return NumUtil.toString(Long.MAX_VALUE);
    } else {
      if (num < 0L) {
        return "0";
      }
      return NumUtil.toString(num);
    }
  }

  /**
   * 
   */
  private void setGiftList(String shopCode, String commodityCode, DetailCartBean reqBean, CatalogService service,
      DataIOService ioService) {

    // 20130709 yamanaka 未使用表 CommentOut
    // List<Gift> result = service.getAvailableGiftList(shopCode,
    // commodityCode);
    List<Gift> result = new ArrayList<Gift>();

    boolean displayGift = true;
    if (result.isEmpty()) {
      // 表示可能なギフトがない場合は表示しない
      displayGift = false;
    }
    reqBean.setDisplayGift(displayGift);

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setGiftList(utilService.getGiftList(shopCode, commodityCode));

    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setShopCode(reqBean.getShopCode());
    condition.setContentsType(ContentsType.IMAGE_DATA_SHOP_GIFT);

    List<DetailCartGift> detailGiftList = new ArrayList<DetailCartGift>();
    for (Gift gift : result) {
      condition.setGiftCode(gift.getGiftCode());
      if (ioService.contentsExists(condition)) {
        DetailCartGift detailCartGift = new DetailCartGift();
        detailCartGift.setGiftName(gift.getGiftName());
        detailCartGift.setGiftCode(gift.getGiftCode());
        detailGiftList.add(detailCartGift);
      }
    }
    reqBean.setGiftImageList(detailGiftList);
  }

  /**
   * @param sh
   * @return true or false
   */
  private boolean isArraivalGoodsDisplay(CommodityContainer sh, Price price) {

    if (price.isReserved()) {
      return false;
    }

    if (sh.getCommodityHeader().getArrivalGoodsFlg().equals(ArrivalGoodsFlg.UNACCEPTABLE.longValue())) {
      return false;
    }

    if (sh.getCommodityHeader().getStockManagementType().equals(StockManagementType.NONE.longValue())) {
      return false;
    }

    if (sh.getCommodityHeader().getStockManagementType().equals(StockManagementType.NOSTOCK.longValue())) {
      return false;
    }

    if (sh.getContainerAddInfo().getAvailableStockQuantity() == null) {
      return false;
    }

    if (sh.getContainerAddInfo().getAvailableStockQuantity() >= 1) {
      return false;
    }

    return true;

  }

  /**
   * @param sc
   * @return standardAssortment
   */
  private String setStandardMode(CommodityContainer sc) {
    String standardAssortment;
    if (StringUtil.hasValue(sc.getCommodityHeader().getCommodityStandard1Name())
        && StringUtil.hasValue(sc.getCommodityHeader().getCommodityStandard2Name())) {
      standardAssortment = STANDARD_BOTH;
    } else if (StringUtil.hasValue(sc.getCommodityHeader().getCommodityStandard1Name())
        && StringUtil.isNullOrEmpty(sc.getCommodityHeader().getCommodityStandard2Name())) {
      standardAssortment = STANDARD_ONLY1;
    } else if (StringUtil.isNullOrEmpty(sc.getCommodityHeader().getCommodityStandard1Name())
        && StringUtil.hasValue(sc.getCommodityHeader().getCommodityStandard2Name())) {
      standardAssortment = STANDARD_ONLY2;
    } else {
      standardAssortment = STANDARD_NONE;
    }
    return standardAssortment;
  }

  /**
   * @param reqBean
   * @param price
   */
  private void setDisplayPriceMode(DetailCartBean reqBean, Price price) {
    if (price.isSale()) {
      // 通常の販売期間の場合
      reqBean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      setAppliedCampaign(price, reqBean);
    } else if (price.isDiscount()) {
      // 特別価格期間の場合
      reqBean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
      setAppliedCampaign(price, reqBean);
    } else if (price.isReserved()) {
      // 予約期間の場合
      reqBean.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
      setAppliedCampaign(price, reqBean);
    } else {
      reqBean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      setAppliedCampaign(price, reqBean);
    }
  }

  /**
   * @param standardAssortment
   * @param standardName1Set
   * @param standardName2Set
   * @param nameList
   * @param sh
   * @param cartDetail
   */
  private void createStandardList(String standardAssortment, Set<String> standardName1Set, Set<String> standardName2Set,
      List<NameValue> nameList, CommodityContainer sh, DetailCartDetail cartDetail) {
    if (standardAssortment.equals(STANDARD_NONE)) {
      nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode(), sh.getCommodityDetail().getSkuCode()));
    } else if (standardAssortment.equals(STANDARD_BOTH)) {
      setSummaryStandardName1(standardName1Set, sh);
      setSummaryStandardName2(standardName2Set, sh);
      setStandardDetailName(standardAssortment, nameList, sh, cartDetail);
    } else if (standardAssortment.equals(STANDARD_ONLY1)) {
      setSummaryStandardName1(standardName1Set, sh);
      setStandardDetailName(standardAssortment, nameList, sh, cartDetail);
    } else if (standardAssortment.equals(STANDARD_ONLY2)) {
      setSummaryStandardName2(standardName2Set, sh);
      setStandardDetailName(standardAssortment, nameList, sh, cartDetail);
    }
  }

  /**
   * 規格ドロップダウンリストを設定します。<BR>
   * ドロップダウンリストの表示例:<BR>
   * 規格名称が2つ設定されている場合(STANDARD_BOTH)= "SKUコード(規格詳細1名称:規格詳細2名称)"<BR>
   * 規格名称1、または規格名称2のみ設定されている場合(STANDARD_ONLY1 or STANDARD_ONLY2)=
   * "SKUコード(規格詳細1名称または規格詳細2名称)"<BR>
   * 
   * @param nameList
   * @param sh
   * @param cartDetail
   */
  private void setStandardDetailName(String standardAssortment, List<NameValue> nameList, CommodityContainer sh,
      DetailCartDetail cartDetail) {
    // edit by cs_yuli 20120521 start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    standardDetail1Name = utilService.getNameByLanguage(sh.getCommodityDetail().getStandardDetail1Name(), sh.getCommodityDetail()
        .getStandardDetail1NameEn(), sh.getCommodityDetail().getStandardDetail1NameJp());
    standardDetail2Name = utilService.getNameByLanguage(sh.getCommodityDetail().getStandardDetail2Name(), sh.getCommodityDetail()
        .getStandardDetail2NameEn(), sh.getCommodityDetail().getStandardDetail2NameJp());
    // edit by cs_yuli 20120521 end
    if (standardAssortment.equals(STANDARD_BOTH)) {
      if (StringUtil.hasValue(standardDetail1Name) && StringUtil.hasValue(sh.getCommodityDetail().getStandardDetail2Name())) {
        cartDetail.setStandardDetailName(standardDetail1Name + "_" + standardDetail2Name);
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode() + "(" + standardDetail1Name + ":" + standardDetail2Name
            + ")", sh.getCommodityDetail().getSkuCode()));
      } else if (StringUtil.hasValue(standardDetail1Name)) {
        cartDetail.setStandardDetailName(standardDetail1Name);
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode() + "(" + standardDetail1Name + ")", sh.getCommodityDetail()
            .getSkuCode()));
      } else if (StringUtil.hasValue(standardDetail2Name)) {
        cartDetail.setStandardDetailName(standardDetail2Name);
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode() + "(" + standardDetail2Name + ")", sh.getCommodityDetail()
            .getSkuCode()));
      } else {
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode(), sh.getCommodityDetail().getSkuCode()));
      }
    } else if (standardAssortment.equals(STANDARD_ONLY1)) {
      cartDetail.setStandardDetailName(standardDetail1Name);
      if (StringUtil.hasValue(standardDetail1Name)) {
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode() + "(" + standardDetail1Name + ")", sh.getCommodityDetail()
            .getSkuCode()));
      } else {
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode(), sh.getCommodityDetail().getSkuCode()));
      }
    } else if (standardAssortment.equals(STANDARD_ONLY2)) {
      cartDetail.setStandardDetailName(standardDetail2Name);
      if (StringUtil.hasValue(standardDetail2Name)) {
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode() + "(" + standardDetail2Name + ")", sh.getCommodityDetail()
            .getSkuCode()));
      } else {
        nameList.add(new NameValue(sh.getCommodityDetail().getSkuCode(), sh.getCommodityDetail().getSkuCode()));
      }
    }
  }

  /**
   * @param standardName1Set
   * @param sh
   */
  private void setSummaryStandardName1(Set<String> standardName1Set, CommodityContainer sh) {
    // edit by cs_yuli 20120521 start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    standardDetail1Name = utilService.getNameByLanguage(sh.getCommodityDetail().getStandardDetail1Name(), sh.getCommodityDetail()
        .getStandardDetail1NameEn(), sh.getCommodityDetail().getStandardDetail1NameJp());
    // edit by cs_yuli 20120521 end
    if (StringUtil.hasValue(standardDetail1Name)) {
      standardName1Set.add(standardDetail1Name);
    }
  }

  /**
   * @param standardName2Set
   * @param sh
   */
  private void setSummaryStandardName2(Set<String> standardName2Set, CommodityContainer sh) {
    // edit by cs_yuli 20120521 start
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    standardDetail2Name = utilService.getNameByLanguage(sh.getCommodityDetail().getStandardDetail2Name(), sh.getCommodityDetail()
        .getStandardDetail2NameEn(), sh.getCommodityDetail().getStandardDetail2NameJp());
    // edit by cs_yuli 20120521 end
    if (StringUtil.hasValue(standardDetail2Name)) {
      standardName2Set.add(standardDetail2Name);
    }
  }

  /**
   * @param price
   * @param commodity
   */
  private void setAppliedCampaign(Price price, DetailCartBean cartDetail) {
    if (price.getCampaignInfo() == null) {
      clearAppliedCampaign(cartDetail);
    } else {
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      String campaingnName = utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(), price.getCampaignInfo()
          .getCampaignNameEn(), price.getCampaignInfo().getCampaignNameJp());
      cartDetail.setCampaignPeriod(price.isCampaign());
      cartDetail.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
      cartDetail.setCampaignName(campaingnName);
      cartDetail.setCampaignPeriod(price.isCampaign());
      cartDetail.setCampaignCode(price.getCampaignInfo().getCampaignCode());
      cartDetail.setCampaignName(campaingnName);
      cartDetail.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
    }
  }

  /**
   * @param commodity
   */
  private void clearAppliedCampaign(DetailCartBean cartDetail) {
    cartDetail.setCampaignPeriod(false);
    cartDetail.setCampaignCode("");
    cartDetail.setCampaignName("");
    cartDetail.setCampaignDiscountRate("0");
  }

  /**
   * beanにリクエストから取得したプレビュー用データを設定します。
   * 
   * @param reqBean
   */
  private void setPreviewData(DetailCartBean reqBean) {
    RequestParameter reqparam = getRequestParameter();

    reqBean.setShopCode(reqparam.get("shopCode"));
    reqBean.setCommodityCode(reqparam.get("commodityCode"));
    reqBean.setCommodityName(reqparam.get("commodityName"));
    reqBean.setCommodityDescription(reqparam.get("commodityDescription"));
    reqBean.setCommodityPointRate(reqparam.get("commodityPointRate"));
    reqBean.setLinkUrl(reqparam.get("linkUrl"));

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    reqBean.setShopName(service.getShop(reqBean.getShopCode()).getShopName());

    List<DetailCartDetail> details = reqBean.getCartDetailList();
    // 10036 10.1.4 修正 ここから
    // for (DetailCartDetail detail : details) {
    // detail.setArrivalGoodsFlg(true);
    // }
    //
    // // 商品詳細リストが空の場合=新規登録画面でプレビューを行った場合、
    // // ダミーの商品詳細データを作成する
    // if (details.isEmpty()) {
    // List<CommodityContainer> containerList = new
    // ArrayList<CommodityContainer>();
    //
    // CommodityContainer container = new CommodityContainer();
    // container.setCommodityHeader(new CommodityHeader());
    // container.setCommodityDetail(new CommodityDetail());
    // container.getCommodityHeader().setShopCode(reqBean.getShopCode());
    // container.getCommodityDetail().setCommodityCode(reqBean.getCommodityCode());
    // container.getCommodityHeader().setRepresentSkuCode(reqparam.get("representSkuCode"));
    // container.getCommodityDetail().setSkuCode(reqparam.get("representSkuCode"));
    // container.getCommodityDetail().setUnitPrice(NumUtil.toLong(reqparam.get("unitPrice")));
    // container.getCommodityHeader().setSaleStartDatetime(DateUtil.fromString(reqparam.getDateTimeString("saleStartDatetime")));
    // container.getCommodityHeader().setSaleEndDatetime(DateUtil.fromString(reqparam.getDateTimeString("saleEndtDatetime")));
    // container.getCommodityDetail().setDiscountPrice(NumUtil.toLong(reqparam.get("discountPrice")));
    // container.getCommodityHeader().setDiscountPriceStartDatetime(
    // DateUtil.fromString(reqparam.getDateTimeString("discountPriceStartDatetime")));
    // container.getCommodityHeader().setDiscountPriceEndDatetime(
    // DateUtil.fromString(reqparam.getDateTimeString("discountPirceEndDatetime")));
    // container.getCommodityDetail().setReservationPrice(NumUtil.toLong(reqparam.get("reservationPrice")));
    // container.getCommodityHeader().setReservationStartDatetime(
    // DateUtil.fromString(reqparam.getDateTimeString("reservationStartDatetime")));
    // container.getCommodityHeader().setReservationEndDatetime(
    // DateUtil.fromString(reqparam.getDateTimeString("reservationEndDatetime")));
    // container.getCommodityHeader().setCommodityPointRate(NumUtil.toLong(reqBean.getCommodityPointRate()));
    // container.getCommodityHeader().setCommodityPointStartDatetime(
    // DateUtil.fromString(reqparam.getDateTimeString("commodityPointStartDatetime")));
    // container.getCommodityHeader().setCommodityPointEndDatetime(
    // DateUtil.fromString(reqparam.getDateTimeString("commodityPointEndDatetime")));
    // container.getCommodityHeader().setCommodityTaxType(TaxType.fromValue(reqparam.get("taxType")).longValue());
    // container.getCommodityHeader().setArrivalGoodsFlg(ArrivalGoodsFlg.fromValue(reqparam.get("arrivalGoodsFlg")).longValue());
    //
    // containerList.add(container);
    // CatalogService catalogSvc =
    // ServiceLocator.getCatalogService(getLoginInfo());
    // Campaign campaign =
    // catalogSvc.getAppliedCampaignInfo(container.getCommodityHeader().getShopCode(),
    // container
    // .getCommodityHeader().getCommodityCode());
    // TaxUtil tu = DIContainer.get("TaxUtil");
    // Long taxRate = tu.getTaxRate();
    // Price price = new Price(container.getCommodityHeader(),
    // container.getCommodityDetail(), campaign, taxRate);
    // reqBean.setCommodityPointPeriod(price.isPoint());
    // reqBean.setCommodityPointRate(Long.toString(container.getCommodityHeader().getCommodityPointRate()));
    // reqBean.setCommodityTaxType(Long.toString(container.getCommodityHeader().getCommodityTaxType()));
    // // 規格名の設定から規格商品の種類を設定する
    // String standardAssortment = setStandardMode(container);
    //
    // // 商品別ポイントの期間内かどうかを設定する
    // reqBean.setCommodityPointPeriod(price.isPoint());
    //
    // // 各種期間の設定から、画面に表示する価格を設定する
    // setDisplayPriceMode(reqBean, price);
    //
    // LinkedHashSet<String> standardName1Set = new LinkedHashSet<String>();
    // LinkedHashSet<String> standardName2Set = new LinkedHashSet<String>();
    //
    // List<NameValue> nameList = new ArrayList<NameValue>();
    // nameList.add(new NameValue("選択してください", ""));
    //
    // DetailCartDetail cartDetail = new DetailCartDetail();
    // cartDetail.setSkuCode(container.getCommodityDetail().getSkuCode());
    // cartDetail.setDiscountPrice(NumUtil.toString(container.getCommodityDetail().getDiscountPrice()));
    // cartDetail.setReservationPrice(NumUtil.toString(container.getCommodityDetail().getReservationPrice()));
    // cartDetail.setUnitPrice(NumUtil.toString(container.getCommodityDetail().getUnitPrice()));
    // cartDetail.setStandardDetail1Name(container.getCommodityDetail().getStandardDetail1Name());
    // cartDetail.setStandardDetail2Name(container.getCommodityDetail().getStandardDetail2Name());
    // cartDetail.setArrivalGoodsFlg(ArrivalGoodsFlg.ACCEPTABLE ==
    // ArrivalGoodsFlg.fromValue(container.getCommodityHeader()
    // .getArrivalGoodsFlg()));
    //
    // // SKU単位の規格リストを作成
    // createStandardList(standardAssortment, standardName1Set,
    // standardName2Set, nameList, container, cartDetail);
    // List<String> standardName1List = new ArrayList<String>(standardName1Set);
    // List<String> standardName2List = new ArrayList<String>(standardName2Set);
    // details.add(cartDetail);
    //
    // reqBean.setCartDetailList(details);
    // reqBean.setStandardList(new ArrayList<CodeAttribute>(nameList));
    // reqBean.setStandardName1List(standardName1List);
    // reqBean.setStandardName2List(standardName2List);
    // reqBean.setStandardAssortment(standardAssortment);
    // reqBean.setStandardSize(Long.valueOf(containerList.size()));
    //
    // setGiftList(reqBean.getShopCode(), reqBean.getCommodityCode(), reqBean,
    // ServiceLocator.getCatalogService(getLoginInfo()),
    // ServiceLocator.getDataIOService(getLoginInfo()));

    // header情報
    // 各期間区分設定(特価・予約・商品別ポイント付与・キャンペーン)
    reqBean.setDiscountPeriod(DateUtil.isPeriodString(reqparam.getDateTimeString("discountPriceStartDatetime"), reqparam
        .getDateTimeString("discountPriceEndDatetime")));
    reqBean.setReservationPeriod(DateUtil.isPeriodString(reqparam.getDateTimeString("reservationStartDatetime"), reqparam
        .getDateTimeString("reservationEndDatetime")));
    reqBean.setCommodityPointPeriod(DateUtil.isPeriodString(reqparam.getDateTimeString("commodityPointStartDatetime"), reqparam
        .getDateTimeString("commodityPointEndDatetime")));
    reqBean.setCommodityPointRate(reqparam.get("commodityPointRate"));
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    Campaign campaign = catalogSvc.getAppliedCampaignInfo(reqBean.getShopCode(), reqBean.getCommodityCode());
    reqBean.setCampaignPeriod(campaign != null);

    // 期間区分によるPriceModeの設定
    if (reqBean.isDiscountPeriod()) {
      // 特別価格期間の場合
      reqBean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
    } else if (reqBean.isReservationPeriod()) {
      // 予約期間の場合
      reqBean.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
    } else {
      reqBean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    }

    // その他header基本情報
    reqBean.setCommodityTaxType(reqparam.get("taxType"));
    reqBean.setStockManagementType(reqparam.get("stockManagementType"));
    reqBean.setStockSufficientThreshold(NumUtil.toLong(reqparam.get("stockThreshold")));

    // detail情報
    // 価格情報
    BigDecimal unitPrice = NumUtil.parse(reqparam.get("unitPrice"));
    BigDecimal discountPrice = NumUtil.parse(reqparam.get("discountPrice"));
    BigDecimal reservationPrice = NumUtil.parse(reqparam.get("reservationPrice"));
    TaxUtil u = DIContainer.get("TaxUtil");
    unitPrice = Price.getPriceIncludingTax(unitPrice, u.getTaxRate(), reqparam.get("taxType"));
    discountPrice = Price.getPriceIncludingTax(discountPrice, u.getTaxRate(), reqparam.get("taxType"));
    reservationPrice = Price.getPriceIncludingTax(reservationPrice, u.getTaxRate(), reqparam.get("taxType"));

    // 新規登録時
    if (details.isEmpty()) {
      reqBean.setStandardSize(1L);
      DetailCartDetail detail = new DetailCartDetail();
      detail.setSkuCode(reqparam.get("representSkuCode"));
      // 価格
      detail.setUnitPrice(String.valueOf(unitPrice));
      detail.setDiscountPrice(String.valueOf(discountPrice));
      detail.setReservationPrice(String.valueOf(reservationPrice));
      // 在庫
      if (reqBean.getStockManagementType().equals(StockManagementType.NONE.getValue())
          || reqBean.getStockManagementType().equals(StockManagementType.NOSTOCK.getValue())) {
        detail.setAvailableStockQuantity("-1");
        detail.setDisplayCartButton(true);
      } else {
        // 新規登録かつ在庫管理する場合は常に在庫切れ状態
        detail.setAvailableStockQuantity("0");
        detail.setDisplayCartButton(false);
        // 在庫状況
        if (reqBean.getStockManagementType().equals(StockManagementType.WITH_STATUS.getValue())) {
          StockStatus stockResult = catalogSvc.getStockStatus(reqBean.getShopCode(), NumUtil.toLong(reqparam.get("stockStatusNo")));
          detail.setStockStatusMessage(stockResult.getOutOfStockMessage());
          reqBean.setOutOfStockMessage(stockResult.getOutOfStockMessage());
        }
      }
      detail.setArrivalGoodsFlg(reqparam.get("arrivalGoodsFlg").equals(ArrivalGoodsFlg.ACCEPTABLE.getValue()));
      details.add(0, detail);
      return;
    }

    // 更新時
    // 代表SKUにデータを設定
    for (DetailCartDetail d : details) {
      if (!d.getSkuCode().equals(reqparam.get("representSkuCode"))) {
        continue;
      }
      // 価格
      if (reqBean.getStandardSize() > 1L) { // 規格あり：価格表示の書式に変換したものをJSで表示
        d.setUnitPrice(getFormatPrice(unitPrice));
        d.setDiscountPrice(getFormatPrice(discountPrice));
        d.setReservationPrice(getFormatPrice(reservationPrice));
      } else { // 規格なし：price.tagで価格表示の書式に変換するためここでは変換不要
        d.setUnitPrice(String.valueOf(unitPrice));
        d.setDiscountPrice(String.valueOf(discountPrice));
        d.setReservationPrice(String.valueOf(reservationPrice));
      }
      // 在庫
      if (reqBean.getStockManagementType().equals(StockManagementType.NONE.getValue())
          || reqBean.getStockManagementType().equals(StockManagementType.NOSTOCK.getValue())) {
        d.setAvailableStockQuantity("-1");
        d.setDisplayCartButton(true);
      } else {
        long stockQuantity = 0L;
        if (reqBean.isReservationPeriod()) {
          stockQuantity = catalogSvc.getReservationAvailableStock(reqBean.getShopCode(), d.getSkuCode());
        } else {
          stockQuantity = catalogSvc.getAvailableStock(reqBean.getShopCode(), d.getSkuCode());
        }
        if (stockQuantity <= 0L) {
          stockQuantity = 0L;
          d.setDisplayCartButton(false);
        }
        d.setAvailableStockQuantity(String.valueOf(stockQuantity));

        // 在庫状況
        if (reqBean.getStockManagementType().equals(StockManagementType.WITH_STATUS.getValue())) {
          StockStatus stockResult = catalogSvc.getStockStatus(reqBean.getShopCode(), NumUtil.toLong(reqparam.get("stockStatusNo")));
          if (stockQuantity <= 0L) {
            d.setStockStatusMessage(stockResult.getOutOfStockMessage());
            reqBean.setOutOfStockMessage(stockResult.getOutOfStockMessage());
          } else if (stockQuantity <= stockResult.getStockSufficientThreshold()) {
            d.setStockStatusMessage(stockResult.getStockLittleMessage());
            reqBean.setStockLittleMessage(stockResult.getStockLittleMessage());
          } else {
            d.setStockStatusMessage(stockResult.getStockSufficientMessage());
            reqBean.setStockSufficientMessage(stockResult.getStockSufficientMessage());
          }
        }
      }
      d.setArrivalGoodsFlg(reqparam.get("arrivalGoodsFlg").equals(ArrivalGoodsFlg.ACCEPTABLE.getValue()));
    }
    // 10.1.4 10036 修正 ここまで

  }

  /**
   * ログイン情報を取得します
   * 
   * @return frontLoginInfo
   */
  public FrontLoginInfo getLoginInfo() {
    LoginInfo loginInfo = getSessionContainer().getLoginInfo();
    FrontLoginInfo frontLoginInfo = null;

    if (loginInfo == null) {
      frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
    } else {
      if (loginInfo instanceof FrontLoginInfo) {
        frontLoginInfo = (FrontLoginInfo) loginInfo;
      } else {
        frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
      }
    }
    return frontLoginInfo;
  }

  private String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }

  /**
   * プレビューモードかどうかを検証します。
   * 
   * @param bean
   * @return
   */
  private boolean checkPreviewDigest(DetailCartBean bean) {
    boolean isPreview = false;
    if (StringUtil.hasValue(bean.getPreviewDigest())) {
      String digest = PasswordUtil.decrypt(bean.getPreviewDigest());
      String[] keys = digest.split("/");
      String shopCode = getPathInfo(0);
      String commodityCode = getPathInfo(1);

      String startDatetime = DateUtil.toDateTimeString(DateUtil.addHour(DateUtil.getSysdate(), -3), DateUtil.TIMESTAMP_FORMAT);
      String endDatetime = DateUtil.toDateTimeString(DateUtil.addMinute(DateUtil.getSysdate(), 3), DateUtil.TIMESTAMP_FORMAT);
      String whenString = keys[2];

      boolean isValid = false;
      isValid = shopCode.equals(keys[0]);
      isValid &= commodityCode.equals(keys[1]);
      isValid &= ValidatorUtil.lessThan(startDatetime, whenString);
      isValid &= ValidatorUtil.lessThan(whenString, endDatetime);

      if (isValid) {
        isPreview = true;
      } else {
        throw new URLNotFoundException();
      }
    }
    return isPreview;
  }

  // 10.1.4 K00175 追加 ここから
  private boolean isReserved(CommodityHeader header) {
    if (header.getReservationStartDatetime() == null && header.getReservationEndDatetime() == null) {
      return false;
    } else {
      return DateUtil.isPeriodDate(header.getReservationStartDatetime(), header.getReservationEndDatetime());
    }
  }

  // 10.1.4 K00175 追加 ここまで

  // 20120413 shen add start
  /**
   * Date型の値をStringに変換する。<BR>
   * システム最大(or最小)日時が設定されている場合は、<BR>
   * 未入力と判断し、""(空文字)を設定する
   * 
   * @param date
   * @return dateString
   */
  private String toDateTimeString(Date date) {
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

  // 20120413 shen add end

  // 2012/11/16 促销对应 ob add start
  /**
   *推荐套餐商品设置
   * 
   * @param CommodityContainer
   * @param DetailCartBean
   * @param CatalogService
   */
  private void getRecommendSetCommodity(CommodityContainer headerContainer, DetailCartBean bean, CatalogService service) {
    // 根据子商品取得套餐商品
    List<String> setCommodityCompositions = service.getSetCommodityByClildCommodity(headerContainer.getCommodityHeader()
        .getCommodityCode(), bean.getShopCode());
    if (setCommodityCompositions != null && setCommodityCompositions.size() > 0) {
      List<String> setCommoditys = new ArrayList<String>();
      for (String setCommodityComposition : setCommodityCompositions) {
        if (!setCommoditys.contains(setCommodityComposition)) {
          setCommoditys.add(setCommodityComposition);
        }
      }

      // 创建套餐商品信息
      for (String setCommodityCode : setCommoditys) {
        boolean addFlg = true;
        for (RecommendSet rec : bean.getRecommendSets()) {
          if (rec.getSetCommodityCode().equals(setCommodityCode))
            addFlg = false;
        }
        if (addFlg) {
          RecommendSet recommendset = getSetComposition(setCommodityCode, bean.getShopCode(), service);
          if (recommendset != null) {
            bean.getRecommendSets().add(recommendset);
          }
        }
      }
    }
  }

  /**
   *套餐信息取得
   * 
   * @param DetailCartDetail
   * @param CommodityContainer
   * @param DetailCartBean
   * @param CatalogService
   * @return 子商品取得是否成功
   */
  private boolean getSetCommodity(DetailCartDetail detailCartDetail, CommodityContainer headerContainer, DetailCartBean bean,
      CatalogService service) {
    // 取得子商品
    List<CommodityCompositionContainer> commodityCompositionContainerList = service.getCommodityCompositionContainerList(bean
        .getShopCode(), headerContainer.getCommodityHeader().getCommodityCode());
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();
    if (commodityCompositionContainerList == null
        || commodityCompositionContainerList.size() < numberPolicy.getMinSetNum().intValue()) {
      return false;
    }

    BigDecimal totalWeight = new BigDecimal(0);
    boolean saleFlg = true;
    BigDecimal totalPrce = new BigDecimal(0);

    // 构成品列表
    List<DetailCartComposition> compositionList = new ArrayList<DetailCartComposition>();
    for (CommodityCompositionContainer child : commodityCompositionContainerList) {

      if (!child.isSalableComposition()) {
        saleFlg = false;
      }

      DetailCartComposition compositionHeader = new DetailCartComposition();
      compositionHeader.setShopCode(child.getShopCode());
      compositionHeader.setCommodityCode(child.getCommodityCode());

      // 语言判断
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        compositionHeader.setCommodityName(child.getCommodityName());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        compositionHeader.setCommodityName(child.getCommodityNameJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        compositionHeader.setCommodityName(child.getCommodityNameEn());
      }

      // 价格设置
      compositionHeader.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      compositionHeader.setTax(TaxType.INCLUDED.getValue());
      compositionHeader.setStockManagementType(child.getStockManagementType().toString());
      compositionHeader.setRepresentSkuCode(child.getRepresentSkuCode());
      compositionHeader.setSelectedSkuCode(child.getRepresentSkuCode());

      // 规格名称设置
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        compositionHeader.setCommodityStandard1Name(child.getCommodityStandard1Name());
        compositionHeader.setCommodityStandard2Name(child.getCommodityStandard2Name());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        compositionHeader.setCommodityStandard1Name(child.getCommodityStandard1NameJp());
        compositionHeader.setCommodityStandard1Name(child.getCommodityStandard1NameJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        compositionHeader.setCommodityStandard1Name(child.getCommodityStandard1NameEn());
        compositionHeader.setCommodityStandard2Name(child.getCommodityStandard2NameEn());
      }
      compositionHeader.setSalableComposition(true);
      String standardAssortment;
      if (StringUtil.hasValue(child.getCommodityStandard1Name()) && StringUtil.hasValue(child.getCommodityStandard2Name())) {
        standardAssortment = STANDARD_BOTH;
      } else if (StringUtil.hasValue(child.getCommodityStandard1Name())
          && StringUtil.isNullOrEmpty(child.getCommodityStandard2Name())) {
        standardAssortment = STANDARD_ONLY1;
      } else if (StringUtil.isNullOrEmpty(child.getCommodityStandard1Name())
          && StringUtil.hasValue(child.getCommodityStandard2Name())) {
        standardAssortment = STANDARD_ONLY2;
      } else {
        standardAssortment = STANDARD_NONE;
      }
      compositionHeader.setStandardAssortment(standardAssortment);

      // 重量设置
      List<DetailCartDetail> compositionSkuList = new ArrayList<DetailCartDetail>();
      if (child.getCommodityDetailList() != null && child.getCommodityDetailList().size() > 0) {
        for (CommodityDetail childDetail : child.getCommodityDetailList()) {
          if (childDetail.getSkuCode().equals(compositionHeader.getSelectedSkuCode())) {
            totalWeight = totalWeight.add(childDetail.getWeight());
            break;
          }
        }
      }

      for (CommodityDetail detail : child.getCommodityDetailList()) {
        // 价格设定
        if (detail.getSkuCode().equals(compositionHeader.getSelectedSkuCode())) {
          // 合计金额
          totalPrce = totalPrce.add(NumUtil.parse(detail.getUnitPrice().toString()));
          compositionHeader.setRepresentPrice(detail.getUnitPrice().toString());
        }

        DetailCartDetail compositionSku = new DetailCartDetail();
        // 规格添加
        StringBuffer standerName = new StringBuffer("");

        // 規格设置
        if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
          if (StringUtil.hasValueAllOf(detail.getStandardDetail1Name(), detail.getStandardDetail2Name())) {
            standerName.append(detail.getStandardDetail1Name());
            standerName.append("/");
            standerName.append(detail.getStandardDetail2Name());
          } else {
            if (StringUtil.hasValue(detail.getStandardDetail1Name())) {
              standerName.append(detail.getStandardDetail1Name());
            }

            if (StringUtil.hasValue(detail.getStandardDetail2Name())) {
              standerName.append(detail.getStandardDetail2Name());
            }
          }

        } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
          if (StringUtil.hasValueAllOf(detail.getStandardDetail1NameJp(), detail.getStandardDetail2NameJp())) {
            standerName.append(detail.getStandardDetail1NameJp());
            standerName.append("/");
            standerName.append(detail.getStandardDetail2NameJp());
          } else {
            if (StringUtil.hasValue(detail.getStandardDetail1NameJp())) {
              standerName.append(detail.getStandardDetail1NameJp());
            }
            if (StringUtil.hasValue(detail.getStandardDetail2NameJp())) {
              standerName.append(detail.getStandardDetail2NameJp());
            }
          }
        } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
          if (StringUtil.hasValueAllOf(detail.getStandardDetail1NameEn(), detail.getStandardDetail2NameEn())) {
            standerName.append(detail.getStandardDetail1NameEn());
            standerName.append("/");
            standerName.append(detail.getStandardDetail2NameEn());
          } else {
            if (StringUtil.hasValue(detail.getStandardDetail1NameEn())) {
              standerName.append(detail.getStandardDetail1NameEn());
            }
            if (StringUtil.hasValue(detail.getStandardDetail2NameEn())) {
              standerName.append(detail.getStandardDetail2NameEn());
            }
          }
        }

        // 下拉列表设置
        compositionHeader.getStander().add(new NameValue(standerName.toString(), detail.getSkuCode()));
        compositionSku.setSkuCode(detail.getSkuCode());
        compositionSku.setStandardDetail1Name(detail.getStandardDetail1Name());
        compositionSku.setStandardDetail2Name(detail.getStandardDetail2Name());
        compositionSku.setUnitPrice(detail.getUnitPrice().toString());
        compositionSkuList.add(compositionSku);
      }

      compositionHeader.setStandardSize((long) compositionSkuList.size());
      compositionHeader.setCompositionSkuList(compositionSkuList);
      compositionList.add(compositionHeader);
    }

    bean.setCompositionList(compositionList);
    String unitPrice = totalPrce.toString();
    detailCartDetail.setUnitPrice(unitPrice);
    detailCartDetail.setWeight(totalWeight.toString());

    return saleFlg;
  }


  
  /**
   *根据商品信息创建套餐信息
   * 
   * @param 商品编号
   * @param 店铺编号
   * @param CatalogService
   * @return 推荐套餐
   */
  private RecommendSet getSetComposition(String commodityCode, String shopCode, CatalogService service) {
//    TaxUtil tu = DIContainer.get("TaxUtil");
//    Long taxRate = tu.getTaxRate();
    SetCommodityInfo setInfo = service.getSetComposition(commodityCode, shopCode);

    if (setInfo == null) {
      return null;
    }

    Long CampaignAvailability = service.campaignAvailability(commodityCode, shopCode, null);
    if (CampaignAvailability == null || CampaignAvailability == 0) {
      return null;
    }

    // 套餐商品价格获取
//    Campaign campaign = service.getAppliedCampaignInfo(shopCode, commodityCode);
//    Price price = new Price(setInfo.getSetCommodity().getCommodityHeader(), setInfo.getSetCommodity().getCommodityDetail(),
//        campaign, taxRate);
    SetCommodityComposition suit = service.getSuitSalePrice(commodityCode);
    
    RecommendSet recommendSet = new RecommendSet();
    recommendSet.setSetCommodityCode(setInfo.getSetCommodity().getCommodityHeader().getCommodityCode());
    recommendSet.setSetPrice(NumUtil.formatCurrency(suit.getRetailPrice()));
    recommendSet.setTaxType(setInfo.getSetCommodity().getCommodityHeader().getCommodityTaxType());
    recommendSet.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();

    // 语言判断
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      recommendSet.setSetCommodityName(setInfo.getSetCommodity().getCommodityHeader().getCommodityName());
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      recommendSet.setSetCommodityName(setInfo.getSetCommodity().getCommodityHeader().getCommodityNameJp());
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      recommendSet.setSetCommodityName(setInfo.getSetCommodity().getCommodityHeader().getCommodityNameEn());
    }

    BigDecimal totalPrice = new BigDecimal(0);
    // 子商品价格设定
    for (CommodityContainer childCommodity : setInfo.getChildCommodity()) {
      DetailRecommendBaseBean child = new DetailRecommendBaseBean();
      child.setCommodityCode(childCommodity.getCommodityHeader().getCommodityCode());
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        child.setCommodityName(childCommodity.getCommodityHeader().getCommodityName());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        child.setCommodityName(childCommodity.getCommodityHeader().getCommodityNameJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        child.setCommodityName(childCommodity.getCommodityHeader().getCommodityNameEn());
      }
      child.setSkuCode(childCommodity.getCommodityHeader().getRepresentSkuCode());
      child.setUnitPrice(childCommodity.getCommodityDetail().getUnitPrice().toString());
      child.setShopCode(shopCode);
      child.setCommodityTaxType(childCommodity.getCommodityHeader().getCommodityTaxType().toString());
      child.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      recommendSet.getChildCommodity().add(child);
      // 合计获取
      totalPrice = totalPrice.add(childCommodity.getCommodityDetail().getUnitPrice());
    }
//    totalPrice = totalPrice.add(price.getRetailPrice().negate());

    recommendSet.setSetSkuCode(setInfo.getSetCommodity().getCommodityHeader().getRepresentSkuCode());
    recommendSet.setUnitPrice(NumUtil.formatCurrency(totalPrice.toString()));
    recommendSet.setDisplayCartButton(!setInfo.isSingleSku());
    recommendSet.setCheapPrice(NumUtil.formatCurrency(totalPrice.subtract(suit.getRetailPrice()).toString()));
    return recommendSet;
  }
  // 2012/11/16 促销对应 ob add end
}
