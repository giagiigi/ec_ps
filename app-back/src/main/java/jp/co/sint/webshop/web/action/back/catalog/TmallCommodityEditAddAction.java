package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityExt;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CategoryAttributeValue;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.data.dto.JdStockAllocation;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.TmallStockAllocation;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.RelatedCategory;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditHeaderBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean.TmallCommodityEditSkuBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class TmallCommodityEditAddAction extends TmallCommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

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
    TmallCommodityEditBean reqBean = getBean();
    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }
    // SKU情報の取得
    CCommodityHeader cheader = new CCommodityHeader();
    CCommodityDetail detail = new CCommodityDetail();
    TmallCommodityEditSkuBean sku = reqBean.getSku();
    TmallCommodityEditHeaderBean head = reqBean.getHeader();
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    String originalCommodityCode = head.getOriginalCommodityCode();
    if(StringUtil.hasValue(originalCommodityCode)){
      CommodityInfo cInfo = catalogService.getCCommodityInfo(shopCode, originalCommodityCode);
      cheader = cInfo.getCheader();
      detail = cInfo.getCdetail();
      
      //c_commodity_header表数据封装
      cheader.setCommodityCode(head.getCommodityCode());
      cheader.setRepresentSkuCode(head.getCommodityCode());
      cheader.setCommodityName(head.getCommodityName());
      cheader.setCommodityNameJp(head.getCommodityNameJp());
      cheader.setCommodityNameEn(head.getCommodityNameEn());
      cheader.setOriginalCommodityCode(originalCommodityCode);
      cheader.setCombinationAmount(NumUtil.toLong(head.getCombinationAmount()));
      cheader.setSaleStartDatetime(DateUtil.fromString(head.getSaleStartDatetime(),true)==null?DateUtil.getMin():DateUtil.fromString(head.getSaleStartDatetime(),true));
      cheader.setSaleEndDatetime(DateUtil.fromString(head.getSaleEndDatetime(),true)==null?DateUtil.getMax():DateUtil.fromString(head.getSaleEndDatetime(),true));
      cheader.setDiscountPriceStartDatetime(DateUtil.fromString(head.getDiscountPriceStartDatetime(),true));
      cheader.setDiscountPriceEndDatetime(DateUtil.fromString(head.getDiscountPriceEndDatetime(),true));
      cheader.setSaleFlgEc(NumUtil.toLong(head.getSaleFlgEc()));
      cheader.setSyncFlagEc(1L);
      cheader.setSyncFlagTmall(1L);
      cheader.setExportFlagErp(0L);
      cheader.setExportFlagWms(0L);
      cheader.setTmallCommodityId(null);
      cheader.setJdCommodityId(null);
      cheader.setSyncFlagJd(1L);
      cheader.setSyncUserJd(null);
      cheader.setSyncTimeJd(null);
      cheader.setOrmRowid(null);
      cheader.setCreatedUser(null);
      cheader.setCreatedDatetime(null);
      cheader.setUpdatedUser(null);
      cheader.setUpdatedDatetime(null);
      cheader.setKeywordCn1(null);
      cheader.setKeywordCn2(null);
      cheader.setKeywordEn1(null);
      cheader.setKeywordEn2(null);
      cheader.setKeywordJp1(null);
      cheader.setKeywordJp2(null);
      if(StringUtil.hasValue(sku.getUnitPrice())){
        cheader.setRepresentSkuUnitPrice(NumUtil.parse(sku.getUnitPrice()));
      }else{
        cheader.setRepresentSkuUnitPrice(null);
      }
      if(StringUtil.hasValue(sku.getTmallUnitPrice())){
        cheader.setTmallRepresentSkuPrice(NumUtil.parse(sku.getTmallUnitPrice()));
      }else{
        cheader.setTmallRepresentSkuPrice(null);
      }
      //detail表数据封装
      BigDecimal weigth=BigDecimalUtil.multiply(detail.getWeight(), cheader.getCombinationAmount());
      detail.setWeight(weigth);
      detail.setCommodityCode(cheader.getCommodityCode());
      detail.setSkuCode(cheader.getCommodityCode());
      detail.setSkuName(cheader.getCommodityName());
      detail.setInnerQuantity(detail.getInnerQuantity()*cheader.getCombinationAmount());
      detail.setOrmRowid(null);
      detail.setCreatedUser(null);
      detail.setCreatedDatetime(null);
      detail.setUpdatedUser(null);
      detail.setUpdatedDatetime(null);
      if(StringUtil.hasValue(sku.getUnitPrice())){
        detail.setUnitPrice(NumUtil.parse(sku.getUnitPrice()));
      }else{
        detail.setUnitPrice(null);
      }
      //20141202 hdh modify 
      if(StringUtil.hasValue(reqBean.getCommodityDiscountPrice())){
        detail.setDiscountPrice(NumUtil.parse(reqBean.getCommodityDiscountPrice()));
      }else{
        detail.setDiscountPrice(null);
      }
      if(StringUtil.hasValue(sku.getTmallUnitPrice())){
        detail.setTmallUnitPrice(NumUtil.parse(sku.getTmallUnitPrice()));
      }else{
        detail.setTmallUnitPrice(null);
      }
      if(StringUtil.hasValue(reqBean.getCommodityTmallDiscountPrice())){
        detail.setTmallDiscountPrice(NumUtil.parse(reqBean.getCommodityTmallDiscountPrice()));
      }else{
        detail.setTmallDiscountPrice(null);
      }
      
      // Stock表数据封装
      Stock stock = catalogService.getStock(shopCode, originalCommodityCode);
      stock.setCommodityCode(cheader.getCommodityCode());
      stock.setSkuCode(cheader.getCommodityCode());
      stock.setStockQuantity(0L);
      stock.setAllocatedQuantity(0L);
      stock.setAllocatedTmall(0L);
      stock.setReservedQuantity(0L);
      stock.setStockTotal(0L);
      stock.setStockTmall(0L);
      stock.setShareRatio(0L);
      stock.setOrmRowid(null);
      stock.setCreatedUser(null);
      stock.setCreatedDatetime(null);
      stock.setUpdatedUser(null);
      stock.setUpdatedDatetime(null);
      
      // c_commodity_ext表数据封装
      CCommodityExt cceBean = null;
      Long onStockFlg = catalogService.getCommodityExtOnStockFlg(originalCommodityCode);
      if (onStockFlg != null){
        cceBean = new CCommodityExt();
        cceBean.setShopCode(shopCode);
        cceBean.setCommodityCode(head.getCommodityCode());
        cceBean.setOnStockFlag(onStockFlg);
        cceBean.setOrmRowid(null);
        cceBean.setCreatedUser(null);
        cceBean.setCreatedDatetime(null);
        cceBean.setUpdatedUser(null);
        cceBean.setUpdatedDatetime(null);
      }
      
      // CategoryCommodity表数据封装
      List<CategoryCommodity> listy = catalogService.getCategoryCommodityList(shopCode, originalCommodityCode);
      CategoryCommodity commodity = null;
      if (listy.size() > 0) {
        commodity = listy.get(0);
        commodity.setCommodityCode(cheader.getCommodityCode());
        commodity.setOrmRowid(null);
        commodity.setCreatedUser(null);
        commodity.setCreatedDatetime(null);
        commodity.setUpdatedUser(null);
        commodity.setUpdatedDatetime(null);
      }

      
      //categoryAttributeValue表数据封装
      List<RelatedCategory> listr=catalogService.getCategoryAttributeValueList(shopCode, commodity.getCategoryCode(), originalCommodityCode);
      RelatedCategory related = null;
      CategoryAttributeValue buteValue = null;
      if(listr.size()>0){
        related=listr.get(0);
        buteValue=catalogService.getCategoryAttributeValue(related.getCategoryCode(), related.getCategoryAttributeNo(), related.getShopCode(), related.getCommodityCode());
        if (buteValue != null){
          buteValue.setCommodityCode(head.getCommodityCode());
          buteValue.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
          buteValue.setCreatedUser(null);
          buteValue.setCreatedDatetime(null);
          buteValue.setUpdatedUser(null);
          buteValue.setUpdatedDatetime(null);
        } 
      }
      
      //Tmall_Stock_Allocation表数据封装
      TmallStockAllocation tamllStockAllocation = new TmallStockAllocation();
      tamllStockAllocation.setShopCode(cheader.getShopCode());
      tamllStockAllocation.setSkuCode(cheader.getCommodityCode());
      tamllStockAllocation.setOriginalCommodityCode(originalCommodityCode);
      tamllStockAllocation.setCommodityCode(cheader.getCommodityCode());
      tamllStockAllocation.setStockQuantity(0L);
      tamllStockAllocation.setAllocatedQuantity(0L);
      tamllStockAllocation.setScaleValue(0L);
      
      // 2014/06/05 库存更新对应 ob_卢 add start
      JdStockAllocation jdStockAllocation = new JdStockAllocation();
      jdStockAllocation.setShopCode(cheader.getShopCode());
      jdStockAllocation.setSkuCode(cheader.getCommodityCode());
      jdStockAllocation.setOriginalCommodityCode(originalCommodityCode);
      jdStockAllocation.setCommodityCode(cheader.getCommodityCode());
      jdStockAllocation.setStockQuantity(0L);
      jdStockAllocation.setAllocatedQuantity(0L);
      jdStockAllocation.setScaleValue(0L);
      // 2014/06/05 库存更新对应 ob_卢 add end

      
      
      // 2014/06/05 库存更新对应 ob_卢 update start
      //ServiceResult result = catalogService.addNewCommodityObjects(cheader, detail, stock, cceBean, commodity, buteValue,tamllStockAllocation);
      ServiceResult result = catalogService.addNewCommodityObjects(cheader, detail, stock, cceBean, commodity, buteValue,tamllStockAllocation, jdStockAllocation);
      // 2014/06/05 库存更新对应 ob_卢 update end
      
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
    }
      
