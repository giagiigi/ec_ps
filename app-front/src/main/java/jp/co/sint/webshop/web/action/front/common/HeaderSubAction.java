package jp.co.sint.webshop.web.action.front.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.OptionalCommodity;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.cart.impl.CartItemImpl;
import jp.co.sint.webshop.service.catalog.CommodityCompositionContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebSubAction;
import jp.co.sint.webshop.web.bean.front.common.HeaderBean;
import jp.co.sint.webshop.web.login.WebLoginManager;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.webutility.CommonSessionContainer;

import org.apache.log4j.Logger;

/**
 * Include jsp用のActionのアクションサブクラスです
 * 
 * @author System Integrator Corp.
 */
public class HeaderSubAction extends WebSubAction {

  /**
   * ログイン情報を取得します
   * 
   * @return frontLoginInfo
   */
  public FrontLoginInfo getLoginInfo() {
    LoginInfo loginInfo = getSessionContainer().getLoginInfo();
    FrontLoginInfo frontLoginInfo = null;

    if (loginInfo == null) {
      frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
    } else {
      if (loginInfo instanceof FrontLoginInfo) {
        frontLoginInfo = (FrontLoginInfo) loginInfo;
      } else {
        frontLoginInfo = WebLoginManager.createFrontNotLoginInfo();
      }
    }
    return frontLoginInfo;
  }

