package jp.co.sint.webshop.web.action.front.mypage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.FavoriteCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.FavoriteListBean;
import jp.co.sint.webshop.web.bean.front.mypage.FavoriteListBean.FavoriteDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030810:お気に入り商品のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class FavoriteListInitAction extends WebFrontAction<FavoriteListBean> {
  


  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    String[] parameter = getRequestParameter().getPathArgs();
    String searchType = "all";
    if (parameter.length > 0 && parameter[0].equals("delete")) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.front.mypage.FavoriteListInitAction.0")));
    } else if (parameter.length > 0) {
      searchType = parameter[0];
    }

    // 検索条件の作成
    FavoriteListBean bean = new FavoriteListBean();

    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setSearchCustomerCode(customerCode);
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    if (searchType.equals("0")) {
        condition.setSearchListSort("orderNo0");
    } else if (searchType.equals("1")) {
        condition.setSearchListSort("orderNo1");
    }
    if(!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)){
    	WebshopConfig config = DIContainer.getWebshopConfig();
      condition.setPageSize(config.getMobilePageSize());
    }
    // 検索結果の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<CommodityContainer> result = service.getFavoriteCommodities(condition);
    List<CommodityContainer> commodityList = result.getRows();

    // nextBeanに検索結果を設定
    List<FavoriteDetail> list = new ArrayList<FavoriteDetail>();

    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();

    for (CommodityContainer cc : commodityList) {
      FavoriteDetail commodity = new FavoriteDetail();
      Campaign campaign = service.getAppliedCampaignInfo(cc.getCommodityHeader().getShopCode(), cc.getCommodityHeader()
          .getCommodityCode());
      Price price = new Price(cc.getCommodityHeader(), cc.getCommodityDetail(), campaign, taxRate);
      
      FavoriteCommodity favoriteCommodity = service.getFavoriteCommodity(customerCode, cc.getCommodityHeader().getShopCode(), cc.getCommodityDetail().getSkuCode());
      commodity.setFavoriteRegisterDate(DateUtil.toDateString(favoriteCommodity.getFavoriteRegisterDate()));
      
      commodity.setCommodityCode(cc.getCommodityHeader().getCommodityCode());
      //add by cs_yuli 20120514 start 
      UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());   
      commodity.setCommodityName(StringUtil.getHeadline(utilService.getNameByLanguage(cc.getCommodityHeader().getCommodityName(),cc.getCommodityHeader().getCommodityNameEn(),cc.getCommodityHeader().getCommodityNameJp()),25));
      commodity.setStandardDetail1Name(utilService.getNameByLanguage(cc.getCommodityDetail().getStandardDetail1Name(),cc.getCommodityDetail().getStandardDetail1NameEn(),cc.getCommodityDetail().getStandardDetail1NameJp()));
      commodity.setStandardDetail2Name(utilService.getNameByLanguage(cc.getCommodityDetail().getStandardDetail2Name(),cc.getCommodityDetail().getStandardDetail2NameEn(),cc.getCommodityDetail().getStandardDetail2NameJp()));
      commodity.setCommodityDescription(utilService.getNameByLanguage(cc.getCommodityHeader().getCommodityDescriptionPc(),cc.getCommodityHeader().getCommodityDescriptionPcEn(),cc.getCommodityHeader().getCommodityDescriptionPcJp()));
      //add by cs_yuli 20120514 end
      commodity.setSkuCode(cc.getCommodityDetail().getSkuCode());
      if(StringUtil.hasValue(cc.getCommodityHeader().getOriginalCommodityCode()) && cc.getCommodityHeader().getCombinationAmount()!=null){
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(new BigDecimal(cc.getCommodityHeader().getCombinationAmount()))));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(new BigDecimal(cc.getCommodityHeader().getCombinationAmount()))));
        commodity.setReservedPrice(NumUtil.toString(price.getReservationPrice().multiply(new BigDecimal(cc.getCommodityHeader().getCombinationAmount()))));
      }else{
        commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
        commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
        commodity.setReservedPrice(NumUtil.toString(price.getReservationPrice()));
      }
      commodity.setCommodityTaxType(NumUtil.toString(cc.getCommodityHeader().getCommodityTaxType()));
      commodity.setCommodityPointRate(NumUtil.toString(cc.getCommodityHeader().getCommodityPointRate()));
      commodity.setReviewScore(NumUtil.toString(cc.getReviewSummary().getReviewScore()));
      commodity.setReviewCount(NumUtil.toString(cc.getReviewSummary().getReviewCount()));
      commodity.setStockManagementType(NumUtil.toString(cc.getCommodityHeader().getStockManagementType()));
      commodity.setAvailableStockQuantity(getFormatNumber(cc.getContainerAddInfo().getAvailableStockQuantity()));
      commodity.setOutOfStockMessage(cc.getStockStatus().getOutOfStockMessage());
      commodity.setStockLittleMessage(cc.getStockStatus().getStockLittleMessage());
      commodity.setStockSufficientMessage(cc.getStockStatus().getStockSufficientMessage());
      
      // 20120731 ysy add start
      int displayDiscountRateLimit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
      commodity.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
      commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
      if (price.getDiscountRate().intValue() >= displayDiscountRateLimit) {
        commodity.setDisplayDiscountRate(true);
      } else {
        commodity.setDisplayDiscountRate(false);
      }
      // 20120731 ysy add end

      if (NumUtil.isNull(cc.getStockStatus().getStockSufficientThreshold())) {
        commodity.setStockSufficientThreshold(0L);
      } else {
        commodity.setStockSufficientThreshold(cc.getStockStatus().getStockSufficientThreshold());
      }

      commodity.setCampaignPeriod(price.isCampaign());
      commodity.setDiscountPeriod(price.isDiscount());
      commodity.setShopCode(cc.getCommodityHeader().getShopCode());
      commodity.setShopName(cc.getShop().getShopName());

      if (price.getCampaignInfo() == null) {
        commodity.setCampaignDiscountRate("");
      } else {
        commodity.setCampaignCode(price.getCampaignInfo().getCampaignCode());
        commodity.setCampaignName(utilService.getNameByLanguage(price.getCampaignInfo().getCampaignName(),price.getCampaignInfo().getCampaignNameEn(),price.getCampaignInfo().getCampaignNameJp()));
        commodity.setCampaignDiscountRate(NumUtil.toString(price.getCampaignInfo().getCampaignDiscountRate()));
      }

      if (price.isSale()) {
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      } else if (price.isDiscount()) {
        commodity.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
      } else if (price.isReserved()) {
        commodity.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
      } else {
        commodity.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
      }

      
      
      // 2012/12/20 促销对应 ob update start
