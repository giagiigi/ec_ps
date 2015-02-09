package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.LanguageCode;
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
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesRecommendBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SalesRecommendSubAction extends WebSubAction {

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
      logger.info("当前SalesRecommendSubAction:sessionID=" + sesContainer.getSession().getId()
          + "开始记录--------------------------------------------------------------------");
    } else {
      logger.info("当前SalesRecommendSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    SalesRecommendBean reqBean = (SalesRecommendBean) getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    // 品店精选 start
    List<CommodityHeadline> pageList = service.getChosenSortCommodity(1L);
    List<CommodityHeadline> chReserveList = service.getIndexBatchCommodityNoCache(1L);
    
    boolean choseAddFlg = true;
    for (CommodityHeadline line : chReserveList) {
      for(CommodityHeadline line2 : pageList) {
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
      if(dc == null) {
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
        if(ch.getDiscountPrice() !=null){
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
    List<CommodityHeadline> chListSale = service.getHotSaleCommodity(currentLanguageCode);
    // 每天batch跑出来的热销商品
    List<CommodityHeadline> pageHotList = service.getIndexBatchCommodity(2L);
    // 前台显示热销商品集合
    List<CommodityHeadline> chList2 = new ArrayList<CommodityHeadline>();
    chList2.addAll(chListSale);
    boolean addFlg = true;
    for (CommodityHeadline line : pageHotList) {
      for(CommodityHeadline line2 : chListSale) {
        if (line.getCommodityCode().equals(line2.getCommodityCode())) {
          addFlg = false;
          break;
        } else {
          addFlg = true;
        }
      }
      if (addFlg) {
        chList2.add(line);
      }

    }
    
    List<CommodityHeadline> pageHotListFinal = new ArrayList<CommodityHeadline>();
    for (int i = 0; i < 6; i++) {
      String priceMode = "1";
      CommodityHeadline ch = new CommodityHeadline();
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
      if(dc == null) {
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
        if(null != ch.getDiscountPrice()){
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
      logger.info("当前SalesRecommendSubAction:sessionID=" + sesContainer.getSession().getId()
          + "结束记录--------------------------------------------------------------------");
    } else {
      logger.info("当前SalesRecommendSubAction:session缺失，结束记录--------------------------------------------------------------------");
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

}
