package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.CommodityPriceType; 
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.PlanInfo;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.SalesPlanBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesPlanBean.PlanCommodityBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SalesPlanSubAction extends WebSubAction { 

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前SalesPlanSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前SalesPlanSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    SalesPlanBean reqBean = (SalesPlanBean) getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    List<PlanInfo> planInfoList = service.getPlanCommodityInfoList();
    int i = 0;
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    int limit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    if (planInfoList.size() > 3) {
      reqBean.setMoreFlag(true);
    }
    for (PlanInfo detailInfo : planInfoList) {
      if (i >= 3) {
        break;
      }
      PlanCommodityBean commodity = new PlanCommodityBean();
      Price price = new Price(detailInfo.getCommodityHeader(), detailInfo.getCommodityDetail(), null, taxRate);
      commodity.setShopCode(detailInfo.getCommodityHeader().getShopCode());
      commodity.setCommodityCode(detailInfo.getCommodityCode());
     //add by cs_yuli 20120514 start 
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 
	  commodity.setCommodityName(utilService.getNameByLanguage(detailInfo
					.getCommodityHeader().getCommodityName(), detailInfo
					.getCommodityHeader().getCommodityNameEn(), detailInfo
					.getCommodityHeader().getCommodityNameJp()));
	 //add by cs_yuli 20120514 end
      commodity.setCommodityTaxType(NumUtil.toString(detailInfo.getCommodityHeader().getCommodityTaxType()));
      if (StringUtil.hasValue(detailInfo.getCommodityHeader().getOriginalCommodityCode())){
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(new BigDecimal(detailInfo.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(new BigDecimal(detailInfo.getCommodityHeader().getCombinationAmount()))));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice().multiply(new BigDecimal(detailInfo.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().multiply(new BigDecimal(detailInfo.getCommodityHeader().getCombinationAmount()))));
      } else {
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
        commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices()));
      }

      commodity.setDiscountRate(NumUtil.toString(price.getDiscountRate()));
      // add by yyq start 20130415
      if(detailInfo.getCommodityHeader().getImportCommodityType() != null){
        commodity.setImportCommodityType(detailInfo.getCommodityHeader().getImportCommodityType());
      }
      if(detailInfo.getCommodityHeader().getClearCommodityType() != null){
        commodity.setClearCommodityType(detailInfo.getCommodityHeader().getClearCommodityType());
      }
      if(detailInfo.getCommodityHeader().getReserveCommodityType1() != null){
        commodity.setReserveCommodityType1(detailInfo.getCommodityHeader().getReserveCommodityType1());
      }
      if(detailInfo.getCommodityHeader().getReserveCommodityType2() != null){
        commodity.setReserveCommodityType2(detailInfo.getCommodityHeader().getReserveCommodityType2());
      }
      if(detailInfo.getCommodityHeader().getReserveCommodityType3() != null){
        commodity.setReserveCommodityType3(detailInfo.getCommodityHeader().getReserveCommodityType3());
      }
      if(detailInfo.getCommodityHeader().getNewReserveCommodityType1() != null){
        commodity.setNewReserveCommodityType1(detailInfo.getCommodityHeader().getNewReserveCommodityType1());
      }
      if(detailInfo.getCommodityHeader().getNewReserveCommodityType2() != null){
        commodity.setNewReserveCommodityType2(detailInfo.getCommodityHeader().getNewReserveCommodityType2());
      }
      if(detailInfo.getCommodityHeader().getNewReserveCommodityType3() != null){
        commodity.setNewReserveCommodityType3(detailInfo.getCommodityHeader().getNewReserveCommodityType3());
      }
      if(detailInfo.getCommodityHeader().getNewReserveCommodityType4() != null){
        commodity.setNewReserveCommodityType4(detailInfo.getCommodityHeader().getNewReserveCommodityType4());
      }
      if(detailInfo.getCommodityHeader().getNewReserveCommodityType5() != null){
        commodity.setNewReserveCommodityType5(detailInfo.getCommodityHeader().getNewReserveCommodityType5());
      }
      if(detailInfo.getCommodityDetail().getInnerQuantity()!= null){
        commodity.setInnerQuantity(detailInfo.getCommodityDetail().getInnerQuantity());
      }
      // add by yyq end 20130415

      if (price.isSale()) {
        // 通常の販売期間の場合
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      } else if (price.isDiscount()) {
        // 特別価格期間の場合
        commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
      } else if (price.isReserved()) {
        // 予約期間の場合
        commodity.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
      } else {
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      }
      if (detailInfo.getReviewSummary().getReviewScore() == null) {
        commodity.setReviewScore("0");
      } else {
        commodity.setReviewScore(NumUtil.toString(detailInfo.getReviewSummary().getReviewScore()));
      }
      if (detailInfo.getReviewSummary().getReviewCount() == null) {
        commodity.setReviewCount("0");
      } else {
        commodity.setReviewCount(NumUtil.toString(detailInfo.getReviewSummary().getReviewCount()));
      }
      if (price.getDiscountRate().intValue() >= limit) {
        commodity.setDisplayDiscountRate(true);
      } else {
        commodity.setDisplayDiscountRate(false);
      }
      reqBean.getPlanCommodityList().add(commodity);
      i++;
    }

    setBean(reqBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前SalesPlanSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前SalesPlanSubAction:session缺失，结束记录--------------------------------------------------------------------");
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
      //add by cs_yuli 20120514 start 
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());  
      commodity.setCommodityName(utilService.getNameByLanguage(
					commodityList.get(j).getCommodityHeader()
							.getCommodityName(), commodityList.get(j)
							.getCommodityHeader().getCommodityNameEn(),
					commodityList.get(j).getCommodityHeader()
							.getCommodityNameJp())); 
      //add by cs_yuli 20120514 end
      commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
      commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
      commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
      commodity.setCommodityTaxType(NumUtil.toString(commodityList.get(j).getCommodityHeader().getCommodityTaxType()));
      commodity.setCommodityPointRate(NumUtil.toString(commodityList.get(j).getCommodityHeader().getCommodityPointRate()));
      commodity.setReviewScore(NumUtil.toString(commodityList.get(j).getReviewSummary().getReviewScore()));
      commodity.setDiscountPeriod(price.isDiscount());
      commodity.setShopCode(commodityList.get(j).getCommodityHeader().getShopCode());
      commodity.setShopName(commodityList.get(j).getShop().getShopName());

      // 商品コードに関連付いている在庫が1つでもあるかどうかを判別
      commodity.setHasStock(service.hasStockCommodity(commodityList.get(j).getCommodityHeader().getShopCode(), commodityList.get(
          j).getCommodityHeader().getCommodityCode()));

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
    if (price.getCampaignInfo() == null) {
      clearAppliedCampaign(commodity);
    } else {
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 
      commodity.setCampaignPeriod(price.isCampaign());
      commodity.setCampaignCode(price.getCampaignInfo().getCampaignCode());
      commodity.setCampaignName(utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(),price.getCampaignInfo().getCampaignNameEn(),price.getCampaignInfo().getCampaignNameJp()));
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
