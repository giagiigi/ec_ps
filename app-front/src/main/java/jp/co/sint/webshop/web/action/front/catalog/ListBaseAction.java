package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.MobileCommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityListBean.CommodityListDetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.DetailCartBean.DetailCartComposition;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2040410:商品一覧のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class ListBaseAction extends WebFrontAction<CommodityListBean> {

  private static final String IMAGE_MODE = "image"; 
  private String commodityDescriptionPc="";
  private String commodityName="";
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
    return true;
  }

  /**
   * @param reqBean
   *          商品Bean
   * @return 商品情報
   */
  public CommodityContainerCondition setSearchCondition(CommodityListBean reqBean) {
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

    if (!getConfig().isOne()) {
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
//    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    // if (reqBean.getPagerValue() == null ||
    // StringUtil.isNullOrEmpty(getRequestParameter().get("pageSize"))) {
    // condition.setPageSize(15);
    // }

    return condition;
  }

  /**
   * @param reqBean
   * @param condition
   */
  public void setCommodityList(CommodityListBean reqBean, CommodityContainerCondition condition) {

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    
    SearchResult<CommodityContainer> result = service.fastFindCommodityContainer(condition, false);

    reqBean.setSearchCategoryCode(condition.getSearchCategoryCode());
    reqBean.setPagerValue(PagerUtil.createValue(result));

    List<CommodityContainer> headlineList = result.getRows();
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    int displayDiscountRateLimit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    for (CommodityContainer sc : headlineList) {
      Campaign campaign = service.getAppliedCampaignInfo(sc.getCommodityHeader().getShopCode(), sc.getCommodityHeader()
          .getCommodityCode());
      // 限时限量商品（秒杀商品）
     Price price = new Price(sc.getCommodityHeader(), sc.getCommodityDetail(), campaign, taxRate);
      CommodityListDetailBean commodity = new CommodityListDetailBean();
      if (StringUtil.hasValue(reqBean.getSelectCommodityCode()) && sc.getCommodityHeader().getCommodityCode().equals(reqBean.getSelectCommodityCode())) {
        commodity.setDisplayFlg(true);
      } else {
        commodity.setDisplayFlg(false);
      }
      
      if (sc.getUseFlg() == 1L && sc.getCommodityHeader().getStockManagementType() != 1L) {
        commodity.setUseFlag(true);
      } else {
        commodity.setUseFlag(false);
      }
      
      // add by yyq start 20130313

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
      
      // 取得套餐明细信息
      List<SetCommodityComposition> compositionList = service.getSetCommodityInfo(sc.getCommodityHeader().getShopCode(), sc
          .getCommodityHeader().getCommodityCode());
      
      //add by twh 2013-6-17 START
      commodity.setSetCommodityFlg(false);
      if(StringUtil.hasValue(sc.getCommodityHeader().getOriginalCommodityCode())){
        commodity.setOriginalCommodityCode(sc.getCommodityHeader().getOriginalCommodityCode());
        commodity.setCombinationAmount(sc.getCommodityHeader().getCombinationAmount());
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs().multiply(new BigDecimal(sc.getCommodityHeader().getCombinationAmount()))));
      } else if (compositionList != null){
        for (SetCommodityComposition edit : compositionList) {
          commodity.setUnitPrice(BigDecimalUtil.add(
              StringUtil.hasValue(commodity.getUnitPrice()) ? new BigDecimal(commodity.getUnitPrice()) : BigDecimal.ZERO,
              edit.getRetailPrice()).toString());
        }  
        commodity.setSetCommodityFlg(true);
      } else {
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
        commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
      }
      //add by twh 2013-6-17 end
      commodity.setCommodityCode(sc.getCommodityHeader().getCommodityCode());
      // add by yyq end 20130313
      //add by cs_yuli 20120514 start 
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());
      commodityName = utilService.getNameByLanguage(sc.getCommodityHeader().getCommodityName(),sc.getCommodityHeader().getCommodityNameEn(),sc.getCommodityHeader().getCommodityNameJp());
      commodity.setCommodityName(commodityName);
      commodityDescriptionPc=utilService.getNameByLanguage(sc.getCommodityHeader().getCommodityDescriptionMobile(),sc.getCommodityHeader().getCommodityDescriptionMobileEn(),sc.getCommodityHeader().getCommodityDescriptionMobileJp());
    //add by cs_yuli 20120514 end

      commodity.setCommodityTaxType(NumUtil.toString(sc.getCommodityHeader().getCommodityTaxType()));
      commodity.setCommodityPointRate(NumUtil.toString(sc.getCommodityHeader().getCommodityPointRate()));
      // modify by wjh 20120104 start
      commodity.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
      if (price.getDiscountRate().intValue() >= displayDiscountRateLimit) {
        commodity.setDisplayDiscountRate(true);
      } else {
        commodity.setDisplayDiscountRate(false);
      }
      String commodityDescripttionPc = WebUtil.removeHtmlTag(commodityDescriptionPc);
      if (StringUtil.hasValue(commodityDescripttionPc) && commodityDescripttionPc.length() > 50) {
        commodity.setCommodityDescription(commodityDescripttionPc.substring(0, 50)+"...");
        commodity.setDescpritionLengthOver(true);

      } else {
        commodity.setCommodityDescription(commodityDescripttionPc);
        commodity.setDescpritionLengthOver(false);
      }
      // modify by wjh 20120104 end

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

      // 套装品库存取值
      if (compositionList != null) {
        List<String> compositionSkuCodeList = new ArrayList<String>();
        for (SetCommodityComposition edit : compositionList) {
          compositionSkuCodeList.add(edit.getChildCommodityCode());
        }
        avaStockQuantity = service.getAvailableStock("00000000", commodity.getCommodityCode(), true, compositionSkuCodeList, null);
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
        commodity.setHasStock(avaStockQuantity == null
            || avaStockQuantity > stockNum);
      } else {
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
        setAppliedCampaign(price, commodity);
        commodity.setHasStock(avaStockQuantity > stockNum);
      }
      DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(commodity.getCommodityCode());
      //限时限购活动特价处理
      if (dc != null) {
          commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
          commodity.setDiscountPrice(NumUtil.toString(dc.getDiscountPrice()));
          commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
          commodity.setDiscountPrices(NumUtil.toString(price.getRetailPrice().subtract(dc.getDiscountPrice())));
          // 限时限量商品（秒杀商品）
          commodity.setDiscountCommodityFlg(true);
      } else {
          commodity.setDiscountCommodityFlg(false);
      }
      
      // 20141024 hdh add start
      if(!StringUtil.isNullOrEmpty(sc.getCampaign())){
        String campaignName = sc.getCampaign().getCampaignName();
        if(StringUtil.hasValue(campaignName)){
          String[] names = campaignName.split("@");
          if(names.length==3){
            commodity.setCurcampaignName(utilService.getNameByLanguage(names[0], names[1], names[2]));
          }
        }
      }
      // 20141024 hdh add end
      reqBean.getList().add(commodity);
    }
    List<CodeAttribute> statusList = new ArrayList<CodeAttribute>();
    // 手机版排序字段名称变更 by lc 2013-01-11
    if (StringUtil.isNullOrEmpty(getClient()) || getClient().equals(ClientType.OTHER)) {
      for (CommodityDisplayOrder status : CommodityDisplayOrder.values()) {
        statusList.add(status);
      }
  } else {
    for (MobileCommodityDisplayOrder status : MobileCommodityDisplayOrder.values()) {
        statusList.add(status);
      }
  }

    reqBean.setCommoditySort(statusList);

    if (reqBean.getList().size() == 0) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.NO_COMMODITY_LIST_ERROR));
    }
  }

  /**
   * @param price
   * @param commodity
   */
  private void setAppliedCampaign(Price price, CommodityListDetailBean commodity) {
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    if (price.getCampaignInfo() == null) {
      clearAppliedCampaign(commodity);
    } else {
      commodity.setCampaignPeriod(price.isCampaign());
      commodity.setCampaignCode(price.getCampaignInfo().getCampaignCode());
      commodity.setCampaignName(utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(),price.getCampaignInfo().getCampaignNameEn(),price.getCampaignInfo().getCampaignNameJp()));
      commodity.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
    }
  }

  /**
   * @param commodity
   */
  private void clearAppliedCampaign(CommodityListDetailBean commodity) {
    commodity.setCampaignPeriod(false);
    commodity.setCampaignCode("");
    commodity.setCampaignName("");
    commodity.setCampaignDiscountRate("0");
  }

  /**
   * @param reqBean
   */
  public void setPictureMode(CommodityListBean reqBean) {
    if (StringUtil.isNullOrEmpty(reqBean.getMode())) {
      reqBean.setMode(IMAGE_MODE);
    }

    if (reqBean.getAlignmentSequence() == null) {
      reqBean.setAlignmentSequence(CommodityDisplayOrder.BY_POPULAR_RANKING.getValue());
    }
  }
}
