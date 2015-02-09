package jp.co.sint.webshop.web.action.front.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.dto.RelatedBrand;
import jp.co.sint.webshop.data.dto.RelatedSiblingCategory;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean.RelatedBrandBean;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean.RelatedSiblingCategoryBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

import org.apache.log4j.Logger;

/**
 * U2040510:商品詳細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DetailInitAction extends DetailBaseAction {

  /**
   * 初期化処理
   */
  @Override
  public void init() {

    // 初期表示時は必ずデータをクリアする
    CommodityDetailBean bean = new CommodityDetailBean();
    bean.setPreview(checkPreviewDigest(getBean()));
    setBean(bean);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String shopCode = "00000000";
    String commodityCode = null;

    // 該当商品が公開中かどうかのチェック
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      // shopCode = urlParam[0];
      commodityCode = urlParam[0];
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      if (!catalogService.isListed(shopCode, commodityCode) && !getBean().isPreview()) {
        throw new URLNotFoundException();
      }
    } else {
      throw new URLNotFoundException();
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前DetailInitAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前DetailInitAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    CommodityDetailBean reqBean = new CommodityDetailBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      setRelatedBrandAndCategory(reqBean,urlParam[0]);
    }

    reqBean.setPreview(getBean().isPreview());
    if (getBean().isPreview()) {
      reqBean.setShopCode(getRequestParameter().get("shopCode"));
      reqBean.setCommodityCode(getRequestParameter().get("commodityCode"));
    }
    
    reqBean.setCategoryCode(getBean().getCategoryCode());
    setNextUrl(null);
    createCommodityInfo(reqBean);
    
    ContentsPath path = DIContainer.get("contentsPath");
    String contentsPath = path.getContentsSharedPath();
    String path1 =contentsPath + "/shop/00000000/commodity/";
    String path2 =contentsPath + "/shop/00000000/commodity/";
    
    if(new File(path1+reqBean.getCommodityCode()+"_m.jpg").exists()){
      reqBean.setPicFlgMid(true);
    }
    if(new File(path1+reqBean.getCommodityCode()+"_1_m.jpg").exists()){
      reqBean.setPicFlgMid1(true);
    }
    if(new File(path1+reqBean.getCommodityCode()+"_2_m.jpg").exists()){
      reqBean.setPicFlgMid2(true);
    }
    if(new File(path1+reqBean.getCommodityCode()+"_3_m.jpg").exists()){
      reqBean.setPicFlgMid3(true);
    }
    if(new File(path1+reqBean.getCommodityCode()+"_4_m.jpg").exists()){
      reqBean.setPicFlgMid4(true);
    }
    if(new File(path1+reqBean.getCommodityCode()+"_t.jpg").exists()){
      reqBean.setPicFlg1(true);
    }
    if(new File(path2+reqBean.getCommodityCode()+"_1_t.jpg").exists()){
      reqBean.setPicFlg2(true);
    }
    if(new File(path2+reqBean.getCommodityCode()+"_2_t.jpg").exists()){
      reqBean.setPicFlg3(true);
    }
    if(new File(path2+reqBean.getCommodityCode()+"_3_t.jpg").exists()){
      reqBean.setPicFlg4(true);
    }
    if(new File(path2+reqBean.getCommodityCode()+"_4_t.jpg").exists()){
      reqBean.setPicFlg5(true);
    }
    
    setRequestBean(reqBean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前DetailInitAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前DetailInitAction:session缺失，结束记录--------------------------------------------------------------------");
    }

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    String[] params = getRequestParameter().getPathArgs();
    if (params.length >= 2) {
      setDisplayMessage(params[1]);
    }
  }
  
  /**
   * プレビューモードかどうかを検証します。
   * 
   * @param bean
   * @return
   */
  private boolean checkPreviewDigest(CommodityDetailBean bean) {
    boolean isPreview = false;
    if (StringUtil.hasValue(bean.getPreviewDigest())) {
      String digest = PasswordUtil.decrypt(bean.getPreviewDigest());
      String[] keys = digest.split("/");
      String shopCode = getPathInfo(0);
      String commodityCode = getPathInfo(1);
      
      String startDatetime = DateUtil.toDateTimeString(DateUtil.addMinute(DateUtil.getSysdate(), -3), DateUtil.TIMESTAMP_FORMAT);
      String endDatetime = DateUtil.toDateTimeString(DateUtil.addMinute(DateUtil.getSysdate(), 3), DateUtil.TIMESTAMP_FORMAT);
      String whenString = keys[2];
      
      boolean isValid = false;
      isValid = shopCode.equals(keys[0]);
      isValid &= commodityCode.equals(keys[1]);
      isValid &= ValidatorUtil.lessThan(startDatetime, whenString);
      isValid &= ValidatorUtil.lessThan(whenString, endDatetime);
      
      if (isValid) {
        isPreview = true;
      } else {
        throw new URLNotFoundException();
      }
    }
    return isPreview;
  }
  
  private void setRelatedBrandAndCategory(CommodityDetailBean reqBean,String commodityCode){
    reqBean.setShopCode("00000000");
    reqBean.setCommodityCode(commodityCode);
    
    UtilService uService = ServiceLocator.getUtilService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    WebshopConfig config = DIContainer.getWebshopConfig();
    List<RelatedBrand> relatedBrandList = catalogService.getRelatedBrandByCommodityCode(commodityCode, config.getShowRelatedBrandNum());
    List<RelatedBrandBean> relatedBrandBeanList = new ArrayList<RelatedBrandBean>();
    if(relatedBrandList !=null && relatedBrandList.size()>0){
      for(RelatedBrand brand:relatedBrandList){
        RelatedBrandBean brandBean = new RelatedBrandBean();
        brandBean.setCommodityCode(brand.getCommodityCode());
        brandBean.setBrandCode(brand.getBrandCode());
        brandBean.setBrandName(uService.getNameByLanguage(brand.getBrandName(),brand.getBrandNameEn(),brand.getBrandNameJp()));
        relatedBrandBeanList.add(brandBean);
      }
    }
    reqBean.setBrands(relatedBrandBeanList);
    
    List<RelatedSiblingCategory>  categoryList = catalogService.getRelatedSiblingCategoryByCommodityCode(commodityCode, config.getShowRelatedCategoryNum());
    List<RelatedSiblingCategoryBean>  categoryBeanList = new ArrayList<RelatedSiblingCategoryBean>();
    if(categoryList !=null && categoryList.size()>0){
      for(RelatedSiblingCategory category:categoryList){
        RelatedSiblingCategoryBean categoryBean = new RelatedSiblingCategoryBean();
        categoryBean.setCommodityCode(category.getCommodityCode());
        categoryBean.setCategoryCode(category.getCategoryCode());
        categoryBean.setCategoryName(uService.getNameByLanguage(category.getCategoryName(), category.getCategoryNameEn(), category.getCategoryNameJp()));
        categoryBeanList.add(categoryBean);
      }
    }
    reqBean.setCategorys(categoryBeanList);
  }

}
