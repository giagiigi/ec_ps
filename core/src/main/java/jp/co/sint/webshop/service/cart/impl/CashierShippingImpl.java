package jp.co.sint.webshop.service.cart.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.cart.CartCommodityInfo.GiftItem;
import jp.co.sint.webshop.utility.BigDecimalUtil;

import org.apache.log4j.Logger;

public class CashierShippingImpl implements CashierShipping {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private CustomerAddress address;

  private DeliveryType deliveryType;

  private List<CartCommodityInfo> commodityInfoList;

  private BigDecimal shippingCharge;

  private BigDecimal shippingChargeTax;

  // 20111228 shen update start
  // private Date deliveryAppointedDate;
  private String deliveryAppointedDate;

  // 20111228 shen update end
  
  private String deliveryAppointedTimeZone;

  private String deliveryAppointedStartTime;

  private String deliveryAppointedTimeEnd;

  private String deliveryRemark;

  private String shopCode;

  private String shopName;

  // soukai add 2012/01/08 ob start
  private BigDecimal deliveryDateCommssion;
  // soukai add 2012/01/08 ob end
  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public CashierShippingImpl() {
    commodityInfoList = new ArrayList<CartCommodityInfo>();
  }

  public BigDecimal getTotalPrice() {
    BigDecimal total = BigDecimal.ZERO;
    for (CartCommodityInfo info : commodityInfoList) {
      // ギフト価格は存在しない場合があるので初期値に0を設定する
      BigDecimal giftPrice = BigDecimal.ZERO;
      if (info.getGiftPrice() != null) {
        giftPrice = info.getGiftPrice();
      }
      total = total.add(BigDecimalUtil.multiply(info.getRetailPrice().add(giftPrice), info.getQuantity()));
      
      // 2012/11/26 促销对应 ob add start
      if (info.getGiftList() != null && info.getGiftList().size() > 0) {
        for (GiftItem giftItem : info.getGiftList()) {
          total = total.add(BigDecimalUtil.multiply(giftItem.getRetailPrice(), giftItem.getQuantity()));
        }
      }
      // 2012/11/26 促销对应 ob add end
    }
    return total;
  }

  /**
   * addressを取得します。
   * 
   * @return the address
   */
  public CustomerAddress getAddress() {
    return address;
  }

  /**
   * addressを設定します。
   * 
   * @param address
   *          the address to set
   */
  public void setAddress(CustomerAddress address) {
    this.address = address;
  }

  /**
   * deliveryTypeを取得します。
   * 
   * @return the deliveryType
   */
  public DeliveryType getDeliveryType() {
    return deliveryType;
  }

  /**
   * deliveryTypeを設定します。
   * 
   * @param deliveryType
   *          the deliveryType to set
   */
  public void setDeliveryType(DeliveryType deliveryType) {
    this.deliveryType = deliveryType;
  }

  /**
   * commodityInfoListを取得します。
   * 
   * @return the commodityInfoList
   */
  public List<CartCommodityInfo> getCommodityInfoList() {
    return commodityInfoList;
  }

  /**
   * commodityInfoListを設定します。
   * 
   * @param commodityInfoList
   *          the commodityInfoList to set
   */
  public void setCommodityInfoList(List<CartCommodityInfo> commodityInfoList) {
    this.commodityInfoList = commodityInfoList;
  }

  public void addCommodityInfo(CartCommodityInfo info) {
    this.commodityInfoList.add(info);
  }

  public String getDeliveryAppointedDate() {
    return deliveryAppointedDate;
  }

  public void setDeliveryAppointedDate(String deliveryAppointedDate) {
    this.deliveryAppointedDate = deliveryAppointedDate;
  }

  /**
   * deliveryAppointedStartTimeを取得します。
   * 
   * @return the deliveryAppointedStartTime
   */
  public String getDeliveryAppointedStartTime() {
    return deliveryAppointedStartTime;
  }

