package jp.co.sint.webshop.web.action.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityPriceType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.PlanDetailType;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.PlanInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.SalePlanMobileBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalePlanMobileBean.PlanCommodityBean;
import jp.co.sint.webshop.web.bean.front.catalog.SalePlanMobileBean.PlanDetailBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * 销售企划
 * 
 * @author kousen
 */
public class SalePlanMobileSubAction extends WebSubAction {

  private String commodityDescriptionPc="";
  private String commodityName="";

  @Override
  public void callService() {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    SalePlanMobileBean bean = new SalePlanMobileBean();

    // 销售企划类型
    String salePlanDetailTypes = "0001";
    bean.setSaleFlag(salePlanDetailTypes);
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());

    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo());
    // 销售企划
    Plan plan = catalogService.getPlan(PlanType.PROMOTION.getValue(), salePlanDetailTypes);

    if (StringUtil.isNullOrEmpty(plan)) {
		return;
	}
    // 设置企划内容
    bean.setPlanCode(plan.getPlanCode());
    // 20120528 tuxinwei update start
    bean.setPlanName(utilService.getNameByLanguage(plan.getPlanName(), plan.getPlanNameEn(), plan.getPlanNameJp()));
    bean.setPlanDescription(utilService.getNameByLanguage(plan.getPlanDescription(), plan.getPlanDescriptionEn(), plan.getPlanDescriptionJp()));
    // 20120528 tuxinwei update end
    bean.setPlanStartDatetime(plan.getPlanStartDatetime());
    bean.setPlanEndDatetime(plan.getPlanEndDatetime());

    // 设置企划明细
    List<PlanInfo> planInfoList = catalogService.getPlanCommodityInfoList(plan.getPlanCode());
    List<PlanDetailBean> planDetailList = new ArrayList<PlanDetailBean>();
    PlanDetailBean detailBean = null;
    int i = 0;
    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    int limit = DIContainer.getWebshopConfig().getDisplayDiscountRateLimit();
    for (PlanInfo detailInfo : planInfoList) {
      PlanCommodityBean commodity = new PlanCommodityBean();
      Price price = new Price(detailInfo.getCommodityHeader(), detailInfo.getCommodityDetail(), null, taxRate);
      commodity.setShopCode(detailInfo.getCommodityHeader().getShopCode());
      commodity.setCommodityCode(detailInfo.getCommodityCode());
	    //add by cs_yuli 20120514 start 
	  	commodityName = utilService.getNameByLanguage(detailInfo.getCommodityHeader().getCommodityName(),detailInfo.getCommodityHeader().getCommodityNameEn(),detailInfo.getCommodityHeader().getCommodityNameJp());
		  commodityDescriptionPc=WebUtil.removeHtmlTag(utilService.getNameByLanguage(detailInfo.getCommodityHeader().getCommodityDescriptionMobile(),detailInfo.getCommodityHeader().getCommodityDescriptionMobileEn(),detailInfo.getCommodityHeader().getCommodityDescriptionMobileJp()));
	    commodity.setCommodityName(commodityName);
		  commodity.setCommodityDescriptionShort(commodityDescriptionPc);	 
      //add by cs_yuli 20120514 end    
      commodity.setCommodityTaxType(NumUtil.toString(detailInfo.getCommodityHeader().getCommodityTaxType()));
      commodity.setUnitPrice(NumUtil.toString(price.getUnitPrice()));
      commodity.setDiscountPrice(NumUtil.toString(price.getDiscountPrice()));
      commodity.setReservationPrice(NumUtil.toString(price.getReservationPrice()));
      commodity.setDiscountRate(NumUtil.toString(price.getDiscountRate().longValue()));
      commodity.setDiscountPrices(NumUtil.toString(price.getDiscountPrices().abs()));
       if (StringUtil.hasValue(commodity.getCommodityDescriptionShort())) {
        if (commodity.getCommodityDescriptionShort().length() > 19) {
          commodity.setCommodityDescriptionShort(commodity.getCommodityDescriptionShort().substring(0, 19));
        }
      }
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
      
      if (i == 0) {
        detailBean = new PlanDetailBean();
        if (PlanDetailType.CATEGORY.getValue().equals(detailInfo.getDetailType())) {
          Category category = catalogService.getCategory(detailInfo.getDetailCode());
          if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
            detailBean.setDetailName(category.getCategoryNamePc());
          } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
            detailBean.setDetailName(category.getCategoryNamePcJp());
          } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
            detailBean.setDetailName(category.getCategoryNamePcEn());
          } 
        }else if (PlanDetailType.BRAND.getValue().equals(detailInfo.getDetailType())) {
          Brand brand = catalogService.getBrand(DIContainer.getWebshopConfig().getSiteShopCode(),detailInfo.getDetailCode());
          if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
            detailBean.setDetailName(brand.getBrandName());
          } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
            detailBean.setDetailName(brand.getBrandJapaneseName());
          } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
            detailBean.setDetailName(brand.getBrandEnglishName());
          } 
        }else {
          if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
            detailBean.setDetailName(detailInfo.getDetailName());
          } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
            detailBean.setDetailName(detailInfo.getDetailNameJp());
          } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
            detailBean.setDetailName(detailInfo.getDetailNameEn());
          } 
        }
        detailBean.setDetailCode(detailInfo.getDetailCode());
        
        if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
          detailBean.setDetailUrl(detailInfo.getDetailUrl());
        } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
          detailBean.setDetailUrl(detailInfo.getDetailUrlJp());
        } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
          detailBean.setDetailUrl(detailInfo.getDetailUrlEn());
        }
        detailBean.setDetailType(detailInfo.getDetailType());
        detailBean.setShowCommodityCount(detailInfo.getShowCommodityCount());
      } else if (detailInfo.getDetailCode().equals(detailBean.getDetailCode())
          && detailInfo.getDetailType().equals(detailBean.getDetailType())) {
      } else {
        planDetailList.add(detailBean);
        detailBean = new PlanDetailBean();
        detailBean.setDetailCode(detailInfo.getDetailCode());
        detailBean.setDetailType(detailInfo.getDetailType());
        if (PlanDetailType.CATEGORY.getValue().equals(detailInfo.getDetailType())) {
          Category category = catalogService.getCategory(detailInfo.getDetailCode());
          if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
            detailBean.setDetailName(category.getCategoryNamePc());
          } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
            detailBean.setDetailName(category.getCategoryNamePcJp());
          } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
            detailBean.setDetailName(category.getCategoryNamePcEn());
          } 
        }else if (PlanDetailType.BRAND.getValue().equals(detailInfo.getDetailType())) {
          Brand brand = catalogService.getBrand(DIContainer.getWebshopConfig().getSiteShopCode(),detailInfo.getDetailCode());
          if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
            detailBean.setDetailName(brand.getBrandName());
          } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
            detailBean.setDetailName(brand.getBrandJapaneseName());
          } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
            detailBean.setDetailName(brand.getBrandEnglishName());
          } 
        }else {
          if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
            detailBean.setDetailName(detailInfo.getDetailName());
          } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
            detailBean.setDetailName(detailInfo.getDetailNameJp());
          } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
            detailBean.setDetailName(detailInfo.getDetailNameEn());
          } 
        }
        if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
          detailBean.setDetailUrl(detailInfo.getDetailUrl());
        } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
          detailBean.setDetailUrl(detailInfo.getDetailUrlJp());
        } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
          detailBean.setDetailUrl(detailInfo.getDetailUrlEn());
        }
        detailBean.setShowCommodityCount(detailInfo.getShowCommodityCount());
      }
      detailBean.getPlanCommodityList().add(commodity);
      i++;
    }
    if (i != 0) {
      planDetailList.add(detailBean);
    }

    bean.setPlanDetailList(planDetailList);
    setBean(bean);
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
