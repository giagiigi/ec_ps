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
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CategoryData;
import jp.co.sint.webshop.service.catalog.SalesChartsData;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.SalesChartsBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesChartsBean.DetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalesChartsBean.DetailBean.PageBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SalesChartsSubAction extends WebSubAction {

//  /**
//   * アクションを実行します。
//   */
//  @Override
//  public void callService() {
//    
//    String tempCommodityName = "";
//    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
//    
//    SalesChartsBean reqBean = (SalesChartsBean) getBean();
//    List<DetailBean> detailBeanList = new ArrayList<DetailBean>();
//    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
//    
//    //读取一级类目
//    List<CategoryData> categoryList = service.getCategoryTree(null,null,null);
//    for (CategoryData categoryData : categoryList) {
//      //当类目CODE为零时，进行下一条数据封装
//      if (!StringUtil.isNullOrEmpty(categoryData.getCategoryCode()) && categoryData.getCategoryCode().equals("0")) {
//        continue;
//      }
//      
//      DetailBean detailBean = new DetailBean();
//      detailBean.setCategoryCode(categoryData.getCategoryCode());
//      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
//        detailBean.setCategoryName(categoryData.getCategoryNamePc());
//      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
//        detailBean.setCategoryName(categoryData.getCategoryNamePcJp());
//      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
//        detailBean.setCategoryName(categoryData.getCategoryNamePcEn());
//      }
//      //根据类目CODE取得相关商品（按照commodity_popular_rank 人気順位字段排序）
//      List<SalesChartsData> salesChartsDataList = service.getSalesChartsList(categoryData.getCategoryCode(),DIContainer.getWebshopConfig().getIndexCommodityLimit());
//      List<PageBean> pageBeanList = new ArrayList<PageBean>();
//      
//      for (SalesChartsData salesChartsData : salesChartsDataList) {
//        PageBean pageBean = new PageBean();
//        pageBean.setCommodityCode(salesChartsData.getCommodityCode());
//        if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
//          tempCommodityName = salesChartsData.getCommodityName();
//        } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
//          tempCommodityName = salesChartsData.getCommodityNameJp();
//        } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
//          tempCommodityName = salesChartsData.getCommodityNameEn();
//        }
//        pageBean.setCommodityName(StringUtil.getHeadline(tempCommodityName, 25));
//        
//        getBeanChart(pageBean, salesChartsData, service);
//        pageBeanList.add(pageBean);
//      }
//      detailBean.setPageBeanList(pageBeanList);
//      detailBeanList.add(detailBean);
//    }
//    
//    reqBean.setSalesChartsList(detailBeanList);
//    setBean(reqBean);
//  }

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前SalesChartsSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前SalesChartsSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }
    
    String tempCommodityName = "";
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    
    SalesChartsBean reqBean = (SalesChartsBean) getBean();
    List<DetailBean> detailBeanList = new ArrayList<DetailBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    
    //读取一级类目
    List<CategoryData> categoryList = service.getCategoryTree(null,null,null);
    for (CategoryData categoryData : categoryList) {
      //当类目CODE为零时，进行下一条数据封装
      if (!StringUtil.isNullOrEmpty(categoryData.getCategoryCode()) && categoryData.getCategoryCode().equals("0")) {
        continue;
      }
      
      DetailBean detailBean = new DetailBean();
      detailBean.setCategoryCode(categoryData.getCategoryCode());
      if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
        detailBean.setCategoryName(categoryData.getCategoryNamePc());
      } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
        detailBean.setCategoryName(categoryData.getCategoryNamePcJp());
      } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
        detailBean.setCategoryName(categoryData.getCategoryNamePcEn());
      }
      
      //根据类目CODE取得相关商品（按照commodity_popular_rank 人気順位字段排序）
      List<SalesChartsData> salesChartsDataList = service.getSalesChartsList(categoryData.getCategoryCode(),DIContainer.getWebshopConfig().getIndexTagCode(),DIContainer.getWebshopConfig().getIndexCommodityLimit());
      List<PageBean> pageBeanList = new ArrayList<PageBean>();
      
      for (SalesChartsData salesChartsData : salesChartsDataList) {
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
      detailBean.setPageBeanList(pageBeanList);
      detailBeanList.add(detailBean);
    }
    reqBean.setSalesChartsList(detailBeanList);
    setBean(reqBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前SalesChartsSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前SalesChartsSubAction:session缺失，结束记录--------------------------------------------------------------------");
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

    Campaign campaign = service.getAppliedCampaignInfo(DIContainer.getWebshopConfig().getSiteShopCode(), data.getCommodityCode());
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    int displayDiscountRateLimit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    Price price = new Price(ch, cd, campaign, taxRate);

    if(StringUtil.hasValue(data.getOriginalCommodityCode())){
      bean.setUnitPrice(NumUtil.toString(BigDecimalUtil.multiply(price.getUnitPrice(), data.getCombinationAmount())));
      bean.setDiscountPrice(NumUtil.toString(BigDecimalUtil.multiply(price.getDiscountPrice(), data.getCombinationAmount())));
      if (data.getReservationPrice()!=null){
        bean.setReservationPrice(NumUtil.toString(BigDecimalUtil.multiply(data.getReservationPrice(), data.getCombinationAmount())));
      }
      bean.setDiscountPrices(NumUtil.toString(BigDecimalUtil.multiply(price.getDiscountPrices().abs(), data.getCombinationAmount())));
    } else {
      bean.setUnitPrice(NumUtil.toString(data.getUnitPrice()));
      bean.setDiscountPrice(NumUtil.toString(data.getDiscountPrice()));
      bean.setReservationPrice(NumUtil.toString(data.getReservationPrice()));
      bean.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
    }
    
    bean.setCommodityTaxType(data.getCommodityTaxType());
    bean.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
    // add by yyq start 20130415
    if(data.getImportCommodityType() != null){
      bean.setImportCommodityType(data.getImportCommodityType());
    }
    if(data.getClearCommodityType() != null){
      bean.setClearCommodityType(data.getClearCommodityType());
    }
    if(data.getReserveCommodityType1() != null){
      bean.setReserveCommodityType1(data.getReserveCommodityType1());
    }
    if(data.getReserveCommodityType2() != null){
      bean.setReserveCommodityType2(data.getReserveCommodityType2());
    }
    if(data.getReserveCommodityType3() != null){
      bean.setReserveCommodityType3(data.getReserveCommodityType3());
    }
    if(data.getNewReserveCommodityType1() != null){
      bean.setNewReserveCommodityType1(data.getNewReserveCommodityType1());
    }
    if(data.getNewReserveCommodityType2() != null){
      bean.setNewReserveCommodityType2(data.getNewReserveCommodityType2());
    }
    if(data.getNewReserveCommodityType3() != null){
      bean.setNewReserveCommodityType3(data.getNewReserveCommodityType3());
    }
    if(data.getNewReserveCommodityType4() != null){
      bean.setNewReserveCommodityType4(data.getNewReserveCommodityType4());
    }
    if(data.getNewReserveCommodityType5() != null){
      bean.setNewReserveCommodityType5(data.getNewReserveCommodityType5());
    }
    if(data.getInnerQuantity()!= null){
      bean.setInnerQuantity(data.getInnerQuantity());
    }
    // add by yyq end 20130415
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
