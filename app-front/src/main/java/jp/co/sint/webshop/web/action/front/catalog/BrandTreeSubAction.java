package jp.co.sint.webshop.web.action.front.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.BrandData;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.catalog.BrandTreeBean;
import jp.co.sint.webshop.web.bean.front.catalog.BrandTreeBean.BrandTreeDetailBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * カテゴリツリーのサブアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandTreeSubAction extends WebSubAction {

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {

    BrandTreeBean reqBean = (BrandTreeBean) getBean();
    List<BrandTreeDetailBean> brandList = new ArrayList<BrandTreeDetailBean>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    // プレビューモード（管理側）だった場合、クエリ文字列に設定されているコードを取得
    if (StringUtil.hasValue(getRequestParameter().get("preview"))) {
      reqBean.setPreview(true);
      reqBean.setShopCode(getRequestParameter().get("shopCode"));
      reqBean.setCommodityCode(getRequestParameter().get("commodityCode"));
    } else {
      reqBean.setPreview(false);
      String[] urlParam = getRequestParameter().getPathArgs();
      if (urlParam.length >= 2) {
        reqBean.setShopCode(urlParam[0]);
        reqBean.setCommodityCode(urlParam[1]);
      }
    }
    
    CommodityContainerCondition condition = setSearchCondition(reqBean);
    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 
    // ソート済みカテゴリツリーの取得
    List<BrandData> list = service.getBrandList(condition);
    for (BrandData brand : list) {
      BrandTreeDetailBean bean = new BrandTreeDetailBean();
      if (brand.getCommodityCount() != 0L) {
        bean.setShopCode(brand.getShopCode());
        bean.setBrandCode(brand.getBrandCode());        
        bean.setBrandName(utilService.getNameByLanguage(brand.getBrandName(), brand.getBrandEnglishName(), brand.getBrandJapaneseName()));
        bean.setBrandNameAbbr(brand.getBrandNameAbbr());
        bean.setBrandDescription(utilService.getNameByLanguage(brand.getBrandDescription(),brand.getBrandDescriptionEn(),brand.getBrandDescriptionJp()));
        bean.setCommodityCount(brand.getCommodityCount());
        bean.setCommodityPopularRank(brand.getCommodityPopularRank());
        bean.setCategoryCode(reqBean.getCategoryCode());
        bean.setReviewScore(reqBean.getReviewScore());
        bean.setPrice(reqBean.getPrice());
        bean.setCategoryAttribute1(reqBean.getCategoryAttribute1());
        bean.setAlignmentSequence(reqBean.getAlignmentSequence());
        bean.setMode(reqBean.getMode());
        bean.setPageSize(reqBean.getPageSize());
        brandList.add(bean);
      }
    }
    Collections.sort(brandList, new BrandComparator());
    reqBean.setBrandList(brandList);

    setBean(reqBean);

  }
  
  /**
   * @param reqBean
   *          商品Bean
   * @return 商品情報
   */
  public CommodityContainerCondition setSearchCondition(BrandTreeBean reqBean) {
    CommodityContainerCondition condition = new CommodityContainerCondition();
    condition.setReviewScore(reqBean.getReviewScore());
//    condition.setSearchWord(reqBean.getSearchWord());
    if (StringUtil.hasValue(reqBean.getPrice())) {
      CodeAttribute price = PriceList.fromValue(reqBean.getPrice());
      String[] prices = price.getName().split(",");
      if (prices.length == 2) {
        condition.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
        condition.setSearchPriceEnd(NumUtil.parse(prices[1]).abs().toString());
      } else{
        condition.setSearchPriceStart(NumUtil.parse(prices[0]).abs().toString());
        condition.setSearchPriceEnd("9999999999");
      }
    }
    condition.setByRepresent(true);
    condition.setSearchCategoryCode(reqBean.getCategoryCode());
    condition.setSearchBrandCode(reqBean.getBrandCode());
    condition.setSearchCategoryAttribute1(reqBean.getCategoryAttribute1());

    if (StringUtil.hasValue(reqBean.getAlignmentSequence())) {
      condition.setAlignmentSequence(reqBean.getAlignmentSequence());
    } else {
      reqBean.setAlignmentSequence(CommodityDisplayOrder.BY_POPULAR_RANKING.getValue());
      condition.setAlignmentSequence(reqBean.getAlignmentSequence());
    }

    condition.setDisplayClientType(DisplayClientType.PC.getValue());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    return condition;
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

  public static class BrandComparator implements Serializable, Comparator<BrandTreeDetailBean> {

    private static final long serialVersionUID = 1L;

    public int compare(BrandTreeDetailBean o1, BrandTreeDetailBean o2) {
      // ショップコード、商品コードからアクセスキーを生成し、順位値で
      // 並べ替える
      BigDecimal b1 = new BigDecimal(o1.getCommodityPopularRank());
      BigDecimal b2 = new BigDecimal(o1.getCommodityCount());
      BigDecimal b3 = new BigDecimal(o2.getCommodityPopularRank());
      BigDecimal b4 = new BigDecimal(o2.getCommodityCount());
      BigDecimal b5 = b1.divide(b2);
      BigDecimal b6 = b3.divide(b4);
      return b5.compareTo(b6);
    }
  }
}
