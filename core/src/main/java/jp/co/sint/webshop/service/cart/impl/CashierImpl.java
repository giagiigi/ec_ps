package jp.co.sint.webshop.service.cart.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryRegion;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.OptionalCommodity;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.campain.CampaignInfo;
import jp.co.sint.webshop.service.campain.CampainFilter;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.service.cart.CashierDelivery;
import jp.co.sint.webshop.service.cart.CashierDiscount;
import jp.co.sint.webshop.service.cart.CashierInvoice;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * @author System Integrator Corp.
 */
public class CashierImpl implements Cashier {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Customer customer;

  private CashierPayment payment = new CashierPayment();

  private CustomerAddress selfAddress;

  private List<CashierShipping> cashierShippingList = new ArrayList<CashierShipping>();

  private List<CartCommodityInfo> usableCommodity = new ArrayList<CartCommodityInfo>();

  // 2012/11/21 促销对应 ob add start
  // 多重促销活动赠品
  private List<CartCommodityInfo> otherGiftList = new ArrayList<CartCommodityInfo>();

  // 2012/11/21 促销对应 ob add end

  private String usePoint;

  private String useCoupon;

  private String message;

  private String caution;

  private boolean usablePoint;

  private boolean reserve;
  
  private BigDecimal giftCardUseAmount = BigDecimal.ZERO;
  
  private BigDecimal outerCardUseAmount = BigDecimal.ZERO;

  private List<CustomerCoupon> customerCouponId;

  // add by V10-CH start
  private boolean usableCoupon;

  // add by V10-CH end

  // 20111221 shen add start
  private CashierInvoice invoice;

  private CashierDelivery delivery;

  private CashierDiscount discount = new CashierDiscount();

  // 20111221 shen add end

  // 20121224 txw add start
  private int itemCount;

  private BigDecimal optionalCheapPrice = BigDecimal.ZERO;
  
  private Map<String ,OptionalCommodity> optionalCommodityMap = new HashMap<String ,OptionalCommodity>();
  
  private List<String> opCommodityCodeList = new ArrayList<String>();
  
  
  /**
   * @return the optionalCommodityMap
   */
  public Map<String, OptionalCommodity> getOptionalCommodityMap() {
    return optionalCommodityMap;
  }

  
  /**
   * @param optionalCommodityMap the optionalCommodityMap to set
   */
  public void setOptionalCommodityMap(Map<String, OptionalCommodity> optionalCommodityMap) {
    this.optionalCommodityMap = optionalCommodityMap;
  }

  
  /**
   * @return the optionalCheapPrice
   */
  public BigDecimal getOptionalCheapPrice() {
    return optionalCheapPrice;
  }

  
  /**
   * @param optionalCheapPrice the optionalCheapPrice to set
   */
  public void setOptionalCheapPrice(BigDecimal optionalCheapPrice) {
    this.optionalCheapPrice = optionalCheapPrice;
  }
  
  public CashierImpl(boolean reserve) {
    cashierShippingList = new ArrayList<CashierShipping>();
    this.reserve = reserve;
  }

  /**
   * @return the itemCount
   */
  public int getItemCount() {
    return itemCount;
  }

  /**
   * @param itemCount
   *          the itemCount to set
   */
  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  /**
   * @return the usablePoint
   */
  public boolean isUsablePoint() {
    return usablePoint;
  }

  /**
   * @param usablePoint
   *          the usablePoint to set
   */
  public void setUsablePoint(boolean usablePoint) {
    this.usablePoint = usablePoint;
  }

  public BigDecimal getGrandTotalPrice() {
    BigDecimal totalPrice = BigDecimal.ZERO;
    for (CashierShipping item : this.getShippingList()) {
      totalPrice = totalPrice.add(item.getTotalPrice());
    }
    totalPrice = totalPrice.add(getTotalShippingCharge());
    return totalPrice;
  }

