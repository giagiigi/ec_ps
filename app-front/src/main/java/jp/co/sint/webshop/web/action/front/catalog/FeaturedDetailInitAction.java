package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.data.dto.PlanDetail;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.FeaturedDetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalePlanBean;
import jp.co.sint.webshop.web.bean.front.catalog.FeaturedDetailBean.FeaturedDetailCommodityBean;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;
/**
 * 特集企划详细
 * 
 * @author kousen
 */
public class FeaturedDetailInitAction extends WebFrontAction<SalePlanBean> { 
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      return true;
    }
    setNextUrl("/app/common/index");
    return false;
  }

  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前FeaturedDetailInitAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前FeaturedDetailInitAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    FeaturedDetailBean bean = new FeaturedDetailBean();
    String[] urlParam = getRequestParameter().getPathArgs();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityContainerCondition condition = new CommodityContainerCondition(); 
    condition.setPageSize(200);
    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 
    // 区分 当企划类型为0时，为sale_flagセール区分。当企划类型为2时，为spec_flag特集区分
    String saleOrSpecFlag = urlParam[0];
    // 企划类型 0.销售企划 2.特集企划
    String salePlanDetailTypes = urlParam[1];
    // 企划明细编号
    String detailCode = urlParam[2];
    bean.setPlanDetailType(saleOrSpecFlag);
    
    Plan plan = catalogService.getPlan(PlanType.FEATURE.getValue(), saleOrSpecFlag);
    if (plan == null) {
      setNextUrl("/app/common/index");
      return FrontActionResult.RESULT_SUCCESS;
    }
    
    //设置企划内容
    bean.setPlanName(plan.getPlanName());
    bean.setPlanStartDatetime(plan.getPlanStartDatetime());
    bean.setPlanEndDatetime(plan.getPlanEndDatetime());
    
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    PlanDetail planDetail = communicationService.getPlanDetail(plan.getPlanCode(), salePlanDetailTypes, detailCode);
    if (planDetail == null) {
      setNextUrl("/app/common/index");
      return FrontActionResult.RESULT_SUCCESS;
    }
    if (salePlanDetailTypes.equals(PlanDetailType.CATEGORY.getValue())) {
      condition.setSearchCategoryCode(detailCode); // 商品目录
      condition.setSearchSpecFlag(saleOrSpecFlag); // セール区分
      condition.setSearchPlanDetail(planDetail);
    } else if (salePlanDetailTypes.equals(PlanDetailType.BRAND.getValue())) {
      condition.setSearchBrandCode(detailCode); // ブランドコード
      condition.setSearchSpecFlag(saleOrSpecFlag); // 特集区分
      condition.setSearchPlanDetail(planDetail);
    } else {
      setNextUrl("/app/common/index");
      return FrontActionResult.RESULT_SUCCESS;
    }
    // 品牌
    if (PlanDetailType.BRAND.longValue().equals(planDetail.getDetailType())) {
      Brand brand = catalogService.getBrand("00000000", planDetail.getDetailCode());
      bean.setDetailName(utilService.getNameByLanguage(brand.getBrandName(),brand.getBrandEnglishName(),brand.getBrandJapaneseName()));
    // 商品分类
    } else if (PlanDetailType.CATEGORY.longValue().equals(planDetail.getDetailType())) {
      bean.setDetailName(utilService.getNameByLanguage(catalogService.getCategory(planDetail.getDetailCode()).getCategoryNamePc(),
    		  catalogService.getCategory(planDetail.getDetailCode()).getCategoryNamePcEn(),
    		  catalogService.getCategory(planDetail.getDetailCode()).getCategoryNamePcJp()));
    // 自由组合
    } else if (PlanDetailType.FREE.longValue().equals(planDetail.getDetailType())) {
      bean.setDetailName(planDetail.getDetailName());
    }
    SearchResult<CommodityContainer> result = catalogService.fastFindCommodityContainer(condition, false);
    List<FeaturedDetailCommodityBean> list = new ArrayList<FeaturedDetailCommodityBean>();
    
    List<CommodityContainer> headlineList = result.getRows();
    
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    int displayDiscountRateLimit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    
    for (CommodityContainer sc : headlineList) {
     
      Campaign campaign = catalogService.getAppliedCampaignInfo(sc.getCommodityHeader().getShopCode(), sc.getCommodityHeader()
          .getCommodityCode());
      Price price = new Price(sc.getCommodityHeader(), sc.getCommodityDetail(), campaign, taxRate);
      FeaturedDetailCommodityBean commodity = new FeaturedDetailCommodityBean();
     
      //add by twh 2013-6-17 START
      //commodity.setSetCommodityFlg(false);

      commodity.setShopCode(sc.getCommodityHeader().getShopCode());
      commodity.setCommodityCode(sc.getCommodityHeader().getCommodityCode());
      //add by cs_yuli 20120514 start 
      String commodityDescripttionPc ="";  
      commodity.setCommodityName(utilService.getNameByLanguage(sc.getCommodityHeader().getCommodityName(),sc.getCommodityHeader().getCommodityNameEn(),sc.getCommodityHeader().getCommodityNameJp()));
      commodityDescripttionPc = WebUtil.removeHtmlTag(utilService.getNameByLanguage(sc.getCommodityHeader().getCommodityDescriptionMobile(),sc.getCommodityHeader().getCommodityDescriptionMobileEn(),sc.getCommodityHeader().getCommodityDescriptionMobileJp()));
	    //add by cs_yuli 20120514 end
      commodity.setCommodityDescriptionShort(utilService.getNameByLanguage(sc.getCommodityHeader().getCommodityDescriptionMobile(),sc.getCommodityHeader().getCommodityDescriptionMobileEn(),sc.getCommodityHeader().getCommodityDescriptionMobileJp()));
      if (sc.getUseFlg() == 1L && sc.getCommodityHeader().getStockManagementType() != 1L) {
        commodity.setUseFlag(true);
      } else {
        commodity.setUseFlag(false);
      }
      if(StringUtil.hasValue(sc.getCommodityHeader().getOriginalCommodityCode())){
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
      } else {
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
        //System.out.println(NumUtil.toString(price.getUnitPrice()));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
        commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
      }
      commodity.setCommodityTaxType(NumUtil.toString(sc.getCommodityHeader().getCommodityTaxType()));

      
      
      // add by yyq start 20130415
      if(sc.getCommodityHeader().getImportCommodityType() != null){
        commodity.setImportCommodityType(sc.getCommodityHeader().getImportCommodityType());
      }
      if(sc.getCommodityHeader().getClearCommodityType() != null){
        commodity.setClearCommodityType(sc.getCommodityHeader().getClearCommodityType());
      }
      if(sc.getCommodityHeader().getReserveCommodityType1() != null){
        commodity.setReserveCommodityType1(sc.getCommodityHeader().getReserveCommodityType1());
      }
      if(sc.getCommodityHeader().getReserveCommodityType2() != null){
        commodity.setReserveCommodityType2(sc.getCommodityHeader().getReserveCommodityType2());
      }
      if(sc.getCommodityHeader().getReserveCommodityType3() != null){
        commodity.setReserveCommodityType3(sc.getCommodityHeader().getReserveCommodityType3());
      }
      if(sc.getCommodityHeader().getNewReserveCommodityType1() != null){
        commodity.setNewReserveCommodityType1(sc.getCommodityHeader().getNewReserveCommodityType1());
      }
      if(sc.getCommodityHeader().getNewReserveCommodityType2() != null){
        commodity.setNewReserveCommodityType2(sc.getCommodityHeader().getNewReserveCommodityType2());
      }
      if(sc.getCommodityHeader().getNewReserveCommodityType3() != null){
        commodity.setNewReserveCommodityType3(sc.getCommodityHeader().getNewReserveCommodityType3());
      }
      if(sc.getCommodityHeader().getNewReserveCommodityType4() != null){
        commodity.setNewReserveCommodityType4(sc.getCommodityHeader().getNewReserveCommodityType4());
      }
      if(sc.getCommodityHeader().getNewReserveCommodityType5() != null){
        commodity.setNewReserveCommodityType5(sc.getCommodityHeader().getNewReserveCommodityType5());
      }
      if(sc.getCommodityDetail().getInnerQuantity()!= null){
        commodity.setInnerQuantity(sc.getCommodityDetail().getInnerQuantity());
      }
       /**
        * 获取套装品的明细
        */
       // 取得套餐明细信息
     List<SetCommodityComposition> compositionList = catalogService.getSetCommodityInfo(sc.getCommodityHeader().getShopCode(), sc.getCommodityHeader().getCommodityCode());
       if(compositionList==null){
         commodity.setSetCommodityFlg(false);
       //  System.out.println("不是套装品: "+sc.getCommodityHeader().getCommodityCode());
       }else{
         commodity.setSetCommodityFlg(true);
       //  System.out.println("是套装品："+sc.getCommodityHeader().getCommodityCode());
       }
      //zzy 2015-1-7 add end
      // add by yyq end 20130415
      //commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices()));
      commodity.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
      if (price.getDiscountRate().intValue() >= displayDiscountRateLimit) {
        commodity.setDisplayDiscountRate(true);
      } else {
        commodity.setDisplayDiscountRate(false);
      }
       
      if (StringUtil.hasValue(commodityDescripttionPc) && commodityDescripttionPc.length() > 50) {
        commodity.setCommodityDescription(commodityDescripttionPc.substring(0, 50));
        commodity.setDescpritionLengthOver(true);

      } else {
        commodity.setCommodityDescription(commodityDescripttionPc);
        commodity.setDescpritionLengthOver(false);
      }
      if (sc.getReviewSummary().getReviewScore() == null) {
        commodity.setReviewScore("0");
      } else {
        commodity.setReviewScore(NumUtil.toString(sc.getReviewSummary().getReviewScore()));
      }
      if (sc.getReviewSummary().getReviewCount() == null) {
        commodity.setReviewCount("0");
      } else {
        commodity.setReviewCount(NumUtil.toString(sc.getReviewSummary().getReviewCount()));
      }

      commodity.setDiscountPeriod(price.isDiscount());
      commodity.setReservationPeriod(price.isReserved());
      commodity.setShopCode(sc.getCommodityHeader().getShopCode());
      commodity.setShopName(sc.getShop().getShopName());

      // 商品別ポイント付与期間かどうかの判別
      commodity.setCommodityPointPeriod(price.isPoint());

      // 在庫管理区分区分が在庫数表示または在庫状況表示のときに
      // [在庫なし]を表示する
      switch (StockManagementType.fromValue(sc.getCommodityHeader().getStockManagementType())) {
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
      //组合商品库存判断对应
      Long stockNum = 0L;
      Long avaStockQuantity = sc.getContainerAddInfo().getAvailableStockQuantity();
      if(StringUtil.hasValue(sc.getCommodityHeader().getOriginalCommodityCode())){
        StockDao sDao = DIContainer.getDao(StockDao.class); 
        Stock stock = sDao.load("00000000",sc.getCommodityHeader().getOriginalCommodityCode());
        avaStockQuantity = stock.getStockQuantity() - stock.getAllocatedQuantity();
        //减去1之后才能确保个数正好为组合数量时也能显示有库存
        stockNum = sc.getCommodityHeader().getCombinationAmount()-1;
      }
      if (StringUtil.hasValue(commodity.getCommodityDescriptionShort())) {
        if (commodity.getCommodityDescriptionShort().length() > 19) {
          commodity.setCommodityDescriptionShort(commodity.getCommodityDescriptionShort().substring(0, 19));
        }
      }
        //套装品库存取值
      if (compositionList != null) {
        List<String> compositionSkuCodeList = new ArrayList<String>();
        for (SetCommodityComposition edit : compositionList) {
          compositionSkuCodeList.add(edit.getChildCommodityCode());
        }
        avaStockQuantity = catalogService.getAvailableStock("00000000", commodity.getCommodityCode(), true, compositionSkuCodeList, null);
      }
      if (price.isSale()) {
        // 通常の販売期間の場合
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
        setAppliedCampaign(price, commodity);
        commodity.setHasStock(avaStockQuantity > stockNum );
      } else if (price.isDiscount()) {
        // 特別価格期間の場合
        commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
        setAppliedCampaign(price, commodity);
        commodity.setHasStock(avaStockQuantity > stockNum);
      } else if (price.isReserved()) {
        // 予約期間の場合
        commodity.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
        setAppliedCampaign(price, commodity);
        commodity.setHasStock(avaStockQuantity == null|| avaStockQuantity > stockNum);
      } else {
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
        setAppliedCampaign(price, commodity);
        commodity.setHasStock(avaStockQuantity > stockNum);
      }
      DiscountCommodity dc = catalogService.getDiscountCommodityByCommodityCode(commodity.getCommodityCode());
      //限时限购活动特价处理
      if (dc != null) {
        commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
        commodity.setDiscountPrice(NumUtil.toString(dc.getDiscountPrice()));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
        commodity.setDiscountPrices(NumUtil.toString(price.getRetailPrice().subtract(dc.getDiscountPrice())));
        commodity.setDiscountCommodityFlg(true);
      } else {
        commodity.setDiscountCommodityFlg(false);
      }
      if(sc.getCampaign()!=null){
        // 20141024 hdh add start
        String campaignName = sc.getCampaign().getCampaignName();
        if(StringUtil.hasValue(campaignName)){
          String[] names = campaignName.split("@");
          if(names.length==3){
            commodity.setCurCampaignName(utilService.getNameByLanguage(names[0], names[1], names[2]));
          }
        }
        // 20141024 hdh add start
      }
      list.add(commodity);
    }
    bean.setList(list);
    setNextUrl(null);
    setRequestBean(bean);
    if(sesContainer.getSession() != null){
      logger.info("当前FeaturedDetailInitAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前FeaturedDetailInitAction:session缺失，结束记录--------------------------------------------------------------------");
    }
    return FrontActionResult.RESULT_SUCCESS;
  }

  
  /**
   * @param price
   * @param commodity
   */
  private void setAppliedCampaign(Price price, FeaturedDetailCommodityBean commodity) {
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
  private void clearAppliedCampaign(FeaturedDetailCommodityBean commodity) {
    commodity.setCampaignPeriod(false);
    commodity.setCampaignCode("");
    commodity.setCampaignName("");
    commodity.setCampaignDiscountRate("0");
  }
  
}
