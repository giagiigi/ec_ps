package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean.CCommoditySkuDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;

/**
 * U1040140:商品SKUのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class TmallCommoditySkuBaseAction extends WebBackAction<TmallCommoditySkuBean> {

  public static final String COMPLETE_FILE_DELETE = "fileDelete";

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public abstract boolean validate();

  public boolean validatePeriod(CCommoditySkuDetailBean sku) {
    boolean isValid = true;

    isValid = validateBean(sku);
    if(isValid == false) {
      return false;
    }
    
    for (CCommoditySkuDetailBean detail : getBean().getList()) {
      if (detail.getSkuCode() != null) {
        if (!detail.getSkuCode().equals(sku.getSkuCode())) {
          // 如果两个销售属性均不为空
          if(!StringUtil.isNullOrEmpty(detail.getStandardDetail1Id()) && !StringUtil.isNullOrEmpty(detail.getStandardDetail2Id())){
            if(detail.getStandardDetail1Id().equals(sku.getStandardDetail1Id()) && detail.getStandardDetail2Id().equals(sku.getStandardDetail2Id())){
              addErrorMessage("销售属性已存在，不可重复添加");
              isValid = false;
            }
          }
          // 如果只有StandardDetail1Id
          if(!StringUtil.isNullOrEmpty(detail.getStandardDetail1Id()) && StringUtil.isNullOrEmpty(detail.getStandardDetail2Id())){
            if(detail.getStandardDetail1Id().equals(sku.getStandardDetail1Id())){
              addErrorMessage("销售属性已存在，不可重复添加");
              isValid = false;
            }
          }
          // 如果只有StandardDetail2Id
          if(StringUtil.isNullOrEmpty(detail.getStandardDetail1Id()) && !StringUtil.isNullOrEmpty(detail.getStandardDetail2Id())){
            if(detail.getStandardDetail2Id().equals(sku.getStandardDetail2Id())){
              addErrorMessage("销售属性已存在，不可重复添加");
              isValid = false;
            }
          }
        }
      }
    }
    // 特別価格が入力された場合は、特別価格期間が設定されているかチェック
    if (StringUtil.hasValue(sku.getDiscountPrice())) {
      if (!StringUtil.hasValueAnyOf(getBean().getDiscountPriceStartDatetime(), getBean().getDiscountPriceEndDatetime())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuBaseAction.0"), Messages
            .getString("web.action.back.catalog.CommoditySkuBaseAction.1")));
        isValid = false;
      }
    }



    // 特別価格期間が設定されている場合は、特別価格の必須チェック
    if (StringUtil.hasValueAnyOf(getBean().getDiscountPriceStartDatetime(), getBean().getDiscountPriceEndDatetime())) {
      if (StringUtil.isNullOrEmpty(sku.getDiscountPrice())) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.SET_PERIOD_ERROR, Messages
            .getString("web.action.back.catalog.CommoditySkuBaseAction.1"), Messages
            .getString("web.action.back.catalog.CommoditySkuBaseAction.0")));
        isValid = false;
      }
    }


    
    /**
     * 如果定价标志为1（fixed_price_flag）官网和淘宝售价,
     *        官网和淘宝的特别价格必须等于希望售价（suggeste_price）
     * 
     *    
     */
    if (StringUtil.hasValue(sku.getFixedPriceFlag()) && "1".equals(sku.getFixedPriceFlag())) {
      if(StringUtil.hasValue(sku.getSuggestePrice())){
        if(StringUtil.hasValue(sku.getUnitPrice())){
          if (!sku.getUnitPrice().equals(sku.getSuggestePrice())){
            addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_EQULSE, Messages
                .getString("web.bean.back.catalog.ExportObjectList.101"), Messages
                .getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.6")));
            isValid = false;
          }
        }
        if(StringUtil.hasValue(sku.getTmallUnitPrice())){
          if(!sku.getTmallUnitPrice().equals(sku.getSuggestePrice())){
            addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_EQULSE, Messages
                .getString("web.bean.back.catalog.ExportObjectList.102"), Messages
                .getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.6")));
            isValid = false;
          }
        }
        if(StringUtil.hasValue(sku.getDiscountPrice())){
          if(!sku.getDiscountPrice().equals(sku.getSuggestePrice())){
            addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_EQULSE, Messages
                .getString("web.bean.back.catalog.ExportObjectList.103"), Messages
                .getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.6")));
            isValid = false;
          }
        }
        if(StringUtil.hasValue(sku.getTmallDiscountPrice())){
          if(!sku.getTmallDiscountPrice().equals(sku.getSuggestePrice())){
            addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_EQULSE, Messages
                .getString("web.bean.back.catalog.ExportObjectList.104"), Messages
                .getString("web.action.back.catalog.CommoditySkuPriceUpdateAction.6")));
            isValid = false;
          }
        }
      }
    }
    //如果最小单价（min_price）不为空 所有价格都必须大于最小单价    20141202 hdh 去掉最小单价验证
