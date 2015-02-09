package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.SalesChartsData;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.SalesStarBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesStarBean.DetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesStarBean.DetailBean.PageBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SalesStarSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前SalesStarSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前SalesStarSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    String tempCommodityName = "";
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();

    SalesStarBean reqBean = (SalesStarBean) getBean();
    List<DetailBean> detailBeanList = new ArrayList<DetailBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    DetailBean detailBean = new DetailBean();

    List<SalesChartsData> salesStarDataList = service.getSalesStarList(DIContainer.getWebshopConfig().getDetailStarTagCode(),
        DIContainer.getWebshopConfig().getDetailStarCommodityLimit());
    List<PageBean> pageBeanList = new ArrayList<PageBean>();

    for (SalesChartsData salesChartsData : salesStarDataList) {
      PageBean pageBean = new PageBean();
      pageBean.setCommodityCode(salesChartsData.getCommodityCode());
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        tempCommodityName = salesChartsData.getCommodityName();
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        tempCommodityName = salesChartsData.getCommodityNameJp();
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        tempCommodityName = salesChartsData.getCommodityNameEn();
      }
      pageBean.setCommodityName(StringUtil.getHeadline(tempCommodityName, 25));

      getBeanChart(pageBean, salesChartsData, service);
      pageBeanList.add(pageBean);
    }
    if(pageBeanList != null && pageBeanList.size() > 0){
      reqBean.setDisplayMode(true);
    }
    detailBean.setPageBeanList(pageBeanList);
    detailBeanList.add(detailBean);
    reqBean.setSalesStarList(detailBeanList);
    setBean(reqBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前SalesStarSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前SalesStarSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
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

  public void getBeanChart(PageBean bean, SalesChartsData data, CatalogService service) {
    CommodityHeader ch = new CommodityHeader();
    ch.setShopCode(DIContainer.getWebshopConfig().getSiteShopCode());
    ch.setCommodityCode(data.getCommodityCode());
    ch.setCommodityTaxType(Long.parseLong(data.getCommodityTaxType()));
    ch.setSaleStartDatetime(data.getSaleStartDatetime());
    ch.setSaleEndDatetime(data.getDiscountPriceEndDatetime());
    ch.setDiscountPriceStartDatetime(data.getDiscountPriceStartDatetime());
    ch.setDiscountPriceEndDatetime(data.getDiscountPriceEndDatetime());
    ch = service.getCommodityHeader(DIContainer.getWebshopConfig().getSiteShopCode(), data.getCommodityCode());
    CommodityDetail cd = new CommodityDetail();
    cd.setUnitPrice(data.getUnitPrice());
    cd.setDiscountPrice(data.getDiscountPrice());
    cd.setReservationPrice(data.getReservationPrice());

    Campaign campaign = service.getAppliedCampaignInfo(DIContainer.getWebshopConfig().getSiteShopCode(), data.getCommodityCode());
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    int displayDiscountRateLimit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    Price price = new Price(ch, cd, campaign, taxRate);
    if (StringUtil.hasValue(ch.getOriginalCommodityCode())  && ch.getCombinationAmount()!=null){
      bean.setUnitPrice(NumUtil.toString(data.getUnitPrice().multiply(new BigDecimal(ch.getCombinationAmount()))));
      if(data.getDiscountPrice()!=null){
        bean.setDiscountPrice(NumUtil.toString(data.getDiscountPrice().multiply(new BigDecimal(ch.getCombinationAmount()))));
      }
    }else{
      bean.setUnitPrice(NumUtil.toString(data.getUnitPrice()));
      bean.setDiscountPrice(NumUtil.toString(data.getDiscountPrice()));
      bean.setReservationPrice(NumUtil.toString(data.getReservationPrice()));
    }
    bean.setCommodityTaxType(data.getCommodityTaxType());
    bean.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
    bean.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
    bean.setImportCommodityType(data.getImportCommodityType());
    bean.setClearCommodityType(data.getClearCommodityType());
    bean.setReserveCommodityType1(data.getReserveCommodityType1());
    bean.setReserveCommodityType2(data.getReserveCommodityType2());
    bean.setReserveCommodityType3(data.getReserveCommodityType3());
    bean.setNewReserveCommodityType1(data.getNewReserveCommodityType1());
    bean.setNewReserveCommodityType2(data.getNewReserveCommodityType2());
    bean.setNewReserveCommodityType3(data.getNewReserveCommodityType3());
    bean.setNewReserveCommodityType4(data.getNewReserveCommodityType4());
    bean.setNewReserveCommodityType5(data.getNewReserveCommodityType5());
    bean.setInnerQuantity(data.getInnerQuantity());
    
    // 如果为限时限量商品（秒杀商品）则discountCommodityflg为true，否则false
    DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(data.getCommodityCode());
    if(dc == null) {
      bean.setDiscountCommodityFlg(false);
    } else {
      bean.setDiscountCommodityFlg(true);
    }
    
    if (price.getDiscountRate().intValue() >= displayDiscountRateLimit) {
      bean.setDisplayDiscountRate(true);
    } else {
      bean.setDisplayDiscountRate(false);
    }
    if (price.isSale()) {
      // 通常の販売期間の場合
      bean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    } else if (price.isDiscount()) {
      // 特別価格期間の場合
      bean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
    } else if (price.isReserved()) {
      // 予約期間の場合
      bean.setPriceMode(CommodityPriceType.RESERVATION_PRICE.getValue());
    } else {
      bean.setPriceMode(CommodityPriceType.UNIT_PRICE.getValue());
    }
  }
}
