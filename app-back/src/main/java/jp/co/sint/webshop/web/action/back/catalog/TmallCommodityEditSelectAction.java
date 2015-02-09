package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebShopSpecialConfig;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.BrandDao;
import jp.co.sint.webshop.data.dao.TmallCategoryDao;
import jp.co.sint.webshop.data.dao.TmallCommodityPropertyDao;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.DataContainer;
import jp.co.sint.webshop.data.dto.TmallCategory;
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditHeaderBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditSkuBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.file.UploadContents;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditSelectAction extends TmallCommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;

    authorization = Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= getLoginInfo().getShopCode().equals(shopCode);
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // URLパラメータのチェック
    if (getRequestParameter().getPathArgs().length >= 2) {
      String[] params = getRequestParameter().getPathArgs();
      if (params.length == 4 && params[3] != null && params[3].equals("update")) {

        /*********************************** check毛利率，如果修改价格导致毛利率<0则报错（售价-（平均移动成本*（1+税率））/售价） **********************************/
        String updateSkuCode = this.getUpdateSkuCode();
        CatalogService catalogservice = ServiceLocator.getCatalogService(getLoginInfo());
        if (StringUtil.isNullOrEmpty(updateSkuCode)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
          return false;
        }

        SearchResult<CCommodityDetail> ccommodityDetailSearchResult = catalogservice.searchCCommodityDetail(updateSkuCode);

        CCommodityDetail ccommodityDetail = ccommodityDetailSearchResult.getRows().get(0);

        BigDecimal newEcDiscountPriceReadyForCheck = ccommodityDetail.getDiscountPrice();
        // 后台的tmall特价就是前台的jd售价
        BigDecimal newJdPriceReadyForCheck = ccommodityDetail.getTmallDiscountPrice();
        SearchResult<CCommodityHeader> cCommodityHeaderSearchResult = catalogservice.searchCCommodityHeader(updateSkuCode);

        // 如果此商品新更新的“平均计算成本”的值不为空，才check毛利率
        // 分2种情况，如果新更新的“平均计算成本”的值为空，说明用户不想check毛利率了，自然不用检查；如果不为空，则计算毛利率
        if (ccommodityDetail.getAverageCost() != null) {
          // 不是清仓商品，才check毛利率。
          if (cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1) {

            // check Ec特价的毛利率
            if ((cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
                && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null
                && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()) 
                && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate()))
                || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
                    && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() == null 
                    && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()))
                || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() == null
                    && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null 
                    && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate()))) {
              if (newEcDiscountPriceReadyForCheck != null) {
                BigDecimal ecSpecialGrossProfitRate = calculateGrossProfitRate(newEcDiscountPriceReadyForCheck, catalogservice,
                    ccommodityDetail);
                // 毛利率小于0，报错.
                if (ecSpecialGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                  addErrorMessage("EC特价的毛利率为:" + ecSpecialGrossProfitRate.toString() + "，小于0");
                }
              }
            }

            // check JD售价的毛利率
            if (newJdPriceReadyForCheck != null) {
              BigDecimal jdGrossProfitRate = calculateGrossProfitRate(newJdPriceReadyForCheck, catalogservice, ccommodityDetail);
              // 毛利率小于0，报错.
              if (jdGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                addErrorMessage("JD售价的毛利率为:" + jdGrossProfitRate.toString() + "，小于0");
              }
            }
          }
        }

        /*********************************** check毛利率，如果修改价格导致毛利率<0则报错（售价-（平均移动成本*（1+税率））/售价） end **********************************/
      }
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());

    // nextBeanを生成する
    TmallCommodityEditBean reqBean = new TmallCommodityEditBean();
    TmallCommodityEditHeaderBean headerBean = new TmallCommodityEditHeaderBean();
    TmallCommodityEditSkuBean skuBean = new TmallCommodityEditSkuBean();

    // URLパラメータを取得する
    // パラメータ形式:～/select/【ショップコード】/【商品コード】/【編集モード】[/【完了パラメータ】]
    String[] params = getRequestParameter().getPathArgs();
    String shopCode = params[0];
    String commodityCode = params[1];
    // String editMode = params[2];

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = catalogService.getCCommodityInfo(shopCode, commodityCode);

    if (commodityInfo == null || commodityInfo.getCheader() == null || commodityInfo.getCdetail() == null) {
      setNextUrl("/app/catalog/tmall_commodity_list/init/nodata");
      return BackActionResult.RESULT_SUCCESS;
    }
    // 设置action对应的bean
    CCommodityHeader header = commodityInfo.getCheader();
    headerBean = buildBeanByCcheader(header);
    // 用于判断商品Code是否改变
    reqBean.setOldCommodityCode(header.getCommodityCode());
    skuBean = buildSkuBean(commodityInfo.getCdetail());
    reqBean.setCommodityCode(header.getCommodityCode());
    //增加字段原商品编号与组合数
    //skuBean.setOriginalCommodityCode(header.getOriginalCommodityCode());
    //skuBean.setCombinationAmount(NumUtil.toString(header.getCombinationAmount()));
    // CCommodityDetail detail = commodityInfo.getCdetail();
    // Stock stock = commodityInfo.getStock();

    //    
    /**
     * 查询类别
     */
    List<TmallCategory> listCategorys = catalogService.loadAllChildCategory();
    reqBean.setListCategorys(listCategorys);

    // skuBean.setPurchasePrice(NumUtil.toString(detail.getPurchasePrice()));
    // skuBean.setTmallUnitPrice(String.valueOf(detail.getTmallUnitPrice()));
    // skuBean.setStandardDetail1Id(replaceNull(detail.getStandardDetail1Id()));
    // skuBean.setStandardDetail2Id(replaceNull(detail.getStandardDetail2Id()));
    // skuBean.setUnitPrice(NumUtil.toString(detail.getUnitPrice()));
    // skuBean.setDiscountPrice(NumUtil.toString(detail.getDiscountPrice()));
    // skuBean.setReservationLimit(NumUtil.toString(stock.getReservationLimit()));
    // skuBean.setOneshotReservationLimit(NumUtil.toString(stock.getOneshotReservationLimit()));
    /**
     * 查询类别对应的属性
     */
    // List<TmallProperty> properties =
    // catalogService.loadTmallPropertiesByCategoryId(String.valueOf(header.getTmallCategoryId()));
    TmallCategory thisCategory = new TmallCategory();
    TmallCategoryDao categoryDao = DIContainer.getDao(TmallCategoryDao.class);
    thisCategory = categoryDao.load(String.valueOf(header.getTmallCategoryId()));

    if (thisCategory != null) {

      reqBean.setCategoryCode(thisCategory.getCategoryCode());
      // thisCategory.setPropertyContain(properties);
      StringBuilder sb = new StringBuilder("");
      TmallCategory category = new TmallCategory();
      category.setCategoryCode(thisCategory.getCategoryCode());
      category.setCategoryName(thisCategory.getCategoryName());
      category.setParentCode(thisCategory.getParentCode());
      getFullCategoryName(category, sb);
      String strs[] = sb.toString().split("&&&__");
      StringBuilder nameSb = new StringBuilder("");
      for (int i = strs.length - 1; i > 0; i--) {
        nameSb.append(strs[i]);
        if (i - 1 > 0 && !"".equals(strs[i - 1])) {
          nameSb.append(">");
        }
      }
      
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      reqBean.setOrignalPlaceList(utilService.getOriginalPlace());
      /**
       * 查询类目名称 各级类目名称都显示出来 格式：父类目名>子类目名....
       */
      reqBean.setCategoryName(nameSb.toString());
      /**
       * 查询商品包含的属性
       */
      // List<TmallCommodityProperty> propertys =
      // catalogService.loadPropertyByCommodityCode(header.getCommodityCode());
      //      
      // reqBean.setProperties(propertys);
      // for(int i=0;i<properties.size();i++){
      // TmallProperty pros=properties.get(i);
      // List<TmallPropertyValue> valueList = pros.getPropertyValueContain();
      // for(TmallPropertyValue value : valueList){
      // value.setIsSelect(validateSelectItem(propertys,value));
      // }
      // }
      // 将类型属性生成html
      // CategoryViewUtil util = new CategoryViewUtil();
      // String proString = util.buildHtml(properties);
      // reqBean.setPropertyHtml(proString);
    } else {
      if (headerBean.getCommodityType() != 1) {
        addWarningMessage("警告：商品类目没有设置");
      }

    }
    // 初始化特价区分
    reqBean.setPlanDetailTypeList(DIContainer.getPlanDetailTypeValue().getSalePlanDetailTypes());
    // 初始化特集区分类型
    reqBean.setFeaturedPlanDetailTypes(DIContainer.getPlanDetailTypeValue().getFeaturedPlanDetailTypes());
    // 设置商品类目
    headerBean.setCategory(thisCategory);
    // 商品品牌初始化
    if (header.getBrandCode() != null && !"".equals(header.getBrandCode())) {
      BrandDao brandDao = DIContainer.getDao(BrandDao.class);
      Brand brand = brandDao.load(header.getShopCode(), header.getBrandCode());
      if (brand != null) {
        reqBean.setCommodityBrand(brand);
        reqBean.setCommodityBrandName(brand.getBrandName());
      } else {
        if (headerBean.getCommodityType() != 1) {
          addWarningMessage("警告：商品品牌没有设置");
        }
        headerBean.setBrandCode(null);
      }
    }
    // 查询商品包含的属性
    TmallCommodityPropertyDao tmallCommodityPropertyDao = DIContainer.getDao(TmallCommodityPropertyDao.class);
    List<TmallCommodityProperty> ps = tmallCommodityPropertyDao.loadByCommodityCode(commodityCode);

    DataContainer<TmallCommodityProperty> datas = new DataContainer<TmallCommodityProperty>();
    datas.setElements(ps);
    reqBean.setCommodityPropertyJson(datas.toJsonString());
    /**
     * 查询sku单价
     */
    // Map<String, String> prices =
    // catalogService.findTmallDetailMinPrice(header.getShopCode(),
    // header.getCommodityCode());
    // if(prices!=null){
    // //设置SKU单价
    // skuBean.setUnitPrice(prices.get("unit_price"));
    // skuBean.setPurchasePrice(prices.get("purchase_price"));
    // skuBean.setTmallUnitPrice(prices.get("tmall_unit_price"));
    // skuBean.setTmallDiscountPrice(prices.get("tmall_discount_price"));
    // skuBean.setDiscountPrice(prices.get("discount_price"));
    // }
    // if (!NumUtil.isNull(stock.getStockThreshold()) &&
    // stock.getStockThreshold() == 0) {
    // skuBean.setStockThreshold(null);
    // } else {
    // skuBean.setStockThreshold(NumUtil.toString(stock.getStockThreshold()));
    // }

    // if (editMode.equals("edit")) {
    // reqBean.setCommodityCode(header.getCommodityCode());
    // reqBean.setMode(MODE_UPDATE);
    //
    // headerBean.setUpdateDatetime(header.getUpdatedDatetime());
    //
    // skuBean.setRepresentSkuCode(detail.getSkuCode());
    // skuBean.setUpdateDatetime(detail.getUpdatedDatetime());
    //
    // ContentsSearchCondition condition = new ContentsSearchCondition();
    // condition.setContentsType(ContentsType.IMAGE_DATA_SHOP_COMMODITY);
    // condition.setShopCode(getLoginInfo().getShopCode());
    // condition.setCommodityCode(reqBean.getCommodityCode());
    // condition.setSkuCode(skuBean.getRepresentSkuCode());
    // } else {
    // reqBean.setMode(MODE_NEW);
    // }
    // 共通項目を設定する
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // headerBean.setDeliveyTypeName(utilService.getAvailableDeliveryType(commodityInfo.getCheader().getShopCode()));
    headerBean.setStockStatusName(utilService.getStockStatusNames(commodityInfo.getCheader().getShopCode()));

    reqBean.setCommodityCode(header.getCommodityCode());
    reqBean.setShopCode(shopCode);
    reqBean.setHeader(headerBean);
    reqBean.setCommodityDiscountPrice(skuBean.getDiscountPrice());
    reqBean.setCommodityTmallDiscountPrice(skuBean.getTmallDiscountPrice());
    reqBean.setSku(skuBean);

    // 商品画像、サムネイル画像、代表SKU画像のコード値を設定する
    List<CodeAttribute> imageList = new ArrayList<CodeAttribute>();
    // 10.1.2 10071 修正 ここから
    // imageList.add(new NameValue("商品画像(PC・携帯共通)", reqBean.getCommodityCode() +
    // "/" + UploadContents.COMMODITY_IMAGE));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.0"), reqBean
        .getCommodityCode()
        + "/" + UploadContents.COMMODITY_IMAGE));
    // 10.1.2 10071 修正 ここまで
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.1"), reqBean
        .getCommodityCode()
        + "/" + UploadContents.COMMODITY_THUMBNAIL_PC));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.2"), reqBean
        .getCommodityCode()
        + "/" + UploadContents.COMMODITY_THUMBNAIL_MOBILE));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.3"), reqBean.getSku()
        .getRepresentSkuCode()
        + "/" + UploadContents.SKU_IMAGE_PC));
    imageList.add(new NameValue(Messages.getString("web.action.back.catalog.CommodityEditSelectAction.4"), reqBean.getSku()
        .getRepresentSkuCode()
        + "/" + UploadContents.SKU_IMAGE_MOBILE));
    reqBean.setImageList(imageList);
    reqBean.setMode(MODE_UPDATE);
    // setNextUrl("/app/catalog/tmall_commodity_list/");

    reqBean.setShowMode("edit");
    
    // 20150120 hdh add start
    //特定用户有权限编辑商品有效期
    reqBean.setShowShelfLifeReadOnly(true);
    WebShopSpecialConfig spciConfig = DIContainer.getWebShopSpecialConfig();
    if(spciConfig.getShelfLifeUserList()!=null && spciConfig.getShelfLifeUserList().size() > 0){
      for(String userId:spciConfig.getShelfLifeUserList()){
        if(StringUtil.isNull(userId)){
          continue;
        }
        if(userId.equals(getLoginInfo().getLoginId())){
          reqBean.setShowShelfLifeReadOnly(false);
          break;
        }
      }
    }
    // 20150121 hdh add end
    
    // 遷移先Beanを設定する
    setRequestBean(reqBean);

    // 遷移元情報をセッションに保存
    //DisplayTransition.add(getBean(), "/app/catalog/tmall_commodity_list/search_back", getSessionContainer());
    //DisplayTransition.add(getBean(), "/app/catalog/commodity_constitute/register/" + reqBean.getCommodityCode(), getSessionContainer());
    logger.debug("nextUrl: " + getNextUrl());
    return BackActionResult.RESULT_SUCCESS;
  }

  TmallCategoryDao dao = DIContainer.getDao(TmallCategoryDao.class);

  public void getFullCategoryName(TmallCategory category, StringBuilder sb) {
    sb.append("&&&__" + category.getCategoryName());
    if (!"0".equals(category.getParentCode())) {
      TmallCategory list = dao.load(category.getParentCode());
      if (list != null) {
        TmallCategory cate = list;
        getFullCategoryName(cate, sb);
      } else {
        return;
      }
    } else {
      return;
    }
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TmallCommodityEditBean reqBean = (TmallCommodityEditBean) getRequestBean();
    if (reqBean != null) {
      setDisplayControl(reqBean);
      setCompleteMessage();
    }
    setRequestBean(reqBean);

  }

  /**
   * 完了メッセージを設定する
   * 
   * @param complete
   * @param moduleName
   */
  private void setCompleteMessage() {

    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length >= 4) {
      complete = params[3];
    }
    if (complete.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
          .getString("web.action.back.catalog.CommodityEditSelectAction.5")));
    } else if (complete.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommodityEditSelectAction.5")));
    } else if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommodityEditSelectAction.6")));
    } else if (complete.equals(WebConstantCode.COMPLETE_UPLOAD)) {

      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      if (messageBean != null) {
        if (messageBean.getResult().equals(ResultType.SUCCESS)) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPLOAD_COMPLETE, Messages
              .getString("web.action.back.catalog.CommodityEditSelectAction.6")));
        } else {
          addErrorMessage(WebMessage.get(ActionErrorMessage.UPLOAD_FAILED, Messages
              .getString("web.action.back.catalog.CommodityEditSelectAction.6")));
        }

        for (UploadResult ur : messageBean.getUploadDetailList()) {
          for (String s : ur.getInformationMessage()) {
            addInformationMessage(s);
          }
          for (String s : ur.getWarningMessage()) {
            addWarningMessage(s);
          }
          for (String s : ur.getErrorMessage()) {
            addErrorMessage(s);
          }
        }
      }

    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditSelectAction.7");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012008";
  }
  
  public BigDecimal calculateGrossProfitRate(BigDecimal priceReadyForCheck, CatalogService catalogService, CCommodityDetail ccommodityDetail) { 
    
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(ccommodityDetail.getSkuCode());
    BigDecimal averageCost = new BigDecimal(0);
    averageCost = ccommodityDetail.getAverageCost();
    String taxClass = cCommodityDetailSearchResult.getRows().get(0).getTaxClass();
    BigDecimal taxClassB = new BigDecimal(taxClass);
    BigDecimal taxClassPercent = taxClassB.multiply(new BigDecimal(0.01));
    // 毛利率 = (售价-平均移动成本*(1+税率))/售价
    BigDecimal grossProfitRate = priceReadyForCheck.subtract(taxClassPercent.add(new BigDecimal(1)).multiply(averageCost)).divide(priceReadyForCheck, 2, RoundingMode.HALF_UP);
    return grossProfitRate;
  }
}
