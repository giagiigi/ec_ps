package jp.co.sint.webshop.web.action.front.info;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.SitemapBean;

/**
 * U2050410:サイトマップのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class SitemapInitAction extends WebFrontAction<SitemapBean> {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // カテゴリ情報の取得
//    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
//    List<CategoryInfo> categoryInfoList = catalogService.getAllCategory();
//    List<CategoryInformation> categoryInformationList = new ArrayList<CategoryInformation>();
//    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
//    for (CategoryInfo categoryinfo : categoryInfoList) {
//    	 String name=utilService.getNameByLanguage(categoryinfo.getCategoryNamePc(),categoryinfo.getCategoryNamePcEn(),categoryinfo.getCategoryNamePcJp());
//      if (categoryinfo.getDepth().equals(1L)) {
//        CategoryInformation categoryInformation = new CategoryInformation();
//       
//        // カテゴリ第一階層情報を設定
//        UrlSet firstCategoryInfo = new UrlSet();
//        firstCategoryInfo.setName(name);
//        firstCategoryInfo.setUrl("/app/catalog/list/init?searchCategoryCode=" + categoryinfo.getCategoryCode());
//        categoryInformation.setFirstCategoryInfo(firstCategoryInfo);
//        categoryInformation.setSecondCategoryInfoList(new ArrayList<UrlSet>());
//
//        // カテゴリコードを設定
//        categoryInformation.setCategoryCode(categoryinfo.getCategoryCode());
//
//        categoryInformationList.add(categoryInformation);
//      } else if (categoryinfo.getDepth().equals(2L)) {
//        UrlSet secondCategoryInfo = new UrlSet();
//        secondCategoryInfo.setName(name);
//        secondCategoryInfo.setUrl("/app/catalog/list/init?searchCategoryCode=" + categoryinfo.getCategoryCode());
//        for (CategoryInformation categoryInformation : categoryInformationList) {
//          if (categoryInformation.getCategoryCode().equals(categoryinfo.getParentCategoryCode())) {
//            categoryInformation.getSecondCategoryInfoList().add(secondCategoryInfo);
//          }
//        }
//      }

//    }
//
//    // ショップ情報を取得する
//    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
//
//    List<Shop> shopList = shopService.getShopAll();
//    List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();
//
//    for (Shop shop : shopList) {
//
//      Date endDate = shop.getCloseDatetime();
//      if (shop.getCloseDatetime() == null) {
//        endDate = DateUtil.getMax();
//      }
//      endDate = DateUtil.setHour(shop.getCloseDatetime(), 23);
//      endDate = DateUtil.setMinute(endDate, 59);
//      endDate = DateUtil.setSecond(endDate, 59);
//      if (!DateUtil.isPeriodDate(shop.getOpenDatetime(), endDate)) {
//        continue;
//      }
//
//      UrlSet urlSet = new UrlSet();
//
//      urlSet.setName(shop.getShopName());
//      urlSet.setUrl("/app/info/shopinfo/init/" + shop.getShopCode());
//
//      ShopInfo shopInfo = new ShopInfo();
//      shopInfo.setShopCode(shop.getShopCode());
//      shopInfo.setShopName(shop.getShopName());
//      shopInfo.setShopInfo(urlSet);
//      UrlSet complianceUrl = new UrlSet();
//      complianceUrl.setUrl("/contents/shop/" + shop.getShopCode() + "/compliance/");
//      shopInfo.setShopCompliance(complianceUrl);
//
//      shopInfoList.add(shopInfo);
//    }
//
//    SitemapBean bean = new SitemapBean();
//    bean.setCategoryInfoList(categoryInformationList);
//    bean.setShopInfoList(shopInfoList);
//
//    setRequestBean(bean);
//
//    return FrontActionResult.RESULT_SUCCESS;
    
    // 20120723 ysy add start
    SitemapBean bean = new SitemapBean();
    String[] params = getRequestParameter().getPathArgs();
    if (params.length > 0) {
      bean.setFilePath(params[0]);
    }else{
      bean.setFilePath("sitemap");
    }
    setRequestBean(bean);
    setNextUrl(null);
    return FrontActionResult.RESULT_SUCCESS;
    // 20120723 ysy add end
  }
}
