package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.DetailRecommendBaseBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * お気に入り商品詳細のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailRecommendBaseAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
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
    CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
    List<DetailRecommendBaseBean> list = new ArrayList<DetailRecommendBaseBean>();
    for (CommodityContainer cc : commodityList) {
      String shopCode = cc.getCommodityHeader().getShopCode();
      String commodityCode = cc.getCommodityHeader().getCommodityCode();
      Campaign campaign = catalogSvc.getAppliedCampaignInfo(shopCode, commodityCode);
      Price price = new Price(cc.getCommodityHeader(), cc.getCommodityDetail(), campaign, taxRate);
      DetailRecommendBaseBean commodity = new DetailRecommendBaseBean();
      commodity.setCommodityCode(cc.getCommodityHeader().getCommodityCode());
      // add by cs_yuli 20120514 start
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      commodity.setCommodityName(utilService.getNameByLanguage(cc.getCommodityHeader().getCommodityName(), cc.getCommodityHeader()
          .getCommodityNameEn(), cc.getCommodityHeader().getCommodityNameJp()));
      // add by cs_yuli 20120514 end
      if(StringUtil.hasValue(cc.getCommodityHeader().getOriginalCommodityCode()) && cc.getCommodityHeader().getCombinationAmount()!=null){
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(new BigDecimal(cc.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(new BigDecimal(cc.getCommodityHeader().getCombinationAmount()))));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice().multiply(new BigDecimal(cc.getCommodityHeader().getCombinationAmount()))));
      }else{
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
        commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
      }
      commodity.setCommodityTaxType(NumUtil.toString(cc.getCommodityHeader().getCommodityTaxType()));
      commodity.setCommodityPointRate(NumUtil.toString(cc.getCommodityHeader().getCommodityPointRate()));
      commodity.setReviewScore(NumUtil.toString(cc.getReviewSummary().getReviewScore()));
      commodity.setDiscountPeriod(price.isDiscount());
      commodity.setShopCode(cc.getCommodityHeader().getShopCode());
      commodity.setShopName(cc.getShop().getShopName());
      // add by yyq start 20130415
      if(cc.getCommodityHeader().getImportCommodityType() != null){
        commodity.setImportCommodityType(cc.getCommodityHeader().getImportCommodityType());
      }
      if(cc.getCommodityHeader().getClearCommodityType() != null){
        commodity.setClearCommodityType(cc.getCommodityHeader().getClearCommodityType());
      }
      if(cc.getCommodityHeader().getReserveCommodityType1() != null){
        commodity.setReserveCommodityType1(cc.getCommodityHeader().getReserveCommodityType1());
      }
      if(cc.getCommodityHeader().getReserveCommodityType2() != null){
        commodity.setReserveCommodityType2(cc.getCommodityHeader().getReserveCommodityType2());
      }
      if(cc.getCommodityHeader().getReserveCommodityType3() != null){
        commodity.setReserveCommodityType3(cc.getCommodityHeader().getReserveCommodityType3());
      }
      if(cc.getCommodityHeader().getNewReserveCommodityType1() != null){
        commodity.setNewReserveCommodityType1(cc.getCommodityHeader().getNewReserveCommodityType1());
      }
      if(cc.getCommodityHeader().getNewReserveCommodityType2() != null){
        commodity.setNewReserveCommodityType2(cc.getCommodityHeader().getNewReserveCommodityType2());
      }
      if(cc.getCommodityHeader().getNewReserveCommodityType3() != null){
        commodity.setNewReserveCommodityType3(cc.getCommodityHeader().getNewReserveCommodityType3());
      }
      if(cc.getCommodityHeader().getNewReserveCommodityType4() != null){
        commodity.setNewReserveCommodityType4(cc.getCommodityHeader().getNewReserveCommodityType4());
      }
      if(cc.getCommodityHeader().getNewReserveCommodityType5() != null){
        commodity.setNewReserveCommodityType5(cc.getCommodityHeader().getNewReserveCommodityType5());
      }
      if(cc.getCommodityDetail().getInnerQuantity()!= null){
        commodity.setInnerQuantity(cc.getCommodityDetail().getInnerQuantity());
      }
      
      DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(commodityCode);
      if(dc == null) {
        commodity.setDiscountCommodityFlg(false);
      } else {
        commodity.setDiscountCommodityFlg(true);
      }
      
      // add by yyq end 20130415

      // 商品コードに関連付いている在庫が1つでもあるかどうかを判別
      commodity.setHasStock(service.hasStockCommodity(cc.getCommodityHeader().getShopCode(), cc.getCommodityHeader().getCommodityCode()));

      // 商品別ポイント付与期間かどうかの判別
      commodity.setCommodityPointPeriod(price.isPoint());

      // 販売・特価・予約期間による違いを設定
      setSaleType(price, commodity);

      // 在庫管理区分区分が在庫数表示または在庫状況表示のときに
      // [在庫なし]を表示する
      switch (StockManagementType.fromValue(cc.getCommodityHeader().getStockManagementType())) {
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
    if (list.size() > 0) {
      pageList.add(list);
    }
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
   * WebshopConfig設定情報から商品限度数を取得します。
   * 
   * @param commodityList
   * @return maxCount
   */
  public int getMaxCount(List<CommodityContainer> commodityList) {
    WebshopConfig config = DIContainer.getWebshopConfig();

    int maxCount = 0;
    if (config.getRecommendCommodityMaxCount() < commodityList.size()) {
      maxCount = config.getRecommendCommodityMaxCount();
    } else {
      maxCount = commodityList.size();
    }

    return maxCount;
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