  /**
   * アクションを実行します。
   */
  @Override
  public void callService() {
    
    Logger logger = Logger.getLogger(this.getClass());
    // 获得sessionID start
    CommonSessionContainer sesContainer = (CommonSessionContainer) getSessionContainer();
    if(sesContainer.getSession() != null){
      logger.info("当前HeaderSubAction:sessionID="+sesContainer.getSession().getId()+"开始记录--------------------------------------------------------------------");
    }else{
      logger.info("当前HeaderSubAction:session缺失，开始记录--------------------------------------------------------------------");
    }

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    List<CodeAttribute> categories = utilService.getMajorCategories();
    List<CodeAttribute> list = new ArrayList<CodeAttribute>();
    for (CodeAttribute ca : categories) {
      list.add(new NameValue(StringUtil.getHeadline(ca.getName(), 10), ca.getValue()));
    }
    HeaderBean bean = (HeaderBean) getBean();
    bean.setCategoryTreeList(list);

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Category currentCategory = service.getCategory(bean.getCategoryTreeCondition());
    bean.setFirstRank(currentCategory == null || currentCategory.getDepth() <= 1);
    // 現在のカテゴリからルートまでのパンくずリストを取得
    List<CodeAttribute> topicPath = service.getTopicPath(null, null, bean.getCategoryTreeCondition());
    List<CodeAttribute> subPath = new ArrayList<CodeAttribute>();
    Category rootCategory = service.getRootCategory();
    subPath.add(new NameValue(utilService.getNameByLanguage(rootCategory.getCategoryNamePc(),rootCategory.getCategoryNamePcEn(),rootCategory.getCategoryNamePcJp()), rootCategory.getCategoryCode()));
    for (CodeAttribute ca : topicPath) {
      String name = StringUtil.getHeadline(ca.getName(), 10);
      // /category/[categoryCode]の形になっているのでカテゴリコードのみを残す
      String value = ca.getValue().replace("/category/", "");
      subPath.add(new NameValue(name, value));
    }
    bean.setSubPathList(subPath);
    Cart cart = getSessionContainer().getCart();
    // 2012/12/07 促销对应 ob update start
    // int itemCount = cart.getItemCount();
    int itemCount = cart.getItemCountExceptGift();
    // 2012/12/07 促销对应 ob update end
    
    bean.setItemCount(itemCount);
    BigDecimal grandTotal = cart.getGrandTotal();
    bean.setGrandTotal(grandTotal);
    List<CartItem> cartItemList = cart.get();
    bean.setCartItemList(cartItemList);
    BigDecimal weightTotal = BigDecimal.ZERO;
    List<CartItem> newCartItemList = new ArrayList<CartItem>();
    // 因限时限购价格重新计算
    grandTotal=BigDecimal.ZERO;
    List<OptionalCampaign> optinalList = service.getOptionalCampaignList();
    Map<OptionalCampaign, List<CartCommodityInfo>> optionalMap = new HashMap<OptionalCampaign, List<CartCommodityInfo>>();
    
    for (CartItem item : cartItemList) {
      DiscountCommodity dc = service.getDiscountCommodityByCommodityCode(item.getCommodityCode());
      CommodityHeader ch = service.getCommodityHeader("00000000", item.getCommodityCode());

      weightTotal = weightTotal.add(BigDecimalUtil.multiply(item.getWeight(), item.getQuantity()));
      
      CartItemImpl discountItem = null;
      CartItemImpl newItem=null;
      int totalNum = item.getQuantity();

      // 2012/12/07 促销对应 ob add start
      if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
        for (GiftItem gift : item.getCommodityInfo().getGiftList()) {
          weightTotal = weightTotal.add(BigDecimalUtil.multiply(gift.getWeight(), gift.getQuantity()));
        }
      }
      // 2012/12/07 促销对应 ob add end
      if(SetCommodityFlg.OBJECTIN.longValue().equals(ch.getSetCommodityFlg())) {
        item.getCommodityInfo().setRetailPrice(service.getSuitSalePrice(item.getCommodityCode()).getRetailPrice());
        item.getCommodityInfo().setUnitPrice(getSuitOldPrice(item.getCommodityCode()));
      }
      if (dc != null ){
        //item.getCommodityInfo().setRetailPrice(dc.getDiscountPrice());
        item.getCommodityInfo().setDiscountTimePrice(dc.getDiscountPrice());
        

        // 20140430 hdh add start
        // 限时限量折扣
        //历史所有客户购买总数
        Long siteTotalAmount = 0L;
        siteTotalAmount= service.getHistoryBuyAmountTotal(item.getCommodityCode());
        if (siteTotalAmount == null){
            siteTotalAmount = 0L;
        }
        
        Long historyNum = 0L;
        String customerCode = getLoginInfo().getCustomerCode();
        if (StringUtil.hasValue(customerCode)) {
          //当前登录客户历史购买数量
          historyNum = service.getHistoryBuyAmount(item.getCommodityCode(), customerCode);
          if (historyNum == null){
            historyNum = 0L;
          }
        }
        if ( dc.getSiteMaxTotalNum() > siteTotalAmount){
          Long curQuantity = 0L;
          //限购商品剩余可购买数量
          Long avalibleAmount = dc.getSiteMaxTotalNum() - siteTotalAmount;
          if(avalibleAmount < 0L ){
            avalibleAmount = 0L;
          }
          if (dc.getCustomerMaxTotalNum()!=null){
            //当前客户可购买限购商品数
            curQuantity = dc.getCustomerMaxTotalNum() - historyNum;
            if(curQuantity < 0L ){
              curQuantity = 0L;
            }
            if(curQuantity > avalibleAmount){
              curQuantity = avalibleAmount;
            }
          }
          if (totalNum <= curQuantity){
            newItem = new CartItemImpl();;
            newItem.setCommodityName(item.getCommodityName());
            newItem.setWeight(item.getWeight());
            newItem.setQuantity(totalNum);
            newItem.setRetailPrice(dc.getDiscountPrice());
            newItem.setCommodityType(item.getCommodityType());
            newItem.setCommodityCode(item.getCommodityCode());
            newItem.setShopCode(item.getShopCode());
            
            if (optinalList != null && optinalList.size() > 0) {
              for (OptionalCampaign oc : optinalList) {
                if (oc.getRelatedCommodity().contains("," + newItem.getCommodityCode() + ",")) {
                  if (optionalMap.get(oc) == null) {
                    List<CartCommodityInfo> list2 = new ArrayList<CartCommodityInfo>();
                    optionalMap.put(oc, list2);
                  }
                  optionalMap.get(oc).add(newItem.getCommodityInfo());
                  break;
                }
              }
            }
          } else {
            if (curQuantity != 0L) {
              discountItem = new CartItemImpl();
              discountItem.setCommodityName(item.getCommodityName());
              discountItem.setWeight(item.getWeight());
              discountItem.setQuantity(curQuantity.intValue());
              discountItem.setRetailPrice(dc.getDiscountPrice());
              discountItem.setCommodityType(item.getCommodityType());
              discountItem.setCommodityCode(item.getCommodityCode());
              discountItem.setShopCode(item.getShopCode());
              
            }
            newItem = new CartItemImpl();;
            newItem.setCommodityName(item.getCommodityName());
            newItem.setWeight(item.getWeight());
            newItem.setQuantity(item.getQuantity()-curQuantity.intValue());
            newItem.setRetailPrice(item.getRetailPrice());
            newItem.setCommodityType(item.getCommodityType());
            newItem.setCommodityCode(item.getCommodityCode());
            newItem.setShopCode(item.getShopCode());
          
            if (optinalList != null && optinalList.size() > 0) {
              for (OptionalCampaign oc : optinalList) {
                if (oc.getRelatedCommodity().contains("," + newItem.getCommodityCode() + ",")) {
                  if (optionalMap.get(oc) == null) {
                    List<CartCommodityInfo> list2 = new ArrayList<CartCommodityInfo>();
                    optionalMap.put(oc, list2);
                  }
                  optionalMap.get(oc).add(newItem.getCommodityInfo());
                  break;
                }
              }
            }
          }
        } 

        
      }
      if( dc!=null&&newItem !=null){
        newCartItemList.add(newItem);
        grandTotal = grandTotal.add(BigDecimalUtil.multiply(newItem.getRetailPrice().add(newItem.getGiftPrice()), newItem.getQuantity()));
        if(discountItem !=null){
          newCartItemList.add(discountItem);
          grandTotal = grandTotal.add(BigDecimalUtil.multiply(discountItem.getRetailPrice().add(discountItem.getGiftPrice()), discountItem.getQuantity()));
        }
      }else{
        newCartItemList.add((CartItemImpl)item);
        grandTotal = grandTotal.add(BigDecimalUtil.multiply(item.getRetailPrice().add(item.getGiftPrice()), item.getQuantity()));
        if (optinalList != null && optinalList.size() > 0) {
          for (OptionalCampaign oc : optinalList) {
            if (oc.getRelatedCommodity().contains("," + item.getCommodityCode() + ",")) {
              if (optionalMap.get(oc) == null) {
                List<CartCommodityInfo> list2 = new ArrayList<CartCommodityInfo>();
                optionalMap.put(oc, list2);
              }
              optionalMap.get(oc).add(item.getCommodityInfo());
              break;
            }
          }
        }
      }
    }
    
    
 // 任选商品对应
    BigDecimal cheapPrice = BigDecimal.ZERO;
    Map<String, OptionalCommodity> optionalCommodityMap = new HashMap<String, OptionalCommodity>();
    for (OptionalCampaign oc : optionalMap.keySet()) {

      Collections.sort(optionalMap.get(oc));

      // 任选活动和原商品差价计算
      Long orderLimitNum = oc.getOrderLimitNum();
      Long optionalNum = oc.getOptionalNum();

      List<CartCommodityInfo> singleList = new ArrayList<CartCommodityInfo>();

      for (CartCommodityInfo ccb : optionalMap.get(oc)) {
        for (int i = 0; i < ccb.getQuantity(); i++) {
          singleList.add(ccb);
        }
      }

      if (singleList.size() >= optionalNum) {

        // 已购买的商品可匹配最多套数
        Long buyTime = singleList.size() / optionalNum;
        // 实际可购买套数
        Long realTime = 0L;
        if (buyTime < orderLimitNum) {
          realTime = buyTime;
        } else {
          realTime = orderLimitNum;
        }

        // 按套餐商品价格购买的商品
        List<CartCommodityInfo> realSingleList = new ArrayList<CartCommodityInfo>();
        for (int i = 0; i < realTime * optionalNum; i++) {
          realSingleList.add(singleList.get(i));
        }
        // 算出差价 封装optionalCommodityMap
        cheapPrice = getOptional(cheapPrice, realSingleList, optionalCommodityMap, oc);
      }
    }
    bean.setGrandTotal(grandTotal.subtract(cheapPrice));
    if(newCartItemList.size()>0){
      bean.setCartItemList(newCartItemList);
    }
    // 20140504 hdh add end
    bean.setWeightTotal(NumUtil.parseBigDecimalWithoutZero(weightTotal));
    setBean(bean);
    
