package jp.co.sint.webshop.web.action.front.mypage;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.mypage.RecommendListBean;
import jp.co.sint.webshop.web.bean.front.mypage.RecommendListBean.RecommendDetail;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U2030820:おすすめ商品のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RecommendListInitAction extends WebFrontAction<RecommendListBean> {
	  

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
    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 
    // 検索条件の作成
    RecommendListBean bean = new RecommendListBean();

    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setSearchCustomerCode(customerCode);
    condition.setDisplayClientType(DisplayClientType.PC.getValue());
    condition.setByRepresent(true);
    condition.setMaxFetchSize(DIContainer.getWebshopConfig().getCustomerRecommendCommodityMaxLineCount());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索結果の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<CommodityContainer> result = service.getRecommendedCommodities(condition);
    // 検索結果が上限値を超えたら、取得数を上限値の値にする。
    if (result.isOverflow()) {
      result.setRowCount(result.getMaxFetchSize());
    }
    List<CommodityContainer> commodityList = result.getRows();

    // nextBeanに検索結果を設定
    List<RecommendDetail> list = new ArrayList<RecommendDetail>();
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    for (CommodityContainer cc : commodityList) {
      RecommendDetail commodity = new RecommendDetail();
      Campaign campaign = service.getAppliedCampaignInfo(cc.getCommodityHeader().getShopCode(), cc.getCommodityHeader()
          .getCommodityCode());
      Price price = new Price(cc.getCommodityHeader(), cc.getCommodityDetail(), campaign, taxRate);

      //add by cs_yuli 20120514 start 
      commodity.setCommodityName(utilService.getNameByLanguage(cc.getCommodityHeader().getCommodityName(),cc.getCommodityHeader().getCommodityNameEn(),cc.getCommodityHeader().getCommodityNameJp()));
      commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(utilService.getNameByLanguage(cc.getCommodityHeader().getCommodityDescriptionMobile(),cc.getCommodityHeader().getCommodityDescriptionMobileEn(),cc.getCommodityHeader().getCommodityDescriptionMobileJp())));
      commodity.setCommodityDescription(WebUtil.removeHtmlTag(utilService.getNameByLanguage(cc.getCommodityHeader().getCommodityDescriptionPc(),cc.getCommodityHeader().getCommodityDescriptionPcEn(),cc.getCommodityHeader().getCommodityDescriptionPcJp())));
      //add by cs_yuli 20120514 end
      commodity.setCommodityCode(cc.getCommodityHeader().getCommodityCode());
      commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
      commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
      commodity.setReservedPrice(NumUtil.toString(price.getReservationPrice()));
      commodity.setCommodityTaxType(NumUtil.toString(cc.getCommodityHeader().getCommodityTaxType()));
      commodity.setCommodityPointRate(NumUtil.toString(cc.getCommodityHeader().getCommodityPointRate()));
      // 20120116 ysy update start
//      commodity.setCommodityDescription(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionPc()));
//      commodity.setCommodityDescriptionShort(WebUtil.removeHtmlTag(cc.getCommodityHeader().getCommodityDescriptionMobile()));
      // 20120116 ysy update end
      commodity.setReviewScore(NumUtil.toString(cc.getReviewSummary().getReviewScore()));
      commodity.setStockQuantity(NumUtil.toString(cc.getContainerAddInfo().getAvailableStockQuantity()));
      commodity.setCampaignPeriod(price.isCampaign());
      commodity.setPointPeriod(price.isPoint());
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

      // 商品コードに関連付いている在庫が1つでもあるかどうかを判別
      if (service.hasStockCommodity(cc.getCommodityHeader().getShopCode(), cc.getCommodityHeader().getCommodityCode())) {
        commodity.setStockStatus(RecommendListBean.STOCK_TRUE);
      } else {
        commodity.setStockStatus(RecommendListBean.STOCK_FALSE);
      }

      list.add(commodity);
    }
    bean.setList(list);

    // ページ情報の追加
    bean.setPagerValue(PagerUtil.createValue(result));
    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
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
