package jp.co.sint.webshop.web.action.back.catalog;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.TmallPropertyValueDao;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryCondition;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean.CCommoditySkuDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommoditySkuUpdateAction extends TmallCommoditySkuBaseAction {

  BigDecimal ecGrossProfitRate = null, ecSpecialGrossProfitRate = null, tmallGrossProfitRate = null, jdGrossProfitRate = null;
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
    boolean isValid = true;

    if (getRequestParameter().getPathArgs().length <= 0) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    String updateSkuCode = this.getUpdateSkuCode();

    if (StringUtil.isNullOrEmpty(updateSkuCode)) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      return false;
    }

    CCommoditySkuDetailBean updateSku = null;
    for (CCommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode() != null) {
        if (detail.getSkuCode().equals(updateSkuCode)) {
          updateSku = detail;
        }
      }

      if (!StringUtil.isNullOrEmpty(detail.getDiscountPrice())) {
        if (Double.parseDouble(detail.getDiscountPrice()) > Double.parseDouble(detail.getUnitPrice())) {
          addErrorMessage("官网特价大于代表SKU单价");
          return false;
        }
      }
      
      if(!StringUtil.hasValue(detail.getTmallDiscountPrice())) {
        addErrorMessage("JD售价不能为空");
        return false;
      }
    }
    if (updateSku == null) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED, "SKU"));
      return false;
    }
    // 各価格と適用期間の相互チェック
    isValid = validatePeriod(updateSku);
    // 判断sku名称是否只有48个字节
    if (StringUtil.hasValue(updateSku.getSkuName())) {
      try {
        // 判断第24个字节是否为汉字
        String skuName = StringUtil.subStringByByte(updateSku.getSkuName(), 24);
        int skuNameSize = skuName.getBytes("GBK").length;
        // 如果是24的话，为汉字
        if (skuNameSize == 24) {
          if (updateSku.getSkuName().getBytes("GBK").length > 48) {
            addErrorMessage(Messages.getString("service.data.csv.CommodityDetailImportDataSource.34"));
            isValid &= false;
          }
        } else {
          if (updateSku.getSkuName().getBytes("GBK").length > 47) {
            addErrorMessage(Messages.getString("service.data.csv.CommodityDetailImportDataSource.33"));
            isValid &= false;
          }
        }
      } catch (UnsupportedEncodingException e) {
        // 字节转化失败
        addErrorMessage(Messages.getString("service.data.csv.CommodityDetailImportDataSource.35"));
        isValid &= false;
      }
    }

    return isValid;
  }

  // public static void main(String[] args) {
  // String str = "一12345678901234567890123456789012345678901234567";
  // System.out.println(str.equals(StringUtil.subStringByByte(str,48)));
  // }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    TmallCommoditySkuBean reqBean = getBean();
    CCommoditySkuDetailBean updateSku = null;
    for (CCommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode().equals(getUpdateSkuCode())) {
        updateSku = detail;
      }
    }


    /***********************************向commodity_price_change_history表插入信息*****************************************/
    String newEcDiscountPrice = "", newJdPrice = "", newEcPrice = "", newTmallPrice = "";

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(getUpdateSkuCode());
    
    // 如果价格变动了,则向该表插入变动信息

    // 如果此次更新更改了Ec特价
    if(StringUtil.hasValue(updateSku.getDiscountPrice())) {
      if(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
        // 如果原Ec特价不为null且新的Ec特价与表中的Ec特价不同
        if(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice().compareTo(new BigDecimal(updateSku.getDiscountPrice())) != 0) {
          newEcDiscountPrice = updateSku.getDiscountPrice();
        }
      } else {
        // 如果原Ec特价为null
        newEcDiscountPrice = updateSku.getDiscountPrice();
      }
    }
    // 如果此次更新更改了京东售价(前台的京东售价为后台的tmallDiscountPrice)
    if(StringUtil.hasValue(updateSku.getTmallDiscountPrice())) {
      if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
        // 如果原京东售价不为null且新的京东售价与表中的京东售价不同
        if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice().compareTo(new BigDecimal(updateSku.getTmallDiscountPrice())) != 0) {
          newJdPrice = updateSku.getTmallDiscountPrice();
        }
      } else {
        // 如果原京东售价为null
        newJdPrice = updateSku.getTmallDiscountPrice();
      }
    }
    
    // 如果此次更新更改了淘宝售价
    if(StringUtil.hasValue(updateSku.getTmallUnitPrice())) {
      if(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice() != null) {
        // 如果原淘宝售价不为null且新的京东售价与表中的京东售价不同
        if(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice().compareTo(new BigDecimal(updateSku.getTmallUnitPrice())) != 0) {
          newTmallPrice = updateSku.getTmallUnitPrice();
        }
      } else {
        // 如果原淘宝售价为null
        newTmallPrice = updateSku.getTmallUnitPrice();
      }
    }
    
    // 如果此次更新更改了EC售价
    if(StringUtil.hasValue(updateSku.getUnitPrice())) {
      if(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null) {
        // 如果原EC售价不为null且新的京东售价与表中的京东售价不同
        if(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice().compareTo(new BigDecimal(updateSku.getUnitPrice())) != 0) {
          newEcPrice = updateSku.getUnitPrice();
        }
      } else {
        // 如果原EC售价为null
         newEcPrice = updateSku.getUnitPrice();
      }
    }
    
    

    CommodityPriceChangeHistory commodityPriceChangeHistory = new CommodityPriceChangeHistory();
    ServiceResult serviceResult = null;
    SearchResult<CCommodityHeader> cCommodityHeaderSearchResult = catalogService.searchCCommodityHeader(updateSku.getCommodityCode());

    // 4个价格中至少1个变动了才insert
    if (StringUtil.hasValueAnyOf(newEcDiscountPrice, newJdPrice, newEcPrice, newTmallPrice)) {
      // 有平均计算成本且不是清仓商品，才计算毛利率并insert，否则不insert
      if (StringUtil.hasValue(updateSku.getAverageCost()) && NumUtil.isDecimal(updateSku.getAverageCost())) {
        if (cCommodityHeaderSearchResult.getRows().get(0).getClearCommodityType() != 1) {

          // 计算Ec售价的毛利率
          // 如果有Ec特价（且在特价期间内）的话，按Ec特价算毛利率，不检查Ec售价。
          if (StringUtil.hasValue(reqBean.getEdit().getDiscountPrice())
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
            ; // do nothing
          } else {
            if(StringUtil.hasValue(newEcPrice)) {
              ecGrossProfitRate = calculateGrossProfitRate(newEcPrice, catalogService, updateSku);
            } else if(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice() != null){
              ecGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getUnitPrice().toString(), catalogService, updateSku);
            } else {
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
              ecSpecialGrossProfitRate = calculateGrossProfitRate(newEcDiscountPrice, catalogService, updateSku);
            } else if (cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice() != null) {
              ecSpecialGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getDiscountPrice()
                  .toString(), catalogService, updateSku);
            } else {
              ecSpecialGrossProfitRate = null;
            }
          }

          // 计算Tmall售价的毛利率
          if(StringUtil.hasValue(newTmallPrice)) {
            tmallGrossProfitRate = calculateGrossProfitRate(newTmallPrice, catalogService, updateSku);
          }
          else if(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice() != null) {
            tmallGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getTmallUnitPrice().toString(), catalogService, updateSku);
          }
          else {
            tmallGrossProfitRate = null;
          }
          // 计算JD售价的毛利率
          if(StringUtil.hasValue(newJdPrice)) {
            jdGrossProfitRate = calculateGrossProfitRate(newJdPrice, catalogService, updateSku);
          }
          else if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
            jdGrossProfitRate = calculateGrossProfitRate(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice().toString(), catalogService, updateSku);
          }
          else {
            jdGrossProfitRate = null;
          }
          commodityPriceChangeHistory = setCommodityPriceChangeHistory(commodityPriceChangeHistory, reqBean, newEcDiscountPrice,
              newJdPrice, newEcPrice, newTmallPrice);
          serviceResult = catalogService.insertCommodityPriceChangeHistory(commodityPriceChangeHistory);
        }
      }
    }
    if (serviceResult != null && serviceResult.hasError()) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.warn("插入commodity_price_change_history表错误。");
      return BackActionResult.SERVICE_ERROR;
    }
    /**********************************向commodity_price_change_history表插入信息 end 2014/07/22***************************/
    
    
    
    // 更新用DTOの作成
    CCommodityDetail detail = convertBeanToDto(updateSku);
    
    
    if (!StringUtil.isNullOrEmpty(detail.getStandardDetail1Id())) {
      TmallPropertyValueDao hDao = DIContainer.getDao(TmallPropertyValueDao.class);
      TmallPropertyValue tpv = hDao.load(reqBean.getHeader().getTmallCategoryId().toString(), reqBean.getHeader().getStandard1Id(),
          detail.getStandardDetail1Id());
      if(!StringUtil.isNullOrEmpty(tpv.getValueName())){
        detail.setStandardDetail1Name(tpv.getValueName());
      }
      if(!StringUtil.isNullOrEmpty(tpv.getValueNameEn())){
        detail.setStandardDetail1NameEn(tpv.getValueNameEn());
      }
      if(!StringUtil.isNullOrEmpty(tpv.getValueNameJp())){
        detail.setStandardDetail1NameJp(tpv.getValueNameJp());
      }
    }
    
    if (!StringUtil.isNullOrEmpty(detail.getStandardDetail2Id())) {
      TmallPropertyValueDao hDao = DIContainer.getDao(TmallPropertyValueDao.class);
      TmallPropertyValue tpv = hDao.load(reqBean.getHeader().getTmallCategoryId().toString(), reqBean.getHeader().getStandard2Id(),
          detail.getStandardDetail2Id());
      if(!StringUtil.isNullOrEmpty(tpv.getValueName())){
        detail.setStandardDetail2Name(tpv.getValueName());
      }
      if(!StringUtil.isNullOrEmpty(tpv.getValueNameEn())){
        detail.setStandardDetail2NameEn(tpv.getValueNameEn());
      }
      if(!StringUtil.isNullOrEmpty(tpv.getValueNameJp())){
        detail.setStandardDetail2NameJp(tpv.getValueNameJp());
      }
    }

    Stock stock = new Stock();
    // 目前版本在更新sku时无需再更新库存 update by os014 2012-02-22
    // stock.setShopCode(getLoginInfo().getShopCode());
    // stock.setSkuCode(updateSku.getSkuCode());
    // stock.setCommodityCode(getBean().getParentCommodityCode());
    // stock.setStockThreshold(NumUtil.toLong(updateSku.getStockThreshold(),
    // null));
    // stock.setUpdatedUser(getLoginInfo().getLoginId());

    // 更新処理
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.updateCCommoditySku(detail, stock);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "SKU"));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CatalogServiceErrorContent.STANDARD_NAME_SET_ERROR)) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.FAULT_STANDARD_DETAIL_NAME_SET_ERROR));
          setRequestBean(getBean());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/catalog/tmall_commodity_sku/init/" + getBean().getParentCommodityCode() + "/" + getBean().getShopCode() + "/"
        + WebConstantCode.COMPLETE_UPDATE);
    setRequestBean(reqBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "商品明细编辑";
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
  
  private CommodityPriceChangeHistory setCommodityPriceChangeHistory(CommodityPriceChangeHistory commodityPriceChangeHistory, TmallCommoditySkuBean reqBean, String newEcDiscountPrice, String newJdPrice, String newEcPrice, String newTmallPrice) {

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = service.searchCCommodityDetail(getUpdateSkuCode());
    
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
    // TmallDiscountPrice前台显示为jd售价
    if(cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice() != null) {
        jdPrice = cCommodityDetailSearchResult.getRows().get(0).getTmallDiscountPrice().toString();
    }
    commodityPriceChangeHistory.setCommodityCode(getUpdateSkuCode());
    commodityPriceChangeHistory.setSubmitTime(DateUtil.getSysdate());
    commodityPriceChangeHistory.setResponsiblePerson(this.getLoginInfo().getName());
    if(StringUtil.hasValue(ecPrice)) {
      commodityPriceChangeHistory.setOldOfficialPrice(new BigDecimal(ecPrice));
    } else {
      commodityPriceChangeHistory.setOldOfficialPrice(null);
    }
    if(StringUtil.hasValue(newEcPrice)) {
      commodityPriceChangeHistory.setNewOfficialPrice(new BigDecimal(newEcPrice));
    } else {
      commodityPriceChangeHistory.setNewOfficialPrice(null);
    }
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
    if(StringUtil.hasValue(newTmallPrice)) {
      commodityPriceChangeHistory.setNewTmallPrice(new BigDecimal(newTmallPrice));
    } else {
      commodityPriceChangeHistory.setNewTmallPrice(null);
    }
    if(StringUtil.hasValue(jdPrice)) {
      commodityPriceChangeHistory.setOldJdPrice(new BigDecimal(jdPrice));
    } else {
      commodityPriceChangeHistory.setOldJdPrice(null);
    }
    if(StringUtil.hasValue(newJdPrice)) {
      commodityPriceChangeHistory.setNewJdPrice(new BigDecimal(newJdPrice));
    } else {
      commodityPriceChangeHistory.setNewJdPrice(null);
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
  
  public BigDecimal calculateGrossProfitRate(String priceReadyForCheck, CatalogService catalogService, CCommoditySkuDetailBean updateSku) {
    
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(updateSku.getCommodityCode());
    BigDecimal newPriceB = new BigDecimal(priceReadyForCheck);
    BigDecimal averageCost = new BigDecimal(0);
    // 如果新更新了“平均计算成本”，以新更新的成本为根据计算毛利率
    if(StringUtil.hasValue(updateSku.getAverageCost())) {
      averageCost = new BigDecimal(updateSku.getAverageCost());
    } else {
      averageCost = cCommodityDetailSearchResult.getRows().get(0).getAverageCost();
    }
    String taxClass = cCommodityDetailSearchResult.getRows().get(0).getTaxClass();
    BigDecimal taxClassB = new BigDecimal(taxClass);
    BigDecimal taxClassPercent = taxClassB.multiply(new BigDecimal(0.01));
    // 毛利率 = (售价-平均移动成本*（1+税率））/售价
    BigDecimal grossProfitRate = newPriceB.subtract(taxClassPercent.add(new BigDecimal(1)).multiply(averageCost)).divide(newPriceB, 2, RoundingMode.HALF_UP);
    return grossProfitRate;
  }
  
  
  
}
