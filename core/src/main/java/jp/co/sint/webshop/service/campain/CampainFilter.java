package jp.co.sint.webshop.service.campain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dao.OrderCampaignDao;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;
import jp.co.sint.webshop.data.domain.CampaignConditionType;
import jp.co.sint.webshop.data.domain.CampaignMainType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.OrderCampaign;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.impl.AbstractServiceImpl;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class CampainFilter extends AbstractServiceImpl{
  
  private static final Long MAX_QUENTITY = 99999999L;
  private static final long serialVersionUID = 1L;
  
   
   /**
    * 判断订单是否免运费
    * 判断条件：1，订单中商品被设置成免邮商品；2，发货地址符合限定规则
    * @param CampaignInfo (封装了地址，电话，商品SKU，数量，订单号码等)
    * @return true,免运费。 false,不免运费。
    * 
    */
    public boolean isFreeShippingCharge(CampaignInfo campaignI){
      
      boolean result = false;
     
      // 获取当前活动列表
      List<CampaignMain> cmList = getCurrentCampaigns(campaignI);
     
      if (cmList.size() > 0) {//当前时间段内有活动
        String prevCampaignCode=""; //上一条活动code
        String currCampaignCode=""; //当前活动code
        boolean isVal = true;
        for (CampaignMain campaignMain : cmList) {
          
          currCampaignCode = campaignMain.getCampaignCode();
          
          // 相同活动下，所有条件必须全部审核通过。如果有一条不通过，则进行下一个活动判断
          if (!isVal && prevCampaignCode.equals(currCampaignCode)) {
            continue;
          }
          // 当活动代码切换时，上一条审核通过，则返回true，认定待查询订单或商品符合活动
          if (!prevCampaignCode.equals(currCampaignCode) && result) {
            return result;
          }
          
          Long orderLimit = campaignMain.getOrderLimit() == null ? MAX_QUENTITY : campaignMain.getOrderLimit();
          
          // 验证用户订购次数(当用户code不为空时)
          if (!StringUtil.isNullOrEmpty(campaignI.getCustomerCode()) && !validateUserOrderTimes(campaignI, orderLimit,currCampaignCode)) {
            prevCampaignCode = campaignMain.getCampaignCode();
            isVal = false;
            continue;
          }
          
          //验证订单是否免费
          result = validateOrderFreeStatus(campaignMain, campaignI);
          
          if(!result){
            prevCampaignCode = campaignMain.getCampaignCode();
            isVal = false;
            continue;
          }else{
            prevCampaignCode = campaignMain.getCampaignCode();
            isVal = true;
          }
          
        }
        
      }else{//当前时间没有活动
        return false;
      }
      
       // 返回订单免费状态
       return result;
    }
  
  /**
   * 验证订单是否免运费
   * 
   * 条件1，订单中的商品必须是免运费商品
   * 条件2，订单的发货地址必须符合 eg.江浙沪等
   * @param campaignM 当前活动
   * @param campaignI 当前订单信息
   * @return
   */
   
  private boolean validateOrderFreeStatus(CampaignMain campaignM , CampaignInfo campaignI){
    boolean result = false;
    // 验证发货地址
    if (campaignM.getCampaignConditionType().toString().equals(CampaignConditionType.ORDER_ADDRESS.getValue())) {
      //取得实际发货地址
      String shippingArea = campaignI.getPrefectureCode();
      //取得活动条件表中限定地址
      String[] conditionAreas = campaignM.getAttributrValue().split(",");
      
      if (conditionAreas.length == 1 && StringUtil.isNullOrEmpty(conditionAreas[0])) {
        // 如果免邮地区列表未设置，则认为全部地区不免邮
        return false;
      }
      //循环判断发货地址省份是否在免邮范围内
      for (String conditionA : conditionAreas) {
        if (shippingArea.equals(conditionA)){
          result = true;
          break;
        }
      }
      
    }else if (campaignM.getCampaignConditionType().toString().equals(CampaignConditionType.ORDER_COMMODITY.getValue())) {
      // 验证订单中的商品是否包含在活动条件设定的免邮商品中
      
      //取得订单中的商品列表
      List<OrderDetail> cList = campaignI.getCommodityList();
      List<String> odListStr = new ArrayList<String>();
      //将订单商品列表提取出来
      for (OrderDetail orderD : cList) {
        odListStr.add(orderD.getCommodityCode());
      }
      
      //取得活动条件表中设定的免邮商品列表
      String[] conditionCommoditys = campaignM.getAttributrValue().split(",");
      
      //将字符串数组转换成LIst
      List<String> lStr = Arrays.asList(conditionCommoditys);
      
      if (lStr.size() == 1 && StringUtil.isNullOrEmpty(lStr.get(0))) {
        // 如果免邮商品列表未设置，则认为全部商品不免邮
        return false;
      }
      // 获得对象商品区分
      Long cConditionFlg = campaignM.getCampaignConditionFlg();
      
      boolean containFlg = false;
      if (cConditionFlg.toString().equals(CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.getValue())) {
        //当区分是包含时
        
        // 检查免邮商品列表中是否包含了订单中的商品
        for (String sku : odListStr) {
          containFlg = lStr.contains(sku);
          if (containFlg) {//返回包含状态
            break;
          }
        }
      }else { //当区分是仅有时
        
        containFlg = lStr.containsAll(odListStr);
        //判断订单中商品是否全部在免邮商品列表中
      }
      
      
      //如果包含活动商品
      if (containFlg) {
        // 取得活动条件中单个商品限制个数
        Long maxCommodityNum = campaignM.getMaxCommodityNum() == null ? MAX_QUENTITY : campaignM.getMaxCommodityNum();
        // 循环判断订单中单个商品个数是否超出限制
        for (OrderDetail orderD : cList) {
          // 只要有某一个商品购买数量超过限制数量，则返回false，表示该订单不参与活动
          if (orderD.getPurchasingAmount() > maxCommodityNum) {
            return false;
          }
        }
        // 验证单个商品订购数量限制
        result = true;
        
      } else {
        result = false;
      }    
      
    }else{ //如果为免邮活动之外的条件，返回true。
      result = true;
    }
    
    return result;
  }
  
  /**
   * 验证用户历史订单状况
   * @param orderC
   * @param orderLimit
   * @return boolean true,通过验证。false,未通过
   */
  private boolean validateUserOrderTimes(CampaignInfo campaignI, Long orderLimit, String campainCode){
    String mobileNumber = "";
    String phoneNumber = "";
    String nameAddress = "";
    
    boolean result = true;
    
    //取得当前订单配送人电话
    if (!StringUtil.isNullOrEmpty(campaignI.getMobileNumber())) {
      mobileNumber = campaignI.getMobileNumber();
    }else{
      mobileNumber = null;
    }
    
    //取得当前订单配送人手机
    if (!StringUtil.isNullOrEmpty(campaignI.getPhoneNumber())) {
      phoneNumber = campaignI.getPhoneNumber();
    }else {
      phoneNumber = null;
    }
    
    // 取得当前配送人姓名和地址
    if (!StringUtil.isNullOrEmpty(campaignI.getAddress3())) {
      nameAddress = campaignI.getLastName() + campaignI.getAddress1()
          + campaignI.getAddress2() + campaignI.getAddress3() + campaignI.getAddress4();
    } else {
      nameAddress = campaignI.getLastName() + campaignI.getAddress1()
          + campaignI.getAddress2() + campaignI.getAddress4();
    }
    
    //查询当前用户订单中信息在订单历史记录中的状况
    Query query = new SimpleQuery(CampainQuery.GET_USER_HISTORY_ORDERS, campainCode, campaignI.getCustomerCode());
    Long orderCount = DatabaseUtil.executeScalar(query, Long.class);
    
    //如果历史订单中的相关信息已存在，并且大于活动条件限制的次数，则不再参加该多动
    if (orderCount >= orderLimit) {
      result = false;
    }
    
    return result;
  }
  
  /**
   * 插入活动订单
   * @param order
   * @param cashier
   */
 public void insertCampainOrder (CampaignInfo campaignI){
   CampaignMain campaignM = getFreeShippingChargeCM(campaignI);
   OrderCampaignDao dao = DIContainer.getDao(OrderCampaignDao.class);
   
    if ( campaignM != null) {// 如当前订购符合活动，则将订单编号插入活动订单表
      OrderCampaign orderC = new OrderCampaign();
      orderC.setOrderNo(campaignI.getOrderNo());
      orderC.setCampaignCode(campaignM.getCampaignCode());
      orderC.setCampaignName(campaignM.getCampaignName());
      orderC.setCampaignType(campaignM.getCampaignType());
      orderC.setAttributrValue(campaignM.getAttributrValue());
      
      dao.insert(orderC, getLoginInfo());
    }else{// 如果不符合当前活动，则查看活动订单表中是否存在该订单信息，如存在则将其删除
      // 2012/11/30 ob delete start
//      if(dao.exists(campaignI.getOrderNo())){
//        dao.delete(campaignI.getOrderNo());
//      }
      // 2012/11/30 ob delete end
    }
    
  }
 
 /**
  * 根据购物状况，返回符合正在进行的活动
  * @param cashier
  * @return
  */
 private CampaignMain getFreeShippingChargeCM(CampaignInfo campaignI){
   
   boolean result = false;
   CampaignMain cm = null;
   // 获取当前活动列表
   List<CampaignMain> cmList = getCurrentCampaigns(campaignI);
   
   if (cmList.size() > 0) {//当前时间段内有活动
     String prevCampaignCode=""; //上一条活动code
     String currCampaignCode=""; //当前活动code
     boolean isVal = true;
     int i = 0;
     for (CampaignMain campaignMain : cmList) {
       i++;
       currCampaignCode = campaignMain.getCampaignCode();
       
       // 相同活动下，所有条件必须全部审核通过。如果有一条不通过，则进行下一个活动判断
       if (!isVal && prevCampaignCode.equals(currCampaignCode)) {
         continue;
       }
       // 当活动代码切换时，上一条审核通过，则返回true，认定待查询订单或商品符合活动
       if (!prevCampaignCode.equals(currCampaignCode) && result) {
         return cmList.get(i-2);
       }
       
       Long orderLimit = campaignMain.getOrderLimit() == null ? MAX_QUENTITY : campaignMain.getOrderLimit();
       
       // 验证用户订购次数
       if (!validateUserOrderTimes(campaignI, orderLimit,currCampaignCode)) {
         prevCampaignCode = campaignMain.getCampaignCode();
         isVal = false;
         continue;
       }
       
       //验证订单是否免费
       result = validateOrderFreeStatus(campaignMain, campaignI);
       
       if(!result){
         prevCampaignCode = campaignMain.getCampaignCode();
         isVal = false;
         continue;
       }else{
         prevCampaignCode = campaignMain.getCampaignCode();
         isVal = true;
       }
       
     }
     
     if (result) {
      cm = cmList.get(cmList.size()-1);
    }
   }else{//当前时间没有活动
     return null;
   }
   
   return cm;
//   if (cmList.size() > 0) {//当前时间段内有活动
//     Long orderLimit = cmList.get(0).getOrderLimit() == null ? MAX_QUENTITY : cmList.get(0).getOrderLimit();
//     
//     String campainC = cmList.get(0).getCampaignCode();
//     // 验证用户订购次数
//     if (!validateUserOrderTimes(campaignI, orderLimit,campainC)) {
//       return null;
//     }
//     
//   }else{//当前时间没有活动
//     return null;
//   }
   
//   boolean result = true;
//   
//    // 判断当前订单是否在活动列表中
//    for (CampaignMain campaignMain : cmList) {
//       //当活动类型为免运费时
//       if (campaignMain.getCampaignType().toString().equals(CampaignMainType.SHIPPING_CHARGE_FREE.getValue())) {
//         
//         //循环验证订单是否免费。（只要某一次不符合，即停止循环！）
//         result = validateOrderFreeStatus(campaignMain, campaignI);
//         
//         if(!result){
//           return null;
//         }
//       }
//     }
//    
//    // 返回活动bean
//    return cmList.get(0);
 }
 
 /**
  * 根据商品SKU，返回符合正在进行的活动
  * @param sku
  * @return 无符合的活动，返回null。否则返回当前的活动
  */
 public CampaignMain existsSkuCampaign(String sku){
   
    // 获取当前活动列表
    List<CampaignMain> cmList = getCurrentCampaigns(null);
    
    if (cmList.size() <= 0) {//当前时间段内无活动
       return null;
    }
     
    // 判断当前订单是否在活动列表中
    for (CampaignMain campaignMain : cmList) {
      // 当活动类型为免运费时
      if (campaignMain.getCampaignConditionType() != null) {
        if (campaignMain.getCampaignConditionType().toString().equals(CampaignConditionType.ORDER_COMMODITY.getValue())) {

          // 取得活动条件表中设定的免邮商品列表
          String[] conditionCommoditys = campaignMain.getAttributrValue().split(",");
          // 将字符串数组转换成LIst
          List<String> lStr = Arrays.asList(conditionCommoditys);

          if (lStr.contains(sku)) {
            return campaignMain;
          }
        }
      }

    }

    // 当前SKU无进行中活动
    return null;
 }
 
 /**
   * 根据商品SKU，返回符合正在进行的活动列表
  * @param sku
  * @return 无符合的活动，返回null。否则返回活动列表
  */
 // 2012/12/04 ob update start
 //private List<CampaignMain> getCurrentCampaigns(){
 private List<CampaignMain> getCurrentCampaigns(CampaignInfo campaignI){
 // 2012/12/04 ob udpate end
   
   List<CampaignMain> cmList = null;
   
   // 获取当前活动列表
   Query query = new SimpleQuery(CampainQuery.GET_CURRENT_CAMPAIN_LIST);
   // 2012/12/04 ob add start
   if (campaignI!=null && campaignI.getOrderDateTime()!=null) {
	   query = new SimpleQuery(CampainQuery.GET_CURRENT_CAMPAIN_LIST_BY_ORDERDATETIME,campaignI.getOrderDateTime());
   }
   // 2012/12/04 ob add end
   cmList = DatabaseUtil.loadAsBeanList(query, CampaignMain.class);
   
   return cmList;
 }
 
 // 2012/11/16 促销对应 ob add start
  /**
   * 取得当前进行中的活动列表
   * 
   * @return 无符合的活动，返回null。否则返回活动列表
   */
  private List<CampaignMain> getCurrentCampaignsNormal() {

    List<CampaignMain> cmList = null;

    // 获取当前活动列表
    Query query = new SimpleQuery(CampainQuery.GET_CURRENT_CAMPAIN_LIST_NORMAL);
    cmList = DatabaseUtil.loadAsBeanList(query, CampaignMain.class);

    return cmList;
  }

  /**
   * 根据商品SKU，返回符合正在进行的活动(特定商品促销)
   * 
   * @param sku
   * @return 无符合的活动，返回null。否则返回当前的活动
   */
  public List<CampaignMain> getSkuCampaigns(String sku) {

    // 获取当前活动列表
    List<CampaignMain> cmList = getCurrentCampaignsNormal();
    List<CampaignMain> campaigns;
    if (cmList.size() <= 0) {// 当前时间段内无活动
      return null;
    } else {
      campaigns = new ArrayList<CampaignMain>();
      // 判断当前订单是否在活动列表中
      for (CampaignMain campaignMain : cmList) {
        // 当活动类型为对象商品
        if (campaignMain.getCampaignConditionType() != null && campaignMain.getCampaignType() != null
            && CampaignConditionType.ORDER_COMMODITY.longValue().equals(campaignMain.getCampaignConditionType())
            && CampaignMainType.GIFT.longValue().equals(campaignMain.getCampaignType())) {

          // 取得活动中设定的商品列表
          String[] conditionCommoditys = campaignMain.getAttributrValue().split(",");
          // 将字符串数组转换成LIst
          List<String> lStr = Arrays.asList(conditionCommoditys);
          if (lStr.contains(sku)) {
            campaigns.add(campaignMain);
          }
        }
      }
    }
    // 当前SKU无进行中活动
    return campaigns;
  }

  /**
   * 根据商品SKU，根据商品SKU，返回符合正在进行的活动的赠品(特定商品促销)
   * 
   * @param sku
   * @return 无符合的活动，返回null。否则返回当前的活动的赠品
   */
  public List<GiftItem> getOtherGiftCodeBySku(String sku, String shopCode) {

    List<GiftItem> items;
    List<String> otherGiftCodes = new ArrayList<String>();
    // 获取当前活动列表
    List<CampaignMain> cmList = getCurrentCampaignsNormal();
    List<CampaignMain> campaigns;
    if (cmList == null || cmList.size() <= 0) {// 当前时间段内无活动
      return null;
    } else {
      items = new ArrayList<GiftItem>();
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      TaxUtil u = DIContainer.get("TaxUtil");
      Long taxRate = u.getTaxRate();
      campaigns = new ArrayList<CampaignMain>();
      // 判断当前订单是否在活动列表中
      for (CampaignMain campaignMain : cmList) {
        // 当活动类型为对象商品
        if (campaignMain.getCampaignConditionType() != null && campaignMain.getCampaignType() != null
            && CampaignConditionType.ORDER_COMMODITY.longValue().equals(campaignMain.getCampaignConditionType())
            && CampaignMainType.GIFT.longValue().equals(campaignMain.getCampaignType())) {
          // 取得活动中设定的商品列表
          String[] conditionCommoditys = campaignMain.getAttributrValue().split(",");
          // 将字符串数组转换成LIst
          List<String> lStr = Arrays.asList(conditionCommoditys);
          if (lStr.contains(sku)) {
            campaigns.add(campaignMain);
            Query query = new SimpleQuery(CatalogQuery.GET_CAMPAIGN_DOING_GIFT, campaignMain.getCampaignCode());
            List<String> giftcodes = DatabaseUtil.loadAsStringList(query);
            if (giftcodes != null && giftcodes.size() > 0) {
              String[] gifts = giftcodes.get(0).split(",");
              for (String giftCode : gifts) {
                if (!otherGiftCodes.contains(giftCode)) {
                  GiftItem item = new GiftItem();
                  CommodityInfo container = catalogService.getCommodityInfo(shopCode, giftCode);
                  if (container != null && container.getHeader() != null) {
                    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
                      item.setGiftName(container.getHeader().getCommodityName());
                      item.setStandardDetail1Name(container.getDetail().getStandardDetail1Name());
                      item.setStandardDetail2Name(container.getDetail().getStandardDetail2Name());
                      item.setCampaignName(campaignMain.getCampaignName());
                    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
                      item.setGiftName(container.getHeader().getCommodityNameJp());
                      item.setStandardDetail1Name(container.getDetail().getStandardDetail1NameJp());
                      item.setStandardDetail2Name(container.getDetail().getStandardDetail2NameJp());
                      item.setCampaignName(campaignMain.getCampaignNameJp());
                    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
                      item.setGiftName(container.getHeader().getCommodityNameEn());
                      item.setStandardDetail1Name(container.getDetail().getStandardDetail1NameEn());
                      item.setStandardDetail2Name(container.getDetail().getStandardDetail2NameEn());
                      item.setCampaignName(campaignMain.getCampaignNameEn());
                    }
                    
                    Price price = new Price(container.getHeader(), container.getDetail(), null, taxRate);
                    item.setUnitTaxCharge(price.getUnitTaxCharge());
                    item.setUnitPrice(price.getUnitPrice());
                    item.setRetailPrice(price.getRetailPrice());
                    item.setDiscount(price.isDiscount());
                    item.setWeight(container.getDetail().getWeight());
                    item.setTaxType(container.getHeader().getCommodityTaxType());
                    item.setGiftCode(container.getHeader().getCommodityCode());
                    item.setShopCode(shopCode);
                    item.setGiftSkuCode(container.getHeader().getRepresentSkuCode());
                    item.setCampaignCode(campaignMain.getCampaignCode());
                    items.add(item);
                    otherGiftCodes.add(giftCode);
                  }
                }
              }
            }
          }
        }
      }
    }
    return items;
  }

  // 2012/11/16 促销对应 ob add start
}