    if(sesContainer.getSession() != null){
      logger.info("当前HeaderSubAction:sessionID="+sesContainer.getSession().getId()+"结束记录--------------------------------------------------------------------");
    }else{
      logger.info("当前HeaderSubAction:session缺失，结束记录--------------------------------------------------------------------");
    }
  }
  
  private BigDecimal getOptional(BigDecimal cheapPrice, List<CartCommodityInfo> realSingleList,
      Map<String, OptionalCommodity> optionalCommodityMap, OptionalCampaign oc) {
    BigDecimal partTotalPrice = BigDecimal.ZERO;

    List<CartCommodityInfo> list = new ArrayList<CartCommodityInfo>();
    for (int i = 0; i < realSingleList.size(); i++) {
      partTotalPrice = partTotalPrice.add(realSingleList.get(i).getRetailPrice());
      list.add(realSingleList.get(i));
      if ((i + 1) % oc.getOptionalNum() == 0) {
        if (BigDecimalUtil.isAbove(partTotalPrice, oc.getOptionalPrice())) {
          // 当前套的差价
          BigDecimal curCheapPrice = partTotalPrice.subtract(oc.getOptionalPrice());
          // 加入到总差价
          cheapPrice = cheapPrice.add(curCheapPrice);
        }
        partTotalPrice = BigDecimal.ZERO;
        list.clear();
      }
    }
    return cheapPrice;
  }
  
  
  private BigDecimal getSuitOldPrice(String suitCommodityCode) {
    CatalogService catalogService = ServiceLocator.getCatalogService(ServiceLoginInfo.getInstance());
    List<CommodityCompositionContainer> commodityCompositionContainerList = catalogService.getCommodityCompositionContainerList("00000000", suitCommodityCode);
    BigDecimal totalPrce = new BigDecimal(0);
    for (CommodityCompositionContainer child : commodityCompositionContainerList) {
      for (CommodityDetail detail : child.getCommodityDetailList()) {
          totalPrce = totalPrce.add(NumUtil.parse(detail.getUnitPrice().toString()));
      }
    }
    return totalPrce;
  }

}
