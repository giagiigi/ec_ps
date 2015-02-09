package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.DeliveryType;

public interface CashierShipping extends Serializable {

  String getShopCode();

  String getShopName();

  /**
   * CashierItemごとにかかる料金<br>
   * （商品販売価格 ＋ ギフト価格）× 個数 ＋ 送料
   * 
   * @return CashierItemごとにかかる料金
   */
  BigDecimal getTotalPrice();

  CustomerAddress getAddress();

  DeliveryType getDeliveryType();

  /**
   * 送料（税込）を取得する
   * 
   * @return 送料（税込）
   */
  BigDecimal getShippingCharge();

  /**
   * 送料の税額を取得する
   * 
   * @return 送料の税額
   */
  BigDecimal getShippingChargeTax();

  /**
   * お届け指定日を取得する
   * 
   * @return お届け指定日
   */
  // 20111228 shen update start
  // Date getDeliveryAppointedDate();
  String getDeliveryAppointedDate();
  // 20111228 shen update end

  String getDeliveryAppointedTimeZone();
  
  /**
   * お届け指定の時間帯開始時刻を取得する
   * 
   * @return お届け指定の時間帯開始時刻
   */
  String getDeliveryAppointedStartTime();

  /**
   * お届け指定の時間帯終了時刻を取得する
   * 
   * @return お届け指定の時間帯終了時刻
   */
  String getDeliveryAppointedTimeEnd();

  String getDeliveryRemark();

  /**
   * 送料（税込）を設定する
   * 
   * @param shippingCharge
   */
  void setShippingCharge(BigDecimal shippingCharge);

  /**
   * 送料の税額を設定する
   * 
   * @param shippingChargeTax
   */
  void setShippingChargeTax(BigDecimal shippingChargeTax);

  /**
   * お届け指定日を設定する
   * 
   * @param deliveryAppointedDate
   */
  // 20111228 shen update start
  // void setDeliveryAppointedDate(Date deliveryAppointedDate);
  void setDeliveryAppointedDate(String deliveryAppointedDate);
  // 20111228 shen update end
  
  void setDeliveryAppointedTimeZone(String deliveryAppointedTime);

  /**
   * お届け指定の時間帯Startを設定する
   * 
   * @param appointedStartTime
   */
  void setDeliveryAppointedStartTime(String appointedStartTime);

  /**
   * お届け指定の時間帯Endを設定する
   * 
   * @param appointedStartTime
   */
  void setDeliveryAppointedTimeEnd(String appointedStartTime);

  void setDeliveryRemark(String deliveryRemark);

  List<CartCommodityInfo> getCommodityInfoList();

  void addCommodityInfo(CartCommodityInfo info);

  void setCommodityInfo(CartCommodityInfo info);
  
  //soukai add 2012/01/08 ob start
  BigDecimal getDeliveryDateCommssion();
  
  void setDeliveryDateCommssion(BigDecimal deliveryDateCommssion);
  
  //soukai add 2012/01/08 ob end
  
}
