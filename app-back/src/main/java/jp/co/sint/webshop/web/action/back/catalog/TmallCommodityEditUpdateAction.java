package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;


import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.ClearCommodityType;
import jp.co.sint.webshop.data.domain.UsingFlg;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryCondition;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditSkuBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditUpdateAction extends TmallCommodityEditBaseAction {

  BigDecimal ecSpecialGrossProfitRate = null, jdGrossProfitRate = null, ecGrossProfitRate = null, tmallGrossProfitRate = null;
  
  public boolean validate() {
    boolean validation = true;
    return validation;
  }
  
  
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TmallCommodityEditBean reqBean = getBean();

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    // SKU情報の取得
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodityInfo = catalogService.getCCommodityInfo(shopCode, reqBean.getOldCommodityCode());

    //特价设定
    
    
    if (commodityInfo == null || commodityInfo.getCheader() == null || commodityInfo.getCdetail() == null
        || commodityInfo.getStock() == null) {
      setNextUrl("/app/catalog/tmall_commodity_list/init/nodata");
      return BackActionResult.RESULT_SUCCESS;
    }

    // DTOに入力値をセット
    //setCommodityData(commodityInfo, reqBean);


    
    /*********************************** 向commodity_price_change_history表插入信息 **********************************/
    // 插入价格历史表的动作，必须在更新c_commodity_detail之前，因为如果在之后的话就取不到原价格了
    String newEcDiscountPrice = "", newJdPrice = "";

    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(reqBean.getCommodityCode());
    SearchResult<CCommodityHeader> cCommodityHeaderSearchResult = catalogService.searchCCommodityHeader(reqBean.getCommodityCode());
    // 如果价格变动了,则向该表插入变动信息

    // 如果此次更新更改了Ec特价
    if (reqBean.getCommodityDiscountPrice() != null && reqBean.getCommodityDiscountPrice() != "") {
      if (cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
        // 如果原Ec特价不为null且新的Ec特价与表中的Ec特价不同
        if (cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice().compareTo(new BigDecimal(reqBean.getCommodityDiscountPrice())) != 0) {
          newEcDiscountPrice = reqBean.getCommodityDiscountPrice();
        }
      } else {
        // 如果原Ec特价为null
        newEcDiscountPrice = reqBean.getCommodityDiscountPrice();
      }
    }
    // 如果此次更新更改了京东售价(前台的京东售价为后台的tmallDiscountPrice)
    if (reqBean.getCommodityTmallDiscountPrice() != null && reqBean.getCommodityTmallDiscountPrice() != "") {
      if (cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
        // 如果原京东售价不为null且新的京东售价与表中的京东售价不同
        if (cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice().compareTo(new BigDecimal(reqBean.getCommodityTmallDiscountPrice())) != 0) {
          newJdPrice = reqBean.getCommodityTmallDiscountPrice();
        }
      } else {
        // 如果原京东售价为null
        newJdPrice = reqBean.getCommodityTmallDiscountPrice();
      }
    }

    CommodityPriceChangeHistory commodityPriceChangeHistory = new CommodityPriceChangeHistory();
    ServiceResult serviceResult = null;
    CCommodityDetail ccommodityDetail = cCommodityDetailSearchResult.getRows().get(0);
    if (StringUtil.hasValueAnyOf(newEcDiscountPrice, newJdPrice)) {
      // 有平均计算成本且不是清仓商品，才计算毛利率并insert，否则不insert
      if (ccommodityDetail.getAverageCost() != null) {
        // 这个页面bean里有清仓flg，所以不取数据库的过去数据，取页面上的最新的值
        if (Integer.parseInt(reqBean.getHeader().getClearCommodityTypeEc()) != 1) {

          // 计算Ec售价的毛利率
          if(StringUtil.hasValue(reqBean.getCommodityDiscountPrice())
              && ((cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()) 
              && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() != null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() == null 
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime().before(DateUtil.getSysdate()))
              || (cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceStartDatetime() == null
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime() != null 
                  && cCommodityHeaderSearchResult.getRows().get(0).getDiscountPriceEndDatetime().after(DateUtil.getSysdate())))) {
            ; // do nothing.
          } else {
            if(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null) {
              ecGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice().toString(), catalogService, reqBean);
            }
            else {
              ecGrossProfitRate = null;
            }
          }
          // 计算Ec特价的毛利率
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
            if (StringUtil.hasValue(newEcDiscountPrice)) {
              ecSpecialGrossProfitRate = calculateGrossProfitRate(newEcDiscountPrice, catalogService, reqBean);
            } else if(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
              ecSpecialGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice().toString(), catalogService, reqBean);
            } else {
              ecSpecialGrossProfitRate = null;
            }
          }
          
          // 计算Tmall售价的毛利率
          if(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice() != null) {
            tmallGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice().toString(), catalogService, reqBean);
          }
          else {
            tmallGrossProfitRate = null;
          }
          
          // 计算JD售价的毛利率
          if (StringUtil.hasValue(newJdPrice)) {
            jdGrossProfitRate = calculateGrossProfitRate(newJdPrice, catalogService, reqBean);
          } else if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
            jdGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice().toString(), catalogService, reqBean);
          } else {
            jdGrossProfitRate = null;
          }

          commodityPriceChangeHistory = setCommodityPriceChangeHistory(commodityPriceChangeHistory, reqBean, newEcDiscountPrice, newJdPrice);
          
          // 如果修改了价格，就插入价格变动表。
          if (StringUtil.hasValueAnyOf(newEcDiscountPrice, newJdPrice)) {
            serviceResult = catalogService.insertCommodityPriceChangeHistory(commodityPriceChangeHistory);
          }
        }
      }
    }
    if (serviceResult != null && serviceResult.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn("插入commodity_price_change_history表错误。");
      return BackActionResult.SERVICE_ERROR;
    }
    /********************************** 向commodity_price_change_history表插入信息 end 2014/07/22 ***************************/
    
    
    // 更新処理
    CCommodityHeader cheader = new CCommodityHeader();
    
    cheader = buildHeader(reqBean.getHeader());
    cheader.setShopCode(shopCode);
    List<TmallCommodityProperty> propertys = copyTmallCommodityProperty(reqBean.getCommodityPropertysList(),reqBean.getCommodityStandardPopList());
    if(cheader.getSaleStartDatetime()==null){
      cheader.setSaleStartDatetime(DateUtil.getMin());
    }
    if(cheader.getSaleEndDatetime() == null){
      cheader.setSaleEndDatetime(DateUtil.getMax());
    }
    //更新header
    ServiceResult result = catalogService.updateCcheader(cheader,reqBean.getCommodityDiscountPrice(),reqBean.getCommodityTmallDiscountPrice(),propertys,reqBean.getOldCommodityCode());
    if (result.hasError()) {
      setRequestBean(reqBean);
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
        }
      }
    
      return BackActionResult.SERVICE_ERROR;
    }
    
    if(cheader.getCommodityType() != null){
      if(cheader.getCommodityType() == 1L){
        Stock stock = new Stock();
        CCommodityDetail detail = new CCommodityDetail();
        TmallCommodityEditSkuBean sku = reqBean.getSku();
        detail.setShopCode(cheader.getShopCode());
        detail.setCommodityCode(cheader.getCommodityCode());
        detail.setSkuCode(cheader.getCommodityCode());
        detail.setMinimumOrder(sku.getMinimumOrder());
        detail.setMaximumOrder(sku.getMaximumOrder());
        detail.setOrderMultiple(sku.getOrderMultiple());
        detail.setStockWarning(sku.getStockWarning());
        detail.setWeight(sku.getWeight());
        detail.setSkuName(cheader.getCommodityName());
        detail.setPurchasePrice(new BigDecimal(0));
        detail.setUnitPrice(new BigDecimal(0));
        detail.setTaxClass("0");
        detail.setUseFlg(UsingFlg.VISIBLE.longValue());
        detail.setTmallUseFlg(UsingFlg.HIDDEN.longValue());
        ServiceResult resultSku = catalogService.updateCCommoditySku(detail, stock);
        if (resultSku.hasError()) {
          for (ServiceErrorContent error : resultSku.getServiceErrorList()) {
            if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
              addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "SKU"));
              setRequestBean(getBean());
              return BackActionResult.SERVICE_ERROR;
            } else if (error.equals(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR)) {
              addErrorMessage(WebMessage.get(CatalogErrorMessage.FAULT_STANDARD_DETAIL_NAME_SET_ERROR));
              setRequestBean(getBean());
              return BackActionResult.SERVICE_ERROR;
            }
          }
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

  
    /**
     * 更新商品属性 tmall_commodity_property
     * 
     */
    //获取属性与属性值集合
