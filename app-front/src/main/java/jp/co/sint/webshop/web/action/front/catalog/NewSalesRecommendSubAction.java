package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;
import jp.co.sint.webshop.web.bean.front.catalog.NewSalesRecommendBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.SessionUrl;

import org.apache.log4j.Logger;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewSalesRecommendSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if (sesContainer.getSession() != null) {
      logger.info("当前NewSalesRecommendSubAction:sessionID=" + sesContainer.getSession().getId()
          + "开始记录--------------------------------------------------------------------");
    } else {
      logger
          .info("当前NewSalesRecommendSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    NewSalesRecommendBean reqBean = (NewSalesRecommendBean) getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    // 品店精选 start
    List<CommodityHeadline> pageList = service.getChosenSortCommodity(1L);
//    List<CommodityHeadline> chReserveList = service.getIndexBatchCommodityNoCache(1L);
    List<CommodityHeadline> chReserveList = service.getIndexBatchCommodity(1L);

    boolean choseAddFlg = true;
    for (CommodityHeadline line : chReserveList) {
      for (CommodityHeadline line2 : pageList) {
        if (line.getCommodityCode().equals(line2.getCommodityCode())) {
          choseAddFlg = false;
          break;
        } else {
          choseAddFlg = true;
        }
      }
      if (choseAddFlg) {
        pageList.add(line);
      }

    }

    List<CommodityHeadline> pageListFinal = new ArrayList<CommodityHeadline>();
    for (int i = 0; i < 10; i++) {
//      CommodityHeadline ch = pageList.get(i);
      
      CommodityHeadline ch = new CommodityHeadline();
      CommodityHeadline chl = pageList.get(i);
      ch = (CommodityHeadline)chl.clone();
      String priceMode = "1";
      if (isSale(ch)) {
        priceMode = CommodityPriceType.UNIT_PRICE.getValue();
      } else if (isDiscount(ch)) {
        priceMode = CommodityPriceType.DISCOUNT_PRICE.getValue();
      } else {
        priceMode = CommodityPriceType.UNIT_PRICE.getValue();
      }
      ch.setPriceMode(priceMode);

      // 如果为限时限量商品（秒杀商品）则discountCommodityflg为true，否则false
      DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(ch.getCommodityCode());
      if (dc == null) {
        ch.setDiscountCommodityFlg(false);
      } else {
        ch.setDiscountCommodityFlg(true);
      }

      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        ch.setCommodityName(ch.getCommodityNameCn());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        ch.setCommodityName(ch.getCommodityNameEn());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        ch.setCommodityName(ch.getCommodityNameJp());
      }

      // 组合品价格处理start
      if (StringUtil.hasValue(ch.getOriginalCommodityCode()) && ch.getCombinationAmount() != null) {
        ch.setUnitPrice(ch.getUnitPrice().multiply(new BigDecimal(ch.getCombinationAmount())));
        if (ch.getDiscountPrice() != null) {
          ch.setDiscountPrice(ch.getDiscountPrice().multiply(new BigDecimal(ch.getCombinationAmount())));
        }

      } else {
        ch.setUnitPrice(ch.getUnitPrice());
        ch.setDiscountPrice(ch.getDiscountPrice());
      }
      // 组合品价格处理end

      pageListFinal.add(ch);
    }
    reqBean.setPageList(pageListFinal);
    // 品店精选 end
    // 热销商品 start

    

//    List<CommodityHeadline> chListSale = service.getHotSaleCommodityNoCache(currentLanguageCode);
//    // 每天batch跑出来的热销商品
//    List<CommodityHeadline> pageHotList = service.getIndexBatchCommodityNoCache(2L);
//    // 前台显示热销商品集合
//    List<CommodityHeadline> chList2 = new ArrayList<CommodityHeadline>();
    List<CommodityHeadline> chList2 =getHotSaleList(reqBean);
//    chList2.addAll(chListSale);
//    boolean addFlg = true;
//    for (CommodityHeadline line : pageHotList) {
//      for (CommodityHeadline line2 : chListSale) {
//        if (line.getCommodityCode().equals(line2.getCommodityCode())) {
//          addFlg = false;
//          break;
//        } else {
//          addFlg = true;
//        }
//      }
//      if (addFlg) {
//        chList2.add(line);
//      }
//
//    }

    List<CommodityHeadline> pageHotListFinal = new ArrayList<CommodityHeadline>();
    
    int len = 6;
    if (chList2 == null) {
      len = 0;
    } else {
      if (chList2.size() < 6) {
        len = chList2.size();
      }
    }

    for (int i = 0; i < len; i++) {
      String priceMode = "1";
      CommodityHeadline ch =  null;
      CommodityHeadline chl = chList2.get(i);
      ch = (CommodityHeadline)chl.clone();
      if (isSale(ch)) {
        priceMode = CommodityPriceType.UNIT_PRICE.getValue();
      } else if (isDiscount(ch)) {
        priceMode = CommodityPriceType.DISCOUNT_PRICE.getValue();
      } else {
        priceMode = CommodityPriceType.UNIT_PRICE.getValue();
      }
      ch.setPriceMode(priceMode);

      // 如果为限时限量商品（秒杀商品）则discountCommodityflg为true，否则false
      DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(ch.getCommodityCode());
      if (dc == null) {
        ch.setDiscountCommodityFlg(false);
      } else {
        ch.setDiscountCommodityFlg(true);
      }

      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        ch.setCommodityName(ch.getCommodityNameCn());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        ch.setCommodityName(ch.getCommodityNameEn());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        ch.setCommodityName(ch.getCommodityNameJp());
      }

      // 组合品价格处理start
      if (StringUtil.hasValue(ch.getOriginalCommodityCode()) && ch.getCombinationAmount() != null) {
        ch.setUnitPrice(ch.getUnitPrice().multiply(new BigDecimal(ch.getCombinationAmount())));
        if (null != ch.getDiscountPrice()) {
          ch.setDiscountPrice(ch.getDiscountPrice().multiply(new BigDecimal(ch.getCombinationAmount())));
        }
      } else {
        ch.setUnitPrice(ch.getUnitPrice());
        ch.setDiscountPrice(ch.getDiscountPrice());
      }
      // 组合品价格处理end

      pageHotListFinal.add(ch);
    }
    reqBean.setPageHotList(pageHotListFinal);
    // 热销商品 end
    setBean(reqBean);

    if (sesContainer.getSession() != null) {
      logger.info("当前NewSalesRecommendSubAction:sessionID=" + sesContainer.getSession().getId()
          + "结束记录--------------------------------------------------------------------");
    } else {
      logger
          .info("当前NewSalesRecommendSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
  }

  public boolean isDiscount(CommodityHeadline ch) {
    if (ch.getDiscountPriceStartDatetime() == null && ch.getDiscountPriceEndDatetime() == null) {
      return false;
    } else {
      return DateUtil.isPeriodDate(ch.getDiscountPriceStartDatetime(), ch.getDiscountPriceEndDatetime());
    }
  }

  public boolean isSale(CommodityHeadline ch) {
    if (this.isDiscount(ch) || this.isReserved(ch)) {
      return false;
    } else {
      return DateUtil.isPeriodDate(ch.getSaleStartDatetime(), ch.getSaleEndDatetime());
    }
  }

  public boolean isReserved(CommodityHeadline ch) {
    if (ch.getReservationStartDatetime() == null && ch.getReservationEndDatetime() == null) {
      return false;
    } else {
      return DateUtil.isPeriodDate(ch.getReservationStartDatetime(), ch.getReservationEndDatetime());
    }
  }

  /**
   * お気に入り商品を設定します。
   * 
   * @param pageList
   * @param service
   * @param commodityList
   */
  public void setRecommendCommodity(List<List<DetailRecommendBaseBean>> pageList, CatalogService service,
      List<CommodityContainer> commodityList) {
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    List<DetailRecommendBaseBean> list = new ArrayList<DetailRecommendBaseBean>();
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    for (int j = 0; j < commodityList.size(); j++) {
      String shopCode = commodityList.get(j).getCommodityHeader().getShopCode();
      String commodityCode = commodityList.get(j).getCommodityHeader().getCommodityCode();
      Campaign campaign = catalogSvc.getAppliedCampaignInfo(shopCode, commodityCode);
      Price price = new Price(commodityList.get(j).getCommodityHeader(), commodityList.get(j).getCommodityDetail(), campaign,
          taxRate);

      DetailRecommendBaseBean commodity = new DetailRecommendBaseBean();
      commodity.setCommodityCode(commodityList.get(j).getCommodityHeader().getCommodityCode());
      // add by cs_yuli 20120514 start
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        commodity.setCommodityName(commodityList.get(j).getCommodityHeader().getCommodityName());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        commodity.setCommodityName(commodityList.get(j).getCommodityHeader().getCommodityNameEn());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        commodity.setCommodityName(commodityList.get(j).getCommodityHeader().getCommodityNameJp());
      }
      // add by cs_yuli 20120514 end
      if (StringUtil.hasValue(commodityList.get(j).getCommodityHeader().getOriginalCommodityCode())
          && commodityList.get(j).getCommodityHeader().getCombinationAmount() != null) {
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(
            new BigDecimal(commodityList.get(j).getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(
            new BigDecimal(commodityList.get(j).getCommodityHeader().getCombinationAmount()))));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice().multiply(
            new BigDecimal(commodityList.get(j).getCommodityHeader().getCombinationAmount()))));
      } else {
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
      }

      commodity.setCommodityTaxType(NumUtil.toString(commodityList.get(j).getCommodityHeader().getCommodityTaxType()));
      commodity.setCommodityPointRate(NumUtil.toString(commodityList.get(j).getCommodityHeader().getCommodityPointRate()));
      commodity.setReviewScore(NumUtil.toString(commodityList.get(j).getReviewSummary().getReviewScore()));
      commodity.setDiscountPeriod(price.isDiscount());
      commodity.setShopCode(commodityList.get(j).getCommodityHeader().getShopCode());
      commodity.setShopName(commodityList.get(j).getShop().getShopName());
      // add by yyq start 20130313
      if (commodityList.get(j).getCommodityHeader().getImportCommodityType() != null) {
        commodity.setImportCommodityType(commodityList.get(j).getCommodityHeader().getImportCommodityType());
      }
      if (commodityList.get(j).getCommodityHeader().getClearCommodityType() != null) {
        commodity.setClearCommodityType(commodityList.get(j).getCommodityHeader().getClearCommodityType());
      }
      if (commodityList.get(j).getCommodityHeader().getReserveCommodityType1() != null) {
        commodity.setReserveCommodityType1(commodityList.get(j).getCommodityHeader().getReserveCommodityType1());
      }
      if (commodityList.get(j).getCommodityHeader().getReserveCommodityType2() != null) {
        commodity.setReserveCommodityType2(commodityList.get(j).getCommodityHeader().getReserveCommodityType2());
      }
      if (commodityList.get(j).getCommodityHeader().getReserveCommodityType3() != null) {
        commodity.setReserveCommodityType3(commodityList.get(j).getCommodityHeader().getReserveCommodityType3());
      }
      if (commodityList.get(j).getCommodityHeader().getNewReserveCommodityType1() != null) {
        commodity.setNewReserveCommodityType1(commodityList.get(j).getCommodityHeader().getNewReserveCommodityType1());
      }
      if (commodityList.get(j).getCommodityHeader().getNewReserveCommodityType2() != null) {
        commodity.setNewReserveCommodityType2(commodityList.get(j).getCommodityHeader().getNewReserveCommodityType2());
      }
      if (commodityList.get(j).getCommodityHeader().getNewReserveCommodityType3() != null) {
        commodity.setNewReserveCommodityType3(commodityList.get(j).getCommodityHeader().getNewReserveCommodityType3());
      }
      if (commodityList.get(j).getCommodityHeader().getNewReserveCommodityType4() != null) {
        commodity.setNewReserveCommodityType4(commodityList.get(j).getCommodityHeader().getNewReserveCommodityType4());
      }
      if (commodityList.get(j).getCommodityHeader().getNewReserveCommodityType5() != null) {
        commodity.setNewReserveCommodityType5(commodityList.get(j).getCommodityHeader().getNewReserveCommodityType5());
      }
      if (commodityList.get(j).getCommodityDetail().getInnerQuantity() != null) {
        commodity.setInnerQuantity(commodityList.get(j).getCommodityDetail().getInnerQuantity());
      }
      // add by yyq end 20130313

      // 商品コードに関連付いている在庫が1つでもあるかどうかを判別
      commodity.setHasStock(service.hasStockCommodity(commodityList.get(j).getCommodityHeader().getShopCode(), commodityList.get(j)
          .getCommodityHeader().getCommodityCode()));

      // 商品別ポイント付与期間かどうかの判別
      commodity.setCommodityPointPeriod(price.isPoint());

      // 販売・特価・予約期間による違いを設定
      setSaleType(price, commodity);

      // 在庫管理区分区分が在庫数表示または在庫状況表示のときに
      // [在庫なし]を表示する
      switch (StockManagementType.fromValue(commodityList.get(j).getCommodityHeader().getStockManagementType())) {
        case WITH_QUANTITY:
        case WITH_STATUS:
          commodity.setDisplayStockStatus(true);
          break;
        case NONE:
        case NOSTOCK:
        default:
          commodity.setDisplayStockStatus(false);
          break;
      }

      list.add(commodity);

    }

    pageList.add(list);
  }

  /**
   * 商品の販売種別を設定します。
   * 
   * @param price
   * @param commodity
   */
  public void setSaleType(Price price, DetailRecommendBaseBean commodity) {
    if (price.isSale()) {
      // 通常の販売期間の場合
      commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      setAppliedCampaign(price, commodity);
    } else if (price.isDiscount()) {
      // 特別価格期間の場合
      commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
      setAppliedCampaign(price, commodity);
    } else if (price.isReserved()) {
      // 予約期間の場合
      commodity.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
      setAppliedCampaign(price, commodity);
    } else {
      commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      setAppliedCampaign(price, commodity);
    }
  }

  /**
   * @param price
   * @param commodity
   */
  public void setAppliedCampaign(Price price, DetailRecommendBaseBean commodity) {
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    if (price.getCampaignInfo() == null) {
      clearAppliedCampaign(commodity);
    } else {
      commodity.setCampaignPeriod(price.isCampaign());
      commodity.setCampaignCode(price.getCampaignInfo().getCampaignCode());
      commodity.setCampaignName(utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(), price.getCampaignInfo()
          .getCampaignNameEn(), price.getCampaignInfo().getCampaignNameJp()));
      commodity.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
    }
  }

  /**
   * @param commodity
   */
  public void clearAppliedCampaign(DetailRecommendBaseBean commodity) {
    commodity.setCampaignPeriod(false);
    commodity.setCampaignCode("");
    commodity.setCampaignName("");
    commodity.setCampaignDiscountRate("0");
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

  public List<CommodityHeadline> getHotSaleList(NewSalesRecommendBean reqBean) {

    String HALF_SPACE = " ";
    // reqBean.getList().clear();
    String[] urlParam = getRequestParameter().getPathArgs();

    if (urlParam.length == 2 && urlParam[0].equals("moveback")) {
      reqBean.setSearchWord(urlParam[1]);
    } else if (urlParam.length >= 1) {
      reqBean.setSearchCategoryCode(urlParam[0]);
    }

    // 検索条件の設定
    reqBean.setAlignmentSequence(CommodityDisplayOrder.BY_POPULAR_RANKING.getValue());
    PagerValue value = new PagerValue();

    // if (StringUtil.isNullOrEmpty(getClient()) ||
    // getClient().equals(ClientType.OTHER)) {
    // value.setPageSize(40);
    // } else {
    // WebshopConfig config = DIContainer.getWebshopConfig();
    // value.setPageSize(config.getMobilePageSize());
    // }
    // reqBean.setUrl(null);
    // if (reqBean.isSessionRead()) {
    SessionUrl url = getSessionContainer().getSessionUrl();
//    String uri = getSessionContainer().getSessionUrl().getUrl();
    if (url != null) {
      // reqBean.setUrl(uri);
      // reqBean.setDisplayCategoryFlg(url.isDisplayCategoryFlg());
      // 品店精选
      if (StringUtil.hasValue(url.getSelected())) {
        reqBean.setSearchSelected(url.getSelected());
        reqBean.setSelectedFlg(true);
      } else {
        reqBean.setSelectedFlg(false);
      }
      // 商品目录
      if (StringUtil.hasValue(url.getCategoryCode())) {
        reqBean.setSearchCategoryCode(url.getCategoryCode());
      }
      // 品牌
      if (StringUtil.hasValue(url.getBrandCode())) {
        reqBean.setSearchBrandCode(url.getBrandCode());
      }
      // 评论区域
      if (StringUtil.hasValue(url.getReviewScore())) {
        reqBean.setSearchReviewScore(url.getReviewScore());
      }
      // 价格区域
      if (StringUtil.hasValue(url.getPriceType())) {
        CodeAttribute price = PriceList.fromValue(url.getPriceType());
        reqBean.setSearchPrice(url.getPriceType());
        if (price != null && !StringUtil.isNullOrEmpty(price.getName())) {
          String[] prices = price.getName().split(",");
          if (prices.length == 2) {
            reqBean.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
            reqBean.setSearchPriceEnd(NumUtil.parse(prices[1]).abs().toString());
          } else {
            reqBean.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
            reqBean.setSearchPriceEnd("9999999999");
          }
        }
      } else {
        if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())) {
          reqBean.setSearchPriceStart(NumUtil.parse(url.getPriceStart()).abs().toString());
          reqBean.setPriceStart(NumUtil.parse(url.getPriceStart()).abs().toString());
        }
        if (StringUtil.hasValue(url.getPriceEnd()) && NumUtil.isDecimal(url.getPriceEnd())) {
          reqBean.setSearchPriceEnd(NumUtil.parse(url.getPriceEnd()).abs().toString());
          reqBean.setPriceEnd(NumUtil.parse(url.getPriceEnd()).abs().toString());
          if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())
              && BigDecimalUtil.isAbove(NumUtil.parse(url.getPriceStart()), NumUtil.parse(url.getPriceEnd()))) {
            String tempSearchPriceStart = reqBean.getSearchPriceStart();
            reqBean.setSearchPriceStart(NumUtil.parse(url.getPriceEnd()).toString());
            reqBean.setSearchPriceEnd(NumUtil.parse(tempSearchPriceStart).toString());
            reqBean.setPriceStart(reqBean.getSearchPriceStart());
            reqBean.setPriceEnd(reqBean.getSearchPriceEnd());
          }
        }
        if (StringUtil.hasValue(url.getPriceStart()) && NumUtil.isDecimal(url.getPriceStart())) {
          if (url.getPriceEnd().equals("")) {
            reqBean.setSearchPriceEnd("9999999999");
          }
        }
      }
      // 属性1
      if (StringUtil.hasValue(url.getAttribute1())) {
        reqBean.setSearchAttribute1(url.getAttribute1());
      }
      // 属性2
      if (StringUtil.hasValue(url.getAttribute2())) {
        reqBean.setSearchAttribute2(url.getAttribute2());
      }
      // 属性3
      if (StringUtil.hasValue(url.getAttribute3())) {
        reqBean.setSearchAttribute3(url.getAttribute3());
      }
      // 排序
      if (StringUtil.hasValue(url.getSort())) {
        reqBean.setAlignmentSequence(url.getSort());
      }
      // 页数
      if (StringUtil.hasValue(url.getPageSize())) {
        if (NumUtil.isNum(url.getPageSize())) {
          value.setPageSize(Integer.parseInt(url.getPageSize()));
        } else {
          value.setPageSize(Integer.parseInt("1"));
        }
      }
      if (StringUtil.hasValue(url.getCurrentPage())) {
        if (NumUtil.isNum(url.getCurrentPage())) {
          value.setCurrentPage(Integer.parseInt(url.getCurrentPage()));
        } else {
          value.setCurrentPage(Integer.parseInt("1"));
        }
      }
      // 关键字
      if (StringUtil.hasValue(url.getSearchWord())) {
        reqBean.setSearchWord(url.getSearchWord());
      }
      // 关键字
      if (StringUtil.hasValue(url.getSearchWord())) {
        reqBean.setEncoded_searchWord(url.getSearchWord());
      }
      // 进口品
      if (StringUtil.hasValue(url.getImportCommodityType())) {
        reqBean.setImportCommodityType(url.getImportCommodityType());
      }
      // 清仓品
      if (StringUtil.hasValue(url.getClearCommodityType())) {
        reqBean.setClearCommodityType(url.getClearCommodityType());
      }
      // asahi商品
      if (StringUtil.hasValue(url.getReserveCommodityType1())) {
        reqBean.setReserveCommodityType1(url.getReserveCommodityType1());
      }
      // hot商品
      if (StringUtil.hasValue(url.getReserveCommodityType2())) {
        reqBean.setReserveCommodityType2(url.getReserveCommodityType2());
      }
      // 商品展示区分
      if (StringUtil.hasValue(url.getReserveCommodityType3())) {
        reqBean.setReserveCommodityType3(url.getReserveCommodityType3());
      }
      // 预留商品区分1
      if (StringUtil.hasValue(url.getNewReserveCommodityType1())) {
        reqBean.setNewReserveCommodityType1(url.getNewReserveCommodityType1());
      }
      // 预留商品区分2
      if (StringUtil.hasValue(url.getNewReserveCommodityType2())) {
        reqBean.setNewReserveCommodityType2(url.getNewReserveCommodityType2());
      }
      // 预留商品区分3
      if (StringUtil.hasValue(url.getNewReserveCommodityType3())) {
        reqBean.setNewReserveCommodityType3(url.getNewReserveCommodityType3());
      }
      // 预留商品区分4
      if (StringUtil.hasValue(url.getNewReserveCommodityType4())) {
        reqBean.setNewReserveCommodityType4(url.getNewReserveCommodityType4());
      }
      // 预留商品区分5
      if (StringUtil.hasValue(url.getNewReserveCommodityType5())) {
        reqBean.setNewReserveCommodityType5(url.getNewReserveCommodityType5());
      }
    }
    // }
    reqBean.setPagerValue(value);
    String str = reqBean.getSearchWord();
    str = str.replace("/", HALF_SPACE);
    str = str.replace("\\", HALF_SPACE);
    reqBean.setSearchWord(str);
    reqBean.setEncoded_searchWord(str);

    CommodityContainerCondition condition = setSearchCondition(reqBean);
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("importCommodityType"))) {
      condition.setImportCommodityType(getRequestParameter().get("importCommodityType"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("clearCommodityType"))) {
      condition.setClearCommodityType(getRequestParameter().get("clearCommodityType"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("reserveCommodityType1"))) {
      condition.setReserveCommodityType1(getRequestParameter().get("reserveCommodityType1"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("reserveCommodityType2"))) {
      condition.setReserveCommodityType2(getRequestParameter().get("reserveCommodityType2"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("reserveCommodityType3"))) {
      condition.setReserveCommodityType3(getRequestParameter().get("reserveCommodityType3"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType1"))) {
      condition.setNewReserveCommodityType1(getRequestParameter().get("newReserveCommodityType1"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType2"))) {
      condition.setNewReserveCommodityType2(getRequestParameter().get("newReserveCommodityType2"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType3"))) {
      condition.setNewReserveCommodityType3(getRequestParameter().get("newReserveCommodityType3"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType4"))) {
      condition.setNewReserveCommodityType4(getRequestParameter().get("newReserveCommodityType4"));
    }
    if (!StringUtil.isNullOrEmpty(getRequestParameter().get("newReserveCommodityType5"))) {
      condition.setNewReserveCommodityType5(getRequestParameter().get("newReserveCommodityType5"));
    }
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    condition.setForHotSale("hotSale");
    condition.setPageSize(10);

    SearchResult<CommodityHeadline> result = service.fastFindCommodityHeaderLine(condition, false);
    if (result == null) {
      return null;
    }
    return result.getRows();

  }

  public CommodityContainerCondition setSearchCondition(NewSalesRecommendBean reqBean) {
    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setSearchMethod(reqBean.getSearchMethod());
    condition.setSearchSelected(reqBean.getSearchSelected());
    condition.setSearchWord(reqBean.getSearchWord()); // 关键字
    condition.setSearchCategoryCode(reqBean.getSearchCategoryCode()); // 商品目录
    condition.setSearchBrandCode(reqBean.getSearchBrandCode()); // 品牌
    condition.setReviewScore(reqBean.getSearchReviewScore()); // 评论
    condition.setSearchPriceStart(reqBean.getSearchPriceStart()); // 开始价格
    condition.setSearchPriceEnd(reqBean.getSearchPriceEnd()); // 结束价格
    condition.setSearchCategoryAttribute1(reqBean.getSearchAttribute1()); // 商品属性1
    condition.setSearchCategoryAttribute2(reqBean.getSearchAttribute2()); // 商品属性2
    condition.setSearchCategoryAttribute3(reqBean.getSearchAttribute3()); // 商品属性3
    condition.setPageSize(reqBean.getPagerValue().getPageSize()); // 件数表示
    condition.setAlignmentSequence(reqBean.getAlignmentSequence()); // 排序
    condition.setImportCommodityType(reqBean.getImportCommodityType()); // 进口品
    condition.setClearCommodityType(reqBean.getClearCommodityType()); // 清仓品
    condition.setReserveCommodityType1(reqBean.getReserveCommodityType1()); // asahi商品
    condition.setReserveCommodityType2(reqBean.getReserveCommodityType2()); // hot商品
    condition.setReserveCommodityType3(reqBean.getReserveCommodityType3()); // 商品展示区分
    condition.setNewReserveCommodityType1(reqBean.getNewReserveCommodityType1()); // 预约商品1
    condition.setNewReserveCommodityType2(reqBean.getNewReserveCommodityType2()); // 预约商品2
    condition.setNewReserveCommodityType3(reqBean.getNewReserveCommodityType3()); // 预约商品3
    condition.setNewReserveCommodityType4(reqBean.getNewReserveCommodityType4()); // 预约商品4
    condition.setNewReserveCommodityType5(reqBean.getNewReserveCommodityType5()); // 预约商品5
    WebshopConfig config = DIContainer.getWebshopConfig();
    if (!config.isOne()) {
      condition.setSearchShopCode(reqBean.getSearchShopCode());
    }
    condition.setSearchDetailAttributeList(reqBean.getSearchCategoryAttributeList());
    condition.setByRepresent(true);
    condition.setSearchCommodityCode(reqBean.getSearchCommodityCode());
    if (StringUtil.hasValue(reqBean.getSearchCampaignCode())) {
      condition.setSearchCampaignCode(reqBean.getSearchCampaignCode());
    }
    condition.setSearchTagCode(reqBean.getSearchTagCode());
    condition.setDisplayClientType(DisplayClientType.PC.getValue());
    condition.setPageSize(reqBean.getPagerValue().getPageSize());
    condition.setCurrentPage(reqBean.getPagerValue().getCurrentPage());
    if (condition.getMaxFetchSize() == 0) {
      condition.setMaxFetchSize(AbstractQuery.DEFAULT_MAX_FETCH_SIZE);
    }
    // condition = PagerUtil.createSearchCondition(getRequestParameter(),
    // condition);
    // if (reqBean.getPagerValue() == null ||
    // StringUtil.isNullOrEmpty(getRequestParameter().get("pageSize"))) {
    // condition.setPageSize(15);
    // }

    return condition;
  }
}