  public void addCashierItem(CustomerAddress address, DeliveryType deliveryType, List<CartCommodityInfo> commodityInfoList) {
    if (deliveryType == null) {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error(Messages.log("service.cart.impl.CashierImpl.0"));
      throw new RuntimeException();
    }
    CashierShippingImpl item = (CashierShippingImpl) getShipping(deliveryType.getShopCode(), deliveryType.getDeliveryTypeNo(),
        address.getAddressNo());
    boolean insertItem = false;
    if (item == null) {
      item = new CashierShippingImpl();
      insertItem = true;
    }

    List<CartCommodityInfo> commodityInfoListOrg = item.getCommodityInfoList();
    for (CartCommodityInfo addCommodity : commodityInfoList) {
      boolean insertCommodity = true;
      for (CartCommodityInfo info : commodityInfoListOrg) {
        if (info.getSkuCode().equals(addCommodity.getSkuCode()) ) {
          if( info.getIsDiscountCommodity().equals(addCommodity.getIsDiscountCommodity())){
            // 同一商品が既に登録されている場合は数量更新
            info.setQuantity(info.getQuantity() + addCommodity.getQuantity());
            insertCommodity = false;
          } else {
            insertCommodity = true;
          }
        }
      }
      if (insertCommodity) {
        // 登録されていない場合はOrgにadd
        // add時は新しいオブジェクトで設定する。
        commodityInfoListOrg.add(copyCartCommodityInfo(addCommodity));
      }
    }

    ShopManagementService service = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    item.setShopCode(deliveryType.getShopCode());
    item.setShopName(service.getShop(deliveryType.getShopCode()).getShopName());
    item.setAddress(address);
    item.setDeliveryType(deliveryType);
    item.setCommodityInfoList(commodityInfoListOrg);
    // 20111228 shen add start
    item.setDeliveryAppointedDate("");
    item.setDeliveryAppointedStartTime("");
    item.setDeliveryAppointedTimeEnd("");
    // 20111228 shen add end

    if (insertItem) {
      
      cashierShippingList.add(item);
    }
    recomputeShippingCharge();
  }

