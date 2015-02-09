package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.dao.TmallPropertyValueDao;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.TmallProperty;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean.CCommoditySkuDetailBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommoditySkuInitAction extends TmallCommoditySkuBaseAction {

  private static final String UNIT_PRICE = "unit";

  private static final String DISCOUNT_PRICE = "discount";

  private static final String SUGGESTE_PRICE = "suggeste";

  private static final String PURCHASE_PRICE = "purchase";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }


  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] params = getRequestParameter().getPathArgs();
    // 从商品编辑页面点击按钮直接进入
    if (params.length >= 2) {
      
      // 更新sku后，刷新页面返回InitAction后走这个。如果毛利率小于0画面报错
      if (params.length == 3 && params[2] != null && params[2].equals("update")) {
        
        /***********************************check毛利率，如果修改价格导致毛利率<0则报错（售价-（平均移动成本*（1+税率））/售价）**********************************/
        String updateSkuCode = this.getUpdateSkuCode();
        CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
        if (StringUtil.isNullOrEmpty(updateSkuCode)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
          return false;
        }

        SearchResult<CCommodityDetail> ccommodityDetailSearchResult = catalogService.searchCCommodityDetail(updateSkuCode);
        
        CCommodityDetail ccommodityDetail = ccommodityDetailSearchResult.getRows().get(0);
        
        BigDecimal newEcDiscountPriceReadyForCheck = ccommodityDetail.getDiscountPrice();
        // 后台的tmall特价就是前台的jd售价
        BigDecimal newJdPriceReadyForCheck = ccommodityDetail.getTmallDiscountPrice();
        BigDecimal newEcPricePriceReadyForCheck = ccommodityDetail.getUnitPrice();
        BigDecimal newTmallPriceReadyForCheck = ccommodityDetail.getTmallUnitPrice();
        SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(ccommodityDetail.getSkuCode());
        SearchResult<CCommodityHeader> cCommodityHeaderSearchResult = catalogService.searchCCommodityHeader(ccommodityDetail.getSkuCode());

        // 如果此商品新更新的“平均计算成本”的值不为空，才check毛利率
        // 分2种情况，如果新更新的“平均计算成本”的值为空，说明用户不想check毛利率了，自然不用检查；如果不为空，则计算毛利率用新更新的成本，旧的在数据库中存的成本是否为空不重要了。
        if (ccommodityDetail.getAverageCost() != null) {
          // 不是清仓商品，才check毛利率。
          if (cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1) {
            // check Ec售价的毛利率
            // 如果有Ec特价（且在特价期间内）的话，按Ec特价算毛利率，不check Ec售价。
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
              ; // do nothing
            } else {
              if (newEcPricePriceReadyForCheck != null) {
                BigDecimal ecGrossProfitRate = calculateGrossProfitRate(newEcPricePriceReadyForCheck, catalogService, ccommodityDetail);
                // 毛利率小于0，报错
                if (ecGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                  addErrorMessage("EC售价的毛利率为:" + ecGrossProfitRate.toString() + "，小于0");
                }
              }
            }
            
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
                  BigDecimal ecSpecialGrossProfitRate = calculateGrossProfitRate(newEcDiscountPriceReadyForCheck, catalogService, ccommodityDetail);
                  // 毛利率小于0，报错
                  if (ecSpecialGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                    addErrorMessage("EC特价的毛利率为:" + ecSpecialGrossProfitRate.toString() + "，小于0");
                  }
                }
              }
            
            // check Tmall售价的毛利率
            if (newTmallPriceReadyForCheck != null) {
              BigDecimal TmallGrossProfitRate = calculateGrossProfitRate(newTmallPriceReadyForCheck, catalogService, ccommodityDetail);
              // 毛利率小于0，报错
              if (TmallGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                addErrorMessage("Tmall售价的毛利率为:" + TmallGrossProfitRate.toString() + "，小于0");
              }
            }
            
            // check JD售价的毛利率
            if (newJdPriceReadyForCheck != null) {
              BigDecimal jdGrossProfitRate = calculateGrossProfitRate(newJdPriceReadyForCheck, catalogService, ccommodityDetail);
              // 毛利率小于0，报错
              if (jdGrossProfitRate.compareTo(new BigDecimal(0)) == -1) {
                addErrorMessage("JD售价的毛利率为:" + jdGrossProfitRate.toString() + "，小于0");
              }
            }
          }
        }
        
        /***********************************check毛利率，如果修改价格导致毛利率<0则报错（售价-（平均移动成本*（1+税率））/售价） end**********************************/
        
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
    TmallCommoditySkuBean reqBean = getBean();
    String args[] = getRequestParameter().getPathArgs();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String commodityCode = args[0];
    String shopCode = args[1];
    reqBean.setShopCode(shopCode);
    reqBean.setParentCommodityCode(commodityCode);
    // 查询header
    CCommodityHeaderDao dao = DIContainer.get("CCommodityHeaderDao");
    CCommodityHeader parent = dao.load(shopCode, commodityCode);
    reqBean.setHeader(parent);
    // 查询skulist
    List<CCommodityDetail> skuList = catalogService.getSkuListByCommodityCode(commodityCode, shopCode);

    if (parent == null || skuList.isEmpty()) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    // 共通項目の設定
    reqBean.setShopCode(parent.getShopCode());
    reqBean.setParentCommodityCode(parent.getCommodityCode());
    reqBean.setParentCommodityName(parent.getCommodityName());
    reqBean.setStandardDetail1Name(parent.getStandard1Name());
    reqBean.setStandardDetail2Name(parent.getStandard2Name());
    reqBean.setDiscountPriceStartDatetime(DateUtil.toDateTimeString(parent.getDiscountPriceStartDatetime()));
    reqBean.setDiscountPriceEndDatetime(DateUtil.toDateTimeString(parent.getDiscountPriceEndDatetime()));
    // reqBean.setReservationStartDatetime(DateUtil.toDateTimeString(parent.getReservationStartDatetime()));
    // reqBean.setReservationEndDatetime(DateUtil.toDateTimeString(parent.getReservationEndDatetime()));
    // reqBean.setSalePriceChangeDatetime(DateUtil.toDateTimeString(parent.getChangeUnitPriceDatetime()));
    // reqBean.setUnitPricePrimaryAll("");
    reqBean.setDiscountPriceAll("");
    reqBean.setSuggestePriceAll("");
    reqBean.setPurchasePriceAll("");
    reqBean.setUnitPriceAll("");
    // reqBean.setStockManagementType(NumUtil.toString(parent.getStockManagementType()));

    // 查询sku属性名称
    List<TmallProperty> propertys = catalogService.loadSkuPropertyByCategoryId(NumUtil.toString(parent.getTmallCategoryId()));
    

    /**
     * 设置界面销售属性显示方式
     * 如果商品所属类目只有一个销售属性，界面只可设置销售属性1
     *  如果没有销售属性，界面不能设置销售属性
     */
