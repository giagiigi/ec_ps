package jp.co.sint.webshop.web.action.front.order;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.sint.webshop.data.dao.CampaignMainDao;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.NewCampaignConditionType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CampaignCondition;
import jp.co.sint.webshop.data.dto.CampaignMain;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.MyCouponInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CartItem;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.CompositionItem;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.service.cart.impl.CartItemImpl;
import jp.co.sint.webshop.service.catalog.CommodityAvailability;
import jp.co.sint.webshop.service.communication.CampaignLine;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.web.bean.front.order.ConfirmBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean;
import jp.co.sint.webshop.web.bean.front.order.ShippingBean.ShippingDetailBean.ShippingCommodityBean.GiftBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2020140:注文内容確認のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class ConfirmBaseAction extends OrderBaseAction<ConfirmBean> {

  // 2012/12/03 促销对应 ob add start
  /**
   * 在庫・予約上限数チェック
   * 
   * @return boolean
   */
  public boolean shippingValidation() {
    Cashier cashier = getBean().getCashier();
    boolean valid = true;
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    Map<Sku, Integer> amountMap = new HashMap<Sku, Integer>();
    CommodityAvailability commodityAvailability = null;

    for (CashierShipping shipping : cashier.getShippingList()) {
      // 统计通常商品/套餐商品
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        List<String> errorMesssageList = BeanValidator.validate(commodity).getErrorMessages();
        String displayName = getDisplayName(commodity.getCommodityName(), commodity.getStandardDetail1Name(), commodity
            .getStandardDetail2Name());
        if (errorMesssageList.size() > 0) {
          for (String s : errorMesssageList) {
            addErrorMessage(MessageFormat.format(Messages.getString("web.action.front.order.ShippingBaseAction.1"), displayName, s));
          }
          valid &= false;
        }

        String shopCode = shipping.getShopCode();

        if (CommodityType.GIFT.longValue().equals(commodity.getCommodityType())) {
          // 已领取的多重促销活动的赠品

          String skuCode = commodity.getSkuCode();
          Sku sku = getSku(amountMap, shopCode, skuCode);
          int amount = commodity.getQuantity();
          if (sku == null) {
            sku = new Sku(shopCode, skuCode, CommodityType.GIFT.longValue());
            sku.setDisplayName(getDisplayName(commodity.getCommodityName(), commodity.getStandardDetail1Name(), commodity
                .getStandardDetail2Name()));
          } else {
            amount += amountMap.get(sku);
          }
          amountMap.put(sku, amount);
        } else {
          if (commodity.getCompositionList() != null && commodity.getCompositionList().size() > 0) {
            // 套餐商品时

            for (CompositionItem composition : commodity.getCompositionList()) {
              String compositionSkuCode = composition.getSkuCode();
              Sku compositionSku = getSku(amountMap, shopCode, compositionSkuCode);
              int compositionAmount = commodity.getQuantity();
              if (compositionSku == null) {
                compositionSku = new Sku(shopCode, compositionSkuCode, CommodityType.GENERALGOODS.longValue());
                compositionSku.setDisplayName(getDisplayName(composition.getCommodityName(), composition.getStandardDetail1Name(),
                    composition.getStandardDetail2Name()));
              } else {
                compositionAmount += amountMap.get(compositionSku);
              }
              amountMap.put(compositionSku, compositionAmount);
            }
          } else {
            // 通常商品时

            String skuCode = commodity.getSkuCode();
            Sku sku = getSku(amountMap, shopCode, skuCode);
            int amount = commodity.getQuantity();
            if (sku == null) {
              sku = new Sku(shopCode, skuCode, CommodityType.GENERALGOODS.longValue());
              sku.setDisplayName(getDisplayName(commodity.getCommodityName(), commodity.getStandardDetail1Name(), commodity
                  .getStandardDetail2Name()));
            } else {
              amount += amountMap.get(sku);
            }
            amountMap.put(sku, amount);
          }

          // 统计多重促销活动的赠品
          if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
            CampaignMainDao camDao = DIContainer.getDao(CampaignMainDao.class);
            for (GiftItem gift : commodity.getGiftList()) {
              String giftShopCode = shipping.getShopCode();
              String giftCode = gift.getGiftSkuCode();
              Sku giftSku = getSku(amountMap, giftShopCode, giftCode);
              CampaignMain camMain = camDao.load(gift.getCampaignCode());
              int giftAmount = 1;
              if (camMain.getGiftAmount() != null ) {
                giftAmount = Integer.parseInt(camMain.getGiftAmount().toString());
              }
              if (giftSku == null) {
                giftSku = new Sku(shopCode, giftCode, CommodityType.GIFT.longValue());
                giftSku.setDisplayName(getDisplayName(gift.getGiftName(), gift.getStandardDetail1Name(), gift
                    .getStandardDetail1Name()));
              } else {
                giftAmount += amountMap.get(giftSku);
              }
              amountMap.put(giftSku, giftAmount);
            }
          }
        }
      }
    }

    boolean isReserve = false;
    CommodityHeaderDao chDao = DIContainer.getDao(CommodityHeaderDao.class);
    for (CashierShipping shipping : cashier.getShippingList()) {
//      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
//        String shopCode = shipping.getShopCode();
//        String skuCode = commodity.getSkuCode();
//        Long availableStock = 0L;
//        String commodityName = getDisplayName(commodity.getCommodityName(), commodity.getStandardDetail1Name(), commodity
//            .getStandardDetail2Name());

//        if (SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getSetCommodityFlg())) {
//          skuCode = commodity.getSkuCode().split(":")[0];
//        }

        // 最小购买数check
//        if (CommodityType.GENERALGOODS.longValue().equals(commodity.getCommodityType())) {
//          if (commodity.getCompositionList() != null && commodity.getCompositionList().size() > 0) {
//            // 套餐商品
//            List<String> compositionSkuCodeList = new ArrayList<String>();
//            List<String> giftSkuCodeList = new ArrayList<String>();
//            for (CompositionItem composition : commodity.getCompositionList()) {
//              if (StringUtil.hasValue(composition.getSkuCode())) {
//                compositionSkuCodeList.add(composition.getSkuCode());
//              }
//            }
//
//            if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
//              for (GiftItem gift : commodity.getGiftList()) {
//                if (StringUtil.hasValue(gift.getGiftSkuCode())) {
//                  giftSkuCodeList.add(gift.getGiftSkuCode());
//                }
//              }
//            }
//
//            availableStock = catalogService.getAvailableStock(shopCode, skuCode, true, compositionSkuCodeList, giftSkuCodeList);
//            if (availableStock < Integer.valueOf(commodity.getQuantity()).longValue()) {
//              addErrorMessage(WebMessage
//                  .get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
//              valid &= false;
//            }
//          } else {
//            // 通常商品
//            List<String> giftSkuCodeList = new ArrayList<String>();
//
//            if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
//              for (GiftItem gift : commodity.getGiftList()) {
//                if (StringUtil.hasValue(gift.getGiftSkuCode())) {
//                  giftSkuCodeList.add(gift.getGiftSkuCode());
//                }
//              }
//            }
//
//            CommodityHeader ch = chDao.load(shopCode, skuCode);
//
//            if (StringUtil.hasValue(ch.getOriginalCommodityCode())) {
//              availableStock = catalogService.getAvailableStock(shopCode, ch.getOriginalCommodityCode(), false, null,
//                  giftSkuCodeList);
//              availableStock = availableStock / ch.getCombinationAmount();
//            } else {
//              availableStock = catalogService.getAvailableStock(shopCode, skuCode, false, null, giftSkuCodeList);
//            }
//            if (availableStock < Integer.valueOf(commodity.getQuantity()).longValue()) {
//              addErrorMessage(WebMessage
//                  .get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
//              valid &= false;
//            }
//          }

//          // 各套餐的可购买性check
//          if (valid && commodity.getCompositionList() != null && commodity.getCompositionList().size() > 0) {
//            commodityAvailability = catalogService.isAvailable(shopCode, skuCode, commodity.getQuantity(), false, true);
//            if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
//              addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, commodityName));
//              valid &= false;
//            } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
//              addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, commodityName));
//              valid &= false;
//            } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
//              valid &= true;
//            }
//          }

//          // 组合商品库存判断时候 要将购物车里面的所有商品都进行循环判断 add by twh start
//          if (valid) {
//            Long allCount = 0L;
//            Long allStock = 0L;
//            if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
//              allStock = catalogService.getAvailableStock(shopCode, commodity.getOriginalCommodityCode());
//              allCount = commodity.getQuantity() * commodity.getCombinationAmount();
//            } else {
//              allStock = catalogService.getAvailableStock(shopCode, skuCode);
//              allCount = commodity.getQuantity() * 1L;
//            }
//
//            for (CartCommodityInfo cci : shipping.getCommodityInfoList()) {
//              if (!cci.getCommodityCode().equals(skuCode)) {
//                boolean rel = false;
//                if (StringUtil.hasValue(commodity.getOriginalCommodityCode())) {
//                  rel = (commodity.getOriginalCommodityCode().equals(cci.getCommodityCode()) || (StringUtil.hasValue(cci
//                      .getOriginalCommodityCode()) && commodity.getOriginalCommodityCode().equals(cci.getOriginalCommodityCode())));
//                } else {
//                  rel = (StringUtil.hasValue(cci.getOriginalCommodityCode()) && commodity.getCommodityCode().equals(
//                      cci.getOriginalCommodityCode()));
//                }
//
//                if (rel) {
//                  if (StringUtil.hasValue(cci.getOriginalCommodityCode())) {
//                    allCount = allCount + cci.getQuantity() * cci.getCombinationAmount();
//                  } else {
//                    allCount = allCount + cci.getQuantity();
//                  }
//                }
//              }
//            }
//            if (allCount > allStock) {
//              addErrorMessage(WebMessage
//                  .get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
//              valid &= false;
//            }
//          }
//        }
        // 组合商品库存判断时候 要将购物车里面的所有商品都进行循环判断 add by twh end
//      }
      
      
      // 套装，单品，组合，库存综合判断
      List<CartCommodityInfo> suitList = new ArrayList<CartCommodityInfo>();
      List<CartCommodityInfo> combineList = new ArrayList<CartCommodityInfo>();
      List<CartCommodityInfo> simpleListAll = new ArrayList<CartCommodityInfo>();
      List<CartCommodityInfo> simpleList = new ArrayList<CartCommodityInfo>();
      List<CartCommodityInfo> discountList = new ArrayList<CartCommodityInfo>();
      Map<String,Integer> notSimpleMap = new HashMap<String,Integer>();
      for (CartCommodityInfo commodityBean : shipping.getCommodityInfoList()) {
        if (StringUtil.hasValue(commodityBean.getOriginalCommodityCode())) {
          combineList.add(commodityBean);
        } else if (SetCommodityFlg.OBJECTIN.longValue().equals(commodityBean.getSetCommodityFlg())) {
          suitList.add(commodityBean);
        } else {
          simpleListAll.add(commodityBean);
        }
      }
      for( CartCommodityInfo commodityBean : simpleListAll) {
        if(commodityBean.getIsDiscountCommodity().equals("true")) {
          discountList.add(commodityBean);
        } else {
          simpleList.add(commodityBean);
        }
      }
      
      // 限时限量折扣商品
      for (CartCommodityInfo commodityBean : discountList) {
        if (notSimpleMap.get(commodityBean.getCommodityCode()) == null ) {
          notSimpleMap.put(commodityBean.getCommodityCode(), commodityBean.getQuantity());
        } else {
          int num = notSimpleMap.get(commodityBean.getCommodityCode());
          num += commodityBean.getQuantity();
          notSimpleMap.remove(commodityBean.getCommodityCode());
          notSimpleMap.put(commodityBean.getCommodityCode(), num);
        }
      }
      
      // 套餐商品
      for (CartCommodityInfo commodityBean : suitList) {
        String commodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail2Name());
        List<String> compositionSkuCodeList = new ArrayList<String>();
        List<String> giftSkuCodeList = new ArrayList<String>();
        String skuCode = commodityBean.getSkuCodeOfSet();
        for (CompositionItem composition : commodityBean.getCompositionList()) {
          CartCommodityInfo commodity = new CartCommodityInfo();
          commodity.setCommodityName(commodityBean.getCommodityName());
          commodity.setCommodityCode(composition.getSkuCode());
          commodity.setQuantity(0);
          simpleList.add(commodity);
          if (StringUtil.hasValue(composition.getSkuCode())) {
            compositionSkuCodeList.add(composition.getSkuCode());
            if (notSimpleMap.get(composition.getSkuCode()) == null ) {
              notSimpleMap.put(composition.getSkuCode(), commodityBean.getQuantity());
            } else {
              int num = notSimpleMap.get(composition.getSkuCode());
              num +=  commodityBean.getQuantity();
              notSimpleMap.remove(composition.getSkuCode());
              notSimpleMap.put(composition.getSkuCode(), num);
            }
          }
        }
        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
          for (GiftItem gift : commodityBean.getGiftList()) {
            if (StringUtil.hasValue(gift.getGiftCode())) {
              giftSkuCodeList.add(gift.getGiftCode());
            }
          }
        }
        Long availableStock = catalogService.getAvailableStock("00000000", skuCode, true, compositionSkuCodeList, giftSkuCodeList);
        if (availableStock < commodityBean.getQuantity()) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
          valid &= false;
        }
      }
      
      // 组合商品
      for (CartCommodityInfo commodityBean : combineList) {
        String commodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail2Name());
        List<String> giftSkuCodeList = new ArrayList<String>();
        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
          for (GiftItem gift : commodityBean.getGiftList()) {
            if (StringUtil.hasValue(gift.getGiftCode())) {
              giftSkuCodeList.add(gift.getGiftCode());
            }
          }
        }
        
        CommodityHeader ch = chDao.load("00000000", commodityBean.getCommodityCode());
        CartCommodityInfo commodity = new CartCommodityInfo();
        commodity.setCommodityCode(ch.getOriginalCommodityCode());
        commodity.setCampaignCode(ch.getCombinationAmount().toString());
        commodity.setQuantity(-100);
        commodity.setCommodityName(commodityBean.getCommodityName());
        simpleList.add(commodity);
        Long availableStock = catalogService.getAvailableStock("00000000", ch.getOriginalCommodityCode());
        availableStock = availableStock/ch.getCombinationAmount();
        
        if (availableStock < commodityBean.getQuantity()) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
          valid &= false;
        }
        if (notSimpleMap.get(ch.getOriginalCommodityCode()) == null ) {
          notSimpleMap.put(ch.getOriginalCommodityCode(), commodityBean.getQuantity()*ch.getCombinationAmount().intValue() );
        } else {
          int num = notSimpleMap.get(ch.getOriginalCommodityCode());
          num += commodityBean.getQuantity()*ch.getCombinationAmount();
          notSimpleMap.remove(ch.getOriginalCommodityCode());
          notSimpleMap.put(ch.getOriginalCommodityCode(), num);
        }
      }
      
      // 通常商品
      boolean combineFlg = false; 
      for (CartCommodityInfo commodityBean : simpleList) {
        if (commodityBean.getQuantity() == -100) {
          combineFlg = true;
          commodityBean.setQuantity(0);
        } else {
          combineFlg = false;
        }
        String commodityName = getDisplayName(commodityBean.getCommodityName(), commodityBean.getStandardDetail1Name(), commodityBean.getStandardDetail2Name());
        Integer num = notSimpleMap.get(commodityBean.getCommodityCode());
        Integer totalNum = commodityBean.getQuantity();
        if (num == null) {
          num = 0;
        }
        totalNum += num;
        
        List<String> giftSkuCodeList = new ArrayList<String>();
        if (commodityBean.getGiftList() != null && commodityBean.getGiftList().size() > 0) {
          for (GiftItem gift : commodityBean.getGiftList()) {
            if (StringUtil.hasValue(gift.getGiftCode())) {
              giftSkuCodeList.add(gift.getGiftCode());
            }
          }
        }
        Long availableStock = catalogService.getAvailableStock("00000000", commodityBean.getCommodityCode());
        if (availableStock < totalNum ) {
           if (combineFlg) {
             addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock/Long.parseLong(commodityBean.getCampaignCode())+""));
           } else {
             addErrorMessage(WebMessage.get(OrderErrorMessage.TOTAL_STOCK_SHORTAGE_ERROR, commodityName, availableStock.toString()));
           }
           valid &= false;
        }
      }
    }

    if (valid) {
      for (Entry<Sku, Integer> entry : amountMap.entrySet()) {
        String shopCode = entry.getKey().getShopCode();
        String skuCode = entry.getKey().getSkuCode();
        Long commodityType = entry.getKey().getCommodityType();
        Integer amount = entry.getValue();
        String displayName = entry.getKey().getDisplayName();

        isReserve = getSessionContainer().getCart().getReserve(shopCode, skuCode) != null;

        if (CommodityType.GENERALGOODS.longValue().equals(commodityType)) {
          // 各通常商品可购买性check
          commodityAvailability = catalogService.isAvailable(shopCode, skuCode, amount.intValue(), isReserve);

        } else {
          // 各赠品可购买性check
          commodityAvailability = catalogService.isAvailableGift(shopCode, skuCode, amount.intValue());
        }

        if (commodityAvailability.equals(CommodityAvailability.NOT_EXIST_SKU)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_PERIOD)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.OUT_OF_PERIOD_WITH_NAME, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_ALLOCATE_DEFAULT_ERROR, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.STOCK_SHORTAGE)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.STOCK_SHORTAGE_ERROR, displayName, String.valueOf(catalogService
              .getAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.RESERVATION_LIMIT_OVER)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.RESERVATION_OVER, displayName, NumUtil.toString(catalogService
              .getReservationAvailableStock(shopCode, skuCode))));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.OUT_OF_RESERVATION_STOCK)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_AVAILABLE, displayName));
          valid &= false;
        } else if (commodityAvailability.equals(CommodityAvailability.AVAILABLE)) {
          valid &= true;
        }
      }
    }

    return valid;
  }

  /**
   * 折扣券的使用可能验证
   * 
   * @return boolean
   */
  public boolean couponValidation() {
    Cashier cashier = getBean().getCashier();
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    CommunicationService comService = ServiceLocator.getCommunicationService(getLoginInfo());

    // 统计各通常商品/套餐商品的数量
    Map<Sku, Integer> amountMap = getAmountMap();

    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {

        // 商品优惠券活动的适用性check
        if (StringUtil.hasValue(commodity.getCampaignCouponCode())) {
          List<CampaignCondition> conditionList = comService.getCampaignConditionByCouponCode(commodity.getCampaignCouponCode());
          if (conditionList != null && conditionList.size() > 0) {
            String[] attributeValues = conditionList.get(0).getAttributrValue().split(",");
            boolean isExit = false;
            for (int i = 0; i < attributeValues.length; i++) {
              if (attributeValues[i].equals(commodity.getCommodityCode())) {

                isExit = true;

                // 使用商品SKU的合计数量>=条件的对象商品限定件数
                String shopCode = commodity.getShopCode();
                String skuCode = commodity.getSkuCode();
                if (conditionList.get(0).getMaxCommodityNum() != null && !amountMap.isEmpty()) {
                  if (conditionList.get(0).getMaxCommodityNum() > amountMap.get(getSku(amountMap, shopCode, skuCode))) {
                    addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_USE_LIMIT_ERROR, commodity
                        .getCampaignCouponCode(), conditionList.get(0).getMaxCommodityNum().toString()));
                    return false;
                  }
                }

                // 以前使用限制次数
                Long couponUsedCounts = orderService.getCampaignDiscountUsedCount(commodity.getCampaignCouponCode(), "");
                // 本次使用限制次数
                Long couponCounts = getCouponCounts(commodity.getCampaignCouponCode());
                // 使用回数check
                if (couponUsedCounts != null
                    && conditionList.get(0).getUseLimit() != null
                    && !amountMap.isEmpty()
                    && BigDecimalUtil.isAbove(BigDecimalUtil.add(couponUsedCounts, couponCounts), new BigDecimal(conditionList.get(
                        0).getUseLimit()))) {
                  addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_MAX_COMMODITY_NUM_ERROR, commodity
                      .getCampaignCouponCode()));
                  return false;
                }
                break;
              }
            }

            if (!isExit) {
              addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_DETAIL_ERROR, commodity.getCampaignCouponCode()));
              return false;
            }
          } else {
            addErrorMessage(WebMessage.get(OrderErrorMessage.COMMODITY_COUPON_DETAIL_ERROR, commodity.getCampaignCouponCode()));
            return false;
          }
        }
      }
    }

    return true;
  }

  private Long getCouponCounts(String couponUsedCounts) {
    Long result = 0L;
    Cashier cashier = getBean().getCashier();

    for (CashierShipping shipping : cashier.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        if (couponUsedCounts.equals(commodity.getCampaignCouponCode())) {
          result = result + 1L;
        }
      }

    }

    return result;
  }

  /**
   * 统计各通常商品/套餐商品的数量
   * 
   * @return 各通常商品/套餐商品的数量
   */
  private Map<Sku, Integer> getAmountMap() {
    Map<Sku, Integer> amountMap = new HashMap<Sku, Integer>();

    // 统计各通常商品/套餐商品的数量
    for (CashierShipping shipping : getBean().getCashier().getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        String shopCode = shipping.getShopCode();
        String skuCode = commodity.getSkuCode();
        Sku sku = getSku(amountMap, shopCode, skuCode);
        int amount = commodity.getQuantity();
        if (sku == null) {
          sku = new Sku(shopCode, skuCode);
          sku.setDisplayName(getDisplayName(commodity.getCommodityName(), commodity.getStandardDetail1Name(), commodity
              .getStandardDetail2Name()));
        } else {
          amount += amountMap.get(sku);
        }
        amountMap.put(sku, amount);
      }

    }

    return amountMap;
  }

  private Sku getSku(Map<Sku, Integer> map, String shopCode, String skuCode) {
    for (Entry<Sku, Integer> entry : map.entrySet()) {
      Sku mapSku = entry.getKey();
      if (mapSku.getShopCode().equals(shopCode) && mapSku.getSkuCode().equals(skuCode)) {
        return entry.getKey();
      }
    }
    return null;
  }

  public String getDisplayName(String commodityName, String detail1Name, String detail2Name) {
    String display = commodityName;

    if (StringUtil.hasValue(detail1Name) && StringUtil.hasValue(detail2Name)) {
      display += "(" + MessageFormat.format(Messages.getString("web.action.front.order.OrderAction.0"), detail1Name, detail2Name)
          + ")";
    } else if (StringUtil.hasValue(detail1Name)) {
      display += "(" + detail1Name + ")";
    } else if (StringUtil.hasValue(detail2Name)) {
      display += "(" + detail2Name + ")";
    }
    return display;
  }

  // 2012/12/03 促销对应 ob add end

  // 2013/04/16 优惠券对应 ob add start
  // 2013/04/08 优惠券对应 ob add start
  public MyCouponInfo getAviableCouponInfo(String shopCode, String couponIssueNo) {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Cashier cashier = getBean().getCashier();
    MyCouponInfo newCouponHistory = service.getMyCoupon(getLoginInfo().getCustomerCode(), couponIssueNo);

    MyCouponInfo result = new MyCouponInfo();

    List<String> objectCommodity = new ArrayList<String>();

    BigDecimal objectTotalPrice = BigDecimal.ZERO;
    BigDecimal objectTotalPriceTemp = BigDecimal.ZERO;

    List<NewCouponHistoryUseInfo> useCommodityList = service.getUseCommodityList(newCouponHistory.getCouponIssueNo());
    List<NewCouponHistoryUseInfo> brandList = service.getBrandCodeList(newCouponHistory.getCouponIssueNo());
    List<NewCouponHistoryUseInfo> importCommodityTypeList = service.getImportCommodityTypeList(newCouponHistory.getCouponIssueNo());
    // 20131010 txw add start
    List<NewCouponHistoryUseInfo> categoryList = service.getCategoryCodeList(newCouponHistory.getCouponIssueNo());
    // 20131010 txw add end
    boolean objectFlg = false;
    if (useCommodityList != null && useCommodityList.size() > 0) {
      objectFlg = true;
    }
    if (brandList != null && brandList.size() > 0) {
      objectFlg = true;
    }
    // 20131010 txw add start
    if (categoryList != null && categoryList.size() > 0) {
      objectFlg = true;
    }
    // 20131010 txw add end
    if (importCommodityTypeList != null && importCommodityTypeList.size() > 0) {
      objectFlg = true;
    }
    if (objectFlg) {
      objectCommodity = getObjectCommodity(shopCode, useCommodityList, brandList, importCommodityTypeList, categoryList);
    }

    // 指定商品的购买金额取得
    if (objectCommodity.size() > 0) {
      for (String commodityCode : objectCommodity) {
        for (CashierShipping shipping : cashier.getShippingList()) {
          for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
            if (commodityCode.equals(commodity.getCommodityCode())) {
              objectTotalPrice = BigDecimalUtil.add(objectTotalPrice, BigDecimalUtil.multiply(commodity.getRetailPrice(), commodity
                  .getQuantity()));
            }
          }
        }
      }
    }

    // 朋友推荐 优惠券时没有USE_TYPE字段默认可使用
    if (StringUtil.isNullOrEmpty(newCouponHistory.getUseType())) {
      newCouponHistory.setUseType(0L);
    }
    if (newCouponHistory.getUseType().equals(1L)) {
      if (objectFlg) {
        objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice().subtract(objectTotalPrice);
      } else {
        objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
      }
    } else {
      if (objectFlg) {
        objectTotalPriceTemp = objectTotalPrice;
      } else {
        objectTotalPriceTemp = getBean().getCashier().getTotalCommodityPrice();
      }
    }
    // objectTotalPriceTemp = objectTotalPrice;

    // 优惠前后标志
    // if
    // (BeforeAfterDiscountType.AFTERDISCOUNT.longValue().equals(newCouponHistory.getBeforeAfterDiscountType()))
    // {
    // BigDecimal couponAmount = BigDecimal.ZERO;
    // if
    // (CouponIssueType.FIXED.longValue().equals(newCouponHistory.getCouponIssueType()))
    // {
    // if (newCouponHistory.getCouponAmount() != null) {
    // couponAmount = newCouponHistory.getCouponAmount();
    // }
    // } else {
    // couponAmount =
    // BigDecimalUtil.divide(BigDecimalUtil.multiply(objectTotalPrice,
    // BigDecimalUtil.tempFormatLong(
    // newCouponHistory.getCouponProportion(), BigDecimal.ZERO)), 100, 2,
    // RoundingMode.HALF_UP);
    // }
    // objectTotalPrice = BigDecimalUtil.subtract(objectTotalPrice,
    // couponAmount);
    // }

    // 优惠券利用最小购买金额
    if (BigDecimalUtil.isAboveOrEquals(objectTotalPriceTemp, newCouponHistory.getMinUseOrderAmount())) {
      newCouponHistory.setObjectTotalPrice(objectTotalPriceTemp);
      result = newCouponHistory;
    }

    return result;
  }

  /**
   * 购物车中满足优惠券使用规则（商品编号或品牌编号或商品区分品）的商品一览设定
   * 
   * @param shopCode
   * @param newCouponHistory
   *          优惠券发行履历信息
   * @param objectCommodity
   *          购物车中满足优惠券使用规则的商品一览
   */
  private List<String> getObjectCommodity(String shopCode, List<NewCouponHistoryUseInfo> useCommodityList,
      List<NewCouponHistoryUseInfo> brandList, List<NewCouponHistoryUseInfo> importCommodityTypeList,
      List<NewCouponHistoryUseInfo> categoryList) {
    List<CommodityHeader> commodityListOfCart = new ArrayList<CommodityHeader>();
    CatalogService cs = ServiceLocator.getCatalogService(getLoginInfo());
    // CommunicationService service =
    // ServiceLocator.getCommunicationService(getLoginInfo());
    // List<String> commodityCodeOfCart = new ArrayList<String>();
    List<String> objectCommodity = new ArrayList<String>();

    for (CashierShipping shipping : getBean().getCashier().getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        commodityListOfCart.add(cs.getCommodityHeader(shopCode, commodity.getCommodityCode()));
      }
    }

    // List<NewCouponHistoryUseInfo> useCommodityList =
    // service.getUseCommodityList(newCouponHistory.getCouponIssueNo());
    // List<NewCouponHistoryUseInfo> brandList =
    // service.getBrandCodeList(newCouponHistory.getCouponIssueNo());
    // List<NewCouponHistoryUseInfo> importCommodityTypeList =
    // service.getImportCommodityTypeList(newCouponHistory.getCouponIssueNo());

    List<String> useCommodityCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo useCommodity : useCommodityList) {
      useCommodityCodeList.add(useCommodity.getCommodityCode());
    }

    List<String> brandCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo brand : brandList) {
      brandCodeList.add(brand.getBrandCode());
    }

    // 20131010 txw add start
    List<String> categoryCodeList = new ArrayList<String>();
    for (NewCouponHistoryUseInfo category : categoryList) {
      categoryCodeList.add(category.getCategoryCode());
    }
    // 20131010 txw add end

    List<Long> importCommodityTypeCodeList = new ArrayList<Long>();
    for (NewCouponHistoryUseInfo importCommodityType : importCommodityTypeList) {
      importCommodityTypeCodeList.add(importCommodityType.getImportCommodityType());
    }

    for (CommodityHeader ch : commodityListOfCart) {

      // 使用关联信息(商品编号)
      if (useCommodityCodeList != null && useCommodityCodeList.size() > 0 && useCommodityCodeList.contains(ch.getCommodityCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(品牌编号)
      if (brandCodeList != null && brandCodeList.size() > 0 && brandCodeList.contains(ch.getBrandCode())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }

      // 使用关联信息(商品区分品)
      if (importCommodityTypeCodeList != null && importCommodityTypeCodeList.size() > 0
          && importCommodityTypeCodeList.contains(ch.getImportCommodityType())) {
        if (!objectCommodity.contains(ch.getCommodityCode())) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
      // commodityCodeOfCart.add(ch.getCommodityCode());

      // 20131010 txw add start
      // 使用关联信息(分类编号)
      if (categoryCodeList != null && categoryCodeList.size() > 0) {
        String categoryPath = ch.getCategoryPath();
        categoryPath = categoryPath.replaceAll("/", "");
        categoryPath = categoryPath.replaceAll("#", "");
        String[] categorys = categoryPath.split("~");
        boolean existCategory = false;
        if (StringUtil.hasValue(categoryPath)) {
          existCategory = false;
        } else {
          existCategory = true;
        }
        for (String categoryCode : categorys) {
          if (categoryCodeList.contains(categoryCode)) {
            existCategory = true;
            break;
          }
        }
        if (!objectCommodity.contains(ch.getCommodityCode()) && existCategory) {
          objectCommodity.add(ch.getCommodityCode());
        }
      }
      // 20131010 txw add end
    }

    // if
    // (!RelatedCommodityFlg.HAVE.longValue().equals(newCouponHistory.getRelatedCommodityFlg()))
    // {
    // if (objectCommodity == null || objectCommodity.size() < 1) {
    // objectCommodity = commodityCodeOfCart;
    // }
    // }
    // if (useCommodityCodeList.size() < 1 && brandCodeList.size() < 1 &&
    // importCommodityTypeCodeList.size() < 1) {
    // objectCommodity = commodityCodeOfCart;
    // }
    return objectCommodity;

  }
  // 2013/04/08 优惠券对应 ob add end
  
  
  
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
      //如果最小购买数为0则赠品最多只送活动中的赠品数量  20140806
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
      if (cartItem != null && cartItem.getCommodityInfo() != null && cartItem.getCommodityInfo().getGiftList() != null
          && cartItem.getCommodityInfo().getGiftList().size() > 0) {
        for (GiftItem gift : cart.get(shopCode, firstCommdityCode).getCommodityInfo().getGiftList()) {
          gift.setQuantity(giftNum.intValue());
        }
      }
    }

  }

}
