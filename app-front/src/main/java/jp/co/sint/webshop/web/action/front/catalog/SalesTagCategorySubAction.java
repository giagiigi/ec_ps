package jp.co.sint.webshop.web.action.front.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.LanguageCode;
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
import jp.co.sint.webshop.web.bean.front.catalog.SalesTagCategoryBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesTagCategoryBean.DetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesTagCategoryBean.DetailBean.PageBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SalesTagCategorySubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {

    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if (sesContainer.getSession() != null) {
      logger.info("当前SalesTagCategorySubAction:sessionID=" + sesContainer.getSession().getId()
          + "开始记录--------------------------------------------------------------------");
    } else {
      logger.info("当前SalesTagCategorySubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    String tempCommodityName = "";
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();

    SalesTagCategoryBean reqBean = (SalesTagCategoryBean) getBean();
    List<DetailBean> detailBeanList = new ArrayList<DetailBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    List<String> tagList = DIContainer.getSalesTagByTop().getSalesTagByTop();

    if (tagList != null && tagList.size() > 0) {
      Long tagCCLimit = NumUtil.toLong(DIContainer.getWebshopConfig().getTagCategoryCommodityLimit());
      String indexLimit = DIContainer.getWebshopConfig().getIndexCommodityLimit();
      for (int i = 0; i < tagList.size(); i++) {
        List<SalesChartsData> salesStarDataList = service.getSalesStarList((String) tagList.get(i), indexLimit);
        if (salesStarDataList != null && salesStarDataList.size() > 0) {
          DetailBean detailBean = new DetailBean();
          List<PageBean> pageBeanList = new ArrayList<PageBean>();
          Long idx = 0L;
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
            if (!(tagList.get(i).equals("TOPPAGE1007") || tagList.get(i).equals("TOPPAGE1008") || tagList.get(i).equals(
                "TOPPAGE1009"))) {
              pageBeanList.add(pageBean);
            } else {
              if (pageBeanList.size() < 5) {
                pageBeanList.add(pageBean);
              }
            }

            // 首页推荐商品的候补实现
            idx++;
            if (idx == tagCCLimit) {
              break;
            }
          }
          if (pageBeanList != null && pageBeanList.size() > 0) {
            reqBean.setDisplayMode(true);
          }
          detailBean.setTagCode((String) tagList.get(i));
          detailBean.setPageBeanList(pageBeanList);
          detailBeanList.add(detailBean);
        }
      }

    }

    reqBean.setSalesTagCategoryList(detailBeanList);
    setBean(reqBean);

    if (sesContainer.getSession() != null) {
      logger.info("当前SalesTagCategorySubAction:sessionID=" + sesContainer.getSession().getId()
          + "结束记录--------------------------------------------------------------------");
    } else {
      logger.info("当前SalesTagCategorySubAction:session缺失，结束记录--------------------------------------------------------------------");
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

    CommodityDetail cd = new CommodityDetail();
    cd.setUnitPrice(data.getUnitPrice());
    cd.setDiscountPrice(data.getDiscountPrice());
    cd.setReservationPrice(data.getReservationPrice());

    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    int displayDiscountRateLimit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    Price price = new Price(ch, cd, null, taxRate);
    if (StringUtil.hasValue(data.getOriginalCommodityCode())) {
      bean.setUnitPrice(NumUtil.toString(price.getUnitPrice().multiply(new BigDecimal(data.getCombinationAmount()))));
      bean.setDiscountPrice(NumUtil.toString(price.getDiscountPrice().multiply(new BigDecimal(data.getCombinationAmount()))));
      bean.setReservationPrice(NumUtil.toString(price.getReservationPrice().multiply(new BigDecimal(data.getCombinationAmount()))));
      bean.setDiscountPrices(NumUtil
          .toString(price.getDiscountPrices().abs().multiply(new BigDecimal(data.getCombinationAmount()))));
    } else {
      bean.setUnitPrice(NumUtil.toString(data.getUnitPrice()));
      bean.setDiscountPrice(NumUtil.toString(data.getDiscountPrice()));
      bean.setReservationPrice(NumUtil.toString(data.getReservationPrice()));
      bean.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
    }

    bean.setCommodityTaxType(data.getCommodityTaxType());

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
    
    // 如果为限时限量商品（秒杀商品）则discountCommodityflg为true，否则false
    DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(data.getCommodityCode());
    if(dc == null) {
      bean.setDiscountCommodityFlg(false);
    } else {
      bean.setPriceMode(CommodityPriceType.DISCOUNT_PRICE.getValue());
      bean.setDiscountPrice(NumUtil.toString(dc.getDiscountPrice()));
      bean.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
      bean.setDiscountPrices(NumUtil.toString(price.getRetailPrice().subtract(dc.getDiscountPrice())));
      bean.setDiscountCommodityFlg(true);
    }
  }
}