//    reqBean.setsta
    if(propertys == null || propertys.size() == 0){
      reqBean.setStandard1DisplayFlag(false);
      reqBean.setStandard2DisplayFlag(false);
    } else if(propertys.size() == 1){
      reqBean.setStandard1DisplayFlag(true);
      reqBean.setStandard2DisplayFlag(false);
    } else {
      reqBean.setStandard1DisplayFlag(true);
      reqBean.setStandard2DisplayFlag(true);
    }
    reqBean.setStandards(buildPropertyCodeAttribute(propertys));
    // 查询sku属性值1集合
    TmallPropertyValueDao valueDao = DIContainer.getDao(TmallPropertyValueDao.class);
    List<TmallPropertyValue> value1List = valueDao.loadByPropertyId(parent.getStandard1Id(), NumUtil.toString(parent
        .getTmallCategoryId()));
    reqBean.setStandard1ValueList(buildPropertyValueCodeAttribute(value1List));
    // 查询sku属性值2集合
    List<TmallPropertyValue> value2List = valueDao.loadByPropertyId(parent.getStandard2Id(), NumUtil.toString(parent
        .getTmallCategoryId()));
    reqBean.setStandard2ValueList(buildPropertyValueCodeAttribute(value2List));
    //税区分LIST
    reqBean.setTaxClassList(DIContainer.getTaxClassValue().getTaxClass());
    
    List<CCommoditySkuDetailBean> list = convertList(skuList);
    reqBean.setList(list);
    setCompleteMessage();
    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /** 
   * add by os014 2012-01-31
   * 根据属性名组装 界面属性下拉列表键值对
   * @param propertys 属性名集合
   * @return 属性下拉列表键值对
   */
  private List<CodeAttribute> buildPropertyCodeAttribute(List<TmallProperty> propertys) {
    if (propertys == null) {
      return null;
    }
    List<CodeAttribute> results = new ArrayList<CodeAttribute>();
    results.add(new NameValue("",""));
    for (TmallProperty property : propertys) {
      results.add(new NameValue(property.getPropertyName(), property.getPropertyId()));
    }
    return results;
  }
  /** 
   * add by os014 2012-01-31
   * 根据属性名组装 界面属性下拉列表键值对
   * @param propertys 属性名集合
   * @return 属性下拉列表键值对
   */
  private List<CodeAttribute> buildPropertyValueCodeAttribute(List<TmallPropertyValue> values) {
    if (values == null) {
      return null;
    }
    List<CodeAttribute> results = new ArrayList<CodeAttribute>();
    results.add(new NameValue(null, null));
    for (TmallPropertyValue value : values) {
      results.add(new NameValue(value.getValueName(), value.getValueId()));
    }
    return results;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TmallCommoditySkuBean bean = getBean();
    /**
     * 判断商品是否己同期 同期后：sku不能删除与更新
     */
    Date tmallSyncTime = null;//bean.getHeader().getSyncTimeTmall();
    if (tmallSyncTime == null) {
      bean.setDisplayUpdateButton(true);
      bean.setDisplayDeleteButton(true);
      bean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      bean.setDisplayUpdateButton(false);
      bean.setDisplayDeleteButton(false);
      bean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
    }
    
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.catalog.TmallCommoditySkuAction.0");
    // return
    // Messages.getString("web.action.back.catalog.CommodityEditConfirmAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104024003";
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert 更新完了時：update 削除完了時：delete 商品画像アップロード時 : upload <BR>
   * 
   * @param complete
   *          処理完了パラメータ
   */
  private void setCompleteMessage() {

    String[] params = getRequestParameter().getPathArgs();
    String complete = "";
    if (params.length >= 3) {
      complete = params[2];
    } else {
      return;
    }

    if (WebConstantCode.COMPLETE_INSERT.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "SKU"));
    } else if (WebConstantCode.COMPLETE_UPDATE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "SKU"));
    } else if (WebConstantCode.COMPLETE_DELETE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "SKU"));
    } else if (COMPLETE_FILE_DELETE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.6")));
    } else if (WebConstantCode.COMPLETE_UPLOAD.equals(complete)) {
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      if (messageBean != null) {
        if (messageBean.getResult().equals(ResultType.SUCCESS)) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPLOAD_COMPLETE, Messages
              .getString("web.action.back.catalog.CommoditySkuInitAction.7")));
        } else {
          addErrorMessage(WebMessage.get(ActionErrorMessage.UPLOAD_FAILED, Messages
              .getString("web.action.back.catalog.CommoditySkuInitAction.7")));
        }

        List<UploadResult> resultList = messageBean.getUploadDetailList();
        for (UploadResult ur : resultList) {

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
    } else if (UNIT_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.17")));
    } else if (DISCOUNT_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.9")));
    } else if (SUGGESTE_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.15")));
    } else if (PURCHASE_PRICE.equals(complete)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommoditySkuInitAction.16")));
    }
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