//   // 有効在庫数チェック
//      String shopCode = commodity.getShopCode();
//      String skuCode = commodity.getSkuCode();
//      boolean isReserve = CartUtil.isReserve(getCart(), shopCode, skuCode);
//      CommodityAvailability commodityAvailability = service.isAvailable(shopCode, skuCode, 1, isReserve);
//      if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
//        commodity.setSale(true);
//        commodity.setDisplayCartButton(true);
//      } else {
//        commodity.setSale(false);
//        commodity.setDisplayCartButton(false);
//      }
      CommodityDetail dl = cc.getCommodityDetail();
      CommodityHeader dh = cc.getCommodityHeader();
      commodity.setGiftFlg(CommodityType.GIFT.longValue().equals(dh.getCommodityType()));
      String diplayCommodityName = getDisplayName(dh.getCommodityName(), dl.getStandardDetail1Name(), dl.getStandardDetail2Name());
      commodity.setDisplayCartButton(checkAvailable(dl.getShopCode(), dl.getCommodityCode(), dl.getSkuCode(), dh.getCommodityType(), dh.getSetCommodityFlg(), 1L, diplayCommodityName, false));
      commodity.setSale(commodity.isDisplayCartButton());
      // 2012/12/20 促销对应 ob update end
      

      list.add(commodity);
    }
    bean.setList(list);

    // ページ情報の追加
    bean.setPagerValue(PagerUtil.createValue(result));

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  private Long getFormatNumber(Long num) {
    if (NumUtil.isNull(num)) {
      return Long.MAX_VALUE;
    } else {
      return num;
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }
}
