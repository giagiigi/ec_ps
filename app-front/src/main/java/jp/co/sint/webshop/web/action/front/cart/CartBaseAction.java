package jp.co.sint.webshop.web.action.front.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.CampaignConditionFlg;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.OptionalCommodity;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.cart.impl.CartItemImpl;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.NumberLimitPolicy;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.cart.CartBean;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean.CommodityCartBean;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean.CommodityCartBean.CompositionBean;
import jp.co.sint.webshop.web.bean.front.cart.CartBean.ShopCartBean.CommodityCartBean.GiftBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020110:ショッピングカートのアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class CartBaseAction extends WebFrontAction<CartBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  // 2012/11/24 促销对应 ob update start
  public boolean validate() {
    CartBean bean = getBean();
    boolean valid = true; 
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityAvailability commodityAvailability = null;
    String shopCode = null;
    String skuCode = null;
    NumberLimitPolicy numberPolicy = DIContainer.getNumberLimitPolicy();

    for (ShopCartBean shopBean : bean.getShopCartBean()) {
      BigDecimal totalCurrency = BigDecimal.ZERO;
      int totalQuantity = 0;
      Long availableStock = 0L;
      for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
        String diplayCommodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail1Name());
        List<String> errorMessageList = BeanValidator.validate(commodityBean).getErrorMessages();
        if (errorMessageList.size() > 0) {
          for (String s : errorMessageList) {
            addErrorMessage(MessageFormat.format(Messages.getString("web.action.front.cart.CartBaseAction.0"), diplayCommodityName, s));
          }
          return false;

        }
        
 

        shopCode = commodityBean.getShopCode();
        skuCode = commodityBean.getSkuCode();
        int quantity = Integer.parseInt(commodityBean.getCommodityAmount());

        totalCurrency = totalCurrency.add(BigDecimalUtil.multiply(commodityBean.getCommoditySalesPrice().add(commodityBean.getGiftPrice()), 
            NumUtil.parseLong(quantity)));
        totalQuantity = totalQuantity + quantity;
        
        // 限界値チェック
        ValidationResult resultCurrency = numberPolicy.validTotalCurrency(totalCurrency);
        if (resultCurrency.isError()) {
          addErrorMessage(getLimitErrorMessageOfEachMode(shopBean.getShopName(), resultCurrency.getFormedMessage()));
          valid = false;
        }
        
        ValidationResult resultAmount = numberPolicy.validTotalAmount(Long.valueOf(totalQuantity));
        if (resultAmount.isError()) {
          addErrorMessage(getLimitErrorMessageOfEachMode(shopBean.getShopName(), resultAmount.getFormedMessage()));
          valid &= false;
        }
        
        if (!valid) {
          return false;
        }
        
        
//        // 最小购买数check
//        if (!commodityBean.isGift()) {
//          if (commodityBean.isSetCommodity()) {
//            // 套餐商品
//            List<String> compositionSkuCodeList = new ArrayList<String>();
//            List<String> giftSkuCodeList = new ArrayList<String>();
//            skuCode = commodityBean.getSkuCodeOfSet();
//            for (CompositionBean composition : commodityBean.getCompositionList()) {
//              if (StringUtil.hasValue(composition.getSkuCode())) {
//                compositionSkuCodeList.add(composition.getSkuCode());
//              }
//            }
//            
//            if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
//              for (GiftBean gift : commodityBean.getGiftList()) {
//                if (StringUtil.hasValue(gift.getGiftSkuCode())) {
//                  giftSkuCodeList.add(gift.getGiftSkuCode());
//                }
//              }
//            }
//            availableStock = catalogService.getAvailableStock(shopCode, skuCode, true, compositionSkuCodeList, giftSkuCodeList);
//            if (availableStock < NumUtil.toLong(commodityBean.getCommodityAmount(), 0L)) {
//              addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
//              valid &= false;
//            }
//          } else {
//           // 通常商品
//            List<String> giftSkuCodeList = new ArrayList<String>();
//
//            if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
//              for (GiftBean gift : commodityBean.getGiftList()) {
//                if (StringUtil.hasValue(gift.getGiftSkuCode())) {
//                  giftSkuCodeList.add(gift.getGiftSkuCode());
//                }
//              }
//            }
//            CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
//            CommodityHeader ch = chDao.load(shopCode, commodityBean.getCommodityCode());
//
//            if(StringUtil.hasValue(ch.getOriginalCommodityCode())){
//              availableStock = catalogService.getAvailableStock(shopCode, ch.getOriginalCommodityCode(), false, null, giftSkuCodeList);
//              availableStock = availableStock/ch.getCombinationAmount();
//            }else{
//              availableStock = catalogService.getAvailableStock(shopCode, skuCode, false, null, giftSkuCodeList);
//            }
//            
//            if (availableStock < NumUtil.toLong(commodityBean.getCommodityAmount(), 0L)) {
//              addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
//              valid &= false;
//            }
//          }
//        }
        
//        // 各套餐的可购买性check
//        if (valid && commodityBean.isSetCommodity()) {
//          commodityAvailability = catalogService.isAvailable(shopCode, skuCode, quantity, commodityBean.isReserve(), true);
//          
//          if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
//            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, diplayCommodityName));
//            valid &= false;
//          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
//            addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, diplayCommodityName));
//            valid &= false;
//          } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
//            valid &= true;
//          }
//        }
        
        
//        //组合商品库存判断时候  要将购物车里面的所有商品都进行循环判断  add  by twh  start
//        if(valid && !commodityBean.isSetCommodity()){
//          if (getCart().get().size() > 0) {
//            Long allCount = 0L;
//            Long allStock = 0L;
//            if (StringUtil.hasValue(commodityBean.getOriginalCommodityCode())) {
//              allStock = catalogService.getAvailableStock(commodityBean.getShopCode(),commodityBean.getOriginalCommodityCode());
//              allCount = quantity  * commodityBean.getCombinationAmount();
//            } else {
//              allStock = catalogService.getAvailableStock(commodityBean.getShopCode(), skuCode);
//              allCount = quantity  * 1L;
//            }
//  
//            for (CartItem item : getCart().get()) {
//              if (!item.getCommodityCode().equals(skuCode)) {
//                boolean rel = false;
//                if (StringUtil.hasValue(commodityBean.getOriginalCommodityCode())) {
//                  rel = (commodityBean.getOriginalCommodityCode().equals(item.getCommodityCode()) || (StringUtil.hasValue(item
//                      .getOriginalCommodityCode()) && commodityBean.getOriginalCommodityCode().equals(item.getOriginalCommodityCode())));
//                } else {
//                  rel = (StringUtil.hasValue(item.getOriginalCommodityCode()) && commodityBean.getCommodityCode().equals(
//                      item.getOriginalCommodityCode()));
//                }
//                
//                if (rel) {
//                  if (StringUtil.hasValue(item.getOriginalCommodityCode()) && item.getCombinationAmount() != null) {
//                    allCount = allCount + item.getQuantity() * item.getCombinationAmount();
//                  } else {
//                    allCount = allCount + item.getQuantity();
//                  }
//                }
//              }
//            }
//            if (allCount > allStock) {
//              addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
//              valid &= false;
//            }
//          }
//        }
//        //组合商品库存判断时候  要将购物车里面的所有商品都进行循环判断  add  by twh end
      }
      
      
      // 套装，单品，组合，库存综合判断
      List<CommodityCartBean> suitList = new ArrayList<CommodityCartBean>();
      List<CommodityCartBean> combineList = new ArrayList<CommodityCartBean>();
      List<CommodityCartBean> simpleListAll = new ArrayList<CommodityCartBean>();
      List<CommodityCartBean> simpleList = new ArrayList<CommodityCartBean>();
      List<CommodityCartBean> discountList = new ArrayList<CommodityCartBean>();
      Map<String,Long> notSimpleMap = new HashMap<String,Long>();
      for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
        if (StringUtil.hasValue(commodityBean.getOriginalCommodityCode())) {
          combineList.add(commodityBean);
        } else if (commodityBean.isSetCommodity()) {
          suitList.add(commodityBean);
        } else {
          simpleListAll.add(commodityBean);
        }
      }
      for( CommodityCartBean commodityBean : simpleListAll) {
        if(commodityBean.getIsDiscountCommodity().equals("true")) {
          discountList.add(commodityBean);
        } else {
          simpleList.add(commodityBean);
        }
      }
      
      // 限时限量折扣商品
      for (CommodityCartBean commodityBean : discountList) {
        if (notSimpleMap.get(commodityBean.getCommodityCode()) == null ) {
          notSimpleMap.put(commodityBean.getCommodityCode(), Long.parseLong(commodityBean.getCommodityAmount()));
        } else {
          Long num = notSimpleMap.get(commodityBean.getCommodityCode());
          num += Long.parseLong(commodityBean.getCommodityAmount());
          notSimpleMap.remove(commodityBean.getCommodityCode());
          notSimpleMap.put(commodityBean.getCommodityCode(), num);
        }
      }
      
      // 套餐商品
      for (CommodityCartBean commodityBean : suitList) {
        String diplayCommodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail1Name());
        List<String> compositionSkuCodeList = new ArrayList<String>();
        List<String> giftSkuCodeList = new ArrayList<String>();
        skuCode = commodityBean.getSkuCodeOfSet();
        for (CompositionBean composition : commodityBean.getCompositionList()) {
          CommodityCartBean commodity = new CommodityCartBean();
          commodity.setCommodityName(commodityBean.getCommodityName());
          commodity.setCommodityCode(composition.getSkuCode());
          commodity.setCommodityAmount("0");
          simpleList.add(commodity);
          if (StringUtil.hasValue(composition.getSkuCode())) {
            compositionSkuCodeList.add(composition.getSkuCode());
            if (notSimpleMap.get(composition.getSkuCode()) == null ) {
              notSimpleMap.put(composition.getSkuCode(), Long.parseLong(commodityBean.getCommodityAmount()));
            } else {
              Long num = notSimpleMap.get(composition.getSkuCode());
              num += Long.parseLong(commodityBean.getCommodityAmount());
              notSimpleMap.remove(composition.getSkuCode());
              notSimpleMap.put(composition.getSkuCode(), num);
            }
          }
        }
        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
          for (GiftBean gift : commodityBean.getGiftList()) {
            if (StringUtil.hasValue(gift.getGiftSkuCode())) {
              giftSkuCodeList.add(gift.getGiftSkuCode());
            }
          }
        }
        availableStock = catalogService.getAvailableStock(shopCode, skuCode, true, compositionSkuCodeList, giftSkuCodeList);
        if (availableStock < NumUtil.toLong(commodityBean.getCommodityAmount(), 0L)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
          return false;
        }
      }
      
      // 组合商品
      CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
      for (CommodityCartBean commodityBean : combineList) {
        String diplayCommodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail1Name());
        List<String> giftSkuCodeList = new ArrayList<String>();
        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
          for (GiftBean gift : commodityBean.getGiftList()) {
            if (StringUtil.hasValue(gift.getGiftSkuCode())) {
              giftSkuCodeList.add(gift.getGiftSkuCode());
            }
          }
        }

        CommodityHeader ch = chDao.load(shopCode, commodityBean.getCommodityCode());
        CommodityCartBean commodity = new CommodityCartBean();
        commodity.setCommodityCode(ch.getOriginalCommodityCode());
        commodity.setCampaignCode(ch.getCombinationAmount().toString());
        commodity.setCommodityAmount("-100");
        commodity.setCommodityName(commodityBean.getCommodityName());
        simpleList.add(commodity);
        availableStock = catalogService.getAvailableStock(shopCode, ch.getOriginalCommodityCode());
        availableStock = availableStock/ch.getCombinationAmount();
        
        if (availableStock < NumUtil.toLong(commodityBean.getCommodityAmount(), 0L)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
          return false;
        }
        if (notSimpleMap.get(ch.getOriginalCommodityCode()) == null ) {
          notSimpleMap.put(ch.getOriginalCommodityCode(), Long.parseLong(commodityBean.getCommodityAmount())*ch.getCombinationAmount() );
        } else {
          Long num = notSimpleMap.get(ch.getOriginalCommodityCode());
          num += Long.parseLong(commodityBean.getCommodityAmount())*ch.getCombinationAmount();
          notSimpleMap.remove(ch.getOriginalCommodityCode());
          notSimpleMap.put(ch.getOriginalCommodityCode(), num);
        }
      }
      
      
      
      // 通常商品
      boolean combineFlg = false; 
      for (CommodityCartBean commodityBean : simpleList) {
        if (commodityBean.getCommodityAmount().equals("-100")) {
          combineFlg = true;
          commodityBean.setCommodityAmount("0");
        } else {
          combineFlg = false;
        }
        String diplayCommodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail1Name());
        Long num = notSimpleMap.get(commodityBean.getCommodityCode());
        Long totalNum = Long.parseLong(commodityBean.getCommodityAmount());
        if (num == null) {
          num = 0L;
        }
        totalNum += num;
        
        List<String> giftSkuCodeList = new ArrayList<String>();
        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
           for (GiftBean gift : commodityBean.getGiftList()) {
             if (StringUtil.hasValue(gift.getGiftSkuCode())) {
               giftSkuCodeList.add(gift.getGiftSkuCode());
             }
           }
        }
        availableStock = catalogService.getAvailableStock(shopCode, commodityBean.getCommodityCode());
        if (availableStock < totalNum ) {
           if (combineFlg) {
             addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, (availableStock/Long.parseLong(commodityBean.getCampaignCode()))+""));
           } else {
             addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, diplayCommodityName, availableStock.toString()));
           }
           return false;
        }
      }
      
      // 购物车内的多重优惠活动的赠品信息check
      createOtherGift(shopBean, false);
      List<CommodityCartBean> acceptedGiftList = new ArrayList<CommodityCartBean>();
      for (CartItem item : getCart().get()) {
        CommodityCartBean commodity = new CommodityCartBean();
        if (CommodityType.GIFT.longValue().equals(item.getCommodityInfo().getCommodityType())) {
          commodity = createAcceptedGift(item);
          commodity.setGift(true);
          acceptedGiftList.add(commodity);
        }
      }

      if (acceptedGiftList.size() > 0) {
        shopBean.setAcceptedGiftBean(acceptedGiftList);
      }
      
      // 各通常商品可购买性check
      if (valid) {
        Map<String, Long> commodityAmountMap = getCommodityTotalAmountFromBean();
        for (Object key : commodityAmountMap.keySet()) {
          commodityAvailability = catalogService.isAvailable(shopCode, key.toString(), commodityAmountMap.get(key).intValue(), false);
          String commodityName = getBean().getCartCommodityName().get(key);
          
          
          if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, commodityName));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, commodityName));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityName));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE_ERROR, commodityName, String.valueOf(catalogService
                .getAvailableStock(shopCode, key.toString()))));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, commodityName, NumUtil.toString(catalogService
                .getReservationAvailableStock(shopCode, key.toString()))));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, commodityName));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
            valid &= true;
          }  
        }
      }
      
      // 各赠品可购买性check
      if (valid) {
        Map<String, Long> cartGiftTotalAmount = getGiftTotalAmountFromBean();
        for (Object key : cartGiftTotalAmount.keySet()) {
          commodityAvailability = catalogService.isAvailableGift(shopCode, key.toString(), cartGiftTotalAmount.get(key).intValue(), true);
          String commodityName = getBean().getCartGiftName().get(key);
          if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, commodityName));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE_ERROR, commodityName, String.valueOf(catalogService.getAvailableStockByCommodityCode(shopCode, key.toString()))));
            valid &= false;
          } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
            valid &= true;
          }  
        }
      }
      
      
    }
    
    return valid;
  }


  /**
   * カートの商品をコピーします。
   */
  public void copyBeanToCart() {
    Cart cart = getCart();
    CartBean bean = getBean();
    // 所有商品集合
    List<CommodityCartBean> allCartBean = new ArrayList<CommodityCartBean>();
    
    for (ShopCartBean cartBean : bean.getShopCartBean()) {
      //A件B元活动内外商品综合
      allCartBean.addAll(cartBean.getCommodityCartBean());
      allCartBean.addAll(cartBean.getOptionalCommodityList());
      for (int i = 0 ;i < allCartBean.size()-1; i++) {
        for (int j = i+1 ;j < allCartBean.size(); j++) {
          if (allCartBean.get(i).getSkuCode().equals(allCartBean.get(j).getSkuCode())){
            Long oldAmountI = Long.parseLong(allCartBean.get(i).getCommodityAmount());
            Long oldAmountJ = Long.parseLong(allCartBean.get(j).getCommodityAmount());
            oldAmountI = oldAmountI + oldAmountJ;
            allCartBean.get(i).setCommodityAmount(oldAmountI.toString());
            allCartBean.remove(j);
            j--;
          }
        }
      }
      
      for (CommodityCartBean commodityBean : allCartBean) {
        // 20111214 shen update start
        cart.updateQuantity(commodityBean.getShopCode(), commodityBean.getSkuCode(), StringUtil.isNullOrEmpty(cartBean
            .getReserveSkuCode()), Integer.parseInt(commodityBean.getCommodityAmount()), getLoginInfo().getCustomerCode());
        // 20111214 shen update end
      }
    }
  }

  /**
   * 注文商品が無い場合は「次へボタン」を表示させず、ある場合は オペレーションモード別に注文品を取得してBeanを1件生成します。
   * 
   * @return bean
   */
  public CartBean createBeanFromCart() {
    Cart cart = getCart();
    CartBean bean = new CartBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String lang = DIContainer.getLocaleContext().getCurrentLanguageCode();
    List<ShopCartBean> cartBeanList = new ArrayList<ShopCartBean>();
    // 商品がひとつも追加されていなかった場合、空で返す
    if (cart.getItemCount() == 0) {
      bean.setDisplayNextButton(false);
      bean.setShopCartBean(cartBeanList);

      return bean;
    } else if (getConfig().getOperatingMode() == OperatingMode.MALL || getConfig().getOperatingMode() == OperatingMode.ONE) {
      // モール一括決済もしくは一店舗決済の場合、決済単位はモール＋予約
      // 注文品取得
      ShopCartBean cartBean = createShopCartBean(getConfig().getSiteShopCode());
      List<CommodityCartBean> commodityList = new ArrayList<CommodityCartBean>();
      List<CommodityCartBean> acceptedGiftList = new ArrayList<CommodityCartBean>();
      BigDecimal claimTotal = BigDecimal.ZERO;
      BigDecimal weightTotal = BigDecimal.ZERO;
      
      Map<OptionalCampaign ,List<CommodityCartBean>> optionalMap = new HashMap<OptionalCampaign ,List<CommodityCartBean>>();
      // A件B元更新功能用
      List<CommodityCartBean> optinalcommodityList = new ArrayList<CommodityCartBean>();
      // A件B元页面显示用
      List<CommodityCartBean> optinalcommodityListPage = new ArrayList<CommodityCartBean>();
      List<OptionalCampaign>  optinalList = service.getOptionalCampaignList();
      
      
      
      for (CartItem item : cart.get()) {
        
        
        // 2012/11/22 促销对应 ob update start
        CommodityCartBean commodity = new CommodityCartBean();
        
        if (CommodityType.GENERALGOODS.longValue().equals(item.getCommodityInfo().getCommodityType())) {
          DiscountCommodity dCommodity = service.getDiscountCommodityByCommodityCode(item.getCommodityCode());
          //历史所有客户购买总数
          Long siteTotalAmount = 0L;
          if (dCommodity != null){
            siteTotalAmount= service.getHistoryBuyAmountTotal(item.getCommodityCode());
            if (siteTotalAmount == null){
              siteTotalAmount = 0L;
            }
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
          if (dCommodity != null && dCommodity.getSiteMaxTotalNum() > siteTotalAmount){
            Long quantity = 0L;
            //限购商品剩余可购买数量
            Long avalibleAmount = dCommodity.getSiteMaxTotalNum() - siteTotalAmount;
            if(avalibleAmount < 0L ){
              avalibleAmount = 0L;
            }
            BigDecimal price1 = item.getRetailPrice();
            BigDecimal price2 = item.getUnitPrice();
            if (dCommodity.getCustomerMaxTotalNum()!=null){
              //当前客户可购买限购商品数
              quantity = dCommodity.getCustomerMaxTotalNum() - historyNum;
              if(quantity < 0L ){
                quantity = 0L;
              }
              if(quantity > avalibleAmount){
                quantity = avalibleAmount;
              }
            }
            if (dCommodity.getDiscountPrice() != null){
              if(StringUtil.hasValue(item.getCommodityInfo().getOriginalCommodityCode()) && item.getCommodityInfo().getCombinationAmount() != null) {
                item.getCommodityInfo().setRetailPrice(BigDecimalUtil.
                    divide(dCommodity.getDiscountPrice(),item.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
                item.getCommodityInfo().setUnitPrice(BigDecimalUtil.
                    divide(dCommodity.getDiscountPrice(),item.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
              } else {
                item.getCommodityInfo().setRetailPrice(dCommodity.getDiscountPrice());
                item.getCommodityInfo().setUnitPrice(dCommodity.getDiscountPrice());
              }
            }
            int totalNum = item.getQuantity();
            if (totalNum <= quantity){
              commodity = createCommodity(item);
              commodity.setGift(false);
              commodity.setIsDiscountCommodity("true");
              commodityList.add(commodity);
              claimTotal = claimTotal.add(commodity.getSubTotal());
            } else {
              if(quantity != 0L){
                item.setQuantity(Integer.parseInt(quantity.toString()));
                commodity = createCommodity(item);
                commodity.setCommoditySalesPrice(item.getCommodityInfo().getDiscountTimePrice());
                commodity.setGift(false);
                commodity.setIsDiscountCommodity("true");
                commodityList.add(commodity);
                claimTotal = claimTotal.add(commodity.getSubTotal());
              }
              if(StringUtil.hasValue(item.getCommodityInfo().getOriginalCommodityCode()) && item.getCommodityInfo().getCombinationAmount() != null) {
                item.getCommodityInfo().setRetailPrice(BigDecimalUtil.divide(price1,item.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
                item.getCommodityInfo().setUnitPrice(BigDecimalUtil.divide(price2,item.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
              } else {
                item.getCommodityInfo().setRetailPrice(price1);
                item.getCommodityInfo().setUnitPrice(price2);
              }

              item.setQuantity(totalNum - Integer.parseInt(quantity.toString()));
              commodity = createCommodity(item);
              commodity.setGift(false);
              commodityList.add(commodity);
              claimTotal = claimTotal.add(commodity.getSubTotal());
            }
            item.getCommodityInfo().setIsDiscountCommodity("true");
            if(StringUtil.hasValue(item.getCommodityInfo().getOriginalCommodityCode()) && item.getCommodityInfo().getCombinationAmount() != null) {
              item.getCommodityInfo().setRetailPrice(BigDecimalUtil.divide(price1,item.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
              item.getCommodityInfo().setUnitPrice(BigDecimalUtil.divide(price2,item.getCommodityInfo().getCombinationAmount(),2,RoundingMode.DOWN));
            } else {
              item.getCommodityInfo().setRetailPrice(price1);
              item.getCommodityInfo().setUnitPrice(price2);
            }
//            item.getCommodityInfo().setRetailPrice(price1);
//            item.getCommodityInfo().setUnitPrice(price2);
            item.setQuantity(totalNum);
          } else {
            commodity = createCommodity(item);
            commodity.setGift(false);
            commodityList.add(commodity);
            claimTotal = claimTotal.add(commodity.getSubTotal());
          }

          weightTotal = weightTotal.add(BigDecimalUtil.multiply(item.getWeight(), item.getQuantity()));
        }
        // 2012/11/22 促销对应 ob update end
        
      }
      
      List<CommodityCartBean> removeList = new ArrayList<CommodityCartBean>();
      for (CommodityCartBean itemBean : commodityList) {
        // 任选商品对应 start
        if(optinalList != null && optinalList.size() > 0){
          boolean optionalFlg = false;
          for (OptionalCampaign oc : optinalList) {
            optionalFlg = false;
            if(oc.getRelatedCommodity().contains("," + itemBean.getCommodityCode() + ",") && !("true").equals(itemBean.getIsDiscountCommodity())) {
              if(optionalMap.get(oc) == null ) {
                List<CommodityCartBean> list = new ArrayList<CommodityCartBean>();
                optionalMap.put(oc, list);
              }
              optionalMap.get(oc).add(itemBean);
              removeList.add(itemBean);
              optionalFlg = true;
              break;
            }
          }
          if (optionalFlg) {
            continue;
          }
        }
        // 任选商品对应 end
      }
      if (removeList.size() > 0) {
        commodityList.removeAll(removeList);
      }
      
      // 任选商品对应
      BigDecimal cheapPrice = BigDecimal.ZERO;
      Map<String ,OptionalCommodity> optionalCommodityMap = new HashMap<String ,OptionalCommodity>();
      List<String> opCodeList = new ArrayList<String>();
      for (OptionalCampaign oc : optionalMap.keySet()) {
        
        CommodityCartBean optionalBean = new CommodityCartBean();
        if (lang.equals("zh-cn")) {
          optionalBean.setCommodityName(oc.getCampaignName());
        } else if (lang.equals("ja-jp")) {
          optionalBean.setCommodityName(oc.getCampaignNameJp());
        } else if (lang.equals("en-us")) {
          optionalBean.setCommodityName(oc.getCampaignNameEn());
        } else {
          optionalBean.setCommodityName(oc.getCampaignName());
        }
        
        optionalBean.setCommodityAmount("optionalFlg");
        optinalcommodityListPage.add(optionalBean);
        Collections.sort(optionalMap.get(oc));
        
        // 任选活动和原商品差价计算
        Long orderLimitNum = oc.getOrderLimitNum();
        Long optionalNum = oc.getOptionalNum();
        
        List<CommodityCartBean>  singleList = new ArrayList<CommodityCartBean>();
        
        for (CommodityCartBean ccb : optionalMap.get(oc)) {
          opCodeList.add(ccb.getCommodityCode());
          for (int i=0; i<Integer.parseInt(ccb.getCommodityAmount()); i++) {
            singleList.add(ccb);
          }
          optinalcommodityListPage.add(ccb);
          optinalcommodityList.add(ccb);
        }
        
        if (singleList.size() >=  optionalNum) {
          
          // 已购买的商品可匹配最多套数
          Long buyTime = singleList.size()/optionalNum;
          // 实际可购买套数
          Long realTime = 0L;
          if (buyTime < orderLimitNum) {
            realTime = buyTime;
          } else {
            realTime = orderLimitNum;
          }
          
          // 按套餐商品价格购买的商品
          List<CommodityCartBean>  realSingleList = new ArrayList<CommodityCartBean>();
          for (int i = 0 ; i < realTime*optionalNum ; i++) {
            realSingleList.add(singleList.get(i));
          }
          // 算出差价 封装optionalCommodityMap
          cheapPrice = getOptional(cheapPrice,realSingleList,optionalCommodityMap,oc);
        }
        
      }
      cart.setOptionalCheapPrice(cheapPrice);
      cart.setOptionalCommodityMap(optionalCommodityMap);
      cart.setOpCommodityCodeList(opCodeList);
      cartBean.setOptionalCommodityList(optinalcommodityList);
      cartBean.setOptionalCommodityListPage(optinalcommodityListPage);
      
      if (commodityList.size() > 0 || optinalcommodityList.size() > 0) {
        cartBean.setCommodityCartBean(commodityList);
        cartBean.setDeliveryShopCode("");
        cartBean.setReserveSkuCode("");
        
        for (CommodityCartBean commodity : commodityList) {
          if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
            for (GiftBean giftBean : commodity.getGiftList()) {
              claimTotal = claimTotal.add(giftBean.getSubTotal());
              weightTotal = weightTotal.add(BigDecimalUtil.multiply(giftBean.getWeight(), NumUtil.toLong(giftBean.getGiftAmount(), 0L)));
            }
          }
        }
        cartBean.setShopClaimTotal(claimTotal.subtract(cheapPrice));
        cartBean.setWeightTotal(NumUtil.parseBigDecimalWithoutZero(weightTotal));
        
        cartBeanList.add(cartBean);
      }
      
      // 2012/11/22 促销对应 ob add start
      
      // 多重促销活动信息取得
      createOtherGift(cartBean, true);
      
      for (CartItem item : cart.get()) {
        CommodityCartBean commodity = new CommodityCartBean();
        if (CommodityType.GIFT.longValue().equals(item.getCommodityInfo().getCommodityType())) {
          commodity = createAcceptedGift(item);
          commodity.setGift(true);
          acceptedGiftList.add(commodity);
          weightTotal = weightTotal.add(BigDecimalUtil.multiply(item.getWeight(), item.getQuantity()));
        }
      }

      if (acceptedGiftList.size() > 0) {
        cartBean.setAcceptedGiftBean(acceptedGiftList);
        cartBean.setWeightTotal(NumUtil.parseBigDecimalWithoutZero(weightTotal));
      }
      // 2012/11/22 促销对应 ob add end

    } else if (getConfig().getOperatingMode() == OperatingMode.SHOP) {
      // ショップ個別決済の場合、決済単位はショップ＋予約
      // 注文品取得
      for (String shopCode : cart.getShopCodeList()) {
        ShopCartBean cartBean = createShopCartBean(shopCode);
        List<CommodityCartBean> commodityList = new ArrayList<CommodityCartBean>();
        BigDecimal claimTotal = BigDecimal.ZERO;
        for (CartItem item : cart.get(shopCode)) {
          CommodityCartBean commodity = createCommodity(item);
          commodityList.add(commodity);
          claimTotal = claimTotal.add(commodity.getSubTotal());
        }
        if (commodityList.size() > 0) {
          cartBean.setCommodityCartBean(commodityList);
          cartBean.setDeliveryShopCode("");
          cartBean.setReserveSkuCode("");
          cartBean.setShopClaimTotal(claimTotal);
          cartBeanList.add(cartBean);
        }
      }

    }
    // 予約品取得
    for (CartItem item : cart.getReserve()) {
      String shopCode = "";
      if (getConfig().getOperatingMode() == OperatingMode.SHOP) {
        shopCode = item.getShopCode();
      } else {
        shopCode = getConfig().getSiteShopCode();
      }
      ShopCartBean reserveBean = createShopCartBean(shopCode);
      CommodityCartBean reserveCommodity = createCommodity(item);
      List<CommodityCartBean> reserveCommodityList = new ArrayList<CommodityCartBean>();
      reserveCommodityList.add(reserveCommodity);
      reserveBean.setCommodityCartBean(reserveCommodityList);
      reserveBean.setDeliveryShopCode(reserveCommodity.getShopCode());
      reserveBean.setReserveSkuCode(reserveCommodity.getSkuCode());
      reserveBean.setShopClaimTotal(reserveCommodity.getSubTotal());

      cartBeanList.add(reserveBean);
    }

    // 次へボタンを表示するに設定
    bean.setDisplayNextButton(true);

    bean.setShopCartBean(cartBeanList);

    return bean;
  }
  
  private BigDecimal getOptional(BigDecimal cheapPrice,List<CommodityCartBean>  realSingleList
                                                                ,Map<String ,OptionalCommodity> optionalCommodityMap,OptionalCampaign oc) {
    BigDecimal partTotalPrice = BigDecimal.ZERO;
    
    BigDecimal cpTotal = BigDecimal.ZERO;
    BigDecimal cp1 = BigDecimal.ZERO;
    BigDecimal cp2 = BigDecimal.ZERO;
    List<CommodityCartBean> list = new ArrayList<CommodityCartBean>();
    for (int i=0 ; i< realSingleList.size() ; i++) {
      partTotalPrice = partTotalPrice.add(realSingleList.get(i).getCommoditySalesPrice());
      list.add(realSingleList.get(i));
      if ((i+1)%oc.getOptionalNum() == 0) {
        if (BigDecimalUtil.isAbove(partTotalPrice, oc.getOptionalPrice())) {
          // 当前套的差价
          BigDecimal curCheapPrice = partTotalPrice.subtract(oc.getOptionalPrice());
          // 加入到总差价
          cheapPrice = cheapPrice.add(curCheapPrice);
          
          // 封装MAP 
          for(int j=0 ; j< oc.getOptionalNum()-1 ; j++){
            cp1 = list.get(j).getCommoditySalesPrice().multiply(curCheapPrice).divide(partTotalPrice, 2);
            cp1 = cp1.setScale(2, RoundingMode.HALF_UP);
            cpTotal = cpTotal.add(cp1);
            createOptional(oc,list.get(j),cp1,optionalCommodityMap);
          }
          cp2 = curCheapPrice.subtract(cpTotal);
          createOptional(oc,list.get(list.size()-1),cp2,optionalCommodityMap);
          
        }
        cpTotal = BigDecimal.ZERO;
        partTotalPrice = BigDecimal.ZERO;
        list.clear();
      }
    }
    return cheapPrice;
  }

  private OptionalCommodity createOptional(OptionalCampaign oc,CommodityCartBean cartBean,BigDecimal cheapPrice,Map<String ,OptionalCommodity> optionalCommodityMap){
    OptionalCommodity commodity = new OptionalCommodity();
    commodity.setCommodityCode(cartBean.getCommodityCode());
    commodity.setCheapPrice(cheapPrice);
    commodity.setCommodityAmount(1L);
    commodity.setOptionalCode(oc.getCampaignCode());
    commodity.setOptionalNameCn(oc.getCampaignName());
    commodity.setOptionalNameEn(oc.getCampaignNameEn());
    commodity.setOptionalNameJp(oc.getCampaignNameJp());
    commodity.setRealPrice(cartBean.getCommoditySalesPrice());
    
    if(optionalCommodityMap.get(commodity.getCommodityCode()+":"+cheapPrice) != null) {
      Long oldAmount = optionalCommodityMap.get(commodity.getCommodityCode()+":"+cheapPrice).getCommodityAmount();
      optionalCommodityMap.get(commodity.getCommodityCode()+":"+cheapPrice).setCommodityAmount(oldAmount + 1);
    } else {
      optionalCommodityMap.put(commodity.getCommodityCode()+":"+cheapPrice, commodity);
    }
    
    return commodity;
  }
  
  
  /**
   * 決済ショップコードを元にShopCartBeanを1件生成します。
   * 
   * @param shopCode
   *          決済ショップコード
   * @return cartBean
   */
  private ShopCartBean createShopCartBean(String shopCode) {
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
    ShopCartBean cartBean = new ShopCartBean();

    Shop paymentShop = shopService.getShop(shopCode);
    cartBean.setShopCode(paymentShop.getShopCode());
    cartBean.setShopName(paymentShop.getShopName());

    return cartBean;
  }

  /**
   * カートアイテムを元に1商品情報を生成します。
   * 
   * @param item
   * @return commodity
   */
  private CommodityCartBean createCommodity(CartItem item) {
    CommodityCartBean commodity = new CommodityCartBean();
    commodity.setShopCode(item.getShopCode());
    commodity.setCommodityAmount(Integer.toString(item.getQuantity()));
    commodity.setCommodityCode(item.getCommodityCode());
    commodity.setCommodityName(item.getCommodityName());
    commodity.setCommodityUnitPrice(item.getUnitPrice());
    commodity.setCommoditySalesPrice(item.getRetailPrice());
    commodity.setReserve(item.isReserve());
    commodity.setGiftName(item.getGiftName());
    commodity.setGiftOptionCode(item.getGiftCode());
    commodity.setGiftPrice(item.getGiftPrice());
    commodity.setSkuCode(item.getSkuCode());
    commodity.setStandardDetail1Name(item.getStandardDetail1Name());
    commodity.setStandardDetail2Name(item.getStandardDetail2Name());
    if(StringUtil.hasValue(item.getOriginalCommodityCode())){
      commodity.setOriginalCommodityCode(item.getOriginalCommodityCode());
      commodity.setCombinationAmount(item.getCombinationAmount());
    }

      

    commodity.setSubTotal(BigDecimalUtil.multiply(item.getRetailPrice().add(item.getGiftPrice()), item.getQuantity()));
    commodity.setDiscountPrice(BigDecimalUtil.subtract(item.getUnitPrice(), item.getRetailPrice()));
    BigDecimal discountRate = BigDecimalUtil.multiply(BigDecimal.ONE, 100);
    if (item.getUnitPrice() != null && BigDecimalUtil.isAbove(item.getUnitPrice(), BigDecimal.ZERO) ) {
      discountRate = BigDecimalUtil.multiply(100, BigDecimalUtil.subtract(BigDecimal.ONE, BigDecimalUtil.divide(item
          .getRetailPrice(), item.getUnitPrice(), 2, RoundingMode.UP)));
    }
    commodity.setDiscountRate(NumUtil.toString(discountRate.longValue()));
    commodity.setDisplayDiscountRate(discountRate.intValue() > DIContainer.getWebshopConfig().getDisplayDiscountRateLimit());
    commodity.setWeight(NumUtil.parseBigDecimalWithoutZero(BigDecimalUtil.multiply(item.getWeight(), item.getQuantity())));
    commodity.setStockManagementType(item.getCommodityInfo().getStockManagementType());
    
    // 2012/11/22 促销对应 ob add start
    commodity.setSetCommodity(false);
    if (SetCommodityFlg.OBJECTIN.longValue().equals(item.getCommodityInfo().getSetCommodityFlg())) {
      List<CompositionBean> compositionList = new ArrayList<CompositionBean>();
      for (CompositionItem compositionItem : item.getCommodityInfo().getCompositionList()) {
        CompositionBean composition = new CompositionBean();
        composition.setShopCode(compositionItem.getShopCode());
        composition.setCommodityCode(compositionItem.getCommodityCode());
        composition.setCommodityName(compositionItem.getCommodityName());
        composition.setSkuCode(compositionItem.getSkuCode());
        composition.setStandardDetail1Name(compositionItem.getStandardDetail1Name());
        composition.setStandardDetail2Name(compositionItem.getStandardDetail2Name());
        compositionList.add(composition);
      }
      commodity.setSkuCodeOfSet(item.getSkuCode().split(":")[0]);
      commodity.setSetCommodity(true);
      commodity.setCompositionList(compositionList);
    }
    
    if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
      List<GiftBean> giftList = new ArrayList<GiftBean>();
//      CampaignMainDao camDao = DIContainer.getDao(CampaignMainDao.class);
      
      for (GiftItem giftItem : item.getCommodityInfo().getGiftList()) {
        // 20140805 hdh update start
//        CampaignMain camMain = camDao.load(giftItem.getCampaignCode());
        Long giftAmount = NumUtil.toLong(giftItem.getQuantity()+"");
//        if (camMain.getGiftAmount() != null ) {
//          giftAmount = NutilgiftItem.getQuantity();
//        }
        //20140805 hdh update end
        GiftBean gift = new GiftBean();
        gift.setShopCode(giftItem.getShopCode());
        gift.setGiftCode(giftItem.getGiftCode());
        gift.setGiftSkuCode(giftItem.getGiftSkuCode());
        gift.setGiftName(giftItem.getGiftName());
        gift.setCampaignCode(giftItem.getCampaignCode());
        gift.setCampaignName(giftItem.getCampaignName());
        gift.setStandardDetail1Name(giftItem.getStandardDetail1Name());
        gift.setStandardDetail2Name(giftItem.getStandardDetail2Name());
        gift.setGiftSalesPrice(giftItem.getRetailPrice());
        gift.setGiftAmount(giftAmount.toString());
        gift.setSubTotal(BigDecimalUtil.multiply(giftItem.getRetailPrice(), giftAmount));
        gift.setWeight(giftItem.getWeight());
        gift.setSubTotalWeight(NumUtil.parseBigDecimalWithoutZero(BigDecimalUtil.multiply(giftItem.getWeight(), giftAmount)));
        
        giftList.add(gift);
      }
      commodity.setGiftList(giftList);
    }
    // 2012/11/22 促销对应 ob add end
    return commodity;
  }
  
  // 2012/11/22 促销对应 ob add start
  /**
   * 多重促销活动赠品一览生成
   */
  private void createOtherGift(ShopCartBean cartBean, boolean resetBean) {

    // 可选择的多重促销活动的赠品
    Set<String> acceptableGiftCodeSet = getAcceptableGiftCodeSet(cartBean); 
  
    if (resetBean && getCart().getItemCountExceptGift() > 0) {
      // 可选择的多重促销活动的赠品信息保存到bean中
      setOtherGiftInfo(cartBean, acceptableGiftCodeSet);
    }
    
 
  }
  
  /**
   * 验证指定的多重促销活动的赠品是否可领取
   * @param true:可领取 false:不可领取
   */
  public boolean checkGiftAcceptable(String campaignCode, String commodityCode) {
    boolean result = false;
    Set<String> acceptableGiftCodeSet = getAcceptableGiftCodeSet(getBean().getShopCartBean().get(0));
    
    if (acceptableGiftCodeSet != null && !acceptableGiftCodeSet.isEmpty() && acceptableGiftCodeSet.contains(campaignCode + "~" + commodityCode)) {
      result = true;
    }
    
    return result;
  }
  /**
   * 已领取多重促销活动赠品信息生成
   */
  private CommodityCartBean createAcceptedGift(CartItem item) {
    CommodityCartBean acceptedGift = new CommodityCartBean();
    acceptedGift.setShopCode(item.getShopCode());
    acceptedGift.setCommodityAmount(Integer.toString(item.getQuantity()));
    acceptedGift.setCommodityCode(item.getCommodityCode());
    acceptedGift.setCommodityName(item.getCommodityName());
    acceptedGift.setCampaignCode(item.getCommodityInfo().getMultipleCampaignCode());
    acceptedGift.setCampaignName(item.getCommodityInfo().getMultipleCampaignName());
    acceptedGift.setCommoditySalesPrice(item.getRetailPrice());
    acceptedGift.setSkuCode(item.getSkuCode().split("~")[1]);
    acceptedGift.setStandardDetail1Name(item.getStandardDetail1Name());
    acceptedGift.setStandardDetail2Name(item.getStandardDetail2Name());
    acceptedGift.setSubTotal(BigDecimalUtil.multiply(item.getRetailPrice(), item.getQuantity()));
    acceptedGift.setWeight(NumUtil.parseBigDecimalWithoutZero(BigDecimalUtil.multiply(item.getWeight(), item.getQuantity())));

    return acceptedGift;
  }
  
  private void setOtherGiftInfo(ShopCartBean cartBean, Set<String> otherGiftCodeSet) {
    List<CommodityCartBean> giftBeanList = new ArrayList<CommodityCartBean>();
    List<CommodityCartBean> campaignInfoList = new ArrayList<CommodityCartBean>();
    List<String> campaignCodeList = new ArrayList<String>();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CampaignMainDao mainDao = DIContainer.getDao(CampaignMainDao.class);
    
    String shopCode = getCart().getShopCodeList().get(0);
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    
    for (String giftCode : otherGiftCodeSet) {
      String campaignCode = giftCode.split("~")[0];
      String commodityCode = giftCode.split("~")[1];
      CampaignMain main = mainDao.load(campaignCode);
      
      CommodityInfo commodityInfo = service.getCommodityInfo(shopCode, commodityCode);
      if (commodityInfo != null && commodityInfo.getHeader() != null && commodityInfo.getDetail() != null) {
        CommodityHeader header = commodityInfo.getHeader();
        CommodityDetail detail = commodityInfo.getDetail();
        CommodityCartBean giftBean = new CommodityCartBean();
        
        giftBean.setShopCode(header.getShopCode());
        giftBean.setCommodityAmount(BigDecimal.ONE.toString());
        giftBean.setCommodityCode(header.getCommodityCode());
        
        giftBean.setCommodityUnitPrice(detail.getUnitPrice());
        giftBean.setCommoditySalesPrice(detail.getUnitPrice());
        giftBean.setSkuCode(detail.getSkuCode());
        
        if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
          giftBean.setCommodityName(header.getCommodityName());
          giftBean.setStandardDetail1Name(detail.getStandardDetail1Name());
          giftBean.setStandardDetail2Name(detail.getStandardDetail2Name());
          if (main != null) {
            giftBean.setCampaignName(main.getCampaignName());
            giftBean.setCampaignCode(main.getCampaignCode());
          }
          
        } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
          giftBean.setCommodityName(header.getCommodityNameJp());
          giftBean.setStandardDetail1Name(detail.getStandardDetail1NameJp());
          giftBean.setStandardDetail2Name(detail.getStandardDetail2NameJp());
          if (main != null) {
            giftBean.setCampaignName(main.getCampaignNameJp());
            giftBean.setCampaignCode(main.getCampaignCode());
          }
        } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
          giftBean.setCommodityName(header.getCommodityNameEn());
          giftBean.setStandardDetail1Name(detail.getStandardDetail1NameEn());
          giftBean.setStandardDetail2Name(detail.getStandardDetail2NameEn());
          if (main != null) {
            giftBean.setCampaignName(main.getCampaignNameEn());
            giftBean.setCampaignCode(main.getCampaignCode());
          }
        }

        CommodityCartBean campaignBean = new CommodityCartBean();
        campaignBean.setCampaignCode(main.getCampaignCode());
        campaignBean.setCampaignName(main.getCampaignName());
        if (!campaignCodeList.contains(main.getCampaignCode())) {
        	campaignInfoList.add(campaignBean);
        	campaignCodeList.add(main.getCampaignCode());
        }
        giftBeanList.add(giftBean);
      }
      
    }
    cartBean.setCampaignInfoList(campaignInfoList);
    cartBean.setGiftBean(giftBeanList);
  }
  
  private Set<String> getAcceptableGiftCodeSet(ShopCartBean cartBean) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    Cart cart = getCart();
    Set<String> otherGiftCodeSet = new HashSet<String>(); // 多重促销活动的所有赠品
    Set<String> acceptableGiftCodeSet = new HashSet<String>(); // 可选择的多重促销活动的赠品
    String shopCode = cart.getShopCodeList().get(0);
   
    List<CampaignInfo> campaignInfoList = service.getMultipleGiftCampaignInfo(DateUtil.getSysdate(), cartBean.getShopClaimTotal(), getSessionContainer().getMedia());
    if (campaignInfoList != null && campaignInfoList.size() > 0) {
      
     Set<String> commodityCodeSet = new HashSet<String>(); // 购物车中商品编号一览
     Set<String> acceptedGiftCodeSet = new HashSet<String>(); // 购物车中已领取赠品编号一览
     
      for (CartItem item : getCart().get()) {
        if (CommodityType.GENERALGOODS.longValue().equals(item.getCommodityInfo().getCommodityType())) {
          commodityCodeSet.add(item.getCommodityCode());
        }
      }
      
      for (CampaignInfo campaignInfo : campaignInfoList) {

        Set<String> attributeValue = new HashSet<String>(); // 对象商品编号List
        
        if (campaignInfo.getConditionList() != null && campaignInfo.getConditionList().size() > 0 
            && campaignInfo.getConditionList().get(0).getAttributrValue() != null) {
          
          attributeValue.addAll(Arrays.asList(campaignInfo.getConditionList().get(0).getAttributrValue().split(",")));

          if (attributeValue != null && StringUtil.hasValue(campaignInfo.getConditionList().get(0).getAttributrValue().replace(",", "").trim())) {
            if (CampaignConditionFlg.CAMPAIGN_CONDITION_CONTAIN.longValue().equals(campaignInfo.getConditionList().get(0).getCampaignConditionFlg())) {
              // 对象商品区分：包含
              for (String commodityCode : commodityCodeSet) {
                if (attributeValue.contains(commodityCode)) {
                  if (campaignInfo.getCampaignDoings() != null && campaignInfo.getCampaignDoings().getAttributrValue() != null) {
                    
                    if (StringUtil.hasValue(campaignInfo.getCampaignDoings().getAttributrValue().replace(",", "").trim())) {
                      String[] attributeValueTemp = campaignInfo.getCampaignDoings().getAttributrValue().split(","); // 促销行为表中的赠品List
                      for (int i = 0; i < attributeValueTemp.length; i++) {
                        otherGiftCodeSet.add(campaignInfo.getCampaignMain().getCampaignCode() + "~" + attributeValueTemp[i]);
                      }
                    }
 
                    break;
                  }
                }
              }
            } else {
              // 对象商品区分：仅有
              if (attributeValue.containsAll(commodityCodeSet)) {
                if (campaignInfo.getCampaignDoings() != null && campaignInfo.getCampaignDoings().getAttributrValue() != null) {
                  
                  if (StringUtil.hasValue(campaignInfo.getCampaignDoings().getAttributrValue().replace(",", "").trim())) {
                    String[] attributeValueTemp = campaignInfo.getCampaignDoings().getAttributrValue().split(","); // 促销行为表中的赠品List
                    for (int i = 0; i < attributeValueTemp.length; i++) {
                      otherGiftCodeSet.add(campaignInfo.getCampaignMain().getCampaignCode() + "~" + attributeValueTemp[i]);
                    }
                  }
                }
              }
            }
          } else {
            // 关联商品未设置时，将所有赠品放入
            
            if (campaignInfo.getCampaignDoings() != null && campaignInfo.getCampaignDoings().getAttributrValue() != null) {
              
              if (StringUtil.hasValue(campaignInfo.getCampaignDoings().getAttributrValue().replace(",", "").trim())) {
                String[] attributeValueTemp = campaignInfo.getCampaignDoings().getAttributrValue().split(","); // 促销行为表中的赠品List
                for (int i = 0; i < attributeValueTemp.length; i++) {
                  otherGiftCodeSet.add(campaignInfo.getCampaignMain().getCampaignCode() + "~" + attributeValueTemp[i]);
                }
              }
            }
          }
        }
      }
      
      if (otherGiftCodeSet != null && !otherGiftCodeSet.isEmpty()) {
        
        // 将购物车中不满足当前多关联促销活动的赠品从购物车中移除
        for (CartItem item : cart.get()) {
          if (CommodityType.GIFT.longValue().equals(item.getCommodityType())) {
            if (!otherGiftCodeSet.contains(item.getCommodityInfo().getMultipleCampaignCode() + "~" + item.getCommodityCode())) {
              cart.remove(item.getShopCode(), item.getSkuCode(), item.getGiftCode(), getLoginInfo().getCustomerCode());
            } else {
              acceptedGiftCodeSet.add(item.getCommodityInfo().getMultipleCampaignCode() + "~" + item.getCommodityCode());
            }
          }
        }
       
        // 可领取的多重促销活动赠品信息生成
        Map<String, Long> cartGiftTotalAmount = getGiftTotalAmountFromCart(); // 购物车中各赠品的数量信息

        for (String otherGiftCode : otherGiftCodeSet) {
          if (acceptedGiftCodeSet == null || !acceptedGiftCodeSet.contains(otherGiftCode))  {
            if (cartGiftTotalAmount.containsKey(otherGiftCode.split("~")[1])) {
              if (catalogService.isAvailableGift(shopCode, otherGiftCode.split("~")[1], 
                  new Long(cartGiftTotalAmount.get(otherGiftCode.split("~")[1]) + 1L).intValue(), true).equals(CommodityAvailability.AVAILABLE)) {
                acceptableGiftCodeSet.add(otherGiftCode);
              }
            } else {
              if (catalogService.isAvailableGift(shopCode, otherGiftCode.split("~")[1], 1, true).equals(CommodityAvailability.AVAILABLE)) {
                acceptableGiftCodeSet.add(otherGiftCode);
              }
            }
          }
        }
      } else {
        
        // 将购物车中多关联促销活动的赠品从购物车中移除
        cart.clearAcceptedGift();
      }
    } else {
      
      // 将购物车中多关联促销活动的赠品从购物车中移除
      cart.clearAcceptedGift();
      
    }
    
    return acceptableGiftCodeSet;
  
  }
  
  /**
   * 统计购物车中各赠品的数量
   * @return 购物车中各赠品的数量信息
   */
  public Map<String, Long> getGiftTotalAmountFromCart() {
    Map<String, Long> cartGiftTotalAmount = new HashMap<String, Long>(); // 购物车中各赠品的数量信息
    Map<String, String> cartGiftName = new HashMap<String, String>(); // 购物车中各赠品的名称信息

    // 统计购物车中各赠品的数量
    for (CartItem item : getCart().get()) {
      if (CommodityType.GIFT.longValue().equals((item.getCommodityInfo().getCommodityType()))) {
        if (cartGiftTotalAmount.containsKey(item.getCommodityCode())) {
          cartGiftTotalAmount.put(item.getCommodityCode(), cartGiftTotalAmount.get(item.getCommodityCode()) + item.getQuantity());
        } else {
          cartGiftTotalAmount.put(item.getCommodityCode(), (long) item.getQuantity());
        }
        
        String displayName = getDisplayName(item.getCommodityName(), item.getStandardDetail1Name(), item.getStandardDetail2Name());
        cartGiftName.put(item.getCommodityCode(), displayName);
      } else {
        if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
          for (GiftItem giftItem : item.getCommodityInfo().getGiftList()) {
            if (cartGiftTotalAmount.containsKey(giftItem.getGiftCode())) {
              cartGiftTotalAmount.put(giftItem.getGiftCode(), cartGiftTotalAmount.get(giftItem.getGiftCode()) + giftItem.getQuantity());
            } else {
              cartGiftTotalAmount.put(giftItem.getGiftCode(), (long) giftItem.getQuantity());
            }
            
            String displayName = getDisplayName(giftItem.getGiftName(), giftItem.getStandardDetail1Name(), giftItem.getStandardDetail2Name());
            cartGiftName.put(giftItem.getGiftCode(), displayName);
          }
        }
      }
    }
    
    getBean().setCartGiftName(cartGiftName);
    return cartGiftTotalAmount;
  }
  
  /**
   * 统计Bean中各赠品的数量
   * @return 购物车中各赠品的数量信息
   */
  public Map<String, Long> getGiftTotalAmountFromBean() {
    Map<String, Long> cartGiftTotalAmount = new HashMap<String, Long>(); // 购物车中各赠品的数量信息
    Map<String, String> cartGiftName = new HashMap<String, String>(); // 购物车中各赠品的名称信息

    // 统计购物车中各赠品的数量
    for (ShopCartBean shopBean : getBean().getShopCartBean()) {
      // 20140807 hdh add start 赠品重新计算
      giftReset(shopBean);
      // 20140808 hdh add end;
      
      // 通常商品/套餐商品的赠品
      for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
          for (GiftBean giftItem : commodityBean.getGiftList()) {
            if (cartGiftTotalAmount.containsKey(giftItem.getGiftCode())) {
              cartGiftTotalAmount.put(giftItem.getGiftCode(), cartGiftTotalAmount.get(giftItem.getGiftCode()) + NumUtil.toLong(giftItem.getGiftAmount(), 0L));
            } else {
              cartGiftTotalAmount.put(giftItem.getGiftCode(), NumUtil.toLong(giftItem.getGiftAmount(), 0L));
            }
            
            String displayName = getDisplayName(giftItem.getGiftName(), giftItem.getStandardDetail1Name(), giftItem.getStandardDetail2Name());
            cartGiftName.put(giftItem.getGiftCode(), displayName);
          }
        }
      }
      
      // 已领取的多重促销活动的赠品
      if (shopBean.getAcceptedGiftBean() != null && shopBean.getAcceptedGiftBean().size() > 0) {
        for (CommodityCartBean acceptedGiftBean : shopBean.getAcceptedGiftBean()) {
          if (cartGiftTotalAmount.containsKey(acceptedGiftBean.getCommodityCode())) {
            cartGiftTotalAmount.put(acceptedGiftBean.getCommodityCode(), 
                cartGiftTotalAmount.get(acceptedGiftBean.getCommodityCode()) + NumUtil.toLong(acceptedGiftBean.getCommodityAmount(), 0L));
          } else {
            cartGiftTotalAmount.put(acceptedGiftBean.getCommodityCode(), NumUtil.toLong(acceptedGiftBean.getCommodityAmount(), 0L));
          }
          
          String displayName = getDisplayName(acceptedGiftBean.getCommodityName(), acceptedGiftBean.getStandardDetail1Name(), acceptedGiftBean.getStandardDetail2Name());
          cartGiftName.put(acceptedGiftBean.getCommodityCode(), displayName);
        }
      }
    } 
       
    getBean().setCartGiftName(cartGiftName);
    return cartGiftTotalAmount;
  }
  
  /**
   * 统计Bean中各通常商品的数量
   * @return 购物车中各通常商品的数量信息
   */
  public Map<String, Long> getCommodityTotalAmountFromBean() {
    Map<String, Long> cartCartCommodityTotalAmount = new HashMap<String, Long>(); // 购物车中各通常商品的数量信息
    Map<String, String> cartCommodityName = new HashMap<String, String>(); // 购物车中各通常商品的名称信息
    for (ShopCartBean shopBean : getBean().getShopCartBean()) {
      for (CommodityCartBean commodityBean : shopBean.getCommodityCartBean()) {
        if (commodityBean.getCompositionList() != null && commodityBean.getCompositionList().size() > 0) {
          for (CompositionBean composition : commodityBean.getCompositionList()) {
            if (cartCartCommodityTotalAmount.containsKey(composition.getSkuCode())) {
              cartCartCommodityTotalAmount.put(composition.getSkuCode(), 
                  cartCartCommodityTotalAmount.get(composition.getSkuCode()) + NumUtil.toLong(commodityBean.getCommodityAmount(), 0L));
            } else {
              cartCartCommodityTotalAmount.put(composition.getSkuCode(), NumUtil.toLong(commodityBean.getCommodityAmount(), 0L));
            }

            String displayCommodityName = getDisplayName(composition.getCommodityName(), composition.getStandardDetail1Name(), composition.getStandardDetail2Name());
            cartCommodityName.put(composition.getSkuCode(), displayCommodityName);
          }
        } else {
          if (cartCartCommodityTotalAmount.containsKey(commodityBean.getSkuCode())) {
            cartCartCommodityTotalAmount.put(commodityBean.getSkuCode(), 
                cartCartCommodityTotalAmount.get(commodityBean.getSkuCode()) + NumUtil.toLong(commodityBean.getCommodityAmount(), 0L));
          } else {
            cartCartCommodityTotalAmount.put(commodityBean.getSkuCode(), NumUtil.toLong(commodityBean.getCommodityAmount(), 0L));
          }

          String displayCommodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail2Name());
          cartCommodityName.put(commodityBean.getSkuCode(), displayCommodityName);
        }
      }
    }

    getBean().setCartCommodityName(cartCommodityName);
    return cartCartCommodityTotalAmount;
  }
  // 2012/11/22 促销对应 ob add end

  // 10.1.3 10034 追加 ここから
  /**
   * 動作モード(ONE、MALL、SHOP)に応じたエラーメッセージを返します。 (一店舗版の場合はエラーメッセージにショップ名を含まないようにする。)
   * 
   * @param shopName
   *          formedMessgage
   * @return 動作モード(ONE、MALL、SHOP)に応じたエラーメッセージ
   */
  protected String getLimitErrorMessageOfEachMode(String shopName, String formedMessage) {
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      return formedMessage;
    } else {
      return shopName + ":" + formedMessage;
    }
  }
  // 10.1.3 10034 追加 ここまで

  
  /** 赠品重新计算 **/
  public void giftReset(ShopCartBean shopBean) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    List<CommodityCartBean> items = shopBean.getCommodityCartBean();

    // 购物车中商品符合赠品促销的活动编号
    List<String> compaignCodes = new ArrayList<String>();
    for (CommodityCartBean item : items) {
      if (item.getGiftList() != null && item.getGiftList().size() > 0) {
        String compaignCode = item.getGiftList().get(0).getCampaignCode();
        if (StringUtil.hasValue(compaignCode) && !compaignCodes.contains(compaignCode)) {
          compaignCodes.add(compaignCode);
        }
      }
    }

    for (String code : compaignCodes) {
      // 赠品活动中的关联商品
      CampaignLine campaignLine = communicationService.loadCampaignLine(code);
      if (campaignLine == null) {
        continue;
      }
      List<CampaignCondition> cList = campaignLine.getConditionList();
      String commodityStr = ""; // 关联商品编号接连串
      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          CampaignCondition condition = cList.get(i);
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
          if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
        }
      }
      if (StringUtil.isNullOrEmpty(commodityStr)) {
        continue;
      }

      String firstCommdityCode = null; // 取符合活动的第一个商品编号
      int totalCommodityNum = 0; // 符合当前活动的商品总数量
      for (CommodityCartBean item : items) {
        if (item.getGiftList() == null || item.getGiftList().size() == 0
            || !code.equals(item.getGiftList().get(0).getCampaignCode())) {
          continue;
        }
        // 只取第一个商品

        String commodiyCode = item.getCommodityCode();
        String[] commodityCodes = commodityStr.split(",");
        for (String curCode : commodityCodes) {
          if (StringUtil.isNullOrEmpty(curCode)) {
            continue;
          }
          if (curCode.equals(commodiyCode)) {
            totalCommodityNum += NumUtil.toLong(item.getCommodityAmount());

            if (firstCommdityCode == null) {
              firstCommdityCode = item.getCommodityCode();
            } else {
              // 清空后面的商品赠品信息
              item.setGiftList(null);
              // item.setCampaignName(null);
              // item.setCampaignCode(null);
              // item.setGiftName(null);
              item.setGiftOptionCode("");
            }
          }
        }
      }
      Long giftNum   = 0L;
      //如果最小购买数为0则赠品最多只送活动中的赠品数量  20140806
      if("0".equals(NumUtil.toString(campaignLine.getCampaignMain().getMinCommodityNum()))){
        giftNum = campaignLine.getCampaignMain().getGiftAmount();
      }else{
        giftNum = (totalCommodityNum / campaignLine.getCampaignMain().getMinCommodityNum())
        * campaignLine.getCampaignMain().getGiftAmount();
      }
      // 重置赠品数量

      for (CommodityCartBean item : items) {
        if (StringUtil.hasValue(firstCommdityCode) && firstCommdityCode.equals(item.getCommodityCode())) {

          if (giftNum.intValue() != 0 &&item.getGiftList()!=null) {
            for (GiftBean gift : item.getGiftList()) {
              gift.setGiftAmount(NumUtil.toString(giftNum));
            }
          } else {
            item.setGiftList(null);
            item.setCampaignName(null);
            item.setCampaignCode(null);
            item.setGiftName(null);
            item.setGiftOptionCode("");
          }

          break;
        }

      }

    }

  }

  /** 赠品重新计算 **/
  public void giftReset(Cart cart, String shopCode) {

    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    List<CartItem> items = cart.get();

    // 购物车中商品符合赠品促销的活动编号
    List<String> compaignCodes = new ArrayList<String>();
    for (CartItem item : items) {
      if (item.getCommodityInfo().getGiftList() != null && item.getCommodityInfo().getGiftList().size() > 0) {
        String compaignCode = item.getCommodityInfo().getGiftList().get(0).getCampaignCode();
        if (StringUtil.hasValue(compaignCode) && !compaignCodes.contains(compaignCode)) {
          compaignCodes.add(compaignCode);
        }
      }
    }

    for (String code : compaignCodes) {
      // 赠品活动中的关联商品
      CampaignLine campaignLine = communicationService.loadCampaignLine(code);
      if (campaignLine == null) {
        continue;
      }
      List<CampaignCondition> cList = campaignLine.getConditionList();
      String commodityStr = ""; // 关联商品编号接连串
      if (cList != null && cList.size() > 0) {
        for (int i = 0; i < cList.size(); i++) {
          CampaignCondition condition = cList.get(i);
          if (NewCampaignConditionType.ORDER_COMMODITY.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
          if (NewCampaignConditionType.COMMODITY_RELATED.longValue().equals(condition.getCampaignConditionType())) {
            commodityStr = cList.get(i).getAttributrValue();
            break;
          }
        }
      }
      if (StringUtil.isNullOrEmpty(commodityStr)) {
        continue;
      }

      String firstCommdityCode = null; // 取符合活动的第一个商品编号
      int totalCommodityNum = 0; // 符合当前活动的商品总数量
      for (CartItem item : items) {

        // 只取第一个商品
        String commodiyCode = item.getCommodityCode();
        String[] commodityCodes = commodityStr.split(",");
        for (String curCode : commodityCodes) {
          if (StringUtil.isNullOrEmpty(curCode)) {
            continue;
          }
          if (curCode.equals(commodiyCode)) {
            totalCommodityNum += item.getQuantity();

            if (firstCommdityCode == null) {
              firstCommdityCode = item.getCommodityCode();
            } else {
              // 清空后面的商品赠品信息
              CartItemImpl impl = (CartItemImpl) item;
              impl.getCommodityInfo().setGiftList(null);
              // impl.getCommodityInfo().setGiftCode(null);
              // impl.setGiftCode(null);
              // impl.setGiftName(null);
            }
          }
        }
      }
      Long giftNum   = 0L;
      if("0".equals(NumUtil.toString(campaignLine.getCampaignMain().getMinCommodityNum()))){
        giftNum = campaignLine.getCampaignMain().getGiftAmount();
      }else{
        giftNum = (totalCommodityNum / campaignLine.getCampaignMain().getMinCommodityNum())
        * campaignLine.getCampaignMain().getGiftAmount();
      }
      
      
      // 重置赠品数量

      if (giftNum.intValue() == 0) {
        cart.get(shopCode, firstCommdityCode).getCommodityInfo().setGiftList(null);
        return;
      }
      CartItem cartItem = cart.get(shopCode, firstCommdityCode);
      if(cartItem!=null && cartItem.getCommodityInfo()!=null && cartItem.getCommodityInfo().getGiftList()!=null
          && cartItem.getCommodityInfo().getGiftList().size()>0 ){
        for (GiftItem gift : cartItem.getCommodityInfo().getGiftList()) {
          gift.setQuantity(giftNum.intValue());
        }
      }

    }

  }
}