//      //新增
//      ServiceResult res1 = catalogService.addCcheader(cheader);
//      if (res1.hasError()) {
//        setRequestBean(reqBean);
//        for (ServiceErrorContent error : res1.getServiceErrorList()) {
//          if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//                .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//          }
//        }
//        return BackActionResult.SERVICE_ERROR;
//      }
//      ServiceResult res2 = catalogService.addCdetail(detail);
//      if (res2.hasError()) {
//        setRequestBean(reqBean);
//        for (ServiceErrorContent error : res2.getServiceErrorList()) {
//          if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//                .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//          }
//        }
//        return BackActionResult.SERVICE_ERROR;
//      }
//      ServiceResult res3 = catalogService.addStock(stock);
//      if (res3.hasError()) {
//        setRequestBean(reqBean);
//        for (ServiceErrorContent error : res3.getServiceErrorList()) {
//          if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//                .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//          }
//        }
//        return BackActionResult.SERVICE_ERROR;
//      }
//      ServiceResult res4 = catalogService.addCategoryCommodity(commodity);
//      if (res4.hasError()) {
//        setRequestBean(reqBean);
//        for (ServiceErrorContent error : res4.getServiceErrorList()) {
//          if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//                .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//          }
//        }
//        return BackActionResult.SERVICE_ERROR;
//      }
//      ServiceResult res5 = catalogService.insertCategoryAttributeValue(buteValue);
//      if (res5.hasError()) {
//        setRequestBean(reqBean);
//        for (ServiceErrorContent error : res5.getServiceErrorList()) {
//          if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//                .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//          }
//        }
//        return BackActionResult.SERVICE_ERROR;
//      }
//
//      // 增加
//      if (!reqBean.isPagePd()) {
//        ServiceResult res = catalogService.addTmallStockAllocation(tamllStockAllocation);
//        if (res.hasError()) {
//          setRequestBean(reqBean);
//          for (ServiceErrorContent error : res.getServiceErrorList()) {
//            if (CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR.equals(error)) {
//              addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
//                  .getString("web.action.back.catalog.TmallCommodityEditUpdateAction.0")));
//            }
//          }
//          return BackActionResult.SERVICE_ERROR;
//        }
//      }
//    }
    // 次画面のBeanを設定する
    setRequestBean(reqBean);
    setNextUrl("/app/catalog/commodity_constitute/register/" + originalCommodityCode);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditAddAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022009";
  }

}