//    if (StringUtil.hasValue(sku.getMinPrice())) {
//      if (StringUtil.hasValue(sku.getUnitPrice())) {
//        if (!ValidatorUtil.lessThanOrEquals(Double.valueOf(sku.getMinPrice()), Double.valueOf(sku.getUnitPrice()))) {
//          addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_MORE, Messages
//              .getString("web.bean.back.catalog.ExportObjectList.101"), Messages
//              .getString("web.action.back.catalog.CCommodityEditBaseAction.2")));
//          isValid = false;
//        }
//      }
//      if (StringUtil.hasValue(sku.getTmallUnitPrice())) {
//        if (!ValidatorUtil.lessThanOrEquals(Double.valueOf(sku.getMinPrice()), Double.valueOf(sku.getTmallUnitPrice()))) {
//          addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_MORE, Messages
//              .getString("web.bean.back.catalog.ExportObjectList.102"), Messages
//              .getString("web.action.back.catalog.CCommodityEditBaseAction.2")));
//          isValid = false;
//        }
//      }
//      if (StringUtil.hasValue(sku.getDiscountPrice())) {
//        if (!ValidatorUtil.lessThanOrEquals(Double.valueOf(sku.getMinPrice()), Double.valueOf(sku.getDiscountPrice()))) {
//          addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_MORE, Messages
//              .getString("web.bean.back.catalog.ExportObjectList.103"), Messages
//              .getString("web.action.back.catalog.CCommodityEditBaseAction.2")));
//          isValid = false;
//        }
//      }
//      if (StringUtil.hasValue(sku.getTmallDiscountPrice())) {
//        if (!ValidatorUtil.lessThanOrEquals(Double.valueOf(sku.getMinPrice()), Double.valueOf(sku.getTmallDiscountPrice()))) {
//          addErrorMessage(WebMessage.get(CatalogErrorMessage.SKU_DATE_COMPARABLE_ERROR_MORE, Messages
//              .getString("web.bean.back.catalog.ExportObjectList.104"), Messages
//              .getString("web.action.back.catalog.CCommodityEditBaseAction.2")));
//          isValid = false;
//        }
//      }
//
//    }
    /**
     * 如果入り数（inner_quantity）,入り数単位(inner_quantity_unit)
     * ,WEB表示単価単位入り数(inner_unit_for_price) 中有一个值不为空，其它两个值也不能为空
     * 且WEB表示単価単位入り数 必须小于 入り数
     * 
     */
    if (StringUtil.hasValueAnyOf(sku.getInnerQuantity(), sku.getInnerQuantityUnit(), sku.getInnerUnitForPrice())) {
      if (!StringUtil.hasValueAllOf(sku.getInnerQuantity(), sku.getInnerQuantityUnit(), sku.getInnerUnitForPrice())) {
        addErrorMessage("如果内包装数，内包装数单位和价格计算单位其中一个设置了值那么其它两个都不能为空");
        isValid = false;
      }else{
        if(!ValidatorUtil.lessThanOrEquals(Double.valueOf(sku.getInnerUnitForPrice()), Double.valueOf(sku.getInnerQuantity()))){
          addErrorMessage("价格计算单位 必须小于 内包装数");
          isValid = false;
        }
      }
    }
    /**
     * 如果容量（volume）,容量単位(volume_unit)
     * ,WEB表示単価単位容量(volume_unit_for_price) 中有一个值不为空，其它两个值也不能为空
     * 且WEB表示単価単位容量 必须小于 容量
     */
    if (StringUtil.hasValueAnyOf(sku.getVolume(), sku.getVolumeUnit(), sku.getVolumeUnitForPrice())) {
      if (!StringUtil.hasValueAllOf(sku.getVolume(), sku.getVolumeUnit(), sku.getVolumeUnitForPrice())) {
        addErrorMessage("如果容量，容量单位和价格计算单位容量其中一个设置了值那么其它两个都不能为空");
        isValid = false;
      }else{
        if(!ValidatorUtil.lessThanOrEquals(Double.valueOf(sku.getVolumeUnitForPrice()), Double.valueOf(sku.getVolume()))){
          addErrorMessage("价格计算单位容量 必须小于 容量");
          isValid = false;
        }
      }
    }
    return isValid;
  }

  protected List<CCommoditySkuDetailBean> convertList(List<CCommodityDetail> skuList) {
    List<CCommoditySkuDetailBean> list = new ArrayList<CCommoditySkuDetailBean>();
    for (CCommodityDetail detail : skuList) {
      CCommoditySkuDetailBean bean = convertBean(detail);
      list.add(bean);
    }
    return list;
  }

  /**
   * 查找beanList中的代表sku对象
   * @param repSkucode 代表skuCode
   * @return CCommoditySkuDetailBean
   */
  protected CCommoditySkuDetailBean getRepresentSkuFromList(String repSkucode){
    if(getBean().getList()==null||getBean().getList().size()==0){
      return null;
    }else{
      for(CCommoditySkuDetailBean bean:getBean().getList()){
        if(repSkucode.equals(bean.getSkuCode())){
          return bean;
        }
      }
    }
    return null;
  }
  protected CCommoditySkuDetailBean convertBean(CCommodityDetail detail) {
    CCommoditySkuDetailBean bean = new CCommoditySkuDetailBean();
    bean.setCommodityCode(detail.getCommodityCode());
    bean.setShopCode(detail.getShopCode());
    bean.setDiscountPrice(NumUtil.toString(detail.getDiscountPrice()));
    bean.setMaximumOrder(NumUtil.toString(detail.getMaximumOrder()));
    bean.setMinimumOrder(NumUtil.toString(detail.getMinimumOrder()));
    bean.setSuggestePrice(NumUtil.toString(detail.getSuggestePrice()));
    bean.setOrderMultiple(NumUtil.toString(detail.getOrderMultiple()));
    bean.setPurchasePrice(NumUtil.toString(detail.getPurchasePrice()));
    bean.setTmallDiscountPrice(NumUtil.toString(detail.getTmallDiscountPrice()));
    bean.setTmallUnitPrice(NumUtil.toString(detail.getTmallUnitPrice()));
    bean.setTmallSkuId(detail.getTmallSkuId());
    bean.setUnitPrice(NumUtil.toString(detail.getUnitPrice()));
    bean.setWeight(NumUtil.toString(detail.getWeight()));
    bean.setStandardDetail1Id(replaceNull(detail.getStandardDetail1Id()));
    bean.setStandardDetail1Name(replaceNull(detail.getStandardDetail1Name()));
    bean.setStandardDetail2Id(replaceNull(detail.getStandardDetail2Id()));
    bean.setStandardDetail2Name(replaceNull(detail.getStandardDetail2Name()));
    bean.setSkuCode(replaceNull(detail.getSkuCode()));
    bean.setSkuName(replaceNull(detail.getSkuName()));
    bean.setStockWarning(NumUtil.toString(detail.getStockWarning()));
    bean.setVolume(NumUtil.toString(detail.getVolume()));
    bean.setVolumeUnit(detail.getVolumeUnit());
    
    bean.setFixedPriceFlag(NumUtil.toString(detail.getFixedPriceFlag()));
    bean.setHeight(NumUtil.toString(detail.getHeight()));
    bean.setWidth(NumUtil.toString(detail.getWidth()));
    bean.setLength(NumUtil.toString(detail.getLength()));
    bean.setMinPrice(NumUtil.toString(detail.getMinPrice()));
    bean.setInnerQuantity(NumUtil.toString(detail.getInnerQuantity()));
    bean.setInnerQuantityUnit(detail.getInnerQuantityUnit());
    bean.setInnerUnitForPrice(NumUtil.toString(detail.getInnerUnitForPrice()));
    bean.setVolumeUnitForPrice(NumUtil.toString(detail.getVolumeUnitForPrice()));
    bean.setTmallUseFlg(NumUtil.toString(detail.getTmallUseFlg()));
    //2014/4/28 京东WBS对应 ob_李 add start
    bean.setJdUseFlg(NumUtil.toString(detail.getJdUseFlg()));
    //2014/4/28 京东WBS对应 ob_李 add end
    bean.setUseFlg(NumUtil.toString(detail.getUseFlg()));
    bean.setTaxClass(replaceNull(detail.getTaxClass()));
    bean.setUnitBox(NumUtil.toString(detail.getUnitBox()));
    bean.setAverageCost(NumUtil.toString(detail.getAverageCost()));
    return bean;
  }
  
  protected CCommodityDetail convertBeanToDto(CCommoditySkuDetailBean bean){
    CCommodityDetail detail = new CCommodityDetail();
    detail.setCommodityCode(bean.getCommodityCode());
    detail.setShopCode(bean.getShopCode());
    detail.setDiscountPrice(parseBigDecimalFromString(bean.getDiscountPrice()));
    detail.setMaximumOrder(parseLongFromString(bean.getMaximumOrder()));
    detail.setMinimumOrder(parseLongFromString(bean.getMinimumOrder()));
    detail.setSuggestePrice(NumUtil.parse(bean.getSuggestePrice()));
    detail.setOrderMultiple(parseLongFromString(bean.getOrderMultiple()));
    detail.setPurchasePrice(NumUtil.parse(bean.getPurchasePrice()));
    detail.setTmallDiscountPrice(parseBigDecimalFromString(bean.getTmallDiscountPrice()));
    detail.setTmallUnitPrice(NumUtil.parse(bean.getTmallUnitPrice()));
    detail.setUnitPrice(NumUtil.parse(bean.getUnitPrice()));
    detail.setWeight(parseBigDecimalFromString(bean.getWeight()));
    detail.setStandardDetail1Id(replaceNull(bean.getStandardDetail1Id()));
    detail.setStandardDetail1Name(replaceNull(bean.getStandardDetail1Name()));
    detail.setStandardDetail2Id(replaceNull(bean.getStandardDetail2Id()));
    detail.setStandardDetail2Name(replaceNull(bean.getStandardDetail2Name()));
    detail.setSkuCode(replaceNull(bean.getSkuCode()));
    detail.setSkuName(replaceNull(bean.getSkuName()));
    detail.setStockWarning(parseLongFromString(bean.getStockWarning()));
    detail.setVolume(parseBigDecimalFromString(bean.getVolume()));
    detail.setVolumeUnit(replaceNull(bean.getVolumeUnit()));
    detail.setTmallSkuId(bean.getTmallSkuId());
    detail.setFixedPriceFlag(parseLongFromString(bean.getFixedPriceFlag()));
    
    detail.setHeight(parseBigDecimalFromString(bean.getHeight()));
    detail.setWidth(parseBigDecimalFromString(bean.getWidth()));
    detail.setLength(parseBigDecimalFromString(bean.getLength()));
    detail.setMinPrice(parseBigDecimalFromString(bean.getMinPrice()));
    detail.setInnerQuantity(parseLongFromString(bean.getInnerQuantity()));
    detail.setInnerQuantityUnit(replaceNull(bean.getInnerQuantityUnit()));
    detail.setInnerUnitForPrice(parseLongFromString(bean.getInnerUnitForPrice()));
    detail.setVolumeUnitForPrice(parseBigDecimalFromString(bean.getVolumeUnitForPrice()));
    detail.setTmallUseFlg(parseLongFromString(bean.getTmallUseFlg()));
    //2014/4/28 京东WBS对应 ob_李 add start
    detail.setJdUseFlg(parseLongFromString(bean.getJdUseFlg()));
    //2014/4/28 京东WBS对应 ob_李 add end
    detail.setUseFlg(parseLongFromString(bean.getUseFlg()));
    detail.setTaxClass(bean.getTaxClass());
    detail.setUnitBox(parseLongFromString(bean.getUnitBox()));
    detail.setAverageCost(parseBigDecimalFromString(bean.getAverageCost()));
    return detail;
  }
  protected String replaceNull(String value) {
    if ("".equals(value)) {
      return null;
    }
    return value;
  }

  /**
   * 获取url参数skuCode
   * @return
   */
  protected String getUpdateSkuCode() {

    String[] params = getRequestParameter().getPathArgs();
    String skuCode = "";
    if (params.length > 0) {
      skuCode = params[0];
    }

    return skuCode;
  }
  protected Long parseLongFromString(String value){
    if(StringUtil.hasValue(value)){
      return NumUtil.toLong(value);
    }else{
      return null;
    }
  }
  protected BigDecimal parseBigDecimalFromString(String value){
    if(StringUtil.hasValue(value)){
      return NumUtil.parse(value);
    }else{
      return null;
    }
  }
}