//    PropertyKeys keys = reqBean.getPropertyKeys();
//    ServiceResult updatePropertyResult = catalogService.updateCommodityPropertys(NumUtil.toString(cheader.getTmallCategoryId()),reqBean.getCommodityCode(), shopCode, keys);
//    if(updatePropertyResult.hasError()){
//      setRequestBean(reqBean);
//      for (ServiceErrorContent error : updatePropertyResult.getServiceErrorList()) {
//        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//              .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//        }
//      }
//      return BackActionResult.SERVICE_ERROR;
//    }
    
//    ServiceResult updateBrandPropertyResult = catalogService.updateTmallCommodityProperty(propertys);
//    if(updateBrandPropertyResult.hasError()){
//      setRequestBean(reqBean);
//      for (ServiceErrorContent error : updateBrandPropertyResult.getServiceErrorList()) {
//        if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//              .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//        }
//      }
//      return BackActionResult.SERVICE_ERROR;
//    }
    //更新品牌属性
    

    
    // 次画面のBeanを設定する
    setRequestBean(reqBean);

    // 完了パラメータを渡して、初期画面へ遷移する
    setNextUrl("/app/catalog/tmall_commodity_edit/select/" + getLoginInfo().getShopCode() + "/" + reqBean.getCommodityCode() + "/edit" + "/"
        + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditUpdateAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022009";
  }

  private CommodityPriceChangeHistory setCommodityPriceChangeHistory(CommodityPriceChangeHistory commodityPriceChangeHistory, TmallCommodityEditBean reqBean, String newEcDiscountPrice, String newJdPrice) {
   
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = service.searchCCommodityDetail(reqBean.getCommodityCode());
    
    String ecDiscountPrice = "", ecPrice = "", tmallPrice = "", jdPrice = ""; 
    if(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null) {
      ecPrice = cCommodityDetailSearchResult.getRows().get(0).getUnitPrice().toString();
    }
    if(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
      ecDiscountPrice = cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice().toString();
    }
    if(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice() != null) {
      tmallPrice = cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice().toString();
    }
    if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
      jdPrice = cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice().toString();
    }
    commodityPriceChangeHistory.setCommodityCode(reqBean.getCommodityCode());
    commodityPriceChangeHistory.setSubmitTime(DateUtil.getSysdate());
    commodityPriceChangeHistory.setResponsiblePerson(this.getLoginInfo().getName());
    if(StringUtil.hasValue(ecPrice)) {
      commodityPriceChangeHistory.setOldOfficialPrice(new BigDecimal(ecPrice));
    } else {
      commodityPriceChangeHistory.setOldOfficialPrice(null);
    }
    commodityPriceChangeHistory.setNewOfficialPrice(null);
    if(StringUtil.hasValue(ecDiscountPrice)) {
      commodityPriceChangeHistory.setOldOfficialSpecialPrice(new BigDecimal(ecDiscountPrice));
    } else {
      commodityPriceChangeHistory.setOldOfficialSpecialPrice(null);
    }
    if(StringUtil.hasValue(newEcDiscountPrice)) {
      commodityPriceChangeHistory.setNewOfficialSpecialPrice(new BigDecimal(newEcDiscountPrice));
    } else {
      commodityPriceChangeHistory.setNewOfficialSpecialPrice(null);
    }
    if(StringUtil.hasValue(tmallPrice)) {
      commodityPriceChangeHistory.setOldTmallPrice(new BigDecimal(tmallPrice));
    } else {
      commodityPriceChangeHistory.setOldTmallPrice(null);
    }
    commodityPriceChangeHistory.setNewTmallPrice(null);
    if(StringUtil.hasValue(jdPrice)) {
      commodityPriceChangeHistory.setOldJdPrice(new BigDecimal(jdPrice));
    } else {
      commodityPriceChangeHistory.setOldJdPrice(null);
    }
    if(StringUtil.hasValue(newJdPrice)) {
      commodityPriceChangeHistory.setNewJdPrice(new BigDecimal(newJdPrice));
    }
    commodityPriceChangeHistory.setOrmRowid(DatabaseUtil.generateSequence(SequenceType.COMMODITY_PRICE_CHANGE_HISTORY_SEQ));
    commodityPriceChangeHistory.setCreatedUser(this.getLoginInfo().getRecordingFormat());
    commodityPriceChangeHistory.setCreatedDatetime(DateUtil.getSysdate());
    commodityPriceChangeHistory.setUpdatedUser(this.getLoginInfo().getRecordingFormat());
    commodityPriceChangeHistory.setUpdatedDatetime(DateUtil.getSysdate());
    // 0为未审核，1为已审核
    commodityPriceChangeHistory.setReviewOrNotFlg(new Long(0));
    commodityPriceChangeHistory.setEcProfitMargin(ecGrossProfitRate);
    commodityPriceChangeHistory.setEcSpecialProfitMargin(ecSpecialGrossProfitRate);
    commodityPriceChangeHistory.setTmallProfitMargin(tmallGrossProfitRate);
    commodityPriceChangeHistory.setJdProfitMargin(jdGrossProfitRate);
    return commodityPriceChangeHistory;
    
  }
  

  public BigDecimal calculateGrossProfitRate(String priceReadyForCheck, CatalogService catalogService, TmallCommodityEditBean reqBean) {
    
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(reqBean.getCommodityCode());
    BigDecimal newPriceB = new BigDecimal(priceReadyForCheck);
    BigDecimal averageCost = cCommodityDetailSearchResult.getRows().get(0).getAverageCost();
    String taxClass = cCommodityDetailSearchResult.getRows().get(0).getTaxClass();
    BigDecimal taxClassB = new BigDecimal(taxClass);
    BigDecimal taxClassPercent = taxClassB.multiply(new BigDecimal(0.01));
    // 毛利率 = 售价-平均移动成本*（1+税率））/售价
    BigDecimal grossProfitRate = newPriceB.subtract(taxClassPercent.add(new BigDecimal(1)).multiply(averageCost)).divide(newPriceB, 2, RoundingMode.HALF_UP);
    return grossProfitRate;
  }
  
}