  /**
   * deliveryAppointedStartTimeを設定します。
   * 
   * @param deliveryAppointedStartTime
   *          the deliveryAppointedStartTime to set
   */
  public void setDeliveryAppointedStartTime(String deliveryAppointedStartTime) {
    this.deliveryAppointedStartTime = deliveryAppointedStartTime;
  }

  /**
   * deliveryAppointedTimeEndを取得します。
   * 
   * @return the deliveryAppointedTimeEnd
   */
  public String getDeliveryAppointedTimeEnd() {
    return deliveryAppointedTimeEnd;
  }

  /**
   * deliveryAppointedTimeEndを設定します。
   * 
   * @param deliveryAppointedTimeEnd
   *          the deliveryAppointedTimeEnd to set
   */
  public void setDeliveryAppointedTimeEnd(String deliveryAppointedTimeEnd) {
    this.deliveryAppointedTimeEnd = deliveryAppointedTimeEnd;
  }

  /**
   * shippingChargeを取得します。
   * 
   * @return the shippingCharge
   */
  public BigDecimal getShippingCharge() {
    return shippingCharge;
  }

  /**
   * shippingChargeを設定します。
   * 
   * @param shippingCharge
   *          the shippingCharge to set
   */
  public void setShippingCharge(BigDecimal shippingCharge) {
    this.shippingCharge = shippingCharge;
  }

  /**
   * shippingChargeTaxを取得します。
   * 
   * @return the shippingChargeTax
   */
  public BigDecimal getShippingChargeTax() {
    return shippingChargeTax;
  }

  /**
   * shippingChargeTaxを設定します。
   * 
   * @param shippingChargeTax
   *          the shippingChargeTax to set
   */
  public void setShippingChargeTax(BigDecimal shippingChargeTax) {
    this.shippingChargeTax = shippingChargeTax;
  }

  /**
   * deliveryRemarkを取得します。
   * 
   * @return the deliveryRemark
   */
  public String getDeliveryRemark() {
    return deliveryRemark;
  }

  /**
   * deliveryRemarkを設定します。
   * 
   * @param deliveryRemark
   *          the deliveryRemark to set
   */
  public void setDeliveryRemark(String deliveryRemark) {
    this.deliveryRemark = deliveryRemark;
  }

  public void setCommodityInfo(CartCommodityInfo info) {
    if (getShopCode().equals(info.getShopCode())) {
      List<CartCommodityInfo> newList = this.getCommodityInfoList();
      CartCommodityInfo removeItem = new CartCommodityInfo();
      for (CartCommodityInfo cartInfo : this.commodityInfoList) {
        if (cartInfo.getSkuCode().equals(info.getSkuCode())) {
          removeItem = cartInfo;
        }
      }
      newList.remove(removeItem);
      newList.add(info);

      this.setCommodityInfoList(newList);
    } else {
      Logger logger = Logger.getLogger(this.getClass());
      logger.error("shop code is failed in setCommodityInfo");
      throw new RuntimeException();
    }
  }

  /**
   * @return the shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * @param shopName
   *          the shopName to set
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

/**
 * @return the deliveryDateCommssion
 */
public BigDecimal getDeliveryDateCommssion() {
	return deliveryDateCommssion;
}

/**
 * @param deliveryDateCommssion the deliveryDateCommssion to set
 */
public void setDeliveryDateCommssion(BigDecimal deliveryDateCommssion) {
	this.deliveryDateCommssion = deliveryDateCommssion;
}


/**
 * @return the deliveryAppointedTimeZone
 */
public String getDeliveryAppointedTimeZone() {
  return deliveryAppointedTimeZone;
}


/**
 * @param deliveryAppointedTimeZone the deliveryAppointedTimeZone to set
 */
public void setDeliveryAppointedTimeZone(String deliveryAppointedTimeZone) {
  this.deliveryAppointedTimeZone = deliveryAppointedTimeZone;
}

}