  public void recomputeShippingCharge() {
    // 配送種別による送料計算
    for (CashierShipping shipping : getItemList()) {

      BigDecimal subTotal = BigDecimal.ZERO;
      BigDecimal subTotalWeight = BigDecimal.ZERO;
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        subTotal = subTotal.add(BigDecimalUtil.multiply(commodity.getRetailPrice().add(commodity.getGiftPrice()), commodity
            .getQuantity()));
        subTotalWeight = subTotalWeight.add(commodity.getWeight().multiply(new BigDecimal(commodity.getQuantity())));
        if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
          for (GiftItem giftItem : commodity.getGiftList()) {
            subTotalWeight = subTotalWeight.add(BigDecimalUtil.multiply(giftItem.getWeight(), giftItem.getQuantity()));
            subTotal = subTotal.add(BigDecimalUtil.multiply(giftItem.getRetailPrice(), giftItem.getQuantity()));
          }
        }
      }
      ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
      shipping.setShippingChargeTax(BigDecimal.ZERO);
      shipping.setDeliveryDateCommssion(BigDecimal.ZERO);
      List<DeliveryCompany> deliveryCompanyList = new ArrayList<DeliveryCompany>();
      DeliveryCompany deliveryCompany = null;
      if (payment != null && payment.getSelectPayment() != null) {
        // 配送公司取得
        UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
        // 根据支付方式取得全部配送公司
        deliveryCompanyList = utilService.getDeliveryCompanys(shipping.getDeliveryType().getShopCode(), shipping.getAddress()
            .getPrefectureCode(), shipping.getAddress().getCityCode(), shipping.getAddress().getAreaCode(), 
            payment.getSelectPayment().isCashOnDelivery(), subTotalWeight.toString());
        
        // 封装List<DeliveryCompanyBean>
        List<DeliveryCompany> lsDcb = new ArrayList<DeliveryCompany>();
        DeliveryCompany dcBean = new DeliveryCompany();
        dcBean.setDeliveryCompanyNo("D000");
        for (DeliveryCompany dc : deliveryCompanyList) {
          if (!dcBean.getDeliveryCompanyNo().equals(dc.getDeliveryCompanyNo())) {
            DeliveryCompany dcb = new DeliveryCompany();
            dcb.setDeliveryCompanyNo(dc.getDeliveryCompanyNo());
            dcb.setDeliveryCompanyName(dc.getDeliveryCompanyName());

            lsDcb.add(dcb);
          }
          dcBean.setDeliveryCompanyNo(dc.getDeliveryCompanyNo());
        }
        if (lsDcb.size() > 0) {
          if (lsDcb.size() == 1) {// 如果只发现一条配送公司，那么选择该条配送公司进行运费计算
            deliveryCompany = lsDcb.get(0);
          } else {// 如果有两条以上配送公司，那么选画面单选按钮选中的配送公司
            if (delivery != null && delivery.getDeliveryCompanyCode() != null) {
              DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
              boolean flg = false;
              // 判断选中配送公司是否在列表中
              for (DeliveryCompany dcb : lsDcb) {
                if (delivery.getDeliveryCompanyCode().equals(dcb.getDeliveryCompanyNo())) {
                  flg = true;
                  break;
                }
              }

              if (flg) {
                deliveryCompany = dao.load(delivery.getDeliveryCompanyCode());
              } else {
                String strDeliveryCompanyNo = "";
                String strDeliveryCompanyNoYmd = "";
                BigDecimal deliveryCharge = BigDecimal.ZERO;
                for (DeliveryCompany dc : lsDcb) {
                  BigDecimal cacheDeliveryCharge = BigDecimal.ZERO;
                  cacheDeliveryCharge = shopService.getShippingCharge(shipping.getAddress().getPrefectureCode(), subTotal.subtract(this.optionalCheapPrice),
                      subTotalWeight, dc.getDeliveryCompanyNo());
                  // 循环第一轮时，为运费deliveryCharge赋值
                  if (BigDecimalUtil.equals(deliveryCharge, BigDecimal.ZERO)) {
                    deliveryCharge = cacheDeliveryCharge;
                  }
                  // 两条以上的配送公司，默认选择运费最低的
                  if (BigDecimalUtil.isBelowOrEquals(cacheDeliveryCharge, deliveryCharge)) {
                    deliveryCharge = cacheDeliveryCharge;
                    strDeliveryCompanyNo = dc.getDeliveryCompanyNo();
                  }
                  if(!StringUtil.isNullOrEmpty(dc.getDeliveryCompanyNo()) && dc.getDeliveryCompanyNo().equals("D006")){
                    strDeliveryCompanyNoYmd = dc.getDeliveryCompanyNo();
                  }
                }
                if(!StringUtil.isNullOrEmpty(strDeliveryCompanyNoYmd)){
                  deliveryCompany = dao.load(strDeliveryCompanyNoYmd);
                }else{
                  deliveryCompany = dao.load(strDeliveryCompanyNo);
                }
                
              }
            } else {// 如果画面配送公司单选按钮未选择，那么用运费便宜的配送公司
              String strDeliveryCompanyNo = "";
              String strDeliveryCompanyNoYmd = "";
              BigDecimal deliveryCharge = BigDecimal.ZERO;
              for (DeliveryCompany dc : lsDcb) {
                BigDecimal cacheDeliveryCharge = BigDecimal.ZERO;
                cacheDeliveryCharge = shopService.getShippingCharge(shipping.getAddress().getPrefectureCode(), subTotal,
                    subTotalWeight, dc.getDeliveryCompanyNo());
                // 循环第一轮时，为运费deliveryCharge赋值
                if (BigDecimalUtil.equals(deliveryCharge, BigDecimal.ZERO)) {
                  deliveryCharge = cacheDeliveryCharge;
                }
                // 两条以上的配送公司，默认选择运费最低的
                if (BigDecimalUtil.isBelowOrEquals(cacheDeliveryCharge, deliveryCharge)) {
                  deliveryCharge = cacheDeliveryCharge;
                  strDeliveryCompanyNo = dc.getDeliveryCompanyNo();
                }
                if(!StringUtil.isNullOrEmpty(dc.getDeliveryCompanyNo()) && dc.getDeliveryCompanyNo().equals("D006")){
                  strDeliveryCompanyNoYmd = dc.getDeliveryCompanyNo();
                }
              }
              deliveryCompany = new DeliveryCompany();
              if(!StringUtil.isNullOrEmpty(strDeliveryCompanyNoYmd)){
                deliveryCompany.setDeliveryCompanyNo(strDeliveryCompanyNoYmd);
              }else{
                deliveryCompany.setDeliveryCompanyNo(strDeliveryCompanyNo);
              }
              
              
            }
          }
        } else {
          deliveryCompany = utilService.getDefaultDeliveryCompany();
        }
        if (deliveryCompany != null) {

          boolean isFree = isFreeShippingCharge(this);
          if (!isFree) { // 如果不满足活动条件
            shipping.setShippingCharge(shopService.getShippingCharge(shipping.getAddress().getPrefectureCode(), subTotal.subtract(this.optionalCheapPrice),
                subTotalWeight, deliveryCompany.getDeliveryCompanyNo()));
          } else {
            shipping.setShippingCharge(BigDecimal.ZERO);
          }
          // 手续费设定
            DeliveryRegion deliveryRegion = shopService.getDeliveryRegion("00000000", deliveryCompany
                .getDeliveryCompanyNo(), shipping.getAddress().getPrefectureCode());
            if (deliveryRegion != null) {
              shipping.setDeliveryDateCommssion(deliveryRegion.getDeliveryDatetimeCommission());
            }
        }
      } else { // 未选择支付方式时，运费无法计算，所以设置成零
        shipping.setShippingCharge(BigDecimal.ZERO);
      }
    }
    // soukai update 2012/01/05 ob end
  }

  /**
   * 根据ShippingHeaderBean查看本次发货是否是免邮费
   * 
   * @param bean
   * @return
   */
  private boolean isFreeShippingCharge(Cashier bean) {
    boolean result = false;

    CampainFilter cf = new CampainFilter();
    CampaignInfo campaignI = new CampaignInfo();
    campaignI.setMobileNumber(bean.getShippingList().get(0).getAddress().getMobileNumber());
    campaignI.setPhoneNumber(bean.getShippingList().get(0).getAddress().getPhoneNumber());
    campaignI.setLastName(bean.getShippingList().get(0).getAddress().getAddressLastName());
    campaignI.setAddress1(bean.getShippingList().get(0).getAddress().getAddress1());
    campaignI.setAddress2(bean.getShippingList().get(0).getAddress().getAddress2());
    campaignI.setAddress3(bean.getShippingList().get(0).getAddress().getAddress3());
    campaignI.setAddress4(bean.getShippingList().get(0).getAddress().getAddress4());
    campaignI.setCustomerCode(bean.getShippingList().get(0).getAddress().getCustomerCode());
    campaignI.setPrefectureCode(bean.getShippingList().get(0).getAddress().getPrefectureCode());
    List<OrderDetail> commodityList = new ArrayList<OrderDetail>();

    for (CartCommodityInfo detail : bean.getShippingList().get(0).getCommodityInfoList()) {
      // 2012/12/07 促销对应 ob add start
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        // 2012/12/07 促销对应 ob add end
        OrderDetail orderD = new OrderDetail();
        orderD.setCommodityCode(detail.getCommodityCode());
        orderD.setPurchasingAmount(NumUtil.toLong(detail.getQuantity() + ""));
        commodityList.add(orderD);
        // 2012/12/07 促销对应 ob add start
      }
      // 2012/12/07 促销对应 ob add end
    }
    campaignI.setCommodityList(commodityList);
    result = cf.isFreeShippingCharge(campaignI);
    return result;
  }

  public void addCashierItem(CustomerAddress address, DeliveryType deliveryType, CartCommodityInfo commodityInfo) {
    List<CartCommodityInfo> commodityInfoList = new ArrayList<CartCommodityInfo>();
    commodityInfoList.add(commodityInfo);
    addCashierItem(address, deliveryType, commodityInfoList);
  }

  public CashierShipping getShipping(String shopCode, Long deliveryCode, Long addressNo) {
    for (CashierShipping item : cashierShippingList) {
      if (item.getShopCode().equals(shopCode) && item.getDeliveryType().getDeliveryTypeNo().equals(deliveryCode)
          && item.getAddress().getAddressNo().equals(addressNo)) {
        return item;
      }
    }
    return null;
  }

  public List<CashierShipping> getItemList() {
    return cashierShippingList;
  }

  public List<CashierShipping> getItemListByAddress(Long addressNo) {
    List<CashierShipping> itemList = new ArrayList<CashierShipping>();
    for (CashierShipping item : cashierShippingList) {
      if (item.getAddress().getAddressNo().equals(addressNo)) {
        itemList.add(item);
      }
    }
    return itemList;
  }

  public List<CashierShipping> getItemListByDelivery(Long deliveryTypeCode) {
    List<CashierShipping> itemList = new ArrayList<CashierShipping>();
    for (CashierShipping item : cashierShippingList) {
      if (item.getDeliveryType().getDeliveryTypeNo().equals(deliveryTypeCode)) {
        itemList.add(item);
      }
    }
    return itemList;
  }

  public void removeCashierItem(String shopCode, Long deliveryCode, Long addressNo, String skuCode ,String isDiscount) {

    List<CashierShipping> removeShippings = new ArrayList<CashierShipping>();
    for (CashierShipping item : cashierShippingList) {
      if (item.getDeliveryType().getShopCode().equals(shopCode) && item.getDeliveryType().getDeliveryTypeNo().equals(deliveryCode)
          && item.getAddress().getAddressNo().equals(addressNo)) {

        // 削除するアイテムリスト
        List<CartCommodityInfo> removeItems = new ArrayList<CartCommodityInfo>();

        for (CartCommodityInfo commodity : item.getCommodityInfoList()) {

          if (commodity.getSkuCode().equals(skuCode) && commodity.getIsDiscountCommodity().equals(isDiscount)) {
            removeItems.add(commodity);
          }
        }

        // 削除アイテムリストを用いて、commodityInfoListから一件づつ削除
        for (CartCommodityInfo removeItem : removeItems) {
          item.getCommodityInfoList().remove(removeItem);
        }

        if (item.getCommodityInfoList().size() < 1) {
          removeShippings.add(item);
        }
      }
    }

    for (CashierShipping cs : removeShippings) {
      removeShipping(cs.getShopCode(), cs.getDeliveryType().getDeliveryTypeNo(), cs.getAddress().getAddressNo());
    }
  }

  // 2012-11-29 促销对应 ob add start
  public void removeOtherGift(String multipleCampaignCode, String skuCode) {
    for (CartCommodityInfo gift : otherGiftList) {
      if (StringUtil.hasValue(gift.getSkuCode())) {
        String[] tmp = gift.getSkuCode().split("~");
        if (tmp.length > 1) {
          if (skuCode.equals(tmp[1]) && multipleCampaignCode.equals(tmp[0])) {
            otherGiftList.remove(gift);
            break;
          }
        } else {
          if (skuCode.equals(gift.getSkuCode()) && multipleCampaignCode.equals(gift.getMultipleCampaignCode())) {
            otherGiftList.remove(gift);
            break;
          }
        }
      }
    }
    for (CashierShipping shipping : cashierShippingList) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        if (CommodityType.GIFT.longValue().equals(commodity.getCommodityType()) && StringUtil.hasValue(commodity.getSkuCode())) {
          String[] tmp = commodity.getSkuCode().split("~");
          if (tmp.length > 1) {
            if (skuCode.equals(tmp[1]) && multipleCampaignCode.equals(tmp[0])) {
              shipping.getCommodityInfoList().remove(commodity);
              break;
            }
          } else {
            if (skuCode.equals(commodity.getSkuCode()) && multipleCampaignCode.equals(commodity.getMultipleCampaignCode())) {
              shipping.getCommodityInfoList().remove(commodity);
              break;
            }
          }
        }
      }
    }
  }

  // 2012-11-29 促销对应 ob add end

  public void removeShipping(CashierShipping shipping) {
    removeShipping(shipping.getShopCode(), shipping.getDeliveryType().getDeliveryTypeNo(), shipping.getAddress().getAddressNo());
  }

  public void removeShipping(String shopCode, Long deliveryCode, Long addressNo) {
    // 削除するアイテムリスト
    List<CashierShipping> removeItems = new ArrayList<CashierShipping>();

    for (CashierShipping item : cashierShippingList) {
      if (item.getDeliveryType().getShopCode().equals(shopCode) && item.getDeliveryType().getDeliveryTypeNo().equals(deliveryCode)
          && item.getAddress().getAddressNo().equals(addressNo)) {
        removeItems.add(item);
      }
    }
    // 削除アイテムリストを用いて、cashierShippingListから一件づつ削除
    for (CashierShipping removeItem : removeItems) {
      cashierShippingList.remove(removeItem);
    }
  }

  public void removeShippingList(List<CashierShipping> shippingList) {
    cashierShippingList.removeAll(shippingList);
  }

  public void updateCashierShipping(CashierShipping cashierItem) {
    for (CashierShipping item : cashierShippingList) {
      if (item.getDeliveryType().getDeliveryTypeNo().equals(cashierItem.getDeliveryType().getDeliveryTypeNo())
          && item.getAddress().getAddressNo().equals(cashierItem.getAddress().getAddressNo())) {
        cashierShippingList.remove(item);
        cashierShippingList.add(cashierItem);
      }
    }
  }

  /**
   * customerを取得します。
   * 
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer cashierCustomer, CustomerAddress cashierSelfAddress) {
    this.customer = cashierCustomer;
    this.selfAddress = cashierSelfAddress;
  }

  /**
   * selfAddressを取得します。
   * 
   * @return the selfAddress
   */
  public CustomerAddress getSelfAddress() {
    return selfAddress;
  }

  public List<CartCommodityInfo> getUsableCommodity() {
    return usableCommodity;
  }

  /**
   * usableCommodityを設定します。
   * 
   * @param usableCommodity
   *          the usableCommodity to set
   */
  public void setUsableCommodity(List<CartCommodityInfo> usableCommodity) {
    this.usableCommodity = usableCommodity;
  }

  // 2012/11/21 促销对应 ob add start
  /**
   * 已领取多关联促销活动赠品取得
   */
  public List<CartCommodityInfo> getOtherGiftList() {
    return otherGiftList;
  }

  /**
   * 已领取多关联促销活动赠品设定
   * 
   * @param otherGiftList
   *          已领取多关联促销活动赠品List
   */
  public void setOtherGiftList(List<CartCommodityInfo> otherGiftList) {
    this.otherGiftList = otherGiftList;
  }

  // 2012/11/21 促销对应 ob add end

  /**
   * @return the payment
   */
  public CashierPayment getPayment() {
    return payment;
  }

  /**
   * @param payment
   *          the payment to set
   */
  public void setPayment(CashierPayment payment) {
    this.payment = payment;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the caution
   */
  public String getCaution() {
    return caution;
  }

  /**
   * @param caution
   *          the caution to set
   */
  public void setCaution(String caution) {
    this.caution = caution;
  }

  public List<CashierShipping> getShippingList() {
    return cashierShippingList;
  }

  /**
   * @return the usePoint
   */
  public String getUsePoint() {
    return usePoint;
  }

  /**
   * @param usePoint
   *          the usePoint to set
   */
  public void setUsePoint(String usePoint) {
    this.usePoint = usePoint;
  }

  public BigDecimal getTotalCommodityPrice() {
    BigDecimal result = BigDecimal.ZERO;
    for (CashierShipping shipping : cashierShippingList) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        result = result.add(BigDecimalUtil.multiply(commodity.getRetailPrice(), commodity.getQuantity()));

        // 2012/11/26 促销对应 ob add start
        if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
          for (GiftItem giftItem : commodity.getGiftList()) {
            result = result.add(BigDecimalUtil.multiply(giftItem.getRetailPrice(), giftItem.getQuantity()));
          }
        }
        // 2012/11/26 促销对应 ob add end
      }
    }
    result = result.subtract(this.optionalCheapPrice);
    return result;
  }
  
  public BigDecimal getNotOptionalCommodityPrice() {
    BigDecimal result = BigDecimal.ZERO;
    if (this.opCommodityCodeList != null && this.opCommodityCodeList.size() > 0 ) {
      for (CashierShipping shipping : cashierShippingList) {
        for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
            if (!this.opCommodityCodeList.contains(commodity.getCommodityCode()) || "true".equals(commodity.getIsDiscountCommodity())) {
              result = result.add(BigDecimalUtil.multiply(commodity.getRetailPrice(), commodity.getQuantity()));

              if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
                for (GiftItem giftItem : commodity.getGiftList()) {
                  result = result.add(BigDecimalUtil.multiply(giftItem.getRetailPrice(), giftItem.getQuantity()));
                }
              }
            }
        }
      }
      
    } else {
      result =  getTotalCommodityPrice();
    }
    
    return result;
  }

  
  public BigDecimal getTotalCouponCommodityPrice(String CouponCommodity) {
	if (StringUtil.isNullOrEmpty(CouponCommodity)) {
	    return getTotalCommodityPrice();
	}
	// 取得优惠券包含对象商品list
	String[] commodityList = CouponCommodity.split(";");
    BigDecimal result = BigDecimal.ZERO;
    
    
    for (String commodityCode : commodityList) {
    	for (CashierShipping shipping : cashierShippingList) {
    		for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
    			if (commodityCode.equals(commodity.getCommodityCode())) {
    				result = result.add(BigDecimalUtil.multiply(commodity.getRetailPrice(), commodity.getQuantity()));
				}
    		}
    	}
    }
    
    return result;
  }
  
  public BigDecimal getTotalGiftPrice() {
    BigDecimal result = BigDecimal.ZERO;
    for (CashierShipping shipping : cashierShippingList) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        BigDecimal giftPrice = BigDecimal.ZERO;
        if (!NumUtil.isNull(commodity.getGiftPrice())) {
          giftPrice = commodity.getGiftPrice();
        }
        result = result.add(BigDecimalUtil.multiply(giftPrice, commodity.getQuantity()));
      }
    }
    return result;
  }

  public BigDecimal getTotalShippingCharge() {
    BigDecimal result = BigDecimal.ZERO;
    for (CashierShipping shipping : cashierShippingList) {
      BigDecimal shippingCharge;
      if (NumUtil.isNull(shipping.getShippingCharge())) {
        shippingCharge = BigDecimal.ZERO;
      } else {
        shippingCharge = shipping.getShippingCharge();
      }
      result = result.add(shippingCharge);
    }
    return result;
  }

  /**
   * paymentShopCodeを返します。
   * 
   * @return the paymentShopCode
   */
  public String getPaymentShopCode() {
    return this.payment.getShopCode();
  }

  public void clear() {
    cashierShippingList.clear();
    usableCommodity.clear();
    // 2012/11/21 促销对应 ob add start
    otherGiftList.clear();
    // 2012/11/21 促销对应 ob add end
    usePoint = "";
    message = "";
    caution = "";
    customer = null;
    selfAddress = null;
    payment = null;

  }

  /**
   * @return the reserve
   */
  public boolean isReserve() {
    return reserve;
  }

  private CartCommodityInfo copyCartCommodityInfo(CartCommodityInfo c) {
    Object clone = c.clone();
    if (clone != null && clone instanceof CartCommodityInfo) {
      return (CartCommodityInfo) clone;
    }
    return c;
  }

  public List<CashierShipping> getCashierShippingList() {
    return cashierShippingList;
  }

  public void setCashierShippingList(List<CashierShipping> cashierShippingList) {
    this.cashierShippingList = cashierShippingList;
  }

  public List<CustomerCoupon> getCustomerCouponId() {
    return customerCouponId;
  }

  public void setCustomerCouponId(List<CustomerCoupon> customerCouponId) {
    this.customerCouponId = customerCouponId;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setReserve(boolean reserve) {
    this.reserve = reserve;
  }

  public void setSelfAddress(CustomerAddress selfAddress) {
    this.selfAddress = selfAddress;
  }

  public String getUseCoupon() {
    return useCoupon;
  }

  public void setUseCoupon(String useCoupon) {
    this.useCoupon = useCoupon;
  }

  public boolean isUsableCoupon() {
    return usableCoupon;
  }

  public void setUsableCoupon(boolean usableCoupon) {
    this.usableCoupon = usableCoupon;
  }

  public CashierInvoice getInvoice() {
    return invoice;
  }

  public void setInvoice(CashierInvoice invoice) {
    this.invoice = invoice;
  }

  public CashierDiscount getDiscount() {
    return discount;
  }

  public void setDiscount(CashierDiscount discount) {
    this.discount = discount;
  }

  // 20111223 shen add start
  public BigDecimal getDiscountPrice() {

    if (customer != null && customer.getCustomerCode().equals("guest")) {
      return BigDecimal.ZERO;
    }

    if (discount.getDiscountPrice() == null) {
      return BigDecimal.ZERO;
    }
    // 2012-11-26 促销对应 ob update start
    // return discount.getDiscountPrice();

    BigDecimal discountResult = discount.getDiscountPrice();

    for (CashierShipping shipping : this.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        if (commodity.getCampaignCouponPrice() != null
            && BigDecimalUtil.isAbove(commodity.getCampaignCouponPrice(), BigDecimal.ZERO)) {
          discountResult = discountResult.add(BigDecimalUtil.multiply(commodity.getCampaignCouponPrice(), commodity.getQuantity()));
        }
      }
    }
    return discountResult;
    // 2012-11-26 促销对应 ob update end

  }

  public BigDecimal getPaymentTotalPrice() {

    BigDecimal totalPrice = BigDecimal.ZERO;
    for (CashierShipping item : this.getShippingList()) {
      totalPrice = totalPrice.add(item.getTotalPrice());
    }
    totalPrice = totalPrice.subtract(getDiscountPrice());

    if (BigDecimalUtil.isBelow(totalPrice, BigDecimal.ZERO)) {
      totalPrice = BigDecimal.ZERO;
    }
    totalPrice = totalPrice.add(getTotalShippingCharge());
    
    totalPrice = totalPrice.add(getTotalDeliveryDateCommssion());

    totalPrice = totalPrice.subtract(giftCardUseAmount);
    
    if (payment != null && payment.getSelectPayment() != null ) {
      if (PaymentMethodType.OUTER_CARD.getValue().equals(payment.getSelectPayment().getPaymentMethodType())) {
        totalPrice = totalPrice.add(outerCardUseAmount);
      }
    }
    totalPrice = totalPrice.subtract(this.optionalCheapPrice);
    return totalPrice;
  }

  /*
   * private BigDecimal getPaymentCommission() { if (payment.getSelectPayment()
   * != null && payment.getSelectPayment().getPaymentCommission() != null) {
   * return payment.getSelectPayment().getPaymentCommission(); } else { return
   * BigDecimal.ZERO; } }
   */
  // 20111223 shen add end
  // 20120104 shen add start
  public BigDecimal getTotalWeight() {
    BigDecimal totalWeight = BigDecimal.ZERO;
    for (CashierShipping shipping : this.getShippingList()) {
      for (CartCommodityInfo commodity : shipping.getCommodityInfoList()) {
        totalWeight = totalWeight.add(BigDecimalUtil.multiply(commodity.getWeight(), commodity.getQuantity()));

        // 2012/11/26 促销对应 ob add start
        if (commodity.getGiftList() != null && commodity.getGiftList().size() > 0) {
          for (GiftItem giftItem : commodity.getGiftList()) {
            totalWeight = totalWeight.add(BigDecimalUtil.multiply(giftItem.getWeight(), giftItem.getQuantity()));
          }
        }
        // 2012/11/26 促销对应 ob add end
      }
    }
    return totalWeight;
  }

  // 20120104 shen add end

  /*
   * (non-Javadoc)
   * @see
   * jp.co.sint.webshop.service.cart.Cashier#getTotalDeliveryDateCommssion()
   */
  @Override
  public BigDecimal getTotalDeliveryDateCommssion() {
    BigDecimal totalCommssion = BigDecimal.ZERO;
    for (CashierShipping shipping : this.getShippingList()) {
      totalCommssion = totalCommssion.add(shipping.getDeliveryDateCommssion());
    }
    return totalCommssion;
  }

  /**
   * @return the delivery
   */
  public CashierDelivery getDelivery() {
    return delivery;
  }

  /**
   * @param delivery
   *          the delivery to set
   */
  public void setDelivery(CashierDelivery delivery) {
    this.delivery = delivery;
  }

  
  /**
   * @return the giftCardUseAmount
   */
  public BigDecimal getGiftCardUseAmount() {
    return this.giftCardUseAmount;
  }

  
  /**
   * @param giftCardUseAmount the giftCardUseAmount to set
   */
  public void setGiftCardUseAmount(BigDecimal giftCardUseAmount) {
    this.giftCardUseAmount = giftCardUseAmount;
  }

  
  /**
   * @return the outerCardUseAmount
   */
  public BigDecimal getOuterCardUseAmount() {
    return outerCardUseAmount;
  }

  
  /**
   * @param outerCardUseAmount the outerCardUseAmount to set
   */
  public void setOuterCardUseAmount(BigDecimal outerCardUseAmount) {
    this.outerCardUseAmount = outerCardUseAmount;
  }


  
  /**
   * @return the opCommodityCodeList
   */
  public List<String> getOpCommodityCodeList() {
    return opCommodityCodeList;
  }


  
  /**
   * @param opCommodityCodeList the opCommodityCodeList to set
   */
  public void setOpCommodityCodeList(List<String> opCommodityCodeList) {
    this.opCommodityCodeList = opCommodityCodeList;
  }

}
