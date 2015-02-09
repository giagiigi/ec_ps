package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignEditBean;
import jp.co.sint.webshop.web.bean.back.communication.OptionalCampaignEditBean.CommoditiyDetailBean;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public abstract class OptionalCampaignEditBaseAction extends WebBackAction<OptionalCampaignEditBean> {

  @Override
  public abstract boolean authorize();

  @Override
  public abstract WebActionResult callService();

  @Override
  public abstract boolean validate();

  public void setOptionalCampaign(OptionalCampaignEditBean bean, OptionalCampaign campaign) {

    campaign.setCampaignCode(bean.getCampaignCode());
    campaign.setCampaignName(bean.getCampaignName());
    campaign.setCampaignNameEn(bean.getCampaignNameEn());
    campaign.setCampaignNameJp(bean.getCampaignNameJp());
    campaign.setRelatedCommodity(bean.getObjectCommodities());
    campaign.setOptionalNum(NumUtil.toLong(bean.getOptionalNum()));
    if (StringUtil.hasValue(bean.getOptionalPrice())) {
      campaign.setOptionalPrice(new BigDecimal(bean.getOptionalPrice()));
    }
    campaign.setOrderLimitNum(NumUtil.toLong(bean.getOrderLimitNum()));

    campaign.setCampaignStartDate(DateUtil.fromString(bean.getCampaignStartDate(), true));
    campaign.setCampaignEndDate(DateUtil.fromString(bean.getCampaignEndDate(), true));
    campaign.setShopCode(getLoginInfo().getShopCode());
    campaign.setCampaignStatus(1L);

  }

  public void setRelateCommodity(OptionalCampaignEditBean bean, OptionalCampaign campaign) {
    if (StringUtil.hasValue(campaign.getRelatedCommodity())) {
      String[] infos = campaign.getRelatedCommodity().split(",");
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      String shopCode = getLoginInfo().getShopCode();
      List<CommoditiyDetailBean> relatedList = new ArrayList<CommoditiyDetailBean>();
      for (String code : infos) {
        if (StringUtil.isNullOrEmpty(code)) {
          continue;
        } else {
          CommoditiyDetailBean detail = new CommoditiyDetailBean();
          CommodityHeader header = catalogService.getCommodityHeader(shopCode, code);
          detail.setCommodityCode(code);
          detail.setCommodityName(header.getCommodityName());
          relatedList.add(detail);
        }
      }
      bean.setCommoditiyDetailBeanList(relatedList);
    }
  }

  public void setOptionalCampaignEditBean(OptionalCampaignEditBean bean, OptionalCampaign campaign) {
    bean.setCampaignCode(campaign.getCampaignCode());
    bean.setCampaignName(campaign.getCampaignName());
    bean.setCampaignNameEn(campaign.getCampaignNameEn());
    bean.setCampaignNameJp(campaign.getCampaignNameJp());
    bean.setCampaignStartDate(DateUtil.toDateTimeString(campaign.getCampaignStartDate()));
    bean.setCampaignEndDate(DateUtil.toDateTimeString(campaign.getCampaignEndDate()));
    bean.setUpdateDatetime(campaign.getUpdatedDatetime());
    bean.setOptionalNum(NumUtil.toString(campaign.getOptionalNum()));
    bean.setOptionalPrice(NumUtil.toString(campaign.getOptionalPrice()));
    bean.setOrderLimitNum(NumUtil.toString(campaign.getOrderLimitNum()));
    
    bean.setObjectCommodities(campaign.getRelatedCommodity());

    bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
    bean.setDisplayLoginButtonFlg(false);
    bean.setDisplayUpdateButtonFlg(true);
    

  }

  // 判断是否已经存在
  public boolean isExists(OptionalCampaignEditBean bean, OptionalCampaign campaign) {
    String str = "," + bean.getCommoditiyDetailBean().getCommodityCode() + ",";
    if(StringUtil.isNullOrEmpty(campaign.getRelatedCommodity())){
      return false;
    }
    return campaign.getRelatedCommodity().indexOf(str) > -1;
  }

  // 验证添加的信息是否是关联商品
  public boolean isRelated(OptionalCampaignEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean
        .getCommoditiyDetailBean().getCommodityCode(), true, false);
    if (commodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 验证添加的信息是否在商品表中存在
  public boolean isCommodity(OptionalCampaignEditBean bean, boolean relatedFlg) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = null;

    if (relatedFlg) {
      commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean.getCommoditiyDetailBean()
          .getCommodityCode(), true, false);
    }

    if (commodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  // 折扣券的关联商品登录时，套餐商品时不可登录
  public boolean isSetCommodity(OptionalCampaignEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader commodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean
        .getCommoditiyDetailBean().getCommodityCode(), true, false);
    if (commodityHeader != null && !SetCommodityFlg.OBJECTIN.longValue().equals(commodityHeader.getSetCommodityFlg())) {
      flag = false;
    } else {
      flag = true;
    }
    return flag;
  }

  // 验证添加的信息是否是赠送商品
  public boolean isGift(OptionalCampaignEditBean bean) {
    boolean flag = false;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader ommodityHeader = catalogService.loadBycommodityCode(getLoginInfo().getShopCode(), bean
        .getCommoditiyDetailBean().getCommodityCode(), false, true);
    if (ommodityHeader == null) {
      flag = true;
    }
    return flag;
  }

  //连接新的活动商品
  public String setJoin(OptionalCampaignEditBean bean,OptionalCampaign campaign){
    String relatedCode = null;
    if(StringUtil.isNullOrEmpty(campaign.getRelatedCommodity())){
      relatedCode = ",";
    }else{
      relatedCode = campaign.getRelatedCommodity();
    }
    relatedCode = relatedCode + bean.getCommoditiyDetailBean().getCommodityCode() +","; 
    return relatedCode;
  }
  
  
  //连接删除之后的活动商品
  public String setCut(OptionalCampaignEditBean bean,OptionalCampaign campaign){
    String relatedCode = ",";

    String[] info = campaign.getRelatedCommodity().split(",");
    for(String commodityCode:bean.getCheckedCode()){
      if(StringUtil.isNullOrEmpty(commodityCode)){
        continue;
      }
      for(int i=0;i<info.length;i++){
        if(commodityCode.equals(info[i])){
          info[i]=null;
        }
      }
    }
    
    for(int i=0;i<info.length;i++){
      if(StringUtil.hasValue(info[i])){
        relatedCode = relatedCode + info[i]+ ",";
      }
    }
    
    return relatedCode;
  }
  
  
}
